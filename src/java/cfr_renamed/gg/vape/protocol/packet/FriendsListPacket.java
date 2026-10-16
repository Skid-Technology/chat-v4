/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.FriendsListResponsePacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class FriendsListPacket
extends ZeusTrackedPacket<FriendsListResponsePacket> {
    private static boolean var_boolean_K;

    public static boolean s() {
        return var_boolean_K;
    }

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
    }

    public static void m(boolean bl) {
        var_boolean_K = bl;
    }

    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
    }

    public static boolean e() {
        boolean bl = FriendsListPacket.s();
        return !bl;
    }

    static {
        if (FriendsListPacket.e()) {
            FriendsListPacket.m(true);
        }
    }
}

