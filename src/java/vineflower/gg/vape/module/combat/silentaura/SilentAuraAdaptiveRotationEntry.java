package gg.vape.module.combat.silentaura;

public class SilentAuraAdaptiveRotationEntry {
   public static final int[] MODE_ORDINALS = new int[SilentAuraRotationMode.values().length];

   SilentAuraAdaptiveRotationEntry() {
   }

   static {
      try {
         MODE_ORDINALS[SilentAuraRotationMode.FLICKING_AWAY.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
      }

      try {
         MODE_ORDINALS[SilentAuraRotationMode.ATTACKING.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
      }

      try {
         MODE_ORDINALS[SilentAuraRotationMode.IDLE.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
      }
   }
}
