package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEquipmentSlotSet;
import gg.vape.wrapper.Wrapper;

public class EquipmentSlotSet extends Wrapper {
   public int d() {
      return MEquipmentSlotSet.e(vapeInstance.getMappingsMapperCompat().Ro, this.I);
   }

   public static EquipmentSlotSet n() {
      return new EquipmentSlotSet(MEquipmentSlotSet.e(vapeInstance.getMappingsMapperCompat().Ro));
   }

   public static EquipmentSlotSet T(int slot) {
      switch (slot) {
         case 0:
            return k();
         case 1:
            return q();
         case 2:
            return o();
         case 3:
            return n();
         case 4:
            return j();
         case 5:
            return U();
         default:
            return null;
      }
   }

   public static EquipmentSlotSet q() {
      return new EquipmentSlotSet(MEquipmentSlotSet.P(vapeInstance.getMappingsMapperCompat().Ro));
   }

   public static EquipmentSlotSet U() {
      return new EquipmentSlotSet(MEquipmentSlotSet.T(vapeInstance.getMappingsMapperCompat().Ro));
   }

   public static EquipmentSlotSet o() {
      return new EquipmentSlotSet(MEquipmentSlotSet.s(vapeInstance.getMappingsMapperCompat().Ro));
   }

   public static EquipmentSlotSet k() {
      return new EquipmentSlotSet(MEquipmentSlotSet.r(vapeInstance.getMappingsMapperCompat().Ro));
   }

   public MappedFieldSingletonWrapper y() {
      return new MappedFieldSingletonWrapper(MEquipmentSlotSet.a(vapeInstance.getMappingsMapperCompat().Ro, this.I));
   }

   public static EquipmentSlotSet j() {
      return new EquipmentSlotSet(MEquipmentSlotSet.t(vapeInstance.getMappingsMapperCompat().Ro));
   }

   public EquipmentSlotSet(Object object) {
      super(object);
   }
}
