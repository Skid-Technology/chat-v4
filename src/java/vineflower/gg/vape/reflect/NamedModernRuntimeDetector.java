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

public final class NamedModernRuntimeDetector {
   private static final String MINECRAFT_CLASS = "net/minecraft/client/Minecraft";

   private NamedModernRuntimeDetector() {
   }

   public static boolean is12111Runtime(VanillaSrgMappings mappings, ClassLoader... preferredLoaders) {
      Class<?> minecraftClass = mappings.resolveClass("net/minecraft/client/Minecraft", preferredLoaders);
      if (minecraftClass != null && matchesModernMinecraftStructure(minecraftClass)) {
         String version = getDetectedVersionString(minecraftClass, preferredLoaders);
         if (version != null && !version.isEmpty()) {
            String v = version.toLowerCase();
            if (v.contains("26.2")
               || v.contains("26.1")
               || v.contains("1.20")
               || v.contains("1.19")
               || v.contains("1.18")
               || v.contains("1.17")
               || v.contains("1.16")
               || v.contains("1.12")
               || v.contains("1.8")
               || v.contains("1.7")) {
               return false;
            }

            if (v.contains("1.21")) {
               return true;
            }
         }

         boolean hasMace = mappings.resolveClass("net/minecraft/world/item/MaceItem", preferredLoaders) != null;
         boolean hasLevelExtractor = mappings.resolveClass("net/minecraft/client/renderer/extract/LevelExtractor", preferredLoaders) != null;
         return hasMace && !hasLevelExtractor;
      } else {
         return false;
      }
   }

   public static boolean is1206Runtime(VanillaSrgMappings mappings, ClassLoader... preferredLoaders) {
      Class<?> minecraftClass = mappings.resolveClass("net/minecraft/client/Minecraft", preferredLoaders);
      if (minecraftClass != null && matchesModernMinecraftStructure(minecraftClass)) {
         String version = getDetectedVersionString(minecraftClass, preferredLoaders);
         if (version != null && !version.isEmpty()) {
            String v = version.toLowerCase();
            return v.contains("1.20.6") || v.contains("1.20.5") || v.contains("1.20");
         } else {
            boolean hasArmorTrim = mappings.resolveClass("net/minecraft/world/item/armortrim/ArmorTrim", preferredLoaders) != null;
            boolean hasMace = mappings.resolveClass("net/minecraft/world/item/MaceItem", preferredLoaders) != null;
            return hasArmorTrim && !hasMace;
         }
      } else {
         return false;
      }
   }

   public static int detectVersion(ClassLoader... preferredLoaders) {
      for (ClassLoader loader : candidateLoaders(preferredLoaders)) {
         Class<?> minecraftClass = resolveRuntimeClass("net/minecraft/client/Minecraft", loader);
         if (minecraftClass != null && matchesModernMinecraftStructure(minecraftClass)) {
            String version = getDetectedVersionString(minecraftClass, preferredLoaders);
            if (version != null && !version.isEmpty()) {
               int protocol = parseVersionToProtocol(version);
               if (protocol != 0) {
                  return protocol;
               }
            }

            if (resolveRuntimeClass("net/minecraft/client/renderer/extract/LevelExtractor", loader) != null) {
               return 110;
            }

            if (resolveRuntimeClass("net/minecraft/world/item/MaceItem", loader) != null) {
               return 61;
            }

            if (resolveRuntimeClass("net/minecraft/world/item/armortrim/ArmorTrim", loader) != null) {
               return 50;
            }
         }
      }

      return 0;
   }

   public static int parseVersionToProtocol(String version) {
      if (version == null) {
         return 0;
      } else {
         String v = version.trim().toLowerCase();
         if (v.contains("26.2")) {
            return 110;
         } else if (v.contains("26.1")) {
            return 100;
         } else if (v.contains("1.21.11")) {
            return 61;
         } else if (v.contains("1.21.10")) {
            return 60;
         } else if (v.contains("1.21.6")) {
            return 56;
         } else if (v.contains("1.21.5")) {
            return 55;
         } else if (v.contains("1.21.4")) {
            return 54;
         } else if (v.contains("1.21.0")) {
            return 51;
         } else if (v.contains("1.21.1") || v.contains("1.21")) {
            return 61;
         } else if (v.contains("1.20.6") || v.contains("1.20.5") || v.contains("1.20")) {
            return 50;
         } else if (v.contains("1.17")) {
            return 37;
         } else if (v.contains("1.16.5") || v.contains("1.16")) {
            return 35;
         } else if (v.contains("1.14.4") || v.contains("1.14")) {
            return 28;
         } else if (v.contains("1.12.2") || v.contains("1.12")) {
            return 23;
         } else if (v.contains("1.8.9") || v.contains("1.8")) {
            return 15;
         } else {
            return !v.contains("1.7.10") && !v.contains("1.7") ? 0 : 13;
         }
      }
   }

   public static String getDetectedVersionString(Class<?> minecraftClass, ClassLoader... preferredLoaders) {
      try {
         InputStream stream = minecraftClass.getResourceAsStream("/version.json");

         label263: {
            String fabricVer;
            try {
               if (stream == null) {
                  break label263;
               }

               String ver = parseVersionJson(stream);
               if (ver == null || ver.isEmpty()) {
                  break label263;
               }

               fabricVer = ver;
            } catch (Throwable var24) {
               if (stream != null) {
                  try {
                     stream.close();
                  } catch (Throwable var17) {
                     var24.addSuppressed(var17);
                  }
               }

               throw var24;
            }

            stream.close();
            return fabricVer;
         }

         if (stream != null) {
            stream.close();
         }
      } catch (Throwable var25) {
      }

      try {
         InputStream stream = minecraftClass.getResourceAsStream("version.json");

         label246: {
            String var38;
            try {
               if (stream == null) {
                  break label246;
               }

               String ver = parseVersionJson(stream);
               if (ver == null || ver.isEmpty()) {
                  break label246;
               }

               var38 = ver;
            } catch (Throwable var22) {
               if (stream != null) {
                  try {
                     stream.close();
                  } catch (Throwable var16) {
                     var22.addSuppressed(var16);
                  }
               }

               throw var22;
            }

            stream.close();
            return var38;
         }

         if (stream != null) {
            stream.close();
         }
      } catch (Throwable var23) {
      }

      for (ClassLoader loader : candidateLoaders(preferredLoaders)) {
         if (loader != null) {
            try {
               InputStream stream = loader.getResourceAsStream("version.json");

               label225: {
                  String worldVersion;
                  try {
                     if (stream == null) {
                        break label225;
                     }

                     String ver = parseVersionJson(stream);
                     if (ver == null || ver.isEmpty()) {
                        break label225;
                     }

                     worldVersion = ver;
                  } catch (Throwable var20) {
                     if (stream != null) {
                        try {
                           stream.close();
                        } catch (Throwable var14) {
                           var20.addSuppressed(var14);
                        }
                     }

                     throw var20;
                  }

                  stream.close();
                  return worldVersion;
               }

               if (stream != null) {
                  stream.close();
               }
            } catch (Throwable var21) {
            }

            try {
               InputStream stream = loader.getResourceAsStream("/version.json");

               String var48;
               label212: {
                  try {
                     if (stream != null) {
                        String ver = parseVersionJson(stream);
                        if (ver != null && !ver.isEmpty()) {
                           var48 = ver;
                           break label212;
                        }
                     }
                  } catch (Throwable var18) {
                     if (stream != null) {
                        try {
                           stream.close();
                        } catch (Throwable var15) {
                           var18.addSuppressed(var15);
                        }
                     }

                     throw var18;
                  }

                  if (stream != null) {
                     stream.close();
                  }
                  continue;
               }

               stream.close();
               return var48;
            } catch (Throwable var19) {
            }
         }
      }

      for (ClassLoader loaderx : candidateLoaders(preferredLoaders)) {
         if (loaderx != null) {
            try {
               Class<?> sharedConstants = Class.forName("net.minecraft.SharedConstants", false, loaderx);
               Method method = sharedConstants.getMethod("getCurrentVersion");
               Object worldVersion = method.invoke(null);
               if (worldVersion != null) {
                  try {
                     Method getNameMethod = worldVersion.getClass().getMethod("getName");
                     String name = (String)getNameMethod.invoke(worldVersion);
                     if (name != null && !name.isEmpty()) {
                        return name;
                     }
                  } catch (Throwable var12) {
                  }

                  try {
                     Method getIdMethod = worldVersion.getClass().getMethod("getId");
                     String id = (String)getIdMethod.invoke(worldVersion);
                     if (id != null && !id.isEmpty()) {
                        return id;
                     }
                  } catch (Throwable var11) {
                  }
               }
            } catch (Throwable var13) {
            }
         }
      }

      try {
         Method getInstanceMethod = minecraftClass.getMethod("getInstance");
         Object instance = getInstanceMethod.invoke(null);
         if (instance != null) {
            Method getLaunchedVersionMethod = minecraftClass.getMethod("getLaunchedVersion");
            String launched = (String)getLaunchedVersionMethod.invoke(instance);
            if (launched != null && !launched.isEmpty()) {
               return launched;
            }
         }
      } catch (Throwable var10) {
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
      } catch (Throwable var9) {
      }

      String sysVer = System.getProperty("minecraft.version");
      if (sysVer != null && !sysVer.isEmpty()) {
         return sysVer;
      } else {
         String lunarVer = System.getProperty("lunar.version");
         if (lunarVer != null && !lunarVer.isEmpty()) {
            return lunarVer;
         } else {
            String fabricVer = System.getProperty("fabric.gameVersion");
            return fabricVer != null && !fabricVer.isEmpty() ? fabricVer : null;
         }
      }
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
      } catch (Throwable var3) {
      }

      return null;
   }

   private static boolean matchesModernMinecraftStructure(Class<?> minecraftClass) {
      try {
         Method getter = minecraftClass.getDeclaredMethod("getInstance");
         if (Modifier.isStatic(getter.getModifiers()) && getter.getReturnType() == minecraftClass) {
            try {
               Field instance = minecraftClass.getDeclaredField("instance");
               if (Modifier.isStatic(instance.getModifiers()) && instance.getType() == minecraftClass) {
                  return true;
               }
            } catch (Throwable var6) {
            }

            for (Field field : minecraftClass.getDeclaredFields()) {
               if (Modifier.isStatic(field.getModifiers()) && field.getType() == minecraftClass) {
                  return true;
               }
            }

            return true;
         } else {
            return false;
         }
      } catch (Throwable var7) {
         return false;
      }
   }

   private static Class<?> resolveRuntimeClass(String runtimeInternalName, ClassLoader loader) {
      String binaryName = runtimeInternalName.replace('/', '.');

      try {
         return Class.forName(binaryName, false, loader);
      } catch (Throwable var4) {
         return null;
      }
   }

   private static Set<ClassLoader> candidateLoaders(ClassLoader... preferredLoaders) {
      Set<ClassLoader> loaders = new LinkedHashSet<>();
      if (preferredLoaders != null) {
         for (ClassLoader loader : preferredLoaders) {
            if (loader != null) {
               loaders.add(loader);
            }
         }
      }

      ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
      if (contextLoader != null) {
         loaders.add(contextLoader);
      }

      ClassLoader mappingLoader = NamedModernRuntimeDetector.class.getClassLoader();
      if (mappingLoader != null) {
         loaders.add(mappingLoader);
      }

      try {
         ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
         if (systemLoader != null) {
            loaders.add(systemLoader);
         }
      } catch (Throwable var6) {
      }

      return loaders;
   }
}
