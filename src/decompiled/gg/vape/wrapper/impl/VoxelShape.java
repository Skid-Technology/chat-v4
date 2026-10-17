/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.RenderItemFontBridge;

public class VoxelShape
extends Wrapper {
    public VoxelShape(Object handle) {
        super(handle);
    }

    public RenderItemFontBridge getBufferSource() {
        return new RenderItemFontBridge(VoxelShape.vapeInstance.getMappingsMapperCompat().Cl.getBufferSource(this.I));
    }
}

