/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.GroupOption;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class GroupOptionUpdatePacket
implements ZeusSerializablePacket {
    private GroupOption option;
    private static String var_java_lang_String_r;
    private Object value;

    public static void C(String string) {
        var_java_lang_String_r = string;
    }

    public GroupOption getOption() {
        return this.option;
    }

    public Object getValue() {
        return this.value;
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.option = zeusPacketBuffer.readEnum(GroupOption.class);
        this.value = this.option.getValueReader().apply(zeusPacketBuffer);
    }

    public static String b() {
        return var_java_lang_String_r;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeEnum(this.option);
        this.option.getValueWriter().accept(zeusPacketBuffer, this.value);
    }

    public GroupOptionUpdatePacket() {
    }

    public GroupOptionUpdatePacket(GroupOption option, Object value) {
        this.option = option;
        this.value = value;
    }

    static {
        if (GroupOptionUpdatePacket.b() == null) {
            GroupOptionUpdatePacket.C("UDYYwc");
        }
    }
}

