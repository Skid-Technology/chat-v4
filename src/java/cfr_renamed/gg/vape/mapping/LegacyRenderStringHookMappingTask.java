/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.LegacyRenderStringEventRender3DCallback;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.CtBehavior;

public class LegacyRenderStringHookMappingTask
extends JavassistMappingTask {
    private final static String c = "#call($1);}";

    public LegacyRenderStringHookMappingTask() {
        super(MappedClasses.Vk);
    }

    @Override
    public void transform() {
        LegacyRenderStringHookMappingTask.p(LegacyRenderStringEventRender3DCallback.class);
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().C5.var_gg_vape_mapping_MappingMethod_A;
        CtBehavior ctBehavior = this.F(mappingMethod);
        try {
            ctBehavior.insertBefore("{" + LegacyRenderStringEventRender3DCallback.class.getName() + var_java_lang_String_c);
        }
        catch (CannotCompileException cannotCompileException) {
            Vape.logThrowable(cannotCompileException);
        }
    }
}

