/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.EnchantmentRegistry;
import gg.vape.wrapper.impl.RegistryLookup;
import gg.vape.wrapper.impl.ResourceKey;

public class EnchantmentRegistryAccess
extends RegistryLookup {
    public EnchantmentRegistryAccess(Object wrappedObject) {
        super(wrappedObject);
    }

    @Override
    public EnchantmentRegistry lookupOrThrow(ResourceKey resourceKey) {
        return new EnchantmentRegistry(EnchantmentRegistryAccess.vapeInstance.getMappings().CT.lookupOrThrow(this.getObject(), resourceKey.getObject()));
    }
}

