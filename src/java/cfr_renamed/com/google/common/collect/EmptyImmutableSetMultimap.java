/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;

@GwtCompatible(serializable=true)
class EmptyImmutableSetMultimap
extends ImmutableSetMultimap<Object, Object> {
    final static EmptyImmutableSetMultimap INSTANCE = new EmptyImmutableSetMultimap();
    private final static long serialVersionUID = 0L;

    private EmptyImmutableSetMultimap() {
        super(ImmutableMap.of(), 0, null);
    }

    private Object readResolve() {
        return INSTANCE;
    }
}

