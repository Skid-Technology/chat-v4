/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Supplier;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.StandardTable;
import com.google.common.collect.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(serializable=true)
public class HashBasedTable<R, C, V>
extends StandardTable<R, C, V> {
    private final static long serialVersionUID = 0L;

    public static <R, C, V> HashBasedTable<R, C, V> create() {
        return new HashBasedTable(new HashMap(), new Factory(0));
    }

    public static <R, C, V> HashBasedTable<R, C, V> create(int expectedRows, int expectedCellsPerRow) {
        CollectPreconditions.checkNonnegative(expectedCellsPerRow, "expectedCellsPerRow");
        HashMap backingMap = Maps.newHashMapWithExpectedSize(expectedRows);
        return new HashBasedTable(backingMap, new Factory(expectedCellsPerRow));
    }

    public static <R, C, V> HashBasedTable<R, C, V> create(Table<? extends R, ? extends C, ? extends V> table) {
        HashBasedTable<R, C, V> result = HashBasedTable.create();
        result.putAll((Table)table);
        return result;
    }

    HashBasedTable(Map<R, Map<C, V>> backingMap, Factory<C, V> factory) {
        super(backingMap, factory);
    }

    @Override
    public boolean contains(@Nullable Object rowKey, @Nullable Object columnKey) {
        return super.contains(rowKey, columnKey);
    }

    @Override
    public boolean containsColumn(@Nullable Object columnKey) {
        return super.containsColumn(columnKey);
    }

    @Override
    public boolean containsRow(@Nullable Object rowKey) {
        return super.containsRow(rowKey);
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        return super.containsValue(value);
    }

    @Override
    public V get(@Nullable Object rowKey, @Nullable Object columnKey) {
        return super.get(rowKey, columnKey);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    @Override
    public V remove(@Nullable Object rowKey, @Nullable Object columnKey) {
        return super.remove(rowKey, columnKey);
    }

    @Override
    public Map columnMap() {
        return super.columnMap();
    }

    @Override
    public Map rowMap() {
        return super.rowMap();
    }

    @Override
    public Collection values() {
        return super.values();
    }

    @Override
    public Set columnKeySet() {
        return super.columnKeySet();
    }

    @Override
    public Set rowKeySet() {
        return super.rowKeySet();
    }

    @Override
    public Map column(Object x0) {
        return super.column(x0);
    }

    @Override
    public Map row(Object x0) {
        return super.row(x0);
    }

    @Override
    public Set cellSet() {
        return super.cellSet();
    }

    @Override
    public Object put(Object x0, Object x1, Object x2) {
        return super.put(x0, x1, x2);
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
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
    public void putAll(Table x0) {
        super.putAll(x0);
    }

    private static class Factory<C, V>
    implements Supplier<Map<C, V>>,
    Serializable {
        final int expectedSize;
        private final static long serialVersionUID = 0L;

        Factory(int expectedSize) {
            this.expectedSize = expectedSize;
        }

        @Override
        public Map<C, V> get() {
            return Maps.newHashMapWithExpectedSize(this.expectedSize);
        }

        @Override
        public Object get() {
            return this.get();
        }
    }
}

