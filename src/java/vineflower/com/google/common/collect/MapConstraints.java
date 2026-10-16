package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@Beta
@GwtCompatible
public final class MapConstraints {
   private MapConstraints() {
   }

   public static MapConstraint<Object, Object> notNull() {
      return MapConstraints.NotNullMapConstraint.INSTANCE;
   }

   public static <K, V> Map<K, V> constrainedMap(Map<K, V> map, MapConstraint<? super K, ? super V> constraint) {
      return new MapConstraints.ConstrainedMap<>(map, constraint);
   }

   public static <K, V> Multimap<K, V> constrainedMultimap(Multimap<K, V> multimap, MapConstraint<? super K, ? super V> constraint) {
      return new MapConstraints.ConstrainedMultimap<>(multimap, constraint);
   }

   public static <K, V> ListMultimap<K, V> constrainedListMultimap(ListMultimap<K, V> multimap, MapConstraint<? super K, ? super V> constraint) {
      return new MapConstraints.ConstrainedListMultimap<>(multimap, constraint);
   }

   public static <K, V> SetMultimap<K, V> constrainedSetMultimap(SetMultimap<K, V> multimap, MapConstraint<? super K, ? super V> constraint) {
      return new MapConstraints.ConstrainedSetMultimap<>(multimap, constraint);
   }

   public static <K, V> SortedSetMultimap<K, V> constrainedSortedSetMultimap(SortedSetMultimap<K, V> multimap, MapConstraint<? super K, ? super V> constraint) {
      return new MapConstraints.ConstrainedSortedSetMultimap<>(multimap, constraint);
   }

   private static <K, V> Entry<K, V> constrainedEntry(final Entry<K, V> entry, final MapConstraint<? super K, ? super V> constraint) {
      Preconditions.checkNotNull(entry);
      Preconditions.checkNotNull(constraint);
      return new ForwardingMapEntry<K, V>() {
         @Override
         protected Entry<K, V> delegate() {
            return entry;
         }

         @Override
         public V setValue(V value) {
            constraint.checkKeyValue((K)this.getKey(), value);
            return entry.setValue(value);
         }
      };
   }

   private static <K, V> Entry<K, Collection<V>> constrainedAsMapEntry(
      final Entry<K, Collection<V>> entry, final MapConstraint<? super K, ? super V> constraint
   ) {
      Preconditions.checkNotNull(entry);
      Preconditions.checkNotNull(constraint);
      return new ForwardingMapEntry<K, Collection<V>>() {
         @Override
         protected Entry<K, Collection<V>> delegate() {
            return entry;
         }

         public Collection<V> getValue() {
            return Constraints.constrainedTypePreservingCollection(entry.getValue(), new Constraint<V>() {
               @Override
               public V checkElement(V value) {
                  constraint.checkKeyValue(getKey(), value);
                  return value;
               }
            });
         }
      };
   }

   private static <K, V> Set<Entry<K, Collection<V>>> constrainedAsMapEntries(
      Set<Entry<K, Collection<V>>> entries, MapConstraint<? super K, ? super V> constraint
   ) {
      return new MapConstraints.ConstrainedAsMapEntries<>(entries, constraint);
   }

   private static <K, V> Collection<Entry<K, V>> constrainedEntries(Collection<Entry<K, V>> entries, MapConstraint<? super K, ? super V> constraint) {
      return (Collection<Entry<K, V>>)(entries instanceof Set
         ? constrainedEntrySet((Set<Entry<K, V>>)entries, constraint)
         : new MapConstraints.ConstrainedEntries<>(entries, constraint));
   }

   private static <K, V> Set<Entry<K, V>> constrainedEntrySet(Set<Entry<K, V>> entries, MapConstraint<? super K, ? super V> constraint) {
      return new MapConstraints.ConstrainedEntrySet<>(entries, constraint);
   }

   public static <K, V> BiMap<K, V> constrainedBiMap(BiMap<K, V> map, MapConstraint<? super K, ? super V> constraint) {
      return new MapConstraints.ConstrainedBiMap<>(map, null, constraint);
   }

   private static <K, V> Collection<V> checkValues(K key, Iterable<? extends V> values, MapConstraint<? super K, ? super V> constraint) {
      Collection<V> copy = Lists.newArrayList(values);

      for (V value : copy) {
         constraint.checkKeyValue(key, value);
      }

      return copy;
   }

   private static <K, V> Map<K, V> checkMap(Map<? extends K, ? extends V> map, MapConstraint<? super K, ? super V> constraint) {
      Map<K, V> copy = new LinkedHashMap<>(map);

      for (Entry<K, V> entry : copy.entrySet()) {
         constraint.checkKeyValue(entry.getKey(), entry.getValue());
      }

      return copy;
   }

   static class ConstrainedAsMapEntries<K, V> extends ForwardingSet<Entry<K, Collection<V>>> {
      private final MapConstraint<? super K, ? super V> constraint;
      private final Set<Entry<K, Collection<V>>> entries;

      ConstrainedAsMapEntries(Set<Entry<K, Collection<V>>> entries, MapConstraint<? super K, ? super V> constraint) {
         this.entries = entries;
         this.constraint = constraint;
      }

      @Override
      protected Set<Entry<K, Collection<V>>> delegate() {
         return this.entries;
      }

      @Override
      public Iterator<Entry<K, Collection<V>>> iterator() {
         final Iterator<Entry<K, Collection<V>>> iterator = this.entries.iterator();
         return new ForwardingIterator<Entry<K, Collection<V>>>() {
            public Entry<K, Collection<V>> next() {
               return MapConstraints.constrainedAsMapEntry(iterator.next(), ConstrainedAsMapEntries.this.constraint);
            }

            @Override
            protected Iterator<Entry<K, Collection<V>>> delegate() {
               return iterator;
            }
         };
      }

      @Override
      public Object[] toArray() {
         return this.standardToArray();
      }

      @Override
      public <T> T[] toArray(T[] array) {
         return (T[])this.standardToArray(array);
      }

      @Override
      public boolean contains(Object o) {
         return Maps.containsEntryImpl(this.delegate(), o);
      }

      @Override
      public boolean containsAll(Collection<?> c) {
         return this.standardContainsAll(c);
      }

      @Override
      public boolean equals(@Nullable Object object) {
         return this.standardEquals(object);
      }

      @Override
      public int hashCode() {
         return this.standardHashCode();
      }

      @Override
      public boolean remove(Object o) {
         return Maps.removeEntryImpl(this.delegate(), o);
      }

      @Override
      public boolean removeAll(Collection<?> c) {
         return this.standardRemoveAll(c);
      }

      @Override
      public boolean retainAll(Collection<?> c) {
         return this.standardRetainAll(c);
      }
   }

   private static class ConstrainedAsMapValues<K, V> extends ForwardingCollection<Collection<V>> {
      final Collection<Collection<V>> delegate;
      final Set<Entry<K, Collection<V>>> entrySet;

      ConstrainedAsMapValues(Collection<Collection<V>> delegate, Set<Entry<K, Collection<V>>> entrySet) {
         this.delegate = delegate;
         this.entrySet = entrySet;
      }

      @Override
      protected Collection<Collection<V>> delegate() {
         return this.delegate;
      }

      @Override
      public Iterator<Collection<V>> iterator() {
         final Iterator<Entry<K, Collection<V>>> iterator = this.entrySet.iterator();
         return new Iterator<Collection<V>>() {
            @Override
            public boolean hasNext() {
               return iterator.hasNext();
            }

            public Collection<V> next() {
               return iterator.next().getValue();
            }

            @Override
            public void remove() {
               iterator.remove();
            }
         };
      }

      @Override
      public Object[] toArray() {
         return this.standardToArray();
      }

      @Override
      public <T> T[] toArray(T[] array) {
         return (T[])this.standardToArray(array);
      }

      @Override
      public boolean contains(Object o) {
         return this.standardContains(o);
      }

      @Override
      public boolean containsAll(Collection<?> c) {
         return this.standardContainsAll(c);
      }

      @Override
      public boolean remove(Object o) {
         return this.standardRemove(o);
      }

      @Override
      public boolean removeAll(Collection<?> c) {
         return this.standardRemoveAll(c);
      }

      @Override
      public boolean retainAll(Collection<?> c) {
         return this.standardRetainAll(c);
      }
   }

   private static class ConstrainedBiMap<K, V> extends MapConstraints.ConstrainedMap<K, V> implements BiMap<K, V> {
      volatile BiMap<V, K> inverse;

      ConstrainedBiMap(BiMap<K, V> delegate, @Nullable BiMap<V, K> inverse, MapConstraint<? super K, ? super V> constraint) {
         super(delegate, constraint);
         this.inverse = inverse;
      }

      protected BiMap<K, V> delegate() {
         return (BiMap<K, V>)super.delegate();
      }

      @Override
      public V forcePut(K key, V value) {
         this.constraint.checkKeyValue(key, value);
         return this.delegate().forcePut(key, value);
      }

      @Override
      public BiMap<V, K> inverse() {
         if (this.inverse == null) {
            this.inverse = new MapConstraints.ConstrainedBiMap<>(this.delegate().inverse(), this, new MapConstraints.InverseConstraint<>(this.constraint));
         }

         return this.inverse;
      }

      @Override
      public Set<V> values() {
         return this.delegate().values();
      }
   }

   private static class ConstrainedEntries<K, V> extends ForwardingCollection<Entry<K, V>> {
      final MapConstraint<? super K, ? super V> constraint;
      final Collection<Entry<K, V>> entries;

      ConstrainedEntries(Collection<Entry<K, V>> entries, MapConstraint<? super K, ? super V> constraint) {
         this.entries = entries;
         this.constraint = constraint;
      }

      @Override
      protected Collection<Entry<K, V>> delegate() {
         return this.entries;
      }

      @Override
      public Iterator<Entry<K, V>> iterator() {
         final Iterator<Entry<K, V>> iterator = this.entries.iterator();
         return new ForwardingIterator<Entry<K, V>>() {
            public Entry<K, V> next() {
               return MapConstraints.constrainedEntry(iterator.next(), ConstrainedEntries.this.constraint);
            }

            @Override
            protected Iterator<Entry<K, V>> delegate() {
               return iterator;
            }
         };
      }

      @Override
      public Object[] toArray() {
         return this.standardToArray();
      }

      @Override
      public <T> T[] toArray(T[] array) {
         return (T[])this.standardToArray(array);
      }

      @Override
      public boolean contains(Object o) {
         return Maps.containsEntryImpl(this.delegate(), o);
      }

      @Override
      public boolean containsAll(Collection<?> c) {
         return this.standardContainsAll(c);
      }

      @Override
      public boolean remove(Object o) {
         return Maps.removeEntryImpl(this.delegate(), o);
      }

      @Override
      public boolean removeAll(Collection<?> c) {
         return this.standardRemoveAll(c);
      }

      @Override
      public boolean retainAll(Collection<?> c) {
         return this.standardRetainAll(c);
      }
   }

   static class ConstrainedEntrySet<K, V> extends MapConstraints.ConstrainedEntries<K, V> implements Set<Entry<K, V>> {
      ConstrainedEntrySet(Set<Entry<K, V>> entries, MapConstraint<? super K, ? super V> constraint) {
         super(entries, constraint);
      }

      @Override
      public boolean equals(@Nullable Object object) {
         return Sets.equalsImpl(this, object);
      }

      @Override
      public int hashCode() {
         return Sets.hashCodeImpl(this);
      }
   }

   private static class ConstrainedListMultimap<K, V> extends MapConstraints.ConstrainedMultimap<K, V> implements ListMultimap<K, V> {
      ConstrainedListMultimap(ListMultimap<K, V> delegate, MapConstraint<? super K, ? super V> constraint) {
         super(delegate, constraint);
      }

      @Override
      public List<V> get(K key) {
         return (List<V>)super.get(key);
      }

      @Override
      public List<V> removeAll(Object key) {
         return (List<V>)super.removeAll(key);
      }

      @Override
      public List<V> replaceValues(K key, Iterable<? extends V> values) {
         return (List<V>)super.replaceValues(key, values);
      }
   }

   static class ConstrainedMap<K, V> extends ForwardingMap<K, V> {
      private final Map<K, V> delegate;
      final MapConstraint<? super K, ? super V> constraint;
      private transient Set<Entry<K, V>> entrySet;

      ConstrainedMap(Map<K, V> delegate, MapConstraint<? super K, ? super V> constraint) {
         this.delegate = Preconditions.checkNotNull(delegate);
         this.constraint = Preconditions.checkNotNull(constraint);
      }

      @Override
      protected Map<K, V> delegate() {
         return this.delegate;
      }

      @Override
      public Set<Entry<K, V>> entrySet() {
         Set<Entry<K, V>> result = this.entrySet;
         if (result == null) {
            this.entrySet = result = MapConstraints.constrainedEntrySet(this.delegate.entrySet(), this.constraint);
         }

         return result;
      }

      @Override
      public V put(K key, V value) {
         this.constraint.checkKeyValue(key, value);
         return this.delegate.put(key, value);
      }

      @Override
      public void putAll(Map<? extends K, ? extends V> map) {
         this.delegate.putAll(MapConstraints.checkMap(map, this.constraint));
      }
   }

   private static class ConstrainedMultimap<K, V> extends ForwardingMultimap<K, V> implements Serializable {
      final MapConstraint<? super K, ? super V> constraint;
      final Multimap<K, V> delegate;
      transient Collection<Entry<K, V>> entries;
      transient Map<K, Collection<V>> asMap;

      public ConstrainedMultimap(Multimap<K, V> delegate, MapConstraint<? super K, ? super V> constraint) {
         this.delegate = Preconditions.checkNotNull(delegate);
         this.constraint = Preconditions.checkNotNull(constraint);
      }

      @Override
      protected Multimap<K, V> delegate() {
         return this.delegate;
      }

      @Override
      public Map<K, Collection<V>> asMap() {
         Map<K, Collection<V>> result = this.asMap;
         if (result == null) {
            final Map<K, Collection<V>> asMapDelegate = this.delegate.asMap();
            this.asMap = result = new ForwardingMap<K, Collection<V>>() {
               Set<Entry<K, Collection<V>>> entrySet;
               Collection<Collection<V>> values;

               @Override
               protected Map<K, Collection<V>> delegate() {
                  return asMapDelegate;
               }

               @Override
               public Set<Entry<K, Collection<V>>> entrySet() {
                  Set<Entry<K, Collection<V>>> resultx = this.entrySet;
                  if (resultx == null) {
                     this.entrySet = resultx = MapConstraints.constrainedAsMapEntries(asMapDelegate.entrySet(), ConstrainedMultimap.this.constraint);
                  }

                  return resultx;
               }

               public Collection<V> get(Object key) {
                  try {
                     Collection<V> collection = ConstrainedMultimap.this.get(key);
                     return collection.isEmpty() ? null : collection;
                  } catch (ClassCastException var3) {
                     return null;
                  }
               }

               @Override
               public Collection<Collection<V>> values() {
                  Collection<Collection<V>> resultx = this.values;
                  if (resultx == null) {
                     this.values = resultx = new MapConstraints.ConstrainedAsMapValues<>(this.delegate().values(), this.entrySet());
                  }

                  return resultx;
               }

               @Override
               public boolean containsValue(Object o) {
                  return this.values().contains(o);
               }
            };
         }

         return result;
      }

      @Override
      public Collection<Entry<K, V>> entries() {
         Collection<Entry<K, V>> result = this.entries;
         if (result == null) {
            this.entries = result = MapConstraints.constrainedEntries(this.delegate.entries(), this.constraint);
         }

         return result;
      }

      @Override
      public Collection<V> get(final K key) {
         return Constraints.constrainedTypePreservingCollection(this.delegate.get(key), new Constraint<V>() {
            @Override
            public V checkElement(V value) {
               ConstrainedMultimap.this.constraint.checkKeyValue(key, value);
               return value;
            }
         });
      }

      @Override
      public boolean put(K key, V value) {
         this.constraint.checkKeyValue(key, value);
         return this.delegate.put(key, value);
      }

      @Override
      public boolean putAll(K key, Iterable<? extends V> values) {
         return this.delegate.putAll(key, MapConstraints.checkValues(key, values, this.constraint));
      }

      @Override
      public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
         boolean changed = false;

         for (Entry<? extends K, ? extends V> entry : multimap.entries()) {
            changed |= this.put((K)entry.getKey(), (V)entry.getValue());
         }

         return changed;
      }

      @Override
      public Collection<V> replaceValues(K key, Iterable<? extends V> values) {
         return this.delegate.replaceValues(key, MapConstraints.checkValues(key, values, this.constraint));
      }
   }

   private static class ConstrainedSetMultimap<K, V> extends MapConstraints.ConstrainedMultimap<K, V> implements SetMultimap<K, V> {
      ConstrainedSetMultimap(SetMultimap<K, V> delegate, MapConstraint<? super K, ? super V> constraint) {
         super(delegate, constraint);
      }

      @Override
      public Set<V> get(K key) {
         return (Set<V>)super.get(key);
      }

      @Override
      public Set<Entry<K, V>> entries() {
         return (Set<Entry<K, V>>)super.entries();
      }

      @Override
      public Set<V> removeAll(Object key) {
         return (Set<V>)super.removeAll(key);
      }

      @Override
      public Set<V> replaceValues(K key, Iterable<? extends V> values) {
         return (Set<V>)super.replaceValues(key, values);
      }
   }

   private static class ConstrainedSortedSetMultimap<K, V> extends MapConstraints.ConstrainedSetMultimap<K, V> implements SortedSetMultimap<K, V> {
      ConstrainedSortedSetMultimap(SortedSetMultimap<K, V> delegate, MapConstraint<? super K, ? super V> constraint) {
         super(delegate, constraint);
      }

      @Override
      public SortedSet<V> get(K key) {
         return (SortedSet<V>)super.get(key);
      }

      @Override
      public SortedSet<V> removeAll(Object key) {
         return (SortedSet<V>)super.removeAll(key);
      }

      @Override
      public SortedSet<V> replaceValues(K key, Iterable<? extends V> values) {
         return (SortedSet<V>)super.replaceValues(key, values);
      }

      @Override
      public Comparator<? super V> valueComparator() {
         return ((SortedSetMultimap)this.delegate()).valueComparator();
      }
   }

   private static class InverseConstraint<K, V> implements MapConstraint<K, V> {
      final MapConstraint<? super V, ? super K> constraint;

      public InverseConstraint(MapConstraint<? super V, ? super K> constraint) {
         this.constraint = Preconditions.checkNotNull(constraint);
      }

      @Override
      public void checkKeyValue(K key, V value) {
         this.constraint.checkKeyValue(value, key);
      }
   }

   private static enum NotNullMapConstraint implements MapConstraint<Object, Object> {
      INSTANCE;

      @Override
      public void checkKeyValue(Object key, Object value) {
         Preconditions.checkNotNull(key);
         Preconditions.checkNotNull(value);
      }

      @Override
      public String toString() {
         return "Not null";
      }
   }
}
