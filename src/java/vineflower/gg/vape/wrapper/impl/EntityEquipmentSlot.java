package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEntityEquipmentSlot;
import gg.vape.wrapper.Wrapper;

public class EntityEquipmentSlot extends Wrapper {
   public static EntityEquipmentSlot L() {
      return new EntityEquipmentSlot(MEntityEquipmentSlot.Q(vapeInstance.getMappingsMapperCompat().CY));
   }

   public boolean v() {
      return MEntityEquipmentSlot.S(vapeInstance.getMappingsMapperCompat().CY, this.I);
   }

   public EntityEquipmentSlot(Object object) {
      super(object);
   }

   public int W() {
      return MEntityEquipmentSlot.c(vapeInstance.getMappingsMapperCompat().CY, this.I);
   }
}
