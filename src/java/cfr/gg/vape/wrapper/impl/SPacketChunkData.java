/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.Packet;
import gg.vape.wrapper.impl.SPacketChunkDataExtracted;

public class SPacketChunkData
extends Packet {
    public SPacketChunkData(Object handle) {
        super(handle);
    }

    public int getChunkZ() {
        return SPacketChunkData.vapeInstance.getMappingsMapperCompat().h8.getChunkZ(this.I);
    }

    public SPacketChunkDataExtracted getExtractedData() {
        return new SPacketChunkDataExtracted(SPacketChunkData.vapeInstance.getMappingsMapperCompat().h8.getExtractedData(this.I));
    }

    public int getChunkX() {
        return SPacketChunkData.vapeInstance.getMappingsMapperCompat().h8.getChunkX(this.I);
    }
}

