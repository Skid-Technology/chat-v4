/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.CPacketUseEntityActionConstructorMarker;

public class CPacketUseEntity
extends Wrapper {
    private CPacketUseEntity(Object handle) {
        super(handle);
    }

    public static CPacketUseEntity attack() {
        return new CPacketUseEntity(CPacketUseEntity.vapeInstance.getMappingsMapperCompat().Ra.getAttackAction());
    }

    public static CPacketUseEntity interactAt() {
        return new CPacketUseEntity(CPacketUseEntity.vapeInstance.getMappingsMapperCompat().Ra.getInteractAtAction());
    }

    public static CPacketUseEntity interact() {
        return new CPacketUseEntity(CPacketUseEntity.vapeInstance.getMappingsMapperCompat().Ra.getInteractAction());
    }

    public CPacketUseEntity(Object handle, CPacketUseEntityActionConstructorMarker constructorMarker) {
        this(handle);
    }
}

