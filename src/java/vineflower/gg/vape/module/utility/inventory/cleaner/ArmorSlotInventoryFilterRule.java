package gg.vape.module.utility.inventory.cleaner;

public class ArmorSlotInventoryFilterRule extends SlotInventoryFilterRule {
   @Override
   public int getContainerSlot() {
      return 5 + super.getSlot();
   }

   public ArmorSlotInventoryFilterRule(int armorSlot) {
      super(armorSlot);
   }
}
