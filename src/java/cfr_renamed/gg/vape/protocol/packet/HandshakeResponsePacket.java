/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.HandshakePacket;
import gg.vape.protocol.packet.HandshakeStatus;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class HandshakeResponsePacket
extends ZeusTrackedPacket<HandshakePacket> {
    private static boolean var_boolean_B;
    private HandshakeStatus var_gg_vape_protocol_packet_HandshakeStatus_f;

    public HandshakeResponsePacket() {
    }

    @Override
    public void x(ZeusPacketBuffer gx_12) {
        this.var_gg_vape_protocol_packet_HandshakeStatus_f = gx_12.readEnum(HandshakeStatus.class);
    }

    public HandshakeResponsePacket(HandshakePacket tz_02, HandshakeStatus eG) {
        super(tz_02);
        this.var_gg_vape_protocol_packet_HandshakeStatus_f = eG;
    }

    public static boolean V() {
        return var_boolean_B;
    }

    public HandshakeStatus Q() {
        return this.var_gg_vape_protocol_packet_HandshakeStatus_f;
    }

    @Override
    public void T(ZeusPacketBuffer gx_12) {
        gx_12.writeEnum(this.var_gg_vape_protocol_packet_HandshakeStatus_f);
    }

    public static void i(boolean bl) {
        var_boolean_B = bl;
    }

    public static boolean w() {
        boolean bl = HandshakeResponsePacket.V();
        return true;
    }

    static {
        if (HandshakeResponsePacket.V()) {
            HandshakeResponsePacket.i(true);
        }
    }
}

