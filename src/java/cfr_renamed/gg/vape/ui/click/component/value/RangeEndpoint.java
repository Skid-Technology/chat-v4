/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.component.value;

public enum RangeEndpoint {
    MINIMUM,
    MAXIMUM;

    private final static RangeEndpoint[] LEGACY_VALUES;

    static {
        String[] legacyNames = new String[]{"MINIMUM", "MAXIMUM"};
        LEGACY_VALUES = new RangeEndpoint[]{MINIMUM, MAXIMUM};
    }
}

