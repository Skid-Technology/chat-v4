/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module;

public enum ModuleDisplayScope {
    ALL,
    STANDALONE_ONLY,
    FRAMES_ONLY;

    private final static ModuleDisplayScope[] VALUES;

    static {
        String[] names = new String[]{"STANDALONE_ONLY", "ALL", "FRAMES_ONLY"};
        VALUES = new ModuleDisplayScope[]{ALL, STANDALONE_ONLY, FRAMES_ONLY};
    }
}

