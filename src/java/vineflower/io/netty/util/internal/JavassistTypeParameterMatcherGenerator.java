package io.netty.util.internal;

import gg.vape.shaded.javassist.ClassClassPath;
import gg.vape.shaded.javassist.ClassPath;
import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.CtClass;
import gg.vape.shaded.javassist.NotFoundException;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.Method;

public final class JavassistTypeParameterMatcherGenerator {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(JavassistTypeParameterMatcherGenerator.class);
   private static final ClassPool classPool = new ClassPool(true);

   public static void appendClassPath(ClassPath classpath) {
      classPool.appendClassPath(classpath);
   }

   public static void appendClassPath(String pathname) throws NotFoundException {
      classPool.appendClassPath(pathname);
   }

   public static TypeParameterMatcher generate(Class<?> type) {
      ClassLoader classLoader = PlatformDependent.getContextClassLoader();
      if (classLoader == null) {
         classLoader = PlatformDependent.getSystemClassLoader();
      }

      return generate(type, classLoader);
   }

   public static TypeParameterMatcher generate(Class<?> type, ClassLoader classLoader) {
      String typeName = typeName(type);
      String className = "io.netty.util.internal.__matchers__." + typeName + "Matcher";

      try {
         try {
            return (TypeParameterMatcher)Class.forName(className, true, classLoader).newInstance();
         } catch (Exception var8) {
            CtClass c = classPool.getAndRename(NoOpTypeParameterMatcher.class.getName(), className);
            c.setModifiers(c.getModifiers() | 16);
            c.getDeclaredMethod("match").setBody("{ return $1 instanceof " + typeName + "; }");
            byte[] byteCode = c.toBytecode();
            c.detach();
            Method method = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
            method.setAccessible(true);
            Class<?> generated = (Class<?>)method.invoke(classLoader, className, byteCode, 0, byteCode.length);
            if (type != Object.class) {
               logger.debug("Generated: {}", generated.getName());
            }

            return (TypeParameterMatcher)generated.newInstance();
         }
      } catch (RuntimeException var9) {
         throw var9;
      } catch (Exception var10) {
         throw new RuntimeException(var10);
      }
   }

   private static String typeName(Class<?> type) {
      return type.isArray() ? typeName(type.getComponentType()) + "[]" : type.getName();
   }

   private JavassistTypeParameterMatcherGenerator() {
   }

   static {
      classPool.appendClassPath(new ClassClassPath(NoOpTypeParameterMatcher.class));
   }
}
