/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.C03PacketPlayer;

public class CPacketPlayer
extends C03PacketPlayer {
    public static CPacketPlayer create(boolean onGround, boolean horizontalCollision) {
        return new CPacketPlayer(CPacketPlayer.vapeInstance.getMappingsMapperCompat().C_.newInstance(onGround, horizontalCollision));
    }

    private CPacketPlayer(Object wrappedObject) {
        super(wrappedObject);
    }
}

