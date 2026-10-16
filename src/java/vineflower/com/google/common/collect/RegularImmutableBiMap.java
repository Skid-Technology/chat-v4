package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true, emulated = true)
class RegularImmutableBiMap<K, V> extends ImmutableBiMap<K, V> {
   static final double MAX_LOAD_FACTOR = 1.2;
   private final transient ImmutableMapEntry<K, V>[] keyTable;
   private final transient ImmutableMapEntry<K, V>[] valueTable;
   private final transient ImmutableMapEntry<K, V>[] entries;
   private final transient int mask;
   private final transient int hashCode;
   private transient ImmutableBiMap<V, K> inverse;

   RegularImmutableBiMap(ImmutableMapEntry.TerminalEntry<?, ?>... entriesToAdd) {
      this(entriesToAdd.length, entriesToAdd);
   }

   RegularImmutableBiMap(int n, ImmutableMapEntry.TerminalEntry<?, ?>[] entriesToAdd) {
      int tableSize = Hashing.closedTableSize(n, 1.2);
      this.mask = tableSize - 1;
      ImmutableMapEntry<K, V>[] keyTable = createEntryArray(tableSize);
      ImmutableMapEntry<K, V>[] valueTable = createEntryArray(tableSize);
      ImmutableMapEntry<K, V>[] entries = createEntryArray(n);
      int hashCode = 0;

      for (int i = 0; i < n; i++) {
         ImmutableMapEntry.TerminalEntry<K, V> entry = (ImmutableMapEntry.TerminalEntry<K, V>)entriesToAdd[i];
         K key = entry.getKey();
         V value = entry.getValue();
         int keyHash = key.hashCode();
         int valueHash = value.hashCode();
         int keyBucket = Hashing.smear(keyHash) & this.mask;
         int valueBucket = Hashing.smear(valueHash) & this.mask;
         ImmutableMapEntry<K, V> nextInKeyBucket = keyTable[keyBucket];

         for (ImmutableMapEntry<K, V> keyEntry = nextInKeyBucket; keyEntry != null; keyEntry = keyEntry.getNextInKeyBucket()) {
            checkNoConflict(!key.equals(keyEntry.getKey()), "key", entry, keyEntry);
         }

         ImmutableMapEntry<K, V> nextInValueBucket = valueTable[valueBucket];

         for (ImmutableMapEntry<K, V> valueEntry = nextInValueBucket; valueEntry != null; valueEntry = valueEntry.getNextInValueBucket()) {
            checkNoConflict(!value.equals(valueEntry.getValue()), "value", entry, valueEntry);
         }

         ImmutableMapEntry<K, V> newEntry = (ImmutableMapEntry<K, V>)(nextInKeyBucket == null && nextInValueBucket == null
            ? entry
            : new RegularImmutableBiMap.NonTerminalBiMapEntry<>(entry, nextInKeyBucket, nextInValueBucket));
         keyTable[keyBucket] = newEntry;
         valueTable[valueBucket] = newEntry;
         entries[i] = newEntry;
         hashCode += keyHash ^ valueHash;
      }

      this.keyTable = keyTable;
      this.valueTable = valueTable;
      this.entries = entries;
      this.hashCode = hashCode;
   }

   RegularImmutableBiMap(Entry<?, ?>[] entriesToAdd) {
      int n = entriesToAdd.length;
      int tableSize = Hashing.closedTableSize(n, 1.2);
      this.mask = tableSize - 1;
      ImmutableMapEntry<K, V>[] keyTable = createEntryArray(tableSize);
      ImmutableMapEntry<K, V>[] valueTable = createEntryArray(tableSize);
      ImmutableMapEntry<K, V>[] entries = createEntryArray(n);
      int hashCode = 0;

      for (int i = 0; i < n; i++) {
         Entry<K, V> entry = (Entry<K, V>)entriesToAdd[i];
         K key = entry.getKey();
         V value = entry.getValue();
         CollectPreconditions.checkEntryNotNull(key, value);
         int keyHash = key.hashCode();
         int valueHash = value.hashCode();
         int keyBucket = Hashing.smear(keyHash) & this.mask;
         int valueBucket = Hashing.smear(valueHash) & this.mask;
         ImmutableMapEntry<K, V> nextInKeyBucket = keyTable[keyBucket];

         for (ImmutableMapEntry<K, V> keyEntry = nextInKeyBucket; keyEntry != null; keyEntry = keyEntry.getNextInKeyBucket()) {
            checkNoConflict(!key.equals(keyEntry.getKey()), "key", entry, keyEntry);
         }

         ImmutableMapEntry<K, V> nextInValueBucket = valueTable[valueBucket];

         for (ImmutableMapEntry<K, V> valueEntry = nextInValueBucket; valueEntry != null; valueEntry = valueEntry.getNextInValueBucket()) {
            checkNoConflict(!value.equals(valueEntry.getValue()), "value", entry, valueEntry);
         }

         ImmutableMapEntry<K, V> newEntry = (ImmutableMapEntry<K, V>)(nextInKeyBucket == null && nextInValueBucket == null
            ? new ImmutableMapEntry.TerminalEntry<>(key, value)
            : new RegularImmutableBiMap.NonTerminalBiMapEntry<>(key, value, nextInKeyBucket, nextInValueBucket));
         keyTable[keyBucket] = newEntry;
         valueTable[valueBucket] = newEntry;
         entries[i] = newEntry;
         hashCode += keyHash ^ valueHash;
      }

      this.keyTable = keyTable;
      this.valueTable = valueTable;
      this.entries = entries;
      this.hashCode = hashCode;
   }

   private static <K, V> ImmutableMapEntry<K, V>[] createEntryArray(int length) {
      return new ImmutableMapEntry[length];
   }

   @Nullable
   @Override
   public V get(@Nullable Object key) {
      if (key == null) {
         return null;
      } else {
         int bucket = Hashing.smear(key.hashCode()) & this.mask;

         for (ImmutableMapEntry<K, V> entry = this.keyTable[bucket]; entry != null; entry = entry.getNextInKeyBucket()) {
            if (key.equals(entry.getKey())) {
               return entry.getValue();
            }
         }

         return null;
      }
   }

   @Override
   ImmutableSet<Entry<K, V>> createEntrySet() {
      return new ImmutableMapEntrySet<K, V>() {
         @Override
         ImmutableMap<K, V> map() {
            return RegularImmutableBiMap.this;
         }

         @Override
         public UnmodifiableIterator<Entry<K, V>> iterator() {
            return this.asList().iterator();
         }

         @Override
         ImmutableList<Entry<K, V>> createAsList() {
            return new RegularImmutableAsList<>(this, RegularImmutableBiMap.this.entries);
         }

         @Override
         boolean isHashCodeFast() {
            return true;
         }

         @Override
         public int hashCode() {
            return RegularImmutableBiMap.this.hashCode;
         }
      };
   }

   @Override
   boolean isPartialView() {
      return false;
   }

   @Override
   public int size() {
      return this.entries.length;
   }

   @Override
   public ImmutableBiMap<V, K> inverse() {
      ImmutableBiMap<V, K> result = this.inverse;
      return result == null ? (this.inverse = new RegularImmutableBiMap.Inverse()) : result;
   }

   private final class Inverse extends ImmutableBiMap<V, K> {
      private Inverse() {
      }

      @Override
      public int size() {
         return this.inverse().size();
      }

      @Override
      public ImmutableBiMap<K, V> inverse() {
         return RegularImmutableBiMap.this;
      }

      @Override
      public K get(@Nullable Object value) {
         if (value == null) {
            return null;
         } else {
            int bucket = Hashing.smear(value.hashCode()) & RegularImmutableBiMap.this.mask;

            for (ImmutableMapEntry<K, V> entry = RegularImmutableBiMap.this.valueTable[bucket]; entry != null; entry = entry.getNextInValueBucket()) {
               if (value.equals(entry.getValue())) {
                  return entry.getKey();
               }
            }

            return null;
         }
      }

      @Override
      ImmutableSet<Entry<V, K>> createEntrySet() {
         return new RegularImmutableBiMap.Inverse.InverseEntrySet();
      }

      @Override
      boolean isPartialView() {
         return false;
      }

      @Override
      Object writeReplace() {
         return new RegularImmutableBiMap.InverseSerializedForm<>(RegularImmutableBiMap.this);
      }

      final class InverseEntrySet extends ImmutableMapEntrySet<V, K> {
         @Override
         ImmutableMap<V, K> map() {
            return Inverse.this;
         }

         @Override
         boolean isHashCodeFast() {
            return true;
         }

         @Override
         public int hashCode() {
            return RegularImmutableBiMap.this.hashCode;
         }

         @Override
         public UnmodifiableIterator<Entry<V, K>> iterator() {
            return (UnmodifiableIterator<Entry<V, K>>)this.asList().iterator();
         }

         @Override
         ImmutableList<Entry<V, K>> createAsList() {
            return new ImmutableAsList<Entry<V, K>>() {
               public Entry<V, K> get(int index) {
                  Entry<K, V> entry = RegularImmutableBiMap.this.entries[index];
                  return Maps.immutableEntry(entry.getValue(), entry.getKey());
               }

               @Override
               ImmutableCollection<Entry<V, K>> delegateCollection() {
                  return InverseEntrySet.this;
               }
            };
         }
      }
   }

   private static class InverseSerializedForm<K, V> implements Serializable {
      private final ImmutableBiMap<K, V> forward;
      private static final long serialVersionUID = 1L;

      InverseSerializedForm(ImmutableBiMap<K, V> forward) {
         this.forward = forward;
      }

      Object readResolve() {
         return this.forward.inverse();
      }
   }

   private static final class NonTerminalBiMapEntry<K, V> extends ImmutableMapEntry<K, V> {
      @Nullable
      private final ImmutableMapEntry<K, V> nextInKeyBucket;
      @Nullable
      private final ImmutableMapEntry<K, V> nextInValueBucket;

      NonTerminalBiMapEntry(K key, V value, @Nullable ImmutableMapEntry<K, V> nextInKeyBucket, @Nullable ImmutableMapEntry<K, V> nextInValueBucket) {
         super(key, value);
         this.nextInKeyBucket = nextInKeyBucket;
         this.nextInValueBucket = nextInValueBucket;
      }

      NonTerminalBiMapEntry(
         ImmutableMapEntry<K, V> contents, @Nullable ImmutableMapEntry<K, V> nextInKeyBucket, @Nullable ImmutableMapEntry<K, V> nextInValueBucket
      ) {
         super(contents);
         this.nextInKeyBucket = nextInKeyBucket;
         this.nextInValueBucket = nextInValueBucket;
      }

      @Nullable
      @Override
      ImmutableMapEntry<K, V> getNextInKeyBucket() {
         return this.nextInKeyBucket;
      }

      @Nullable
      @Override
      ImmutableMapEntry<K, V> getNextInValueBucket() {
         return this.nextInValueBucket;
      }
   }
}
