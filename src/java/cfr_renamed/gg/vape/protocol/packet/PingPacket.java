/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.PingResponsePacket;
import gg.vape.protocol.packet.PingTargetData;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class PingPacket
extends ZeusTrackedPacket<PingResponsePacket> {
    private PingTargetData var_gg_vape_protocol_packet_PingTargetData_L;
    private static String var_java_lang_String_J;

    @Override
    public void T(ZeusPacketBuffer gx_12) {
        this.var_gg_vape_protocol_packet_PingTargetData_L.n(gx_12);
    }

    public PingPacket(PingTargetData pingTargetData) {
        this.var_gg_vape_protocol_packet_PingTargetData_L = pingTargetData;
    }

    @Override
    public void x(ZeusPacketBuffer gx_12) {
        this.var_gg_vape_protocol_packet_PingTargetData_L = new PingTargetData(gx_12);
    }

    public PingTargetData i() {
        return this.var_gg_vape_protocol_packet_PingTargetData_L;
    }

    public static void O(String string) {
        var_java_lang_String_J = string;
    }

    public static String F() {
        return var_java_lang_String_J;
    }

    public PingPacket() {
    }

    static {
        if (PingPacket.F() != null) {
            PingPacket.O("cFpSzb");
        }
    }
}

