/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.manager.client;

public enum OnlineAccountState {
    REGISTERED,
    BANNED,
    UNREGISTERED,
    REGISTRATION_OFFLINE,
    CONNECTING;

    private final static OnlineAccountState[] cachedValues;

    static {
        String[] obfuscationNames = new String[]{"REGISTRATION_OFFLINE", "CONNECTING", "BANNED", "REGISTERED", "UNREGISTERED"};
        cachedValues = new OnlineAccountState[]{REGISTERED, BANNED, UNREGISTERED, REGISTRATION_OFFLINE, CONNECTING};
    }
}

