/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.mapping.EventPreRenderEntityForgeCallback;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;

public class LegacyEntityRenderPreEventMappingTask
extends JavassistMappingTask {
    private final static String c = "$0, $1, $3, $4, $5";

    public LegacyEntityRenderPreEventMappingTask() {
        super(MappedClasses.var_java_lang_Class_x);
    }

    @Override
    public void transform() {
        this.k(Vape.INSTANCE.getMappings().legacyEntityRenderPreHook.constructorMethod, EventPreRenderEntityForgeCallback.class, var_java_lang_String_c);
    }
}

