/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.AbstractListMultimap;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Serialization;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@GwtCompatible(serializable=true, emulated=true)
public final class ArrayListMultimap<K, V>
extends AbstractListMultimap<K, V> {
    private final static int DEFAULT_VALUES_PER_KEY = 3;
    @VisibleForTesting
    transient int expectedValuesPerKey;
    @GwtIncompatible(value="Not needed in emulated source.")
    private final static long serialVersionUID = 0L;

    public static <K, V> ArrayListMultimap<K, V> create() {
        return new ArrayListMultimap<K, V>();
    }

    public static <K, V> ArrayListMultimap<K, V> create(int expectedKeys, int expectedValuesPerKey) {
        return new ArrayListMultimap<K, V>(expectedKeys, expectedValuesPerKey);
    }

    public static <K, V> ArrayListMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
        return new ArrayListMultimap<K, V>(multimap);
    }

    private ArrayListMultimap() {
        super(new HashMap());
        this.expectedValuesPerKey = 3;
    }

    private ArrayListMultimap(int expectedKeys, int expectedValuesPerKey) {
        super(Maps.newHashMapWithExpectedSize(expectedKeys));
        CollectPreconditions.checkNonnegative(expectedValuesPerKey, "expectedValuesPerKey");
        this.expectedValuesPerKey = expectedValuesPerKey;
    }

    private ArrayListMultimap(Multimap<? extends K, ? extends V> multimap) {
        this(multimap.keySet().size(), multimap instanceof ArrayListMultimap ? ((ArrayListMultimap)multimap).expectedValuesPerKey : 3);
        this.putAll((Multimap)multimap);
    }

    @Override
    List<V> createCollection() {
        return new ArrayList(this.expectedValuesPerKey);
    }

    public void trimToSize() {
        for (Collection collection : this.backingMap().values()) {
            ArrayList arrayList = (ArrayList)collection;
            arrayList.trimToSize();
        }
    }

    @GwtIncompatible(value="java.io.ObjectOutputStream")
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeInt(this.expectedValuesPerKey);
        Serialization.writeMultimap(this, stream);
    }

    @GwtIncompatible(value="java.io.ObjectOutputStream")
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.expectedValuesPerKey = stream.readInt();
        int distinctKeys = Serialization.readCount(stream);
        HashMap map = Maps.newHashMapWithExpectedSize(distinctKeys);
        this.setMap(map);
        Serialization.populateMultimap(this, stream, distinctKeys);
    }

    @Override
    public boolean equals(Object x0) {
        return super.equals(x0);
    }

    @Override
    public Map asMap() {
        return super.asMap();
    }

    @Override
    public boolean put(Object x0, Object x1) {
        return super.put(x0, x1);
    }

    @Override
    public List replaceValues(Object x0, Iterable x1) {
        return super.replaceValues(x0, x1);
    }

    @Override
    public List removeAll(Object x0) {
        return super.removeAll(x0);
    }

    @Override
    public List get(Object x0) {
        return super.get(x0);
    }

    @Override
    public Collection entries() {
        return super.entries();
    }

    @Override
    public Collection values() {
        return super.values();
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public boolean containsKey(Object x0) {
        return super.containsKey(x0);
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    Collection createCollection() {
        return this.createCollection();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public Multiset keys() {
        return super.keys();
    }

    @Override
    public Set keySet() {
        return super.keySet();
    }

    @Override
    public boolean putAll(Multimap x0) {
        return super.putAll(x0);
    }

    @Override
    public boolean putAll(Object x0, Iterable x1) {
        return super.putAll(x0, x1);
    }

    @Override
    public boolean remove(Object x0, Object x1) {
        return super.remove(x0, x1);
    }

    @Override
    public boolean containsEntry(Object x0, Object x1) {
        return super.containsEntry(x0, x1);
    }

    @Override
    public boolean containsValue(Object x0) {
        return super.containsValue(x0);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
}

