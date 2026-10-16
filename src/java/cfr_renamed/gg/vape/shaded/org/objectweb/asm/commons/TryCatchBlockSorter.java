/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.commons;

import gg.vape.shaded.org.objectweb.asm.MethodVisitor;
import gg.vape.shaded.org.objectweb.asm.tree.MethodNode;
import gg.vape.shaded.org.objectweb.asm.tree.TryCatchBlockNode;
import java.util.Collections;
import java.util.Comparator;

public class TryCatchBlockSorter
extends MethodNode {
    public TryCatchBlockSorter(MethodVisitor methodVisitor, int access, String name, String descriptor, String signature, String[] exceptions) {
        this(589824, methodVisitor, access, name, descriptor, signature, exceptions);
        if (this.getClass() != TryCatchBlockSorter.class) {
            throw new IllegalStateException();
        }
    }

    protected TryCatchBlockSorter(int api, MethodVisitor methodVisitor, int access, String name, String descriptor, String signature, String[] exceptions) {
        super(api, access, name, descriptor, signature, exceptions);
        this.mv = methodVisitor;
    }

    public void visitEnd() {
        Collections.sort(this.tryCatchBlocks, new Comparator<TryCatchBlockNode>(){

            @Override
            public int compare(TryCatchBlockNode tryCatchBlockNode1, TryCatchBlockNode tryCatchBlockNode2) {
                return this.blockLength(tryCatchBlockNode1) - this.blockLength(tryCatchBlockNode2);
            }

            private int blockLength(TryCatchBlockNode tryCatchBlockNode) {
                int startIndex = TryCatchBlockSorter.this.instructions.indexOf(tryCatchBlockNode.start);
                int endIndex = TryCatchBlockSorter.this.instructions.indexOf(tryCatchBlockNode.end);
                return endIndex - startIndex;
            }

            @Override
            public int compare(Object object, Object object2) {
                return this.compare((TryCatchBlockNode)object, (TryCatchBlockNode)object2);
            }
        });
        for (int i = 0; i < this.tryCatchBlocks.size(); ++i) {
            ((TryCatchBlockNode)this.tryCatchBlocks.get(i)).updateIndex(i);
        }
        if (this.mv != null) {
            this.accept(this.mv);
        }
    }
}

