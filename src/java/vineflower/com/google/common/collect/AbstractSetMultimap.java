package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractSetMultimap<K, V> extends AbstractMapBasedMultimap<K, V> implements SetMultimap<K, V> {
   private static final long serialVersionUID = 7431625294878419160L;

   protected AbstractSetMultimap(Map<K, Collection<V>> map) {
      super(map);
   }

   abstract Set<V> createCollection();

   Set<V> createUnmodifiableEmptyCollection() {
      return ImmutableSet.of();
   }

   @Override
   public Set<V> get(@Nullable K key) {
      return (Set<V>)super.get(key);
   }

   @Override
   public Set<Entry<K, V>> entries() {
      return (Set<Entry<K, V>>)super.entries();
   }

   @Override
   public Set<V> removeAll(@Nullable Object key) {
      return (Set<V>)super.removeAll(key);
   }

   @Override
   public Set<V> replaceValues(@Nullable K key, Iterable<? extends V> values) {
      return (Set<V>)super.replaceValues(key, values);
   }

   @Override
   public Map<K, Collection<V>> asMap() {
      return super.asMap();
   }

   @Override
   public boolean put(@Nullable K key, @Nullable V value) {
      return super.put(key, value);
   }

   @Override
   public boolean equals(@Nullable Object object) {
      return super.equals(object);
   }
}
