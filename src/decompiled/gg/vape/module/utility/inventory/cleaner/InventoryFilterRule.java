/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.module.utility.inventory.cleaner;

import gg.vape.module.utility.inventory.cleaner.InventoryFilterPreset;
import gg.vape.module.utility.inventory.cleaner.InventoryItemCategory;
import gg.vape.module.utility.inventory.cleaner.ItemFilterSelection;
import gg.vape.wrapper.impl.ItemStack;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface InventoryFilterRule {
    public void setPreset(@Nullable InventoryFilterPreset var1);

    public void clearPresetReference();

    @Nullable
    public UUID getSharedPresetId();

    default public boolean matches(ItemStack itemStack) {
        InventoryFilterPreset preset = this.resolvePreset();
        return preset == null || preset.matches(itemStack);
    }

    public ItemFilterSelection getItemSelection();

    public void setPriorityOverride(@Nullable InventoryItemCategory var1);

    @NotNull
    public InventoryItemCategory getPriority();

    public void reset();

    public InventoryItemCategory getDefaultPriority();

    @Nullable
    public InventoryFilterPreset resolvePreset();
}

