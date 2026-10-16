/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MEntityRemovalReason
extends Mapping {
    private MappingField var_gg_vape_mapping_MappingField_O;
    private MappingField var_gg_vape_mapping_MappingField_x;
    private MappingField var_gg_vape_mapping_MappingField_X;
    private MappingField var_gg_vape_mapping_MappingField_v;
    private MappingField var_gg_vape_mapping_MappingField_f;

    private Object D() {
        return this.var_gg_vape_mapping_MappingField_f.getObject(null);
    }

    public static Object k(MEntityRemovalReason mEntityRemovalReason) {
        return mEntityRemovalReason.l();
    }

    public MEntityRemovalReason() {
        super(MappedClasses.var_java_lang_Class_c);
        Class clazz = MappedClasses.var_java_lang_Class_c;
        boolean bl = true;
        String string = "KILLED";
        MEntityRemovalReason mEntityRemovalReason = this;
        this.var_gg_vape_mapping_MappingField_x = this.registerStaticField(string, bl, clazz);
        Class clazz2 = MappedClasses.var_java_lang_Class_c;
        boolean bl2 = true;
        String string2 = "DISCARDED";
        MEntityRemovalReason mEntityRemovalReason2 = this;
        this.var_gg_vape_mapping_MappingField_O = this.registerStaticField(string2, bl2, clazz2);
        Class clazz3 = MappedClasses.var_java_lang_Class_c;
        boolean bl3 = true;
        String string3 = "UNLOADED_TO_CHUNK";
        MEntityRemovalReason mEntityRemovalReason3 = this;
        this.var_gg_vape_mapping_MappingField_X = this.registerStaticField(string3, bl3, clazz3);
        Class clazz4 = MappedClasses.var_java_lang_Class_c;
        boolean bl4 = true;
        String string4 = "UNLOADED_WITH_PLAYER";
        MEntityRemovalReason mEntityRemovalReason4 = this;
        this.var_gg_vape_mapping_MappingField_f = this.registerStaticField(string4, bl4, clazz4);
        Class clazz5 = MappedClasses.var_java_lang_Class_c;
        boolean bl5 = true;
        String string5 = "CHANGED_DIMENSION";
        MEntityRemovalReason mEntityRemovalReason5 = this;
        this.var_gg_vape_mapping_MappingField_v = this.registerStaticField(string5, bl5, clazz5);
    }

    public static Object Z(MEntityRemovalReason mEntityRemovalReason) {
        return mEntityRemovalReason.D();
    }

    public static Object N(MEntityRemovalReason mEntityRemovalReason) {
        return mEntityRemovalReason.Y();
    }

    private Object l() {
        return this.var_gg_vape_mapping_MappingField_x.getObject(null);
    }

    public static Object U(MEntityRemovalReason mEntityRemovalReason) {
        return mEntityRemovalReason.Z();
    }

    private Object e() {
        return this.var_gg_vape_mapping_MappingField_O.getObject(null);
    }

    private Object Z() {
        return this.var_gg_vape_mapping_MappingField_v.getObject(null);
    }

    public static Object i(MEntityRemovalReason mEntityRemovalReason) {
        return mEntityRemovalReason.e();
    }

    private Object Y() {
        return this.var_gg_vape_mapping_MappingField_X.getObject(null);
    }
}

