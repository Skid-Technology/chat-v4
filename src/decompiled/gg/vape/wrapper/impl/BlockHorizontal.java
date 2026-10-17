/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MBlockHorizontal;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.BlockProperty;

public class BlockHorizontal
extends Block {
    public BlockHorizontal(Object wrappedObject) {
        super(wrappedObject);
    }

    public static BlockProperty facing() {
        return new BlockProperty(MBlockHorizontal.getFacing(BlockHorizontal.vapeInstance.getMappingsMapperCompat().a));
    }
}

