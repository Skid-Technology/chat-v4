package gg.vape.module.utility.inventory.cleaner;

import gg.vape.wrapper.impl.ItemStack;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface InventoryFilterRule {
   void setPreset(@Nullable InventoryFilterPreset var1);

   void clearPresetReference();

   @Nullable
   UUID getSharedPresetId();

   default boolean matches(ItemStack itemStack) {
      InventoryFilterPreset preset = this.resolvePreset();
      return preset == null || preset.matches(itemStack);
   }

   ItemFilterSelection getItemSelection();

   void setPriorityOverride(@Nullable InventoryItemCategory var1);

   @NotNull
   InventoryItemCategory getPriority();

   void reset();

   InventoryItemCategory getDefaultPriority();

   @Nullable
   InventoryFilterPreset resolvePreset();
}
