/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MEntityPlayerSP;
import gg.vape.ui.click.component.GuiComponent;

public class MEntityClientPlayerMP
extends Mapping {
    private final MappingField var_gg_vape_mapping_MappingField_E;
    private final MappingField var_gg_vape_mapping_MappingField_x;
    private final MappingField var_gg_vape_mapping_MappingField_a;
    private final MappingField var_gg_vape_mapping_MappingField_f;
    private final MappingField var_gg_vape_mapping_MappingField_Q;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_N;
    private final MappingField var_gg_vape_mapping_MappingField_j;
    private final MappingField var_gg_vape_mapping_MappingField_G;
    private final MappingField var_gg_vape_mapping_MappingField_K;
    private final MappingField var_gg_vape_mapping_MappingField_D;
    public MappingMethod var_gg_vape_mapping_MappingMethod_M;
    private final MappingField var_gg_vape_mapping_MappingField_k;
    private final MappingField var_gg_vape_mapping_MappingField_Z;

    public static double p(MEntityClientPlayerMP mEntityClientPlayerMP, Object object) {
        return mEntityClientPlayerMP.V(object);
    }

    private float I(Object object) {
        return this.var_gg_vape_mapping_MappingField_a.getFloat(object);
    }

    public static float K(MEntityClientPlayerMP mEntityClientPlayerMP, Object object) {
        return mEntityClientPlayerMP.I(object);
    }

    private void d(Object object, double d) {
        this.var_gg_vape_mapping_MappingField_K.setDouble(object, d);
    }

    public static void S(MEntityClientPlayerMP mEntityClientPlayerMP, Object object, boolean bl) {
        mEntityClientPlayerMP.q(object, bl);
    }

    private boolean R(Object object) {
        return this.var_gg_vape_mapping_MappingField_D.getBoolean(object);
    }

    private double V(Object object) {
        return this.var_gg_vape_mapping_MappingField_x.getDouble(object);
    }

    public static void O(MEntityClientPlayerMP mEntityClientPlayerMP, Object object, float f) {
        mEntityClientPlayerMP.r(object, f);
    }

    public static boolean Q(MEntityClientPlayerMP mEntityClientPlayerMP, Object object) {
        return mEntityClientPlayerMP.R(object);
    }

    private void m(Object object, int n) {
        this.var_gg_vape_mapping_MappingField_j.setInt(object, n);
    }

    public static boolean T(MEntityClientPlayerMP mEntityClientPlayerMP, Object object) {
        return mEntityClientPlayerMP.X(object);
    }

    public static void q(MEntityClientPlayerMP mEntityClientPlayerMP, Object object, double d) {
        mEntityClientPlayerMP.b(object, d);
    }

    private void Y(Object object, boolean bl) {
        this.var_gg_vape_mapping_MappingField_D.setBoolean(object, bl);
    }

    public static int K$src$I$jaup0x(MEntityClientPlayerMP mEntityClientPlayerMP, Object object) {
        return mEntityClientPlayerMP.Q(object);
    }

    public static float l(MEntityClientPlayerMP mEntityClientPlayerMP, Object object) {
        return mEntityClientPlayerMP.G(object);
    }

    private Object A(Object object) {
        return this.var_gg_vape_mapping_MappingField_f.getObject(object);
    }

    private void m(Object object, double d) {
        this.var_gg_vape_mapping_MappingField_Q.setDouble(object, d);
    }

    public static Object W(MEntityClientPlayerMP mEntityClientPlayerMP, Object object) {
        return mEntityClientPlayerMP.A(object);
    }

    public static void D(MEntityClientPlayerMP mEntityClientPlayerMP, Object object, boolean bl) {
        mEntityClientPlayerMP.B(object, bl);
    }

    private void B(Object object, boolean bl) {
        this.var_gg_vape_mapping_MappingField_Z.setBoolean(object, bl);
    }

    private void a(Object object, double d) {
        this.var_gg_vape_mapping_MappingField_x.setDouble(object, d);
    }

    public static void v(MEntityClientPlayerMP mEntityClientPlayerMP, Object object, double d) {
        mEntityClientPlayerMP.d(object, d);
    }

    public static void D(MEntityClientPlayerMP mEntityClientPlayerMP, Object object, float f) {
        mEntityClientPlayerMP.g(object, f);
    }

    private int Q(Object object) {
        return this.var_gg_vape_mapping_MappingField_j.getInt(object);
    }

    public static double g(MEntityClientPlayerMP mEntityClientPlayerMP, Object object) {
        return mEntityClientPlayerMP.j(object);
    }

    public static void n(MEntityClientPlayerMP mEntityClientPlayerMP, Object object, double d) {
        mEntityClientPlayerMP.a(object, d);
    }

    public MEntityClientPlayerMP() {
        super(MappedClasses.DI);
        Class clazz = MappedClasses.F1;
        boolean bl = true;
        String string = "sendQueue";
        MEntityClientPlayerMP mEntityClientPlayerMP = this;
        this.var_gg_vape_mapping_MappingField_f = this.J(string, bl, clazz);
        Class<Boolean> clazz2 = Boolean.TYPE;
        boolean bl2 = true;
        String string2 = "wasSprinting";
        MEntityClientPlayerMP mEntityClientPlayerMP2 = this;
        this.var_gg_vape_mapping_MappingField_D = this.J(string2, bl2, clazz2);
        Class<Boolean> clazz3 = Boolean.TYPE;
        boolean bl3 = true;
        String string3 = "wasSneaking";
        MEntityClientPlayerMP mEntityClientPlayerMP3 = this;
        this.var_gg_vape_mapping_MappingField_G = this.J(string3, bl3, clazz3);
        Class<Double> clazz4 = Double.TYPE;
        boolean bl4 = true;
        String string4 = "oldPosX";
        MEntityClientPlayerMP mEntityClientPlayerMP4 = this;
        this.var_gg_vape_mapping_MappingField_k = this.J(string4, bl4, clazz4);
        Class<Double> clazz5 = Double.TYPE;
        boolean bl5 = true;
        String string5 = "oldMinY";
        MEntityClientPlayerMP mEntityClientPlayerMP5 = this;
        this.var_gg_vape_mapping_MappingField_K = this.J(string5, bl5, clazz5);
        Class<Double> clazz6 = Double.TYPE;
        boolean bl6 = true;
        String string6 = "oldPosY";
        MEntityClientPlayerMP mEntityClientPlayerMP6 = this;
        this.var_gg_vape_mapping_MappingField_Q = this.J(string6, bl6, clazz6);
        Class<Double> clazz7 = Double.TYPE;
        boolean bl7 = true;
        String string7 = "oldPosZ";
        MEntityClientPlayerMP mEntityClientPlayerMP7 = this;
        this.var_gg_vape_mapping_MappingField_x = this.J(string7, bl7, clazz7);
        Class<Float> clazz8 = Float.TYPE;
        boolean bl8 = true;
        String string8 = "oldRotationYaw";
        MEntityClientPlayerMP mEntityClientPlayerMP8 = this;
        this.var_gg_vape_mapping_MappingField_a = this.J(string8, bl8, clazz8);
        Class<Float> clazz9 = Float.TYPE;
        boolean bl9 = true;
        String string9 = "oldRotationPitch";
        MEntityClientPlayerMP mEntityClientPlayerMP9 = this;
        this.var_gg_vape_mapping_MappingField_E = this.J(string9, bl9, clazz9);
        GuiComponent[] guiComponentArray = MEntityPlayerSP.r();
        Class<Integer> clazz10 = Integer.TYPE;
        boolean bl10 = true;
        String string10 = "ticksSinceMovePacket";
        MEntityClientPlayerMP mEntityClientPlayerMP10 = this;
        this.var_gg_vape_mapping_MappingField_j = this.J(string10, bl10, clazz10);
        Class<Boolean> clazz11 = Boolean.TYPE;
        boolean bl11 = true;
        String string11 = "wasOnGround";
        MEntityClientPlayerMP mEntityClientPlayerMP11 = this;
        this.var_gg_vape_mapping_MappingField_Z = this.J(string11, bl11, clazz11);
        Class[] classArray = new Class[]{String.class};
        Class<Void> clazz12 = Void.TYPE;
        boolean bl12 = true;
        String string12 = "sendChatMessage";
        MEntityClientPlayerMP mEntityClientPlayerMP12 = this;
        this.var_gg_vape_mapping_MappingMethod_N = this.Y(string12, bl12, clazz12, classArray);
        Class[] classArray2 = new Class[]{};
        Class<Void> clazz13 = Void.TYPE;
        boolean bl13 = true;
        String string13 = "sendMotionUpdates";
        MEntityClientPlayerMP mEntityClientPlayerMP13 = this;
        this.var_gg_vape_mapping_MappingMethod_M = this.Y(string13, bl13, clazz13, classArray2);
        if (GuiComponent.getLegacyComponentState() == null) {
            MEntityPlayerSP.W(new GuiComponent[5]);
        }
    }

    public static double H(MEntityClientPlayerMP mEntityClientPlayerMP, Object object) {
        return mEntityClientPlayerMP.Z(object);
    }

    private double j(Object object) {
        return this.var_gg_vape_mapping_MappingField_Q.getDouble(object);
    }

    private double b(Object object) {
        return this.var_gg_vape_mapping_MappingField_K.getDouble(object);
    }

    private void g(Object object, float f) {
        this.var_gg_vape_mapping_MappingField_E.setFloat(object, f);
    }

    public static void z(MEntityClientPlayerMP mEntityClientPlayerMP, Object object, double d) {
        mEntityClientPlayerMP.m(object, d);
    }

    private void q(Object object, boolean bl) {
        this.var_gg_vape_mapping_MappingField_G.setBoolean(object, bl);
    }

    private void A(Object object, String string) {
        this.var_gg_vape_mapping_MappingMethod_N.invokeVoid(object, string);
    }

    private float G(Object object) {
        return this.var_gg_vape_mapping_MappingField_E.getFloat(object);
    }

    private void b(Object object, double d) {
        this.var_gg_vape_mapping_MappingField_k.setDouble(object, d);
    }

    private double Z(Object object) {
        return this.var_gg_vape_mapping_MappingField_k.getDouble(object);
    }

    public static double x(MEntityClientPlayerMP mEntityClientPlayerMP, Object object) {
        return mEntityClientPlayerMP.b(object);
    }

    private void r(Object object, float f) {
        this.var_gg_vape_mapping_MappingField_a.setFloat(object, f);
    }

    public static void e(MEntityClientPlayerMP mEntityClientPlayerMP, Object object, String string) {
        mEntityClientPlayerMP.A(object, string);
    }

    public static void F(MEntityClientPlayerMP mEntityClientPlayerMP, Object object, boolean bl) {
        mEntityClientPlayerMP.Y(object, bl);
    }

    private boolean X(Object object) {
        return this.var_gg_vape_mapping_MappingField_G.getBoolean(object);
    }

    public static void Y(MEntityClientPlayerMP mEntityClientPlayerMP, Object object, int n) {
        mEntityClientPlayerMP.m(object, n);
    }
}

