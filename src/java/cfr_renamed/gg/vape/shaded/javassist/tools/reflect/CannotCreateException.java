/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.tools.reflect;

public class CannotCreateException
extends Exception {
    private final static long serialVersionUID = 1L;

    public CannotCreateException(String s) {
        super(s);
    }

    public CannotCreateException(Exception e) {
        super("by " + e.toString());
    }
}

