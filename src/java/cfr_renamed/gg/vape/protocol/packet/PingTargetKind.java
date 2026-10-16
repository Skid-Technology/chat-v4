/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

public enum PingTargetKind {
    POSITION,
    BLOCK,
    ENTITY;

    private final static PingTargetKind[] var_gg_vape_protocol_packet_PingTargetKind_arr_T;

    static {
        String[] stringArray = new String[]{"BLOCK", "POSITION", "ENTITY"};
        var_gg_vape_protocol_packet_PingTargetKind_arr_T = new PingTargetKind[]{POSITION, BLOCK, ENTITY};
    }
}

