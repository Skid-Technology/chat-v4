package gg.vape.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

final class NamedLegacyRuntimeDetector {
   private static final String MINECRAFT_CLASS = "net/minecraft/client/Minecraft";

   private NamedLegacyRuntimeDetector() {
   }

   static boolean is1710Runtime(VanillaSrgMappings mappings, ClassLoader... preferredLoaders) {
      return isRuntime(
         mappings,
         new String[]{"net/minecraft/client/renderer/WorldRenderer", "net/minecraft/network/NetworkManager", "net/minecraft/util/ChunkCoordinates"},
         new String[]{"net/minecraft/util/BlockPos", "net/minecraft/util/math/BlockPos"},
         "theMinecraft",
         preferredLoaders
      );
   }

   static boolean is189Runtime(VanillaSrgMappings mappings, ClassLoader... preferredLoaders) {
      return isRuntime(
         mappings,
         new String[]{"net/minecraft/client/renderer/WorldRenderer", "net/minecraft/client/renderer/vertex/VertexFormat", "net/minecraft/util/BlockPos"},
         new String[]{"net/minecraft/util/ChunkCoordinates", "net/minecraft/util/math/BlockPos"},
         "theMinecraft",
         preferredLoaders
      );
   }

   static boolean is1122Runtime(VanillaSrgMappings mappings, ClassLoader... preferredLoaders) {
      return isRuntime(
         mappings,
         new String[]{"net/minecraft/client/renderer/BufferBuilder", "net/minecraft/network/play/client/CPacketPlayer", "net/minecraft/util/math/BlockPos"},
         new String[]{"net/minecraft/util/ChunkCoordinates", "net/minecraft/util/BlockPos"},
         "instance",
         preferredLoaders
      );
   }

   static boolean is1165Runtime(VanillaSrgMappings mappings, ClassLoader... preferredLoaders) {
      return isRuntime(
         mappings,
         new String[]{"net/minecraft/util/math/vector/Vector3d", "net/minecraft/network/play/client/CMovementPacket", "net/minecraft/util/math/BlockPos"},
         new String[]{"net/minecraft/util/ChunkCoordinates", "net/minecraft/util/BlockPos", "net/minecraft/client/renderer/BufferBuilder"},
         "instance",
         preferredLoaders
      );
   }

   private static boolean isRuntime(
      VanillaSrgMappings mappings, String[] requiredClasses, String[] forbiddenClasses, String instanceFieldName, ClassLoader... preferredLoaders
   ) {
      Class<?> minecraftClass = mappings.resolveClass("net/minecraft/client/Minecraft", preferredLoaders);
      if (minecraftClass != null && matchesMinecraftStructure(minecraftClass, instanceFieldName)) {
         ClassLoader gameLoader = minecraftClass.getClassLoader();

         for (String requiredClass : requiredClasses) {
            Class<?> resolvedClass = mappings.resolveClass(requiredClass, preferredLoaders);
            if (resolvedClass == null || resolvedClass.getClassLoader() != gameLoader) {
               return false;
            }
         }

         for (String forbiddenClass : forbiddenClasses) {
            if (mappings.resolveClass(forbiddenClass, preferredLoaders) != null) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean matchesMinecraftStructure(Class<?> minecraftClass, String instanceFieldName) {
      try {
         Method getter = minecraftClass.getDeclaredMethod("getMinecraft");
         Field instance = minecraftClass.getDeclaredField(instanceFieldName);
         return Modifier.isStatic(getter.getModifiers())
            && getter.getReturnType() == minecraftClass
            && Modifier.isStatic(instance.getModifiers())
            && instance.getType() == minecraftClass;
      } catch (NoSuchMethodException var4) {
         return false;
      } catch (NoSuchFieldException var5) {
         return false;
      } catch (SecurityException var6) {
         return false;
      } catch (LinkageError var7) {
         return false;
      }
   }
}
