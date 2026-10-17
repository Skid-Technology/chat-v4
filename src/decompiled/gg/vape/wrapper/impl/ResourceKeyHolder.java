/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ResourceKey;

public class ResourceKeyHolder
extends Wrapper {
    public static ResourceKey gold() {
        return new ResourceKey(ResourceKeyHolder.vapeInstance.getMappingsMapperCompat().qV.getGold());
    }

    public ResourceKeyHolder(Object wrappedObject) {
        super(wrappedObject);
    }
}

