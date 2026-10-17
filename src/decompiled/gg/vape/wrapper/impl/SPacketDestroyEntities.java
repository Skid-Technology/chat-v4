/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.SPacketDestroyEntitiesBase;

public class SPacketDestroyEntities
extends SPacketDestroyEntitiesBase {
    public int[] getEntityIds() {
        return SPacketDestroyEntities.vapeInstance.getMappingsMapperCompat().C7.getEntityIds(this.I);
    }

    public SPacketDestroyEntities(Object handle) {
        super(handle);
    }
}

