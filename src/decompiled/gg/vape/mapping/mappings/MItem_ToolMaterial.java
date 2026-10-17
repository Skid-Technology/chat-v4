/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.mappings.MItemStack;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MItem_ToolMaterial
extends Mapping {
    private final MappingField U;
    private final MappingField n;
    private final MappingField q;
    private final MappingField r;
    private final MappingField h;

    public static float j(MItem_ToolMaterial mItem_ToolMaterial, Object object) {
        return mItem_ToolMaterial.a(object);
    }

    public static Object U(MItem_ToolMaterial mItem_ToolMaterial) {
        return mItem_ToolMaterial.D();
    }

    public MItem_ToolMaterial() {
        this(MItemStack.M());
    }

    private MItem_ToolMaterial(int n) {
        super(MappedClasses.F5);
        MItem_ToolMaterial mItem_ToolMaterial;
        String string;
        boolean bl;
        Class clazz;
        if (n != 0) {
            Class<Float> clazz2 = Float.TYPE;
            boolean bl2 = true;
            String string2 = ForgeVersion.c() >= 23 ? "attackDamage" : "damageVsEntity";
            MItem_ToolMaterial mItem_ToolMaterial2 = this;
            this.r = mItem_ToolMaterial2.J(string2, bl2, clazz2);
            Class clazz22 = MappedClasses.F5;
            boolean bl22 = Wrapper.isNativeAvailable;
            String string22 = "WOOD";
            MItem_ToolMaterial mItem_ToolMaterial22 = this;
            this.h = this.registerStaticField(string22, bl22, clazz22);
            Class clazz3 = MappedClasses.F5;
            boolean bl3 = Wrapper.isNativeAvailable;
            String string3 = "STONE";
            MItem_ToolMaterial mItem_ToolMaterial3 = this;
            this.U = this.registerStaticField(string3, bl3, clazz3);
            Class clazz4 = MappedClasses.F5;
            boolean bl4 = Wrapper.isNativeAvailable;
            String string4 = "IRON";
            MItem_ToolMaterial mItem_ToolMaterial4 = this;
            this.n = this.registerStaticField(string4, bl4, clazz4);
            Class clazz5 = MappedClasses.F5;
            boolean bl5 = Wrapper.isNativeAvailable;
            String string5 = "GOLD";
            MItem_ToolMaterial mItem_ToolMaterial5 = this;
            this.q = this.registerStaticField(string5, bl5, clazz5);
            return;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            clazz = Float.TYPE;
            bl = true;
            string = "attackDamage";
            mItem_ToolMaterial = this;
            this.r = mItem_ToolMaterial.J(string, bl, clazz);
        } else {
            clazz = Float.TYPE;
            bl = true;
            string = ForgeVersion.c() >= 23 ? "attackDamage" : "damageVsEntity";
            mItem_ToolMaterial = this;
            this.r = mItem_ToolMaterial.J(string, bl, clazz);
        }
        clazz = MappedClasses.F5;
        bl = Wrapper.isNativeAvailable;
        string = "WOOD";
        mItem_ToolMaterial = this;
        this.h = mItem_ToolMaterial.registerStaticField(string, bl, clazz);
        Class clazz6 = MappedClasses.F5;
        boolean bl6 = Wrapper.isNativeAvailable;
        String string6 = "STONE";
        MItem_ToolMaterial mItem_ToolMaterial6 = this;
        this.U = this.registerStaticField(string6, bl6, clazz6);
        Class clazz7 = MappedClasses.F5;
        boolean bl7 = Wrapper.isNativeAvailable;
        String string7 = "IRON";
        MItem_ToolMaterial mItem_ToolMaterial7 = this;
        this.n = this.registerStaticField(string7, bl7, clazz7);
        Class clazz8 = MappedClasses.F5;
        boolean bl8 = Wrapper.isNativeAvailable;
        String string8 = "GOLD";
        MItem_ToolMaterial mItem_ToolMaterial8 = this;
        this.q = this.registerStaticField(string8, bl8, clazz8);
    }

    private Object C() {
        return this.h.getObject(null);
    }

    private Object p() {
        return this.q.getObject(null);
    }

    public static Object M(MItem_ToolMaterial mItem_ToolMaterial) {
        return mItem_ToolMaterial.C();
    }

    private float a(Object object) {
        return this.r.getFloat(object);
    }

    public static Object r(MItem_ToolMaterial mItem_ToolMaterial) {
        return mItem_ToolMaterial.S();
    }

    public static Object H(MItem_ToolMaterial mItem_ToolMaterial) {
        return mItem_ToolMaterial.p();
    }

    private Object D() {
        return this.n.getObject(null);
    }

    private Object S() {
        return this.U.getObject(null);
    }
}

