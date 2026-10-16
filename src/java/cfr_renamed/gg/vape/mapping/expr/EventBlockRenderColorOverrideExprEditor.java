/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.expr;

import gg.vape.mapping.RenderLivingBaseEventMappingTask;
import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.expr.ExprEditor;
import gg.vape.shaded.javassist.expr.MethodCall;

public class EventBlockRenderColorOverrideExprEditor
extends ExprEditor {
    final RenderLivingBaseEventMappingTask var_gg_vape_mapping_RenderLivingBaseEventMappingTask_W;
    final String var_java_lang_String_a;

    public EventBlockRenderColorOverrideExprEditor(RenderLivingBaseEventMappingTask renderLivingBaseEventMappingTask, String string) {
        this.var_gg_vape_mapping_RenderLivingBaseEventMappingTask_W = renderLivingBaseEventMappingTask;
        this.var_java_lang_String_a = string;
    }

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }

    @Override
    public void edit(MethodCall methodCall) throws CannotCompileException {
        if (methodCall.getMethodName().equals("flip")) {
            methodCall.replace("{" + this.var_java_lang_String_a + "#flip($0);$_ = $proceed($$);}");
        }
    }
}

