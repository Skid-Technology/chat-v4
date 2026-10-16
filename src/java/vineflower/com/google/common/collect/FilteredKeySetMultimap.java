package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Predicate;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
final class FilteredKeySetMultimap<K, V> extends FilteredKeyMultimap<K, V> implements FilteredSetMultimap<K, V> {
   FilteredKeySetMultimap(SetMultimap<K, V> unfiltered, Predicate<? super K> keyPredicate) {
      super(unfiltered, keyPredicate);
   }

   @Override
   public SetMultimap<K, V> unfiltered() {
      return (SetMultimap<K, V>)this.unfiltered;
   }

   @Override
   public Set<V> get(K key) {
      return (Set<V>)super.get(key);
   }

   @Override
   public Set<V> removeAll(Object key) {
      return (Set<V>)super.removeAll(key);
   }

   @Override
   public Set<V> replaceValues(K key, Iterable<? extends V> values) {
      return (Set<V>)super.replaceValues(key, values);
   }

   @Override
   public Set<Entry<K, V>> entries() {
      return (Set<Entry<K, V>>)super.entries();
   }

   Set<Entry<K, V>> createEntries() {
      return new FilteredKeySetMultimap.EntrySet();
   }

   class EntrySet extends FilteredKeyMultimap<K, V>.Entries implements Set<Entry<K, V>> {
      @Override
      public int hashCode() {
         return Sets.hashCodeImpl(this);
      }

      @Override
      public boolean equals(@Nullable Object o) {
         return Sets.equalsImpl(this, o);
      }
   }
}
