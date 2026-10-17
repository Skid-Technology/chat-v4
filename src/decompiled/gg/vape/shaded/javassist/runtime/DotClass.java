/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.runtime;

public class DotClass {
    public static NoClassDefFoundError fail(ClassNotFoundException e) {
        return new NoClassDefFoundError(e.getMessage());
    }
}

