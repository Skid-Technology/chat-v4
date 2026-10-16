/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.value;

public enum ValueDisplayNameMode {
    FULL,
    SIMPLE,
    CUSTOM;

    private final static ValueDisplayNameMode[] legacyValues;

    static {
        String[] stringArray = new String[]{"FULL", "SIMPLE", "CUSTOM"};
        legacyValues = new ValueDisplayNameMode[]{FULL, SIMPLE, CUSTOM};
    }
}

