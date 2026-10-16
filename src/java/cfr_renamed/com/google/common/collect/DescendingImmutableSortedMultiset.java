/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.google.common.collect;

import com.google.common.collect.BoundType;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSortedMultiset;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Multiset;
import com.google.common.collect.SortedMultiset;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import javax.annotation.Nullable;

final class DescendingImmutableSortedMultiset<E>
extends ImmutableSortedMultiset<E> {
    private final transient ImmutableSortedMultiset<E> forward;

    DescendingImmutableSortedMultiset(ImmutableSortedMultiset<E> forward) {
        this.forward = forward;
    }

    @Override
    public int count(@Nullable Object element) {
        return this.forward.count(element);
    }

    @Override
    public Multiset.Entry<E> firstEntry() {
        return this.forward.lastEntry();
    }

    @Override
    public Multiset.Entry<E> lastEntry() {
        return this.forward.firstEntry();
    }

    @Override
    public int size() {
        return this.forward.size();
    }

    @Override
    public ImmutableSortedSet<E> elementSet() {
        return ((ImmutableSortedSet)this.forward.elementSet()).descendingSet();
    }

    @Override
    Multiset.Entry<E> getEntry(int index) {
        return (Multiset.Entry)((ImmutableCollection)((Object)this.forward.entrySet())).asList().reverse().get(index);
    }

    @Override
    public ImmutableSortedMultiset<E> descendingMultiset() {
        return this.forward;
    }

    @Override
    public ImmutableSortedMultiset<E> headMultiset(E upperBound, BoundType boundType) {
        return ((ImmutableSortedMultiset)this.forward.tailMultiset((Object)upperBound, boundType)).descendingMultiset();
    }

    @Override
    public ImmutableSortedMultiset<E> tailMultiset(E lowerBound, BoundType boundType) {
        return ((ImmutableSortedMultiset)this.forward.headMultiset((Object)lowerBound, boundType)).descendingMultiset();
    }

    @Override
    boolean isPartialView() {
        return this.forward.isPartialView();
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
    public SortedMultiset descendingMultiset() {
        return this.descendingMultiset();
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

