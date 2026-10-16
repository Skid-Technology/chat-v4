/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.AbstractMapBasedMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractSetMultimap<K, V>
extends AbstractMapBasedMultimap<K, V>
implements SetMultimap<K, V> {
    private final static long serialVersionUID = 7431625294878419160L;

    protected AbstractSetMultimap(Map<K, Collection<V>> map) {
        super(map);
    }

    @Override
    abstract Set<V> createCollection();

    @Override
    Set<V> createUnmodifiableEmptyCollection() {
        return ImmutableSet.of();
    }

    @Override
    public Set<V> get(@Nullable K key) {
        return (Set)super.get(key);
    }

    @Override
    public Set<Map.Entry<K, V>> entries() {
        return (Set)super.entries();
    }

    @Override
    public Set<V> removeAll(@Nullable Object key) {
        return (Set)super.removeAll(key);
    }

    @Override
    public Set<V> replaceValues(@Nullable K key, Iterable<? extends V> values) {
        return (Set)super.replaceValues(key, values);
    }

    @Override
    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }

    @Override
    public boolean put(@Nullable K key, @Nullable V value) {
        return super.put(key, value);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return super.equals(object);
    }

    @Override
    public Collection entries() {
        return this.entries();
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

