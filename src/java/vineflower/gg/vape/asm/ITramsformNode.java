package gg.vape.asm;

import gg.vape.shaded.org.objectweb.asm.tree.ClassNode;
import gg.vape.shaded.org.objectweb.asm.tree.InsnList;
import gg.vape.shaded.org.objectweb.asm.tree.MethodNode;

public interface ITramsformNode {
   String getOwnerInternalName();

   InsnList getLoadInstructions();

   boolean hasOwner();

   String getDescriptor();

   ITramsformNode setOwner(Class var1);

   InsnList getStoreInstructions();

   void prepare(ClassNode var1, MethodNode var2);
}
