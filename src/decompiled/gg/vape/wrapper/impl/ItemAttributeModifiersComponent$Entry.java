/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.AttributeModifier;
import gg.vape.wrapper.impl.EquipmentSlotGroup;
import gg.vape.wrapper.impl.Holder;

public class ItemAttributeModifiersComponent$Entry
extends Wrapper {
    public EquipmentSlotGroup getEquipmentSlotGroup() {
        Holder attributeHolder = new Holder(ItemAttributeModifiersComponent$Entry.vapeInstance.getMappingsMapperCompat().DN.getAttribute(this.I));
        return new EquipmentSlotGroup(attributeHolder.N());
    }

    public AttributeModifier getModifier() {
        return new AttributeModifier(ItemAttributeModifiersComponent$Entry.vapeInstance.getMappingsMapperCompat().DN.getModifier(this.I));
    }

    public ItemAttributeModifiersComponent$Entry(Object wrappedObject) {
        super(wrappedObject);
    }
}

