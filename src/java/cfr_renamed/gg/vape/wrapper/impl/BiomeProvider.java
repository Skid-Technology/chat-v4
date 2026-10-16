/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MBiomeProviderBridge;
import gg.vape.wrapper.Wrapper;

public class BiomeProvider
extends Wrapper {
    public Iterable getAllEffects() {
        return MBiomeProviderBridge.getAllEffects(BiomeProvider.vapeInstance.getMappingsMapperCompat().var_gg_vape_mapping_mappings_MBiomeProviderBridge_K, this.I);
    }

    public BiomeProvider(Object wrappedObject) {
        super(wrappedObject);
    }
}

