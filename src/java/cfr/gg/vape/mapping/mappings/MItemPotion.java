/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MItemStack;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.List;

public class MItemPotion
extends Mapping {
    private final MappingMethod f;
    private MappingMethod G;

    public static Object M(MItemPotion mItemPotion, Object object, Object object2) {
        return mItemPotion.V(object, object2);
    }

    private List M(Object object, Object object2) {
        return (List)this.G.invokeObject(object, object2);
    }

    public MItemPotion() {
        this(MItemStack.f());
    }

    private MItemPotion(int n) {
        super(MappedClasses.Di);
        MItemPotion mItemPotion;
        MItemPotion mItemPotion2;
        String string;
        boolean bl;
        Class<String> clazz;
        Class[] classArray;
        int n2 = n;
        if (ForgeVersion.MC_1_12_2.d()) {
            if (ForgeVersion.MC_1_20_6.v()) {
                classArray = new Class[]{MappedClasses.VK};
                clazz = List.class;
                bl = true;
                string = "getEffectsFromStack";
                Class clazz2 = MappedClasses.uV;
                mItemPotion2 = this;
                this.G = mItemPotion2.registerStaticMethodForOwner(clazz2, string, bl, clazz, classArray);
            }
        } else {
            classArray = new Class[]{MappedClasses.VK};
            clazz = List.class;
            bl = true;
            string = "getEffects";
            mItemPotion = this;
            this.G = mItemPotion.Y(string, bl, clazz, classArray);
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            classArray = new Class[]{MappedClasses.VK};
            clazz = MappedClasses.Yr;
            bl = true;
            string = "getDisplayName";
            Class clazz3 = MappedClasses.lb;
            mItemPotion2 = this;
            this.f = mItemPotion2.registerInstanceMethodForOwner(clazz3, string, bl, clazz, classArray);
        } else {
            classArray = new Class[]{MappedClasses.VK};
            clazz = String.class;
            bl = true;
            string = "getItemStackDisplayName";
            mItemPotion = this;
            this.f = mItemPotion.Y(string, bl, clazz, classArray);
        }
    }

    public static List R(MItemPotion mItemPotion, Object object, Object object2) {
        return mItemPotion.M(object, object2);
    }

    private Object V(Object object, Object object2) {
        return this.f.invokeObject(object, object2);
    }

    public static String B(MItemPotion mItemPotion, Object object, Object object2) {
        return mItemPotion.W(object, object2);
    }

    private String W(Object object, Object object2) {
        return (String)this.f.invokeObject(object, object2);
    }
}

