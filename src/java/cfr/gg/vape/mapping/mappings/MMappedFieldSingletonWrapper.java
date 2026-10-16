/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MMappedFieldSingletonWrapper
extends Mapping {
    private static final String HUMANOID_ARMOR_FIELD_NAME = "HUMANOID_ARMOR";
    private final MappingField humanoidArmorField = this.registerStaticField("HUMANOID_ARMOR", true, MappedClasses.Vf);

    public MMappedFieldSingletonWrapper() {
        super(MappedClasses.Vf);
    }

    public static MappingField getHumanoidArmorField(MMappedFieldSingletonWrapper mapping) {
        return mapping.humanoidArmorField;
    }
}

