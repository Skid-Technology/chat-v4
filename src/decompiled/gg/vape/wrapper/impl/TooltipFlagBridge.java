/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class TooltipFlagBridge
extends Wrapper {
    public TooltipFlagBridge(Object handle) {
        super(handle);
    }

    public static TooltipFlagBridge searchTab() {
        if (ForgeVersion.MC_1_20_6.d()) {
            Vape.notifyNativeStackTrace();
        }
        return new TooltipFlagBridge(TooltipFlagBridge.vapeInstance.getMappings().creativeTabsSearch.getSearchTab());
    }
}

