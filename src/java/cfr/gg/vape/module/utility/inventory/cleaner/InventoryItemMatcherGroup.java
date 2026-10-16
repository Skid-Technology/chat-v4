/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.module.utility.inventory.cleaner;

import gg.vape.module.utility.inventory.cleaner.DescribedOption;
import gg.vape.unmap.INamed;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public enum InventoryItemMatcherGroup implements INamed,
DescribedOption
{
    HIDDEN("Hidden"),
    WEAPONS("Weapons", "Picking any type of weapon", "weapons"),
    TOOLS("Tools", "Picking any type of tool or weapon", "tools"),
    FOOD("Food", "Picking any type of food", "food-hover@2x"),
    BLOCKS("Blocks", "Picking any type of block", "blocks-hover@2x"),
    ARMOR("Armor", "Picking any type of Armor", "armor_item");

    public static final List<InventoryItemMatcherGroup> VALUES;
    private final String name;
    @Nullable
    private final String description;
    @Nullable
    private final String iconName;

    private InventoryItemMatcherGroup(String name) {
        this(name, null, null);
    }

    private InventoryItemMatcherGroup(String name, String description, String iconName) {
        this.name = name;
        this.description = description;
        this.iconName = iconName;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Nullable
    public String getIconName() {
        return this.iconName;
    }

    static {
        VALUES = Arrays.asList(InventoryItemMatcherGroup.values());
    }
}

