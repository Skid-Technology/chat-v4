/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.mapping.EntityClientPlayerMPMotionMappingTask;
import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.CtClass;
import gg.vape.shaded.javassist.expr.ExprEditor;
import gg.vape.shaded.javassist.expr.FieldAccess;
import gg.vape.shaded.javassist.expr.MethodCall;
import gg.vape.wrapper.impl.ForgeVersion;

public class EntityClientPlayerMPMotionExprEditor
extends ExprEditor {
    final boolean var_boolean_A;
    final EntityClientPlayerMPMotionMappingTask var_gg_vape_mapping_EntityClientPlayerMPMotionMappingTask_j;
    final String var_java_lang_String_I;
    final CtClass var_gg_vape_shaded_javassist_CtClass_S;
    final String var_java_lang_String_R;

    @Override
    public void edit(FieldAccess fieldAccess) throws CannotCompileException {
        try {
            if (fieldAccess.getFieldName().equals(EntityClientPlayerMPMotionMappingTask.j(this.var_gg_vape_mapping_EntityClientPlayerMPMotionMappingTask_j)) && fieldAccess.getSignature().equals("F") && (!this.var_boolean_A || fieldAccess.getField().getDeclaringClass().equals(this.var_gg_vape_shaded_javassist_CtClass_S))) {
                String string = "$_ = " + this.var_java_lang_String_I + "#getRotationYaw();";
                fieldAccess.replace(string);
                return;
            }
            if (fieldAccess.getFieldName().equals(EntityClientPlayerMPMotionMappingTask.z(this.var_gg_vape_mapping_EntityClientPlayerMPMotionMappingTask_j)) && fieldAccess.getSignature().equals("F") && (!this.var_boolean_A || fieldAccess.getField().getDeclaringClass().equals(this.var_gg_vape_shaded_javassist_CtClass_S))) {
                String string = "$_ = " + this.var_java_lang_String_I + "#getRotationPitch();";
                fieldAccess.replace(string);
                return;
            }
            if (fieldAccess.getFieldName().equals(EntityClientPlayerMPMotionMappingTask.Q(this.var_gg_vape_mapping_EntityClientPlayerMPMotionMappingTask_j)) && fieldAccess.getSignature().equals("Z") && (!this.var_boolean_A || fieldAccess.getField().getDeclaringClass().equals(this.var_gg_vape_shaded_javassist_CtClass_S))) {
                String string = "$_ = " + this.var_java_lang_String_I + "#isOnGround();";
                fieldAccess.replace(string);
                return;
            }
        }
        catch (Exception exception) {
            Vape.logThrowable(exception);
        }
    }

    @Override
    public void edit(MethodCall methodCall) throws CannotCompileException {
        if (ForgeVersion.MC_1_16_5.d() && methodCall.getMethodName().equals(this.var_java_lang_String_R)) {
            String string = "$_ = " + this.var_java_lang_String_I + "#getY();";
            methodCall.replace(string);
            return;
        }
    }

    public EntityClientPlayerMPMotionExprEditor(EntityClientPlayerMPMotionMappingTask entityClientPlayerMPMotionMappingTask, String string, String string2, boolean bl, CtClass ctClass) {
        this.var_gg_vape_mapping_EntityClientPlayerMPMotionMappingTask_j = entityClientPlayerMPMotionMappingTask;
        this.var_java_lang_String_R = string;
        this.var_java_lang_String_I = string2;
        this.var_boolean_A = bl;
        this.var_gg_vape_shaded_javassist_CtClass_S = ctClass;
    }

    private static Exception a(Exception exception) {
        return exception;
    }
}

