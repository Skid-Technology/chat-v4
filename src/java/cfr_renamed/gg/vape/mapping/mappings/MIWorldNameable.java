/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MWorld;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MIWorldNameable
extends Mapping {
    private final MappingMethod var_gg_vape_mapping_MappingMethod_Z;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_C;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_c;

    public Object A(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_c.invokeObject(object, new Object[0]);
    }

    public boolean V(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_Z.invokeBoolean(object, new Object[0]);
    }

    public MIWorldNameable() {
        this(MWorld.E$src$Z$o9fqyv());
    }

    private MIWorldNameable(boolean bl) {
        super(MappedClasses.Yp);
        if (bl) {
            MIWorldNameable mIWorldNameable;
            String string;
            Class clazz;
            Class[] classArray;
            if (ForgeVersion.MC_1_16_5.d()) {
                classArray = new Class[]{};
                clazz = MappedClasses.Yr;
                boolean bl2 = true;
                string = "getName";
                mIWorldNameable = this;
                this.var_gg_vape_mapping_MappingMethod_C = mIWorldNameable.Y(string, bl2, clazz, classArray);
            } else {
                classArray = new Class[]{};
                clazz = String.class;
                boolean bl3 = true;
                string = "getName";
                mIWorldNameable = this;
                this.var_gg_vape_mapping_MappingMethod_C = mIWorldNameable.Y(string, bl3, clazz, classArray);
            }
            classArray = new Class[]{};
            clazz = Boolean.TYPE;
            boolean bl4 = true;
            string = "hasCustomName";
            mIWorldNameable = this;
            this.var_gg_vape_mapping_MappingMethod_Z = mIWorldNameable.Y(string, bl4, clazz, classArray);
            Class[] classArray2 = new Class[]{};
            Class clazz2 = MappedClasses.Yr;
            boolean bl5 = true;
            String string2 = "getDisplayName";
            MIWorldNameable mIWorldNameable2 = this;
            this.var_gg_vape_mapping_MappingMethod_c = this.Y(string2, bl5, clazz2, classArray2);
            if (GuiComponent.getLegacyComponentState() == null) {
                MWorld.g(false);
            }
            return;
        }
        Class[] classArray = new Class[]{};
        Class<String> clazz = String.class;
        boolean bl6 = true;
        String string = "getName";
        MIWorldNameable mIWorldNameable = this;
        this.var_gg_vape_mapping_MappingMethod_C = mIWorldNameable.Y(string, bl6, clazz, classArray);
        Class[] classArray3 = new Class[]{};
        Class<Boolean> clazz3 = Boolean.TYPE;
        boolean bl7 = true;
        String string3 = "hasCustomName";
        MIWorldNameable mIWorldNameable3 = this;
        this.var_gg_vape_mapping_MappingMethod_Z = this.Y(string3, bl7, clazz3, classArray3);
        Class[] classArray4 = new Class[]{};
        Class clazz4 = MappedClasses.Yr;
        boolean bl8 = true;
        String string4 = "getDisplayName";
        MIWorldNameable mIWorldNameable4 = this;
        this.var_gg_vape_mapping_MappingMethod_c = this.Y(string4, bl8, clazz4, classArray4);
        if (GuiComponent.getLegacyComponentState() == null) {
            MWorld.g(true);
        }
    }

    public String L(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_C.invokeObject(object, new Object[0]).toString();
    }

    public Object s(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_C.invokeObject(object, new Object[0]);
    }
}

