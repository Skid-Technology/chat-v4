/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.Packet;

public class CPacketHeldItemChange
extends Packet {
    public static CPacketHeldItemChange create(int selectedSlot) {
        return new CPacketHeldItemChange(CPacketHeldItemChange.vapeInstance.getMappingsMapperCompat().Ck.createPacket(selectedSlot));
    }

    public CPacketHeldItemChange(Object handle) {
        super(handle);
    }
}

