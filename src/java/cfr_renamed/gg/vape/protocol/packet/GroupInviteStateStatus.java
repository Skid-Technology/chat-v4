/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

public enum GroupInviteStateStatus {
    SUCCESSFULLY_ACCEPTED,
    SUCCESSFULLY_DECLINED,
    GROUP_FULL,
    FAILED;

    private final static GroupInviteStateStatus[] var_gg_vape_protocol_packet_GroupInviteStateStatus_arr_O;

    static {
        String[] stringArray = new String[]{"FAILED", "SUCCESSFULLY_ACCEPTED", "SUCCESSFULLY_DECLINED", "GROUP_FULL"};
        var_gg_vape_protocol_packet_GroupInviteStateStatus_arr_O = new GroupInviteStateStatus[]{SUCCESSFULLY_ACCEPTED, SUCCESSFULLY_DECLINED, GROUP_FULL, FAILED};
    }
}

