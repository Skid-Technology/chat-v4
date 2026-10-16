/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventChunkRenderRebuild;
import gg.vape.mapping.EventInjectionSpec;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;

public class ChunkRenderRebuildEventMappingTask
extends JavassistMappingTask {
    private final static String c = "($r) 1";

    @Override
    public void transform() {
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().qg.var_gg_vape_mapping_MappingMethod_w;
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventChunkRenderRebuild.class);
        eventInjectionSpec.setReturnExpression(var_java_lang_String_c);
        this.registerEventInjection(eventInjectionSpec);
    }

    public ChunkRenderRebuildEventMappingTask() {
        super(MappedClasses.Zk);
    }
}

