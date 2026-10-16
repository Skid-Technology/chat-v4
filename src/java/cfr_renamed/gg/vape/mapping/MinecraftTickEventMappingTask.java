/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventClickMouse;
import gg.vape.event.impl.EventMouseOverUpdate;
import gg.vape.event.impl.EventPostTick;
import gg.vape.event.impl.EventPreTick;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MinecraftTickCallbackExprEditor;
import gg.vape.shaded.javassist.CtBehavior;
import gg.vape.shaded.javassist.expr.MethodCall;
import gg.vape.wrapper.impl.ForgeVersion;

public class MinecraftTickEventMappingTask
extends JavassistMappingTask {
    private static Exception a(Exception exception) {
        return exception;
    }

    public MinecraftTickEventMappingTask() {
        super(MappedClasses.uP);
    }

    @Override
    public void transform() {
        try {
            MappingMethod mappingMethod = Vape.INSTANCE.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.var_gg_vape_mapping_MappingMethod_a;
            CtBehavior ctBehavior = this.F(Vape.INSTANCE.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.var_gg_vape_mapping_MappingMethod_v);
            boolean[] blArray = new boolean[]{false};
            ctBehavior.instrument(new MinecraftTickCallbackExprEditor(this, mappingMethod, blArray));
            if (!blArray[0]) {
                this.O(mappingMethod, EventPreTick.class, "", "");
                this.j(mappingMethod, EventPostTick.class, "", "");
            }
            if (ForgeVersion.MC_1_20_6.d()) {
                MappingMethod mappingMethod2 = Vape.INSTANCE.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.var_gg_vape_mapping_MappingMethod_q;
                this.O(mappingMethod2, EventClickMouse.class, "", "false");
                if (ForgeVersion.MC_26_1.d() && Vape.INSTANCE.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.var_gg_vape_mapping_MappingMethod_i != null && !Vape.INSTANCE.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.var_gg_vape_mapping_MappingMethod_i.hasResolutionFailed()) {
                    this.k(Vape.INSTANCE.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.var_gg_vape_mapping_MappingMethod_i, EventMouseOverUpdate.class, "$1");
                }
            }
        }
        catch (Exception exception) {
            throw new RuntimeException("Failed to create hooks", exception);
        }
    }

    public static boolean y(MethodCall methodCall, MappingMethod mappingMethod) {
        return MinecraftTickEventMappingTask.c(methodCall, mappingMethod);
    }
}

