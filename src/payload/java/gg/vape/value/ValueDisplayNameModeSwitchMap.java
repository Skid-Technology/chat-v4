package gg.vape.value;

public class ValueDisplayNameModeSwitchMap {
   public static final int[] T = new int[ValueDisplayNameMode.values().length];

   ValueDisplayNameModeSwitchMap() {
   }

   static {
      try {
         T[ValueDisplayNameMode.CUSTOM.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
      }

      try {
         T[ValueDisplayNameMode.SIMPLE.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
      }

      try {
         T[ValueDisplayNameMode.FULL.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
      }
   }
}
