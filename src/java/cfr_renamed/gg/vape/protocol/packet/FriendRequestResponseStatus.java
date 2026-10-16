/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

public enum FriendRequestResponseStatus {
    SENT,
    SELF_REQUEST,
    ALREADY_FRIENDS,
    ALREADY_SENT,
    INVALID_USER,
    INVALID;

    private final static FriendRequestResponseStatus[] var_gg_vape_protocol_packet_FriendRequestResponseStatus_arr_a;

    static {
        String[] stringArray = new String[]{"INVALID", "ALREADY_SENT", "SENT", "INVALID_USER", "ALREADY_FRIENDS", "SELF_REQUEST"};
        var_gg_vape_protocol_packet_FriendRequestResponseStatus_arr_a = new FriendRequestResponseStatus[]{SENT, SELF_REQUEST, ALREADY_FRIENDS, ALREADY_SENT, INVALID_USER, INVALID};
    }
}

