package gg.vape.module.utility.inventory.cleaner;

public class MatcherBackedInventoryItemCategoryBuilder extends InventoryItemCategoryBuilder<MatcherBackedInventoryItemCategoryBuilder> {
   public MatcherBackedInventoryItemCategory build() {
      return new MatcherBackedInventoryItemCategory(this);
   }
}
