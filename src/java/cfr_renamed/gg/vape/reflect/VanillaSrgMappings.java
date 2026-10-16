/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.reflect;

import gg.vape.reflect.ParameterResolver;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

final class VanillaSrgMappings {
    private final String minecraftVersion;
    private final String resource;
    private final String runtimeMinecraftClass;
    private final String runtimeMinecraftGetter;
    private final String runtimeMinecraftInstanceField;
    private final String[] runtimeAnchors;
    private volatile MappingData data;

    VanillaSrgMappings(String minecraftVersion, String resource, String runtimeMinecraftClass, String runtimeMinecraftGetter, String runtimeMinecraftInstanceField, String ... runtimeAnchors) {
        this.minecraftVersion = minecraftVersion;
        this.resource = resource;
        this.runtimeMinecraftClass = runtimeMinecraftClass;
        this.runtimeMinecraftGetter = runtimeMinecraftGetter;
        this.runtimeMinecraftInstanceField = runtimeMinecraftInstanceField;
        this.runtimeAnchors = (String[])runtimeAnchors.clone();
    }

    String remapClassName(String sourceClassName) {
        return this.getData().classNames.get(VanillaSrgMappings.normalizeInternalName(sourceClassName));
    }

    Class<?> resolveClass(String sourceClassName, ClassLoader ... preferredLoaders) {
        String reverseMappedName;
        String normalizedName = VanillaSrgMappings.normalizeInternalName(sourceClassName);
        if (normalizedName == null || normalizedName.isEmpty()) {
            return null;
        }
        LinkedHashSet<String> candidateNames = new LinkedHashSet<String>();
        String remappedName = this.getData().classNames.get(normalizedName);
        if (remappedName != null) {
            candidateNames.add(remappedName);
        }
        if ((reverseMappedName = this.getData().reverseClassNames.get(normalizedName)) != null) {
            candidateNames.add(reverseMappedName);
        }
        candidateNames.add(normalizedName);
        Set<ClassLoader> loaders = VanillaSrgMappings.candidateLoaders(preferredLoaders);
        for (String candidateName : candidateNames) {
            String binaryName = candidateName.replace('/', '.');
            for (ClassLoader loader : loaders) {
                try {
                    return Class.forName(binaryName, false, loader);
                }
                catch (ClassNotFoundException classNotFoundException) {
                }
                catch (LinkageError linkageError) {
                }
            }
        }
        return null;
    }

    boolean isRuntimePresent(ClassLoader ... preferredLoaders) {
        for (ClassLoader loader : VanillaSrgMappings.candidateLoaders(preferredLoaders)) {
            Class<?> minecraftClass = VanillaSrgMappings.resolveRuntimeClass(this.runtimeMinecraftClass, loader);
            if (minecraftClass == null) continue;
            ClassLoader definingLoader = minecraftClass.getClassLoader();
            boolean anchorsMatch = true;
            for (String runtimeName : this.runtimeAnchors) {
                Class<?> anchorClass = VanillaSrgMappings.resolveRuntimeClass(runtimeName, loader);
                if (anchorClass != null && anchorClass.getClassLoader() == definingLoader) continue;
                anchorsMatch = false;
                break;
            }
            if (!anchorsMatch || !this.matchesMinecraftStructure(minecraftClass)) continue;
            return true;
        }
        return false;
    }

    String lookupFieldSrgName(Field field) {
        if (field == null) {
            return null;
        }
        return this.getData().fieldNames.get(VanillaSrgMappings.memberKey(field.getDeclaringClass().getName(), field.getName()));
    }

    String lookupMethodSrgName(Method method) {
        if (method == null) {
            return null;
        }
        String ownerAndName = VanillaSrgMappings.memberKey(method.getDeclaringClass().getName(), method.getName());
        String descriptor = ParameterResolver.methodDescriptor(method.getParameterTypes(), method.getReturnType());
        return this.getData().methodNames.get(ownerAndName + ' ' + descriptor);
    }

    int getClassMappingCount() {
        return this.getData().classNames.size();
    }

    int getFieldMappingCount() {
        return this.getData().fieldNames.size();
    }

    int getMethodMappingCount() {
        return this.getData().methodNames.size();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private MappingData getData() {
        MappingData result = this.data;
        if (result != null) {
            return result;
        }
        VanillaSrgMappings vanillaSrgMappings = this;
        synchronized (vanillaSrgMappings) {
            result = this.data;
            if (result == null) {
                this.data = result = this.loadMappings();
            }
        }
        return result;
    }

    private MappingData loadMappings() {
        LinkedHashMap<String, String> classNames = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> reverseClassNames = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> fieldNames = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> methodNames = new LinkedHashMap<String, String>();
        try {
            InputStream stream = VanillaSrgMappings.class.getResourceAsStream(this.resource);
            try {
                if (stream == null) {
                    throw new FileNotFoundException("Missing mapping resource: " + this.resource);
                }
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));){
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] columns;
                        if (line.startsWith("CL: ")) {
                            columns = line.split("\\s+");
                            if (columns.length != 3) continue;
                            classNames.put(columns[2], columns[1]);
                            reverseClassNames.put(columns[1], columns[2]);
                            continue;
                        }
                        if (line.startsWith("FD: ")) {
                            columns = line.split("\\s+");
                            if (columns.length != 3) continue;
                            fieldNames.put(columns[1], VanillaSrgMappings.simpleMemberName(columns[2]));
                            continue;
                        }
                        if (!line.startsWith("MD: ") || (columns = line.split("\\s+")).length != 5) continue;
                        methodNames.put(columns[1] + ' ' + columns[2], VanillaSrgMappings.simpleMemberName(columns[3]));
                    }
                }
            }
            catch (Throwable throwable) {
                if (stream != null) {
                    try {
                        stream.close();
                    }
                    catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                }
                throw throwable;
            }
            stream.close();
        }
        catch (IOException exception) {
            throw new IllegalStateException("Unable to load " + this.minecraftVersion + " mappings", exception);
        }
        if (classNames.isEmpty() || fieldNames.isEmpty() || methodNames.isEmpty()) {
            throw new IllegalStateException("Incomplete " + this.minecraftVersion + " mappings in " + this.resource);
        }
        return new MappingData(classNames, reverseClassNames, fieldNames, methodNames);
    }

    private boolean matchesMinecraftStructure(Class<?> minecraftClass) {
        try {
            Method getter = minecraftClass.getDeclaredMethod(this.runtimeMinecraftGetter, new Class[0]);
            Field instance = minecraftClass.getDeclaredField(this.runtimeMinecraftInstanceField);
            return Modifier.isStatic(getter.getModifiers()) && getter.getReturnType() == minecraftClass && Modifier.isStatic(instance.getModifiers()) && instance.getType() == minecraftClass;
        }
        catch (NoSuchMethodException ignored) {
            return false;
        }
        catch (NoSuchFieldException ignored) {
            return false;
        }
        catch (SecurityException ignored) {
            return false;
        }
        catch (LinkageError ignored) {
            return false;
        }
    }

    private static Class<?> resolveRuntimeClass(String runtimeInternalName, ClassLoader loader) {
        String binaryName = runtimeInternalName.replace('/', '.');
        try {
            return Class.forName(binaryName, false, loader);
        }
        catch (ClassNotFoundException ignored) {
            return null;
        }
        catch (LinkageError ignored) {
            return null;
        }
    }

    private static Set<ClassLoader> candidateLoaders(ClassLoader ... preferredLoaders) {
        ClassLoader mappingLoader;
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
        if ((mappingLoader = VanillaSrgMappings.class.getClassLoader()) != null) {
            loaders.add(mappingLoader);
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

    private static String normalizeInternalName(String className) {
        if (className == null) {
            return null;
        }
        String normalized = className.trim().replace('.', '/');
        if (normalized.length() > 2 && normalized.charAt(0) == 'L' && normalized.charAt(normalized.length() - 1) == ';') {
            normalized = normalized.substring(1, normalized.length() - 1);
        }
        return normalized;
    }

    private static String memberKey(String ownerName, String memberName) {
        return ownerName.replace('.', '/') + '/' + memberName;
    }

    private static String simpleMemberName(String qualifiedName) {
        int separator = qualifiedName.lastIndexOf(47);
        return separator < 0 ? qualifiedName : qualifiedName.substring(separator + 1);
    }

    private static final class MappingData {
        final Map<String, String> classNames;
        final Map<String, String> reverseClassNames;
        final Map<String, String> fieldNames;
        final Map<String, String> methodNames;

        MappingData(Map<String, String> classNames, Map<String, String> reverseClassNames, Map<String, String> fieldNames, Map<String, String> methodNames) {
            this.classNames = Collections.unmodifiableMap(classNames);
            this.reverseClassNames = Collections.unmodifiableMap(reverseClassNames);
            this.fieldNames = Collections.unmodifiableMap(fieldNames);
            this.methodNames = Collections.unmodifiableMap(methodNames);
        }
    }
}

