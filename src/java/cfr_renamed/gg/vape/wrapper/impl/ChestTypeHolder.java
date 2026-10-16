/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ItemCameraTransformSubtypeValue;

public class ChestTypeHolder
extends Wrapper {
    public static ItemCameraTransformSubtypeValue woodenToolMaterials() {
        return new ItemCameraTransformSubtypeValue(ChestTypeHolder.vapeInstance.getMappingsMapperCompat().var_gg_vape_mapping_mappings_MChestTypeHolder_L.getWoodenToolMaterials());
    }

    public static ItemCameraTransformSubtypeValue goldToolMaterials() {
        return new ItemCameraTransformSubtypeValue(ChestTypeHolder.vapeInstance.getMappingsMapperCompat().var_gg_vape_mapping_mappings_MChestTypeHolder_L.getGoldToolMaterials());
    }

    public ChestTypeHolder(Object wrappedObject) {
        super(wrappedObject);
    }
}

