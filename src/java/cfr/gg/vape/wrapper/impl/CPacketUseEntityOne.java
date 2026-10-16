/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.ModelPlayer;
import gg.vape.wrapper.impl.Packet;

public class CPacketUseEntityOne
extends Packet {
    public CPacketUseEntityOne(Object handle) {
        super(handle);
    }

    public static CPacketUseEntityOne create(ModelPlayer playerCapabilities) {
        return new CPacketUseEntityOne(CPacketUseEntityOne.vapeInstance.getMappingsMapperCompat().RE.createPlayerAbilitiesPacket(playerCapabilities.getObject()));
    }
}

