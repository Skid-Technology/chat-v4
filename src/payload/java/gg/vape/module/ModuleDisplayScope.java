package gg.vape.module;

public enum ModuleDisplayScope {
   ALL,
   STANDALONE_ONLY,
   FRAMES_ONLY;

   private static final ModuleDisplayScope[] VALUES = new ModuleDisplayScope[]{ALL, STANDALONE_ONLY, FRAMES_ONLY};

   static {
      String[] var10000 = new String[]{"STANDALONE_ONLY", "ALL", "FRAMES_ONLY"};
   }
}
