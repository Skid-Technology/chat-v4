package gg.vape.shaded.org.objectweb.asm.tree.analysis;

import gg.vape.shaded.org.objectweb.asm.tree.AbstractInsnNode;
import java.util.Set;

public class SourceValue implements Value {
   public final int size;
   public final Set<AbstractInsnNode> insns;

   public SourceValue(int size) {
      this(size, new SmallSet<>());
   }

   public SourceValue(int size, AbstractInsnNode insnNode) {
      this.size = size;
      this.insns = new SmallSet<>(insnNode);
   }

   public SourceValue(int size, Set<AbstractInsnNode> insnSet) {
      this.size = size;
      this.insns = insnSet;
   }

   @Override
   public int getSize() {
      return this.size;
   }

   @Override
   public boolean equals(Object value) {
      if (!(value instanceof SourceValue)) {
         return false;
      } else {
         SourceValue sourceValue = (SourceValue)value;
         return this.size == sourceValue.size && this.insns.equals(sourceValue.insns);
      }
   }

   @Override
   public int hashCode() {
      return this.insns.hashCode();
   }
}
