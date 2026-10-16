package gg.vape.shaded.org.objectweb.asm.tree;

import gg.vape.shaded.org.objectweb.asm.Handle;
import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import java.util.Map;

public class InvokeDynamicInsnNode extends AbstractInsnNode {
   public String name;
   public String desc;
   public Handle bsm;
   public Object[] bsmArgs;

   public InvokeDynamicInsnNode(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
      super(186);
      this.name = name;
      this.desc = descriptor;
      this.bsm = bootstrapMethodHandle;
      this.bsmArgs = bootstrapMethodArguments;
   }

   @Override
   public int getType() {
      return 6;
   }

   @Override
   public void accept(MethodVisitor methodVisitor) {
      methodVisitor.visitInvokeDynamicInsn(this.name, this.desc, this.bsm, this.bsmArgs);
      this.acceptAnnotations(methodVisitor);
   }

   @Override
   public AbstractInsnNode clone(Map<LabelNode, LabelNode> clonedLabels) {
      return new InvokeDynamicInsnNode(this.name, this.desc, this.bsm, this.bsmArgs).cloneAnnotations(this);
   }
}
