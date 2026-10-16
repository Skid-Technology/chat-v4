package gg.vape.shaded.org.objectweb.asm.tree;

import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import java.util.Map;

public class IntInsnNode extends AbstractInsnNode {
   public int operand;

   public IntInsnNode(int opcode, int operand) {
      super(opcode);
      this.operand = operand;
   }

   public void setOpcode(int opcode) {
      this.opcode = opcode;
   }

   @Override
   public int getType() {
      return 1;
   }

   @Override
   public void accept(MethodVisitor methodVisitor) {
      methodVisitor.visitIntInsn(this.opcode, this.operand);
      this.acceptAnnotations(methodVisitor);
   }

   @Override
   public AbstractInsnNode clone(Map<LabelNode, LabelNode> clonedLabels) {
      return new IntInsnNode(this.opcode, this.operand).cloneAnnotations(this);
   }
}
