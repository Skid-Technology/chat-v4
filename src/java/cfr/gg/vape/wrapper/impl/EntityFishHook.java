/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.AxisAlignedBB;

public class EntityFishHook
extends Wrapper {
    public EntityFishHook(Object object) {
        super(object);
    }

    public boolean o() {
        return EntityFishHook.vapeInstance.getMappingsMapperCompat().Cr.isEmpty(this.I);
    }

    public AxisAlignedBB n() {
        return new AxisAlignedBB(EntityFishHook.vapeInstance.getMappingsMapperCompat().Cr.getBoundingBox(this.I));
    }
}

