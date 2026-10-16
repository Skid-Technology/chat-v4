/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.HeartbeatPacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;
import org.jetbrains.annotations.Nullable;

public class HeartbeatResponsePacket
extends ZeusTrackedPacket<HeartbeatPacket> {
    @Override
    public void T(ZeusPacketBuffer gx_12) {
    }

    public HeartbeatResponsePacket(@Nullable HeartbeatPacket tW) {
        super(tW);
    }

    @Override
    public void x(ZeusPacketBuffer gx_12) {
    }

    public HeartbeatResponsePacket() {
    }
}

