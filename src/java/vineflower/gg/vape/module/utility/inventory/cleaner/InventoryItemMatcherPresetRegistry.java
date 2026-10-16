package gg.vape.module.utility.inventory.cleaner;

import gg.vape.wrapper.impl.ItemStack;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public class InventoryItemMatcherPresetRegistry {
   private static final Map<String, InventoryItemMatcherPreset> presetsByName = new LinkedHashMap<>();
   public static final InventoryItemMatcherPreset NO_RULE;

   @Nullable
   public static InventoryItemMatcherPreset getByName(String name) {
      return presetsByName.get(name);
   }

   public static List<InventoryFilterPresetData> findMatchingPresets(InventoryFilterRule rule) {
      ArrayList<InventoryFilterPresetData> matchingPresets = new ArrayList<>();
      InventoryItemMatcher selectedMatcher = rule.getItemSelection().getMatcher();
      ItemStack selectedStack = rule.getItemSelection().getItemStack();

      for (InventoryItemMatcherPreset preset : presetsByName.values()) {
         if (preset.getMatchers().isEmpty()
            || (selectedMatcher == null || preset.getMatchers().contains(selectedMatcher))
               && (selectedStack == null || !preset.getMatchers().stream().noneMatch(matcher -> matcherAcceptsStack(selectedStack, matcher)))) {
            matchingPresets.add(preset);
         }
      }

      return matchingPresets;
   }

   private static void register(InventoryItemMatcherPreset preset) {
      presetsByName.put(preset.getName(), preset);
   }

   private static boolean matcherAcceptsStack(ItemStack itemStack, InventoryItemMatcher matcher) {
      return matcher.matches(itemStack, itemStack.getItem());
   }

   static {
      String noRuleName = "No rule";
      NO_RULE = InventoryItemMatcherPreset.builder().name(noRuleName).build();
      register(NO_RULE);
   }
}
