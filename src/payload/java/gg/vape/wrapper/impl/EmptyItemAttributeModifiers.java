package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEmptyItemAttributeModifiers;

public class EmptyItemAttributeModifiers extends ItemAttributeModifiers {
   public static EmptyItemAttributeModifiers create() {
      return new EmptyItemAttributeModifiers(MEmptyItemAttributeModifiers.create(vapeInstance.getMappingsMapperCompat().Rw));
   }

   public EmptyItemAttributeModifiers(Object wrappedObject) {
      super(wrappedObject);
   }
}
