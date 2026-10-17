/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class MAtomicReferenceArrayBridge
extends Mapping {
    private static final String CHUNKS_FIELD_NAME = "chunks";
    private final MappingField chunksField = this.J("chunks", true, AtomicReferenceArray.class);

    public MAtomicReferenceArrayBridge() {
        super(MappedClasses.zd);
    }

    public Object getChunks(Object storage) {
        return this.chunksField.getObject(storage);
    }
}

