/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.manager.client.OnlineDisconnectReason;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ServerDisconnectPacket
implements ZeusSerializablePacket {
    private static int[] var_int_arr_R;
    private OnlineDisconnectReason var_gg_vape_manager_client_OnlineDisconnectReason_t;

    public static int[] i() {
        return var_int_arr_R;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeEnum(this.var_gg_vape_manager_client_OnlineDisconnectReason_t);
    }

    public ServerDisconnectPacket(OnlineDisconnectReason onlineDisconnectReason) {
        this.var_gg_vape_manager_client_OnlineDisconnectReason_t = onlineDisconnectReason;
    }

    public OnlineDisconnectReason q() {
        return this.var_gg_vape_manager_client_OnlineDisconnectReason_t;
    }

    public ServerDisconnectPacket() {
    }

    public static void U(int[] nArray) {
        var_int_arr_R = nArray;
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.var_gg_vape_manager_client_OnlineDisconnectReason_t = zeusPacketBuffer.readEnum(OnlineDisconnectReason.class);
    }

    static {
        if (ServerDisconnectPacket.i() != null) {
            ServerDisconnectPacket.U(new int[1]);
        }
    }
}

