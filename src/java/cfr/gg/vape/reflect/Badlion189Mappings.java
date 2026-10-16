/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.reflect;

import gg.vape.reflect.Vanilla189Mappings;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

public final class Badlion189Mappings {
    private static final String[] MARKER_RESOURCES = new String[]{"net/badlion/client/Wrapper.class", "net/badlion/client/tweaker/BadlionTransformer.class", "net/badlion/clientcommon/main/Launch.class"};

    private Badlion189Mappings() {
    }

    public static boolean isRuntimePresent(ClassLoader ... preferredLoaders) {
        return Badlion189Mappings.hasBadlionMarker(preferredLoaders) && Vanilla189Mappings.isRuntimePresent(preferredLoaders);
    }

    public static Class<?> resolveClass(String sourceClassName, ClassLoader ... preferredLoaders) {
        return Vanilla189Mappings.resolveClass(sourceClassName, preferredLoaders);
    }

    static String lookupFieldSrgName(Field field) {
        return Vanilla189Mappings.lookupFieldSrgName(field);
    }

    static String lookupMethodSrgName(Method method) {
        return Vanilla189Mappings.lookupMethodSrgName(method);
    }

    private static boolean hasBadlionMarker(ClassLoader ... preferredLoaders) {
        for (ClassLoader loader : Badlion189Mappings.candidateLoaders(preferredLoaders)) {
            for (String marker : MARKER_RESOURCES) {
                try {
                    URL resource = loader.getResource(marker);
                    if (resource == null) continue;
                    return true;
                }
                catch (RuntimeException runtimeException) {
                }
                catch (LinkageError linkageError) {
                    // empty catch block
                }
            }
        }
        return false;
    }

    private static Set<ClassLoader> candidateLoaders(ClassLoader ... preferredLoaders) {
        ClassLoader profileLoader;
        ClassLoader contextLoader;
        LinkedHashSet<ClassLoader> loaders = new LinkedHashSet<ClassLoader>();
        if (preferredLoaders != null) {
            for (ClassLoader loader : preferredLoaders) {
                if (loader == null) continue;
                loaders.add(loader);
            }
        }
        if ((contextLoader = Thread.currentThread().getContextClassLoader()) != null) {
            loaders.add(contextLoader);
        }
        if ((profileLoader = Badlion189Mappings.class.getClassLoader()) != null) {
            loaders.add(profileLoader);
        }
        try {
            ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
            if (systemLoader != null) {
                loaders.add(systemLoader);
            }
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        return loaders;
    }
}

