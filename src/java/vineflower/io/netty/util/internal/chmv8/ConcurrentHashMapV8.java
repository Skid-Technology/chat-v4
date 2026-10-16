package io.netty.util.internal.chmv8;

import io.netty.util.internal.IntegerHolder;
import io.netty.util.internal.InternalThreadLocalMap;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import sun.misc.Unsafe;

public class ConcurrentHashMapV8<K, V> implements ConcurrentMap<K, V>, Serializable {
   private static final long serialVersionUID = 7249069246763182397L;
   private static final int MAXIMUM_CAPACITY = 1073741824;
   private static final int DEFAULT_CAPACITY = 16;
   static final int MAX_ARRAY_SIZE = 2147483639;
   private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
   private static final float LOAD_FACTOR = 0.75F;
   static final int TREEIFY_THRESHOLD = 8;
   static final int UNTREEIFY_THRESHOLD = 6;
   static final int MIN_TREEIFY_CAPACITY = 64;
   private static final int MIN_TRANSFER_STRIDE = 16;
   static final int MOVED = -1;
   static final int TREEBIN = -2;
   static final int RESERVED = -3;
   static final int HASH_BITS = Integer.MAX_VALUE;
   static final int NCPU = Runtime.getRuntime().availableProcessors();
   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[]{
      new ObjectStreamField("segments", ConcurrentHashMapV8.Segment[].class),
      new ObjectStreamField("segmentMask", int.class),
      new ObjectStreamField("segmentShift", int.class)
   };
   transient volatile ConcurrentHashMapV8.Node<K, V>[] table;
   private transient volatile ConcurrentHashMapV8.Node<K, V>[] nextTable;
   private transient volatile long baseCount;
   private transient volatile int sizeCtl;
   private transient volatile int transferIndex;
   private transient volatile int transferOrigin;
   private transient volatile int cellsBusy;
   private transient volatile ConcurrentHashMapV8.CounterCell[] counterCells;
   private transient ConcurrentHashMapV8.KeySetView<K, V> keySet;
   private transient ConcurrentHashMapV8.ValuesView<K, V> values;
   private transient ConcurrentHashMapV8.EntrySetView<K, V> entrySet;
   static final AtomicInteger counterHashCodeGenerator = new AtomicInteger();
   static final int SEED_INCREMENT = 1640531527;
   private static final Unsafe U;
   private static final long SIZECTL;
   private static final long TRANSFERINDEX;
   private static final long TRANSFERORIGIN;
   private static final long BASECOUNT;
   private static final long CELLSBUSY;
   private static final long CELLVALUE;
   private static final long ABASE;
   private static final int ASHIFT;

   static final int spread(int h) {
      return (h ^ h >>> 16) & 2147483647;
   }

   private static final int tableSizeFor(int c) {
      int n = c - 1;
      n |= n >>> 1;
      n |= n >>> 2;
      n |= n >>> 4;
      n |= n >>> 8;
      n |= n >>> 16;
      return n < 0 ? 1 : (n >= 1073741824 ? 1073741824 : n + 1);
   }

   static Class<?> comparableClassFor(Object x) {
      if (x instanceof Comparable) {
         Class<?> c;
         if ((c = x.getClass()) == String.class) {
            return c;
         }

         Type[] ts;
         if ((ts = c.getGenericInterfaces()) != null) {
            for (int i = 0; i < ts.length; i++) {
               Type[] as;
               Type t;
               ParameterizedType p;
               if ((t = ts[i]) instanceof ParameterizedType
                  && (p = (ParameterizedType)t).getRawType() == Comparable.class
                  && (as = p.getActualTypeArguments()) != null
                  && as.length == 1
                  && as[0] == c) {
                  return c;
               }
            }
         }
      }

      return null;
   }

   static int compareComparables(Class<?> kc, Object k, Object x) {
      return x != null && x.getClass() == kc ? ((Comparable)k).compareTo(x) : 0;
   }

   static final <K, V> ConcurrentHashMapV8.Node<K, V> tabAt(ConcurrentHashMapV8.Node<K, V>[] tab, int i) {
      return (ConcurrentHashMapV8.Node<K, V>)U.getObjectVolatile(tab, ((long)i << ASHIFT) + ABASE);
   }

   static final <K, V> boolean casTabAt(ConcurrentHashMapV8.Node<K, V>[] tab, int i, ConcurrentHashMapV8.Node<K, V> c, ConcurrentHashMapV8.Node<K, V> v) {
      return U.compareAndSwapObject(tab, ((long)i << ASHIFT) + ABASE, c, v);
   }

   static final <K, V> void setTabAt(ConcurrentHashMapV8.Node<K, V>[] tab, int i, ConcurrentHashMapV8.Node<K, V> v) {
      U.putObjectVolatile(tab, ((long)i << ASHIFT) + ABASE, v);
   }

   public ConcurrentHashMapV8() {
   }

   public ConcurrentHashMapV8(int initialCapacity) {
      if (initialCapacity < 0) {
         throw new IllegalArgumentException();
      } else {
         int cap = initialCapacity >= 536870912 ? 1073741824 : tableSizeFor(initialCapacity + (initialCapacity >>> 1) + 1);
         this.sizeCtl = cap;
      }
   }

   public ConcurrentHashMapV8(Map<? extends K, ? extends V> m) {
      this.sizeCtl = 16;
      this.putAll(m);
   }

   public ConcurrentHashMapV8(int initialCapacity, float loadFactor) {
      this(initialCapacity, loadFactor, 1);
   }

   public ConcurrentHashMapV8(int initialCapacity, float loadFactor, int concurrencyLevel) {
      if (loadFactor > 0.0F && initialCapacity >= 0 && concurrencyLevel > 0) {
         if (initialCapacity < concurrencyLevel) {
            initialCapacity = concurrencyLevel;
         }

         long size = (long)(1.0 + (float)initialCapacity / loadFactor);
         int cap = size >= 1073741824L ? 1073741824 : tableSizeFor((int)size);
         this.sizeCtl = cap;
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public int size() {
      long n = this.sumCount();
      return n < 0L ? 0 : (n > 2147483647L ? Integer.MAX_VALUE : (int)n);
   }

   @Override
   public boolean isEmpty() {
      return this.sumCount() <= 0L;
   }

   @Override
   public V get(Object key) {
      int h = spread(key.hashCode());
      ConcurrentHashMapV8.Node<K, V>[] tab = this.table;
      ConcurrentHashMapV8.Node<K, V> e;
      int n;
      if (this.table != null && (n = tab.length) > 0 && (e = tabAt(tab, n - 1 & h)) != null) {
         int eh = e.hash;
         if (e.hash == h) {
            K ek = e.key;
            if (e.key == key || ek != null && key.equals(ek)) {
               return e.val;
            }
         } else if (eh < 0) {
            ConcurrentHashMapV8.Node<K, V> p;
            return (p = e.find(h, key)) != null ? p.val : null;
         }

         while ((e = e.next) != null) {
            if (e.hash == h) {
               K ek = e.key;
               if (e.key == key || ek != null && key.equals(ek)) {
                  return e.val;
               }
            }
         }
      }

      return null;
   }

   @Override
   public boolean containsKey(Object key) {
      return this.get(key) != null;
   }

   @Override
   public boolean containsValue(Object value) {
      if (value == null) {
         throw new NullPointerException();
      } else {
         ConcurrentHashMapV8.Node<K, V>[] t = this.table;
         if (this.table != null) {
            ConcurrentHashMapV8.Traverser<K, V> it = new ConcurrentHashMapV8.Traverser<>(t, t.length, 0, t.length);

            ConcurrentHashMapV8.Node<K, V> p;
            while ((p = it.advance()) != null) {
               V v = p.val;
               if (p.val == value || v != null && value.equals(v)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   @Override
   public V put(K key, V value) {
      return this.putVal(key, value, false);
   }

   final V putVal(K key, V value, boolean onlyIfAbsent) {
      if (key != null && value != null) {
         int hash = spread(key.hashCode());
         int binCount = 0;
         ConcurrentHashMapV8.Node<K, V>[] tab = this.table;

         while (true) {
            int n;
            while (tab == null || (n = tab.length) == 0) {
               tab = this.initTable();
            }

            ConcurrentHashMapV8.Node<K, V> f;
            int i;
            if ((f = tabAt(tab, i = n - 1 & hash)) == null) {
               if (casTabAt(tab, i, null, new ConcurrentHashMapV8.Node<>(hash, key, value, null))) {
                  break;
               }
            } else {
               int fh = f.hash;
               if (f.hash == -1) {
                  tab = this.helpTransfer(tab, f);
               } else {
                  V oldVal = null;
                  synchronized (f) {
                     if (tabAt(tab, i) == f) {
                        if (fh < 0) {
                           if (f instanceof ConcurrentHashMapV8.TreeBin) {
                              binCount = 2;
                              ConcurrentHashMapV8.Node<K, V> p;
                              if ((p = ((ConcurrentHashMapV8.TreeBin)f).putTreeVal(hash, key, value)) != null) {
                                 oldVal = p.val;
                                 if (!onlyIfAbsent) {
                                    p.val = value;
                                 }
                              }
                           }
                        } else {
                           binCount = 1;
                           ConcurrentHashMapV8.Node<K, V> e = f;

                           while (true) {
                              if (e.hash == hash) {
                                 K ek = e.key;
                                 if (e.key == key || ek != null && key.equals(ek)) {
                                    oldVal = e.val;
                                    if (!onlyIfAbsent) {
                                       e.val = value;
                                    }
                                    break;
                                 }
                              }

                              ConcurrentHashMapV8.Node<K, V> pred = e;
                              if ((e = e.next) == null) {
                                 pred.next = new ConcurrentHashMapV8.Node<>(hash, key, value, null);
                                 break;
                              }

                              binCount++;
                           }
                        }
                     }
                  }

                  if (binCount != 0) {
                     if (binCount >= 8) {
                        this.treeifyBin(tab, i);
                     }

                     if (oldVal != null) {
                        return oldVal;
                     }
                     break;
                  }
               }
            }
         }

         this.addCount(1L, binCount);
         return null;
      } else {
         throw new NullPointerException();
      }
   }

   @Override
   public void putAll(Map<? extends K, ? extends V> m) {
      this.tryPresize(m.size());

      for (Entry<? extends K, ? extends V> e : m.entrySet()) {
         this.putVal((K)e.getKey(), (V)e.getValue(), false);
      }
   }

   @Override
   public V remove(Object key) {
      return this.replaceNode(key, null, null);
   }

   final V replaceNode(Object key, V value, Object cv) {
      int hash = spread(key.hashCode());
      ConcurrentHashMapV8.Node<K, V>[] tab = this.table;

      ConcurrentHashMapV8.Node<K, V> f;
      int n;
      int i;
      while (tab != null && (n = tab.length) != 0 && (f = tabAt(tab, i = n - 1 & hash)) != null) {
         int fh = f.hash;
         if (f.hash == -1) {
            tab = this.helpTransfer(tab, f);
         } else {
            V oldVal = null;
            boolean validated = false;
            synchronized (f) {
               if (tabAt(tab, i) == f) {
                  if (fh < 0) {
                     if (f instanceof ConcurrentHashMapV8.TreeBin) {
                        validated = true;
                        ConcurrentHashMapV8.TreeBin<K, V> t = (ConcurrentHashMapV8.TreeBin<K, V>)f;
                        ConcurrentHashMapV8.TreeNode<K, V> r = t.root;
                        ConcurrentHashMapV8.TreeNode<K, V> p;
                        if (t.root != null && (p = r.findTreeNode(hash, key, null)) != null) {
                           V pv = p.val;
                           if (cv == null || cv == pv || pv != null && cv.equals(pv)) {
                              oldVal = pv;
                              if (value != null) {
                                 p.val = value;
                              } else if (t.removeTreeNode(p)) {
                                 setTabAt(tab, i, untreeify(t.first));
                              }
                           }
                        }
                     }
                  } else {
                     validated = true;
                     ConcurrentHashMapV8.Node<K, V> e = f;
                     ConcurrentHashMapV8.Node<K, V> pred = null;

                     do {
                        if (e.hash == hash) {
                           K ek = e.key;
                           if (e.key == key || ek != null && key.equals(ek)) {
                              V ev = e.val;
                              if (cv == null || cv == ev || ev != null && cv.equals(ev)) {
                                 oldVal = ev;
                                 if (value != null) {
                                    e.val = value;
                                 } else if (pred != null) {
                                    pred.next = e.next;
                                 } else {
                                    setTabAt(tab, i, e.next);
                                 }
                              }
                              break;
                           }
                        }

                        pred = e;
                     } while ((e = e.next) != null);
                  }
               }
            }

            if (validated) {
               if (oldVal != null) {
                  if (value == null) {
                     this.addCount(-1L, -1);
                  }

                  return oldVal;
               }
               break;
            }
         }
      }

      return null;
   }

   @Override
   public void clear() {
      long delta = 0L;
      int i = 0;
      ConcurrentHashMapV8.Node<K, V>[] tab = this.table;

      while (tab != null && i < tab.length) {
         ConcurrentHashMapV8.Node<K, V> f = tabAt(tab, i);
         if (f == null) {
            i++;
         } else {
            int fh = f.hash;
            if (f.hash == -1) {
               tab = this.helpTransfer(tab, f);
               i = 0;
            } else {
               synchronized (f) {
                  if (tabAt(tab, i) == f) {
                     for (ConcurrentHashMapV8.Node<K, V> p = (ConcurrentHashMapV8.Node<K, V>)(fh >= 0
                           ? f
                           : (f instanceof ConcurrentHashMapV8.TreeBin ? ((ConcurrentHashMapV8.TreeBin)f).first : null));
                        p != null;
                        p = p.next
                     ) {
                        delta--;
                     }

                     setTabAt(tab, i++, null);
                  }
               }
            }
         }
      }

      if (delta != 0L) {
         this.addCount(delta, -1);
      }
   }

   public ConcurrentHashMapV8.KeySetView<K, V> keySet() {
      ConcurrentHashMapV8.KeySetView<K, V> ks = this.keySet;
      return this.keySet != null ? ks : (this.keySet = new ConcurrentHashMapV8.KeySetView<>(this, null));
   }

   @Override
   public Collection<V> values() {
      ConcurrentHashMapV8.ValuesView<K, V> vs = this.values;
      return this.values != null ? vs : (this.values = new ConcurrentHashMapV8.ValuesView<>(this));
   }

   @Override
   public Set<Entry<K, V>> entrySet() {
      ConcurrentHashMapV8.EntrySetView<K, V> es = this.entrySet;
      return this.entrySet != null ? es : (this.entrySet = new ConcurrentHashMapV8.EntrySetView<>(this));
   }

   @Override
   public int hashCode() {
      int h = 0;
      ConcurrentHashMapV8.Node<K, V>[] t = this.table;
      if (this.table != null) {
         ConcurrentHashMapV8.Traverser<K, V> it = new ConcurrentHashMapV8.Traverser<>(t, t.length, 0, t.length);

         ConcurrentHashMapV8.Node<K, V> p;
         while ((p = it.advance()) != null) {
            h += p.key.hashCode() ^ p.val.hashCode();
         }
      }

      return h;
   }

   @Override
   public String toString() {
      ConcurrentHashMapV8.Node<K, V>[] t = this.table;
      int f = this.table == null ? 0 : t.length;
      ConcurrentHashMapV8.Traverser<K, V> it = new ConcurrentHashMapV8.Traverser<>(t, f, 0, f);
      StringBuilder sb = new StringBuilder();
      sb.append('{');
      ConcurrentHashMapV8.Node<K, V> p;
      if ((p = it.advance()) != null) {
         while (true) {
            K k = p.key;
            V v = p.val;
            sb.append(k == this ? "(this Map)" : k);
            sb.append('=');
            sb.append(v == this ? "(this Map)" : v);
            if ((p = it.advance()) == null) {
               break;
            }

            sb.append(',').append(' ');
         }
      }

      return sb.append('}').toString();
   }

   @Override
   public boolean equals(Object o) {
      if (o != this) {
         if (!(o instanceof Map)) {
            return false;
         }

         Map<?, ?> m = (Map<?, ?>)o;
         ConcurrentHashMapV8.Node<K, V>[] t = this.table;
         int f = this.table == null ? 0 : t.length;
         ConcurrentHashMapV8.Traverser<K, V> it = new ConcurrentHashMapV8.Traverser<>(t, f, 0, f);

         ConcurrentHashMapV8.Node<K, V> p;
         while ((p = it.advance()) != null) {
            V val = p.val;
            Object v = m.get(p.key);
            if (v == null || v != val && !v.equals(val)) {
               return false;
            }
         }

         for (Entry<?, ?> e : m.entrySet()) {
            Object mv;
            Object v;
            Object mk;
            if ((mk = e.getKey()) == null || (mv = e.getValue()) == null || (v = this.get(mk)) == null || mv != v && !mv.equals(v)) {
               return false;
            }
         }
      }

      return true;
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      int sshift = 0;

      int ssize;
      for (ssize = 1; ssize < 16; ssize <<= 1) {
         sshift++;
      }

      int segmentShift = 32 - sshift;
      int segmentMask = ssize - 1;
      ConcurrentHashMapV8.Segment<K, V>[] segments = new ConcurrentHashMapV8.Segment[16];

      for (int i = 0; i < segments.length; i++) {
         segments[i] = new ConcurrentHashMapV8.Segment<>(0.75F);
      }

      s.putFields().put("segments", segments);
      s.putFields().put("segmentShift", segmentShift);
      s.putFields().put("segmentMask", segmentMask);
      s.writeFields();
      ConcurrentHashMapV8.Node<K, V>[] t = this.table;
      if (this.table != null) {
         ConcurrentHashMapV8.Traverser<K, V> it = new ConcurrentHashMapV8.Traverser<>(t, t.length, 0, t.length);

         ConcurrentHashMapV8.Node<K, V> p;
         while ((p = it.advance()) != null) {
            s.writeObject(p.key);
            s.writeObject(p.val);
         }
      }

      s.writeObject(null);
      s.writeObject(null);
      segments = null;
   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      this.sizeCtl = -1;
      s.defaultReadObject();
      long size = 0L;
      ConcurrentHashMapV8.Node<K, V> p = null;

      while (true) {
         K k = (K)s.readObject();
         V v = (V)s.readObject();
         if (k == null || v == null) {
            if (size == 0L) {
               this.sizeCtl = 0;
            } else {
               int n;
               if (size >= 536870912L) {
                  n = 1073741824;
               } else {
                  int sz = (int)size;
                  n = tableSizeFor(sz + (sz >>> 1) + 1);
               }

               v = (V)((ConcurrentHashMapV8.Node[])(new ConcurrentHashMapV8.Node[n]));
               int mask = n - 1;
               long added = 0L;

               while (p != null) {
                  ConcurrentHashMapV8.Node<K, V> next = p.next;
                  int h = p.hash;
                  int j = h & mask;
                  boolean insertAtFront;
                  ConcurrentHashMapV8.Node<K, V> first;
                  if ((first = tabAt(v, j)) == null) {
                     insertAtFront = true;
                  } else {
                     K kx = p.key;
                     if (first.hash < 0) {
                        ConcurrentHashMapV8.TreeBin<K, V> t = (ConcurrentHashMapV8.TreeBin<K, V>)first;
                        if (t.putTreeVal(h, kx, p.val) == null) {
                           added++;
                        }

                        insertAtFront = false;
                     } else {
                        int binCount = 0;
                        insertAtFront = true;

                        for (ConcurrentHashMapV8.Node<K, V> q = first; q != null; q = q.next) {
                           if (q.hash == h) {
                              K qk = q.key;
                              if (q.key == kx || qk != null && kx.equals(qk)) {
                                 insertAtFront = false;
                                 break;
                              }
                           }

                           binCount++;
                        }

                        if (insertAtFront && binCount >= 8) {
                           insertAtFront = false;
                           added++;
                           p.next = first;
                           ConcurrentHashMapV8.TreeNode<K, V> hd = null;
                           ConcurrentHashMapV8.TreeNode<K, V> tl = null;

                           for (ConcurrentHashMapV8.Node<K, V> var26 = p; var26 != null; var26 = var26.next) {
                              ConcurrentHashMapV8.TreeNode<K, V> t = new ConcurrentHashMapV8.TreeNode<>(var26.hash, var26.key, var26.val, null, null);
                              if ((t.prev = tl) == null) {
                                 hd = t;
                              } else {
                                 tl.next = t;
                              }

                              tl = t;
                           }

                           setTabAt(v, j, new ConcurrentHashMapV8.TreeBin<>(hd));
                        }
                     }
                  }

                  if (insertAtFront) {
                     added++;
                     p.next = first;
                     setTabAt(v, j, p);
                  }

                  p = next;
               }

               this.table = v;
               this.sizeCtl = n - (n >>> 2);
               this.baseCount = added;
            }

            return;
         }

         p = new ConcurrentHashMapV8.Node<>(spread(k.hashCode()), k, v, p);
         size++;
      }
   }

   @Override
   public V putIfAbsent(K key, V value) {
      return this.putVal(key, value, true);
   }

   @Override
   public boolean remove(Object key, Object value) {
      if (key == null) {
         throw new NullPointerException();
      } else {
         return value != null && this.replaceNode(key, null, value) != null;
      }
   }

   @Override
   public boolean replace(K key, V oldValue, V newValue) {
      if (key != null && oldValue != null && newValue != null) {
         return this.replaceNode(key, newValue, oldValue) != null;
      } else {
         throw new NullPointerException();
      }
   }

   @Override
   public V replace(K key, V value) {
      if (key != null && value != null) {
         return this.replaceNode(key, value, null);
      } else {
         throw new NullPointerException();
      }
   }

   @Override
   public V getOrDefault(Object key, V defaultValue) {
      V v;
      return (v = this.get(key)) == null ? defaultValue : v;
   }

   public void forEach(ConcurrentHashMapV8.BiAction<? super K, ? super V> action) {
      if (action == null) {
         throw new NullPointerException();
      } else {
         ConcurrentHashMapV8.Node<K, V>[] t = this.table;
         if (this.table != null) {
            ConcurrentHashMapV8.Traverser<K, V> it = new ConcurrentHashMapV8.Traverser<>(t, t.length, 0, t.length);

            ConcurrentHashMapV8.Node<K, V> p;
            while ((p = it.advance()) != null) {
               action.apply(p.key, p.val);
            }
         }
      }
   }

   public void replaceAll(ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends V> function) {
      if (function == null) {
         throw new NullPointerException();
      } else {
         ConcurrentHashMapV8.Node<K, V>[] t = this.table;
         if (this.table != null) {
            ConcurrentHashMapV8.Traverser<K, V> it = new ConcurrentHashMapV8.Traverser<>(t, t.length, 0, t.length);

            ConcurrentHashMapV8.Node<K, V> p;
            while ((p = it.advance()) != null) {
               V oldValue = p.val;
               K key = p.key;

               V newValue;
               do {
                  newValue = (V)function.apply(key, oldValue);
                  if (newValue == null) {
                     throw new NullPointerException();
                  }
               } while (this.replaceNode(key, newValue, oldValue) == null && (oldValue = this.get(key)) != null);
            }
         }
      }
   }

   public V computeIfAbsent(K key, ConcurrentHashMapV8.Fun<? super K, ? extends V> mappingFunction) {
      if (key != null && mappingFunction != null) {
         int h = spread(key.hashCode());
         V val = null;
         int binCount = 0;
         ConcurrentHashMapV8.Node<K, V>[] tab = this.table;

         while (true) {
            int n;
            while (tab == null || (n = tab.length) == 0) {
               tab = this.initTable();
            }

            ConcurrentHashMapV8.Node<K, V> f;
            int i;
            if ((f = tabAt(tab, i = n - 1 & h)) == null) {
               ConcurrentHashMapV8.Node<K, V> r = new ConcurrentHashMapV8.ReservationNode<>();
               synchronized (r) {
                  if (casTabAt(tab, i, null, r)) {
                     binCount = 1;
                     ConcurrentHashMapV8.Node<K, V> node = null;

                     try {
                        if ((val = (V)mappingFunction.apply(key)) != null) {
                           node = new ConcurrentHashMapV8.Node<>(h, key, val, null);
                        }
                     } finally {
                        setTabAt(tab, i, node);
                     }
                  }
               }

               if (binCount != 0) {
                  break;
               }
            } else {
               int fh = f.hash;
               if (f.hash == -1) {
                  tab = this.helpTransfer(tab, f);
               } else {
                  boolean added = false;
                  synchronized (f) {
                     if (tabAt(tab, i) == f) {
                        if (fh < 0) {
                           if (f instanceof ConcurrentHashMapV8.TreeBin) {
                              binCount = 2;
                              ConcurrentHashMapV8.TreeBin<K, V> t = (ConcurrentHashMapV8.TreeBin<K, V>)f;
                              ConcurrentHashMapV8.TreeNode<K, V> r = t.root;
                              ConcurrentHashMapV8.TreeNode<K, V> p;
                              if (t.root != null && (p = r.findTreeNode(h, key, null)) != null) {
                                 val = p.val;
                              } else if ((val = (V)mappingFunction.apply(key)) != null) {
                                 added = true;
                                 t.putTreeVal(h, key, val);
                              }
                           }
                        } else {
                           binCount = 1;
                           ConcurrentHashMapV8.Node<K, V> e = f;

                           while (true) {
                              if (e.hash == h) {
                                 K ek = e.key;
                                 if (e.key == key || ek != null && key.equals(ek)) {
                                    val = e.val;
                                    break;
                                 }
                              }

                              ConcurrentHashMapV8.Node<K, V> pred = e;
                              if ((e = e.next) == null) {
                                 if ((val = (V)mappingFunction.apply(key)) != null) {
                                    added = true;
                                    pred.next = new ConcurrentHashMapV8.Node<>(h, key, val, null);
                                 }
                                 break;
                              }

                              binCount++;
                           }
                        }
                     }
                  }

                  if (binCount != 0) {
                     if (binCount >= 8) {
                        this.treeifyBin(tab, i);
                     }

                     if (!added) {
                        return val;
                     }
                     break;
                  }
               }
            }
         }

         if (val != null) {
            this.addCount(1L, binCount);
         }

         return val;
      } else {
         throw new NullPointerException();
      }
   }

   public V computeIfPresent(K key, ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends V> remappingFunction) {
      if (key != null && remappingFunction != null) {
         int h = spread(key.hashCode());
         V val = null;
         int delta = 0;
         int binCount = 0;
         ConcurrentHashMapV8.Node<K, V>[] tab = this.table;

         while (true) {
            int n;
            while (tab == null || (n = tab.length) == 0) {
               tab = this.initTable();
            }

            ConcurrentHashMapV8.Node<K, V> f;
            int i;
            if ((f = tabAt(tab, i = n - 1 & h)) == null) {
               break;
            }

            int fh = f.hash;
            if (f.hash == -1) {
               tab = this.helpTransfer(tab, f);
            } else {
               synchronized (f) {
                  if (tabAt(tab, i) == f) {
                     if (fh < 0) {
                        if (f instanceof ConcurrentHashMapV8.TreeBin) {
                           binCount = 2;
                           ConcurrentHashMapV8.TreeBin<K, V> t = (ConcurrentHashMapV8.TreeBin<K, V>)f;
                           ConcurrentHashMapV8.TreeNode<K, V> r = t.root;
                           ConcurrentHashMapV8.TreeNode<K, V> p;
                           if (t.root != null && (p = r.findTreeNode(h, key, null)) != null) {
                              val = (V)remappingFunction.apply(key, p.val);
                              if (val != null) {
                                 p.val = val;
                              } else {
                                 delta = -1;
                                 if (t.removeTreeNode(p)) {
                                    setTabAt(tab, i, untreeify(t.first));
                                 }
                              }
                           }
                        }
                     } else {
                        binCount = 1;
                        ConcurrentHashMapV8.Node<K, V> e = f;
                        ConcurrentHashMapV8.Node<K, V> pred = null;

                        while (true) {
                           if (e.hash == h) {
                              K ek = e.key;
                              if (e.key == key || ek != null && key.equals(ek)) {
                                 val = (V)remappingFunction.apply(key, e.val);
                                 if (val != null) {
                                    e.val = val;
                                 } else {
                                    delta = -1;
                                    ConcurrentHashMapV8.Node<K, V> en = e.next;
                                    if (pred != null) {
                                       pred.next = en;
                                    } else {
                                       setTabAt(tab, i, en);
                                    }
                                 }
                                 break;
                              }
                           }

                           pred = e;
                           if ((e = e.next) == null) {
                              break;
                           }

                           binCount++;
                        }
                     }
                  }
               }

               if (binCount != 0) {
                  break;
               }
            }
         }

         if (delta != 0) {
            this.addCount(delta, binCount);
         }

         return val;
      } else {
         throw new NullPointerException();
      }
   }

   public V compute(K key, ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends V> remappingFunction) {
      if (key != null && remappingFunction != null) {
         int h = spread(key.hashCode());
         V val = null;
         int delta = 0;
         int binCount = 0;
         ConcurrentHashMapV8.Node<K, V>[] tab = this.table;

         while (true) {
            int n;
            while (tab == null || (n = tab.length) == 0) {
               tab = this.initTable();
            }

            ConcurrentHashMapV8.Node<K, V> f;
            int i;
            if ((f = tabAt(tab, i = n - 1 & h)) == null) {
               ConcurrentHashMapV8.Node<K, V> r = new ConcurrentHashMapV8.ReservationNode<>();
               synchronized (r) {
                  if (casTabAt(tab, i, null, r)) {
                     binCount = 1;
                     ConcurrentHashMapV8.Node<K, V> node = null;

                     try {
                        if ((val = (V)remappingFunction.apply(key, null)) != null) {
                           delta = 1;
                           node = new ConcurrentHashMapV8.Node<>(h, key, val, null);
                        }
                     } finally {
                        setTabAt(tab, i, node);
                     }
                  }
               }

               if (binCount != 0) {
                  break;
               }
            } else {
               int fh = f.hash;
               if (f.hash == -1) {
                  tab = this.helpTransfer(tab, f);
               } else {
                  synchronized (f) {
                     if (tabAt(tab, i) == f) {
                        if (fh < 0) {
                           if (f instanceof ConcurrentHashMapV8.TreeBin) {
                              binCount = 1;
                              ConcurrentHashMapV8.TreeBin<K, V> t = (ConcurrentHashMapV8.TreeBin<K, V>)f;
                              ConcurrentHashMapV8.TreeNode<K, V> r = t.root;
                              ConcurrentHashMapV8.TreeNode<K, V> p;
                              if (t.root != null) {
                                 p = r.findTreeNode(h, key, null);
                              } else {
                                 p = null;
                              }

                              V pv = p == null ? null : p.val;
                              val = (V)remappingFunction.apply(key, pv);
                              if (val != null) {
                                 if (p != null) {
                                    p.val = val;
                                 } else {
                                    delta = 1;
                                    t.putTreeVal(h, key, val);
                                 }
                              } else if (p != null) {
                                 delta = -1;
                                 if (t.removeTreeNode(p)) {
                                    setTabAt(tab, i, untreeify(t.first));
                                 }
                              }
                           }
                        } else {
                           binCount = 1;
                           ConcurrentHashMapV8.Node<K, V> e = f;
                           ConcurrentHashMapV8.Node<K, V> pred = null;

                           while (true) {
                              if (e.hash == h) {
                                 K ek = e.key;
                                 if (e.key == key || ek != null && key.equals(ek)) {
                                    val = (V)remappingFunction.apply(key, e.val);
                                    if (val != null) {
                                       e.val = val;
                                    } else {
                                       delta = -1;
                                       ConcurrentHashMapV8.Node<K, V> en = e.next;
                                       if (pred != null) {
                                          pred.next = en;
                                       } else {
                                          setTabAt(tab, i, en);
                                       }
                                    }
                                    break;
                                 }
                              }

                              pred = e;
                              if ((e = e.next) == null) {
                                 val = (V)remappingFunction.apply(key, null);
                                 if (val != null) {
                                    delta = 1;
                                    pred.next = new ConcurrentHashMapV8.Node<>(h, key, val, null);
                                 }
                                 break;
                              }

                              binCount++;
                           }
                        }
                     }
                  }

                  if (binCount != 0) {
                     if (binCount >= 8) {
                        this.treeifyBin(tab, i);
                     }
                     break;
                  }
               }
            }
         }

         if (delta != 0) {
            this.addCount(delta, binCount);
         }

         return val;
      } else {
         throw new NullPointerException();
      }
   }

   public V merge(K key, V value, ConcurrentHashMapV8.BiFun<? super V, ? super V, ? extends V> remappingFunction) {
      if (key != null && value != null && remappingFunction != null) {
         int h = spread(key.hashCode());
         V val = null;
         int delta = 0;
         int binCount = 0;
         ConcurrentHashMapV8.Node<K, V>[] tab = this.table;

         while (true) {
            int n;
            while (tab == null || (n = tab.length) == 0) {
               tab = this.initTable();
            }

            ConcurrentHashMapV8.Node<K, V> f;
            int i;
            if ((f = tabAt(tab, i = n - 1 & h)) == null) {
               if (casTabAt(tab, i, null, new ConcurrentHashMapV8.Node<>(h, key, value, null))) {
                  delta = 1;
                  val = value;
                  break;
               }
            } else {
               int fh = f.hash;
               if (f.hash == -1) {
                  tab = this.helpTransfer(tab, f);
               } else {
                  synchronized (f) {
                     if (tabAt(tab, i) == f) {
                        if (fh < 0) {
                           if (f instanceof ConcurrentHashMapV8.TreeBin) {
                              binCount = 2;
                              ConcurrentHashMapV8.TreeBin<K, V> t = (ConcurrentHashMapV8.TreeBin<K, V>)f;
                              ConcurrentHashMapV8.TreeNode<K, V> r = t.root;
                              ConcurrentHashMapV8.TreeNode<K, V> p = r == null ? null : r.findTreeNode(h, key, null);
                              val = p == null ? value : remappingFunction.apply(p.val, value);
                              if (val != null) {
                                 if (p != null) {
                                    p.val = val;
                                 } else {
                                    delta = 1;
                                    t.putTreeVal(h, key, val);
                                 }
                              } else if (p != null) {
                                 delta = -1;
                                 if (t.removeTreeNode(p)) {
                                    setTabAt(tab, i, untreeify(t.first));
                                 }
                              }
                           }
                        } else {
                           binCount = 1;
                           ConcurrentHashMapV8.Node<K, V> e = f;
                           ConcurrentHashMapV8.Node<K, V> pred = null;

                           while (true) {
                              if (e.hash == h) {
                                 K ek = e.key;
                                 if (e.key == key || ek != null && key.equals(ek)) {
                                    val = (V)remappingFunction.apply(e.val, value);
                                    if (val != null) {
                                       e.val = val;
                                    } else {
                                       delta = -1;
                                       ConcurrentHashMapV8.Node<K, V> en = e.next;
                                       if (pred != null) {
                                          pred.next = en;
                                       } else {
                                          setTabAt(tab, i, en);
                                       }
                                    }
                                    break;
                                 }
                              }

                              pred = e;
                              if ((e = e.next) == null) {
                                 delta = 1;
                                 val = value;
                                 pred.next = new ConcurrentHashMapV8.Node<>(h, key, value, null);
                                 break;
                              }

                              binCount++;
                           }
                        }
                     }
                  }

                  if (binCount != 0) {
                     if (binCount >= 8) {
                        this.treeifyBin(tab, i);
                     }
                     break;
                  }
               }
            }
         }

         if (delta != 0) {
            this.addCount(delta, binCount);
         }

         return val;
      } else {
         throw new NullPointerException();
      }
   }

   @Deprecated
   public boolean contains(Object value) {
      return this.containsValue(value);
   }

   public Enumeration<K> keys() {
      ConcurrentHashMapV8.Node<K, V>[] t = this.table;
      int f = this.table == null ? 0 : t.length;
      return new ConcurrentHashMapV8.KeyIterator<>(t, f, 0, f, this);
   }

   public Enumeration<V> elements() {
      ConcurrentHashMapV8.Node<K, V>[] t = this.table;
      int f = this.table == null ? 0 : t.length;
      return new ConcurrentHashMapV8.ValueIterator<>(t, f, 0, f, this);
   }

   public long mappingCount() {
      long n = this.sumCount();
      return n < 0L ? 0L : n;
   }

   public static <K> ConcurrentHashMapV8.KeySetView<K, Boolean> newKeySet() {
      return new ConcurrentHashMapV8.KeySetView<>(new ConcurrentHashMapV8<>(), Boolean.TRUE);
   }

   public static <K> ConcurrentHashMapV8.KeySetView<K, Boolean> newKeySet(int initialCapacity) {
      return new ConcurrentHashMapV8.KeySetView<>(new ConcurrentHashMapV8<>(initialCapacity), Boolean.TRUE);
   }

   public ConcurrentHashMapV8.KeySetView<K, V> keySet(V mappedValue) {
      if (mappedValue == null) {
         throw new NullPointerException();
      } else {
         return new ConcurrentHashMapV8.KeySetView<>(this, mappedValue);
      }
   }

   private final ConcurrentHashMapV8.Node<K, V>[] initTable() {
      ConcurrentHashMapV8.Node<K, V>[] tab;
      while (true) {
         tab = this.table;
         if (this.table != null && tab.length != 0) {
            break;
         }

         int sc = this.sizeCtl;
         if (this.sizeCtl < 0) {
            Thread.yield();
         } else if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {
            try {
               tab = this.table;
               if (this.table == null || tab.length == 0) {
                  int n = sc > 0 ? sc : 16;
                  ConcurrentHashMapV8.Node<K, V>[] nt = new ConcurrentHashMapV8.Node[n];
                  tab = nt;
                  this.table = nt;
                  sc = n - (n >>> 2);
               }
               break;
            } finally {
               this.sizeCtl = sc;
            }
         }
      }

      return tab;
   }

   private final void addCount(long x, int check) {
      IntegerHolder hc;
      boolean uncontended;
      InternalThreadLocalMap threadLocals;
      label74: {
         long s;
         label71: {
            ConcurrentHashMapV8.CounterCell[] as = this.counterCells;
            if (this.counterCells == null) {
               long b = this.baseCount;
               if (U.compareAndSwapLong(this, BASECOUNT, this.baseCount, s = b + x)) {
                  break label71;
               }
            }

            uncontended = true;
            threadLocals = InternalThreadLocalMap.get();
            ConcurrentHashMapV8.CounterCell a;
            int m;
            if ((hc = threadLocals.counterHashCode()) == null || as == null || (m = as.length - 1) < 0 || (a = as[m & hc.value]) == null) {
               break label74;
            }

            long v = a.value;
            if (!(uncontended = U.compareAndSwapLong(a, CELLVALUE, a.value, v + x))) {
               break label74;
            }

            if (check <= 1) {
               return;
            }

            s = this.sumCount();
         }

         if (check >= 0) {
            while (true) {
               int sc = this.sizeCtl;
               if (s < this.sizeCtl) {
                  break;
               }

               ConcurrentHashMapV8.Node<K, V>[] tab = this.table;
               if (this.table == null || tab.length >= 1073741824) {
                  break;
               }

               if (sc < 0) {
                  if (sc == -1 || this.transferIndex <= this.transferOrigin) {
                     break;
                  }

                  ConcurrentHashMapV8.Node<K, V>[] nt = this.nextTable;
                  if (this.nextTable == null) {
                     break;
                  }

                  if (U.compareAndSwapInt(this, SIZECTL, sc, sc - 1)) {
                     this.transfer(tab, nt);
                  }
               } else if (U.compareAndSwapInt(this, SIZECTL, sc, -2)) {
                  this.transfer(tab, null);
               }

               s = this.sumCount();
            }
         }

         return;
      }

      this.fullAddCount(threadLocals, x, hc, uncontended);
   }

   final ConcurrentHashMapV8.Node<K, V>[] helpTransfer(ConcurrentHashMapV8.Node<K, V>[] tab, ConcurrentHashMapV8.Node<K, V> f) {
      if (f instanceof ConcurrentHashMapV8.ForwardingNode) {
         ConcurrentHashMapV8.Node<K, V>[] nextTab = ((ConcurrentHashMapV8.ForwardingNode)f).nextTable;
         if (((ConcurrentHashMapV8.ForwardingNode)f).nextTable != null) {
            if (nextTab == this.nextTable && tab == this.table && this.transferIndex > this.transferOrigin) {
               int sc = this.sizeCtl;
               if (this.sizeCtl < -1 && U.compareAndSwapInt(this, SIZECTL, sc, sc - 1)) {
                  this.transfer(tab, nextTab);
               }
            }

            return nextTab;
         }
      }

      return this.table;
   }

   private final void tryPresize(int size) {
      int c = size >= 536870912 ? 1073741824 : tableSizeFor(size + (size >>> 1) + 1);

      while (true) {
         int sc = this.sizeCtl;
         if (this.sizeCtl < 0) {
            break;
         }

         ConcurrentHashMapV8.Node<K, V>[] tab = this.table;
         int n;
         if (tab != null && (n = tab.length) != 0) {
            if (c <= sc || n >= 1073741824) {
               break;
            }

            if (tab == this.table && U.compareAndSwapInt(this, SIZECTL, sc, -2)) {
               this.transfer(tab, null);
            }
         } else {
            n = sc > c ? sc : c;
            if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {
               try {
                  if (this.table == tab) {
                     ConcurrentHashMapV8.Node<K, V>[] nt = new ConcurrentHashMapV8.Node[n];
                     this.table = nt;
                     sc = n - (n >>> 2);
                  }
               } finally {
                  this.sizeCtl = sc;
               }
            }
         }
      }
   }

   private final void transfer(ConcurrentHashMapV8.Node<K, V>[] tab, ConcurrentHashMapV8.Node<K, V>[] nextTab) {
      int n = tab.length;
      int stride;
      if ((stride = NCPU > 1 ? (n >>> 3) / NCPU : n) < 16) {
         stride = 16;
      }

      if (nextTab == null) {
         try {
            ConcurrentHashMapV8.Node<K, V>[] nt = new ConcurrentHashMapV8.Node[n << 1];
            nextTab = nt;
         } catch (Throwable var29) {
            this.sizeCtl = Integer.MAX_VALUE;
            return;
         }

         this.nextTable = nextTab;
         this.transferOrigin = n;
         this.transferIndex = n;
         ConcurrentHashMapV8.ForwardingNode<K, V> rev = new ConcurrentHashMapV8.ForwardingNode<>(tab);
         int k = n;

         while (k > 0) {
            int nextk = k > stride ? k - stride : 0;

            for (int m = nextk; m < k; m++) {
               nextTab[m] = rev;
            }

            for (int m = n + nextk; m < n + k; m++) {
               nextTab[m] = rev;
            }

            k = nextk;
            U.putOrderedInt(this, TRANSFERORIGIN, nextk);
         }
      }

      int nextn = nextTab.length;
      ConcurrentHashMapV8.ForwardingNode<K, V> fwd = new ConcurrentHashMapV8.ForwardingNode<>(nextTab);
      boolean advance = true;
      boolean finishing = false;
      int i = 0;
      int bound = 0;

      while (true) {
         while (!advance) {
            if (i >= 0 && i < n && i + n < nextn) {
               ConcurrentHashMapV8.Node<K, V> f;
               if ((f = tabAt(tab, i)) != null) {
                  int fh = f.hash;
                  if (f.hash == -1) {
                     advance = true;
                  } else {
                     synchronized (f) {
                        if (tabAt(tab, i) == f) {
                           if (fh >= 0) {
                              int runBit = fh & n;
                              ConcurrentHashMapV8.Node<K, V> lastRun = f;

                              for (ConcurrentHashMapV8.Node<K, V> p = f.next; p != null; p = p.next) {
                                 int b = p.hash & n;
                                 if (b != runBit) {
                                    runBit = b;
                                    lastRun = p;
                                 }
                              }

                              ConcurrentHashMapV8.Node<K, V> ln;
                              ConcurrentHashMapV8.Node<K, V> hn;
                              if (runBit == 0) {
                                 ln = lastRun;
                                 hn = null;
                              } else {
                                 hn = lastRun;
                                 ln = null;
                              }

                              for (ConcurrentHashMapV8.Node<K, V> px = f; px != lastRun; px = px.next) {
                                 int ph = px.hash;
                                 K pk = px.key;
                                 V pv = px.val;
                                 if ((ph & n) == 0) {
                                    ln = new ConcurrentHashMapV8.Node<>(ph, pk, pv, ln);
                                 } else {
                                    hn = new ConcurrentHashMapV8.Node<>(ph, pk, pv, hn);
                                 }
                              }

                              setTabAt(nextTab, i, ln);
                              setTabAt(nextTab, i + n, hn);
                              setTabAt(tab, i, fwd);
                              advance = true;
                           } else if (f instanceof ConcurrentHashMapV8.TreeBin) {
                              ConcurrentHashMapV8.TreeBin<K, V> t = (ConcurrentHashMapV8.TreeBin<K, V>)f;
                              ConcurrentHashMapV8.TreeNode<K, V> lo = null;
                              ConcurrentHashMapV8.TreeNode<K, V> loTail = null;
                              ConcurrentHashMapV8.TreeNode<K, V> hi = null;
                              ConcurrentHashMapV8.TreeNode<K, V> hiTail = null;
                              int lc = 0;
                              int hc = 0;

                              for (ConcurrentHashMapV8.Node<K, V> e = t.first; e != null; e = e.next) {
                                 int h = e.hash;
                                 ConcurrentHashMapV8.TreeNode<K, V> pxx = new ConcurrentHashMapV8.TreeNode<>(h, e.key, e.val, null, null);
                                 if ((h & n) == 0) {
                                    if ((pxx.prev = loTail) == null) {
                                       lo = pxx;
                                    } else {
                                       loTail.next = pxx;
                                    }

                                    loTail = pxx;
                                    lc++;
                                 } else {
                                    if ((pxx.prev = hiTail) == null) {
                                       hi = pxx;
                                    } else {
                                       hiTail.next = pxx;
                                    }

                                    hiTail = pxx;
                                    hc++;
                                 }
                              }

                              ConcurrentHashMapV8.Node<K, V> ln = (ConcurrentHashMapV8.Node<K, V>)(lc <= 6
                                 ? untreeify(lo)
                                 : (hc != 0 ? new ConcurrentHashMapV8.TreeBin<>(lo) : t));
                              ConcurrentHashMapV8.Node<K, V> hn = (ConcurrentHashMapV8.Node<K, V>)(hc <= 6
                                 ? untreeify(hi)
                                 : (lc != 0 ? new ConcurrentHashMapV8.TreeBin<>(hi) : t));
                              setTabAt(nextTab, i, ln);
                              setTabAt(nextTab, i + n, hn);
                              setTabAt(tab, i, fwd);
                              advance = true;
                           }
                        }
                     }
                  }
               } else if (casTabAt(tab, i, null, fwd)) {
                  setTabAt(nextTab, i, null);
                  setTabAt(nextTab, i + n, null);
                  advance = true;
               }
            } else {
               if (finishing) {
                  this.nextTable = null;
                  this.table = nextTab;
                  this.sizeCtl = (n << 1) - (n >>> 1);
                  return;
               }

               int sc;
               do {
                  sc = this.sizeCtl;
               } while (!U.compareAndSwapInt(this, SIZECTL, this.sizeCtl, ++sc));

               if (sc != -1) {
                  return;
               }

               advance = true;
               finishing = true;
               i = n;
            }
         }

         if (--i < bound && !finishing) {
            int nextIndex = this.transferIndex;
            if (this.transferIndex <= this.transferOrigin) {
               i = -1;
               advance = false;
            } else {
               int nextBound;
               if (U.compareAndSwapInt(this, TRANSFERINDEX, nextIndex, nextBound = nextIndex > stride ? nextIndex - stride : 0)) {
                  bound = nextBound;
                  i = nextIndex - 1;
                  advance = false;
               }
            }
         } else {
            advance = false;
         }
      }
   }

   private final void treeifyBin(ConcurrentHashMapV8.Node<K, V>[] tab, int index) {
      if (tab != null) {
         int n;
         if ((n = tab.length) < 64) {
            if (tab == this.table) {
               int sc = this.sizeCtl;
               if (this.sizeCtl >= 0 && U.compareAndSwapInt(this, SIZECTL, sc, -2)) {
                  this.transfer(tab, null);
               }
            }
         } else {
            ConcurrentHashMapV8.Node<K, V> b;
            if ((b = tabAt(tab, index)) != null && b.hash >= 0) {
               synchronized (b) {
                  if (tabAt(tab, index) == b) {
                     ConcurrentHashMapV8.TreeNode<K, V> hd = null;
                     ConcurrentHashMapV8.TreeNode<K, V> tl = null;

                     for (ConcurrentHashMapV8.Node<K, V> e = b; e != null; e = e.next) {
                        ConcurrentHashMapV8.TreeNode<K, V> p = new ConcurrentHashMapV8.TreeNode<>(e.hash, e.key, e.val, null, null);
                        if ((p.prev = tl) == null) {
                           hd = p;
                        } else {
                           tl.next = p;
                        }

                        tl = p;
                     }

                     setTabAt(tab, index, new ConcurrentHashMapV8.TreeBin<>(hd));
                  }
               }
            }
         }
      }
   }

   static <K, V> ConcurrentHashMapV8.Node<K, V> untreeify(ConcurrentHashMapV8.Node<K, V> b) {
      ConcurrentHashMapV8.Node<K, V> hd = null;
      ConcurrentHashMapV8.Node<K, V> tl = null;

      for (ConcurrentHashMapV8.Node<K, V> q = b; q != null; q = q.next) {
         ConcurrentHashMapV8.Node<K, V> p = new ConcurrentHashMapV8.Node<>(q.hash, q.key, q.val, null);
         if (tl == null) {
            hd = p;
         } else {
            tl.next = p;
         }

         tl = p;
      }

      return hd;
   }

   final int batchFor(long b) {
      long n;
      if (b != Long.MAX_VALUE && (n = this.sumCount()) > 1L && n >= b) {
         int sp = ForkJoinPool.getCommonPoolParallelism() << 2;
         long var6;
         return b > 0L && (var6 = n / b) < sp ? (int)var6 : sp;
      } else {
         return 0;
      }
   }

   public void forEach(long parallelismThreshold, ConcurrentHashMapV8.BiAction<? super K, ? super V> action) {
      if (action == null) {
         throw new NullPointerException();
      } else {
         new ConcurrentHashMapV8.ForEachMappingTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, action).invoke();
      }
   }

   public <U> void forEach(
      long parallelismThreshold, ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> transformer, ConcurrentHashMapV8.Action<? super U> action
   ) {
      if (transformer != null && action != null) {
         new ConcurrentHashMapV8.ForEachTransformedMappingTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, transformer, action).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public <U> U search(long parallelismThreshold, ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> searchFunction) {
      if (searchFunction == null) {
         throw new NullPointerException();
      } else {
         return new ConcurrentHashMapV8.SearchMappingsTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, searchFunction, new AtomicReference())
            .invoke();
      }
   }

   public <U> U reduce(
      long parallelismThreshold,
      ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> transformer,
      ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer
   ) {
      if (transformer != null && reducer != null) {
         return new ConcurrentHashMapV8.MapReduceMappingsTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, reducer)
            .invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public double reduceToDouble(
      long parallelismThreshold,
      ConcurrentHashMapV8.ObjectByObjectToDouble<? super K, ? super V> transformer,
      double basis,
      ConcurrentHashMapV8.DoubleByDoubleToDouble reducer
   ) {
      if (transformer != null && reducer != null) {
         return new ConcurrentHashMapV8.MapReduceMappingsToDoubleTask<>(
               null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer
            )
            .invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public long reduceToLong(
      long parallelismThreshold,
      ConcurrentHashMapV8.ObjectByObjectToLong<? super K, ? super V> transformer,
      long basis,
      ConcurrentHashMapV8.LongByLongToLong reducer
   ) {
      if (transformer != null && reducer != null) {
         return new ConcurrentHashMapV8.MapReduceMappingsToLongTask<>(
               null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer
            )
            .invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public int reduceToInt(
      long parallelismThreshold,
      ConcurrentHashMapV8.ObjectByObjectToInt<? super K, ? super V> transformer,
      int basis,
      ConcurrentHashMapV8.IntByIntToInt reducer
   ) {
      if (transformer != null && reducer != null) {
         return new ConcurrentHashMapV8.MapReduceMappingsToIntTask<>(
               null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer
            )
            .invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public void forEachKey(long parallelismThreshold, ConcurrentHashMapV8.Action<? super K> action) {
      if (action == null) {
         throw new NullPointerException();
      } else {
         new ConcurrentHashMapV8.ForEachKeyTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, action).invoke();
      }
   }

   public <U> void forEachKey(
      long parallelismThreshold, ConcurrentHashMapV8.Fun<? super K, ? extends U> transformer, ConcurrentHashMapV8.Action<? super U> action
   ) {
      if (transformer != null && action != null) {
         new ConcurrentHashMapV8.ForEachTransformedKeyTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, transformer, action).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public <U> U searchKeys(long parallelismThreshold, ConcurrentHashMapV8.Fun<? super K, ? extends U> searchFunction) {
      if (searchFunction == null) {
         throw new NullPointerException();
      } else {
         return new ConcurrentHashMapV8.SearchKeysTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, searchFunction, new AtomicReference())
            .invoke();
      }
   }

   public K reduceKeys(long parallelismThreshold, ConcurrentHashMapV8.BiFun<? super K, ? super K, ? extends K> reducer) {
      if (reducer == null) {
         throw new NullPointerException();
      } else {
         return new ConcurrentHashMapV8.ReduceKeysTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, reducer).invoke();
      }
   }

   public <U> U reduceKeys(
      long parallelismThreshold,
      ConcurrentHashMapV8.Fun<? super K, ? extends U> transformer,
      ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer
   ) {
      if (transformer != null && reducer != null) {
         return new ConcurrentHashMapV8.MapReduceKeysTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, reducer).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public double reduceKeysToDouble(
      long parallelismThreshold, ConcurrentHashMapV8.ObjectToDouble<? super K> transformer, double basis, ConcurrentHashMapV8.DoubleByDoubleToDouble reducer
   ) {
      if (transformer != null && reducer != null) {
         return new ConcurrentHashMapV8.MapReduceKeysToDoubleTask<>(
               null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer
            )
            .invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public long reduceKeysToLong(
      long parallelismThreshold, ConcurrentHashMapV8.ObjectToLong<? super K> transformer, long basis, ConcurrentHashMapV8.LongByLongToLong reducer
   ) {
      if (transformer != null && reducer != null) {
         return new ConcurrentHashMapV8.MapReduceKeysToLongTask<>(
               null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer
            )
            .invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public int reduceKeysToInt(
      long parallelismThreshold, ConcurrentHashMapV8.ObjectToInt<? super K> transformer, int basis, ConcurrentHashMapV8.IntByIntToInt reducer
   ) {
      if (transformer != null && reducer != null) {
         return new ConcurrentHashMapV8.MapReduceKeysToIntTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer)
            .invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public void forEachValue(long parallelismThreshold, ConcurrentHashMapV8.Action<? super V> action) {
      if (action == null) {
         throw new NullPointerException();
      } else {
         new ConcurrentHashMapV8.ForEachValueTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, action).invoke();
      }
   }

   public <U> void forEachValue(
      long parallelismThreshold, ConcurrentHashMapV8.Fun<? super V, ? extends U> transformer, ConcurrentHashMapV8.Action<? super U> action
   ) {
      if (transformer != null && action != null) {
         new ConcurrentHashMapV8.ForEachTransformedValueTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, transformer, action).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public <U> U searchValues(long parallelismThreshold, ConcurrentHashMapV8.Fun<? super V, ? extends U> searchFunction) {
      if (searchFunction == null) {
         throw new NullPointerException();
      } else {
         return new ConcurrentHashMapV8.SearchValuesTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, searchFunction, new AtomicReference())
            .invoke();
      }
   }

   public V reduceValues(long parallelismThreshold, ConcurrentHashMapV8.BiFun<? super V, ? super V, ? extends V> reducer) {
      if (reducer == null) {
         throw new NullPointerException();
      } else {
         return new ConcurrentHashMapV8.ReduceValuesTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, reducer).invoke();
      }
   }

   public <U> U reduceValues(
      long parallelismThreshold,
      ConcurrentHashMapV8.Fun<? super V, ? extends U> transformer,
      ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer
   ) {
      if (transformer != null && reducer != null) {
         return new ConcurrentHashMapV8.MapReduceValuesTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, reducer).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public double reduceValuesToDouble(
      long parallelismThreshold, ConcurrentHashMapV8.ObjectToDouble<? super V> transformer, double basis, ConcurrentHashMapV8.DoubleByDoubleToDouble reducer
   ) {
      if (transformer != null && reducer != null) {
         return new ConcurrentHashMapV8.MapReduceValuesToDoubleTask<>(
               null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer
            )
            .invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public long reduceValuesToLong(
      long parallelismThreshold, ConcurrentHashMapV8.ObjectToLong<? super V> transformer, long basis, ConcurrentHashMapV8.LongByLongToLong reducer
   ) {
      if (transformer != null && reducer != null) {
         return new ConcurrentHashMapV8.MapReduceValuesToLongTask<>(
               null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer
            )
            .invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public int reduceValuesToInt(
      long parallelismThreshold, ConcurrentHashMapV8.ObjectToInt<? super V> transformer, int basis, ConcurrentHashMapV8.IntByIntToInt reducer
   ) {
      if (transformer != null && reducer != null) {
         return new ConcurrentHashMapV8.MapReduceValuesToIntTask<>(
               null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer
            )
            .invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public void forEachEntry(long parallelismThreshold, ConcurrentHashMapV8.Action<? super Entry<K, V>> action) {
      if (action == null) {
         throw new NullPointerException();
      } else {
         new ConcurrentHashMapV8.ForEachEntryTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, action).invoke();
      }
   }

   public <U> void forEachEntry(
      long parallelismThreshold, ConcurrentHashMapV8.Fun<Entry<K, V>, ? extends U> transformer, ConcurrentHashMapV8.Action<? super U> action
   ) {
      if (transformer != null && action != null) {
         new ConcurrentHashMapV8.ForEachTransformedEntryTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, transformer, action).invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public <U> U searchEntries(long parallelismThreshold, ConcurrentHashMapV8.Fun<Entry<K, V>, ? extends U> searchFunction) {
      if (searchFunction == null) {
         throw new NullPointerException();
      } else {
         return new ConcurrentHashMapV8.SearchEntriesTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, searchFunction, new AtomicReference())
            .invoke();
      }
   }

   public Entry<K, V> reduceEntries(long parallelismThreshold, ConcurrentHashMapV8.BiFun<Entry<K, V>, Entry<K, V>, ? extends Entry<K, V>> reducer) {
      if (reducer == null) {
         throw new NullPointerException();
      } else {
         return new ConcurrentHashMapV8.ReduceEntriesTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, reducer).invoke();
      }
   }

   public <U> U reduceEntries(
      long parallelismThreshold,
      ConcurrentHashMapV8.Fun<Entry<K, V>, ? extends U> transformer,
      ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer
   ) {
      if (transformer != null && reducer != null) {
         return new ConcurrentHashMapV8.MapReduceEntriesTask<>(null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, reducer)
            .invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public double reduceEntriesToDouble(
      long parallelismThreshold, ConcurrentHashMapV8.ObjectToDouble<Entry<K, V>> transformer, double basis, ConcurrentHashMapV8.DoubleByDoubleToDouble reducer
   ) {
      if (transformer != null && reducer != null) {
         return new ConcurrentHashMapV8.MapReduceEntriesToDoubleTask<>(
               null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer
            )
            .invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public long reduceEntriesToLong(
      long parallelismThreshold, ConcurrentHashMapV8.ObjectToLong<Entry<K, V>> transformer, long basis, ConcurrentHashMapV8.LongByLongToLong reducer
   ) {
      if (transformer != null && reducer != null) {
         return new ConcurrentHashMapV8.MapReduceEntriesToLongTask<>(
               null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer
            )
            .invoke();
      } else {
         throw new NullPointerException();
      }
   }

   public int reduceEntriesToInt(
      long parallelismThreshold, ConcurrentHashMapV8.ObjectToInt<Entry<K, V>> transformer, int basis, ConcurrentHashMapV8.IntByIntToInt reducer
   ) {
      if (transformer != null && reducer != null) {
         return new ConcurrentHashMapV8.MapReduceEntriesToIntTask<>(
               null, this.batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer
            )
            .invoke();
      } else {
         throw new NullPointerException();
      }
   }

   final long sumCount() {
      ConcurrentHashMapV8.CounterCell[] as = this.counterCells;
      long sum = this.baseCount;
      if (as != null) {
         for (int i = 0; i < as.length; i++) {
            ConcurrentHashMapV8.CounterCell a;
            if ((a = as[i]) != null) {
               sum += a.value;
            }
         }
      }

      return sum;
   }

   private final void fullAddCount(InternalThreadLocalMap threadLocals, long x, IntegerHolder hc, boolean wasUncontended) {
      int h;
      if (hc == null) {
         hc = new IntegerHolder();
         int s = counterHashCodeGenerator.addAndGet(1640531527);
         h = hc.value = s == 0 ? 1 : s;
         threadLocals.setCounterHashCode(hc);
      } else {
         h = hc.value;
      }

      boolean collide = false;

      while (true) {
         ConcurrentHashMapV8.CounterCell[] as = this.counterCells;
         int n;
         if (this.counterCells != null && (n = as.length) > 0) {
            ConcurrentHashMapV8.CounterCell a;
            if ((a = as[n - 1 & h]) == null) {
               if (this.cellsBusy == 0) {
                  ConcurrentHashMapV8.CounterCell r = new ConcurrentHashMapV8.CounterCell(x);
                  if (this.cellsBusy == 0 && U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
                     boolean created = false;

                     try {
                        ConcurrentHashMapV8.CounterCell[] rs = this.counterCells;
                        int m;
                        int j;
                        if (this.counterCells != null && (m = rs.length) > 0 && rs[j = m - 1 & h] == null) {
                           rs[j] = r;
                           created = true;
                        }
                     } finally {
                        this.cellsBusy = 0;
                     }

                     if (created) {
                        break;
                     }
                     continue;
                  }
               }

               collide = false;
            } else if (!wasUncontended) {
               wasUncontended = true;
            } else {
               long v = a.value;
               if (U.compareAndSwapLong(a, CELLVALUE, a.value, v + x)) {
                  break;
               }

               if (this.counterCells != as || n >= NCPU) {
                  collide = false;
               } else if (!collide) {
                  collide = true;
               } else if (this.cellsBusy == 0 && U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
                  try {
                     if (this.counterCells == as) {
                        ConcurrentHashMapV8.CounterCell[] rs = new ConcurrentHashMapV8.CounterCell[n << 1];

                        for (int i = 0; i < n; i++) {
                           rs[i] = as[i];
                        }

                        this.counterCells = rs;
                     }
                  } finally {
                     this.cellsBusy = 0;
                  }

                  collide = false;
                  continue;
               }
            }

            h ^= h << 13;
            h ^= h >>> 17;
            h ^= h << 5;
         } else if (this.cellsBusy == 0 && this.counterCells == as && U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
            boolean init = false;

            try {
               if (this.counterCells == as) {
                  ConcurrentHashMapV8.CounterCell[] rs = new ConcurrentHashMapV8.CounterCell[2];
                  rs[h & 1] = new ConcurrentHashMapV8.CounterCell(x);
                  this.counterCells = rs;
                  init = true;
               }
            } finally {
               this.cellsBusy = 0;
            }

            if (init) {
               break;
            }
         } else {
            long vx = this.baseCount;
            if (U.compareAndSwapLong(this, BASECOUNT, this.baseCount, vx + x)) {
               break;
            }
         }
      }

      hc.value = h;
   }

   private static Unsafe getUnsafe() {
      try {
         return Unsafe.getUnsafe();
      } catch (SecurityException var2) {
         try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() {
               public Unsafe run() throws Exception {
                  Class<Unsafe> k = Unsafe.class;

                  for (Field f : k.getDeclaredFields()) {
                     f.setAccessible(true);
                     Object x = f.get(null);
                     if (k.isInstance(x)) {
                        return k.cast(x);
                     }
                  }

                  throw new NoSuchFieldError("the Unsafe");
               }
            });
         } catch (PrivilegedActionException var1) {
            throw new RuntimeException("Could not initialize intrinsics", var1.getCause());
         }
      }
   }

   static {
      try {
         U = getUnsafe();
         Class<?> k = ConcurrentHashMapV8.class;
         SIZECTL = U.objectFieldOffset(k.getDeclaredField("sizeCtl"));
         TRANSFERINDEX = U.objectFieldOffset(k.getDeclaredField("transferIndex"));
         TRANSFERORIGIN = U.objectFieldOffset(k.getDeclaredField("transferOrigin"));
         BASECOUNT = U.objectFieldOffset(k.getDeclaredField("baseCount"));
         CELLSBUSY = U.objectFieldOffset(k.getDeclaredField("cellsBusy"));
         Class<?> ck = ConcurrentHashMapV8.CounterCell.class;
         CELLVALUE = U.objectFieldOffset(ck.getDeclaredField("value"));
         Class<?> ak = ConcurrentHashMapV8.Node[].class;
         ABASE = U.arrayBaseOffset(ak);
         int scale = U.arrayIndexScale(ak);
         if ((scale & scale - 1) != 0) {
            throw new Error("data type scale not a power of two");
         } else {
            ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
         }
      } catch (Exception var4) {
         throw new Error(var4);
      }
   }

   public interface Action<A> {
      void apply(A var1);
   }

   static class BaseIterator<K, V> extends ConcurrentHashMapV8.Traverser<K, V> {
      final ConcurrentHashMapV8<K, V> map;
      ConcurrentHashMapV8.Node<K, V> lastReturned;

      BaseIterator(ConcurrentHashMapV8.Node<K, V>[] tab, int size, int index, int limit, ConcurrentHashMapV8<K, V> map) {
         super(tab, size, index, limit);
         this.map = map;
         this.advance();
      }

      public final boolean hasNext() {
         return this.next != null;
      }

      public final boolean hasMoreElements() {
         return this.next != null;
      }

      public final void remove() {
         ConcurrentHashMapV8.Node<K, V> p = this.lastReturned;
         if (this.lastReturned == null) {
            throw new IllegalStateException();
         } else {
            this.lastReturned = null;
            this.map.replaceNode(p.key, null, null);
         }
      }
   }

   public interface BiAction<A, B> {
      void apply(A var1, B var2);
   }

   public interface BiFun<A, B, T> {
      T apply(A var1, B var2);
   }

   abstract static class BulkTask<K, V, R> extends CountedCompleter<R> {
      ConcurrentHashMapV8.Node<K, V>[] tab;
      ConcurrentHashMapV8.Node<K, V> next;
      int index;
      int baseIndex;
      int baseLimit;
      final int baseSize;
      int batch;

      BulkTask(ConcurrentHashMapV8.BulkTask<K, V, ?> par, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t) {
         super(par);
         this.batch = b;
         this.index = this.baseIndex = i;
         if ((this.tab = t) == null) {
            this.baseSize = this.baseLimit = 0;
         } else if (par == null) {
            this.baseSize = this.baseLimit = t.length;
         } else {
            this.baseLimit = f;
            this.baseSize = par.baseSize;
         }
      }

      final ConcurrentHashMapV8.Node<K, V> advance() {
         ConcurrentHashMapV8.Node<K, V> e = this.next;
         if (this.next != null) {
            e = e.next;
         }

         while (true) {
            if (e != null) {
               return this.next = e;
            }

            if (this.baseIndex >= this.baseLimit) {
               break;
            }

            ConcurrentHashMapV8.Node<K, V>[] t = this.tab;
            if (this.tab == null) {
               break;
            }

            int n;
            int var10000 = n = t.length;
            int i = this.index;
            if (var10000 <= this.index || i < 0) {
               break;
            }

            if ((e = ConcurrentHashMapV8.tabAt(t, this.index)) != null && e.hash < 0) {
               if (e instanceof ConcurrentHashMapV8.ForwardingNode) {
                  this.tab = ((ConcurrentHashMapV8.ForwardingNode)e).nextTable;
                  e = null;
                  continue;
               }

               if (e instanceof ConcurrentHashMapV8.TreeBin) {
                  e = ((ConcurrentHashMapV8.TreeBin)e).first;
               } else {
                  e = null;
               }
            }

            if ((this.index = this.index + this.baseSize) >= n) {
               this.index = ++this.baseIndex;
            }
         }

         return this.next = null;
      }
   }

   abstract static class CollectionView<K, V, E> implements Collection<E>, Serializable {
      private static final long serialVersionUID = 7249069246763182397L;
      final ConcurrentHashMapV8<K, V> map;
      private static final String oomeMsg = "Required array size too large";

      CollectionView(ConcurrentHashMapV8<K, V> map) {
         this.map = map;
      }

      public ConcurrentHashMapV8<K, V> getMap() {
         return this.map;
      }

      @Override
      public final void clear() {
         this.map.clear();
      }

      @Override
      public final int size() {
         return this.map.size();
      }

      @Override
      public final boolean isEmpty() {
         return this.map.isEmpty();
      }

      @Override
      public abstract Iterator<E> iterator();

      @Override
      public abstract boolean contains(Object var1);

      @Override
      public abstract boolean remove(Object var1);

      @Override
      public final Object[] toArray() {
         long sz = this.map.mappingCount();
         if (sz > 2147483639L) {
            throw new OutOfMemoryError("Required array size too large");
         } else {
            int n = (int)sz;
            Object[] r = new Object[n];
            int i = 0;

            for (E e : this) {
               if (i == n) {
                  if (n >= 2147483639) {
                     throw new OutOfMemoryError("Required array size too large");
                  }

                  if (n >= 1073741819) {
                     n = 2147483639;
                  } else {
                     n += (n >>> 1) + 1;
                  }

                  r = Arrays.copyOf(r, n);
               }

               r[i++] = e;
            }

            return i == n ? r : Arrays.copyOf(r, i);
         }
      }

      @Override
      public final <T> T[] toArray(T[] a) {
         long sz = this.map.mappingCount();
         if (sz > 2147483639L) {
            throw new OutOfMemoryError("Required array size too large");
         } else {
            int m = (int)sz;
            T[] r = (T[])(a.length >= m ? a : (Object[])Array.newInstance(a.getClass().getComponentType(), m));
            int n = r.length;
            int i = 0;

            for (E e : this) {
               if (i == n) {
                  if (n >= 2147483639) {
                     throw new OutOfMemoryError("Required array size too large");
                  }

                  if (n >= 1073741819) {
                     n = 2147483639;
                  } else {
                     n += (n >>> 1) + 1;
                  }

                  r = (T[])Arrays.copyOf(r, n);
               }

               r[i++] = (T)e;
            }

            if (a == r && i < n) {
               r[i] = null;
               return r;
            } else {
               return (T[])(i == n ? r : Arrays.copyOf(r, i));
            }
         }
      }

      @Override
      public final String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append('[');
         Iterator<E> it = this.iterator();
         if (it.hasNext()) {
            while (true) {
               Object e = it.next();
               sb.append(e == this ? "(this Collection)" : e);
               if (!it.hasNext()) {
                  break;
               }

               sb.append(',').append(' ');
            }
         }

         return sb.append(']').toString();
      }

      @Override
      public final boolean containsAll(Collection<?> c) {
         if (c != this) {
            for (Object e : c) {
               if (e == null || !this.contains(e)) {
                  return false;
               }
            }
         }

         return true;
      }

      @Override
      public final boolean removeAll(Collection<?> c) {
         boolean modified = false;
         Iterator<E> it = this.iterator();

         while (it.hasNext()) {
            if (c.contains(it.next())) {
               it.remove();
               modified = true;
            }
         }

         return modified;
      }

      @Override
      public final boolean retainAll(Collection<?> c) {
         boolean modified = false;
         Iterator<E> it = this.iterator();

         while (it.hasNext()) {
            if (!c.contains(it.next())) {
               it.remove();
               modified = true;
            }
         }

         return modified;
      }
   }

   public interface ConcurrentHashMapSpliterator<T> {
      ConcurrentHashMapV8.ConcurrentHashMapSpliterator<T> trySplit();

      long estimateSize();

      void forEachRemaining(ConcurrentHashMapV8.Action<? super T> var1);

      boolean tryAdvance(ConcurrentHashMapV8.Action<? super T> var1);
   }

   static final class CounterCell {
      volatile long p0;
      volatile long p1;
      volatile long p2;
      volatile long p3;
      volatile long p4;
      volatile long p5;
      volatile long p6;
      volatile long value;
      volatile long q0;
      volatile long q1;
      volatile long q2;
      volatile long q3;
      volatile long q4;
      volatile long q5;
      volatile long q6;

      CounterCell(long x) {
         this.value = x;
      }
   }

   static final class CounterHashCode {
      int code;
   }

   public interface DoubleByDoubleToDouble {
      double apply(double var1, double var3);
   }

   static final class EntryIterator<K, V> extends ConcurrentHashMapV8.BaseIterator<K, V> implements Iterator<Entry<K, V>> {
      EntryIterator(ConcurrentHashMapV8.Node<K, V>[] tab, int index, int size, int limit, ConcurrentHashMapV8<K, V> map) {
         super(tab, index, size, limit, map);
      }

      public final Entry<K, V> next() {
         ConcurrentHashMapV8.Node<K, V> p = this.next;
         if (this.next == null) {
            throw new NoSuchElementException();
         } else {
            K k = p.key;
            V v = p.val;
            this.lastReturned = p;
            this.advance();
            return new ConcurrentHashMapV8.MapEntry<>(k, v, this.map);
         }
      }
   }

   static final class EntrySetView<K, V> extends ConcurrentHashMapV8.CollectionView<K, V, Entry<K, V>> implements Set<Entry<K, V>>, Serializable {
      private static final long serialVersionUID = 2249069246763182397L;

      EntrySetView(ConcurrentHashMapV8<K, V> map) {
         super(map);
      }

      @Override
      public boolean contains(Object o) {
         Object k;
         Object v;
         Object r;
         Entry<?, ?> e;
         return o instanceof Entry
            && (k = (e = (Entry<?, ?>)o).getKey()) != null
            && (r = this.map.get(k)) != null
            && (v = e.getValue()) != null
            && (v == r || v.equals(r));
      }

      @Override
      public boolean remove(Object o) {
         Object k;
         Object v;
         Entry<?, ?> e;
         return o instanceof Entry && (k = (e = (Entry<?, ?>)o).getKey()) != null && (v = e.getValue()) != null && this.map.remove(k, v);
      }

      @Override
      public Iterator<Entry<K, V>> iterator() {
         ConcurrentHashMapV8<K, V> m = this.map;
         ConcurrentHashMapV8.Node<K, V>[] t = m.table;
         int f = m.table == null ? 0 : t.length;
         return new ConcurrentHashMapV8.EntryIterator<>(t, f, 0, f, m);
      }

      public boolean add(Entry<K, V> e) {
         return this.map.putVal(e.getKey(), e.getValue(), false) == null;
      }

      @Override
      public boolean addAll(Collection<? extends Entry<K, V>> c) {
         boolean added = false;

         for (Entry<K, V> e : c) {
            if (this.add(e)) {
               added = true;
            }
         }

         return added;
      }

      @Override
      public final int hashCode() {
         int h = 0;
         ConcurrentHashMapV8.Node<K, V>[] t = this.map.table;
         if (this.map.table != null) {
            ConcurrentHashMapV8.Traverser<K, V> it = new ConcurrentHashMapV8.Traverser<>(t, t.length, 0, t.length);

            ConcurrentHashMapV8.Node<K, V> p;
            while ((p = it.advance()) != null) {
               h += p.hashCode();
            }
         }

         return h;
      }

      @Override
      public final boolean equals(Object o) {
         Set<?> c;
         return o instanceof Set && ((c = (Set<?>)o) == this || this.containsAll(c) && c.containsAll(this));
      }

      public ConcurrentHashMapV8.ConcurrentHashMapSpliterator<Entry<K, V>> spliterator166() {
         ConcurrentHashMapV8<K, V> m = this.map;
         long n = m.sumCount();
         ConcurrentHashMapV8.Node<K, V>[] t = m.table;
         int f = m.table == null ? 0 : t.length;
         return new ConcurrentHashMapV8.EntrySpliterator<>(t, f, 0, f, n < 0L ? 0L : n, m);
      }

      public void forEach(ConcurrentHashMapV8.Action<? super Entry<K, V>> action) {
         if (action == null) {
            throw new NullPointerException();
         } else {
            ConcurrentHashMapV8.Node<K, V>[] t = this.map.table;
            if (this.map.table != null) {
               ConcurrentHashMapV8.Traverser<K, V> it = new ConcurrentHashMapV8.Traverser<>(t, t.length, 0, t.length);

               ConcurrentHashMapV8.Node<K, V> p;
               while ((p = it.advance()) != null) {
                  action.apply(new ConcurrentHashMapV8.MapEntry<>(p.key, p.val, this.map));
               }
            }
         }
      }
   }

   static final class EntrySpliterator<K, V>
      extends ConcurrentHashMapV8.Traverser<K, V>
      implements ConcurrentHashMapV8.ConcurrentHashMapSpliterator<Entry<K, V>> {
      final ConcurrentHashMapV8<K, V> map;
      long est;

      EntrySpliterator(ConcurrentHashMapV8.Node<K, V>[] tab, int size, int index, int limit, long est, ConcurrentHashMapV8<K, V> map) {
         super(tab, size, index, limit);
         this.map = map;
         this.est = est;
      }

      @Override
      public ConcurrentHashMapV8.ConcurrentHashMapSpliterator<Entry<K, V>> trySplit() {
         int i = this.baseIndex;
         int f = this.baseLimit;
         int h;
         return (h = this.baseIndex + this.baseLimit >>> 1) <= i
            ? null
            : new ConcurrentHashMapV8.EntrySpliterator<>(this.tab, this.baseSize, this.baseLimit = h, f, this.est >>>= 1, this.map);
      }

      @Override
      public void forEachRemaining(ConcurrentHashMapV8.Action<? super Entry<K, V>> action) {
         if (action == null) {
            throw new NullPointerException();
         } else {
            ConcurrentHashMapV8.Node<K, V> p;
            while ((p = this.advance()) != null) {
               action.apply(new ConcurrentHashMapV8.MapEntry<>(p.key, p.val, this.map));
            }
         }
      }

      @Override
      public boolean tryAdvance(ConcurrentHashMapV8.Action<? super Entry<K, V>> action) {
         if (action == null) {
            throw new NullPointerException();
         } else {
            ConcurrentHashMapV8.Node<K, V> p;
            if ((p = this.advance()) == null) {
               return false;
            } else {
               action.apply(new ConcurrentHashMapV8.MapEntry<>(p.key, p.val, this.map));
               return true;
            }
         }
      }

      @Override
      public long estimateSize() {
         return this.est;
      }
   }

   static final class ForEachEntryTask<K, V> extends ConcurrentHashMapV8.BulkTask<K, V, Void> {
      final ConcurrentHashMapV8.Action<? super Entry<K, V>> action;

      ForEachEntryTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.Action<? super Entry<K, V>> action
      ) {
         super(p, b, i, f, t);
         this.action = action;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.Action<? super Entry<K, V>> action = this.action;
         if (this.action != null) {
            int i = this.baseIndex;

            while (this.batch > 0) {
               int f = this.baseLimit;
               int h;
               if ((h = this.baseLimit + i >>> 1) <= i) {
                  break;
               }

               this.addToPendingCount(1);
               new ConcurrentHashMapV8.ForEachEntryTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, action).fork();
            }

            while ((p = this.advance()) != null) {
               action.apply(p);
            }

            this.propagateCompletion();
         }
      }
   }

   static final class ForEachKeyTask<K, V> extends ConcurrentHashMapV8.BulkTask<K, V, Void> {
      final ConcurrentHashMapV8.Action<? super K> action;

      ForEachKeyTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, ConcurrentHashMapV8.Action<? super K> action
      ) {
         super(p, b, i, f, t);
         this.action = action;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.Action<? super K> action = this.action;
         if (this.action != null) {
            int i = this.baseIndex;

            while (this.batch > 0) {
               int f = this.baseLimit;
               int h;
               if ((h = this.baseLimit + i >>> 1) <= i) {
                  break;
               }

               this.addToPendingCount(1);
               new ConcurrentHashMapV8.ForEachKeyTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, action).fork();
            }

            while ((p = this.advance()) != null) {
               action.apply(p.key);
            }

            this.propagateCompletion();
         }
      }
   }

   static final class ForEachMappingTask<K, V> extends ConcurrentHashMapV8.BulkTask<K, V, Void> {
      final ConcurrentHashMapV8.BiAction<? super K, ? super V> action;

      ForEachMappingTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.BiAction<? super K, ? super V> action
      ) {
         super(p, b, i, f, t);
         this.action = action;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.BiAction<? super K, ? super V> action = this.action;
         if (this.action != null) {
            int i = this.baseIndex;

            while (this.batch > 0) {
               int f = this.baseLimit;
               int h;
               if ((h = this.baseLimit + i >>> 1) <= i) {
                  break;
               }

               this.addToPendingCount(1);
               new ConcurrentHashMapV8.ForEachMappingTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, action).fork();
            }

            while ((p = this.advance()) != null) {
               action.apply(p.key, p.val);
            }

            this.propagateCompletion();
         }
      }
   }

   static final class ForEachTransformedEntryTask<K, V, U> extends ConcurrentHashMapV8.BulkTask<K, V, Void> {
      final ConcurrentHashMapV8.Fun<Entry<K, V>, ? extends U> transformer;
      final ConcurrentHashMapV8.Action<? super U> action;

      ForEachTransformedEntryTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.Fun<Entry<K, V>, ? extends U> transformer,
         ConcurrentHashMapV8.Action<? super U> action
      ) {
         super(p, b, i, f, t);
         this.transformer = transformer;
         this.action = action;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.Fun<Entry<K, V>, ? extends U> transformer = this.transformer;
         if (this.transformer != null) {
            ConcurrentHashMapV8.Action<? super U> action = this.action;
            if (this.action != null) {
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  this.addToPendingCount(1);
                  new ConcurrentHashMapV8.ForEachTransformedEntryTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, transformer, action).fork();
               }

               while ((p = this.advance()) != null) {
                  U u;
                  if ((u = (U)transformer.apply(p)) != null) {
                     action.apply(u);
                  }
               }

               this.propagateCompletion();
            }
         }
      }
   }

   static final class ForEachTransformedKeyTask<K, V, U> extends ConcurrentHashMapV8.BulkTask<K, V, Void> {
      final ConcurrentHashMapV8.Fun<? super K, ? extends U> transformer;
      final ConcurrentHashMapV8.Action<? super U> action;

      ForEachTransformedKeyTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.Fun<? super K, ? extends U> transformer,
         ConcurrentHashMapV8.Action<? super U> action
      ) {
         super(p, b, i, f, t);
         this.transformer = transformer;
         this.action = action;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.Fun<? super K, ? extends U> transformer = this.transformer;
         if (this.transformer != null) {
            ConcurrentHashMapV8.Action<? super U> action = this.action;
            if (this.action != null) {
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  this.addToPendingCount(1);
                  new ConcurrentHashMapV8.ForEachTransformedKeyTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, transformer, action).fork();
               }

               while ((p = this.advance()) != null) {
                  U u;
                  if ((u = (U)transformer.apply(p.key)) != null) {
                     action.apply(u);
                  }
               }

               this.propagateCompletion();
            }
         }
      }
   }

   static final class ForEachTransformedMappingTask<K, V, U> extends ConcurrentHashMapV8.BulkTask<K, V, Void> {
      final ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> transformer;
      final ConcurrentHashMapV8.Action<? super U> action;

      ForEachTransformedMappingTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> transformer,
         ConcurrentHashMapV8.Action<? super U> action
      ) {
         super(p, b, i, f, t);
         this.transformer = transformer;
         this.action = action;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> transformer = this.transformer;
         if (this.transformer != null) {
            ConcurrentHashMapV8.Action<? super U> action = this.action;
            if (this.action != null) {
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  this.addToPendingCount(1);
                  new ConcurrentHashMapV8.ForEachTransformedMappingTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, transformer, action).fork();
               }

               while ((p = this.advance()) != null) {
                  U u;
                  if ((u = (U)transformer.apply(p.key, p.val)) != null) {
                     action.apply(u);
                  }
               }

               this.propagateCompletion();
            }
         }
      }
   }

   static final class ForEachTransformedValueTask<K, V, U> extends ConcurrentHashMapV8.BulkTask<K, V, Void> {
      final ConcurrentHashMapV8.Fun<? super V, ? extends U> transformer;
      final ConcurrentHashMapV8.Action<? super U> action;

      ForEachTransformedValueTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.Fun<? super V, ? extends U> transformer,
         ConcurrentHashMapV8.Action<? super U> action
      ) {
         super(p, b, i, f, t);
         this.transformer = transformer;
         this.action = action;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.Fun<? super V, ? extends U> transformer = this.transformer;
         if (this.transformer != null) {
            ConcurrentHashMapV8.Action<? super U> action = this.action;
            if (this.action != null) {
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  this.addToPendingCount(1);
                  new ConcurrentHashMapV8.ForEachTransformedValueTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, transformer, action).fork();
               }

               while ((p = this.advance()) != null) {
                  U u;
                  if ((u = (U)transformer.apply(p.val)) != null) {
                     action.apply(u);
                  }
               }

               this.propagateCompletion();
            }
         }
      }
   }

   static final class ForEachValueTask<K, V> extends ConcurrentHashMapV8.BulkTask<K, V, Void> {
      final ConcurrentHashMapV8.Action<? super V> action;

      ForEachValueTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p, int b, int i, int f, ConcurrentHashMapV8.Node<K, V>[] t, ConcurrentHashMapV8.Action<? super V> action
      ) {
         super(p, b, i, f, t);
         this.action = action;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.Action<? super V> action = this.action;
         if (this.action != null) {
            int i = this.baseIndex;

            while (this.batch > 0) {
               int f = this.baseLimit;
               int h;
               if ((h = this.baseLimit + i >>> 1) <= i) {
                  break;
               }

               this.addToPendingCount(1);
               new ConcurrentHashMapV8.ForEachValueTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, action).fork();
            }

            while ((p = this.advance()) != null) {
               action.apply(p.val);
            }

            this.propagateCompletion();
         }
      }
   }

   static final class ForwardingNode<K, V> extends ConcurrentHashMapV8.Node<K, V> {
      final ConcurrentHashMapV8.Node<K, V>[] nextTable;

      ForwardingNode(ConcurrentHashMapV8.Node<K, V>[] tab) {
         super(-1, null, null, null);
         this.nextTable = tab;
      }

      @Override
      ConcurrentHashMapV8.Node<K, V> find(int h, Object k) {
         ConcurrentHashMapV8.Node<K, V>[] tab = this.nextTable;

         ConcurrentHashMapV8.Node<K, V> e;
         int n;
         label41:
         while (k != null && tab != null && (n = tab.length) != 0 && (e = ConcurrentHashMapV8.tabAt(tab, n - 1 & h)) != null) {
            do {
               int eh = e.hash;
               if (e.hash == h) {
                  K ek = e.key;
                  if (e.key == k || ek != null && k.equals(ek)) {
                     return e;
                  }
               }

               if (eh < 0) {
                  if (!(e instanceof ConcurrentHashMapV8.ForwardingNode)) {
                     return e.find(h, k);
                  }

                  tab = ((ConcurrentHashMapV8.ForwardingNode)e).nextTable;
                  continue label41;
               }
            } while ((e = e.next) != null);

            return null;
         }

         return null;
      }
   }

   public interface Fun<A, T> {
      T apply(A var1);
   }

   public interface IntByIntToInt {
      int apply(int var1, int var2);
   }

   static final class KeyIterator<K, V> extends ConcurrentHashMapV8.BaseIterator<K, V> implements Iterator<K>, Enumeration<K> {
      KeyIterator(ConcurrentHashMapV8.Node<K, V>[] tab, int index, int size, int limit, ConcurrentHashMapV8<K, V> map) {
         super(tab, index, size, limit, map);
      }

      @Override
      public final K next() {
         ConcurrentHashMapV8.Node<K, V> p = this.next;
         if (this.next == null) {
            throw new NoSuchElementException();
         } else {
            K k = p.key;
            this.lastReturned = p;
            this.advance();
            return k;
         }
      }

      @Override
      public final K nextElement() {
         return this.next();
      }
   }

   public static class KeySetView<K, V> extends ConcurrentHashMapV8.CollectionView<K, V, K> implements Set<K>, Serializable {
      private static final long serialVersionUID = 7249069246763182397L;
      private final V value;

      KeySetView(ConcurrentHashMapV8<K, V> map, V value) {
         super(map);
         this.value = value;
      }

      public V getMappedValue() {
         return this.value;
      }

      @Override
      public boolean contains(Object o) {
         return this.map.containsKey(o);
      }

      @Override
      public boolean remove(Object o) {
         return this.map.remove(o) != null;
      }

      @Override
      public Iterator<K> iterator() {
         ConcurrentHashMapV8<K, V> m = this.map;
         ConcurrentHashMapV8.Node<K, V>[] t = m.table;
         int f = m.table == null ? 0 : t.length;
         return new ConcurrentHashMapV8.KeyIterator<>(t, f, 0, f, m);
      }

      @Override
      public boolean add(K e) {
         V v = this.value;
         if (this.value == null) {
            throw new UnsupportedOperationException();
         } else {
            return this.map.putVal(e, v, true) == null;
         }
      }

      @Override
      public boolean addAll(Collection<? extends K> c) {
         boolean added = false;
         V v = this.value;
         if (this.value == null) {
            throw new UnsupportedOperationException();
         } else {
            for (K e : c) {
               if (this.map.putVal(e, v, true) == null) {
                  added = true;
               }
            }

            return added;
         }
      }

      @Override
      public int hashCode() {
         int h = 0;

         for (K e : this) {
            h += e.hashCode();
         }

         return h;
      }

      @Override
      public boolean equals(Object o) {
         Set<?> c;
         return o instanceof Set && ((c = (Set<?>)o) == this || this.containsAll(c) && c.containsAll(this));
      }

      public ConcurrentHashMapV8.ConcurrentHashMapSpliterator<K> spliterator166() {
         ConcurrentHashMapV8<K, V> m = this.map;
         long n = m.sumCount();
         ConcurrentHashMapV8.Node<K, V>[] t = m.table;
         int f = m.table == null ? 0 : t.length;
         return new ConcurrentHashMapV8.KeySpliterator<>(t, f, 0, f, n < 0L ? 0L : n);
      }

      public void forEach(ConcurrentHashMapV8.Action<? super K> action) {
         if (action == null) {
            throw new NullPointerException();
         } else {
            ConcurrentHashMapV8.Node<K, V>[] t = this.map.table;
            if (this.map.table != null) {
               ConcurrentHashMapV8.Traverser<K, V> it = new ConcurrentHashMapV8.Traverser<>(t, t.length, 0, t.length);

               ConcurrentHashMapV8.Node<K, V> p;
               while ((p = it.advance()) != null) {
                  action.apply(p.key);
               }
            }
         }
      }
   }

   static final class KeySpliterator<K, V> extends ConcurrentHashMapV8.Traverser<K, V> implements ConcurrentHashMapV8.ConcurrentHashMapSpliterator<K> {
      long est;

      KeySpliterator(ConcurrentHashMapV8.Node<K, V>[] tab, int size, int index, int limit, long est) {
         super(tab, size, index, limit);
         this.est = est;
      }

      @Override
      public ConcurrentHashMapV8.ConcurrentHashMapSpliterator<K> trySplit() {
         int i = this.baseIndex;
         int f = this.baseLimit;
         int h;
         return (h = this.baseIndex + this.baseLimit >>> 1) <= i
            ? null
            : new ConcurrentHashMapV8.KeySpliterator<>(this.tab, this.baseSize, this.baseLimit = h, f, this.est >>>= 1);
      }

      @Override
      public void forEachRemaining(ConcurrentHashMapV8.Action<? super K> action) {
         if (action == null) {
            throw new NullPointerException();
         } else {
            ConcurrentHashMapV8.Node<K, V> p;
            while ((p = this.advance()) != null) {
               action.apply(p.key);
            }
         }
      }

      @Override
      public boolean tryAdvance(ConcurrentHashMapV8.Action<? super K> action) {
         if (action == null) {
            throw new NullPointerException();
         } else {
            ConcurrentHashMapV8.Node<K, V> p;
            if ((p = this.advance()) == null) {
               return false;
            } else {
               action.apply(p.key);
               return true;
            }
         }
      }

      @Override
      public long estimateSize() {
         return this.est;
      }
   }

   public interface LongByLongToLong {
      long apply(long var1, long var3);
   }

   static final class MapEntry<K, V> implements Entry<K, V> {
      final K key;
      V val;
      final ConcurrentHashMapV8<K, V> map;

      MapEntry(K key, V val, ConcurrentHashMapV8<K, V> map) {
         this.key = key;
         this.val = val;
         this.map = map;
      }

      @Override
      public K getKey() {
         return this.key;
      }

      @Override
      public V getValue() {
         return this.val;
      }

      @Override
      public int hashCode() {
         return this.key.hashCode() ^ this.val.hashCode();
      }

      @Override
      public String toString() {
         return this.key + "=" + this.val;
      }

      @Override
      public boolean equals(Object o) {
         Object k;
         Object v;
         Entry<?, ?> e;
         return o instanceof Entry
            && (k = (e = (Entry<?, ?>)o).getKey()) != null
            && (v = e.getValue()) != null
            && (k == this.key || k.equals(this.key))
            && (v == this.val || v.equals(this.val));
      }

      @Override
      public V setValue(V value) {
         if (value == null) {
            throw new NullPointerException();
         } else {
            V v = this.val;
            this.val = value;
            this.map.put(this.key, value);
            return v;
         }
      }
   }

   static final class MapReduceEntriesTask<K, V, U> extends ConcurrentHashMapV8.BulkTask<K, V, U> {
      final ConcurrentHashMapV8.Fun<Entry<K, V>, ? extends U> transformer;
      final ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer;
      U result;
      ConcurrentHashMapV8.MapReduceEntriesTask<K, V, U> rights;
      ConcurrentHashMapV8.MapReduceEntriesTask<K, V, U> nextRight;

      MapReduceEntriesTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.MapReduceEntriesTask<K, V, U> nextRight,
         ConcurrentHashMapV8.Fun<Entry<K, V>, ? extends U> transformer,
         ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer
      ) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.reducer = reducer;
      }

      @Override
      public final U getRawResult() {
         return this.result;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.Fun<Entry<K, V>, ? extends U> transformer = this.transformer;
         if (this.transformer != null) {
            ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer = this.reducer;
            if (this.reducer != null) {
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  this.addToPendingCount(1);
                  (this.rights = new ConcurrentHashMapV8.MapReduceEntriesTask<>(
                        this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, reducer
                     ))
                     .fork();
               }

               U r = null;

               ConcurrentHashMapV8.Node<K, V> p;
               while ((p = this.advance()) != null) {
                  U u;
                  if ((u = (U)transformer.apply(p)) != null) {
                     r = r == null ? u : reducer.apply(r, u);
                  }
               }

               this.result = r;

               for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                  ConcurrentHashMapV8.MapReduceEntriesTask<K, V, U> t = (ConcurrentHashMapV8.MapReduceEntriesTask<K, V, U>)c;

                  for (ConcurrentHashMapV8.MapReduceEntriesTask<K, V, U> s = t.rights; s != null; s = t.rights = s.nextRight) {
                     U sr = s.result;
                     if (s.result != null) {
                        U tr = t.result;
                        t.result = t.result == null ? sr : reducer.apply(tr, sr);
                     }
                  }
               }
            }
         }
      }
   }

   static final class MapReduceEntriesToDoubleTask<K, V> extends ConcurrentHashMapV8.BulkTask<K, V, Double> {
      final ConcurrentHashMapV8.ObjectToDouble<Entry<K, V>> transformer;
      final ConcurrentHashMapV8.DoubleByDoubleToDouble reducer;
      final double basis;
      double result;
      ConcurrentHashMapV8.MapReduceEntriesToDoubleTask<K, V> rights;
      ConcurrentHashMapV8.MapReduceEntriesToDoubleTask<K, V> nextRight;

      MapReduceEntriesToDoubleTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.MapReduceEntriesToDoubleTask<K, V> nextRight,
         ConcurrentHashMapV8.ObjectToDouble<Entry<K, V>> transformer,
         double basis,
         ConcurrentHashMapV8.DoubleByDoubleToDouble reducer
      ) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Double getRawResult() {
         return this.result;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.ObjectToDouble<Entry<K, V>> transformer = this.transformer;
         if (this.transformer != null) {
            ConcurrentHashMapV8.DoubleByDoubleToDouble reducer = this.reducer;
            if (this.reducer != null) {
               double r = this.basis;
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  this.addToPendingCount(1);
                  (this.rights = new ConcurrentHashMapV8.MapReduceEntriesToDoubleTask<>(
                        this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer
                     ))
                     .fork();
               }

               while ((p = this.advance()) != null) {
                  r = reducer.apply(r, transformer.apply(p));
               }

               this.result = r;

               for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                  ConcurrentHashMapV8.MapReduceEntriesToDoubleTask<K, V> t = (ConcurrentHashMapV8.MapReduceEntriesToDoubleTask<K, V>)c;

                  for (ConcurrentHashMapV8.MapReduceEntriesToDoubleTask<K, V> s = t.rights; s != null; s = t.rights = s.nextRight) {
                     t.result = reducer.apply(t.result, s.result);
                  }
               }
            }
         }
      }
   }

   static final class MapReduceEntriesToIntTask<K, V> extends ConcurrentHashMapV8.BulkTask<K, V, Integer> {
      final ConcurrentHashMapV8.ObjectToInt<Entry<K, V>> transformer;
      final ConcurrentHashMapV8.IntByIntToInt reducer;
      final int basis;
      int result;
      ConcurrentHashMapV8.MapReduceEntriesToIntTask<K, V> rights;
      ConcurrentHashMapV8.MapReduceEntriesToIntTask<K, V> nextRight;

      MapReduceEntriesToIntTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.MapReduceEntriesToIntTask<K, V> nextRight,
         ConcurrentHashMapV8.ObjectToInt<Entry<K, V>> transformer,
         int basis,
         ConcurrentHashMapV8.IntByIntToInt reducer
      ) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Integer getRawResult() {
         return this.result;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.ObjectToInt<Entry<K, V>> transformer = this.transformer;
         if (this.transformer != null) {
            ConcurrentHashMapV8.IntByIntToInt reducer = this.reducer;
            if (this.reducer != null) {
               int r = this.basis;
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  this.addToPendingCount(1);
                  (this.rights = new ConcurrentHashMapV8.MapReduceEntriesToIntTask<>(
                        this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer
                     ))
                     .fork();
               }

               while ((p = this.advance()) != null) {
                  r = reducer.apply(r, transformer.apply(p));
               }

               this.result = r;

               for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                  ConcurrentHashMapV8.MapReduceEntriesToIntTask<K, V> t = (ConcurrentHashMapV8.MapReduceEntriesToIntTask<K, V>)c;

                  for (ConcurrentHashMapV8.MapReduceEntriesToIntTask<K, V> s = t.rights; s != null; s = t.rights = s.nextRight) {
                     t.result = reducer.apply(t.result, s.result);
                  }
               }
            }
         }
      }
   }

   static final class MapReduceEntriesToLongTask<K, V> extends ConcurrentHashMapV8.BulkTask<K, V, Long> {
      final ConcurrentHashMapV8.ObjectToLong<Entry<K, V>> transformer;
      final ConcurrentHashMapV8.LongByLongToLong reducer;
      final long basis;
      long result;
      ConcurrentHashMapV8.MapReduceEntriesToLongTask<K, V> rights;
      ConcurrentHashMapV8.MapReduceEntriesToLongTask<K, V> nextRight;

      MapReduceEntriesToLongTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.MapReduceEntriesToLongTask<K, V> nextRight,
         ConcurrentHashMapV8.ObjectToLong<Entry<K, V>> transformer,
         long basis,
         ConcurrentHashMapV8.LongByLongToLong reducer
      ) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Long getRawResult() {
         return this.result;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.ObjectToLong<Entry<K, V>> transformer = this.transformer;
         if (this.transformer != null) {
            ConcurrentHashMapV8.LongByLongToLong reducer = this.reducer;
            if (this.reducer != null) {
               long r = this.basis;
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  this.addToPendingCount(1);
                  (this.rights = new ConcurrentHashMapV8.MapReduceEntriesToLongTask<>(
                        this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer
                     ))
                     .fork();
               }

               while ((p = this.advance()) != null) {
                  r = reducer.apply(r, transformer.apply(p));
               }

               this.result = r;

               for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                  ConcurrentHashMapV8.MapReduceEntriesToLongTask<K, V> t = (ConcurrentHashMapV8.MapReduceEntriesToLongTask<K, V>)c;

                  for (ConcurrentHashMapV8.MapReduceEntriesToLongTask<K, V> s = t.rights; s != null; s = t.rights = s.nextRight) {
                     t.result = reducer.apply(t.result, s.result);
                  }
               }
            }
         }
      }
   }

   static final class MapReduceKeysTask<K, V, U> extends ConcurrentHashMapV8.BulkTask<K, V, U> {
      final ConcurrentHashMapV8.Fun<? super K, ? extends U> transformer;
      final ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer;
      U result;
      ConcurrentHashMapV8.MapReduceKeysTask<K, V, U> rights;
      ConcurrentHashMapV8.MapReduceKeysTask<K, V, U> nextRight;

      MapReduceKeysTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.MapReduceKeysTask<K, V, U> nextRight,
         ConcurrentHashMapV8.Fun<? super K, ? extends U> transformer,
         ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer
      ) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.reducer = reducer;
      }

      @Override
      public final U getRawResult() {
         return this.result;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.Fun<? super K, ? extends U> transformer = this.transformer;
         if (this.transformer != null) {
            ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer = this.reducer;
            if (this.reducer != null) {
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  this.addToPendingCount(1);
                  (this.rights = new ConcurrentHashMapV8.MapReduceKeysTask<>(
                        this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, reducer
                     ))
                     .fork();
               }

               U r = null;

               ConcurrentHashMapV8.Node<K, V> p;
               while ((p = this.advance()) != null) {
                  U u;
                  if ((u = (U)transformer.apply(p.key)) != null) {
                     r = r == null ? u : reducer.apply(r, u);
                  }
               }

               this.result = r;

               for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                  ConcurrentHashMapV8.MapReduceKeysTask<K, V, U> t = (ConcurrentHashMapV8.MapReduceKeysTask<K, V, U>)c;

                  for (ConcurrentHashMapV8.MapReduceKeysTask<K, V, U> s = t.rights; s != null; s = t.rights = s.nextRight) {
                     U sr = s.result;
                     if (s.result != null) {
                        U tr = t.result;
                        t.result = t.result == null ? sr : reducer.apply(tr, sr);
                     }
                  }
               }
            }
         }
      }
   }

   static final class MapReduceKeysToDoubleTask<K, V> extends ConcurrentHashMapV8.BulkTask<K, V, Double> {
      final ConcurrentHashMapV8.ObjectToDouble<? super K> transformer;
      final ConcurrentHashMapV8.DoubleByDoubleToDouble reducer;
      final double basis;
      double result;
      ConcurrentHashMapV8.MapReduceKeysToDoubleTask<K, V> rights;
      ConcurrentHashMapV8.MapReduceKeysToDoubleTask<K, V> nextRight;

      MapReduceKeysToDoubleTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.MapReduceKeysToDoubleTask<K, V> nextRight,
         ConcurrentHashMapV8.ObjectToDouble<? super K> transformer,
         double basis,
         ConcurrentHashMapV8.DoubleByDoubleToDouble reducer
      ) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Double getRawResult() {
         return this.result;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.ObjectToDouble<? super K> transformer = this.transformer;
         if (this.transformer != null) {
            ConcurrentHashMapV8.DoubleByDoubleToDouble reducer = this.reducer;
            if (this.reducer != null) {
               double r = this.basis;
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  this.addToPendingCount(1);
                  (this.rights = new ConcurrentHashMapV8.MapReduceKeysToDoubleTask<>(
                        this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer
                     ))
                     .fork();
               }

               while ((p = this.advance()) != null) {
                  r = reducer.apply(r, transformer.apply(p.key));
               }

               this.result = r;

               for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                  ConcurrentHashMapV8.MapReduceKeysToDoubleTask<K, V> t = (ConcurrentHashMapV8.MapReduceKeysToDoubleTask<K, V>)c;

                  for (ConcurrentHashMapV8.MapReduceKeysToDoubleTask<K, V> s = t.rights; s != null; s = t.rights = s.nextRight) {
                     t.result = reducer.apply(t.result, s.result);
                  }
               }
            }
         }
      }
   }

   static final class MapReduceKeysToIntTask<K, V> extends ConcurrentHashMapV8.BulkTask<K, V, Integer> {
      final ConcurrentHashMapV8.ObjectToInt<? super K> transformer;
      final ConcurrentHashMapV8.IntByIntToInt reducer;
      final int basis;
      int result;
      ConcurrentHashMapV8.MapReduceKeysToIntTask<K, V> rights;
      ConcurrentHashMapV8.MapReduceKeysToIntTask<K, V> nextRight;

      MapReduceKeysToIntTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.MapReduceKeysToIntTask<K, V> nextRight,
         ConcurrentHashMapV8.ObjectToInt<? super K> transformer,
         int basis,
         ConcurrentHashMapV8.IntByIntToInt reducer
      ) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Integer getRawResult() {
         return this.result;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.ObjectToInt<? super K> transformer = this.transformer;
         if (this.transformer != null) {
            ConcurrentHashMapV8.IntByIntToInt reducer = this.reducer;
            if (this.reducer != null) {
               int r = this.basis;
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  this.addToPendingCount(1);
                  (this.rights = new ConcurrentHashMapV8.MapReduceKeysToIntTask<>(
                        this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer
                     ))
                     .fork();
               }

               while ((p = this.advance()) != null) {
                  r = reducer.apply(r, transformer.apply(p.key));
               }

               this.result = r;

               for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                  ConcurrentHashMapV8.MapReduceKeysToIntTask<K, V> t = (ConcurrentHashMapV8.MapReduceKeysToIntTask<K, V>)c;

                  for (ConcurrentHashMapV8.MapReduceKeysToIntTask<K, V> s = t.rights; s != null; s = t.rights = s.nextRight) {
                     t.result = reducer.apply(t.result, s.result);
                  }
               }
            }
         }
      }
   }

   static final class MapReduceKeysToLongTask<K, V> extends ConcurrentHashMapV8.BulkTask<K, V, Long> {
      final ConcurrentHashMapV8.ObjectToLong<? super K> transformer;
      final ConcurrentHashMapV8.LongByLongToLong reducer;
      final long basis;
      long result;
      ConcurrentHashMapV8.MapReduceKeysToLongTask<K, V> rights;
      ConcurrentHashMapV8.MapReduceKeysToLongTask<K, V> nextRight;

      MapReduceKeysToLongTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.MapReduceKeysToLongTask<K, V> nextRight,
         ConcurrentHashMapV8.ObjectToLong<? super K> transformer,
         long basis,
         ConcurrentHashMapV8.LongByLongToLong reducer
      ) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Long getRawResult() {
         return this.result;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.ObjectToLong<? super K> transformer = this.transformer;
         if (this.transformer != null) {
            ConcurrentHashMapV8.LongByLongToLong reducer = this.reducer;
            if (this.reducer != null) {
               long r = this.basis;
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  this.addToPendingCount(1);
                  (this.rights = new ConcurrentHashMapV8.MapReduceKeysToLongTask<>(
                        this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer
                     ))
                     .fork();
               }

               while ((p = this.advance()) != null) {
                  r = reducer.apply(r, transformer.apply(p.key));
               }

               this.result = r;

               for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                  ConcurrentHashMapV8.MapReduceKeysToLongTask<K, V> t = (ConcurrentHashMapV8.MapReduceKeysToLongTask<K, V>)c;

                  for (ConcurrentHashMapV8.MapReduceKeysToLongTask<K, V> s = t.rights; s != null; s = t.rights = s.nextRight) {
                     t.result = reducer.apply(t.result, s.result);
                  }
               }
            }
         }
      }
   }

   static final class MapReduceMappingsTask<K, V, U> extends ConcurrentHashMapV8.BulkTask<K, V, U> {
      final ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> transformer;
      final ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer;
      U result;
      ConcurrentHashMapV8.MapReduceMappingsTask<K, V, U> rights;
      ConcurrentHashMapV8.MapReduceMappingsTask<K, V, U> nextRight;

      MapReduceMappingsTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.MapReduceMappingsTask<K, V, U> nextRight,
         ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> transformer,
         ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer
      ) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.reducer = reducer;
      }

      @Override
      public final U getRawResult() {
         return this.result;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> transformer = this.transformer;
         if (this.transformer != null) {
            ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer = this.reducer;
            if (this.reducer != null) {
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  this.addToPendingCount(1);
                  (this.rights = new ConcurrentHashMapV8.MapReduceMappingsTask<>(
                        this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, reducer
                     ))
                     .fork();
               }

               U r = null;

               ConcurrentHashMapV8.Node<K, V> p;
               while ((p = this.advance()) != null) {
                  U u;
                  if ((u = (U)transformer.apply(p.key, p.val)) != null) {
                     r = r == null ? u : reducer.apply(r, u);
                  }
               }

               this.result = r;

               for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                  ConcurrentHashMapV8.MapReduceMappingsTask<K, V, U> t = (ConcurrentHashMapV8.MapReduceMappingsTask<K, V, U>)c;

                  for (ConcurrentHashMapV8.MapReduceMappingsTask<K, V, U> s = t.rights; s != null; s = t.rights = s.nextRight) {
                     U sr = s.result;
                     if (s.result != null) {
                        U tr = t.result;
                        t.result = t.result == null ? sr : reducer.apply(tr, sr);
                     }
                  }
               }
            }
         }
      }
   }

   static final class MapReduceMappingsToDoubleTask<K, V> extends ConcurrentHashMapV8.BulkTask<K, V, Double> {
      final ConcurrentHashMapV8.ObjectByObjectToDouble<? super K, ? super V> transformer;
      final ConcurrentHashMapV8.DoubleByDoubleToDouble reducer;
      final double basis;
      double result;
      ConcurrentHashMapV8.MapReduceMappingsToDoubleTask<K, V> rights;
      ConcurrentHashMapV8.MapReduceMappingsToDoubleTask<K, V> nextRight;

      MapReduceMappingsToDoubleTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.MapReduceMappingsToDoubleTask<K, V> nextRight,
         ConcurrentHashMapV8.ObjectByObjectToDouble<? super K, ? super V> transformer,
         double basis,
         ConcurrentHashMapV8.DoubleByDoubleToDouble reducer
      ) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Double getRawResult() {
         return this.result;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.ObjectByObjectToDouble<? super K, ? super V> transformer = this.transformer;
         if (this.transformer != null) {
            ConcurrentHashMapV8.DoubleByDoubleToDouble reducer = this.reducer;
            if (this.reducer != null) {
               double r = this.basis;
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  this.addToPendingCount(1);
                  (this.rights = new ConcurrentHashMapV8.MapReduceMappingsToDoubleTask<>(
                        this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer
                     ))
                     .fork();
               }

               while ((p = this.advance()) != null) {
                  r = reducer.apply(r, transformer.apply(p.key, p.val));
               }

               this.result = r;

               for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                  ConcurrentHashMapV8.MapReduceMappingsToDoubleTask<K, V> t = (ConcurrentHashMapV8.MapReduceMappingsToDoubleTask<K, V>)c;

                  for (ConcurrentHashMapV8.MapReduceMappingsToDoubleTask<K, V> s = t.rights; s != null; s = t.rights = s.nextRight) {
                     t.result = reducer.apply(t.result, s.result);
                  }
               }
            }
         }
      }
   }

   static final class MapReduceMappingsToIntTask<K, V> extends ConcurrentHashMapV8.BulkTask<K, V, Integer> {
      final ConcurrentHashMapV8.ObjectByObjectToInt<? super K, ? super V> transformer;
      final ConcurrentHashMapV8.IntByIntToInt reducer;
      final int basis;
      int result;
      ConcurrentHashMapV8.MapReduceMappingsToIntTask<K, V> rights;
      ConcurrentHashMapV8.MapReduceMappingsToIntTask<K, V> nextRight;

      MapReduceMappingsToIntTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.MapReduceMappingsToIntTask<K, V> nextRight,
         ConcurrentHashMapV8.ObjectByObjectToInt<? super K, ? super V> transformer,
         int basis,
         ConcurrentHashMapV8.IntByIntToInt reducer
      ) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Integer getRawResult() {
         return this.result;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.ObjectByObjectToInt<? super K, ? super V> transformer = this.transformer;
         if (this.transformer != null) {
            ConcurrentHashMapV8.IntByIntToInt reducer = this.reducer;
            if (this.reducer != null) {
               int r = this.basis;
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  this.addToPendingCount(1);
                  (this.rights = new ConcurrentHashMapV8.MapReduceMappingsToIntTask<>(
                        this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer
                     ))
                     .fork();
               }

               while ((p = this.advance()) != null) {
                  r = reducer.apply(r, transformer.apply(p.key, p.val));
               }

               this.result = r;

               for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                  ConcurrentHashMapV8.MapReduceMappingsToIntTask<K, V> t = (ConcurrentHashMapV8.MapReduceMappingsToIntTask<K, V>)c;

                  for (ConcurrentHashMapV8.MapReduceMappingsToIntTask<K, V> s = t.rights; s != null; s = t.rights = s.nextRight) {
                     t.result = reducer.apply(t.result, s.result);
                  }
               }
            }
         }
      }
   }

   static final class MapReduceMappingsToLongTask<K, V> extends ConcurrentHashMapV8.BulkTask<K, V, Long> {
      final ConcurrentHashMapV8.ObjectByObjectToLong<? super K, ? super V> transformer;
      final ConcurrentHashMapV8.LongByLongToLong reducer;
      final long basis;
      long result;
      ConcurrentHashMapV8.MapReduceMappingsToLongTask<K, V> rights;
      ConcurrentHashMapV8.MapReduceMappingsToLongTask<K, V> nextRight;

      MapReduceMappingsToLongTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.MapReduceMappingsToLongTask<K, V> nextRight,
         ConcurrentHashMapV8.ObjectByObjectToLong<? super K, ? super V> transformer,
         long basis,
         ConcurrentHashMapV8.LongByLongToLong reducer
      ) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Long getRawResult() {
         return this.result;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.ObjectByObjectToLong<? super K, ? super V> transformer = this.transformer;
         if (this.transformer != null) {
            ConcurrentHashMapV8.LongByLongToLong reducer = this.reducer;
            if (this.reducer != null) {
               long r = this.basis;
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  this.addToPendingCount(1);
                  (this.rights = new ConcurrentHashMapV8.MapReduceMappingsToLongTask<>(
                        this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer
                     ))
                     .fork();
               }

               while ((p = this.advance()) != null) {
                  r = reducer.apply(r, transformer.apply(p.key, p.val));
               }

               this.result = r;

               for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                  ConcurrentHashMapV8.MapReduceMappingsToLongTask<K, V> t = (ConcurrentHashMapV8.MapReduceMappingsToLongTask<K, V>)c;

                  for (ConcurrentHashMapV8.MapReduceMappingsToLongTask<K, V> s = t.rights; s != null; s = t.rights = s.nextRight) {
                     t.result = reducer.apply(t.result, s.result);
                  }
               }
            }
         }
      }
   }

   static final class MapReduceValuesTask<K, V, U> extends ConcurrentHashMapV8.BulkTask<K, V, U> {
      final ConcurrentHashMapV8.Fun<? super V, ? extends U> transformer;
      final ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer;
      U result;
      ConcurrentHashMapV8.MapReduceValuesTask<K, V, U> rights;
      ConcurrentHashMapV8.MapReduceValuesTask<K, V, U> nextRight;

      MapReduceValuesTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.MapReduceValuesTask<K, V, U> nextRight,
         ConcurrentHashMapV8.Fun<? super V, ? extends U> transformer,
         ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer
      ) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.reducer = reducer;
      }

      @Override
      public final U getRawResult() {
         return this.result;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.Fun<? super V, ? extends U> transformer = this.transformer;
         if (this.transformer != null) {
            ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer = this.reducer;
            if (this.reducer != null) {
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  this.addToPendingCount(1);
                  (this.rights = new ConcurrentHashMapV8.MapReduceValuesTask<>(
                        this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, reducer
                     ))
                     .fork();
               }

               U r = null;

               ConcurrentHashMapV8.Node<K, V> p;
               while ((p = this.advance()) != null) {
                  U u;
                  if ((u = (U)transformer.apply(p.val)) != null) {
                     r = r == null ? u : reducer.apply(r, u);
                  }
               }

               this.result = r;

               for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                  ConcurrentHashMapV8.MapReduceValuesTask<K, V, U> t = (ConcurrentHashMapV8.MapReduceValuesTask<K, V, U>)c;

                  for (ConcurrentHashMapV8.MapReduceValuesTask<K, V, U> s = t.rights; s != null; s = t.rights = s.nextRight) {
                     U sr = s.result;
                     if (s.result != null) {
                        U tr = t.result;
                        t.result = t.result == null ? sr : reducer.apply(tr, sr);
                     }
                  }
               }
            }
         }
      }
   }

   static final class MapReduceValuesToDoubleTask<K, V> extends ConcurrentHashMapV8.BulkTask<K, V, Double> {
      final ConcurrentHashMapV8.ObjectToDouble<? super V> transformer;
      final ConcurrentHashMapV8.DoubleByDoubleToDouble reducer;
      final double basis;
      double result;
      ConcurrentHashMapV8.MapReduceValuesToDoubleTask<K, V> rights;
      ConcurrentHashMapV8.MapReduceValuesToDoubleTask<K, V> nextRight;

      MapReduceValuesToDoubleTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.MapReduceValuesToDoubleTask<K, V> nextRight,
         ConcurrentHashMapV8.ObjectToDouble<? super V> transformer,
         double basis,
         ConcurrentHashMapV8.DoubleByDoubleToDouble reducer
      ) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Double getRawResult() {
         return this.result;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.ObjectToDouble<? super V> transformer = this.transformer;
         if (this.transformer != null) {
            ConcurrentHashMapV8.DoubleByDoubleToDouble reducer = this.reducer;
            if (this.reducer != null) {
               double r = this.basis;
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  this.addToPendingCount(1);
                  (this.rights = new ConcurrentHashMapV8.MapReduceValuesToDoubleTask<>(
                        this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer
                     ))
                     .fork();
               }

               while ((p = this.advance()) != null) {
                  r = reducer.apply(r, transformer.apply(p.val));
               }

               this.result = r;

               for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                  ConcurrentHashMapV8.MapReduceValuesToDoubleTask<K, V> t = (ConcurrentHashMapV8.MapReduceValuesToDoubleTask<K, V>)c;

                  for (ConcurrentHashMapV8.MapReduceValuesToDoubleTask<K, V> s = t.rights; s != null; s = t.rights = s.nextRight) {
                     t.result = reducer.apply(t.result, s.result);
                  }
               }
            }
         }
      }
   }

   static final class MapReduceValuesToIntTask<K, V> extends ConcurrentHashMapV8.BulkTask<K, V, Integer> {
      final ConcurrentHashMapV8.ObjectToInt<? super V> transformer;
      final ConcurrentHashMapV8.IntByIntToInt reducer;
      final int basis;
      int result;
      ConcurrentHashMapV8.MapReduceValuesToIntTask<K, V> rights;
      ConcurrentHashMapV8.MapReduceValuesToIntTask<K, V> nextRight;

      MapReduceValuesToIntTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.MapReduceValuesToIntTask<K, V> nextRight,
         ConcurrentHashMapV8.ObjectToInt<? super V> transformer,
         int basis,
         ConcurrentHashMapV8.IntByIntToInt reducer
      ) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Integer getRawResult() {
         return this.result;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.ObjectToInt<? super V> transformer = this.transformer;
         if (this.transformer != null) {
            ConcurrentHashMapV8.IntByIntToInt reducer = this.reducer;
            if (this.reducer != null) {
               int r = this.basis;
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  this.addToPendingCount(1);
                  (this.rights = new ConcurrentHashMapV8.MapReduceValuesToIntTask<>(
                        this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer
                     ))
                     .fork();
               }

               while ((p = this.advance()) != null) {
                  r = reducer.apply(r, transformer.apply(p.val));
               }

               this.result = r;

               for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                  ConcurrentHashMapV8.MapReduceValuesToIntTask<K, V> t = (ConcurrentHashMapV8.MapReduceValuesToIntTask<K, V>)c;

                  for (ConcurrentHashMapV8.MapReduceValuesToIntTask<K, V> s = t.rights; s != null; s = t.rights = s.nextRight) {
                     t.result = reducer.apply(t.result, s.result);
                  }
               }
            }
         }
      }
   }

   static final class MapReduceValuesToLongTask<K, V> extends ConcurrentHashMapV8.BulkTask<K, V, Long> {
      final ConcurrentHashMapV8.ObjectToLong<? super V> transformer;
      final ConcurrentHashMapV8.LongByLongToLong reducer;
      final long basis;
      long result;
      ConcurrentHashMapV8.MapReduceValuesToLongTask<K, V> rights;
      ConcurrentHashMapV8.MapReduceValuesToLongTask<K, V> nextRight;

      MapReduceValuesToLongTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.MapReduceValuesToLongTask<K, V> nextRight,
         ConcurrentHashMapV8.ObjectToLong<? super V> transformer,
         long basis,
         ConcurrentHashMapV8.LongByLongToLong reducer
      ) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.transformer = transformer;
         this.basis = basis;
         this.reducer = reducer;
      }

      public final Long getRawResult() {
         return this.result;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.ObjectToLong<? super V> transformer = this.transformer;
         if (this.transformer != null) {
            ConcurrentHashMapV8.LongByLongToLong reducer = this.reducer;
            if (this.reducer != null) {
               long r = this.basis;
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  this.addToPendingCount(1);
                  (this.rights = new ConcurrentHashMapV8.MapReduceValuesToLongTask<>(
                        this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, transformer, r, reducer
                     ))
                     .fork();
               }

               while ((p = this.advance()) != null) {
                  r = reducer.apply(r, transformer.apply(p.val));
               }

               this.result = r;

               for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
                  ConcurrentHashMapV8.MapReduceValuesToLongTask<K, V> t = (ConcurrentHashMapV8.MapReduceValuesToLongTask<K, V>)c;

                  for (ConcurrentHashMapV8.MapReduceValuesToLongTask<K, V> s = t.rights; s != null; s = t.rights = s.nextRight) {
                     t.result = reducer.apply(t.result, s.result);
                  }
               }
            }
         }
      }
   }

   static class Node<K, V> implements Entry<K, V> {
      final int hash;
      final K key;
      volatile V val;
      volatile ConcurrentHashMapV8.Node<K, V> next;

      Node(int hash, K key, V val, ConcurrentHashMapV8.Node<K, V> next) {
         this.hash = hash;
         this.key = key;
         this.val = val;
         this.next = next;
      }

      @Override
      public final K getKey() {
         return this.key;
      }

      @Override
      public final V getValue() {
         return this.val;
      }

      @Override
      public final int hashCode() {
         return this.key.hashCode() ^ this.val.hashCode();
      }

      @Override
      public final String toString() {
         return this.key + "=" + this.val;
      }

      @Override
      public final V setValue(V value) {
         throw new UnsupportedOperationException();
      }

      @Override
      public final boolean equals(Object o) {
         Object k;
         Object v;
         Entry<?, ?> e;
         if (o instanceof Entry && (k = (e = (Entry<?, ?>)o).getKey()) != null && (v = e.getValue()) != null && (k == this.key || k.equals(this.key))) {
            Object u = this.val;
            if (v == this.val || v.equals(u)) {
               return true;
            }
         }

         return false;
      }

      ConcurrentHashMapV8.Node<K, V> find(int h, Object k) {
         ConcurrentHashMapV8.Node<K, V> e = this;
         if (k != null) {
            do {
               if (e.hash == h) {
                  K ek = e.key;
                  if (e.key == k || ek != null && k.equals(ek)) {
                     return e;
                  }
               }
            } while ((e = e.next) != null);
         }

         return null;
      }
   }

   public interface ObjectByObjectToDouble<A, B> {
      double apply(A var1, B var2);
   }

   public interface ObjectByObjectToInt<A, B> {
      int apply(A var1, B var2);
   }

   public interface ObjectByObjectToLong<A, B> {
      long apply(A var1, B var2);
   }

   public interface ObjectToDouble<A> {
      double apply(A var1);
   }

   public interface ObjectToInt<A> {
      int apply(A var1);
   }

   public interface ObjectToLong<A> {
      long apply(A var1);
   }

   static final class ReduceEntriesTask<K, V> extends ConcurrentHashMapV8.BulkTask<K, V, Entry<K, V>> {
      final ConcurrentHashMapV8.BiFun<Entry<K, V>, Entry<K, V>, ? extends Entry<K, V>> reducer;
      Entry<K, V> result;
      ConcurrentHashMapV8.ReduceEntriesTask<K, V> rights;
      ConcurrentHashMapV8.ReduceEntriesTask<K, V> nextRight;

      ReduceEntriesTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.ReduceEntriesTask<K, V> nextRight,
         ConcurrentHashMapV8.BiFun<Entry<K, V>, Entry<K, V>, ? extends Entry<K, V>> reducer
      ) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.reducer = reducer;
      }

      public final Entry<K, V> getRawResult() {
         return this.result;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.BiFun<Entry<K, V>, Entry<K, V>, ? extends Entry<K, V>> reducer = this.reducer;
         if (this.reducer != null) {
            int i = this.baseIndex;

            while (this.batch > 0) {
               int f = this.baseLimit;
               int h;
               if ((h = this.baseLimit + i >>> 1) <= i) {
                  break;
               }

               this.addToPendingCount(1);
               (this.rights = new ConcurrentHashMapV8.ReduceEntriesTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, reducer))
                  .fork();
            }

            Entry<K, V> r = null;

            ConcurrentHashMapV8.Node<K, V> p;
            while ((p = this.advance()) != null) {
               r = (Entry<K, V>)(r == null ? p : reducer.apply(r, p));
            }

            this.result = r;

            for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
               ConcurrentHashMapV8.ReduceEntriesTask<K, V> t = (ConcurrentHashMapV8.ReduceEntriesTask<K, V>)c;

               for (ConcurrentHashMapV8.ReduceEntriesTask<K, V> s = t.rights; s != null; s = t.rights = s.nextRight) {
                  Entry<K, V> sr = s.result;
                  if (s.result != null) {
                     Entry<K, V> tr = t.result;
                     t.result = t.result == null ? sr : reducer.apply(tr, sr);
                  }
               }
            }
         }
      }
   }

   static final class ReduceKeysTask<K, V> extends ConcurrentHashMapV8.BulkTask<K, V, K> {
      final ConcurrentHashMapV8.BiFun<? super K, ? super K, ? extends K> reducer;
      K result;
      ConcurrentHashMapV8.ReduceKeysTask<K, V> rights;
      ConcurrentHashMapV8.ReduceKeysTask<K, V> nextRight;

      ReduceKeysTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.ReduceKeysTask<K, V> nextRight,
         ConcurrentHashMapV8.BiFun<? super K, ? super K, ? extends K> reducer
      ) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.reducer = reducer;
      }

      @Override
      public final K getRawResult() {
         return this.result;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.BiFun<? super K, ? super K, ? extends K> reducer = this.reducer;
         if (this.reducer != null) {
            int i = this.baseIndex;

            while (this.batch > 0) {
               int f = this.baseLimit;
               int h;
               if ((h = this.baseLimit + i >>> 1) <= i) {
                  break;
               }

               this.addToPendingCount(1);
               (this.rights = new ConcurrentHashMapV8.ReduceKeysTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, reducer)).fork();
            }

            K r = null;

            ConcurrentHashMapV8.Node<K, V> p;
            while ((p = this.advance()) != null) {
               K u = p.key;
               r = r == null ? u : (u == null ? r : reducer.apply(r, u));
            }

            this.result = r;

            for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
               ConcurrentHashMapV8.ReduceKeysTask<K, V> t = (ConcurrentHashMapV8.ReduceKeysTask<K, V>)c;

               for (ConcurrentHashMapV8.ReduceKeysTask<K, V> s = t.rights; s != null; s = t.rights = s.nextRight) {
                  K sr = s.result;
                  if (s.result != null) {
                     K tr = t.result;
                     t.result = t.result == null ? sr : reducer.apply(tr, sr);
                  }
               }
            }
         }
      }
   }

   static final class ReduceValuesTask<K, V> extends ConcurrentHashMapV8.BulkTask<K, V, V> {
      final ConcurrentHashMapV8.BiFun<? super V, ? super V, ? extends V> reducer;
      V result;
      ConcurrentHashMapV8.ReduceValuesTask<K, V> rights;
      ConcurrentHashMapV8.ReduceValuesTask<K, V> nextRight;

      ReduceValuesTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.ReduceValuesTask<K, V> nextRight,
         ConcurrentHashMapV8.BiFun<? super V, ? super V, ? extends V> reducer
      ) {
         super(p, b, i, f, t);
         this.nextRight = nextRight;
         this.reducer = reducer;
      }

      @Override
      public final V getRawResult() {
         return this.result;
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.BiFun<? super V, ? super V, ? extends V> reducer = this.reducer;
         if (this.reducer != null) {
            int i = this.baseIndex;

            while (this.batch > 0) {
               int f = this.baseLimit;
               int h;
               if ((h = this.baseLimit + i >>> 1) <= i) {
                  break;
               }

               this.addToPendingCount(1);
               (this.rights = new ConcurrentHashMapV8.ReduceValuesTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, this.rights, reducer))
                  .fork();
            }

            V r = null;

            ConcurrentHashMapV8.Node<K, V> p;
            while ((p = this.advance()) != null) {
               V v = p.val;
               r = r == null ? v : reducer.apply(r, v);
            }

            this.result = r;

            for (CountedCompleter<?> c = this.firstComplete(); c != null; c = c.nextComplete()) {
               ConcurrentHashMapV8.ReduceValuesTask<K, V> t = (ConcurrentHashMapV8.ReduceValuesTask<K, V>)c;

               for (ConcurrentHashMapV8.ReduceValuesTask<K, V> s = t.rights; s != null; s = t.rights = s.nextRight) {
                  V sr = s.result;
                  if (s.result != null) {
                     V tr = t.result;
                     t.result = t.result == null ? sr : reducer.apply(tr, sr);
                  }
               }
            }
         }
      }
   }

   static final class ReservationNode<K, V> extends ConcurrentHashMapV8.Node<K, V> {
      ReservationNode() {
         super(-3, null, null, null);
      }

      @Override
      ConcurrentHashMapV8.Node<K, V> find(int h, Object k) {
         return null;
      }
   }

   static final class SearchEntriesTask<K, V, U> extends ConcurrentHashMapV8.BulkTask<K, V, U> {
      final ConcurrentHashMapV8.Fun<Entry<K, V>, ? extends U> searchFunction;
      final AtomicReference<U> result;

      SearchEntriesTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.Fun<Entry<K, V>, ? extends U> searchFunction,
         AtomicReference<U> result
      ) {
         super(p, b, i, f, t);
         this.searchFunction = searchFunction;
         this.result = result;
      }

      @Override
      public final U getRawResult() {
         return this.result.get();
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.Fun<Entry<K, V>, ? extends U> searchFunction = this.searchFunction;
         if (this.searchFunction != null) {
            AtomicReference<U> result = this.result;
            if (this.result != null) {
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  if (result.get() != null) {
                     return;
                  }

                  this.addToPendingCount(1);
                  new ConcurrentHashMapV8.SearchEntriesTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, searchFunction, result).fork();
               }

               while (result.get() == null) {
                  ConcurrentHashMapV8.Node<K, V> p;
                  if ((p = this.advance()) == null) {
                     this.propagateCompletion();
                     break;
                  }

                  U u;
                  if ((u = (U)searchFunction.apply(p)) != null) {
                     if (result.compareAndSet(null, u)) {
                        this.quietlyCompleteRoot();
                     }

                     return;
                  }
               }
            }
         }
      }
   }

   static final class SearchKeysTask<K, V, U> extends ConcurrentHashMapV8.BulkTask<K, V, U> {
      final ConcurrentHashMapV8.Fun<? super K, ? extends U> searchFunction;
      final AtomicReference<U> result;

      SearchKeysTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.Fun<? super K, ? extends U> searchFunction,
         AtomicReference<U> result
      ) {
         super(p, b, i, f, t);
         this.searchFunction = searchFunction;
         this.result = result;
      }

      @Override
      public final U getRawResult() {
         return this.result.get();
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.Fun<? super K, ? extends U> searchFunction = this.searchFunction;
         if (this.searchFunction != null) {
            AtomicReference<U> result = this.result;
            if (this.result != null) {
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  if (result.get() != null) {
                     return;
                  }

                  this.addToPendingCount(1);
                  new ConcurrentHashMapV8.SearchKeysTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, searchFunction, result).fork();
               }

               while (result.get() == null) {
                  ConcurrentHashMapV8.Node<K, V> p;
                  if ((p = this.advance()) == null) {
                     this.propagateCompletion();
                     break;
                  }

                  U u;
                  if ((u = (U)searchFunction.apply(p.key)) != null) {
                     if (result.compareAndSet(null, u)) {
                        this.quietlyCompleteRoot();
                     }
                     break;
                  }
               }
            }
         }
      }
   }

   static final class SearchMappingsTask<K, V, U> extends ConcurrentHashMapV8.BulkTask<K, V, U> {
      final ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> searchFunction;
      final AtomicReference<U> result;

      SearchMappingsTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> searchFunction,
         AtomicReference<U> result
      ) {
         super(p, b, i, f, t);
         this.searchFunction = searchFunction;
         this.result = result;
      }

      @Override
      public final U getRawResult() {
         return this.result.get();
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> searchFunction = this.searchFunction;
         if (this.searchFunction != null) {
            AtomicReference<U> result = this.result;
            if (this.result != null) {
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  if (result.get() != null) {
                     return;
                  }

                  this.addToPendingCount(1);
                  new ConcurrentHashMapV8.SearchMappingsTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, searchFunction, result).fork();
               }

               while (result.get() == null) {
                  ConcurrentHashMapV8.Node<K, V> p;
                  if ((p = this.advance()) == null) {
                     this.propagateCompletion();
                     break;
                  }

                  U u;
                  if ((u = (U)searchFunction.apply(p.key, p.val)) != null) {
                     if (result.compareAndSet(null, u)) {
                        this.quietlyCompleteRoot();
                     }
                     break;
                  }
               }
            }
         }
      }
   }

   static final class SearchValuesTask<K, V, U> extends ConcurrentHashMapV8.BulkTask<K, V, U> {
      final ConcurrentHashMapV8.Fun<? super V, ? extends U> searchFunction;
      final AtomicReference<U> result;

      SearchValuesTask(
         ConcurrentHashMapV8.BulkTask<K, V, ?> p,
         int b,
         int i,
         int f,
         ConcurrentHashMapV8.Node<K, V>[] t,
         ConcurrentHashMapV8.Fun<? super V, ? extends U> searchFunction,
         AtomicReference<U> result
      ) {
         super(p, b, i, f, t);
         this.searchFunction = searchFunction;
         this.result = result;
      }

      @Override
      public final U getRawResult() {
         return this.result.get();
      }

      @Override
      public final void compute() {
         ConcurrentHashMapV8.Fun<? super V, ? extends U> searchFunction = this.searchFunction;
         if (this.searchFunction != null) {
            AtomicReference<U> result = this.result;
            if (this.result != null) {
               int i = this.baseIndex;

               while (this.batch > 0) {
                  int f = this.baseLimit;
                  int h;
                  if ((h = this.baseLimit + i >>> 1) <= i) {
                     break;
                  }

                  if (result.get() != null) {
                     return;
                  }

                  this.addToPendingCount(1);
                  new ConcurrentHashMapV8.SearchValuesTask<>(this, this.batch >>>= 1, this.baseLimit = h, f, this.tab, searchFunction, result).fork();
               }

               while (result.get() == null) {
                  ConcurrentHashMapV8.Node<K, V> p;
                  if ((p = this.advance()) == null) {
                     this.propagateCompletion();
                     break;
                  }

                  U u;
                  if ((u = (U)searchFunction.apply(p.val)) != null) {
                     if (result.compareAndSet(null, u)) {
                        this.quietlyCompleteRoot();
                     }
                     break;
                  }
               }
            }
         }
      }
   }

   static class Segment<K, V> extends ReentrantLock implements Serializable {
      private static final long serialVersionUID = 2249069246763182397L;
      final float loadFactor;

      Segment(float lf) {
         this.loadFactor = lf;
      }
   }

   static class Traverser<K, V> {
      ConcurrentHashMapV8.Node<K, V>[] tab;
      ConcurrentHashMapV8.Node<K, V> next;
      int index;
      int baseIndex;
      int baseLimit;
      final int baseSize;

      Traverser(ConcurrentHashMapV8.Node<K, V>[] tab, int size, int index, int limit) {
         this.tab = tab;
         this.baseSize = size;
         this.baseIndex = this.index = index;
         this.baseLimit = limit;
         this.next = null;
      }

      final ConcurrentHashMapV8.Node<K, V> advance() {
         ConcurrentHashMapV8.Node<K, V> e = this.next;
         if (this.next != null) {
            e = e.next;
         }

         while (true) {
            if (e != null) {
               return this.next = e;
            }

            if (this.baseIndex >= this.baseLimit) {
               break;
            }

            ConcurrentHashMapV8.Node<K, V>[] t = this.tab;
            if (this.tab == null) {
               break;
            }

            int n;
            int var10000 = n = t.length;
            int i = this.index;
            if (var10000 <= this.index || i < 0) {
               break;
            }

            if ((e = ConcurrentHashMapV8.tabAt(t, this.index)) != null && e.hash < 0) {
               if (e instanceof ConcurrentHashMapV8.ForwardingNode) {
                  this.tab = ((ConcurrentHashMapV8.ForwardingNode)e).nextTable;
                  e = null;
                  continue;
               }

               if (e instanceof ConcurrentHashMapV8.TreeBin) {
                  e = ((ConcurrentHashMapV8.TreeBin)e).first;
               } else {
                  e = null;
               }
            }

            if ((this.index = this.index + this.baseSize) >= n) {
               this.index = ++this.baseIndex;
            }
         }

         return this.next = null;
      }
   }

   static final class TreeBin<K, V> extends ConcurrentHashMapV8.Node<K, V> {
      ConcurrentHashMapV8.TreeNode<K, V> root;
      volatile ConcurrentHashMapV8.TreeNode<K, V> first;
      volatile Thread waiter;
      volatile int lockState;
      static final int WRITER = 1;
      static final int WAITER = 2;
      static final int READER = 4;
      private static final Unsafe U;
      private static final long LOCKSTATE;

      TreeBin(ConcurrentHashMapV8.TreeNode<K, V> b) {
         super(-2, null, null, null);
         this.first = b;
         ConcurrentHashMapV8.TreeNode<K, V> r = null;
         ConcurrentHashMapV8.TreeNode<K, V> x = b;

         while (x != null) {
            ConcurrentHashMapV8.TreeNode<K, V> next = (ConcurrentHashMapV8.TreeNode<K, V>)x.next;
            x.left = x.right = null;
            if (r == null) {
               x.parent = null;
               x.red = false;
               r = x;
            } else {
               Object key = x.key;
               int hash = x.hash;
               Class<?> kc = null;
               ConcurrentHashMapV8.TreeNode<K, V> p = r;

               int dir;
               ConcurrentHashMapV8.TreeNode<K, V> xp;
               do {
                  int ph = p.hash;
                  if (p.hash > hash) {
                     dir = -1;
                  } else if (ph < hash) {
                     dir = 1;
                  } else if (kc == null && (kc = ConcurrentHashMapV8.comparableClassFor(key)) == null) {
                     dir = 0;
                  } else {
                     dir = ConcurrentHashMapV8.compareComparables(kc, key, p.key);
                  }

                  xp = p;
               } while ((p = dir <= 0 ? p.left : p.right) != null);

               x.parent = xp;
               if (dir <= 0) {
                  xp.left = x;
               } else {
                  xp.right = x;
               }

               r = balanceInsertion(r, x);
            }

            x = next;
         }

         this.root = r;
      }

      private final void lockRoot() {
         if (!U.compareAndSwapInt(this, LOCKSTATE, 0, 1)) {
            this.contendedLock();
         }
      }

      private final void unlockRoot() {
         this.lockState = 0;
      }

      private final void contendedLock() {
         boolean waiting = false;

         while (true) {
            int s = this.lockState;
            if ((this.lockState & 1) == 0) {
               if (U.compareAndSwapInt(this, LOCKSTATE, s, 1)) {
                  if (waiting) {
                     this.waiter = null;
                  }

                  return;
               }
            } else if ((s & 2) == 0) {
               if (U.compareAndSwapInt(this, LOCKSTATE, s, s | 2)) {
                  waiting = true;
                  this.waiter = Thread.currentThread();
               }
            } else if (waiting) {
               LockSupport.park(this);
            }
         }
      }

      @Override
      final ConcurrentHashMapV8.Node<K, V> find(int h, Object k) {
         if (k != null) {
            for (ConcurrentHashMapV8.Node<K, V> e = this.first; e != null; e = e.next) {
               int s = this.lockState;
               if ((this.lockState & 3) != 0) {
                  if (e.hash == h) {
                     K ek = e.key;
                     if (e.key == k || ek != null && k.equals(ek)) {
                        return e;
                     }
                  }
               } else if (U.compareAndSwapInt(this, LOCKSTATE, s, s + 4)) {
                  ConcurrentHashMapV8.TreeNode<K, V> p;
                  try {
                     ConcurrentHashMapV8.TreeNode<K, V> r = this.root;
                     p = this.root == null ? null : r.findTreeNode(h, k, null);
                  } finally {
                     int ls;
                     do {
                        ls = this.lockState;
                     } while (!U.compareAndSwapInt(this, LOCKSTATE, this.lockState, ls - 4));

                     if (ls == 6) {
                        Thread w = this.waiter;
                        if (this.waiter != null) {
                           LockSupport.unpark(w);
                        }
                     }
                  }

                  return p;
               }
            }
         }

         return null;
      }

      final ConcurrentHashMapV8.TreeNode<K, V> putTreeVal(int h, K k, V v) {
         Class<?> kc = null;
         ConcurrentHashMapV8.TreeNode<K, V> p = this.root;

         while (true) {
            if (p == null) {
               this.first = this.root = new ConcurrentHashMapV8.TreeNode<>(h, k, v, null, null);
            } else {
               int ph = p.hash;
               int dir;
               if (p.hash > h) {
                  dir = -1;
               } else if (ph < h) {
                  dir = 1;
               } else {
                  K pk = p.key;
                  if (p.key == k || pk != null && k.equals(pk)) {
                     return p;
                  }

                  if (kc == null && (kc = ConcurrentHashMapV8.comparableClassFor(k)) == null || (dir = ConcurrentHashMapV8.compareComparables(kc, k, pk)) == 0) {
                     if (p.left == null) {
                        dir = 1;
                     } else {
                        ConcurrentHashMapV8.TreeNode<K, V> pr = p.right;
                        ConcurrentHashMapV8.TreeNode<K, V> q;
                        if (p.right != null && (q = pr.findTreeNode(h, k, kc)) != null) {
                           return q;
                        }

                        dir = -1;
                     }
                  }
               }

               ConcurrentHashMapV8.TreeNode<K, V> xp = p;
               if ((p = dir < 0 ? p.left : p.right) != null) {
                  continue;
               }

               ConcurrentHashMapV8.TreeNode<K, V> f = this.first;
               ConcurrentHashMapV8.TreeNode<K, V> x;
               this.first = x = new ConcurrentHashMapV8.TreeNode<>(h, k, v, f, xp);
               if (f != null) {
                  f.prev = x;
               }

               if (dir < 0) {
                  xp.left = x;
               } else {
                  xp.right = x;
               }

               if (!xp.red) {
                  x.red = true;
               } else {
                  this.lockRoot();

                  try {
                     this.root = balanceInsertion(this.root, x);
                  } finally {
                     this.unlockRoot();
                  }
               }
            }

            assert checkInvariants(this.root);

            return null;
         }
      }

      final boolean removeTreeNode(ConcurrentHashMapV8.TreeNode<K, V> p) {
         ConcurrentHashMapV8.TreeNode<K, V> next = (ConcurrentHashMapV8.TreeNode<K, V>)p.next;
         ConcurrentHashMapV8.TreeNode<K, V> pred = p.prev;
         if (pred == null) {
            this.first = next;
         } else {
            pred.next = next;
         }

         if (next != null) {
            next.prev = pred;
         }

         if (this.first == null) {
            this.root = null;
            return true;
         } else {
            ConcurrentHashMapV8.TreeNode<K, V> r = this.root;
            if (this.root != null && r.right != null) {
               ConcurrentHashMapV8.TreeNode<K, V> rl = r.left;
               if (r.left != null && rl.left != null) {
                  this.lockRoot();

                  try {
                     ConcurrentHashMapV8.TreeNode<K, V> pl = p.left;
                     ConcurrentHashMapV8.TreeNode<K, V> pr = p.right;
                     ConcurrentHashMapV8.TreeNode<K, V> replacement;
                     if (pl != null && pr != null) {
                        ConcurrentHashMapV8.TreeNode<K, V> s = pr;

                        while (true) {
                           ConcurrentHashMapV8.TreeNode<K, V> sl = s.left;
                           if (s.left == null) {
                              boolean c = s.red;
                              s.red = p.red;
                              p.red = c;
                              ConcurrentHashMapV8.TreeNode<K, V> sr = s.right;
                              ConcurrentHashMapV8.TreeNode<K, V> pp = p.parent;
                              if (s == pr) {
                                 p.parent = s;
                                 s.right = p;
                              } else {
                                 ConcurrentHashMapV8.TreeNode<K, V> sp = s.parent;
                                 if ((p.parent = sp) != null) {
                                    if (s == sp.left) {
                                       sp.left = p;
                                    } else {
                                       sp.right = p;
                                    }
                                 }

                                 s.right = pr;
                                 pr.parent = s;
                              }

                              p.left = null;
                              s.left = pl;
                              pl.parent = s;
                              if ((p.right = sr) != null) {
                                 sr.parent = p;
                              }

                              if ((s.parent = pp) == null) {
                                 r = s;
                              } else if (p == pp.left) {
                                 pp.left = s;
                              } else {
                                 pp.right = s;
                              }

                              if (sr != null) {
                                 replacement = sr;
                              } else {
                                 replacement = p;
                              }
                              break;
                           }

                           s = sl;
                        }
                     } else if (pl != null) {
                        replacement = pl;
                     } else if (pr != null) {
                        replacement = pr;
                     } else {
                        replacement = p;
                     }

                     if (replacement != p) {
                        ConcurrentHashMapV8.TreeNode<K, V> ppx = replacement.parent = p.parent;
                        if (ppx == null) {
                           r = replacement;
                        } else if (p == ppx.left) {
                           ppx.left = replacement;
                        } else {
                           ppx.right = replacement;
                        }

                        p.left = p.right = p.parent = null;
                     }

                     this.root = p.red ? r : balanceDeletion(r, replacement);
                     if (p == replacement) {
                        ConcurrentHashMapV8.TreeNode<K, V> ppx = p.parent;
                        if (p.parent != null) {
                           if (p == ppx.left) {
                              ppx.left = null;
                           } else if (p == ppx.right) {
                              ppx.right = null;
                           }

                           p.parent = null;
                        }
                     }
                  } finally {
                     this.unlockRoot();
                  }

                  assert checkInvariants(this.root);

                  return false;
               }
            }

            return true;
         }
      }

      static <K, V> ConcurrentHashMapV8.TreeNode<K, V> rotateLeft(ConcurrentHashMapV8.TreeNode<K, V> root, ConcurrentHashMapV8.TreeNode<K, V> p) {
         if (p != null) {
            ConcurrentHashMapV8.TreeNode<K, V> r = p.right;
            if (p.right != null) {
               ConcurrentHashMapV8.TreeNode<K, V> rl;
               if ((rl = p.right = r.left) != null) {
                  rl.parent = p;
               }

               ConcurrentHashMapV8.TreeNode<K, V> pp;
               if ((pp = r.parent = p.parent) == null) {
                  root = r;
                  r.red = false;
               } else if (pp.left == p) {
                  pp.left = r;
               } else {
                  pp.right = r;
               }

               r.left = p;
               p.parent = r;
            }
         }

         return root;
      }

      static <K, V> ConcurrentHashMapV8.TreeNode<K, V> rotateRight(ConcurrentHashMapV8.TreeNode<K, V> root, ConcurrentHashMapV8.TreeNode<K, V> p) {
         if (p != null) {
            ConcurrentHashMapV8.TreeNode<K, V> l = p.left;
            if (p.left != null) {
               ConcurrentHashMapV8.TreeNode<K, V> lr;
               if ((lr = p.left = l.right) != null) {
                  lr.parent = p;
               }

               ConcurrentHashMapV8.TreeNode<K, V> pp;
               if ((pp = l.parent = p.parent) == null) {
                  root = l;
                  l.red = false;
               } else if (pp.right == p) {
                  pp.right = l;
               } else {
                  pp.left = l;
               }

               l.right = p;
               p.parent = l;
            }
         }

         return root;
      }

      static <K, V> ConcurrentHashMapV8.TreeNode<K, V> balanceInsertion(ConcurrentHashMapV8.TreeNode<K, V> root, ConcurrentHashMapV8.TreeNode<K, V> x) {
         x.red = true;

         while (true) {
            ConcurrentHashMapV8.TreeNode<K, V> xp = x.parent;
            if (x.parent == null) {
               x.red = false;
               return x;
            }

            if (!xp.red) {
               break;
            }

            ConcurrentHashMapV8.TreeNode<K, V> xpp = xp.parent;
            if (xp.parent == null) {
               break;
            }

            ConcurrentHashMapV8.TreeNode<K, V> xppl = xpp.left;
            if (xp == xpp.left) {
               ConcurrentHashMapV8.TreeNode<K, V> xppr = xpp.right;
               if (xpp.right != null && xppr.red) {
                  xppr.red = false;
                  xp.red = false;
                  xpp.red = true;
                  x = xpp;
               } else {
                  if (x == xp.right) {
                     x = xp;
                     root = rotateLeft(root, xp);
                     xpp = (xp = xp.parent) == null ? null : xp.parent;
                  }

                  if (xp != null) {
                     xp.red = false;
                     if (xpp != null) {
                        xpp.red = true;
                        root = rotateRight(root, xpp);
                     }
                  }
               }
            } else if (xppl != null && xppl.red) {
               xppl.red = false;
               xp.red = false;
               xpp.red = true;
               x = xpp;
            } else {
               if (x == xp.left) {
                  x = xp;
                  root = rotateRight(root, xp);
                  xpp = (xp = xp.parent) == null ? null : xp.parent;
               }

               if (xp != null) {
                  xp.red = false;
                  if (xpp != null) {
                     xpp.red = true;
                     root = rotateLeft(root, xpp);
                  }
               }
            }
         }

         return root;
      }

      static <K, V> ConcurrentHashMapV8.TreeNode<K, V> balanceDeletion(ConcurrentHashMapV8.TreeNode<K, V> root, ConcurrentHashMapV8.TreeNode<K, V> x) {
         while (x != null && x != root) {
            ConcurrentHashMapV8.TreeNode<K, V> xp = x.parent;
            if (x.parent == null) {
               x.red = false;
               return x;
            }

            if (x.red) {
               x.red = false;
               return root;
            }

            ConcurrentHashMapV8.TreeNode<K, V> xpl = xp.left;
            if (xp.left == x) {
               ConcurrentHashMapV8.TreeNode<K, V> xpr = xp.right;
               if (xp.right != null && xpr.red) {
                  xpr.red = false;
                  xp.red = true;
                  root = rotateLeft(root, xp);
                  xp = x.parent;
                  xpr = x.parent == null ? null : xp.right;
               }

               if (xpr == null) {
                  x = xp;
               } else {
                  ConcurrentHashMapV8.TreeNode<K, V> sl = xpr.left;
                  ConcurrentHashMapV8.TreeNode<K, V> sr = xpr.right;
                  if (sr != null && sr.red || sl != null && sl.red) {
                     if (sr == null || !sr.red) {
                        if (sl != null) {
                           sl.red = false;
                        }

                        xpr.red = true;
                        root = rotateRight(root, xpr);
                        xp = x.parent;
                        xpr = x.parent == null ? null : xp.right;
                     }

                     if (xpr != null) {
                        ConcurrentHashMapV8.TreeNode var13;
                        boolean var14;
                        if (xp == null) {
                           var13 = xpr;
                           var14 = false;
                        } else {
                           var13 = xpr;
                           var14 = xp.red;
                        }

                        var13.red = var14;
                        sr = xpr.right;
                        if (xpr.right != null) {
                           sr.red = false;
                        }
                     }

                     if (xp != null) {
                        xp.red = false;
                        root = rotateLeft(root, xp);
                     }

                     x = root;
                  } else {
                     xpr.red = true;
                     x = xp;
                  }
               }
            } else {
               if (xpl != null && xpl.red) {
                  xpl.red = false;
                  xp.red = true;
                  root = rotateRight(root, xp);
                  xp = x.parent;
                  xpl = x.parent == null ? null : xp.left;
               }

               if (xpl == null) {
                  x = xp;
               } else {
                  ConcurrentHashMapV8.TreeNode<K, V> sl = xpl.left;
                  ConcurrentHashMapV8.TreeNode<K, V> sr = xpl.right;
                  if (sl != null && sl.red || sr != null && sr.red) {
                     if (sl == null || !sl.red) {
                        if (sr != null) {
                           sr.red = false;
                        }

                        xpl.red = true;
                        root = rotateLeft(root, xpl);
                        xp = x.parent;
                        xpl = x.parent == null ? null : xp.left;
                     }

                     if (xpl != null) {
                        ConcurrentHashMapV8.TreeNode var10000;
                        boolean var10001;
                        if (xp == null) {
                           var10000 = xpl;
                           var10001 = false;
                        } else {
                           var10000 = xpl;
                           var10001 = xp.red;
                        }

                        var10000.red = var10001;
                        sl = xpl.left;
                        if (xpl.left != null) {
                           sl.red = false;
                        }
                     }

                     if (xp != null) {
                        xp.red = false;
                        root = rotateRight(root, xp);
                     }

                     x = root;
                  } else {
                     xpl.red = true;
                     x = xp;
                  }
               }
            }
         }

         return root;
      }

      static <K, V> boolean checkInvariants(ConcurrentHashMapV8.TreeNode<K, V> t) {
         ConcurrentHashMapV8.TreeNode<K, V> tp = t.parent;
         ConcurrentHashMapV8.TreeNode<K, V> tl = t.left;
         ConcurrentHashMapV8.TreeNode<K, V> tr = t.right;
         ConcurrentHashMapV8.TreeNode<K, V> tb = t.prev;
         ConcurrentHashMapV8.TreeNode<K, V> tn = (ConcurrentHashMapV8.TreeNode<K, V>)t.next;
         if (tb != null && tb.next != t) {
            return false;
         } else if (tn != null && tn.prev != t) {
            return false;
         } else if (tp != null && t != tp.left && t != tp.right) {
            return false;
         } else if (tl == null || tl.parent == t && tl.hash <= t.hash) {
            if (tr == null || tr.parent == t && tr.hash >= t.hash) {
               if (t.red && tl != null && tl.red && tr != null && tr.red) {
                  return false;
               } else {
                  return tl != null && !checkInvariants(tl) ? false : tr == null || checkInvariants(tr);
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }

      static {
         try {
            U = ConcurrentHashMapV8.getUnsafe();
            Class<?> k = ConcurrentHashMapV8.TreeBin.class;
            LOCKSTATE = U.objectFieldOffset(k.getDeclaredField("lockState"));
         } catch (Exception var1) {
            throw new Error(var1);
         }
      }
   }

   static final class TreeNode<K, V> extends ConcurrentHashMapV8.Node<K, V> {
      ConcurrentHashMapV8.TreeNode<K, V> parent;
      ConcurrentHashMapV8.TreeNode<K, V> left;
      ConcurrentHashMapV8.TreeNode<K, V> right;
      ConcurrentHashMapV8.TreeNode<K, V> prev;
      boolean red;

      TreeNode(int hash, K key, V val, ConcurrentHashMapV8.Node<K, V> next, ConcurrentHashMapV8.TreeNode<K, V> parent) {
         super(hash, key, val, next);
         this.parent = parent;
      }

      @Override
      ConcurrentHashMapV8.Node<K, V> find(int h, Object k) {
         return this.findTreeNode(h, k, null);
      }

      final ConcurrentHashMapV8.TreeNode<K, V> findTreeNode(int h, Object k, Class<?> kc) {
         if (k != null) {
            ConcurrentHashMapV8.TreeNode<K, V> p = this;

            do {
               ConcurrentHashMapV8.TreeNode<K, V> pl = p.left;
               ConcurrentHashMapV8.TreeNode<K, V> pr = p.right;
               int ph = p.hash;
               if (p.hash > h) {
                  p = pl;
               } else if (ph < h) {
                  p = pr;
               } else {
                  K pk = p.key;
                  if (p.key == k || pk != null && k.equals(pk)) {
                     return p;
                  }

                  if (pl == null && pr == null) {
                     break;
                  }

                  int dir;
                  if ((kc != null || (kc = ConcurrentHashMapV8.comparableClassFor(k)) != null)
                     && (dir = ConcurrentHashMapV8.compareComparables(kc, k, pk)) != 0) {
                     p = dir < 0 ? pl : pr;
                  } else if (pl == null) {
                     p = pr;
                  } else {
                     ConcurrentHashMapV8.TreeNode<K, V> q;
                     if (pr != null && (q = pr.findTreeNode(h, k, kc)) != null) {
                        return q;
                     }

                     p = pl;
                  }
               }
            } while (p != null);
         }

         return null;
      }
   }

   static final class ValueIterator<K, V> extends ConcurrentHashMapV8.BaseIterator<K, V> implements Iterator<V>, Enumeration<V> {
      ValueIterator(ConcurrentHashMapV8.Node<K, V>[] tab, int index, int size, int limit, ConcurrentHashMapV8<K, V> map) {
         super(tab, index, size, limit, map);
      }

      @Override
      public final V next() {
         ConcurrentHashMapV8.Node<K, V> p = this.next;
         if (this.next == null) {
            throw new NoSuchElementException();
         } else {
            V v = p.val;
            this.lastReturned = p;
            this.advance();
            return v;
         }
      }

      @Override
      public final V nextElement() {
         return this.next();
      }
   }

   static final class ValueSpliterator<K, V> extends ConcurrentHashMapV8.Traverser<K, V> implements ConcurrentHashMapV8.ConcurrentHashMapSpliterator<V> {
      long est;

      ValueSpliterator(ConcurrentHashMapV8.Node<K, V>[] tab, int size, int index, int limit, long est) {
         super(tab, size, index, limit);
         this.est = est;
      }

      @Override
      public ConcurrentHashMapV8.ConcurrentHashMapSpliterator<V> trySplit() {
         int i = this.baseIndex;
         int f = this.baseLimit;
         int h;
         return (h = this.baseIndex + this.baseLimit >>> 1) <= i
            ? null
            : new ConcurrentHashMapV8.ValueSpliterator<>(this.tab, this.baseSize, this.baseLimit = h, f, this.est >>>= 1);
      }

      @Override
      public void forEachRemaining(ConcurrentHashMapV8.Action<? super V> action) {
         if (action == null) {
            throw new NullPointerException();
         } else {
            ConcurrentHashMapV8.Node<K, V> p;
            while ((p = this.advance()) != null) {
               action.apply(p.val);
            }
         }
      }

      @Override
      public boolean tryAdvance(ConcurrentHashMapV8.Action<? super V> action) {
         if (action == null) {
            throw new NullPointerException();
         } else {
            ConcurrentHashMapV8.Node<K, V> p;
            if ((p = this.advance()) == null) {
               return false;
            } else {
               action.apply(p.val);
               return true;
            }
         }
      }

      @Override
      public long estimateSize() {
         return this.est;
      }
   }

   static final class ValuesView<K, V> extends ConcurrentHashMapV8.CollectionView<K, V, V> implements Collection<V>, Serializable {
      private static final long serialVersionUID = 2249069246763182397L;

      ValuesView(ConcurrentHashMapV8<K, V> map) {
         super(map);
      }

      @Override
      public final boolean contains(Object o) {
         return this.map.containsValue(o);
      }

      @Override
      public final boolean remove(Object o) {
         if (o != null) {
            Iterator<V> it = this.iterator();

            while (it.hasNext()) {
               if (o.equals(it.next())) {
                  it.remove();
                  return true;
               }
            }
         }

         return false;
      }

      @Override
      public final Iterator<V> iterator() {
         ConcurrentHashMapV8<K, V> m = this.map;
         ConcurrentHashMapV8.Node<K, V>[] t = m.table;
         int f = m.table == null ? 0 : t.length;
         return new ConcurrentHashMapV8.ValueIterator<>(t, f, 0, f, m);
      }

      @Override
      public final boolean add(V e) {
         throw new UnsupportedOperationException();
      }

      @Override
      public final boolean addAll(Collection<? extends V> c) {
         throw new UnsupportedOperationException();
      }

      public ConcurrentHashMapV8.ConcurrentHashMapSpliterator<V> spliterator166() {
         ConcurrentHashMapV8<K, V> m = this.map;
         long n = m.sumCount();
         ConcurrentHashMapV8.Node<K, V>[] t = m.table;
         int f = m.table == null ? 0 : t.length;
         return new ConcurrentHashMapV8.ValueSpliterator<>(t, f, 0, f, n < 0L ? 0L : n);
      }

      public void forEach(ConcurrentHashMapV8.Action<? super V> action) {
         if (action == null) {
            throw new NullPointerException();
         } else {
            ConcurrentHashMapV8.Node<K, V>[] t = this.map.table;
            if (this.map.table != null) {
               ConcurrentHashMapV8.Traverser<K, V> it = new ConcurrentHashMapV8.Traverser<>(t, t.length, 0, t.length);

               ConcurrentHashMapV8.Node<K, V> p;
               while ((p = it.advance()) != null) {
                  action.apply(p.val);
               }
            }
         }
      }
   }
}
