/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.EnchantmentRegistryAccess;

public class EnchantmentHelperBridge
extends Wrapper {
    public EnchantmentHelperBridge(Object handle) {
        super(handle);
    }

    public static EnchantmentRegistryAccess createLookup() {
        return new EnchantmentRegistryAccess(EnchantmentHelperBridge.vapeInstance.getMappingsMapperCompat().vanillaRegistries.createLookup());
    }
}

