/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventKeyBindingState;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.impl.ForgeVersion;

public class KeyBindingStateEventMappingTask
extends JavassistMappingTask {
    private final static String var_java_lang_String_c;

    public KeyBindingStateEventMappingTask() {
        super(MappedClasses.DR);
    }

    @Override
    public void transform() {
        if (ForgeVersion.MC_1_16_5.d()) {
            this.O(Vape.INSTANCE.getMappings().hJ.var_gg_vape_mapping_MappingMethod_s, EventKeyBindingState.class, var_java_lang_String_c, "");
            return;
        }
    }

    static {
        try {
            var_java_lang_String_c = "$0, $1";
        }
        catch (Exception exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }
}

