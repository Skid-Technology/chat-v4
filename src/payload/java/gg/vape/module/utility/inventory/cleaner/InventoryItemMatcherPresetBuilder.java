package gg.vape.module.utility.inventory.cleaner;

import java.util.ArrayList;
import java.util.List;

public class InventoryItemMatcherPresetBuilder {
   private String name;
   private final List<InventoryItemMatcher> matchers;
   private final List<InventoryFilterConditionGroup> conditionGroups = new ArrayList<>();

   public InventoryItemMatcherPreset build() {
      return new InventoryItemMatcherPreset(this.name, this.conditionGroups, this.matchers);
   }

   public InventoryItemMatcherPresetBuilder() {
      this.matchers = new ArrayList<>();
   }

   public InventoryItemMatcherPresetBuilder addMatcher(InventoryItemMatcher matcher) {
      this.matchers.add(matcher);
      return this;
   }

   public InventoryItemMatcherPresetBuilder addConditionGroup(InventoryFilterConditionGroup conditionGroup) {
      this.conditionGroups.add(conditionGroup);
      return this;
   }

   public InventoryItemMatcherPresetBuilder name(String name) {
      this.name = name;
      return this;
   }

   public InventoryItemMatcherPresetBuilder addConditionGroup(InventoryFilterConditionGroupBuilder groupBuilder) {
      this.conditionGroups.add(groupBuilder.build());
      return this;
   }
}
