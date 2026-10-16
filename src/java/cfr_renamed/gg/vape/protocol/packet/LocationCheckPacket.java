/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class LocationCheckPacket
implements ZeusSerializablePacket {
    private int var_int_a;
    private int var_int_H;
    private int var_int_j;

    public int L() {
        return this.var_int_j;
    }

    @Override
    public void S(ZeusPacketBuffer gx_12) {
        this.var_int_H = gx_12.readInt();
        this.var_int_j = gx_12.readInt();
        this.var_int_a = gx_12.readInt();
    }

    @Override
    public void o(ZeusPacketBuffer gx_12) {
        gx_12.writeInt(this.var_int_H);
        gx_12.writeInt(this.var_int_j);
        gx_12.writeInt(this.var_int_a);
    }

    public int f() {
        return this.var_int_H;
    }

    public LocationCheckPacket(int n, int n2, int n3) {
        this.var_int_H = n;
        this.var_int_j = n2;
        this.var_int_a = n3;
    }

    public LocationCheckPacket() {
    }

    public int r() {
        return this.var_int_a;
    }
}

