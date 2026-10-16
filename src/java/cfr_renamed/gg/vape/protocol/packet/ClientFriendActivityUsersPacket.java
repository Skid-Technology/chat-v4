/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ClientFriendActivityUsersAction;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ClientFriendActivityUsersPacket
implements ZeusSerializablePacket {
    private static boolean var_boolean_i;
    private ClientFriendActivityUsersAction var_gg_vape_protocol_packet_ClientFriendActivityUsersAction_h;
    private long[] var_long_arr_a;

    public long[] B() {
        return this.var_long_arr_a;
    }

    public static boolean F() {
        boolean bl = ClientFriendActivityUsersPacket.c();
        return !bl;
    }

    private ClientFriendActivityUsersPacket(ClientFriendActivityUsersAction clientFriendActivityUsersAction, long[] lArray) {
        this(clientFriendActivityUsersAction);
        this.var_long_arr_a = lArray;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeEnum(this.var_gg_vape_protocol_packet_ClientFriendActivityUsersAction_h);
        if (this.var_gg_vape_protocol_packet_ClientFriendActivityUsersAction_h == ClientFriendActivityUsersAction.ADD) {
            zeusPacketBuffer.writeVarInt(this.var_long_arr_a.length);
            for (long l : this.var_long_arr_a) {
                zeusPacketBuffer.writeLong(l);
            }
        }
    }

    public static void Q(boolean bl) {
        var_boolean_i = bl;
    }

    public ClientFriendActivityUsersAction y() {
        return this.var_gg_vape_protocol_packet_ClientFriendActivityUsersAction_h;
    }

    public static ClientFriendActivityUsersPacket X(long[] lArray) {
        return new ClientFriendActivityUsersPacket(ClientFriendActivityUsersAction.ADD, lArray);
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.var_gg_vape_protocol_packet_ClientFriendActivityUsersAction_h = zeusPacketBuffer.readEnum(ClientFriendActivityUsersAction.class);
        if (this.var_gg_vape_protocol_packet_ClientFriendActivityUsersAction_h == ClientFriendActivityUsersAction.ADD) {
            int n = zeusPacketBuffer.readVarInt();
            this.var_long_arr_a = new long[n];
            for (int i = 0; i < n; ++i) {
                this.var_long_arr_a[i] = zeusPacketBuffer.readLong();
            }
        }
    }

    private ClientFriendActivityUsersPacket(ClientFriendActivityUsersAction clientFriendActivityUsersAction) {
        this.var_gg_vape_protocol_packet_ClientFriendActivityUsersAction_h = clientFriendActivityUsersAction;
    }

    public static ClientFriendActivityUsersPacket u() {
        return new ClientFriendActivityUsersPacket(ClientFriendActivityUsersAction.CHANGED_WORLD);
    }

    public ClientFriendActivityUsersPacket() {
    }

    public static boolean c() {
        return var_boolean_i;
    }

    static {
        if (!ClientFriendActivityUsersPacket.F()) {
            ClientFriendActivityUsersPacket.Q(true);
        }
    }
}

