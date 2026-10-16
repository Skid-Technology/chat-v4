/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper;

import gg.vape.Vape;
import gg.vape.ui.click.component.GuiComponent;
import java.util.HashMap;
import java.util.Map;

public class Wrapper {
    private final static Map<String, Integer> wrapperIdCache;
    public static boolean nativeDisabled;
    private final static String nullInstanceText;
    private static GuiComponent[] obfuscationState;
    public static boolean isNativeAvailable;
    protected Object var_java_lang_Object_I;
    public static Vape vapeInstance;

    public String toString() {
        if (this.var_java_lang_Object_I == null) {
            return nullInstanceText;
        }
        try {
            return this.var_java_lang_Object_I.toString();
        }
        catch (Exception exception) {
            return "";
        }
    }

    public boolean isInstance(Class clazz) {
        return this.isNotNull() && clazz != null && clazz.isInstance(this.var_java_lang_Object_I);
    }

    public Object getObject() {
        return this.var_java_lang_Object_I;
    }

    public boolean isNull() {
        return this.var_java_lang_Object_I == null;
    }

    public Wrapper(Object object) {
        if (object instanceof Wrapper) {
            Wrapper wrapper = (Wrapper)object;
            this.var_java_lang_Object_I = wrapper.getObject();
            return;
        }
        this.var_java_lang_Object_I = object;
    }

    public static GuiComponent[] getObfuscationState() {
        return obfuscationState;
    }

    public boolean isNotNull() {
        return !this.isNull();
    }

    public static void setObfuscationState(GuiComponent[] state) {
        obfuscationState = state;
    }

    public boolean equals(Object object) {
        if (object == null || this.var_java_lang_Object_I == null) {
            return false;
        }
        if (object instanceof Wrapper) {
            Wrapper wrapper = (Wrapper)object;
            return this.var_java_lang_Object_I.equals(wrapper.var_java_lang_Object_I);
        }
        return super.equals(object);
    }

    static {
        Wrapper.setObfuscationState(null);
        nullInstanceText = "Null instance";
        vapeInstance = Vape.INSTANCE;
        isNativeAvailable = vapeInstance.isForgeAbsent();
        nativeDisabled = false;
        wrapperIdCache = new HashMap<String, Integer>();
    }
}

