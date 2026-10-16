package gg.vape.module.utility.inventory.cleaner;

public class StackSizeInventoryItemCategoryBuilder extends InventoryItemCategoryBuilder<StackSizeInventoryItemCategoryBuilder> {
   private ComparisonOperator comparisonOperator;
   private int stackSize;

   public StackSizeInventoryItemCategoryBuilder withStackSize(int size) {
      this.stackSize = size;
      return this;
   }

   public StackSizeInventoryItemCategory build() {
      return new StackSizeInventoryItemCategory(this);
   }

   public StackSizeInventoryItemCategoryBuilder withOperator(ComparisonOperator operator) {
      this.comparisonOperator = operator;
      return this;
   }

   public ComparisonOperator getOperator() {
      return this.comparisonOperator;
   }

   public int getStackSize() {
      return this.stackSize;
   }
}
