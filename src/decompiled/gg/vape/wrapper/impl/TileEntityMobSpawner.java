/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.MobSpawnerBaseLogic;
import gg.vape.wrapper.impl.TileEntity;

public class TileEntityMobSpawner
extends TileEntity {
    public MobSpawnerBaseLogic getSpawnerBaseLogic() {
        return new MobSpawnerBaseLogic(TileEntityMobSpawner.vapeInstance.getMappingsMapperCompat().mobSpawnerTileEntity.getSpawnerBaseLogic(this.I));
    }

    public TileEntityMobSpawner(Object wrappedObject) {
        super(wrappedObject);
    }
}

