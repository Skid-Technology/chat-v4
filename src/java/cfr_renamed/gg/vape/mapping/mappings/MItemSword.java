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

public class MItemSword
extends Mapping {
    private MappingMethod var_gg_vape_mapping_MappingMethod_Q;
    private MappingField var_gg_vape_mapping_MappingField_E;
    private MappingMethod var_gg_vape_mapping_MappingMethod_T;
    private MappingField var_gg_vape_mapping_MappingField_d;
    private MappingField var_gg_vape_mapping_MappingField_V;

    public static float T(MItemSword mItemSword, Object object) {
        return mItemSword.r(object);
    }

    public static Object x(MItemSword mItemSword, Object object) {
        return mItemSword.B(object);
    }

    private float U(Object object) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return this.var_gg_vape_mapping_MappingMethod_Q.invokeFloat(object, new Object[0]);
        }
        return this.var_gg_vape_mapping_MappingField_d.getFloat(object);
    }

    private Object B(Object object) {
        return this.var_gg_vape_mapping_MappingField_E.getObject(object);
    }

    public static float X(MItemSword mItemSword, Object object) {
        return mItemSword.l(object);
    }

    public static float c(MItemSword mItemSword, Object object) {
        return mItemSword.U(object);
    }

    private float r(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_T.invokeFloat(object, new Object[0]);
    }

    private float l(Object object) {
        return this.var_gg_vape_mapping_MappingField_V.getFloat(object);
    }

    public MItemSword() {
        this(MItemStack.f());
    }

    private MItemSword(int n) {
        super(MappedClasses.Ye);
        int n2 = n;
        if (ForgeVersion.MC_1_16_5.d()) {
            String string;
            Class[] classArray;
            MItemSword mItemSword;
            if (ForgeVersion.MC_1_21_4.v()) {
                Class clazz = MappedClasses.lh;
                boolean bl = true;
                String string2 = "tier";
                Class clazz2 = MappedClasses.uT;
                mItemSword = this;
                this.var_gg_vape_mapping_MappingField_E = mItemSword.registerInstanceFieldForOwner(clazz2, string2, bl, clazz);
            }
            if (ForgeVersion.MC_1_20_6.d()) {
                if (ForgeVersion.MC_1_21_4.v()) {
                    classArray = new Class[]{};
                    Class<Float> clazz = Float.TYPE;
                    boolean bl = true;
                    string = "getSpeed";
                    Class clazz3 = MappedClasses.lh;
                    MItemSword mItemSword2 = this;
                    this.var_gg_vape_mapping_MappingMethod_Q = mItemSword2.registerInstanceMethodForOwner(clazz3, string, bl, clazz, classArray);
                    Class[] classArray2 = new Class[]{};
                    Class<Float> clazz4 = Float.TYPE;
                    boolean bl2 = true;
                    String string2 = "getAttackDamageBonus";
                    Class clazz5 = MappedClasses.lh;
                    MItemSword mItemSword22 = this;
                    this.var_gg_vape_mapping_MappingMethod_T = this.registerInstanceMethodForOwner(clazz5, string2, bl2, clazz4, classArray2);
                }
            } else {
                classArray = new Class[]{};
                Class<Float> clazz = Float.TYPE;
                boolean bl = true;
                string = "getAttackDamage";
                mItemSword = this;
                this.var_gg_vape_mapping_MappingMethod_T = mItemSword.Y(string, bl, clazz, classArray);
                Class<Float> clazz6 = Float.TYPE;
                boolean bl3 = true;
                String string3 = "efficiency";
                MItemSword mItemSword3 = this;
                this.var_gg_vape_mapping_MappingField_d = this.J(string3, bl3, clazz6);
            }
        } else {
            Class clazz = MappedClasses.F5;
            boolean bl = true;
            String string = "toolMaterial";
            MItemSword mItemSword = this;
            this.var_gg_vape_mapping_MappingField_E = mItemSword.J(string, bl, clazz);
            Class<Float> clazz7 = Float.TYPE;
            boolean bl4 = true;
            String string4 = ForgeVersion.c() >= 23 ? "attackDamage" : "damageVsEntity";
            MItemSword mItemSword4 = this;
            this.var_gg_vape_mapping_MappingField_V = this.J(string4, bl4, clazz7);
            Class<Float> clazz8 = Float.TYPE;
            boolean bl5 = true;
            String string5 = ForgeVersion.c() >= 23 ? "efficiency" : "efficiencyOnProperMaterial";
            MItemSword mItemSword5 = this;
            this.var_gg_vape_mapping_MappingField_d = this.J(string5, bl5, clazz8);
        }
    }
}

