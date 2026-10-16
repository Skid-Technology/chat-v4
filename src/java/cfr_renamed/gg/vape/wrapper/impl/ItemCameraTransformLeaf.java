/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.ItemCameraTransformIntermediate;
import gg.vape.wrapper.impl.ItemCameraTransformSubtypeValue;

public class ItemCameraTransformLeaf
extends ItemCameraTransformIntermediate {
    public ItemCameraTransformSubtypeValue getKey() {
        return new ItemCameraTransformSubtypeValue(ItemCameraTransformLeaf.vapeInstance.getMappingsMapperCompat().var_gg_vape_mapping_mappings_MItemCameraTransformLeafBridge_C.getKey(this.I));
    }

    public ItemCameraTransformLeaf(Object wrappedObject) {
        super(wrappedObject);
    }
}

