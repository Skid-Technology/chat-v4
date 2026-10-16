package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractTable<R, C, V> implements Table<R, C, V> {
   private transient Set<Table.Cell<R, C, V>> cellSet;
   private transient Collection<V> values;

   @Override
   public boolean containsRow(@Nullable Object rowKey) {
      return Maps.safeContainsKey(this.rowMap(), rowKey);
   }

   @Override
   public boolean containsColumn(@Nullable Object columnKey) {
      return Maps.safeContainsKey(this.columnMap(), columnKey);
   }

   @Override
   public Set<R> rowKeySet() {
      return this.rowMap().keySet();
   }

   @Override
   public Set<C> columnKeySet() {
      return this.columnMap().keySet();
   }

   @Override
   public boolean containsValue(@Nullable Object value) {
      for (Map<C, V> row : this.rowMap().values()) {
         if (row.containsValue(value)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean contains(@Nullable Object rowKey, @Nullable Object columnKey) {
      Map<C, V> row = Maps.safeGet(this.rowMap(), rowKey);
      return row != null && Maps.safeContainsKey(row, columnKey);
   }

   @Override
   public V get(@Nullable Object rowKey, @Nullable Object columnKey) {
      Map<C, V> row = Maps.safeGet(this.rowMap(), rowKey);
      return row == null ? null : Maps.safeGet(row, columnKey);
   }

   @Override
   public boolean isEmpty() {
      return this.size() == 0;
   }

   @Override
   public void clear() {
      Iterators.clear(this.cellSet().iterator());
   }

   @Override
   public V remove(@Nullable Object rowKey, @Nullable Object columnKey) {
      Map<C, V> row = Maps.safeGet(this.rowMap(), rowKey);
      return row == null ? null : Maps.safeRemove(row, columnKey);
   }

   @Override
   public V put(R rowKey, C columnKey, V value) {
      return this.row(rowKey).put(columnKey, value);
   }

   @Override
   public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
      for (Table.Cell<? extends R, ? extends C, ? extends V> cell : table.cellSet()) {
         this.put((R)cell.getRowKey(), (C)cell.getColumnKey(), (V)cell.getValue());
      }
   }

   @Override
   public Set<Table.Cell<R, C, V>> cellSet() {
      Set<Table.Cell<R, C, V>> result = this.cellSet;
      return result == null ? (this.cellSet = this.createCellSet()) : result;
   }

   Set<Table.Cell<R, C, V>> createCellSet() {
      return new AbstractTable.CellSet();
   }

   abstract Iterator<Table.Cell<R, C, V>> cellIterator();

   @Override
   public Collection<V> values() {
      Collection<V> result = this.values;
      return result == null ? (this.values = this.createValues()) : result;
   }

   Collection<V> createValues() {
      return new AbstractTable.Values();
   }

   Iterator<V> valuesIterator() {
      return new TransformedIterator<Table.Cell<R, C, V>, V>(this.cellSet().iterator()) {
         V transform(Table.Cell<R, C, V> cell) {
            return cell.getValue();
         }
      };
   }

   @Override
   public boolean equals(@Nullable Object obj) {
      return Tables.equalsImpl(this, obj);
   }

   @Override
   public int hashCode() {
      return this.cellSet().hashCode();
   }

   @Override
   public String toString() {
      return this.rowMap().toString();
   }

   class CellSet extends AbstractSet<Table.Cell<R, C, V>> {
      @Override
      public boolean contains(Object o) {
         if (!(o instanceof Table.Cell)) {
            return false;
         } else {
            Table.Cell<?, ?, ?> cell = (Table.Cell<?, ?, ?>)o;
            Map<C, V> row = Maps.safeGet(AbstractTable.this.rowMap(), cell.getRowKey());
            return row != null && Collections2.safeContains(row.entrySet(), Maps.immutableEntry(cell.getColumnKey(), cell.getValue()));
         }
      }

      @Override
      public boolean remove(@Nullable Object o) {
         if (!(o instanceof Table.Cell)) {
            return false;
         } else {
            Table.Cell<?, ?, ?> cell = (Table.Cell<?, ?, ?>)o;
            Map<C, V> row = Maps.safeGet(AbstractTable.this.rowMap(), cell.getRowKey());
            return row != null && Collections2.safeRemove(row.entrySet(), Maps.immutableEntry(cell.getColumnKey(), cell.getValue()));
         }
      }

      @Override
      public void clear() {
         AbstractTable.this.clear();
      }

      @Override
      public Iterator<Table.Cell<R, C, V>> iterator() {
         return AbstractTable.this.cellIterator();
      }

      @Override
      public int size() {
         return AbstractTable.this.size();
      }
   }

   class Values extends AbstractCollection<V> {
      @Override
      public Iterator<V> iterator() {
         return AbstractTable.this.valuesIterator();
      }

      @Override
      public boolean contains(Object o) {
         return AbstractTable.this.containsValue(o);
      }

      @Override
      public void clear() {
         AbstractTable.this.clear();
      }

      @Override
      public int size() {
         return AbstractTable.this.size();
      }
   }
}
