package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MContainer;
import gg.vape.wrapper.Wrapper;
import java.util.ArrayList;
import java.util.List;

public class Container extends Wrapper {
   public Slot getSlot(int slotIndex) {
      return this.isNull() ? new Slot(null) : new Slot(MContainer.getSlot(vapeInstance.getMappings().container, this.I, slotIndex));
   }

   public Container(Object object) {
      super(object);
   }

   public ItemStack getCarried() {
      return new ItemStack(MContainer.getCarried(vapeInstance.getMappings().container, this.I));
   }

   public List<Slot> getInventorySlots() {
      List slotHandles = vapeInstance.getMappings().container.getSlots(this.I);
      ArrayList<Slot> slots = new ArrayList<>();

      for (Object slotHandle : slotHandles) {
         slots.add(new Slot(slotHandle));
      }

      return slots;
   }

   public int getWindowId() {
      return this.isNull() ? 0 : vapeInstance.getMappings().container.getWindowId(this.I);
   }
}
