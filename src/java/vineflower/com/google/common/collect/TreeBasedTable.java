package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true)
@Beta
public class TreeBasedTable<R, C, V> extends StandardRowSortedTable<R, C, V> {
   private final Comparator<? super C> columnComparator;
   private static final long serialVersionUID = 0L;

   public static <R extends Comparable, C extends Comparable, V> TreeBasedTable<R, C, V> create() {
      return new TreeBasedTable<>(Ordering.natural(), Ordering.natural());
   }

   public static <R, C, V> TreeBasedTable<R, C, V> create(Comparator<? super R> rowComparator, Comparator<? super C> columnComparator) {
      Preconditions.checkNotNull(rowComparator);
      Preconditions.checkNotNull(columnComparator);
      return new TreeBasedTable<>(rowComparator, columnComparator);
   }

   public static <R, C, V> TreeBasedTable<R, C, V> create(TreeBasedTable<R, C, ? extends V> table) {
      TreeBasedTable<R, C, V> result = new TreeBasedTable<>(table.rowComparator(), table.columnComparator());
      result.putAll(table);
      return result;
   }

   TreeBasedTable(Comparator<? super R> rowComparator, Comparator<? super C> columnComparator) {
      super(new TreeMap<>(rowComparator), new TreeBasedTable.Factory<>(columnComparator));
      this.columnComparator = columnComparator;
   }

   public Comparator<? super R> rowComparator() {
      return this.rowKeySet().comparator();
   }

   public Comparator<? super C> columnComparator() {
      return this.columnComparator;
   }

   public SortedMap<C, V> row(R rowKey) {
      return new TreeBasedTable.TreeRow(rowKey);
   }

   @Override
   public SortedSet<R> rowKeySet() {
      return super.rowKeySet();
   }

   @Override
   public SortedMap<R, Map<C, V>> rowMap() {
      return super.rowMap();
   }

   @Override
   Iterator<C> createColumnKeyIterator() {
      final Comparator<? super C> comparator = this.columnComparator();
      final Iterator<C> merged = Iterators.mergeSorted(Iterables.transform(this.backingMap.values(), new Function<Map<C, V>, Iterator<C>>() {
         public Iterator<C> apply(Map<C, V> input) {
            return input.keySet().iterator();
         }
      }), comparator);
      return new AbstractIterator<C>() {
         C lastValue;

         @Override
         protected C computeNext() {
            while (merged.hasNext()) {
               C next = merged.next();
               boolean duplicate = this.lastValue != null && comparator.compare(next, this.lastValue) == 0;
               if (!duplicate) {
                  this.lastValue = next;
                  return this.lastValue;
               }
            }

            this.lastValue = null;
            return (C)this.endOfData();
         }
      };
   }

   private static class Factory<C, V> implements Supplier<TreeMap<C, V>>, Serializable {
      final Comparator<? super C> comparator;
      private static final long serialVersionUID = 0L;

      Factory(Comparator<? super C> comparator) {
         this.comparator = comparator;
      }

      public TreeMap<C, V> get() {
         return new TreeMap<>(this.comparator);
      }
   }

   private class TreeRow extends StandardTable<R, C, V>.Row implements SortedMap<C, V> {
      @Nullable
      final C lowerBound;
      @Nullable
      final C upperBound;
      transient SortedMap<C, V> wholeRow;

      TreeRow(R rowKey) {
         this(rowKey, null, null);
      }

      TreeRow(R rowKey, @Nullable C lowerBound, @Nullable C upperBound) {
         super(rowKey);
         this.lowerBound = lowerBound;
         this.upperBound = upperBound;
         Preconditions.checkArgument(lowerBound == null || upperBound == null || this.compare(lowerBound, upperBound) <= 0);
      }

      public SortedSet<C> keySet() {
         return new Maps.SortedKeySet<>(this);
      }

      @Override
      public Comparator<? super C> comparator() {
         return TreeBasedTable.this.columnComparator();
      }

      int compare(Object a, Object b) {
         Comparator<Object> cmp = this.comparator();
         return cmp.compare(a, b);
      }

      boolean rangeContains(@Nullable Object o) {
         return o != null
            && (this.lowerBound == null || this.compare(this.lowerBound, o) <= 0)
            && (this.upperBound == null || this.compare(this.upperBound, o) > 0);
      }

      @Override
      public SortedMap<C, V> subMap(C fromKey, C toKey) {
         Preconditions.checkArgument(this.rangeContains(Preconditions.checkNotNull(fromKey)) && this.rangeContains(Preconditions.checkNotNull(toKey)));
         return TreeBasedTable.this.new TreeRow(this.rowKey, fromKey, toKey);
      }

      @Override
      public SortedMap<C, V> headMap(C toKey) {
         Preconditions.checkArgument(this.rangeContains(Preconditions.checkNotNull(toKey)));
         return TreeBasedTable.this.new TreeRow(this.rowKey, this.lowerBound, toKey);
      }

      @Override
      public SortedMap<C, V> tailMap(C fromKey) {
         Preconditions.checkArgument(this.rangeContains(Preconditions.checkNotNull(fromKey)));
         return TreeBasedTable.this.new TreeRow(this.rowKey, fromKey, this.upperBound);
      }

      @Override
      public C firstKey() {
         SortedMap<C, V> backing = this.backingRowMap();
         if (backing == null) {
            throw new NoSuchElementException();
         } else {
            return (C)this.backingRowMap().firstKey();
         }
      }

      @Override
      public C lastKey() {
         SortedMap<C, V> backing = this.backingRowMap();
         if (backing == null) {
            throw new NoSuchElementException();
         } else {
            return (C)this.backingRowMap().lastKey();
         }
      }

      SortedMap<C, V> wholeRow() {
         if (this.wholeRow == null || this.wholeRow.isEmpty() && TreeBasedTable.this.backingMap.containsKey(this.rowKey)) {
            this.wholeRow = (SortedMap<C, V>)TreeBasedTable.this.backingMap.get(this.rowKey);
         }

         return this.wholeRow;
      }

      SortedMap<C, V> backingRowMap() {
         return (SortedMap<C, V>)super.backingRowMap();
      }

      SortedMap<C, V> computeBackingRowMap() {
         SortedMap<C, V> map = this.wholeRow();
         if (map != null) {
            if (this.lowerBound != null) {
               map = map.tailMap(this.lowerBound);
            }

            if (this.upperBound != null) {
               map = map.headMap(this.upperBound);
            }

            return map;
         } else {
            return null;
         }
      }

      @Override
      void maintainEmptyInvariant() {
         if (this.wholeRow() != null && this.wholeRow.isEmpty()) {
            TreeBasedTable.this.backingMap.remove(this.rowKey);
            this.wholeRow = null;
            this.backingRowMap = null;
         }
      }

      @Override
      public boolean containsKey(Object key) {
         return this.rangeContains(key) && super.containsKey(key);
      }

      @Override
      public V put(C key, V value) {
         Preconditions.checkArgument(this.rangeContains(Preconditions.checkNotNull(key)));
         return (V)super.put((V)key, value);
      }
   }
}
