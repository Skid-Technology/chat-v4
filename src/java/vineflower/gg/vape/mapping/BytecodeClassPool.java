package gg.vape.mapping;

import gg.vape.shaded.javassist.ByteArrayClassPath;
import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.CtClass;
import gg.vape.shaded.javassist.NotFoundException;
import gg.vape.shaded.javassist.bytecode.Descriptor;
import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Set;

public class BytecodeClassPool extends ClassPool {
   private final MappingClassBytecodeResolver W;
   private final Set<String> runtimeClassPaths = new HashSet<>();

   private static Exception a(Exception exception) {
      return exception;
   }

   @Override
   public synchronized CtClass get(String string) throws NotFoundException {
      this.ensureRuntimeClassPath(string);

      try {
         return super.get(string);
      } catch (NotFoundException var6) {
         if (this.N(string)) {
            return super.get(string);
         } else {
            throw var6;
         }
      } catch (RuntimeException var7) {
         if (this.N(string)) {
            try {
               return super.get(string);
            } catch (Throwable var5) {
            }
         }

         try {
            CtClass existing = super.getOrNull(string);
            return existing != null ? existing : this.makeClass(normalizeClassName(string));
         } catch (Throwable var4) {
            throw var7;
         }
      }
   }

   public BytecodeClassPool(MappingClassBytecodeResolver mappingClassBytecodeResolver) {
      super(true);
      this.W = mappingClassBytecodeResolver;
   }

   @Override
   public synchronized CtClass getOrNull(String string) {
      this.ensureRuntimeClassPath(string);

      try {
         CtClass ctClass = super.getOrNull(string);
         if (ctClass == null && this.N(string)) {
            ctClass = super.getOrNull(string);
         }

         return ctClass;
      } catch (RuntimeException var6) {
         if (this.N(string)) {
            try {
               return super.getOrNull(string);
            } catch (Throwable var5) {
            }
         }

         try {
            return this.makeClass(normalizeClassName(string));
         } catch (Throwable var4) {
            return null;
         }
      }
   }

   private void ensureRuntimeClassPath(String requestedName) {
      String className = normalizeClassName(requestedName);
      if (!className.startsWith("java.") && !className.startsWith("javax.") && !className.startsWith("sun.") && !className.startsWith("jdk.")) {
         if (this.runtimeClassPaths.add(className)) {
            byte[] bytecode = this.W.y(className);
            if (bytecode == null) {
               this.runtimeClassPaths.remove(className);
            } else {
               this.removeCached(className);

               try {
                  CtClass cc = this.makeClass(new ByteArrayInputStream(bytecode));
                  if (!cc.getName().equals(className)) {
                     cc.setName(className);
                  }
               } catch (Exception var5) {
                  this.insertClassPath(new ByteArrayClassPath(className, bytecode));
               }
            }
         }
      }
   }

   private boolean N(String string) {
      String className = normalizeClassName(string);
      byte[] byArray = this.W.y(className);
      if (byArray != null) {
         try {
            CtClass cc = this.makeClass(new ByteArrayInputStream(byArray));
            if (!cc.getName().equals(className)) {
               cc.setName(className);
            }
         } catch (Exception var5) {
            this.insertClassPath(new ByteArrayClassPath(className, byArray));
         }

         return true;
      } else {
         return false;
      }
   }

   private static String normalizeClassName(String requestedName) {
      String className = requestedName;
      if (requestedName.startsWith("[")) {
         className = Descriptor.toClassName(requestedName);
      }

      while (className.endsWith("[]")) {
         className = className.substring(0, className.length() - 2);
      }

      return className;
   }
}
