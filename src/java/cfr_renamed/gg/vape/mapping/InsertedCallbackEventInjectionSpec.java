/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.mapping.EventInjectionSpec;
import gg.vape.mapping.InsertedCallbackMarker;
import gg.vape.mapping.MappingMethod;

public class InsertedCallbackEventInjectionSpec
extends EventInjectionSpec {
    private final static String var_java_lang_String_h;

    @Override
    public String buildInjectionCode() {
        String callbackCode = this.getEventClass().getName() + var_java_lang_String_h;
        return callbackCode;
    }

    public InsertedCallbackEventInjectionSpec(MappingMethod mappingMethod, Class<? extends InsertedCallbackMarker> clazz) {
        super(mappingMethod, clazz);
    }

    static {
        try {
            var_java_lang_String_h = "#call();";
        }
        catch (Exception exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }
}

