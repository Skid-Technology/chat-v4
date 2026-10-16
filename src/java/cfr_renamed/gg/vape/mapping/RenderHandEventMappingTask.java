/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventPostRenderHand;
import gg.vape.event.impl.EventPreRenderHand;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;

public class RenderHandEventMappingTask
extends JavassistMappingTask {
    @Override
    public void transform() {
        if (Vape.INSTANCE.getMappings().RY.var_gg_vape_mapping_MappingMethod_x != null && !Vape.INSTANCE.getMappings().RY.var_gg_vape_mapping_MappingMethod_x.hasResolutionFailed()) {
            this.c(Vape.INSTANCE.getMappings().RY.var_gg_vape_mapping_MappingMethod_x, EventPreRenderHand.class, "");
            this.k(Vape.INSTANCE.getMappings().RY.var_gg_vape_mapping_MappingMethod_x, EventPostRenderHand.class, "");
        }
    }

    public RenderHandEventMappingTask() {
        super(MappedClasses.lt);
    }
}

