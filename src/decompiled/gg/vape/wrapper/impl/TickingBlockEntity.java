/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MTickingBlockEntity;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.BlockPos;

public class TickingBlockEntity
extends Wrapper {
    public BlockPos getPos() {
        return new BlockPos(MTickingBlockEntity.getPos(TickingBlockEntity.vapeInstance.getMappingsMapperCompat().tickingBlockEntity, this.I));
    }

    public TickingBlockEntity(Object wrappedObject) {
        super(wrappedObject);
    }
}

