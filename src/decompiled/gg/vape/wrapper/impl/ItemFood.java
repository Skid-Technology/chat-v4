/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.Item;

public class ItemFood
extends Item {
    public float getSaturation() {
        return ItemFood.vapeInstance.getMappings().itemFood.getSaturation(this.I);
    }

    public ItemFood(Object wrappedObject) {
        super(wrappedObject);
    }

    public int getNutrition() {
        return ItemFood.vapeInstance.getMappings().itemFood.getNutrition(this.I);
    }
}

