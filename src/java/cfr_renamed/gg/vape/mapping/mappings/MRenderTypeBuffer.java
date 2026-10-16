/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MMinecraft;
import gg.vape.wrapper.impl.ForgeVersion;

public class MRenderTypeBuffer
extends Mapping {
    private MappingMethod var_gg_vape_mapping_MappingMethod_L;
    private MappingMethod var_gg_vape_mapping_MappingMethod_l;
    private MappingMethod var_gg_vape_mapping_MappingMethod_Z;

    public static void n(MRenderTypeBuffer mRenderTypeBuffer, Object object) {
        mRenderTypeBuffer.Z(object);
    }

    public MRenderTypeBuffer() {
        this(MMinecraft.Q());
    }

    private MRenderTypeBuffer(String[] stringArray) {
        super(MappedClasses.ZK);
        String string;
        boolean bl;
        Class clazz;
        Class[] classArray;
        if (stringArray != null) {
            if (ForgeVersion.MC_1_21_0.v()) {
                Class[] classArray2 = new Class[]{};
                Class<Void> clazz2 = Void.TYPE;
                boolean bl2 = true;
                String string2 = "endBatch";
                Class clazz22 = MappedClasses.lp;
                MRenderTypeBuffer mRenderTypeBuffer = this;
                this.var_gg_vape_mapping_MappingMethod_Z = mRenderTypeBuffer.registerInstanceMethodForOwner(clazz22, string2, bl2, clazz2, classArray2);
            }
            return;
        }
        if (ForgeVersion.MC_1_21_0.v()) {
            classArray = new Class[]{MappedClasses.lX};
            clazz = MappedClasses.lp;
            bl = true;
            string = "getImpl";
            MRenderTypeBuffer mRenderTypeBuffer = this;
            this.var_gg_vape_mapping_MappingMethod_L = mRenderTypeBuffer.registerStaticMethod(string, bl, clazz, classArray);
        }
        classArray = new Class[]{};
        clazz = Void.TYPE;
        bl = true;
        string = "finish";
        Class clazz3 = MappedClasses.lp;
        MRenderTypeBuffer mRenderTypeBuffer = this;
        this.var_gg_vape_mapping_MappingMethod_l = mRenderTypeBuffer.registerInstanceMethodForOwner(clazz3, string, bl, clazz, classArray);
        if (ForgeVersion.MC_1_17.d()) {
            Class[] classArray2 = new Class[]{};
            Class<Void> clazz4 = Void.TYPE;
            boolean bl2 = true;
            String string2 = "endBatch";
            Class clazz5 = MappedClasses.lp;
            MRenderTypeBuffer mRenderTypeBuffer2 = this;
            this.var_gg_vape_mapping_MappingMethod_Z = this.registerInstanceMethodForOwner(clazz5, string2, bl2, clazz4, classArray2);
        }
    }

    private void z(Object object) {
        this.var_gg_vape_mapping_MappingMethod_Z.invokeVoidNoArgs(object);
    }

    public static void Q(MRenderTypeBuffer mRenderTypeBuffer, Object object) {
        mRenderTypeBuffer.z(object);
    }

    public static Object t(MRenderTypeBuffer mRenderTypeBuffer, Object object) {
        return mRenderTypeBuffer.c(object);
    }

    private Object c(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_L.invokeObject(null, object);
    }

    private void Z(Object object) {
        this.var_gg_vape_mapping_MappingMethod_l.invokeVoidNoArgs(object);
    }
}

