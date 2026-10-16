/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingFieldBuilder;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MEntityPlayerSP
extends Mapping {
    final public MappingMethod var_gg_vape_mapping_MappingMethod_z;
    public MappingMethod var_gg_vape_mapping_MappingMethod_G;
    public MappingMethod var_gg_vape_mapping_MappingMethod_k;
    private MappingField var_gg_vape_mapping_MappingField_L;
    private MappingField var_gg_vape_mapping_MappingField_I;
    private MappingField var_gg_vape_mapping_MappingField_h;
    private MappingField var_gg_vape_mapping_MappingField_n;
    private MappingField var_gg_vape_mapping_MappingField_K;
    private final MappingField var_gg_vape_mapping_MappingField_q;
    private static GuiComponent[] var_gg_vape_ui_click_component_GuiComponent_arr_p;
    private MappingField var_gg_vape_mapping_MappingField_g;
    private MappingField var_gg_vape_mapping_MappingField_E;
    private MappingField var_gg_vape_mapping_MappingField_d;
    private MappingField var_gg_vape_mapping_MappingField_F;
    private MappingField var_gg_vape_mapping_MappingField_f;
    private MappingField var_gg_vape_mapping_MappingField_c;
    private MappingField var_gg_vape_mapping_MappingField_Z;
    private MappingField var_gg_vape_mapping_MappingField_U;
    private MappingField var_gg_vape_mapping_MappingField_D;
    private MappingField var_gg_vape_mapping_MappingField_A;
    private MappingField var_gg_vape_mapping_MappingField_b;
    private MappingField var_gg_vape_mapping_MappingField_r;
    public MappingMethod var_gg_vape_mapping_MappingMethod_X;

    private float s(Object object) {
        return this.var_gg_vape_mapping_MappingField_c.getFloat(object);
    }

    private void D(Object object, double d) {
        this.var_gg_vape_mapping_MappingField_A.setDouble(object, d);
    }

    private void A(Object object, float f) {
        this.var_gg_vape_mapping_MappingField_K.setFloat(object, f);
    }

    public static void B(MEntityPlayerSP mEntityPlayerSP, Object object, float f) {
        mEntityPlayerSP.N(object, f);
    }

    public MEntityPlayerSP() {
        this(MEntityPlayerSP.r());
    }

    private MEntityPlayerSP(GuiComponent[] guiComponentArray) {
        super(MappedClasses.z5);
        GuiComponent[] guiComponentArray2 = guiComponentArray;
        Class clazz = MappedClasses.qO;
        boolean bl = true;
        String string = "movementInput";
        MEntityPlayerSP mEntityPlayerSP = this;
        this.var_gg_vape_mapping_MappingField_q = this.J(string, bl, clazz);
        if (ForgeVersion.MC_1_7_10.Y()) {
            Class[] classArray;
            if (ForgeVersion.MC_1_12_2.d()) {
                Class<Float> clazz2 = Float.TYPE;
                boolean bl2 = Wrapper.isNativeAvailable;
                String string2 = "field_191988_bg";
                Class clazz3 = MappedClasses.zm;
                MEntityPlayerSP mEntityPlayerSP2 = this;
                this.var_gg_vape_mapping_MappingField_g = this.registerInstanceFieldForOwner(clazz3, string2, bl2, clazz2);
                Class clazz4 = MappedClasses.F1;
                boolean bl3 = true;
                String string3 = "connection";
                MEntityPlayerSP mEntityPlayerSP3 = this;
                this.var_gg_vape_mapping_MappingField_U = this.J(string3, bl3, clazz4);
            } else {
                Class clazz5 = MappedClasses.F1;
                boolean bl4 = true;
                String string4 = "sendQueue";
                MEntityPlayerSP mEntityPlayerSP4 = this;
                this.var_gg_vape_mapping_MappingField_U = this.J(string4, bl4, clazz5);
            }
            Class<Double> clazz6 = Double.TYPE;
            boolean bl5 = true;
            String string5 = "lastReportedPosX";
            MEntityPlayerSP mEntityPlayerSP5 = this;
            this.var_gg_vape_mapping_MappingField_A = this.J(string5, bl5, clazz6);
            Class<Double> clazz7 = Double.TYPE;
            boolean bl6 = true;
            String string6 = "lastReportedPosY";
            MEntityPlayerSP mEntityPlayerSP6 = this;
            this.var_gg_vape_mapping_MappingField_E = this.J(string6, bl6, clazz7);
            Class<Double> clazz8 = Double.TYPE;
            boolean bl7 = true;
            String string7 = "lastReportedPosZ";
            MEntityPlayerSP mEntityPlayerSP7 = this;
            this.var_gg_vape_mapping_MappingField_D = this.J(string7, bl7, clazz8);
            Class<Float> clazz9 = Float.TYPE;
            boolean bl8 = true;
            String string8 = "lastReportedYaw";
            MEntityPlayerSP mEntityPlayerSP8 = this;
            this.var_gg_vape_mapping_MappingField_d = this.J(string8, bl8, clazz9);
            Class<Float> clazz10 = Float.TYPE;
            boolean bl9 = true;
            String string9 = "lastReportedPitch";
            MEntityPlayerSP mEntityPlayerSP9 = this;
            this.var_gg_vape_mapping_MappingField_c = this.J(string9, bl9, clazz10);
            Class<Boolean> clazz11 = Boolean.TYPE;
            boolean bl10 = true;
            String string10 = "serverSprintState";
            MEntityPlayerSP mEntityPlayerSP10 = this;
            this.var_gg_vape_mapping_MappingField_F = this.J(string10, bl10, clazz11);
            Class<Integer> clazz12 = Integer.TYPE;
            boolean bl11 = true;
            String string11 = "positionUpdateTicks";
            MEntityPlayerSP mEntityPlayerSP11 = this;
            this.var_gg_vape_mapping_MappingField_I = this.J(string11, bl11, clazz12);
            if (ForgeVersion.MC_1_20_6.d()) {
                classArray = new Class[]{String.class};
                Class<Void> clazz13 = Void.TYPE;
                boolean bl12 = true;
                String string12 = "sendChat";
                Class clazz14 = MappedClasses.F1;
                MEntityPlayerSP mEntityPlayerSP12 = this;
                this.var_gg_vape_mapping_MappingMethod_G = this.registerInstanceMethodForOwner(clazz14, string12, bl12, clazz13, classArray);
            } else {
                classArray = new Class[]{String.class};
                Class<Void> clazz15 = Void.TYPE;
                boolean bl13 = true;
                String string13 = "sendChatMessage";
                MEntityPlayerSP mEntityPlayerSP13 = this;
                this.var_gg_vape_mapping_MappingMethod_G = this.Y(string13, bl13, clazz15, classArray);
            }
            classArray = new Class[]{};
            Class<Void> clazz16 = Void.TYPE;
            boolean bl14 = true;
            String string14 = "onUpdateWalkingPlayer";
            MEntityPlayerSP mEntityPlayerSP14 = this;
            this.var_gg_vape_mapping_MappingMethod_k = this.Y(string14, bl14, clazz16, classArray);
        }
        if (ForgeVersion.MC_1_16_5.d() && ForgeVersion.MC_1_20_6.v()) {
            Class<Integer> clazz17 = Integer.TYPE;
            boolean bl15 = true;
            String string15 = "sprintTime";
            MEntityPlayerSP mEntityPlayerSP15 = this;
            this.var_gg_vape_mapping_MappingField_h = this.J(string15, bl15, clazz17);
        } else if (ForgeVersion.MC_1_21_10.v()) {
            Class<Integer> clazz18 = Integer.TYPE;
            boolean bl16 = true;
            String string16 = "sprintingTicksLeft";
            MEntityPlayerSP mEntityPlayerSP16 = this;
            this.var_gg_vape_mapping_MappingField_h = this.J(string16, bl16, clazz18);
        }
        Class<Integer> clazz19 = Integer.TYPE;
        String string17 = "sprintToggleTimer";
        MEntityPlayerSP mEntityPlayerSP17 = this;
        this.var_gg_vape_mapping_MappingField_Z = ((MappingFieldBuilder)this.fieldBuilder(string17, clazz19).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "sprintTriggerTime")).buildField();
        Class[] classArray = new Class[]{};
        Class<Void> clazz20 = Void.TYPE;
        String string18 = "onLivingUpdate";
        MEntityPlayerSP mEntityPlayerSP18 = this;
        this.var_gg_vape_mapping_MappingMethod_z = ((MappingMethodBuilder)((MappingMethodBuilder)this.methodBuilder(string18, clazz20, classArray).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "aiStep")).setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.zQ)).buildMethod();
        if (ForgeVersion.MC_1_12_2.d()) {
            if (!ForgeVersion.MC_1_16_5.d()) {
                Class[] classArray2 = new Class[]{MappedClasses.Y6, Float.TYPE, Float.TYPE};
                Class<Void> clazz21 = Void.TYPE;
                boolean bl17 = true;
                String string19 = "playSound";
                MEntityPlayerSP mEntityPlayerSP19 = this;
                this.var_gg_vape_mapping_MappingMethod_X = this.Y(string19, bl17, clazz21, classArray2);
            }
        } else {
            Class[] classArray3 = new Class[]{String.class, Float.TYPE, Float.TYPE};
            Class<Void> clazz22 = Void.TYPE;
            boolean bl18 = true;
            String string20 = "playSound";
            MEntityPlayerSP mEntityPlayerSP20 = this;
            this.var_gg_vape_mapping_MappingMethod_X = this.Y(string20, bl18, clazz22, classArray3);
        }
        Class<Float> clazz23 = Float.TYPE;
        boolean bl19 = true;
        String string21 = "timeInPortal";
        MEntityPlayerSP mEntityPlayerSP21 = this;
        this.var_gg_vape_mapping_MappingField_b = this.J(string21, bl19, clazz23);
        Class<Float> clazz24 = Float.TYPE;
        boolean bl20 = true;
        String string22 = "prevTimeInPortal";
        MEntityPlayerSP mEntityPlayerSP22 = this;
        this.var_gg_vape_mapping_MappingField_r = this.J(string22, bl20, clazz24);
        Class<Float> clazz25 = Float.TYPE;
        String string23 = "renderArmYaw";
        MEntityPlayerSP mEntityPlayerSP23 = this;
        this.var_gg_vape_mapping_MappingField_f = ((MappingFieldBuilder)this.fieldBuilder(string23, clazz25).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "yBob")).buildField();
        Class<Float> clazz26 = Float.TYPE;
        String string24 = "renderArmPitch";
        MEntityPlayerSP mEntityPlayerSP24 = this;
        this.var_gg_vape_mapping_MappingField_n = ((MappingFieldBuilder)this.fieldBuilder(string24, clazz26).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "xBob")).buildField();
        Class<Float> clazz27 = Float.TYPE;
        String string25 = "prevRenderArmYaw";
        MEntityPlayerSP mEntityPlayerSP25 = this;
        this.var_gg_vape_mapping_MappingField_K = ((MappingFieldBuilder)this.fieldBuilder(string25, clazz27).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "yBobO")).buildField();
        Class<Float> clazz28 = Float.TYPE;
        String string26 = "prevRenderArmPitch";
        MEntityPlayerSP mEntityPlayerSP26 = this;
        this.var_gg_vape_mapping_MappingField_L = ((MappingFieldBuilder)this.fieldBuilder(string26, clazz28).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "xBobO")).buildField();
    }

    private Object b(Object object) {
        return this.var_gg_vape_mapping_MappingField_U.getObject(object);
    }

    private void m(Object object, double d) {
        this.var_gg_vape_mapping_MappingField_E.setDouble(object, d);
    }

    private float E(Object object) {
        return this.var_gg_vape_mapping_MappingField_b.getFloat(object);
    }

    public static double a(MEntityPlayerSP mEntityPlayerSP, Object object) {
        return mEntityPlayerSP.C(object);
    }

    private double C(Object object) {
        return this.var_gg_vape_mapping_MappingField_A.getDouble(object);
    }

    public static void X(MEntityPlayerSP mEntityPlayerSP, Object object, double d) {
        mEntityPlayerSP.D(object, d);
    }

    public static void Z(MEntityPlayerSP mEntityPlayerSP, Object object, float f) {
        mEntityPlayerSP.f(object, f);
    }

    public static void f(MEntityPlayerSP mEntityPlayerSP, Object object, float f) {
        mEntityPlayerSP.n(object, f);
    }

    private double K(Object object) {
        return this.var_gg_vape_mapping_MappingField_E.getDouble(object);
    }

    public static float j(MEntityPlayerSP mEntityPlayerSP, Object object) {
        return mEntityPlayerSP.G(object);
    }

    public static void T(MEntityPlayerSP mEntityPlayerSP, Object object, double d) {
        mEntityPlayerSP.H(object, d);
    }

    public Object r$src$Ljava_lang_Object_$zogw4x(Object object) {
        return this.var_gg_vape_mapping_MappingField_q.getObject(object);
    }

    public static Object I(MEntityPlayerSP mEntityPlayerSP, Object object) {
        return mEntityPlayerSP.b(object);
    }

    private void f(Object object, float f) {
        this.var_gg_vape_mapping_MappingField_d.setFloat(object, f);
    }

    public static float z(MEntityPlayerSP mEntityPlayerSP, Object object) {
        return mEntityPlayerSP.x(object);
    }

    private void U(Object object, float f) {
        this.var_gg_vape_mapping_MappingField_L.setFloat(object, f);
    }

    private float I$src$F$xvpl6i(Object object) {
        return this.var_gg_vape_mapping_MappingField_r.getFloat(object);
    }

    private void G(Object object, int n) {
        this.var_gg_vape_mapping_MappingField_I.setInt(object, n);
    }

    private void s(Object object, float f) {
        this.var_gg_vape_mapping_MappingField_f.setFloat(object, f);
    }

    public static void A(MEntityPlayerSP mEntityPlayerSP, Object object, float f) {
        mEntityPlayerSP.s(object, f);
    }

    public static void W(GuiComponent[] guiComponentArray) {
        var_gg_vape_ui_click_component_GuiComponent_arr_p = guiComponentArray;
    }

    public static float q(MEntityPlayerSP mEntityPlayerSP, Object object) {
        return mEntityPlayerSP.s(object);
    }

    private float G(Object object) {
        return this.var_gg_vape_mapping_MappingField_K.getFloat(object);
    }

    private void B(Object object, String string) {
        this.var_gg_vape_mapping_MappingMethod_G.invokeVoid(object, string);
    }

    private void n(Object object, float f) {
        this.var_gg_vape_mapping_MappingField_n.setFloat(object, f);
    }

    public static float M(MEntityPlayerSP mEntityPlayerSP, Object object) {
        return mEntityPlayerSP.E(object);
    }

    private float w(Object object) {
        return this.var_gg_vape_mapping_MappingField_L.getFloat(object);
    }

    public float z(Object object) {
        return this.var_gg_vape_mapping_MappingField_g.getFloat(object);
    }

    public static void d(MEntityPlayerSP mEntityPlayerSP, Object object, float f) {
        mEntityPlayerSP.U(object, f);
    }

    private float d(Object object) {
        return this.var_gg_vape_mapping_MappingField_f.getFloat(object);
    }

    public static int z$src$I$9x5vb2(MEntityPlayerSP mEntityPlayerSP, Object object) {
        return mEntityPlayerSP.y(object);
    }

    public int L(Object object) {
        return this.var_gg_vape_mapping_MappingField_Z.getInt(object);
    }

    private void N(Object object, float f) {
        this.var_gg_vape_mapping_MappingField_c.setFloat(object, f);
    }

    public static void X(MEntityPlayerSP mEntityPlayerSP, Object object, String string) {
        mEntityPlayerSP.B(object, string);
    }

    public static float V(MEntityPlayerSP mEntityPlayerSP, Object object) {
        return mEntityPlayerSP.w(object);
    }

    public int r(Object object) {
        return this.var_gg_vape_mapping_MappingField_h.getInt(object);
    }

    public static float a$src$F$12v06yw(MEntityPlayerSP mEntityPlayerSP, Object object) {
        return mEntityPlayerSP.I$src$F$xvpl6i(object);
    }

    public static void y(MEntityPlayerSP mEntityPlayerSP, Object object, int n) {
        mEntityPlayerSP.G(object, n);
    }

    public static void j(MEntityPlayerSP mEntityPlayerSP, Object object, float f) {
        mEntityPlayerSP.A(object, f);
    }

    public static float o(MEntityPlayerSP mEntityPlayerSP, Object object) {
        return mEntityPlayerSP.d(object);
    }

    public static GuiComponent[] r() {
        return var_gg_vape_ui_click_component_GuiComponent_arr_p;
    }

    private double I(Object object) {
        return this.var_gg_vape_mapping_MappingField_D.getDouble(object);
    }

    private int y(Object object) {
        return this.var_gg_vape_mapping_MappingField_I.getInt(object);
    }

    public static float O(MEntityPlayerSP mEntityPlayerSP, Object object) {
        return mEntityPlayerSP.X(object);
    }

    private float X(Object object) {
        return this.var_gg_vape_mapping_MappingField_n.getFloat(object);
    }

    public static double T(MEntityPlayerSP mEntityPlayerSP, Object object) {
        return mEntityPlayerSP.K(object);
    }

    public void l(Object object, float f) {
        this.var_gg_vape_mapping_MappingField_g.setFloat(object, f);
    }

    public static double g(MEntityPlayerSP mEntityPlayerSP, Object object) {
        return mEntityPlayerSP.I(object);
    }

    private float x(Object object) {
        return this.var_gg_vape_mapping_MappingField_d.getFloat(object);
    }

    public static void m(MEntityPlayerSP mEntityPlayerSP, Object object, double d) {
        mEntityPlayerSP.m(object, d);
    }

    private void H(Object object, double d) {
        this.var_gg_vape_mapping_MappingField_D.setDouble(object, d);
    }

    static {
        MEntityPlayerSP.W(new GuiComponent[5]);
    }
}

