package gg.vape.module.utility.inventory.cleaner;

import gg.vape.wrapper.impl.ItemStack;
import java.util.List;

public abstract class AbstractInventoryFilterPreset implements InventoryFilterPresetData {
   protected String name;

   public AbstractInventoryFilterPreset(String name) {
      this.name = name;
   }

   @Override
   public boolean matches(ItemStack itemStack) {
      List<InventoryFilterConditionGroup> conditionGroups = this.getConditionGroups();
      if (conditionGroups.isEmpty()) {
         return true;
      } else {
         for (InventoryFilterConditionGroup conditionGroup : conditionGroups) {
            if (conditionGroup.matches(itemStack)) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public String getName() {
      return this.name;
   }
}
