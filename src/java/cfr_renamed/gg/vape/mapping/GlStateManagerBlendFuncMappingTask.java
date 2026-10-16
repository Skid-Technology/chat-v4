/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.mapping.InsertedCallbackLockMarker;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.CtBehavior;

public class GlStateManagerBlendFuncMappingTask
extends JavassistMappingTask {
    @Override
    public void transform() {
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().Dt.var_gg_vape_mapping_MappingMethod_O;
        CtBehavior ctBehavior = this.F(mappingMethod);
        try {
            String string = "{if(" + InsertedCallbackLockMarker.class.getName() + "#check($1, $2)) {   return;}}";
            ctBehavior.insertBefore(string);
        }
        catch (CannotCompileException cannotCompileException) {
            Vape.logThrowable(cannotCompileException);
        }
    }

    public GlStateManagerBlendFuncMappingTask() {
        super(MappedClasses.var_java_lang_Class_K);
    }
}

