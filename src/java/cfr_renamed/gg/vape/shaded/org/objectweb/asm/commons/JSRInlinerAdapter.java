/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.commons;

import gg.vape.shaded.org.objectweb.asm.Label;
import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import gg.vape.shaded.org.objectweb.asm.Opcodes;
import gg.vape.shaded.org.objectweb.asm.tree.AbstractInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.InsnList;
import gg.vape.shaded.org.objectweb.asm.tree.InsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.JumpInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.LabelNode;
import gg.vape.shaded.org.objectweb.asm.tree.LocalVariableNode;
import gg.vape.shaded.org.objectweb.asm.tree.LookupSwitchInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.MethodNode;
import gg.vape.shaded.org.objectweb.asm.tree.TableSwitchInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.TryCatchBlockNode;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class JSRInlinerAdapter
extends MethodNode
implements Opcodes {
    private final BitSet mainSubroutineInsns = new BitSet();
    private final Map<LabelNode, BitSet> subroutinesInsns = new HashMap<LabelNode, BitSet>();
    final BitSet sharedSubroutineInsns = new BitSet();

    public JSRInlinerAdapter(MethodVisitor methodVisitor, int access, String name, String descriptor, String signature, String[] exceptions) {
        this(589824, methodVisitor, access, name, descriptor, signature, exceptions);
        if (this.getClass() != JSRInlinerAdapter.class) {
            throw new IllegalStateException();
        }
    }

    protected JSRInlinerAdapter(int api, MethodVisitor methodVisitor, int access, String name, String descriptor, String signature, String[] exceptions) {
        super(api, access, name, descriptor, signature, exceptions);
        this.mv = methodVisitor;
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        super.visitJumpInsn(opcode, label);
        LabelNode labelNode = ((JumpInsnNode)this.instructions.getLast()).label;
        if (opcode == 168 && !this.subroutinesInsns.containsKey(labelNode)) {
            this.subroutinesInsns.put(labelNode, new BitSet());
        }
    }

    @Override
    public void visitEnd() {
        if (!this.subroutinesInsns.isEmpty()) {
            this.findSubroutinesInsns();
            this.emitCode();
        }
        if (this.mv != null) {
            this.accept(this.mv);
        }
    }

    private void findSubroutinesInsns() {
        BitSet visitedInsns = new BitSet();
        this.findSubroutineInsns(0, this.mainSubroutineInsns, visitedInsns);
        for (Map.Entry<LabelNode, BitSet> entry : this.subroutinesInsns.entrySet()) {
            LabelNode jsrLabelNode = entry.getKey();
            BitSet subroutineInsns = entry.getValue();
            this.findSubroutineInsns(this.instructions.indexOf(jsrLabelNode), subroutineInsns, visitedInsns);
        }
    }

    private void findSubroutineInsns(int startInsnIndex, BitSet subroutineInsns, BitSet visitedInsns) {
        boolean applicableHandlerFound;
        this.findReachableInsns(startInsnIndex, subroutineInsns, visitedInsns);
        do {
            applicableHandlerFound = false;
            for (TryCatchBlockNode tryCatchBlockNode : this.tryCatchBlocks) {
                int handlerIndex = this.instructions.indexOf(tryCatchBlockNode.handler);
                if (subroutineInsns.get(handlerIndex)) continue;
                int startIndex = this.instructions.indexOf(tryCatchBlockNode.start);
                int endIndex = this.instructions.indexOf(tryCatchBlockNode.end);
                int firstSubroutineInsnAfterTryCatchStart = subroutineInsns.nextSetBit(startIndex);
                if (firstSubroutineInsnAfterTryCatchStart < startIndex || firstSubroutineInsnAfterTryCatchStart >= endIndex) continue;
                this.findReachableInsns(handlerIndex, subroutineInsns, visitedInsns);
                applicableHandlerFound = true;
            }
        } while (applicableHandlerFound);
    }

    private void findReachableInsns(int insnIndex, BitSet subroutineInsns, BitSet visitedInsns) {
        for (int currentInsnIndex = insnIndex; currentInsnIndex < this.instructions.size(); ++currentInsnIndex) {
            if (subroutineInsns.get(currentInsnIndex)) {
                return;
            }
            subroutineInsns.set(currentInsnIndex);
            if (visitedInsns.get(currentInsnIndex)) {
                this.sharedSubroutineInsns.set(currentInsnIndex);
            }
            visitedInsns.set(currentInsnIndex);
            AbstractInsnNode currentInsnNode = this.instructions.get(currentInsnIndex);
            if (currentInsnNode.getType() == 7 && currentInsnNode.getOpcode() != 168) {
                JumpInsnNode jumpInsnNode = (JumpInsnNode)currentInsnNode;
                this.findReachableInsns(this.instructions.indexOf(jumpInsnNode.label), subroutineInsns, visitedInsns);
            } else if (currentInsnNode.getType() == 11) {
                TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode)currentInsnNode;
                this.findReachableInsns(this.instructions.indexOf(tableSwitchInsnNode.dflt), subroutineInsns, visitedInsns);
                for (LabelNode labelNode : tableSwitchInsnNode.labels) {
                    this.findReachableInsns(this.instructions.indexOf(labelNode), subroutineInsns, visitedInsns);
                }
            } else if (currentInsnNode.getType() == 12) {
                LookupSwitchInsnNode lookupSwitchInsnNode = (LookupSwitchInsnNode)currentInsnNode;
                this.findReachableInsns(this.instructions.indexOf(lookupSwitchInsnNode.dflt), subroutineInsns, visitedInsns);
                for (LabelNode labelNode : lookupSwitchInsnNode.labels) {
                    this.findReachableInsns(this.instructions.indexOf(labelNode), subroutineInsns, visitedInsns);
                }
            }
            switch (this.instructions.get(currentInsnIndex).getOpcode()) {
                case 167: 
                case 169: 
                case 170: 
                case 171: 
                case 172: 
                case 173: 
                case 174: 
                case 175: 
                case 176: 
                case 177: 
                case 191: {
                    return;
                }
            }
        }
    }

    private void emitCode() {
        LinkedList<Instantiation> worklist = new LinkedList<Instantiation>();
        worklist.add(new Instantiation(null, this.mainSubroutineInsns));
        InsnList newInstructions = new InsnList();
        ArrayList<TryCatchBlockNode> newTryCatchBlocks = new ArrayList<TryCatchBlockNode>();
        ArrayList<LocalVariableNode> newLocalVariables = new ArrayList<LocalVariableNode>();
        while (!worklist.isEmpty()) {
            Instantiation instantiation = (Instantiation)worklist.removeFirst();
            this.emitInstantiation(instantiation, worklist, newInstructions, newTryCatchBlocks, newLocalVariables);
        }
        this.instructions = newInstructions;
        this.tryCatchBlocks = newTryCatchBlocks;
        this.localVariables = newLocalVariables;
    }

    private void emitInstantiation(Instantiation instantiation, List<Instantiation> worklist, InsnList newInstructions, List<TryCatchBlockNode> newTryCatchBlocks, List<LocalVariableNode> newLocalVariables) {
        LabelNode end;
        LabelNode start;
        LabelNode previousLabelNode = null;
        for (int i = 0; i < this.instructions.size(); ++i) {
            AbstractInsnNode insnNode = this.instructions.get(i);
            if (insnNode.getType() == 8) {
                LabelNode labelNode = (LabelNode)insnNode;
                LabelNode clonedLabelNode = instantiation.getClonedLabel(labelNode);
                if (clonedLabelNode == previousLabelNode) continue;
                newInstructions.add(clonedLabelNode);
                previousLabelNode = clonedLabelNode;
                continue;
            }
            if (instantiation.findOwner(i) != instantiation) continue;
            if (insnNode.getOpcode() == 169) {
                LabelNode retLabel = null;
                Instantiation retLabelOwner = instantiation;
                while (retLabelOwner != null) {
                    if (retLabelOwner.subroutineInsns.get(i)) {
                        retLabel = retLabelOwner.returnLabel;
                    }
                    retLabelOwner = retLabelOwner.parent;
                }
                if (retLabel == null) {
                    throw new IllegalArgumentException(JSRInlinerAdapter.stringConcat$0(i));
                }
                newInstructions.add(new JumpInsnNode(167, retLabel));
                continue;
            }
            if (insnNode.getOpcode() == 168) {
                LabelNode jsrLabelNode = ((JumpInsnNode)insnNode).label;
                BitSet subroutineInsns = this.subroutinesInsns.get(jsrLabelNode);
                Instantiation newInstantiation = new Instantiation(instantiation, subroutineInsns);
                LabelNode clonedJsrLabelNode = newInstantiation.getClonedLabelForJumpInsn(jsrLabelNode);
                newInstructions.add(new InsnNode(1));
                newInstructions.add(new JumpInsnNode(167, clonedJsrLabelNode));
                newInstructions.add(newInstantiation.returnLabel);
                worklist.add(newInstantiation);
                continue;
            }
            newInstructions.add(insnNode.clone(instantiation));
        }
        for (TryCatchBlockNode tryCatchBlockNode : this.tryCatchBlocks) {
            start = instantiation.getClonedLabel(tryCatchBlockNode.start);
            if (start == (end = instantiation.getClonedLabel(tryCatchBlockNode.end))) continue;
            LabelNode handler = instantiation.getClonedLabelForJumpInsn(tryCatchBlockNode.handler);
            if (start == null || end == null || handler == null) {
                throw new AssertionError((Object)"Internal error!");
            }
            newTryCatchBlocks.add(new TryCatchBlockNode(start, end, handler, tryCatchBlockNode.type));
        }
        for (LocalVariableNode localVariableNode : this.localVariables) {
            start = instantiation.getClonedLabel(localVariableNode.start);
            if (start == (end = instantiation.getClonedLabel(localVariableNode.end))) continue;
            newLocalVariables.add(new LocalVariableNode(localVariableNode.name, localVariableNode.desc, localVariableNode.signature, start, end, localVariableNode.index));
        }
    }

    private static String stringConcat$0(int n) {
        return "Instruction #" + n + " is a RET not owned by any subroutine";
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private final class Instantiation
    extends AbstractMap<LabelNode, LabelNode> {
        final Instantiation parent;
        final BitSet subroutineInsns;
        final Map<LabelNode, LabelNode> clonedLabels;
        final LabelNode returnLabel;

        Instantiation(Instantiation parent, BitSet subroutineInsns) {
            Instantiation instantiation = parent;
            while (instantiation != null) {
                if (instantiation.subroutineInsns == subroutineInsns) {
                    throw new IllegalArgumentException(Instantiation.stringConcat$0(subroutineInsns));
                }
                instantiation = instantiation.parent;
            }
            this.parent = parent;
            this.subroutineInsns = subroutineInsns;
            this.returnLabel = parent == null ? null : new LabelNode();
            this.clonedLabels = new HashMap<LabelNode, LabelNode>();
            LabelNode clonedLabelNode = null;
            for (int insnIndex = 0; insnIndex < JSRInlinerAdapter.this.instructions.size(); ++insnIndex) {
                AbstractInsnNode insnNode = JSRInlinerAdapter.this.instructions.get(insnIndex);
                if (insnNode.getType() == 8) {
                    LabelNode labelNode = (LabelNode)insnNode;
                    if (clonedLabelNode == null) {
                        clonedLabelNode = new LabelNode();
                    }
                    this.clonedLabels.put(labelNode, clonedLabelNode);
                    continue;
                }
                if (this.findOwner(insnIndex) != this) continue;
                clonedLabelNode = null;
            }
        }

        private static String stringConcat$0(BitSet bitSet) {
            return "Recursive invocation of " + bitSet;
        }

        Instantiation findOwner(int insnIndex) {
            if (!this.subroutineInsns.get(insnIndex)) {
                return null;
            }
            if (!JSRInlinerAdapter.this.sharedSubroutineInsns.get(insnIndex)) {
                return this;
            }
            Instantiation owner = this;
            Instantiation instantiation = this.parent;
            while (instantiation != null) {
                if (instantiation.subroutineInsns.get(insnIndex)) {
                    owner = instantiation;
                }
                instantiation = instantiation.parent;
            }
            return owner;
        }

        LabelNode getClonedLabelForJumpInsn(LabelNode labelNode) {
            return this.findOwner((int)JSRInlinerAdapter.this.instructions.indexOf((AbstractInsnNode)labelNode)).clonedLabels.get(labelNode);
        }

        LabelNode getClonedLabel(LabelNode labelNode) {
            return this.clonedLabels.get(labelNode);
        }

        @Override
        public Set<Map.Entry<LabelNode, LabelNode>> entrySet() {
            throw new UnsupportedOperationException();
        }

        @Override
        public LabelNode get(Object key) {
            return this.getClonedLabelForJumpInsn((LabelNode)key);
        }

        @Override
        public boolean equals(Object other) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int hashCode() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object get(Object object) {
            return this.get(object);
        }
    }
}

