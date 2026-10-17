/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.scopedpool;

import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.scopedpool.ScopedClassPool;
import gg.vape.shaded.javassist.scopedpool.ScopedClassPoolRepository;

public interface ScopedClassPoolFactory {
    public ScopedClassPool create(ClassLoader var1, ClassPool var2, ScopedClassPoolRepository var3);

    public ScopedClassPool create(ClassPool var1, ScopedClassPoolRepository var2);
}

