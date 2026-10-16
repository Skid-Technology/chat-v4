/*
 * Decompiled with CFR 0.152.
 */
package io.netty.util.internal;

import gg.vape.shaded.javassist.ClassClassPath;
import gg.vape.shaded.javassist.ClassPath;
import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.CtClass;
import gg.vape.shaded.javassist.NotFoundException;
import io.netty.util.internal.NoOpTypeParameterMatcher;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.TypeParameterMatcher;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.Method;

public final class JavassistTypeParameterMatcherGenerator {
    private final static InternalLogger logger = InternalLoggerFactory.getInstance(JavassistTypeParameterMatcherGenerator.class);
    private final static ClassPool classPool = new ClassPool(true);

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
        return JavassistTypeParameterMatcherGenerator.generate(type, classLoader);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static TypeParameterMatcher generate(Class<?> type, ClassLoader classLoader) {
        String typeName = JavassistTypeParameterMatcherGenerator.typeName(type);
        String className = "io.netty.util.internal.__matchers__." + typeName + "Matcher";
        try {
            return (TypeParameterMatcher)Class.forName(className, true, classLoader).newInstance();
        }
        catch (Exception e) {
            try {
                CtClass c = classPool.getAndRename(NoOpTypeParameterMatcher.class.getName(), className);
                c.setModifiers(c.getModifiers() | 0x10);
                c.getDeclaredMethod("match").setBody("{ return $1 instanceof " + typeName + "; }");
                byte[] byteCode = c.toBytecode();
                c.detach();
                Method method = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE);
                method.setAccessible(true);
                Class generated = (Class)method.invoke((Object)classLoader, className, byteCode, 0, byteCode.length);
                if (type != Object.class) {
                    logger.debug("Generated: {}", (Object)generated.getName());
                }
                return (TypeParameterMatcher)generated.newInstance();
            }
            catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    private static String typeName(Class<?> type) {
        if (type.isArray()) {
            return JavassistTypeParameterMatcherGenerator.typeName(type.getComponentType()) + "[]";
        }
        return type.getName();
    }

    private JavassistTypeParameterMatcherGenerator() {
    }

    static {
        classPool.appendClassPath(new ClassClassPath(NoOpTypeParameterMatcher.class));
    }
}

