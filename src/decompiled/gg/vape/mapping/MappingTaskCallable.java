/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping;

import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.NotFoundException;

public interface MappingTaskCallable<T> {
    public T c() throws CannotCompileException, NotFoundException;
}

