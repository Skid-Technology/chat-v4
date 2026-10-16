/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

public enum FriendRequestUpdateStatus {
    ACCEPTED,
    DECLINED,
    UNKNOWN;

    private final static FriendRequestUpdateStatus[] var_gg_vape_protocol_packet_FriendRequestUpdateStatus_arr_k;

    static {
        String[] stringArray = new String[]{"DECLINED", "ACCEPTED", "UNKNOWN"};
        var_gg_vape_protocol_packet_FriendRequestUpdateStatus_arr_k = new FriendRequestUpdateStatus[]{ACCEPTED, DECLINED, UNKNOWN};
    }
}

