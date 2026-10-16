package gg.vape.shaded.org.objectweb.asm.commons;

import gg.vape.shaded.org.objectweb.asm.ConstantDynamic;
import gg.vape.shaded.org.objectweb.asm.Handle;
import gg.vape.shaded.org.objectweb.asm.Label;
import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import gg.vape.shaded.org.objectweb.asm.Opcodes;
import gg.vape.shaded.org.objectweb.asm.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AdviceAdapter extends GeneratorAdapter implements Opcodes {
   private static final Object UNINITIALIZED_THIS = new Object();
   private static final Object OTHER = new Object();
   private static final String INVALID_OPCODE = "Invalid opcode ";
   protected int methodAccess;
   protected String methodDesc;
   private final boolean isConstructor;
   private boolean superClassConstructorCalled;
   private List<Object> stackFrame;
   private Map<Label, List<Object>> forwardJumpStackFrames;

   protected AdviceAdapter(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
      super(api, methodVisitor, access, name, descriptor);
      this.methodAccess = access;
      this.methodDesc = descriptor;
      this.isConstructor = "<init>".equals(name);
   }

   @Override
   public void visitCode() {
      super.visitCode();
      if (this.isConstructor) {
         this.stackFrame = new ArrayList<>();
         this.forwardJumpStackFrames = new HashMap<>();
      } else {
         this.onMethodEnter();
      }
   }

   @Override
   public void visitLabel(Label label) {
      super.visitLabel(label);
      if (this.isConstructor && this.forwardJumpStackFrames != null) {
         List<Object> labelStackFrame = this.forwardJumpStackFrames.get(label);
         if (labelStackFrame != null) {
            this.stackFrame = labelStackFrame;
            this.superClassConstructorCalled = false;
            this.forwardJumpStackFrames.remove(label);
         }
      }
   }

   @Override
   public void visitInsn(int opcode) {
      if (this.isConstructor && !this.superClassConstructorCalled) {
         switch (opcode) {
            case 0:
            case 47:
            case 49:
            case 116:
            case 117:
            case 118:
            case 119:
            case 134:
            case 138:
            case 139:
            case 143:
            case 145:
            case 146:
            case 147:
            case 190:
               break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 11:
            case 12:
            case 13:
            case 133:
            case 135:
            case 140:
            case 141:
               this.pushValue(OTHER);
               break;
            case 9:
            case 10:
            case 14:
            case 15:
               this.pushValue(OTHER);
               this.pushValue(OTHER);
               break;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 132:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
            case 167:
            case 168:
            case 169:
            case 170:
            case 171:
            case 178:
            case 179:
            case 180:
            case 181:
            case 182:
            case 183:
            case 184:
            case 185:
            case 186:
            case 187:
            case 188:
            case 189:
            case 192:
            case 193:
            default:
               throw new IllegalArgumentException(stringConcat$0(opcode));
            case 46:
            case 48:
            case 50:
            case 51:
            case 52:
            case 53:
            case 87:
            case 96:
            case 98:
            case 100:
            case 102:
            case 104:
            case 106:
            case 108:
            case 110:
            case 112:
            case 114:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 128:
            case 130:
            case 136:
            case 137:
            case 142:
            case 144:
            case 149:
            case 150:
            case 194:
            case 195:
               this.popValue();
               break;
            case 79:
            case 81:
            case 83:
            case 84:
            case 85:
            case 86:
            case 148:
            case 151:
            case 152:
               this.popValue();
               this.popValue();
               this.popValue();
               break;
            case 80:
            case 82:
               this.popValue();
               this.popValue();
               this.popValue();
               this.popValue();
               break;
            case 88:
            case 97:
            case 99:
            case 101:
            case 103:
            case 105:
            case 107:
            case 109:
            case 111:
            case 113:
            case 115:
            case 127:
            case 129:
            case 131:
               this.popValue();
               this.popValue();
               break;
            case 89:
               this.pushValue(this.peekValue());
               break;
            case 90: {
               int stackSize = this.stackFrame.size();
               this.stackFrame.add(stackSize - 2, this.stackFrame.get(stackSize - 1));
               break;
            }
            case 91: {
               int stackSize = this.stackFrame.size();
               this.stackFrame.add(stackSize - 3, this.stackFrame.get(stackSize - 1));
               break;
            }
            case 92: {
               int stackSize = this.stackFrame.size();
               this.stackFrame.add(stackSize - 2, this.stackFrame.get(stackSize - 1));
               this.stackFrame.add(stackSize - 2, this.stackFrame.get(stackSize - 1));
               break;
            }
            case 93: {
               int stackSize = this.stackFrame.size();
               this.stackFrame.add(stackSize - 3, this.stackFrame.get(stackSize - 1));
               this.stackFrame.add(stackSize - 3, this.stackFrame.get(stackSize - 1));
               break;
            }
            case 94: {
               int stackSize = this.stackFrame.size();
               this.stackFrame.add(stackSize - 4, this.stackFrame.get(stackSize - 1));
               this.stackFrame.add(stackSize - 4, this.stackFrame.get(stackSize - 1));
               break;
            }
            case 95: {
               int stackSize = this.stackFrame.size();
               this.stackFrame.add(stackSize - 2, this.stackFrame.get(stackSize - 1));
               this.stackFrame.remove(stackSize);
               break;
            }
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
               throw new IllegalArgumentException("Invalid return in constructor");
            case 177:
               this.onMethodExit(opcode);
               this.endConstructorBasicBlockWithoutSuccessor();
               break;
            case 191:
               this.popValue();
               this.onMethodExit(opcode);
               this.endConstructorBasicBlockWithoutSuccessor();
         }
      } else {
         switch (opcode) {
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
            case 177:
            case 191:
               this.onMethodExit(opcode);
            case 178:
            case 179:
            case 180:
            case 181:
            case 182:
            case 183:
            case 184:
            case 185:
            case 186:
            case 187:
            case 188:
            case 189:
            case 190:
         }
      }

      super.visitInsn(opcode);
   }

   @Override
   public void visitVarInsn(int opcode, int varIndex) {
      super.visitVarInsn(opcode, varIndex);
      if (this.isConstructor && !this.superClassConstructorCalled) {
         switch (opcode) {
            case 21:
            case 23:
               this.pushValue(OTHER);
               break;
            case 22:
            case 24:
               this.pushValue(OTHER);
               this.pushValue(OTHER);
               break;
            case 25:
               this.pushValue(varIndex == 0 ? UNINITIALIZED_THIS : OTHER);
               break;
            case 54:
            case 56:
            case 58:
               this.popValue();
               break;
            case 55:
            case 57:
               this.popValue();
               this.popValue();
               break;
            case 169:
               this.endConstructorBasicBlockWithoutSuccessor();
               break;
            default:
               throw new IllegalArgumentException(stringConcat$1(opcode));
         }
      }
   }

   @Override
   public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
      super.visitFieldInsn(opcode, owner, name, descriptor);
      if (this.isConstructor && !this.superClassConstructorCalled) {
         char firstDescriptorChar = descriptor.charAt(0);
         boolean longOrDouble = firstDescriptorChar == 'J' || firstDescriptorChar == 'D';
         switch (opcode) {
            case 178:
               this.pushValue(OTHER);
               if (longOrDouble) {
                  this.pushValue(OTHER);
               }
               break;
            case 179:
               this.popValue();
               if (longOrDouble) {
                  this.popValue();
               }
               break;
            case 180:
               if (longOrDouble) {
                  this.pushValue(OTHER);
               }
               break;
            case 181:
               this.popValue();
               this.popValue();
               if (longOrDouble) {
                  this.popValue();
               }
               break;
            default:
               throw new IllegalArgumentException(stringConcat$2(opcode));
         }
      }
   }

   @Override
   public void visitIntInsn(int opcode, int operand) {
      super.visitIntInsn(opcode, operand);
      if (this.isConstructor && !this.superClassConstructorCalled && opcode != 188) {
         this.pushValue(OTHER);
      }
   }

   @Override
   public void visitLdcInsn(Object value) {
      super.visitLdcInsn(value);
      if (this.isConstructor && !this.superClassConstructorCalled) {
         this.pushValue(OTHER);
         if (value instanceof Double || value instanceof Long || value instanceof ConstantDynamic && ((ConstantDynamic)value).getSize() == 2) {
            this.pushValue(OTHER);
         }
      }
   }

   @Override
   public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
      super.visitMultiANewArrayInsn(descriptor, numDimensions);
      if (this.isConstructor && !this.superClassConstructorCalled) {
         for (int i = 0; i < numDimensions; i++) {
            this.popValue();
         }

         this.pushValue(OTHER);
      }
   }

   @Override
   public void visitTypeInsn(int opcode, String type) {
      super.visitTypeInsn(opcode, type);
      if (this.isConstructor && !this.superClassConstructorCalled && opcode == 187) {
         this.pushValue(OTHER);
      }
   }

   @Override
   public void visitMethodInsn(int opcodeAndSource, String owner, String name, String descriptor, boolean isInterface) {
      if (this.api < 327680 && (opcodeAndSource & 256) == 0) {
         super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface);
      } else {
         super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface);
         int opcode = opcodeAndSource & -257;
         this.doVisitMethodInsn(opcode, name, descriptor);
      }
   }

   private void doVisitMethodInsn(int opcode, String name, String descriptor) {
      if (this.isConstructor && !this.superClassConstructorCalled) {
         for (Type argumentType : Type.getArgumentTypes(descriptor)) {
            this.popValue();
            if (argumentType.getSize() == 2) {
               this.popValue();
            }
         }

         switch (opcode) {
            case 182:
            case 185:
               this.popValue();
               break;
            case 183:
               Object value = this.popValue();
               if (value == UNINITIALIZED_THIS && !this.superClassConstructorCalled && name.equals("<init>")) {
                  this.superClassConstructorCalled = true;
                  this.onMethodEnter();
               }
            case 184:
         }

         Type returnType = Type.getReturnType(descriptor);
         if (returnType != Type.VOID_TYPE) {
            this.pushValue(OTHER);
            if (returnType.getSize() == 2) {
               this.pushValue(OTHER);
            }
         }
      }
   }

   @Override
   public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
      super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
      this.doVisitMethodInsn(186, name, descriptor);
   }

   @Override
   public void visitJumpInsn(int opcode, Label label) {
      super.visitJumpInsn(opcode, label);
      if (this.isConstructor && !this.superClassConstructorCalled) {
         switch (opcode) {
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 198:
            case 199:
               this.popValue();
               break;
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
               this.popValue();
               this.popValue();
               break;
            case 167:
               this.endConstructorBasicBlockWithoutSuccessor();
               break;
            case 168:
               this.pushValue(OTHER);
            case 169:
            case 170:
            case 171:
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
            case 177:
            case 178:
            case 179:
            case 180:
            case 181:
            case 182:
            case 183:
            case 184:
            case 185:
            case 186:
            case 187:
            case 188:
            case 189:
            case 190:
            case 191:
            case 192:
            case 193:
            case 194:
            case 195:
            case 196:
            case 197:
         }

         this.addForwardJump(label);
      }
   }

   @Override
   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
      super.visitLookupSwitchInsn(dflt, keys, labels);
      if (this.isConstructor && !this.superClassConstructorCalled) {
         this.popValue();
         this.addForwardJumps(dflt, labels);
         this.endConstructorBasicBlockWithoutSuccessor();
      }
   }

   @Override
   public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
      super.visitTableSwitchInsn(min, max, dflt, labels);
      if (this.isConstructor && !this.superClassConstructorCalled) {
         this.popValue();
         this.addForwardJumps(dflt, labels);
         this.endConstructorBasicBlockWithoutSuccessor();
      }
   }

   @Override
   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
      super.visitTryCatchBlock(start, end, handler, type);
      if (this.isConstructor && !this.forwardJumpStackFrames.containsKey(handler)) {
         List<Object> handlerStackFrame = new ArrayList<>();
         handlerStackFrame.add(OTHER);
         this.forwardJumpStackFrames.put(handler, handlerStackFrame);
      }
   }

   private void addForwardJumps(Label dflt, Label[] labels) {
      this.addForwardJump(dflt);

      for (Label label : labels) {
         this.addForwardJump(label);
      }
   }

   private void addForwardJump(Label label) {
      if (!this.forwardJumpStackFrames.containsKey(label)) {
         this.forwardJumpStackFrames.put(label, new ArrayList<>(this.stackFrame));
      }
   }

   private void endConstructorBasicBlockWithoutSuccessor() {
      this.superClassConstructorCalled = true;
   }

   private Object popValue() {
      return this.stackFrame.remove(this.stackFrame.size() - 1);
   }

   private Object peekValue() {
      return this.stackFrame.get(this.stackFrame.size() - 1);
   }

   private void pushValue(Object value) {
      this.stackFrame.add(value);
   }

   protected void onMethodEnter() {
   }

   protected void onMethodExit(int opcode) {
   }
}
