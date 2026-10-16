/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.Wrapper;

public class MMoverType
extends Mapping {
    private final MappingField var_gg_vape_mapping_MappingField_W;
    private final MappingField var_gg_vape_mapping_MappingField_x;
    private final MappingField var_gg_vape_mapping_MappingField_M;
    private final MappingField var_gg_vape_mapping_MappingField_N;
    private final MappingField var_gg_vape_mapping_MappingField_J;

    public Object k() {
        return this.var_gg_vape_mapping_MappingField_M.getObject(null);
    }

    public MMoverType() {
        super(MappedClasses.var_java_lang_Class_k);
        Class clazz = MappedClasses.var_java_lang_Class_k;
        boolean bl = Wrapper.isNativeAvailable;
        String string = "SELF";
        MMoverType mMoverType = this;
        this.var_gg_vape_mapping_MappingField_x = this.registerStaticField(string, bl, clazz);
        Class clazz2 = MappedClasses.var_java_lang_Class_k;
        boolean bl2 = Wrapper.isNativeAvailable;
        String string2 = "PLAYER";
        MMoverType mMoverType2 = this;
        this.var_gg_vape_mapping_MappingField_W = this.registerStaticField(string2, bl2, clazz2);
        Class clazz3 = MappedClasses.var_java_lang_Class_k;
        boolean bl3 = Wrapper.isNativeAvailable;
        String string3 = "PISTON";
        MMoverType mMoverType3 = this;
        this.var_gg_vape_mapping_MappingField_N = this.registerStaticField(string3, bl3, clazz3);
        Class clazz4 = MappedClasses.var_java_lang_Class_k;
        boolean bl4 = Wrapper.isNativeAvailable;
        String string4 = "SHULKER_BOX";
        MMoverType mMoverType4 = this;
        this.var_gg_vape_mapping_MappingField_J = this.registerStaticField(string4, bl4, clazz4);
        Class clazz5 = MappedClasses.var_java_lang_Class_k;
        boolean bl5 = Wrapper.isNativeAvailable;
        String string5 = "SHULKER";
        MMoverType mMoverType5 = this;
        this.var_gg_vape_mapping_MappingField_M = this.registerStaticField(string5, bl5, clazz5);
    }

    public Object x() {
        return this.var_gg_vape_mapping_MappingField_N.getObject(null);
    }

    public Object W() {
        return this.var_gg_vape_mapping_MappingField_x.getObject(null);
    }

    public Object h() {
        return this.var_gg_vape_mapping_MappingField_W.getObject(null);
    }

    public Object q() {
        return this.var_gg_vape_mapping_MappingField_J.getObject(null);
    }
}

