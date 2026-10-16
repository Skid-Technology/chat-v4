/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.reflect;

import gg.vape.reflect.VanillaSrgMappings;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

final class NamedLegacyRuntimeDetector {
    private final static String MINECRAFT_CLASS = "net/minecraft/client/Minecraft";

    private NamedLegacyRuntimeDetector() {
    }

    static boolean is1710Runtime(VanillaSrgMappings mappings, ClassLoader ... preferredLoaders) {
        return NamedLegacyRuntimeDetector.isRuntime(mappings, new String[]{"net/minecraft/client/renderer/WorldRenderer", "net/minecraft/network/NetworkManager", "net/minecraft/util/ChunkCoordinates"}, new String[]{"net/minecraft/util/BlockPos", "net/minecraft/util/math/BlockPos"}, "theMinecraft", preferredLoaders);
    }

    static boolean is189Runtime(VanillaSrgMappings mappings, ClassLoader ... preferredLoaders) {
        return NamedLegacyRuntimeDetector.isRuntime(mappings, new String[]{"net/minecraft/client/renderer/WorldRenderer", "net/minecraft/client/renderer/vertex/VertexFormat", "net/minecraft/util/BlockPos"}, new String[]{"net/minecraft/util/ChunkCoordinates", "net/minecraft/util/math/BlockPos"}, "theMinecraft", preferredLoaders);
    }

    static boolean is1122Runtime(VanillaSrgMappings mappings, ClassLoader ... preferredLoaders) {
        return NamedLegacyRuntimeDetector.isRuntime(mappings, new String[]{"net/minecraft/client/renderer/BufferBuilder", "net/minecraft/network/play/client/CPacketPlayer", "net/minecraft/util/math/BlockPos"}, new String[]{"net/minecraft/util/ChunkCoordinates", "net/minecraft/util/BlockPos"}, "instance", preferredLoaders);
    }

    private static boolean isRuntime(VanillaSrgMappings mappings, String[] requiredClasses, String[] forbiddenClasses, String instanceFieldName, ClassLoader ... preferredLoaders) {
        Class<?> minecraftClass = mappings.resolveClass(MINECRAFT_CLASS, preferredLoaders);
        if (minecraftClass == null || !NamedLegacyRuntimeDetector.matchesMinecraftStructure(minecraftClass, instanceFieldName)) {
            return false;
        }
        ClassLoader gameLoader = minecraftClass.getClassLoader();
        for (String requiredClass : requiredClasses) {
            Class<?> resolvedClass = mappings.resolveClass(requiredClass, preferredLoaders);
            if (resolvedClass != null && resolvedClass.getClassLoader() == gameLoader) continue;
            return false;
        }
        for (String forbiddenClass : forbiddenClasses) {
            if (mappings.resolveClass(forbiddenClass, preferredLoaders) == null) continue;
            return false;
        }
        return true;
    }

    private static boolean matchesMinecraftStructure(Class<?> minecraftClass, String instanceFieldName) {
        try {
            Method getter = minecraftClass.getDeclaredMethod("getMinecraft", new Class[0]);
            Field instance = minecraftClass.getDeclaredField(instanceFieldName);
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
}

