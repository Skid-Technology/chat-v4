package gg.vape.shaded.org.objectweb.asm.tree;

import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import java.util.Map;

public class JumpInsnNode extends AbstractInsnNode {
   public LabelNode label;

   public JumpInsnNode(int opcode, LabelNode label) {
      super(opcode);
      this.label = label;
   }

   public void setOpcode(int opcode) {
      this.opcode = opcode;
   }

   @Override
   public int getType() {
      return 7;
   }

   @Override
   public void accept(MethodVisitor methodVisitor) {
      methodVisitor.visitJumpInsn(this.opcode, this.label.getLabel());
      this.acceptAnnotations(methodVisitor);
   }

   @Override
   public AbstractInsnNode clone(Map<LabelNode, LabelNode> clonedLabels) {
      return new JumpInsnNode(this.opcode, clone(this.label, clonedLabels)).cloneAnnotations(this);
   }
}
