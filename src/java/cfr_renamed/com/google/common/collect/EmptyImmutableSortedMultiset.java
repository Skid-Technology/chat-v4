/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.BoundType;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedMultiset;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multiset;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import javax.annotation.Nullable;

final class EmptyImmutableSortedMultiset<E>
extends ImmutableSortedMultiset<E> {
    private final ImmutableSortedSet<E> elementSet;

    EmptyImmutableSortedMultiset(Comparator<? super E> comparator) {
        this.elementSet = ImmutableSortedSet.emptySet(comparator);
    }

    @Override
    public Multiset.Entry<E> firstEntry() {
        return null;
    }

    @Override
    public Multiset.Entry<E> lastEntry() {
        return null;
    }

    @Override
    public int count(@Nullable Object element) {
        return 0;
    }

    @Override
    public boolean containsAll(Collection<?> targets) {
        return targets.isEmpty();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public ImmutableSortedSet<E> elementSet() {
        return this.elementSet;
    }

    @Override
    Multiset.Entry<E> getEntry(int index) {
        throw new AssertionError((Object)"should never be called");
    }

    @Override
    public ImmutableSortedMultiset<E> headMultiset(E upperBound, BoundType boundType) {
        Preconditions.checkNotNull(upperBound);
        Preconditions.checkNotNull(boundType);
        return this;
    }

    @Override
    public ImmutableSortedMultiset<E> tailMultiset(E lowerBound, BoundType boundType) {
        Preconditions.checkNotNull(lowerBound);
        Preconditions.checkNotNull(boundType);
        return this;
    }

    @Override
    public UnmodifiableIterator<E> iterator() {
        return Iterators.emptyIterator();
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object instanceof Multiset) {
            Multiset other = (Multiset)object;
            return other.isEmpty();
        }
        return false;
    }

    @Override
    boolean isPartialView() {
        return false;
    }

    @Override
    int copyIntoArray(Object[] dst, int offset) {
        return offset;
    }

    @Override
    public ImmutableList<E> asList() {
        return ImmutableList.of();
    }

    @Override
    public SortedMultiset tailMultiset(Object x0, BoundType x1) {
        return this.tailMultiset(x0, x1);
    }

    @Override
    public SortedMultiset headMultiset(Object x0, BoundType x1) {
        return this.headMultiset(x0, x1);
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    @Override
    public NavigableSet elementSet() {
        return this.elementSet();
    }

    @Override
    public SortedSet elementSet() {
        return this.elementSet();
    }

    @Override
    public Set elementSet() {
        return this.elementSet();
    }
}

