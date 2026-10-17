/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.FogType;

public class FogRenderer
extends Wrapper {
    public FogRenderer(Object wrappedObject) {
        super(wrappedObject);
    }

    public Object getBuffer(FogType fogType) {
        return FogRenderer.vapeInstance.getMappingsMapperCompat().fogRenderer.getBuffer(this.I, fogType.getObject());
    }
}

