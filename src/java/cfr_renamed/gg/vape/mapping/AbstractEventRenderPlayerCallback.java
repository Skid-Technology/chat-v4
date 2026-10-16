/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.mapping.InsertedCallbackMarker;
import gg.vape.mapping.MappedClasses;

public class AbstractEventRenderPlayerCallback
extends InsertedCallbackMarker {
    private static String var_java_lang_String_s;

    private static boolean isPlayerInstance(Object object) {
        boolean bl = MappedClasses.Yl.isInstance(object) || MappedClasses.z5.isInstance(object);
        return bl;
    }

    public static String n() {
        return var_java_lang_String_s;
    }

    public static void D(String string) {
        var_java_lang_String_s = string;
    }

    public static boolean access$000(Object object) {
        return AbstractEventRenderPlayerCallback.isPlayerInstance(object);
    }

    static {
        if (AbstractEventRenderPlayerCallback.n() == null) {
            AbstractEventRenderPlayerCallback.D("LTsfgb");
        }
    }
}

