/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MPlayerControllerMP
extends Mapping {
    public final MappingMethod r;
    public final MappingMethod c;
    public final MappingMethod i;
    private final MappingField l;
    public final MappingMethod I;
    public final MappingMethod K;
    private final MappingField V;
    private static int y;
    public final MappingMethod O;
    private final MappingField A;
    public final MappingMethod f;
    public final MappingMethod L;

    public Object S(Object object, int n, int n2, int n3, int n4, Object object2) {
        return this.I.invokeObject(object, n, n2, n3, n4, object2);
    }

    public static void p(int n) {
        y = n;
    }

    private Object S(Object object) {
        return this.V.getObject(object);
    }

    public boolean U(Object object, Object object2, Object object3, Object object4, int n, int n2, int n3, int n4, Object object5) {
        return this.r.invokeBoolean(object, object2, object3, object4, n, n2, n3, n4, object5);
    }

    private void U(Object object) {
        this.f.invokeVoidNoArgs(object);
    }

    private float X(Object object) {
        return this.A.getFloat(object);
    }

    private boolean h(Object object) {
        return this.l.getBoolean(object);
    }

    public float z(Object object) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return (float)this.K.invokeDouble(object, new Object[0]);
        }
        return this.K.invokeFloat(object, new Object[0]);
    }

    public void o(Object object) {
        this.L.invokeVoidNoArgs(object);
    }

    public Object H(Object object, Object object2, Object object3, Object object4) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return this.c.invokeObject(object, object2, object4);
        }
        return this.c.invokeObject(object, object2, object3, object4);
    }

    public Object N(Object object, int n, int n2, int n3, Object object2, Object object3) {
        if (ForgeVersion.MC_1_17.d()) {
            this.I.invokeVoid(object, n, n2, n3, object2, object3);
            return null;
        }
        return this.I.invokeObject(object, n, n2, n3, object2, object3);
    }

    public static void v(MPlayerControllerMP mPlayerControllerMP, Object object) {
        mPlayerControllerMP.U(object);
    }

    public static Object w(MPlayerControllerMP mPlayerControllerMP, Object object) {
        return mPlayerControllerMP.S(object);
    }

    public MPlayerControllerMP() {
        this(MPlayerControllerMP.V());
    }

    private MPlayerControllerMP(int n) {
        super(MappedClasses.ld);
        Class[] classArray;
        MPlayerControllerMP mPlayerControllerMP;
        String string;
        boolean bl;
        Class<Void> clazz;
        Class[] classArray2;
        Class[] classArray3;
        MPlayerControllerMP mPlayerControllerMP2;
        String string2;
        boolean bl2;
        Class<Boolean> clazz2;
        int n2 = n;
        if (ForgeVersion.MC_1_12_2.d()) {
            clazz2 = MappedClasses.F1;
            bl2 = true;
            string2 = "connection";
            mPlayerControllerMP2 = this;
            this.V = mPlayerControllerMP2.J(string2, bl2, clazz2);
            if (ForgeVersion.MC_1_17.d()) {
                classArray3 = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, MappedClasses.V_, MappedClasses.Yl};
                Class<Void> clazz22 = Void.TYPE;
                boolean bl22 = true;
                String string22 = ForgeVersion.MC_26_1.d() ? "handleContainerInput" : "handleInventoryMouseClick";
                MPlayerControllerMP mPlayerControllerMP22 = this;
                this.I = this.Y(string22, bl22, clazz22, classArray3);
            } else {
                classArray3 = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, MappedClasses.V_, MappedClasses.Yl};
                Class clazz3 = MappedClasses.VK;
                boolean bl3 = true;
                String string3 = "windowClick";
                MPlayerControllerMP mPlayerControllerMP3 = this;
                this.I = this.Y(string3, bl3, clazz3, classArray3);
            }
            classArray3 = new Class[]{MappedClasses.Yl, MappedClasses.YU, MappedClasses.Yf};
            Class clazz4 = MappedClasses.zr;
            boolean bl4 = true;
            String string4 = "processRightClick";
            MPlayerControllerMP mPlayerControllerMP4 = this;
            this.c = this.Y(string4, bl4, clazz4, classArray3);
        } else {
            clazz2 = MappedClasses.F1;
            bl2 = true;
            string2 = "netClientHandler";
            mPlayerControllerMP2 = this;
            this.V = mPlayerControllerMP2.J(string2, bl2, clazz2);
            classArray3 = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, MappedClasses.Yl};
            Class clazz5 = MappedClasses.VK;
            boolean bl5 = true;
            String string5 = "windowClick";
            MPlayerControllerMP mPlayerControllerMP5 = this;
            this.I = this.Y(string5, bl5, clazz5, classArray3);
            Class[] classArray22 = new Class[]{MappedClasses.Yl, MappedClasses.YU, MappedClasses.VK};
            Class<Boolean> clazz6 = Boolean.TYPE;
            boolean bl6 = true;
            String string6 = "sendUseItem";
            MPlayerControllerMP mPlayerControllerMP6 = this;
            this.c = this.Y(string6, bl6, clazz6, classArray22);
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            classArray2 = new Class[]{};
            clazz = Void.TYPE;
            bl = true;
            string = "tick";
            mPlayerControllerMP = this;
            this.L = mPlayerControllerMP.Y(string, bl, clazz, classArray2);
        } else {
            classArray2 = new Class[]{};
            clazz = Void.TYPE;
            bl = true;
            string = "updateController";
            mPlayerControllerMP = this;
            this.L = mPlayerControllerMP.Y(string, bl, clazz, classArray2);
        }
        clazz2 = Boolean.TYPE;
        boolean bl3 = true;
        String string3 = "isHittingBlock";
        mPlayerControllerMP2 = this;
        this.l = mPlayerControllerMP2.J(string3, bl3, clazz2);
        Class<Float> clazz7 = Float.TYPE;
        boolean bl7 = true;
        String string7 = "curBlockDamageMP";
        MPlayerControllerMP mPlayerControllerMP7 = this;
        this.A = this.J(string7, bl7, clazz7);
        if (ForgeVersion.MC_1_20_6.d()) {
            classArray = new Class[]{};
            Class<Double> clazz8 = Double.TYPE;
            boolean bl8 = true;
            String string8 = "blockInteractionRange";
            Class clazz9 = MappedClasses.Yl;
            MPlayerControllerMP mPlayerControllerMP8 = this;
            this.K = this.registerInstanceMethodForOwner(clazz9, string8, bl8, clazz8, classArray);
        } else {
            classArray = new Class[]{};
            Class<Float> clazz10 = Float.TYPE;
            boolean bl9 = true;
            String string9 = "getBlockReachDistance";
            MPlayerControllerMP mPlayerControllerMP9 = this;
            this.K = this.Y(string9, bl9, clazz10, classArray);
        }
        classArray = new Class[]{MappedClasses.Yl, MappedClasses.zc};
        Class<Void> clazz11 = Void.TYPE;
        boolean bl10 = true;
        String string10 = "attackEntity";
        MPlayerControllerMP mPlayerControllerMP10 = this;
        this.i = this.Y(string10, bl10, clazz11, classArray);
        Class[] classArray32 = new Class[]{};
        Class<Void> clazz12 = Void.TYPE;
        boolean bl11 = true;
        String string11 = "syncCurrentPlayItem";
        MPlayerControllerMP mPlayerControllerMP11 = this;
        this.f = this.Y(string11, bl11, clazz12, classArray32);
        Class[] classArray4 = new Class[]{MappedClasses.Yl};
        Class<Void> clazz13 = Void.TYPE;
        boolean bl12 = true;
        String string12 = "onStoppedUsingItem";
        MPlayerControllerMP mPlayerControllerMP12 = this;
        this.O = this.Y(string12, bl12, clazz13, classArray4);
        if (ForgeVersion.MC_1_7_10.Y()) {
            if (ForgeVersion.MC_1_12_2.d()) {
                if (ForgeVersion.MC_1_16_5.d()) {
                    if (ForgeVersion.MC_1_17.d()) {
                        if (ForgeVersion.MC_1_20_6.d()) {
                            Class[] classArray5 = new Class[]{MappedClasses.z5, MappedClasses.Yf, MappedClasses.qF};
                            Class clazz14 = MappedClasses.zr;
                            boolean bl13 = true;
                            String string13 = "useItemOn";
                            MPlayerControllerMP mPlayerControllerMP13 = this;
                            this.r = this.Y(string13, bl13, clazz14, classArray5);
                        } else {
                            Class[] classArray6 = new Class[]{MappedClasses.z5, MappedClasses.Z, MappedClasses.Yf, MappedClasses.qF};
                            Class clazz15 = MappedClasses.zr;
                            boolean bl14 = true;
                            String string14 = "useItemOn";
                            MPlayerControllerMP mPlayerControllerMP14 = this;
                            this.r = this.Y(string14, bl14, clazz15, classArray6);
                        }
                    } else {
                        Class[] classArray7 = new Class[]{MappedClasses.z5, MappedClasses.Z, MappedClasses.Yf, MappedClasses.qF};
                        Class clazz16 = MappedClasses.zr;
                        boolean bl15 = Wrapper.isNativeAvailable;
                        String string15 = "func_217292_a";
                        MPlayerControllerMP mPlayerControllerMP15 = this;
                        this.r = this.Y(string15, bl15, clazz16, classArray7);
                    }
                } else {
                    Class[] classArray8 = new Class[]{MappedClasses.z5, MappedClasses.Z, MappedClasses.lf, MappedClasses.q0, MappedClasses.qP, MappedClasses.Yf};
                    Class clazz17 = MappedClasses.zr;
                    boolean bl16 = true;
                    String string16 = "processRightClickBlock";
                    MPlayerControllerMP mPlayerControllerMP16 = this;
                    this.r = this.Y(string16, bl16, clazz17, classArray8);
                }
            } else {
                Class[] classArray9 = new Class[]{MappedClasses.z5, MappedClasses.Z, MappedClasses.VK, MappedClasses.lf, MappedClasses.q0, MappedClasses.qP};
                Class<Boolean> clazz18 = Boolean.TYPE;
                boolean bl17 = true;
                String string17 = "onPlayerRightClick";
                MPlayerControllerMP mPlayerControllerMP17 = this;
                this.r = this.Y(string17, bl17, clazz18, classArray9);
            }
        } else {
            Class[] classArray10 = new Class[]{MappedClasses.Yl, MappedClasses.YU, MappedClasses.VK, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, MappedClasses.qP};
            Class<Boolean> clazz19 = Boolean.TYPE;
            boolean bl18 = true;
            String string18 = "onPlayerRightClick";
            MPlayerControllerMP mPlayerControllerMP18 = this;
            this.r = this.Y(string18, bl18, clazz19, classArray10);
        }
    }

    public Object e(Object object, Object object2, Object object3, Object object4, Object object5) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return this.r.invokeObject(object, object2, object4, object5);
        }
        return this.r.invokeObject(object, object2, object3, object4, object5);
    }

    public static boolean K(MPlayerControllerMP mPlayerControllerMP, Object object) {
        return mPlayerControllerMP.h(object);
    }

    public boolean Q(Object object, Object object2, Object object3, Object object4) {
        return this.c.invokeBoolean(object, object2, object3, object4);
    }

    public static void attackEntity(MPlayerControllerMP mPlayerControllerMP, Object object, Object object2, Object object3) {
        mPlayerControllerMP.q(object, object2, object3);
    }

    public void onStoppedUsingItem(Object object, Object object2) {
        this.O.invokeVoid(object, object2);
    }

    public static int d() {
        return y;
    }

    public Object m(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        return this.r.invokeObject(object, object2, object3, object4, object5, object6, object7);
    }

    public static int V() {
        int n = MPlayerControllerMP.d();
        if (n == 0) {
            return 118;
        }
        return 0;
    }

    private void q(Object object, Object object2, Object object3) {
        this.i.invokeVoid(object, object2, object3);
    }

    public static float l(MPlayerControllerMP mPlayerControllerMP, Object object) {
        return mPlayerControllerMP.X(object);
    }

    public boolean J(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        return this.r.invokeBoolean(object, object2, object3, object4, object5, object6, object7);
    }

    static {
        MPlayerControllerMP.p(0);
    }
}

