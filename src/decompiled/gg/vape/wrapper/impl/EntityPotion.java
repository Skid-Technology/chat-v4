/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.EntityEnderPearl;
import gg.vape.wrapper.impl.ItemStack;

public class EntityPotion
extends EntityEnderPearl {
    public EntityPotion(Object potionEntityHandle) {
        super(potionEntityHandle);
    }

    public ItemStack getPotion() {
        Object itemHandle = EntityPotion.vapeInstance.getMappingsMapperCompat().entityPotion.getPotionItem(this.getObject());
        return new ItemStack(itemHandle);
    }
}

