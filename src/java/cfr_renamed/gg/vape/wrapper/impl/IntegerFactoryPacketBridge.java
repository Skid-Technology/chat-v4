/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.Packet;

public class IntegerFactoryPacketBridge
extends Packet {
    public static IntegerFactoryPacketBridge createConfirmTeleportPacket(int teleportId) {
        return new IntegerFactoryPacketBridge(IntegerFactoryPacketBridge.vapeInstance.getMappingsMapperCompat().hp.createConfirmTeleportPacket(teleportId));
    }

    public IntegerFactoryPacketBridge(Object handle) {
        super(handle);
    }
}

