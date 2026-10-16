package gg.vape.shaded.org.objectweb.asm.tree;

import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import java.util.Map;

public class LdcInsnNode extends AbstractInsnNode {
   public Object cst;

   public LdcInsnNode(Object value) {
      super(18);
      this.cst = value;
   }

   @Override
   public int getType() {
      return 9;
   }

   @Override
   public void accept(MethodVisitor methodVisitor) {
      methodVisitor.visitLdcInsn(this.cst);
      this.acceptAnnotations(methodVisitor);
   }

   @Override
   public AbstractInsnNode clone(Map<LabelNode, LabelNode> clonedLabels) {
      return new LdcInsnNode(this.cst).cloneAnnotations(this);
   }
}
