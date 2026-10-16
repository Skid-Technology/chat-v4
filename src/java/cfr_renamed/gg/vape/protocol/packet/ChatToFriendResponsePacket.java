/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ChatToFriendPacket;
import gg.vape.protocol.packet.ChatToFriendStatus;
import gg.vape.protocol.packet.ZeusTrackedPacket;
import gg.vape.ui.click.component.GuiComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChatToFriendResponsePacket
extends ZeusTrackedPacket<ChatToFriendPacket> {
    private ChatToFriendStatus var_gg_vape_protocol_packet_ChatToFriendStatus_n;
    @Nullable
    private String var_java_lang_String_N;
    private static GuiComponent[] var_gg_vape_ui_click_component_GuiComponent_arr_o;
    private long var_long_l;

    public ChatToFriendResponsePacket(@Nullable ChatToFriendPacket chatToFriendPacket, @NotNull String string) {
        this(chatToFriendPacket, ChatToFriendStatus.SUCCESS);
        this.var_java_lang_String_N = string;
        this.var_long_l = System.currentTimeMillis();
    }

    public ChatToFriendResponsePacket(@Nullable ChatToFriendPacket chatToFriendPacket, ChatToFriendStatus chatToFriendStatus) {
        super(chatToFriendPacket);
        this.var_gg_vape_protocol_packet_ChatToFriendStatus_n = chatToFriendStatus;
    }

    public static void T(GuiComponent[] guiComponentArray) {
        var_gg_vape_ui_click_component_GuiComponent_arr_o = guiComponentArray;
    }

    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeEnum(this.var_gg_vape_protocol_packet_ChatToFriendStatus_n);
        zeusPacketBuffer.writeString(this.var_java_lang_String_N);
        zeusPacketBuffer.writeLong(this.var_long_l);
    }

    public static GuiComponent[] A() {
        return var_gg_vape_ui_click_component_GuiComponent_arr_o;
    }

    public ChatToFriendStatus Z() {
        return this.var_gg_vape_protocol_packet_ChatToFriendStatus_n;
    }

    @Nullable
    public String X() {
        return this.var_java_lang_String_N;
    }

    public ChatToFriendResponsePacket() {
    }

    public long y() {
        return this.var_long_l;
    }

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
        this.var_gg_vape_protocol_packet_ChatToFriendStatus_n = zeusPacketBuffer.readEnum(ChatToFriendStatus.class);
        this.var_java_lang_String_N = zeusPacketBuffer.readString(255);
        this.var_long_l = zeusPacketBuffer.readLong();
    }

    static {
        if (ChatToFriendResponsePacket.A() != null) {
            ChatToFriendResponsePacket.T(new GuiComponent[1]);
        }
    }
}

