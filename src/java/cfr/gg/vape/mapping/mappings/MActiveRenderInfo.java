/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MEntityRenderer;
import gg.vape.wrapper.impl.ForgeVersion;

public class MActiveRenderInfo
extends Mapping {
    public MappingField s;
    public MappingMethod g;
    public MappingMethod A;
    public MappingField a;
    public MappingField V;
    public MappingField f;
    public MappingField z;
    public MappingField j;

    public static Object p(MActiveRenderInfo mActiveRenderInfo, Object object) {
        return mActiveRenderInfo.n(object);
    }

    private Object I(Object object) {
        return this.s.getObject(object);
    }

    private Object T(Object object) {
        return this.V.getObject(object);
    }

    private Object U(Object object) {
        return this.z.getObject(object);
    }

    public static void P(MActiveRenderInfo mActiveRenderInfo, Object object, Object object2) {
        mActiveRenderInfo.W(object, object2);
    }

    private float G(Object object) {
        return this.a.getFloat(object);
    }

    private float F(Object object) {
        return this.j.getFloat(object);
    }

    private void W(Object object, Object object2) {
        this.g.invokeVoid(object, object2);
    }

    public static float A(MActiveRenderInfo mActiveRenderInfo, Object object) {
        return mActiveRenderInfo.F(object);
    }

    public static Object Z(MActiveRenderInfo mActiveRenderInfo, Object object) {
        return mActiveRenderInfo.U(object);
    }

    public static Object i(MActiveRenderInfo mActiveRenderInfo, Object object) {
        return mActiveRenderInfo.I(object);
    }

    public MActiveRenderInfo() {
        this(MEntityRenderer.n());
    }

    private MActiveRenderInfo(int n) {
        super(MappedClasses.lt);
        MActiveRenderInfo mActiveRenderInfo;
        String string;
        Class[] classArray;
        MActiveRenderInfo mActiveRenderInfo2;
        String string2;
        boolean bl;
        Class<Float> clazz;
        int n2 = n;
        if (ForgeVersion.MC_1_21_6.d()) {
            clazz = MappedClasses.qI;
            bl = true;
            string2 = "rotation";
            mActiveRenderInfo2 = this;
            this.V = mActiveRenderInfo2.J(string2, bl, clazz);
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            clazz = Float.TYPE;
            bl = true;
            string2 = "pitch";
            mActiveRenderInfo2 = this;
            this.a = mActiveRenderInfo2.J(string2, bl, clazz);
            Class<Float> clazz2 = Float.TYPE;
            boolean bl2 = true;
            String string22 = "yaw";
            MActiveRenderInfo mActiveRenderInfo22 = this;
            this.j = this.J(string22, bl2, clazz2);
            Class clazz3 = MappedClasses.qP;
            boolean bl3 = true;
            String string3 = "pos";
            MActiveRenderInfo mActiveRenderInfo3 = this;
            this.f = this.J(string3, bl3, clazz3);
            Class[] classArray2 = new Class[]{MappedClasses.qP};
            Class<Void> clazz4 = Void.TYPE;
            boolean bl4 = true;
            String string4 = "setPosition";
            MActiveRenderInfo mActiveRenderInfo4 = this;
            this.g = this.Y(string4, bl4, clazz4, classArray2);
            Class clazz5 = MappedClasses.zc;
            boolean bl5 = true;
            String string5 = "renderViewEntity";
            MActiveRenderInfo mActiveRenderInfo5 = this;
            this.z = this.J(string5, bl5, clazz5);
            if (ForgeVersion.MC_1_21_11.d()) {
                Class clazz6 = MappedClasses.YU;
                boolean bl6 = true;
                String string6 = "level";
                MActiveRenderInfo mActiveRenderInfo6 = this;
                this.s = this.J(string6, bl6, clazz6);
            } else {
                Class clazz7 = MappedClasses.zJ;
                boolean bl7 = true;
                String string7 = "world";
                MActiveRenderInfo mActiveRenderInfo7 = this;
                this.s = this.J(string7, bl7, clazz7);
            }
        }
        if (ForgeVersion.MC_1_7_10.L()) {
            classArray = new Class[]{MappedClasses.YU, MappedClasses.zm, Float.TYPE};
            Class clazz2 = MappedClasses.Zk;
            boolean bl2 = true;
            string = "getBlockAtEntityViewpoint";
            mActiveRenderInfo = this;
            this.A = mActiveRenderInfo.registerStaticMethod(string, bl2, clazz2, classArray);
        } else if (ForgeVersion.MC_1_8_9.L()) {
            classArray = new Class[]{MappedClasses.YU, MappedClasses.zc, Float.TYPE};
            Class clazz3 = MappedClasses.Zk;
            boolean bl3 = true;
            string = "getBlockAtEntityViewpoint";
            mActiveRenderInfo = this;
            this.A = mActiveRenderInfo.registerStaticMethod(string, bl3, clazz3, classArray);
        } else if (ForgeVersion.MC_1_12_2.L()) {
            classArray = new Class[]{MappedClasses.YU, MappedClasses.zc, Float.TYPE};
            Class clazz4 = MappedClasses.Vv;
            boolean bl4 = true;
            string = "getBlockStateAtEntityViewpoint";
            mActiveRenderInfo = this;
            this.A = mActiveRenderInfo.registerStaticMethod(string, bl4, clazz4, classArray);
        }
    }

    public static float q(MActiveRenderInfo mActiveRenderInfo, Object object) {
        return mActiveRenderInfo.G(object);
    }

    public static Object d(MActiveRenderInfo mActiveRenderInfo, Object object) {
        return mActiveRenderInfo.T(object);
    }

    private Object n(Object object) {
        return this.f.getObject(object);
    }
}

