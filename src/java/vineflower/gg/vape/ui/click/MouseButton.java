package gg.vape.ui.click;

public enum MouseButton {
   LEFT_CLICK,
   RIGHT_CLICK,
   MIDDLE_CLICK,
   UNKNOWN;

   private static final MouseButton[] J = new MouseButton[]{LEFT_CLICK, RIGHT_CLICK, MIDDLE_CLICK, UNKNOWN};

   public static MouseButton A(int n) {
      for (MouseButton mouseButton : values()) {
         if (mouseButton.ordinal() == n) {
            return mouseButton;
         }
      }

      return UNKNOWN;
   }

   public MouseClickButton G() {
      return this == LEFT_CLICK
         ? MouseClickButton.LEFT_CLICK
         : (this == RIGHT_CLICK ? MouseClickButton.RIGHT_CLICK : (this == MIDDLE_CLICK ? MouseClickButton.MIDDLE_CLICK : null));
   }

   static {
      String[] var10000 = new String[]{"LEFT_CLICK", "MIDDLE_CLICK", "RIGHT_CLICK", "UNKNOWN"};
   }
}
