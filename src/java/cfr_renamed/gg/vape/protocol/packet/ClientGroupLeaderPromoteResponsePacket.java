/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ClientGroupLeaderPromotePacket;
import gg.vape.protocol.packet.ClientGroupLeaderPromoteStatus;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class ClientGroupLeaderPromoteResponsePacket
extends ZeusTrackedPacket<ClientGroupLeaderPromotePacket> {
    private ClientGroupLeaderPromoteStatus var_gg_vape_protocol_packet_ClientGroupLeaderPromoteStatus_H;

    @Override
    public void T(ZeusPacketBuffer gx_12) {
        gx_12.writeEnum(this.var_gg_vape_protocol_packet_ClientGroupLeaderPromoteStatus_H);
    }

    @Override
    public void x(ZeusPacketBuffer gx_12) {
        this.var_gg_vape_protocol_packet_ClientGroupLeaderPromoteStatus_H = gx_12.readEnum(ClientGroupLeaderPromoteStatus.class);
    }

    public ClientGroupLeaderPromoteStatus M() {
        return this.var_gg_vape_protocol_packet_ClientGroupLeaderPromoteStatus_H;
    }

    public ClientGroupLeaderPromoteResponsePacket(ClientGroupLeaderPromotePacket gl_02, ClientGroupLeaderPromoteStatus vp_12) {
        super(gl_02);
        this.var_gg_vape_protocol_packet_ClientGroupLeaderPromoteStatus_H = vp_12;
    }

    public ClientGroupLeaderPromoteResponsePacket() {
    }
}

