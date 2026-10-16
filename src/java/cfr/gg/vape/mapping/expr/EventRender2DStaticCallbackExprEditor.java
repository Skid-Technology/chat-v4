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
    final EntityRendererEventMappingTask P;
    final AtomicBoolean u;
    final MappingMethod t;

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }

    public EventRender2DStaticCallbackExprEditor(EntityRendererEventMappingTask entityRendererEventMappingTask, AtomicBoolean atomicBoolean, MappingMethod mappingMethod) {
        this.P = entityRendererEventMappingTask;
        this.u = atomicBoolean;
        this.t = mappingMethod;
    }

    @Override
    public void edit(MethodCall methodCall) throws CannotCompileException {
        if (this.u.get()) {
            return;
        }
        if (methodCall.getMethodName().equals(this.t.getResolvedName()) && methodCall.getSignature().equals(this.t.getDescriptor())) {
            methodCall.replace("{ " + EventRender2DStaticCallback.class.getName() + "#call(); $_ = $proceed($$); }");
            this.u.set(true);
        }
    }
}

