/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

public enum HandshakeStatus {
    MATCHING,
    OUTDATED_CLIENT,
    OUTDATED_SERVER;

    private final static HandshakeStatus[] var_gg_vape_protocol_packet_HandshakeStatus_arr_q;

    static {
        String[] stringArray = new String[]{"OUTDATED_SERVER", "MATCHING", "OUTDATED_CLIENT"};
        var_gg_vape_protocol_packet_HandshakeStatus_arr_q = new HandshakeStatus[]{MATCHING, OUTDATED_CLIENT, OUTDATED_SERVER};
    }
}

