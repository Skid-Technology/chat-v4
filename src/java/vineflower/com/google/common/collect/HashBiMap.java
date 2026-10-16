package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
public final class HashBiMap<K, V> extends AbstractMap<K, V> implements BiMap<K, V>, Serializable {
   private static final double LOAD_FACTOR = 1.0;
   private transient HashBiMap.BiEntry<K, V>[] hashTableKToV;
   private transient HashBiMap.BiEntry<K, V>[] hashTableVToK;
   private transient int size;
   private transient int mask;
   private transient int modCount;
   private transient BiMap<V, K> inverse;
   @GwtIncompatible("Not needed in emulated source")
   private static final long serialVersionUID = 0L;

   public static <K, V> HashBiMap<K, V> create() {
      return create(16);
   }

   public static <K, V> HashBiMap<K, V> create(int expectedSize) {
      return new HashBiMap<>(expectedSize);
   }

   public static <K, V> HashBiMap<K, V> create(Map<? extends K, ? extends V> map) {
      HashBiMap<K, V> bimap = create(map.size());
      bimap.putAll(map);
      return bimap;
   }

   private HashBiMap(int expectedSize) {
      this.init(expectedSize);
   }

   private void init(int expectedSize) {
      CollectPreconditions.checkNonnegative(expectedSize, "expectedSize");
      int tableSize = Hashing.closedTableSize(expectedSize, 1.0);
      this.hashTableKToV = this.createTable(tableSize);
      this.hashTableVToK = this.createTable(tableSize);
      this.mask = tableSize - 1;
      this.modCount = 0;
      this.size = 0;
   }

   private void delete(HashBiMap.BiEntry<K, V> entry) {
      int keyBucket = entry.keyHash & this.mask;
      HashBiMap.BiEntry<K, V> prevBucketEntry = null;

      for (HashBiMap.BiEntry<K, V> bucketEntry = this.hashTableKToV[keyBucket]; bucketEntry != entry; bucketEntry = bucketEntry.nextInKToVBucket) {
         prevBucketEntry = bucketEntry;
      }

      if (prevBucketEntry == null) {
         this.hashTableKToV[keyBucket] = entry.nextInKToVBucket;
      } else {
         prevBucketEntry.nextInKToVBucket = entry.nextInKToVBucket;
      }

      int valueBucket = entry.valueHash & this.mask;
      prevBucketEntry = null;

      for (HashBiMap.BiEntry<K, V> bucketEntry = this.hashTableVToK[valueBucket]; bucketEntry != entry; bucketEntry = bucketEntry.nextInVToKBucket) {
         prevBucketEntry = bucketEntry;
      }

      if (prevBucketEntry == null) {
         this.hashTableVToK[valueBucket] = entry.nextInVToKBucket;
      } else {
         prevBucketEntry.nextInVToKBucket = entry.nextInVToKBucket;
      }

      this.size--;
      this.modCount++;
   }

   private void insert(HashBiMap.BiEntry<K, V> entry) {
      int keyBucket = entry.keyHash & this.mask;
      entry.nextInKToVBucket = this.hashTableKToV[keyBucket];
      this.hashTableKToV[keyBucket] = entry;
      int valueBucket = entry.valueHash & this.mask;
      entry.nextInVToKBucket = this.hashTableVToK[valueBucket];
      this.hashTableVToK[valueBucket] = entry;
      this.size++;
      this.modCount++;
   }

   private static int hash(@Nullable Object o) {
      return Hashing.smear(o == null ? 0 : o.hashCode());
   }

   private HashBiMap.BiEntry<K, V> seekByKey(@Nullable Object key, int keyHash) {
      for (HashBiMap.BiEntry<K, V> entry = this.hashTableKToV[keyHash & this.mask]; entry != null; entry = entry.nextInKToVBucket) {
         if (keyHash == entry.keyHash && Objects.equal(key, entry.key)) {
            return entry;
         }
      }

      return null;
   }

   private HashBiMap.BiEntry<K, V> seekByValue(@Nullable Object value, int valueHash) {
      for (HashBiMap.BiEntry<K, V> entry = this.hashTableVToK[valueHash & this.mask]; entry != null; entry = entry.nextInVToKBucket) {
         if (valueHash == entry.valueHash && Objects.equal(value, entry.value)) {
            return entry;
         }
      }

      return null;
   }

   @Override
   public boolean containsKey(@Nullable Object key) {
      return this.seekByKey(key, hash(key)) != null;
   }

   @Override
   public boolean containsValue(@Nullable Object value) {
      return this.seekByValue(value, hash(value)) != null;
   }

   @Nullable
   @Override
   public V get(@Nullable Object key) {
      HashBiMap.BiEntry<K, V> entry = this.seekByKey(key, hash(key));
      return entry == null ? null : entry.value;
   }

   @Override
   public V put(@Nullable K key, @Nullable V value) {
      return this.put(key, value, false);
   }

   @Override
   public V forcePut(@Nullable K key, @Nullable V value) {
      return this.put(key, value, true);
   }

   private V put(@Nullable K key, @Nullable V value, boolean force) {
      int keyHash = hash(key);
      int valueHash = hash(value);
      HashBiMap.BiEntry<K, V> oldEntryForKey = this.seekByKey(key, keyHash);
      if (oldEntryForKey != null && valueHash == oldEntryForKey.valueHash && Objects.equal(value, oldEntryForKey.value)) {
         return value;
      } else {
         HashBiMap.BiEntry<K, V> oldEntryForValue = this.seekByValue(value, valueHash);
         if (oldEntryForValue != null) {
            if (!force) {
               throw new IllegalArgumentException("value already present: " + value);
            }

            this.delete(oldEntryForValue);
         }

         if (oldEntryForKey != null) {
            this.delete(oldEntryForKey);
         }

         HashBiMap.BiEntry<K, V> newEntry = new HashBiMap.BiEntry<>(key, keyHash, value, valueHash);
         this.insert(newEntry);
         this.rehashIfNecessary();
         return oldEntryForKey == null ? null : oldEntryForKey.value;
      }
   }

   @Nullable
   private K putInverse(@Nullable V value, @Nullable K key, boolean force) {
      int valueHash = hash(value);
      int keyHash = hash(key);
      HashBiMap.BiEntry<K, V> oldEntryForValue = this.seekByValue(value, valueHash);
      if (oldEntryForValue != null && keyHash == oldEntryForValue.keyHash && Objects.equal(key, oldEntryForValue.key)) {
         return key;
      } else {
         HashBiMap.BiEntry<K, V> oldEntryForKey = this.seekByKey(key, keyHash);
         if (oldEntryForKey != null) {
            if (!force) {
               throw new IllegalArgumentException("value already present: " + key);
            }

            this.delete(oldEntryForKey);
         }

         if (oldEntryForValue != null) {
            this.delete(oldEntryForValue);
         }

         HashBiMap.BiEntry<K, V> newEntry = new HashBiMap.BiEntry<>(key, keyHash, value, valueHash);
         this.insert(newEntry);
         this.rehashIfNecessary();
         return oldEntryForValue == null ? null : oldEntryForValue.key;
      }
   }

   private void rehashIfNecessary() {
      HashBiMap.BiEntry<K, V>[] oldKToV = this.hashTableKToV;
      if (Hashing.needsResizing(this.size, oldKToV.length, 1.0)) {
         int newTableSize = oldKToV.length * 2;
         this.hashTableKToV = this.createTable(newTableSize);
         this.hashTableVToK = this.createTable(newTableSize);
         this.mask = newTableSize - 1;
         this.size = 0;

         for (int bucket = 0; bucket < oldKToV.length; bucket++) {
            HashBiMap.BiEntry<K, V> entry = oldKToV[bucket];

            while (entry != null) {
               HashBiMap.BiEntry<K, V> nextEntry = entry.nextInKToVBucket;
               this.insert(entry);
               entry = nextEntry;
            }
         }

         this.modCount++;
      }
   }

   private HashBiMap.BiEntry<K, V>[] createTable(int length) {
      return new HashBiMap.BiEntry[length];
   }

   @Override
   public V remove(@Nullable Object key) {
      HashBiMap.BiEntry<K, V> entry = this.seekByKey(key, hash(key));
      if (entry == null) {
         return null;
      } else {
         this.delete(entry);
         return entry.value;
      }
   }

   @Override
   public void clear() {
      this.size = 0;
      Arrays.fill(this.hashTableKToV, null);
      Arrays.fill(this.hashTableVToK, null);
      this.modCount++;
   }

   @Override
   public int size() {
      return this.size;
   }

   @Override
   public Set<K> keySet() {
      return new HashBiMap.KeySet();
   }

   @Override
   public Set<V> values() {
      return this.inverse().keySet();
   }

   @Override
   public Set<Entry<K, V>> entrySet() {
      return new HashBiMap.EntrySet();
   }

   @Override
   public BiMap<V, K> inverse() {
      return this.inverse == null ? (this.inverse = new HashBiMap.Inverse()) : this.inverse;
   }

   @GwtIncompatible("java.io.ObjectOutputStream")
   private void writeObject(ObjectOutputStream stream) throws IOException {
      stream.defaultWriteObject();
      Serialization.writeMap(this, stream);
   }

   @GwtIncompatible("java.io.ObjectInputStream")
   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      int size = Serialization.readCount(stream);
      this.init(size);
      Serialization.populateMap(this, stream, size);
   }

   private static final class BiEntry<K, V> extends ImmutableEntry<K, V> {
      final int keyHash;
      final int valueHash;
      @Nullable
      HashBiMap.BiEntry<K, V> nextInKToVBucket;
      @Nullable
      HashBiMap.BiEntry<K, V> nextInVToKBucket;

      BiEntry(K key, int keyHash, V value, int valueHash) {
         super(key, value);
         this.keyHash = keyHash;
         this.valueHash = valueHash;
      }
   }

   private final class EntrySet extends Maps.EntrySet<K, V> {
      private EntrySet() {
      }

      @Override
      Map<K, V> map() {
         return HashBiMap.this;
      }

      @Override
      public Iterator<Entry<K, V>> iterator() {
         return new HashBiMap<K, V>.Itr<Entry<K, V>>() {
            Entry<K, V> output(HashBiMap.BiEntry<K, V> entry) {
               return new MapEntry(entry);
            }

            class MapEntry extends AbstractMapEntry<K, V> {
               HashBiMap.BiEntry<K, V> delegate;

               MapEntry(HashBiMap.BiEntry<K, V> entry) {
                  this.delegate = entry;
               }

               @Override
               public K getKey() {
                  return this.delegate.key;
               }

               @Override
               public V getValue() {
                  return this.delegate.value;
               }

               @Override
               public V setValue(V value) {
                  V oldValue = this.delegate.value;
                  int valueHash = HashBiMap.hash(value);
                  if (valueHash == this.delegate.valueHash && Objects.equal(value, oldValue)) {
                     return value;
                  } else {
                     Preconditions.checkArgument(HashBiMap.this.seekByValue(value, valueHash) == null, "value already present: %s", value);
                     HashBiMap.this.delete(this.delegate);
                     HashBiMap.BiEntry<K, V> newEntry = new HashBiMap.BiEntry<>(this.delegate.key, this.delegate.keyHash, value, valueHash);
                     HashBiMap.this.insert(newEntry);
                     expectedModCount = HashBiMap.this.modCount;
                     if (toRemove == this.delegate) {
                        toRemove = newEntry;
                     }

                     this.delegate = newEntry;
                     return oldValue;
                  }
               }
            }
         };
      }
   }

   private final class Inverse extends AbstractMap<V, K> implements BiMap<V, K>, Serializable {
      private Inverse() {
      }

      BiMap<K, V> forward() {
         return HashBiMap.this;
      }

      @Override
      public int size() {
         return HashBiMap.this.size;
      }

      @Override
      public void clear() {
         this.forward().clear();
      }

      @Override
      public boolean containsKey(@Nullable Object value) {
         return this.forward().containsValue(value);
      }

      @Override
      public K get(@Nullable Object value) {
         HashBiMap.BiEntry<K, V> entry = HashBiMap.this.seekByValue(value, HashBiMap.hash(value));
         return entry == null ? null : entry.key;
      }

      @Override
      public K put(@Nullable V value, @Nullable K key) {
         return HashBiMap.this.putInverse(value, key, false);
      }

      @Override
      public K forcePut(@Nullable V value, @Nullable K key) {
         return HashBiMap.this.putInverse(value, key, true);
      }

      @Override
      public K remove(@Nullable Object value) {
         HashBiMap.BiEntry<K, V> entry = HashBiMap.this.seekByValue(value, HashBiMap.hash(value));
         if (entry == null) {
            return null;
         } else {
            HashBiMap.this.delete(entry);
            return entry.key;
         }
      }

      @Override
      public BiMap<K, V> inverse() {
         return this.forward();
      }

      @Override
      public Set<V> keySet() {
         return new HashBiMap.Inverse.InverseKeySet();
      }

      @Override
      public Set<K> values() {
         return this.forward().keySet();
      }

      @Override
      public Set<Entry<V, K>> entrySet() {
         return new Maps.EntrySet<V, K>() {
            @Override
            Map<V, K> map() {
               return Inverse.this;
            }

            @Override
            public Iterator<Entry<V, K>> iterator() {
               return new HashBiMap<K, V>.Itr<Entry<V, K>>() {
                  Entry<V, K> output(HashBiMap.BiEntry<K, V> entry) {
                     return new InverseEntry(entry);
                  }

                  class InverseEntry extends AbstractMapEntry<V, K> {
                     HashBiMap.BiEntry<K, V> delegate;

                     InverseEntry(HashBiMap.BiEntry<K, V> entry) {
                        this.delegate = entry;
                     }

                     @Override
                     public V getKey() {
                        return this.delegate.value;
                     }

                     @Override
                     public K getValue() {
                        return this.delegate.key;
                     }

                     @Override
                     public K setValue(K key) {
                        K oldKey = this.delegate.key;
                        int keyHash = HashBiMap.hash(key);
                        if (keyHash == this.delegate.keyHash && Objects.equal(key, oldKey)) {
                           return key;
                        } else {
                           Preconditions.checkArgument(HashBiMap.this.seekByKey(key, keyHash) == null, "value already present: %s", key);
                           HashBiMap.this.delete(this.delegate);
                           HashBiMap.BiEntry<K, V> newEntry = new HashBiMap.BiEntry<>(key, keyHash, this.delegate.value, this.delegate.valueHash);
                           HashBiMap.this.insert(newEntry);
                           expectedModCount = HashBiMap.this.modCount;
                           return oldKey;
                        }
                     }
                  }
               };
            }
         };
      }

      Object writeReplace() {
         return new HashBiMap.InverseSerializedForm<>(HashBiMap.this);
      }

      private final class InverseKeySet extends Maps.KeySet<V, K> {
         InverseKeySet() {
            super(Inverse.this);
         }

         @Override
         public boolean remove(@Nullable Object o) {
            HashBiMap.BiEntry<K, V> entry = HashBiMap.this.seekByValue(o, HashBiMap.hash(o));
            if (entry == null) {
               return false;
            } else {
               HashBiMap.this.delete(entry);
               return true;
            }
         }

         @Override
         public Iterator<V> iterator() {
            return new HashBiMap<K, V>.Itr<V>() {
               @Override
               V output(HashBiMap.BiEntry<K, V> entry) {
                  return entry.value;
               }
            };
         }
      }
   }

   private static final class InverseSerializedForm<K, V> implements Serializable {
      private final HashBiMap<K, V> bimap;

      InverseSerializedForm(HashBiMap<K, V> bimap) {
         this.bimap = bimap;
      }

      Object readResolve() {
         return this.bimap.inverse();
      }
   }

   abstract class Itr<T> implements Iterator<T> {
      int nextBucket = 0;
      HashBiMap.BiEntry<K, V> next = null;
      HashBiMap.BiEntry<K, V> toRemove = null;
      int expectedModCount = HashBiMap.this.modCount;

      private void checkForConcurrentModification() {
         if (HashBiMap.this.modCount != this.expectedModCount) {
            throw new ConcurrentModificationException();
         }
      }

      @Override
      public boolean hasNext() {
         this.checkForConcurrentModification();
         if (this.next != null) {
            return true;
         } else {
            while (this.nextBucket < HashBiMap.this.hashTableKToV.length) {
               if (HashBiMap.this.hashTableKToV[this.nextBucket] != null) {
                  this.next = HashBiMap.this.hashTableKToV[this.nextBucket++];
                  return true;
               }

               this.nextBucket++;
            }

            return false;
         }
      }

      @Override
      public T next() {
         this.checkForConcurrentModification();
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            HashBiMap.BiEntry<K, V> entry = this.next;
            this.next = entry.nextInKToVBucket;
            this.toRemove = entry;
            return this.output(entry);
         }
      }

      @Override
      public void remove() {
         this.checkForConcurrentModification();
         CollectPreconditions.checkRemove(this.toRemove != null);
         HashBiMap.this.delete(this.toRemove);
         this.expectedModCount = HashBiMap.this.modCount;
         this.toRemove = null;
      }

      abstract T output(HashBiMap.BiEntry<K, V> var1);
   }

   private final class KeySet extends Maps.KeySet<K, V> {
      KeySet() {
         super(HashBiMap.this);
      }

      @Override
      public Iterator<K> iterator() {
         return new HashBiMap<K, V>.Itr<K>() {
            @Override
            K output(HashBiMap.BiEntry<K, V> entry) {
               return entry.key;
            }
         };
      }

      @Override
      public boolean remove(@Nullable Object o) {
         HashBiMap.BiEntry<K, V> entry = HashBiMap.this.seekByKey(o, HashBiMap.hash(o));
         if (entry == null) {
            return false;
         } else {
            HashBiMap.this.delete(entry);
            return true;
         }
      }
   }
}
