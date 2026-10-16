/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.Registry;
import gg.vape.wrapper.impl.ResourceKey;

public class RegistryAccess
extends Wrapper {
    public RegistryAccess(Object wrappedObject) {
        super(wrappedObject);
    }

    public Registry lookupOrThrow(ResourceKey resourceKey) {
        return new Registry(RegistryAccess.vapeInstance.getMappingsMapperCompat().qs.lookupOrThrow(this.I, resourceKey.getObject()));
    }
}

