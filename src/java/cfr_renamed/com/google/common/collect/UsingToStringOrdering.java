/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Ordering;
import java.io.Serializable;

@GwtCompatible(serializable=true)
final class UsingToStringOrdering
extends Ordering<Object>
implements Serializable {
    final static UsingToStringOrdering INSTANCE = new UsingToStringOrdering();
    private final static long serialVersionUID = 0L;

    @Override
    public int compare(Object left, Object right) {
        return left.toString().compareTo(right.toString());
    }

    private Object readResolve() {
        return INSTANCE;
    }

    public String toString() {
        return "Ordering.usingToString()";
    }

    private UsingToStringOrdering() {
    }
}

