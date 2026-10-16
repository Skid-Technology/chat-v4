package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
import com.google.common.collect.AbstractSequentialIterator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.common.util.concurrent.Uninterruptibles;
import java.io.IOException;
import java.io.ObjectInputStream;
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
import java.util.concurrent.Callable;
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

@GwtCompatible(emulated = true)
class LocalCache<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V> {
   static final int MAXIMUM_CAPACITY = 1073741824;
   static final int MAX_SEGMENTS = 65536;
   static final int CONTAINS_VALUE_RETRIES = 3;
   static final int DRAIN_THRESHOLD = 63;
   static final int DRAIN_MAX = 16;
   static final Logger logger = Logger.getLogger(LocalCache.class.getName());
   static final ListeningExecutorService sameThreadExecutor = MoreExecutors.sameThreadExecutor();
   final int segmentMask;
   final int segmentShift;
   final LocalCache.Segment<K, V>[] segments;
   final int concurrencyLevel;
   final Equivalence<Object> keyEquivalence;
   final Equivalence<Object> valueEquivalence;
   final LocalCache.Strength keyStrength;
   final LocalCache.Strength valueStrength;
   final long maxWeight;
   final Weigher<K, V> weigher;
   final long expireAfterAccessNanos;
   final long expireAfterWriteNanos;
   final long refreshNanos;
   final Queue<RemovalNotification<K, V>> removalNotificationQueue;
   final RemovalListener<K, V> removalListener;
   final Ticker ticker;
   final LocalCache.EntryFactory entryFactory;
   final AbstractCache.StatsCounter globalStatsCounter;
   @Nullable
   final CacheLoader<? super K, V> defaultLoader;
   static final LocalCache.ValueReference<Object, Object> UNSET = new LocalCache.ValueReference<Object, Object>() {
      @Override
      public Object get() {
         return null;
      }

      @Override
      public int getWeight() {
         return 0;
      }

      @Override
      public LocalCache.ReferenceEntry<Object, Object> getEntry() {
         return null;
      }

      @Override
      public LocalCache.ValueReference<Object, Object> copyFor(
         ReferenceQueue<Object> queue, @Nullable Object value, LocalCache.ReferenceEntry<Object, Object> entry
      ) {
         return this;
      }

      @Override
      public boolean isLoading() {
         return false;
      }

      @Override
      public boolean isActive() {
         return false;
      }

      @Override
      public Object waitForValue() {
         return null;
      }

      @Override
      public void notifyNewValue(Object newValue) {
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
   Set<K> keySet;
   Collection<V> values;
   Set<Entry<K, V>> entrySet;

   LocalCache(CacheBuilder<? super K, ? super V> builder, @Nullable CacheLoader<? super K, V> loader) {
      this.concurrencyLevel = Math.min(builder.getConcurrencyLevel(), 65536);
      this.keyStrength = builder.getKeyStrength();
      this.valueStrength = builder.getValueStrength();
      this.keyEquivalence = builder.getKeyEquivalence();
      this.valueEquivalence = builder.getValueEquivalence();
      this.maxWeight = builder.getMaximumWeight();
      this.weigher = builder.getWeigher();
      this.expireAfterAccessNanos = builder.getExpireAfterAccessNanos();
      this.expireAfterWriteNanos = builder.getExpireAfterWriteNanos();
      this.refreshNanos = builder.getRefreshNanos();
      this.removalListener = builder.getRemovalListener();
      this.removalNotificationQueue = (Queue<RemovalNotification<K, V>>)(this.removalListener == CacheBuilder.NullListener.INSTANCE
         ? discardingQueue()
         : new ConcurrentLinkedQueue<>());
      this.ticker = builder.getTicker(this.recordsTime());
      this.entryFactory = LocalCache.EntryFactory.getFactory(this.keyStrength, this.usesAccessEntries(), this.usesWriteEntries());
      this.globalStatsCounter = builder.getStatsCounterSupplier().get();
      this.defaultLoader = loader;
      int initialCapacity = Math.min(builder.getInitialCapacity(), 1073741824);
      if (this.evictsBySize() && !this.customWeigher()) {
         initialCapacity = Math.min(initialCapacity, (int)this.maxWeight);
      }

      int segmentShift = 0;

      int segmentCount;
      for (segmentCount = 1; segmentCount < this.concurrencyLevel && (!this.evictsBySize() || segmentCount * 20 <= this.maxWeight); segmentCount <<= 1) {
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
         long maxSegmentWeight = this.maxWeight / segmentCount + 1L;
         long remainder = this.maxWeight % segmentCount;

         for (int i = 0; i < this.segments.length; i++) {
            if (i == remainder) {
               maxSegmentWeight--;
            }

            this.segments[i] = this.createSegment(segmentSize, maxSegmentWeight, builder.getStatsCounterSupplier().get());
         }
      } else {
         for (int i = 0; i < this.segments.length; i++) {
            this.segments[i] = this.createSegment(segmentSize, -1L, builder.getStatsCounterSupplier().get());
         }
      }
   }

   boolean evictsBySize() {
      return this.maxWeight >= 0L;
   }

   boolean customWeigher() {
      return this.weigher != CacheBuilder.OneWeigher.INSTANCE;
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

   boolean refreshes() {
      return this.refreshNanos > 0L;
   }

   boolean usesAccessQueue() {
      return this.expiresAfterAccess() || this.evictsBySize();
   }

   boolean usesWriteQueue() {
      return this.expiresAfterWrite();
   }

   boolean recordsWrite() {
      return this.expiresAfterWrite() || this.refreshes();
   }

   boolean recordsAccess() {
      return this.expiresAfterAccess();
   }

   boolean recordsTime() {
      return this.recordsWrite() || this.recordsAccess();
   }

   boolean usesWriteEntries() {
      return this.usesWriteQueue() || this.recordsWrite();
   }

   boolean usesAccessEntries() {
      return this.usesAccessQueue() || this.recordsAccess();
   }

   boolean usesKeyReferences() {
      return this.keyStrength != LocalCache.Strength.STRONG;
   }

   boolean usesValueReferences() {
      return this.valueStrength != LocalCache.Strength.STRONG;
   }

   static <K, V> LocalCache.ValueReference<K, V> unset() {
      return (LocalCache.ValueReference<K, V>)UNSET;
   }

   static <K, V> LocalCache.ReferenceEntry<K, V> nullEntry() {
      return LocalCache.NullEntry.INSTANCE;
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
   LocalCache.ReferenceEntry<K, V> newEntry(K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) {
      return this.segmentFor(hash).newEntry(key, hash, next);
   }

   @GuardedBy("Segment.this")
   @VisibleForTesting
   LocalCache.ReferenceEntry<K, V> copyEntry(LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newNext) {
      int hash = original.getHash();
      return this.segmentFor(hash).copyEntry(original, newNext);
   }

   @GuardedBy("Segment.this")
   @VisibleForTesting
   LocalCache.ValueReference<K, V> newValueReference(LocalCache.ReferenceEntry<K, V> entry, V value, int weight) {
      int hash = entry.getHash();
      return this.valueStrength.referenceValue(this.segmentFor(hash), entry, Preconditions.checkNotNull(value), weight);
   }

   int hash(@Nullable Object key) {
      int h = this.keyEquivalence.hash(key);
      return rehash(h);
   }

   void reclaimValue(LocalCache.ValueReference<K, V> valueReference) {
      LocalCache.ReferenceEntry<K, V> entry = valueReference.getEntry();
      int hash = entry.getHash();
      this.segmentFor(hash).reclaimValue(entry.getKey(), hash, valueReference);
   }

   void reclaimKey(LocalCache.ReferenceEntry<K, V> entry) {
      int hash = entry.getHash();
      this.segmentFor(hash).reclaimKey(entry, hash);
   }

   @VisibleForTesting
   boolean isLive(LocalCache.ReferenceEntry<K, V> entry, long now) {
      return this.segmentFor(entry.getHash()).getLiveValue(entry, now) != null;
   }

   LocalCache.Segment<K, V> segmentFor(int hash) {
      return this.segments[hash >>> this.segmentShift & this.segmentMask];
   }

   LocalCache.Segment<K, V> createSegment(int initialCapacity, long maxSegmentWeight, AbstractCache.StatsCounter statsCounter) {
      return new LocalCache.Segment<>(this, initialCapacity, maxSegmentWeight, statsCounter);
   }

   @Nullable
   V getLiveValue(LocalCache.ReferenceEntry<K, V> entry, long now) {
      if (entry.getKey() == null) {
         return null;
      } else {
         V value = entry.getValueReference().get();
         if (value == null) {
            return null;
         } else {
            return this.isExpired(entry, now) ? null : value;
         }
      }
   }

   boolean isExpired(LocalCache.ReferenceEntry<K, V> entry, long now) {
      Preconditions.checkNotNull(entry);
      return this.expiresAfterAccess() && now - entry.getAccessTime() >= this.expireAfterAccessNanos
         ? true
         : this.expiresAfterWrite() && now - entry.getWriteTime() >= this.expireAfterWriteNanos;
   }

   @GuardedBy("Segment.this")
   static <K, V> void connectAccessOrder(LocalCache.ReferenceEntry<K, V> previous, LocalCache.ReferenceEntry<K, V> next) {
      previous.setNextInAccessQueue(next);
      next.setPreviousInAccessQueue(previous);
   }

   @GuardedBy("Segment.this")
   static <K, V> void nullifyAccessOrder(LocalCache.ReferenceEntry<K, V> nulled) {
      LocalCache.ReferenceEntry<K, V> nullEntry = nullEntry();
      nulled.setNextInAccessQueue(nullEntry);
      nulled.setPreviousInAccessQueue(nullEntry);
   }

   @GuardedBy("Segment.this")
   static <K, V> void connectWriteOrder(LocalCache.ReferenceEntry<K, V> previous, LocalCache.ReferenceEntry<K, V> next) {
      previous.setNextInWriteQueue(next);
      next.setPreviousInWriteQueue(previous);
   }

   @GuardedBy("Segment.this")
   static <K, V> void nullifyWriteOrder(LocalCache.ReferenceEntry<K, V> nulled) {
      LocalCache.ReferenceEntry<K, V> nullEntry = nullEntry();
      nulled.setNextInWriteQueue(nullEntry);
      nulled.setPreviousInWriteQueue(nullEntry);
   }

   void processPendingNotifications() {
      RemovalNotification<K, V> notification;
      while ((notification = this.removalNotificationQueue.poll()) != null) {
         try {
            this.removalListener.onRemoval(notification);
         } catch (Throwable var3) {
            logger.log(Level.WARNING, "Exception thrown by removal listener", var3);
         }
      }
   }

   final LocalCache.Segment<K, V>[] newSegmentArray(int ssize) {
      return new LocalCache.Segment[ssize];
   }

   public void cleanUp() {
      for (LocalCache.Segment<?, ?> segment : this.segments) {
         segment.cleanUp();
      }
   }

   @Override
   public boolean isEmpty() {
      long sum = 0L;
      LocalCache.Segment<K, V>[] segments = this.segments;

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

   long longSize() {
      LocalCache.Segment<K, V>[] segments = this.segments;
      long sum = 0L;

      for (int i = 0; i < segments.length; i++) {
         sum += segments[i].count;
      }

      return sum;
   }

   @Override
   public int size() {
      return Ints.saturatedCast(this.longSize());
   }

   @Nullable
   @Override
   public V get(@Nullable Object key) {
      if (key == null) {
         return null;
      } else {
         int hash = this.hash(key);
         return this.segmentFor(hash).get(key, hash);
      }
   }

   @Nullable
   public V getIfPresent(Object key) {
      int hash = this.hash(Preconditions.checkNotNull(key));
      V value = this.segmentFor(hash).get(key, hash);
      if (value == null) {
         this.globalStatsCounter.recordMisses(1);
      } else {
         this.globalStatsCounter.recordHits(1);
      }

      return value;
   }

   V get(K key, CacheLoader<? super K, V> loader) throws ExecutionException {
      int hash = this.hash(Preconditions.checkNotNull(key));
      return this.segmentFor(hash).get(key, hash, loader);
   }

   V getOrLoad(K key) throws ExecutionException {
      return this.get(key, this.defaultLoader);
   }

   ImmutableMap<K, V> getAllPresent(Iterable<?> keys) {
      int hits = 0;
      int misses = 0;
      Map<K, V> result = Maps.newLinkedHashMap();

      for (Object key : keys) {
         V value = this.get(key);
         if (value == null) {
            misses++;
         } else {
            result.put((K)key, value);
            hits++;
         }
      }

      this.globalStatsCounter.recordHits(hits);
      this.globalStatsCounter.recordMisses(misses);
      return ImmutableMap.copyOf(result);
   }

   ImmutableMap<K, V> getAll(Iterable<? extends K> keys) throws ExecutionException {
      int hits = 0;
      int misses = 0;
      Map<K, V> result = Maps.newLinkedHashMap();
      Set<K> keysToLoad = Sets.newLinkedHashSet();

      for (K key : keys) {
         V value = this.get(key);
         if (!result.containsKey(key)) {
            result.put(key, value);
            if (value == null) {
               misses++;
               keysToLoad.add(key);
            } else {
               hits++;
            }
         }
      }

      ImmutableMap var16;
      try {
         if (!keysToLoad.isEmpty()) {
            try {
               Map<K, V> newEntries = this.loadAll(keysToLoad, this.defaultLoader);

               for (K keyx : keysToLoad) {
                  V value = newEntries.get(keyx);
                  if (value == null) {
                     throw new CacheLoader.InvalidCacheLoadException("loadAll failed to return a value for " + keyx);
                  }

                  result.put(keyx, value);
               }
            } catch (CacheLoader.UnsupportedLoadingOperationException var13) {
               for (K keyx : keysToLoad) {
                  misses--;
                  result.put(keyx, this.get(keyx, this.defaultLoader));
               }
            }
         }

         var16 = ImmutableMap.copyOf(result);
      } finally {
         this.globalStatsCounter.recordHits(hits);
         this.globalStatsCounter.recordMisses(misses);
      }

      return var16;
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Nullable
   Map<K, V> loadAll(Set<? extends K> keys, CacheLoader<? super K, V> loader) throws ExecutionException {
      Preconditions.checkNotNull(loader);
      Preconditions.checkNotNull(keys);
      Stopwatch stopwatch = Stopwatch.createStarted();
      boolean success = false;
      boolean var17 = false /* VF: Semaphore variable */;

      Map result;
      try {
         var17 = true;
         Map<K, V> nullsPresent = loader.loadAll(keys);
         result = nullsPresent;
         success = true;
         var17 = false;
      } catch (CacheLoader.UnsupportedLoadingOperationException var18) {
         success = true;
         throw var18;
      } catch (InterruptedException var19) {
         Thread.currentThread().interrupt();
         throw new ExecutionException(var19);
      } catch (RuntimeException var20) {
         throw new UncheckedExecutionException(var20);
      } catch (Exception var21) {
         throw new ExecutionException(var21);
      } catch (Error var22) {
         throw new ExecutionError(var22);
      } finally {
         if (var17) {
            if (!success) {
               this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
            }
         }
      }

      if (result == null) {
         this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
         throw new CacheLoader.InvalidCacheLoadException(loader + " returned null map from loadAll");
      } else {
         stopwatch.stop();
         boolean nullsPresent = false;

         for (Entry<K, V> entry : result.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            if (key != null && value != null) {
               this.put(key, value);
            } else {
               nullsPresent = true;
            }
         }

         if (nullsPresent) {
            this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
            throw new CacheLoader.InvalidCacheLoadException(loader + " returned null keys or values from loadAll");
         } else {
            this.globalStatsCounter.recordLoadSuccess(stopwatch.elapsed(TimeUnit.NANOSECONDS));
            return result;
         }
      }
   }

   LocalCache.ReferenceEntry<K, V> getEntry(@Nullable Object key) {
      if (key == null) {
         return null;
      } else {
         int hash = this.hash(key);
         return this.segmentFor(hash).getEntry(key, hash);
      }
   }

   void refresh(K key) {
      int hash = this.hash(Preconditions.checkNotNull(key));
      this.segmentFor(hash).refresh(key, hash, this.defaultLoader, false);
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
         long now = this.ticker.read();
         LocalCache.Segment<K, V>[] segments = this.segments;
         long last = -1L;

         for (int i = 0; i < 3; i++) {
            long sum = 0L;

            for (LocalCache.Segment<K, V> segment : segments) {
               int c = segment.count;
               AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = segment.table;

               for (int j = 0; j < table.length(); j++) {
                  for (LocalCache.ReferenceEntry<K, V> e = table.get(j); e != null; e = e.getNext()) {
                     V v = segment.getLiveValue(e, now);
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
      for (LocalCache.Segment<K, V> segment : this.segments) {
         segment.clear();
      }
   }

   void invalidateAll(Iterable<?> keys) {
      for (Object key : keys) {
         this.remove(key);
      }
   }

   @Override
   public Set<K> keySet() {
      Set<K> ks = this.keySet;
      return ks != null ? ks : (this.keySet = new LocalCache.KeySet(this));
   }

   @Override
   public Collection<V> values() {
      Collection<V> vs = this.values;
      return vs != null ? vs : (this.values = new LocalCache.Values(this));
   }

   @GwtIncompatible("Not supported.")
   @Override
   public Set<Entry<K, V>> entrySet() {
      Set<Entry<K, V>> es = this.entrySet;
      return es != null ? es : (this.entrySet = new LocalCache.EntrySet(this));
   }

   abstract class AbstractCacheSet<T> extends AbstractSet<T> {
      final ConcurrentMap<?, ?> map;

      AbstractCacheSet(ConcurrentMap<?, ?> map) {
         this.map = map;
      }

      @Override
      public int size() {
         return this.map.size();
      }

      @Override
      public boolean isEmpty() {
         return this.map.isEmpty();
      }

      @Override
      public void clear() {
         this.map.clear();
      }
   }

   abstract static class AbstractReferenceEntry<K, V> implements LocalCache.ReferenceEntry<K, V> {
      @Override
      public LocalCache.ValueReference<K, V> getValueReference() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setValueReference(LocalCache.ValueReference<K, V> valueReference) {
         throw new UnsupportedOperationException();
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getNext() {
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
      public long getAccessTime() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setAccessTime(long time) {
         throw new UnsupportedOperationException();
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getNextInAccessQueue() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> next) {
         throw new UnsupportedOperationException();
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> previous) {
         throw new UnsupportedOperationException();
      }

      @Override
      public long getWriteTime() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setWriteTime(long time) {
         throw new UnsupportedOperationException();
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getNextInWriteQueue() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> next) {
         throw new UnsupportedOperationException();
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> previous) {
         throw new UnsupportedOperationException();
      }
   }

   static final class AccessQueue<K, V> extends AbstractQueue<LocalCache.ReferenceEntry<K, V>> {
      final LocalCache.ReferenceEntry<K, V> head = new LocalCache.AbstractReferenceEntry<K, V>() {
         LocalCache.ReferenceEntry<K, V> nextAccess = this;
         LocalCache.ReferenceEntry<K, V> previousAccess = this;

         @Override
         public long getAccessTime() {
            return Long.MAX_VALUE;
         }

         @Override
         public void setAccessTime(long time) {
         }

         @Override
         public LocalCache.ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
         }

         @Override
         public void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> next) {
            this.nextAccess = next;
         }

         @Override
         public LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
         }

         @Override
         public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> previous) {
            this.previousAccess = previous;
         }
      };

      public boolean offer(LocalCache.ReferenceEntry<K, V> entry) {
         LocalCache.connectAccessOrder(entry.getPreviousInAccessQueue(), entry.getNextInAccessQueue());
         LocalCache.connectAccessOrder(this.head.getPreviousInAccessQueue(), entry);
         LocalCache.connectAccessOrder(entry, this.head);
         return true;
      }

      public LocalCache.ReferenceEntry<K, V> peek() {
         LocalCache.ReferenceEntry<K, V> next = this.head.getNextInAccessQueue();
         return next == this.head ? null : next;
      }

      public LocalCache.ReferenceEntry<K, V> poll() {
         LocalCache.ReferenceEntry<K, V> next = this.head.getNextInAccessQueue();
         if (next == this.head) {
            return null;
         } else {
            this.remove(next);
            return next;
         }
      }

      @Override
      public boolean remove(Object o) {
         LocalCache.ReferenceEntry<K, V> e = (LocalCache.ReferenceEntry<K, V>)o;
         LocalCache.ReferenceEntry<K, V> previous = e.getPreviousInAccessQueue();
         LocalCache.ReferenceEntry<K, V> next = e.getNextInAccessQueue();
         LocalCache.connectAccessOrder(previous, next);
         LocalCache.nullifyAccessOrder(e);
         return next != LocalCache.NullEntry.INSTANCE;
      }

      @Override
      public boolean contains(Object o) {
         LocalCache.ReferenceEntry<K, V> e = (LocalCache.ReferenceEntry<K, V>)o;
         return e.getNextInAccessQueue() != LocalCache.NullEntry.INSTANCE;
      }

      @Override
      public boolean isEmpty() {
         return this.head.getNextInAccessQueue() == this.head;
      }

      @Override
      public int size() {
         int size = 0;

         for (LocalCache.ReferenceEntry<K, V> e = this.head.getNextInAccessQueue(); e != this.head; e = e.getNextInAccessQueue()) {
            size++;
         }

         return size;
      }

      @Override
      public void clear() {
         LocalCache.ReferenceEntry<K, V> e = this.head.getNextInAccessQueue();

         while (e != this.head) {
            LocalCache.ReferenceEntry<K, V> next = e.getNextInAccessQueue();
            LocalCache.nullifyAccessOrder(e);
            e = next;
         }

         this.head.setNextInAccessQueue(this.head);
         this.head.setPreviousInAccessQueue(this.head);
      }

      @Override
      public Iterator<LocalCache.ReferenceEntry<K, V>> iterator() {
         return new AbstractSequentialIterator<LocalCache.ReferenceEntry<K, V>>(this.peek()) {
            protected LocalCache.ReferenceEntry<K, V> computeNext(LocalCache.ReferenceEntry<K, V> previous) {
               LocalCache.ReferenceEntry<K, V> next = previous.getNextInAccessQueue();
               return next == AccessQueue.this.head ? null : next;
            }
         };
      }
   }

   static enum EntryFactory {
      STRONG {
         @Override
         <K, V> LocalCache.ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> segment, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) {
            return new LocalCache.StrongEntry<>(key, hash, next);
         }
      },
      STRONG_ACCESS {
         @Override
         <K, V> LocalCache.ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> segment, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) {
            return new LocalCache.StrongAccessEntry<>(key, hash, next);
         }

         @Override
         <K, V> LocalCache.ReferenceEntry<K, V> copyEntry(
            LocalCache.Segment<K, V> segment, LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newNext
         ) {
            LocalCache.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
            this.copyAccessEntry(original, newEntry);
            return newEntry;
         }
      },
      STRONG_WRITE {
         @Override
         <K, V> LocalCache.ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> segment, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) {
            return new LocalCache.StrongWriteEntry<>(key, hash, next);
         }

         @Override
         <K, V> LocalCache.ReferenceEntry<K, V> copyEntry(
            LocalCache.Segment<K, V> segment, LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newNext
         ) {
            LocalCache.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
            this.copyWriteEntry(original, newEntry);
            return newEntry;
         }
      },
      STRONG_ACCESS_WRITE {
         @Override
         <K, V> LocalCache.ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> segment, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) {
            return new LocalCache.StrongAccessWriteEntry<>(key, hash, next);
         }

         @Override
         <K, V> LocalCache.ReferenceEntry<K, V> copyEntry(
            LocalCache.Segment<K, V> segment, LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newNext
         ) {
            LocalCache.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
            this.copyAccessEntry(original, newEntry);
            this.copyWriteEntry(original, newEntry);
            return newEntry;
         }
      },
      WEAK {
         @Override
         <K, V> LocalCache.ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> segment, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) {
            return new LocalCache.WeakEntry<>(segment.keyReferenceQueue, key, hash, next);
         }
      },
      WEAK_ACCESS {
         @Override
         <K, V> LocalCache.ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> segment, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) {
            return new LocalCache.WeakAccessEntry<>(segment.keyReferenceQueue, key, hash, next);
         }

         @Override
         <K, V> LocalCache.ReferenceEntry<K, V> copyEntry(
            LocalCache.Segment<K, V> segment, LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newNext
         ) {
            LocalCache.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
            this.copyAccessEntry(original, newEntry);
            return newEntry;
         }
      },
      WEAK_WRITE {
         @Override
         <K, V> LocalCache.ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> segment, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) {
            return new LocalCache.WeakWriteEntry<>(segment.keyReferenceQueue, key, hash, next);
         }

         @Override
         <K, V> LocalCache.ReferenceEntry<K, V> copyEntry(
            LocalCache.Segment<K, V> segment, LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newNext
         ) {
            LocalCache.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
            this.copyWriteEntry(original, newEntry);
            return newEntry;
         }
      },
      WEAK_ACCESS_WRITE {
         @Override
         <K, V> LocalCache.ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> segment, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) {
            return new LocalCache.WeakAccessWriteEntry<>(segment.keyReferenceQueue, key, hash, next);
         }

         @Override
         <K, V> LocalCache.ReferenceEntry<K, V> copyEntry(
            LocalCache.Segment<K, V> segment, LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newNext
         ) {
            LocalCache.ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
            this.copyAccessEntry(original, newEntry);
            this.copyWriteEntry(original, newEntry);
            return newEntry;
         }
      };

      static final int ACCESS_MASK = 1;
      static final int WRITE_MASK = 2;
      static final int WEAK_MASK = 4;
      static final LocalCache.EntryFactory[] factories = new LocalCache.EntryFactory[]{
         STRONG, STRONG_ACCESS, STRONG_WRITE, STRONG_ACCESS_WRITE, WEAK, WEAK_ACCESS, WEAK_WRITE, WEAK_ACCESS_WRITE
      };

      private EntryFactory() {
      }

      static LocalCache.EntryFactory getFactory(LocalCache.Strength keyStrength, boolean usesAccessQueue, boolean usesWriteQueue) {
         int flags = (keyStrength == LocalCache.Strength.WEAK ? 4 : 0) | (usesAccessQueue ? 1 : 0) | (usesWriteQueue ? 2 : 0);
         return factories[flags];
      }

      abstract <K, V> LocalCache.ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> var1, K var2, int var3, @Nullable LocalCache.ReferenceEntry<K, V> var4);

      @GuardedBy("Segment.this")
      <K, V> LocalCache.ReferenceEntry<K, V> copyEntry(
         LocalCache.Segment<K, V> segment, LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newNext
      ) {
         return this.newEntry(segment, original.getKey(), original.getHash(), newNext);
      }

      @GuardedBy("Segment.this")
      <K, V> void copyAccessEntry(LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newEntry) {
         newEntry.setAccessTime(original.getAccessTime());
         LocalCache.connectAccessOrder(original.getPreviousInAccessQueue(), newEntry);
         LocalCache.connectAccessOrder(newEntry, original.getNextInAccessQueue());
         LocalCache.nullifyAccessOrder(original);
      }

      @GuardedBy("Segment.this")
      <K, V> void copyWriteEntry(LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newEntry) {
         newEntry.setWriteTime(original.getWriteTime());
         LocalCache.connectWriteOrder(original.getPreviousInWriteQueue(), newEntry);
         LocalCache.connectWriteOrder(newEntry, original.getNextInWriteQueue());
         LocalCache.nullifyWriteOrder(original);
      }
   }

   final class EntryIterator extends LocalCache<K, V>.HashIterator<Entry<K, V>> {
      public Entry<K, V> next() {
         return this.nextEntry();
      }
   }

   final class EntrySet extends LocalCache<K, V>.AbstractCacheSet<Entry<K, V>> {
      EntrySet(ConcurrentMap<?, ?> map) {
         super(map);
      }

      @Override
      public Iterator<Entry<K, V>> iterator() {
         return LocalCache.this.new EntryIterator();
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
               V v = LocalCache.this.get(key);
               return v != null && LocalCache.this.valueEquivalence.equivalent(e.getValue(), v);
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
            return key != null && LocalCache.this.remove(key, e.getValue());
         }
      }
   }

   abstract class HashIterator<T> implements Iterator<T> {
      int nextSegmentIndex = LocalCache.this.segments.length - 1;
      int nextTableIndex = -1;
      LocalCache.Segment<K, V> currentSegment;
      AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> currentTable;
      LocalCache.ReferenceEntry<K, V> nextEntry;
      LocalCache<K, V>.WriteThroughEntry nextExternal;
      LocalCache<K, V>.WriteThroughEntry lastReturned;

      HashIterator() {
         this.advance();
      }

      @Override
      public abstract T next();

      final void advance() {
         this.nextExternal = null;
         if (!this.nextInChain()) {
            if (!this.nextInTable()) {
               while (this.nextSegmentIndex >= 0) {
                  this.currentSegment = LocalCache.this.segments[this.nextSegmentIndex--];
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

      boolean advanceTo(LocalCache.ReferenceEntry<K, V> entry) {
         boolean var6;
         try {
            long now = LocalCache.this.ticker.read();
            K key = entry.getKey();
            V value = LocalCache.this.getLiveValue(entry, now);
            if (value == null) {
               return false;
            }

            this.nextExternal = LocalCache.this.new WriteThroughEntry(key, value);
            var6 = true;
         } finally {
            this.currentSegment.postReadCleanup();
         }

         return var6;
      }

      @Override
      public boolean hasNext() {
         return this.nextExternal != null;
      }

      LocalCache<K, V>.WriteThroughEntry nextEntry() {
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
         Preconditions.checkState(this.lastReturned != null);
         LocalCache.this.remove(this.lastReturned.getKey());
         this.lastReturned = null;
      }
   }

   final class KeyIterator extends LocalCache<K, V>.HashIterator<K> {
      @Override
      public K next() {
         return this.nextEntry().getKey();
      }
   }

   final class KeySet extends LocalCache<K, V>.AbstractCacheSet<K> {
      KeySet(ConcurrentMap<?, ?> map) {
         super(map);
      }

      @Override
      public Iterator<K> iterator() {
         return LocalCache.this.new KeyIterator();
      }

      @Override
      public boolean contains(Object o) {
         return this.map.containsKey(o);
      }

      @Override
      public boolean remove(Object o) {
         return this.map.remove(o) != null;
      }
   }

   static final class LoadingSerializationProxy<K, V> extends LocalCache.ManualSerializationProxy<K, V> implements LoadingCache<K, V>, Serializable {
      private static final long serialVersionUID = 1L;
      transient LoadingCache<K, V> autoDelegate;

      LoadingSerializationProxy(LocalCache<K, V> cache) {
         super(cache);
      }

      private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
         in.defaultReadObject();
         CacheBuilder<K, V> builder = this.recreateCacheBuilder();
         this.autoDelegate = builder.build(this.loader);
      }

      @Override
      public V get(K key) throws ExecutionException {
         return this.autoDelegate.get(key);
      }

      @Override
      public V getUnchecked(K key) {
         return this.autoDelegate.getUnchecked(key);
      }

      @Override
      public ImmutableMap<K, V> getAll(Iterable<? extends K> keys) throws ExecutionException {
         return this.autoDelegate.getAll(keys);
      }

      @Override
      public final V apply(K key) {
         return this.autoDelegate.apply(key);
      }

      @Override
      public void refresh(K key) {
         this.autoDelegate.refresh(key);
      }

      private Object readResolve() {
         return this.autoDelegate;
      }
   }

   static class LoadingValueReference<K, V> implements LocalCache.ValueReference<K, V> {
      volatile LocalCache.ValueReference<K, V> oldValue;
      final SettableFuture<V> futureValue = SettableFuture.create();
      final Stopwatch stopwatch = Stopwatch.createUnstarted();

      public LoadingValueReference() {
         this(LocalCache.unset());
      }

      public LoadingValueReference(LocalCache.ValueReference<K, V> oldValue) {
         this.oldValue = oldValue;
      }

      @Override
      public boolean isLoading() {
         return true;
      }

      @Override
      public boolean isActive() {
         return this.oldValue.isActive();
      }

      @Override
      public int getWeight() {
         return this.oldValue.getWeight();
      }

      public boolean set(@Nullable V newValue) {
         return this.futureValue.set(newValue);
      }

      public boolean setException(Throwable t) {
         return this.futureValue.setException(t);
      }

      private ListenableFuture<V> fullyFailedFuture(Throwable t) {
         return Futures.immediateFailedFuture(t);
      }

      @Override
      public void notifyNewValue(@Nullable V newValue) {
         if (newValue != null) {
            this.set(newValue);
         } else {
            this.oldValue = LocalCache.unset();
         }
      }

      public ListenableFuture<V> loadFuture(K key, CacheLoader<? super K, V> loader) {
         this.stopwatch.start();
         V previousValue = this.oldValue.get();

         try {
            if (previousValue == null) {
               V newValue = loader.load(key);
               return (ListenableFuture<V>)(this.set(newValue) ? this.futureValue : Futures.immediateFuture(newValue));
            } else {
               ListenableFuture<V> newValue = loader.reload(key, previousValue);
               return newValue == null ? Futures.immediateFuture(null) : Futures.transform(newValue, new Function<V, V>() {
                  @Override
                  public V apply(V newValue) {
                     LoadingValueReference.this.set(newValue);
                     return newValue;
                  }
               });
            }
         } catch (Throwable var5) {
            if (var5 instanceof InterruptedException) {
               Thread.currentThread().interrupt();
            }

            return (ListenableFuture<V>)(this.setException(var5) ? this.futureValue : this.fullyFailedFuture(var5));
         }
      }

      public long elapsedNanos() {
         return this.stopwatch.elapsed(TimeUnit.NANOSECONDS);
      }

      @Override
      public V waitForValue() throws ExecutionException {
         return Uninterruptibles.getUninterruptibly(this.futureValue);
      }

      @Override
      public V get() {
         return this.oldValue.get();
      }

      public LocalCache.ValueReference<K, V> getOldValue() {
         return this.oldValue;
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getEntry() {
         return null;
      }

      @Override
      public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, @Nullable V value, LocalCache.ReferenceEntry<K, V> entry) {
         return this;
      }
   }

   static class LocalLoadingCache<K, V> extends LocalCache.LocalManualCache<K, V> implements LoadingCache<K, V> {
      private static final long serialVersionUID = 1L;

      LocalLoadingCache(CacheBuilder<? super K, ? super V> builder, CacheLoader<? super K, V> loader) {
         super(new LocalCache<>(builder, Preconditions.checkNotNull(loader)));
      }

      @Override
      public V get(K key) throws ExecutionException {
         return this.localCache.getOrLoad(key);
      }

      @Override
      public V getUnchecked(K key) {
         try {
            return this.get(key);
         } catch (ExecutionException var3) {
            throw new UncheckedExecutionException(var3.getCause());
         }
      }

      @Override
      public ImmutableMap<K, V> getAll(Iterable<? extends K> keys) throws ExecutionException {
         return this.localCache.getAll(keys);
      }

      @Override
      public void refresh(K key) {
         this.localCache.refresh(key);
      }

      @Override
      public final V apply(K key) {
         return this.getUnchecked(key);
      }

      @Override
      Object writeReplace() {
         return new LocalCache.LoadingSerializationProxy<>(this.localCache);
      }
   }

   static class LocalManualCache<K, V> implements Cache<K, V>, Serializable {
      final LocalCache<K, V> localCache;
      private static final long serialVersionUID = 1L;

      LocalManualCache(CacheBuilder<? super K, ? super V> builder) {
         this(new LocalCache<>(builder, null));
      }

      private LocalManualCache(LocalCache<K, V> localCache) {
         this.localCache = localCache;
      }

      @Nullable
      @Override
      public V getIfPresent(Object key) {
         return this.localCache.getIfPresent(key);
      }

      @Override
      public V get(K key, final Callable<? extends V> valueLoader) throws ExecutionException {
         Preconditions.checkNotNull(valueLoader);
         return this.localCache.get(key, new CacheLoader<Object, V>() {
            @Override
            public V load(Object key) throws Exception {
               return (V)valueLoader.call();
            }
         });
      }

      @Override
      public ImmutableMap<K, V> getAllPresent(Iterable<?> keys) {
         return this.localCache.getAllPresent(keys);
      }

      @Override
      public void put(K key, V value) {
         this.localCache.put(key, value);
      }

      @Override
      public void putAll(Map<? extends K, ? extends V> m) {
         this.localCache.putAll(m);
      }

      @Override
      public void invalidate(Object key) {
         Preconditions.checkNotNull(key);
         this.localCache.remove(key);
      }

      @Override
      public void invalidateAll(Iterable<?> keys) {
         this.localCache.invalidateAll(keys);
      }

      @Override
      public void invalidateAll() {
         this.localCache.clear();
      }

      @Override
      public long size() {
         return this.localCache.longSize();
      }

      @Override
      public ConcurrentMap<K, V> asMap() {
         return this.localCache;
      }

      @Override
      public CacheStats stats() {
         AbstractCache.SimpleStatsCounter aggregator = new AbstractCache.SimpleStatsCounter();
         aggregator.incrementBy(this.localCache.globalStatsCounter);

         for (LocalCache.Segment<K, V> segment : this.localCache.segments) {
            aggregator.incrementBy(segment.statsCounter);
         }

         return aggregator.snapshot();
      }

      @Override
      public void cleanUp() {
         this.localCache.cleanUp();
      }

      Object writeReplace() {
         return new LocalCache.ManualSerializationProxy<>(this.localCache);
      }
   }

   static class ManualSerializationProxy<K, V> extends ForwardingCache<K, V> implements Serializable {
      private static final long serialVersionUID = 1L;
      final LocalCache.Strength keyStrength;
      final LocalCache.Strength valueStrength;
      final Equivalence<Object> keyEquivalence;
      final Equivalence<Object> valueEquivalence;
      final long expireAfterWriteNanos;
      final long expireAfterAccessNanos;
      final long maxWeight;
      final Weigher<K, V> weigher;
      final int concurrencyLevel;
      final RemovalListener<? super K, ? super V> removalListener;
      final Ticker ticker;
      final CacheLoader<? super K, V> loader;
      transient Cache<K, V> delegate;

      ManualSerializationProxy(LocalCache<K, V> cache) {
         this(
            cache.keyStrength,
            cache.valueStrength,
            cache.keyEquivalence,
            cache.valueEquivalence,
            cache.expireAfterWriteNanos,
            cache.expireAfterAccessNanos,
            cache.maxWeight,
            cache.weigher,
            cache.concurrencyLevel,
            cache.removalListener,
            cache.ticker,
            cache.defaultLoader
         );
      }

      private ManualSerializationProxy(
         LocalCache.Strength keyStrength,
         LocalCache.Strength valueStrength,
         Equivalence<Object> keyEquivalence,
         Equivalence<Object> valueEquivalence,
         long expireAfterWriteNanos,
         long expireAfterAccessNanos,
         long maxWeight,
         Weigher<K, V> weigher,
         int concurrencyLevel,
         RemovalListener<? super K, ? super V> removalListener,
         Ticker ticker,
         CacheLoader<? super K, V> loader
      ) {
         this.keyStrength = keyStrength;
         this.valueStrength = valueStrength;
         this.keyEquivalence = keyEquivalence;
         this.valueEquivalence = valueEquivalence;
         this.expireAfterWriteNanos = expireAfterWriteNanos;
         this.expireAfterAccessNanos = expireAfterAccessNanos;
         this.maxWeight = maxWeight;
         this.weigher = weigher;
         this.concurrencyLevel = concurrencyLevel;
         this.removalListener = removalListener;
         this.ticker = ticker != Ticker.systemTicker() && ticker != CacheBuilder.NULL_TICKER ? ticker : null;
         this.loader = loader;
      }

      CacheBuilder<K, V> recreateCacheBuilder() {
         CacheBuilder<K, V> builder = CacheBuilder.newBuilder()
            .setKeyStrength(this.keyStrength)
            .setValueStrength(this.valueStrength)
            .keyEquivalence(this.keyEquivalence)
            .valueEquivalence(this.valueEquivalence)
            .concurrencyLevel(this.concurrencyLevel)
            .removalListener(this.removalListener);
         builder.strictParsing = false;
         if (this.expireAfterWriteNanos > 0L) {
            builder.expireAfterWrite(this.expireAfterWriteNanos, TimeUnit.NANOSECONDS);
         }

         if (this.expireAfterAccessNanos > 0L) {
            builder.expireAfterAccess(this.expireAfterAccessNanos, TimeUnit.NANOSECONDS);
         }

         if (this.weigher != CacheBuilder.OneWeigher.INSTANCE) {
            builder.weigher(this.weigher);
            if (this.maxWeight != -1L) {
               builder.maximumWeight(this.maxWeight);
            }
         } else if (this.maxWeight != -1L) {
            builder.maximumSize(this.maxWeight);
         }

         if (this.ticker != null) {
            builder.ticker(this.ticker);
         }

         return builder;
      }

      private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
         in.defaultReadObject();
         CacheBuilder<K, V> builder = this.recreateCacheBuilder();
         this.delegate = builder.build();
      }

      private Object readResolve() {
         return this.delegate;
      }

      @Override
      protected Cache<K, V> delegate() {
         return this.delegate;
      }
   }

   private static enum NullEntry implements LocalCache.ReferenceEntry<Object, Object> {
      INSTANCE;

      @Override
      public LocalCache.ValueReference<Object, Object> getValueReference() {
         return null;
      }

      @Override
      public void setValueReference(LocalCache.ValueReference<Object, Object> valueReference) {
      }

      @Override
      public LocalCache.ReferenceEntry<Object, Object> getNext() {
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
      public long getAccessTime() {
         return 0L;
      }

      @Override
      public void setAccessTime(long time) {
      }

      @Override
      public LocalCache.ReferenceEntry<Object, Object> getNextInAccessQueue() {
         return this;
      }

      @Override
      public void setNextInAccessQueue(LocalCache.ReferenceEntry<Object, Object> next) {
      }

      @Override
      public LocalCache.ReferenceEntry<Object, Object> getPreviousInAccessQueue() {
         return this;
      }

      @Override
      public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<Object, Object> previous) {
      }

      @Override
      public long getWriteTime() {
         return 0L;
      }

      @Override
      public void setWriteTime(long time) {
      }

      @Override
      public LocalCache.ReferenceEntry<Object, Object> getNextInWriteQueue() {
         return this;
      }

      @Override
      public void setNextInWriteQueue(LocalCache.ReferenceEntry<Object, Object> next) {
      }

      @Override
      public LocalCache.ReferenceEntry<Object, Object> getPreviousInWriteQueue() {
         return this;
      }

      @Override
      public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<Object, Object> previous) {
      }
   }

   interface ReferenceEntry<K, V> {
      LocalCache.ValueReference<K, V> getValueReference();

      void setValueReference(LocalCache.ValueReference<K, V> var1);

      @Nullable
      LocalCache.ReferenceEntry<K, V> getNext();

      int getHash();

      @Nullable
      K getKey();

      long getAccessTime();

      void setAccessTime(long var1);

      LocalCache.ReferenceEntry<K, V> getNextInAccessQueue();

      void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> var1);

      LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue();

      void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> var1);

      long getWriteTime();

      void setWriteTime(long var1);

      LocalCache.ReferenceEntry<K, V> getNextInWriteQueue();

      void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> var1);

      LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue();

      void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> var1);
   }

   static class Segment<K, V> extends ReentrantLock {
      final LocalCache<K, V> map;
      volatile int count;
      @GuardedBy("Segment.this")
      int totalWeight;
      int modCount;
      int threshold;
      volatile AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table;
      final long maxSegmentWeight;
      final ReferenceQueue<K> keyReferenceQueue;
      final ReferenceQueue<V> valueReferenceQueue;
      final Queue<LocalCache.ReferenceEntry<K, V>> recencyQueue;
      final AtomicInteger readCount = new AtomicInteger();
      @GuardedBy("Segment.this")
      final Queue<LocalCache.ReferenceEntry<K, V>> writeQueue;
      @GuardedBy("Segment.this")
      final Queue<LocalCache.ReferenceEntry<K, V>> accessQueue;
      final AbstractCache.StatsCounter statsCounter;

      Segment(LocalCache<K, V> map, int initialCapacity, long maxSegmentWeight, AbstractCache.StatsCounter statsCounter) {
         this.map = map;
         this.maxSegmentWeight = maxSegmentWeight;
         this.statsCounter = Preconditions.checkNotNull(statsCounter);
         this.initTable(this.newEntryArray(initialCapacity));
         this.keyReferenceQueue = map.usesKeyReferences() ? new ReferenceQueue<>() : null;
         this.valueReferenceQueue = map.usesValueReferences() ? new ReferenceQueue<>() : null;
         this.recencyQueue = (Queue<LocalCache.ReferenceEntry<K, V>>)(map.usesAccessQueue() ? new ConcurrentLinkedQueue<>() : LocalCache.discardingQueue());
         this.writeQueue = (Queue<LocalCache.ReferenceEntry<K, V>>)(map.usesWriteQueue() ? new LocalCache.WriteQueue<>() : LocalCache.discardingQueue());
         this.accessQueue = (Queue<LocalCache.ReferenceEntry<K, V>>)(map.usesAccessQueue() ? new LocalCache.AccessQueue<>() : LocalCache.discardingQueue());
      }

      AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> newEntryArray(int size) {
         return new AtomicReferenceArray<>(size);
      }

      void initTable(AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> newTable) {
         this.threshold = newTable.length() * 3 / 4;
         if (!this.map.customWeigher() && this.threshold == this.maxSegmentWeight) {
            this.threshold++;
         }

         this.table = newTable;
      }

      @GuardedBy("Segment.this")
      LocalCache.ReferenceEntry<K, V> newEntry(K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) {
         return this.map.entryFactory.newEntry(this, Preconditions.checkNotNull(key), hash, next);
      }

      @GuardedBy("Segment.this")
      LocalCache.ReferenceEntry<K, V> copyEntry(LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newNext) {
         if (original.getKey() == null) {
            return null;
         } else {
            LocalCache.ValueReference<K, V> valueReference = original.getValueReference();
            V value = valueReference.get();
            if (value == null && valueReference.isActive()) {
               return null;
            } else {
               LocalCache.ReferenceEntry<K, V> newEntry = this.map.entryFactory.copyEntry(this, original, newNext);
               newEntry.setValueReference(valueReference.copyFor(this.valueReferenceQueue, value, newEntry));
               return newEntry;
            }
         }
      }

      @GuardedBy("Segment.this")
      void setValue(LocalCache.ReferenceEntry<K, V> entry, K key, V value, long now) {
         LocalCache.ValueReference<K, V> previous = entry.getValueReference();
         int weight = this.map.weigher.weigh(key, value);
         Preconditions.checkState(weight >= 0, "Weights must be non-negative");
         LocalCache.ValueReference<K, V> valueReference = this.map.valueStrength.referenceValue(this, entry, value, weight);
         entry.setValueReference(valueReference);
         this.recordWrite(entry, weight, now);
         previous.notifyNewValue(value);
      }

      V get(K key, int hash, CacheLoader<? super K, V> loader) throws ExecutionException {
         Preconditions.checkNotNull(key);
         Preconditions.checkNotNull(loader);

         try {
            if (this.count != 0) {
               LocalCache.ReferenceEntry<K, V> e = this.getEntry(key, hash);
               if (e != null) {
                  long now = this.map.ticker.read();
                  V value = this.getLiveValue(e, now);
                  if (value != null) {
                     this.recordRead(e, now);
                     this.statsCounter.recordHits(1);
                     return this.scheduleRefresh(e, key, hash, value, now, loader);
                  }

                  LocalCache.ValueReference<K, V> valueReference = e.getValueReference();
                  if (valueReference.isLoading()) {
                     return this.waitForLoadingValue(e, key, valueReference);
                  }
               }
            }

            return this.lockedGetOrLoad(key, hash, loader);
         } catch (ExecutionException var13) {
            Throwable cause = var13.getCause();
            if (cause instanceof Error) {
               throw new ExecutionError((Error)cause);
            } else if (cause instanceof RuntimeException) {
               throw new UncheckedExecutionException(cause);
            } else {
               throw var13;
            }
         } finally {
            this.postReadCleanup();
         }
      }

      V lockedGetOrLoad(K key, int hash, CacheLoader<? super K, V> loader) throws ExecutionException {
         LocalCache.ValueReference<K, V> valueReference = null;
         LocalCache.LoadingValueReference<K, V> loadingValueReference = null;
         boolean createNewEntry = true;
         this.lock();

         LocalCache.ReferenceEntry<K, V> e;
         try {
            long now = this.map.ticker.read();
            this.preWriteCleanup(now);
            int newCount = this.count - 1;
            AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
            int index = hash & table.length() - 1;
            LocalCache.ReferenceEntry<K, V> first = table.get(index);

            for (e = first; e != null; e = e.getNext()) {
               K entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  valueReference = e.getValueReference();
                  if (valueReference.isLoading()) {
                     createNewEntry = false;
                  } else {
                     V value = valueReference.get();
                     if (value == null) {
                        this.enqueueNotification(entryKey, hash, valueReference, RemovalCause.COLLECTED);
                     } else {
                        if (!this.map.isExpired(e, now)) {
                           this.recordLockedRead(e, now);
                           this.statsCounter.recordHits(1);
                           return value;
                        }

                        this.enqueueNotification(entryKey, hash, valueReference, RemovalCause.EXPIRED);
                     }

                     this.writeQueue.remove(e);
                     this.accessQueue.remove(e);
                     this.count = newCount;
                  }
                  break;
               }
            }

            if (createNewEntry) {
               loadingValueReference = new LocalCache.LoadingValueReference<>();
               if (e == null) {
                  e = this.newEntry(key, hash, first);
                  e.setValueReference(loadingValueReference);
                  table.set(index, e);
               } else {
                  e.setValueReference(loadingValueReference);
               }
            }
         } finally {
            this.unlock();
            this.postWriteCleanup();
         }

         if (createNewEntry) {
            Object var9;
            try {
               synchronized (e) {
                  var9 = this.loadSync(key, hash, loadingValueReference, loader);
               }
            } finally {
               this.statsCounter.recordMisses(1);
            }

            return (V)var9;
         } else {
            return this.waitForLoadingValue(e, key, valueReference);
         }
      }

      V waitForLoadingValue(LocalCache.ReferenceEntry<K, V> e, K key, LocalCache.ValueReference<K, V> valueReference) throws ExecutionException {
         if (!valueReference.isLoading()) {
            throw new AssertionError();
         } else {
            Preconditions.checkState(!Thread.holdsLock(e), "Recursive load of: %s", key);

            Object var7;
            try {
               V value = valueReference.waitForValue();
               if (value == null) {
                  throw new CacheLoader.InvalidCacheLoadException("CacheLoader returned null for key " + key + ".");
               }

               long now = this.map.ticker.read();
               this.recordRead(e, now);
               var7 = value;
            } finally {
               this.statsCounter.recordMisses(1);
            }

            return (V)var7;
         }
      }

      V loadSync(K key, int hash, LocalCache.LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> loader) throws ExecutionException {
         ListenableFuture<V> loadingFuture = loadingValueReference.loadFuture(key, loader);
         return this.getAndRecordStats(key, hash, loadingValueReference, loadingFuture);
      }

      ListenableFuture<V> loadAsync(
         final K key, final int hash, final LocalCache.LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> loader
      ) {
         final ListenableFuture<V> loadingFuture = loadingValueReference.loadFuture(key, loader);
         loadingFuture.addListener(new Runnable() {
            @Override
            public void run() {
               try {
                  V newValue = Segment.this.getAndRecordStats(key, hash, loadingValueReference, loadingFuture);
               } catch (Throwable var2) {
                  LocalCache.logger.log(Level.WARNING, "Exception thrown during refresh", var2);
                  loadingValueReference.setException(var2);
               }
            }
         }, LocalCache.sameThreadExecutor);
         return loadingFuture;
      }

      V getAndRecordStats(K key, int hash, LocalCache.LoadingValueReference<K, V> loadingValueReference, ListenableFuture<V> newValue) throws ExecutionException {
         V value = null;

         try {
            value = Uninterruptibles.getUninterruptibly(newValue);
            if (value == null) {
               throw new CacheLoader.InvalidCacheLoadException("CacheLoader returned null for key " + key + ".");
            } else {
               this.statsCounter.recordLoadSuccess(loadingValueReference.elapsedNanos());
               this.storeLoadedValue(key, hash, loadingValueReference, value);
               return value;
            }
         } finally {
            if (value == null) {
               this.statsCounter.recordLoadException(loadingValueReference.elapsedNanos());
               this.removeLoadingValue(key, hash, loadingValueReference);
            }
         }
      }

      V scheduleRefresh(LocalCache.ReferenceEntry<K, V> entry, K key, int hash, V oldValue, long now, CacheLoader<? super K, V> loader) {
         if (this.map.refreshes() && now - entry.getWriteTime() > this.map.refreshNanos && !entry.getValueReference().isLoading()) {
            V newValue = this.refresh(key, hash, loader, true);
            if (newValue != null) {
               return newValue;
            }
         }

         return oldValue;
      }

      @Nullable
      V refresh(K key, int hash, CacheLoader<? super K, V> loader, boolean checkTime) {
         LocalCache.LoadingValueReference<K, V> loadingValueReference = this.insertLoadingValueReference(key, hash, checkTime);
         if (loadingValueReference == null) {
            return null;
         } else {
            ListenableFuture<V> result = this.loadAsync(key, hash, loadingValueReference, loader);
            if (result.isDone()) {
               try {
                  return Uninterruptibles.getUninterruptibly(result);
               } catch (Throwable var8) {
               }
            }

            return null;
         }
      }

      @Nullable
      LocalCache.LoadingValueReference<K, V> insertLoadingValueReference(K key, int hash, boolean checkTime) {
         LocalCache.ReferenceEntry<K, V> e = null;
         this.lock();

         try {
            long now = this.map.ticker.read();
            this.preWriteCleanup(now);
            AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
            int index = hash & table.length() - 1;
            LocalCache.ReferenceEntry<K, V> first = table.get(index);

            for (LocalCache.ReferenceEntry<K, V> var17 = first; var17 != null; var17 = var17.getNext()) {
               K entryKey = (K)var17.getKey();
               if (var17.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  LocalCache.ValueReference<K, V> valueReference = var17.getValueReference();
                  if (!valueReference.isLoading() && (!checkTime || now - var17.getWriteTime() >= this.map.refreshNanos)) {
                     this.modCount++;
                     LocalCache.LoadingValueReference<K, V> loadingValueReference = new LocalCache.LoadingValueReference<>(valueReference);
                     var17.setValueReference(loadingValueReference);
                     return loadingValueReference;
                  }

                  return null;
               }
            }

            this.modCount++;
            LocalCache.LoadingValueReference<K, V> loadingValueReference = new LocalCache.LoadingValueReference<>();
            e = this.newEntry(key, hash, first);
            e.setValueReference(loadingValueReference);
            table.set(index, e);
            return loadingValueReference;
         } finally {
            this.unlock();
            this.postWriteCleanup();
         }
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
            LocalCache.ReferenceEntry<K, V> entry = (LocalCache.ReferenceEntry<K, V>)ref;
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
            LocalCache.ValueReference<K, V> valueReference = (LocalCache.ValueReference<K, V>)ref;
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

      void recordRead(LocalCache.ReferenceEntry<K, V> entry, long now) {
         if (this.map.recordsAccess()) {
            entry.setAccessTime(now);
         }

         this.recencyQueue.add(entry);
      }

      @GuardedBy("Segment.this")
      void recordLockedRead(LocalCache.ReferenceEntry<K, V> entry, long now) {
         if (this.map.recordsAccess()) {
            entry.setAccessTime(now);
         }

         this.accessQueue.add(entry);
      }

      @GuardedBy("Segment.this")
      void recordWrite(LocalCache.ReferenceEntry<K, V> entry, int weight, long now) {
         this.drainRecencyQueue();
         this.totalWeight += weight;
         if (this.map.recordsAccess()) {
            entry.setAccessTime(now);
         }

         if (this.map.recordsWrite()) {
            entry.setWriteTime(now);
         }

         this.accessQueue.add(entry);
         this.writeQueue.add(entry);
      }

      @GuardedBy("Segment.this")
      void drainRecencyQueue() {
         LocalCache.ReferenceEntry<K, V> e;
         while ((e = this.recencyQueue.poll()) != null) {
            if (this.accessQueue.contains(e)) {
               this.accessQueue.add(e);
            }
         }
      }

      void tryExpireEntries(long now) {
         if (this.tryLock()) {
            try {
               this.expireEntries(now);
            } finally {
               this.unlock();
            }
         }
      }

      @GuardedBy("Segment.this")
      void expireEntries(long now) {
         this.drainRecencyQueue();

         LocalCache.ReferenceEntry<K, V> e;
         while ((e = this.writeQueue.peek()) != null && this.map.isExpired(e, now)) {
            if (!this.removeEntry(e, e.getHash(), RemovalCause.EXPIRED)) {
               throw new AssertionError();
            }
         }

         while ((e = this.accessQueue.peek()) != null && this.map.isExpired(e, now)) {
            if (!this.removeEntry(e, e.getHash(), RemovalCause.EXPIRED)) {
               throw new AssertionError();
            }
         }
      }

      @GuardedBy("Segment.this")
      void enqueueNotification(LocalCache.ReferenceEntry<K, V> entry, RemovalCause cause) {
         this.enqueueNotification(entry.getKey(), entry.getHash(), entry.getValueReference(), cause);
      }

      @GuardedBy("Segment.this")
      void enqueueNotification(@Nullable K key, int hash, LocalCache.ValueReference<K, V> valueReference, RemovalCause cause) {
         this.totalWeight = this.totalWeight - valueReference.getWeight();
         if (cause.wasEvicted()) {
            this.statsCounter.recordEviction();
         }

         if (this.map.removalNotificationQueue != LocalCache.DISCARDING_QUEUE) {
            V value = valueReference.get();
            RemovalNotification<K, V> notification = new RemovalNotification<>(key, value, cause);
            this.map.removalNotificationQueue.offer(notification);
         }
      }

      @GuardedBy("Segment.this")
      void evictEntries() {
         if (this.map.evictsBySize()) {
            this.drainRecencyQueue();

            while (this.totalWeight > this.maxSegmentWeight) {
               LocalCache.ReferenceEntry<K, V> e = this.getNextEvictable();
               if (!this.removeEntry(e, e.getHash(), RemovalCause.SIZE)) {
                  throw new AssertionError();
               }
            }
         }
      }

      LocalCache.ReferenceEntry<K, V> getNextEvictable() {
         for (LocalCache.ReferenceEntry<K, V> e : this.accessQueue) {
            int weight = e.getValueReference().getWeight();
            if (weight > 0) {
               return e;
            }
         }

         throw new AssertionError();
      }

      LocalCache.ReferenceEntry<K, V> getFirst(int hash) {
         AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
         return table.get(hash & table.length() - 1);
      }

      @Nullable
      LocalCache.ReferenceEntry<K, V> getEntry(Object key, int hash) {
         for (LocalCache.ReferenceEntry<K, V> e = this.getFirst(hash); e != null; e = e.getNext()) {
            if (e.getHash() == hash) {
               K entryKey = e.getKey();
               if (entryKey == null) {
                  this.tryDrainReferenceQueues();
               } else if (this.map.keyEquivalence.equivalent(key, entryKey)) {
                  return e;
               }
            }
         }

         return null;
      }

      @Nullable
      LocalCache.ReferenceEntry<K, V> getLiveEntry(Object key, int hash, long now) {
         LocalCache.ReferenceEntry<K, V> e = this.getEntry(key, hash);
         if (e == null) {
            return null;
         } else if (this.map.isExpired(e, now)) {
            this.tryExpireEntries(now);
            return null;
         } else {
            return e;
         }
      }

      V getLiveValue(LocalCache.ReferenceEntry<K, V> entry, long now) {
         if (entry.getKey() == null) {
            this.tryDrainReferenceQueues();
            return null;
         } else {
            V value = entry.getValueReference().get();
            if (value == null) {
               this.tryDrainReferenceQueues();
               return null;
            } else if (this.map.isExpired(entry, now)) {
               this.tryExpireEntries(now);
               return null;
            } else {
               return value;
            }
         }
      }

      @Nullable
      V get(Object key, int hash) {
         try {
            if (this.count != 0) {
               long now = this.map.ticker.read();
               LocalCache.ReferenceEntry<K, V> e = this.getLiveEntry(key, hash, now);
               if (e == null) {
                  return null;
               }

               V value = e.getValueReference().get();
               if (value != null) {
                  this.recordRead(e, now);
                  return this.scheduleRefresh(e, e.getKey(), hash, value, now, this.map.defaultLoader);
               }

               this.tryDrainReferenceQueues();
            }

            return null;
         } finally {
            this.postReadCleanup();
         }
      }

      boolean containsKey(Object key, int hash) {
         boolean var6;
         try {
            if (this.count == 0) {
               return false;
            }

            long now = this.map.ticker.read();
            LocalCache.ReferenceEntry<K, V> e = this.getLiveEntry(key, hash, now);
            if (e != null) {
               return e.getValueReference().get() != null;
            }

            var6 = false;
         } finally {
            this.postReadCleanup();
         }

         return var6;
      }

      @VisibleForTesting
      boolean containsValue(Object value) {
         try {
            if (this.count != 0) {
               long now = this.map.ticker.read();
               AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
               int length = table.length();

               for (int i = 0; i < length; i++) {
                  for (LocalCache.ReferenceEntry<K, V> e = table.get(i); e != null; e = e.getNext()) {
                     V entryValue = this.getLiveValue(e, now);
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

      @Nullable
      V put(K key, int hash, V value, boolean onlyIfAbsent) {
         this.lock();

         try {
            long now = this.map.ticker.read();
            this.preWriteCleanup(now);
            int newCount = this.count + 1;
            if (newCount > this.threshold) {
               this.expand();
               newCount = this.count + 1;
            }

            AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
            int index = hash & table.length() - 1;
            LocalCache.ReferenceEntry<K, V> first = table.get(index);

            for (LocalCache.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
               K entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  LocalCache.ValueReference<K, V> valueReference = e.getValueReference();
                  V entryValue = valueReference.get();
                  if (entryValue != null) {
                     if (!onlyIfAbsent) {
                        this.modCount++;
                        this.enqueueNotification(key, hash, valueReference, RemovalCause.REPLACED);
                        this.setValue(e, key, value, now);
                        this.evictEntries();
                        return entryValue;
                     }

                     this.recordLockedRead(e, now);
                     return entryValue;
                  }

                  this.modCount++;
                  if (valueReference.isActive()) {
                     this.enqueueNotification(key, hash, valueReference, RemovalCause.COLLECTED);
                     this.setValue(e, key, value, now);
                     newCount = this.count;
                  } else {
                     this.setValue(e, key, value, now);
                     newCount = this.count + 1;
                  }

                  this.count = newCount;
                  this.evictEntries();
                  return null;
               }
            }

            this.modCount++;
            LocalCache.ReferenceEntry<K, V> newEntry = this.newEntry(key, hash, first);
            this.setValue(newEntry, key, value, now);
            table.set(index, newEntry);
            newCount = this.count + 1;
            this.count = newCount;
            this.evictEntries();
            return null;
         } finally {
            this.unlock();
            this.postWriteCleanup();
         }
      }

      @GuardedBy("Segment.this")
      void expand() {
         AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> oldTable = this.table;
         int oldCapacity = oldTable.length();
         if (oldCapacity < 1073741824) {
            int newCount = this.count;
            AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> newTable = this.newEntryArray(oldCapacity << 1);
            this.threshold = newTable.length() * 3 / 4;
            int newMask = newTable.length() - 1;

            for (int oldIndex = 0; oldIndex < oldCapacity; oldIndex++) {
               LocalCache.ReferenceEntry<K, V> head = oldTable.get(oldIndex);
               if (head != null) {
                  LocalCache.ReferenceEntry<K, V> next = head.getNext();
                  int headIndex = head.getHash() & newMask;
                  if (next == null) {
                     newTable.set(headIndex, head);
                  } else {
                     LocalCache.ReferenceEntry<K, V> tail = head;
                     int tailIndex = headIndex;

                     for (LocalCache.ReferenceEntry<K, V> e = next; e != null; e = e.getNext()) {
                        int newIndex = e.getHash() & newMask;
                        if (newIndex != tailIndex) {
                           tailIndex = newIndex;
                           tail = e;
                        }
                     }

                     newTable.set(tailIndex, tail);

                     for (LocalCache.ReferenceEntry<K, V> ex = head; ex != tail; ex = ex.getNext()) {
                        int newIndex = ex.getHash() & newMask;
                        LocalCache.ReferenceEntry<K, V> newNext = newTable.get(newIndex);
                        LocalCache.ReferenceEntry<K, V> newFirst = this.copyEntry(ex, newNext);
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
            long now = this.map.ticker.read();
            this.preWriteCleanup(now);
            AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
            int index = hash & table.length() - 1;
            LocalCache.ReferenceEntry<K, V> first = table.get(index);

            for (LocalCache.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
               K entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  LocalCache.ValueReference<K, V> valueReference = e.getValueReference();
                  V entryValue = valueReference.get();
                  if (entryValue != null) {
                     if (!this.map.valueEquivalence.equivalent(oldValue, entryValue)) {
                        this.recordLockedRead(e, now);
                        return false;
                     }

                     this.modCount++;
                     this.enqueueNotification(key, hash, valueReference, RemovalCause.REPLACED);
                     this.setValue(e, key, newValue, now);
                     this.evictEntries();
                     return true;
                  }

                  if (valueReference.isActive()) {
                     int newCount = this.count - 1;
                     this.modCount++;
                     LocalCache.ReferenceEntry<K, V> newFirst = this.removeValueFromChain(first, e, entryKey, hash, valueReference, RemovalCause.COLLECTED);
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

      @Nullable
      V replace(K key, int hash, V newValue) {
         this.lock();

         try {
            long now = this.map.ticker.read();
            this.preWriteCleanup(now);
            AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
            int index = hash & table.length() - 1;
            LocalCache.ReferenceEntry<K, V> first = table.get(index);

            for (LocalCache.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
               K entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  LocalCache.ValueReference<K, V> valueReference = e.getValueReference();
                  V entryValue = valueReference.get();
                  if (entryValue != null) {
                     this.modCount++;
                     this.enqueueNotification(key, hash, valueReference, RemovalCause.REPLACED);
                     this.setValue(e, key, newValue, now);
                     this.evictEntries();
                     return entryValue;
                  }

                  if (valueReference.isActive()) {
                     int newCount = this.count - 1;
                     this.modCount++;
                     LocalCache.ReferenceEntry<K, V> newFirst = this.removeValueFromChain(first, e, entryKey, hash, valueReference, RemovalCause.COLLECTED);
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

      @Nullable
      V remove(Object key, int hash) {
         this.lock();

         try {
            long now = this.map.ticker.read();
            this.preWriteCleanup(now);
            int newCount = this.count - 1;
            AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
            int index = hash & table.length() - 1;
            LocalCache.ReferenceEntry<K, V> first = table.get(index);

            for (LocalCache.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
               K entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  LocalCache.ValueReference<K, V> valueReference = e.getValueReference();
                  V entryValue = valueReference.get();
                  RemovalCause cause;
                  if (entryValue != null) {
                     cause = RemovalCause.EXPLICIT;
                  } else {
                     if (!valueReference.isActive()) {
                        return null;
                     }

                     cause = RemovalCause.COLLECTED;
                  }

                  this.modCount++;
                  LocalCache.ReferenceEntry<K, V> newFirst = this.removeValueFromChain(first, e, entryKey, hash, valueReference, cause);
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

      boolean storeLoadedValue(K key, int hash, LocalCache.LoadingValueReference<K, V> oldValueReference, V newValue) {
         this.lock();

         try {
            long now = this.map.ticker.read();
            this.preWriteCleanup(now);
            int newCount = this.count + 1;
            if (newCount > this.threshold) {
               this.expand();
               newCount = this.count + 1;
            }

            AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
            int index = hash & table.length() - 1;
            LocalCache.ReferenceEntry<K, V> first = table.get(index);

            for (LocalCache.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
               K entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  LocalCache.ValueReference<K, V> valueReference = e.getValueReference();
                  V entryValue = valueReference.get();
                  if (oldValueReference != valueReference && (entryValue != null || valueReference == LocalCache.UNSET)) {
                     LocalCache.ValueReference<K, V> var21 = new LocalCache.WeightedStrongValueReference(newValue, 0);
                     this.enqueueNotification(key, hash, var21, RemovalCause.REPLACED);
                     return false;
                  }

                  this.modCount++;
                  if (oldValueReference.isActive()) {
                     RemovalCause cause = entryValue == null ? RemovalCause.COLLECTED : RemovalCause.REPLACED;
                     this.enqueueNotification(key, hash, oldValueReference, cause);
                     newCount--;
                  }

                  this.setValue(e, key, newValue, now);
                  this.count = newCount;
                  this.evictEntries();
                  return true;
               }
            }

            this.modCount++;
            LocalCache.ReferenceEntry<K, V> newEntry = this.newEntry(key, hash, first);
            this.setValue(newEntry, key, newValue, now);
            table.set(index, newEntry);
            this.count = newCount;
            this.evictEntries();
            return true;
         } finally {
            this.unlock();
            this.postWriteCleanup();
         }
      }

      boolean remove(Object key, int hash, Object value) {
         this.lock();

         try {
            long now = this.map.ticker.read();
            this.preWriteCleanup(now);
            int newCount = this.count - 1;
            AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
            int index = hash & table.length() - 1;
            LocalCache.ReferenceEntry<K, V> first = table.get(index);

            for (LocalCache.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
               K entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  LocalCache.ValueReference<K, V> valueReference = e.getValueReference();
                  V entryValue = valueReference.get();
                  RemovalCause cause;
                  if (this.map.valueEquivalence.equivalent(value, entryValue)) {
                     cause = RemovalCause.EXPLICIT;
                  } else {
                     if (entryValue != null || !valueReference.isActive()) {
                        return false;
                     }

                     cause = RemovalCause.COLLECTED;
                  }

                  this.modCount++;
                  LocalCache.ReferenceEntry<K, V> newFirst = this.removeValueFromChain(first, e, entryKey, hash, valueReference, cause);
                  newCount = this.count - 1;
                  table.set(index, newFirst);
                  this.count = newCount;
                  return cause == RemovalCause.EXPLICIT;
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
               AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;

               for (int i = 0; i < table.length(); i++) {
                  for (LocalCache.ReferenceEntry<K, V> e = table.get(i); e != null; e = e.getNext()) {
                     if (e.getValueReference().isActive()) {
                        this.enqueueNotification(e, RemovalCause.EXPLICIT);
                     }
                  }
               }

               for (int i = 0; i < table.length(); i++) {
                  table.set(i, null);
               }

               this.clearReferenceQueues();
               this.writeQueue.clear();
               this.accessQueue.clear();
               this.readCount.set(0);
               this.modCount++;
               this.count = 0;
            } finally {
               this.unlock();
               this.postWriteCleanup();
            }
         }
      }

      @Nullable
      @GuardedBy("Segment.this")
      LocalCache.ReferenceEntry<K, V> removeValueFromChain(
         LocalCache.ReferenceEntry<K, V> first,
         LocalCache.ReferenceEntry<K, V> entry,
         @Nullable K key,
         int hash,
         LocalCache.ValueReference<K, V> valueReference,
         RemovalCause cause
      ) {
         this.enqueueNotification(key, hash, valueReference, cause);
         this.writeQueue.remove(entry);
         this.accessQueue.remove(entry);
         if (valueReference.isLoading()) {
            valueReference.notifyNewValue(null);
            return first;
         } else {
            return this.removeEntryFromChain(first, entry);
         }
      }

      @Nullable
      @GuardedBy("Segment.this")
      LocalCache.ReferenceEntry<K, V> removeEntryFromChain(LocalCache.ReferenceEntry<K, V> first, LocalCache.ReferenceEntry<K, V> entry) {
         int newCount = this.count;
         LocalCache.ReferenceEntry<K, V> newFirst = entry.getNext();

         for (LocalCache.ReferenceEntry<K, V> e = first; e != entry; e = e.getNext()) {
            LocalCache.ReferenceEntry<K, V> next = this.copyEntry(e, newFirst);
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

      @GuardedBy("Segment.this")
      void removeCollectedEntry(LocalCache.ReferenceEntry<K, V> entry) {
         this.enqueueNotification(entry, RemovalCause.COLLECTED);
         this.writeQueue.remove(entry);
         this.accessQueue.remove(entry);
      }

      boolean reclaimKey(LocalCache.ReferenceEntry<K, V> entry, int hash) {
         this.lock();

         try {
            int newCount = this.count - 1;
            AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
            int index = hash & table.length() - 1;
            LocalCache.ReferenceEntry<K, V> first = table.get(index);

            for (LocalCache.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
               if (e == entry) {
                  this.modCount++;
                  LocalCache.ReferenceEntry<K, V> newFirst = this.removeValueFromChain(
                     first, e, e.getKey(), hash, e.getValueReference(), RemovalCause.COLLECTED
                  );
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

      boolean reclaimValue(K key, int hash, LocalCache.ValueReference<K, V> valueReference) {
         this.lock();

         try {
            int newCount = this.count - 1;
            AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
            int index = hash & table.length() - 1;
            LocalCache.ReferenceEntry<K, V> first = table.get(index);

            for (LocalCache.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
               K entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  LocalCache.ValueReference<K, V> v = e.getValueReference();
                  if (v != valueReference) {
                     return false;
                  }

                  this.modCount++;
                  LocalCache.ReferenceEntry<K, V> newFirst = this.removeValueFromChain(first, e, entryKey, hash, valueReference, RemovalCause.COLLECTED);
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

      boolean removeLoadingValue(K key, int hash, LocalCache.LoadingValueReference<K, V> valueReference) {
         this.lock();

         try {
            AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
            int index = hash & table.length() - 1;
            LocalCache.ReferenceEntry<K, V> first = table.get(index);

            for (LocalCache.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
               K entryKey = e.getKey();
               if (e.getHash() == hash && entryKey != null && this.map.keyEquivalence.equivalent(key, entryKey)) {
                  LocalCache.ValueReference<K, V> v = e.getValueReference();
                  if (v != valueReference) {
                     return false;
                  }

                  if (valueReference.isActive()) {
                     e.setValueReference(valueReference.getOldValue());
                  } else {
                     LocalCache.ReferenceEntry<K, V> newFirst = this.removeEntryFromChain(first, e);
                     table.set(index, newFirst);
                  }

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
      boolean removeEntry(LocalCache.ReferenceEntry<K, V> entry, int hash, RemovalCause cause) {
         int newCount = this.count - 1;
         AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table = this.table;
         int index = hash & table.length() - 1;
         LocalCache.ReferenceEntry<K, V> first = table.get(index);

         for (LocalCache.ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
            if (e == entry) {
               this.modCount++;
               LocalCache.ReferenceEntry<K, V> newFirst = this.removeValueFromChain(first, e, e.getKey(), hash, e.getValueReference(), cause);
               newCount = this.count - 1;
               table.set(index, newFirst);
               this.count = newCount;
               return true;
            }
         }

         return false;
      }

      void postReadCleanup() {
         if ((this.readCount.incrementAndGet() & 63) == 0) {
            this.cleanUp();
         }
      }

      @GuardedBy("Segment.this")
      void preWriteCleanup(long now) {
         this.runLockedCleanup(now);
      }

      void postWriteCleanup() {
         this.runUnlockedCleanup();
      }

      void cleanUp() {
         long now = this.map.ticker.read();
         this.runLockedCleanup(now);
         this.runUnlockedCleanup();
      }

      void runLockedCleanup(long now) {
         if (this.tryLock()) {
            try {
               this.drainReferenceQueues();
               this.expireEntries(now);
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

   static class SoftValueReference<K, V> extends SoftReference<V> implements LocalCache.ValueReference<K, V> {
      final LocalCache.ReferenceEntry<K, V> entry;

      SoftValueReference(ReferenceQueue<V> queue, V referent, LocalCache.ReferenceEntry<K, V> entry) {
         super(referent, queue);
         this.entry = entry;
      }

      @Override
      public int getWeight() {
         return 1;
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getEntry() {
         return this.entry;
      }

      @Override
      public void notifyNewValue(V newValue) {
      }

      @Override
      public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, LocalCache.ReferenceEntry<K, V> entry) {
         return new LocalCache.SoftValueReference<>(queue, value, entry);
      }

      @Override
      public boolean isLoading() {
         return false;
      }

      @Override
      public boolean isActive() {
         return true;
      }

      @Override
      public V waitForValue() {
         return this.get();
      }
   }

   static enum Strength {
      STRONG {
         @Override
         <K, V> LocalCache.ValueReference<K, V> referenceValue(LocalCache.Segment<K, V> segment, LocalCache.ReferenceEntry<K, V> entry, V value, int weight) {
            return (LocalCache.ValueReference<K, V>)(weight == 1
               ? new LocalCache.StrongValueReference<>(value)
               : new LocalCache.WeightedStrongValueReference<>(value, weight));
         }

         @Override
         Equivalence<Object> defaultEquivalence() {
            return Equivalence.equals();
         }
      },
      SOFT {
         @Override
         <K, V> LocalCache.ValueReference<K, V> referenceValue(LocalCache.Segment<K, V> segment, LocalCache.ReferenceEntry<K, V> entry, V value, int weight) {
            return (LocalCache.ValueReference<K, V>)(weight == 1
               ? new LocalCache.SoftValueReference<>(segment.valueReferenceQueue, value, entry)
               : new LocalCache.WeightedSoftValueReference<>(segment.valueReferenceQueue, value, entry, weight));
         }

         @Override
         Equivalence<Object> defaultEquivalence() {
            return Equivalence.identity();
         }
      },
      WEAK {
         @Override
         <K, V> LocalCache.ValueReference<K, V> referenceValue(LocalCache.Segment<K, V> segment, LocalCache.ReferenceEntry<K, V> entry, V value, int weight) {
            return (LocalCache.ValueReference<K, V>)(weight == 1
               ? new LocalCache.WeakValueReference<>(segment.valueReferenceQueue, value, entry)
               : new LocalCache.WeightedWeakValueReference<>(segment.valueReferenceQueue, value, entry, weight));
         }

         @Override
         Equivalence<Object> defaultEquivalence() {
            return Equivalence.identity();
         }
      };

      private Strength() {
      }

      abstract <K, V> LocalCache.ValueReference<K, V> referenceValue(LocalCache.Segment<K, V> var1, LocalCache.ReferenceEntry<K, V> var2, V var3, int var4);

      abstract Equivalence<Object> defaultEquivalence();
   }

   static final class StrongAccessEntry<K, V> extends LocalCache.StrongEntry<K, V> {
      volatile long accessTime = Long.MAX_VALUE;
      @GuardedBy("Segment.this")
      LocalCache.ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
      @GuardedBy("Segment.this")
      LocalCache.ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();

      StrongAccessEntry(K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) {
         super(key, hash, next);
      }

      @Override
      public long getAccessTime() {
         return this.accessTime;
      }

      @Override
      public void setAccessTime(long time) {
         this.accessTime = time;
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getNextInAccessQueue() {
         return this.nextAccess;
      }

      @Override
      public void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> next) {
         this.nextAccess = next;
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue() {
         return this.previousAccess;
      }

      @Override
      public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> previous) {
         this.previousAccess = previous;
      }
   }

   static final class StrongAccessWriteEntry<K, V> extends LocalCache.StrongEntry<K, V> {
      volatile long accessTime = Long.MAX_VALUE;
      @GuardedBy("Segment.this")
      LocalCache.ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
      @GuardedBy("Segment.this")
      LocalCache.ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();
      volatile long writeTime = Long.MAX_VALUE;
      @GuardedBy("Segment.this")
      LocalCache.ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
      @GuardedBy("Segment.this")
      LocalCache.ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();

      StrongAccessWriteEntry(K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) {
         super(key, hash, next);
      }

      @Override
      public long getAccessTime() {
         return this.accessTime;
      }

      @Override
      public void setAccessTime(long time) {
         this.accessTime = time;
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getNextInAccessQueue() {
         return this.nextAccess;
      }

      @Override
      public void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> next) {
         this.nextAccess = next;
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue() {
         return this.previousAccess;
      }

      @Override
      public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> previous) {
         this.previousAccess = previous;
      }

      @Override
      public long getWriteTime() {
         return this.writeTime;
      }

      @Override
      public void setWriteTime(long time) {
         this.writeTime = time;
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getNextInWriteQueue() {
         return this.nextWrite;
      }

      @Override
      public void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> next) {
         this.nextWrite = next;
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue() {
         return this.previousWrite;
      }

      @Override
      public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> previous) {
         this.previousWrite = previous;
      }
   }

   static class StrongEntry<K, V> extends LocalCache.AbstractReferenceEntry<K, V> {
      final K key;
      final int hash;
      final LocalCache.ReferenceEntry<K, V> next;
      volatile LocalCache.ValueReference<K, V> valueReference = LocalCache.unset();

      StrongEntry(K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) {
         this.key = key;
         this.hash = hash;
         this.next = next;
      }

      @Override
      public K getKey() {
         return this.key;
      }

      @Override
      public LocalCache.ValueReference<K, V> getValueReference() {
         return this.valueReference;
      }

      @Override
      public void setValueReference(LocalCache.ValueReference<K, V> valueReference) {
         this.valueReference = valueReference;
      }

      @Override
      public int getHash() {
         return this.hash;
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getNext() {
         return this.next;
      }
   }

   static class StrongValueReference<K, V> implements LocalCache.ValueReference<K, V> {
      final V referent;

      StrongValueReference(V referent) {
         this.referent = referent;
      }

      @Override
      public V get() {
         return this.referent;
      }

      @Override
      public int getWeight() {
         return 1;
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getEntry() {
         return null;
      }

      @Override
      public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, LocalCache.ReferenceEntry<K, V> entry) {
         return this;
      }

      @Override
      public boolean isLoading() {
         return false;
      }

      @Override
      public boolean isActive() {
         return true;
      }

      @Override
      public V waitForValue() {
         return this.get();
      }

      @Override
      public void notifyNewValue(V newValue) {
      }
   }

   static final class StrongWriteEntry<K, V> extends LocalCache.StrongEntry<K, V> {
      volatile long writeTime = Long.MAX_VALUE;
      @GuardedBy("Segment.this")
      LocalCache.ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
      @GuardedBy("Segment.this")
      LocalCache.ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();

      StrongWriteEntry(K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) {
         super(key, hash, next);
      }

      @Override
      public long getWriteTime() {
         return this.writeTime;
      }

      @Override
      public void setWriteTime(long time) {
         this.writeTime = time;
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getNextInWriteQueue() {
         return this.nextWrite;
      }

      @Override
      public void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> next) {
         this.nextWrite = next;
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue() {
         return this.previousWrite;
      }

      @Override
      public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> previous) {
         this.previousWrite = previous;
      }
   }

   final class ValueIterator extends LocalCache<K, V>.HashIterator<V> {
      @Override
      public V next() {
         return this.nextEntry().getValue();
      }
   }

   interface ValueReference<K, V> {
      @Nullable
      V get();

      V waitForValue() throws ExecutionException;

      int getWeight();

      @Nullable
      LocalCache.ReferenceEntry<K, V> getEntry();

      LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> var1, @Nullable V var2, LocalCache.ReferenceEntry<K, V> var3);

      void notifyNewValue(@Nullable V var1);

      boolean isLoading();

      boolean isActive();
   }

   final class Values extends AbstractCollection<V> {
      private final ConcurrentMap<?, ?> map;

      Values(ConcurrentMap<?, ?> map) {
         this.map = map;
      }

      @Override
      public int size() {
         return this.map.size();
      }

      @Override
      public boolean isEmpty() {
         return this.map.isEmpty();
      }

      @Override
      public void clear() {
         this.map.clear();
      }

      @Override
      public Iterator<V> iterator() {
         return LocalCache.this.new ValueIterator();
      }

      @Override
      public boolean contains(Object o) {
         return this.map.containsValue(o);
      }
   }

   static final class WeakAccessEntry<K, V> extends LocalCache.WeakEntry<K, V> {
      volatile long accessTime = Long.MAX_VALUE;
      @GuardedBy("Segment.this")
      LocalCache.ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
      @GuardedBy("Segment.this")
      LocalCache.ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();

      WeakAccessEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) {
         super(queue, key, hash, next);
      }

      @Override
      public long getAccessTime() {
         return this.accessTime;
      }

      @Override
      public void setAccessTime(long time) {
         this.accessTime = time;
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getNextInAccessQueue() {
         return this.nextAccess;
      }

      @Override
      public void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> next) {
         this.nextAccess = next;
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue() {
         return this.previousAccess;
      }

      @Override
      public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> previous) {
         this.previousAccess = previous;
      }
   }

   static final class WeakAccessWriteEntry<K, V> extends LocalCache.WeakEntry<K, V> {
      volatile long accessTime = Long.MAX_VALUE;
      @GuardedBy("Segment.this")
      LocalCache.ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
      @GuardedBy("Segment.this")
      LocalCache.ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();
      volatile long writeTime = Long.MAX_VALUE;
      @GuardedBy("Segment.this")
      LocalCache.ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
      @GuardedBy("Segment.this")
      LocalCache.ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();

      WeakAccessWriteEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) {
         super(queue, key, hash, next);
      }

      @Override
      public long getAccessTime() {
         return this.accessTime;
      }

      @Override
      public void setAccessTime(long time) {
         this.accessTime = time;
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getNextInAccessQueue() {
         return this.nextAccess;
      }

      @Override
      public void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> next) {
         this.nextAccess = next;
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue() {
         return this.previousAccess;
      }

      @Override
      public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> previous) {
         this.previousAccess = previous;
      }

      @Override
      public long getWriteTime() {
         return this.writeTime;
      }

      @Override
      public void setWriteTime(long time) {
         this.writeTime = time;
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getNextInWriteQueue() {
         return this.nextWrite;
      }

      @Override
      public void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> next) {
         this.nextWrite = next;
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue() {
         return this.previousWrite;
      }

      @Override
      public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> previous) {
         this.previousWrite = previous;
      }
   }

   static class WeakEntry<K, V> extends WeakReference<K> implements LocalCache.ReferenceEntry<K, V> {
      final int hash;
      final LocalCache.ReferenceEntry<K, V> next;
      volatile LocalCache.ValueReference<K, V> valueReference = LocalCache.unset();

      WeakEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) {
         super(key, queue);
         this.hash = hash;
         this.next = next;
      }

      @Override
      public K getKey() {
         return this.get();
      }

      @Override
      public long getAccessTime() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setAccessTime(long time) {
         throw new UnsupportedOperationException();
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getNextInAccessQueue() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> next) {
         throw new UnsupportedOperationException();
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> previous) {
         throw new UnsupportedOperationException();
      }

      @Override
      public long getWriteTime() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setWriteTime(long time) {
         throw new UnsupportedOperationException();
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getNextInWriteQueue() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> next) {
         throw new UnsupportedOperationException();
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> previous) {
         throw new UnsupportedOperationException();
      }

      @Override
      public LocalCache.ValueReference<K, V> getValueReference() {
         return this.valueReference;
      }

      @Override
      public void setValueReference(LocalCache.ValueReference<K, V> valueReference) {
         this.valueReference = valueReference;
      }

      @Override
      public int getHash() {
         return this.hash;
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getNext() {
         return this.next;
      }
   }

   static class WeakValueReference<K, V> extends WeakReference<V> implements LocalCache.ValueReference<K, V> {
      final LocalCache.ReferenceEntry<K, V> entry;

      WeakValueReference(ReferenceQueue<V> queue, V referent, LocalCache.ReferenceEntry<K, V> entry) {
         super(referent, queue);
         this.entry = entry;
      }

      @Override
      public int getWeight() {
         return 1;
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getEntry() {
         return this.entry;
      }

      @Override
      public void notifyNewValue(V newValue) {
      }

      @Override
      public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, LocalCache.ReferenceEntry<K, V> entry) {
         return new LocalCache.WeakValueReference<>(queue, value, entry);
      }

      @Override
      public boolean isLoading() {
         return false;
      }

      @Override
      public boolean isActive() {
         return true;
      }

      @Override
      public V waitForValue() {
         return this.get();
      }
   }

   static final class WeakWriteEntry<K, V> extends LocalCache.WeakEntry<K, V> {
      volatile long writeTime = Long.MAX_VALUE;
      @GuardedBy("Segment.this")
      LocalCache.ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
      @GuardedBy("Segment.this")
      LocalCache.ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();

      WeakWriteEntry(ReferenceQueue<K> queue, K key, int hash, @Nullable LocalCache.ReferenceEntry<K, V> next) {
         super(queue, key, hash, next);
      }

      @Override
      public long getWriteTime() {
         return this.writeTime;
      }

      @Override
      public void setWriteTime(long time) {
         this.writeTime = time;
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getNextInWriteQueue() {
         return this.nextWrite;
      }

      @Override
      public void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> next) {
         this.nextWrite = next;
      }

      @Override
      public LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue() {
         return this.previousWrite;
      }

      @Override
      public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> previous) {
         this.previousWrite = previous;
      }
   }

   static final class WeightedSoftValueReference<K, V> extends LocalCache.SoftValueReference<K, V> {
      final int weight;

      WeightedSoftValueReference(ReferenceQueue<V> queue, V referent, LocalCache.ReferenceEntry<K, V> entry, int weight) {
         super(queue, referent, entry);
         this.weight = weight;
      }

      @Override
      public int getWeight() {
         return this.weight;
      }

      @Override
      public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, LocalCache.ReferenceEntry<K, V> entry) {
         return new LocalCache.WeightedSoftValueReference<>(queue, value, entry, this.weight);
      }
   }

   static final class WeightedStrongValueReference<K, V> extends LocalCache.StrongValueReference<K, V> {
      final int weight;

      WeightedStrongValueReference(V referent, int weight) {
         super(referent);
         this.weight = weight;
      }

      @Override
      public int getWeight() {
         return this.weight;
      }
   }

   static final class WeightedWeakValueReference<K, V> extends LocalCache.WeakValueReference<K, V> {
      final int weight;

      WeightedWeakValueReference(ReferenceQueue<V> queue, V referent, LocalCache.ReferenceEntry<K, V> entry, int weight) {
         super(queue, referent, entry);
         this.weight = weight;
      }

      @Override
      public int getWeight() {
         return this.weight;
      }

      @Override
      public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, LocalCache.ReferenceEntry<K, V> entry) {
         return new LocalCache.WeightedWeakValueReference<>(queue, value, entry, this.weight);
      }
   }

   static final class WriteQueue<K, V> extends AbstractQueue<LocalCache.ReferenceEntry<K, V>> {
      final LocalCache.ReferenceEntry<K, V> head = new LocalCache.AbstractReferenceEntry<K, V>() {
         LocalCache.ReferenceEntry<K, V> nextWrite = this;
         LocalCache.ReferenceEntry<K, V> previousWrite = this;

         @Override
         public long getWriteTime() {
            return Long.MAX_VALUE;
         }

         @Override
         public void setWriteTime(long time) {
         }

         @Override
         public LocalCache.ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
         }

         @Override
         public void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> next) {
            this.nextWrite = next;
         }

         @Override
         public LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
         }

         @Override
         public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> previous) {
            this.previousWrite = previous;
         }
      };

      public boolean offer(LocalCache.ReferenceEntry<K, V> entry) {
         LocalCache.connectWriteOrder(entry.getPreviousInWriteQueue(), entry.getNextInWriteQueue());
         LocalCache.connectWriteOrder(this.head.getPreviousInWriteQueue(), entry);
         LocalCache.connectWriteOrder(entry, this.head);
         return true;
      }

      public LocalCache.ReferenceEntry<K, V> peek() {
         LocalCache.ReferenceEntry<K, V> next = this.head.getNextInWriteQueue();
         return next == this.head ? null : next;
      }

      public LocalCache.ReferenceEntry<K, V> poll() {
         LocalCache.ReferenceEntry<K, V> next = this.head.getNextInWriteQueue();
         if (next == this.head) {
            return null;
         } else {
            this.remove(next);
            return next;
         }
      }

      @Override
      public boolean remove(Object o) {
         LocalCache.ReferenceEntry<K, V> e = (LocalCache.ReferenceEntry<K, V>)o;
         LocalCache.ReferenceEntry<K, V> previous = e.getPreviousInWriteQueue();
         LocalCache.ReferenceEntry<K, V> next = e.getNextInWriteQueue();
         LocalCache.connectWriteOrder(previous, next);
         LocalCache.nullifyWriteOrder(e);
         return next != LocalCache.NullEntry.INSTANCE;
      }

      @Override
      public boolean contains(Object o) {
         LocalCache.ReferenceEntry<K, V> e = (LocalCache.ReferenceEntry<K, V>)o;
         return e.getNextInWriteQueue() != LocalCache.NullEntry.INSTANCE;
      }

      @Override
      public boolean isEmpty() {
         return this.head.getNextInWriteQueue() == this.head;
      }

      @Override
      public int size() {
         int size = 0;

         for (LocalCache.ReferenceEntry<K, V> e = this.head.getNextInWriteQueue(); e != this.head; e = e.getNextInWriteQueue()) {
            size++;
         }

         return size;
      }

      @Override
      public void clear() {
         LocalCache.ReferenceEntry<K, V> e = this.head.getNextInWriteQueue();

         while (e != this.head) {
            LocalCache.ReferenceEntry<K, V> next = e.getNextInWriteQueue();
            LocalCache.nullifyWriteOrder(e);
            e = next;
         }

         this.head.setNextInWriteQueue(this.head);
         this.head.setPreviousInWriteQueue(this.head);
      }

      @Override
      public Iterator<LocalCache.ReferenceEntry<K, V>> iterator() {
         return new AbstractSequentialIterator<LocalCache.ReferenceEntry<K, V>>(this.peek()) {
            protected LocalCache.ReferenceEntry<K, V> computeNext(LocalCache.ReferenceEntry<K, V> previous) {
               LocalCache.ReferenceEntry<K, V> next = previous.getNextInWriteQueue();
               return next == WriteQueue.this.head ? null : next;
            }
         };
      }
   }

   final class WriteThroughEntry implements Entry<K, V> {
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
         throw new UnsupportedOperationException();
      }

      @Override
      public String toString() {
         return this.getKey() + "=" + this.getValue();
      }
   }
}
