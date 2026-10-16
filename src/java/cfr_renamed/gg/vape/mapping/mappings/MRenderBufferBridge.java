/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.BlockStateContainerBridge;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GpuTextureView;
import gg.vape.wrapper.impl.ScaledResolution;
import java.util.Map;

public class MRenderBufferBridge
extends Mapping {
    public MappingMethod var_gg_vape_mapping_MappingMethod_z;
    private MappingMethod var_gg_vape_mapping_MappingMethod_H;
    private MappingField var_gg_vape_mapping_MappingField_r;
    private MappingMethod var_gg_vape_mapping_MappingMethod_e;
    private MappingField var_gg_vape_mapping_MappingField_K;

    private Object I(Object object) {
        return this.var_gg_vape_mapping_MappingField_K.getObject(object);
    }

    public static Map P$src$Ljava_util_Map_$14yo47i(MRenderBufferBridge mRenderBufferBridge, Object object) {
        return mRenderBufferBridge.o(object);
    }

    private Object c(Object object) {
        return this.var_gg_vape_mapping_MappingField_K.getObject(object);
    }

    public static void v(MRenderBufferBridge mRenderBufferBridge, Object object) {
        mRenderBufferBridge.T(object);
    }

    public static Object P(MRenderBufferBridge mRenderBufferBridge, Object object) {
        return mRenderBufferBridge.I(object);
    }

    private void T(Object object) {
        this.var_gg_vape_mapping_MappingMethod_H.invokeVoidNoArgs(object);
    }

    public MRenderBufferBridge() {
        this(ScaledResolution.q());
    }

    private MRenderBufferBridge(int n) {
        super(MappedClasses.var_java_lang_Class_w);
        if (n != 0) {
            this.var_gg_vape_mapping_MappingMethod_z = ForgeVersion.MC_26_2.d() ? this.Y("render", true, Void.TYPE, new Class[0]) : this.Y("render", true, Void.TYPE, MappedClasses.qk);
            if (ForgeVersion.MC_26_1.d()) {
                this.var_gg_vape_mapping_MappingField_K = this.J("itemAtlas", true, MappedClasses.uF);
                this.var_gg_vape_mapping_MappingMethod_H = this.Y("invalidateItemAtlas", true, Void.TYPE, new Class[0]);
            } else if (ForgeVersion.MC_1_21_10.d()) {
                this.var_gg_vape_mapping_MappingField_K = this.J("itemsAtlas", true, MappedClasses.GPU_TEXTURE);
                this.var_gg_vape_mapping_MappingField_r = this.J("atlasPositions", true, Map.class);
                this.var_gg_vape_mapping_MappingMethod_e = this.Y("calculateAtlasSizeInPixels", true, Integer.TYPE, Integer.TYPE);
                this.var_gg_vape_mapping_MappingMethod_H = this.Y("invalidateItemAtlas", true, Void.TYPE, new Class[0]);
            }
            return;
        }
        this.var_gg_vape_mapping_MappingMethod_z = ForgeVersion.MC_26_2.d() ? this.Y("render", true, Void.TYPE, new Class[0]) : this.Y("render", true, Void.TYPE, MappedClasses.qk);
        if (ForgeVersion.MC_26_1.d()) {
            this.var_gg_vape_mapping_MappingField_K = this.J("itemAtlas", true, MappedClasses.uF);
            this.var_gg_vape_mapping_MappingMethod_H = this.Y("invalidateItemAtlas", true, Void.TYPE, new Class[0]);
        } else if (ForgeVersion.MC_1_21_10.d()) {
            this.var_gg_vape_mapping_MappingField_K = this.J("itemsAtlas", true, MappedClasses.GPU_TEXTURE);
            this.var_gg_vape_mapping_MappingField_r = this.J("atlasPositions", true, Map.class);
            this.var_gg_vape_mapping_MappingMethod_e = this.Y("calculateAtlasSizeInPixels", true, Integer.TYPE, Integer.TYPE);
            this.var_gg_vape_mapping_MappingMethod_H = this.Y("invalidateItemAtlas", true, Void.TYPE, new Class[0]);
        }
    }

    public static int J(MRenderBufferBridge mRenderBufferBridge, Object object, int n) {
        return mRenderBufferBridge.a(object, n);
    }

    public void o(Object object, Object object2) {
        if (ForgeVersion.MC_26_2.d()) {
            this.var_gg_vape_mapping_MappingMethod_z.invokeVoidNoArgs(object);
            return;
        }
        this.var_gg_vape_mapping_MappingMethod_z.invokeVoid(object, object2);
    }

    public static Object V(MRenderBufferBridge mRenderBufferBridge, Object object) {
        return mRenderBufferBridge.c(object);
    }

    private int a(Object object, int n) {
        if (this.var_gg_vape_mapping_MappingMethod_e == null) {
            Object object2 = this.c(object);
            if (object2 == null) {
                return 0;
            }
            BlockStateContainerBridge blockStateContainerBridge = new BlockStateContainerBridge(object2);
            GpuTextureView textureView = blockStateContainerBridge.getTextureView();
            return textureView.isNull() ? 0 : textureView.getWidth(0);
        }
        return this.var_gg_vape_mapping_MappingMethod_e.invokeInt(object, n);
    }

    private Map o(Object object) {
        if (this.var_gg_vape_mapping_MappingField_r == null) {
            return null;
        }
        return (Map)this.var_gg_vape_mapping_MappingField_r.getObject(object);
    }
}

