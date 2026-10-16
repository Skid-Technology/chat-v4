/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MBlockLayerOverrideFallbackHook
extends Mapping {
    private static int controlFlowState;
    private final static String GC_METHOD_NAME;
    final public MappingMethod gcMethod = this.registerStaticMethod(GC_METHOD_NAME, false, Void.TYPE, new Class[0]);

    public static void setBlockLayerFallbackControlFlowState(int state) {
        controlFlowState = state;
    }

    public static int getControlFlowConstant() {
        MBlockLayerOverrideFallbackHook.getBlockLayerFallbackControlFlowState();
        return 41;
    }

    public MBlockLayerOverrideFallbackHook() {
        this(MBlockLayerOverrideFallbackHook.getBlockLayerFallbackControlFlowState());
    }

    private MBlockLayerOverrideFallbackHook(int controlFlowState) {
        super(MappedClasses.lA);
    }

    public static int getBlockLayerFallbackControlFlowState() {
        return controlFlowState;
    }

    static {
        MBlockLayerOverrideFallbackHook.setBlockLayerFallbackControlFlowState(0);
        GC_METHOD_NAME = "gc";
    }
}

