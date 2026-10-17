/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.UnmodifiableView
 */
package gg.vape.module.utility.inventory.cleaner;

import gg.vape.module.utility.inventory.cleaner.InventoryFilterConditionGroup;
import gg.vape.unmap.INamed;
import gg.vape.wrapper.impl.ItemStack;
import java.util.List;
import org.jetbrains.annotations.UnmodifiableView;

public interface InventoryFilterPresetData
extends INamed {
    public @UnmodifiableView List<InventoryFilterConditionGroup> getConditionGroups();

    public boolean matches(ItemStack var1);

    @Override
    public String getName();
}

