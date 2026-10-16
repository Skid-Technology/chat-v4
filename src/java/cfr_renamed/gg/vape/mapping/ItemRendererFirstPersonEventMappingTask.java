/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventRenderItemInFirstPerson;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;

public class ItemRendererFirstPersonEventMappingTask
extends JavassistMappingTask {
    private final static String c = "$0, $1";

    @Override
    public void transform() {
        this.c(Vape.INSTANCE.getMappings().qE.var_gg_vape_mapping_MappingMethod_S, EventRenderItemInFirstPerson.class, var_java_lang_String_c);
    }

    public ItemRendererFirstPersonEventMappingTask() {
        super(MappedClasses.zN);
    }
}

