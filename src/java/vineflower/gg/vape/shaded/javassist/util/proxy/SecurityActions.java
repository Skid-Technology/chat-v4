package gg.vape.shaded.javassist.util.proxy;

import gg.vape.shaded.javassist.bytecode.ClassFile;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SecurityActions extends SecurityManager {
   public static final SecurityActions stack = new SecurityActions();

   public Class<?> getCallerClass() {
      return this.getClassContext()[2];
   }

   static Method[] getDeclaredMethods(final Class<?> clazz) {
      return System.getSecurityManager() == null ? clazz.getDeclaredMethods() : AccessController.doPrivileged(new PrivilegedAction<Method[]>() {
         public Method[] run() {
            return clazz.getDeclaredMethods();
         }
      });
   }

   static Constructor<?>[] getDeclaredConstructors(final Class<?> clazz) {
      return System.getSecurityManager() == null ? clazz.getDeclaredConstructors() : AccessController.doPrivileged(new PrivilegedAction<Constructor<?>[]>() {
         public Constructor<?>[] run() {
            return clazz.getDeclaredConstructors();
         }
      });
   }

   static MethodHandle getMethodHandle(final Class<?> clazz, final String name, final Class<?>[] params) throws NoSuchMethodException {
      try {
         return AccessController.doPrivileged(new PrivilegedExceptionAction<MethodHandle>() {
            public MethodHandle run() throws IllegalAccessException, NoSuchMethodException, SecurityException {
               Method rmet = clazz.getDeclaredMethod(name, params);
               rmet.setAccessible(true);
               MethodHandle meth = MethodHandles.lookup().unreflect(rmet);
               rmet.setAccessible(false);
               return meth;
            }
         });
      } catch (PrivilegedActionException var4) {
         if (var4.getCause() instanceof NoSuchMethodException) {
            throw (NoSuchMethodException)var4.getCause();
         } else {
            throw new RuntimeException(var4.getCause());
         }
      }
   }

   static Method getDeclaredMethod(final Class<?> clazz, final String name, final Class<?>[] types) throws NoSuchMethodException {
      if (System.getSecurityManager() == null) {
         return clazz.getDeclaredMethod(name, types);
      } else {
         try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<Method>() {
               public Method run() throws Exception {
                  return clazz.getDeclaredMethod(name, types);
               }
            });
         } catch (PrivilegedActionException var4) {
            if (var4.getCause() instanceof NoSuchMethodException) {
               throw (NoSuchMethodException)var4.getCause();
            } else {
               throw new RuntimeException(var4.getCause());
            }
         }
      }
   }

   static Constructor<?> getDeclaredConstructor(final Class<?> clazz, final Class<?>[] types) throws NoSuchMethodException {
      if (System.getSecurityManager() == null) {
         return clazz.getDeclaredConstructor(types);
      } else {
         try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<Constructor<?>>() {
               public Constructor<?> run() throws Exception {
                  return clazz.getDeclaredConstructor(types);
               }
            });
         } catch (PrivilegedActionException var3) {
            if (var3.getCause() instanceof NoSuchMethodException) {
               throw (NoSuchMethodException)var3.getCause();
            } else {
               throw new RuntimeException(var3.getCause());
            }
         }
      }
   }

   static void setAccessible(final AccessibleObject ao, final boolean accessible) {
      if (System.getSecurityManager() == null) {
         ao.setAccessible(accessible);
      } else {
         AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
               ao.setAccessible(accessible);
               return null;
            }
         });
      }
   }

   static void set(final Field fld, final Object target, final Object value) throws IllegalAccessException {
      if (System.getSecurityManager() == null) {
         fld.set(target, value);
      } else {
         try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
               public Void run() throws Exception {
                  fld.set(target, value);
                  return null;
               }
            });
         } catch (PrivilegedActionException var4) {
            if (var4.getCause() instanceof NoSuchMethodException) {
               throw (IllegalAccessException)var4.getCause();
            }

            throw new RuntimeException(var4.getCause());
         }
      }
   }

   static SecurityActions.TheUnsafe getSunMiscUnsafeAnonymously() throws ClassNotFoundException {
      try {
         return AccessController.doPrivileged(
            new PrivilegedExceptionAction<SecurityActions.TheUnsafe>() {
               public SecurityActions.TheUnsafe run() throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
                  Class<?> unsafe = Class.forName("sun.misc.Unsafe");
                  Field theUnsafe = unsafe.getDeclaredField("theUnsafe");
                  theUnsafe.setAccessible(true);
                  SecurityActions.TheUnsafe usf = SecurityActions.stack.new TheUnsafe(unsafe, theUnsafe.get(null));
                  theUnsafe.setAccessible(false);
                  SecurityActions.disableWarning(usf);
                  return usf;
               }
            }
         );
      } catch (PrivilegedActionException var1) {
         if (var1.getCause() instanceof ClassNotFoundException) {
            throw (ClassNotFoundException)var1.getCause();
         } else if (var1.getCause() instanceof NoSuchFieldException) {
            throw new ClassNotFoundException("No such instance.", var1.getCause());
         } else if (!(var1.getCause() instanceof IllegalAccessException) && !(var1.getCause() instanceof SecurityException)) {
            throw new RuntimeException(var1.getCause());
         } else {
            throw new ClassNotFoundException("Security denied access.", var1.getCause());
         }
      }
   }

   static void disableWarning(SecurityActions.TheUnsafe tu) {
      try {
         if (ClassFile.MAJOR_VERSION < 53) {
            return;
         }

         Class<?> cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
         Field logger = cls.getDeclaredField("logger");
         tu.call("putObjectVolatile", cls, tu.call("staticFieldOffset", logger), null);
      } catch (Exception var3) {
      }
   }

   class TheUnsafe {
      final Class<?> unsafe;
      final Object theUnsafe;
      final Map<String, List<Method>> methods = new HashMap<>();

      TheUnsafe(Class<?> c, Object o) {
         this.unsafe = c;
         this.theUnsafe = o;

         for (Method m : this.unsafe.getDeclaredMethods()) {
            if (!this.methods.containsKey(m.getName())) {
               this.methods.put(m.getName(), Collections.singletonList(m));
            } else {
               if (this.methods.get(m.getName()).size() == 1) {
                  this.methods.put(m.getName(), new ArrayList<>(this.methods.get(m.getName())));
               }

               this.methods.get(m.getName()).add(m);
            }
         }
      }

      private Method getM(String name, Object[] o) {
         return this.methods.get(name).get(0);
      }

      public Object call(String name, Object... args) {
         try {
            return this.getM(name, args).invoke(this.theUnsafe, args);
         } catch (Throwable var4) {
            var4.printStackTrace();
            return null;
         }
      }
   }
}
