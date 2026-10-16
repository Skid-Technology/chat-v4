/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.AbstractMapBasedMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractListMultimap<K, V>
extends AbstractMapBasedMultimap<K, V>
implements ListMultimap<K, V> {
    private final static long serialVersionUID = 6588350623831699109L;

    protected AbstractListMultimap(Map<K, Collection<V>> map) {
        super(map);
    }

    @Override
    abstract List<V> createCollection();

    @Override
    List<V> createUnmodifiableEmptyCollection() {
        return ImmutableList.of();
    }

    @Override
    public List<V> get(@Nullable K key) {
        return (List)super.get(key);
    }

    @Override
    public List<V> removeAll(@Nullable Object key) {
        return (List)super.removeAll(key);
    }

    @Override
    public List<V> replaceValues(@Nullable K key, Iterable<? extends V> values) {
        return (List)super.replaceValues(key, values);
    }

    @Override
    public boolean put(@Nullable K key, @Nullable V value) {
        return super.put(key, value);
    }

    @Override
    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return super.equals(object);
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

