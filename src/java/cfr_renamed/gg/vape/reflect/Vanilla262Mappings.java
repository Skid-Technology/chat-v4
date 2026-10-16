/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.reflect;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.Set;

public final class Vanilla262Mappings {
    private final static String MINECRAFT_CLASS = "net/minecraft/client/Minecraft";
    private final static String CLIENT_LEVEL_CLASS = "net/minecraft/client/multiplayer/ClientLevel";
    private final static String LOCAL_PLAYER_CLASS = "net/minecraft/client/player/LocalPlayer";
    private final static String ABSTRACT_PLAYER_CLASS = "net/minecraft/client/player/AbstractClientPlayer";
    private final static String LEVEL_CLASS = "net/minecraft/world/level/Level";

    private Vanilla262Mappings() {
    }

    public static Class<?> resolveClass(String sourceClassName, ClassLoader ... preferredLoaders) {
        String internalName = Vanilla262Mappings.normalizeInternalName(sourceClassName);
        if (internalName == null || internalName.isEmpty()) {
            return null;
        }
        for (ClassLoader loader : Vanilla262Mappings.candidateLoaders(preferredLoaders)) {
            Class<?> resolved = Vanilla262Mappings.resolveRuntimeClass(internalName, loader);
            if (resolved == null) continue;
            return resolved;
        }
        return null;
    }

    public static boolean isRuntimePresent(ClassLoader ... preferredLoaders) {
        for (ClassLoader loader : Vanilla262Mappings.candidateLoaders(preferredLoaders)) {
            ClassLoader definingLoader;
            Class<?> minecraftClass = Vanilla262Mappings.resolveRuntimeClass(MINECRAFT_CLASS, loader);
            if (minecraftClass == null || (definingLoader = minecraftClass.getClassLoader()) == null) continue;
            Class<?> clientLevel = Vanilla262Mappings.resolveRuntimeClass(CLIENT_LEVEL_CLASS, definingLoader);
            Class<?> localPlayer = Vanilla262Mappings.resolveRuntimeClass(LOCAL_PLAYER_CLASS, definingLoader);
            Class<?> abstractPlayer = Vanilla262Mappings.resolveRuntimeClass(ABSTRACT_PLAYER_CLASS, definingLoader);
            Class<?> level = Vanilla262Mappings.resolveRuntimeClass(LEVEL_CLASS, definingLoader);
            if (clientLevel == null || localPlayer == null || abstractPlayer == null || level == null || clientLevel.getSuperclass() != level || localPlayer.getSuperclass() != abstractPlayer || !Vanilla262Mappings.matchesMinecraftStructure(minecraftClass) || !Vanilla262Mappings.matchesVersionMetadata(minecraftClass)) continue;
            return true;
        }
        return false;
    }

    private static boolean matchesMinecraftStructure(Class<?> minecraftClass) {
        try {
            Method getter = minecraftClass.getDeclaredMethod("getInstance", new Class[0]);
            Field instance = minecraftClass.getDeclaredField("instance");
            return Modifier.isStatic(getter.getModifiers()) && getter.getReturnType() == minecraftClass && Modifier.isStatic(instance.getModifiers()) && instance.getType() == minecraftClass;
        }
        catch (ReflectiveOperationException ignored) {
            return false;
        }
        catch (SecurityException ignored) {
            return false;
        }
        catch (LinkageError ignored) {
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean matchesVersionMetadata(Class<?> minecraftClass) {
        try {
            JsonElement element;
            InputStream stream;
            block9: {
                boolean bl;
                stream = minecraftClass.getResourceAsStream("/version.json");
                try {
                    if (stream == null) {
                        return false;
                    }
                    element = JsonParser.parseReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
                    if (element.isJsonObject()) break block9;
                    bl = false;
                }
                catch (Throwable throwable) {
                    if (stream == null) throw throwable;
                    try {
                        stream.close();
                        throw throwable;
                    }
                    catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                    throw throwable;
                }
                stream.close();
                return bl;
            }
            JsonObject version = element.getAsJsonObject();
            boolean bl = "26.2".equals(Vanilla262Mappings.getString(version, "id")) && "26.2".equals(Vanilla262Mappings.getString(version, "name")) && Vanilla262Mappings.getInteger(version, "world_version") == 4903 && Vanilla262Mappings.getInteger(version, "protocol_version") == 776 && Vanilla262Mappings.getInteger(version, "java_version") == 25;
            stream.close();
            return bl;
        }
        catch (Exception ignored) {
            return false;
        }
        catch (LinkageError ignored) {
            return false;
        }
    }

    private static String getString(JsonObject object, String name) {
        JsonElement value = object.get(name);
        return value != null && value.isJsonPrimitive() ? value.getAsString() : null;
    }

    private static int getInteger(JsonObject object, String name) {
        JsonElement value = object.get(name);
        return value != null && value.isJsonPrimitive() ? value.getAsInt() : Integer.MIN_VALUE;
    }

    private static Class<?> resolveRuntimeClass(String internalName, ClassLoader loader) {
        try {
            return Class.forName(internalName.replace('/', '.'), false, loader);
        }
        catch (ClassNotFoundException ignored) {
            return null;
        }
        catch (LinkageError ignored) {
            return null;
        }
        catch (SecurityException ignored) {
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
        if ((mappingLoader = Vanilla262Mappings.class.getClassLoader()) != null) {
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
}

