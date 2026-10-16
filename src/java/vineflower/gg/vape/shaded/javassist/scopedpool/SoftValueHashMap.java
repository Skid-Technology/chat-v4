package gg.vape.shaded.javassist.scopedpool;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class SoftValueHashMap<K, V> implements Map<K, V> {
   private Map<K, SoftValueHashMap.SoftValueRef<K, V>> hash;
   private ReferenceQueue<V> queue = new ReferenceQueue<>();

   @Override
   public Set<Entry<K, V>> entrySet() {
      this.processQueue();
      Set<Entry<K, V>> ret = new HashSet<>();

      for (Entry<K, SoftValueHashMap.SoftValueRef<K, V>> e : this.hash.entrySet()) {
         ret.add(new SimpleImmutableEntry<>(e.getKey(), e.getValue().get()));
      }

      return ret;
   }

   private void processQueue() {
      Object ref;
      if (!this.hash.isEmpty()) {
         while ((ref = this.queue.poll()) != null) {
            if (ref instanceof SoftValueHashMap.SoftValueRef) {
               SoftValueHashMap.SoftValueRef que = (SoftValueHashMap.SoftValueRef)ref;
               if (ref == this.hash.get(que.key)) {
                  this.hash.remove(que.key);
               }
            }
         }
      }
   }

   public SoftValueHashMap(int initialCapacity, float loadFactor) {
      this.hash = new ConcurrentHashMap<>(initialCapacity, loadFactor);
   }

   public SoftValueHashMap(int initialCapacity) {
      this.hash = new ConcurrentHashMap<>(initialCapacity);
   }

   public SoftValueHashMap() {
      this.hash = new ConcurrentHashMap<>();
   }

   public SoftValueHashMap(Map<K, V> t) {
      this(Math.max(2 * t.size(), 11), 0.75F);
      this.putAll(t);
   }

   @Override
   public int size() {
      this.processQueue();
      return this.hash.size();
   }

   @Override
   public boolean isEmpty() {
      this.processQueue();
      return this.hash.isEmpty();
   }

   @Override
   public boolean containsKey(Object key) {
      this.processQueue();
      return this.hash.containsKey(key);
   }

   @Override
   public V get(Object key) {
      this.processQueue();
      return this.valueOrNull(this.hash.get(key));
   }

   @Override
   public V put(K key, V value) {
      this.processQueue();
      return this.valueOrNull(this.hash.put(key, SoftValueHashMap.SoftValueRef.create(key, value, this.queue)));
   }

   @Override
   public V remove(Object key) {
      this.processQueue();
      return this.valueOrNull(this.hash.remove(key));
   }

   @Override
   public void clear() {
      this.processQueue();
      this.hash.clear();
   }

   @Override
   public boolean containsValue(Object arg0) {
      this.processQueue();
      if (null == arg0) {
         return false;
      } else {
         for (SoftValueHashMap.SoftValueRef<K, V> e : this.hash.values()) {
            if (null != e && arg0.equals(e.get())) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public Set<K> keySet() {
      this.processQueue();
      return this.hash.keySet();
   }

   @Override
   public void putAll(Map<? extends K, ? extends V> arg0) {
      this.processQueue();

      for (K key : arg0.keySet()) {
         this.put(key, (V)arg0.get(key));
      }
   }

   @Override
   public Collection<V> values() {
      this.processQueue();
      List<V> ret = new ArrayList<>();

      for (SoftValueHashMap.SoftValueRef<K, V> e : this.hash.values()) {
         ret.add(e.get());
      }

      return ret;
   }

   private V valueOrNull(SoftValueHashMap.SoftValueRef<K, V> rtn) {
      return null == rtn ? null : rtn.get();
   }

   private static class SoftValueRef<K, V> extends SoftReference<V> {
      public K key;

      private SoftValueRef(K key, V val, ReferenceQueue<V> q) {
         super(val, q);
         this.key = key;
      }

      private static <K, V> SoftValueHashMap.SoftValueRef<K, V> create(K key, V val, ReferenceQueue<V> q) {
         return val == null ? null : new SoftValueHashMap.SoftValueRef<>(key, val, q);
      }
   }
}
