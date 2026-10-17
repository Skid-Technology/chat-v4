/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.component.value;

public enum ColorChannelType {
    BLOCK_CHILD("Custom color"),
    RAINBOW("Rainbow"),
    SATURATION("Saturation"),
    VIBRANCE("Vibrance"),
    OPACITY("Opacity");

    private final String displayName;
    private static final ColorChannelType[] VALUES_CACHE;

    public String getDisplayName() {
        return this.displayName;
    }

    private ColorChannelType(String displayName) {
        this.displayName = displayName;
    }

    static {
        String[] legacyNames = new String[]{"Opacity", "SATURATION", "Saturation", "Custom color", "OPACITY", "BLOCK_CHILD", "Vibrance", "Rainbow", "VIBRANCE", "RAINBOW"};
        VALUES_CACHE = new ColorChannelType[]{BLOCK_CHILD, RAINBOW, SATURATION, VIBRANCE, OPACITY};
    }
}

