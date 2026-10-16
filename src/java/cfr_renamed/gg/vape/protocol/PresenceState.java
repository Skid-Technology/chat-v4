/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol;

public enum PresenceState {
    ONLINE,
    AWAY,
    OFFLINE;

    private final static PresenceState[] var_gg_vape_protocol_PresenceState_arr_Q;

    static {
        String[] stringArray = new String[]{"OFFLINE", "AWAY", "ONLINE"};
        var_gg_vape_protocol_PresenceState_arr_Q = new PresenceState[]{ONLINE, AWAY, OFFLINE};
    }
}

