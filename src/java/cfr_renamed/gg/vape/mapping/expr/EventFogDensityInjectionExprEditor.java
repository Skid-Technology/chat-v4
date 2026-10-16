/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.expr;

import gg.vape.event.EventBus;
import gg.vape.event.impl.EventFogDensity;
import gg.vape.mapping.EntityRendererEventMappingTask;
import gg.vape.mapping.MappingMethod;
import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.expr.ExprEditor;
import gg.vape.shaded.javassist.expr.MethodCall;
import java.util.concurrent.atomic.AtomicBoolean;

public class EventFogDensityInjectionExprEditor
extends ExprEditor {
    final EntityRendererEventMappingTask var_gg_vape_mapping_EntityRendererEventMappingTask_u;
    final MappingMethod var_gg_vape_mapping_MappingMethod_d;
    final AtomicBoolean var_java_util_concurrent_atomic_AtomicBoolean_o;

    public EventFogDensityInjectionExprEditor(EntityRendererEventMappingTask entityRendererEventMappingTask, MappingMethod mappingMethod, AtomicBoolean atomicBoolean) {
        this.var_gg_vape_mapping_EntityRendererEventMappingTask_u = entityRendererEventMappingTask;
        this.var_gg_vape_mapping_MappingMethod_d = mappingMethod;
        this.var_java_util_concurrent_atomic_AtomicBoolean_o = atomicBoolean;
    }

    @Override
    public void edit(MethodCall methodCall) throws CannotCompileException {
        if (methodCall.getMethodName().equals(this.var_gg_vape_mapping_MappingMethod_d.getResolvedName()) && methodCall.getSignature().equals(this.var_gg_vape_mapping_MappingMethod_d.getDescriptor())) {
            String string = EventFogDensity.class.getName();
            methodCall.replace("$_ = $proceed($$);" + string + " vapeEvent = new " + string + "(0.1f);if (vapeEvent." + EventBus.getFireMethod(EventFogDensity.class).getName() + "()) {return;}");
            this.var_java_util_concurrent_atomic_AtomicBoolean_o.set(true);
        }
    }

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }
}

