/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MKeyBinding
extends Mapping {
    public MappingMethod var_gg_vape_mapping_MappingMethod_s;
    public MappingMethod var_gg_vape_mapping_MappingMethod_q;
    final public MappingMethod var_gg_vape_mapping_MappingMethod_c;
    private final MappingField var_gg_vape_mapping_MappingField_B;
    private MappingMethod var_gg_vape_mapping_MappingMethod_T;
    final public MappingMethod var_gg_vape_mapping_MappingMethod_p;
    private static boolean var_boolean_e;
    public MappingMethod var_gg_vape_mapping_MappingMethod_Z;
    private MappingMethod var_gg_vape_mapping_MappingMethod_X;
    final public MappingMethod var_gg_vape_mapping_MappingMethod_z;
    public MappingField var_gg_vape_mapping_MappingField_E;
    private final MappingField var_gg_vape_mapping_MappingField_x;

    public void d(Object object, boolean bl) {
        this.var_gg_vape_mapping_MappingMethod_q.invokeVoid(null, object, bl);
    }

    public static boolean U() {
        return var_boolean_e;
    }

    public void x(Object object) {
        this.var_gg_vape_mapping_MappingMethod_z.invokeVoid(null, object);
    }

    public void s(int n) {
        this.var_gg_vape_mapping_MappingMethod_z.invokeVoid(null, n);
    }

    public Object l(Object object) {
        return this.var_gg_vape_mapping_MappingField_E.getObject(object);
    }

    public void o(Object object, boolean bl) {
        this.var_gg_vape_mapping_MappingMethod_s.invokeVoid(object, bl);
    }

    public MKeyBinding() {
        super(MappedClasses.DR);
        Class<Boolean> clazz = Boolean.TYPE;
        boolean bl = true;
        String string = "pressed";
        MKeyBinding mKeyBinding = this;
        this.var_gg_vape_mapping_MappingField_x = this.J(string, bl, clazz);
        Class<Integer> clazz2 = Integer.TYPE;
        boolean bl2 = true;
        String string2 = "pressTime";
        MKeyBinding mKeyBinding2 = this;
        this.var_gg_vape_mapping_MappingField_B = this.J(string2, bl2, clazz2);
        if (MKeyBinding.U()) {
            Class[] classArray = new Class[]{};
            Class<Boolean> clazz3 = Boolean.TYPE;
            boolean bl3 = true;
            String string3 = "isPressed";
            MKeyBinding mKeyBinding3 = this;
            this.var_gg_vape_mapping_MappingMethod_c = this.Y(string3, bl3, clazz3, classArray);
            Class[] classArray2 = new Class[]{};
            Class<Void> clazz4 = Void.TYPE;
            boolean bl4 = true;
            String string4 = "unPressAllKeys";
            MKeyBinding mKeyBinding4 = this;
            this.var_gg_vape_mapping_MappingMethod_T = this.registerStaticMethod(string4, bl4, clazz4, classArray2);
            Class[] classArray3 = new Class[]{Integer.TYPE};
            Class<Void> clazz5 = Void.TYPE;
            boolean bl5 = true;
            String string5 = "onTick";
            MKeyBinding mKeyBinding5 = this;
            this.var_gg_vape_mapping_MappingMethod_z = this.registerStaticMethod(string5, bl5, clazz5, classArray3);
            if (GuiComponent.getLegacyComponentState() == null) {
                MKeyBinding.O(false);
            }
            this.var_gg_vape_mapping_MappingMethod_p = null;
            return;
        }
        Class[] classArray = new Class[]{};
        Class<Boolean> clazz6 = Boolean.TYPE;
        boolean bl6 = true;
        String string6 = "isPressed";
        MKeyBinding mKeyBinding6 = this;
        this.var_gg_vape_mapping_MappingMethod_p = this.Y(string6, bl6, clazz6, classArray);
        if (Wrapper.vapeInstance.isVanillaMinecraftPresent() && ForgeVersion.MC_1_16_5.v()) {
            Class[] classArray4 = new Class[]{};
            Class<Boolean> clazz7 = Boolean.TYPE;
            boolean bl7 = true;
            String string7 = ForgeVersion.MC_1_7_10.L() ? "getIsKeyPressed" : "isKeyDown";
            MKeyBinding mKeyBinding7 = this;
            this.var_gg_vape_mapping_MappingMethod_c = this.Y(string7, bl7, clazz7, classArray4);
        } else {
            Class[] classArray5 = new Class[]{};
            Class<Boolean> clazz8 = Boolean.TYPE;
            boolean bl8 = Wrapper.isNativeAvailable;
            String string8 = "func_151470_d";
            MKeyBinding mKeyBinding8 = this;
            this.var_gg_vape_mapping_MappingMethod_c = this.Y(string8, bl8, clazz8, classArray5);
        }
        Class[] classArray6 = new Class[]{};
        Class<Void> clazz9 = Void.TYPE;
        boolean bl9 = true;
        String string9 = "unPressAllKeys";
        MKeyBinding mKeyBinding9 = this;
        this.var_gg_vape_mapping_MappingMethod_T = this.registerStaticMethod(string9, bl9, clazz9, classArray6);
        if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray7 = new Class[]{Boolean.TYPE};
            Class<Void> clazz10 = Void.TYPE;
            boolean bl10 = true;
            String string10 = "setPressed";
            MKeyBinding mKeyBinding10 = this;
            this.var_gg_vape_mapping_MappingMethod_s = this.Y(string10, bl10, clazz10, classArray7);
            Class[] classArray8 = new Class[]{};
            Class<Void> clazz11 = Void.TYPE;
            boolean bl11 = true;
            String string11 = "unpressKey";
            MKeyBinding mKeyBinding11 = this;
            this.var_gg_vape_mapping_MappingMethod_X = this.Y(string11, bl11, clazz11, classArray8);
            Class clazz12 = MappedClasses.zp;
            boolean bl12 = true;
            String string12 = "keyCode";
            MKeyBinding mKeyBinding12 = this;
            this.var_gg_vape_mapping_MappingField_E = this.J(string12, bl12, clazz12);
            Class[] classArray9 = new Class[]{MappedClasses.zp, Boolean.TYPE};
            Class<Void> clazz13 = Void.TYPE;
            boolean bl13 = true;
            String string13 = "setKeyBindState";
            MKeyBinding mKeyBinding13 = this;
            this.var_gg_vape_mapping_MappingMethod_q = this.registerStaticMethod(string13, bl13, clazz13, classArray9);
            Class[] classArray10 = new Class[]{MappedClasses.zp};
            Class<Void> clazz14 = Void.TYPE;
            boolean bl14 = true;
            String string14 = "onTick";
            MKeyBinding mKeyBinding14 = this;
            this.var_gg_vape_mapping_MappingMethod_z = this.registerStaticMethod(string14, bl14, clazz14, classArray10);
        } else {
            Class[] classArray11 = new Class[]{};
            Class<Integer> clazz15 = Integer.TYPE;
            boolean bl15 = true;
            String string15 = "getKeyCode";
            MKeyBinding mKeyBinding15 = this;
            this.var_gg_vape_mapping_MappingMethod_Z = this.Y(string15, bl15, clazz15, classArray11);
            Class[] classArray12 = new Class[]{Integer.TYPE, Boolean.TYPE};
            Class<Void> clazz16 = Void.TYPE;
            boolean bl16 = true;
            String string16 = "setKeyBindState";
            MKeyBinding mKeyBinding16 = this;
            this.var_gg_vape_mapping_MappingMethod_q = this.registerStaticMethod(string16, bl16, clazz16, classArray12);
            Class[] classArray13 = new Class[]{Integer.TYPE};
            Class<Void> clazz17 = Void.TYPE;
            boolean bl17 = true;
            String string17 = "onTick";
            MKeyBinding mKeyBinding17 = this;
            this.var_gg_vape_mapping_MappingMethod_z = this.registerStaticMethod(string17, bl17, clazz17, classArray13);
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            MKeyBinding.O(true);
        }
    }

    public void g(int n, boolean bl) {
        this.var_gg_vape_mapping_MappingMethod_q.invokeVoid(null, n, bl);
    }

    public boolean M(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_p.invokeBoolean(object, new Object[0]);
    }

    public static boolean j() {
        boolean bl = MKeyBinding.U();
        return !bl;
    }

    public void S(Object object) {
        this.var_gg_vape_mapping_MappingMethod_X.invokeVoidNoArgs(object);
    }

    public static void O(boolean bl) {
        var_boolean_e = bl;
    }

    public void q(Object object, int n) {
        this.var_gg_vape_mapping_MappingField_B.setInt(object, n);
    }

    public int n(Object object) {
        return this.var_gg_vape_mapping_MappingField_B.getInt(object);
    }

    public void R() {
        this.var_gg_vape_mapping_MappingMethod_T.invokeVoidNoArgs(null);
    }

    public boolean S$src$Z$wvm5qt(Object object) {
        return this.var_gg_vape_mapping_MappingField_x.getBoolean(object);
    }

    public boolean a(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_c.invokeBoolean(object, new Object[0]);
    }

    public int B(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_Z.invokeInt(object, new Object[0]);
    }

    static {
        MKeyBinding.O(false);
    }
}

