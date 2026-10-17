/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.CPacketUseEntityAction;
import gg.vape.wrapper.impl.Vec3;

public class CPacketUseEntityActionPacket
extends CPacketUseEntityAction {
    public CPacketUseEntityActionPacket(Object wrappedObject) {
        super(wrappedObject, null);
    }

    public Vec3 getLocation() {
        return new Vec3(CPacketUseEntityActionPacket.vapeInstance.getMappingsMapperCompat().h6.getLocation(this.I));
    }
}

