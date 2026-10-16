/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MRenderManager;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import java.nio.FloatBuffer;
import java.util.List;

public class MRenderLivingBase
extends Mapping {
    private MappingField var_gg_vape_mapping_MappingField_g;
    public MappingMethod var_gg_vape_mapping_MappingMethod_c;
    public MappingMethod var_gg_vape_mapping_MappingMethod_O;
    private MappingField var_gg_vape_mapping_MappingField_G;
    private MappingMethod var_gg_vape_mapping_MappingMethod_Q;
    private MappingMethod var_gg_vape_mapping_MappingMethod_h;
    public MappingMethod var_gg_vape_mapping_MappingMethod_r;
    private MappingField var_gg_vape_mapping_MappingField_f;
    private MappingField var_gg_vape_mapping_MappingField_o;

    public static Object i(MRenderLivingBase mRenderLivingBase, Object object) {
        return mRenderLivingBase.I(object);
    }

    public static void O(MRenderLivingBase mRenderLivingBase, Object object, Object object2) {
        mRenderLivingBase.B(object, object2);
    }

    private Object I(Object object) {
        return this.var_gg_vape_mapping_MappingField_G.getObject(object);
    }

    private void B(Object object, Object object2) {
        this.var_gg_vape_mapping_MappingField_G.setObject(object, object2);
    }

    private Object d(Object object) {
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.var_gg_vape_mapping_MappingField_g.getObject(object);
        }
        if (ForgeVersion.MC_1_7_10.L()) {
            return this.var_gg_vape_mapping_MappingField_o.getObject(object);
        }
        return this.var_gg_vape_mapping_MappingMethod_Q.invokeObject(object, new Object[0]);
    }

    public static FloatBuffer y(MRenderLivingBase mRenderLivingBase, Object object) {
        return mRenderLivingBase.D(object);
    }

    public static Object x(MRenderLivingBase mRenderLivingBase, Object object) {
        return mRenderLivingBase.d(object);
    }

    public MRenderLivingBase() {
        this(MRenderManager.O());
    }

    private MRenderLivingBase(String[] stringArray) {
        super(MappedClasses.Fq);
        String[] stringArray2 = stringArray;
        if (ForgeVersion.MC_1_21_10.d()) {
            this.var_gg_vape_mapping_MappingMethod_c = null;
            Class clazz = MappedClasses.V6;
            boolean bl = true;
            String string = "entityModel";
            MRenderLivingBase mRenderLivingBase = this;
            this.var_gg_vape_mapping_MappingField_g = mRenderLivingBase.J(string, bl, clazz);
        } else if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray = new Class[]{MappedClasses.zc, Float.TYPE, Float.TYPE, MappedClasses.DQ, MappedClasses.ZK, Integer.TYPE};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "render";
            Class clazz2 = MappedClasses.VQ;
            MRenderLivingBase mRenderLivingBase = this;
            this.var_gg_vape_mapping_MappingMethod_c = mRenderLivingBase.registerInstanceMethodForOwner(clazz2, string, bl, clazz, classArray);
            Class clazz3 = MappedClasses.V6;
            boolean bl2 = true;
            String string2 = "entityModel";
            MRenderLivingBase mRenderLivingBase2 = this;
            this.var_gg_vape_mapping_MappingField_g = this.J(string2, bl2, clazz3);
        } else if (ForgeVersion.MC_1_7_10.L()) {
            Class clazz = MappedClasses.var_java_lang_Class_M;
            boolean bl = true;
            String string = "mainModel";
            MRenderLivingBase mRenderLivingBase = this;
            this.var_gg_vape_mapping_MappingField_o = mRenderLivingBase.J(string, bl, clazz);
            Class[] classArray = new Class[]{MappedClasses.zm, Double.TYPE, Double.TYPE, Double.TYPE};
            Class<Void> clazz4 = Void.TYPE;
            boolean bl3 = true;
            String string3 = "passSpecialRender";
            MRenderLivingBase mRenderLivingBase3 = this;
            this.var_gg_vape_mapping_MappingMethod_r = this.Y(string3, bl3, clazz4, classArray);
        } else {
            MRenderLivingBase mRenderLivingBase;
            String string;
            boolean bl;
            Class clazz;
            if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
                clazz = List.class;
                bl = true;
                string = "layerRenderers";
                mRenderLivingBase = this;
                this.var_gg_vape_mapping_MappingField_G = mRenderLivingBase.J(string, bl, clazz);
            } else {
                clazz = List.class;
                bl = Wrapper.isNativeAvailable;
                string = "field_177097_h";
                mRenderLivingBase = this;
                this.var_gg_vape_mapping_MappingField_G = mRenderLivingBase.J(string, bl, clazz);
            }
            clazz = FloatBuffer.class;
            bl = true;
            string = "brightnessBuffer";
            mRenderLivingBase = this;
            this.var_gg_vape_mapping_MappingField_f = mRenderLivingBase.J(string, bl, clazz);
            Class[] classArray = new Class[]{};
            Class clazz5 = MappedClasses.var_java_lang_Class_M;
            boolean bl4 = true;
            String string4 = "getMainModel";
            MRenderLivingBase mRenderLivingBase4 = this;
            this.var_gg_vape_mapping_MappingMethod_Q = this.Y(string4, bl4, clazz5, classArray);
            Class[] classArray2 = new Class[]{MappedClasses.zm, Double.TYPE, Double.TYPE, Double.TYPE};
            Class<Void> clazz6 = Void.TYPE;
            boolean bl5 = true;
            String string5 = "renderName";
            MRenderLivingBase mRenderLivingBase5 = this;
            this.var_gg_vape_mapping_MappingMethod_c = this.Y(string5, bl5, clazz6, classArray2);
            Class[] classArray3 = new Class[]{};
            Class<Void> clazz7 = Void.TYPE;
            boolean bl6 = true;
            String string6 = "unsetBrightness";
            MRenderLivingBase mRenderLivingBase6 = this;
            this.var_gg_vape_mapping_MappingMethod_h = this.Y(string6, bl6, clazz7, classArray3);
            Class[] classArray4 = new Class[]{MappedClasses.zm, Float.TYPE, Boolean.TYPE};
            Class<Boolean> clazz8 = Boolean.TYPE;
            boolean bl7 = true;
            String string7 = "setBrightness";
            MRenderLivingBase mRenderLivingBase7 = this;
            this.var_gg_vape_mapping_MappingMethod_O = this.Y(string7, bl7, clazz8, classArray4);
        }
    }

    boolean A(Object object, Object object2, float f, boolean bl) {
        return this.var_gg_vape_mapping_MappingMethod_O.invokeBoolean(object, object2, Float.valueOf(f), bl);
    }

    private void t(Object object) {
        this.var_gg_vape_mapping_MappingMethod_h.invokeVoidNoArgs(object);
    }

    private FloatBuffer D(Object object) {
        return (FloatBuffer)this.var_gg_vape_mapping_MappingField_f.getObject(object);
    }
}

