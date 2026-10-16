/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.reflect;

import gg.vape.reflect.Vanilla262Mappings;

public final class Fabric262Mappings {
    private final static String FABRIC_LAUNCHER_CLASS = "net/fabricmc/loader/impl/launch/FabricLauncherBase";

    private Fabric262Mappings() {
    }

    public static Class<?> resolveClass(String sourceClassName, ClassLoader ... preferredLoaders) {
        return Vanilla262Mappings.resolveClass(sourceClassName, preferredLoaders);
    }

    public static boolean isRuntimePresent(ClassLoader ... preferredLoaders) {
        return Vanilla262Mappings.isRuntimePresent(preferredLoaders) && Vanilla262Mappings.resolveClass(FABRIC_LAUNCHER_CLASS, preferredLoaders) != null;
    }
}

