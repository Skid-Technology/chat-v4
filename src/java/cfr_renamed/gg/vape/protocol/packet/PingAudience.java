/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

public enum PingAudience {
    FRIENDS,
    GROUP;

    private final static PingAudience[] var_gg_vape_protocol_packet_PingAudience_arr_K;

    static {
        String[] stringArray = new String[]{"GROUP", "FRIENDS"};
        var_gg_vape_protocol_packet_PingAudience_arr_K = new PingAudience[]{FRIENDS, GROUP};
    }
}

