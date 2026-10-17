package gg.vape.rotation;

public enum MouseButtonActionState {
   NONE,
   PRESS,
   RELEASE;

   private static final MouseButtonActionState[] cachedValues = new MouseButtonActionState[]{NONE, PRESS, RELEASE};

   static {
      String[] var10000 = new String[]{"NONE", "RELEASE", "PRESS"};
   }
}
