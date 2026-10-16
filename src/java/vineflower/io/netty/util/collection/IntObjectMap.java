package io.netty.util.collection;

public interface IntObjectMap<V> {
   V get(int var1);

   V put(int var1, V var2);

   void putAll(IntObjectMap<V> var1);

   V remove(int var1);

   int size();

   boolean isEmpty();

   void clear();

   boolean containsKey(int var1);

   boolean containsValue(V var1);

   Iterable<IntObjectMap.Entry<V>> entries();

   int[] keys();

   V[] values(Class<V> var1);

   public interface Entry<V> {
      int key();

      V value();

      void setValue(V var1);
   }
}
