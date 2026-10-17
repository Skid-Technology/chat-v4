package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import java.util.ArrayList;
import java.util.List;

public class ItemAttributeModifiersComponent extends Wrapper {
   public List<Object> getRawModifiers() {
      return vapeInstance.getMappingsMapperCompat().r.getModifiers(this.I);
   }

   public List<ItemAttributeModifiersComponent$Entry> getEntries() {
      ArrayList<ItemAttributeModifiersComponent$Entry> entries = new ArrayList<>();

      for (Object modifier : this.getRawModifiers()) {
         entries.add(new ItemAttributeModifiersComponent$Entry(modifier));
      }

      return entries;
   }

   public ItemAttributeModifiersComponent(Object wrappedObject) {
      super(wrappedObject);
   }
}
