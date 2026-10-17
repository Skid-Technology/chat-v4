/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MTrajectoriesItemBridge
extends Mapping {
    private final MappingMethod isChargedMethod = this.registerStaticMethod("isCharged", true, Boolean.TYPE, MappedClasses.VK);

    public MTrajectoriesItemBridge() {
        super(MappedClasses.YA);
    }

    public boolean isCharged(Object itemStack) {
        return this.isChargedMethod.invokeBoolean(null, itemStack);
    }
}

