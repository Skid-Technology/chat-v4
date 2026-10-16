/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.HandshakeResponsePacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class HandshakePacket
extends ZeusTrackedPacket<HandshakeResponsePacket> {
    private int var_int_t;
    private static int[] var_int_arr_K;

    public HandshakePacket(int n) {
        this.var_int_t = n;
    }

    public HandshakePacket() {
    }

    public static int[] P() {
        return var_int_arr_K;
    }

    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeVarInt(this.var_int_t);
    }

    public static void C(int[] nArray) {
        var_int_arr_K = nArray;
    }

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
        this.var_int_t = zeusPacketBuffer.readVarInt();
    }

    public int E() {
        return this.var_int_t;
    }

    static {
        if (HandshakePacket.P() != null) {
            HandshakePacket.C(new int[5]);
        }
    }
}

