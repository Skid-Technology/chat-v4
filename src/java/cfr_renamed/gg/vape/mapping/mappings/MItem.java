/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.mapping.mappings.MItemStack;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ITextComponent;
import java.util.List;
import java.util.Map;

public class MItem
extends Mapping {
    private MappingMethod var_gg_vape_mapping_MappingMethod_W;
    final public MappingMethod var_gg_vape_mapping_MappingMethod_f;
    private MappingMethod var_gg_vape_mapping_MappingMethod_G;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_d;
    private MappingField var_gg_vape_mapping_MappingField_b;
    final public MappingMethod var_gg_vape_mapping_MappingMethod_F;
    private MappingMethod var_gg_vape_mapping_MappingMethod_e;
    private MappingMethod var_gg_vape_mapping_MappingMethod_N;
    private MappingMethod var_gg_vape_mapping_MappingMethod_Z;
    private MappingField var_gg_vape_mapping_MappingField_v;
    private MappingMethod var_gg_vape_mapping_MappingMethod_S;
    private MappingMethod var_gg_vape_mapping_MappingMethod_c;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_h;
    private MappingField var_gg_vape_mapping_MappingField_M;
    private MappingMethod var_gg_vape_mapping_MappingMethod_Q;
    private MappingMethod var_gg_vape_mapping_MappingMethod_w;
    final public MappingMethod var_gg_vape_mapping_MappingMethod_p;
    private final MappingField var_gg_vape_mapping_MappingField_r;

    public static Object r(MItem mItem, String string) {
        return mItem.O(string);
    }

    private Object D(Object object, Object object2) {
        return this.var_gg_vape_mapping_MappingMethod_h.invokeObject(object, object2);
    }

    private Object W(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_c.invokeObject(object, new Object[0]);
    }

    private int M(Object object) {
        return this.var_gg_vape_mapping_MappingField_b.getInt(object);
    }

    public static Object Z(MItem mItem) {
        return mItem.D();
    }

    public static Object i(MItem mItem, Object object) {
        return mItem.W(object);
    }

    public static String X(MItem mItem, Object object) {
        return mItem.w(object);
    }

    public static Object h(MItem mItem, Object object, Object object2) {
        return mItem.D(object, object2);
    }

    public static Object K(MItem mItem, Object object) {
        return mItem.B(object);
    }

    private boolean X(Object object) {
        return this.var_gg_vape_mapping_MappingField_M.getBoolean(object);
    }

    private String T(Object object, Object object2) {
        Object object3 = this.var_gg_vape_mapping_MappingMethod_d.invokeObject(object, object2);
        if (ForgeVersion.MC_1_16_5.d()) {
            ITextComponent iTextComponent = new ITextComponent(object3);
            return iTextComponent.getFormattedText();
        }
        return object3.toString();
    }

    public static String k(MItem mItem, Object object) {
        return mItem.W$src$Ljava_lang_String_$1nf3kj0(object);
    }

    public static Object F(MItem mItem, Object object) {
        return mItem.q(object);
    }

    private int R(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_F.invokeInt(null, object);
    }

    public static int Q(MItem mItem, Object object, Object object2) {
        return mItem.A(object, object2);
    }

    private Map d() {
        return (Map)this.var_gg_vape_mapping_MappingField_v.getObject(null);
    }

    public static int l(MItem mItem, Object object) {
        return mItem.R(object);
    }

    public static Object z(MItem mItem, Object object) {
        return mItem.t(object);
    }

    public static boolean N(MItem mItem, Object object) {
        return mItem.X(object);
    }

    private Object q(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_Z.invokeObject(object, new Object[0]);
    }

    private String w(Object object) {
        return (String)this.var_gg_vape_mapping_MappingMethod_N.invokeObject(object, new Object[0]);
    }

    private int A(Object object, Object object2) {
        return this.var_gg_vape_mapping_MappingMethod_e.invokeInt(object, object2);
    }

    public static String o(MItem mItem, Object object, Object object2) {
        return mItem.T(object, object2);
    }

    public int c(Object object, Object object2, Object object3) {
        return this.var_gg_vape_mapping_MappingMethod_e.invokeInt(object, object2, object3);
    }

    public static Object W(MItem mItem, int n) {
        return mItem.J(n);
    }

    private Object J(int n) {
        return this.var_gg_vape_mapping_MappingMethod_f.invokeObject(null, n);
    }

    private Object D() {
        return this.var_gg_vape_mapping_MappingField_r.getObject(null);
    }

    public static int d(MItem mItem, Object object) {
        return mItem.M(object);
    }

    private Object t(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_w.invokeObject(object, new Object[0]);
    }

    public MItem() {
        this(MItemStack.M());
    }

    private MItem(int n) {
        super(MappedClasses.lb);
        MItem mItem;
        Class<Integer> clazz;
        MItem mItem2;
        String string;
        boolean bl;
        Class clazz2;
        Class[] classArray;
        int n2 = n;
        if (n2 != 0) {
            if (ForgeVersion.MC_1_21_0.d()) {
                Class<Integer> clazz3 = Integer.TYPE;
                boolean bl2 = true;
                String string2 = "maxDamage";
                MItem mItem3 = this;
                this.var_gg_vape_mapping_MappingField_b = mItem3.J(string2, bl2, clazz3);
            }
            Class[] classArray2 = new Class[]{};
            Class clazz4 = MappedClasses.VK;
            boolean bl3 = true;
            String string3 = "getDefaultInstance";
            MItem mItem4 = this;
            this.var_gg_vape_mapping_MappingMethod_w = mItem4.Y(string3, bl3, clazz4, classArray2);
            Class[] classArray22 = new Class[]{MappedClasses.lb};
            Class<Integer> clazz22 = Integer.TYPE;
            boolean bl2 = true;
            String string2 = "getIdFromItem";
            MItem mItem22 = this;
            this.var_gg_vape_mapping_MappingMethod_F = this.registerStaticMethod(string2, bl2, clazz22, classArray22);
            Class[] classArray3 = new Class[]{Integer.TYPE};
            Class clazz3 = MappedClasses.lb;
            boolean bl32 = true;
            String string32 = "getItemById";
            MItem mItem3 = this;
            this.var_gg_vape_mapping_MappingMethod_f = this.registerStaticMethod(string32, bl32, clazz3, classArray3);
            if (ForgeVersion.MC_1_7_10.L()) {
                Class clazz42 = MappedClasses.lz;
                boolean bl4 = true;
                String string4 = "ITEM";
                Class clazz5 = MappedClasses.var_java_lang_Class_R;
                MItem mItem42 = this;
                mItem42.registerStaticFieldForOwner(clazz5, string4, bl4, clazz42);
            }
            Class clazz6 = MappedClasses.lz;
            boolean bl5 = true;
            String string5 = "ITEM";
            Class clazz7 = MappedClasses.Fk;
            MItem mItem5 = this;
            this.var_gg_vape_mapping_MappingField_r = this.registerStaticFieldForOwner(clazz7, string5, bl5, clazz6);
            Class[] classArray4 = new Class[]{MappedClasses.zX, MappedClasses.Vd};
            Class<Void> clazz8 = Void.TYPE;
            boolean bl6 = true;
            String string6 = "fillItemGroup";
            MItem mItem6 = this;
            this.var_gg_vape_mapping_MappingMethod_Q = this.Y(string6, bl6, clazz8, classArray4);
            if (ForgeVersion.MC_1_21_0.v()) {
                Class[] classArray5 = new Class[]{MappedClasses.VK};
                Class clazz9 = MappedClasses.Yr;
                boolean bl7 = true;
                String string7 = "getDisplayName";
                MItem mItem7 = this;
                mItem7.Y(string7, bl7, clazz9, classArray5);
                Class[] classArray6 = new Class[]{MappedClasses.VK};
                Class clazz10 = MappedClasses.Yr;
                boolean bl8 = true;
                String string8 = "getDisplayName";
                MItem mItem8 = this;
                mItem8.Y(string8, bl8, clazz10, classArray6);
            }
            Class[] classArray7 = new Class[]{MappedClasses.VK};
            Class<String> clazz11 = String.class;
            boolean bl9 = true;
            String string9 = "getItemStackDisplayName";
            MItem mItem9 = this;
            this.var_gg_vape_mapping_MappingMethod_d = this.Y(string9, bl9, clazz11, classArray7);
            Class[] classArray8 = new Class[]{MappedClasses.VK};
            Class<String> clazz12 = String.class;
            boolean bl10 = true;
            String string10 = "getUnlocalizedNameInefficiently";
            MItem mItem10 = this;
            this.var_gg_vape_mapping_MappingMethod_h = this.Y(string10, bl10, clazz12, classArray8);
            if (ForgeVersion.MC_1_8_9.d()) {
                Class[] classArray9 = new Class[]{MappedClasses.YU, MappedClasses.Yl, Boolean.TYPE};
                Class clazz13 = MappedClasses.DT;
                boolean bl11 = true;
                String string11 = "rayTrace";
                MItem mItem11 = this;
                mItem11.Y(string11, bl11, clazz13, classArray9);
            }
            Class[] classArray10 = new Class[]{MappedClasses.YU, MappedClasses.Yl, Boolean.TYPE};
            Class clazz14 = MappedClasses.DT;
            boolean bl12 = true;
            String string12 = "getMovingObjectPositionFromPlayer";
            MItem mItem12 = this;
            this.var_gg_vape_mapping_MappingMethod_p = this.Y(string12, bl12, clazz14, classArray10);
            Class[] classArray11 = new Class[]{MappedClasses.VK};
            Class<Integer> clazz15 = Integer.TYPE;
            String string13 = "getMaxItemUseDuration";
            MItem mItem13 = this;
            this.var_gg_vape_mapping_MappingMethod_e = ((MappingMethodBuilder)this.methodBuilder(string13, clazz15, classArray11).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "getUseDuration")).setParameterTypesForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.VK, MappedClasses.zm).buildMethod();
            if (GuiComponent.getLegacyComponentState() == null) {
                MItemStack.E(++n2);
            }
            return;
        }
        if (ForgeVersion.MC_1_21_0.d()) {
            classArray = new Class[]{};
            clazz2 = String.class;
            bl = true;
            string = "getDescriptionId";
            mItem2 = this;
            this.var_gg_vape_mapping_MappingMethod_N = mItem2.Y(string, bl, clazz2, classArray);
        }
        if (ForgeVersion.MC_1_7_10.L() && Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
            clazz = Integer.TYPE;
            boolean bl4 = true;
            String string4 = "maxDurability";
            mItem = this;
            this.var_gg_vape_mapping_MappingField_b = mItem.J(string4, bl4, clazz);
        } else if (ForgeVersion.MC_1_20_6.v()) {
            clazz = Integer.TYPE;
            boolean bl5 = true;
            String string5 = "maxDamage";
            mItem = this;
            this.var_gg_vape_mapping_MappingField_b = mItem.J(string5, bl5, clazz);
        } else {
            classArray = new Class[]{};
            clazz2 = MappedClasses.VK;
            bl = true;
            string = "getDefaultInstance";
            mItem2 = this;
            this.var_gg_vape_mapping_MappingMethod_w = mItem2.Y(string, bl, clazz2, classArray);
        }
        classArray = new Class[]{MappedClasses.lb};
        Class<Integer> clazz5 = Integer.TYPE;
        boolean bl6 = true;
        string = "getIdFromItem";
        mItem2 = this;
        this.var_gg_vape_mapping_MappingMethod_F = mItem2.registerStaticMethod(string, bl6, clazz5, classArray);
        Class[] classArray12 = new Class[]{Integer.TYPE};
        Class clazz16 = MappedClasses.lb;
        boolean bl13 = true;
        String string14 = "getItemById";
        MItem mItem14 = this;
        this.var_gg_vape_mapping_MappingMethod_f = this.registerStaticMethod(string14, bl13, clazz16, classArray12);
        if (ForgeVersion.MC_1_7_10.L()) {
            Class[] classArray13 = new Class[]{};
            Class<String> clazz17 = String.class;
            boolean bl14 = true;
            String string15 = "getIconString";
            MItem mItem15 = this;
            this.var_gg_vape_mapping_MappingMethod_S = this.Y(string15, bl14, clazz17, classArray13);
        } else if (ForgeVersion.MC_1_16_5.v()) {
            Class[] classArray14 = new Class[]{String.class};
            Class clazz18 = MappedClasses.lb;
            boolean bl15 = true;
            String string16 = "getByNameOrId";
            MItem mItem16 = this;
            this.var_gg_vape_mapping_MappingMethod_W = this.registerStaticMethod(string16, bl15, clazz18, classArray14);
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            if (ForgeVersion.MC_1_16_5.d()) {
                if (ForgeVersion.MC_1_20_6.d()) {
                    Class clazz19 = MappedClasses.lz;
                    boolean bl16 = true;
                    String string17 = "ITEM";
                    Class clazz20 = MappedClasses.var_java_lang_Class_R;
                    MItem mItem17 = this;
                    this.var_gg_vape_mapping_MappingField_r = this.registerStaticFieldForOwner(clazz20, string17, bl16, clazz19);
                } else {
                    Class clazz21 = MappedClasses.lz;
                    boolean bl17 = true;
                    String string18 = "ITEM";
                    Class clazz22 = MappedClasses.Fk;
                    MItem mItem18 = this;
                    this.var_gg_vape_mapping_MappingField_r = this.registerStaticFieldForOwner(clazz22, string18, bl17, clazz21);
                    Class[] classArray15 = new Class[]{MappedClasses.zX, MappedClasses.Vd};
                    Class<Void> clazz23 = Void.TYPE;
                    boolean bl18 = true;
                    String string19 = "fillItemGroup";
                    MItem mItem19 = this;
                    this.var_gg_vape_mapping_MappingMethod_Q = this.Y(string19, bl18, clazz23, classArray15);
                }
                if (ForgeVersion.MC_1_21_0.v()) {
                    Class[] classArray16 = new Class[]{};
                    Class clazz24 = MappedClasses.Yr;
                    boolean bl19 = true;
                    String string20 = "getName";
                    MItem mItem20 = this;
                    this.var_gg_vape_mapping_MappingMethod_c = this.Y(string20, bl19, clazz24, classArray16);
                }
            } else {
                Class clazz25 = MappedClasses.zz;
                boolean bl20 = true;
                String string21 = "REGISTRY";
                MItem mItem21 = this;
                this.var_gg_vape_mapping_MappingField_r = this.registerStaticField(string21, bl20, clazz25);
                Class[] classArray17 = new Class[]{MappedClasses.zX, MappedClasses.Vd};
                Class<Void> clazz26 = Void.TYPE;
                boolean bl21 = true;
                String string22 = "getSubItems";
                MItem mItem22 = this;
                this.var_gg_vape_mapping_MappingMethod_Q = this.Y(string22, bl21, clazz26, classArray17);
            }
        } else {
            Class clazz27 = MappedClasses.zz;
            boolean bl22 = true;
            String string23 = "itemRegistry";
            MItem mItem23 = this;
            this.var_gg_vape_mapping_MappingField_r = this.registerStaticField(string23, bl22, clazz27);
            Class[] classArray18 = new Class[]{MappedClasses.lb, MappedClasses.zX, List.class};
            Class<Void> clazz28 = Void.TYPE;
            boolean bl23 = true;
            String string24 = "getSubItems";
            MItem mItem24 = this;
            this.var_gg_vape_mapping_MappingMethod_Q = this.Y(string24, bl23, clazz28, classArray18);
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray19 = new Class[]{MappedClasses.VK};
            Class clazz29 = MappedClasses.Yr;
            boolean bl24 = true;
            String string25 = "getDisplayName";
            MItem mItem25 = this;
            this.var_gg_vape_mapping_MappingMethod_d = this.Y(string25, bl24, clazz29, classArray19);
            Class[] classArray20 = new Class[]{MappedClasses.VK};
            Class clazz30 = MappedClasses.Yr;
            boolean bl25 = true;
            String string26 = "getDisplayName";
            MItem mItem26 = this;
            this.var_gg_vape_mapping_MappingMethod_h = this.Y(string26, bl25, clazz30, classArray20);
        } else {
            Class[] classArray21 = new Class[]{MappedClasses.VK};
            Class<String> clazz31 = String.class;
            boolean bl26 = true;
            String string27 = "getItemStackDisplayName";
            MItem mItem27 = this;
            this.var_gg_vape_mapping_MappingMethod_d = this.Y(string27, bl26, clazz31, classArray21);
            Class[] classArray22 = new Class[]{MappedClasses.VK};
            Class<String> clazz32 = String.class;
            boolean bl27 = true;
            String string28 = "getUnlocalizedNameInefficiently";
            MItem mItem28 = this;
            this.var_gg_vape_mapping_MappingMethod_h = this.Y(string28, bl27, clazz32, classArray22);
        }
        if (ForgeVersion.MC_1_8_9.d()) {
            Class<Map> clazz33 = Map.class;
            boolean bl28 = true;
            String string29 = "BLOCK_TO_ITEM";
            MItem mItem29 = this;
            this.var_gg_vape_mapping_MappingField_v = this.registerStaticField(string29, bl28, clazz33);
        }
        if (ForgeVersion.MC_1_16_5.v()) {
            Class<Boolean> clazz34 = Boolean.TYPE;
            boolean bl29 = true;
            String string30 = "hasSubtypes";
            MItem mItem30 = this;
            this.var_gg_vape_mapping_MappingField_M = this.J(string30, bl29, clazz34);
        }
        if (ForgeVersion.MC_1_20_6.d()) {
            Class[] classArray23 = new Class[]{};
            Class clazz35 = MappedClasses.zD;
            boolean bl30 = true;
            String string31 = "components";
            MItem mItem31 = this;
            this.var_gg_vape_mapping_MappingMethod_G = this.Y(string31, bl30, clazz35, classArray23);
        }
        if (ForgeVersion.MC_26_1.d()) {
            Class[] classArray24 = new Class[]{};
            Class clazz36 = MappedClasses.Vo;
            boolean bl31 = true;
            String string32 = "builtInRegistryHolder";
            MItem mItem32 = this;
            this.var_gg_vape_mapping_MappingMethod_Z = this.Y(string32, bl31, clazz36, classArray24);
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            Class[] classArray25 = new Class[]{MappedClasses.YU, MappedClasses.Yl, MappedClasses.Y9};
            Class clazz37 = MappedClasses.qF;
            boolean bl32 = true;
            String string33 = "getPlayerPOVHitResult";
            MItem mItem33 = this;
            this.var_gg_vape_mapping_MappingMethod_p = this.registerStaticMethod(string33, bl32, clazz37, classArray25);
        } else if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray26 = new Class[]{MappedClasses.YU, MappedClasses.Yl, MappedClasses.Dm};
            Class clazz38 = MappedClasses.qF;
            boolean bl33 = true;
            String string34 = "getPlayerPOVHitResult";
            MItem mItem34 = this;
            this.var_gg_vape_mapping_MappingMethod_p = this.registerStaticMethod(string34, bl33, clazz38, classArray26);
        } else if (ForgeVersion.MC_1_12_2.d()) {
            Class[] classArray27 = new Class[]{MappedClasses.YU, MappedClasses.Yl, Boolean.TYPE};
            Class clazz39 = MappedClasses.DT;
            boolean bl34 = true;
            String string35 = "rayTrace";
            MItem mItem35 = this;
            this.var_gg_vape_mapping_MappingMethod_p = this.Y(string35, bl34, clazz39, classArray27);
        } else {
            Class[] classArray28 = new Class[]{MappedClasses.YU, MappedClasses.Yl, Boolean.TYPE};
            Class clazz40 = MappedClasses.DT;
            boolean bl35 = true;
            String string36 = "getMovingObjectPositionFromPlayer";
            MItem mItem36 = this;
            this.var_gg_vape_mapping_MappingMethod_p = this.Y(string36, bl35, clazz40, classArray28);
        }
        Class[] classArray29 = new Class[]{MappedClasses.VK};
        Class<Integer> clazz41 = Integer.TYPE;
        String string37 = "getMaxItemUseDuration";
        MItem mItem37 = this;
        this.var_gg_vape_mapping_MappingMethod_e = ((MappingMethodBuilder)this.methodBuilder(string37, clazz41, classArray29).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "getUseDuration")).setParameterTypesForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.VK, MappedClasses.zm).buildMethod();
        if (GuiComponent.getLegacyComponentState() == null) {
            MItemStack.E(++n2);
        }
    }

    public static Map U(MItem mItem) {
        return mItem.d();
    }

    private String W$src$Ljava_lang_String_$1nf3kj0(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_S.invokeObject(object, new Object[0]).toString();
    }

    private Object O(String string) {
        return this.var_gg_vape_mapping_MappingMethod_W.invokeObject(null, string);
    }

    public static void V(MItem mItem, Object object, Object object2, Object object3, List list) {
        mItem.h(object, object2, object3, list);
    }

    private void h(Object object, Object object2, Object object3, List list) {
        if (ForgeVersion.MC_1_12_2.d()) {
            this.var_gg_vape_mapping_MappingMethod_Q.invokeVoid(object, object3, list);
        } else {
            this.var_gg_vape_mapping_MappingMethod_Q.invokeVoid(object, object2, object3, list);
        }
    }

    private Object B(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_G.invokeObject(object, new Object[0]);
    }
}

