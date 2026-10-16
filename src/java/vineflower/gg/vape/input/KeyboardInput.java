package gg.vape.input;

import gg.vape.wrapper.impl.ForgeVersion;
import org.lwjgl.input.Keyboard;

public class KeyboardInput {
   private static KeyboardInputState cachedState;

   public static boolean isKeyDown(int keyCode) {
      return getState().isKeyDown(keyCode);
   }

   public static String getKeyName(int keyCode) {
      if (keyCode < 0) {
         int mouseButton = keyCode + 100;
         return "M" + mouseButton;
      } else {
         return ForgeVersion.MC_1_16_5.d() ? String.valueOf((char)keyCode) : Keyboard.getKeyName(keyCode);
      }
   }

   public static KeyboardInputState getState() {
      if (cachedState == null) {
         cachedState = InputEventDispatcher.getInstance().getKeyboardState();
      }

      return cachedState;
   }
}
