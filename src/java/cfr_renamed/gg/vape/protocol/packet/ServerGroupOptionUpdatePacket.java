/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.GroupOption;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ServerGroupOptionUpdatePacket
implements ZeusSerializablePacket {
    private GroupOption option;
    private static int var_int_m;
    private Object value;

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.option = zeusPacketBuffer.readEnum(GroupOption.class);
        this.value = this.option.getValueReader().apply(zeusPacketBuffer);
    }

    public static int s() {
        int n = ServerGroupOptionUpdatePacket.T();
        if (n == 0) {
            return 32;
        }
        return 0;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeEnum(this.option);
        this.option.getValueWriter().accept(zeusPacketBuffer, this.value);
    }

    public Object getValue() {
        return this.value;
    }

    public GroupOption getOption() {
        return this.option;
    }

    public static int T() {
        return var_int_m;
    }

    public ServerGroupOptionUpdatePacket(GroupOption option, Object value) {
        this.option = option;
        this.value = value;
    }

    public static void j(int n) {
        var_int_m = n;
    }

    public ServerGroupOptionUpdatePacket() {
    }

    static {
        if (ServerGroupOptionUpdatePacket.s() != 0) {
            ServerGroupOptionUpdatePacket.j(13);
        }
    }
}

