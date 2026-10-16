package gg.vape.shaded.javassist.util.proxy;

import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.bytecode.ClassFile;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.List;

public class DefineClassHelper {
   private static final DefineClassHelper.Helper privileged = (DefineClassHelper.Helper)(ClassFile.MAJOR_VERSION > 54
      ? new DefineClassHelper.Java11()
      : (
         ClassFile.MAJOR_VERSION >= 53
            ? new DefineClassHelper.Java9()
            : (ClassFile.MAJOR_VERSION >= 51 ? new DefineClassHelper.Java7() : new DefineClassHelper.JavaOther())
      ));

   public static Class<?> toClass(String className, Class<?> neighbor, ClassLoader loader, ProtectionDomain domain, byte[] bcode) throws CannotCompileException {
      try {
         return privileged.defineClass(className, bcode, 0, bcode.length, neighbor, loader, domain);
      } catch (RuntimeException var7) {
         throw var7;
      } catch (CannotCompileException var8) {
         throw var8;
      } catch (ClassFormatError var9) {
         Throwable t = var9.getCause();
         throw new CannotCompileException((Throwable)(t == null ? var9 : t));
      } catch (Exception var10) {
         throw new CannotCompileException(var10);
      }
   }

   public static Class<?> toClass(Class<?> neighbor, byte[] bcode) throws CannotCompileException {
      try {
         DefineClassHelper.class.getModule().addReads(neighbor.getModule());
         Lookup lookup = MethodHandles.lookup();
         Lookup prvlookup = MethodHandles.privateLookupIn(neighbor, lookup);
         return prvlookup.defineClass(bcode);
      } catch (IllegalArgumentException | IllegalAccessException var4) {
         throw new CannotCompileException(var4.getMessage() + ": " + neighbor.getName() + " has no permission to define the class");
      }
   }

   public static Class<?> toClass(Lookup lookup, byte[] bcode) throws CannotCompileException {
      try {
         return lookup.defineClass(bcode);
      } catch (IllegalArgumentException | IllegalAccessException var3) {
         throw new CannotCompileException(var3.getMessage());
      }
   }

   static Class<?> toPublicClass(String className, byte[] bcode) throws CannotCompileException {
      try {
         Lookup lookup = MethodHandles.lookup();
         lookup = lookup.dropLookupMode(2);
         return lookup.defineClass(bcode);
      } catch (Throwable var3) {
         throw new CannotCompileException(var3);
      }
   }

   private DefineClassHelper() {
   }

   private abstract static class Helper {
      private Helper() {
      }

      abstract Class<?> defineClass(String var1, byte[] var2, int var3, int var4, Class<?> var5, ClassLoader var6, ProtectionDomain var7) throws ClassFormatError, CannotCompileException;
   }

   private static class Java11 extends DefineClassHelper.JavaOther {
      private Java11() {
      }

      @Override
      Class<?> defineClass(String name, byte[] bcode, int off, int len, Class<?> neighbor, ClassLoader loader, ProtectionDomain protectionDomain) throws ClassFormatError, CannotCompileException {
         return neighbor != null ? DefineClassHelper.toClass(neighbor, bcode) : super.defineClass(name, bcode, off, len, neighbor, loader, protectionDomain);
      }
   }

   private static class Java7 extends DefineClassHelper.Helper {
      private final SecurityActions stack;
      private final MethodHandle defineClass;

      private Java7() {
         this.stack = SecurityActions.stack;
         this.defineClass = this.getDefineClassMethodHandle();
      }

      private final MethodHandle getDefineClassMethodHandle() {
         if (DefineClassHelper.privileged != null && this.stack.getCallerClass() != this.getClass()) {
            throw new IllegalAccessError("Access denied for caller.");
         } else {
            try {
               return SecurityActions.getMethodHandle(
                  ClassLoader.class, "defineClass", new Class[]{String.class, byte[].class, int.class, int.class, ProtectionDomain.class}
               );
            } catch (NoSuchMethodException var2) {
               throw new RuntimeException("cannot initialize", var2);
            }
         }
      }

      @Override
      Class<?> defineClass(String name, byte[] b, int off, int len, Class<?> neighbor, ClassLoader loader, ProtectionDomain protectionDomain) throws ClassFormatError {
         if (this.stack.getCallerClass() != DefineClassHelper.class) {
            throw new IllegalAccessError("Access denied for caller.");
         } else {
            try {
               return (Class<?>)this.defineClass.invokeWithArguments(loader, name, b, off, len, protectionDomain);
            } catch (Throwable var9) {
               if (var9 instanceof RuntimeException) {
                  throw (RuntimeException)var9;
               } else if (var9 instanceof ClassFormatError) {
                  throw (ClassFormatError)var9;
               } else {
                  throw new ClassFormatError(var9.getMessage());
               }
            }
         }
      }
   }

   private static class Java9 extends DefineClassHelper.Helper {
      private final Object stack;
      private final Method getCallerClass;
      private final DefineClassHelper.Java9.ReferencedUnsafe sunMiscUnsafe = this.getReferencedUnsafe();

      Java9() {
         Class<?> stackWalkerClass = null;

         try {
            stackWalkerClass = Class.forName("java.lang.StackWalker");
         } catch (ClassNotFoundException var4) {
         }

         if (stackWalkerClass != null) {
            try {
               Class<?> optionClass = Class.forName("java.lang.StackWalker$Option");
               this.stack = stackWalkerClass.getMethod("getInstance", optionClass).invoke(null, optionClass.getEnumConstants()[0]);
               this.getCallerClass = stackWalkerClass.getMethod("getCallerClass");
            } catch (Throwable var3) {
               throw new RuntimeException("cannot initialize", var3);
            }
         } else {
            this.stack = null;
            this.getCallerClass = null;
         }
      }

      private final DefineClassHelper.Java9.ReferencedUnsafe getReferencedUnsafe() {
         try {
            if (DefineClassHelper.privileged != null && this.getCallerClass.invoke(this.stack) != this.getClass()) {
               throw new IllegalAccessError("Access denied for caller.");
            }
         } catch (Exception var5) {
            throw new RuntimeException("cannot initialize", var5);
         }

         try {
            SecurityActions.TheUnsafe usf = SecurityActions.getSunMiscUnsafeAnonymously();
            List<Method> defineClassMethod = usf.methods.get("defineClass");
            if (null == defineClassMethod) {
               return null;
            } else {
               MethodHandle meth = MethodHandles.lookup().unreflect(defineClassMethod.get(0));
               return new DefineClassHelper.Java9.ReferencedUnsafe(usf, meth);
            }
         } catch (Throwable var4) {
            throw new RuntimeException("cannot initialize", var4);
         }
      }

      @Override
      Class<?> defineClass(String name, byte[] b, int off, int len, Class<?> neighbor, ClassLoader loader, ProtectionDomain protectionDomain) throws ClassFormatError {
         try {
            if (this.getCallerClass.invoke(this.stack) != DefineClassHelper.class) {
               throw new IllegalAccessError("Access denied for caller.");
            }
         } catch (Exception var9) {
            throw new RuntimeException("cannot initialize", var9);
         }

         return this.sunMiscUnsafe.defineClass(name, b, off, len, loader, protectionDomain);
      }

      final class ReferencedUnsafe {
         private final SecurityActions.TheUnsafe sunMiscUnsafeTheUnsafe;
         private final MethodHandle defineClass;

         ReferencedUnsafe(SecurityActions.TheUnsafe usf, MethodHandle meth) {
            this.sunMiscUnsafeTheUnsafe = usf;
            this.defineClass = meth;
         }

         Class<?> defineClass(String name, byte[] b, int off, int len, ClassLoader loader, ProtectionDomain protectionDomain) throws ClassFormatError {
            try {
               if (Java9.this.getCallerClass.invoke(Java9.this.stack) != DefineClassHelper.Java9.class) {
                  throw new IllegalAccessError("Access denied for caller.");
               }
            } catch (Exception var9) {
               throw new RuntimeException("cannot initialize", var9);
            }

            try {
               return (Class<?>)this.defineClass.invokeWithArguments(this.sunMiscUnsafeTheUnsafe.theUnsafe, name, b, off, len, loader, protectionDomain);
            } catch (Throwable var8) {
               if (var8 instanceof RuntimeException) {
                  throw (RuntimeException)var8;
               } else if (var8 instanceof ClassFormatError) {
                  throw (ClassFormatError)var8;
               } else {
                  throw new ClassFormatError(var8.getMessage());
               }
            }
         }
      }
   }

   private static class JavaOther extends DefineClassHelper.Helper {
      private final Method defineClass = this.getDefineClassMethod();
      private final SecurityActions stack;

      private JavaOther() {
         this.stack = SecurityActions.stack;
      }

      private final Method getDefineClassMethod() {
         if (DefineClassHelper.privileged != null && this.stack.getCallerClass() != this.getClass()) {
            throw new IllegalAccessError("Access denied for caller.");
         } else {
            try {
               return SecurityActions.getDeclaredMethod(
                  ClassLoader.class, "defineClass", new Class[]{String.class, byte[].class, int.class, int.class, ProtectionDomain.class}
               );
            } catch (NoSuchMethodException var2) {
               throw new RuntimeException("cannot initialize", var2);
            }
         }
      }

      @Override
      Class<?> defineClass(String name, byte[] b, int off, int len, Class<?> neighbor, ClassLoader loader, ProtectionDomain protectionDomain) throws ClassFormatError, CannotCompileException {
         Class<?> klass = this.stack.getCallerClass();
         if (klass != DefineClassHelper.class && klass != this.getClass()) {
            throw new IllegalAccessError("Access denied for caller.");
         } else {
            try {
               SecurityActions.setAccessible(this.defineClass, true);
               return (Class<?>)this.defineClass.invoke(loader, name, b, off, len, protectionDomain);
            } catch (Throwable var10) {
               if (var10 instanceof ClassFormatError) {
                  throw (ClassFormatError)var10;
               } else if (var10 instanceof RuntimeException) {
                  throw (RuntimeException)var10;
               } else {
                  throw new CannotCompileException(var10);
               }
            }
         }
      }
   }
}
