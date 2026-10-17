/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MOpenedChestTypeSentinelBridge
extends Mapping {
    private static final String BASIC_FIELD_NAME = "BASIC";
    private final MappingField basicField = this.registerStaticField("BASIC", true, MappedClasses.q1);

    public MOpenedChestTypeSentinelBridge() {
        super(MappedClasses.q1);
    }

    public Object getBasic() {
        return this.basicField.getObject(null);
    }
}

