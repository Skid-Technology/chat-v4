/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.input;

public enum BindActivationMode {
    TOGGLE("Toggle"),
    HELD("Enable while held");

    private static final BindActivationMode[] legacyValues;
    private final String displayName;

    public String getDisplayName() {
        return this.displayName;
    }

    public BindActivationMode toggle() {
        return this == TOGGLE ? HELD : TOGGLE;
    }

    private BindActivationMode(String displayName) {
        this.displayName = displayName;
    }

    static {
        String[] legacyNames = new String[]{"Toggle", "TOGGLE", "HELD", "Enable while held"};
        legacyValues = new BindActivationMode[]{TOGGLE, HELD};
    }
}

