/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MBoundTickingBlockEntity
extends Mapping {
    private static final String BLOCK_ENTITY_FIELD_NAME = "blockEntity";
    private final MappingField blockEntityField = this.J("blockEntity", true, MappedClasses.ZI);

    public MBoundTickingBlockEntity() {
        super(MappedClasses.BOUND_TICKING_BLOCK_ENTITY);
    }

    public Object getBlockEntity(Object boundTickingBlockEntity) {
        return this.blockEntityField.getObject(boundTickingBlockEntity);
    }
}

