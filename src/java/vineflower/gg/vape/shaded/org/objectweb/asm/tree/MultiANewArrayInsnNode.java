package gg.vape.shaded.org.objectweb.asm.tree;

import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import java.util.Map;

public class MultiANewArrayInsnNode extends AbstractInsnNode {
   public String desc;
   public int dims;

   public MultiANewArrayInsnNode(String descriptor, int numDimensions) {
      super(197);
      this.desc = descriptor;
      this.dims = numDimensions;
   }

   @Override
   public int getType() {
      return 13;
   }

   @Override
   public void accept(MethodVisitor methodVisitor) {
      methodVisitor.visitMultiANewArrayInsn(this.desc, this.dims);
      this.acceptAnnotations(methodVisitor);
   }

   @Override
   public AbstractInsnNode clone(Map<LabelNode, LabelNode> clonedLabels) {
      return new MultiANewArrayInsnNode(this.desc, this.dims).cloneAnnotations(this);
   }
}
