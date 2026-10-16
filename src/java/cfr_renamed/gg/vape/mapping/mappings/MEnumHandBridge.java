/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;

public class MEnumHandBridge
extends Mapping {
    private final static String var_java_lang_String_b;
    private static int var_int_d;
    private MappingField var_gg_vape_mapping_MappingField_s;

    public static Object c(MEnumHandBridge mEnumHandBridge) {
        return mEnumHandBridge.h();
    }

    private Object h() {
        return this.var_gg_vape_mapping_MappingField_s.getObject(null);
    }

    public MEnumHandBridge() {
        this(MEnumHandBridge.V());
    }

    private MEnumHandBridge(int n) {
        super(MappedClasses.YF);
        int n2 = n;
        Class clazz = MappedClasses.YF;
        boolean bl = true;
        String string = var_java_lang_String_b;
        MEnumHandBridge mEnumHandBridge = this;
        this.var_gg_vape_mapping_MappingField_s = this.registerStaticField(string, bl, clazz);
        if (GuiComponent.getLegacyComponentState() == null) {
            MEnumHandBridge.q(++n2);
        }
    }

    public static void q(int n) {
        var_int_d = n;
    }

    public static int s() {
        return var_int_d;
    }

    public static int V() {
        int n = MEnumHandBridge.s();
        if (n == 0) {
            return 92;
        }
        return 0;
    }

    static {
        MEnumHandBridge.q(0);
        var_java_lang_String_b = "QUADS";
    }
}

