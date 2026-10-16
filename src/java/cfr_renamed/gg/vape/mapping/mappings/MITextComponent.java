/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.List;

public class MITextComponent
extends Mapping {
    private final MappingMethod var_gg_vape_mapping_MappingMethod_Q;
    private MappingMethod var_gg_vape_mapping_MappingMethod_S;
    private MappingMethod var_gg_vape_mapping_MappingMethod_C;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_v;
    private MappingMethod var_gg_vape_mapping_MappingMethod_H;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_g;
    private MappingMethod var_gg_vape_mapping_MappingMethod_L;
    private MappingMethod var_gg_vape_mapping_MappingMethod_l;

    private String H(Object object) {
        return (String)this.var_gg_vape_mapping_MappingMethod_Q.invokeObject(object, new Object[0]);
    }

    private String y(Object object) {
        return (String)this.var_gg_vape_mapping_MappingMethod_g.invokeObject(object, new Object[0]);
    }

    private Object O(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_C.invokeObject(object, new Object[0]);
    }

    public static Object y(MITextComponent mITextComponent, Object object) {
        return mITextComponent.O(object);
    }

    public static Object r(MITextComponent mITextComponent, Object object) {
        return mITextComponent.o(object);
    }

    private Object A(String string) {
        return this.var_gg_vape_mapping_MappingMethod_S.invokeObject(null, string);
    }

    public static String b(MITextComponent mITextComponent, Object object) {
        return mITextComponent.H(object);
    }

    public static Object J(MITextComponent mITextComponent, String string) {
        return mITextComponent.w(string);
    }

    public static Object e(MITextComponent mITextComponent, Object object) {
        return mITextComponent.l(object);
    }

    private Object l(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_H.invokeObject(object, new Object[0]);
    }

    public static Object f(MITextComponent mITextComponent, String string) {
        return mITextComponent.A(string);
    }

    private List<Object> B(Object object) {
        return (List)this.var_gg_vape_mapping_MappingMethod_v.invokeObject(object, new Object[0]);
    }

    public MITextComponent() {
        this(BlockData.W());
    }

    private MITextComponent(String[] stringArray) {
        super(MappedClasses.Yr);
        MITextComponent mITextComponent;
        String string;
        boolean bl;
        Class clazz;
        Class[] classArray;
        String[] stringArray2 = stringArray;
        if (ForgeVersion.MC_1_21_0.d()) {
            classArray = new Class[]{String.class};
            clazz = MappedClasses.qQ;
            bl = true;
            string = "translatable";
            mITextComponent = this;
            this.var_gg_vape_mapping_MappingMethod_l = mITextComponent.registerStaticMethod(string, bl, clazz, classArray);
        }
        if (ForgeVersion.MC_1_20_6.d()) {
            classArray = new Class[]{};
            clazz = MappedClasses.YT;
            bl = true;
            string = "getContents";
            mITextComponent = this;
            this.var_gg_vape_mapping_MappingMethod_L = mITextComponent.Y(string, bl, clazz, classArray);
            Class[] classArray2 = new Class[]{};
            Class clazz2 = MappedClasses.Va;
            boolean bl2 = true;
            String string2 = "getStyle";
            MITextComponent mITextComponent2 = this;
            this.var_gg_vape_mapping_MappingMethod_H = this.Y(string2, bl2, clazz2, classArray2);
            Class[] classArray3 = new Class[]{String.class};
            Class<?> clazz3 = MappedClasses.uM;
            boolean bl3 = true;
            String string3 = "literal";
            MITextComponent mITextComponent3 = this;
            this.var_gg_vape_mapping_MappingMethod_S = this.registerStaticMethod(string3, bl3, clazz3, classArray3);
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            classArray = new Class[]{};
            clazz = String.class;
            bl = ForgeVersion.MC_1_20_6.v();
            string = "getString";
            mITextComponent = this;
            this.var_gg_vape_mapping_MappingMethod_Q = mITextComponent.Y(string, bl, clazz, classArray);
            if (ForgeVersion.MC_1_20_6.d()) {
                Class[] classArray4 = new Class[]{};
                Class clazz4 = MappedClasses.YT;
                boolean bl4 = true;
                String string4 = "getContents";
                MITextComponent mITextComponent4 = this;
                this.var_gg_vape_mapping_MappingMethod_g = this.Y(string4, bl4, clazz4, classArray4);
            } else {
                Class[] classArray5 = new Class[]{};
                Class<String> clazz5 = String.class;
                boolean bl5 = true;
                String string5 = "getUnformattedComponentText";
                MITextComponent mITextComponent5 = this;
                this.var_gg_vape_mapping_MappingMethod_g = this.Y(string5, bl5, clazz5, classArray5);
            }
        } else {
            classArray = new Class[]{};
            clazz = String.class;
            bl = true;
            string = "getFormattedText";
            mITextComponent = this;
            this.var_gg_vape_mapping_MappingMethod_Q = mITextComponent.Y(string, bl, clazz, classArray);
            Class[] classArray6 = new Class[]{};
            Class<String> clazz6 = String.class;
            boolean bl6 = true;
            String string6 = "getUnformattedText";
            MITextComponent mITextComponent6 = this;
            this.var_gg_vape_mapping_MappingMethod_g = this.Y(string6, bl6, clazz6, classArray6);
        }
        classArray = new Class[]{};
        clazz = List.class;
        bl = true;
        string = "getSiblings";
        mITextComponent = this;
        this.var_gg_vape_mapping_MappingMethod_v = mITextComponent.Y(string, bl, clazz, classArray);
        if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray7 = new Class[]{};
            Class clazz7 = MappedClasses.YO;
            boolean bl7 = true;
            String string7 = "copy";
            MITextComponent mITextComponent7 = this;
            this.var_gg_vape_mapping_MappingMethod_C = this.Y(string7, bl7, clazz7, classArray7);
        }
    }

    private Object o(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_L.invokeObject(object, new Object[0]);
    }

    private Object w(String string) {
        return this.var_gg_vape_mapping_MappingMethod_l.invokeObject(null, string);
    }

    public static List V(MITextComponent mITextComponent, Object object) {
        return mITextComponent.B(object);
    }

    public static String A(MITextComponent mITextComponent, Object object) {
        return mITextComponent.y(object);
    }
}

