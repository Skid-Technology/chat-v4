/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.expr;

import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappingMethod;
import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.expr.ExprEditor;
import gg.vape.shaded.javassist.expr.MethodCall;
import java.util.concurrent.atomic.AtomicInteger;

public class MethodCallLineCaptureExprEditor
extends ExprEditor {
    final AtomicInteger M;
    final JavassistMappingTask I;
    final MappingMethod x;

    @Override
    public void edit(MethodCall methodCall) throws CannotCompileException {
        if (methodCall.getMethodName().equals(this.x.getResolvedName()) && methodCall.getSignature().equals(this.x.getDescriptor())) {
            this.M.set(methodCall.getLineNumber());
        }
    }

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }

    public MethodCallLineCaptureExprEditor(JavassistMappingTask javassistMappingTask, MappingMethod mappingMethod, AtomicInteger atomicInteger) {
        this.I = javassistMappingTask;
        this.x = mappingMethod;
        this.M = atomicInteger;
    }
}

