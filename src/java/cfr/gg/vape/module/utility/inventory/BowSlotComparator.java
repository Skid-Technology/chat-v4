/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.utility.inventory;

import gg.vape.config.ClientSettings;
import gg.vape.module.utility.AutoHotbar;
import gg.vape.wrapper.impl.Slot;
import java.util.Comparator;

public class BowSlotComparator
implements Comparator<Slot> {
    final AutoHotbar autoHotbar;

    @Override
    public int compare(Slot first, Slot second) {
        return this.compareBowScore(first, second);
    }

    public int compareBowScore(Slot first, Slot second) {
        return Double.compare(ClientSettings.getToolDamageScore(first.getStack()), ClientSettings.getToolDamageScore(second.getStack()));
    }

    public BowSlotComparator(AutoHotbar autoHotbar) {
        this.autoHotbar = autoHotbar;
    }
}

