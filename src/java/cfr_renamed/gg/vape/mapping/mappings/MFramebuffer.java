/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MShaderGroup;
import gg.vape.wrapper.impl.ForgeVersion;

public class MFramebuffer
extends Mapping {
    private MappingField var_gg_vape_mapping_MappingField_l;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_r;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_f;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_P;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_F;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_H;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_S;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_V;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_W;

    public static void D(MFramebuffer mFramebuffer, Object object) {
        mFramebuffer.x(object);
    }

    public static void bindFramebuffer(MFramebuffer mFramebuffer, Object object, boolean bl) {
        mFramebuffer.bindFramebuffer(object, bl);
    }

    public static void G(MFramebuffer mFramebuffer, Object object, int n, int n2) {
        mFramebuffer.E(object, n, n2);
    }

    public static void l(MFramebuffer mFramebuffer, Object object) {
        mFramebuffer.A(object);
    }

    public static void e(MFramebuffer mFramebuffer, Object object) {
        mFramebuffer.I(object);
    }

    private void bindFramebuffer(Object object, boolean bl) {
        if (this.var_gg_vape_mapping_MappingMethod_r == null) {
            return;
        }
        this.var_gg_vape_mapping_MappingMethod_r.invokeVoid(object, bl);
    }

    public static void m(MFramebuffer mFramebuffer, Object object) {
        mFramebuffer.Q(object);
    }

    private void Q(Object object) {
        if (this.var_gg_vape_mapping_MappingMethod_P == null) {
            return;
        }
        this.var_gg_vape_mapping_MappingMethod_P.invokeVoidNoArgs(object);
    }

    private void k(Object object, int n, int n2, boolean bl) {
        if (this.var_gg_vape_mapping_MappingMethod_f == null) {
            return;
        }
        if (ForgeVersion.MC_1_21_0.d()) {
            this.var_gg_vape_mapping_MappingMethod_f.invokeVoid(object, n, n2);
            return;
        }
        this.var_gg_vape_mapping_MappingMethod_f.invokeVoid(object, n, n2, bl);
    }

    public static int L(MFramebuffer mFramebuffer, Object object) {
        return mFramebuffer.r(object);
    }

    public MFramebuffer() {
        this(MShaderGroup.V());
    }

    private MFramebuffer(String[] stringArray) {
        super(MappedClasses.ll);
        String string;
        boolean bl;
        Class<Void> clazz;
        Class[] classArray;
        String[] stringArray2 = stringArray;
        if (ForgeVersion.MC_1_21_4.d()) {
            this.var_gg_vape_mapping_MappingMethod_S = null;
            this.var_gg_vape_mapping_MappingMethod_V = null;
            this.var_gg_vape_mapping_MappingMethod_f = null;
            this.var_gg_vape_mapping_MappingMethod_r = null;
            this.var_gg_vape_mapping_MappingMethod_P = null;
            this.var_gg_vape_mapping_MappingMethod_F = null;
            this.var_gg_vape_mapping_MappingMethod_W = null;
            this.var_gg_vape_mapping_MappingMethod_H = null;
        } else if (ForgeVersion.MC_1_16_5.d()) {
            MFramebuffer mFramebuffer;
            if (ForgeVersion.MC_1_17.d()) {
                classArray = new Class[]{Integer.TYPE, Integer.TYPE};
                clazz = Void.TYPE;
                bl = false;
                string = "<init>";
                Class clazz2 = MappedClasses.zA;
                mFramebuffer = this;
                this.var_gg_vape_mapping_MappingMethod_S = mFramebuffer.registerInstanceMethodForOwner(clazz2, string, bl, clazz, classArray);
            } else {
                classArray = new Class[]{Integer.TYPE, Integer.TYPE, Boolean.TYPE, Boolean.TYPE};
                clazz = Void.TYPE;
                bl = false;
                string = "<init>";
                MFramebuffer mFramebuffer2 = this;
                this.var_gg_vape_mapping_MappingMethod_S = mFramebuffer2.Y(string, bl, clazz, classArray);
            }
            classArray = new Class[]{Integer.TYPE, Integer.TYPE};
            clazz = Void.TYPE;
            bl = true;
            string = "createBindFramebuffers";
            Class clazz3 = MappedClasses.Fo;
            mFramebuffer = this;
            this.var_gg_vape_mapping_MappingMethod_V = mFramebuffer.registerInstanceMethodForOwner(clazz3, string, bl, clazz, classArray);
            Class[] classArray2 = new Class[]{Integer.TYPE, Integer.TYPE, Boolean.TYPE};
            Class<Void> clazz4 = Void.TYPE;
            boolean bl2 = true;
            String string2 = "createBuffers";
            MFramebuffer mFramebuffer2 = this;
            this.var_gg_vape_mapping_MappingMethod_f = this.Y(string2, bl2, clazz4, classArray2);
            Class[] classArray3 = new Class[]{Boolean.TYPE};
            Class<Void> clazz5 = Void.TYPE;
            boolean bl3 = true;
            String string3 = "bindFramebuffer";
            MFramebuffer mFramebuffer3 = this;
            this.var_gg_vape_mapping_MappingMethod_r = this.Y(string3, bl3, clazz5, classArray3);
            Class[] classArray4 = new Class[]{};
            Class<Void> clazz6 = Void.TYPE;
            boolean bl4 = true;
            String string4 = "unbindFramebuffer";
            MFramebuffer mFramebuffer4 = this;
            this.var_gg_vape_mapping_MappingMethod_P = this.Y(string4, bl4, clazz6, classArray4);
            Class[] classArray5 = new Class[]{};
            Class<Void> clazz7 = Void.TYPE;
            boolean bl5 = true;
            String string5 = "bindFramebufferTexture";
            MFramebuffer mFramebuffer5 = this;
            this.var_gg_vape_mapping_MappingMethod_F = this.Y(string5, bl5, clazz7, classArray5);
            Class[] classArray6 = new Class[]{};
            Class<Void> clazz8 = Void.TYPE;
            boolean bl6 = true;
            String string6 = "unbindFramebufferTexture";
            MFramebuffer mFramebuffer6 = this;
            this.var_gg_vape_mapping_MappingMethod_W = this.Y(string6, bl6, clazz8, classArray6);
            Class[] classArray7 = new Class[]{};
            Class<Void> clazz9 = Void.TYPE;
            boolean bl7 = true;
            String string7 = "deleteFramebuffer";
            MFramebuffer mFramebuffer7 = this;
            this.var_gg_vape_mapping_MappingMethod_H = this.Y(string7, bl7, clazz9, classArray7);
        } else {
            classArray = new Class[]{Integer.TYPE, Integer.TYPE, Boolean.TYPE};
            clazz = Void.TYPE;
            bl = false;
            string = "<init>";
            MFramebuffer mFramebuffer = this;
            this.var_gg_vape_mapping_MappingMethod_S = mFramebuffer.Y(string, bl, clazz, classArray);
            Class[] classArray8 = new Class[]{Integer.TYPE, Integer.TYPE};
            Class<Void> clazz10 = Void.TYPE;
            boolean bl8 = true;
            String string8 = "createBindFramebuffer";
            MFramebuffer mFramebuffer8 = this;
            this.var_gg_vape_mapping_MappingMethod_V = this.Y(string8, bl8, clazz10, classArray8);
            Class[] classArray9 = new Class[]{Integer.TYPE, Integer.TYPE};
            Class<Void> clazz11 = Void.TYPE;
            boolean bl9 = true;
            String string9 = "createFramebuffer";
            MFramebuffer mFramebuffer9 = this;
            this.var_gg_vape_mapping_MappingMethod_f = this.Y(string9, bl9, clazz11, classArray9);
            Class[] classArray10 = new Class[]{Boolean.TYPE};
            Class<Void> clazz12 = Void.TYPE;
            boolean bl10 = true;
            String string10 = "bindFramebuffer";
            MFramebuffer mFramebuffer10 = this;
            this.var_gg_vape_mapping_MappingMethod_r = this.Y(string10, bl10, clazz12, classArray10);
            Class[] classArray11 = new Class[]{};
            Class<Void> clazz13 = Void.TYPE;
            boolean bl11 = true;
            String string11 = "unbindFramebuffer";
            MFramebuffer mFramebuffer11 = this;
            this.var_gg_vape_mapping_MappingMethod_P = this.Y(string11, bl11, clazz13, classArray11);
            Class[] classArray12 = new Class[]{};
            Class<Void> clazz14 = Void.TYPE;
            boolean bl12 = true;
            String string12 = "bindFramebufferTexture";
            MFramebuffer mFramebuffer12 = this;
            this.var_gg_vape_mapping_MappingMethod_F = this.Y(string12, bl12, clazz14, classArray12);
            Class[] classArray13 = new Class[]{};
            Class<Void> clazz15 = Void.TYPE;
            boolean bl13 = true;
            String string13 = "unbindFramebufferTexture";
            MFramebuffer mFramebuffer13 = this;
            this.var_gg_vape_mapping_MappingMethod_W = this.Y(string13, bl13, clazz15, classArray13);
            Class[] classArray14 = new Class[]{};
            Class<Void> clazz16 = Void.TYPE;
            boolean bl14 = true;
            String string14 = "deleteFramebuffer";
            MFramebuffer mFramebuffer14 = this;
            this.var_gg_vape_mapping_MappingMethod_H = this.Y(string14, bl14, clazz16, classArray14);
        }
        if (ForgeVersion.MC_1_21_6.v()) {
            Class<Integer> clazz2 = Integer.TYPE;
            boolean bl2 = true;
            String string2 = "depthBuffer";
            MFramebuffer mFramebuffer = this;
            this.var_gg_vape_mapping_MappingField_l = mFramebuffer.J(string2, bl2, clazz2);
        }
    }

    private void E(Object object, int n, int n2) {
        if (this.var_gg_vape_mapping_MappingMethod_f == null) {
            return;
        }
        this.var_gg_vape_mapping_MappingMethod_f.invokeVoid(object, n, n2);
    }

    public static Object create(MFramebuffer mFramebuffer, int n, int n2, boolean bl) {
        return mFramebuffer.create(n, n2, bl);
    }

    private void x(Object object) {
        if (this.var_gg_vape_mapping_MappingMethod_W == null) {
            return;
        }
        this.var_gg_vape_mapping_MappingMethod_W.invokeVoidNoArgs(object);
    }

    private Object create(int n, int n2, boolean bl) {
        if (this.var_gg_vape_mapping_MappingMethod_S == null) {
            return null;
        }
        if (ForgeVersion.MC_1_17.d()) {
            return this.var_gg_vape_mapping_MappingMethod_S.newInstance(n, n2);
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.var_gg_vape_mapping_MappingMethod_S.newInstance(n, n2, bl, false);
        }
        return this.var_gg_vape_mapping_MappingMethod_S.newInstance(n, n2, bl);
    }

    private int r(Object object) {
        return this.var_gg_vape_mapping_MappingField_l.getInt(object);
    }

    private void A(Object object) {
        if (this.var_gg_vape_mapping_MappingMethod_F == null) {
            return;
        }
        this.var_gg_vape_mapping_MappingMethod_F.invokeVoidNoArgs(object);
    }

    private void f(Object object, int n, int n2) {
        if (this.var_gg_vape_mapping_MappingMethod_V == null) {
            return;
        }
        this.var_gg_vape_mapping_MappingMethod_V.invokeVoid(object, n, n2);
    }

    public static void w(MFramebuffer mFramebuffer, Object object, int n, int n2, boolean bl) {
        mFramebuffer.k(object, n, n2, bl);
    }

    public static void depthBuffer(MFramebuffer mFramebuffer, Object object, int n) {
        mFramebuffer.depthBuffer(object, n);
    }

    private void I(Object object) {
        if (this.var_gg_vape_mapping_MappingMethod_H == null) {
            return;
        }
        this.var_gg_vape_mapping_MappingMethod_H.invokeVoidNoArgs(object);
    }

    public static void f(MFramebuffer mFramebuffer, Object object, int n, int n2) {
        mFramebuffer.f(object, n, n2);
    }

    private void depthBuffer(Object object, int n) {
        this.var_gg_vape_mapping_MappingField_l.setInt(object, n);
    }
}

