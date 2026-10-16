/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventEntityJoinWorld;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class LegacyWorldEntityJoinEventMappingTask
extends JavassistMappingTask {
    private final static String var_java_lang_String_c;

    @Override
    public void transform() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return;
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            return;
        }
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().Cy.var_gg_vape_mapping_MappingMethod_q;
        this.c(mappingMethod, EventEntityJoinWorld.class, var_java_lang_String_c);
    }

    public LegacyWorldEntityJoinEventMappingTask() {
        super(MappedClasses.YU);
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

