package gg.vape.module.utility.inventory.cleaner;

public class InventoryItemMatcherBuilder extends InventoryItemMatcherBuilderBase<InventoryItemMatcherBuilder> {
   public InventoryItemMatcherBuilderFoundation composite() {
      return CompositeInventoryItemMatcher.builderFrom(this);
   }

   public StringInventoryItemMatcherBuilder stringMatcher() {
      return StringInventoryItemMatcher.builderFrom(this);
   }

   public ClassInventoryItemMatcherBuilder classMatcher() {
      return ClassInventoryItemMatcher.builderFrom(this);
   }
}
