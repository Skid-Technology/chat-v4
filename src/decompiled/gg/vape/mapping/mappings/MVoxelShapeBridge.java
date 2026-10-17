/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MVoxelShapeBridge
extends Mapping {
    private MappingMethod bufferSourceMethod;

    public MVoxelShapeBridge() {
        super(MappedClasses.ZL);
        if (ForgeVersion.MC_26_2.v()) {
            this.bufferSourceMethod = this.Y("bufferSource", true, MappedClasses.lp, new Class[0]);
        }
    }

    public Object getBufferSource(Object renderer) {
        if (this.bufferSourceMethod == null) {
            return null;
        }
        return this.bufferSourceMethod.invokeObject(renderer, new Object[0]);
    }
}

