/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MEntityRenderer;
import gg.vape.wrapper.impl.ForgeVersion;

public class MLightTexture
extends Mapping {
    public MappingMethod var_gg_vape_mapping_MappingMethod_s;
    public MappingMethod var_gg_vape_mapping_MappingMethod_k;
    public MappingMethod var_gg_vape_mapping_MappingMethod_d;

    public static void B(MLightTexture mLightTexture, Object object, float f) {
        mLightTexture.k(object, f);
    }

    public static void z(MLightTexture mLightTexture, Object object) {
        mLightTexture.d(object);
    }

    private void F(Object object) {
        this.var_gg_vape_mapping_MappingMethod_s.invokeVoidNoArgs(object);
    }

    private void k(Object object, float f) {
        this.var_gg_vape_mapping_MappingMethod_d.invokeVoid(object, Float.valueOf(f));
    }

    public static void f(MLightTexture mLightTexture, Object object) {
        mLightTexture.F(object);
    }

    private void d(Object object) {
        this.var_gg_vape_mapping_MappingMethod_k.invokeVoidNoArgs(object);
    }

    public MLightTexture() {
        this(MEntityRenderer.X());
    }

    private MLightTexture(int n) {
        super(MappedClasses.zH);
        if (n != 0) {
            Class[] classArray = new Class[]{};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "enableLightmap";
            MLightTexture mLightTexture = this;
            this.var_gg_vape_mapping_MappingMethod_k = mLightTexture.Y(string, bl, clazz, classArray);
            return;
        }
        if (ForgeVersion.MC_1_21_11.v()) {
            Class[] classArray = new Class[]{Float.TYPE};
            Class<Void> clazz = Void.TYPE;
            boolean bl = true;
            String string = "updateLightmap";
            MLightTexture mLightTexture = this;
            this.var_gg_vape_mapping_MappingMethod_d = mLightTexture.Y(string, bl, clazz, classArray);
            Class[] classArray2 = new Class[]{};
            Class<Void> clazz2 = Void.TYPE;
            boolean bl2 = true;
            String string2 = "disableLightmap";
            MLightTexture mLightTexture2 = this;
            this.var_gg_vape_mapping_MappingMethod_s = this.Y(string2, bl2, clazz2, classArray2);
            Class[] classArray3 = new Class[]{};
            Class<Void> clazz3 = Void.TYPE;
            boolean bl3 = true;
            String string3 = "enableLightmap";
            MLightTexture mLightTexture3 = this;
            this.var_gg_vape_mapping_MappingMethod_k = this.Y(string3, bl3, clazz3, classArray3);
        }
    }
}

