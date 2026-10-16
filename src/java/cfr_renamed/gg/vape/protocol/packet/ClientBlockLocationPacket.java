/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ClientBlockLocationPacket
implements ZeusSerializablePacket {
    private int var_int_x;
    private int var_int_y;
    private int var_int_z;
    private long userId;

    public int getZ() {
        return this.var_int_z;
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.userId = zeusPacketBuffer.readLong();
        this.var_int_x = zeusPacketBuffer.readInt();
        this.var_int_y = zeusPacketBuffer.readInt();
        this.var_int_z = zeusPacketBuffer.readInt();
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeLong(this.userId);
        zeusPacketBuffer.writeInt(this.var_int_x);
        zeusPacketBuffer.writeInt(this.var_int_y);
        zeusPacketBuffer.writeInt(this.var_int_z);
    }

    public int getX() {
        return this.var_int_x;
    }

    public int getY() {
        return this.var_int_y;
    }

    public ClientBlockLocationPacket(long userId, int x, int y, int z) {
        this.userId = userId;
        this.var_int_x = x;
        this.var_int_y = y;
        this.var_int_z = z;
    }

    public ClientBlockLocationPacket() {
    }

    public long getUserId() {
        return this.userId;
    }
}

