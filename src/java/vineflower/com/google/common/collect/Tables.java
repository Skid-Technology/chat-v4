package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible
public final class Tables {
   private static final Function<? extends Map<?, ?>, ? extends Map<?, ?>> UNMODIFIABLE_WRAPPER = new Function<Map<Object, Object>, Map<Object, Object>>() {
      public Map<Object, Object> apply(Map<Object, Object> input) {
         return Collections.unmodifiableMap(input);
      }
   };

   private Tables() {
   }

   public static <R, C, V> Table.Cell<R, C, V> immutableCell(@Nullable R rowKey, @Nullable C columnKey, @Nullable V value) {
      return new Tables.ImmutableCell<>(rowKey, columnKey, value);
   }

   public static <R, C, V> Table<C, R, V> transpose(Table<R, C, V> table) {
      return (Table<C, R, V>)(table instanceof Tables.TransposeTable ? ((Tables.TransposeTable)table).original : new Tables.TransposeTable<>(table));
   }

   @Beta
   public static <R, C, V> Table<R, C, V> newCustomTable(Map<R, Map<C, V>> backingMap, Supplier<? extends Map<C, V>> factory) {
      Preconditions.checkArgument(backingMap.isEmpty());
      Preconditions.checkNotNull(factory);
      return new StandardTable<>(backingMap, factory);
   }

   @Beta
   public static <R, C, V1, V2> Table<R, C, V2> transformValues(Table<R, C, V1> fromTable, Function<? super V1, V2> function) {
      return new Tables.TransformedTable<>(fromTable, function);
   }

   public static <R, C, V> Table<R, C, V> unmodifiableTable(Table<? extends R, ? extends C, ? extends V> table) {
      return new Tables.UnmodifiableTable<>(table);
   }

   @Beta
   public static <R, C, V> RowSortedTable<R, C, V> unmodifiableRowSortedTable(RowSortedTable<R, ? extends C, ? extends V> table) {
      return new Tables.UnmodifiableRowSortedMap<>(table);
   }

   private static <K, V> Function<Map<K, V>, Map<K, V>> unmodifiableWrapper() {
      return (Function<Map<K, V>, Map<K, V>>)UNMODIFIABLE_WRAPPER;
   }

   static boolean equalsImpl(Table<?, ?, ?> table, @Nullable Object obj) {
      if (obj == table) {
         return true;
      } else if (obj instanceof Table) {
         Table<?, ?, ?> that = (Table<?, ?, ?>)obj;
         return table.cellSet().equals(that.cellSet());
      } else {
         return false;
      }
   }

   abstract static class AbstractCell<R, C, V> implements Table.Cell<R, C, V> {
      @Override
      public boolean equals(Object obj) {
         if (obj == this) {
            return true;
         } else if (!(obj instanceof Table.Cell)) {
            return false;
         } else {
            Table.Cell<?, ?, ?> other = (Table.Cell<?, ?, ?>)obj;
            return Objects.equal(this.getRowKey(), other.getRowKey())
               && Objects.equal(this.getColumnKey(), other.getColumnKey())
               && Objects.equal(this.getValue(), other.getValue());
         }
      }

      @Override
      public int hashCode() {
         return Objects.hashCode(this.getRowKey(), this.getColumnKey(), this.getValue());
      }

      @Override
      public String toString() {
         return "(" + this.getRowKey() + "," + this.getColumnKey() + ")=" + this.getValue();
      }
   }

   static final class ImmutableCell<R, C, V> extends Tables.AbstractCell<R, C, V> implements Serializable {
      private final R rowKey;
      private final C columnKey;
      private final V value;
      private static final long serialVersionUID = 0L;

      ImmutableCell(@Nullable R rowKey, @Nullable C columnKey, @Nullable V value) {
         this.rowKey = rowKey;
         this.columnKey = columnKey;
         this.value = value;
      }

      @Override
      public R getRowKey() {
         return this.rowKey;
      }

      @Override
      public C getColumnKey() {
         return this.columnKey;
      }

      @Override
      public V getValue() {
         return this.value;
      }
   }

   private static class TransformedTable<R, C, V1, V2> extends AbstractTable<R, C, V2> {
      final Table<R, C, V1> fromTable;
      final Function<? super V1, V2> function;

      TransformedTable(Table<R, C, V1> fromTable, Function<? super V1, V2> function) {
         this.fromTable = Preconditions.checkNotNull(fromTable);
         this.function = Preconditions.checkNotNull(function);
      }

      @Override
      public boolean contains(Object rowKey, Object columnKey) {
         return this.fromTable.contains(rowKey, columnKey);
      }

      @Override
      public V2 get(Object rowKey, Object columnKey) {
         return this.contains(rowKey, columnKey) ? this.function.apply(this.fromTable.get(rowKey, columnKey)) : null;
      }

      @Override
      public int size() {
         return this.fromTable.size();
      }

      @Override
      public void clear() {
         this.fromTable.clear();
      }

      @Override
      public V2 put(R rowKey, C columnKey, V2 value) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void putAll(Table<? extends R, ? extends C, ? extends V2> table) {
         throw new UnsupportedOperationException();
      }

      @Override
      public V2 remove(Object rowKey, Object columnKey) {
         return this.contains(rowKey, columnKey) ? this.function.apply(this.fromTable.remove(rowKey, columnKey)) : null;
      }

      @Override
      public Map<C, V2> row(R rowKey) {
         return Maps.transformValues(this.fromTable.row(rowKey), this.function);
      }

      @Override
      public Map<R, V2> column(C columnKey) {
         return Maps.transformValues(this.fromTable.column(columnKey), this.function);
      }

      Function<Table.Cell<R, C, V1>, Table.Cell<R, C, V2>> cellFunction() {
         return new Function<Table.Cell<R, C, V1>, Table.Cell<R, C, V2>>() {
            public Table.Cell<R, C, V2> apply(Table.Cell<R, C, V1> cell) {
               return Tables.immutableCell(cell.getRowKey(), cell.getColumnKey(), TransformedTable.this.function.apply(cell.getValue()));
            }
         };
      }

      @Override
      Iterator<Table.Cell<R, C, V2>> cellIterator() {
         return Iterators.transform(this.fromTable.cellSet().iterator(), this.cellFunction());
      }

      @Override
      public Set<R> rowKeySet() {
         return this.fromTable.rowKeySet();
      }

      @Override
      public Set<C> columnKeySet() {
         return this.fromTable.columnKeySet();
      }

      @Override
      Collection<V2> createValues() {
         return Collections2.transform(this.fromTable.values(), this.function);
      }

      @Override
      public Map<R, Map<C, V2>> rowMap() {
         Function<Map<C, V1>, Map<C, V2>> rowFunction = new Function<Map<C, V1>, Map<C, V2>>() {
            public Map<C, V2> apply(Map<C, V1> row) {
               return Maps.transformValues(row, TransformedTable.this.function);
            }
         };
         return Maps.transformValues(this.fromTable.rowMap(), rowFunction);
      }

      @Override
      public Map<C, Map<R, V2>> columnMap() {
         Function<Map<R, V1>, Map<R, V2>> columnFunction = new Function<Map<R, V1>, Map<R, V2>>() {
            public Map<R, V2> apply(Map<R, V1> column) {
               return Maps.transformValues(column, TransformedTable.this.function);
            }
         };
         return Maps.transformValues(this.fromTable.columnMap(), columnFunction);
      }
   }

   private static class TransposeTable<C, R, V> extends AbstractTable<C, R, V> {
      final Table<R, C, V> original;
      private static final Function<Table.Cell<?, ?, ?>, Table.Cell<?, ?, ?>> TRANSPOSE_CELL = new Function<Table.Cell<?, ?, ?>, Table.Cell<?, ?, ?>>() {
         public Table.Cell<?, ?, ?> apply(Table.Cell<?, ?, ?> cell) {
            return Tables.immutableCell(cell.getColumnKey(), cell.getRowKey(), cell.getValue());
         }
      };

      TransposeTable(Table<R, C, V> original) {
         this.original = Preconditions.checkNotNull(original);
      }

      @Override
      public void clear() {
         this.original.clear();
      }

      @Override
      public Map<C, V> column(R columnKey) {
         return this.original.row(columnKey);
      }

      @Override
      public Set<R> columnKeySet() {
         return this.original.rowKeySet();
      }

      @Override
      public Map<R, Map<C, V>> columnMap() {
         return this.original.rowMap();
      }

      @Override
      public boolean contains(@Nullable Object rowKey, @Nullable Object columnKey) {
         return this.original.contains(columnKey, rowKey);
      }

      @Override
      public boolean containsColumn(@Nullable Object columnKey) {
         return this.original.containsRow(columnKey);
      }

      @Override
      public boolean containsRow(@Nullable Object rowKey) {
         return this.original.containsColumn(rowKey);
      }

      @Override
      public boolean containsValue(@Nullable Object value) {
         return this.original.containsValue(value);
      }

      @Override
      public V get(@Nullable Object rowKey, @Nullable Object columnKey) {
         return this.original.get(columnKey, rowKey);
      }

      @Override
      public V put(C rowKey, R columnKey, V value) {
         return this.original.put(columnKey, rowKey, value);
      }

      @Override
      public void putAll(Table<? extends C, ? extends R, ? extends V> table) {
         this.original.putAll(Tables.transpose(table));
      }

      @Override
      public V remove(@Nullable Object rowKey, @Nullable Object columnKey) {
         return this.original.remove(columnKey, rowKey);
      }

      @Override
      public Map<R, V> row(C rowKey) {
         return this.original.column(rowKey);
      }

      @Override
      public Set<C> rowKeySet() {
         return this.original.columnKeySet();
      }

      @Override
      public Map<C, Map<R, V>> rowMap() {
         return this.original.columnMap();
      }

      @Override
      public int size() {
         return this.original.size();
      }

      @Override
      public Collection<V> values() {
         return this.original.values();
      }

      @Override
      Iterator<Table.Cell<C, R, V>> cellIterator() {
         return Iterators.transform(this.original.cellSet().iterator(), TRANSPOSE_CELL);
      }
   }

   static final class UnmodifiableRowSortedMap<R, C, V> extends Tables.UnmodifiableTable<R, C, V> implements RowSortedTable<R, C, V> {
      private static final long serialVersionUID = 0L;

      public UnmodifiableRowSortedMap(RowSortedTable<R, ? extends C, ? extends V> delegate) {
         super(delegate);
      }

      protected RowSortedTable<R, C, V> delegate() {
         return (RowSortedTable<R, C, V>)super.delegate();
      }

      @Override
      public SortedMap<R, Map<C, V>> rowMap() {
         Function<Map<C, V>, Map<C, V>> wrapper = Tables.unmodifiableWrapper();
         return Collections.unmodifiableSortedMap(Maps.transformValues(this.delegate().rowMap(), wrapper));
      }

      @Override
      public SortedSet<R> rowKeySet() {
         return Collections.unmodifiableSortedSet(this.delegate().rowKeySet());
      }
   }

   private static class UnmodifiableTable<R, C, V> extends ForwardingTable<R, C, V> implements Serializable {
      final Table<? extends R, ? extends C, ? extends V> delegate;
      private static final long serialVersionUID = 0L;

      UnmodifiableTable(Table<? extends R, ? extends C, ? extends V> delegate) {
         this.delegate = Preconditions.checkNotNull(delegate);
      }

      @Override
      protected Table<R, C, V> delegate() {
         return (Table<R, C, V>)this.delegate;
      }

      @Override
      public Set<Table.Cell<R, C, V>> cellSet() {
         return Collections.unmodifiableSet(super.cellSet());
      }

      @Override
      public void clear() {
         throw new UnsupportedOperationException();
      }

      @Override
      public Map<R, V> column(@Nullable C columnKey) {
         return Collections.unmodifiableMap(super.column(columnKey));
      }

      @Override
      public Set<C> columnKeySet() {
         return Collections.unmodifiableSet(super.columnKeySet());
      }

      @Override
      public Map<C, Map<R, V>> columnMap() {
         Function<Map<R, V>, Map<R, V>> wrapper = Tables.unmodifiableWrapper();
         return Collections.unmodifiableMap(Maps.transformValues(super.columnMap(), wrapper));
      }

      @Override
      public V put(@Nullable R rowKey, @Nullable C columnKey, @Nullable V value) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
         throw new UnsupportedOperationException();
      }

      @Override
      public V remove(@Nullable Object rowKey, @Nullable Object columnKey) {
         throw new UnsupportedOperationException();
      }

      @Override
      public Map<C, V> row(@Nullable R rowKey) {
         return Collections.unmodifiableMap(super.row(rowKey));
      }

      @Override
      public Set<R> rowKeySet() {
         return Collections.unmodifiableSet(super.rowKeySet());
      }

      @Override
      public Map<R, Map<C, V>> rowMap() {
         Function<Map<C, V>, Map<C, V>> wrapper = Tables.unmodifiableWrapper();
         return Collections.unmodifiableMap(Maps.transformValues(super.rowMap(), wrapper));
      }

      @Override
      public Collection<V> values() {
         return Collections.unmodifiableCollection(super.values());
      }
   }
}
