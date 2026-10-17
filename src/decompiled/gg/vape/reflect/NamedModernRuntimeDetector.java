/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.reflect;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gg.vape.reflect.VanillaSrgMappings;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public final class NamedModernRuntimeDetector {
    private static final String MINECRAFT_CLASS = "net/minecraft/client/Minecraft";

    private NamedModernRuntimeDetector() {
    }

    public static boolean is12111Runtime(VanillaSrgMappings mappings, ClassLoader ... preferredLoaders) {
        Class<?> minecraftClass = mappings.resolveClass(MINECRAFT_CLASS, preferredLoaders);
        if (minecraftClass == null || !NamedModernRuntimeDetector.matchesModernMinecraftStructure(minecraftClass)) {
            return false;
        }
        String version = NamedModernRuntimeDetector.getDetectedVersionString(minecraftClass, preferredLoaders);
        if (version != null && !version.isEmpty()) {
            String v = version.toLowerCase();
            if (v.contains("26.2") || v.contains("26.1") || v.contains("1.20") || v.contains("1.19") || v.contains("1.18") || v.contains("1.17") || v.contains("1.16") || v.contains("1.12") || v.contains("1.8") || v.contains("1.7")) {
                return false;
            }
            if (v.contains("1.21")) {
                return true;
            }
        }
        boolean hasMace = mappings.resolveClass("net/minecraft/world/item/MaceItem", preferredLoaders) != null;
        boolean hasLevelExtractor = mappings.resolveClass("net/minecraft/client/renderer/extract/LevelExtractor", preferredLoaders) != null;
        return hasMace && !hasLevelExtractor;
    }

    public static boolean is1206Runtime(VanillaSrgMappings mappings, ClassLoader ... preferredLoaders) {
        Class<?> minecraftClass = mappings.resolveClass(MINECRAFT_CLASS, preferredLoaders);
        if (minecraftClass == null || !NamedModernRuntimeDetector.matchesModernMinecraftStructure(minecraftClass)) {
            return false;
        }
        String version = NamedModernRuntimeDetector.getDetectedVersionString(minecraftClass, preferredLoaders);
        if (version != null && !version.isEmpty()) {
            String v = version.toLowerCase();
            return v.contains("1.20.6") || v.contains("1.20.5") || v.contains("1.20");
        }
        boolean hasArmorTrim = mappings.resolveClass("net/minecraft/world/item/armortrim/ArmorTrim", preferredLoaders) != null;
        boolean hasMace = mappings.resolveClass("net/minecraft/world/item/MaceItem", preferredLoaders) != null;
        return hasArmorTrim && !hasMace;
    }

    public static int detectVersion(ClassLoader ... preferredLoaders) {
        Set<ClassLoader> loaders = NamedModernRuntimeDetector.candidateLoaders(preferredLoaders);
        for (ClassLoader loader : loaders) {
            int protocol;
            Class<?> minecraftClass = NamedModernRuntimeDetector.resolveRuntimeClass(MINECRAFT_CLASS, loader);
            if (minecraftClass == null || !NamedModernRuntimeDetector.matchesModernMinecraftStructure(minecraftClass)) continue;
            String version = NamedModernRuntimeDetector.getDetectedVersionString(minecraftClass, preferredLoaders);
            if (version != null && !version.isEmpty() && (protocol = NamedModernRuntimeDetector.parseVersionToProtocol(version)) != 0) {
                return protocol;
            }
            if (NamedModernRuntimeDetector.resolveRuntimeClass("net/minecraft/client/renderer/extract/LevelExtractor", loader) != null) {
                return 110;
            }
            if (NamedModernRuntimeDetector.resolveRuntimeClass("net/minecraft/world/item/MaceItem", loader) != null) {
                return 61;
            }
            if (NamedModernRuntimeDetector.resolveRuntimeClass("net/minecraft/world/item/armortrim/ArmorTrim", loader) == null) continue;
            return 50;
        }
        return 0;
    }

    public static int parseVersionToProtocol(String version) {
        if (version == null) {
            return 0;
        }
        String v = version.trim().toLowerCase();
        if (v.contains("26.2")) {
            return 110;
        }
        if (v.contains("26.1")) {
            return 100;
        }
        if (v.contains("1.21.11")) {
            return 61;
        }
        if (v.contains("1.21.10")) {
            return 60;
        }
        if (v.contains("1.21.6")) {
            return 56;
        }
        if (v.contains("1.21.5")) {
            return 55;
        }
        if (v.contains("1.21.4")) {
            return 54;
        }
        if (v.contains("1.21.0")) {
            return 51;
        }
        if (v.contains("1.21.1") || v.contains("1.21")) {
            return 61;
        }
        if (v.contains("1.20.6") || v.contains("1.20.5") || v.contains("1.20")) {
            return 50;
        }
        if (v.contains("1.17")) {
            return 37;
        }
        if (v.contains("1.16.5") || v.contains("1.16")) {
            return 35;
        }
        if (v.contains("1.14.4") || v.contains("1.14")) {
            return 28;
        }
        if (v.contains("1.12.2") || v.contains("1.12")) {
            return 23;
        }
        if (v.contains("1.8.9") || v.contains("1.8")) {
            return 15;
        }
        if (v.contains("1.7.10") || v.contains("1.7")) {
            return 13;
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String getDetectedVersionString(Class<?> minecraftClass, ClassLoader ... preferredLoaders) {
        try {
            InputStream stream222;
            block46: {
                String string;
                stream222 = minecraftClass.getResourceAsStream("/version.json");
                try {
                    String ver2;
                    if (stream222 == null || (ver2 = NamedModernRuntimeDetector.parseVersionJson(stream222)) == null || ver2.isEmpty()) break block46;
                    string = ver2;
                }
                catch (Throwable ver2) {
                    if (stream222 == null) throw ver2;
                    try {
                        stream222.close();
                        throw ver2;
                    }
                    catch (Throwable throwable) {
                        ver2.addSuppressed(throwable);
                    }
                    throw ver2;
                }
                stream222.close();
                return string;
            }
            if (stream222 != null) {
                stream222.close();
            }
        }
        catch (Throwable stream222) {
            // empty catch block
        }
        try {
            Iterator<ClassLoader> stream322;
            block48: {
                String string;
                stream322 = minecraftClass.getResourceAsStream("version.json");
                try {
                    String ver;
                    if (stream322 == null || (ver = NamedModernRuntimeDetector.parseVersionJson((InputStream)((Object)stream322))) == null || ver.isEmpty()) break block48;
                    string = ver;
                }
                catch (Throwable ver) {
                    if (stream322 == null) throw ver;
                    try {
                        ((InputStream)((Object)stream322)).close();
                        throw ver;
                    }
                    catch (Throwable throwable) {
                        ver.addSuppressed(throwable);
                    }
                    throw ver;
                }
                ((InputStream)((Object)stream322)).close();
                return string;
            }
            if (stream322 != null) {
                ((InputStream)((Object)stream322)).close();
            }
        }
        catch (Throwable stream322) {
            // empty catch block
        }
        for (ClassLoader loader : NamedModernRuntimeDetector.candidateLoaders(preferredLoaders)) {
            if (loader == null) continue;
            try {
                InputStream stream422;
                block50: {
                    String string;
                    stream422 = loader.getResourceAsStream("version.json");
                    try {
                        String ver3;
                        if (stream422 == null || (ver3 = NamedModernRuntimeDetector.parseVersionJson(stream422)) == null || ver3.isEmpty()) break block50;
                        string = ver3;
                    }
                    catch (Throwable ver3) {
                        if (stream422 == null) throw ver3;
                        try {
                            stream422.close();
                            throw ver3;
                        }
                        catch (Throwable throwable) {
                            ver3.addSuppressed(throwable);
                        }
                        throw ver3;
                    }
                    stream422.close();
                    return string;
                }
                if (stream422 != null) {
                    stream422.close();
                }
            }
            catch (Throwable stream422) {
                // empty catch block
            }
            try {
                InputStream stream522;
                block52: {
                    String string;
                    stream522 = loader.getResourceAsStream("/version.json");
                    try {
                        String ver;
                        if (stream522 == null || (ver = NamedModernRuntimeDetector.parseVersionJson(stream522)) == null || ver.isEmpty()) break block52;
                        string = ver;
                    }
                    catch (Throwable ver) {
                        if (stream522 == null) throw ver;
                        try {
                            stream522.close();
                            throw ver;
                        }
                        catch (Throwable throwable) {
                            ver.addSuppressed(throwable);
                        }
                        throw ver;
                    }
                    stream522.close();
                    return string;
                }
                if (stream522 == null) continue;
                stream522.close();
            }
            catch (Throwable stream522) {
            }
        }
        for (ClassLoader loader : NamedModernRuntimeDetector.candidateLoaders(preferredLoaders)) {
            if (loader == null) continue;
            try {
                Class<?> sharedConstants = Class.forName("net.minecraft.SharedConstants", false, loader);
                Method method = sharedConstants.getMethod("getCurrentVersion", new Class[0]);
                Object worldVersion = method.invoke(null, new Object[0]);
                if (worldVersion == null) continue;
                try {
                    Method getNameMethod = worldVersion.getClass().getMethod("getName", new Class[0]);
                    String name = (String)getNameMethod.invoke(worldVersion, new Object[0]);
                    if (name != null && !name.isEmpty()) {
                        return name;
                    }
                }
                catch (Throwable getNameMethod) {
                    // empty catch block
                }
                try {
                    Method getIdMethod = worldVersion.getClass().getMethod("getId", new Class[0]);
                    String id = (String)getIdMethod.invoke(worldVersion, new Object[0]);
                    if (id == null || id.isEmpty()) continue;
                    return id;
                }
                catch (Throwable throwable) {
                }
            }
            catch (Throwable sharedConstants) {}
        }
        try {
            Method getLaunchedVersionMethod;
            String launched;
            Method getInstanceMethod = minecraftClass.getMethod("getInstance", new Class[0]);
            Object instance = getInstanceMethod.invoke(null, new Object[0]);
            if (instance != null && (launched = (String)(getLaunchedVersionMethod = minecraftClass.getMethod("getLaunchedVersion", new Class[0])).invoke(instance, new Object[0])) != null && !launched.isEmpty()) {
                return launched;
            }
        }
        catch (Throwable getInstanceMethod) {
            // empty catch block
        }
        try {
            Package pkg = minecraftClass.getPackage();
            if (pkg != null) {
                String implVer = pkg.getImplementationVersion();
                if (implVer != null && !implVer.isEmpty()) {
                    return implVer;
                }
                String specVer = pkg.getSpecificationVersion();
                if (specVer != null && !specVer.isEmpty()) {
                    return specVer;
                }
            }
        }
        catch (Throwable pkg) {
            // empty catch block
        }
        String sysVer = System.getProperty("minecraft.version");
        if (sysVer != null && !sysVer.isEmpty()) {
            return sysVer;
        }
        String lunarVer = System.getProperty("lunar.version");
        if (lunarVer != null && !lunarVer.isEmpty()) {
            return lunarVer;
        }
        String fabricVer = System.getProperty("fabric.gameVersion");
        if (fabricVer == null) return null;
        if (fabricVer.isEmpty()) return null;
        return fabricVer;
    }

    private static String parseVersionJson(InputStream stream) {
        try {
            JsonElement element = JsonParser.parseReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            if (!element.isJsonObject()) {
                return null;
            }
            JsonObject obj = element.getAsJsonObject();
            if (obj.has("id")) {
                return obj.get("id").getAsString();
            }
            if (obj.has("name")) {
                return obj.get("name").getAsString();
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return null;
    }

    private static boolean matchesModernMinecraftStructure(Class<?> minecraftClass) {
        try {
            Method getter = minecraftClass.getDeclaredMethod("getInstance", new Class[0]);
            if (!Modifier.isStatic(getter.getModifiers()) || getter.getReturnType() != minecraftClass) {
                return false;
            }
            try {
                Field instance = minecraftClass.getDeclaredField("instance");
                if (Modifier.isStatic(instance.getModifiers()) && instance.getType() == minecraftClass) {
                    return true;
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            for (Field field : minecraftClass.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers()) || field.getType() != minecraftClass) continue;
                return true;
            }
            return true;
        }
        catch (Throwable ignored) {
            return false;
        }
    }

    private static Class<?> resolveRuntimeClass(String runtimeInternalName, ClassLoader loader) {
        String binaryName = runtimeInternalName.replace('/', '.');
        try {
            return Class.forName(binaryName, false, loader);
        }
        catch (Throwable ignored) {
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
        if ((mappingLoader = NamedModernRuntimeDetector.class.getClassLoader()) != null) {
            loaders.add(mappingLoader);
        }
        try {
            ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
            if (systemLoader != null) {
                loaders.add(systemLoader);
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return loaders;
    }
}

