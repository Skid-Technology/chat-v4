/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

public enum GroupInviteUpdateStatus {
    ACCEPTED,
    SENT,
    DECLINED;

    private final static GroupInviteUpdateStatus[] var_gg_vape_protocol_packet_GroupInviteUpdateStatus_arr_k;

    static {
        String[] stringArray = new String[]{"DECLINED", "ACCEPTED", "SENT"};
        var_gg_vape_protocol_packet_GroupInviteUpdateStatus_arr_k = new GroupInviteUpdateStatus[]{ACCEPTED, SENT, DECLINED};
    }
}

