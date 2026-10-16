package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true, emulated = true)
public abstract class ImmutableSortedMap<K, V> extends ImmutableSortedMapFauxverideShim<K, V> implements NavigableMap<K, V> {
   private static final Comparator<Comparable> NATURAL_ORDER = Ordering.natural();
   private static final ImmutableSortedMap<Comparable, Object> NATURAL_EMPTY_MAP = new EmptyImmutableSortedMap<>(NATURAL_ORDER);
   private transient ImmutableSortedMap<K, V> descendingMap;
   private static final long serialVersionUID = 0L;

   static <K, V> ImmutableSortedMap<K, V> emptyMap(Comparator<? super K> comparator) {
      return (ImmutableSortedMap<K, V>)(Ordering.natural().equals(comparator) ? of() : new EmptyImmutableSortedMap<>(comparator));
   }

   static <K, V> ImmutableSortedMap<K, V> fromSortedEntries(Comparator<? super K> comparator, int size, Entry<K, V>[] entries) {
      if (size == 0) {
         return emptyMap(comparator);
      } else {
         ImmutableList.Builder<K> keyBuilder = ImmutableList.builder();
         ImmutableList.Builder<V> valueBuilder = ImmutableList.builder();

         for (int i = 0; i < size; i++) {
            Entry<K, V> entry = entries[i];
            keyBuilder.add(entry.getKey());
            valueBuilder.add(entry.getValue());
         }

         return new RegularImmutableSortedMap<>(new RegularImmutableSortedSet<>(keyBuilder.build(), comparator), valueBuilder.build());
      }
   }

   static <K, V> ImmutableSortedMap<K, V> from(ImmutableSortedSet<K> keySet, ImmutableList<V> valueList) {
      return (ImmutableSortedMap<K, V>)(keySet.isEmpty()
         ? emptyMap(keySet.comparator())
         : new RegularImmutableSortedMap<>((RegularImmutableSortedSet<K>)keySet, valueList));
   }

   public static <K, V> ImmutableSortedMap<K, V> of() {
      return (ImmutableSortedMap<K, V>)NATURAL_EMPTY_MAP;
   }

   public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k1, V v1) {
      return from(ImmutableSortedSet.of(k1), ImmutableList.of(v1));
   }

   public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k1, V v1, K k2, V v2) {
      return fromEntries(Ordering.natural(), false, 2, entryOf(k1, v1), entryOf(k2, v2));
   }

   public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
      return fromEntries(Ordering.natural(), false, 3, entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3));
   }

   public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
      return fromEntries(Ordering.natural(), false, 4, entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4));
   }

   public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
      return fromEntries(Ordering.natural(), false, 5, entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4), entryOf(k5, v5));
   }

   public static <K, V> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
      Ordering<K> naturalOrder = Ordering.natural();
      return copyOfInternal(map, naturalOrder);
   }

   public static <K, V> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> map, Comparator<? super K> comparator) {
      return copyOfInternal(map, Preconditions.checkNotNull(comparator));
   }

   public static <K, V> ImmutableSortedMap<K, V> copyOfSorted(SortedMap<K, ? extends V> map) {
      Comparator<? super K> comparator = map.comparator();
      if (comparator == null) {
         comparator = NATURAL_ORDER;
      }

      return copyOfInternal(map, comparator);
   }

   private static <K, V> ImmutableSortedMap<K, V> copyOfInternal(Map<? extends K, ? extends V> map, Comparator<? super K> comparator) {
      boolean sameComparator = false;
      if (map instanceof SortedMap) {
         SortedMap<?, ?> sortedMap = (SortedMap<?, ?>)map;
         Comparator<?> comparator2 = sortedMap.comparator();
         sameComparator = comparator2 == null ? comparator == NATURAL_ORDER : comparator.equals(comparator2);
      }

      if (sameComparator && map instanceof ImmutableSortedMap) {
         ImmutableSortedMap<K, V> kvMap = (ImmutableSortedMap<K, V>)map;
         if (!kvMap.isPartialView()) {
            return kvMap;
         }
      }

      Entry<K, V>[] entries = map.entrySet().toArray(new Entry[0]);
      return fromEntries(comparator, sameComparator, entries.length, entries);
   }

   static <K, V> ImmutableSortedMap<K, V> fromEntries(Comparator<? super K> comparator, boolean sameComparator, int size, Entry<K, V>... entries) {
      for (int i = 0; i < size; i++) {
         Entry<K, V> entry = entries[i];
         entries[i] = entryOf(entry.getKey(), entry.getValue());
      }

      if (!sameComparator) {
         sortEntries(comparator, size, entries);
         validateEntries(size, entries, comparator);
      }

      return fromSortedEntries(comparator, size, entries);
   }

   private static <K, V> void sortEntries(Comparator<? super K> comparator, int size, Entry<K, V>[] entries) {
      Arrays.sort(entries, 0, size, Ordering.from(comparator).onKeys());
   }

   private static <K, V> void validateEntries(int size, Entry<K, V>[] entries, Comparator<? super K> comparator) {
      for (int i = 1; i < size; i++) {
         checkNoConflict(comparator.compare(entries[i - 1].getKey(), entries[i].getKey()) != 0, "key", entries[i - 1], entries[i]);
      }
   }

   public static <K extends Comparable<?>, V> ImmutableSortedMap.Builder<K, V> naturalOrder() {
      return new ImmutableSortedMap.Builder<>(Ordering.natural());
   }

   public static <K, V> ImmutableSortedMap.Builder<K, V> orderedBy(Comparator<K> comparator) {
      return new ImmutableSortedMap.Builder<>(comparator);
   }

   public static <K extends Comparable<?>, V> ImmutableSortedMap.Builder<K, V> reverseOrder() {
      return new ImmutableSortedMap.Builder<>(Ordering.natural().reverse());
   }

   ImmutableSortedMap() {
   }

   ImmutableSortedMap(ImmutableSortedMap<K, V> descendingMap) {
      this.descendingMap = descendingMap;
   }

   @Override
   public int size() {
      return this.values().size();
   }

   @Override
   public boolean containsValue(@Nullable Object value) {
      return this.values().contains(value);
   }

   @Override
   boolean isPartialView() {
      return this.keySet().isPartialView() || this.values().isPartialView();
   }

   @Override
   public ImmutableSet<Entry<K, V>> entrySet() {
      return super.entrySet();
   }

   public abstract ImmutableSortedSet<K> keySet();

   @Override
   public abstract ImmutableCollection<V> values();

   @Override
   public Comparator<? super K> comparator() {
      return this.keySet().comparator();
   }

   @Override
   public K firstKey() {
      return this.keySet().first();
   }

   @Override
   public K lastKey() {
      return this.keySet().last();
   }

   public ImmutableSortedMap<K, V> headMap(K toKey) {
      return this.headMap(toKey, false);
   }

   public abstract ImmutableSortedMap<K, V> headMap(K var1, boolean var2);

   public ImmutableSortedMap<K, V> subMap(K fromKey, K toKey) {
      return this.subMap(fromKey, true, toKey, false);
   }

   public ImmutableSortedMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
      Preconditions.checkNotNull(fromKey);
      Preconditions.checkNotNull(toKey);
      Preconditions.checkArgument(this.comparator().compare(fromKey, toKey) <= 0, "expected fromKey <= toKey but %s > %s", fromKey, toKey);
      return this.headMap(toKey, toInclusive).tailMap(fromKey, fromInclusive);
   }

   public ImmutableSortedMap<K, V> tailMap(K fromKey) {
      return this.tailMap(fromKey, true);
   }

   public abstract ImmutableSortedMap<K, V> tailMap(K var1, boolean var2);

   @Override
   public Entry<K, V> lowerEntry(K key) {
      return this.headMap(key, false).lastEntry();
   }

   @Override
   public K lowerKey(K key) {
      return Maps.keyOrNull(this.lowerEntry(key));
   }

   @Override
   public Entry<K, V> floorEntry(K key) {
      return this.headMap(key, true).lastEntry();
   }

   @Override
   public K floorKey(K key) {
      return Maps.keyOrNull(this.floorEntry(key));
   }

   @Override
   public Entry<K, V> ceilingEntry(K key) {
      return this.tailMap(key, true).firstEntry();
   }

   @Override
   public K ceilingKey(K key) {
      return Maps.keyOrNull(this.ceilingEntry(key));
   }

   @Override
   public Entry<K, V> higherEntry(K key) {
      return this.tailMap(key, false).firstEntry();
   }

   @Override
   public K higherKey(K key) {
      return Maps.keyOrNull(this.higherEntry(key));
   }

   @Override
   public Entry<K, V> firstEntry() {
      return this.isEmpty() ? null : this.entrySet().asList().get(0);
   }

   @Override
   public Entry<K, V> lastEntry() {
      return this.isEmpty() ? null : this.entrySet().asList().get(this.size() - 1);
   }

   @Deprecated
   @Override
   public final Entry<K, V> pollFirstEntry() {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   @Override
   public final Entry<K, V> pollLastEntry() {
      throw new UnsupportedOperationException();
   }

   public ImmutableSortedMap<K, V> descendingMap() {
      ImmutableSortedMap<K, V> result = this.descendingMap;
      if (result == null) {
         result = this.descendingMap = this.createDescendingMap();
      }

      return result;
   }

   abstract ImmutableSortedMap<K, V> createDescendingMap();

   public ImmutableSortedSet<K> navigableKeySet() {
      return this.keySet();
   }

   public ImmutableSortedSet<K> descendingKeySet() {
      return this.keySet().descendingSet();
   }

   @Override
   Object writeReplace() {
      return new ImmutableSortedMap.SerializedForm(this);
   }

   public static class Builder<K, V> extends ImmutableMap.Builder<K, V> {
      private final Comparator<? super K> comparator;

      public Builder(Comparator<? super K> comparator) {
         this.comparator = Preconditions.checkNotNull(comparator);
      }

      public ImmutableSortedMap.Builder<K, V> put(K key, V value) {
         super.put(key, value);
         return this;
      }

      public ImmutableSortedMap.Builder<K, V> put(Entry<? extends K, ? extends V> entry) {
         super.put(entry);
         return this;
      }

      public ImmutableSortedMap.Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
         super.putAll(map);
         return this;
      }

      public ImmutableSortedMap<K, V> build() {
         return ImmutableSortedMap.fromEntries(this.comparator, false, this.size, this.entries);
      }
   }

   private static class SerializedForm extends ImmutableMap.SerializedForm {
      private final Comparator<Object> comparator;
      private static final long serialVersionUID = 0L;

      SerializedForm(ImmutableSortedMap<?, ?> sortedMap) {
         super(sortedMap);
         this.comparator = (Comparator<Object>)sortedMap.comparator();
      }

      @Override
      Object readResolve() {
         ImmutableSortedMap.Builder<Object, Object> builder = new ImmutableSortedMap.Builder<>(this.comparator);
         return this.createMap(builder);
      }
   }
}
