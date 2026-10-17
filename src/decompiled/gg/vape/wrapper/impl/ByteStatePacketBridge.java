/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.Packet;

public class ByteStatePacketBridge
extends Packet {
    public ByteStatePacketBridge(Object handle) {
        super(handle);
    }

    public byte getHeadYaw() {
        return ByteStatePacketBridge.vapeInstance.getMappingsMapperCompat().hy.getHeadYaw(this.I);
    }
}

