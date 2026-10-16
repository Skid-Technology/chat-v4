/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.mapping.mappings;

import com.google.common.util.concurrent.ListenableFuture;
import gg.vape.Vape;
import gg.vape.asm.matcher.MethodNodeMatcher;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingFieldBuilder;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import java.io.File;
import java.util.Map;
import java.util.concurrent.Executor;
import org.jetbrains.annotations.Nullable;

public class MMinecraft
extends Mapping {
    private final MappingField O_;
    private MappingField var_gg_vape_mapping_MappingField_U;
    private MappingField OW;
    private MappingField var_gg_vape_mapping_MappingField_f;
    private MappingField Oj;
    private MappingField OT;
    private static String[] var_java_lang_String_arr_P;
    private MappingMethod var_gg_vape_mapping_MappingMethod_r;
    private MappingMethod OQ;
    public MappingMethod var_gg_vape_mapping_MappingMethod_q;
    private MappingField O6;
    private final MappingField var_gg_vape_mapping_MappingField_h;
    private final MappingField var_gg_vape_mapping_MappingField_H;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_N;
    private final MappingField var_gg_vape_mapping_MappingField_X;
    private MappingMethod var_gg_vape_mapping_MappingMethod_n;
    private final MappingField var_gg_vape_mapping_MappingField_E;
    private MappingField OX;
    private final MappingField var_gg_vape_mapping_MappingField_S;
    private final MappingField var_gg_vape_mapping_MappingField_s;
    private MethodNodeMatcher var_gg_vape_asm_matcher_MethodNodeMatcher_k;
    private final MappingField var_gg_vape_mapping_MappingField_A;
    private MappingMethod OD;
    private final MappingField O0;
    final public MappingMethod var_gg_vape_mapping_MappingMethod_d;
    private MappingField var_gg_vape_mapping_MappingField_M;
    private MappingField Oy;
    private MappingMethod var_gg_vape_mapping_MappingMethod_m;
    private final MappingField var_gg_vape_mapping_MappingField_T;
    final public MappingMethod var_gg_vape_mapping_MappingMethod_g;
    private MappingField Os;
    public MappingMethod var_gg_vape_mapping_MappingMethod_i;
    private MappingField var_gg_vape_mapping_MappingField_b;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_B;
    private MappingMethod OV;
    final public MappingMethod var_gg_vape_mapping_MappingMethod_v;
    @Nullable
    private MappingMethod var_gg_vape_mapping_MappingMethod_G;
    private final MappingField On;
    private final MappingField var_gg_vape_mapping_MappingField_w;
    private MappingField var_gg_vape_mapping_MappingField_Z;
    private MappingMethod var_gg_vape_mapping_MappingMethod_c;
    final public MappingMethod var_gg_vape_mapping_MappingMethod_V;
    public MappingField var_gg_vape_mapping_MappingField_y;
    private MappingField var_gg_vape_mapping_MappingField_u;
    private MappingField var_gg_vape_mapping_MappingField_J;
    private MappingMethod var_gg_vape_mapping_MappingMethod_Q;
    final public MappingField var_gg_vape_mapping_MappingField_I;
    public MappingMethod Of;
    private MappingField var_gg_vape_mapping_MappingField_L;
    private MethodNodeMatcher var_gg_vape_asm_matcher_MethodNodeMatcher_K;
    private final MappingField var_gg_vape_mapping_MappingField_e;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_o;
    public MappingMethod var_gg_vape_mapping_MappingMethod_a;
    public MappingField var_gg_vape_mapping_MappingField_j;
    private static Object var_java_lang_Object_z;
    private MappingField var_gg_vape_mapping_MappingField_O;
    private MappingMethod var_gg_vape_mapping_MappingMethod_Y;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_D;
    private MappingField var_gg_vape_mapping_MappingField_l;
    private final MappingField OU;
    final public MappingField var_gg_vape_mapping_MappingField_F;
    private MappingField var_gg_vape_mapping_MappingField_C;
    final public MappingField var_gg_vape_mapping_MappingField_W;
    private MappingField OJ;
    public MappingMethod OS;
    private MappingMethod var_gg_vape_mapping_MappingMethod_x;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_p;
    private final MappingMethod Oq;
    private MappingField OP;

    public Object w(Object object) {
        return this.Oj.getObject(object);
    }

    public static void b(MMinecraft mMinecraft, Object object) {
        mMinecraft.j$src$V$1tqn94f(object);
    }

    private boolean R$src$Z$1ww0oyb(Object object) {
        return this.O6.getBoolean(object) && this.var_gg_vape_mapping_MappingField_Z.getObject(object) != null;
    }

    public void a(Object object, Object object2) {
        this.var_gg_vape_mapping_MappingField_S.setObject(object, object2);
    }

    private Object L(Object object) {
        return this.OJ.getObject(object);
    }

    public Object m(Object object) {
        return this.var_gg_vape_mapping_MappingField_H.getObject(object);
    }

    public Object V(Object object) {
        if (ForgeVersion.MC_26_2.d()) {
            return this.var_gg_vape_mapping_MappingMethod_D.invokeObject(this.var_gg_vape_mapping_MappingField_H.getObject(object), new Object[0]);
        }
        if (this.var_gg_vape_mapping_MappingField_f == null) {
            return null;
        }
        return this.var_gg_vape_mapping_MappingField_f.getObject(object);
    }

    public static Object n(MMinecraft mMinecraft, Object object) {
        return mMinecraft.Z(object);
    }

    public Object a(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_p.invokeObject(object, new Object[0]);
    }

    public static boolean f(MMinecraft mMinecraft, Object object) {
        return mMinecraft.a$src$Z$miyrdu(object);
    }

    public static String[] Q() {
        return var_java_lang_String_arr_P;
    }

    public static void v(MMinecraft mMinecraft, Object object, int n) {
        mMinecraft.Y(object, n);
    }

    public Object T(Object object) {
        return this.var_gg_vape_mapping_MappingField_I.getObject(object);
    }

    public static Object p(MMinecraft mMinecraft, Object object) {
        return mMinecraft.L(object);
    }

    public static Object G(MMinecraft mMinecraft, Object object) {
        return mMinecraft.K(object);
    }

    public static void Y(MMinecraft mMinecraft, Object object) {
        mMinecraft.y(object);
    }

    private Object n$src$Ljava_lang_Object_$3wfxxt(Object object) {
        return this.var_gg_vape_mapping_MappingField_C.getObject(object);
    }

    private void S(Object object, Object object2) {
        this.OU.setObject(object, object2);
    }

    public static Object o(MMinecraft mMinecraft, Object object) {
        return mMinecraft.s(object);
    }

    private boolean a$src$Z$miyrdu(Object object) {
        return this.Os.getBoolean(object);
    }

    public Object E(Object object) {
        return this.var_gg_vape_mapping_MappingField_F.getObject(object);
    }

    public Map h$src$Ljava_util_Map_$8i5rvc(Object object) {
        if (this.var_gg_vape_mapping_MappingField_b == null || this.var_gg_vape_mapping_MappingField_U == null) {
            return null;
        }
        Object object2 = this.var_gg_vape_mapping_MappingField_b.getObject(object);
        return object2 == null ? null : (Map)this.var_gg_vape_mapping_MappingField_U.getObject(object2);
    }

    public int Y(Object object) {
        return this.OW.getInt(object);
    }

    public static Object w(MMinecraft mMinecraft, Object object) {
        return mMinecraft.C(object);
    }

    public int R(Object object) {
        return this.OT.getInt(object);
    }

    public void R(Object object, int n) {
        this.OW.setInt(object, n);
    }

    private Object M(Object object) {
        return this.var_gg_vape_mapping_MappingField_M.getObject(object);
    }

    public Object b(Object object) {
        if (var_java_lang_Object_z == null) {
            var_java_lang_Object_z = this.var_gg_vape_mapping_MappingField_s.getObject(object);
        }
        return var_java_lang_Object_z;
    }

    private Object o(Object object) {
        return this.var_gg_vape_mapping_MappingField_X.getObject(object);
    }

    private Object g(Object object) {
        return this.var_gg_vape_mapping_MappingField_u.getObject(object);
    }

    public Object U(Object object) {
        if (ForgeVersion.MC_26_2.d()) {
            return this.var_gg_vape_mapping_MappingMethod_n.invokeObject(this.A(object), new Object[0]);
        }
        return this.var_gg_vape_mapping_MappingField_L.getObject(object);
    }

    public Object F(Object object) {
        return this.var_gg_vape_mapping_MappingField_S.getObject(object);
    }

    public boolean X$src$Z$1ecebix(Object object) {
        if (ForgeVersion.MC_26_1.d()) {
            return this.var_gg_vape_mapping_MappingMethod_r.invokeBoolean(object, new Object[0]);
        }
        return this.var_gg_vape_mapping_MappingField_h.getBoolean(object);
    }

    public void B(Object object, int n) {
        this.OT.setInt(object, n);
    }

    private Object G(Object object) {
        return this.var_gg_vape_mapping_MappingField_j.getObject(object);
    }

    private void p(Object object, Runnable runnable) {
        if (ForgeVersion.MC_1_16_5.d()) {
            this.OV.invokeVoid(object, runnable);
        } else {
            this.OV.invokeObject(object, runnable);
        }
    }

    public static void R(MMinecraft mMinecraft, Object object) {
        mMinecraft.Y$src$V$1yxhxum(object);
    }

    public static void x(MMinecraft mMinecraft, Object object, int n) {
        mMinecraft.v(object, n);
    }

    private void k(Object object, boolean bl) {
        if (this.var_gg_vape_mapping_MappingField_h == null) {
            return;
        }
        this.var_gg_vape_mapping_MappingField_h.setBoolean(object, bl);
    }

    public static void u(MMinecraft mMinecraft, Object object, boolean bl) {
        mMinecraft.k(object, bl);
    }

    public Object A(Object object) {
        return this.O_.getObject(object);
    }

    private int n(Object object) {
        return this.O0.getInt(object);
    }

    public boolean f(Object object) {
        if (ForgeVersion.MC_26_1.d()) {
            return this.var_gg_vape_mapping_MappingMethod_r.invokeBoolean(object, new Object[0]);
        }
        return this.var_gg_vape_mapping_MappingField_h.getBoolean(object);
    }

    public static Object v(MMinecraft mMinecraft, Object object) {
        return mMinecraft.g(object);
    }

    public static Object W(MMinecraft mMinecraft, Object object) {
        return mMinecraft.H(object);
    }

    public Object l(Object object) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return this.var_gg_vape_mapping_MappingMethod_c.invokeObject(object, new Object[0]);
        }
        return this.OX.getObject(object);
    }

    public static boolean I(MMinecraft mMinecraft, Object object) {
        return mMinecraft.R$src$Z$1ww0oyb(object);
    }

    public static Object X(MMinecraft mMinecraft, Object object) {
        return mMinecraft.I(object);
    }

    public static MappingMethod m(MMinecraft mMinecraft) {
        return mMinecraft.OV;
    }

    public Object J() {
        return this.var_gg_vape_mapping_MappingMethod_o.invokeObject(null, new Object[0]);
    }

    private Object H(Object object) {
        return this.var_gg_vape_mapping_MappingField_W.getObject(object);
    }

    public void P(Object object, Object object2) {
        this.var_gg_vape_mapping_MappingField_w.setObject(object, object2);
    }

    public Object u(Object object) {
        if (ForgeVersion.MC_26_2.d()) {
            return this.var_gg_vape_mapping_MappingMethod_D.invokeObject(this.var_gg_vape_mapping_MappingField_H.getObject(object), new Object[0]);
        }
        return this.var_gg_vape_mapping_MappingMethod_D.invokeObject(object, new Object[0]);
    }

    public Object J(Object object) {
        return this.var_gg_vape_mapping_MappingField_y.getObject(object);
    }

    private Object K(Object object) {
        return this.OU.getObject(object);
    }

    private Object I(Object object) {
        return this.var_gg_vape_mapping_MappingField_E.getObject(object);
    }

    public void r(Object object, Object object2) {
        if (ForgeVersion.MC_26_2.d()) {
            this.var_gg_vape_mapping_MappingMethod_Q.invokeVoid(this.A(object), object2);
            return;
        }
        this.OS.invokeVoid(object, object2);
    }

    public static Object k(MMinecraft mMinecraft, Object object) {
        return mMinecraft.G(object);
    }

    private void v(Object object, int n) {
        this.Oy.setInt(object, n);
    }

    public static void t(MMinecraft mMinecraft, Object object, boolean bl) {
        mMinecraft.U(object, bl);
    }

    public Object h(Object object) {
        if (ForgeVersion.MC_26_1.d()) {
            return this.OD.invokeObject(object, new Object[0]);
        }
        return this.On.getObject(object);
    }

    private Object Z(Object object) {
        return this.var_gg_vape_mapping_MappingField_l.getObject(object);
    }

    public static Object x(MMinecraft mMinecraft, Object object) {
        return mMinecraft.M(object);
    }

    public Object S(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_m != null ? this.var_gg_vape_mapping_MappingMethod_m.invokeObject(object, new Object[0]) : null;
    }

    public static Object y$src$Ljava_lang_Object_$1igycos(MMinecraft mMinecraft, Object object) {
        return mMinecraft.n$src$Ljava_lang_Object_$3wfxxt(object);
    }

    private void Y(Object object, int n) {
        this.O0.setInt(object, n);
    }

    public static int S(MMinecraft mMinecraft, Object object) {
        return mMinecraft.f$src$I$1ifct3s(object);
    }

    public int L() {
        return this.var_gg_vape_mapping_MappingField_A.getInt(null);
    }

    public static void u(String[] stringArray) {
        var_java_lang_String_arr_P = stringArray;
    }

    private void j$src$V$1tqn94f(Object object) {
        if (ForgeVersion.MC_1_16_5.d()) {
            Minecraft.s().O();
            KeyBinding.H();
            this.k(object, false);
            return;
        }
        this.var_gg_vape_mapping_MappingMethod_x.invokeVoidNoArgs(object);
    }

    public static void p(MMinecraft mMinecraft, Object object, Object object2) {
        mMinecraft.S(object, object2);
    }

    public Object R$src$Ljava_lang_Object_$11ec019(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_B.invokeObject(object, new Object[0]);
    }

    private void U(Object object, boolean bl) {
        this.Os.setBoolean(object, bl);
    }

    private int f$src$I$1ifct3s(Object object) {
        return this.Oy.getInt(object);
    }

    public Object c(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_N.invokeObject(object, new Object[0]);
    }

    public void V$src$V$9672l7(Object object) {
        if (ForgeVersion.MC_1_16_5.d()) {
            this.k(object, true);
            return;
        }
        this.OQ.invokeVoidNoArgs(object);
    }

    private Object C(Object object) {
        return this.var_gg_vape_mapping_MappingField_e.getObject(object);
    }

    public static int y(MMinecraft mMinecraft, Object object) {
        return mMinecraft.n(object);
    }

    public Object P(Object object) {
        return this.Oq.invokeObject(object, new Object[0]);
    }

    private void y(Object object) {
        this.var_gg_vape_mapping_MappingMethod_g.invokeVoidNoArgs(object);
    }

    public MappingMethod getGuiSetScreenMethod() {
        return this.var_gg_vape_mapping_MappingMethod_Q;
    }

    public MMinecraft() {
        this(MMinecraft.Q());
    }

    private MMinecraft(String[] stringArray) {
        super(MappedClasses.uP);
        MMinecraft mMinecraft65;
        String string65;
        boolean bl58;
        Class clazz69;
        Class[] classArray25;
        boolean bl;
        Class[] classArray;
        MMinecraft mMinecraft;
        String string;
        boolean bl2;
        Class clazz;
        String[] stringArray2 = stringArray;
        if (ForgeVersion.MC_1_7_10.L()) {
            clazz = MappedClasses.DI;
            bl2 = true;
            string = "thePlayer";
            mMinecraft = this;
            this.var_gg_vape_mapping_MappingField_I = mMinecraft.J(string, bl2, clazz);
            if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
                Class clazz2 = MappedClasses.uQ;
                boolean bl22 = true;
                String string2 = "fontRendererObj";
                MMinecraft mMinecraft2 = this;
                this.var_gg_vape_mapping_MappingField_y = this.J(string2, bl22, clazz2);
                classArray = new Class[]{};
                Class<Void> clazz3 = Void.TYPE;
                boolean bl3 = true;
                String string3 = "clickMouse";
                MMinecraft mMinecraft3 = this;
                this.var_gg_vape_mapping_MappingMethod_q = this.Y(string3, bl3, clazz3, classArray);
                Class[] classArray2 = new Class[]{};
                Class<Void> clazz4 = Void.TYPE;
                boolean bl4 = true;
                String string4 = "rightClickMouse";
                MMinecraft mMinecraft4 = this;
                this.var_gg_vape_mapping_MappingMethod_g = this.Y(string4, bl4, clazz4, classArray2);
                Class[] classArray3 = new Class[]{Boolean.TYPE};
                Class<Void> clazz5 = Void.TYPE;
                boolean bl5 = true;
                String string5 = "sendClickBlockToController";
                MMinecraft mMinecraft5 = this;
                this.Of = this.Y(string5, bl5, clazz5, classArray3);
            } else {
                Class clazz6 = MappedClasses.uQ;
                boolean bl6 = true;
                String string6 = "fontRendererObj";
                MMinecraft mMinecraft6 = this;
                this.var_gg_vape_mapping_MappingField_y = this.J(string6, bl6, clazz6);
                classArray = new Class[]{};
                Class<Void> clazz7 = Void.TYPE;
                boolean bl7 = Wrapper.isNativeAvailable;
                String string7 = "func_147116_af";
                MMinecraft mMinecraft7 = this;
                this.var_gg_vape_mapping_MappingMethod_q = this.Y(string7, bl7, clazz7, classArray);
                Class[] classArray4 = new Class[]{};
                Class<Void> clazz8 = Void.TYPE;
                boolean bl8 = Wrapper.isNativeAvailable;
                String string8 = "func_147121_ag";
                MMinecraft mMinecraft8 = this;
                this.var_gg_vape_mapping_MappingMethod_g = this.Y(string8, bl8, clazz8, classArray4);
                Class[] classArray5 = new Class[]{Boolean.TYPE};
                Class<Void> clazz9 = Void.TYPE;
                boolean bl9 = Wrapper.isNativeAvailable;
                String string9 = "func_147115_a";
                MMinecraft mMinecraft9 = this;
                this.Of = this.Y(string9, bl9, clazz9, classArray5);
            }
        } else {
            if (ForgeVersion.MC_1_12_2.d()) {
                clazz = MappedClasses.z5;
                bl2 = true;
                string = "player";
                mMinecraft = this;
                this.var_gg_vape_mapping_MappingField_I = mMinecraft.J(string, bl2, clazz);
            } else {
                clazz = MappedClasses.z5;
                bl2 = true;
                string = "thePlayer";
                mMinecraft = this;
                this.var_gg_vape_mapping_MappingField_I = mMinecraft.J(string, bl2, clazz);
            }
            if (ForgeVersion.MC_1_16_5.d()) {
                clazz = MappedClasses.uQ;
                bl2 = true;
                string = "fontRenderer";
                mMinecraft = this;
                this.var_gg_vape_mapping_MappingField_y = mMinecraft.J(string, bl2, clazz);
                if (ForgeVersion.MC_26_1.v()) {
                    Class clazz10 = MappedClasses.z8;
                    boolean bl10 = true;
                    String string10 = "itemRenderer";
                    MMinecraft mMinecraft10 = this;
                    this.var_gg_vape_mapping_MappingField_j = this.J(string10, bl10, clazz10);
                }
            } else {
                clazz = MappedClasses.uQ;
                bl2 = true;
                string = ForgeVersion.c() >= 23 ? "fontRenderer" : "fontRendererObj";
                mMinecraft = this;
                this.var_gg_vape_mapping_MappingField_y = mMinecraft.J(string, bl2, clazz);
                Class clazz11 = MappedClasses.z8;
                boolean bl11 = true;
                String string11 = "renderItem";
                MMinecraft mMinecraft11 = this;
                this.var_gg_vape_mapping_MappingField_j = this.J(string11, bl11, clazz11);
            }
            clazz = MappedClasses.Dc;
            bl2 = true;
            string = "renderManager";
            mMinecraft = this;
            this.Oj = mMinecraft.J(string, bl2, clazz);
            Class<Integer> clazz12 = Integer.TYPE;
            boolean bl12 = true;
            String string12 = "leftClickCounter";
            MMinecraft mMinecraft12 = this;
            this.Oy = this.J(string12, bl12, clazz12);
            classArray = new Class[]{};
            Class<Void> clazz13 = Void.TYPE;
            boolean bl13 = true;
            String string13 = "clickMouse";
            MMinecraft mMinecraft13 = this;
            this.var_gg_vape_mapping_MappingMethod_q = this.Y(string13, bl13, clazz13, classArray);
            Class[] classArray6 = new Class[]{};
            Class<Void> clazz14 = Void.TYPE;
            boolean bl14 = true;
            String string14 = "rightClickMouse";
            MMinecraft mMinecraft14 = this;
            this.var_gg_vape_mapping_MappingMethod_g = this.Y(string14, bl14, clazz14, classArray6);
            if (ForgeVersion.MC_26_1.d()) {
                Class[] classArray7 = new Class[]{Float.TYPE};
                Class<Void> clazz15 = Void.TYPE;
                boolean bl15 = true;
                String string15 = "pick";
                MMinecraft mMinecraft15 = this;
                this.var_gg_vape_mapping_MappingMethod_i = this.Y(string15, bl15, clazz15, classArray7);
            }
            Class[] classArray8 = new Class[]{Boolean.TYPE};
            Class<Void> clazz16 = Void.TYPE;
            boolean bl16 = true;
            String string16 = "sendClickBlockToController";
            MMinecraft mMinecraft16 = this;
            this.Of = this.Y(string16, bl16, clazz16, classArray8);
        }
        clazz = MappedClasses.var_java_lang_Class_Z;
        String string2 = "theWorld";
        MMinecraft mMinecraft2 = this;
        this.var_gg_vape_mapping_MappingField_F = ((MappingFieldBuilder)mMinecraft2.fieldBuilder(string2, clazz).setNameForVersion(ForgeVersion.MC_1_12_2.n(), "world")).buildField();
        Class[] classArray2 = new Class[]{};
        Class clazz17 = MappedClasses.F1;
        String string17 = "getNetHandler";
        MMinecraft mMinecraft17 = this;
        this.var_gg_vape_mapping_MappingMethod_p = ((MappingMethodBuilder)this.methodBuilder(string17, clazz17, classArray2).setNameForVersion(ForgeVersion.MC_1_12_2.n(), "getConnection")).buildMethod();
        if (ForgeVersion.MC_1_16_5.d()) {
            if (ForgeVersion.MC_26_1.d()) {
                this.var_gg_vape_mapping_MappingField_h = null;
                Class[] classArray9 = new Class[]{};
                Class<Boolean> clazz18 = Boolean.TYPE;
                boolean bl3 = true;
                String string18 = "isWindowActive";
                MMinecraft mMinecraft18 = this;
                this.var_gg_vape_mapping_MappingMethod_r = this.Y(string18, bl3, clazz18, classArray9);
            } else {
                Class<Boolean> clazz19 = Boolean.TYPE;
                boolean bl4 = true;
                String string19 = "isWindowFocused";
                MMinecraft mMinecraft19 = this;
                this.var_gg_vape_mapping_MappingField_h = this.J(string19, bl4, clazz19);
            }
            Class clazz20 = MappedClasses.FW;
            bl = true;
            String string20 = "gameRenderer";
            MMinecraft mMinecraft20 = this;
            this.var_gg_vape_mapping_MappingField_H = this.J(string20, bl, clazz20);
            Class clazz21 = ForgeVersion.MC_26_2.d() ? MappedClasses.LEVEL_EXTRACTOR : MappedClasses.zs;
            boolean bl17 = true;
            String string21 = ForgeVersion.MC_26_2.d() ? "levelExtractor" : "worldRenderer";
            MMinecraft mMinecraft21 = this;
            this.var_gg_vape_mapping_MappingField_X = this.J(string21, bl17, clazz21);
            Class clazz22 = MappedClasses.Fi;
            boolean bl18 = true;
            String string22 = "mainWindow";
            MMinecraft mMinecraft22 = this;
            this.var_gg_vape_mapping_MappingField_C = this.J(string22, bl18, clazz22);
            Class clazz23 = MappedClasses.FO;
            boolean bl19 = true;
            String string23 = "networkManager";
            MMinecraft mMinecraft23 = this;
            this.var_gg_vape_mapping_MappingField_e = this.J(string23, bl19, clazz23);
            Class clazz24 = MappedClasses.FI;
            boolean bl20 = true;
            String string24 = "integratedServer";
            MMinecraft mMinecraft24 = this;
            this.var_gg_vape_mapping_MappingField_Z = this.J(string24, bl20, clazz24);
            Class[] classArray10 = new Class[]{};
            Class clazz25 = MappedClasses.uP;
            boolean bl21 = true;
            String string25 = "getInstance";
            MMinecraft mMinecraft25 = this;
            this.var_gg_vape_mapping_MappingMethod_o = this.registerStaticMethod(string25, bl21, clazz25, classArray10);
            if (ForgeVersion.c() < ForgeVersion.MC_1_20_6.i()) {
                Class[] classArray11 = new Class[]{Runnable.class};
                Class<Void> clazz26 = Void.TYPE;
                boolean bl22 = false;
                String string26 = "execute";
                Class<Executor> clazz27 = Executor.class;
                MMinecraft mMinecraft26 = this;
                this.OV = this.registerInstanceMethodForOwner(clazz27, string26, bl22, clazz26, classArray11);
            }
        } else {
            Class<Boolean> clazz28 = Boolean.TYPE;
            bl = true;
            String string27 = "fullscreen";
            MMinecraft mMinecraft27 = this;
            this.Os = this.J(string27, bl, clazz28);
            Class<Boolean> clazz29 = Boolean.TYPE;
            boolean bl23 = true;
            String string28 = "inGameHasFocus";
            MMinecraft mMinecraft28 = this;
            this.var_gg_vape_mapping_MappingField_h = this.J(string28, bl23, clazz29);
            Class clazz30 = MappedClasses.FW;
            boolean bl24 = true;
            String string29 = "entityRenderer";
            MMinecraft mMinecraft29 = this;
            this.var_gg_vape_mapping_MappingField_H = this.J(string29, bl24, clazz30);
            Class clazz31 = MappedClasses.zs;
            boolean bl25 = true;
            String string30 = "renderGlobal";
            MMinecraft mMinecraft30 = this;
            this.var_gg_vape_mapping_MappingField_X = this.J(string30, bl25, clazz31);
            Class<Integer> clazz32 = Integer.TYPE;
            boolean bl26 = true;
            String string31 = "displayHeight";
            MMinecraft mMinecraft31 = this;
            this.OT = this.J(string31, bl26, clazz32);
            Class<Integer> clazz33 = Integer.TYPE;
            boolean bl27 = true;
            String string32 = "displayWidth";
            MMinecraft mMinecraft32 = this;
            this.OW = this.J(string32, bl27, clazz33);
            Class clazz34 = MappedClasses.FO;
            boolean bl28 = true;
            String string33 = ForgeVersion.c() >= 23 ? "networkManager" : "myNetworkManager";
            MMinecraft mMinecraft33 = this;
            this.var_gg_vape_mapping_MappingField_e = this.J(string33, bl28, clazz34);
            Class clazz35 = MappedClasses.FI;
            boolean bl29 = true;
            String string34 = ForgeVersion.c() >= 23 ? "integratedServer" : "theIntegratedServer";
            MMinecraft mMinecraft34 = this;
            this.var_gg_vape_mapping_MappingField_Z = this.J(string34, bl29, clazz35);
            Class[] classArray12 = new Class[]{};
            Class clazz36 = MappedClasses.uP;
            boolean bl30 = true;
            String string35 = "getMinecraft";
            MMinecraft mMinecraft35 = this;
            this.var_gg_vape_mapping_MappingMethod_o = this.registerStaticMethod(string35, bl30, clazz36, classArray12);
            Class[] classArray13 = new Class[]{};
            Class<Void> clazz37 = Void.TYPE;
            boolean bl31 = true;
            String string36 = "setIngameNotInFocus";
            MMinecraft mMinecraft36 = this;
            this.var_gg_vape_mapping_MappingMethod_x = this.Y(string36, bl31, clazz37, classArray13);
            Class[] classArray14 = new Class[]{};
            Class<Void> clazz38 = Void.TYPE;
            boolean bl32 = true;
            String string37 = "setIngameFocus";
            MMinecraft mMinecraft37 = this;
            this.OQ = this.Y(string37, bl32, clazz38, classArray14);
            if (ForgeVersion.MC_1_8_9.d()) {
                Class[] classArray15 = new Class[]{Runnable.class};
                Class<ListenableFuture> clazz39 = ListenableFuture.class;
                boolean bl33 = true;
                String string38 = "addScheduledTask";
                Class clazz40 = MappedClasses.V3;
                MMinecraft mMinecraft38 = this;
                this.OV = this.registerInstanceMethodForOwner(clazz40, string38, bl33, clazz39, classArray15);
            }
        }
        Class<Integer> clazz41 = Integer.TYPE;
        bl = true;
        String string39 = "debugFPS";
        MMinecraft mMinecraft39 = this;
        this.var_gg_vape_mapping_MappingField_A = this.registerStaticField(string39, bl, clazz41);
        if (ForgeVersion.MC_26_2.d()) {
            this.var_gg_vape_mapping_MappingField_L = null;
            Class[] classArray16 = new Class[]{};
            Class clazz42 = MappedClasses.VW;
            boolean bl34 = true;
            String string40 = "screen";
            Class clazz43 = MappedClasses.uH;
            MMinecraft mMinecraft40 = this;
            this.var_gg_vape_mapping_MappingMethod_n = this.registerInstanceMethodForOwner(clazz43, string40, bl34, clazz42, classArray16);
            Class[] classArray17 = new Class[]{MappedClasses.VW};
            Class<Void> clazz44 = Void.TYPE;
            boolean bl35 = true;
            String string41 = "setScreen";
            Class clazz45 = MappedClasses.uH;
            MMinecraft mMinecraft41 = this;
            this.var_gg_vape_mapping_MappingMethod_Q = this.registerInstanceMethodForOwner(clazz45, string41, bl35, clazz44, classArray17);
        } else {
            Class clazz46 = MappedClasses.VW;
            boolean bl36 = true;
            String string42 = "currentScreen";
            MMinecraft mMinecraft42 = this;
            this.var_gg_vape_mapping_MappingField_L = this.J(string42, bl36, clazz46);
        }
        Class clazz47 = MappedClasses.lT;
        boolean bl37 = true;
        String string43 = "gameSettings";
        MMinecraft mMinecraft43 = this;
        this.var_gg_vape_mapping_MappingField_s = this.J(string43, bl37, clazz47);
        if (ForgeVersion.MC_26_1.d()) {
            this.On = null;
            Class[] classArray18 = new Class[]{};
            Class clazz48 = MappedClasses.zc;
            boolean bl38 = true;
            String string44 = "getCameraEntity";
            MMinecraft mMinecraft44 = this;
            this.OD = this.Y(string44, bl38, clazz48, classArray18);
            Class[] classArray19 = new Class[]{MappedClasses.zc};
            Class<Void> clazz49 = Void.TYPE;
            boolean bl39 = true;
            String string45 = "setCameraEntity";
            MMinecraft mMinecraft45 = this;
            this.var_gg_vape_mapping_MappingMethod_Y = this.Y(string45, bl39, clazz49, classArray19);
        } else {
            Class clazz50 = ForgeVersion.MC_1_7_10.L() ? MappedClasses.zm : MappedClasses.zc;
            boolean bl40 = true;
            String string46 = "renderViewEntity";
            MMinecraft mMinecraft46 = this;
            this.On = this.J(string46, bl40, clazz50);
        }
        Class clazz51 = MappedClasses.ld;
        boolean bl41 = true;
        String string47 = "playerController";
        MMinecraft mMinecraft47 = this;
        this.var_gg_vape_mapping_MappingField_T = this.J(string47, bl41, clazz51);
        Class clazz52 = MappedClasses.DT;
        boolean bl42 = true;
        String string48 = "objectMouseOver";
        MMinecraft mMinecraft48 = this;
        this.var_gg_vape_mapping_MappingField_w = this.J(string48, bl42, clazz52);
        Class clazz53 = MappedClasses.zc;
        boolean bl43 = true;
        String string49 = "pointedEntity";
        MMinecraft mMinecraft49 = this;
        this.var_gg_vape_mapping_MappingField_S = this.J(string49, bl43, clazz53);
        Class clazz54 = MappedClasses.YN;
        String string50 = "timer";
        MMinecraft mMinecraft50 = this;
        this.var_gg_vape_mapping_MappingField_W = ((MappingFieldBuilder)this.fieldBuilder(string50, clazz54).setNameForVersion(ForgeVersion.MC_1_21_4.n(), "deltaTracker")).buildField();
        Class<Integer> clazz55 = Integer.TYPE;
        boolean bl44 = true;
        String string51 = "rightClickDelayTimer";
        MMinecraft mMinecraft51 = this;
        this.O0 = this.J(string51, bl44, clazz55);
        Class clazz56 = MappedClasses.Ys;
        boolean bl45 = true;
        String string52 = "mouseHelper";
        MMinecraft mMinecraft52 = this;
        this.var_gg_vape_mapping_MappingField_E = this.J(string52, bl45, clazz56);
        Class clazz57 = MappedClasses.qS;
        boolean bl46 = true;
        String string53 = "session";
        MMinecraft mMinecraft53 = this;
        this.OU = this.J(string53, bl46, clazz57);
        Class clazz58 = MappedClasses.Zj;
        boolean bl47 = true;
        String string54 = "ingameGUI";
        MMinecraft mMinecraft54 = this;
        this.O_ = this.J(string54, bl47, clazz58);
        Class<Boolean> clazz59 = Boolean.TYPE;
        boolean bl48 = true;
        String string55 = "integratedServerIsRunning";
        MMinecraft mMinecraft55 = this;
        this.O6 = this.J(string55, bl48, clazz59);
        if (ForgeVersion.MC_1_8_9.B()) {
            Class clazz60 = MappedClasses.qv;
            boolean bl49 = true;
            String string56 = "effectRenderer";
            MMinecraft mMinecraft56 = this;
            this.var_gg_vape_mapping_MappingField_u = this.J(string56, bl49, clazz60);
        }
        if (ForgeVersion.MC_1_16_5.v()) {
            Class<Boolean> clazz61 = Boolean.TYPE;
            boolean bl50 = true;
            String string57 = "fullscreen";
            MMinecraft mMinecraft57 = this;
            this.Os = this.J(string57, bl50, clazz61);
            Class[] classArray20 = new Class[]{};
            Class<Void> clazz62 = Void.TYPE;
            boolean bl51 = true;
            String string58 = "toggleFullscreen";
            MMinecraft mMinecraft58 = this;
            this.var_gg_vape_mapping_MappingMethod_G = this.Y(string58, bl51, clazz62, classArray20);
        }
        if (ForgeVersion.MC_1_20_6.d()) {
            Class[] classArray21 = new Class[]{};
            Class clazz63 = MappedClasses.uR;
            boolean bl52 = true;
            String string59 = "getCurrentServer";
            MMinecraft mMinecraft59 = this;
            this.var_gg_vape_mapping_MappingMethod_c = this.Y(string59, bl52, clazz63, classArray21);
            Class clazz64 = MappedClasses.un;
            boolean bl53 = true;
            String string60 = "keyboardHandler";
            MMinecraft mMinecraft60 = this;
            this.var_gg_vape_mapping_MappingField_O = this.J(string60, bl53, clazz64);
        } else {
            Class clazz65 = MappedClasses.uR;
            boolean bl54 = true;
            String string61 = "currentServerData";
            MMinecraft mMinecraft61 = this;
            this.OX = this.J(string61, bl54, clazz65);
        }
        if (ForgeVersion.MC_26_2.d()) {
            this.OS = null;
        } else {
            Class[] classArray22 = new Class[]{MappedClasses.VW};
            Class<Void> clazz66 = Void.TYPE;
            boolean bl55 = true;
            String string62 = "displayGuiScreen";
            MMinecraft mMinecraft62 = this;
            this.OS = this.Y(string62, bl55, clazz66, classArray22);
        }
        Class[] classArray23 = new Class[]{};
        Class clazz67 = MappedClasses.Dt;
        boolean bl56 = true;
        String string63 = "getTextureManager";
        MMinecraft mMinecraft63 = this;
        this.var_gg_vape_mapping_MappingMethod_N = this.Y(string63, bl56, clazz67, classArray23);
        Class[] classArray24 = new Class[]{};
        Class clazz68 = MappedClasses.Fp;
        boolean bl57 = true;
        String string64 = "getSkinManager";
        MMinecraft mMinecraft64 = this;
        this.Oq = this.Y(string64, bl57, clazz68, classArray24);
        if (ForgeVersion.MC_26_2.d()) {
            classArray25 = new Class[]{};
            clazz69 = MappedClasses.DA;
            bl58 = true;
            string65 = "getFramebuffer";
            mMinecraft65 = this;
            this.var_gg_vape_mapping_MappingMethod_D = this.registerInstanceMethodForOwner(MappedClasses.FW, string65, bl58, clazz69, classArray25);
        } else {
            classArray25 = new Class[]{};
            clazz69 = MappedClasses.ll;
            bl58 = true;
            string65 = "getFramebuffer";
            mMinecraft65 = this;
            this.var_gg_vape_mapping_MappingMethod_D = this.Y(string65, bl58, clazz69, classArray25);
        }
        Class[] classArray26 = new Class[]{};
        Class clazz70 = MappedClasses.FI;
        boolean bl59 = true;
        String string66 = "getIntegratedServer";
        MMinecraft mMinecraft66 = this;
        this.var_gg_vape_mapping_MappingMethod_B = this.Y(string66, bl59, clazz70, classArray26);
        if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray27 = new Class[]{};
            Class<Void> clazz71 = Void.TYPE;
            boolean bl60 = true;
            String string67 = "runTick";
            MMinecraft mMinecraft67 = this;
            this.var_gg_vape_mapping_MappingMethod_a = this.Y(string67, bl60, clazz71, classArray27);
            Class[] classArray28 = new Class[]{Boolean.TYPE};
            Class<Void> clazz72 = Void.TYPE;
            boolean bl61 = true;
            String string68 = "runGameLoop";
            MMinecraft mMinecraft68 = this;
            this.var_gg_vape_mapping_MappingMethod_v = this.Y(string68, bl61, clazz72, classArray28);
        } else {
            Class[] classArray29 = new Class[]{};
            Class<Void> clazz73 = Void.TYPE;
            boolean bl62 = true;
            String string69 = "runTick";
            MMinecraft mMinecraft69 = this;
            this.var_gg_vape_mapping_MappingMethod_a = this.Y(string69, bl62, clazz73, classArray29);
            Class[] classArray30 = new Class[]{};
            Class<Void> clazz74 = Void.TYPE;
            boolean bl63 = true;
            String string70 = "runGameLoop";
            MMinecraft mMinecraft70 = this;
            this.var_gg_vape_mapping_MappingMethod_v = this.Y(string70, bl63, clazz74, classArray30);
        }
        if (ForgeVersion.c() == ForgeVersion.MC_1_8_9.i()) {
            Class[] classArray31 = new Class[]{MappedClasses.qA};
            Class<Void> clazz75 = Void.TYPE;
            boolean bl64 = true;
            String string71 = "displayCrashReport";
            MMinecraft mMinecraft71 = this;
            this.var_gg_vape_mapping_MappingMethod_V = this.Y(string71, bl64, clazz75, classArray31);
        } else {
            this.var_gg_vape_mapping_MappingMethod_V = null;
        }
        if (ForgeVersion.c() == ForgeVersion.MC_1_21_11.i()) {
            Class[] classArray32 = new Class[]{File.class, MappedClasses.qA};
            Class<Integer> clazz76 = Integer.TYPE;
            boolean bl65 = true;
            String string72 = "saveReport";
            MMinecraft mMinecraft72 = this;
            this.var_gg_vape_mapping_MappingMethod_d = this.registerStaticMethod(string72, bl65, clazz76, classArray32);
        } else {
            this.var_gg_vape_mapping_MappingMethod_d = null;
        }
        if (ForgeVersion.MC_1_17.d()) {
            Class clazz77 = MappedClasses.ZL;
            boolean bl66 = true;
            String string73 = "renderBuffers";
            MMinecraft mMinecraft73 = this;
            this.var_gg_vape_mapping_MappingField_M = this.J(string73, bl66, clazz77);
        }
        if (ForgeVersion.MC_1_21_6.d()) {
            if (ForgeVersion.MC_1_21_10.d()) {
                Class clazz78 = MappedClasses.q4;
                boolean bl67 = true;
                String string74 = "atlasManager";
                MMinecraft mMinecraft74 = this;
                this.var_gg_vape_mapping_MappingField_l = this.J(string74, bl67, clazz78);
                Class clazz79 = MappedClasses.DA;
                boolean bl68 = true;
                String string75 = "mainRenderTarget";
                MMinecraft mMinecraft75 = this;
                this.var_gg_vape_mapping_MappingField_f = ForgeVersion.MC_26_2.d() ? null : this.J(string75, bl68, clazz79);
                Class clazz80 = MappedClasses.lr;
                boolean bl69 = true;
                String string76 = "fontManager";
                MMinecraft mMinecraft76 = this;
                this.OP = this.J(string76, bl69, clazz80);
            } else {
                Class clazz81 = MappedClasses.D_;
                boolean bl70 = true;
                String string77 = "guiSprites";
                MMinecraft mMinecraft77 = this;
                this.OJ = this.J(string77, bl70, clazz81);
            }
        }
        if (ForgeVersion.MC_1_21_10.d()) {
            Class[] classArray33 = new Class[]{};
            Class clazz82 = MappedClasses.lC;
            boolean bl71 = true;
            String string78 = "getFramerateLimitTracker";
            MMinecraft mMinecraft78 = this;
            this.var_gg_vape_mapping_MappingMethod_m = this.Y(string78, bl71, clazz82, classArray33);
            Class clazz83 = MappedClasses.FF;
            boolean bl72 = true;
            String string79 = "resourcePackRepository";
            MMinecraft mMinecraft79 = this;
            this.var_gg_vape_mapping_MappingField_J = this.J(string79, bl72, clazz83);
            Class clazz84 = MappedClasses.l_;
            boolean bl73 = true;
            String string80 = "modelManager";
            MMinecraft mMinecraft80 = this;
            this.var_gg_vape_mapping_MappingField_b = this.J(string80, bl73, clazz84);
            Class<Map> clazz85 = Map.class;
            boolean bl74 = true;
            String string81 = "bakedItemStackModels";
            Class clazz86 = MappedClasses.l_;
            MMinecraft mMinecraft81 = this;
            this.var_gg_vape_mapping_MappingField_U = this.registerInstanceFieldForOwner(clazz86, string81, bl74, clazz85);
        }
    }

    public Object j(Object object) {
        if (this.OP == null) {
            return null;
        }
        return this.OP.getObject(object);
    }

    public Object r(Object object) {
        return this.var_gg_vape_mapping_MappingField_T.getObject(object);
    }

    public void G(Object object, Object object2) {
        if (ForgeVersion.MC_26_1.d()) {
            this.var_gg_vape_mapping_MappingMethod_Y.invokeVoid(object, object2);
            return;
        }
        this.On.setObject(object, object2);
    }

    public static Object N(MMinecraft mMinecraft, Object object) {
        return mMinecraft.o(object);
    }

    public Object X(Object object) {
        return this.var_gg_vape_mapping_MappingField_J != null ? this.var_gg_vape_mapping_MappingField_J.getObject(object) : null;
    }

    private Object s(Object object) {
        return this.var_gg_vape_mapping_MappingField_O.getObject(object);
    }

    public Object q(Object object) {
        return this.var_gg_vape_mapping_MappingField_w.getObject(object);
    }

    private void Y$src$V$1yxhxum(Object object) {
        this.var_gg_vape_mapping_MappingMethod_G.invokeVoidNoArgs(object);
    }

    public void Y(Object object, Object object2) {
        if (ForgeVersion.MC_26_2.d()) {
            this.var_gg_vape_mapping_MappingMethod_Q.invokeVoid(this.A(object), object2);
            return;
        }
        this.var_gg_vape_mapping_MappingField_L.setObject(object, object2);
    }

    static {
        MMinecraft.u(null);
    }
}

