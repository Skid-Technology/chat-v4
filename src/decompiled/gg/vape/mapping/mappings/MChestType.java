/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MChestType
extends Mapping {
    private static final String FALL_DAMAGE_RESETTING_FIELD_NAME = "FALLDAMAGE_RESETTING";
    private final MappingField fallDamageResettingField = this.registerStaticField("FALLDAMAGE_RESETTING", true, MappedClasses.lN);

    public MChestType() {
        super(MappedClasses.lN);
    }

    public Object getFallDamageResetting() {
        return this.fallDamageResettingField.getObject(null);
    }
}

