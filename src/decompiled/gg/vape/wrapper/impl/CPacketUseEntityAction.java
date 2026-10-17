/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.CPacketUseEntity;
import gg.vape.wrapper.impl.CPacketUseEntityActionConstructorMarker;

public class CPacketUseEntityAction
extends Wrapper {
    private CPacketUseEntityAction(Object handle) {
        super(handle);
    }

    public CPacketUseEntityAction(Object handle, CPacketUseEntityActionConstructorMarker constructorMarker) {
        this(handle);
    }

    public CPacketUseEntity getType() {
        return new CPacketUseEntity(CPacketUseEntityAction.vapeInstance.getMappingsMapperCompat().qz.getType(this.I), null);
    }
}

