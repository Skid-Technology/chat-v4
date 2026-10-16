package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonObject;
import gg.vape.wrapper.impl.ItemStack;
import java.util.List;

public class ItemNameFilterCondition extends AbstractTextFilterCondition<ItemNameFilterCondition> {
   public ItemNameFilterCondition(JsonObject jsonObject) {
      super(jsonObject);
   }

   public ItemNameFilterCondition() {
   }

   public ItemNameFilterCondition(List<String> texts, TextMatchMode matchMode) {
      super(texts, matchMode);
   }

   @Override
   public boolean matches(ItemStack itemStack) {
      return !itemStack.isNull() && !itemStack.getItem().isNull()
         ? this.getMatchMode().matchesAny(itemStack.getItem().getItemStackDisplayName(itemStack), this.getTexts())
         : false;
   }

   @Override
   public InventoryFilterConditionType getType() {
      return InventoryFilterConditionType.ITEM_NAME;
   }

   public ItemNameFilterCondition copy() {
      return new ItemNameFilterCondition(this.getTexts(), this.getMatchMode());
   }
}
