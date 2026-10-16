/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MRenderItemContext;
import gg.vape.wrapper.Wrapper;

public class RenderItemContext
extends Wrapper {
    public static RenderItemContext gui() {
        return new RenderItemContext(MRenderItemContext.getGui(RenderItemContext.vapeInstance.getMappingsMapperCompat().var_gg_vape_mapping_mappings_MRenderItemContext_A));
    }

    public RenderItemContext(Object wrappedObject) {
        super(wrappedObject);
    }
}

