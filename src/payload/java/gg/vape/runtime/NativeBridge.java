package gg.vape.runtime;

import gg.vape.Vape;
import gg.vape.lifecycle.VapeErrorLogger;
import gg.vape.reflect.Badlion189Mappings;
import gg.vape.reflect.Fabric12111Mappings;
import gg.vape.reflect.Fabric262Mappings;
import gg.vape.reflect.NamedModernRuntimeDetector;
import gg.vape.reflect.Type;
import gg.vape.reflect.Vanilla1122Mappings;
import gg.vape.reflect.Vanilla1165Mappings;
import gg.vape.reflect.Vanilla1206Mappings;
import gg.vape.reflect.Vanilla12111Mappings;
import gg.vape.reflect.Vanilla1710Mappings;
import gg.vape.reflect.Vanilla189Mappings;
import gg.vape.reflect.Vanilla262Mappings;
import gg.vape.ui.click.GuiScreenNativeCallbackBridge;
import gg.vape.utils.Base64Util;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.glu.GLU;

public class NativeBridge {
   private static final String DEFAULT_CONFIG_JSON = "{\"friends\":[],\"profiles\":{},\"otherdata\":[{\"frames\":[{\"title\":\"Combat\",\"x\":32,\"y\":32,\"visible\":true,\"pinned\":false},{\"title\":\"Render\",\"x\":144,\"y\":32,\"visible\":true,\"pinned\":false},{\"title\":\"Utility\",\"x\":256,\"y\":32,\"visible\":true,\"pinned\":false},{\"title\":\"World\",\"x\":368,\"y\":32,\"visible\":true,\"pinned\":false},{\"title\":\"Inventory\",\"x\":480,\"y\":32,\"visible\":true,\"pinned\":false},{\"title\":\"Favorites\",\"x\":592,\"y\":32,\"visible\":true,\"pinned\":false},{\"title\":\"Settings\",\"x\":32,\"y\":32,\"visible\":false,\"pinned\":false},{\"title\":\"ModuleSearch\",\"x\":32,\"y\":32,\"visible\":false,\"pinned\":false}]}]}";
   private static boolean forgeAbsent = true;
   private static volatile int vanillaMappingVersion;
   private static volatile boolean badlion189Runtime;
   private static volatile boolean fabric12111Runtime;
   private static volatile boolean fabric262Runtime;
   static boolean alphaTestWasEnabled;
   private static Method glGetFloatMethod;
   private static Method glGetIntegerVectorMethod;
   private static Method glVertexPointerMethod;
   private static Method glColorPointerMethod;
   private static Method glTexCoordPointerMethod;

   private static void resolveGlGetFloatMethod() {
      if (glGetFloatMethod == null) {
         try {
            glGetFloatMethod = GL11.class.getMethod("glGetFloatv", int.class, FloatBuffer.class);
         } catch (NoSuchMethodException var3) {
            try {
               glGetFloatMethod = GL11.class.getMethod("glGetFloat", int.class, FloatBuffer.class);
            } catch (NoSuchMethodException var2) {
               throw new IllegalStateException("Unable to resolve OpenGL float state method", var2);
            }
         }
      }
   }

   private static void readGlFloats(int state, FloatBuffer destination) {
      resolveGlGetFloatMethod();

      try {
         glGetFloatMethod.invoke(null, state, destination);
      } catch (ReflectiveOperationException var3) {
         throw new IllegalStateException("Unable to read OpenGL state " + state, var3);
      }
   }

   private static void readGlIntegers(int state, IntBuffer destination) {
      if (glGetIntegerVectorMethod == null) {
         try {
            glGetIntegerVectorMethod = GL11.class.getMethod("glGetIntegerv", int.class, IntBuffer.class);
         } catch (NoSuchMethodException var6) {
            try {
               glGetIntegerVectorMethod = GL11.class.getMethod("glGetInteger", int.class, IntBuffer.class);
            } catch (NoSuchMethodException var5) {
               throw new IllegalStateException("Unable to resolve OpenGL integer state method", var5);
            }
         }
      }

      try {
         glGetIntegerVectorMethod.invoke(null, state, destination);
      } catch (ReflectiveOperationException var4) {
         throw new IllegalStateException("Unable to read OpenGL integer state " + state, var4);
      }
   }

   private static Method resolveFloatPointerMethod(String name) {
      try {
         return GL11.class.getMethod(name, int.class, int.class, int.class, FloatBuffer.class);
      } catch (NoSuchMethodException var4) {
         try {
            return GL11.class.getMethod(name, int.class, int.class, FloatBuffer.class);
         } catch (NoSuchMethodException var3) {
            throw new IllegalStateException("Unable to resolve OpenGL pointer method " + name, var3);
         }
      }
   }

   private static void invokeFloatPointer(Method method, int size, int stride, FloatBuffer buffer, String name) {
      try {
         if (method.getParameterCount() == 4) {
            method.invoke(null, size, 5126, stride, buffer);
         } else {
            method.invoke(null, size, stride, buffer);
         }
      } catch (ReflectiveOperationException var6) {
         throw new IllegalStateException("Unable to invoke OpenGL pointer method " + name, var6);
      }
   }

   public static void vertexPointer(int size, int stride, FloatBuffer buffer) {
      if (glVertexPointerMethod == null) {
         glVertexPointerMethod = resolveFloatPointerMethod("glVertexPointer");
      }

      invokeFloatPointer(glVertexPointerMethod, size, stride, buffer, "glVertexPointer");
   }

   public static void colorPointer(int size, int stride, FloatBuffer buffer) {
      if (glColorPointerMethod == null) {
         glColorPointerMethod = resolveFloatPointerMethod("glColorPointer");
      }

      invokeFloatPointer(glColorPointerMethod, size, stride, buffer, "glColorPointer");
   }

   public static void texCoordPointer(int size, int stride, FloatBuffer buffer) {
      if (glTexCoordPointerMethod == null) {
         glTexCoordPointerMethod = resolveFloatPointerMethod("glTexCoordPointer");
      }

      invokeFloatPointer(glTexCoordPointerMethod, size, stride, buffer, "glTexCoordPointer");
   }

   public static int getGlIntegerSafe(int state) {
      try {
         return GL11.glGetInteger(state);
      } catch (Throwable var2) {
         return 0;
      }
   }

   public static void bindBufferSafe(int target, int buffer) {
      try {
         GL15.glBindBuffer(target, buffer);
      } catch (Throwable var3) {
      }
   }

   public static void bindVertexArraySafe(int array) {
      try {
         GL30.glBindVertexArray(array);
      } catch (Throwable var2) {
      }
   }

   public static int ss_3(String value) {
      return 0;
   }

   public static int sts() {
      return 1;
   }

   public static void smdp(int mode, int value) {
      smd(mode, value);
   }

   public static Object grh() {
      return null;
   }

   public static int mf(int fontId, int style, String text) {
      return 0;
   }

   public static void exit(boolean forced) {
      System.out.println("exit " + forced);
   }

   public static byte[] gt(String key) {
      return new byte[0];
   }

   public static void dc() {
   }

   public static String[] gcf(Class<?> targetClass) {
      if (targetClass == null) {
         return new String[0];
      } else {
         Field[] fields = targetClass.getDeclaredFields();
         String[] names = new String[fields.length];

         for (int index = 0; index < fields.length; index++) {
            names[index] = fields[index].getName();
         }

         return names;
      }
   }

   public static int gts() {
      return 1;
   }

   public static boolean isForgeAbsent() {
      return forgeAbsent;
   }

   public static double[] trn(double worldX, double worldY, double worldZ) {
      FloatBuffer modelViewMatrix = BufferUtils.createFloatBuffer(16);
      FloatBuffer projectionMatrix = BufferUtils.createFloatBuffer(16);
      IntBuffer viewport = BufferUtils.createIntBuffer(16);
      readGlFloats(2982, modelViewMatrix);
      readGlFloats(2983, projectionMatrix);
      readGlIntegers(2978, viewport);
      FloatBuffer screenPosition = BufferUtils.createFloatBuffer(3);
      GLU.gluProject((float)worldX, (float)worldY, (float)worldZ, modelViewMatrix, projectionMatrix, viewport, screenPosition);
      return new double[]{screenPosition.get(0), screenPosition.get(1), screenPosition.get(2)};
   }

   public static boolean gtcf(Object target, int index, int flags) {
      return false;
   }

   public static native String gkn(long var0);

   public static void mb(int messageCode) {
   }

   public static native short gks(int var0);

   public static void rs(int phase, double width, double height) {
      GL11.glClear(256);
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      GL11.glOrtho(0.0, width, height, 0.0, 1000.0, 3000.0);
      GL11.glMatrixMode(5888);
      GL11.glLoadIdentity();
      GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
      if (phase > 0) {
         if (alphaTestWasEnabled) {
            GL11.glEnable(3008);
         }
      } else {
         alphaTestWasEnabled = GL11.glIsEnabled(3008);
         if (alphaTestWasEnabled) {
            GL11.glDisable(3008);
         }
      }
   }

   public static Class<?> gc(String internalName) {
      try {
         return Class.forName(internalName.replace("/", "."));
      } catch (Throwable var2) {
         return null;
      }
   }

   public static native String gat();

   public static Object[] gco(Class<?> targetClass) {
      return new Object[0];
   }

   public static native byte[] gcb(Class<?> var0);

   public static native void trs(int var0);

   public static String cs(int stringId) {
      return "";
   }

   public static native byte[] gfb(String var0);

   public static void scm(String sourceName, String mappedName) {
   }

   public static Class<?> gcj(String descriptor) {
      try {
         return descriptor.startsWith("[")
            ? Class.forName(descriptor.substring(2, descriptor.length() - 1).replace("/", "."))
            : Class.forName(Type.getType(descriptor.substring(1, descriptor.length() - 1)).getClassName());
      } catch (Exception var2) {
         return null;
      }
   }

   public static String gp(String key) {
      return "all".equals(key)
         ? Base64Util.encodeUtf8Base64(
            "{\"friends\":[],\"profiles\":{},\"otherdata\":[{\"frames\":[{\"title\":\"Combat\",\"x\":32,\"y\":32,\"visible\":true,\"pinned\":false},{\"title\":\"Render\",\"x\":144,\"y\":32,\"visible\":true,\"pinned\":false},{\"title\":\"Utility\",\"x\":256,\"y\":32,\"visible\":true,\"pinned\":false},{\"title\":\"World\",\"x\":368,\"y\":32,\"visible\":true,\"pinned\":false},{\"title\":\"Inventory\",\"x\":480,\"y\":32,\"visible\":true,\"pinned\":false},{\"title\":\"Favorites\",\"x\":592,\"y\":32,\"visible\":true,\"pinned\":false},{\"title\":\"Settings\",\"x\":32,\"y\":32,\"visible\":false,\"pinned\":false},{\"title\":\"ModuleSearch\",\"x\":32,\"y\":32,\"visible\":false,\"pinned\":false}]}]}"
         )
         : "";
   }

   public static void test() {
   }

   public static double gshv2(int fontId, String text) {
      return 0.0;
   }

   public static String gcs(Class<?> targetClass) {
      return targetClass == null ? "" : Type.getDescriptor(targetClass);
   }

   public static native int mvk(int var0, int var1);

   public static double gsh(int fontId, String text) {
      return 0.0;
   }

   public static void start() throws Throwable {
      forgeAbsent = !isClassPresent("net.minecraftforge.common.ForgeVersion")
         && !isClassPresent("net.minecraftforge.fml.loading.FMLLoader")
         && !isClassPresent("net.neoforged.neoforge.common.NeoForgeVersion")
         && !isClassPresent("net.neoforged.fml.loading.FMLLoader");
      Vape vape = new Vape();
      invokeVoidInit(vape, "loadMappings");
      if (badlion189Runtime) {
         sce("Runtime profile: Badlion Client 1.8.9 (vanilla SRG namespace)");
      }

      sce("LOAD initAccountInfo");
      if (!vape.initAccountInfo()) {
         try {
            VapeErrorLogger.getInstance().logError("WARN initAccountInfo; continuing without account information");
         } catch (Throwable var2) {
         }

         sce("WARN initAccountInfo; continuing without account information");
      } else {
         sce("OK initAccountInfo");
      }

      invokeVoidInit(vape, "initializeManagers");
   }

   private static void invokeVoidInit(Vape vape, String name) {
      for (Method method : Vape.class.getDeclaredMethods()) {
         if (method.getName().equals(name) && method.getReturnType().equals(void.class)) {
            sce("LOAD " + name);
            method.setAccessible(true);

            try {
               method.invoke(vape);
               sce("OK " + name);
            } catch (InvocationTargetException var8) {
               logThrowable(name, (Throwable)(var8.getCause() != null ? var8.getCause() : var8));
            } catch (Throwable var9) {
               logThrowable(name, var9);
            }

            return;
         }
      }

      try {
         VapeErrorLogger.getInstance().logError("MISSING void " + name + "()");
      } catch (Throwable var7) {
      }

      sce("MISSING void " + name + "()");
   }

   private static void logThrowable(String context, Throwable error) {
      try {
         VapeErrorLogger.getInstance().logThrowable(error);
      } catch (Throwable var6) {
      }

      int depth = 0;

      for (Throwable current = error; current != null && depth < 8; depth++) {
         sce("EXC " + context + " -> " + current.getClass().getName() + ": " + current.getMessage());
         StackTraceElement[] frames = current.getStackTrace();

         for (int frameIndex = 0; frameIndex < frames.length && frameIndex < 12; frameIndex++) {
            sce("    at " + frames[frameIndex].toString());
         }

         current = current.getCause();
      }
   }

   public static double gswv2(int fontId, String text) {
      return 0.0;
   }

   public static int ds(int fontId, String text, double x, double y, int color) {
      return 0;
   }

   public static double gsw(int fontId, String text) {
      return 0.0;
   }

   public static void su(String username) {
   }

   public static native void cpy(String var0);

   public static long smpm(boolean pressed, long windowHandle, int button, long cursorPosition, long extraInfo) {
      return 0L;
   }

   public static void rl() {
   }

   public static String[] gcm(Class<?> targetClass) {
      if (targetClass == null) {
         return new String[0];
      } else {
         Method[] methods = targetClass.getDeclaredMethods();
         String[] names = new String[methods.length];

         for (int index = 0; index < methods.length; index++) {
            names[index] = methods[index].getName();
         }

         return names;
      }
   }

   public static int gk() {
      return 0;
   }

   public static native void smd(int var0, int var1);

   public static void rsc() {
   }

   public static void updc(String serverDescription, String clientDescription) {
   }

   public static void fs() {
   }

   public static native int dsv2(int var0, String var1, double var2, double var4, int var6, float var7);

   public static int gmv() {
      Throwable lastFailure = null;

      try {
         Object minorVersion = readStaticField("net.minecraftforge.common.ForgeVersion", "minorVersion");
         if (minorVersion instanceof Number) {
            return ((Number)minorVersion).intValue();
         }

         new IllegalStateException("ForgeVersion.minorVersion is not numeric");
      } catch (Throwable var12) {
      }

      try {
         Object forgeVersion = readStaticField("net.minecraftforge.fml.loading.FMLLoader", "forgeVersion");
         int parsedVersion = parseForgeVersion(forgeVersion);
         if (parsedVersion >= 0) {
            return parsedVersion;
         }

         new IllegalStateException("FMLLoader.forgeVersion is not supported: " + forgeVersion);
      } catch (Throwable var11) {
      }

      try {
         Object forgeVersion = readStaticField("net.minecraftforge.common.ForgeVersion", "forgeVersion");
         int parsedVersion = parseForgeVersion(forgeVersion);
         if (parsedVersion >= 0) {
            return parsedVersion;
         }

         new IllegalStateException("ForgeVersion.forgeVersion is not supported: " + forgeVersion);
      } catch (Throwable var10) {
      }

      try {
         Class<?> fmlLoader = Class.forName("net.minecraftforge.fml.loading.FMLLoader");
         Object versionInfo = fmlLoader.getMethod("versionInfo").invoke(null);
         Object forgeVersion2 = versionInfo.getClass().getMethod("forgeVersion").invoke(versionInfo);
         int parsedVersion2 = parseForgeVersion(forgeVersion2);
         if (parsedVersion2 >= 0) {
            return parsedVersion2;
         }

         lastFailure = new IllegalStateException("FMLLoader.versionInfo().forgeVersion() is not supported: " + forgeVersion2);
      } catch (Throwable var9) {
         lastFailure = var9;
      }

      try {
         Object forgeVersion = readStaticField("net.neoforged.neoforge.common.NeoForgeVersion", "MOD_VERSION");
         int parsedVersion = parseForgeVersion(forgeVersion);
         if (parsedVersion >= 0) {
            return parsedVersion;
         }
      } catch (Throwable var8) {
      }

      try {
         Class<?> neoFmlLoader = Class.forName("net.neoforged.fml.loading.FMLLoader");
         Object versionInfo = neoFmlLoader.getMethod("versionInfo").invoke(null);
         Object mcVersion = versionInfo.getClass().getMethod("mcVersion").invoke(versionInfo);
         int parsedVersion2 = parseForgeVersion(mcVersion);
         if (parsedVersion2 >= 0) {
            return parsedVersion2;
         }
      } catch (Throwable var7) {
      }

      int vanillaVersion = detectVanillaMappingVersion(Thread.currentThread().getContextClassLoader(), NativeBridge.class.getClassLoader());
      if (vanillaVersion != 0) {
         return vanillaVersion;
      } else {
         IllegalStateException failure = new IllegalStateException("Unable to determine Minecraft/Forge version");
         if (lastFailure != null) {
            failure.initCause(lastFailure);
         }

         throw failure;
      }
   }

   private static boolean isClassPresent(String className) {
      try {
         Class.forName(className, false, NativeBridge.class.getClassLoader());
         return true;
      } catch (ClassNotFoundException var2) {
         return false;
      }
   }

   private static int detectVanillaMappingVersion(ClassLoader... preferredLoaders) {
      int detectedVersion = vanillaMappingVersion;
      if (detectedVersion != 0) {
         return detectedVersion;
      } else {
         boolean vanilla1710 = Vanilla1710Mappings.isRuntimePresent(preferredLoaders);
         boolean badlion189 = Badlion189Mappings.isRuntimePresent(preferredLoaders);
         boolean vanilla189 = badlion189 || Vanilla189Mappings.isRuntimePresent(preferredLoaders);
         boolean vanilla1122 = Vanilla1122Mappings.isRuntimePresent(preferredLoaders);
         boolean vanilla1165 = Vanilla1165Mappings.isRuntimePresent(preferredLoaders);
         boolean vanilla1206 = Vanilla1206Mappings.isRuntimePresent(preferredLoaders);
         boolean vanilla12111 = Vanilla12111Mappings.isRuntimePresent(preferredLoaders);
         boolean fabric12111 = Fabric12111Mappings.isRuntimePresent(preferredLoaders);
         boolean vanilla262 = Vanilla262Mappings.isRuntimePresent(preferredLoaders);
         boolean fabric262 = Fabric262Mappings.isRuntimePresent(preferredLoaders);
         int matchingVersions = (vanilla1710 ? 1 : 0)
            + (vanilla189 ? 1 : 0)
            + (vanilla1122 ? 1 : 0)
            + (vanilla1165 ? 1 : 0)
            + (vanilla1206 ? 1 : 0)
            + (!vanilla12111 && !fabric12111 ? 0 : 1)
            + (!vanilla262 && !fabric262 ? 0 : 1);
         if (matchingVersions == 1) {
            badlion189Runtime = badlion189;
            fabric12111Runtime = fabric12111;
            fabric262Runtime = fabric262;
            detectedVersion = vanilla1710
               ? 13
               : (vanilla189 ? 15 : (vanilla1122 ? 23 : (vanilla1165 ? 35 : (vanilla1206 ? 50 : (!vanilla12111 && !fabric12111 ? 110 : 61)))));
         }

         if (detectedVersion == 0) {
            detectedVersion = NamedModernRuntimeDetector.detectVersion(preferredLoaders);
            if (detectedVersion != 54 && detectedVersion != 55 && detectedVersion != 56 && detectedVersion != 60 && detectedVersion != 61) {
               if ((detectedVersion == 100 || detectedVersion == 110) && Fabric262Mappings.isRuntimePresent(preferredLoaders)) {
                  fabric262Runtime = true;
               }
            } else if (Fabric12111Mappings.isRuntimePresent(preferredLoaders)) {
               fabric12111Runtime = true;
            }
         }

         if (detectedVersion != 0) {
            vanillaMappingVersion = detectedVersion;
         }

         return detectedVersion;
      }
   }

   private static Object readStaticField(String className, String fieldName) throws Exception {
      Class<?> owner = Class.forName(className);

      Field field;
      try {
         field = owner.getField(fieldName);
      } catch (NoSuchFieldException var5) {
         field = owner.getDeclaredField(fieldName);
      }

      field.setAccessible(true);
      return field.get(null);
   }

   private static int parseForgeVersion(Object value) {
      if (value == null) {
         return -1;
      } else {
         String text = String.valueOf(value).trim();
         int start = 0;

         while (start < text.length() && !Character.isDigit(text.charAt(start))) {
            start++;
         }

         int end = start;

         while (end < text.length() && Character.isDigit(text.charAt(end))) {
            end++;
         }

         if (start == end) {
            return -1;
         } else {
            try {
               int parsed = Integer.parseInt(text.substring(start, end));
               if (parsed == 62) {
                  return 100;
               } else if (parsed == 65) {
                  return 110;
               } else {
                  return isKnownVersionId(parsed) ? parsed : -1;
               }
            } catch (NumberFormatException var5) {
               return -1;
            }
         }
      }
   }

   private static boolean isKnownVersionId(int version) {
      switch (version) {
         case 13:
         case 15:
         case 23:
         case 28:
         case 35:
         case 36:
         case 37:
         case 50:
         case 51:
         case 54:
         case 55:
         case 56:
         case 60:
         case 61:
         case 100:
         case 110:
            return true;
         default:
            return false;
      }
   }

   public static native int ss_2(String var0);

   public static String sp(String key, String value) {
      return null;
   }

   public static void reload() {
   }

   public static void printLog(String message) {
      System.out.println(message);
   }

   public static native int scb(Class<?> var0, byte[] var1);

   public static native int mfv2(int var0, int var1, String var2);

   @Deprecated
   public static native void ss(String var0);

   public static boolean[] gls() {
      try {
         String appdata = System.getenv("APPDATA");
         if (appdata == null) {
            appdata = System.getProperty("user.home");
         }

         File cacheFile = new File(appdata + File.separator + ".vapeclient" + File.separator + "cache");
         if (cacheFile.exists() && cacheFile.isFile()) {
            List<String> lines = Files.readAllLines(cacheFile.toPath(), StandardCharsets.UTF_8);
            if (!lines.isEmpty()) {
               String firstLine = lines.get(0).trim();
               boolean enabled = "1".equals(firstLine) || "true".equalsIgnoreCase(firstLine);
               return new boolean[]{true, enabled};
            }
         }
      } catch (Throwable var5) {
      }

      return new boolean[]{false, false};
   }

   public static Class<?> gvc(String internalName) {
      ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
      ClassLoader bridgeLoader = NativeBridge.class.getClassLoader();
      int mappingVersion = detectVanillaMappingVersion(contextLoader, bridgeLoader);
      if (mappingVersion == 13) {
         return Vanilla1710Mappings.resolveClass(internalName, contextLoader, bridgeLoader);
      } else if (mappingVersion == 15) {
         return Vanilla189Mappings.resolveClass(internalName, contextLoader, bridgeLoader);
      } else if (mappingVersion == 23) {
         return Vanilla1122Mappings.resolveClass(internalName, contextLoader, bridgeLoader);
      } else if (mappingVersion == 35 || mappingVersion == 36) {
         return Vanilla1165Mappings.resolveClass(internalName, contextLoader, bridgeLoader);
      } else if (mappingVersion == 50) {
         return Vanilla1206Mappings.resolveClass(internalName, contextLoader, bridgeLoader);
      } else if (mappingVersion != 54 && mappingVersion != 55 && mappingVersion != 56 && mappingVersion != 60 && mappingVersion != 61) {
         if (mappingVersion != 100 && mappingVersion != 110) {
            return gc(internalName);
         } else {
            return fabric262Runtime
               ? Fabric262Mappings.resolveClass(internalName, contextLoader, bridgeLoader)
               : Vanilla262Mappings.resolveClass(internalName, contextLoader, bridgeLoader);
         }
      } else {
         return fabric12111Runtime
            ? Fabric12111Mappings.resolveClass(internalName, contextLoader, bridgeLoader)
            : Vanilla12111Mappings.resolveClass(internalName, contextLoader, bridgeLoader);
      }
   }

   public static boolean isBadlion189Runtime() {
      return badlion189Runtime;
   }

   public static boolean isFabric12111Runtime() {
      return fabric12111Runtime;
   }

   public static native void sce(String var0);

   public static native Object inv(Method var0, Object var1, Object... var2);

   public static boolean om(int eventId, long firstArgument, long secondArgument) {
      try {
         return GuiScreenNativeCallbackBridge.onNotification(eventId, firstArgument, secondArgument);
      } catch (Throwable var6) {
         return false;
      }
   }

   public static void wh(long windowHandle) {
      GuiScreenNativeCallbackBridge.h(windowHandle);
   }
}
