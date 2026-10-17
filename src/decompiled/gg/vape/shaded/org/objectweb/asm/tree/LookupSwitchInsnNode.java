/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.tree;

import gg.vape.shaded.org.objectweb.asm.Label;
import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import gg.vape.shaded.org.objectweb.asm.tree.AbstractInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.LabelNode;
import gg.vape.shaded.org.objectweb.asm.tree.Util;
import java.util.List;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class LookupSwitchInsnNode
extends AbstractInsnNode {
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
        int n = keysArray.length;
        for (int i = 0; i < n; ++i) {
            keysArray[i] = this.keys.get(i);
        }
        Label[] labelsArray = new Label[this.labels.size()];
        int n2 = labelsArray.length;
        for (int i = 0; i < n2; ++i) {
            labelsArray[i] = this.labels.get(i).getLabel();
        }
        methodVisitor.visitLookupSwitchInsn(this.dflt.getLabel(), keysArray, labelsArray);
        this.acceptAnnotations(methodVisitor);
    }

    @Override
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> clonedLabels) {
        LookupSwitchInsnNode clone = new LookupSwitchInsnNode(LookupSwitchInsnNode.clone(this.dflt, clonedLabels), null, LookupSwitchInsnNode.clone(this.labels, clonedLabels));
        clone.keys.addAll(this.keys);
        return clone.cloneAnnotations(this);
    }
}

