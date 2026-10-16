package gg.vape.module.combat.crystalaura;

public class CrystalAuraActionStateSwitchMap {
   public static final int[] stateOrdinals = new int[CrystalAuraActionState.values().length];

   CrystalAuraActionStateSwitchMap() {
   }

   static {
      try {
         stateOrdinals[CrystalAuraActionState.BREAKING_CRYSTAL.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
      }

      try {
         stateOrdinals[CrystalAuraActionState.PLACING_CRYSTAL.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
      }

      try {
         stateOrdinals[CrystalAuraActionState.PLACING_OBSIDIAN.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
      }
   }
}
