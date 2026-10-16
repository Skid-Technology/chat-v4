/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ResourceLocation;

public class SoundEvent
extends Wrapper {
    public SoundEvent(Object object) {
        super(object);
    }

    public ResourceLocation V() {
        return new ResourceLocation(SoundEvent.vapeInstance.getMappingsMapperCompat().Cb.getName(this.I));
    }
}

