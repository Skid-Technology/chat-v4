/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol;

public enum PresenceState {
    ONLINE,
    AWAY,
    OFFLINE;

    private static final PresenceState[] Q;

    static {
        String[] stringArray = new String[]{"OFFLINE", "AWAY", "ONLINE"};
        Q = new PresenceState[]{ONLINE, AWAY, OFFLINE};
    }
}

