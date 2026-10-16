package gg.vape.shaded.org.objectweb.asm.tree;

import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import java.util.Map;

public class IincInsnNode extends AbstractInsnNode {
   public int var;
   public int incr;

   public IincInsnNode(int varIndex, int incr) {
      super(132);
      this.var = varIndex;
      this.incr = incr;
   }

   @Override
   public int getType() {
      return 10;
   }

   @Override
   public void accept(MethodVisitor methodVisitor) {
      methodVisitor.visitIincInsn(this.var, this.incr);
      this.acceptAnnotations(methodVisitor);
   }

   @Override
   public AbstractInsnNode clone(Map<LabelNode, LabelNode> clonedLabels) {
      return new IincInsnNode(this.var, this.incr).cloneAnnotations(this);
   }
}
