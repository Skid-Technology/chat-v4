package gg.vape.module.utility.inventory.cleaner;

public class InventoryFilterRulePresetChange {
   private final InventoryFilterRule rule;
   private final boolean added;
   private final InventoryFilterPreset previousPreset;
   private final InventoryFilterPreset newPreset;

   public InventoryFilterPreset getNewPreset() {
      return this.newPreset;
   }

   public boolean wasAdded() {
      return this.added;
   }

   public InventoryFilterPreset getPreviousPreset() {
      return this.previousPreset;
   }

   public InventoryFilterRulePresetChange(InventoryFilterRule rule, InventoryFilterPreset newPreset, InventoryFilterPreset previousPreset, boolean added) {
      this.rule = rule;
      this.newPreset = newPreset;
      this.previousPreset = previousPreset;
      this.added = added;
   }

   public InventoryFilterRule getRule() {
      return this.rule;
   }
}
