/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.ItemStack;

public class EntityItem
extends Entity {
    public ItemStack getItemStack() {
        return new ItemStack(EntityItem.vapeInstance.getMappings().entityItem.getItem(this.I));
    }

    public EntityItem(Object entityItemHandle) {
        super(entityItemHandle);
    }
}

