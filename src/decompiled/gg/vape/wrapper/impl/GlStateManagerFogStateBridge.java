/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import java.util.List;

public class GlStateManagerFogStateBridge
extends Wrapper {
    public List<String> getItemStates() {
        return GlStateManagerFogStateBridge.vapeInstance.getMappingsMapperCompat().q2.getItemStates(this.I);
    }

    public GlStateManagerFogStateBridge(Object wrappedObject) {
        super(wrappedObject);
    }
}

