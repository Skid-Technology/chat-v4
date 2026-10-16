/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;
import java.util.UUID;

public class ClientMinecraftProfilePacket
implements ZeusSerializablePacket {
    private UUID var_java_util_UUID_r;
    private String var_java_lang_String_O;

    @Override
    public void o(ZeusPacketBuffer gx_12) {
        gx_12.writeUuid(this.var_java_util_UUID_r);
        gx_12.writeString(this.var_java_lang_String_O);
    }

    public ClientMinecraftProfilePacket() {
    }

    public String y() {
        return this.var_java_lang_String_O;
    }

    public ClientMinecraftProfilePacket(UUID uUID, String string) {
        this.var_java_util_UUID_r = uUID;
        this.var_java_lang_String_O = string;
    }

    @Override
    public void S(ZeusPacketBuffer gx_12) {
        this.var_java_util_UUID_r = gx_12.readUuid();
        this.var_java_lang_String_O = gx_12.readString(16);
    }

    public UUID e() {
        return this.var_java_util_UUID_r;
    }
}

