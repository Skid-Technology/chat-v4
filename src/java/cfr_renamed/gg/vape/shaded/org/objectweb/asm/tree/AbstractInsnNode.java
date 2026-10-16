/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.tree;

import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import gg.vape.shaded.org.objectweb.asm.tree.LabelNode;
import gg.vape.shaded.org.objectweb.asm.tree.TypeAnnotationNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class AbstractInsnNode {
    final public static int INSN = 0;
    final public static int INT_INSN = 1;
    final public static int VAR_INSN = 2;
    final public static int TYPE_INSN = 3;
    final public static int FIELD_INSN = 4;
    final public static int METHOD_INSN = 5;
    final public static int INVOKE_DYNAMIC_INSN = 6;
    final public static int JUMP_INSN = 7;
    final public static int LABEL = 8;
    final public static int LDC_INSN = 9;
    final public static int IINC_INSN = 10;
    final public static int TABLESWITCH_INSN = 11;
    final public static int LOOKUPSWITCH_INSN = 12;
    final public static int MULTIANEWARRAY_INSN = 13;
    final public static int FRAME = 14;
    final public static int LINE = 15;
    protected int opcode;
    public List<TypeAnnotationNode> visibleTypeAnnotations;
    public List<TypeAnnotationNode> invisibleTypeAnnotations;
    AbstractInsnNode previousInsn;
    AbstractInsnNode nextInsn;
    int index;

    protected AbstractInsnNode(int opcode) {
        this.opcode = opcode;
        this.index = -1;
    }

    public int getOpcode() {
        return this.opcode;
    }

    public abstract int getType();

    public AbstractInsnNode getPrevious() {
        return this.previousInsn;
    }

    public AbstractInsnNode getNext() {
        return this.nextInsn;
    }

    public abstract void accept(MethodVisitor var1);

    protected final void acceptAnnotations(MethodVisitor methodVisitor) {
        TypeAnnotationNode typeAnnotation;
        int i;
        int n;
        if (this.visibleTypeAnnotations != null) {
            n = this.visibleTypeAnnotations.size();
            for (i = 0; i < n; ++i) {
                typeAnnotation = this.visibleTypeAnnotations.get(i);
                typeAnnotation.accept(methodVisitor.visitInsnAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, true));
            }
        }
        if (this.invisibleTypeAnnotations != null) {
            n = this.invisibleTypeAnnotations.size();
            for (i = 0; i < n; ++i) {
                typeAnnotation = this.invisibleTypeAnnotations.get(i);
                typeAnnotation.accept(methodVisitor.visitInsnAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, false));
            }
        }
    }

    public abstract AbstractInsnNode clone(Map<LabelNode, LabelNode> var1);

    static LabelNode clone(LabelNode label, Map<LabelNode, LabelNode> clonedLabels) {
        return clonedLabels.get(label);
    }

    static LabelNode[] clone(List<LabelNode> labels, Map<LabelNode, LabelNode> clonedLabels) {
        LabelNode[] clones = new LabelNode[labels.size()];
        int n = clones.length;
        for (int i = 0; i < n; ++i) {
            clones[i] = clonedLabels.get(labels.get(i));
        }
        return clones;
    }

    protected final AbstractInsnNode cloneAnnotations(AbstractInsnNode insnNode) {
        TypeAnnotationNode cloneAnnotation;
        TypeAnnotationNode sourceAnnotation;
        int i;
        int n;
        if (insnNode.visibleTypeAnnotations != null) {
            this.visibleTypeAnnotations = new ArrayList<TypeAnnotationNode>();
            n = insnNode.visibleTypeAnnotations.size();
            for (i = 0; i < n; ++i) {
                sourceAnnotation = insnNode.visibleTypeAnnotations.get(i);
                cloneAnnotation = new TypeAnnotationNode(sourceAnnotation.typeRef, sourceAnnotation.typePath, sourceAnnotation.desc);
                sourceAnnotation.accept(cloneAnnotation);
                this.visibleTypeAnnotations.add(cloneAnnotation);
            }
        }
        if (insnNode.invisibleTypeAnnotations != null) {
            this.invisibleTypeAnnotations = new ArrayList<TypeAnnotationNode>();
            n = insnNode.invisibleTypeAnnotations.size();
            for (i = 0; i < n; ++i) {
                sourceAnnotation = insnNode.invisibleTypeAnnotations.get(i);
                cloneAnnotation = new TypeAnnotationNode(sourceAnnotation.typeRef, sourceAnnotation.typePath, sourceAnnotation.desc);
                sourceAnnotation.accept(cloneAnnotation);
                this.invisibleTypeAnnotations.add(cloneAnnotation);
            }
        }
        return this;
    }
}

