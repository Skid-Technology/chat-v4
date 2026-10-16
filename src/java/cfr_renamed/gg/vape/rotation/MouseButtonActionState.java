/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.rotation;

public enum MouseButtonActionState {
    NONE,
    PRESS,
    RELEASE;

    private final static MouseButtonActionState[] cachedValues;

    static {
        String[] stateNames = new String[]{"NONE", "RELEASE", "PRESS"};
        cachedValues = new MouseButtonActionState[]{NONE, PRESS, RELEASE};
    }
}

