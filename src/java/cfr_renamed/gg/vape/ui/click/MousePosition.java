/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click;

public class MousePosition {
    public int var_int_H;
    public int var_int_O;
    private final static String var_java_lang_String_b = "[%s, %s]";

    public String toString() {
        return String.format(var_java_lang_String_b, this.var_int_O, this.var_int_H);
    }

    public MousePosition(int n, int n2) {
        this.var_int_O = n;
        this.var_int_H = n2;
    }
}

