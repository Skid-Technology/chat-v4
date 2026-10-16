/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.expr;

import gg.vape.mapping.EntityRendererEventMappingTask;
import gg.vape.mapping.EventRender2DStaticCallback;
import gg.vape.mapping.MappingMethod;
import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.expr.ExprEditor;
import gg.vape.shaded.javassist.expr.MethodCall;
import java.util.concurrent.atomic.AtomicBoolean;

public class EventRender2DStaticCallbackExprEditor
extends ExprEditor {
    final EntityRendererEventMappingTask var_gg_vape_mapping_EntityRendererEventMappingTask_P;
    final AtomicBoolean var_java_util_concurrent_atomic_AtomicBoolean_u;
    final MappingMethod var_gg_vape_mapping_MappingMethod_t;

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }

    public EventRender2DStaticCallbackExprEditor(EntityRendererEventMappingTask entityRendererEventMappingTask, AtomicBoolean atomicBoolean, MappingMethod mappingMethod) {
        this.var_gg_vape_mapping_EntityRendererEventMappingTask_P = entityRendererEventMappingTask;
        this.var_java_util_concurrent_atomic_AtomicBoolean_u = atomicBoolean;
        this.var_gg_vape_mapping_MappingMethod_t = mappingMethod;
    }

    @Override
    public void edit(MethodCall methodCall) throws CannotCompileException {
        if (this.var_java_util_concurrent_atomic_AtomicBoolean_u.get()) {
            return;
        }
        if (methodCall.getMethodName().equals(this.var_gg_vape_mapping_MappingMethod_t.getResolvedName()) && methodCall.getSignature().equals(this.var_gg_vape_mapping_MappingMethod_t.getDescriptor())) {
            methodCall.replace("{ " + EventRender2DStaticCallback.class.getName() + "#call(); $_ = $proceed($$); }");
            this.var_java_util_concurrent_atomic_AtomicBoolean_u.set(true);
        }
    }
}

