/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.AbstractSetMultimap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.SortedSetMultimap;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractSortedSetMultimap<K, V>
extends AbstractSetMultimap<K, V>
implements SortedSetMultimap<K, V> {
    private final static long serialVersionUID = 430848587173315748L;

    protected AbstractSortedSetMultimap(Map<K, Collection<V>> map) {
        super(map);
    }

    @Override
    abstract SortedSet<V> createCollection();

    @Override
    SortedSet<V> createUnmodifiableEmptyCollection() {
        Comparator comparator = this.valueComparator();
        if (comparator == null) {
            return Collections.unmodifiableSortedSet(this.createCollection());
        }
        return ImmutableSortedSet.emptySet(this.valueComparator());
    }

    @Override
    public SortedSet<V> get(@Nullable K key) {
        return (SortedSet)super.get((Object)key);
    }

    @Override
    public SortedSet<V> removeAll(@Nullable Object key) {
        return (SortedSet)super.removeAll(key);
    }

    @Override
    public SortedSet<V> replaceValues(@Nullable K key, Iterable<? extends V> values) {
        return (SortedSet)super.replaceValues((Object)key, (Iterable)values);
    }

    @Override
    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }

    @Override
    public Collection<V> values() {
        return super.values();
    }

    @Override
    public Set replaceValues(Object x0, Iterable x1) {
        return this.replaceValues(x0, x1);
    }

    @Override
    public Set removeAll(Object x0) {
        return this.removeAll(x0);
    }

    @Override
    public Set get(Object x0) {
        return this.get(x0);
    }

    @Override
    Set createUnmodifiableEmptyCollection() {
        return this.createUnmodifiableEmptyCollection();
    }

    @Override
    Set createCollection() {
        return this.createCollection();
    }

    @Override
    public Collection get(Object x0) {
        return this.get(x0);
    }

    @Override
    public Collection removeAll(Object x0) {
        return this.removeAll(x0);
    }

    @Override
    public Collection replaceValues(Object x0, Iterable x1) {
        return this.replaceValues(x0, x1);
    }

    @Override
    Collection createCollection() {
        return this.createCollection();
    }

    @Override
    Collection createUnmodifiableEmptyCollection() {
        return this.createUnmodifiableEmptyCollection();
    }
}

