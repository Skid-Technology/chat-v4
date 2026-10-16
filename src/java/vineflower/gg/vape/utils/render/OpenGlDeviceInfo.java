package gg.vape.utils.render;

import gg.vape.Vape;
import org.lwjgl.opengl.GL11;

public class OpenGlDeviceInfo {
   public static String vendorName = null;
   public static GpuVendor gpuVendor = GpuVendor.UNKNOWN;
   private static int legacyValue;
   public static String rendererName = null;
   public static String versionName = null;

   public static int getLegacyConstant() {
      int ignoredLegacyValue = getLegacyValue();
      return 112;
   }

   public static int getLegacyValue() {
      return legacyValue;
   }

   public static void collectDeviceInfo() {
      try {
         String renderer = GL11.glGetString(7937);
         String vendor = GL11.glGetString(7936);
         String version = GL11.glGetString(7938);
         vendorName = vendor != null ? vendor : "Unknown Vendor";
         rendererName = renderer != null ? renderer : "Unknown GPU";
         versionName = version != null ? version : "Unknown Version";
         gpuVendor = detectGpuVendor(vendor);
      } catch (Exception var3) {
         Vape.debugLog("Error getting OpenGL: " + var3.getMessage());
      }
   }

   public static void setLegacyValue(int value) {
      legacyValue = value;
   }

   private static Exception propagateException(Exception exception) {
      return exception;
   }

   private static GpuVendor detectGpuVendor(String vendorName) {
      if (vendorName == null) {
         return GpuVendor.UNKNOWN;
      } else {
         switch (vendorName) {
            case "NVIDIA Corporation":
               return GpuVendor.NVIDIA;
            case "Intel":
            case "Intel Open Source Technology Center":
               return GpuVendor.INTEL;
            case "AMD":
            case "ATI Technologies Inc.":
               return GpuVendor.AMD;
            default:
               return GpuVendor.UNKNOWN;
         }
      }
   }

   public static void appendDeviceInfo(StringBuilder output) {
      collectDeviceInfo();
      output.append("GPU Vendor: ").append(gpuVendor.name()).append(" (").append(vendorName).append(")\n");
      output.append("GPU Renderer: ").append(rendererName).append('\n');
      output.append("OpenGL Version: ").append(versionName).append('\n');
   }

   private static void logCapabilities() {
      try {
         Vape.debugLog("MAX_TEXTURE_SIZE - " + GL11.glGetInteger(3379));
         Vape.debugLog("MAX_TEXTURE_UNITS - " + GL11.glGetInteger(34930));
         Vape.debugLog("MAX_VERTEX_ATTRIBS - " + GL11.glGetInteger(34921));
         Vape.debugLog("MAX_COLOR_ATTACHMENTS - " + GL11.glGetInteger(36063));
         Vape.debugLog("MAX_VIEWPORT_WIDTH - " + GL11.glGetInteger(3386));
         Vape.debugLog("MAX_VERTEX_UNIFORM_COMPONENTS - " + GL11.glGetInteger(35658));
         Vape.debugLog("MAX_FRAGMENT_UNIFORM_COMPONENTS - " + GL11.glGetInteger(35657));
      } catch (Exception var1) {
         Vape.debugLog("Failed to collect GPU capabilities: " + var1.getMessage());
      }
   }

   public static void logDeviceInfo() {
      Vape.debugLog("===== Graphics Information =====");
      Vape.debugLog("Vendor: " + gpuVendor.name() + " (" + vendorName + ")");
      Vape.debugLog("Device Name: " + rendererName);
      Vape.debugLog("Driver Version: " + versionName);
      Vape.debugLog("---GPU Capabilities---");
      logCapabilities();
      Vape.debugLog("================================");
   }

   static {
      setLegacyValue(0);
   }
}
