/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MItemStack;
import gg.vape.wrapper.impl.ForgeVersion;

public class MItemArmor
extends Mapping {
    private MappingField var_gg_vape_mapping_MappingField_K;
    private MappingField var_gg_vape_mapping_MappingField_G;
    private MappingField var_gg_vape_mapping_MappingField_Q;
    private MappingField var_gg_vape_mapping_MappingField_T;
    private MappingMethod var_gg_vape_mapping_MappingMethod_U;
    private MappingMethod var_gg_vape_mapping_MappingMethod_u;

    public MItemArmor() {
        this(MItemStack.M());
    }

    private MItemArmor(int n) {
        super(MappedClasses.qU);
        MItemArmor mItemArmor;
        Class<Integer> clazz;
        MItemArmor mItemArmor2;
        String string;
        boolean bl;
        Class<Integer> clazz2;
        Class[] classArray;
        int n2 = n;
        if (ForgeVersion.MC_1_21_4.v()) {
            if (ForgeVersion.MC_1_20_6.d()) {
                classArray = new Class[]{};
                clazz2 = Integer.TYPE;
                bl = true;
                string = "getDefense";
                mItemArmor2 = this;
                this.var_gg_vape_mapping_MappingMethod_u = mItemArmor2.Y(string, bl, clazz2, classArray);
            } else {
                clazz = Integer.TYPE;
                boolean bl2 = true;
                String string2 = "damageReduceAmount";
                mItemArmor = this;
                this.var_gg_vape_mapping_MappingField_T = mItemArmor.J(string2, bl2, clazz);
            }
        }
        if (!ForgeVersion.MC_1_21_0.d()) {
            if (ForgeVersion.MC_1_20_6.d()) {
                clazz = MappedClasses.YW;
                boolean bl3 = true;
                String string3 = "type";
                mItemArmor = this;
                this.var_gg_vape_mapping_MappingField_Q = mItemArmor.J(string3, bl3, clazz);
                Class clazz22 = MappedClasses.Vo;
                boolean bl2 = true;
                String string2 = "material";
                MItemArmor mItemArmor22 = this;
                this.var_gg_vape_mapping_MappingField_K = this.J(string2, bl2, clazz22);
            } else if (ForgeVersion.MC_1_16_5.d()) {
                clazz = MappedClasses.FY;
                boolean bl4 = true;
                String string4 = "slot";
                mItemArmor = this;
                this.var_gg_vape_mapping_MappingField_Q = mItemArmor.J(string4, bl4, clazz);
                Class clazz3 = MappedClasses.uc;
                boolean bl3 = true;
                String string3 = "material";
                MItemArmor mItemArmor3 = this;
                this.var_gg_vape_mapping_MappingField_K = this.J(string3, bl3, clazz3);
            } else if (ForgeVersion.MC_1_12_2.d()) {
                clazz = MappedClasses.FY;
                boolean bl5 = true;
                String string5 = "armorType";
                mItemArmor = this;
                this.var_gg_vape_mapping_MappingField_Q = mItemArmor.J(string5, bl5, clazz);
                Class clazz4 = MappedClasses.ZM;
                boolean bl4 = true;
                String string4 = "material";
                MItemArmor mItemArmor4 = this;
                this.var_gg_vape_mapping_MappingField_K = this.J(string4, bl4, clazz4);
            } else {
                clazz = Integer.TYPE;
                boolean bl6 = true;
                String string6 = "armorType";
                mItemArmor = this;
                this.var_gg_vape_mapping_MappingField_Q = mItemArmor.J(string6, bl6, clazz);
                Class clazz5 = MappedClasses.ZM;
                boolean bl5 = true;
                String string5 = "material";
                MItemArmor mItemArmor5 = this;
                this.var_gg_vape_mapping_MappingField_K = this.J(string5, bl5, clazz5);
            }
        }
        if (ForgeVersion.MC_1_20_6.v()) {
            classArray = new Class[]{MappedClasses.VK};
            clazz2 = Integer.TYPE;
            bl = true;
            string = "getColor";
            mItemArmor2 = this;
            this.var_gg_vape_mapping_MappingMethod_U = mItemArmor2.Y(string, bl, clazz2, classArray);
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            clazz = Float.TYPE;
            boolean bl7 = true;
            String string7 = "toughness";
            mItemArmor = this;
            this.var_gg_vape_mapping_MappingField_G = mItemArmor.J(string7, bl7, clazz);
        }
    }

    private int o(Object object, Object object2) {
        return this.var_gg_vape_mapping_MappingMethod_U.invokeInt(object, object2);
    }

    public static float e(MItemArmor mItemArmor, Object object) {
        return mItemArmor.x(object);
    }

    public static int F(MItemArmor mItemArmor, Object object) {
        return mItemArmor.j(object);
    }

    private float x(Object object) {
        if (ForgeVersion.MC_1_12_2.d()) {
            return this.var_gg_vape_mapping_MappingField_G.getFloat(object);
        }
        return 0.0f;
    }

    private Object D(Object object) {
        return this.var_gg_vape_mapping_MappingField_K.getObject(object);
    }

    public static int q(MItemArmor mItemArmor, Object object) {
        return mItemArmor.P(object);
    }

    private Object T(Object object) {
        return this.var_gg_vape_mapping_MappingField_Q.getObject(object);
    }

    public static Object o(MItemArmor mItemArmor, Object object) {
        return mItemArmor.D(object);
    }

    public static int w(MItemArmor mItemArmor, Object object, Object object2) {
        return mItemArmor.o(object, object2);
    }

    private int P(Object object) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return this.var_gg_vape_mapping_MappingMethod_u.invokeInt(object, new Object[0]);
        }
        return this.var_gg_vape_mapping_MappingField_T.getInt(object);
    }

    public static Object D(MItemArmor mItemArmor, Object object) {
        return mItemArmor.T(object);
    }

    private int j(Object object) {
        return this.var_gg_vape_mapping_MappingField_Q.getInt(object);
    }
}

