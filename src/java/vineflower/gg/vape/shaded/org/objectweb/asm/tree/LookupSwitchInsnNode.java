package gg.vape.shaded.org.objectweb.asm.tree;

import gg.vape.shaded.org.objectweb.asm.Label;
import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import java.util.List;
import java.util.Map;

public class LookupSwitchInsnNode extends AbstractInsnNode {
   public LabelNode dflt;
   public List<Integer> keys;
   public List<LabelNode> labels;

   public LookupSwitchInsnNode(LabelNode dflt, int[] keys, LabelNode[] labels) {
      super(171);
      this.dflt = dflt;
      this.keys = Util.asArrayList(keys);
      this.labels = Util.asArrayList(labels);
   }

   @Override
   public int getType() {
      return 12;
   }

   @Override
   public void accept(MethodVisitor methodVisitor) {
      int[] keysArray = new int[this.keys.size()];
      int i = 0;

      for (int n = keysArray.length; i < n; i++) {
         keysArray[i] = this.keys.get(i);
      }

      Label[] labelsArray = new Label[this.labels.size()];
      int ix = 0;

      for (int n = labelsArray.length; ix < n; ix++) {
         labelsArray[ix] = this.labels.get(ix).getLabel();
      }

      methodVisitor.visitLookupSwitchInsn(this.dflt.getLabel(), keysArray, labelsArray);
      this.acceptAnnotations(methodVisitor);
   }

   @Override
   public AbstractInsnNode clone(Map<LabelNode, LabelNode> clonedLabels) {
      LookupSwitchInsnNode clone = new LookupSwitchInsnNode(clone(this.dflt, clonedLabels), null, clone(this.labels, clonedLabels));
      clone.keys.addAll(this.keys);
      return clone.cloneAnnotations(this);
   }
}
