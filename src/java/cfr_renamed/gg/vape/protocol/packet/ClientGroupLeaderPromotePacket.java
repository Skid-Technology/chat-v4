/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ClientGroupLeaderPromoteResponsePacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class ClientGroupLeaderPromotePacket
extends ZeusTrackedPacket<ClientGroupLeaderPromoteResponsePacket> {
    private long var_long_U;

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
        this.var_long_U = zeusPacketBuffer.readLong();
    }

    public ClientGroupLeaderPromotePacket(UserModel userModel) {
        this.var_long_U = userModel.getId();
    }

    public ClientGroupLeaderPromotePacket() {
    }

    public long d() {
        return this.var_long_U;
    }

    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeLong(this.var_long_U);
    }
}

