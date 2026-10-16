/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MSoundEventRegistryName;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ResourceLocation;

public class SoundEventRegistryName
extends Wrapper {
    public ResourceLocation getRegistryName() {
        return new ResourceLocation(MSoundEventRegistryName.getName(SoundEventRegistryName.vapeInstance.getMappingsMapperCompat().h9, this.I));
    }

    public SoundEventRegistryName(Object wrappedObject) {
        super(wrappedObject);
    }
}

