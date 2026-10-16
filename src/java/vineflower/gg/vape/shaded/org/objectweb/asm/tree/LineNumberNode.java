package gg.vape.shaded.org.objectweb.asm.tree;

import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import java.util.Map;

public class LineNumberNode extends AbstractInsnNode {
   public int line;
   public LabelNode start;

   public LineNumberNode(int line, LabelNode start) {
      super(-1);
      this.line = line;
      this.start = start;
   }

   @Override
   public int getType() {
      return 15;
   }

   @Override
   public void accept(MethodVisitor methodVisitor) {
      methodVisitor.visitLineNumber(this.line, this.start.getLabel());
   }

   @Override
   public AbstractInsnNode clone(Map<LabelNode, LabelNode> clonedLabels) {
      return new LineNumberNode(this.line, clone(this.start, clonedLabels));
   }
}
