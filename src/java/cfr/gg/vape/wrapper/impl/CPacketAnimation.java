/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.EnumHand;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Packet;

public class CPacketAnimation
extends Packet {
    public static CPacketAnimation create() {
        if (ForgeVersion.MC_1_12_2.d()) {
            return new CPacketAnimation(CPacketAnimation.vapeInstance.getMappingsMapperCompat().Q3.createAnimationPacket(EnumHand.mainHand().getObject()));
        }
        return new CPacketAnimation(CPacketAnimation.vapeInstance.getMappingsMapperCompat().Q3.createAnimationPacket());
    }

    public CPacketAnimation(Object handle) {
        super(handle);
    }
}

