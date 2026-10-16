/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventRenderWorldPassExecutorDrain;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;

public class RenderWorldPassExecutorDrainMappingTask
extends JavassistMappingTask {
    private final static String var_java_lang_String_c;

    void V() {
        this.c(Vape.INSTANCE.getMappings().RY.var_gg_vape_mapping_MappingMethod_J, EventRenderWorldPassExecutorDrain.class, var_java_lang_String_c);
    }

    public RenderWorldPassExecutorDrainMappingTask() {
        super(MappedClasses.FW);
    }

    @Override
    public void transform() {
        this.V();
    }

    static {
        try {
            var_java_lang_String_c = "$1";
        }
        catch (Exception exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }
}

