package gg.vape.mapping.runtime;

import gg.vape.wrapper.impl.ForgeVersion;

public class MappingOverrideRegistry {
   private static MappingOverrideSet activeOverrideSet;

   private static MappingOverrideSet createOverrideSet() {
      MappingOverrideSet overrideSet = null;
      switch (ForgeVersion.c()) {
         case 35:
            overrideSet = new MappingOverrideSetV35(35);
         default:
            return overrideSet;
      }
   }

   public static MappingOverrideSet getActiveOverrideSet() {
      if (activeOverrideSet == null) {
         activeOverrideSet = createOverrideSet();
      }

      return activeOverrideSet;
   }
}
