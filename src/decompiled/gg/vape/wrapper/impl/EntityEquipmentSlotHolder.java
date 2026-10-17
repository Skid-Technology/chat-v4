/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEntityEquipmentSlotHolder;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.EntityEquipmentSlot;

public class EntityEquipmentSlotHolder
extends Wrapper {
    public EntityEquipmentSlot getSlot() {
        return new EntityEquipmentSlot(MEntityEquipmentSlotHolder.getSlot(EntityEquipmentSlotHolder.vapeInstance.getMappingsMapperCompat().hh, this.I));
    }

    public EntityEquipmentSlotHolder(Object wrappedObject) {
        super(wrappedObject);
    }
}

