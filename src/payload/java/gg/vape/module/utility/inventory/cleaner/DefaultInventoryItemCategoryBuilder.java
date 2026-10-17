package gg.vape.module.utility.inventory.cleaner;

public class DefaultInventoryItemCategoryBuilder extends InventoryItemCategoryBuilder {
   public StackSizeInventoryItemCategoryBuilder stackSize() {
      return new StackSizeInventoryItemCategoryBuilder();
   }

   public MatcherBackedInventoryItemCategoryBuilder matcherBacked() {
      return new MatcherBackedInventoryItemCategoryBuilder();
   }
}
