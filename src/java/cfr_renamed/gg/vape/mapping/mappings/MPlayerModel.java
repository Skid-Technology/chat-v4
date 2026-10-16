/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.mappings.MModelRenderer;
import gg.vape.wrapper.Wrapper;

public class MPlayerModel
extends Mapping {
    private MappingField var_gg_vape_mapping_MappingField_q;
    private MappingField var_gg_vape_mapping_MappingField_d;
    private MappingField var_gg_vape_mapping_MappingField_M;
    private MappingField var_gg_vape_mapping_MappingField_Y;
    private MappingField var_gg_vape_mapping_MappingField_S;

    public Object F(Object object) {
        return this.var_gg_vape_mapping_MappingField_d.getObject(object);
    }

    public Object e(Object object) {
        return this.var_gg_vape_mapping_MappingField_S.getObject(object);
    }

    public Object G(Object object) {
        return this.var_gg_vape_mapping_MappingField_Y.getObject(object);
    }

    public MPlayerModel() {
        this(MModelRenderer.T());
    }

    private MPlayerModel(int[] nArray) {
        super(MappedClasses.ud);
        if (nArray != null) {
            if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
                Class clazz = MappedClasses.Yd;
                boolean bl = Wrapper.isNativeAvailable;
                String string = "bipedBodyWear";
                MPlayerModel mPlayerModel = this;
                this.var_gg_vape_mapping_MappingField_Y = mPlayerModel.J(string, bl, clazz);
                Class clazz2 = MappedClasses.Yd;
                boolean bl2 = Wrapper.isNativeAvailable;
                String string2 = "bipedRightArmwear";
                MPlayerModel mPlayerModel2 = this;
                this.var_gg_vape_mapping_MappingField_M = this.J(string2, bl2, clazz2);
                Class clazz3 = MappedClasses.Yd;
                boolean bl3 = Wrapper.isNativeAvailable;
                String string3 = "bipedLeftArmwear";
                MPlayerModel mPlayerModel3 = this;
                this.var_gg_vape_mapping_MappingField_q = this.J(string3, bl3, clazz3);
                Class clazz4 = MappedClasses.Yd;
                boolean bl4 = Wrapper.isNativeAvailable;
                String string4 = "bipedRightLegwear";
                MPlayerModel mPlayerModel4 = this;
                this.var_gg_vape_mapping_MappingField_d = this.J(string4, bl4, clazz4);
                Class clazz5 = MappedClasses.Yd;
                boolean bl5 = Wrapper.isNativeAvailable;
                String string5 = "bipedLeftLegwear";
                MPlayerModel mPlayerModel5 = this;
                this.var_gg_vape_mapping_MappingField_S = this.J(string5, bl5, clazz5);
            } else {
                Class clazz = MappedClasses.Yd;
                boolean bl = Wrapper.isNativeAvailable;
                String string = "field_178730_v";
                MPlayerModel mPlayerModel = this;
                this.var_gg_vape_mapping_MappingField_Y = mPlayerModel.J(string, bl, clazz);
                Class clazz6 = MappedClasses.Yd;
                boolean bl6 = Wrapper.isNativeAvailable;
                String string6 = "field_178732_b";
                MPlayerModel mPlayerModel6 = this;
                this.var_gg_vape_mapping_MappingField_M = this.J(string6, bl6, clazz6);
                Class clazz7 = MappedClasses.Yd;
                boolean bl7 = Wrapper.isNativeAvailable;
                String string7 = "field_178734_a";
                MPlayerModel mPlayerModel7 = this;
                this.var_gg_vape_mapping_MappingField_q = this.J(string7, bl7, clazz7);
                Class clazz8 = MappedClasses.Yd;
                boolean bl8 = Wrapper.isNativeAvailable;
                String string8 = "field_178731_d";
                MPlayerModel mPlayerModel8 = this;
                this.var_gg_vape_mapping_MappingField_d = this.J(string8, bl8, clazz8);
                Class clazz9 = MappedClasses.Yd;
                boolean bl9 = Wrapper.isNativeAvailable;
                String string9 = "field_178733_c";
                MPlayerModel mPlayerModel9 = this;
                this.var_gg_vape_mapping_MappingField_S = this.J(string9, bl9, clazz9);
            }
            return;
        }
        Class clazz = MappedClasses.Yd;
        boolean bl = Wrapper.isNativeAvailable;
        String string = "field_178733_c";
        MPlayerModel mPlayerModel = this;
        this.var_gg_vape_mapping_MappingField_S = mPlayerModel.J(string, bl, clazz);
    }

    public Object J(Object object) {
        return this.var_gg_vape_mapping_MappingField_q.getObject(object);
    }

    public Object Y(Object object) {
        return this.var_gg_vape_mapping_MappingField_M.getObject(object);
    }
}

