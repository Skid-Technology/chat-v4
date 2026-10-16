/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.friend.activity;

import gg.vape.protocol.ZeusPacketBuffer;

public class ActivityPositionData {
    private final double var_double_x;
    private final double var_double_y;
    private final double var_double_z;

    ActivityPositionData(ZeusPacketBuffer buffer) {
        this.var_double_x = buffer.readDouble();
        this.var_double_y = buffer.readDouble();
        this.var_double_z = buffer.readDouble();
    }

    public void writeTo(ZeusPacketBuffer buffer) {
        buffer.writeDouble(this.var_double_x);
        buffer.writeDouble(this.var_double_y);
        buffer.writeDouble(this.var_double_z);
    }

    public double getZ() {
        return this.var_double_z;
    }

    public double getY() {
        return this.var_double_y;
    }

    public double getX() {
        return this.var_double_x;
    }

    public ActivityPositionData(double x, double y, double z) {
        this.var_double_x = x;
        this.var_double_y = y;
        this.var_double_z = z;
    }
}

