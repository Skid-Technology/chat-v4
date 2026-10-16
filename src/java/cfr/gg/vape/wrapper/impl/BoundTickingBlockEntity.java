/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.TickingBlockEntity;

public class BoundTickingBlockEntity
extends TickingBlockEntity {
    public BoundTickingBlockEntity(Object wrappedObject) {
        super(wrappedObject);
    }

    public Object getBlockEntity() {
        return BoundTickingBlockEntity.vapeInstance.getMappingsMapperCompat().boundTickingBlockEntity.getBlockEntity(this.I);
    }
}

