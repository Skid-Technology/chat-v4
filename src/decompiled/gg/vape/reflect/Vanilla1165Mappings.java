/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.reflect;

import gg.vape.mapping.runtime.ClassNameRemapTableV35V36Direct;
import gg.vape.mapping.runtime.ClassNameRemapTableV35V36Layered;
import gg.vape.reflect.NamedLegacyRuntimeDetector;
import gg.vape.reflect.VanillaSrgMappings;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public final class Vanilla1165Mappings {
    private static final VanillaSrgMappings MAPPINGS;

    private Vanilla1165Mappings() {
    }

    public static String remapClassName(String sourceClassName) {
        return MAPPINGS.remapClassName(sourceClassName);
    }

    public static Class<?> resolveClass(String sourceClassName, ClassLoader ... preferredLoaders) {
        return MAPPINGS.resolveClass(sourceClassName, preferredLoaders);
    }

    public static boolean isRuntimePresent(ClassLoader ... preferredLoaders) {
        return MAPPINGS.isRuntimePresent(preferredLoaders) || NamedLegacyRuntimeDetector.is1165Runtime(MAPPINGS, preferredLoaders);
    }

    static String lookupFieldSrgName(Field field) {
        return MAPPINGS.lookupFieldSrgName(field);
    }

    static String lookupMethodSrgName(Method method) {
        return MAPPINGS.lookupMethodSrgName(method);
    }

    public static int getClassMappingCount() {
        return MAPPINGS.getClassMappingCount();
    }

    public static int getFieldMappingCount() {
        return MAPPINGS.getFieldMappingCount();
    }

    public static int getMethodMappingCount() {
        return MAPPINGS.getMethodMappingCount();
    }

    static {
        String mcp;
        MAPPINGS = new VanillaSrgMappings("Minecraft 1.16.5", "/mappings/vanilla1165/joined.srg", "djz", "C", "F", "efo", "dwt");
        ClassNameRemapTableV35V36Direct direct = new ClassNameRemapTableV35V36Direct();
        ClassNameRemapTableV35V36Layered layered = new ClassNameRemapTableV35V36Layered();
        Map<String, String> directMappings = direct.getMappings();
        for (Map.Entry<String, String> entry : directMappings.entrySet()) {
            String source = entry.getKey();
            String mojang = entry.getValue();
            mcp = layered.lookupRemappedClassName(source);
            if (mcp == null) {
                mcp = source;
            }
            MAPPINGS.addAlias(mojang, mcp);
            MAPPINGS.addAlias(source, mcp);
            MAPPINGS.addAlias(mojang, source);
        }
        Map<String, String> layeredMappings = layered.getMappings();
        for (Map.Entry<String, String> entry : layeredMappings.entrySet()) {
            String source = entry.getKey();
            mcp = entry.getValue();
            MAPPINGS.addAlias(source, mcp);
        }
        MAPPINGS.addAlias("net/minecraft/world/item/ShieldItem", "net/minecraft/item/ShieldItem");
        MAPPINGS.addAlias("net/minecraft/block/BlockChest$Type", "net/minecraft/state/properties/ChestType");
        MAPPINGS.addAlias("net/minecraft/world/level/block/state/properties/ChestType", "net/minecraft/state/properties/ChestType");
    }
}

