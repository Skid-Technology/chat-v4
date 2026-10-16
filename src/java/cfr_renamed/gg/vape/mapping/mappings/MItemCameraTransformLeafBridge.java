/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MItemCameraTransformLeafBridge
extends Mapping {
    private final static String KEY_FIELD_NAME = "key";
    private final MappingField keyField = this.J("key", true, MappedClasses.qC);

    public Object getKey(Object transform) {
        return this.keyField.getObject(transform);
    }

    public MItemCameraTransformLeafBridge() {
        super(MappedClasses.zo);
    }
}

