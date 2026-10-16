/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.mapping.EntityPlayerSPEventMappingTask;
import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.CtClass;
import gg.vape.shaded.javassist.expr.ExprEditor;
import gg.vape.shaded.javassist.expr.FieldAccess;
import gg.vape.shaded.javassist.expr.MethodCall;
import gg.vape.wrapper.impl.ForgeVersion;

public class EntityPlayerSPMotionExprEditor
extends ExprEditor {
    final String var_java_lang_String_B;
    final boolean var_boolean_c;
    final EntityPlayerSPEventMappingTask var_gg_vape_mapping_EntityPlayerSPEventMappingTask_p;
    final CtClass var_gg_vape_shaded_javassist_CtClass_f;

    @Override
    public void edit(MethodCall methodCall) throws CannotCompileException {
        if (ForgeVersion.MC_1_20_6.v()) {
            return;
        }
        try {
            if (methodCall.getMethodName().equals(EntityPlayerSPEventMappingTask.N(this.var_gg_vape_mapping_EntityPlayerSPEventMappingTask_p)) && methodCall.getSignature().equals("()F") && methodCall.getMethod().getDeclaringClass().equals(this.var_gg_vape_shaded_javassist_CtClass_f)) {
                String string = "$_ = " + this.var_java_lang_String_B + "#getRotationYaw();";
                methodCall.replace(string);
                return;
            }
            if (methodCall.getMethodName().equals(EntityPlayerSPEventMappingTask.B(this.var_gg_vape_mapping_EntityPlayerSPEventMappingTask_p)) && methodCall.getSignature().equals("()F") && methodCall.getMethod().getDeclaringClass().equals(this.var_gg_vape_shaded_javassist_CtClass_f)) {
                String string = "$_ = " + this.var_java_lang_String_B + "#getRotationPitch();";
                methodCall.replace(string);
                return;
            }
            if (methodCall.getMethodName().equals(EntityPlayerSPEventMappingTask.T(this.var_gg_vape_mapping_EntityPlayerSPEventMappingTask_p)) && methodCall.getSignature().equals("()Z") && methodCall.getMethod().getDeclaringClass().equals(this.var_gg_vape_shaded_javassist_CtClass_f)) {
                String string = "$_ = " + this.var_java_lang_String_B + "#isOnGround();";
                methodCall.replace(string);
                return;
            }
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
    }

    public EntityPlayerSPMotionExprEditor(EntityPlayerSPEventMappingTask entityPlayerSPEventMappingTask, CtClass ctClass, String string, boolean bl) {
        this.var_gg_vape_mapping_EntityPlayerSPEventMappingTask_p = entityPlayerSPEventMappingTask;
        this.var_gg_vape_shaded_javassist_CtClass_f = ctClass;
        this.var_java_lang_String_B = string;
        this.var_boolean_c = bl;
    }

    @Override
    public void edit(FieldAccess fieldAccess) throws CannotCompileException {
        try {
            if (ForgeVersion.MC_1_20_6.d()) {
                return;
            }
            if (fieldAccess.getFieldName().equals(EntityPlayerSPEventMappingTask.O(this.var_gg_vape_mapping_EntityPlayerSPEventMappingTask_p)) && fieldAccess.getSignature().equals("F") && (!this.var_boolean_c || fieldAccess.getField().getDeclaringClass().equals(this.var_gg_vape_shaded_javassist_CtClass_f))) {
                String string = "$_ = " + this.var_java_lang_String_B + "#getRotationYaw();";
                fieldAccess.replace(string);
                return;
            }
            if (fieldAccess.getFieldName().equals(EntityPlayerSPEventMappingTask.S(this.var_gg_vape_mapping_EntityPlayerSPEventMappingTask_p)) && fieldAccess.getSignature().equals("F") && (!this.var_boolean_c || fieldAccess.getField().getDeclaringClass().equals(this.var_gg_vape_shaded_javassist_CtClass_f))) {
                String string = "$_ = " + this.var_java_lang_String_B + "#getRotationPitch();";
                fieldAccess.replace(string);
                return;
            }
            if (fieldAccess.getFieldName().equals(EntityPlayerSPEventMappingTask.L(this.var_gg_vape_mapping_EntityPlayerSPEventMappingTask_p)) && fieldAccess.getSignature().equals("Z") && (!this.var_boolean_c || fieldAccess.getField().getDeclaringClass().equals(this.var_gg_vape_shaded_javassist_CtClass_f))) {
                String string = "$_ = " + this.var_java_lang_String_B + "#isOnGround();";
                fieldAccess.replace(string);
                return;
            }
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
    }

    private static Exception a(Exception exception) {
        return exception;
    }
}

