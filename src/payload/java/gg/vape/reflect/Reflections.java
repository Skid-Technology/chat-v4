package gg.vape.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Reflections {
   public static final Map<Integer, Method> METHODS_BY_ID = new HashMap<>();
   public static final Map<Integer, Field> FIELDS_BY_ID = new HashMap<>();
   public static final Map<Integer, Constructor<?>> CONSTRUCTORS_BY_ID = new HashMap<>();
   private static final Set<Integer> REPORTED_INVOCATION_FAILURES = Collections.newSetFromMap(new ConcurrentHashMap<>());

   public static void getMethod(int id, Class<?> owner, String name, String descriptor, boolean remap, boolean isStatic) throws NoSuchMethodException {
      if (name.equals("<init>")) {
         getConstructor(id, owner, descriptor);
      } else if (!name.equals("ordinal") && !name.equals("values")) {
         boolean skipParameterCheck = name.equals("getEnchantmentModifierDamage");

         for (Method method : owner.getDeclaredMethods()) {
            if (MappingRegistry.matches(method, name)
               && (ParameterResolver.matchesDescriptor(method.getParameterTypes(), method.getReturnType(), descriptor) || skipParameterCheck)) {
               method.setAccessible(true);
               METHODS_BY_ID.put(id, method);
               return;
            }
         }

         for (Method methodx : owner.getMethods()) {
            if (methodx.getDeclaringClass() != owner
               && MappingRegistry.matches(methodx, name)
               && (ParameterResolver.matchesDescriptor(methodx.getParameterTypes(), methodx.getReturnType(), descriptor) || skipParameterCheck)) {
               methodx.setAccessible(true);
               METHODS_BY_ID.put(id, methodx);
               return;
            }
         }

         throw new NoSuchMethodException();
      } else {
         for (Method methodxx : getAllMethods(owner)) {
            if (methodxx.getName().equals(name)) {
               METHODS_BY_ID.put(id, methodxx);
            }
         }
      }
   }

   public static void getMinecraftMethod(int id, Class<?> owner, String name, String descriptor, boolean remap) throws NoSuchMethodException {
      getMethod(id, owner, name, descriptor, remap, false);
   }

   public static void getMethod(int id, Class<?> owner, String name, String descriptor, boolean remap) throws NoSuchMethodException {
      getMethod(id, owner, name, descriptor, remap, true);
   }

   public static Method[] getAllMethods(Class<?> clazz) {
      ArrayList<Method> methods = new ArrayList<>();
      ArrayList hierarchy = new ArrayList();
      Class<?> current = clazz;

      do {
         hierarchy.add(current);
      } while ((current = current.getSuperclass()) != Object.class && current != null);

      for (int index = hierarchy.size() - 1; index >= 0; index--) {
         for (Method method : ((Class)hierarchy.get(index)).getDeclaredMethods()) {
            methods.add(method);
         }
      }

      return methods.toArray(new Method[0]);
   }

   public static void getConstructor(int id, Class<?> owner, String descriptor) {
      for (Constructor<?> constructor : owner.getDeclaredConstructors()) {
         if (ParameterResolver.matchesDescriptor(constructor.getParameterTypes(), void.class, descriptor)) {
            constructor.setAccessible(true);
            CONSTRUCTORS_BY_ID.put(id, constructor);
            return;
         }
      }
   }

   public static void invoke(int id, Object instance, Object... params) {
      invokeMethod(id, instance, void.class, params);
   }

   public static boolean invokeMethodBoolean(int id, Object instance, Object... params) {
      return invokeMethod(id, instance, boolean.class, params);
   }

   public static char invokeMethodChar(int id, Object instance, Object... params) {
      return invokeMethod(id, instance, char.class, params);
   }

   public static short invokeMethodShort(int id, Object instance, Object... params) {
      return invokeMethod(id, instance, short.class, params);
   }

   public static int invokeMethodInt(int id, Object instance, Object... params) {
      return invokeMethod(id, instance, int.class, params);
   }

   public static long invokeMethodLong(int id, Object instance, Object... params) {
      return invokeMethod(id, instance, long.class, params);
   }

   public static float invokeMethodFloat(int id, Object instance, Object... params) {
      return invokeMethod(id, instance, float.class, params);
   }

   public static double invokeMethodDouble(int id, Object instance, Object... params) {
      return invokeMethod(id, instance, double.class, params);
   }

   public static Object invokeMethodObject(int id, Object instance, Object... params) {
      return invokeMethod(id, instance, Object.class, params);
   }

   public static boolean[] invokeMethodBooleanArray(int id, Object instance, Object... params) {
      return invokeMethod(id, instance, boolean[].class, params);
   }

   public static char[] invokeMethodCharArray(int id, Object instance, Object... params) {
      return invokeMethod(id, instance, char[].class, params);
   }

   public static short[] invokeMethodShortArray(int id, Object instance, Object... params) {
      return invokeMethod(id, instance, short[].class, params);
   }

   public static int[] invokeMethodIntArray(int id, Object instance, Object... params) {
      return invokeMethod(id, instance, int[].class, params);
   }

   public static long[] invokeMethodLongArray(int id, Object instance, Object... params) {
      return invokeMethod(id, instance, long[].class, params);
   }

   public static float[] invokeMethodFloatArray(int id, Object instance, Object... params) {
      return invokeMethod(id, instance, float[].class, params);
   }

   public static double[] invokeMethodDoubleArray(int id, Object instance, Object... params) {
      return invokeMethod(id, instance, double[].class, params);
   }

   public static Object[] invokeMethodObjectArray(int id, Object instance, Object... params) {
      return invokeMethod(id, instance, Object[].class, params);
   }

   public static <T> T invokeMethod(int id, Object instance, Class<T> resultType, Object... arguments) {
      try {
         Method method = METHODS_BY_ID.get(id);
         if (method == null) {
            throw new IllegalStateException("Method mapping was not registered: id=" + id);
         } else {
            return (T)method.invoke(instance, arguments);
         }
      } catch (Throwable var5) {
         if (REPORTED_INVOCATION_FAILURES.add(id)) {
            System.out.println("Failed invoke method: " + id + "," + resultType.getName() + "," + instance + "," + Arrays.toString(arguments));
            var5.printStackTrace();
         }

         return null;
      }
   }

   public static void getMinecraftField(int id, Class<?> owner, String name, String descriptor, boolean remap) throws NoSuchFieldException {
      getField(id, owner, name, descriptor, remap, false);
   }

   public static void getField(int id, Class<?> owner, String name, String descriptor, boolean remap) throws NoSuchFieldException {
      getField(id, owner, name, descriptor, remap, true);
   }

   public static void getField(int id, Class<?> owner, String name, String descriptor, boolean remap, boolean isStatic) throws NoSuchFieldException {
      for (Field field : owner.getDeclaredFields()) {
         if (MappingRegistry.matches(field, name)) {
            field.setAccessible(true);
            FIELDS_BY_ID.put(id, field);
            return;
         }
      }

      System.out.println("Failed allocate field: " + id + " " + name + " IN " + owner.getName());
      throw new NoSuchFieldException();
   }

   public static boolean getFieldBoolean(int id, Object instance) {
      try {
         return field(id).getBoolean(instance);
      } catch (Throwable var3) {
         throw sneakyThrow(var3);
      }
   }

   public static char getFieldChar(int id, Object instance) {
      try {
         return field(id).getChar(instance);
      } catch (Throwable var3) {
         throw sneakyThrow(var3);
      }
   }

   public static short getFieldShort(int id, Object instance) {
      try {
         return field(id).getShort(instance);
      } catch (Throwable var3) {
         throw sneakyThrow(var3);
      }
   }

   public static int getFieldInt(int id, Object instance) {
      try {
         return field(id).getInt(instance);
      } catch (Throwable var3) {
         throw sneakyThrow(var3);
      }
   }

   public static long getFieldLong(int id, Object instance) {
      try {
         return field(id).getLong(instance);
      } catch (Throwable var3) {
         throw sneakyThrow(var3);
      }
   }

   public static float getFieldFloat(int id, Object instance) {
      try {
         Field field = field(id);
         return field.getType() == float.class ? field.getFloat(instance) : (float)field.getDouble(instance);
      } catch (Throwable var3) {
         throw sneakyThrow(var3);
      }
   }

   public static double getFieldDouble(int id, Object instance) {
      try {
         return field(id).getDouble(instance);
      } catch (Throwable var3) {
         throw sneakyThrow(var3);
      }
   }

   public static Object getFieldObject(int id, Object instance) {
      return getField(id, instance);
   }

   public static boolean[] getFieldBooleanArray(int id, Object instance) {
      return getField(id, instance);
   }

   public static char[] getFieldCharArray(int id, Object instance) {
      return getField(id, instance);
   }

   public static short[] getFieldShortArray(int id, Object instance) {
      return getField(id, instance);
   }

   public static int[] getFieldIntArray(int id, Object instance) {
      return getField(id, instance);
   }

   public static long[] getFieldLongArray(int id, Object instance) {
      return getField(id, instance);
   }

   public static float[] getFieldFloatArray(int id, Object instance) {
      return getField(id, instance);
   }

   public static double[] getFieldDoubleArray(int id, Object instance) {
      return getField(id, instance);
   }

   public static Object[] getFieldObjectArray(int id, Object instance) {
      return getField(id, instance);
   }

   public static <T> T getField(int id, Object instance) {
      try {
         return (T)field(id).get(instance);
      } catch (Exception var3) {
         System.err.println("Failed allocate field: " + id + " IN " + instance + " " + FIELDS_BY_ID.size());
         var3.printStackTrace();
         return null;
      }
   }

   public static void setFieldBoolean(int id, Object instance, boolean value) {
      try {
         field(id).setBoolean(instance, value);
      } catch (Throwable var4) {
         throw sneakyThrow(var4);
      }
   }

   public static void setFieldChar(int id, Object instance, char value) {
      try {
         field(id).setChar(instance, value);
      } catch (Throwable var4) {
         throw sneakyThrow(var4);
      }
   }

   public static void setFieldShort(int id, Object instance, short value) {
      try {
         field(id).setShort(instance, value);
      } catch (Throwable var4) {
         throw sneakyThrow(var4);
      }
   }

   public static void setFieldInt(int id, Object instance, int value) {
      try {
         field(id).setInt(instance, value);
      } catch (Throwable var4) {
         throw sneakyThrow(var4);
      }
   }

   public static void setFieldLong(int id, Object instance, long value) {
      try {
         field(id).setLong(instance, value);
      } catch (Throwable var5) {
         throw sneakyThrow(var5);
      }
   }

   public static void setFieldFloat(int id, Object instance, float value) {
      try {
         field(id).setFloat(instance, value);
      } catch (Throwable var4) {
         throw sneakyThrow(var4);
      }
   }

   public static void setFieldDouble(int id, Object instance, double value) {
      try {
         field(id).setDouble(instance, value);
      } catch (Throwable var5) {
         throw sneakyThrow(var5);
      }
   }

   public static void setFieldObject(int id, Object instance, Object value) {
      setObjectField(id, instance, value);
   }

   public static void setFieldBooleanArray(int id, Object instance, boolean[] value) {
      setObjectField(id, instance, value);
   }

   public static void setFieldCharArray(int id, Object instance, char[] value) {
      setObjectField(id, instance, value);
   }

   public static void setFieldShortArray(int id, Object instance, short[] value) {
      setObjectField(id, instance, value);
   }

   public static void setFieldIntArray(int id, Object instance, int[] value) {
      setObjectField(id, instance, value);
   }

   public static void setFieldLongArray(int id, Object instance, long[] value) {
      setObjectField(id, instance, value);
   }

   public static void setFieldFloatArray(int id, Object instance, float[] value) {
      setObjectField(id, instance, value);
   }

   public static void setFieldDoubleArray(int id, Object instance, double[] value) {
      setObjectField(id, instance, value);
   }

   public static void setFieldObjectArray(int id, Object instance, Object[] value) {
      setObjectField(id, instance, value);
   }

   public static void setObjectField(int id, Object instance, Object value) {
      try {
         field(id).set(instance, value);
      } catch (Throwable var4) {
         throw sneakyThrow(var4);
      }
   }

   public static void invokeMethodVoid(int id, Object instance, Object... params) {
      invokeMethod(id, instance, void.class, params);
   }

   public static Object create(int id, Class<?> cls, Object... params) {
      return invokeConstructor(id, params);
   }

   public static <T> T invokeConstructor(int id, Object... params) {
      try {
         return (T)CONSTRUCTORS_BY_ID.get(id).newInstance(params);
      } catch (Throwable var3) {
         throw sneakyThrow(var3);
      }
   }

   public static byte invokeMethodByte(int id, Object instance, Object... params) {
      return invokeMethod(id, instance, byte.class, params);
   }

   public static String getFieldName(int id) {
      return field(id).getName();
   }

   public static String getMethodName(int id) {
      return METHODS_BY_ID.get(id).getName();
   }

   public static Object invokeMethodObjects(int id, Object instance, Object... params) {
      return invokeMethod(id, instance, Object.class, params);
   }

   private static Field field(int id) {
      return FIELDS_BY_ID.get(id);
   }

   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable failure) throws T {
      throw (T)failure;
   }
}
