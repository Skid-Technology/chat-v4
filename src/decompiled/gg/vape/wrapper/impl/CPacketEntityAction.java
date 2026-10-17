/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.CPacketEntityActionAction;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.Packet;

public class CPacketEntityAction
extends Packet {
    public static CPacketEntityAction create(Entity entity, CPacketEntityActionAction action) {
        return new CPacketEntityAction(CPacketEntityAction.vapeInstance.getMappingsMapperCompat().Y.createPacket(entity.getObject(), action.getObject()));
    }

    public CPacketEntityAction(Object handle) {
        super(handle);
    }

    public static CPacketEntityAction create(Entity entity, int actionId) {
        return new CPacketEntityAction(CPacketEntityAction.vapeInstance.getMappingsMapperCompat().Y.createPacket(entity.getObject(), actionId));
    }
}

