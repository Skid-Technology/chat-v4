package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
abstract class AbstractMapBasedMultimap<K, V> extends AbstractMultimap<K, V> implements Serializable {
   private transient Map<K, Collection<V>> map;
   private transient int totalSize;
   private static final long serialVersionUID = 2447537837011683357L;

   protected AbstractMapBasedMultimap(Map<K, Collection<V>> map) {
      Preconditions.checkArgument(map.isEmpty());
      this.map = map;
   }

   final void setMap(Map<K, Collection<V>> map) {
      this.map = map;
      this.totalSize = 0;

      for (Collection<V> values : map.values()) {
         Preconditions.checkArgument(!values.isEmpty());
         this.totalSize = this.totalSize + values.size();
      }
   }

   Collection<V> createUnmodifiableEmptyCollection() {
      return this.unmodifiableCollectionSubclass(this.createCollection());
   }

   abstract Collection<V> createCollection();

   Collection<V> createCollection(@Nullable K key) {
      return this.createCollection();
   }

   Map<K, Collection<V>> backingMap() {
      return this.map;
   }

   @Override
   public int size() {
      return this.totalSize;
   }

   @Override
   public boolean containsKey(@Nullable Object key) {
      return this.map.containsKey(key);
   }

   @Override
   public boolean put(@Nullable K key, @Nullable V value) {
      Collection<V> collection = this.map.get(key);
      if (collection == null) {
         collection = this.createCollection(key);
         if (collection.add(value)) {
            this.totalSize++;
            this.map.put(key, collection);
            return true;
         } else {
            throw new AssertionError("New Collection violated the Collection spec");
         }
      } else if (collection.add(value)) {
         this.totalSize++;
         return true;
      } else {
         return false;
      }
   }

   private Collection<V> getOrCreateCollection(@Nullable K key) {
      Collection<V> collection = this.map.get(key);
      if (collection == null) {
         collection = this.createCollection(key);
         this.map.put(key, collection);
      }

      return collection;
   }

   @Override
   public Collection<V> replaceValues(@Nullable K key, Iterable<? extends V> values) {
      Iterator<? extends V> iterator = values.iterator();
      if (!iterator.hasNext()) {
         return this.removeAll(key);
      } else {
         Collection<V> collection = this.getOrCreateCollection(key);
         Collection<V> oldValues = this.createCollection();
         oldValues.addAll(collection);
         this.totalSize = this.totalSize - collection.size();
         collection.clear();

         while (iterator.hasNext()) {
            if (collection.add((V)iterator.next())) {
               this.totalSize++;
            }
         }

         return this.unmodifiableCollectionSubclass(oldValues);
      }
   }

   @Override
   public Collection<V> removeAll(@Nullable Object key) {
      Collection<V> collection = this.map.remove(key);
      if (collection == null) {
         return this.createUnmodifiableEmptyCollection();
      } else {
         Collection<V> output = this.createCollection();
         output.addAll(collection);
         this.totalSize = this.totalSize - collection.size();
         collection.clear();
         return this.unmodifiableCollectionSubclass(output);
      }
   }

   Collection<V> unmodifiableCollectionSubclass(Collection<V> collection) {
      if (collection instanceof SortedSet) {
         return Collections.unmodifiableSortedSet((SortedSet<V>)collection);
      } else if (collection instanceof Set) {
         return Collections.unmodifiableSet((Set<? extends V>)collection);
      } else {
         return (Collection<V>)(collection instanceof List
            ? Collections.unmodifiableList((List<? extends V>)collection)
            : Collections.unmodifiableCollection(collection));
      }
   }

   @Override
   public void clear() {
      for (Collection<V> collection : this.map.values()) {
         collection.clear();
      }

      this.map.clear();
      this.totalSize = 0;
   }

   @Override
   public Collection<V> get(@Nullable K key) {
      Collection<V> collection = this.map.get(key);
      if (collection == null) {
         collection = this.createCollection(key);
      }

      return this.wrapCollection(key, collection);
   }

   Collection<V> wrapCollection(@Nullable K key, Collection<V> collection) {
      if (collection instanceof SortedSet) {
         return new AbstractMapBasedMultimap.WrappedSortedSet(key, (SortedSet<V>)collection, null);
      } else if (collection instanceof Set) {
         return new AbstractMapBasedMultimap.WrappedSet(key, (Set<V>)collection);
      } else {
         return (Collection<V>)(collection instanceof List
            ? this.wrapList(key, (List<V>)collection, null)
            : new AbstractMapBasedMultimap.WrappedCollection(key, collection, null));
      }
   }

   private List<V> wrapList(@Nullable K key, List<V> list, @Nullable AbstractMapBasedMultimap<K, V>.WrappedCollection ancestor) {
      return (List<V>)(list instanceof RandomAccess
         ? new AbstractMapBasedMultimap.RandomAccessWrappedList(key, list, ancestor)
         : new AbstractMapBasedMultimap.WrappedList(key, list, ancestor));
   }

   private Iterator<V> iteratorOrListIterator(Collection<V> collection) {
      return (Iterator<V>)(collection instanceof List ? ((List)collection).listIterator() : collection.iterator());
   }

   @Override
   Set<K> createKeySet() {
      return (Set<K>)(this.map instanceof SortedMap
         ? new AbstractMapBasedMultimap.SortedKeySet((SortedMap<K, Collection<V>>)this.map)
         : new AbstractMapBasedMultimap.KeySet(this.map));
   }

   private int removeValuesForKey(Object key) {
      Collection<V> collection = Maps.safeRemove(this.map, key);
      int count = 0;
      if (collection != null) {
         count = collection.size();
         collection.clear();
         this.totalSize -= count;
      }

      return count;
   }

   @Override
   public Collection<V> values() {
      return super.values();
   }

   @Override
   Iterator<V> valueIterator() {
      return new AbstractMapBasedMultimap<K, V>.Itr<V>() {
         @Override
         V output(K key, V value) {
            return value;
         }
      };
   }

   @Override
   public Collection<Entry<K, V>> entries() {
      return super.entries();
   }

   @Override
   Iterator<Entry<K, V>> entryIterator() {
      return new AbstractMapBasedMultimap<K, V>.Itr<Entry<K, V>>() {
         Entry<K, V> output(K key, V value) {
            return Maps.immutableEntry(key, value);
         }
      };
   }

   @Override
   Map<K, Collection<V>> createAsMap() {
      return (Map<K, Collection<V>>)(this.map instanceof SortedMap
         ? new AbstractMapBasedMultimap.SortedAsMap((SortedMap<K, Collection<V>>)this.map)
         : new AbstractMapBasedMultimap.AsMap(this.map));
   }

   private class AsMap extends Maps.ImprovedAbstractMap<K, Collection<V>> {
      final transient Map<K, Collection<V>> submap;

      AsMap(Map<K, Collection<V>> submap) {
         this.submap = submap;
      }

      @Override
      protected Set<Entry<K, Collection<V>>> createEntrySet() {
         return new AbstractMapBasedMultimap.AsMap.AsMapEntries();
      }

      @Override
      public boolean containsKey(Object key) {
         return Maps.safeContainsKey(this.submap, key);
      }

      public Collection<V> get(Object key) {
         Collection<V> collection = Maps.safeGet(this.submap, key);
         return collection == null ? null : AbstractMapBasedMultimap.this.wrapCollection((K)key, collection);
      }

      @Override
      public Set<K> keySet() {
         return AbstractMapBasedMultimap.this.keySet();
      }

      @Override
      public int size() {
         return this.submap.size();
      }

      public Collection<V> remove(Object key) {
         Collection<V> collection = this.submap.remove(key);
         if (collection == null) {
            return null;
         } else {
            Collection<V> output = AbstractMapBasedMultimap.this.createCollection();
            output.addAll(collection);
            AbstractMapBasedMultimap.this.totalSize -= collection.size();
            collection.clear();
            return output;
         }
      }

      @Override
      public boolean equals(@Nullable Object object) {
         return this == object || this.submap.equals(object);
      }

      @Override
      public int hashCode() {
         return this.submap.hashCode();
      }

      @Override
      public String toString() {
         return this.submap.toString();
      }

      @Override
      public void clear() {
         if (this.submap == AbstractMapBasedMultimap.this.map) {
            AbstractMapBasedMultimap.this.clear();
         } else {
            Iterators.clear(new AbstractMapBasedMultimap.AsMap.AsMapIterator());
         }
      }

      Entry<K, Collection<V>> wrapEntry(Entry<K, Collection<V>> entry) {
         K key = entry.getKey();
         return Maps.immutableEntry(key, AbstractMapBasedMultimap.this.wrapCollection(key, entry.getValue()));
      }

      class AsMapEntries extends Maps.EntrySet<K, Collection<V>> {
         @Override
         Map<K, Collection<V>> map() {
            return AsMap.this;
         }

         @Override
         public Iterator<Entry<K, Collection<V>>> iterator() {
            return AsMap.this.new AsMapIterator();
         }

         @Override
         public boolean contains(Object o) {
            return Collections2.safeContains(AsMap.this.submap.entrySet(), o);
         }

         @Override
         public boolean remove(Object o) {
            if (!this.contains(o)) {
               return false;
            } else {
               Entry<?, ?> entry = (Entry<?, ?>)o;
               AbstractMapBasedMultimap.this.removeValuesForKey(entry.getKey());
               return true;
            }
         }
      }

      class AsMapIterator implements Iterator<Entry<K, Collection<V>>> {
         final Iterator<Entry<K, Collection<V>>> delegateIterator = AsMap.this.submap.entrySet().iterator();
         Collection<V> collection;

         @Override
         public boolean hasNext() {
            return this.delegateIterator.hasNext();
         }

         public Entry<K, Collection<V>> next() {
            Entry<K, Collection<V>> entry = this.delegateIterator.next();
            this.collection = entry.getValue();
            return AsMap.this.wrapEntry(entry);
         }

         @Override
         public void remove() {
            this.delegateIterator.remove();
            AbstractMapBasedMultimap.this.totalSize -= this.collection.size();
            this.collection.clear();
         }
      }
   }

   private abstract class Itr<T> implements Iterator<T> {
      final Iterator<Entry<K, Collection<V>>> keyIterator = AbstractMapBasedMultimap.this.map.entrySet().iterator();
      K key = (K)null;
      Collection<V> collection = null;
      Iterator<V> valueIterator = Iterators.emptyModifiableIterator();

      Itr() {
      }

      abstract T output(K var1, V var2);

      @Override
      public boolean hasNext() {
         return this.keyIterator.hasNext() || this.valueIterator.hasNext();
      }

      @Override
      public T next() {
         if (!this.valueIterator.hasNext()) {
            Entry<K, Collection<V>> mapEntry = this.keyIterator.next();
            this.key = mapEntry.getKey();
            this.collection = mapEntry.getValue();
            this.valueIterator = this.collection.iterator();
         }

         return this.output((V)this.key, this.valueIterator.next());
      }

      @Override
      public void remove() {
         this.valueIterator.remove();
         if (this.collection.isEmpty()) {
            this.keyIterator.remove();
         }

         AbstractMapBasedMultimap.this.totalSize--;
      }
   }

   private class KeySet extends Maps.KeySet<K, Collection<V>> {
      KeySet(Map<K, Collection<V>> subMap) {
         super(subMap);
      }

      @Override
      public Iterator<K> iterator() {
         final Iterator<Entry<K, Collection<V>>> entryIterator = this.map().entrySet().iterator();
         return new Iterator<K>() {
            Entry<K, Collection<V>> entry;

            @Override
            public boolean hasNext() {
               return entryIterator.hasNext();
            }

            @Override
            public K next() {
               this.entry = entryIterator.next();
               return this.entry.getKey();
            }

            @Override
            public void remove() {
               CollectPreconditions.checkRemove(this.entry != null);
               Collection<V> collection = this.entry.getValue();
               entryIterator.remove();
               AbstractMapBasedMultimap.this.totalSize -= collection.size();
               collection.clear();
            }
         };
      }

      @Override
      public boolean remove(Object key) {
         int count = 0;
         Collection<V> collection = (Collection<V>)this.map().remove(key);
         if (collection != null) {
            count = collection.size();
            collection.clear();
            AbstractMapBasedMultimap.this.totalSize -= count;
         }

         return count > 0;
      }

      @Override
      public void clear() {
         Iterators.clear(this.iterator());
      }

      @Override
      public boolean containsAll(Collection<?> c) {
         return this.map().keySet().containsAll(c);
      }

      @Override
      public boolean equals(@Nullable Object object) {
         return this == object || this.map().keySet().equals(object);
      }

      @Override
      public int hashCode() {
         return this.map().keySet().hashCode();
      }
   }

   @GwtIncompatible("NavigableAsMap")
   class NavigableAsMap extends AbstractMapBasedMultimap<K, V>.SortedAsMap implements NavigableMap<K, Collection<V>> {
      NavigableAsMap(NavigableMap<K, Collection<V>> submap) {
         super(submap);
      }

      NavigableMap<K, Collection<V>> sortedMap() {
         return (NavigableMap<K, Collection<V>>)super.sortedMap();
      }

      @Override
      public Entry<K, Collection<V>> lowerEntry(K key) {
         Entry<K, Collection<V>> entry = this.sortedMap().lowerEntry(key);
         return entry == null ? null : this.wrapEntry(entry);
      }

      @Override
      public K lowerKey(K key) {
         return (K)this.sortedMap().lowerKey(key);
      }

      @Override
      public Entry<K, Collection<V>> floorEntry(K key) {
         Entry<K, Collection<V>> entry = this.sortedMap().floorEntry(key);
         return entry == null ? null : this.wrapEntry(entry);
      }

      @Override
      public K floorKey(K key) {
         return (K)this.sortedMap().floorKey(key);
      }

      @Override
      public Entry<K, Collection<V>> ceilingEntry(K key) {
         Entry<K, Collection<V>> entry = this.sortedMap().ceilingEntry(key);
         return entry == null ? null : this.wrapEntry(entry);
      }

      @Override
      public K ceilingKey(K key) {
         return (K)this.sortedMap().ceilingKey(key);
      }

      @Override
      public Entry<K, Collection<V>> higherEntry(K key) {
         Entry<K, Collection<V>> entry = this.sortedMap().higherEntry(key);
         return entry == null ? null : this.wrapEntry(entry);
      }

      @Override
      public K higherKey(K key) {
         return (K)this.sortedMap().higherKey(key);
      }

      @Override
      public Entry<K, Collection<V>> firstEntry() {
         Entry<K, Collection<V>> entry = this.sortedMap().firstEntry();
         return entry == null ? null : this.wrapEntry(entry);
      }

      @Override
      public Entry<K, Collection<V>> lastEntry() {
         Entry<K, Collection<V>> entry = this.sortedMap().lastEntry();
         return entry == null ? null : this.wrapEntry(entry);
      }

      @Override
      public Entry<K, Collection<V>> pollFirstEntry() {
         return this.pollAsMapEntry(this.entrySet().iterator());
      }

      @Override
      public Entry<K, Collection<V>> pollLastEntry() {
         return this.pollAsMapEntry(this.descendingMap().entrySet().iterator());
      }

      Entry<K, Collection<V>> pollAsMapEntry(Iterator<Entry<K, Collection<V>>> entryIterator) {
         if (!entryIterator.hasNext()) {
            return null;
         } else {
            Entry<K, Collection<V>> entry = entryIterator.next();
            Collection<V> output = AbstractMapBasedMultimap.this.createCollection();
            output.addAll(entry.getValue());
            entryIterator.remove();
            return Maps.immutableEntry(entry.getKey(), AbstractMapBasedMultimap.this.unmodifiableCollectionSubclass(output));
         }
      }

      @Override
      public NavigableMap<K, Collection<V>> descendingMap() {
         return AbstractMapBasedMultimap.this.new NavigableAsMap(this.sortedMap().descendingMap());
      }

      public NavigableSet<K> keySet() {
         return (NavigableSet<K>)super.keySet();
      }

      NavigableSet<K> createKeySet() {
         return AbstractMapBasedMultimap.this.new NavigableKeySet(this.sortedMap());
      }

      @Override
      public NavigableSet<K> navigableKeySet() {
         return this.keySet();
      }

      @Override
      public NavigableSet<K> descendingKeySet() {
         return this.descendingMap().navigableKeySet();
      }

      public NavigableMap<K, Collection<V>> subMap(K fromKey, K toKey) {
         return (NavigableMap<K, Collection<V>>)this.subMap((boolean)fromKey, true, (boolean)toKey, false);
      }

      @Override
      public NavigableMap<K, Collection<V>> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
         return AbstractMapBasedMultimap.this.new NavigableAsMap(this.sortedMap().subMap(fromKey, fromInclusive, toKey, toInclusive));
      }

      public NavigableMap<K, Collection<V>> headMap(K toKey) {
         return (NavigableMap<K, Collection<V>>)this.headMap((boolean)toKey, false);
      }

      @Override
      public NavigableMap<K, Collection<V>> headMap(K toKey, boolean inclusive) {
         return AbstractMapBasedMultimap.this.new NavigableAsMap(this.sortedMap().headMap(toKey, inclusive));
      }

      public NavigableMap<K, Collection<V>> tailMap(K fromKey) {
         return (NavigableMap<K, Collection<V>>)this.tailMap((boolean)fromKey, true);
      }

      @Override
      public NavigableMap<K, Collection<V>> tailMap(K fromKey, boolean inclusive) {
         return AbstractMapBasedMultimap.this.new NavigableAsMap(this.sortedMap().tailMap(fromKey, inclusive));
      }
   }

   @GwtIncompatible("NavigableSet")
   class NavigableKeySet extends AbstractMapBasedMultimap<K, V>.SortedKeySet implements NavigableSet<K> {
      NavigableKeySet(NavigableMap<K, Collection<V>> subMap) {
         super(subMap);
      }

      NavigableMap<K, Collection<V>> sortedMap() {
         return (NavigableMap<K, Collection<V>>)super.sortedMap();
      }

      @Override
      public K lower(K k) {
         return (K)this.sortedMap().lowerKey(k);
      }

      @Override
      public K floor(K k) {
         return (K)this.sortedMap().floorKey(k);
      }

      @Override
      public K ceiling(K k) {
         return (K)this.sortedMap().ceilingKey(k);
      }

      @Override
      public K higher(K k) {
         return (K)this.sortedMap().higherKey(k);
      }

      @Override
      public K pollFirst() {
         return Iterators.pollNext(this.iterator());
      }

      @Override
      public K pollLast() {
         return Iterators.pollNext(this.descendingIterator());
      }

      @Override
      public NavigableSet<K> descendingSet() {
         return AbstractMapBasedMultimap.this.new NavigableKeySet(this.sortedMap().descendingMap());
      }

      @Override
      public Iterator<K> descendingIterator() {
         return this.descendingSet().iterator();
      }

      public NavigableSet<K> headSet(K toElement) {
         return (NavigableSet<K>)this.headSet((boolean)toElement, false);
      }

      @Override
      public NavigableSet<K> headSet(K toElement, boolean inclusive) {
         return AbstractMapBasedMultimap.this.new NavigableKeySet(this.sortedMap().headMap(toElement, inclusive));
      }

      public NavigableSet<K> subSet(K fromElement, K toElement) {
         return (NavigableSet<K>)this.subSet((boolean)fromElement, true, (boolean)toElement, false);
      }

      @Override
      public NavigableSet<K> subSet(K fromElement, boolean fromInclusive, K toElement, boolean toInclusive) {
         return AbstractMapBasedMultimap.this.new NavigableKeySet(this.sortedMap().subMap(fromElement, fromInclusive, toElement, toInclusive));
      }

      public NavigableSet<K> tailSet(K fromElement) {
         return (NavigableSet<K>)this.tailSet((boolean)fromElement, true);
      }

      @Override
      public NavigableSet<K> tailSet(K fromElement, boolean inclusive) {
         return AbstractMapBasedMultimap.this.new NavigableKeySet(this.sortedMap().tailMap(fromElement, inclusive));
      }
   }

   private class RandomAccessWrappedList extends AbstractMapBasedMultimap<K, V>.WrappedList implements RandomAccess {
      RandomAccessWrappedList(@Nullable K key, List<V> delegate, @Nullable AbstractMapBasedMultimap<K, V>.WrappedCollection ancestor) {
         super(key, delegate, ancestor);
      }
   }

   private class SortedAsMap extends AbstractMapBasedMultimap<K, V>.AsMap implements SortedMap<K, Collection<V>> {
      SortedSet<K> sortedKeySet;

      SortedAsMap(SortedMap<K, Collection<V>> submap) {
         super(submap);
      }

      SortedMap<K, Collection<V>> sortedMap() {
         return (SortedMap<K, Collection<V>>)this.submap;
      }

      @Override
      public Comparator<? super K> comparator() {
         return this.sortedMap().comparator();
      }

      @Override
      public K firstKey() {
         return (K)this.sortedMap().firstKey();
      }

      @Override
      public K lastKey() {
         return (K)this.sortedMap().lastKey();
      }

      @Override
      public SortedMap<K, Collection<V>> headMap(K toKey) {
         return AbstractMapBasedMultimap.this.new SortedAsMap(this.sortedMap().headMap(toKey));
      }

      @Override
      public SortedMap<K, Collection<V>> subMap(K fromKey, K toKey) {
         return AbstractMapBasedMultimap.this.new SortedAsMap(this.sortedMap().subMap(fromKey, toKey));
      }

      @Override
      public SortedMap<K, Collection<V>> tailMap(K fromKey) {
         return AbstractMapBasedMultimap.this.new SortedAsMap(this.sortedMap().tailMap(fromKey));
      }

      public SortedSet<K> keySet() {
         SortedSet<K> result = this.sortedKeySet;
         return result == null ? (this.sortedKeySet = this.createKeySet()) : result;
      }

      SortedSet<K> createKeySet() {
         return AbstractMapBasedMultimap.this.new SortedKeySet(this.sortedMap());
      }
   }

   private class SortedKeySet extends AbstractMapBasedMultimap<K, V>.KeySet implements SortedSet<K> {
      SortedKeySet(SortedMap<K, Collection<V>> subMap) {
         super(subMap);
      }

      SortedMap<K, Collection<V>> sortedMap() {
         return (SortedMap<K, Collection<V>>)super.map();
      }

      @Override
      public Comparator<? super K> comparator() {
         return this.sortedMap().comparator();
      }

      @Override
      public K first() {
         return (K)this.sortedMap().firstKey();
      }

      @Override
      public SortedSet<K> headSet(K toElement) {
         return AbstractMapBasedMultimap.this.new SortedKeySet(this.sortedMap().headMap(toElement));
      }

      @Override
      public K last() {
         return (K)this.sortedMap().lastKey();
      }

      @Override
      public SortedSet<K> subSet(K fromElement, K toElement) {
         return AbstractMapBasedMultimap.this.new SortedKeySet(this.sortedMap().subMap(fromElement, toElement));
      }

      @Override
      public SortedSet<K> tailSet(K fromElement) {
         return AbstractMapBasedMultimap.this.new SortedKeySet(this.sortedMap().tailMap(fromElement));
      }
   }

   private class WrappedCollection extends AbstractCollection<V> {
      final K key;
      Collection<V> delegate;
      final AbstractMapBasedMultimap<K, V>.WrappedCollection ancestor;
      final Collection<V> ancestorDelegate;

      WrappedCollection(@Nullable K key, Collection<V> delegate, @Nullable AbstractMapBasedMultimap<K, V>.WrappedCollection ancestor) {
         this.key = key;
         this.delegate = delegate;
         this.ancestor = ancestor;
         this.ancestorDelegate = ancestor == null ? null : ancestor.getDelegate();
      }

      void refreshIfEmpty() {
         if (this.ancestor != null) {
            this.ancestor.refreshIfEmpty();
            if (this.ancestor.getDelegate() != this.ancestorDelegate) {
               throw new ConcurrentModificationException();
            }
         } else if (this.delegate.isEmpty()) {
            Collection<V> newDelegate = AbstractMapBasedMultimap.this.map.get(this.key);
            if (newDelegate != null) {
               this.delegate = newDelegate;
            }
         }
      }

      void removeIfEmpty() {
         if (this.ancestor != null) {
            this.ancestor.removeIfEmpty();
         } else if (this.delegate.isEmpty()) {
            AbstractMapBasedMultimap.this.map.remove(this.key);
         }
      }

      K getKey() {
         return this.key;
      }

      void addToMap() {
         if (this.ancestor != null) {
            this.ancestor.addToMap();
         } else {
            AbstractMapBasedMultimap.this.map.put(this.key, this.delegate);
         }
      }

      @Override
      public int size() {
         this.refreshIfEmpty();
         return this.delegate.size();
      }

      @Override
      public boolean equals(@Nullable Object object) {
         if (object == this) {
            return true;
         } else {
            this.refreshIfEmpty();
            return this.delegate.equals(object);
         }
      }

      @Override
      public int hashCode() {
         this.refreshIfEmpty();
         return this.delegate.hashCode();
      }

      @Override
      public String toString() {
         this.refreshIfEmpty();
         return this.delegate.toString();
      }

      Collection<V> getDelegate() {
         return this.delegate;
      }

      @Override
      public Iterator<V> iterator() {
         this.refreshIfEmpty();
         return new AbstractMapBasedMultimap.WrappedCollection.WrappedIterator();
      }

      @Override
      public boolean add(V value) {
         this.refreshIfEmpty();
         boolean wasEmpty = this.delegate.isEmpty();
         boolean changed = this.delegate.add(value);
         if (changed) {
            AbstractMapBasedMultimap.this.totalSize++;
            if (wasEmpty) {
               this.addToMap();
            }
         }

         return changed;
      }

      AbstractMapBasedMultimap<K, V>.WrappedCollection getAncestor() {
         return this.ancestor;
      }

      @Override
      public boolean addAll(Collection<? extends V> collection) {
         if (collection.isEmpty()) {
            return false;
         } else {
            int oldSize = this.size();
            boolean changed = this.delegate.addAll(collection);
            if (changed) {
               int newSize = this.delegate.size();
               AbstractMapBasedMultimap.this.totalSize += newSize - oldSize;
               if (oldSize == 0) {
                  this.addToMap();
               }
            }

            return changed;
         }
      }

      @Override
      public boolean contains(Object o) {
         this.refreshIfEmpty();
         return this.delegate.contains(o);
      }

      @Override
      public boolean containsAll(Collection<?> c) {
         this.refreshIfEmpty();
         return this.delegate.containsAll(c);
      }

      @Override
      public void clear() {
         int oldSize = this.size();
         if (oldSize != 0) {
            this.delegate.clear();
            AbstractMapBasedMultimap.this.totalSize -= oldSize;
            this.removeIfEmpty();
         }
      }

      @Override
      public boolean remove(Object o) {
         this.refreshIfEmpty();
         boolean changed = this.delegate.remove(o);
         if (changed) {
            AbstractMapBasedMultimap.this.totalSize--;
            this.removeIfEmpty();
         }

         return changed;
      }

      @Override
      public boolean removeAll(Collection<?> c) {
         if (c.isEmpty()) {
            return false;
         } else {
            int oldSize = this.size();
            boolean changed = this.delegate.removeAll(c);
            if (changed) {
               int newSize = this.delegate.size();
               AbstractMapBasedMultimap.this.totalSize += newSize - oldSize;
               this.removeIfEmpty();
            }

            return changed;
         }
      }

      @Override
      public boolean retainAll(Collection<?> c) {
         Preconditions.checkNotNull(c);
         int oldSize = this.size();
         boolean changed = this.delegate.retainAll(c);
         if (changed) {
            int newSize = this.delegate.size();
            AbstractMapBasedMultimap.this.totalSize += newSize - oldSize;
            this.removeIfEmpty();
         }

         return changed;
      }

      class WrappedIterator implements Iterator<V> {
         final Iterator<V> delegateIterator;
         final Collection<V> originalDelegate;

         WrappedIterator() {
            this.originalDelegate = WrappedCollection.this.delegate;
            this.delegateIterator = AbstractMapBasedMultimap.this.iteratorOrListIterator(WrappedCollection.this.delegate);
         }

         WrappedIterator(Iterator<V> delegateIterator) {
            this.originalDelegate = WrappedCollection.this.delegate;
            this.delegateIterator = delegateIterator;
         }

         void validateIterator() {
            WrappedCollection.this.refreshIfEmpty();
            if (WrappedCollection.this.delegate != this.originalDelegate) {
               throw new ConcurrentModificationException();
            }
         }

         @Override
         public boolean hasNext() {
            this.validateIterator();
            return this.delegateIterator.hasNext();
         }

         @Override
         public V next() {
            this.validateIterator();
            return this.delegateIterator.next();
         }

         @Override
         public void remove() {
            this.delegateIterator.remove();
            AbstractMapBasedMultimap.this.totalSize--;
            WrappedCollection.this.removeIfEmpty();
         }

         Iterator<V> getDelegateIterator() {
            this.validateIterator();
            return this.delegateIterator;
         }
      }
   }

   private class WrappedList extends AbstractMapBasedMultimap<K, V>.WrappedCollection implements List<V> {
      WrappedList(@Nullable K key, List<V> delegate, @Nullable AbstractMapBasedMultimap<K, V>.WrappedCollection ancestor) {
         super(key, delegate, ancestor);
      }

      List<V> getListDelegate() {
         return (List<V>)this.getDelegate();
      }

      @Override
      public boolean addAll(int index, Collection<? extends V> c) {
         if (c.isEmpty()) {
            return false;
         } else {
            int oldSize = this.size();
            boolean changed = this.getListDelegate().addAll(index, c);
            if (changed) {
               int newSize = this.getDelegate().size();
               AbstractMapBasedMultimap.this.totalSize += newSize - oldSize;
               if (oldSize == 0) {
                  this.addToMap();
               }
            }

            return changed;
         }
      }

      @Override
      public V get(int index) {
         this.refreshIfEmpty();
         return (V)this.getListDelegate().get(index);
      }

      @Override
      public V set(int index, V element) {
         this.refreshIfEmpty();
         return this.getListDelegate().set(index, element);
      }

      @Override
      public void add(int index, V element) {
         this.refreshIfEmpty();
         boolean wasEmpty = this.getDelegate().isEmpty();
         this.getListDelegate().add(index, element);
         AbstractMapBasedMultimap.this.totalSize++;
         if (wasEmpty) {
            this.addToMap();
         }
      }

      @Override
      public V remove(int index) {
         this.refreshIfEmpty();
         V value = (V)this.getListDelegate().remove(index);
         AbstractMapBasedMultimap.this.totalSize--;
         this.removeIfEmpty();
         return value;
      }

      @Override
      public int indexOf(Object o) {
         this.refreshIfEmpty();
         return this.getListDelegate().indexOf(o);
      }

      @Override
      public int lastIndexOf(Object o) {
         this.refreshIfEmpty();
         return this.getListDelegate().lastIndexOf(o);
      }

      @Override
      public ListIterator<V> listIterator() {
         this.refreshIfEmpty();
         return new AbstractMapBasedMultimap.WrappedList.WrappedListIterator();
      }

      @Override
      public ListIterator<V> listIterator(int index) {
         this.refreshIfEmpty();
         return new AbstractMapBasedMultimap.WrappedList.WrappedListIterator(index);
      }

      @Override
      public List<V> subList(int fromIndex, int toIndex) {
         this.refreshIfEmpty();
         return AbstractMapBasedMultimap.this.wrapList(
            (K)this.getKey(),
            this.getListDelegate().subList(fromIndex, toIndex),
            (AbstractMapBasedMultimap<K, V>.WrappedCollection)(this.getAncestor() == null ? this : this.getAncestor())
         );
      }

      private class WrappedListIterator extends AbstractMapBasedMultimap<K, V>.WrappedCollection.WrappedIterator implements ListIterator<V> {
         WrappedListIterator() {
         }

         public WrappedListIterator(int index) {
            super(WrappedList.this.getListDelegate().listIterator(index));
         }

         private ListIterator<V> getDelegateListIterator() {
            return (ListIterator<V>)this.getDelegateIterator();
         }

         @Override
         public boolean hasPrevious() {
            return this.getDelegateListIterator().hasPrevious();
         }

         @Override
         public V previous() {
            return (V)this.getDelegateListIterator().previous();
         }

         @Override
         public int nextIndex() {
            return this.getDelegateListIterator().nextIndex();
         }

         @Override
         public int previousIndex() {
            return this.getDelegateListIterator().previousIndex();
         }

         @Override
         public void set(V value) {
            this.getDelegateListIterator().set(value);
         }

         @Override
         public void add(V value) {
            boolean wasEmpty = WrappedList.this.isEmpty();
            this.getDelegateListIterator().add(value);
            AbstractMapBasedMultimap.this.totalSize++;
            if (wasEmpty) {
               WrappedList.this.addToMap();
            }
         }
      }
   }

   @GwtIncompatible("NavigableSet")
   class WrappedNavigableSet extends AbstractMapBasedMultimap<K, V>.WrappedSortedSet implements NavigableSet<V> {
      WrappedNavigableSet(@Nullable K key, NavigableSet<V> delegate, @Nullable AbstractMapBasedMultimap<K, V>.WrappedCollection ancestor) {
         super(key, delegate, ancestor);
      }

      NavigableSet<V> getSortedSetDelegate() {
         return (NavigableSet<V>)super.getSortedSetDelegate();
      }

      @Override
      public V lower(V v) {
         return this.getSortedSetDelegate().lower(v);
      }

      @Override
      public V floor(V v) {
         return this.getSortedSetDelegate().floor(v);
      }

      @Override
      public V ceiling(V v) {
         return this.getSortedSetDelegate().ceiling(v);
      }

      @Override
      public V higher(V v) {
         return this.getSortedSetDelegate().higher(v);
      }

      @Override
      public V pollFirst() {
         return Iterators.pollNext(this.iterator());
      }

      @Override
      public V pollLast() {
         return Iterators.pollNext(this.descendingIterator());
      }

      private NavigableSet<V> wrap(NavigableSet<V> wrapped) {
         return AbstractMapBasedMultimap.this.new WrappedNavigableSet(
            this.key, wrapped, (AbstractMapBasedMultimap<K, V>.WrappedCollection)(this.getAncestor() == null ? this : this.getAncestor())
         );
      }

      @Override
      public NavigableSet<V> descendingSet() {
         return this.wrap(this.getSortedSetDelegate().descendingSet());
      }

      @Override
      public Iterator<V> descendingIterator() {
         return new AbstractMapBasedMultimap.WrappedCollection.WrappedIterator(this.getSortedSetDelegate().descendingIterator());
      }

      @Override
      public NavigableSet<V> subSet(V fromElement, boolean fromInclusive, V toElement, boolean toInclusive) {
         return this.wrap(this.getSortedSetDelegate().subSet(fromElement, fromInclusive, toElement, toInclusive));
      }

      @Override
      public NavigableSet<V> headSet(V toElement, boolean inclusive) {
         return this.wrap(this.getSortedSetDelegate().headSet(toElement, inclusive));
      }

      @Override
      public NavigableSet<V> tailSet(V fromElement, boolean inclusive) {
         return this.wrap(this.getSortedSetDelegate().tailSet(fromElement, inclusive));
      }
   }

   private class WrappedSet extends AbstractMapBasedMultimap<K, V>.WrappedCollection implements Set<V> {
      WrappedSet(@Nullable K key, Set<V> delegate) {
         super(key, delegate, null);
      }

      @Override
      public boolean removeAll(Collection<?> c) {
         if (c.isEmpty()) {
            return false;
         } else {
            int oldSize = this.size();
            boolean changed = Sets.removeAllImpl((Set<?>)this.delegate, c);
            if (changed) {
               int newSize = this.delegate.size();
               AbstractMapBasedMultimap.this.totalSize += newSize - oldSize;
               this.removeIfEmpty();
            }

            return changed;
         }
      }
   }

   private class WrappedSortedSet extends AbstractMapBasedMultimap<K, V>.WrappedCollection implements SortedSet<V> {
      WrappedSortedSet(@Nullable K key, SortedSet<V> delegate, @Nullable AbstractMapBasedMultimap<K, V>.WrappedCollection ancestor) {
         super(key, delegate, ancestor);
      }

      SortedSet<V> getSortedSetDelegate() {
         return (SortedSet<V>)this.getDelegate();
      }

      @Override
      public Comparator<? super V> comparator() {
         return this.getSortedSetDelegate().comparator();
      }

      @Override
      public V first() {
         this.refreshIfEmpty();
         return (V)this.getSortedSetDelegate().first();
      }

      @Override
      public V last() {
         this.refreshIfEmpty();
         return (V)this.getSortedSetDelegate().last();
      }

      @Override
      public SortedSet<V> headSet(V toElement) {
         this.refreshIfEmpty();
         return AbstractMapBasedMultimap.this.new WrappedSortedSet(
            this.getKey(),
            this.getSortedSetDelegate().headSet(toElement),
            (AbstractMapBasedMultimap<K, V>.WrappedCollection)(this.getAncestor() == null ? this : this.getAncestor())
         );
      }

      @Override
      public SortedSet<V> subSet(V fromElement, V toElement) {
         this.refreshIfEmpty();
         return AbstractMapBasedMultimap.this.new WrappedSortedSet(
            this.getKey(),
            this.getSortedSetDelegate().subSet(fromElement, toElement),
            (AbstractMapBasedMultimap<K, V>.WrappedCollection)(this.getAncestor() == null ? this : this.getAncestor())
         );
      }

      @Override
      public SortedSet<V> tailSet(V fromElement) {
         this.refreshIfEmpty();
         return AbstractMapBasedMultimap.this.new WrappedSortedSet(
            this.getKey(),
            this.getSortedSetDelegate().tailSet(fromElement),
            (AbstractMapBasedMultimap<K, V>.WrappedCollection)(this.getAncestor() == null ? this : this.getAncestor())
         );
      }
   }
}
