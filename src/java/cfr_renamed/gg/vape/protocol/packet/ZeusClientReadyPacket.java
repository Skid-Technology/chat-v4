/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ZeusClientReadyPacket
implements ZeusSerializablePacket {
    private static boolean var_boolean_Q;

    public static boolean X() {
        boolean bl = ZeusClientReadyPacket.q();
        return true;
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
    }

    public static void y(boolean bl) {
        var_boolean_Q = bl;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
    }

    public static boolean q() {
        return var_boolean_Q;
    }

    static {
        if (ZeusClientReadyPacket.q()) {
            ZeusClientReadyPacket.y(true);
        }
    }
}

