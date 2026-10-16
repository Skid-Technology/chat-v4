/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;

public class MResourceLocationKey
extends Mapping {
    private static boolean var_boolean_Y;
    private final static String var_java_lang_String_b;
    private final MappingField var_gg_vape_mapping_MappingField_I;

    public static boolean m() {
        boolean bl = MResourceLocationKey.A();
        return !bl;
    }

    public MResourceLocationKey() {
        this(MResourceLocationKey.m());
    }

    private MResourceLocationKey(boolean bl) {
        super(MappedClasses.qJ);
        if (bl) {
            Class clazz = MappedClasses.qJ;
            boolean bl2 = true;
            String string = var_java_lang_String_b;
            MResourceLocationKey mResourceLocationKey = this;
            this.var_gg_vape_mapping_MappingField_I = mResourceLocationKey.registerStaticField(string, bl2, clazz);
            if (GuiComponent.getLegacyComponentState() == null) {
                MResourceLocationKey.L(false);
            }
            return;
        }
        Class clazz = MappedClasses.qJ;
        boolean bl3 = true;
        String string = var_java_lang_String_b;
        MResourceLocationKey mResourceLocationKey = this;
        this.var_gg_vape_mapping_MappingField_I = mResourceLocationKey.registerStaticField(string, bl3, clazz);
        if (GuiComponent.getLegacyComponentState() == null) {
            MResourceLocationKey.L(true);
        }
    }

    public static boolean A() {
        return var_boolean_Y;
    }

    public static void L(boolean bl) {
        var_boolean_Y = bl;
    }

    public Object r() {
        return this.var_gg_vape_mapping_MappingField_I.getObject(null);
    }

    static {
        MResourceLocationKey.L(false);
        var_java_lang_String_b = "CRASH";
    }
}

