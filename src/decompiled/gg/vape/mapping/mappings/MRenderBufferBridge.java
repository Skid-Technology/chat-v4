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
    public MappingMethod z;
    private MappingMethod H;
    private MappingField r;
    private MappingMethod e;
    private MappingField K;

    private Object I(Object object) {
        return this.K.getObject(object);
    }

    public static Map P$src$Ljava_util_Map_$14yo47i(MRenderBufferBridge mRenderBufferBridge, Object object) {
        return mRenderBufferBridge.o(object);
    }

    private Object c(Object object) {
        return this.K.getObject(object);
    }

    public static void v(MRenderBufferBridge mRenderBufferBridge, Object object) {
        mRenderBufferBridge.T(object);
    }

    public static Object P(MRenderBufferBridge mRenderBufferBridge, Object object) {
        return mRenderBufferBridge.I(object);
    }

    private void T(Object object) {
        this.H.invokeVoidNoArgs(object);
    }

    public MRenderBufferBridge() {
        this(ScaledResolution.q());
    }

    private MRenderBufferBridge(int n) {
        super(MappedClasses.w);
        if (n != 0) {
            this.z = ForgeVersion.MC_26_2.d() ? this.Y("render", true, Void.TYPE, new Class[0]) : this.Y("render", true, Void.TYPE, MappedClasses.qk);
            if (ForgeVersion.MC_26_1.d()) {
                this.K = this.J("itemAtlas", true, MappedClasses.uF);
                this.H = this.Y("invalidateItemAtlas", true, Void.TYPE, new Class[0]);
            } else if (ForgeVersion.MC_1_21_10.d()) {
                this.K = this.J("itemsAtlas", true, MappedClasses.GPU_TEXTURE);
                this.r = this.J("atlasPositions", true, Map.class);
                this.e = this.Y("calculateAtlasSizeInPixels", true, Integer.TYPE, Integer.TYPE);
                this.H = this.Y("invalidateItemAtlas", true, Void.TYPE, new Class[0]);
            }
            return;
        }
        this.z = ForgeVersion.MC_26_2.d() ? this.Y("render", true, Void.TYPE, new Class[0]) : this.Y("render", true, Void.TYPE, MappedClasses.qk);
        if (ForgeVersion.MC_26_1.d()) {
            this.K = this.J("itemAtlas", true, MappedClasses.uF);
            this.H = this.Y("invalidateItemAtlas", true, Void.TYPE, new Class[0]);
        } else if (ForgeVersion.MC_1_21_10.d()) {
            this.K = this.J("itemsAtlas", true, MappedClasses.GPU_TEXTURE);
            this.r = this.J("atlasPositions", true, Map.class);
            this.e = this.Y("calculateAtlasSizeInPixels", true, Integer.TYPE, Integer.TYPE);
            this.H = this.Y("invalidateItemAtlas", true, Void.TYPE, new Class[0]);
        }
    }

    public static int J(MRenderBufferBridge mRenderBufferBridge, Object object, int n) {
        return mRenderBufferBridge.a(object, n);
    }

    public void o(Object object, Object object2) {
        if (ForgeVersion.MC_26_2.d()) {
            this.z.invokeVoidNoArgs(object);
            return;
        }
        this.z.invokeVoid(object, object2);
    }

    public static Object V(MRenderBufferBridge mRenderBufferBridge, Object object) {
        return mRenderBufferBridge.c(object);
    }

    private int a(Object object, int n) {
        if (this.e == null) {
            Object object2 = this.c(object);
            if (object2 == null) {
                return 0;
            }
            BlockStateContainerBridge blockStateContainerBridge = new BlockStateContainerBridge(object2);
            GpuTextureView textureView = blockStateContainerBridge.getTextureView();
            return textureView.isNull() ? 0 : textureView.getWidth(0);
        }
        return this.e.invokeInt(object, n);
    }

    private Map o(Object object) {
        if (this.r == null) {
            return null;
        }
        return (Map)this.r.getObject(object);
    }
}

