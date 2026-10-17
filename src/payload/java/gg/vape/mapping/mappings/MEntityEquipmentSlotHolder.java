package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MEntityEquipmentSlotHolder extends Mapping {
   private static final String SLOT_FIELD_NAME = "slot";
   private final MappingField slotField = this.J("slot", true, MappedClasses.FY);

   public static Object getSlot(MEntityEquipmentSlotHolder mapping, Object holder) {
      return mapping.readSlot(holder);
   }

   public MEntityEquipmentSlotHolder() {
      super(MappedClasses.YW);
   }

   private Object readSlot(Object holder) {
      return this.slotField.getObject(holder);
   }
}
