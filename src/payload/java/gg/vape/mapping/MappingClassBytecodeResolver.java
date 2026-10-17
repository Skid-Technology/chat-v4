package gg.vape.mapping;

import gg.vape.runtime.ClassBytecodeCache;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MappingClassBytecodeResolver {
   private static final String b = ".class";
   private final Set<ClassLoader> k;
   private final Map<String, Class<?>> y = new HashMap<>();

   private Class<?> M(String string) {
      try {
         Object[] objectArray = MappedClasses.x();
         if (objectArray == null) {
            return null;
         } else {
            String simple = string;
            int lastDot = string.lastIndexOf(46);
            if (lastDot >= 0) {
               simple = string.substring(lastDot + 1);
            }

            Class<?> candidate = null;

            for (Object object : objectArray) {
               if (object instanceof Class) {
                  Class<?> clazz = (Class<?>)object;
                  if (string.equals(clazz.getName())) {
                     return clazz;
                  }

                  if (candidate == null && simple.equals(clazz.getSimpleName())) {
                     candidate = clazz;
                  }
               }
            }

            return candidate;
         }
      } catch (Throwable var11) {
         return null;
      }
   }

   public synchronized void O(ClassLoader classLoader) {
      if (classLoader != null) {
         this.k.add(classLoader);
      }
   }

   private Class<?> c(String string) {
      Class<?> clazz = this.y.get(string);
      if (clazz != null) {
         return clazz;
      } else {
         Class<?> clazz2 = this.y.get(this.R(string));
         if (clazz2 != null) {
            return clazz2;
         } else {
            Class<?> clazz3 = this.M(string);
            if (clazz3 != null) {
               return clazz3;
            } else {
               ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
               Class<?> clazz4 = this.y(string, classLoader);
               if (clazz4 != null) {
                  return clazz4;
               } else {
                  for (ClassLoader classLoader2 : this.k) {
                     Class<?> clazz5 = this.y(string, classLoader2);
                     if (clazz5 != null) {
                        return clazz5;
                     }
                  }

                  Class<?> clazz6 = this.y(string, JavassistMappingTask.class.getClassLoader());
                  if (clazz6 != null) {
                     return clazz6;
                  } else {
                     int lastDot = string.lastIndexOf(46);
                     if (lastDot >= 0) {
                        String simple = string.substring(lastDot + 1);
                        Class<?> simpleClass = this.y.get(simple);
                        if (simpleClass != null) {
                           return simpleClass;
                        }

                        simpleClass = this.y(simple, classLoader);
                        if (simpleClass != null) {
                           return simpleClass;
                        }

                        for (ClassLoader classLoader2x : this.k) {
                           simpleClass = this.y(simple, classLoader2x);
                           if (simpleClass != null) {
                              return simpleClass;
                           }
                        }
                     }

                     return null;
                  }
               }
            }
         }
      }
   }

   public MappingClassBytecodeResolver() {
      this.k = new HashSet<>();
   }

   private static Throwable a(Throwable throwable) {
      return throwable;
   }

   private String R(String string) {
      int n = string.lastIndexOf(46);
      return n < 0 ? string : string.substring(0, n) + "$" + string.substring(n + 1);
   }

   private Class<?> y(String string, ClassLoader classLoader) {
      if (classLoader == null) {
         return null;
      } else {
         try {
            return Class.forName(string, false, classLoader);
         } catch (Throwable var4) {
            return null;
         }
      }
   }

   public synchronized byte[] y(String string) {
      Class<?> clazz = this.c(string);
      if (clazz == null) {
         return null;
      } else {
         byte[] byArray = ClassBytecodeCache.getClassBytecode(clazz, true);
         if (byArray == null) {
            byArray = this.I(clazz);
         }

         return byArray;
      }
   }

   public synchronized void O(Class<?> clazz) {
      if (clazz != null) {
         if (clazz.isArray()) {
            this.O(clazz.getComponentType());
         } else {
            this.y.put(clazz.getName(), clazz);
            if (clazz.getCanonicalName() != null) {
               this.y.put(clazz.getCanonicalName(), clazz);
            }

            if (clazz.getTypeName() != null) {
               this.y.put(clazz.getTypeName(), clazz);
            }

            for (Class<?> clazz2 : clazz.getInterfaces()) {
               this.O(clazz2);
            }

            Class<?> clazz3 = clazz.getSuperclass();
            if (clazz3 != null && clazz3 != Object.class) {
               this.O(clazz3);
            }
         }
      }
   }

   private byte[] I(Class<?> clazz) {
      ClassLoader classLoader = clazz.getClassLoader();
      if (classLoader == null) {
         return null;
      } else {
         String string = clazz.getName().replace('.', '/') + ".class";

         try {
            try (InputStream inputStream = classLoader.getResourceAsStream(string)) {
               if (inputStream == null) {
                  return null;
               }

               ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
               byte[] byArray = new byte[4096];

               int n;
               while ((n = inputStream.read(byArray)) != -1) {
                  byteArrayOutputStream.write(byArray, 0, n);
               }

               return byteArrayOutputStream.toByteArray();
            }
         } catch (IOException var13) {
            return null;
         }
      }
   }
}
