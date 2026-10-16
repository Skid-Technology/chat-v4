package gg.vape.shaded.javassist.runtime;

public class Desc {
   public static boolean useContextClassLoader = false;
   private static final ThreadLocal<Boolean> USE_CONTEXT_CLASS_LOADER_LOCALLY = new ThreadLocal<Boolean>() {
      protected Boolean initialValue() {
         return false;
      }
   };

   public static void setUseContextClassLoaderLocally() {
      USE_CONTEXT_CLASS_LOADER_LOCALLY.set(true);
   }

   public static void resetUseContextClassLoaderLocally() {
      USE_CONTEXT_CLASS_LOADER_LOCALLY.remove();
   }

   private static Class<?> getClassObject(String name) throws ClassNotFoundException {
      return !useContextClassLoader && !USE_CONTEXT_CLASS_LOADER_LOCALLY.get()
         ? Class.forName(name)
         : Class.forName(name, true, Thread.currentThread().getContextClassLoader());
   }

   public static Class<?> getClazz(String name) {
      try {
         return getClassObject(name);
      } catch (ClassNotFoundException var2) {
         throw new RuntimeException(
            "$class: internal error, could not find class '" + name + "' (Desc.useContextClassLoader: " + Boolean.toString(useContextClassLoader) + ")", var2
         );
      }
   }

   public static Class<?>[] getParams(String desc) {
      if (desc.charAt(0) != '(') {
         throw new RuntimeException("$sig: internal error");
      } else {
         return getType(desc, desc.length(), 1, 0);
      }
   }

   public static Class<?> getType(String desc) {
      Class<?>[] result = getType(desc, desc.length(), 0, 0);
      if (result != null && result.length == 1) {
         return result[0];
      } else {
         throw new RuntimeException("$type: internal error");
      }
   }

   private static Class<?>[] getType(String desc, int descLen, int start, int num) {
      if (start >= descLen) {
         return new Class[num];
      } else {
         char c = desc.charAt(start);
         Class<?> clazz;
         switch (c) {
            case 'B':
               clazz = byte.class;
               break;
            case 'C':
               clazz = char.class;
               break;
            case 'D':
               clazz = double.class;
               break;
            case 'E':
            case 'G':
            case 'H':
            case 'K':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'T':
            case 'U':
            case 'W':
            case 'X':
            case 'Y':
            default:
               return new Class[num];
            case 'F':
               clazz = float.class;
               break;
            case 'I':
               clazz = int.class;
               break;
            case 'J':
               clazz = long.class;
               break;
            case 'L':
            case '[':
               return getClassType(desc, descLen, start, num);
            case 'S':
               clazz = short.class;
               break;
            case 'V':
               clazz = void.class;
               break;
            case 'Z':
               clazz = boolean.class;
         }

         Class<?>[] result = getType(desc, descLen, start + 1, num + 1);
         result[num] = clazz;
         return result;
      }
   }

   private static Class<?>[] getClassType(String desc, int descLen, int start, int num) {
      int end = start;

      while (desc.charAt(end) == '[') {
         end++;
      }

      if (desc.charAt(end) == 'L') {
         end = desc.indexOf(59, end);
         if (end < 0) {
            throw new IndexOutOfBoundsException("bad descriptor");
         }
      }

      String cname;
      if (desc.charAt(start) == 'L') {
         cname = desc.substring(start + 1, end);
      } else {
         cname = desc.substring(start, end + 1);
      }

      Class<?>[] result = getType(desc, descLen, end + 1, num + 1);

      try {
         result[num] = getClassObject(cname.replace('/', '.'));
         return result;
      } catch (ClassNotFoundException var8) {
         throw new RuntimeException(var8.getMessage());
      }
   }
}
