package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true, emulated = true)
final class RegularImmutableMap<K, V> extends ImmutableMap<K, V> {
   private final transient ImmutableMapEntry<K, V>[] entries;
   private final transient ImmutableMapEntry<K, V>[] table;
   private final transient int mask;
   private static final double MAX_LOAD_FACTOR = 1.2;
   private static final long serialVersionUID = 0L;

   RegularImmutableMap(ImmutableMapEntry.TerminalEntry<?, ?>... theEntries) {
      this(theEntries.length, theEntries);
   }

   RegularImmutableMap(int size, ImmutableMapEntry.TerminalEntry<?, ?>[] theEntries) {
      this.entries = this.createEntryArray(size);
      int tableSize = Hashing.closedTableSize(size, 1.2);
      this.table = this.createEntryArray(tableSize);
      this.mask = tableSize - 1;

      for (int entryIndex = 0; entryIndex < size; entryIndex++) {
         ImmutableMapEntry.TerminalEntry<K, V> entry = (ImmutableMapEntry.TerminalEntry<K, V>)theEntries[entryIndex];
         K key = entry.getKey();
         int tableIndex = Hashing.smear(key.hashCode()) & this.mask;
         ImmutableMapEntry<K, V> existing = this.table[tableIndex];
         ImmutableMapEntry<K, V> newEntry = (ImmutableMapEntry<K, V>)(existing == null ? entry : new RegularImmutableMap.NonTerminalMapEntry<>(entry, existing));
         this.table[tableIndex] = newEntry;
         this.entries[entryIndex] = newEntry;
         this.checkNoConflictInBucket(key, newEntry, existing);
      }
   }

   RegularImmutableMap(Entry<?, ?>[] theEntries) {
      int size = theEntries.length;
      this.entries = this.createEntryArray(size);
      int tableSize = Hashing.closedTableSize(size, 1.2);
      this.table = this.createEntryArray(tableSize);
      this.mask = tableSize - 1;

      for (int entryIndex = 0; entryIndex < size; entryIndex++) {
         Entry<K, V> entry = (Entry<K, V>)theEntries[entryIndex];
         K key = entry.getKey();
         V value = entry.getValue();
         CollectPreconditions.checkEntryNotNull(key, value);
         int tableIndex = Hashing.smear(key.hashCode()) & this.mask;
         ImmutableMapEntry<K, V> existing = this.table[tableIndex];
         ImmutableMapEntry<K, V> newEntry = (ImmutableMapEntry<K, V>)(existing == null
            ? new ImmutableMapEntry.TerminalEntry<>(key, value)
            : new RegularImmutableMap.NonTerminalMapEntry<>(key, value, existing));
         this.table[tableIndex] = newEntry;
         this.entries[entryIndex] = newEntry;
         this.checkNoConflictInBucket(key, newEntry, existing);
      }
   }

   private void checkNoConflictInBucket(K key, ImmutableMapEntry<K, V> entry, ImmutableMapEntry<K, V> bucketHead) {
      while (bucketHead != null) {
         checkNoConflict(!key.equals(bucketHead.getKey()), "key", entry, bucketHead);
         bucketHead = bucketHead.getNextInKeyBucket();
      }
   }

   private ImmutableMapEntry<K, V>[] createEntryArray(int size) {
      return new ImmutableMapEntry[size];
   }

   @Override
   public V get(@Nullable Object key) {
      if (key == null) {
         return null;
      } else {
         int index = Hashing.smear(key.hashCode()) & this.mask;

         for (ImmutableMapEntry<K, V> entry = this.table[index]; entry != null; entry = entry.getNextInKeyBucket()) {
            K candidateKey = entry.getKey();
            if (key.equals(candidateKey)) {
               return entry.getValue();
            }
         }

         return null;
      }
   }

   @Override
   public int size() {
      return this.entries.length;
   }

   @Override
   boolean isPartialView() {
      return false;
   }

   @Override
   ImmutableSet<Entry<K, V>> createEntrySet() {
      return new RegularImmutableMap.EntrySet();
   }

   private class EntrySet extends ImmutableMapEntrySet<K, V> {
      private EntrySet() {
      }

      @Override
      ImmutableMap<K, V> map() {
         return RegularImmutableMap.this;
      }

      @Override
      public UnmodifiableIterator<Entry<K, V>> iterator() {
         return this.asList().iterator();
      }

      @Override
      ImmutableList<Entry<K, V>> createAsList() {
         return new RegularImmutableAsList<>(this, RegularImmutableMap.this.entries);
      }
   }

   private static final class NonTerminalMapEntry<K, V> extends ImmutableMapEntry<K, V> {
      private final ImmutableMapEntry<K, V> nextInKeyBucket;

      NonTerminalMapEntry(K key, V value, ImmutableMapEntry<K, V> nextInKeyBucket) {
         super(key, value);
         this.nextInKeyBucket = nextInKeyBucket;
      }

      NonTerminalMapEntry(ImmutableMapEntry<K, V> contents, ImmutableMapEntry<K, V> nextInKeyBucket) {
         super(contents);
         this.nextInKeyBucket = nextInKeyBucket;
      }

      @Override
      ImmutableMapEntry<K, V> getNextInKeyBucket() {
         return this.nextInKeyBucket;
      }

      @Nullable
      @Override
      ImmutableMapEntry<K, V> getNextInValueBucket() {
         return null;
      }
   }
}
