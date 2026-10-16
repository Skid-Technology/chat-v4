/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.asm;

import gg.vape.shaded.org.objectweb.asm.tree.ClassNode;
import gg.vape.shaded.org.objectweb.asm.tree.InsnList;
import gg.vape.shaded.org.objectweb.asm.tree.MethodNode;

public interface ITramsformNode {
    public String getOwnerInternalName();

    public InsnList getLoadInstructions();

    public boolean hasOwner();

    public String getDescriptor();

    public ITramsformNode setOwner(Class var1);

    public InsnList getStoreInstructions();

    public void prepare(ClassNode var1, MethodNode var2);
}

