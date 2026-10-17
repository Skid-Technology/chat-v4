/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.Packet;

public class SPacketHeldItemChange
extends Packet {
    public SPacketHeldItemChange(Object handle) {
        super(handle);
    }

    public static SPacketHeldItemChange createCloseWindowPacket(int windowId) {
        return new SPacketHeldItemChange(SPacketHeldItemChange.vapeInstance.getMappingsMapperCompat().C6.createCloseWindowPacket(windowId));
    }
}

