/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ScaledResolution;

public class MFontRenderer
extends Mapping {
    private MappingMethod var_gg_vape_mapping_MappingMethod_X;
    private MappingMethod var_gg_vape_mapping_MappingMethod_L;
    final public MappingMethod var_gg_vape_mapping_MappingMethod_T;
    private MappingMethod var_gg_vape_mapping_MappingMethod_i;
    private MappingMethod var_gg_vape_mapping_MappingMethod_b;
    private MappingMethod var_gg_vape_mapping_MappingMethod_c;
    private MappingMethod var_gg_vape_mapping_MappingMethod_l;
    private MappingMethod var_gg_vape_mapping_MappingMethod_m;
    public MappingMethod var_gg_vape_mapping_MappingMethod_h;
    private MappingMethod var_gg_vape_mapping_MappingMethod_P;
    private MappingMethod var_gg_vape_mapping_MappingMethod_W;
    private MappingMethod var_gg_vape_mapping_MappingMethod_g;

    private int D(Object object, String string, float f, float f2, int n, boolean bl, Object object2, Object object3, Object object4, int n2, int n3) {
        if (this.var_gg_vape_mapping_MappingMethod_P == null) {
            return 0;
        }
        if (ForgeVersion.MC_1_21_6.d()) {
            this.var_gg_vape_mapping_MappingMethod_P.invokeVoid(object, string, Float.valueOf(f), Float.valueOf(f2), n, bl, object2, object3, object4, n2, n3);
            return 0;
        }
        return this.var_gg_vape_mapping_MappingMethod_P.invokeInt(object, string, Float.valueOf(f), Float.valueOf(f2), n, bl, object2, object3, object4, n2, n3);
    }

    public int C(Object object, Object object2, float f, float f2, int n, boolean bl, Object object3, Object object4, boolean bl2, int n2, int n3) {
        return this.var_gg_vape_mapping_MappingMethod_W.invokeInt(object, object2, Float.valueOf(f), Float.valueOf(f2), n, bl, object3, object4, bl2, n2, n3);
    }

    public static int i(MFontRenderer mFontRenderer, Object object, String string, float f, float f2, int n, boolean bl, Object object2, Object object3, Object object4, int n2, int n3) {
        return mFontRenderer.D(object, string, f, f2, n, bl, object2, object3, object4, n2, n3);
    }

    private int b(Object object, Object object2, Object object3, float f, float f2, int n) {
        return this.var_gg_vape_mapping_MappingMethod_b.invokeInt(object, object2, object3, Float.valueOf(f), Float.valueOf(f2), n);
    }

    public int S(Object object, Object object2, String string, float f, float f2, int n) {
        return this.var_gg_vape_mapping_MappingMethod_h.invokeInt(object, object2, string, Float.valueOf(f), Float.valueOf(f2), n);
    }

    public int u(Object object, String string, int n, int n2, int n3) {
        return this.var_gg_vape_mapping_MappingMethod_h.invokeInt(object, string, n, n2, n3);
    }

    public int m(Object object) {
        return 10;
    }

    public int Y(Object object, String string, float f, float f2, int n, boolean bl) {
        if (ForgeVersion.MC_1_7_10.L()) {
            return this.var_gg_vape_mapping_MappingMethod_L.invokeInt(object, string, (int)f, (int)f2, n, bl);
        }
        return this.var_gg_vape_mapping_MappingMethod_L.invokeInt(object, string, Float.valueOf(f), Float.valueOf(f2), n, bl);
    }

    public int drawStringWithShadow(Object object, String string, float f, float f2, int n) {
        return this.var_gg_vape_mapping_MappingMethod_l.invokeInt(object, string, Float.valueOf(f), Float.valueOf(f2), n);
    }

    public MFontRenderer() {
        this(ScaledResolution.q());
    }

    private MFontRenderer(int n) {
        super(MappedClasses.uQ);
        int n2 = n;
        if (ForgeVersion.MC_1_20_6.d()) {
            Class[] classArray = new Class[]{String.class};
            Class<Integer> clazz = Integer.TYPE;
            boolean bl = true;
            String string = "width";
            MFontRenderer mFontRenderer = this;
            this.var_gg_vape_mapping_MappingMethod_T = mFontRenderer.Y(string, bl, clazz, classArray);
            if (ForgeVersion.MC_26_1.d()) {
                if (ForgeVersion.MC_26_2.v()) {
                    Class[] classArray2 = new Class[]{String.class, Float.TYPE, Float.TYPE, Integer.TYPE, Boolean.TYPE, MappedClasses.ZA, MappedClasses.ZK, MappedClasses.Y8, Integer.TYPE, Integer.TYPE};
                    Class<Void> clazz2 = Void.TYPE;
                    boolean bl2 = true;
                    String string2 = "drawInBatch";
                    MFontRenderer mFontRenderer2 = this;
                    this.var_gg_vape_mapping_MappingMethod_P = this.Y(string2, bl2, clazz2, classArray2);
                }
                Class[] classArray3 = new Class[]{MappedClasses.uQ, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE};
                Class<Void> clazz3 = Void.TYPE;
                boolean bl3 = true;
                String string3 = "text";
                Class clazz4 = MappedClasses.var_java_lang_Class_m;
                MFontRenderer mFontRenderer3 = this;
                this.var_gg_vape_mapping_MappingMethod_m = this.registerInstanceMethodForOwner(clazz4, string3, bl3, clazz3, classArray3);
            } else {
                Class[] classArray4 = new Class[]{String.class, Float.TYPE, Float.TYPE, Integer.TYPE, Boolean.TYPE, MappedClasses.qr, MappedClasses.ZK, MappedClasses.Y8, Integer.TYPE, Integer.TYPE};
                Class<Integer> clazz5 = Integer.TYPE;
                boolean bl4 = true;
                String string4 = "drawInBatch";
                MFontRenderer mFontRenderer4 = this;
                this.var_gg_vape_mapping_MappingMethod_P = this.Y(string4, bl4, clazz5, classArray4);
                Class[] classArray5 = new Class[]{MappedClasses.uQ, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE};
                Class<Integer> clazz6 = Integer.TYPE;
                boolean bl5 = true;
                String string5 = "drawString";
                Class clazz7 = MappedClasses.var_java_lang_Class_m;
                MFontRenderer mFontRenderer5 = this;
                this.var_gg_vape_mapping_MappingMethod_m = this.registerInstanceMethodForOwner(clazz7, string5, bl5, clazz6, classArray5);
            }
        } else {
            MFontRenderer mFontRenderer;
            String string;
            boolean bl;
            Class<Integer> clazz;
            Class[] classArray;
            if (ForgeVersion.MC_1_7_10.L()) {
                classArray = new Class[]{String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE};
                clazz = Integer.TYPE;
                bl = true;
                string = "drawStringWithShadow";
                mFontRenderer = this;
                this.var_gg_vape_mapping_MappingMethod_c = mFontRenderer.Y(string, bl, clazz, classArray);
                Class[] classArray6 = new Class[]{String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE};
                Class<Integer> clazz8 = Integer.TYPE;
                boolean bl6 = true;
                String string6 = "drawString";
                MFontRenderer mFontRenderer6 = this;
                this.var_gg_vape_mapping_MappingMethod_h = this.Y(string6, bl6, clazz8, classArray6);
                Class[] classArray7 = new Class[]{String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE};
                Class<Integer> clazz9 = Integer.TYPE;
                boolean bl7 = true;
                String string7 = "renderString";
                MFontRenderer mFontRenderer7 = this;
                this.var_gg_vape_mapping_MappingMethod_L = this.Y(string7, bl7, clazz9, classArray7);
            } else if (ForgeVersion.MC_1_16_5.d()) {
                classArray = new Class[]{MappedClasses.DQ, MappedClasses.Yr, Float.TYPE, Float.TYPE, Integer.TYPE};
                clazz = Integer.TYPE;
                bl = Wrapper.isNativeAvailable;
                string = "func_243246_a";
                mFontRenderer = this;
                this.var_gg_vape_mapping_MappingMethod_b = mFontRenderer.Y(string, bl, clazz, classArray);
                Class[] classArray8 = new Class[]{MappedClasses.DQ, MappedClasses.Yr, Float.TYPE, Float.TYPE, Integer.TYPE};
                Class<Integer> clazz10 = Integer.TYPE;
                boolean bl8 = Wrapper.isNativeAvailable;
                String string8 = "func_243248_b";
                MFontRenderer mFontRenderer8 = this;
                this.var_gg_vape_mapping_MappingMethod_X = this.Y(string8, bl8, clazz10, classArray8);
                if (ForgeVersion.MC_1_17.d()) {
                    Class[] classArray9 = new Class[]{MappedClasses.DQ, String.class, Float.TYPE, Float.TYPE, Integer.TYPE};
                    Class<Integer> clazz11 = Integer.TYPE;
                    boolean bl9 = false;
                    String string9 = "m_92750_";
                    MFontRenderer mFontRenderer9 = this;
                    this.var_gg_vape_mapping_MappingMethod_g = this.Y(string9, bl9, clazz11, classArray9);
                } else {
                    Class[] classArray10 = new Class[]{MappedClasses.DQ, String.class, Float.TYPE, Float.TYPE, Integer.TYPE};
                    Class<Integer> clazz12 = Integer.TYPE;
                    boolean bl10 = Wrapper.isNativeAvailable;
                    String string10 = "func_238405_a_";
                    MFontRenderer mFontRenderer10 = this;
                    this.var_gg_vape_mapping_MappingMethod_g = this.Y(string10, bl10, clazz12, classArray10);
                }
                Class[] classArray11 = new Class[]{MappedClasses.DQ, String.class, Float.TYPE, Float.TYPE, Integer.TYPE};
                Class<Integer> clazz13 = Integer.TYPE;
                boolean bl11 = Wrapper.isNativeAvailable;
                String string11 = "func_238421_b_";
                MFontRenderer mFontRenderer11 = this;
                this.var_gg_vape_mapping_MappingMethod_h = this.Y(string11, bl11, clazz13, classArray11);
                Class[] classArray12 = new Class[]{MappedClasses.Yr, Float.TYPE, Float.TYPE, Integer.TYPE, Boolean.TYPE, MappedClasses.qr, MappedClasses.ZK, Boolean.TYPE, Integer.TYPE, Integer.TYPE};
                Class<Integer> clazz14 = Integer.TYPE;
                boolean bl12 = Wrapper.isNativeAvailable;
                String string12 = "func_243247_a";
                MFontRenderer mFontRenderer12 = this;
                this.var_gg_vape_mapping_MappingMethod_W = this.Y(string12, bl12, clazz14, classArray12);
            } else {
                classArray = new Class[]{String.class, Float.TYPE, Float.TYPE, Integer.TYPE, Boolean.TYPE};
                clazz = Integer.TYPE;
                bl = true;
                string = "renderString";
                mFontRenderer = this;
                this.var_gg_vape_mapping_MappingMethod_L = mFontRenderer.Y(string, bl, clazz, classArray);
                Class[] classArray13 = new Class[]{String.class, Float.TYPE, Float.TYPE, Integer.TYPE};
                Class<Integer> clazz15 = Integer.TYPE;
                boolean bl13 = true;
                String string13 = "drawStringWithShadow";
                MFontRenderer mFontRenderer13 = this;
                this.var_gg_vape_mapping_MappingMethod_l = this.Y(string13, bl13, clazz15, classArray13);
                Class[] classArray14 = new Class[]{};
                Class<Void> clazz16 = Void.TYPE;
                boolean bl14 = true;
                String string14 = "resetStyles";
                MFontRenderer mFontRenderer14 = this;
                this.var_gg_vape_mapping_MappingMethod_i = this.Y(string14, bl14, clazz16, classArray14);
                if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
                    Class[] classArray15 = new Class[]{String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE};
                    Class<Integer> clazz17 = Integer.TYPE;
                    boolean bl15 = true;
                    String string15 = "drawString";
                    MFontRenderer mFontRenderer15 = this;
                    this.var_gg_vape_mapping_MappingMethod_h = this.Y(string15, bl15, clazz17, classArray15);
                } else {
                    Class[] classArray16 = new Class[]{String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE};
                    Class<Integer> clazz18 = Integer.TYPE;
                    boolean bl16 = Wrapper.isNativeAvailable;
                    String string16 = "func_78276_b";
                    MFontRenderer mFontRenderer16 = this;
                    this.var_gg_vape_mapping_MappingMethod_h = this.Y(string16, bl16, clazz18, classArray16);
                }
            }
            if (ForgeVersion.MC_1_17.d()) {
                classArray = new Class[]{String.class};
                clazz = Integer.TYPE;
                bl = false;
                string = "m_92895_";
                mFontRenderer = this;
                this.var_gg_vape_mapping_MappingMethod_T = mFontRenderer.Y(string, bl, clazz, classArray);
            } else if (ForgeVersion.MC_1_16_5.d()) {
                classArray = new Class[]{String.class};
                clazz = Integer.TYPE;
                bl = Wrapper.isNativeAvailable;
                string = "func_78256_a";
                mFontRenderer = this;
                this.var_gg_vape_mapping_MappingMethod_T = mFontRenderer.Y(string, bl, clazz, classArray);
            } else {
                classArray = new Class[]{String.class};
                clazz = Integer.TYPE;
                bl = true;
                string = "getStringWidth";
                mFontRenderer = this;
                this.var_gg_vape_mapping_MappingMethod_T = mFontRenderer.Y(string, bl, clazz, classArray);
            }
        }
    }

    public int e(Object object, String string) {
        return this.var_gg_vape_mapping_MappingMethod_T.invokeInt(object, string);
    }

    public int C(Object object, Object object2, String string, int n, int n2, int n3, boolean bl) {
        if (ForgeVersion.MC_1_21_6.d()) {
            this.var_gg_vape_mapping_MappingMethod_m.invokeVoid(object, object2, string, n, n2, n3, bl);
            return 0;
        }
        return this.var_gg_vape_mapping_MappingMethod_m.invokeInt(object, object2, string, n, n2, n3, bl);
    }

    public static int A(MFontRenderer mFontRenderer, Object object, Object object2, Object object3, float f, float f2, int n) {
        return mFontRenderer.b(object, object2, object3, f, f2, n);
    }

    private int A(Object object, Object object2, Object object3, float f, float f2, int n) {
        return this.var_gg_vape_mapping_MappingMethod_X.invokeInt(object, object2, object3, Float.valueOf(f), Float.valueOf(f2), n);
    }

    public int M(Object object, Object object2, String string, float f, float f2, int n) {
        return this.var_gg_vape_mapping_MappingMethod_g.invokeInt(object, object2, string, Float.valueOf(f), Float.valueOf(f2), n);
    }

    public static int q(MFontRenderer mFontRenderer, Object object, Object object2, Object object3, float f, float f2, int n) {
        return mFontRenderer.A(object, object2, object3, f, f2, n);
    }

    public int d(Object object, String string, int n, int n2, int n3) {
        return this.var_gg_vape_mapping_MappingMethod_c.invokeInt(object, string, n, n2, n3);
    }

    public void J(Object object) {
        this.var_gg_vape_mapping_MappingMethod_i.invokeVoidNoArgs(object);
    }
}

