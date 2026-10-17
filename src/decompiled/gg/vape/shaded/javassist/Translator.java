/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist;

import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.NotFoundException;

public interface Translator {
    public void start(ClassPool var1) throws NotFoundException, CannotCompileException;

    public void onLoad(ClassPool var1, String var2) throws NotFoundException, CannotCompileException;
}

