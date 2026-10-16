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
    final AtomicInteger var_java_util_concurrent_atomic_AtomicInteger_M;
    final JavassistMappingTask var_gg_vape_mapping_JavassistMappingTask_I;
    final MappingMethod var_gg_vape_mapping_MappingMethod_x;

    @Override
    public void edit(MethodCall methodCall) throws CannotCompileException {
        if (methodCall.getMethodName().equals(this.var_gg_vape_mapping_MappingMethod_x.getResolvedName()) && methodCall.getSignature().equals(this.var_gg_vape_mapping_MappingMethod_x.getDescriptor())) {
            this.var_java_util_concurrent_atomic_AtomicInteger_M.set(methodCall.getLineNumber());
        }
    }

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }

    public MethodCallLineCaptureExprEditor(JavassistMappingTask javassistMappingTask, MappingMethod mappingMethod, AtomicInteger atomicInteger) {
        this.var_gg_vape_mapping_JavassistMappingTask_I = javassistMappingTask;
        this.var_gg_vape_mapping_MappingMethod_x = mappingMethod;
        this.var_java_util_concurrent_atomic_AtomicInteger_M = atomicInteger;
    }
}

