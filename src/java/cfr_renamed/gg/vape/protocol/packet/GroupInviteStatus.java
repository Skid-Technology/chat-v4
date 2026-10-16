/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

public enum GroupInviteStatus {
    SUCCESS,
    ALREADY_INVITED,
    TOO_MANY_INVITES,
    NOT_ONLINE,
    FAILED;

    private final static GroupInviteStatus[] var_gg_vape_protocol_packet_GroupInviteStatus_arr_O;

    static {
        String[] stringArray = new String[]{"NOT_ONLINE", "ALREADY_INVITED", "SUCCESS", "FAILED", "TOO_MANY_INVITES"};
        var_gg_vape_protocol_packet_GroupInviteStatus_arr_O = new GroupInviteStatus[]{SUCCESS, ALREADY_INVITED, TOO_MANY_INVITES, NOT_ONLINE, FAILED};
    }
}

