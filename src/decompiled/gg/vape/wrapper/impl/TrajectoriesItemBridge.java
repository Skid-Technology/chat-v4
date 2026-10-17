/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;

public class TrajectoriesItemBridge
extends Item {
    public static boolean isCharged(ItemStack itemStack) {
        return TrajectoriesItemBridge.vapeInstance.getMappingsMapperCompat().RX.isCharged(itemStack.getObject());
    }

    public TrajectoriesItemBridge(Object handle) {
        super(handle);
    }
}

