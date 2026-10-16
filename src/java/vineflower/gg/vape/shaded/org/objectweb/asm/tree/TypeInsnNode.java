package gg.vape.shaded.org.objectweb.asm.tree;

import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import java.util.Map;

public class TypeInsnNode extends AbstractInsnNode {
   public String desc;

   public TypeInsnNode(int opcode, String type) {
      super(opcode);
      this.desc = type;
   }

   public void setOpcode(int opcode) {
      this.opcode = opcode;
   }

   @Override
   public int getType() {
      return 3;
   }

   @Override
   public void accept(MethodVisitor methodVisitor) {
      methodVisitor.visitTypeInsn(this.opcode, this.desc);
      this.acceptAnnotations(methodVisitor);
   }

   @Override
   public AbstractInsnNode clone(Map<LabelNode, LabelNode> clonedLabels) {
      return new TypeInsnNode(this.opcode, this.desc).cloneAnnotations(this);
   }
}
