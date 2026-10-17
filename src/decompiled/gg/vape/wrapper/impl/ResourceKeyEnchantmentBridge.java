/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MResourceKeyEnchantmentBridge;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ResourceKey;

public class ResourceKeyEnchantmentBridge
extends Wrapper {
    public static ResourceKey enchantment() {
        return new ResourceKey(MResourceKeyEnchantmentBridge.getEnchantment(ResourceKeyEnchantmentBridge.vapeInstance.getMappingsMapperCompat().enchantmentRegistryKey));
    }

    public ResourceKeyEnchantmentBridge(Object handle) {
        super(handle);
    }
}

