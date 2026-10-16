/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ClientGroupLeaderKickPacket;
import gg.vape.protocol.packet.ClientGroupLeaderKickStatus;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class ClientGroupLeaderKickResponsePacket
extends ZeusTrackedPacket<ClientGroupLeaderKickPacket> {
    private ClientGroupLeaderKickStatus var_gg_vape_protocol_packet_ClientGroupLeaderKickStatus_K;

    @Override
    public void x(ZeusPacketBuffer gx_12) {
        this.var_gg_vape_protocol_packet_ClientGroupLeaderKickStatus_K = gx_12.readEnum(ClientGroupLeaderKickStatus.class);
    }

    @Override
    public void T(ZeusPacketBuffer gx_12) {
        gx_12.writeEnum(this.var_gg_vape_protocol_packet_ClientGroupLeaderKickStatus_K);
    }

    public ClientGroupLeaderKickResponsePacket() {
    }

    public ClientGroupLeaderKickResponsePacket(ClientGroupLeaderKickPacket gq_02, ClientGroupLeaderKickStatus iH) {
        super(gq_02);
        this.var_gg_vape_protocol_packet_ClientGroupLeaderKickStatus_K = iH;
    }

    public ClientGroupLeaderKickStatus P() {
        return this.var_gg_vape_protocol_packet_ClientGroupLeaderKickStatus_K;
    }
}

