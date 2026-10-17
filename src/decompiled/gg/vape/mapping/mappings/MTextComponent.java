/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MMutableTextComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MTextComponent
extends Mapping {
    private MappingMethod h;
    private MappingMethod N;
    private MappingMethod Q;

    public MTextComponent() {
        this(MMutableTextComponent.e());
    }

    private MTextComponent(int[] nArray) {
        super(MappedClasses.YO);
        MTextComponent mTextComponent;
        String string;
        boolean bl;
        Class clazz;
        Class[] classArray;
        if (nArray != null) {
            GuiComponent.setLegacyComponentState(new GuiComponent[2]);
            Class[] classArray2 = new Class[]{};
            Class<String> clazz2 = String.class;
            boolean bl2 = false;
            String string2 = "getString";
            MTextComponent mTextComponent2 = this;
            this.h = mTextComponent2.Y(string2, bl2, clazz2, classArray2);
            Class[] classArray22 = new Class[]{};
            Class clazz22 = MappedClasses.t;
            boolean bl22 = true;
            String string22 = "getVisualOrderText";
            Class clazz3 = MappedClasses.Yr;
            MTextComponent mTextComponent22 = this;
            this.N = this.registerInstanceMethodForOwner(clazz3, string22, bl22, clazz22, classArray22);
            return;
        }
        if (ForgeVersion.MC_1_20_6.d()) {
            classArray = new Class[]{};
            clazz = String.class;
            bl = false;
            string = "toString";
            mTextComponent = this;
            this.h = mTextComponent.Y(string, bl, clazz, classArray);
            Class[] classArray3 = new Class[]{MappedClasses.YT};
            Class clazz4 = MappedClasses.YO;
            boolean bl3 = true;
            String string3 = "create";
            MTextComponent mTextComponent3 = this;
            this.Q = this.registerStaticMethod(string3, bl3, clazz4, classArray3);
        } else {
            classArray = new Class[]{};
            clazz = String.class;
            bl = false;
            string = "getString";
            mTextComponent = this;
            this.h = mTextComponent.Y(string, bl, clazz, classArray);
        }
        classArray = new Class[]{};
        clazz = MappedClasses.t;
        bl = true;
        string = "getVisualOrderText";
        Class clazz5 = MappedClasses.Yr;
        MTextComponent mTextComponent3 = this;
        this.N = mTextComponent3.registerInstanceMethodForOwner(clazz5, string, bl, clazz, classArray);
    }

    private Object r(Object object) {
        return this.Q.invokeObject(null, object);
    }

    public static Object n(MTextComponent mTextComponent, Object object) {
        return mTextComponent.r(object);
    }

    public static Object B(MTextComponent mTextComponent, Object object) {
        return mTextComponent.n(object);
    }

    private Object Z(Object object) {
        return this.h.invokeObject(object, new Object[0]);
    }

    private Object n(Object object) {
        return this.h.invokeObject(object, new Object[0]);
    }

    public static Object s(MTextComponent mTextComponent, Object object) {
        return mTextComponent.Z(object);
    }
}

