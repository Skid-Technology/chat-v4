/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.expr;

import gg.vape.Vape;
import gg.vape.event.impl.EventRender2D;
import gg.vape.mapping.EntityRendererEventMappingTask;
import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.expr.ExprEditor;
import gg.vape.shaded.javassist.expr.MethodCall;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.concurrent.atomic.AtomicBoolean;

public class EventRender2DInjectionExprEditor
extends ExprEditor {
    final EntityRendererEventMappingTask var_gg_vape_mapping_EntityRendererEventMappingTask_N;
    final AtomicBoolean var_java_util_concurrent_atomic_AtomicBoolean_g;
    final String var_java_lang_String_s;

    public EventRender2DInjectionExprEditor(EntityRendererEventMappingTask entityRendererEventMappingTask, String string, AtomicBoolean atomicBoolean) {
        this.var_gg_vape_mapping_EntityRendererEventMappingTask_N = entityRendererEventMappingTask;
        this.var_java_lang_String_s = string;
        this.var_java_util_concurrent_atomic_AtomicBoolean_g = atomicBoolean;
    }

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }

    @Override
    public void edit(MethodCall methodCall) throws CannotCompileException {
        if (!methodCall.getSignature().equals(this.var_java_lang_String_s)) {
            return;
        }
        if (ForgeVersion.MC_1_7_10.L() && !methodCall.getMethodName().equals("glAlphaFunc")) {
            return;
        }
        if (ForgeVersion.MC_1_7_10.Y() && ForgeVersion.MC_1_16_5.v() && !methodCall.getMethodName().equals(Vape.INSTANCE.getMappings().Dt.var_gg_vape_mapping_MappingMethod_q.getResolvedName())) {
            return;
        }
        if (ForgeVersion.MC_1_16_5.d() && ForgeVersion.MC_1_17.v() && !methodCall.getMethodName().equals(Vape.INSTANCE.getMappings().var_gg_vape_mapping_mappings_MRenderSystem_f.var_gg_vape_mapping_MappingMethod_v.getResolvedName())) {
            return;
        }
        methodCall.replace("{ $_ = $proceed($$); " + EventRender2D.class.getName() + "#create(); }");
        this.var_java_util_concurrent_atomic_AtomicBoolean_g.set(true);
    }
}

