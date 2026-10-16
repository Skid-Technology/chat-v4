/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class SPacketChunkDataExtracted
extends Wrapper {
    public SPacketChunkDataExtracted(Object handle) {
        super(handle);
    }

    public int getDataSize() {
        return SPacketChunkDataExtracted.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MSPacketChunkDataExtracted_b.getDataSize(this.I);
    }

    public byte[] getData() {
        return SPacketChunkDataExtracted.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MSPacketChunkDataExtracted_b.getData(this.I);
    }
}

