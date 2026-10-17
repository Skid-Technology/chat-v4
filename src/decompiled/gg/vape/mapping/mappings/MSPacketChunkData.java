/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MSPacketChunkData
extends Mapping {
    private final MappingField chunkZField;
    private final MappingField extractedDataField = this.J("extractedData", true, MappedClasses.uU);
    private final MappingField chunkXField = this.J("chunkX", true, Integer.TYPE);

    public int getChunkX(Object packet) {
        return this.chunkXField.getInt(packet);
    }

    public int getChunkZ(Object packet) {
        return this.chunkZField.getInt(packet);
    }

    public Object getExtractedData(Object packet) {
        return this.extractedDataField.getObject(packet);
    }

    public MSPacketChunkData() {
        super(MappedClasses.ZJ);
        this.chunkZField = this.J("chunkZ", true, Integer.TYPE);
    }
}

