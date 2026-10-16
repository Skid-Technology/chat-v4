/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ForwardingSetMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.SortedSetMultimap;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ForwardingSortedSetMultimap<K, V>
extends ForwardingSetMultimap<K, V>
implements SortedSetMultimap<K, V> {
    protected ForwardingSortedSetMultimap() {
    }

    @Override
    protected abstract SortedSetMultimap<K, V> delegate();

    @Override
    public SortedSet<V> get(@Nullable K key) {
        return this.delegate().get(key);
    }

    @Override
    public SortedSet<V> removeAll(@Nullable Object key) {
        return this.delegate().removeAll(key);
    }

    @Override
    public SortedSet<V> replaceValues(K key, Iterable<? extends V> values) {
        return this.delegate().replaceValues(key, values);
    }

    @Override
    public Comparator<? super V> valueComparator() {
        return this.delegate().valueComparator();
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
    protected SetMultimap delegate() {
        return this.delegate();
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
    protected Multimap delegate() {
        return this.delegate();
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }
}

