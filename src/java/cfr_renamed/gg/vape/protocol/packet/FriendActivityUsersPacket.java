/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.FriendActivityUsersAction;
import gg.vape.protocol.packet.ZeusSerializablePacket;
import gg.vape.ui.click.component.GuiComponent;

public class FriendActivityUsersPacket
implements ZeusSerializablePacket {
    private long[] var_long_arr_e;
    private FriendActivityUsersAction var_gg_vape_protocol_packet_FriendActivityUsersAction_B;
    private static GuiComponent[] var_gg_vape_ui_click_component_GuiComponent_arr_j;

    public FriendActivityUsersAction G() {
        return this.var_gg_vape_protocol_packet_FriendActivityUsersAction_B;
    }

    public static FriendActivityUsersPacket h(long[] lArray) {
        return new FriendActivityUsersPacket(FriendActivityUsersAction.CHANGED_WORLD, lArray);
    }

    public static GuiComponent[] L() {
        return var_gg_vape_ui_click_component_GuiComponent_arr_j;
    }

    public FriendActivityUsersPacket() {
    }

    public static void x(GuiComponent[] guiComponentArray) {
        var_gg_vape_ui_click_component_GuiComponent_arr_j = guiComponentArray;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeEnum(this.var_gg_vape_protocol_packet_FriendActivityUsersAction_B);
        zeusPacketBuffer.writeVarInt(this.var_long_arr_e.length);
        for (long l : this.var_long_arr_e) {
            zeusPacketBuffer.writeLong(l);
        }
    }

    private FriendActivityUsersPacket(FriendActivityUsersAction friendActivityUsersAction, long[] lArray) {
        this.var_gg_vape_protocol_packet_FriendActivityUsersAction_B = friendActivityUsersAction;
        this.var_long_arr_e = lArray;
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.var_gg_vape_protocol_packet_FriendActivityUsersAction_B = zeusPacketBuffer.readEnum(FriendActivityUsersAction.class);
        int n = zeusPacketBuffer.readVarInt();
        this.var_long_arr_e = new long[n];
        for (int i = 0; i < n; ++i) {
            this.var_long_arr_e[i] = zeusPacketBuffer.readLong();
        }
    }

    public static FriendActivityUsersPacket E(long[] lArray) {
        return new FriendActivityUsersPacket(FriendActivityUsersAction.ADD, lArray);
    }

    public long[] Y() {
        return this.var_long_arr_e;
    }

    static {
        if (FriendActivityUsersPacket.L() != null) {
            FriendActivityUsersPacket.x(new GuiComponent[3]);
        }
    }
}

