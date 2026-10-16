package gg.vape.inventory;

public interface InventoryClickOptionsStage extends InventoryClickSlotStage {
   InventoryClickBuildStage swapWithFirstHotbarSlot();

   InventoryClickBuildStage throwItem(boolean var1);

   InventoryClickBuildStage pickupAll(boolean var1);

   InventoryClickBuildStage swapWithHotbarSlot(int var1);

   InventoryClickBuildStage quickMove();

   InventoryClickBuildStage pickup();

   InventoryClickBuildStage quickCraft(int var1);

   InventoryClickBuildStage cloneStack();
}
