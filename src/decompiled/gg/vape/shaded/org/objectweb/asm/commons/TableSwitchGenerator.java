/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.org.objectweb.asm.commons;

import gg.vape.shaded.org.objectweb.asm.Label;

public interface TableSwitchGenerator {
    public void generateCase(int var1, Label var2);

    public void generateDefault();
}

