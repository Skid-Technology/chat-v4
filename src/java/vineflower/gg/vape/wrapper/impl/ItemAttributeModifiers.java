package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import java.util.Collection;

public class ItemAttributeModifiers extends Wrapper {
   public int size() {
      return vapeInstance.getMappingsMapperCompat().itemAttributeModifiers.size(this.I);
   }

   public boolean put(Object key, Object value) {
      return vapeInstance.getMappingsMapperCompat().itemAttributeModifiers.put(this.I, key, value);
   }

   public Collection values() {
      return vapeInstance.getMappingsMapperCompat().itemAttributeModifiers.values(this.I);
   }

   public ItemAttributeModifiers(Object handle) {
      super(handle);
   }
}
