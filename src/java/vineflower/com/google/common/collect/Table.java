package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
public interface Table<R, C, V> {
   boolean contains(@Nullable Object var1, @Nullable Object var2);

   boolean containsRow(@Nullable Object var1);

   boolean containsColumn(@Nullable Object var1);

   boolean containsValue(@Nullable Object var1);

   V get(@Nullable Object var1, @Nullable Object var2);

   boolean isEmpty();

   int size();

   @Override
   boolean equals(@Nullable Object var1);

   @Override
   int hashCode();

   void clear();

   V put(R var1, C var2, V var3);

   void putAll(Table<? extends R, ? extends C, ? extends V> var1);

   V remove(@Nullable Object var1, @Nullable Object var2);

   Map<C, V> row(R var1);

   Map<R, V> column(C var1);

   Set<Table.Cell<R, C, V>> cellSet();

   Set<R> rowKeySet();

   Set<C> columnKeySet();

   Collection<V> values();

   Map<R, Map<C, V>> rowMap();

   Map<C, Map<R, V>> columnMap();

   public interface Cell<R, C, V> {
      R getRowKey();

      C getColumnKey();

      V getValue();

      @Override
      boolean equals(@Nullable Object var1);

      @Override
      int hashCode();
   }
}
