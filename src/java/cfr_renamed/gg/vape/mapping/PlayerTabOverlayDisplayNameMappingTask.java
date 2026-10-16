/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventPlayerTabOverlayDisplayName;
import gg.vape.mapping.EventInjectionSpec;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;

public class PlayerTabOverlayDisplayNameMappingTask
extends JavassistMappingTask {
    @Override
    public void transform() {
        EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(Vape.INSTANCE.getMappings().hP.var_gg_vape_mapping_MappingMethod_O, EventPlayerTabOverlayDisplayName.class);
        eventInjectionSpec.setConstructorArguments("$0, $1");
        eventInjectionSpec.setReturnExpression("$event.getDisplayNameInstance()");
        this.registerEventInjection(eventInjectionSpec);
    }

    public PlayerTabOverlayDisplayNameMappingTask() {
        super(MappedClasses.lF);
    }
}

