package org.lwjgl.opencl;

import org.lwjgl.LWJGLUtil;

class CLObjectRegistry<T extends CLObjectChild> {
   private FastLongMap<T> registry;

   final boolean isEmpty() {
      return this.registry == null || this.registry.isEmpty();
   }

   final T getObject(long id) {
      return this.registry == null ? null : this.registry.get(id);
   }

   final boolean hasObject(long id) {
      return this.registry != null && this.registry.containsKey(id);
   }

   final Iterable<FastLongMap.Entry<T>> getAll() {
      return this.registry;
   }

   void registerObject(T object) {
      FastLongMap<T> map = this.getMap();
      Long key = object.getPointer();
      if (LWJGLUtil.DEBUG && map.containsKey(key)) {
         throw new IllegalStateException("Duplicate object found: " + object.getClass() + " - " + key);
      } else {
         this.getMap().put(object.getPointer(), object);
      }
   }

   void unregisterObject(T object) {
      this.getMap().remove(object.getPointerUnsafe());
   }

   private FastLongMap<T> getMap() {
      if (this.registry == null) {
         this.registry = new FastLongMap<>();
      }

      return this.registry;
   }
}
