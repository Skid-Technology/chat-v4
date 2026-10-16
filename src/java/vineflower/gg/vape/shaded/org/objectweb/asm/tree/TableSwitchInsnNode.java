package gg.vape.shaded.org.objectweb.asm.tree;

import gg.vape.shaded.org.objectweb.asm.Label;
import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import java.util.List;
import java.util.Map;

public class TableSwitchInsnNode extends AbstractInsnNode {
   public int min;
   public int max;
   public LabelNode dflt;
   public List<LabelNode> labels;

   public TableSwitchInsnNode(int min, int max, LabelNode dflt, LabelNode... labels) {
      super(170);
      this.min = min;
      this.max = max;
      this.dflt = dflt;
      this.labels = Util.asArrayList(labels);
   }

   @Override
   public int getType() {
      return 11;
   }

   @Override
   public void accept(MethodVisitor methodVisitor) {
      Label[] labelsArray = new Label[this.labels.size()];
      int i = 0;

      for (int n = labelsArray.length; i < n; i++) {
         labelsArray[i] = this.labels.get(i).getLabel();
      }

      methodVisitor.visitTableSwitchInsn(this.min, this.max, this.dflt.getLabel(), labelsArray);
      this.acceptAnnotations(methodVisitor);
   }

   @Override
   public AbstractInsnNode clone(Map<LabelNode, LabelNode> clonedLabels) {
      return new TableSwitchInsnNode(this.min, this.max, clone(this.dflt, clonedLabels), clone(this.labels, clonedLabels)).cloneAnnotations(this);
   }
}
