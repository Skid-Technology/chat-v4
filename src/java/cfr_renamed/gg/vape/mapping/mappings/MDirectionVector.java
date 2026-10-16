/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MDirectionVector
extends Mapping {
    private final static String POSITIVE_FIELD_NAME = "POSITIVE";
    private final MappingField positiveField = this.registerStaticField("POSITIVE", true, MappedClasses.Vy);

    public static Object getPositive(MDirectionVector mapping) {
        return mapping.readPositive();
    }

    public MDirectionVector() {
        super(MappedClasses.Vy);
    }

    private Object readPositive() {
        return this.positiveField.getObject(null);
    }
}

