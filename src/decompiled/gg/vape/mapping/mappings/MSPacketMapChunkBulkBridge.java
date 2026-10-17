/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MSPacketMapChunkBulkBridge
extends Mapping {
    private final MappingField xPositionsField = this.J("xPositions", true, int[].class);
    private final MappingField chunksDataField;
    private final MappingField zPositionsField = this.J("zPositions", true, int[].class);

    public int[] getXPositions(Object packet) {
        return this.xPositionsField.getIntArray(packet);
    }

    public int[] getZPositions(Object packet) {
        return this.zPositionsField.getIntArray(packet);
    }

    public Object[] getChunksData(Object packet) {
        return this.chunksDataField.getObjectArray(packet);
    }

    public MSPacketMapChunkBulkBridge() {
        super(MappedClasses.zB);
        this.chunksDataField = this.J("chunksData", true, MappedClasses.lW);
    }
}

