/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MEnumCreatureAttributeBridge
extends Mapping {
    private final static String UNDEFINED_FIELD_NAME = "UNDEFINED";
    private final MappingField undefinedField = this.registerStaticField("UNDEFINED", true, MappedClasses.var_java_lang_Class_O);

    public MEnumCreatureAttributeBridge() {
        super(MappedClasses.var_java_lang_Class_O);
    }

    public Object getUndefined() {
        return this.undefinedField.getObject(null);
    }
}

