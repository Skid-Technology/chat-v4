package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractSortedSetMultimap<K, V> extends AbstractSetMultimap<K, V> implements SortedSetMultimap<K, V> {
   private static final long serialVersionUID = 430848587173315748L;

   protected AbstractSortedSetMultimap(Map<K, Collection<V>> map) {
      super(map);
   }

   abstract SortedSet<V> createCollection();

   SortedSet<V> createUnmodifiableEmptyCollection() {
      Comparator<? super V> comparator = this.valueComparator();
      return (SortedSet<V>)(comparator == null
         ? Collections.unmodifiableSortedSet(this.createCollection())
         : ImmutableSortedSet.emptySet(this.valueComparator()));
   }

   @Override
   public SortedSet<V> get(@Nullable K key) {
      return (SortedSet<V>)super.get(key);
   }

   @Override
   public SortedSet<V> removeAll(@Nullable Object key) {
      return (SortedSet<V>)super.removeAll(key);
   }

   @Override
   public SortedSet<V> replaceValues(@Nullable K key, Iterable<? extends V> values) {
      return (SortedSet<V>)super.replaceValues(key, values);
   }

   @Override
   public Map<K, Collection<V>> asMap() {
      return super.asMap();
   }

   @Override
   public Collection<V> values() {
      return super.values();
   }
}
