package gg.vape.shaded.org.objectweb.asm.util;

import gg.vape.shaded.org.objectweb.asm.Opcodes;
import gg.vape.shaded.org.objectweb.asm.Type;
import gg.vape.shaded.org.objectweb.asm.tree.AbstractInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.FrameNode;
import gg.vape.shaded.org.objectweb.asm.tree.InsnList;
import gg.vape.shaded.org.objectweb.asm.tree.InsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.JumpInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.LabelNode;
import gg.vape.shaded.org.objectweb.asm.tree.LookupSwitchInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.MethodNode;
import gg.vape.shaded.org.objectweb.asm.tree.TableSwitchInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.TryCatchBlockNode;
import gg.vape.shaded.org.objectweb.asm.tree.TypeInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.analysis.Analyzer;
import gg.vape.shaded.org.objectweb.asm.tree.analysis.AnalyzerException;
import gg.vape.shaded.org.objectweb.asm.tree.analysis.Frame;
import gg.vape.shaded.org.objectweb.asm.tree.analysis.Interpreter;
import gg.vape.shaded.org.objectweb.asm.tree.analysis.Value;
import java.util.Collections;
import java.util.List;

class CheckFrameAnalyzer<V extends Value> extends Analyzer<V> {
   private final Interpreter<V> interpreter;
   private InsnList insnList;
   private int currentLocals;

   CheckFrameAnalyzer(Interpreter<V> interpreter) {
      super(interpreter);
      this.interpreter = interpreter;
   }

   @Override
   protected void init(String owner, MethodNode method) throws AnalyzerException {
      this.insnList = method.instructions;
      this.currentLocals = Type.getArgumentsAndReturnSizes(method.desc) >> 2;
      if ((method.access & 8) != 0) {
         this.currentLocals--;
      }

      Frame<V>[] frames = this.getFrames();
      Frame<V> currentFrame = this.newFrame(frames[0]);
      this.expandFrames(owner, method, currentFrame);

      for (int insnIndex = 0; insnIndex < this.insnList.size(); insnIndex++) {
         Frame<V> oldFrame = frames[insnIndex];
         AbstractInsnNode insnNode = null;

         try {
            insnNode = method.instructions.get(insnIndex);
            int insnOpcode = insnNode.getOpcode();
            int insnType = insnNode.getType();
            if (insnType != 8 && insnType != 15 && insnType != 14) {
               currentFrame.init(oldFrame).execute(insnNode, this.interpreter);
               if (insnNode instanceof JumpInsnNode) {
                  if (insnOpcode == 168) {
                     throw new AnalyzerException(insnNode, "JSR instructions are unsupported");
                  }

                  JumpInsnNode jumpInsn = (JumpInsnNode)insnNode;
                  int targetInsnIndex = this.insnList.indexOf(jumpInsn.label);
                  this.checkFrame(targetInsnIndex, currentFrame, true);
                  if (insnOpcode == 167) {
                     this.endControlFlow(insnIndex);
                  } else {
                     this.checkFrame(insnIndex + 1, currentFrame, false);
                  }
               } else if (insnNode instanceof LookupSwitchInsnNode) {
                  LookupSwitchInsnNode lookupSwitchInsn = (LookupSwitchInsnNode)insnNode;
                  int targetInsnIndex = this.insnList.indexOf(lookupSwitchInsn.dflt);
                  this.checkFrame(targetInsnIndex, currentFrame, true);

                  for (int i = 0; i < lookupSwitchInsn.labels.size(); i++) {
                     LabelNode label = lookupSwitchInsn.labels.get(i);
                     targetInsnIndex = this.insnList.indexOf(label);
                     currentFrame.initJumpTarget(insnOpcode, label);
                     this.checkFrame(targetInsnIndex, currentFrame, true);
                  }

                  this.endControlFlow(insnIndex);
               } else if (insnNode instanceof TableSwitchInsnNode) {
                  TableSwitchInsnNode tableSwitchInsn = (TableSwitchInsnNode)insnNode;
                  int targetInsnIndex = this.insnList.indexOf(tableSwitchInsn.dflt);
                  currentFrame.initJumpTarget(insnOpcode, tableSwitchInsn.dflt);
                  this.checkFrame(targetInsnIndex, currentFrame, true);
                  this.newControlFlowEdge(insnIndex, targetInsnIndex);

                  for (int i = 0; i < tableSwitchInsn.labels.size(); i++) {
                     LabelNode label = tableSwitchInsn.labels.get(i);
                     currentFrame.initJumpTarget(insnOpcode, label);
                     targetInsnIndex = this.insnList.indexOf(label);
                     this.checkFrame(targetInsnIndex, currentFrame, true);
                  }

                  this.endControlFlow(insnIndex);
               } else {
                  if (insnOpcode == 169) {
                     throw new AnalyzerException(insnNode, "RET instructions are unsupported");
                  }

                  if (insnOpcode == 191 || insnOpcode >= 172 && insnOpcode <= 177) {
                     this.endControlFlow(insnIndex);
                  } else {
                     this.checkFrame(insnIndex + 1, currentFrame, false);
                  }
               }
            } else {
               this.checkFrame(insnIndex + 1, oldFrame, false);
            }

            List<TryCatchBlockNode> insnHandlers = this.getHandlers(insnIndex);
            if (insnHandlers != null) {
               for (TryCatchBlockNode tryCatchBlock : insnHandlers) {
                  Type catchType;
                  if (tryCatchBlock.type == null) {
                     catchType = Type.getObjectType("java/lang/Throwable");
                  } else {
                     catchType = Type.getObjectType(tryCatchBlock.type);
                  }

                  Frame<V> handler = this.newFrame(oldFrame);
                  handler.clearStack();
                  handler.push(this.interpreter.newExceptionValue(tryCatchBlock, handler, catchType));
                  this.checkFrame(this.insnList.indexOf(tryCatchBlock.handler), handler, true);
               }
            }

            if (!this.hasNextJvmInsnOrFrame(insnIndex)) {
               break;
            }
         } catch (AnalyzerException var15) {
            throw new AnalyzerException(var15.node, stringConcat$0(insnIndex, var15.getMessage()), var15);
         } catch (RuntimeException var16) {
            throw new AnalyzerException(insnNode, stringConcat$1(insnIndex, var16.getMessage()), var16);
         }
      }
   }

   private void expandFrames(String owner, MethodNode method, Frame<V> initialFrame) throws AnalyzerException {
      int lastJvmOrFrameInsnIndex = -1;
      Frame<V> currentFrame = initialFrame;
      int currentInsnIndex = 0;

      for (AbstractInsnNode insnNode : method.instructions) {
         if (insnNode instanceof FrameNode) {
            try {
               currentFrame = this.expandFrame(owner, currentFrame, (FrameNode)insnNode);
            } catch (AnalyzerException var10) {
               throw new AnalyzerException(var10.node, stringConcat$2(currentInsnIndex, var10.getMessage()), var10);
            }

            for (int index = lastJvmOrFrameInsnIndex + 1; index <= currentInsnIndex; index++) {
               this.getFrames()[index] = currentFrame;
            }
         }

         if (isJvmInsnNode(insnNode) || insnNode instanceof FrameNode) {
            lastJvmOrFrameInsnIndex = currentInsnIndex;
         }

         currentInsnIndex++;
      }
   }

   private Frame<V> expandFrame(String owner, Frame<V> previousFrame, FrameNode frameNode) throws AnalyzerException {
      Frame<V> frame = this.newFrame(previousFrame);
      List<Object> locals = frameNode.local == null ? Collections.emptyList() : frameNode.local;
      int currentLocal = this.currentLocals;
      switch (frameNode.type) {
         case -1:
         case 0:
            currentLocal = 0;
         case 1:
            for (Object type : locals) {
               V value = this.newFrameValue(owner, frameNode, type);
               if (currentLocal + value.getSize() > frame.getLocals()) {
                  throw new AnalyzerException(frameNode, "Cannot append more locals than maxLocals");
               }

               frame.setLocal(currentLocal++, value);
               if (value.getSize() == 2) {
                  frame.setLocal(currentLocal++, this.interpreter.newValue(null));
               }
            }
            break;
         case 2:
            for (Object unusedType : locals) {
               if (currentLocal <= 0) {
                  throw new AnalyzerException(frameNode, "Cannot chop more locals than defined");
               }

               if (currentLocal > 1 && frame.getLocal(currentLocal - 2).getSize() == 2) {
                  currentLocal -= 2;
               } else {
                  currentLocal--;
               }
            }
         case 3:
         case 4:
            break;
         default:
            throw new AnalyzerException(frameNode, stringConcat$3(frameNode.type));
      }

      this.currentLocals = currentLocal;

      while (currentLocal < frame.getLocals()) {
         frame.setLocal(currentLocal++, this.interpreter.newValue(null));
      }

      List<Object> stack = frameNode.stack == null ? Collections.emptyList() : frameNode.stack;
      frame.clearStack();

      for (Object type : stack) {
         frame.push(this.newFrameValue(owner, frameNode, type));
      }

      return frame;
   }

   private V newFrameValue(String owner, FrameNode frameNode, Object type) throws AnalyzerException {
      if (type == Opcodes.TOP) {
         return this.interpreter.newValue(null);
      } else if (type == Opcodes.INTEGER) {
         return this.interpreter.newValue(Type.INT_TYPE);
      } else if (type == Opcodes.FLOAT) {
         return this.interpreter.newValue(Type.FLOAT_TYPE);
      } else if (type == Opcodes.LONG) {
         return this.interpreter.newValue(Type.LONG_TYPE);
      } else if (type == Opcodes.DOUBLE) {
         return this.interpreter.newValue(Type.DOUBLE_TYPE);
      } else if (type == Opcodes.NULL) {
         return this.interpreter.newOperation(new InsnNode(1));
      } else if (type == Opcodes.UNINITIALIZED_THIS) {
         return this.interpreter.newValue(Type.getObjectType(owner));
      } else if (type instanceof String) {
         return this.interpreter.newValue(Type.getObjectType((String)type));
      } else if (!(type instanceof LabelNode)) {
         throw new AnalyzerException(frameNode, stringConcat$4(type));
      } else {
         AbstractInsnNode referencedNode = (LabelNode)type;

         while (referencedNode != null && !isJvmInsnNode(referencedNode)) {
            referencedNode = referencedNode.getNext();
         }

         if (referencedNode != null && referencedNode.getOpcode() == 187) {
            return this.interpreter.newValue(Type.getObjectType(((TypeInsnNode)referencedNode).desc));
         } else {
            throw new AnalyzerException(frameNode, "LabelNode does not designate a NEW instruction");
         }
      }
   }

   private void checkFrame(int insnIndex, Frame<V> frame, boolean requireFrame) throws AnalyzerException {
      Frame<V> oldFrame = this.getFrames()[insnIndex];
      if (oldFrame == null) {
         if (requireFrame) {
            throw new AnalyzerException(null, stringConcat$5(insnIndex));
         }

         this.getFrames()[insnIndex] = this.newFrame(frame);
      } else {
         String error = this.checkMerge(frame, oldFrame);
         if (error != null) {
            throw new AnalyzerException(null, stringConcat$6(insnIndex, error));
         }
      }
   }

   private String checkMerge(Frame<V> srcFrame, Frame<V> dstFrame) {
      int numLocals = srcFrame.getLocals();
      if (numLocals != dstFrame.getLocals()) {
         throw new AssertionError();
      } else {
         for (int i = 0; i < numLocals; i++) {
            V v = this.interpreter.merge(srcFrame.getLocal(i), dstFrame.getLocal(i));
            if (!v.equals(dstFrame.getLocal(i))) {
               return stringConcat$7(i, srcFrame.getLocal(i), dstFrame.getLocal(i));
            }
         }

         int numStack = srcFrame.getStackSize();
         if (numStack != dstFrame.getStackSize()) {
            return "incompatible stack heights";
         } else {
            for (int ix = 0; ix < numStack; ix++) {
               V v = this.interpreter.merge(srcFrame.getStack(ix), dstFrame.getStack(ix));
               if (!v.equals(dstFrame.getStack(ix))) {
                  return stringConcat$8(ix, srcFrame.getStack(ix), dstFrame.getStack(ix));
               }
            }

            return null;
         }
      }
   }

   private void endControlFlow(int insnIndex) throws AnalyzerException {
      if (this.hasNextJvmInsnOrFrame(insnIndex) && this.getFrames()[insnIndex + 1] == null) {
         throw new AnalyzerException(null, stringConcat$9(insnIndex + 1));
      }
   }

   private boolean hasNextJvmInsnOrFrame(int insnIndex) {
      for (AbstractInsnNode insn = this.insnList.get(insnIndex).getNext(); insn != null; insn = insn.getNext()) {
         if (isJvmInsnNode(insn) || insn instanceof FrameNode) {
            return true;
         }
      }

      return false;
   }

   private static boolean isJvmInsnNode(AbstractInsnNode insnNode) {
      return insnNode.getOpcode() >= 0;
   }
}
