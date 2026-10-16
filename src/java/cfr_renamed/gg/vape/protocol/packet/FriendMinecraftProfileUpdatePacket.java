/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;
import java.util.UUID;

public class FriendMinecraftProfileUpdatePacket
implements ZeusSerializablePacket {
    private String var_java_lang_String_v;
    private long var_long_p;
    private UUID var_java_util_UUID_Q;

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.var_long_p = zeusPacketBuffer.readLong();
        this.var_java_util_UUID_Q = zeusPacketBuffer.readUuid();
        this.var_java_lang_String_v = zeusPacketBuffer.readString(16);
    }

    public FriendMinecraftProfileUpdatePacket() {
    }

    public String G() {
        return this.var_java_lang_String_v;
    }

    public FriendMinecraftProfileUpdatePacket(UserModel userModel, UUID uUID, String string) {
        this.var_long_p = userModel.getId();
        this.var_java_util_UUID_Q = uUID;
        this.var_java_lang_String_v = string;
    }

    public UUID l() {
        return this.var_java_util_UUID_Q;
    }

    public long H() {
        return this.var_long_p;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeLong(this.var_long_p);
        zeusPacketBuffer.writeUuid(this.var_java_util_UUID_Q);
        zeusPacketBuffer.writeString(this.var_java_lang_String_v);
    }
}

