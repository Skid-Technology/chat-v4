/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ItemCameraTransformSubtypeValue;

public class ChestTypeHolder
extends Wrapper {
    public static ItemCameraTransformSubtypeValue woodenToolMaterials() {
        return new ItemCameraTransformSubtypeValue(ChestTypeHolder.vapeInstance.getMappingsMapperCompat().L.getWoodenToolMaterials());
    }

    public static ItemCameraTransformSubtypeValue goldToolMaterials() {
        return new ItemCameraTransformSubtypeValue(ChestTypeHolder.vapeInstance.getMappingsMapperCompat().L.getGoldToolMaterials());
    }

    public ChestTypeHolder(Object wrappedObject) {
        super(wrappedObject);
    }
}

