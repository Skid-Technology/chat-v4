package gg.vape.wrapper.impl;

public class GuiChest extends GuiContainer {
   public String getTitle() {
      return ForgeVersion.MC_1_16_5.d() ? this.F().getFormattedText() : this.getLowerChestInventory().getName();
   }

   public GuiChest(Object chestScreenHandle) {
      super(chestScreenHandle);
   }

   public Inventory getLowerChestInventory() {
      if (ForgeVersion.MC_1_16_5.d()) {
         Object containerScreenHandle = vapeInstance.getMappingsMapperCompat().hK.t(this.I);
         Object inventoryHandle = vapeInstance.getMappingsMapperCompat().guiChest.getLowerChestInventory(containerScreenHandle);
         return new Inventory(inventoryHandle);
      } else {
         return new Inventory(vapeInstance.getMappingsMapperCompat().guiChest.getLowerChestInventory(this.I));
      }
   }
}
