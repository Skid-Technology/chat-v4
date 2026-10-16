/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.DataComponentMap;

public class EmptyDataComponentMap
extends DataComponentMap {
    public EmptyDataComponentMap(Object componentMapHandle) {
        super(componentMapHandle);
    }

    public static EmptyDataComponentMap create() {
        Object componentMapHandle = EmptyDataComponentMap.vapeInstance.getMappingsMapperCompat().emptyDataComponentMap.create(DataComponentMap.u().getObject());
        return new EmptyDataComponentMap(componentMapHandle);
    }
}

