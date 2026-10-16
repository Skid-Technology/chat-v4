/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.module.utility.inventory.cleaner;

import gg.vape.module.utility.inventory.cleaner.InventoryFilterPresetData;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterRule;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcher;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherPreset;
import gg.vape.wrapper.impl.ItemStack;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public class InventoryItemMatcherPresetRegistry {
    private final static Map<String, InventoryItemMatcherPreset> presetsByName;
    final public static InventoryItemMatcherPreset NO_RULE;

    @Nullable
    public static InventoryItemMatcherPreset getByName(String name) {
        return presetsByName.get(name);
    }

    public static List<InventoryFilterPresetData> findMatchingPresets(InventoryFilterRule rule) {
        ArrayList<InventoryFilterPresetData> matchingPresets = new ArrayList<InventoryFilterPresetData>();
        InventoryItemMatcher selectedMatcher = rule.getItemSelection().getMatcher();
        ItemStack selectedStack = rule.getItemSelection().getItemStack();
        for (InventoryItemMatcherPreset preset : presetsByName.values()) {
            if (!preset.getMatchers().isEmpty() && (selectedMatcher != null && !preset.getMatchers().contains(selectedMatcher) || selectedStack != null && preset.getMatchers().stream().noneMatch(matcher -> InventoryItemMatcherPresetRegistry.matcherAcceptsStack(selectedStack, matcher)))) continue;
            matchingPresets.add(preset);
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
        presetsByName = new LinkedHashMap<String, InventoryItemMatcherPreset>();
        NO_RULE = InventoryItemMatcherPreset.builder().name(noRuleName).build();
        InventoryItemMatcherPresetRegistry.register(NO_RULE);
    }
}

