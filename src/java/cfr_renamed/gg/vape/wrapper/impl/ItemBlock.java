/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.Item;

public class ItemBlock
extends Item {
    public Block C() {
        return new Block(ItemBlock.vapeInstance.getMappingsMapperCompat().hb.getBlock(this.I));
    }

    public ItemBlock(Object object) {
        super(object);
    }
}

