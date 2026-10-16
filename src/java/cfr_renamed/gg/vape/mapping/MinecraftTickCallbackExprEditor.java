/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.mapping.EventPostTickCallback;
import gg.vape.mapping.EventPreTickCallback;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MinecraftTickEventMappingTask;
import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.expr.ExprEditor;
import gg.vape.shaded.javassist.expr.MethodCall;

public class MinecraftTickCallbackExprEditor
extends ExprEditor {
    final MinecraftTickEventMappingTask var_gg_vape_mapping_MinecraftTickEventMappingTask_a;
    final MappingMethod var_gg_vape_mapping_MappingMethod_m;
    final boolean[] var_boolean_arr_W;

    public MinecraftTickCallbackExprEditor(MinecraftTickEventMappingTask minecraftTickEventMappingTask, MappingMethod mappingMethod, boolean[] blArray) {
        this.var_gg_vape_mapping_MinecraftTickEventMappingTask_a = minecraftTickEventMappingTask;
        this.var_gg_vape_mapping_MappingMethod_m = mappingMethod;
        this.var_boolean_arr_W = blArray;
    }

    @Override
    public void edit(MethodCall methodCall) throws CannotCompileException {
        if (MinecraftTickEventMappingTask.y(methodCall, this.var_gg_vape_mapping_MappingMethod_m)) {
            this.var_boolean_arr_W[0] = true;
            methodCall.replace("{" + EventPreTickCallback.class.getName() + "#call();$_ = $proceed($$);" + EventPostTickCallback.class.getName() + "#call();}");
        }
    }

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }
}

