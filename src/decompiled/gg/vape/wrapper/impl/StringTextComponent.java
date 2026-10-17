/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.TextComponentTranslation;

public class StringTextComponent
extends Wrapper {
    public StringTextComponent(Object componentHandle) {
        super(componentHandle);
    }

    public TextComponentTranslation getItemStackRenderState() {
        Object renderStateHandle = StringTextComponent.vapeInstance.getMappingsMapperCompat().stringTextComponentBridge.getItemStackRenderState(this.I);
        return new TextComponentTranslation(renderStateHandle);
    }

    public String getName() {
        if (MappedClasses.DE != null && ForgeVersion.MC_26_1.d()) {
            return "";
        }
        return StringTextComponent.vapeInstance.getMappingsMapperCompat().stringTextComponentBridge.getName(this.I);
    }
}

