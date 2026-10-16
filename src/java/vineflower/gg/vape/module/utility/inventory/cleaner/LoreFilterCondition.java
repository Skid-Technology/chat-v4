package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonObject;
import gg.vape.wrapper.impl.ItemStack;
import java.util.List;

public class LoreFilterCondition extends AbstractTextFilterCondition<LoreFilterCondition> {
   @Override
   public boolean matches(ItemStack itemStack) {
      if (itemStack.isNull()) {
         return false;
      } else {
         for (String loreLine : itemStack.z()) {
            if (this.getMatchMode().matchesAny(loreLine, this.getTexts())) {
               return true;
            }
         }

         return false;
      }
   }

   public LoreFilterCondition() {
   }

   @Override
   public InventoryFilterConditionType getType() {
      return InventoryFilterConditionType.LORE;
   }

   public LoreFilterCondition(JsonObject jsonObject) {
      super(jsonObject);
   }

   public LoreFilterCondition(List<String> texts, TextMatchMode matchMode) {
      super(texts, matchMode);
   }

   public LoreFilterCondition copy() {
      return new LoreFilterCondition(this.getTexts(), this.getMatchMode());
   }
}
