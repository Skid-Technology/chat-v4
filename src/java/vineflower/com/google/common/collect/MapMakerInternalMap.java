package com.google.common.collect;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Equivalence;
import com.google.common.base.Preconditions;
import com.google.common.base.Ticker;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractQueue;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

class MapMakerInternalMap<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V>, Serializable {
   static final int MAXIMUM_CAPACITY = 1073741824;
   static final int MAX_SEGMENTS = 65536;
   static final int CONTAINS_VALUE_RETRIES = 3;
   static final int DRAIN_THRESHOLD = 63;
   static final int DRAIN_MAX = 16;
   static final long CLEANUP_EXECUTOR_DELAY_SECS = 60L;
   private static final Logger logger = Logger.getLogger(MapMakerInternalMap.class.getName());
   final transient int segmentMask;
   final transient int segmentShift;
   final transient MapMakerInternalMap.Segment<K, V>[] segments;
   final int concurrencyLevel;
   final Equivalence<Object> keyEquivalence;
   final Equivalence<Object> valueEquivalence;
   final MapMakerInternalMap.Strength keyStrength;
   final MapMakerInternalMap.Strength valueStrength;
   final int maximumSize;
   final long expireAfterAccessNanos;
   final long expireAfterWriteNanos;
   final Queue<MapMaker.RemovalNotification<K, V>> removalNotificationQueue;
   final MapMaker.RemovalListener<K, V> removalListener;
   final transient MapMakerInternalMap.EntryFactory entryFactory;
   final Ticker ticker;
   static final MapMakerInternalMap.ValueReference<Object, Object> UNSET = new MapMakerInternalMap.ValueReference<Object, Object>() {
      @Override
      public Object get() {
         return null;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<Object, Object> getEntry() {
         return null;
      }

      @Override
      public MapMakerInternalMap.ValueReference<Object, Object> copyFor(
         ReferenceQueue<Object> queue, @Nullable Object value, MapMakerInternalMap.ReferenceEntry<Object, Object> entry
      ) {
         return this;
      }

      @Override
      public boolean isComputingReference() {
         return false;
      }

      @Override
      public Object waitForValue() {
         return null;
      }

      @Override
      public void clear(MapMakerInternalMap.ValueReference<Object, Object> newValue) {
      }
   };
   static final Queue<? extends Object> DISCARDING_QUEUE = new AbstractQueue<Object>() {
      @Override
      public boolean offer(Object o) {
         return true;
      }

      @Override
      public Object peek() {
         return null;
      }

      @Override
      public Object poll() {
         return null;
      }

      @Override
      public int size() {
         return 0;
      }

      @Override
      public Iterator<Object> iterator() {
         return Iterators.emptyIterator();
      }
   };
   transient Set<K> keySet;
   transient Collection<V> values;
   transient Set<Entry<K, V>> entrySet;
   private static final long serialVersionUID = 5L;

   MapMakerInternalMap(MapMaker builder) {
      this.concurrencyLevel = Math.min(builder.getConcurrencyLevel(), 65536);
      this.keyStrength = builder.getKeyStrength();
      this.valueStrength = builder.getValueStrength();
      this.keyEquivalence = builder.getKeyEquivalence();
      this.valueEquivalence = this.valueStrength.defaultEquivalence();
      this.maximumSize = builder.maximumSize;
      this.expireAfterAccessNanos = builder.getExpireAfterAccessNanos();
      this.expireAfterWriteNanos = builder.getExpireAfterWriteNanos();
      this.entryFactory = MapMakerInternalMap.EntryFactory.getFactory(this.keyStrength, this.expires(), this.evictsBySize());
      this.ticker = builder.getTicker();
      this.removalListener = builder.getRemovalListener();
      this.removalNotificationQueue = (Queue<MapMaker.RemovalNotification<K, V>>)(this.removalListener == GenericMapMaker.NullListener.INSTANCE
         ? discardingQueue()
         : new ConcurrentLinkedQueue<>());
      int initialCapacity = Math.min(builder.getInitialCapacity(), 1073741824);
      if (this.evictsBySize()) {
         initialCapacity = Math.min(initialCapacity, this.maximumSize);
      }

      int segmentShift = 0;

      int segmentCount;
      for (segmentCount = 1; segmentCount < this.concurrencyLevel && (!this.evictsBySize() || segmentCount * 2 <= this.maximumSize); segmentCount <<= 1) {
         segmentShift++;
      }

      this.segmentShift = 32 - segmentShift;
      this.segmentMask = segmentCount - 1;
      this.segments = this.newSegmentArray(segmentCount);
      int segmentCapacity = initialCapacity / segmentCount;
      if (segmentCapacity * segmentCount < initialCapacity) {
         segmentCapacity++;
      }

      int segmentSize = 1;

      while (segmentSize < segmentCapacity) {
         segmentSize <<= 1;
      }

      if (this.evictsBySize()) {
         int maximumSegmentSize = this.maximumSize / segmentCount + 1;
         int remainder = this.maximumSize % segmentCount;

         for (int i = 0; i < this.segments.length; i++) {
            if (i == remainder) {
               maximumSegmentSize--;
            }

            this.segments[i] = this.createSegment(segmentSize, maximumSegmentSize);
         }
      } else {
         for (int i = 0; i < this.segments.length; i++) {
            this.segments[i] = this.createSegment(segmentSize, -1);
         }
      }
   }

   boolean evictsBySize() {
      return this.maximumSize != -1;
   }

   boolean expires() {
      return this.expiresAfterWrite() || this.expiresAfterAccess();
   }

   boolean expiresAfterWrite() {
      return this.expireAfterWriteNanos > 0L;
   }

   boolean expiresAfterAccess() {
      return this.expireAfterAccessNanos > 0L;
   }

   boolean usesKeyReferences() {
      return this.keyStrength != MapMakerInternalMap.Strength.STRONG;
   }

   boolean usesValueReferences() {
      return this.valueStrength != MapMakerInternalMap.Strength.STRONG;
   }

   static <K, V> MapMakerInternalMap.ValueReference<K, V> unset() {
      return (MapMakerInternalMap.ValueReference<K, V>)UNSET;
   }

   static <K, V> MapMakerInternalMap.ReferenceEntry<K, V> nullEntry() {
      return MapMakerInternalMap.NullEntry.INSTANCE;
   }

   static <E> Queue<E> discardingQueue() {
      return (Queue<E>)DISCARDING_QUEUE;
   }

   static int rehash(int h) {
      h += h << 15 ^ -12931;
      h ^= h >>> 10;
      h += h << 3;
      h ^= h >>> 6;
      h += (h << 2) + (h << 14);
      return h ^ h >>> 16;
   }

   @GuardedBy("Segment.this")
   @VisibleForTesting
   MapMakerInternalMap.ReferenceEntry<K, V> newEntry(K key, int hash, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> next) {
      return this.segmentFor(hash).newEntry(key, hash, next);
   }

   @GuardedBy("Segment.this")
   @VisibleForTesting
   MapMakerInternalMap.ReferenceEntry<K, V> copyEntry(MapMakerInternalMap.ReferenceEntry<K, V> original, MapMakerInternalMap.ReferenceEntry<K, V> newNext) {
      int hash = original.getHash();
      return this.segmentFor(hash).copyEntry(original, newNext);
   }

   @GuardedBy("Segment.this")
   @VisibleForTesting
   MapMakerInternalMap.ValueReference<K, V> newValueReference(MapMakerInternalMap.ReferenceEntry<K, V> entry, V value) {
      int hash = entry.getHash();
      return this.valueStrength.referenceValue(this.segmentFor(hash), entry, value);
   }

   int hash(Object key) {
      int h = this.keyEquivalence.hash(key);
      return rehash(h);
   }

   void reclaimValue(MapMakerInternalMap.ValueReference<K, V> valueReference) {
      MapMakerInternalMap.ReferenceEntry<K, V> entry = valueReference.getEntry();
      int hash = entry.getHash();
      this.segmentFor(hash).reclaimValue(entry.getKey(), hash, valueReference);
   }

   void reclaimKey(MapMakerInternalMap.ReferenceEntry<K, V> entry) {
      int hash = entry.getHash();
      this.segmentFor(hash).reclaimKey(entry, hash);
   }

   @VisibleForTesting
   boolean isLive(MapMakerInternalMap.ReferenceEntry<K, V> entry) {
      return this.segmentFor(entry.getHash()).getLiveValue(entry) != null;
   }

   MapMakerInternalMap.Segment<K, V> segmentFor(int hash) {
      return this.segments[hash >>> this.segmentShift & this.segmentMask];
   }

   MapMakerInternalMap.Segment<K, V> createSegment(int initialCapacity, int maxSegmentSize) {
      return new MapMakerInternalMap.Segment<>(this, initialCapacity, maxSegmentSize);
   }

   V getLiveValue(MapMakerInternalMap.ReferenceEntry<K, V> entry) {
      if (entry.getKey() == null) {
         return null;
      } else {
         V value = entry.getValueReference().get();
         if (value == null) {
            return null;
         } else {
            return this.expires() && this.isExpired(entry) ? null : value;
         }
      }
   }

   boolean isExpired(MapMakerInternalMap.ReferenceEntry<K, V> entry) {
      return this.isExpired(entry, this.ticker.read());
   }

   boolean isExpired(MapMakerInternalMap.ReferenceEntry<K, V> entry, long now) {
      return now - entry.getExpirationTime() > 0L;
   }

   @GuardedBy("Segment.this")
   static <K, V> void connectExpirables(MapMakerInternalMap.ReferenceEntry<K, V> previous, MapMakerInternalMap.ReferenceEntry<K, V> next) {
      previous.setNextExpirable(next);
      next.setPreviousExpirable(previous);
   }

   @GuardedBy("Segment.this")
   static <K, V> void nullifyExpirable(MapMakerInternalMap.ReferenceEntry<K, V> nulled) {
      MapMakerInternalMap.ReferenceEntry<K, V> nullEntry = nullEntry();
      nulled.setNextExpirable(nullEntry);
      nulled.setPreviousExpirable(nullEntry);
   }

   void processPendingNotifications() {
      MapMaker.RemovalNotification<K, V> notification;
      while ((notification = this.removalNotificationQueue.poll()) != null) {
         try {
            this.removalListener.onRemoval(notification);
         } catch (Exception var3) {
            logger.log(Level.WARNING, "Exception thrown by removal listener", (Throwable)var3);
         }
      }
   }

   @GuardedBy("Segment.this")
   static <K, V> void connectEvictables(MapMakerInternalMap.ReferenceEntry<K, V> previous, MapMakerInternalMap.ReferenceEntry<K, V> next) {
      previous.setNextEvictable(next);
      next.setPreviousEvictable(previous);
   }

   @GuardedBy("Segment.this")
   static <K, V> void nullifyEvictable(MapMakerInternalMap.ReferenceEntry<K, V> nulled) {
      MapMakerInternalMap.ReferenceEntry<K, V> nullEntry = nullEntry();
      nulled.setNextEvictable(nullEntry);
      nulled.setPreviousEvictable(nullEntry);
   }

   final MapMakerInternalMap.Segment<K, V>[] newSegmentArray(int ssize) {
      return new MapMakerInternalMap.Segment[ssize];
   }

   @Override
   public boolean isEmpty() {
      long sum = 0L;
      MapMakerInternalMap.Segment<K, V>[] segments = this.segments;

      for (int i = 0; i < segments.length; i++) {
         if (segments[i].count != 0) {
            return false;
         }

         sum += segments[i].modCount;
      }

      if (sum != 0L) {
         for (int i = 0; i < segments.length; i++) {
            if (segments[i].count != 0) {
               return false;
            }

            sum -= segments[i].modCount;
         }

         if (sum != 0L) {
            return false;
         }
      }

      return true;
   }

   @Override
   public int size() {
      MapMakerInternalMap.Segment<K, V>[] segments = this.segments;
      long sum = 0L;

      for (int i = 0; i < segments.length; i++) {
         sum += segments[i].count;
      }

      return Ints.saturatedCast(sum);
   }

   @Override
   public V get(@Nullable Object key) {
      if (key == null) {
         return null;
      } else {
         int hash = this.hash(key);
         return this.segmentFor(hash).get(key, hash);
      }
   }

   MapMakerInternalMap.ReferenceEntry<K, V> getEntry(@Nullable Object key) {
      if (key == null) {
         return null;
      } else {
         int hash = this.hash(key);
         return this.segmentFor(hash).getEntry(key, hash);
      }
   }

   @Override
   public boolean containsKey(@Nullable Object key) {
      if (key == null) {
         return false;
      } else {
         int hash = this.hash(key);
         return this.segmentFor(hash).containsKey(key, hash);
      }
   }

   @Override
   public boolean containsValue(@Nullable Object value) {
      if (value == null) {
         return false;
      } else {
         MapMakerInternalMap.Segment<K, V>[] segments = this.segments;
         long last = -1L;

         for (int i = 0; i < 3; i++) {
            long sum = 0L;

            for (MapMakerInternalMap.Segment<K, V> segment : segments) {
               int c = segment.count;
               AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> table = segment.table;

               for (int j = 0; j < table.length(); j++) {
                  for (MapMakerInternalMap.ReferenceEntry<K, V> e = table.get(j); e != null; e = e.getNext()) {
                     V v = segment.getLiveValue(e);
                     if (v != null && this.valueEquivalence.equivalent(value, v)) {
                        return true;
                     }
                  }
               }

               sum += segment.modCount;
            }

            if (sum == last) {
               break;
            }

            last = sum;
         }

         return false;
      }
   }

   @Override
   public V put(K key, V value) {
      Preconditions.checkNotNull(key);
      Preconditions.checkNotNull(value);
      int hash = this.hash(key);
      return this.segmentFor(hash).put(key, hash, value, false);
   }

   @Override
   public V putIfAbsent(K key, V value) {
      Preconditions.checkNotNull(key);
      Preconditions.checkNotNull(value);
      int hash = this.hash(key);
      return this.segmentFor(hash).put(key, hash, value, true);
   }

   @Override
   public void putAll(Map<? extends K, ? extends V> m) {
      for (Entry<? extends K, ? extends V> e : m.entrySet()) {
         this.put((K)e.getKey(), (V)e.getValue());
      }
   }

   @Override
   public V remove(@Nullable Object key) {
      if (key == null) {
         return null;
      } else {
         int hash = this.hash(key);
         return this.segmentFor(hash).remove(key, hash);
      }
   }

   @Override
   public boolean remove(@Nullable Object key, @Nullable Object value) {
      if (key != null && value != null) {
         int hash = this.hash(key);
         return this.segmentFor(hash).remove(key, hash, value);
      } else {
         return false;
      }
   }

   @Override
   public boolean replace(K key, @Nullable V oldValue, V newValue) {
      Preconditions.checkNotNull(key);
      Preconditions.checkNotNull(newValue);
      if (oldValue == null) {
         return false;
      } else {
         int hash = this.hash(key);
         return this.segmentFor(hash).replace(key, hash, oldValue, newValue);
      }
   }

   @Override
   public V replace(K key, V value) {
      Preconditions.checkNotNull(key);
      Preconditions.checkNotNull(value);
      int hash = this.hash(key);
      return this.segmentFor(hash).replace(key, hash, value);
   }

   @Override
   public void clear() {
      for (MapMakerInternalMap.Segment<K, V> segment : this.segments) {
         segment.clear();
      }
   }

   @Override
   public Set<K> keySet() {
      Set<K> ks = this.keySet;
      return ks != null ? ks : (this.keySet = new MapMakerInternalMap.KeySet());
   }

   @Override
   public Collection<V> values() {
      Collection<V> vs = this.values;
      return vs != null ? vs : (this.values = new MapMakerInternalMap.Values());
   }

   @Override
   public Set<Entry<K, V>> entrySet() {
      Set<Entry<K, V>> es = this.entrySet;
      return es != null ? es : (this.entrySet = new MapMakerInternalMap.EntrySet());
   }

   Object writeReplace() {
      return new MapMakerInternalMap.SerializationProxy<>(
         this.keyStrength,
         this.valueStrength,
         this.keyEquivalence,
         this.valueEquivalence,
         this.expireAfterWriteNanos,
         this.expireAfterAccessNanos,
         this.maximumSize,
         this.concurrencyLevel,
         this.removalListener,
         this
      );
   }

   abstract static class AbstractReferenceEntry<K, V> implements MapMakerInternalMap.ReferenceEntry<K, V> {
      @Override
      public MapMakerInternalMap.ValueReference<K, V> getValueReference() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setValueReference(MapMakerInternalMap.ValueReference<K, V> valueReference) {
         throw new UnsupportedOperationException();
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNext() {
         throw new UnsupportedOperationException();
      }

      @Override
      public int getHash() {
         throw new UnsupportedOperationException();
      }

      @Override
      public K getKey() {
         throw new UnsupportedOperationException();
      }

      @Override
      public long getExpirationTime() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setExpirationTime(long time) {
         throw new UnsupportedOperationException();
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNextExpirable() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setNextExpirable(MapMakerInternalMap.ReferenceEntry<K, V> next) {
         throw new UnsupportedOperationException();
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousExpirable() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setPreviousExpirable(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
         throw new UnsupportedOperationException();
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNextEvictable() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setNextEvictable(MapMakerInternalMap.ReferenceEntry<K, V> next) {
         throw new UnsupportedOperationException();
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousEvictable() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setPreviousEvictable(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
         throw new UnsupportedOperationException();
      }
   }

   abstract static class AbstractSerializationProxy<K, V> extends ForwardingConcurrentMap<K, V> implements Serializable {
      private static final long serialVersionUID = 3L;
      final MapMakerInternalMap.Strength keyStrength;
      final MapMakerInternalMap.Strength valueStrength;
      final Equivalence<Object> keyEquivalence;
      final Equivalence<Object> valueEquivalence;
      final long expireAfterWriteNanos;
      final long expireAfterAccessNanos;
      final int maximumSize;
      final int concurrencyLevel;
      final MapMaker.RemovalListener<? super K, ? super V> removalListener;
      transient ConcurrentMap<K, V> delegate;

      AbstractSerializationProxy(
         MapMakerInternalMap.Strength keyStrength,
         MapMakerInternalMap.Strength valueStrength,
         Equivalence<Object> keyEquivalence,
         Equivalence<Object> valueEquivalence,
         long expireAfterWriteNanos,
         long expireAfterAccessNanos,
         int maximumSize,
         int concurrencyLevel,
         MapMaker.RemovalListener<? super K, ? super V> removalListener,
         ConcurrentMap<K, V> delegate
      ) {
         this.keyStrength = keyStrength;
         this.valueStrength = valueStrength;
         this.keyEquivalence = keyEquivalence;
         this.valueEquivalence = valueEquivalence;
         this.expireAfterWriteNanos = expireAfterWriteNanos;
         this.expireAfterAccessNanos = expireAfterAccessNanos;
         this.maximumSize = maximumSize;
         this.concurrencyLevel = concurrencyLevel;
         this.removalListener = removalListener;
         this.delegate = delegate;
      }

      @Override
      protected ConcurrentMap<K, V> delegate() {
         return this.delegate;
      }

      void writeMapTo(ObjectOutputStream out) throws IOException {
         out.writeInt(this.delegate.size());

         for (Entry<K, V> entry : this.delegate.entrySet()) {
            out.writeObject(entry.getKey());
            out.writeObject(entry.getValue());
         }

         out.writeObject(null);
      }

      MapMaker readMapMaker(ObjectInputStream in) throws IOException {
         int size = in.readInt();
         MapMaker mapMaker = new MapMaker()
            .initialCapacity(size)
            .setKeyStrength(this.keyStrength)
            .setValueStrength(this.valueStrength)
            .keyEquivalence(this.keyEquivalence)
            .concurrencyLevel(this.concurrencyLevel);
         mapMaker.removalListener(this.removalListener);
         if (this.expireAfterWriteNanos > 0L) {
            mapMaker.expireAfterWrite(this.expireAfterWriteNanos, TimeUnit.NANOSECONDS);
         }

         if (this.expireAfterAccessNanos > 0L) {
            mapMaker.expireAfterAccess(this.expireAfterAccessNanos, TimeUnit.NANOSECONDS);
         }

         if (this.maximumSize != -1) {
            mapMaker.maximumSize(this.maximumSize);
         }

         return mapMaker;
      }

      void readEntries(ObjectInputStream in) throws IOException, ClassNotFoundException {
         while (true) {
            K key = (K)in.readObject();
            if (key == null) {
               return;
            }

            V value = (V)in.readObject();
            this.delegate.put(key, value);
         }
      }
   }

   static final class CleanupMapTask implements Runnable {
      final WeakReference<MapMakerInternalMap<?, ?>> mapReference;

      public CleanupMapTask(MapMakerInternalMap<?, ?> map) {
         this.mapReference = new WeakReference<>(map);
      }

      @Override
      public void run() {
         MapMakerInternalMap<?, ?> map = this.mapReference.get();
         if (map == null) {
            throw new CancellationException();
         } else {
            for (MapMakerInternalMap.Segment<?, ?> segment : map.segments) {
               segment.runCleanup();
            }
         }
      }
   }

   static enum EntryFactory {
      STRONG {
         @Override
         <K, V> MapMakerInternalMap.ReferenceEntry<K, V> newEntry(
            MapMakerInternalMap.Segment<K, V> segment, K key, int hash, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> next
         ) {
            return new MapMakerInternalMap.StrongEntry<>(key, hash, next);
         }
      },
      STRONG_EXPIRABLE {
         @Override
         <K, V> MapMakerInternalMap.ReferenceEntry<K, V> newEntry(
            MapMakerInternalMap.Segment<K, V> segment, K key, int hash, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> next
         ) {
            return new MapMakerInternalMap.StrongExpirableEntry<>(key, hash, next);
         }

         @Override
         <K, V> MapMakerInternalMap.ReferenceEntry<K, V> copyEntry(
            MapMakerInternalMap.Segment<K, V> segment, MapMakerInternalMap.ReferenceEntry<K, V> original, MapMakerInternalMap.ReferenceEntry<K, V> newNext
         ) {
            MapMakerInternalMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
            this.copyExpirableEntry(original, newEntry);
            return newEntry;
         }
      },
      STRONG_EVICTABLE {
         @Override
         <K, V> MapMakerInternalMap.ReferenceEntry<K, V> newEntry(
            MapMakerInternalMap.Segment<K, V> segment, K key, int hash, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> next
         ) {
            return new MapMakerInternalMap.StrongEvictableEntry<>(key, hash, next);
         }

         @Override
         <K, V> MapMakerInternalMap.ReferenceEntry<K, V> copyEntry(
            MapMakerInternalMap.Segment<K, V> segment, MapMakerInternalMap.ReferenceEntry<K, V> original, MapMakerInternalMap.ReferenceEntry<K, V> newNext
         ) {
            MapMakerInternalMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
            this.copyEvictableEntry(original, newEntry);
            return newEntry;
         }
      },
      STRONG_EXPIRABLE_EVICTABLE {
         @Override
         <K, V> MapMakerInternalMap.ReferenceEntry<K, V> newEntry(
            MapMakerInternalMap.Segment<K, V> segment, K key, int hash, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> next
         ) {
            return new MapMakerInternalMap.StrongExpirableEvictableEntry<>(key, hash, next);
         }

         @Override
         <K, V> MapMakerInternalMap.ReferenceEntry<K, V> copyEntry(
            MapMakerInternalMap.Segment<K, V> segment, MapMakerInternalMap.ReferenceEntry<K, V> original, MapMakerInternalMap.ReferenceEntry<K, V> newNext
         ) {
            MapMakerInternalMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
            this.copyExpirableEntry(original, newEntry);
            this.copyEvictableEntry(original, newEntry);
            return newEntry;
         }
      },
      WEAK {
         @Override
         <K, V> MapMakerInternalMap.ReferenceEntry<K, V> newEntry(
            MapMakerInternalMap.Segment<K, V> segment, K key, int hash, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> next
         ) {
            return new MapMakerInternalMap.WeakEntry<>(segment.keyReferenceQueue, key, hash, next);
         }
      },
      WEAK_EXPIRABLE {
         @Override
         <K, V> MapMakerInternalMap.ReferenceEntry<K, V> newEntry(
            MapMakerInternalMap.Segment<K, V> segment, K key, int hash, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> next
         ) {
            return new MapMakerInternalMap.WeakExpirableEntry<>(segment.keyReferenceQueue, key, hash, next);
         }

         @Override
         <K, V> MapMakerInternalMap.ReferenceEntry<K, V> copyEntry(
            MapMakerInternalMap.Segment<K, V> segment, MapMakerInternalMap.ReferenceEntry<K, V> original, MapMakerInternalMap.ReferenceEntry<K, V> newNext
         ) {
            MapMakerInternalMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
            this.copyExpirableEntry(original, newEntry);
            return newEntry;
         }
      },
      WEAK_EVICTABLE {
         @Override
         <K, V> MapMakerInternalMap.ReferenceEntry<K, V> newEntry(
            MapMakerInternalMap.Segment<K, V> segment, K key, int hash, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> next
         ) {
            return new MapMakerInternalMap.WeakEvictableEntry<>(segment.keyReferenceQueue, key, hash, next);
         }

         @Override
         <K, V> MapMakerInternalMap.ReferenceEntry<K, V> copyEntry(
            MapMakerInternalMap.Segment<K, V> segment, MapMakerInternalMap.ReferenceEntry<K, V> original, MapMakerInternalMap.ReferenceEntry<K, V> newNext
         ) {
            MapMakerInternalMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
            this.copyEvictableEntry(original, newEntry);
            return newEntry;
         }
      },
      WEAK_EXPIRABLE_EVICTABLE {
         @Override
         <K, V> MapMakerInternalMap.ReferenceEntry<K, V> newEntry(
            MapMakerInternalMap.Segment<K, V> segment, K key, int hash, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> next
         ) {
            return new MapMakerInternalMap.WeakExpirableEvictableEntry<>(segment.keyReferenceQueue, key, hash, next);
         }

         @Override
         <K, V> MapMakerInternalMap.ReferenceEntry<K, V> copyEntry(
            MapMakerInternalMap.Segment<K, V> segment, MapMakerInternalMap.ReferenceEntry<K, V> original, MapMakerInternalMap.ReferenceEntry<K, V> newNext
         ) {
            MapMakerInternalMap.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
            this.copyExpirableEntry(original, newEntry);
            this.copyEvictableEntry(original, newEntry);
            return newEntry;
         }
      };

      static final int EXPIRABLE_MASK = 1;
      static final int EVICTABLE_MASK = 2;
      static final MapMakerInternalMap.EntryFactory[][] factories = new MapMakerInternalMap.EntryFactory[][]{
         {STRONG, STRONG_EXPIRABLE, STRONG_EVICTABLE, STRONG_EXPIRABLE_EVICTABLE},
         new MapMakerInternalMap.EntryFactory[0],
         {WEAK, WEAK_EXPIRABLE, WEAK_EVICTABLE, WEAK_EXPIRABLE_EVICTABLE}
      };

      private EntryFactory() {
      }

      static MapMakerInternalMap.EntryFactory getFactory(MapMakerInternalMap.Strength keyStrength, boolean expireAfterWrite, boolean evictsBySize) {
         int flags = (expireAfterWrite ? 1 : 0) | (evictsBySize ? 2 : 0);
         return factories[keyStrength.ordinal()][flags];
      }

      abstract <K, V> MapMakerInternalMap.ReferenceEntry<K, V> newEntry(
         MapMakerInternalMap.Segment<K, V> var1, K var2, int var3, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> var4
      );

      @GuardedBy("Segment.this")
      <K, V> MapMakerInternalMap.ReferenceEntry<K, V> copyEntry(
         MapMakerInternalMap.Segment<K, V> segment, MapMakerInternalMap.ReferenceEntry<K, V> original, MapMakerInternalMap.ReferenceEntry<K, V> newNext
      ) {
         return this.newEntry(segment, original.getKey(), original.getHash(), newNext);
      }

      @GuardedBy("Segment.this")
      <K, V> void copyExpirableEntry(MapMakerInternalMap.ReferenceEntry<K, V> original, MapMakerInternalMap.ReferenceEntry<K, V> newEntry) {
         newEntry.setExpirationTime(original.getExpirationTime());
         MapMakerInternalMap.connectExpirables(original.getPreviousExpirable(), newEntry);
         MapMakerInternalMap.connectExpirables(newEntry, original.getNextExpirable());
         MapMakerInternalMap.nullifyExpirable(original);
      }

      @GuardedBy("Segment.this")
      <K, V> void copyEvictableEntry(MapMakerInternalMap.ReferenceEntry<K, V> original, MapMakerInternalMap.ReferenceEntry<K, V> newEntry) {
         MapMakerInternalMap.connectEvictables(original.getPreviousEvictable(), newEntry);
         MapMakerInternalMap.connectEvictables(newEntry, original.getNextEvictable());
         MapMakerInternalMap.nullifyEvictable(original);
      }
   }

   final class EntryIterator extends MapMakerInternalMap<K, V>.HashIterator<Entry<K, V>> {
      public Entry<K, V> next() {
         return this.nextEntry();
      }
   }

   final class EntrySet extends AbstractSet<Entry<K, V>> {
      @Override
      public Iterator<Entry<K, V>> iterator() {
         return MapMakerInternalMap.this.new EntryIterator();
      }

      @Override
      public boolean contains(Object o) {
         if (!(o instanceof Entry)) {
            return false;
         } else {
            Entry<?, ?> e = (Entry<?, ?>)o;
            Object key = e.getKey();
            if (key == null) {
               return false;
            } else {
               V v = MapMakerInternalMap.this.get(key);
               return v != null && MapMakerInternalMap.this.valueEquivalence.equivalent(e.getValue(), v);
            }
         }
      }

      @Override
      public boolean remove(Object o) {
         if (!(o instanceof Entry)) {
            return false;
         } else {
            Entry<?, ?> e = (Entry<?, ?>)o;
            Object key = e.getKey();
            return key != null && MapMakerInternalMap.this.remove(key, e.getValue());
         }
      }

      @Override
      public int size() {
         return MapMakerInternalMap.this.size();
      }

      @Override
      public boolean isEmpty() {
         return MapMakerInternalMap.this.isEmpty();
      }

      @Override
      public void clear() {
         MapMakerInternalMap.this.clear();
      }
   }

   static final class EvictionQueue<K, V> extends AbstractQueue<MapMakerInternalMap.ReferenceEntry<K, V>> {
      final MapMakerInternalMap.ReferenceEntry<K, V> head = new MapMakerInternalMap.AbstractReferenceEntry<K, V>() {
         MapMakerInternalMap.ReferenceEntry<K, V> nextEvictable = this;
         MapMakerInternalMap.ReferenceEntry<K, V> previousEvictable = this;

         @Override
         public MapMakerInternalMap.ReferenceEntry<K, V> getNextEvictable() {
            return this.nextEvictable;
         }

         @Override
         public void setNextEvictable(MapMakerInternalMap.ReferenceEntry<K, V> next) {
            this.nextEvictable = next;
         }

         @Override
         public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousEvictable() {
            return this.previousEvictable;
         }

         @Override
         public void setPreviousEvictable(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
            this.previousEvictable = previous;
         }
      };

      public boolean offer(MapMakerInternalMap.ReferenceEntry<K, V> entry) {
         MapMakerInternalMap.connectEvictables(entry.getPreviousEvictable(), entry.getNextEvictable());
         MapMakerInternalMap.connectEvictables(this.head.getPreviousEvictable(), entry);
         MapMakerInternalMap.connectEvictables(entry, this.head);
         return true;
      }

      public MapMakerInternalMap.ReferenceEntry<K, V> peek() {
         MapMakerInternalMap.ReferenceEntry<K, V> next = this.head.getNextEvictable();
         return next == this.head ? null : next;
      }

      public MapMakerInternalMap.ReferenceEntry<K, V> poll() {
         MapMakerInternalMap.ReferenceEntry<K, V> next = this.head.getNextEvictable();
         if (next == this.head) {
            return null;
         } else {
            this.remove(next);
            return next;
         }
      }

      @Override
      public boolean remove(Object o) {
         MapMakerInternalMap.ReferenceEntry<K, V> e = (MapMakerInternalMap.ReferenceEntry<K, V>)o;
         MapMakerInternalMap.ReferenceEntry<K, V> previous = e.getPreviousEvictable();
         MapMakerInternalMap.ReferenceEntry<K, V> next = e.getNextEvictable();
         MapMakerInternalMap.connectEvictables(previous, next);
         MapMakerInternalMap.nullifyEvictable(e);
         return next != MapMakerInternalMap.NullEntry.INSTANCE;
      }

      @Override
      public boolean contains(Object o) {
         MapMakerInternalMap.ReferenceEntry<K, V> e = (MapMakerInternalMap.ReferenceEntry<K, V>)o;
         return e.getNextEvictable() != MapMakerInternalMap.NullEntry.INSTANCE;
      }

      @Override
      public boolean isEmpty() {
         return this.head.getNextEvictable() == this.head;
      }

      @Override
      public int size() {
         int size = 0;

         for (MapMakerInternalMap.ReferenceEntry<K, V> e = this.head.getNextEvictable(); e != this.head; e = e.getNextEvictable()) {
            size++;
         }

         return size;
      }

      @Override
      public void clear() {
         MapMakerInternalMap.ReferenceEntry<K, V> e = this.head.getNextEvictable();

         while (e != this.head) {
            MapMakerInternalMap.ReferenceEntry<K, V> next = e.getNextEvictable();
            MapMakerInternalMap.nullifyEvictable(e);
            e = next;
         }

         this.head.setNextEvictable(this.head);
         this.head.setPreviousEvictable(this.head);
      }

      @Override
      public Iterator<MapMakerInternalMap.ReferenceEntry<K, V>> iterator() {
         return new AbstractSequentialIterator<MapMakerInternalMap.ReferenceEntry<K, V>>(this.peek()) {
            protected MapMakerInternalMap.ReferenceEntry<K, V> computeNext(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
               MapMakerInternalMap.ReferenceEntry<K, V> next = previous.getNextEvictable();
               return next == EvictionQueue.this.head ? null : next;
            }
         };
      }
   }

   static final class ExpirationQueue<K, V> extends AbstractQueue<MapMakerInternalMap.ReferenceEntry<K, V>> {
      final MapMakerInternalMap.ReferenceEntry<K, V> head = new MapMakerInternalMap.AbstractReferenceEntry<K, V>() {
         MapMakerInternalMap.ReferenceEntry<K, V> nextExpirable = this;
         MapMakerInternalMap.ReferenceEntry<K, V> previousExpirable = this;

         @Override
         public long getExpirationTime() {
            return Long.MAX_VALUE;
         }

         @Override
         public void setExpirationTime(long time) {
         }

         @Override
         public MapMakerInternalMap.ReferenceEntry<K, V> getNextExpirable() {
            return this.nextExpirable;
         }

         @Override
         public void setNextExpirable(MapMakerInternalMap.ReferenceEntry<K, V> next) {
            this.nextExpirable = next;
         }

         @Override
         public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousExpirable() {
            return this.previousExpirable;
         }

         @Override
         public void setPreviousExpirable(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
            this.previousExpirable = previous;
         }
      };

      public boolean offer(MapMakerInternalMap.ReferenceEntry<K, V> entry) {
         MapMakerInternalMap.connectExpirables(entry.getPreviousExpirable(), entry.getNextExpirable());
         MapMakerInternalMap.connectExpirables(this.head.getPreviousExpirable(), entry);
         MapMakerInternalMap.connectExpirables(entry, this.head);
         return true;
      }

      public MapMakerInternalMap.ReferenceEntry<K, V> peek() {
         MapMakerInternalMap.ReferenceEntry<K, V> next = this.head.getNextExpirable();
         return next == this.head ? null : next;
      }

      public MapMakerInternalMap.ReferenceEntry<K, V> poll() {
         MapMakerInternalMap.ReferenceEntry<K, V> next = this.head.getNextExpirable();
         if (next == this.head) {
            return null;
         } else {
            this.remove(next);
            return next;
         }
      }

      @Override
      public boolean remove(Object o) {
         MapMakerInternalMap.ReferenceEntry<K, V> e = (MapMakerInternalMap.ReferenceEntry<K, V>)o;
         MapMakerInternalMap.ReferenceEntry<K, V> previous = e.getPreviousExpirable();
         MapMakerInternalMap.ReferenceEntry<K, V> next = e.getNextExpirable();
         MapMakerInternalMap.connectExpirables(previous, next);
         MapMakerInternalMap.nullifyExpirable(e);
         return next != MapMakerInternalMap.NullEntry.INSTANCE;
      }

      @Override
      public boolean contains(Object o) {
         MapMakerInternalMap.ReferenceEntry<K, V> e = (MapMakerInternalMap.ReferenceEntry<K, V>)o;
         return e.getNextExpirable() != MapMakerInternalMap.NullEntry.INSTANCE;
      }

      @Override
      public boolean isEmpty() {
         return this.head.getNextExpirable() == this.head;
      }

      @Override
      public int size() {
         int size = 0;

         for (MapMakerInternalMap.ReferenceEntry<K, V> e = this.head.getNextExpirable(); e != this.head; e = e.getNextExpirable()) {
            size++;
         }

         return size;
      }

      @Override
      public void clear() {
         MapMakerInternalMap.ReferenceEntry<K, V> e = this.head.getNextExpirable();

         while (e != this.head) {
            MapMakerInternalMap.ReferenceEntry<K, V> next = e.getNextExpirable();
            MapMakerInternalMap.nullifyExpirable(e);
            e = next;
         }

         this.head.setNextExpirable(this.head);
         this.head.setPreviousExpirable(this.head);
      }

      @Override
      public Iterator<MapMakerInternalMap.ReferenceEntry<K, V>> iterator() {
         return new AbstractSequentialIterator<MapMakerInternalMap.ReferenceEntry<K, V>>(this.peek()) {
            protected MapMakerInternalMap.ReferenceEntry<K, V> computeNext(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
               MapMakerInternalMap.ReferenceEntry<K, V> next = previous.getNextExpirable();
               return next == ExpirationQueue.this.head ? null : next;
            }
         };
      }
   }

   abstract class HashIterator<E> implements Iterator<E> {
      int nextSegmentIndex = MapMakerInternalMap.this.segments.length - 1;
      int nextTableIndex = -1;
      MapMakerInternalMap.Segment<K, V> currentSegment;
      AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> currentTable;
      MapMakerInternalMap.ReferenceEntry<K, V> nextEntry;
      MapMakerInternalMap<K, V>.WriteThroughEntry nextExternal;
      MapMakerInternalMap<K, V>.WriteThroughEntry lastReturned;

      HashIterator() {
         this.advance();
      }

      @Override
      public abstract E next();

      final void advance() {
         this.nextExternal = null;
         if (!this.nextInChain()) {
            if (!this.nextInTable()) {
               while (this.nextSegmentIndex >= 0) {
                  this.currentSegment = MapMakerInternalMap.this.segments[this.nextSegmentIndex--];
                  if (this.currentSegment.count != 0) {
                     this.currentTable = this.currentSegment.table;
                     this.nextTableIndex = this.currentTable.length() - 1;
                     if (this.nextInTable()) {
                        return;
                     }
                  }
               }
            }
         }
      }

      boolean nextInChain() {
         if (this.nextEntry != null) {
            for (this.nextEntry = this.nextEntry.getNext(); this.nextEntry != null; this.nextEntry = this.nextEntry.getNext()) {
               if (this.advanceTo(this.nextEntry)) {
                  return true;
               }
            }
         }

         return false;
      }

      boolean nextInTable() {
         while (this.nextTableIndex >= 0) {
            if ((this.nextEntry = this.currentTable.get(this.nextTableIndex--)) != null && (this.advanceTo(this.nextEntry) || this.nextInChain())) {
               return true;
            }
         }

         return false;
      }

      boolean advanceTo(MapMakerInternalMap.ReferenceEntry<K, V> entry) {
         boolean var4;
         try {
            K key = entry.getKey();
            V value = MapMakerInternalMap.this.getLiveValue(entry);
            if (value == null) {
               return false;
            }

            this.nextExternal = MapMakerInternalMap.this.new WriteThroughEntry(key, value);
            var4 = true;
         } finally {
            this.currentSegment.postReadCleanup();
         }

         return var4;
      }

      @Override
      public boolean hasNext() {
         return this.nextExternal != null;
      }

      MapMakerInternalMap<K, V>.WriteThroughEntry nextEntry() {
         if (this.nextExternal == null) {
            throw new NoSuchElementException();
         } else {
            this.lastReturned = this.nextExternal;
            this.advance();
            return this.lastReturned;
         }
      }

      @Override
      public void remove() {
         CollectPreconditions.checkRemove(this.lastReturned != null);
         MapMakerInternalMap.this.remove(this.lastReturned.getKey());
         this.lastReturned = null;
      }
   }

   final class KeyIterator extends MapMakerInternalMap<K, V>.HashIterator<K> {
      @Override
      public K next() {
         return this.nextEntry().getKey();
      }
   }

   final class KeySet extends AbstractSet<K> {
      @Override
      public Iterator<K> iterator() {
         return MapMakerInternalMap.this.new KeyIterator();
      }

      @Override
      public int size() {
         return MapMakerInternalMap.this.size();
      }

      @Override
      public boolean isEmpty() {
         return MapMakerInternalMap.this.isEmpty();
      }

      @Override
      public boolean contains(Object o) {
         return MapMakerInternalMap.this.containsKey(o);
      }

      @Override
      public boolean remove(Object o) {
         return MapMakerInternalMap.this.remove(o) != null;
      }

      @Override
      public void clear() {
         MapMakerInternalMap.this.clear();
      }
   }

   private static enum NullEntry implements MapMakerInternalMap.ReferenceEntry<Object, Object> {
      INSTANCE;

      @Override
      public MapMakerInternalMap.ValueReference<Object, Object> getValueReference() {
         return null;
      }

      @Override
      public void setValueReference(MapMakerInternalMap.ValueReference<Object, Object> valueReference) {
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<Object, Object> getNext() {
         return null;
      }

      @Override
      public int getHash() {
         return 0;
      }

      @Override
      public Object getKey() {
         return null;
      }

      @Override
      public long getExpirationTime() {
         return 0L;
      }

      @Override
      public void setExpirationTime(long time) {
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<Object, Object> getNextExpirable() {
         return this;
      }

      @Override
      public void setNextExpirable(MapMakerInternalMap.ReferenceEntry<Object, Object> next) {
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<Object, Object> getPreviousExpirable() {
         return this;
      }

      @Override
      public void setPreviousExpirable(MapMakerInternalMap.ReferenceEntry<Object, Object> previous) {
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<Object, Object> getNextEvictable() {
         return this;
      }

      @Override
      public void setNextEvictable(MapMakerInternalMap.ReferenceEntry<Object, Object> next) {
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<Object, Object> getPreviousEvictable() {
         return this;
      }

      @Override
      public void setPreviousEvictable(MapMakerInternalMap.ReferenceEntry<Object, Object> previous) {
      }
   }

   interface ReferenceEntry<K, V> {
      MapMakerInternalMap.ValueReference<K, V> getValueReference();

      void setValueReference(MapMakerInternalMap.ValueReference<K, V> var1);

      MapMakerInternalMap.ReferenceEntry<K, V> getNext();

      int getHash();

      K getKey();

      long getExpirationTime();

      void setExpirationTime(long var1);

      MapMakerInternalMap.ReferenceEntry<K, V> getNextExpirable();

      void setNextExpirable(MapMakerInternalMap.ReferenceEntry<K, V> var1);

      MapMakerInternalMap.ReferenceEntry<K, V> getPreviousExpirable();

      void setPreviousExpirable(MapMakerInternalMap.ReferenceEntry<K, V> var1);

      MapMakerInternalMap.ReferenceEntry<K, V> getNextEvictable();

      void setNextEvictable(MapMakerInternalMap.ReferenceEntry<K, V> var1);

      MapMakerInternalMap.ReferenceEntry<K, V> getPreviousEvictable();

      void setPreviousEvictable(MapMakerInternalMap.ReferenceEntry<K, V> var1);
   }

   static class Segment<K, V> extends ReentrantLock {
      final MapMakerInternalMap<K, V> map;
      volatile int count;
      int modCount;
      int threshold;
      volatile AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> table;
      final int maxSegmentSize;
      final ReferenceQueue<K> keyReferenceQueue;
      final ReferenceQueue<V> valueReferenceQueue;
      final Queue<MapMakerInternalMap.ReferenceEntry<K, V>> recencyQueue;
      final AtomicInteger readCount = new AtomicInteger();
      @GuardedBy("Segment.this")
      final Queue<MapMakerInternalMap.ReferenceEntry<K, V>> evictionQueue;
      @GuardedBy("Segment.this")
      final Queue<MapMakerInternalMap.ReferenceEntry<K, V>> expirationQueue;

      Segment(MapMakerInternalMap<K, V> map, int initialCapacity, int maxSegmentSize) {
         this.map = map;
         this.maxSegmentSize = maxSegmentSize;
         this.initTable(this.newEntryArray(initialCapacity));
         this.keyReferenceQueue = map.usesKeyReferences() ? new ReferenceQueue<>() : null;
         this.valueReferenceQueue = map.usesValueReferences() ? new ReferenceQueue<>() : null;
         this.recencyQueue = (Queue<MapMakerInternalMap.ReferenceEntry<K, V>>)(!map.evictsBySize() && !map.expiresAfterAccess()
            ? MapMakerInternalMap.discardingQueue()
            : new ConcurrentLinkedQueue<>());
         this.evictionQueue = (Queue<MapMakerInternalMap.ReferenceEntry<K, V>>)(map.evictsBySize()
            ? new MapMakerInternalMap.EvictionQueue<>()
            : MapMakerInternalMap.discardingQueue());
         this.expirationQueue = (Queue<MapMakerInternalMap.ReferenceEntry<K, V>>)(map.expires()
            ? new MapMakerInternalMap.ExpirationQueue<>()
            : MapMakerInternalMap.discardingQueue());
      }

      AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> newEntryArray(int size) {
         return new AtomicReferenceArray<>(size);
      }

      void initTable(AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> newTable) {
         this.threshold = newTable.length() * 3 / 4;
         if (this.threshold == this.maxSegmentSize) {
            this.threshold++;
         }

         this.table = newTable;
      }

      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> newEntry(K key, int hash, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> next) {
         return this.map.entryFactory.newEntry(this, key, hash, next);
      }

      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> copyEntry(MapMakerInternalMap.ReferenceEntry<K, V> original, MapMakerInternalMap.ReferenceEntry<K, V> newNext) {
         if (original.getKey() == null) {
            return null;
         } else {
            MapMakerInternalMap.ValueReference<K, V> valueReference = original.getValueReference();
            V value = valueReference.get();
            if (value == null && !valueReference.isComputingReference()) {
               return null;
            } else {
               MapMakerInternalMap.ReferenceEntry<K, V> newEntry = this.map.entryFactory.copyEntry(this, original, newNext);
               newEntry.setValueReference(valueReference.copyFor(this.valueReferenceQueue, value, newEntry));
               return newEntry;
            }
         }
      }

      @GuardedBy("Segment.this")
      void setValue(MapMakerInternalMap.ReferenceEntry<K, V> entry, V value) {
         MapMakerInternalMap.ValueReference<K, V> valueReference = this.map.valueStrength.referenceValue(this, entry, value);
         entry.setValueReference(valueReference);
         this.recordWrite(entry);
      }

      void tryDrainReferenceQueues() {
         if (this.tryLock()) {
            try {
               this.drainReferenceQueues();
            } finally {
               this.unlock();
            }
         }
      }

      @GuardedBy("Segment.this")
      void drainReferenceQueues() {
         if (this.map.usesKeyReferences()) {
            this.drainKeyReferenceQueue();
         }

         if (this.map.usesValueReferences()) {
            this.drainValueReferenceQueue();
         }
      }

      @GuardedBy("Segment.this")
      void drainKeyReferenceQueue() {
         int i = 0;

         Reference<? extends K> ref;
         while ((ref = this.keyReferenceQueue.poll()) != null) {
            MapMakerInternalMap.ReferenceEntry<K, V> entry = (MapMakerInternalMap.ReferenceEntry<K, V>)ref;
            this.map.reclaimKey(entry);
            if (++i == 16) {
               break;
            }
         }
      }

      @GuardedBy("Segment.this")
      void drainValueReferenceQueue() {
         int i = 0;

         Reference<? extends V> ref;
         while ((ref = this.valueReferenceQueue.poll()) != null) {
            MapMakerInternalMap.ValueReference<K, V> valueReference = (MapMakerInternalMap.ValueReference<K, V>)ref;
            this.map.reclaimValue(valueReference);
            if (++i == 16) {
               break;
            }
         }
      }

      void clearReferenceQueues() {
         if (this.map.usesKeyReferences()) {
            this.clearKeyReferenceQueue();
         }

         if (this.map.usesValueReferences()) {
            this.clearValueReferenceQueue();
         }
      }

      void clearKeyReferenceQueue() {
         while (this.keyReferenceQueue.poll() != null) {
         }
      }

      void clearValueReferenceQueue() {
         while (this.valueReferenceQueue.poll() != null) {
         }
      }

      void recordRead(MapMakerInternalMap.ReferenceEntry<K, V> entry) {
         if (this.map.expiresAfterAccess()) {
            this.recordExpirationTime(entry, this.map.expireAfterAccessNanos);
         }

         this.recencyQueue.add(entry);
      }

      @GuardedBy("Segment.this")
      void recordLockedRead(MapMakerInternalMap.ReferenceEntry<K, V> entry) {
         this.evictionQueue.add(entry);
         if (this.map.expiresAfterAccess()) {
            this.recordExpirationTime(entry, this.map.expireAfterAccessNanos);
            this.expirationQueue.add(entry);
         }
      }

      @GuardedBy("Segment.this")
      void recordWrite(MapMakerInternalMap.ReferenceEntry<K, V> entry) {
         this.drainRecencyQueue();
         this.evictionQueue.add(entry);
         if (this.map.expires()) {
            long expiration = this.map.expiresAfterAccess() ? this.map.expireAfterAccessNanos : this.map.expireAfterWriteNanos;
            this.recordExpirationTime(entry, expiration);
            this.expirationQueue.add(entry);
         }
      }

      @GuardedBy("Segment.this")
      void drainRecencyQueue() {
         MapMakerInternalMap.ReferenceEntry<K, V> e;
         while ((e = this.recencyQueue.poll()) != null) {
            if (this.evictionQueue.contains(e)) {
               this.evictionQueue.add(e);
            }

            if (this.map.expiresAfterAccess() && this.expirationQueue.contains(e)) {
               this.expirationQueue.add(e);
            }
         }
      }

      void recordExpirationTime(MapMakerInternalMap.ReferenceEntry<K, V> entry, long expirationNanos) {
         entry.setExpirationTime(this.map.ticker.read() + expirationNanos);
      }

      void tryExpireEntries() {
         if (this.tryLock()) {
            try {
               this.expireEntries();
            } finally {
               this.unlock();
            }
         }
      }

      @GuardedBy("Segment.this")
      void expireEntries() {
         this.drainRecencyQueue();
         if (!this.expirationQueue.isEmpty()) {
            long now = this.map.ticker.read();

            MapMakerInternalMap.ReferenceEntry<K, V> e;
            while ((e = this.expirationQueue.peek()) != null && this.map.isExpired(e, now)) {
               if (!this.removeEntry(e, e.getHash(), MapMaker.RemovalCause.EXPIRED)) {
                  throw new AssertionError();
               }
            }
         }
      }

      void enqueueNotification(MapMakerInternalMap.ReferenceEntry<K, V> entry, MapMaker.RemovalCause cause) {
         this.enqueueNotification(entry.getKey(), entry.getHash(), entry.getValueReference().get(), cause);
      }

      void enqueueNotification(@Nullable K key, int hash, @Nullable V value, MapMaker.RemovalCause cause) {
         if (this.map.removalNotificationQueue != MapMakerInternalMap.DISCARDING_QUEUE) {
            MapMaker.RemovalNotification<K, V> notification = new MapMaker.RemovalNotification<>(key, value, cause);
            this.map.removalNotificationQueue.offer(notification);
         }
      }

      @GuardedBy("Segment.this")
      boolean evictEntries() {
         if (this.map.evictsBySize() && this.count >= this.maxSegmentSize) {
            this.drainRecencyQueue();
            MapMakerInternalMap.ReferenceEntry<K, V> e = this.evictionQueue.remove();
            if (!this.removeEntry(e, e.getHash(), MapMaker.RemovalCause.SIZE)) {
               throw new AssertionError();
            } else {
               return true;
            }
         } else {
            return false;
         }
      }

      MapMakerInternalMap.ReferenceEntry<K, V> getFirst(int hash) {
         AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> table = this.table;
         return table.get(hash & table.length() - 1);
      }

      MapMakerInternalMap.ReferenceEntry<K, V> getEntry(Object key, int hash) {
         if (this.count != 0) {
            for (MapMakerInternalMap.ReferenceEntry<K, V> e = this.getFirst(hash); e != null; e = e.getNext()) {
               if (e.getHash() == hash) {
                  K entryKey = e.getKey();
                  if (entryKey == null) {
                     this.tryDrainReferenceQueues();
                  } else if (this.map.keyEquivalence.equivalent(key, entryKey)) {
                     return e;
                  }
               }
            }
         }

         return null;
      }

      MapMakerInternalMap.ReferenceEntry<K, V> getLiveEntry(Object key, int hash) {
         MapMakerInternalMap.ReferenceEntry<K, V> e = this.getEntry(key, hash);
         if (e == null) {
            return null;
         } else if (this.map.expires() && this.map.isExpired(e)) {
            this.tryExpireEntries();
            return null;
         } else {
            return e;
         }
      }

      V get(Object key, int hash) {
         V value;
         try {
            MapMakerInternalMap.ReferenceEntry<K, V> e = this.getLiveEntry(key, hash);
            if (e != null) {
               value = e.getValueReference().get();
               if (value != null) {
                  this.recordRead(e);
               } else {
                  this.tryDrainReferenceQueues();
               }

               return value;
            }

            value = null;
         } finally {
            this.postReadCleanup();
         }

         return value;
      }

      boolean containsKey(Object key, int hash) {
         boolean var4;
         try {
            if (this.count == 0) {
               return false;
            }

            MapMakerInternalMap.ReferenceEntry<K, V> e = this.getLiveEntry(key, hash);
            if (e != null) {
               return e.getValueReference().get() != null;
            }

            var4 = false;
         } finally {
            this.postReadCleanup();
         }

         return var4;
      }

      @VisibleForTesting
      boolean containsValue(Object value) {
         try {
            if (this.count != 0) {
               AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> table = this.table;
               int length = table.length();

               for (int i = 0; i < length; i++) {
                  for (MapMakerInternalMap.ReferenceEntry<K, V> e = table.get(i); e != null; e = e.getNext()) {
                     V entryValue = this.getLiveValue(e);
                     if (entryValue != null && this.map.valueEquivalence.equivalent(value, entryValue)) {
                        return true;
                     }
                  }
               }
            }

            return false;
         } finally {
            this.postReadCleanup();
         }
      }

      V put(K key, int hash, V value, boolean onlyIfAbsent) {
         this.lock();

         try {
            this.preWriteCleanup();
            int newCount = this.count + 1;
            if (newCount > this.threshold) {
               this.expand();
               newCount = this.count + 1;
            }

            AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> table = this.table;
            int index = hash & table.length() - 1;
            MapMakerInternalMap.ReferenceEntry<K, V> first = table.get(index);

            for (MapMakerInternalMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
               K entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  MapMakerInternalMap.ValueReference<K, V> valueReference = e.getValueReference();
                  V entryValue = valueReference.get();
                  if (entryValue != null) {
                     if (!onlyIfAbsent) {
                        this.modCount++;
                        this.enqueueNotification(key, hash, entryValue, MapMaker.RemovalCause.REPLACED);
                        this.setValue(e, value);
                        return entryValue;
                     }

                     this.recordLockedRead(e);
                     return entryValue;
                  }

                  this.modCount++;
                  this.setValue(e, value);
                  if (!valueReference.isComputingReference()) {
                     this.enqueueNotification(key, hash, entryValue, MapMaker.RemovalCause.COLLECTED);
                     newCount = this.count;
                  } else if (this.evictEntries()) {
                     newCount = this.count + 1;
                  }

                  this.count = newCount;
                  return null;
               }
            }

            this.modCount++;
            MapMakerInternalMap.ReferenceEntry<K, V> newEntry = this.newEntry(key, hash, first);
            this.setValue(newEntry, value);
            table.set(index, newEntry);
            if (this.evictEntries()) {
               newCount = this.count + 1;
            }

            this.count = newCount;
            return null;
         } finally {
            this.unlock();
            this.postWriteCleanup();
         }
      }

      @GuardedBy("Segment.this")
      void expand() {
         AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> oldTable = this.table;
         int oldCapacity = oldTable.length();
         if (oldCapacity < 1073741824) {
            int newCount = this.count;
            AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> newTable = this.newEntryArray(oldCapacity << 1);
            this.threshold = newTable.length() * 3 / 4;
            int newMask = newTable.length() - 1;

            for (int oldIndex = 0; oldIndex < oldCapacity; oldIndex++) {
               MapMakerInternalMap.ReferenceEntry<K, V> head = oldTable.get(oldIndex);
               if (head != null) {
                  MapMakerInternalMap.ReferenceEntry<K, V> next = head.getNext();
                  int headIndex = head.getHash() & newMask;
                  if (next == null) {
                     newTable.set(headIndex, head);
                  } else {
                     MapMakerInternalMap.ReferenceEntry<K, V> tail = head;
                     int tailIndex = headIndex;

                     for (MapMakerInternalMap.ReferenceEntry<K, V> e = next; e != null; e = e.getNext()) {
                        int newIndex = e.getHash() & newMask;
                        if (newIndex != tailIndex) {
                           tailIndex = newIndex;
                           tail = e;
                        }
                     }

                     newTable.set(tailIndex, tail);

                     for (MapMakerInternalMap.ReferenceEntry<K, V> ex = head; ex != tail; ex = ex.getNext()) {
                        int newIndex = ex.getHash() & newMask;
                        MapMakerInternalMap.ReferenceEntry<K, V> newNext = newTable.get(newIndex);
                        MapMakerInternalMap.ReferenceEntry<K, V> newFirst = this.copyEntry(ex, newNext);
                        if (newFirst != null) {
                           newTable.set(newIndex, newFirst);
                        } else {
                           this.removeCollectedEntry(ex);
                           newCount--;
                        }
                     }
                  }
               }
            }

            this.table = newTable;
            this.count = newCount;
         }
      }

      boolean replace(K key, int hash, V oldValue, V newValue) {
         this.lock();

         try {
            this.preWriteCleanup();
            AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> table = this.table;
            int index = hash & table.length() - 1;
            MapMakerInternalMap.ReferenceEntry<K, V> first = table.get(index);

            for (MapMakerInternalMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
               K entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  MapMakerInternalMap.ValueReference<K, V> valueReference = e.getValueReference();
                  V entryValue = valueReference.get();
                  if (entryValue != null) {
                     if (!this.map.valueEquivalence.equivalent(oldValue, entryValue)) {
                        this.recordLockedRead(e);
                        return false;
                     }

                     this.modCount++;
                     this.enqueueNotification(key, hash, entryValue, MapMaker.RemovalCause.REPLACED);
                     this.setValue(e, newValue);
                     return true;
                  }

                  if (this.isCollected(valueReference)) {
                     int newCount = this.count - 1;
                     this.modCount++;
                     this.enqueueNotification(entryKey, hash, entryValue, MapMaker.RemovalCause.COLLECTED);
                     MapMakerInternalMap.ReferenceEntry<K, V> newFirst = this.removeFromChain(first, e);
                     newCount = this.count - 1;
                     table.set(index, newFirst);
                     this.count = newCount;
                  }

                  return false;
               }
            }

            return false;
         } finally {
            this.unlock();
            this.postWriteCleanup();
         }
      }

      V replace(K key, int hash, V newValue) {
         this.lock();

         try {
            this.preWriteCleanup();
            AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> table = this.table;
            int index = hash & table.length() - 1;
            MapMakerInternalMap.ReferenceEntry<K, V> first = table.get(index);

            for (MapMakerInternalMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
               K entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  MapMakerInternalMap.ValueReference<K, V> valueReference = e.getValueReference();
                  V entryValue = valueReference.get();
                  if (entryValue != null) {
                     this.modCount++;
                     this.enqueueNotification(key, hash, entryValue, MapMaker.RemovalCause.REPLACED);
                     this.setValue(e, newValue);
                     return entryValue;
                  }

                  if (this.isCollected(valueReference)) {
                     int newCount = this.count - 1;
                     this.modCount++;
                     this.enqueueNotification(entryKey, hash, entryValue, MapMaker.RemovalCause.COLLECTED);
                     MapMakerInternalMap.ReferenceEntry<K, V> newFirst = this.removeFromChain(first, e);
                     newCount = this.count - 1;
                     table.set(index, newFirst);
                     this.count = newCount;
                  }

                  return null;
               }
            }

            return null;
         } finally {
            this.unlock();
            this.postWriteCleanup();
         }
      }

      V remove(Object key, int hash) {
         this.lock();

         try {
            this.preWriteCleanup();
            int newCount = this.count - 1;
            AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> table = this.table;
            int index = hash & table.length() - 1;
            MapMakerInternalMap.ReferenceEntry<K, V> first = table.get(index);

            for (MapMakerInternalMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
               K entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  MapMakerInternalMap.ValueReference<K, V> valueReference = e.getValueReference();
                  V entryValue = valueReference.get();
                  MapMaker.RemovalCause cause;
                  if (entryValue != null) {
                     cause = MapMaker.RemovalCause.EXPLICIT;
                  } else {
                     if (!this.isCollected(valueReference)) {
                        return null;
                     }

                     cause = MapMaker.RemovalCause.COLLECTED;
                  }

                  this.modCount++;
                  this.enqueueNotification(entryKey, hash, entryValue, cause);
                  MapMakerInternalMap.ReferenceEntry<K, V> newFirst = this.removeFromChain(first, e);
                  newCount = this.count - 1;
                  table.set(index, newFirst);
                  this.count = newCount;
                  return entryValue;
               }
            }

            return null;
         } finally {
            this.unlock();
            this.postWriteCleanup();
         }
      }

      boolean remove(Object key, int hash, Object value) {
         this.lock();

         try {
            this.preWriteCleanup();
            int newCount = this.count - 1;
            AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> table = this.table;
            int index = hash & table.length() - 1;
            MapMakerInternalMap.ReferenceEntry<K, V> first = table.get(index);

            for (MapMakerInternalMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
               K entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  MapMakerInternalMap.ValueReference<K, V> valueReference = e.getValueReference();
                  V entryValue = valueReference.get();
                  MapMaker.RemovalCause cause;
                  if (this.map.valueEquivalence.equivalent(value, entryValue)) {
                     cause = MapMaker.RemovalCause.EXPLICIT;
                  } else {
                     if (!this.isCollected(valueReference)) {
                        return false;
                     }

                     cause = MapMaker.RemovalCause.COLLECTED;
                  }

                  this.modCount++;
                  this.enqueueNotification(entryKey, hash, entryValue, cause);
                  MapMakerInternalMap.ReferenceEntry<K, V> newFirst = this.removeFromChain(first, e);
                  newCount = this.count - 1;
                  table.set(index, newFirst);
                  this.count = newCount;
                  return cause == MapMaker.RemovalCause.EXPLICIT;
               }
            }

            return false;
         } finally {
            this.unlock();
            this.postWriteCleanup();
         }
      }

      void clear() {
         if (this.count != 0) {
            this.lock();

            try {
               AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> table = this.table;
               if (this.map.removalNotificationQueue != MapMakerInternalMap.DISCARDING_QUEUE) {
                  for (int i = 0; i < table.length(); i++) {
                     for (MapMakerInternalMap.ReferenceEntry<K, V> e = table.get(i); e != null; e = e.getNext()) {
                        if (!e.getValueReference().isComputingReference()) {
                           this.enqueueNotification(e, MapMaker.RemovalCause.EXPLICIT);
                        }
                     }
                  }
               }

               for (int i = 0; i < table.length(); i++) {
                  table.set(i, null);
               }

               this.clearReferenceQueues();
               this.evictionQueue.clear();
               this.expirationQueue.clear();
               this.readCount.set(0);
               this.modCount++;
               this.count = 0;
            } finally {
               this.unlock();
               this.postWriteCleanup();
            }
         }
      }

      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> removeFromChain(MapMakerInternalMap.ReferenceEntry<K, V> first, MapMakerInternalMap.ReferenceEntry<K, V> entry) {
         this.evictionQueue.remove(entry);
         this.expirationQueue.remove(entry);
         int newCount = this.count;
         MapMakerInternalMap.ReferenceEntry<K, V> newFirst = entry.getNext();

         for (MapMakerInternalMap.ReferenceEntry<K, V> e = first; e != entry; e = e.getNext()) {
            MapMakerInternalMap.ReferenceEntry<K, V> next = this.copyEntry(e, newFirst);
            if (next != null) {
               newFirst = next;
            } else {
               this.removeCollectedEntry(e);
               newCount--;
            }
         }

         this.count = newCount;
         return newFirst;
      }

      void removeCollectedEntry(MapMakerInternalMap.ReferenceEntry<K, V> entry) {
         this.enqueueNotification(entry, MapMaker.RemovalCause.COLLECTED);
         this.evictionQueue.remove(entry);
         this.expirationQueue.remove(entry);
      }

      boolean reclaimKey(MapMakerInternalMap.ReferenceEntry<K, V> entry, int hash) {
         this.lock();

         try {
            int newCount = this.count - 1;
            AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> table = this.table;
            int index = hash & table.length() - 1;
            MapMakerInternalMap.ReferenceEntry<K, V> first = table.get(index);

            for (MapMakerInternalMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
               if (e == entry) {
                  this.modCount++;
                  this.enqueueNotification(e.getKey(), hash, e.getValueReference().get(), MapMaker.RemovalCause.COLLECTED);
                  MapMakerInternalMap.ReferenceEntry<K, V> newFirst = this.removeFromChain(first, e);
                  newCount = this.count - 1;
                  table.set(index, newFirst);
                  this.count = newCount;
                  return true;
               }
            }

            return false;
         } finally {
            this.unlock();
            this.postWriteCleanup();
         }
      }

      boolean reclaimValue(K key, int hash, MapMakerInternalMap.ValueReference<K, V> valueReference) {
         this.lock();

         try {
            int newCount = this.count - 1;
            AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> table = this.table;
            int index = hash & table.length() - 1;
            MapMakerInternalMap.ReferenceEntry<K, V> first = table.get(index);

            for (MapMakerInternalMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
               K entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  MapMakerInternalMap.ValueReference<K, V> v = e.getValueReference();
                  if (v != valueReference) {
                     return false;
                  }

                  this.modCount++;
                  this.enqueueNotification(key, hash, valueReference.get(), MapMaker.RemovalCause.COLLECTED);
                  MapMakerInternalMap.ReferenceEntry<K, V> newFirst = this.removeFromChain(first, e);
                  newCount = this.count - 1;
                  table.set(index, newFirst);
                  this.count = newCount;
                  return true;
               }
            }

            return false;
         } finally {
            this.unlock();
            if (!this.isHeldByCurrentThread()) {
               this.postWriteCleanup();
            }
         }
      }

      boolean clearValue(K key, int hash, MapMakerInternalMap.ValueReference<K, V> valueReference) {
         this.lock();

         try {
            AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> table = this.table;
            int index = hash & table.length() - 1;
            MapMakerInternalMap.ReferenceEntry<K, V> first = table.get(index);

            for (MapMakerInternalMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
               K entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  MapMakerInternalMap.ValueReference<K, V> v = e.getValueReference();
                  if (v != valueReference) {
                     return false;
                  }

                  MapMakerInternalMap.ReferenceEntry<K, V> newFirst = this.removeFromChain(first, e);
                  table.set(index, newFirst);
                  return true;
               }
            }

            return false;
         } finally {
            this.unlock();
            this.postWriteCleanup();
         }
      }

      @GuardedBy("Segment.this")
      boolean removeEntry(MapMakerInternalMap.ReferenceEntry<K, V> entry, int hash, MapMaker.RemovalCause cause) {
         int newCount = this.count - 1;
         AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> table = this.table;
         int index = hash & table.length() - 1;
         MapMakerInternalMap.ReferenceEntry<K, V> first = table.get(index);

         for (MapMakerInternalMap.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
            if (e == entry) {
               this.modCount++;
               this.enqueueNotification(e.getKey(), hash, e.getValueReference().get(), cause);
               MapMakerInternalMap.ReferenceEntry<K, V> newFirst = this.removeFromChain(first, e);
               newCount = this.count - 1;
               table.set(index, newFirst);
               this.count = newCount;
               return true;
            }
         }

         return false;
      }

      boolean isCollected(MapMakerInternalMap.ValueReference<K, V> valueReference) {
         return valueReference.isComputingReference() ? false : valueReference.get() == null;
      }

      V getLiveValue(MapMakerInternalMap.ReferenceEntry<K, V> entry) {
         if (entry.getKey() == null) {
            this.tryDrainReferenceQueues();
            return null;
         } else {
            V value = entry.getValueReference().get();
            if (value == null) {
               this.tryDrainReferenceQueues();
               return null;
            } else if (this.map.expires() && this.map.isExpired(entry)) {
               this.tryExpireEntries();
               return null;
            } else {
               return value;
            }
         }
      }

      void postReadCleanup() {
         if ((this.readCount.incrementAndGet() & 63) == 0) {
            this.runCleanup();
         }
      }

      @GuardedBy("Segment.this")
      void preWriteCleanup() {
         this.runLockedCleanup();
      }

      void postWriteCleanup() {
         this.runUnlockedCleanup();
      }

      void runCleanup() {
         this.runLockedCleanup();
         this.runUnlockedCleanup();
      }

      void runLockedCleanup() {
         if (this.tryLock()) {
            try {
               this.drainReferenceQueues();
               this.expireEntries();
               this.readCount.set(0);
            } finally {
               this.unlock();
            }
         }
      }

      void runUnlockedCleanup() {
         if (!this.isHeldByCurrentThread()) {
            this.map.processPendingNotifications();
         }
      }
   }

   private static final class SerializationProxy<K, V> extends MapMakerInternalMap.AbstractSerializationProxy<K, V> {
      private static final long serialVersionUID = 3L;

      SerializationProxy(
         MapMakerInternalMap.Strength keyStrength,
         MapMakerInternalMap.Strength valueStrength,
         Equivalence<Object> keyEquivalence,
         Equivalence<Object> valueEquivalence,
         long expireAfterWriteNanos,
         long expireAfterAccessNanos,
         int maximumSize,
         int concurrencyLevel,
         MapMaker.RemovalListener<? super K, ? super V> removalListener,
         ConcurrentMap<K, V> delegate
      ) {
         super(
            keyStrength,
            valueStrength,
            keyEquivalence,
            valueEquivalence,
            expireAfterWriteNanos,
            expireAfterAccessNanos,
            maximumSize,
            concurrencyLevel,
            removalListener,
            delegate
         );
      }

      private void writeObject(ObjectOutputStream out) throws IOException {
         out.defaultWriteObject();
         this.writeMapTo(out);
      }

      private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
         in.defaultReadObject();
         MapMaker mapMaker = this.readMapMaker(in);
         this.delegate = mapMaker.makeMap();
         this.readEntries(in);
      }

      private Object readResolve() {
         return this.delegate;
      }
   }

   static class SoftEntry<K, V> extends SoftReference<K> implements MapMakerInternalMap.ReferenceEntry<K, V> {
      final int hash;
      final MapMakerInternalMap.ReferenceEntry<K, V> next;
      volatile MapMakerInternalMap.ValueReference<K, V> valueReference = MapMakerInternalMap.unset();

      SoftEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> next) {
         super(key, queue);
         this.hash = hash;
         this.next = next;
      }

      @Override
      public K getKey() {
         return this.get();
      }

      @Override
      public long getExpirationTime() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setExpirationTime(long time) {
         throw new UnsupportedOperationException();
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNextExpirable() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setNextExpirable(MapMakerInternalMap.ReferenceEntry<K, V> next) {
         throw new UnsupportedOperationException();
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousExpirable() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setPreviousExpirable(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
         throw new UnsupportedOperationException();
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNextEvictable() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setNextEvictable(MapMakerInternalMap.ReferenceEntry<K, V> next) {
         throw new UnsupportedOperationException();
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousEvictable() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setPreviousEvictable(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
         throw new UnsupportedOperationException();
      }

      @Override
      public MapMakerInternalMap.ValueReference<K, V> getValueReference() {
         return this.valueReference;
      }

      @Override
      public void setValueReference(MapMakerInternalMap.ValueReference<K, V> valueReference) {
         MapMakerInternalMap.ValueReference<K, V> previous = this.valueReference;
         this.valueReference = valueReference;
         previous.clear(valueReference);
      }

      @Override
      public int getHash() {
         return this.hash;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNext() {
         return this.next;
      }
   }

   static final class SoftEvictableEntry<K, V> extends MapMakerInternalMap.SoftEntry<K, V> implements MapMakerInternalMap.ReferenceEntry<K, V> {
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> nextEvictable = MapMakerInternalMap.nullEntry();
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> previousEvictable = MapMakerInternalMap.nullEntry();

      SoftEvictableEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> next) {
         super(queue, key, hash, next);
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNextEvictable() {
         return this.nextEvictable;
      }

      @Override
      public void setNextEvictable(MapMakerInternalMap.ReferenceEntry<K, V> next) {
         this.nextEvictable = next;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousEvictable() {
         return this.previousEvictable;
      }

      @Override
      public void setPreviousEvictable(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
         this.previousEvictable = previous;
      }
   }

   static final class SoftExpirableEntry<K, V> extends MapMakerInternalMap.SoftEntry<K, V> implements MapMakerInternalMap.ReferenceEntry<K, V> {
      volatile long time = Long.MAX_VALUE;
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> nextExpirable = MapMakerInternalMap.nullEntry();
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> previousExpirable = MapMakerInternalMap.nullEntry();

      SoftExpirableEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> next) {
         super(queue, key, hash, next);
      }

      @Override
      public long getExpirationTime() {
         return this.time;
      }

      @Override
      public void setExpirationTime(long time) {
         this.time = time;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNextExpirable() {
         return this.nextExpirable;
      }

      @Override
      public void setNextExpirable(MapMakerInternalMap.ReferenceEntry<K, V> next) {
         this.nextExpirable = next;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousExpirable() {
         return this.previousExpirable;
      }

      @Override
      public void setPreviousExpirable(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
         this.previousExpirable = previous;
      }
   }

   static final class SoftExpirableEvictableEntry<K, V> extends MapMakerInternalMap.SoftEntry<K, V> implements MapMakerInternalMap.ReferenceEntry<K, V> {
      volatile long time = Long.MAX_VALUE;
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> nextExpirable = MapMakerInternalMap.nullEntry();
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> previousExpirable = MapMakerInternalMap.nullEntry();
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> nextEvictable = MapMakerInternalMap.nullEntry();
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> previousEvictable = MapMakerInternalMap.nullEntry();

      SoftExpirableEvictableEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> next) {
         super(queue, key, hash, next);
      }

      @Override
      public long getExpirationTime() {
         return this.time;
      }

      @Override
      public void setExpirationTime(long time) {
         this.time = time;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNextExpirable() {
         return this.nextExpirable;
      }

      @Override
      public void setNextExpirable(MapMakerInternalMap.ReferenceEntry<K, V> next) {
         this.nextExpirable = next;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousExpirable() {
         return this.previousExpirable;
      }

      @Override
      public void setPreviousExpirable(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
         this.previousExpirable = previous;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNextEvictable() {
         return this.nextEvictable;
      }

      @Override
      public void setNextEvictable(MapMakerInternalMap.ReferenceEntry<K, V> next) {
         this.nextEvictable = next;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousEvictable() {
         return this.previousEvictable;
      }

      @Override
      public void setPreviousEvictable(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
         this.previousEvictable = previous;
      }
   }

   static final class SoftValueReference<K, V> extends SoftReference<V> implements MapMakerInternalMap.ValueReference<K, V> {
      final MapMakerInternalMap.ReferenceEntry<K, V> entry;

      SoftValueReference(ReferenceQueue<V> queue, V referent, MapMakerInternalMap.ReferenceEntry<K, V> entry) {
         super(referent, queue);
         this.entry = entry;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getEntry() {
         return this.entry;
      }

      @Override
      public void clear(MapMakerInternalMap.ValueReference<K, V> newValue) {
         this.clear();
      }

      @Override
      public MapMakerInternalMap.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, MapMakerInternalMap.ReferenceEntry<K, V> entry) {
         return new MapMakerInternalMap.SoftValueReference<>(queue, value, entry);
      }

      @Override
      public boolean isComputingReference() {
         return false;
      }

      @Override
      public V waitForValue() {
         return this.get();
      }
   }

   static enum Strength {
      STRONG {
         @Override
         <K, V> MapMakerInternalMap.ValueReference<K, V> referenceValue(
            MapMakerInternalMap.Segment<K, V> segment, MapMakerInternalMap.ReferenceEntry<K, V> entry, V value
         ) {
            return new MapMakerInternalMap.StrongValueReference<>(value);
         }

         @Override
         Equivalence<Object> defaultEquivalence() {
            return Equivalence.equals();
         }
      },
      SOFT {
         @Override
         <K, V> MapMakerInternalMap.ValueReference<K, V> referenceValue(
            MapMakerInternalMap.Segment<K, V> segment, MapMakerInternalMap.ReferenceEntry<K, V> entry, V value
         ) {
            return new MapMakerInternalMap.SoftValueReference<>(segment.valueReferenceQueue, value, entry);
         }

         @Override
         Equivalence<Object> defaultEquivalence() {
            return Equivalence.identity();
         }
      },
      WEAK {
         @Override
         <K, V> MapMakerInternalMap.ValueReference<K, V> referenceValue(
            MapMakerInternalMap.Segment<K, V> segment, MapMakerInternalMap.ReferenceEntry<K, V> entry, V value
         ) {
            return new MapMakerInternalMap.WeakValueReference<>(segment.valueReferenceQueue, value, entry);
         }

         @Override
         Equivalence<Object> defaultEquivalence() {
            return Equivalence.identity();
         }
      };

      private Strength() {
      }

      abstract <K, V> MapMakerInternalMap.ValueReference<K, V> referenceValue(
         MapMakerInternalMap.Segment<K, V> var1, MapMakerInternalMap.ReferenceEntry<K, V> var2, V var3
      );

      abstract Equivalence<Object> defaultEquivalence();
   }

   static class StrongEntry<K, V> implements MapMakerInternalMap.ReferenceEntry<K, V> {
      final K key;
      final int hash;
      final MapMakerInternalMap.ReferenceEntry<K, V> next;
      volatile MapMakerInternalMap.ValueReference<K, V> valueReference = MapMakerInternalMap.unset();

      StrongEntry(K key, int hash, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> next) {
         this.key = key;
         this.hash = hash;
         this.next = next;
      }

      @Override
      public K getKey() {
         return this.key;
      }

      @Override
      public long getExpirationTime() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setExpirationTime(long time) {
         throw new UnsupportedOperationException();
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNextExpirable() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setNextExpirable(MapMakerInternalMap.ReferenceEntry<K, V> next) {
         throw new UnsupportedOperationException();
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousExpirable() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setPreviousExpirable(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
         throw new UnsupportedOperationException();
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNextEvictable() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setNextEvictable(MapMakerInternalMap.ReferenceEntry<K, V> next) {
         throw new UnsupportedOperationException();
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousEvictable() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setPreviousEvictable(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
         throw new UnsupportedOperationException();
      }

      @Override
      public MapMakerInternalMap.ValueReference<K, V> getValueReference() {
         return this.valueReference;
      }

      @Override
      public void setValueReference(MapMakerInternalMap.ValueReference<K, V> valueReference) {
         MapMakerInternalMap.ValueReference<K, V> previous = this.valueReference;
         this.valueReference = valueReference;
         previous.clear(valueReference);
      }

      @Override
      public int getHash() {
         return this.hash;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNext() {
         return this.next;
      }
   }

   static final class StrongEvictableEntry<K, V> extends MapMakerInternalMap.StrongEntry<K, V> implements MapMakerInternalMap.ReferenceEntry<K, V> {
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> nextEvictable = MapMakerInternalMap.nullEntry();
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> previousEvictable = MapMakerInternalMap.nullEntry();

      StrongEvictableEntry(K key, int hash, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> next) {
         super(key, hash, next);
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNextEvictable() {
         return this.nextEvictable;
      }

      @Override
      public void setNextEvictable(MapMakerInternalMap.ReferenceEntry<K, V> next) {
         this.nextEvictable = next;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousEvictable() {
         return this.previousEvictable;
      }

      @Override
      public void setPreviousEvictable(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
         this.previousEvictable = previous;
      }
   }

   static final class StrongExpirableEntry<K, V> extends MapMakerInternalMap.StrongEntry<K, V> implements MapMakerInternalMap.ReferenceEntry<K, V> {
      volatile long time = Long.MAX_VALUE;
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> nextExpirable = MapMakerInternalMap.nullEntry();
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> previousExpirable = MapMakerInternalMap.nullEntry();

      StrongExpirableEntry(K key, int hash, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> next) {
         super(key, hash, next);
      }

      @Override
      public long getExpirationTime() {
         return this.time;
      }

      @Override
      public void setExpirationTime(long time) {
         this.time = time;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNextExpirable() {
         return this.nextExpirable;
      }

      @Override
      public void setNextExpirable(MapMakerInternalMap.ReferenceEntry<K, V> next) {
         this.nextExpirable = next;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousExpirable() {
         return this.previousExpirable;
      }

      @Override
      public void setPreviousExpirable(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
         this.previousExpirable = previous;
      }
   }

   static final class StrongExpirableEvictableEntry<K, V> extends MapMakerInternalMap.StrongEntry<K, V> implements MapMakerInternalMap.ReferenceEntry<K, V> {
      volatile long time = Long.MAX_VALUE;
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> nextExpirable = MapMakerInternalMap.nullEntry();
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> previousExpirable = MapMakerInternalMap.nullEntry();
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> nextEvictable = MapMakerInternalMap.nullEntry();
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> previousEvictable = MapMakerInternalMap.nullEntry();

      StrongExpirableEvictableEntry(K key, int hash, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> next) {
         super(key, hash, next);
      }

      @Override
      public long getExpirationTime() {
         return this.time;
      }

      @Override
      public void setExpirationTime(long time) {
         this.time = time;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNextExpirable() {
         return this.nextExpirable;
      }

      @Override
      public void setNextExpirable(MapMakerInternalMap.ReferenceEntry<K, V> next) {
         this.nextExpirable = next;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousExpirable() {
         return this.previousExpirable;
      }

      @Override
      public void setPreviousExpirable(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
         this.previousExpirable = previous;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNextEvictable() {
         return this.nextEvictable;
      }

      @Override
      public void setNextEvictable(MapMakerInternalMap.ReferenceEntry<K, V> next) {
         this.nextEvictable = next;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousEvictable() {
         return this.previousEvictable;
      }

      @Override
      public void setPreviousEvictable(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
         this.previousEvictable = previous;
      }
   }

   static final class StrongValueReference<K, V> implements MapMakerInternalMap.ValueReference<K, V> {
      final V referent;

      StrongValueReference(V referent) {
         this.referent = referent;
      }

      @Override
      public V get() {
         return this.referent;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getEntry() {
         return null;
      }

      @Override
      public MapMakerInternalMap.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, MapMakerInternalMap.ReferenceEntry<K, V> entry) {
         return this;
      }

      @Override
      public boolean isComputingReference() {
         return false;
      }

      @Override
      public V waitForValue() {
         return this.get();
      }

      @Override
      public void clear(MapMakerInternalMap.ValueReference<K, V> newValue) {
      }
   }

   final class ValueIterator extends MapMakerInternalMap<K, V>.HashIterator<V> {
      @Override
      public V next() {
         return this.nextEntry().getValue();
      }
   }

   interface ValueReference<K, V> {
      V get();

      V waitForValue() throws ExecutionException;

      MapMakerInternalMap.ReferenceEntry<K, V> getEntry();

      MapMakerInternalMap.ValueReference<K, V> copyFor(ReferenceQueue<V> var1, @Nullable V var2, MapMakerInternalMap.ReferenceEntry<K, V> var3);

      void clear(@Nullable MapMakerInternalMap.ValueReference<K, V> var1);

      boolean isComputingReference();
   }

   final class Values extends AbstractCollection<V> {
      @Override
      public Iterator<V> iterator() {
         return MapMakerInternalMap.this.new ValueIterator();
      }

      @Override
      public int size() {
         return MapMakerInternalMap.this.size();
      }

      @Override
      public boolean isEmpty() {
         return MapMakerInternalMap.this.isEmpty();
      }

      @Override
      public boolean contains(Object o) {
         return MapMakerInternalMap.this.containsValue(o);
      }

      @Override
      public void clear() {
         MapMakerInternalMap.this.clear();
      }
   }

   static class WeakEntry<K, V> extends WeakReference<K> implements MapMakerInternalMap.ReferenceEntry<K, V> {
      final int hash;
      final MapMakerInternalMap.ReferenceEntry<K, V> next;
      volatile MapMakerInternalMap.ValueReference<K, V> valueReference = MapMakerInternalMap.unset();

      WeakEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> next) {
         super(key, queue);
         this.hash = hash;
         this.next = next;
      }

      @Override
      public K getKey() {
         return this.get();
      }

      @Override
      public long getExpirationTime() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setExpirationTime(long time) {
         throw new UnsupportedOperationException();
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNextExpirable() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setNextExpirable(MapMakerInternalMap.ReferenceEntry<K, V> next) {
         throw new UnsupportedOperationException();
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousExpirable() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setPreviousExpirable(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
         throw new UnsupportedOperationException();
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNextEvictable() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setNextEvictable(MapMakerInternalMap.ReferenceEntry<K, V> next) {
         throw new UnsupportedOperationException();
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousEvictable() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setPreviousEvictable(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
         throw new UnsupportedOperationException();
      }

      @Override
      public MapMakerInternalMap.ValueReference<K, V> getValueReference() {
         return this.valueReference;
      }

      @Override
      public void setValueReference(MapMakerInternalMap.ValueReference<K, V> valueReference) {
         MapMakerInternalMap.ValueReference<K, V> previous = this.valueReference;
         this.valueReference = valueReference;
         previous.clear(valueReference);
      }

      @Override
      public int getHash() {
         return this.hash;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNext() {
         return this.next;
      }
   }

   static final class WeakEvictableEntry<K, V> extends MapMakerInternalMap.WeakEntry<K, V> implements MapMakerInternalMap.ReferenceEntry<K, V> {
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> nextEvictable = MapMakerInternalMap.nullEntry();
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> previousEvictable = MapMakerInternalMap.nullEntry();

      WeakEvictableEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> next) {
         super(queue, key, hash, next);
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNextEvictable() {
         return this.nextEvictable;
      }

      @Override
      public void setNextEvictable(MapMakerInternalMap.ReferenceEntry<K, V> next) {
         this.nextEvictable = next;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousEvictable() {
         return this.previousEvictable;
      }

      @Override
      public void setPreviousEvictable(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
         this.previousEvictable = previous;
      }
   }

   static final class WeakExpirableEntry<K, V> extends MapMakerInternalMap.WeakEntry<K, V> implements MapMakerInternalMap.ReferenceEntry<K, V> {
      volatile long time = Long.MAX_VALUE;
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> nextExpirable = MapMakerInternalMap.nullEntry();
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> previousExpirable = MapMakerInternalMap.nullEntry();

      WeakExpirableEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> next) {
         super(queue, key, hash, next);
      }

      @Override
      public long getExpirationTime() {
         return this.time;
      }

      @Override
      public void setExpirationTime(long time) {
         this.time = time;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNextExpirable() {
         return this.nextExpirable;
      }

      @Override
      public void setNextExpirable(MapMakerInternalMap.ReferenceEntry<K, V> next) {
         this.nextExpirable = next;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousExpirable() {
         return this.previousExpirable;
      }

      @Override
      public void setPreviousExpirable(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
         this.previousExpirable = previous;
      }
   }

   static final class WeakExpirableEvictableEntry<K, V> extends MapMakerInternalMap.WeakEntry<K, V> implements MapMakerInternalMap.ReferenceEntry<K, V> {
      volatile long time = Long.MAX_VALUE;
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> nextExpirable = MapMakerInternalMap.nullEntry();
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> previousExpirable = MapMakerInternalMap.nullEntry();
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> nextEvictable = MapMakerInternalMap.nullEntry();
      @GuardedBy("Segment.this")
      MapMakerInternalMap.ReferenceEntry<K, V> previousEvictable = MapMakerInternalMap.nullEntry();

      WeakExpirableEvictableEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable MapMakerInternalMap.ReferenceEntry<K, V> next) {
         super(queue, key, hash, next);
      }

      @Override
      public long getExpirationTime() {
         return this.time;
      }

      @Override
      public void setExpirationTime(long time) {
         this.time = time;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNextExpirable() {
         return this.nextExpirable;
      }

      @Override
      public void setNextExpirable(MapMakerInternalMap.ReferenceEntry<K, V> next) {
         this.nextExpirable = next;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousExpirable() {
         return this.previousExpirable;
      }

      @Override
      public void setPreviousExpirable(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
         this.previousExpirable = previous;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getNextEvictable() {
         return this.nextEvictable;
      }

      @Override
      public void setNextEvictable(MapMakerInternalMap.ReferenceEntry<K, V> next) {
         this.nextEvictable = next;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousEvictable() {
         return this.previousEvictable;
      }

      @Override
      public void setPreviousEvictable(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
         this.previousEvictable = previous;
      }
   }

   static final class WeakValueReference<K, V> extends WeakReference<V> implements MapMakerInternalMap.ValueReference<K, V> {
      final MapMakerInternalMap.ReferenceEntry<K, V> entry;

      WeakValueReference(ReferenceQueue<V> queue, V referent, MapMakerInternalMap.ReferenceEntry<K, V> entry) {
         super(referent, queue);
         this.entry = entry;
      }

      @Override
      public MapMakerInternalMap.ReferenceEntry<K, V> getEntry() {
         return this.entry;
      }

      @Override
      public void clear(MapMakerInternalMap.ValueReference<K, V> newValue) {
         this.clear();
      }

      @Override
      public MapMakerInternalMap.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, MapMakerInternalMap.ReferenceEntry<K, V> entry) {
         return new MapMakerInternalMap.WeakValueReference<>(queue, value, entry);
      }

      @Override
      public boolean isComputingReference() {
         return false;
      }

      @Override
      public V waitForValue() {
         return this.get();
      }
   }

   final class WriteThroughEntry extends AbstractMapEntry<K, V> {
      final K key;
      V value;

      WriteThroughEntry(K key, V value) {
         this.key = key;
         this.value = value;
      }

      @Override
      public K getKey() {
         return this.key;
      }

      @Override
      public V getValue() {
         return this.value;
      }

      @Override
      public boolean equals(@Nullable Object object) {
         if (!(object instanceof Entry)) {
            return false;
         } else {
            Entry<?, ?> that = (Entry<?, ?>)object;
            return this.key.equals(that.getKey()) && this.value.equals(that.getValue());
         }
      }

      @Override
      public int hashCode() {
         return this.key.hashCode() ^ this.value.hashCode();
      }

      @Override
      public V setValue(V newValue) {
         V oldValue = MapMakerInternalMap.this.put(this.key, newValue);
         this.value = newValue;
         return oldValue;
      }
   }
}
