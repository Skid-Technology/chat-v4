/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ClientGroupLeaderKickResponsePacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class ClientGroupLeaderKickPacket
extends ZeusTrackedPacket<ClientGroupLeaderKickResponsePacket> {
    private long var_long_f;

    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeLong(this.var_long_f);
    }

    public ClientGroupLeaderKickPacket() {
    }

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
        this.var_long_f = zeusPacketBuffer.readLong();
    }

    public long H() {
        return this.var_long_f;
    }

    public ClientGroupLeaderKickPacket(UserModel userModel) {
        this.var_long_f = userModel.getId();
    }
}

