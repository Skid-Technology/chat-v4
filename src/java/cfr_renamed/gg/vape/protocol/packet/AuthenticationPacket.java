/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.AuthenticationResponsePacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;
import java.util.UUID;

public class AuthenticationPacket
extends ZeusTrackedPacket<AuthenticationResponsePacket> {
    private UUID var_java_util_UUID_I;
    private String var_java_lang_String_E;
    private String var_java_lang_String_V;

    @Override
    public void x(ZeusPacketBuffer zeusPacketBuffer) {
        this.var_java_lang_String_E = zeusPacketBuffer.readString(255);
        this.var_java_util_UUID_I = zeusPacketBuffer.readUuid();
        this.var_java_lang_String_V = zeusPacketBuffer.readString(16);
    }

    public UUID i() {
        return this.var_java_util_UUID_I;
    }

    public String W() {
        return this.var_java_lang_String_V;
    }

    @Override
    public void T(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeString(this.var_java_lang_String_E);
        zeusPacketBuffer.writeUuid(this.var_java_util_UUID_I);
        zeusPacketBuffer.writeString(this.var_java_lang_String_V);
    }

    public AuthenticationPacket() {
    }

    public AuthenticationPacket(String string, UUID uUID, String string2) {
        this.var_java_lang_String_E = string;
        this.var_java_util_UUID_I = uUID;
        this.var_java_lang_String_V = string2;
    }

    public String q() {
        return this.var_java_lang_String_E;
    }
}

