/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.expr;

import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappingMethod;
import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.expr.ExprEditor;
import gg.vape.shaded.javassist.expr.MethodCall;

public class MethodCallInjectionExprEditor
extends ExprEditor {
    final MappingMethod var_gg_vape_mapping_MappingMethod_M;
    final JavassistMappingTask var_gg_vape_mapping_JavassistMappingTask_G;
    final String var_java_lang_String_f;

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }

    public MethodCallInjectionExprEditor(JavassistMappingTask javassistMappingTask, MappingMethod mappingMethod, String string) {
        this.var_gg_vape_mapping_JavassistMappingTask_G = javassistMappingTask;
        this.var_gg_vape_mapping_MappingMethod_M = mappingMethod;
        this.var_java_lang_String_f = string;
    }

    @Override
    public void edit(MethodCall methodCall) throws CannotCompileException {
        if (methodCall.getMethodName().equals(this.var_gg_vape_mapping_MappingMethod_M.getResolvedName()) && methodCall.getSignature().equals(this.var_gg_vape_mapping_MappingMethod_M.getDescriptor())) {
            methodCall.replace(this.var_java_lang_String_f);
        }
    }
}

