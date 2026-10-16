/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEntityFishHook;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.CaughtEntity;

public class EntityFishHookState
extends Wrapper {
    public boolean isOpen() {
        return MEntityFishHook.isOpen(EntityFishHookState.vapeInstance.getMappingsMapperCompat().nettyChannel, this.I);
    }

    public CaughtEntity eventLoop() {
        return new CaughtEntity(MEntityFishHook.eventLoop(EntityFishHookState.vapeInstance.getMappingsMapperCompat().nettyChannel, this.I));
    }

    public EntityFishHookState(Object object) {
        super(object);
    }
}

