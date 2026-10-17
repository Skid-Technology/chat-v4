package gg.vape.unmap;

public class ImageParserFormatSwitchMap {
   public static final int[] FORMAT_SWITCH_MAP = new int[ImageParser$Format.values().length];

   ImageParserFormatSwitchMap() {
   }

   static {
      try {
         FORMAT_SWITCH_MAP[ImageParser$Format.ABGR.ordinal()] = 1;
      } catch (NoSuchFieldError var8) {
      }

      try {
         FORMAT_SWITCH_MAP[ImageParser$Format.RGBA.ordinal()] = 2;
      } catch (NoSuchFieldError var7) {
      }

      try {
         FORMAT_SWITCH_MAP[ImageParser$Format.BGRA.ordinal()] = 3;
      } catch (NoSuchFieldError var6) {
      }

      try {
         FORMAT_SWITCH_MAP[ImageParser$Format.RGB.ordinal()] = 4;
      } catch (NoSuchFieldError var5) {
      }

      try {
         FORMAT_SWITCH_MAP[ImageParser$Format.LUMINANCE.ordinal()] = 5;
      } catch (NoSuchFieldError var4) {
      }

      try {
         FORMAT_SWITCH_MAP[ImageParser$Format.ALPHA.ordinal()] = 6;
      } catch (NoSuchFieldError var3) {
      }

      try {
         FORMAT_SWITCH_MAP[ImageParser$Format.WHITE.ordinal()] = 7;
      } catch (NoSuchFieldError var2) {
      }

      try {
         FORMAT_SWITCH_MAP[ImageParser$Format.LUMINANCE_ALPHA.ordinal()] = 8;
      } catch (NoSuchFieldError var1) {
      }
   }
}
