package gg.vape.reflect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

   private static List<String> parseParameters(String descriptorParams) {
      List<String> parameters = new ArrayList<>();
      int index = 0;
      int length = descriptorParams.length();

      while (index < length) {
         int start = index;

         while (index < length && descriptorParams.charAt(index) == '[') {
            index++;
         }

         if (index >= length) {
            break;
         }

         char typeChar = descriptorParams.charAt(index++);
         if (typeChar == 'L') {
            int semicolon = descriptorParams.indexOf(59, index);
            if (semicolon == -1) {
               break;
            }

            index = semicolon + 1;
         }

         parameters.add(descriptorParams.substring(start, index));
      }

      return parameters;
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
