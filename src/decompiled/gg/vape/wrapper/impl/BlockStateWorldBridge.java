/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.AbstractBlockState;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.World;

public class BlockStateWorldBridge
extends Wrapper {
    public boolean isTag(Object tag) {
        return BlockStateWorldBridge.vapeInstance.getMappingsMapperCompat().hu.isTag(this.getObject(), tag);
    }

    public float getHeight(World world, BlockPos blockPosition) {
        return BlockStateWorldBridge.vapeInstance.getMappingsMapperCompat().hu.getHeight(this.getObject(), world.getObject(), blockPosition.getObject());
    }

    public BlockStateWorldBridge(Object handle) {
        super(handle);
    }

    public AbstractBlockState getType() {
        return new AbstractBlockState(BlockStateWorldBridge.vapeInstance.getMappingsMapperCompat().hu.getType(this.I));
    }

    public boolean isEmpty() {
        return BlockStateWorldBridge.vapeInstance.getMappingsMapperCompat().hu.isEmpty(this.I);
    }
}

