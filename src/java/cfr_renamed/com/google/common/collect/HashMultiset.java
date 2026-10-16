/*
 * Decompiled with CFR 0.152.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.AbstractMapBasedMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Multisets;
import com.google.common.collect.Serialization;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

@GwtCompatible(serializable=true, emulated=true)
public final class HashMultiset<E>
extends AbstractMapBasedMultiset<E> {
    @GwtIncompatible(value="Not needed in emulated source.")
    private final static long serialVersionUID = 0L;

    public static <E> HashMultiset<E> create() {
        return new HashMultiset<E>();
    }

    public static <E> HashMultiset<E> create(int distinctElements) {
        return new HashMultiset<E>(distinctElements);
    }

    public static <E> HashMultiset<E> create(Iterable<? extends E> elements) {
        HashMultiset<E> multiset = HashMultiset.create(Multisets.inferDistinctElements(elements));
        Iterables.addAll(multiset, elements);
        return multiset;
    }

    private HashMultiset() {
        super(new HashMap());
    }

    private HashMultiset(int distinctElements) {
        super(Maps.newHashMapWithExpectedSize(distinctElements));
    }

    @GwtIncompatible(value="java.io.ObjectOutputStream")
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        Serialization.writeMultiset(this, stream);
    }

    @GwtIncompatible(value="java.io.ObjectInputStream")
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        int distinctElements = Serialization.readCount(stream);
        this.setBackingMap(Maps.newHashMapWithExpectedSize(distinctElements));
        Serialization.populateMultiset(this, stream, distinctElements);
    }

    @Override
    public int setCount(Object x0, int x1) {
        return super.setCount(x0, x1);
    }

    @Override
    public int remove(Object x0, int x1) {
        return super.remove(x0, x1);
    }

    @Override
    public int add(Object x0, int x1) {
        return super.add(x0, x1);
    }

    @Override
    public int count(Object x0) {
        return super.count(x0);
    }

    @Override
    public Iterator iterator() {
        return super.iterator();
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public Set entrySet() {
        return super.entrySet();
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
    public boolean equals(Object x0) {
        return super.equals(x0);
    }

    @Override
    public Set elementSet() {
        return super.elementSet();
    }

    @Override
    public boolean retainAll(Collection x0) {
        return super.retainAll(x0);
    }

    @Override
    public boolean removeAll(Collection x0) {
        return super.removeAll(x0);
    }

    @Override
    public boolean addAll(Collection x0) {
        return super.addAll(x0);
    }

    @Override
    public boolean setCount(Object x0, int x1, int x2) {
        return super.setCount(x0, x1, x2);
    }

    @Override
    public boolean remove(Object x0) {
        return super.remove(x0);
    }

    @Override
    public boolean add(Object x0) {
        return super.add(x0);
    }

    @Override
    public boolean contains(Object x0) {
        return super.contains(x0);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
}

