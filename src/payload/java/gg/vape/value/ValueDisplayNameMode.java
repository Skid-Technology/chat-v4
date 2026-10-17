package gg.vape.value;

public enum ValueDisplayNameMode {
   FULL,
   SIMPLE,
   CUSTOM;

   private static final ValueDisplayNameMode[] legacyValues = new ValueDisplayNameMode[]{FULL, SIMPLE, CUSTOM};

   static {
      String[] var10000 = new String[]{"FULL", "SIMPLE", "CUSTOM"};
   }
}
