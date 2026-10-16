/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.mapping.JavassistMappingTask;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.RenderBatchFlushCallbackMarker;
import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.CtBehavior;

public class ThreadBoundCallbackMappingTask
extends JavassistMappingTask {
    private final static String c = "#call();}";

    @Override
    public void transform() {
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().glCommandEncoder.drawFromBuffersMethod;
        CtBehavior ctBehavior = this.F(mappingMethod);
        try {
            ctBehavior.insertBefore("{" + RenderBatchFlushCallbackMarker.class.getName() + var_java_lang_String_c);
        }
        catch (CannotCompileException cannotCompileException) {
            Vape.logThrowable(cannotCompileException);
        }
    }

    public ThreadBoundCallbackMappingTask() {
        super(MappedClasses.zg);
    }
}

