/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.tree;

import gg.vape.shaded.org.objectweb.asm.Label;
import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import gg.vape.shaded.org.objectweb.asm.TypePath;
import gg.vape.shaded.org.objectweb.asm.tree.LabelNode;
import gg.vape.shaded.org.objectweb.asm.tree.TypeAnnotationNode;
import gg.vape.shaded.org.objectweb.asm.tree.Util;
import java.util.List;

public class LocalVariableAnnotationNode
extends TypeAnnotationNode {
    public List<LabelNode> start;
    public List<LabelNode> end;
    public List<Integer> index;

    public LocalVariableAnnotationNode(int typeRef, TypePath typePath, LabelNode[] start, LabelNode[] end, int[] index, String descriptor) {
        this(589824, typeRef, typePath, start, end, index, descriptor);
    }

    public LocalVariableAnnotationNode(int api, int typeRef, TypePath typePath, LabelNode[] start, LabelNode[] end, int[] index, String descriptor) {
        super(api, typeRef, typePath, descriptor);
        this.start = Util.asArrayList(start);
        this.end = Util.asArrayList(end);
        this.index = Util.asArrayList(index);
    }

    public void accept(MethodVisitor methodVisitor, boolean visible) {
        Label[] startLabels = new Label[this.start.size()];
        Label[] endLabels = new Label[this.end.size()];
        int[] indices = new int[this.index.size()];
        int n = startLabels.length;
        for (int i = 0; i < n; ++i) {
            startLabels[i] = this.start.get(i).getLabel();
            endLabels[i] = this.end.get(i).getLabel();
            indices[i] = this.index.get(i);
        }
        this.accept(methodVisitor.visitLocalVariableAnnotation(this.typeRef, this.typePath, startLabels, endLabels, indices, this.desc, visible));
    }
}

