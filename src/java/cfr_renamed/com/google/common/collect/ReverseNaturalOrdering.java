/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.NaturalOrdering;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import java.util.Iterator;

@GwtCompatible(serializable=true)
final class ReverseNaturalOrdering
extends Ordering<Comparable>
implements Serializable {
    final static ReverseNaturalOrdering INSTANCE = new ReverseNaturalOrdering();
    private final static long serialVersionUID = 0L;

    @Override
    public int compare(Comparable left, Comparable right) {
        Preconditions.checkNotNull(left);
        if (left == right) {
            return 0;
        }
        return right.compareTo(left);
    }

    @Override
    public <S extends Comparable> Ordering<S> reverse() {
        return Ordering.natural();
    }

    @Override
    public <E extends Comparable> E min(E a, E b) {
        return NaturalOrdering.INSTANCE.max(a, b);
    }

    @Override
    public <E extends Comparable> E min(E a, E b, E c, E ... rest) {
        return NaturalOrdering.INSTANCE.max(a, b, c, rest);
    }

    @Override
    public <E extends Comparable> E min(Iterator<E> iterator) {
        return (E)((Comparable)NaturalOrdering.INSTANCE.max(iterator));
    }

    @Override
    public <E extends Comparable> E min(Iterable<E> iterable) {
        return (E)((Comparable)NaturalOrdering.INSTANCE.max(iterable));
    }

    @Override
    public <E extends Comparable> E max(E a, E b) {
        return NaturalOrdering.INSTANCE.min(a, b);
    }

    @Override
    public <E extends Comparable> E max(E a, E b, E c, E ... rest) {
        return NaturalOrdering.INSTANCE.min(a, b, c, rest);
    }

    @Override
    public <E extends Comparable> E max(Iterator<E> iterator) {
        return (E)((Comparable)NaturalOrdering.INSTANCE.min(iterator));
    }

    @Override
    public <E extends Comparable> E max(Iterable<E> iterable) {
        return (E)((Comparable)NaturalOrdering.INSTANCE.min(iterable));
    }

    private Object readResolve() {
        return INSTANCE;
    }

    public String toString() {
        return "Ordering.natural().reverse()";
    }

    private ReverseNaturalOrdering() {
    }

    @Override
    public Object max(Object x0, Object x1, Object x2, Object[] x3) {
        return this.max((Comparable)x0, (Comparable)x1, (Comparable)x2, (Comparable[])x3);
    }

    @Override
    public Object max(Object x0, Object x1) {
        return this.max((Comparable)x0, (Comparable)x1);
    }

    @Override
    public Object max(Iterable x0) {
        return this.max(x0);
    }

    @Override
    public Object max(Iterator x0) {
        return this.max(x0);
    }

    @Override
    public Object min(Object x0, Object x1, Object x2, Object[] x3) {
        return this.min((Comparable)x0, (Comparable)x1, (Comparable)x2, (Comparable[])x3);
    }

    @Override
    public Object min(Object x0, Object x1) {
        return this.min((Comparable)x0, (Comparable)x1);
    }

    @Override
    public Object min(Iterable x0) {
        return this.min(x0);
    }

    @Override
    public Object min(Iterator x0) {
        return this.min(x0);
    }

    @Override
    public int compare(Object x0, Object x1) {
        return this.compare((Comparable)x0, (Comparable)x1);
    }
}

