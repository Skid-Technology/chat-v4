/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventBlockRenderColorOpacity;
import gg.vape.mapping.EventInjectionSpec;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;

public class BlockRenderColorOpacityMappingTask
extends JavassistMappingTask {
    private final static String c = "$0, $1, $2, $3, $4";

    public BlockRenderColorOpacityMappingTask() {
        super(MappedClasses.lX);
    }

    @Override
    public void transform() {
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().qZ.var_gg_vape_mapping_MappingMethod_b;
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventBlockRenderColorOpacity.class);
        eventInjectionSpec.setInsertBefore(false);
        eventInjectionSpec.setConstructorArguments(var_java_lang_String_c);
        this.registerEventInjection(eventInjectionSpec);
    }
}

