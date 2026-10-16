/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.mapping.EventRender2DStaticCallback;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.CtBehavior;
import gg.vape.wrapper.impl.ForgeVersion;

public class Render2DStaticCallbackMappingTask
extends JavassistMappingTask {
    private final static String c = "#call();}";

    private static CannotCompileException a(CannotCompileException cannotCompileException) {
        return cannotCompileException;
    }

    @Override
    public void transform() {
        if (ForgeVersion.MC_26_1.d()) {
            return;
        }
        Render2DStaticCallbackMappingTask.p(EventRender2DStaticCallback.class);
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().titledScreen.renderHudMethod;
        if (mappingMethod == null) {
            return;
        }
        CtBehavior ctBehavior = this.F(mappingMethod);
        try {
            ctBehavior.insertAfter("{" + EventRender2DStaticCallback.class.getName() + var_java_lang_String_c);
        }
        catch (CannotCompileException cannotCompileException) {
            Vape.logThrowable(cannotCompileException);
        }
    }

    public Render2DStaticCallbackMappingTask() {
        super(MappedClasses.Zj);
    }
}

