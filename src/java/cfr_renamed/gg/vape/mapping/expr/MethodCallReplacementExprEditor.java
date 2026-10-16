/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.expr;

import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappingMethod;
import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.expr.ExprEditor;
import gg.vape.shaded.javassist.expr.MethodCall;

public class MethodCallReplacementExprEditor
extends ExprEditor {
    final JavassistMappingTask var_gg_vape_mapping_JavassistMappingTask_u;
    final String var_java_lang_String_V;
    final MappingMethod var_gg_vape_mapping_MappingMethod_w;

    public MethodCallReplacementExprEditor(JavassistMappingTask javassistMappingTask, MappingMethod mappingMethod, String string) {
        this.var_gg_vape_mapping_JavassistMappingTask_u = javassistMappingTask;
        this.var_gg_vape_mapping_MappingMethod_w = mappingMethod;
        this.var_java_lang_String_V = string;
    }

    @Override
    public void edit(MethodCall methodCall) throws CannotCompileException {
        if (methodCall.getMethodName().equals(this.var_gg_vape_mapping_MappingMethod_w.getResolvedName()) && methodCall.getSignature().equals(this.var_gg_vape_mapping_MappingMethod_w.getDescriptor())) {
            methodCall.replace(this.var_java_lang_String_V);
        }
    }

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }
}

