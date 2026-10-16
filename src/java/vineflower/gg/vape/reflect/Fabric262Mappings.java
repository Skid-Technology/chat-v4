package gg.vape.reflect;

public final class Fabric262Mappings {
   private static final String FABRIC_LAUNCHER_CLASS = "net/fabricmc/loader/impl/launch/FabricLauncherBase";

   private Fabric262Mappings() {
   }

   public static Class<?> resolveClass(String sourceClassName, ClassLoader... preferredLoaders) {
      return Vanilla262Mappings.resolveClass(sourceClassName, preferredLoaders);
   }

   public static boolean isRuntimePresent(ClassLoader... preferredLoaders) {
      return Vanilla262Mappings.isRuntimePresent(preferredLoaders)
         && Vanilla262Mappings.resolveClass("net/fabricmc/loader/impl/launch/FabricLauncherBase", preferredLoaders) != null;
   }
}
