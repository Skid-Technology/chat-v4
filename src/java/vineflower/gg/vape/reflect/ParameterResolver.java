package gg.vape.reflect;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ParameterResolver {
   private static final Map<Class<?>, String> PRIMITIVE_DESCRIPTORS;

   private ParameterResolver() {
   }

   public static String descriptorFor(Class<?> type) {
      String primitiveDescriptor = PRIMITIVE_DESCRIPTORS.get(type);
      if (primitiveDescriptor != null) {
         return primitiveDescriptor;
      } else {
         return type.isArray() ? type.getName().replace('.', '/') : 'L' + type.getName().replace('.', '/') + ';';
      }
   }

   public static String methodDescriptor(Class<?>[] parameterTypes, Class<?> returnType) {
      StringBuilder descriptor = new StringBuilder();
      descriptor.append('(');

      for (Class<?> parameterType : parameterTypes) {
         descriptor.append(descriptorFor(parameterType));
      }

      descriptor.append(')').append(descriptorFor(returnType));
      return descriptor.toString();
   }

   public static boolean matchesDescriptor(Class<?>[] parameterTypes, Class<?> returnType, String descriptor) {
      return methodDescriptor(parameterTypes, returnType).equals(descriptor);
   }

   static {
      HashMap<Class<?>, String> descriptors = new HashMap<>();
      descriptors.put(boolean.class, "Z");
      descriptors.put(byte.class, "B");
      descriptors.put(char.class, "C");
      descriptors.put(short.class, "S");
      descriptors.put(int.class, "I");
      descriptors.put(long.class, "J");
      descriptors.put(float.class, "F");
      descriptors.put(double.class, "D");
      descriptors.put(void.class, "V");
      PRIMITIVE_DESCRIPTORS = Collections.unmodifiableMap(descriptors);
   }
}
