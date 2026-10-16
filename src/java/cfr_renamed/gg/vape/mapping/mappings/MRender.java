/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MRenderManager;
import gg.vape.wrapper.impl.ForgeVersion;

public class MRender
extends Mapping {
    private MappingMethod var_gg_vape_mapping_MappingMethod_I;
    public MappingMethod var_gg_vape_mapping_MappingMethod_p;
    public MappingMethod var_gg_vape_mapping_MappingMethod_z;
    public MappingMethod var_gg_vape_mapping_MappingMethod_w;

    private Object e(Object object, Object object2) {
        return this.var_gg_vape_mapping_MappingMethod_w.invokeObject(object, object2);
    }

    public static Object s(MRender mRender, Object object, Object object2, float f) {
        return mRender.w(object, object2, f);
    }

    public static Object s(MRender mRender, Object object, Object object2) {
        return mRender.e(object, object2);
    }

    private void S(Object object, Object object2, double d, double d2, double d3, float f, float f2) {
        this.var_gg_vape_mapping_MappingMethod_p.invokeVoid(object, object2, d, d2, d3, Float.valueOf(f), Float.valueOf(f2));
    }

    public static void E(MRender mRender, Object object, Object object2, double d, double d2, double d3, float f, float f2) {
        mRender.S(object, object2, d, d2, d3, f, f2);
    }

    public MRender() {
        this(MRenderManager.O());
    }

    private MRender(String[] stringArray) {
        super(MappedClasses.VQ);
        MRender mRender;
        String string;
        boolean bl;
        Class<Void> clazz;
        Class[] classArray;
        String[] stringArray2 = stringArray;
        if (ForgeVersion.MC_1_21_6.d()) {
            classArray = new Class[]{MappedClasses.zc, Float.TYPE};
            clazz = MappedClasses.qX;
            bl = true;
            string = "createRenderState";
            mRender = this;
            this.var_gg_vape_mapping_MappingMethod_I = mRender.Y(string, bl, clazz, classArray);
        }
        if (ForgeVersion.MC_1_21_10.d()) {
            classArray = new Class[]{MappedClasses.zc, MappedClasses.qX, Float.TYPE};
            clazz = Void.TYPE;
            bl = true;
            string = "extractRenderState";
            mRender = this;
            this.var_gg_vape_mapping_MappingMethod_z = mRender.Y(string, bl, clazz, classArray);
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            if (ForgeVersion.MC_1_21_10.v()) {
                classArray = new Class[]{MappedClasses.zc, Float.TYPE, Float.TYPE, MappedClasses.DQ, MappedClasses.ZK, Integer.TYPE};
                clazz = Void.TYPE;
                bl = true;
                string = "render";
                mRender = this;
                this.var_gg_vape_mapping_MappingMethod_p = mRender.Y(string, bl, clazz, classArray);
            }
        } else {
            classArray = new Class[]{MappedClasses.zc, Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE};
            clazz = Void.TYPE;
            bl = true;
            string = "doRender";
            mRender = this;
            this.var_gg_vape_mapping_MappingMethod_p = mRender.Y(string, bl, clazz, classArray);
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            classArray = new Class[]{MappedClasses.qX};
            clazz = MappedClasses.zC;
            bl = true;
            string = "getTextureLocation";
            Class clazz2 = MappedClasses.Fq;
            MRender mRender2 = this;
            this.var_gg_vape_mapping_MappingMethod_w = mRender2.registerInstanceMethodForOwner(clazz2, string, bl, clazz, classArray);
        } else {
            classArray = new Class[]{MappedClasses.zc};
            clazz = MappedClasses.zC;
            bl = true;
            string = "getEntityTexture";
            mRender = this;
            this.var_gg_vape_mapping_MappingMethod_w = mRender.Y(string, bl, clazz, classArray);
        }
    }

    private Object w(Object object, Object object2, float f) {
        return this.var_gg_vape_mapping_MappingMethod_I.invokeObject(object, object2, Float.valueOf(f));
    }
}

