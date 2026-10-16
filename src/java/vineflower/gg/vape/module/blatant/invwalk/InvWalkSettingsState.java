package gg.vape.module.blatant.invwalk;

import gg.vape.config.ClientSettings;
import gg.vape.input.KeyBindingHelper;
import gg.vape.wrapper.impl.KeyBinding;

public class InvWalkSettingsState {
   private static int keyState;

   public static int getInitializationSentinel() {
      int state = getKeyState();
      return state == 0 ? 116 : 0;
   }

   public static void setKeyState(int state) {
      keyState = state;
   }

   public static boolean isPhysicallyPressed(KeyBinding keyBinding) {
      return ClientSettings.isPhysicalKeyDown(keyBinding);
   }

   public static void synchronizeKey(KeyBinding keyBinding) {
      if (isPhysicallyPressed(keyBinding)) {
         setPressed(keyBinding, true);
      } else if (keyBinding.u()) {
         setPressed(keyBinding, false);
      }
   }

   public static void setPressed(KeyBinding keyBinding, boolean pressed) {
      if (keyBinding != null && keyBinding.getObject() != null) {
         KeyBindingHelper.setPressedAndTick(keyBinding, pressed);
      }
   }

   public static int getKeyState() {
      return keyState;
   }

   static {
      if (getInitializationSentinel() != 0) {
         setKeyState(62);
      }
   }
}
