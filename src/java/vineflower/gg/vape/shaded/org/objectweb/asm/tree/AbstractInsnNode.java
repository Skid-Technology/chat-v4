package gg.vape.shaded.org.objectweb.asm.tree;

import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractInsnNode {
   public static final int INSN = 0;
   public static final int INT_INSN = 1;
   public static final int VAR_INSN = 2;
   public static final int TYPE_INSN = 3;
   public static final int FIELD_INSN = 4;
   public static final int METHOD_INSN = 5;
   public static final int INVOKE_DYNAMIC_INSN = 6;
   public static final int JUMP_INSN = 7;
   public static final int LABEL = 8;
   public static final int LDC_INSN = 9;
   public static final int IINC_INSN = 10;
   public static final int TABLESWITCH_INSN = 11;
   public static final int LOOKUPSWITCH_INSN = 12;
   public static final int MULTIANEWARRAY_INSN = 13;
   public static final int FRAME = 14;
   public static final int LINE = 15;
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
      if (this.visibleTypeAnnotations != null) {
         int i = 0;

         for (int n = this.visibleTypeAnnotations.size(); i < n; i++) {
            TypeAnnotationNode typeAnnotation = this.visibleTypeAnnotations.get(i);
            typeAnnotation.accept(methodVisitor.visitInsnAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, true));
         }
      }

      if (this.invisibleTypeAnnotations != null) {
         int i = 0;

         for (int n = this.invisibleTypeAnnotations.size(); i < n; i++) {
            TypeAnnotationNode typeAnnotation = this.invisibleTypeAnnotations.get(i);
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
      int i = 0;

      for (int n = clones.length; i < n; i++) {
         clones[i] = clonedLabels.get(labels.get(i));
      }

      return clones;
   }

   protected final AbstractInsnNode cloneAnnotations(AbstractInsnNode insnNode) {
      if (insnNode.visibleTypeAnnotations != null) {
         this.visibleTypeAnnotations = new ArrayList<>();
         int i = 0;

         for (int n = insnNode.visibleTypeAnnotations.size(); i < n; i++) {
            TypeAnnotationNode sourceAnnotation = insnNode.visibleTypeAnnotations.get(i);
            TypeAnnotationNode cloneAnnotation = new TypeAnnotationNode(sourceAnnotation.typeRef, sourceAnnotation.typePath, sourceAnnotation.desc);
            sourceAnnotation.accept(cloneAnnotation);
            this.visibleTypeAnnotations.add(cloneAnnotation);
         }
      }

      if (insnNode.invisibleTypeAnnotations != null) {
         this.invisibleTypeAnnotations = new ArrayList<>();
         int i = 0;

         for (int n = insnNode.invisibleTypeAnnotations.size(); i < n; i++) {
            TypeAnnotationNode sourceAnnotation = insnNode.invisibleTypeAnnotations.get(i);
            TypeAnnotationNode cloneAnnotation = new TypeAnnotationNode(sourceAnnotation.typeRef, sourceAnnotation.typePath, sourceAnnotation.desc);
            sourceAnnotation.accept(cloneAnnotation);
            this.invisibleTypeAnnotations.add(cloneAnnotation);
         }
      }

      return this;
   }
}
