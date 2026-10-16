/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.tools.web;

public class BadHttpRequest
extends Exception {
    private final static long serialVersionUID = 1L;
    private Exception var_java_lang_Exception_e;

    public BadHttpRequest() {
        this.var_java_lang_Exception_e = null;
    }

    public BadHttpRequest(Exception _e) {
        this.var_java_lang_Exception_e = _e;
    }

    @Override
    public String toString() {
        if (this.var_java_lang_Exception_e == null) {
            return super.toString();
        }
        return this.var_java_lang_Exception_e.toString();
    }
}

