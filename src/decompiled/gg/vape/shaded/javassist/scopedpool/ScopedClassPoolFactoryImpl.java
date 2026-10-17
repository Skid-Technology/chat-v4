/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.scopedpool;

import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.scopedpool.ScopedClassPool;
import gg.vape.shaded.javassist.scopedpool.ScopedClassPoolFactory;
import gg.vape.shaded.javassist.scopedpool.ScopedClassPoolRepository;

public class ScopedClassPoolFactoryImpl
implements ScopedClassPoolFactory {
    @Override
    public ScopedClassPool create(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository) {
        return new ScopedClassPool(cl, src, repository, false);
    }

    @Override
    public ScopedClassPool create(ClassPool src, ScopedClassPoolRepository repository) {
        return new ScopedClassPool(null, src, repository, true);
    }
}

