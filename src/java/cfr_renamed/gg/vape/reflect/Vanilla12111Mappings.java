/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.reflect;

import gg.vape.reflect.NamedModernRuntimeDetector;
import gg.vape.reflect.VanillaSrgMappings;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class Vanilla12111Mappings {
    private final static VanillaSrgMappings MAPPINGS = new VanillaSrgMappings("Minecraft 1.21.11", "/mappings/vanilla12111/joined.srg", "gfj", "V", "A", "hif", "hnh", "hne", "dwo");

    private Vanilla12111Mappings() {
    }

    public static String remapClassName(String sourceClassName) {
        return MAPPINGS.remapClassName(sourceClassName);
    }

    public static Class<?> resolveClass(String sourceClassName, ClassLoader ... preferredLoaders) {
        return MAPPINGS.resolveClass(sourceClassName, preferredLoaders);
    }

    public static boolean isRuntimePresent(ClassLoader ... preferredLoaders) {
        return MAPPINGS.isRuntimePresent(preferredLoaders) || NamedModernRuntimeDetector.is12111Runtime(MAPPINGS, preferredLoaders);
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
}

