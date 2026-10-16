/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MAttributeModifier;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ResourceLocation;
import java.util.UUID;

public class AttributeModifier
extends Wrapper {
    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(MAttributeModifier.getResourceLocation(AttributeModifier.vapeInstance.getMappings().hv, this.I));
    }

    public AttributeModifier(Object wrappedObject) {
        super(wrappedObject);
    }

    public double getAmount() {
        return AttributeModifier.vapeInstance.getMappings().hv.getAmount(this.I);
    }

    public UUID getId() {
        return MAttributeModifier.getUuid(AttributeModifier.vapeInstance.getMappings().hv, this.I);
    }
}

