/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.mappings.MMappedClassSlotNRegistration;

public class MSPacketDestroyEntities
extends Mapping {
    private final MappingField entityIdsField = this.J("a", false, int[].class);

    public int[] getEntityIds(Object packet) {
        return this.entityIdsField.getIntArray(packet);
    }

    public MSPacketDestroyEntities() {
        this(MMappedClassSlotNRegistration.r());
    }

    private MSPacketDestroyEntities(int controlFlowState) {
        super(MappedClasses.qc);
    }
}

