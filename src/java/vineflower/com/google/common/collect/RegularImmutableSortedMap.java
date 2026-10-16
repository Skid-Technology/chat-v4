package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
final class RegularImmutableSortedMap<K, V> extends ImmutableSortedMap<K, V> {
   private final transient RegularImmutableSortedSet<K> keySet;
   private final transient ImmutableList<V> valueList;

   RegularImmutableSortedMap(RegularImmutableSortedSet<K> keySet, ImmutableList<V> valueList) {
      this.keySet = keySet;
      this.valueList = valueList;
   }

   RegularImmutableSortedMap(RegularImmutableSortedSet<K> keySet, ImmutableList<V> valueList, ImmutableSortedMap<K, V> descendingMap) {
      super(descendingMap);
      this.keySet = keySet;
      this.valueList = valueList;
   }

   @Override
   ImmutableSet<Entry<K, V>> createEntrySet() {
      return new RegularImmutableSortedMap.EntrySet();
   }

   @Override
   public ImmutableSortedSet<K> keySet() {
      return this.keySet;
   }

   @Override
   public ImmutableCollection<V> values() {
      return this.valueList;
   }

   @Override
   public V get(@Nullable Object key) {
      int index = this.keySet.indexOf(key);
      return index == -1 ? null : this.valueList.get(index);
   }

   private ImmutableSortedMap<K, V> getSubMap(int fromIndex, int toIndex) {
      if (fromIndex == 0 && toIndex == this.size()) {
         return this;
      } else {
         return fromIndex == toIndex
            ? emptyMap(this.comparator())
            : from(this.keySet.getSubSet(fromIndex, toIndex), this.valueList.subList(fromIndex, toIndex));
      }
   }

   @Override
   public ImmutableSortedMap<K, V> headMap(K toKey, boolean inclusive) {
      return this.getSubMap(0, this.keySet.headIndex(Preconditions.checkNotNull(toKey), inclusive));
   }

   @Override
   public ImmutableSortedMap<K, V> tailMap(K fromKey, boolean inclusive) {
      return this.getSubMap(this.keySet.tailIndex(Preconditions.checkNotNull(fromKey), inclusive), this.size());
   }

   @Override
   ImmutableSortedMap<K, V> createDescendingMap() {
      return new RegularImmutableSortedMap<>((RegularImmutableSortedSet<K>)this.keySet.descendingSet(), this.valueList.reverse(), this);
   }

   private class EntrySet extends ImmutableMapEntrySet<K, V> {
      private EntrySet() {
      }

      @Override
      public UnmodifiableIterator<Entry<K, V>> iterator() {
         return this.asList().iterator();
      }

      @Override
      ImmutableList<Entry<K, V>> createAsList() {
         return new ImmutableAsList<Entry<K, V>>() {
            private final ImmutableList<K> keyList = RegularImmutableSortedMap.this.keySet().asList();

            public Entry<K, V> get(int index) {
               return Maps.immutableEntry(this.keyList.get(index), RegularImmutableSortedMap.this.valueList.get(index));
            }

            @Override
            ImmutableCollection<Entry<K, V>> delegateCollection() {
               return EntrySet.this;
            }
         };
      }

      @Override
      ImmutableMap<K, V> map() {
         return RegularImmutableSortedMap.this;
      }
   }
}
