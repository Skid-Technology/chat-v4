/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

public enum ChatToFriendStatus {
    SUCCESS,
    NOT_FRIENDS,
    OFFLINE;

    private final static ChatToFriendStatus[] var_gg_vape_protocol_packet_ChatToFriendStatus_arr_f;

    static {
        String[] stringArray = new String[]{"SUCCESS", "OFFLINE", "NOT_FRIENDS"};
        var_gg_vape_protocol_packet_ChatToFriendStatus_arr_f = new ChatToFriendStatus[]{SUCCESS, NOT_FRIENDS, OFFLINE};
    }
}

