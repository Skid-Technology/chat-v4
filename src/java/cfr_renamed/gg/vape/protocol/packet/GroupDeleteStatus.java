/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

public enum GroupDeleteStatus {
    SUCCESS,
    NO_PERMISSION,
    NO_GROUP;

    private final static GroupDeleteStatus[] var_gg_vape_protocol_packet_GroupDeleteStatus_arr_N;

    static {
        String[] stringArray = new String[]{"NO_GROUP", "SUCCESS", "NO_PERMISSION"};
        var_gg_vape_protocol_packet_GroupDeleteStatus_arr_N = new GroupDeleteStatus[]{SUCCESS, NO_PERMISSION, NO_GROUP};
    }
}

