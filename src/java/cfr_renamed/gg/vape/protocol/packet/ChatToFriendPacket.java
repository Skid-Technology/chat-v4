/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ChatToFriendResponsePacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class ChatToFriendPacket
extends ZeusTrackedPacket<ChatToFriendResponsePacket> {
    private long var_long_q;
    private static int[] var_int_arr_l;
    private String var_java_lang_String_Z;

    public long S() {
        return this.var_long_q;
    }

    public String Q() {
        return this.var_java_lang_String_Z;
    }

    public static void W(int[] nArray) {
        var_int_arr_l = nArray;
    }

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
        this.var_long_q = zeusPacketBuffer.readLong();
        this.var_java_lang_String_Z = zeusPacketBuffer.readString(255);
    }

    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeLong(this.var_long_q);
        zeusPacketBuffer.writeString(this.var_java_lang_String_Z);
    }

    public ChatToFriendPacket(UserModel userModel, String string) {
        this.var_long_q = userModel.getId();
        this.var_java_lang_String_Z = string;
    }

    public static int[] Y() {
        return var_int_arr_l;
    }

    public ChatToFriendPacket() {
    }

    static {
        if (ChatToFriendPacket.Y() == null) {
            ChatToFriendPacket.W(new int[1]);
        }
    }
}

