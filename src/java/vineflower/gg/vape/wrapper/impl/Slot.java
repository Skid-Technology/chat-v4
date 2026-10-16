package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MSlot;
import gg.vape.wrapper.Wrapper;

public class Slot extends Wrapper {
   public Slot(Object object) {
      super(object);
   }

   public int getSlotIndex() {
      return MSlot.getSlotIndex(vapeInstance.getMappingsMapperCompat().slot, this.getObject());
   }

   public Inventory getInventory() {
      return new Inventory(MSlot.getInventoryOrContainer(vapeInstance.getMappingsMapperCompat().slot, this.getObject()));
   }

   public int getSlotNumber() {
      return MSlot.getSlotNumber(vapeInstance.getMappingsMapperCompat().slot, this.getObject());
   }

   public ItemStack getStack() {
      return this.isNull() ? new ItemStack(null) : new ItemStack(vapeInstance.getMappingsMapperCompat().slot.getStack(this.getObject()));
   }

   public boolean hasStack() {
      return ForgeVersion.MC_1_21_11.d() ? !this.getStack().isNull() : vapeInstance.getMappingsMapperCompat().slot.getStack(this.getObject()) != null;
   }
}
