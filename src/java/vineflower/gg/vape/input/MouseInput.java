package gg.vape.input;

import gg.vape.wrapper.impl.Minecraft;

public class MouseInput {
   private static MouseInputState cachedState;

   public static int getMouseX() {
      return getState().getMouseX();
   }

   public static boolean isButtonDown(int button) {
      return getState().isButtonDown(button);
   }

   public static int getInvertedMouseY() {
      return Minecraft.h() - getState().getMouseY();
   }

   public static MouseInputState getState() {
      if (cachedState == null) {
         cachedState = InputEventDispatcher.getInstance().getMouseState();
      }

      return cachedState;
   }

   public static int getScrollDelta() {
      return getState().getScrollDelta();
   }

   public static boolean wasHandledByGui() {
      return getState().wasHandledByGui();
   }

   public static long getLastChangeTime() {
      return getState().getLastChangeTime();
   }

   public static boolean isLastButtonDown() {
      return getState().isLastButtonDown();
   }

   public static int getLastButton() {
      return getState().getLastButton();
   }

   public static int getLastButtonBridge() {
      return getLastButton();
   }

   public static long getLastChangeTimeBridge() {
      return getLastChangeTime();
   }
}
