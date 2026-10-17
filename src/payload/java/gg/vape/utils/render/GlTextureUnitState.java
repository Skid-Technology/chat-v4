package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManagerTexGenState;

public class GlTextureUnitState {
   private static boolean samplerSupportEnabled = false;
   private static final int legacyMagicValue;
   private static final String SAMPLER_SUPPORT_MESSAGE = "SamplerFix: GL33 sampler support enabled via hook";
   private static boolean initialized = false;
   private static final int[] savedTextureBindings = new int[8];

   public static void restoreTextureUnitZero() {
      restoreTextureUnit(0);
   }

   @Deprecated
   public static void clearTextureUnits(int textureUnitCount) {
      if (isSamplerSupportEnabled()) {
         for (int textureUnit = 0; textureUnit < textureUnitCount; textureUnit++) {
            clearTextureUnit(textureUnit);
         }
      }
   }

   public static void saveAndClearTextureUnits(int textureUnitCount) {
      if (isSamplerSupportEnabled()) {
         int boundedTextureUnitCount = Math.min(textureUnitCount, 8);

         for (int textureUnit = 0; textureUnit < boundedTextureUnitCount; textureUnit++) {
            saveAndClearTextureUnit(textureUnit);
         }
      }
   }

   public static void restoreTextureUnit(int textureUnit) {
      if (isSamplerSupportEnabled() && textureUnit < 8) {
         GlStateManagerTexGenState.I(textureUnit, savedTextureBindings[textureUnit]);
      }
   }

   @Deprecated
   public static void clearTextureUnitZero() {
      clearTextureUnit(0);
   }

   public static void saveTextureUnit(int textureUnit) {
      if (isSamplerSupportEnabled() && textureUnit < 8) {
         savedTextureBindings[textureUnit] = GlStateManagerTexGenState.J(textureUnit);
      }
   }

   public static void saveAndClearTextureUnitZero() {
      saveAndClearTextureUnit(0);
   }

   public static void restoreTextureUnits(int textureUnitCount) {
      if (isSamplerSupportEnabled()) {
         int boundedTextureUnitCount = Math.min(textureUnitCount, 8);

         for (int textureUnit = 0; textureUnit < boundedTextureUnitCount; textureUnit++) {
            restoreTextureUnit(textureUnit);
         }
      }
   }

   public static void saveAndClearTextureUnit(int textureUnit) {
      if (isSamplerSupportEnabled()) {
         saveTextureUnit(textureUnit);
         clearTextureUnit(textureUnit);
      }
   }

   public static void clearTextureUnit(int textureUnit) {
      if (isSamplerSupportEnabled()) {
         GlStateManagerTexGenState.I(textureUnit, 0);
      }
   }

   public static boolean isSamplerSupportEnabled() {
      if (!initialized) {
         initializeSamplerSupport();
      }

      return samplerSupportEnabled;
   }

   public static void initializeSamplerSupport() {
      if (!initialized) {
         initialized = true;
         if (!GuiRenderPrimitives.d()) {
            samplerSupportEnabled = false;
         } else if (ForgeVersion.MC_1_21_11.v()) {
            samplerSupportEnabled = false;
         } else {
            samplerSupportEnabled = GlStateManagerTexGenState.p();
            if (samplerSupportEnabled) {
               Vape.debugLog(SAMPLER_SUPPORT_MESSAGE);
            }
         }
      }
   }

   static {
      long legacyMagicLong = -700399992203902968L;
      legacyMagicValue = (int)legacyMagicLong;
   }
}
