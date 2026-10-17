/*
 * Decompiled with CFR 0.152.
 */
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

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class CheckFrameAnalyzer<V extends Value>
extends Analyzer<V> {
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
            --this.currentLocals;
        }
        Frame<V>[] frames = this.getFrames();
        Frame currentFrame = this.newFrame(frames[0]);
        this.expandFrames(owner, method, currentFrame);
        for (int insnIndex = 0; insnIndex < this.insnList.size(); ++insnIndex) {
            Frame oldFrame = frames[insnIndex];
            AbstractInsnNode insnNode = null;
            try {
                insnNode = method.instructions.get(insnIndex);
                int insnOpcode = insnNode.getOpcode();
                int insnType = insnNode.getType();
                if (insnType == 8 || insnType == 15 || insnType == 14) {
                    this.checkFrame(insnIndex + 1, oldFrame, false);
                } else {
                    LabelNode label;
                    int i;
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
                        for (i = 0; i < lookupSwitchInsn.labels.size(); ++i) {
                            label = lookupSwitchInsn.labels.get(i);
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
                        for (i = 0; i < tableSwitchInsn.labels.size(); ++i) {
                            label = tableSwitchInsn.labels.get(i);
                            currentFrame.initJumpTarget(insnOpcode, label);
                            targetInsnIndex = this.insnList.indexOf(label);
                            this.checkFrame(targetInsnIndex, currentFrame, true);
                        }
                        this.endControlFlow(insnIndex);
                    } else {
                        if (insnOpcode == 169) {
                            throw new AnalyzerException(insnNode, "RET instructions are unsupported");
                        }
                        if (insnOpcode != 191 && (insnOpcode < 172 || insnOpcode > 177)) {
                            this.checkFrame(insnIndex + 1, currentFrame, false);
                        } else {
                            this.endControlFlow(insnIndex);
                        }
                    }
                }
                List<TryCatchBlockNode> insnHandlers = this.getHandlers(insnIndex);
                if (insnHandlers != null) {
                    for (TryCatchBlockNode tryCatchBlock : insnHandlers) {
                        Type catchType = tryCatchBlock.type == null ? Type.getObjectType("java/lang/Throwable") : Type.getObjectType(tryCatchBlock.type);
                        Frame<V> handler = this.newFrame(oldFrame);
                        handler.clearStack();
                        handler.push(this.interpreter.newExceptionValue(tryCatchBlock, handler, catchType));
                        this.checkFrame(this.insnList.indexOf(tryCatchBlock.handler), handler, true);
                    }
                }
                if (this.hasNextJvmInsnOrFrame(insnIndex)) continue;
                break;
            }
            catch (AnalyzerException e) {
                throw new AnalyzerException(e.node, CheckFrameAnalyzer.stringConcat$0(insnIndex, e.getMessage()), e);
            }
            catch (RuntimeException e) {
                throw new AnalyzerException(insnNode, CheckFrameAnalyzer.stringConcat$1(insnIndex, e.getMessage()), e);
            }
        }
    }

    private static /* synthetic */ String stringConcat$0(int n, String string) {
        return "Error at instruction " + n + ": " + string;
    }

    private static /* synthetic */ String stringConcat$1(int n, String string) {
        return "Error at instruction " + n + ": " + string;
    }

    private void expandFrames(String owner, MethodNode method, Frame<V> initialFrame) throws AnalyzerException {
        int lastJvmOrFrameInsnIndex = -1;
        Frame<V> currentFrame = initialFrame;
        int currentInsnIndex = 0;
        for (AbstractInsnNode insnNode : method.instructions) {
            if (insnNode instanceof FrameNode) {
                try {
                    currentFrame = this.expandFrame(owner, currentFrame, (FrameNode)insnNode);
                }
                catch (AnalyzerException e) {
                    throw new AnalyzerException(e.node, CheckFrameAnalyzer.stringConcat$2(currentInsnIndex, e.getMessage()), e);
                }
                for (int index = lastJvmOrFrameInsnIndex + 1; index <= currentInsnIndex; ++index) {
                    this.getFrames()[index] = currentFrame;
                }
            }
            if (CheckFrameAnalyzer.isJvmInsnNode(insnNode) || insnNode instanceof FrameNode) {
                lastJvmOrFrameInsnIndex = currentInsnIndex;
            }
            ++currentInsnIndex;
        }
    }

    private static /* synthetic */ String stringConcat$2(int n, String string) {
        return "Error at instruction " + n + ": " + string;
    }

    private Frame<V> expandFrame(String owner, Frame<V> previousFrame, FrameNode frameNode) throws AnalyzerException {
        Frame<V> frame = this.newFrame(previousFrame);
        List<Object> locals = frameNode.local == null ? Collections.emptyList() : frameNode.local;
        int currentLocal = this.currentLocals;
        switch (frameNode.type) {
            case -1: 
            case 0: {
                currentLocal = 0;
            }
            case 1: {
                for (Object type : locals) {
                    V value = this.newFrameValue(owner, frameNode, type);
                    if (currentLocal + value.getSize() > frame.getLocals()) {
                        throw new AnalyzerException(frameNode, "Cannot append more locals than maxLocals");
                    }
                    frame.setLocal(currentLocal++, value);
                    if (value.getSize() != 2) continue;
                    frame.setLocal(currentLocal++, this.interpreter.newValue(null));
                }
                break;
            }
            case 2: {
                for (Object unusedType : locals) {
                    if (currentLocal <= 0) {
                        throw new AnalyzerException(frameNode, "Cannot chop more locals than defined");
                    }
                    if (currentLocal > 1 && frame.getLocal(currentLocal - 2).getSize() == 2) {
                        currentLocal -= 2;
                        continue;
                    }
                    --currentLocal;
                }
                break;
            }
            case 3: 
            case 4: {
                break;
            }
            default: {
                throw new AnalyzerException(frameNode, CheckFrameAnalyzer.stringConcat$3(frameNode.type));
            }
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

    private static /* synthetic */ String stringConcat$3(int n) {
        return "Illegal frame type " + n;
    }

    private V newFrameValue(String owner, FrameNode frameNode, Object type) throws AnalyzerException {
        if (type == Opcodes.TOP) {
            return this.interpreter.newValue(null);
        }
        if (type == Opcodes.INTEGER) {
            return this.interpreter.newValue(Type.INT_TYPE);
        }
        if (type == Opcodes.FLOAT) {
            return this.interpreter.newValue(Type.FLOAT_TYPE);
        }
        if (type == Opcodes.LONG) {
            return this.interpreter.newValue(Type.LONG_TYPE);
        }
        if (type == Opcodes.DOUBLE) {
            return this.interpreter.newValue(Type.DOUBLE_TYPE);
        }
        if (type == Opcodes.NULL) {
            return this.interpreter.newOperation(new InsnNode(1));
        }
        if (type == Opcodes.UNINITIALIZED_THIS) {
            return this.interpreter.newValue(Type.getObjectType(owner));
        }
        if (type instanceof String) {
            return this.interpreter.newValue(Type.getObjectType((String)type));
        }
        if (type instanceof LabelNode) {
            AbstractInsnNode referencedNode;
            for (referencedNode = (LabelNode)type; referencedNode != null && !CheckFrameAnalyzer.isJvmInsnNode(referencedNode); referencedNode = referencedNode.getNext()) {
            }
            if (referencedNode == null || referencedNode.getOpcode() != 187) {
                throw new AnalyzerException(frameNode, "LabelNode does not designate a NEW instruction");
            }
            return this.interpreter.newValue(Type.getObjectType(((TypeInsnNode)referencedNode).desc));
        }
        throw new AnalyzerException(frameNode, CheckFrameAnalyzer.stringConcat$4(type));
    }

    private static /* synthetic */ String stringConcat$4(Object object) {
        return "Illegal stack map frame value " + object;
    }

    private void checkFrame(int insnIndex, Frame<V> frame, boolean requireFrame) throws AnalyzerException {
        Frame oldFrame = this.getFrames()[insnIndex];
        if (oldFrame == null) {
            if (requireFrame) {
                throw new AnalyzerException(null, CheckFrameAnalyzer.stringConcat$5(insnIndex));
            }
            this.getFrames()[insnIndex] = this.newFrame(frame);
        } else {
            String error = this.checkMerge(frame, oldFrame);
            if (error != null) {
                throw new AnalyzerException(null, CheckFrameAnalyzer.stringConcat$6(insnIndex, error));
            }
        }
    }

    private static /* synthetic */ String stringConcat$5(int n) {
        return "Expected stack map frame at instruction " + n;
    }

    private static /* synthetic */ String stringConcat$6(int n, String string) {
        return "Stack map frame incompatible with frame at instruction " + n + " (" + string + ")";
    }

    private String checkMerge(Frame<V> srcFrame, Frame<V> dstFrame) {
        int numLocals = srcFrame.getLocals();
        if (numLocals != dstFrame.getLocals()) {
            throw new AssertionError();
        }
        for (int i = 0; i < numLocals; ++i) {
            V v = this.interpreter.merge(srcFrame.getLocal(i), dstFrame.getLocal(i));
            if (v.equals(dstFrame.getLocal(i))) continue;
            return CheckFrameAnalyzer.stringConcat$7(i, srcFrame.getLocal(i), dstFrame.getLocal(i));
        }
        int numStack = srcFrame.getStackSize();
        if (numStack != dstFrame.getStackSize()) {
            return "incompatible stack heights";
        }
        for (int i = 0; i < numStack; ++i) {
            V v = this.interpreter.merge(srcFrame.getStack(i), dstFrame.getStack(i));
            if (v.equals(dstFrame.getStack(i))) continue;
            return CheckFrameAnalyzer.stringConcat$8(i, srcFrame.getStack(i), dstFrame.getStack(i));
        }
        return null;
    }

    private static /* synthetic */ String stringConcat$7(int n, Value value, Value value2) {
        return "incompatible types at local " + n + ": " + value + " and " + value2;
    }

    private static /* synthetic */ String stringConcat$8(int n, Value value, Value value2) {
        return "incompatible types at stack item " + n + ": " + value + " and " + value2;
    }

    private void endControlFlow(int insnIndex) throws AnalyzerException {
        if (this.hasNextJvmInsnOrFrame(insnIndex) && this.getFrames()[insnIndex + 1] == null) {
            throw new AnalyzerException(null, CheckFrameAnalyzer.stringConcat$9(insnIndex + 1));
        }
    }

    private static /* synthetic */ String stringConcat$9(int n) {
        return "Expected stack map frame at instruction " + n;
    }

    private boolean hasNextJvmInsnOrFrame(int insnIndex) {
        for (AbstractInsnNode insn = this.insnList.get(insnIndex).getNext(); insn != null; insn = insn.getNext()) {
            if (!CheckFrameAnalyzer.isJvmInsnNode(insn) && !(insn instanceof FrameNode)) continue;
            return true;
        }
        return false;
    }

    private static boolean isJvmInsnNode(AbstractInsnNode insnNode) {
        return insnNode.getOpcode() >= 0;
    }
}

