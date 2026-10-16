package com.google.common.collect;

import com.google.common.primitives.Primitives;
import java.util.HashMap;
import java.util.Map;

public final class MutableClassToInstanceMap<B> extends MapConstraints.ConstrainedMap<Class<? extends B>, B> implements ClassToInstanceMap<B> {
   private static final MapConstraint<Class<?>, Object> VALUE_CAN_BE_CAST_TO_KEY = new MapConstraint<Class<?>, Object>() {
      public void checkKeyValue(Class<?> key, Object value) {
         MutableClassToInstanceMap.cast(key, value);
      }
   };
   private static final long serialVersionUID = 0L;

   public static <B> MutableClassToInstanceMap<B> create() {
      return new MutableClassToInstanceMap<>(new HashMap<>());
   }

   public static <B> MutableClassToInstanceMap<B> create(Map<Class<? extends B>, B> backingMap) {
      return new MutableClassToInstanceMap<>(backingMap);
   }

   private MutableClassToInstanceMap(Map<Class<? extends B>, B> delegate) {
      super(delegate, VALUE_CAN_BE_CAST_TO_KEY);
   }

   @Override
   public <T extends B> T putInstance(Class<T> type, T value) {
      return cast(type, this.put(type, (B)value));
   }

   @Override
   public <T extends B> T getInstance(Class<T> type) {
      return cast(type, this.get(type));
   }

   private static <B, T extends B> T cast(Class<T> type, B value) {
      return Primitives.wrap(type).cast(value);
   }
}
