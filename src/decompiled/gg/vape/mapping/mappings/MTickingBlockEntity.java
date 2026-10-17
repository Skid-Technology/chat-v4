/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MTickingBlockEntity
extends Mapping {
    private static int[] controlFlowState;
    private final MappingMethod getPosMethod = this.Y(GET_POS_METHOD_NAME, true, MappedClasses.lf, new Class[0]);
    private static final String GET_POS_METHOD_NAME;

    public MTickingBlockEntity() {
        super(MappedClasses.TICKING_BLOCK_ENTITY);
    }

    private Object invokeGetPos(Object tickingBlockEntity) {
        return this.getPosMethod.invokeObject(tickingBlockEntity, new Object[0]);
    }

    public static Object getPos(MTickingBlockEntity mappings, Object tickingBlockEntity) {
        return mappings.invokeGetPos(tickingBlockEntity);
    }

    public static int[] getTickingBlockEntityControlFlowState() {
        return controlFlowState;
    }

    public static void setTickingBlockEntityControlFlowState(int[] state) {
        controlFlowState = state;
    }

    static {
        MTickingBlockEntity.setTickingBlockEntityControlFlowState(new int[5]);
        GET_POS_METHOD_NAME = "getPos";
    }
}

