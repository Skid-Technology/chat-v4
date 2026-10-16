package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonObject;
import gg.vape.Vape;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public class ItemInventoryFilterRule extends AbstractInventoryFilterRule {
   private InventoryFilterAction filterAction = InventoryFilterAction.REMOVE;

   public void setAction(InventoryFilterAction action) {
      this.filterAction = action;
   }

   public ItemInventoryFilterRule() {
   }

   public InventoryFilterAction getAction() {
      return this.filterAction;
   }

   @Override
   public JsonObject toJson(boolean embedSharedPreset) {
      JsonObject jsonObject = super.toJson(embedSharedPreset);
      jsonObject.addProperty("filterAction", this.filterAction.getName());
      return jsonObject;
   }

   @Nullable
   @Override
   public InventoryFilterPreset resolvePreset() {
      UUID sharedPresetId = this.getSharedPresetId();
      return (InventoryFilterPreset)(sharedPresetId != null
         ? Vape.INSTANCE.getInventoryFilterPresetRegistry().getItemRulePresets().getById(sharedPresetId)
         : this.getInlinePreset());
   }

   public ItemInventoryFilterRule(JsonObject jsonObject) {
      super(jsonObject);
      this.filterAction = InventoryFilterAction.fromName(jsonObject.get("filterAction").getAsString());
   }
}
