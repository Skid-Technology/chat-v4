/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.asm.transform;

import gg.vape.shaded.org.objectweb.asm.ClassWriter;

public class TransformClassWriter
extends ClassWriter {
    public TransformClassWriter(int flags) {
        super(flags);
    }

    @Override
    protected String getCommonSuperClass(String firstType, String secondType) {
        return super.getCommonSuperClass(firstType, secondType);
    }
}

