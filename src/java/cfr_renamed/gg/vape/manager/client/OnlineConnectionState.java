/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.manager.client;

public enum OnlineConnectionState {
    OFFLINE,
    OUTDATED_CLIENT,
    OUTDATED_SERVER,
    CONNECTING,
    ONLINE;

    private final static OnlineConnectionState[] cachedValues;
    private static String[] obfuscationState;

    public static void setObfuscationState(String[] state) {
        obfuscationState = state;
    }

    public boolean isOfflineState() {
        return this == OFFLINE || this == OUTDATED_CLIENT || this == OUTDATED_SERVER;
    }

    public static String[] getObfuscationState() {
        return obfuscationState;
    }

    static {
        if (OnlineConnectionState.getObfuscationState() == null) {
            OnlineConnectionState.setObfuscationState(new String[3]);
        }
        String[] obfuscationNames = new String[]{"OFFLINE", "OUTDATED_CLIENT", "ONLINE", "OUTDATED_SERVER", "CONNECTING"};
        cachedValues = new OnlineConnectionState[]{OFFLINE, OUTDATED_CLIENT, OUTDATED_SERVER, CONNECTING, ONLINE};
    }
}

