package gg.vape.input;

public class ExtendedMouseButtonReleaseInputHandler implements InputEventHandler {
   private static boolean legacyState;

   public static void setLegacyState(boolean state) {
      legacyState = state;
   }

   @Override
   public boolean handle(long buttonMetadata, long secondArgument) {
      return InputEventDispatcher.getInstance().getMouseState().setButtonState(KeyboardCodeUtil.decodeExtendedMouseButton(buttonMetadata), false);
   }

   public static boolean getLegacySentinelResult() {
      boolean state = getLegacyState();
      return false;
   }

   public static boolean getLegacyState() {
      return legacyState;
   }

   static {
      if (!getLegacyState()) {
         setLegacyState(true);
      }
   }
}
