package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
final class Synchronized {
   private Synchronized() {
   }

   private static <E> Collection<E> collection(Collection<E> collection, @Nullable Object mutex) {
      return new Synchronized.SynchronizedCollection<>(collection, mutex);
   }

   @VisibleForTesting
   static <E> Set<E> set(Set<E> set, @Nullable Object mutex) {
      return new Synchronized.SynchronizedSet<>(set, mutex);
   }

   private static <E> SortedSet<E> sortedSet(SortedSet<E> set, @Nullable Object mutex) {
      return new Synchronized.SynchronizedSortedSet<>(set, mutex);
   }

   private static <E> List<E> list(List<E> list, @Nullable Object mutex) {
      return (List<E>)(list instanceof RandomAccess
         ? new Synchronized.SynchronizedRandomAccessList<>(list, mutex)
         : new Synchronized.SynchronizedList<>(list, mutex));
   }

   static <E> Multiset<E> multiset(Multiset<E> multiset, @Nullable Object mutex) {
      return (Multiset<E>)(!(multiset instanceof Synchronized.SynchronizedMultiset) && !(multiset instanceof ImmutableMultiset)
         ? new Synchronized.SynchronizedMultiset<>(multiset, mutex)
         : multiset);
   }

   static <K, V> Multimap<K, V> multimap(Multimap<K, V> multimap, @Nullable Object mutex) {
      return (Multimap<K, V>)(!(multimap instanceof Synchronized.SynchronizedMultimap) && !(multimap instanceof ImmutableMultimap)
         ? new Synchronized.SynchronizedMultimap<>(multimap, mutex)
         : multimap);
   }

   static <K, V> ListMultimap<K, V> listMultimap(ListMultimap<K, V> multimap, @Nullable Object mutex) {
      return (ListMultimap<K, V>)(!(multimap instanceof Synchronized.SynchronizedListMultimap) && !(multimap instanceof ImmutableListMultimap)
         ? new Synchronized.SynchronizedListMultimap<>(multimap, mutex)
         : multimap);
   }

   static <K, V> SetMultimap<K, V> setMultimap(SetMultimap<K, V> multimap, @Nullable Object mutex) {
      return (SetMultimap<K, V>)(!(multimap instanceof Synchronized.SynchronizedSetMultimap) && !(multimap instanceof ImmutableSetMultimap)
         ? new Synchronized.SynchronizedSetMultimap<>(multimap, mutex)
         : multimap);
   }

   static <K, V> SortedSetMultimap<K, V> sortedSetMultimap(SortedSetMultimap<K, V> multimap, @Nullable Object mutex) {
      return (SortedSetMultimap<K, V>)(multimap instanceof Synchronized.SynchronizedSortedSetMultimap
         ? multimap
         : new Synchronized.SynchronizedSortedSetMultimap<>(multimap, mutex));
   }

   private static <E> Collection<E> typePreservingCollection(Collection<E> collection, @Nullable Object mutex) {
      if (collection instanceof SortedSet) {
         return sortedSet((SortedSet<E>)collection, mutex);
      } else if (collection instanceof Set) {
         return set((Set<E>)collection, mutex);
      } else {
         return (Collection<E>)(collection instanceof List ? list((List<E>)collection, mutex) : collection(collection, mutex));
      }
   }

   private static <E> Set<E> typePreservingSet(Set<E> set, @Nullable Object mutex) {
      return (Set<E>)(set instanceof SortedSet ? sortedSet((SortedSet<E>)set, mutex) : set(set, mutex));
   }

   @VisibleForTesting
   static <K, V> Map<K, V> map(Map<K, V> map, @Nullable Object mutex) {
      return new Synchronized.SynchronizedMap<>(map, mutex);
   }

   static <K, V> SortedMap<K, V> sortedMap(SortedMap<K, V> sortedMap, @Nullable Object mutex) {
      return new Synchronized.SynchronizedSortedMap<>(sortedMap, mutex);
   }

   static <K, V> BiMap<K, V> biMap(BiMap<K, V> bimap, @Nullable Object mutex) {
      return (BiMap<K, V>)(!(bimap instanceof Synchronized.SynchronizedBiMap) && !(bimap instanceof ImmutableBiMap)
         ? new Synchronized.SynchronizedBiMap<>(bimap, mutex, null)
         : bimap);
   }

   @GwtIncompatible("NavigableSet")
   static <E> NavigableSet<E> navigableSet(NavigableSet<E> navigableSet, @Nullable Object mutex) {
      return new Synchronized.SynchronizedNavigableSet<>(navigableSet, mutex);
   }

   @GwtIncompatible("NavigableSet")
   static <E> NavigableSet<E> navigableSet(NavigableSet<E> navigableSet) {
      return navigableSet(navigableSet, null);
   }

   @GwtIncompatible("NavigableMap")
   static <K, V> NavigableMap<K, V> navigableMap(NavigableMap<K, V> navigableMap) {
      return navigableMap(navigableMap, null);
   }

   @GwtIncompatible("NavigableMap")
   static <K, V> NavigableMap<K, V> navigableMap(NavigableMap<K, V> navigableMap, @Nullable Object mutex) {
      return new Synchronized.SynchronizedNavigableMap<>(navigableMap, mutex);
   }

   @GwtIncompatible("works but is needed only for NavigableMap")
   private static <K, V> Entry<K, V> nullableSynchronizedEntry(@Nullable Entry<K, V> entry, @Nullable Object mutex) {
      return entry == null ? null : new Synchronized.SynchronizedEntry<>(entry, mutex);
   }

   static <E> Queue<E> queue(Queue<E> queue, @Nullable Object mutex) {
      return (Queue<E>)(queue instanceof Synchronized.SynchronizedQueue ? queue : new Synchronized.SynchronizedQueue<>(queue, mutex));
   }

   @GwtIncompatible("Deque")
   static <E> Deque<E> deque(Deque<E> deque, @Nullable Object mutex) {
      return new Synchronized.SynchronizedDeque<>(deque, mutex);
   }

   private static class SynchronizedAsMap<K, V> extends Synchronized.SynchronizedMap<K, Collection<V>> {
      transient Set<Entry<K, Collection<V>>> asMapEntrySet;
      transient Collection<Collection<V>> asMapValues;
      private static final long serialVersionUID = 0L;

      SynchronizedAsMap(Map<K, Collection<V>> delegate, @Nullable Object mutex) {
         super(delegate, mutex);
      }

      public Collection<V> get(Object key) {
         synchronized (this.mutex) {
            Collection<V> collection = (Collection<V>)super.get(key);
            return collection == null ? null : Synchronized.typePreservingCollection(collection, this.mutex);
         }
      }

      @Override
      public Set<Entry<K, Collection<V>>> entrySet() {
         synchronized (this.mutex) {
            if (this.asMapEntrySet == null) {
               this.asMapEntrySet = new Synchronized.SynchronizedAsMapEntries<>(this.delegate().entrySet(), this.mutex);
            }

            return this.asMapEntrySet;
         }
      }

      @Override
      public Collection<Collection<V>> values() {
         synchronized (this.mutex) {
            if (this.asMapValues == null) {
               this.asMapValues = new Synchronized.SynchronizedAsMapValues<>(this.delegate().values(), this.mutex);
            }

            return this.asMapValues;
         }
      }

      @Override
      public boolean containsValue(Object o) {
         return this.values().contains(o);
      }
   }

   private static class SynchronizedAsMapEntries<K, V> extends Synchronized.SynchronizedSet<Entry<K, Collection<V>>> {
      private static final long serialVersionUID = 0L;

      SynchronizedAsMapEntries(Set<Entry<K, Collection<V>>> delegate, @Nullable Object mutex) {
         super(delegate, mutex);
      }

      @Override
      public Iterator<Entry<K, Collection<V>>> iterator() {
         final Iterator<Entry<K, Collection<V>>> iterator = super.iterator();
         return new ForwardingIterator<Entry<K, Collection<V>>>() {
            @Override
            protected Iterator<Entry<K, Collection<V>>> delegate() {
               return iterator;
            }

            public Entry<K, Collection<V>> next() {
               final Entry<K, Collection<V>> entry = (Entry<K, Collection<V>>)super.next();
               return new ForwardingMapEntry<K, Collection<V>>() {
                  @Override
                  protected Entry<K, Collection<V>> delegate() {
                     return entry;
                  }

                  public Collection<V> getValue() {
                     return Synchronized.typePreservingCollection(entry.getValue(), SynchronizedAsMapEntries.this.mutex);
                  }
               };
            }
         };
      }

      @Override
      public Object[] toArray() {
         synchronized (this.mutex) {
            return ObjectArrays.toArrayImpl(this.delegate());
         }
      }

      @Override
      public <T> T[] toArray(T[] array) {
         synchronized (this.mutex) {
            return (T[])ObjectArrays.toArrayImpl(this.delegate(), array);
         }
      }

      @Override
      public boolean contains(Object o) {
         synchronized (this.mutex) {
            return Maps.containsEntryImpl(this.delegate(), o);
         }
      }

      @Override
      public boolean containsAll(Collection<?> c) {
         synchronized (this.mutex) {
            return Collections2.containsAllImpl(this.delegate(), c);
         }
      }

      @Override
      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else {
            synchronized (this.mutex) {
               return Sets.equalsImpl(this.delegate(), o);
            }
         }
      }

      @Override
      public boolean remove(Object o) {
         synchronized (this.mutex) {
            return Maps.removeEntryImpl(this.delegate(), o);
         }
      }

      @Override
      public boolean removeAll(Collection<?> c) {
         synchronized (this.mutex) {
            return Iterators.removeAll(this.delegate().iterator(), c);
         }
      }

      @Override
      public boolean retainAll(Collection<?> c) {
         synchronized (this.mutex) {
            return Iterators.retainAll(this.delegate().iterator(), c);
         }
      }
   }

   private static class SynchronizedAsMapValues<V> extends Synchronized.SynchronizedCollection<Collection<V>> {
      private static final long serialVersionUID = 0L;

      SynchronizedAsMapValues(Collection<Collection<V>> delegate, @Nullable Object mutex) {
         super(delegate, mutex);
      }

      @Override
      public Iterator<Collection<V>> iterator() {
         final Iterator<Collection<V>> iterator = super.iterator();
         return new ForwardingIterator<Collection<V>>() {
            @Override
            protected Iterator<Collection<V>> delegate() {
               return iterator;
            }

            public Collection<V> next() {
               return Synchronized.typePreservingCollection((Collection<V>)super.next(), SynchronizedAsMapValues.this.mutex);
            }
         };
      }
   }

   @VisibleForTesting
   static class SynchronizedBiMap<K, V> extends Synchronized.SynchronizedMap<K, V> implements BiMap<K, V>, Serializable {
      private transient Set<V> valueSet;
      private transient BiMap<V, K> inverse;
      private static final long serialVersionUID = 0L;

      private SynchronizedBiMap(BiMap<K, V> delegate, @Nullable Object mutex, @Nullable BiMap<V, K> inverse) {
         super(delegate, mutex);
         this.inverse = inverse;
      }

      BiMap<K, V> delegate() {
         return (BiMap<K, V>)super.delegate();
      }

      @Override
      public Set<V> values() {
         synchronized (this.mutex) {
            if (this.valueSet == null) {
               this.valueSet = Synchronized.set(this.delegate().values(), this.mutex);
            }

            return this.valueSet;
         }
      }

      @Override
      public V forcePut(K key, V value) {
         synchronized (this.mutex) {
            return this.delegate().forcePut(key, value);
         }
      }

      @Override
      public BiMap<V, K> inverse() {
         synchronized (this.mutex) {
            if (this.inverse == null) {
               this.inverse = new Synchronized.SynchronizedBiMap<>(this.delegate().inverse(), this.mutex, this);
            }

            return this.inverse;
         }
      }
   }

   @VisibleForTesting
   static class SynchronizedCollection<E> extends Synchronized.SynchronizedObject implements Collection<E> {
      private static final long serialVersionUID = 0L;

      private SynchronizedCollection(Collection<E> delegate, @Nullable Object mutex) {
         super(delegate, mutex);
      }

      Collection<E> delegate() {
         return (Collection<E>)super.delegate();
      }

      @Override
      public boolean add(E e) {
         synchronized (this.mutex) {
            return this.delegate().add(e);
         }
      }

      @Override
      public boolean addAll(Collection<? extends E> c) {
         synchronized (this.mutex) {
            return this.delegate().addAll(c);
         }
      }

      @Override
      public void clear() {
         synchronized (this.mutex) {
            this.delegate().clear();
         }
      }

      @Override
      public boolean contains(Object o) {
         synchronized (this.mutex) {
            return this.delegate().contains(o);
         }
      }

      @Override
      public boolean containsAll(Collection<?> c) {
         synchronized (this.mutex) {
            return this.delegate().containsAll(c);
         }
      }

      @Override
      public boolean isEmpty() {
         synchronized (this.mutex) {
            return this.delegate().isEmpty();
         }
      }

      @Override
      public Iterator<E> iterator() {
         return this.delegate().iterator();
      }

      @Override
      public boolean remove(Object o) {
         synchronized (this.mutex) {
            return this.delegate().remove(o);
         }
      }

      @Override
      public boolean removeAll(Collection<?> c) {
         synchronized (this.mutex) {
            return this.delegate().removeAll(c);
         }
      }

      @Override
      public boolean retainAll(Collection<?> c) {
         synchronized (this.mutex) {
            return this.delegate().retainAll(c);
         }
      }

      @Override
      public int size() {
         synchronized (this.mutex) {
            return this.delegate().size();
         }
      }

      @Override
      public Object[] toArray() {
         synchronized (this.mutex) {
            return this.delegate().toArray();
         }
      }

      @Override
      public <T> T[] toArray(T[] a) {
         synchronized (this.mutex) {
            return (T[])this.delegate().toArray(a);
         }
      }
   }

   @GwtIncompatible("Deque")
   private static final class SynchronizedDeque<E> extends Synchronized.SynchronizedQueue<E> implements Deque<E> {
      private static final long serialVersionUID = 0L;

      SynchronizedDeque(Deque<E> delegate, @Nullable Object mutex) {
         super(delegate, mutex);
      }

      Deque<E> delegate() {
         return (Deque<E>)super.delegate();
      }

      @Override
      public void addFirst(E e) {
         synchronized (this.mutex) {
            this.delegate().addFirst(e);
         }
      }

      @Override
      public void addLast(E e) {
         synchronized (this.mutex) {
            this.delegate().addLast(e);
         }
      }

      @Override
      public boolean offerFirst(E e) {
         synchronized (this.mutex) {
            return this.delegate().offerFirst(e);
         }
      }

      @Override
      public boolean offerLast(E e) {
         synchronized (this.mutex) {
            return this.delegate().offerLast(e);
         }
      }

      @Override
      public E removeFirst() {
         synchronized (this.mutex) {
            return this.delegate().removeFirst();
         }
      }

      @Override
      public E removeLast() {
         synchronized (this.mutex) {
            return this.delegate().removeLast();
         }
      }

      @Override
      public E pollFirst() {
         synchronized (this.mutex) {
            return this.delegate().pollFirst();
         }
      }

      @Override
      public E pollLast() {
         synchronized (this.mutex) {
            return this.delegate().pollLast();
         }
      }

      @Override
      public E getFirst() {
         synchronized (this.mutex) {
            return this.delegate().getFirst();
         }
      }

      @Override
      public E getLast() {
         synchronized (this.mutex) {
            return this.delegate().getLast();
         }
      }

      @Override
      public E peekFirst() {
         synchronized (this.mutex) {
            return this.delegate().peekFirst();
         }
      }

      @Override
      public E peekLast() {
         synchronized (this.mutex) {
            return this.delegate().peekLast();
         }
      }

      @Override
      public boolean removeFirstOccurrence(Object o) {
         synchronized (this.mutex) {
            return this.delegate().removeFirstOccurrence(o);
         }
      }

      @Override
      public boolean removeLastOccurrence(Object o) {
         synchronized (this.mutex) {
            return this.delegate().removeLastOccurrence(o);
         }
      }

      @Override
      public void push(E e) {
         synchronized (this.mutex) {
            this.delegate().push(e);
         }
      }

      @Override
      public E pop() {
         synchronized (this.mutex) {
            return this.delegate().pop();
         }
      }

      @Override
      public Iterator<E> descendingIterator() {
         synchronized (this.mutex) {
            return this.delegate().descendingIterator();
         }
      }
   }

   @GwtIncompatible("works but is needed only for NavigableMap")
   private static class SynchronizedEntry<K, V> extends Synchronized.SynchronizedObject implements Entry<K, V> {
      private static final long serialVersionUID = 0L;

      SynchronizedEntry(Entry<K, V> delegate, @Nullable Object mutex) {
         super(delegate, mutex);
      }

      Entry<K, V> delegate() {
         return (Entry<K, V>)super.delegate();
      }

      @Override
      public boolean equals(Object obj) {
         synchronized (this.mutex) {
            return this.delegate().equals(obj);
         }
      }

      @Override
      public int hashCode() {
         synchronized (this.mutex) {
            return this.delegate().hashCode();
         }
      }

      @Override
      public K getKey() {
         synchronized (this.mutex) {
            return this.delegate().getKey();
         }
      }

      @Override
      public V getValue() {
         synchronized (this.mutex) {
            return this.delegate().getValue();
         }
      }

      @Override
      public V setValue(V value) {
         synchronized (this.mutex) {
            return this.delegate().setValue(value);
         }
      }
   }

   private static class SynchronizedList<E> extends Synchronized.SynchronizedCollection<E> implements List<E> {
      private static final long serialVersionUID = 0L;

      SynchronizedList(List<E> delegate, @Nullable Object mutex) {
         super(delegate, mutex);
      }

      List<E> delegate() {
         return (List<E>)super.delegate();
      }

      @Override
      public void add(int index, E element) {
         synchronized (this.mutex) {
            this.delegate().add(index, element);
         }
      }

      @Override
      public boolean addAll(int index, Collection<? extends E> c) {
         synchronized (this.mutex) {
            return this.delegate().addAll(index, c);
         }
      }

      @Override
      public E get(int index) {
         synchronized (this.mutex) {
            return this.delegate().get(index);
         }
      }

      @Override
      public int indexOf(Object o) {
         synchronized (this.mutex) {
            return this.delegate().indexOf(o);
         }
      }

      @Override
      public int lastIndexOf(Object o) {
         synchronized (this.mutex) {
            return this.delegate().lastIndexOf(o);
         }
      }

      @Override
      public ListIterator<E> listIterator() {
         return this.delegate().listIterator();
      }

      @Override
      public ListIterator<E> listIterator(int index) {
         return this.delegate().listIterator(index);
      }

      @Override
      public E remove(int index) {
         synchronized (this.mutex) {
            return this.delegate().remove(index);
         }
      }

      @Override
      public E set(int index, E element) {
         synchronized (this.mutex) {
            return this.delegate().set(index, element);
         }
      }

      @Override
      public List<E> subList(int fromIndex, int toIndex) {
         synchronized (this.mutex) {
            return Synchronized.list(this.delegate().subList(fromIndex, toIndex), this.mutex);
         }
      }

      @Override
      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else {
            synchronized (this.mutex) {
               return this.delegate().equals(o);
            }
         }
      }

      @Override
      public int hashCode() {
         synchronized (this.mutex) {
            return this.delegate().hashCode();
         }
      }
   }

   private static class SynchronizedListMultimap<K, V> extends Synchronized.SynchronizedMultimap<K, V> implements ListMultimap<K, V> {
      private static final long serialVersionUID = 0L;

      SynchronizedListMultimap(ListMultimap<K, V> delegate, @Nullable Object mutex) {
         super(delegate, mutex);
      }

      ListMultimap<K, V> delegate() {
         return (ListMultimap<K, V>)super.delegate();
      }

      @Override
      public List<V> get(K key) {
         synchronized (this.mutex) {
            return Synchronized.list(this.delegate().get(key), this.mutex);
         }
      }

      @Override
      public List<V> removeAll(Object key) {
         synchronized (this.mutex) {
            return this.delegate().removeAll(key);
         }
      }

      @Override
      public List<V> replaceValues(K key, Iterable<? extends V> values) {
         synchronized (this.mutex) {
            return this.delegate().replaceValues(key, values);
         }
      }
   }

   private static class SynchronizedMap<K, V> extends Synchronized.SynchronizedObject implements Map<K, V> {
      transient Set<K> keySet;
      transient Collection<V> values;
      transient Set<Entry<K, V>> entrySet;
      private static final long serialVersionUID = 0L;

      SynchronizedMap(Map<K, V> delegate, @Nullable Object mutex) {
         super(delegate, mutex);
      }

      Map<K, V> delegate() {
         return (Map<K, V>)super.delegate();
      }

      @Override
      public void clear() {
         synchronized (this.mutex) {
            this.delegate().clear();
         }
      }

      @Override
      public boolean containsKey(Object key) {
         synchronized (this.mutex) {
            return this.delegate().containsKey(key);
         }
      }

      @Override
      public boolean containsValue(Object value) {
         synchronized (this.mutex) {
            return this.delegate().containsValue(value);
         }
      }

      @Override
      public Set<Entry<K, V>> entrySet() {
         synchronized (this.mutex) {
            if (this.entrySet == null) {
               this.entrySet = Synchronized.set(this.delegate().entrySet(), this.mutex);
            }

            return this.entrySet;
         }
      }

      @Override
      public V get(Object key) {
         synchronized (this.mutex) {
            return this.delegate().get(key);
         }
      }

      @Override
      public boolean isEmpty() {
         synchronized (this.mutex) {
            return this.delegate().isEmpty();
         }
      }

      @Override
      public Set<K> keySet() {
         synchronized (this.mutex) {
            if (this.keySet == null) {
               this.keySet = Synchronized.set(this.delegate().keySet(), this.mutex);
            }

            return this.keySet;
         }
      }

      @Override
      public V put(K key, V value) {
         synchronized (this.mutex) {
            return this.delegate().put(key, value);
         }
      }

      @Override
      public void putAll(Map<? extends K, ? extends V> map) {
         synchronized (this.mutex) {
            this.delegate().putAll(map);
         }
      }

      @Override
      public V remove(Object key) {
         synchronized (this.mutex) {
            return this.delegate().remove(key);
         }
      }

      @Override
      public int size() {
         synchronized (this.mutex) {
            return this.delegate().size();
         }
      }

      @Override
      public Collection<V> values() {
         synchronized (this.mutex) {
            if (this.values == null) {
               this.values = Synchronized.collection(this.delegate().values(), this.mutex);
            }

            return this.values;
         }
      }

      @Override
      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else {
            synchronized (this.mutex) {
               return this.delegate().equals(o);
            }
         }
      }

      @Override
      public int hashCode() {
         synchronized (this.mutex) {
            return this.delegate().hashCode();
         }
      }
   }

   private static class SynchronizedMultimap<K, V> extends Synchronized.SynchronizedObject implements Multimap<K, V> {
      transient Set<K> keySet;
      transient Collection<V> valuesCollection;
      transient Collection<Entry<K, V>> entries;
      transient Map<K, Collection<V>> asMap;
      transient Multiset<K> keys;
      private static final long serialVersionUID = 0L;

      Multimap<K, V> delegate() {
         return (Multimap<K, V>)super.delegate();
      }

      SynchronizedMultimap(Multimap<K, V> delegate, @Nullable Object mutex) {
         super(delegate, mutex);
      }

      @Override
      public int size() {
         synchronized (this.mutex) {
            return this.delegate().size();
         }
      }

      @Override
      public boolean isEmpty() {
         synchronized (this.mutex) {
            return this.delegate().isEmpty();
         }
      }

      @Override
      public boolean containsKey(Object key) {
         synchronized (this.mutex) {
            return this.delegate().containsKey(key);
         }
      }

      @Override
      public boolean containsValue(Object value) {
         synchronized (this.mutex) {
            return this.delegate().containsValue(value);
         }
      }

      @Override
      public boolean containsEntry(Object key, Object value) {
         synchronized (this.mutex) {
            return this.delegate().containsEntry(key, value);
         }
      }

      @Override
      public Collection<V> get(K key) {
         synchronized (this.mutex) {
            return Synchronized.typePreservingCollection(this.delegate().get(key), this.mutex);
         }
      }

      @Override
      public boolean put(K key, V value) {
         synchronized (this.mutex) {
            return this.delegate().put(key, value);
         }
      }

      @Override
      public boolean putAll(K key, Iterable<? extends V> values) {
         synchronized (this.mutex) {
            return this.delegate().putAll(key, values);
         }
      }

      @Override
      public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
         synchronized (this.mutex) {
            return this.delegate().putAll(multimap);
         }
      }

      @Override
      public Collection<V> replaceValues(K key, Iterable<? extends V> values) {
         synchronized (this.mutex) {
            return this.delegate().replaceValues(key, values);
         }
      }

      @Override
      public boolean remove(Object key, Object value) {
         synchronized (this.mutex) {
            return this.delegate().remove(key, value);
         }
      }

      @Override
      public Collection<V> removeAll(Object key) {
         synchronized (this.mutex) {
            return this.delegate().removeAll(key);
         }
      }

      @Override
      public void clear() {
         synchronized (this.mutex) {
            this.delegate().clear();
         }
      }

      @Override
      public Set<K> keySet() {
         synchronized (this.mutex) {
            if (this.keySet == null) {
               this.keySet = Synchronized.typePreservingSet(this.delegate().keySet(), this.mutex);
            }

            return this.keySet;
         }
      }

      @Override
      public Collection<V> values() {
         synchronized (this.mutex) {
            if (this.valuesCollection == null) {
               this.valuesCollection = Synchronized.collection(this.delegate().values(), this.mutex);
            }

            return this.valuesCollection;
         }
      }

      @Override
      public Collection<Entry<K, V>> entries() {
         synchronized (this.mutex) {
            if (this.entries == null) {
               this.entries = Synchronized.typePreservingCollection(this.delegate().entries(), this.mutex);
            }

            return this.entries;
         }
      }

      @Override
      public Map<K, Collection<V>> asMap() {
         synchronized (this.mutex) {
            if (this.asMap == null) {
               this.asMap = new Synchronized.SynchronizedAsMap<>(this.delegate().asMap(), this.mutex);
            }

            return this.asMap;
         }
      }

      @Override
      public Multiset<K> keys() {
         synchronized (this.mutex) {
            if (this.keys == null) {
               this.keys = Synchronized.multiset(this.delegate().keys(), this.mutex);
            }

            return this.keys;
         }
      }

      @Override
      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else {
            synchronized (this.mutex) {
               return this.delegate().equals(o);
            }
         }
      }

      @Override
      public int hashCode() {
         synchronized (this.mutex) {
            return this.delegate().hashCode();
         }
      }
   }

   private static class SynchronizedMultiset<E> extends Synchronized.SynchronizedCollection<E> implements Multiset<E> {
      transient Set<E> elementSet;
      transient Set<Multiset.Entry<E>> entrySet;
      private static final long serialVersionUID = 0L;

      SynchronizedMultiset(Multiset<E> delegate, @Nullable Object mutex) {
         super(delegate, mutex);
      }

      Multiset<E> delegate() {
         return (Multiset<E>)super.delegate();
      }

      @Override
      public int count(Object o) {
         synchronized (this.mutex) {
            return this.delegate().count(o);
         }
      }

      @Override
      public int add(E e, int n) {
         synchronized (this.mutex) {
            return this.delegate().add(e, n);
         }
      }

      @Override
      public int remove(Object o, int n) {
         synchronized (this.mutex) {
            return this.delegate().remove(o, n);
         }
      }

      @Override
      public int setCount(E element, int count) {
         synchronized (this.mutex) {
            return this.delegate().setCount(element, count);
         }
      }

      @Override
      public boolean setCount(E element, int oldCount, int newCount) {
         synchronized (this.mutex) {
            return this.delegate().setCount(element, oldCount, newCount);
         }
      }

      @Override
      public Set<E> elementSet() {
         synchronized (this.mutex) {
            if (this.elementSet == null) {
               this.elementSet = Synchronized.typePreservingSet(this.delegate().elementSet(), this.mutex);
            }

            return this.elementSet;
         }
      }

      @Override
      public Set<Multiset.Entry<E>> entrySet() {
         synchronized (this.mutex) {
            if (this.entrySet == null) {
               this.entrySet = Synchronized.typePreservingSet(this.delegate().entrySet(), this.mutex);
            }

            return this.entrySet;
         }
      }

      @Override
      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else {
            synchronized (this.mutex) {
               return this.delegate().equals(o);
            }
         }
      }

      @Override
      public int hashCode() {
         synchronized (this.mutex) {
            return this.delegate().hashCode();
         }
      }
   }

   @GwtIncompatible("NavigableMap")
   @VisibleForTesting
   static class SynchronizedNavigableMap<K, V> extends Synchronized.SynchronizedSortedMap<K, V> implements NavigableMap<K, V> {
      transient NavigableSet<K> descendingKeySet;
      transient NavigableMap<K, V> descendingMap;
      transient NavigableSet<K> navigableKeySet;
      private static final long serialVersionUID = 0L;

      SynchronizedNavigableMap(NavigableMap<K, V> delegate, @Nullable Object mutex) {
         super(delegate, mutex);
      }

      NavigableMap<K, V> delegate() {
         return (NavigableMap<K, V>)super.delegate();
      }

      @Override
      public Entry<K, V> ceilingEntry(K key) {
         synchronized (this.mutex) {
            return Synchronized.nullableSynchronizedEntry(this.delegate().ceilingEntry(key), this.mutex);
         }
      }

      @Override
      public K ceilingKey(K key) {
         synchronized (this.mutex) {
            return this.delegate().ceilingKey(key);
         }
      }

      @Override
      public NavigableSet<K> descendingKeySet() {
         synchronized (this.mutex) {
            return this.descendingKeySet == null
               ? (this.descendingKeySet = Synchronized.navigableSet(this.delegate().descendingKeySet(), this.mutex))
               : this.descendingKeySet;
         }
      }

      @Override
      public NavigableMap<K, V> descendingMap() {
         synchronized (this.mutex) {
            return this.descendingMap == null
               ? (this.descendingMap = Synchronized.navigableMap(this.delegate().descendingMap(), this.mutex))
               : this.descendingMap;
         }
      }

      @Override
      public Entry<K, V> firstEntry() {
         synchronized (this.mutex) {
            return Synchronized.nullableSynchronizedEntry(this.delegate().firstEntry(), this.mutex);
         }
      }

      @Override
      public Entry<K, V> floorEntry(K key) {
         synchronized (this.mutex) {
            return Synchronized.nullableSynchronizedEntry(this.delegate().floorEntry(key), this.mutex);
         }
      }

      @Override
      public K floorKey(K key) {
         synchronized (this.mutex) {
            return this.delegate().floorKey(key);
         }
      }

      @Override
      public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
         synchronized (this.mutex) {
            return Synchronized.navigableMap(this.delegate().headMap(toKey, inclusive), this.mutex);
         }
      }

      @Override
      public Entry<K, V> higherEntry(K key) {
         synchronized (this.mutex) {
            return Synchronized.nullableSynchronizedEntry(this.delegate().higherEntry(key), this.mutex);
         }
      }

      @Override
      public K higherKey(K key) {
         synchronized (this.mutex) {
            return this.delegate().higherKey(key);
         }
      }

      @Override
      public Entry<K, V> lastEntry() {
         synchronized (this.mutex) {
            return Synchronized.nullableSynchronizedEntry(this.delegate().lastEntry(), this.mutex);
         }
      }

      @Override
      public Entry<K, V> lowerEntry(K key) {
         synchronized (this.mutex) {
            return Synchronized.nullableSynchronizedEntry(this.delegate().lowerEntry(key), this.mutex);
         }
      }

      @Override
      public K lowerKey(K key) {
         synchronized (this.mutex) {
            return this.delegate().lowerKey(key);
         }
      }

      @Override
      public Set<K> keySet() {
         return this.navigableKeySet();
      }

      @Override
      public NavigableSet<K> navigableKeySet() {
         synchronized (this.mutex) {
            return this.navigableKeySet == null
               ? (this.navigableKeySet = Synchronized.navigableSet(this.delegate().navigableKeySet(), this.mutex))
               : this.navigableKeySet;
         }
      }

      @Override
      public Entry<K, V> pollFirstEntry() {
         synchronized (this.mutex) {
            return Synchronized.nullableSynchronizedEntry(this.delegate().pollFirstEntry(), this.mutex);
         }
      }

      @Override
      public Entry<K, V> pollLastEntry() {
         synchronized (this.mutex) {
            return Synchronized.nullableSynchronizedEntry(this.delegate().pollLastEntry(), this.mutex);
         }
      }

      @Override
      public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
         synchronized (this.mutex) {
            return Synchronized.navigableMap(this.delegate().subMap(fromKey, fromInclusive, toKey, toInclusive), this.mutex);
         }
      }

      @Override
      public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
         synchronized (this.mutex) {
            return Synchronized.navigableMap(this.delegate().tailMap(fromKey, inclusive), this.mutex);
         }
      }

      @Override
      public SortedMap<K, V> headMap(K toKey) {
         return this.headMap(toKey, false);
      }

      @Override
      public SortedMap<K, V> subMap(K fromKey, K toKey) {
         return this.subMap(fromKey, true, toKey, false);
      }

      @Override
      public SortedMap<K, V> tailMap(K fromKey) {
         return this.tailMap(fromKey, true);
      }
   }

   @GwtIncompatible("NavigableSet")
   @VisibleForTesting
   static class SynchronizedNavigableSet<E> extends Synchronized.SynchronizedSortedSet<E> implements NavigableSet<E> {
      transient NavigableSet<E> descendingSet;
      private static final long serialVersionUID = 0L;

      SynchronizedNavigableSet(NavigableSet<E> delegate, @Nullable Object mutex) {
         super(delegate, mutex);
      }

      NavigableSet<E> delegate() {
         return (NavigableSet<E>)super.delegate();
      }

      @Override
      public E ceiling(E e) {
         synchronized (this.mutex) {
            return this.delegate().ceiling(e);
         }
      }

      @Override
      public Iterator<E> descendingIterator() {
         return this.delegate().descendingIterator();
      }

      @Override
      public NavigableSet<E> descendingSet() {
         synchronized (this.mutex) {
            if (this.descendingSet == null) {
               NavigableSet<E> dS = Synchronized.navigableSet(this.delegate().descendingSet(), this.mutex);
               this.descendingSet = dS;
               return dS;
            } else {
               return this.descendingSet;
            }
         }
      }

      @Override
      public E floor(E e) {
         synchronized (this.mutex) {
            return this.delegate().floor(e);
         }
      }

      @Override
      public NavigableSet<E> headSet(E toElement, boolean inclusive) {
         synchronized (this.mutex) {
            return Synchronized.navigableSet(this.delegate().headSet(toElement, inclusive), this.mutex);
         }
      }

      @Override
      public E higher(E e) {
         synchronized (this.mutex) {
            return this.delegate().higher(e);
         }
      }

      @Override
      public E lower(E e) {
         synchronized (this.mutex) {
            return this.delegate().lower(e);
         }
      }

      @Override
      public E pollFirst() {
         synchronized (this.mutex) {
            return this.delegate().pollFirst();
         }
      }

      @Override
      public E pollLast() {
         synchronized (this.mutex) {
            return this.delegate().pollLast();
         }
      }

      @Override
      public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
         synchronized (this.mutex) {
            return Synchronized.navigableSet(this.delegate().subSet(fromElement, fromInclusive, toElement, toInclusive), this.mutex);
         }
      }

      @Override
      public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
         synchronized (this.mutex) {
            return Synchronized.navigableSet(this.delegate().tailSet(fromElement, inclusive), this.mutex);
         }
      }

      @Override
      public SortedSet<E> headSet(E toElement) {
         return this.headSet(toElement, false);
      }

      @Override
      public SortedSet<E> subSet(E fromElement, E toElement) {
         return this.subSet(fromElement, true, toElement, false);
      }

      @Override
      public SortedSet<E> tailSet(E fromElement) {
         return this.tailSet(fromElement, true);
      }
   }

   static class SynchronizedObject implements Serializable {
      final Object delegate;
      final Object mutex;
      @GwtIncompatible("not needed in emulated source")
      private static final long serialVersionUID = 0L;

      SynchronizedObject(Object delegate, @Nullable Object mutex) {
         this.delegate = Preconditions.checkNotNull(delegate);
         this.mutex = mutex == null ? this : mutex;
      }

      Object delegate() {
         return this.delegate;
      }

      @Override
      public String toString() {
         synchronized (this.mutex) {
            return this.delegate.toString();
         }
      }

      @GwtIncompatible("java.io.ObjectOutputStream")
      private void writeObject(ObjectOutputStream stream) throws IOException {
         synchronized (this.mutex) {
            stream.defaultWriteObject();
         }
      }
   }

   private static class SynchronizedQueue<E> extends Synchronized.SynchronizedCollection<E> implements Queue<E> {
      private static final long serialVersionUID = 0L;

      SynchronizedQueue(Queue<E> delegate, @Nullable Object mutex) {
         super(delegate, mutex);
      }

      Queue<E> delegate() {
         return (Queue<E>)super.delegate();
      }

      @Override
      public E element() {
         synchronized (this.mutex) {
            return this.delegate().element();
         }
      }

      @Override
      public boolean offer(E e) {
         synchronized (this.mutex) {
            return this.delegate().offer(e);
         }
      }

      @Override
      public E peek() {
         synchronized (this.mutex) {
            return this.delegate().peek();
         }
      }

      @Override
      public E poll() {
         synchronized (this.mutex) {
            return this.delegate().poll();
         }
      }

      @Override
      public E remove() {
         synchronized (this.mutex) {
            return this.delegate().remove();
         }
      }
   }

   private static class SynchronizedRandomAccessList<E> extends Synchronized.SynchronizedList<E> implements RandomAccess {
      private static final long serialVersionUID = 0L;

      SynchronizedRandomAccessList(List<E> list, @Nullable Object mutex) {
         super(list, mutex);
      }
   }

   static class SynchronizedSet<E> extends Synchronized.SynchronizedCollection<E> implements Set<E> {
      private static final long serialVersionUID = 0L;

      SynchronizedSet(Set<E> delegate, @Nullable Object mutex) {
         super(delegate, mutex);
      }

      Set<E> delegate() {
         return (Set<E>)super.delegate();
      }

      @Override
      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else {
            synchronized (this.mutex) {
               return this.delegate().equals(o);
            }
         }
      }

      @Override
      public int hashCode() {
         synchronized (this.mutex) {
            return this.delegate().hashCode();
         }
      }
   }

   private static class SynchronizedSetMultimap<K, V> extends Synchronized.SynchronizedMultimap<K, V> implements SetMultimap<K, V> {
      transient Set<Entry<K, V>> entrySet;
      private static final long serialVersionUID = 0L;

      SynchronizedSetMultimap(SetMultimap<K, V> delegate, @Nullable Object mutex) {
         super(delegate, mutex);
      }

      SetMultimap<K, V> delegate() {
         return (SetMultimap<K, V>)super.delegate();
      }

      @Override
      public Set<V> get(K key) {
         synchronized (this.mutex) {
            return Synchronized.set(this.delegate().get(key), this.mutex);
         }
      }

      @Override
      public Set<V> removeAll(Object key) {
         synchronized (this.mutex) {
            return this.delegate().removeAll(key);
         }
      }

      @Override
      public Set<V> replaceValues(K key, Iterable<? extends V> values) {
         synchronized (this.mutex) {
            return this.delegate().replaceValues(key, values);
         }
      }

      @Override
      public Set<Entry<K, V>> entries() {
         synchronized (this.mutex) {
            if (this.entrySet == null) {
               this.entrySet = Synchronized.set(this.delegate().entries(), this.mutex);
            }

            return this.entrySet;
         }
      }
   }

   static class SynchronizedSortedMap<K, V> extends Synchronized.SynchronizedMap<K, V> implements SortedMap<K, V> {
      private static final long serialVersionUID = 0L;

      SynchronizedSortedMap(SortedMap<K, V> delegate, @Nullable Object mutex) {
         super(delegate, mutex);
      }

      SortedMap<K, V> delegate() {
         return (SortedMap<K, V>)super.delegate();
      }

      @Override
      public Comparator<? super K> comparator() {
         synchronized (this.mutex) {
            return this.delegate().comparator();
         }
      }

      @Override
      public K firstKey() {
         synchronized (this.mutex) {
            return this.delegate().firstKey();
         }
      }

      @Override
      public SortedMap<K, V> headMap(K toKey) {
         synchronized (this.mutex) {
            return Synchronized.sortedMap(this.delegate().headMap(toKey), this.mutex);
         }
      }

      @Override
      public K lastKey() {
         synchronized (this.mutex) {
            return this.delegate().lastKey();
         }
      }

      @Override
      public SortedMap<K, V> subMap(K fromKey, K toKey) {
         synchronized (this.mutex) {
            return Synchronized.sortedMap(this.delegate().subMap(fromKey, toKey), this.mutex);
         }
      }

      @Override
      public SortedMap<K, V> tailMap(K fromKey) {
         synchronized (this.mutex) {
            return Synchronized.sortedMap(this.delegate().tailMap(fromKey), this.mutex);
         }
      }
   }

   static class SynchronizedSortedSet<E> extends Synchronized.SynchronizedSet<E> implements SortedSet<E> {
      private static final long serialVersionUID = 0L;

      SynchronizedSortedSet(SortedSet<E> delegate, @Nullable Object mutex) {
         super(delegate, mutex);
      }

      SortedSet<E> delegate() {
         return (SortedSet<E>)super.delegate();
      }

      @Override
      public Comparator<? super E> comparator() {
         synchronized (this.mutex) {
            return this.delegate().comparator();
         }
      }

      @Override
      public SortedSet<E> subSet(E fromElement, E toElement) {
         synchronized (this.mutex) {
            return Synchronized.sortedSet(this.delegate().subSet(fromElement, toElement), this.mutex);
         }
      }

      @Override
      public SortedSet<E> headSet(E toElement) {
         synchronized (this.mutex) {
            return Synchronized.sortedSet(this.delegate().headSet(toElement), this.mutex);
         }
      }

      @Override
      public SortedSet<E> tailSet(E fromElement) {
         synchronized (this.mutex) {
            return Synchronized.sortedSet(this.delegate().tailSet(fromElement), this.mutex);
         }
      }

      @Override
      public E first() {
         synchronized (this.mutex) {
            return this.delegate().first();
         }
      }

      @Override
      public E last() {
         synchronized (this.mutex) {
            return this.delegate().last();
         }
      }
   }

   private static class SynchronizedSortedSetMultimap<K, V> extends Synchronized.SynchronizedSetMultimap<K, V> implements SortedSetMultimap<K, V> {
      private static final long serialVersionUID = 0L;

      SynchronizedSortedSetMultimap(SortedSetMultimap<K, V> delegate, @Nullable Object mutex) {
         super(delegate, mutex);
      }

      SortedSetMultimap<K, V> delegate() {
         return (SortedSetMultimap<K, V>)super.delegate();
      }

      @Override
      public SortedSet<V> get(K key) {
         synchronized (this.mutex) {
            return Synchronized.sortedSet(this.delegate().get(key), this.mutex);
         }
      }

      @Override
      public SortedSet<V> removeAll(Object key) {
         synchronized (this.mutex) {
            return this.delegate().removeAll(key);
         }
      }

      @Override
      public SortedSet<V> replaceValues(K key, Iterable<? extends V> values) {
         synchronized (this.mutex) {
            return this.delegate().replaceValues(key, values);
         }
      }

      @Override
      public Comparator<? super V> valueComparator() {
         synchronized (this.mutex) {
            return this.delegate().valueComparator();
         }
      }
   }
}
