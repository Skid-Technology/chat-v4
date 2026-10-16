package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.config.ConfigJsonUtils;
import gg.vape.module.utility.inventory.cleaner.ui.ItemPickerSelection;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractInventoryFilterRule implements InventoryFilterRule {
   @Nullable
   private InventoryFilterPreset inlinePreset;
   @Nullable
   private InventoryItemCategory priorityOverride;
   private final ItemFilterSelection itemSelection;
   @Nullable
   private UUID sharedPresetId;

   @Override
   public ItemFilterSelection getItemSelection() {
      return this.itemSelection;
   }

   @Nullable
   @Override
   public UUID getSharedPresetId() {
      return this.sharedPresetId;
   }

   @Override
   public InventoryItemCategory getDefaultPriority() {
      InventoryItemMatcher matcher = this.getItemSelection().getMatcher();
      if (matcher == null && this.itemSelection.getItemStack() != null) {
         matcher = InventoryItemMatcherRegistry.findBestMatch(this.itemSelection.getItemStack());
      }

      InventoryItemCategory category;
      return matcher != null && (category = matcher.getCategory()) != null ? category : InventoryItemCategoryRegistry.FIRST_AVAILABLE;
   }

   @NotNull
   @Override
   public InventoryItemCategory getPriority() {
      return this.priorityOverride != null ? this.priorityOverride : this.getDefaultPriority();
   }

   @Override
   public void clearPresetReference() {
      this.inlinePreset = null;
      this.sharedPresetId = null;
   }

   @Override
   public void setPreset(@Nullable InventoryFilterPreset preset) {
      this.clearPresetReference();
      if (preset != null) {
         if (preset instanceof SharedInventoryFilterPreset) {
            this.sharedPresetId = preset.getId();
         } else {
            this.inlinePreset = preset;
         }
      }
   }

   @Override
   public void setPriorityOverride(@Nullable InventoryItemCategory priority) {
      this.priorityOverride = priority;
   }

   public AbstractInventoryFilterRule(JsonObject jsonObject) {
      this.itemSelection = new ItemFilterSelection(jsonObject.get("itemFilter"));
      if (jsonObject.has("customRule")) {
         JsonElement jsonElement = jsonObject.get("customRule");
         if (jsonElement.isJsonPrimitive()) {
            this.sharedPresetId = ConfigJsonUtils.getUuid(jsonObject, "customRule");
         } else {
            this.inlinePreset = new InventoryFilterPreset(jsonObject.getAsJsonObject("customRule"));
         }
      }

      if (jsonObject.has("priority")) {
         this.priorityOverride = InventoryItemCategoryRegistry.getById(jsonObject.get("priority").getAsString());
      }
   }

   public AbstractInventoryFilterRule() {
      this.itemSelection = new ItemFilterSelection();
   }

   public JsonObject toJson(boolean embedSharedPreset) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.add("itemFilter", this.itemSelection.toJson());
      InventoryFilterPreset preset = this.inlinePreset;
      if (!embedSharedPreset && preset == null) {
         if (this.sharedPresetId != null) {
            jsonObject.addProperty("customRule", this.sharedPresetId.toString());
         }
      } else {
         if (preset == null) {
            preset = this.resolvePreset();
         }

         if (preset != null) {
            jsonObject.add("customRule", preset.toJson());
         }
      }

      if (this.priorityOverride != null) {
         jsonObject.addProperty("priority", this.priorityOverride.getId());
      }

      return jsonObject;
   }

   @Nullable
   protected InventoryFilterPreset getInlinePreset() {
      return this.inlinePreset;
   }

   @Override
   public void reset() {
      this.itemSelection.setSelection(ItemPickerSelection.empty());
      this.clearPresetReference();
   }
}
