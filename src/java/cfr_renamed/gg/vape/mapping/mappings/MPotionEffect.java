/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MPotion;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MPotionEffect
extends Mapping {
    private MappingMethod var_gg_vape_mapping_MappingMethod_C;
    private MappingField var_gg_vape_mapping_MappingField_l;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_Q;
    private MappingField var_gg_vape_mapping_MappingField_m;
    private MappingMethod var_gg_vape_mapping_MappingMethod_e;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_Y;
    private MappingMethod var_gg_vape_mapping_MappingMethod_V;

    public MPotionEffect() {
        this(MPotion.K());
    }

    private MPotionEffect(int[] nArray) {
        super(MappedClasses.u3);
        MPotionEffect mPotionEffect;
        String string;
        Class[] classArray;
        if (nArray != null) {
            Class[] classArray2 = new Class[]{};
            Class<Integer> clazz = Integer.TYPE;
            boolean bl = true;
            String string2 = "getPotionID";
            MPotionEffect mPotionEffect2 = this;
            this.var_gg_vape_mapping_MappingMethod_e = mPotionEffect2.Y(string2, bl, clazz, classArray2);
            Class[] classArray22 = new Class[]{};
            Class<Integer> clazz2 = Integer.TYPE;
            boolean bl2 = true;
            String string22 = "getAmplifier";
            MPotionEffect mPotionEffect22 = this;
            this.var_gg_vape_mapping_MappingMethod_Q = this.Y(string22, bl2, clazz2, classArray22);
            Class[] classArray3 = new Class[]{};
            Class<Integer> clazz3 = Integer.TYPE;
            boolean bl3 = true;
            String string3 = "getDuration";
            MPotionEffect mPotionEffect3 = this;
            this.var_gg_vape_mapping_MappingMethod_Y = this.Y(string3, bl3, clazz3, classArray3);
            if (GuiComponent.getLegacyComponentState() == null) {
                MPotion.U(new int[1]);
            }
            return;
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            if (ForgeVersion.MC_1_16_5.d()) {
                boolean bl;
                Class<Void> clazz;
                if (ForgeVersion.MC_1_20_6.d()) {
                    classArray = new Class[]{MappedClasses.Vo, Integer.TYPE, Integer.TYPE};
                    clazz = Void.TYPE;
                    bl = false;
                    string = "<init>";
                    mPotionEffect = this;
                    this.var_gg_vape_mapping_MappingMethod_V = mPotionEffect.Y(string, bl, clazz, classArray);
                } else {
                    classArray = new Class[]{MappedClasses.D3, Integer.TYPE, Integer.TYPE};
                    clazz = Void.TYPE;
                    bl = false;
                    string = "<init>";
                    mPotionEffect = this;
                    this.var_gg_vape_mapping_MappingMethod_V = mPotionEffect.Y(string, bl, clazz, classArray);
                }
                Class clazz2 = MappedClasses.D3;
                boolean bl2 = true;
                String string3 = "potion";
                MPotionEffect mPotionEffect3 = this;
                this.var_gg_vape_mapping_MappingField_m = mPotionEffect3.J(string3, bl2, clazz2);
                Class<Boolean> clazz4 = Boolean.TYPE;
                boolean bl4 = true;
                String string4 = "showIcon";
                MPotionEffect mPotionEffect4 = this;
                this.var_gg_vape_mapping_MappingField_l = this.J(string4, bl4, clazz4);
            } else {
                classArray = new Class[]{MappedClasses.FR, Integer.TYPE, Integer.TYPE};
                Class<Void> clazz = Void.TYPE;
                boolean bl = false;
                string = "<init>";
                mPotionEffect = this;
                this.var_gg_vape_mapping_MappingMethod_V = mPotionEffect.Y(string, bl, clazz, classArray);
                Class clazz5 = MappedClasses.FR;
                boolean bl5 = true;
                String string5 = "potion";
                MPotionEffect mPotionEffect5 = this;
                this.var_gg_vape_mapping_MappingField_m = this.J(string5, bl5, clazz5);
            }
        } else {
            classArray = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE};
            Class<Void> clazz = Void.TYPE;
            boolean bl = false;
            string = "<init>";
            mPotionEffect = this;
            this.var_gg_vape_mapping_MappingMethod_C = mPotionEffect.Y(string, bl, clazz, classArray);
            Class[] classArray4 = new Class[]{};
            Class<Integer> clazz6 = Integer.TYPE;
            boolean bl6 = true;
            String string6 = "getPotionID";
            MPotionEffect mPotionEffect6 = this;
            this.var_gg_vape_mapping_MappingMethod_e = this.Y(string6, bl6, clazz6, classArray4);
        }
        classArray = new Class[]{};
        Class<Integer> clazz = Integer.TYPE;
        boolean bl = true;
        string = "getAmplifier";
        mPotionEffect = this;
        this.var_gg_vape_mapping_MappingMethod_Q = mPotionEffect.Y(string, bl, clazz, classArray);
        Class[] classArray5 = new Class[]{};
        Class<Integer> clazz7 = Integer.TYPE;
        boolean bl7 = true;
        String string7 = "getDuration";
        MPotionEffect mPotionEffect7 = this;
        this.var_gg_vape_mapping_MappingMethod_Y = this.Y(string7, bl7, clazz7, classArray5);
        if (GuiComponent.getLegacyComponentState() == null) {
            MPotion.U(new int[1]);
        }
    }

    private Object a(Object object) {
        return this.var_gg_vape_mapping_MappingField_m.getObject(object);
    }

    public static int e(MPotionEffect mPotionEffect, Object object) {
        return mPotionEffect.N(object);
    }

    private Object u(int n, int n2, int n3) {
        return this.var_gg_vape_mapping_MappingMethod_C.newInstance(n, n2, n3);
    }

    private int N(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_Y.invokeInt(object, new Object[0]);
    }

    public static boolean e$src$Z$v3xxtq(MPotionEffect mPotionEffect, Object object) {
        return mPotionEffect.w(object);
    }

    public static int I(MPotionEffect mPotionEffect, Object object) {
        return mPotionEffect.h(object);
    }

    public static Object q(MPotionEffect mPotionEffect, int n, int n2, int n3) {
        return mPotionEffect.u(n, n2, n3);
    }

    public static int K(MPotionEffect mPotionEffect, Object object) {
        return mPotionEffect.e(object);
    }

    private boolean w(Object object) {
        return this.var_gg_vape_mapping_MappingField_l.getBoolean(object);
    }

    private int e(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_Q.invokeInt(object, new Object[0]);
    }

    public Object A(Object object, int n, int n2) {
        return this.var_gg_vape_mapping_MappingMethod_V.newInstance(object, n, n2);
    }

    private int h(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_e.invokeInt(object, new Object[0]);
    }

    public static Object I$src$Ljava_lang_Object_$1dcpybi(MPotionEffect mPotionEffect, Object object) {
        return mPotionEffect.a(object);
    }
}

