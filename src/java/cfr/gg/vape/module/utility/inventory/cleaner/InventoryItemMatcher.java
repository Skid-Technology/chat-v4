/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.module.utility.inventory.cleaner;

import gg.vape.module.utility.inventory.cleaner.DescribedOption;
import gg.vape.module.utility.inventory.cleaner.InventoryItemCategory;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatchContext;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherBuilder;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherGroup;
import gg.vape.unmap.INamed;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import java.util.Comparator;
import org.jetbrains.annotations.Nullable;

public interface InventoryItemMatcher
extends INamed,
DescribedOption {
    default public boolean matches(ItemStack itemStack) {
        return this.matches(itemStack, itemStack.getItem());
    }

    default public InventoryItemCategory getCategory() {
        return null;
    }

    @Nullable
    public String getIconName();

    @Nullable
    public Comparator<InventoryItemMatchContext> getComparator();

    public static InventoryItemMatcherBuilder builder() {
        return new InventoryItemMatcherBuilder();
    }

    public String getId();

    public InventoryItemMatcherGroup getGroup();

    public boolean matches(ItemStack var1, Item var2);

    default public void setCategory(InventoryItemCategory category) {
    }
}

