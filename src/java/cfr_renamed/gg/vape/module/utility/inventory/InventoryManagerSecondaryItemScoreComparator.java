/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.utility.inventory;

import gg.vape.config.ClientSettings;
import gg.vape.module.utility.AutoHotbar;
import gg.vape.wrapper.impl.Slot;
import java.util.Comparator;

public class InventoryManagerSecondaryItemScoreComparator
implements Comparator<Slot> {
    final AutoHotbar autoHotbar;

    @Override
    public int compare(Slot first, Slot second) {
        return this.compareSecondaryScore(first, second);
    }

    public InventoryManagerSecondaryItemScoreComparator(AutoHotbar autoHotbar) {
        this.autoHotbar = autoHotbar;
    }

    public int compareSecondaryScore(Slot first, Slot second) {
        return Double.compare(ClientSettings.getHiddenItemScore(first.getStack()), ClientSettings.getHiddenItemScore(second.getStack()));
    }

    @Override
    public int compare(Object object, Object object2) {
        return this.compare((Slot)object, (Slot)object2);
    }
}

