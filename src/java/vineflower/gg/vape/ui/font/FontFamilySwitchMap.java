package gg.vape.ui.font;

public class FontFamilySwitchMap {
   public static final int[] d = new int[FontFamily.values().length];

   FontFamilySwitchMap() {
   }

   static {
      try {
         d[FontFamily.PROXIMA.ordinal()] = 1;
      } catch (NoSuchFieldError var4) {
      }

      try {
         d[FontFamily.ARIAL.ordinal()] = 2;
      } catch (NoSuchFieldError var3) {
      }

      try {
         d[FontFamily.NOTO.ordinal()] = 3;
      } catch (NoSuchFieldError var2) {
      }

      try {
         d[FontFamily.MINECRAFT.ordinal()] = 4;
      } catch (NoSuchFieldError var1) {
      }
   }
}
