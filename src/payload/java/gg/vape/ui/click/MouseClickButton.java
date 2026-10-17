package gg.vape.ui.click;

public enum MouseClickButton {
   LEFT_CLICK,
   MIDDLE_CLICK,
   RIGHT_CLICK;

   private static final MouseClickButton[] v;
   private static boolean I;

   public static boolean P() {
      boolean bl = p();
      return true;
   }

   public static boolean p() {
      return I;
   }

   public static void b(boolean bl) {
      I = bl;
   }

   static {
      if (p()) {
         b(true);
      }

      String[] var10000 = new String[]{"RIGHT_CLICK", "MIDDLE_CLICK", "LEFT_CLICK"};
      v = new MouseClickButton[]{LEFT_CLICK, MIDDLE_CLICK, RIGHT_CLICK};
   }
}
