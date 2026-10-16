/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.tree.analysis;

import gg.vape.shaded.org.objectweb.asm.tree.AbstractInsnNode;
import gg.vape.shaded.org.objectweb.asm.tree.analysis.SmallSet;
import gg.vape.shaded.org.objectweb.asm.tree.analysis.Value;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SourceValue
implements Value {
    final public int size;
    final public Set<AbstractInsnNode> insns;

    public SourceValue(int size) {
        this(size, new SmallSet<AbstractInsnNode>());
    }

    public SourceValue(int size, AbstractInsnNode insnNode) {
        this.size = size;
        this.insns = new SmallSet<AbstractInsnNode>(insnNode);
    }

    public SourceValue(int size, Set<AbstractInsnNode> insnSet) {
        this.size = size;
        this.insns = insnSet;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    public boolean equals(Object value) {
        if (!(value instanceof SourceValue)) {
            return false;
        }
        SourceValue sourceValue = (SourceValue)value;
        return this.size == sourceValue.size && this.insns.equals(sourceValue.insns);
    }

    public int hashCode() {
        return this.insns.hashCode();
    }
}

