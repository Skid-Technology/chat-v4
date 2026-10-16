package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
@Beta
public final class Enums {
   @GwtIncompatible("java.lang.ref.WeakReference")
   private static final Map<Class<? extends Enum<?>>, Map<String, WeakReference<? extends Enum<?>>>> enumConstantCache = new WeakHashMap<>();

   private Enums() {
   }

   @GwtIncompatible("reflection")
   public static Field getField(Enum<?> enumValue) {
      Class<?> clazz = enumValue.getDeclaringClass();

      try {
         return clazz.getDeclaredField(enumValue.name());
      } catch (NoSuchFieldException var3) {
         throw new AssertionError(var3);
      }
   }

   @Deprecated
   public static <T extends Enum<T>> Function<String, T> valueOfFunction(Class<T> enumClass) {
      return new Enums.ValueOfFunction<>(enumClass);
   }

   public static <T extends Enum<T>> Optional<T> getIfPresent(Class<T> enumClass, String value) {
      Preconditions.checkNotNull(enumClass);
      Preconditions.checkNotNull(value);
      return Platform.getEnumIfPresent(enumClass, value);
   }

   @GwtIncompatible("java.lang.ref.WeakReference")
   private static <T extends Enum<T>> Map<String, WeakReference<? extends Enum<?>>> populateCache(Class<T> enumClass) {
      Map<String, WeakReference<? extends Enum<?>>> result = new HashMap<>();

      for (T enumInstance : EnumSet.allOf(enumClass)) {
         result.put(enumInstance.name(), new WeakReference<>(enumInstance));
      }

      enumConstantCache.put(enumClass, result);
      return result;
   }

   @GwtIncompatible("java.lang.ref.WeakReference")
   static <T extends Enum<T>> Map<String, WeakReference<? extends Enum<?>>> getEnumConstants(Class<T> enumClass) {
      synchronized (enumConstantCache) {
         Map<String, WeakReference<? extends Enum<?>>> constants = enumConstantCache.get(enumClass);
         if (constants == null) {
            constants = populateCache(enumClass);
         }

         return constants;
      }
   }

   public static <T extends Enum<T>> Converter<String, T> stringConverter(Class<T> enumClass) {
      return new Enums.StringConverter<>(enumClass);
   }

   private static final class StringConverter<T extends Enum<T>> extends Converter<String, T> implements Serializable {
      private final Class<T> enumClass;
      private static final long serialVersionUID = 0L;

      StringConverter(Class<T> enumClass) {
         this.enumClass = Preconditions.checkNotNull(enumClass);
      }

      protected T doForward(String value) {
         return Enum.valueOf(this.enumClass, value);
      }

      protected String doBackward(T enumValue) {
         return enumValue.name();
      }

      @Override
      public boolean equals(@Nullable Object object) {
         if (object instanceof Enums.StringConverter) {
            Enums.StringConverter<?> that = (Enums.StringConverter<?>)object;
            return this.enumClass.equals(that.enumClass);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return this.enumClass.hashCode();
      }

      @Override
      public String toString() {
         return "Enums.stringConverter(" + this.enumClass.getName() + ".class)";
      }
   }

   private static final class ValueOfFunction<T extends Enum<T>> implements Function<String, T>, Serializable {
      private final Class<T> enumClass;
      private static final long serialVersionUID = 0L;

      private ValueOfFunction(Class<T> enumClass) {
         this.enumClass = Preconditions.checkNotNull(enumClass);
      }

      public T apply(String value) {
         try {
            return Enum.valueOf(this.enumClass, value);
         } catch (IllegalArgumentException var3) {
            return null;
         }
      }

      @Override
      public boolean equals(@Nullable Object obj) {
         return obj instanceof Enums.ValueOfFunction && this.enumClass.equals(((Enums.ValueOfFunction)obj).enumClass);
      }

      @Override
      public int hashCode() {
         return this.enumClass.hashCode();
      }

      @Override
      public String toString() {
         return "Enums.valueOf(" + this.enumClass + ")";
      }
   }
}
