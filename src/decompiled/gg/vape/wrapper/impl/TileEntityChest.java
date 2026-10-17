/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.TileEntity;

public class TileEntityChest
extends TileEntity {
    public float getLidOpenness() {
        if (ForgeVersion.MC_1_17.d()) {
            return TileEntityChest.vapeInstance.getMappingsMapperCompat().chestTileEntity.getOpenness(this.getObject(), 0.0f);
        }
        return TileEntityChest.vapeInstance.getMappingsMapperCompat().chestTileEntity.getStoredOpenness(this.getObject());
    }

    public TileEntityChest(Object wrappedObject) {
        super(wrappedObject);
    }

    public int getOpenCount() {
        return TileEntityChest.vapeInstance.getMappingsMapperCompat().chestTileEntity.getOpenCount(this.getObject());
    }
}

