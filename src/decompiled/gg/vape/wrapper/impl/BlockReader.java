/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.BlockStatePredicate;

public class BlockReader
extends Wrapper {
    public BlockReader(Object object) {
        super(object);
    }

    public BlockStatePredicate f(BlockPos blockPos) {
        return new BlockStatePredicate(BlockReader.vapeInstance.getMappingsMapperCompat().y.getBlockState(this.I, blockPos.getObject()));
    }
}

