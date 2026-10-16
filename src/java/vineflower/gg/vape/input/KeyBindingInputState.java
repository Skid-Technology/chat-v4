package gg.vape.input;

import gg.vape.config.ClientSettings;
import gg.vape.runtime.NativeBridge;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;

public class KeyBindingInputState {
   public static final int WM_LBUTTONDOWN;
   public static final int VK_LBUTTON = 1;
   public static final int WM_RBUTTONUP;
   public static final int MK_RBUTTON;
   public static final int WM_LBUTTONUP;
   public static final int VK_RBUTTON;
   public static final int VK_XBUTTON1;
   public static final int WM_MBUTTONDOWN;
   public static final int WM_RBUTTONDOWN;
   public static final int MK_MBUTTON;
   public static final int MK_LBUTTON = 1;
   public static final int XBUTTON2;
   public static final int WM_XBUTTONDOWN;
   public static final int VK_MBUTTON;
   public static final int WM_MBUTTONUP;
   public static final int XBUTTON1 = 1;
   public static final int WM_XBUTTONUP;
   public static final int VK_XBUTTON2;

   public static void sendMiddleButtonDown() {
      sendMiddleButtonDown(false);
   }

   public static boolean isXButton1Down() {
      return (NativeBridge.gks(5) & 256) != 0;
   }

   public static boolean isLeftButtonDown() {
      return (NativeBridge.gks(1) & 256) != 0;
   }

   public static void pressKeyBinding(KeyBinding keyBinding, boolean usePostMessage) {
      int keyCode = ClientSettings.getPlatformKeyCode(keyBinding);
      int mouseButtonThreshold = ForgeVersion.MC_1_21_4.d() ? 4 : 0;
      if (keyCode > mouseButtonThreshold) {
         ClientSettings.setPhysicalKeyState(keyBinding, true);
      } else {
         if (ForgeVersion.MC_1_21_4.v()) {
            keyCode += 100;
         }

         if (keyCode == 0) {
            sendLeftButtonDown(usePostMessage);
         } else {
            sendRightButtonDown(usePostMessage);
         }
      }
   }

   public static void sendXButton2Up() {
      sendXButton2Up(false);
   }

   public static void dispatchMouseButtonMessage(int button, boolean pressed, boolean usePostMessage) {
      switch (button) {
         case 0:
            if (pressed) {
               sendLeftButtonDown(usePostMessage);
            } else {
               sendLeftButtonUp(usePostMessage);
            }

            return;
         case 1:
            if (pressed) {
               sendRightButtonDown(usePostMessage);
            } else {
               sendRightButtonUp(usePostMessage);
            }

            return;
         case 2:
            if (pressed) {
               sendMiddleButtonDown(usePostMessage);
            } else {
               sendMiddleButtonUp(usePostMessage);
            }

            return;
         case 3:
            if (pressed) {
               sendXButton1Down(usePostMessage);
            } else {
               sendXButton1Up(usePostMessage);
            }

            return;
         case 4:
            if (pressed) {
               sendXButton2Down(usePostMessage);
            } else {
               sendXButton2Up(usePostMessage);
            }

            return;
         default:
            throw new IllegalArgumentException("Unsupported mouse button: " + button);
      }
   }

   public static void sendRightButtonUp(boolean usePostMessage) {
      if (usePostMessage) {
         NativeBridge.smdp(2, 517);
      } else {
         NativeBridge.smd(2, 517);
      }
   }

   public static void sendLeftButtonDown() {
      sendLeftButtonDown(false);
   }

   public static boolean isMiddleButtonDown() {
      return (NativeBridge.gks(4) & 256) != 0;
   }

   public static void releaseKeyBinding(KeyBinding keyBinding, boolean usePostMessage) {
      int keyCode = ClientSettings.getPlatformKeyCode(keyBinding);
      int mouseButtonThreshold = ForgeVersion.MC_1_21_4.d() ? 4 : 0;
      if (keyCode > mouseButtonThreshold) {
         ClientSettings.setPhysicalKeyState(keyBinding, false);
      } else {
         if (ForgeVersion.MC_1_21_4.v()) {
            keyCode += 100;
         }

         if (keyCode == 0) {
            sendLeftButtonUp(usePostMessage);
         } else {
            sendRightButtonUp(usePostMessage);
         }
      }
   }

   public static boolean isMouseButtonDown(int button) {
      switch (button) {
         case 0:
            return isLeftButtonDown();
         case 1:
            return isRightButtonDown();
         case 2:
            return isMiddleButtonDown();
         case 3:
            return isXButton1Down();
         case 4:
            return isXButton2Down();
         default:
            throw new IllegalArgumentException("Unsupported mouse button: " + button);
      }
   }

   public static void sendXButton1Down(boolean usePostMessage) {
      sendExtendedMouseButtonMessage(usePostMessage, true, 1);
   }

   public static void releaseMouseButton(int button, boolean usePostMessage) {
      dispatchMouseButtonMessage(button, false, usePostMessage);
   }

   public static void sendMiddleButtonDown(boolean usePostMessage) {
      if (usePostMessage) {
         NativeBridge.smdp(16, 519);
      } else {
         NativeBridge.smd(16, 519);
      }
   }

   public static void sendLeftButtonUp(boolean usePostMessage) {
      if (usePostMessage) {
         NativeBridge.smdp(1, 514);
      } else {
         NativeBridge.smd(1, 514);
      }
   }

   public static void sendRightButtonDown() {
      sendRightButtonDown(false);
   }

   public static void sendXButton2Down(boolean usePostMessage) {
      sendExtendedMouseButtonMessage(usePostMessage, true, 2);
   }

   private static IllegalArgumentException preserveException(IllegalArgumentException exception) {
      return exception;
   }

   public static void sendLeftButtonDown(boolean usePostMessage) {
      if (usePostMessage) {
         NativeBridge.smdp(1, 513);
      } else {
         NativeBridge.smd(1, 513);
      }
   }

   public static void sendXButton2Down() {
      sendXButton2Down(false);
   }

   public static void sendUseKeyUp() {
      releaseUseKey(false);
   }

   public static boolean isXButton2Down() {
      return (NativeBridge.gks(6) & 256) != 0;
   }

   public static void sendMiddleButtonUp(boolean usePostMessage) {
      if (usePostMessage) {
         NativeBridge.smdp(16, 520);
      } else {
         NativeBridge.smd(16, 520);
      }
   }

   public static void sendXButton1Up() {
      sendXButton1Up(false);
   }

   public static void releaseAttackKey() {
      releaseAttackKey(false);
   }

   public static void pressAttackKey() {
      pressAttackKey(false);
   }

   public static void sendXButton1Up(boolean usePostMessage) {
      sendExtendedMouseButtonMessage(usePostMessage, false, 1);
   }

   public static void releaseAttackKey(boolean usePostMessage) {
      releaseKeyBinding(Minecraft.gameSettings().F(), usePostMessage);
   }

   public static void sendXButton2Up(boolean usePostMessage) {
      sendExtendedMouseButtonMessage(usePostMessage, false, 2);
   }

   public static boolean isRightButtonDown() {
      return (NativeBridge.gks(2) & 256) != 0;
   }

   public static void sendMouseButtonUp(int button, boolean usePostMessage) {
      dispatchMouseButtonMessage(button, false, usePostMessage);
   }

   public static void sendMiddleButtonUp() {
      sendMiddleButtonUp(false);
   }

   public static void pressUseKey(boolean usePostMessage) {
      pressKeyBinding(Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362(), usePostMessage);
   }

   public static void pressMouseButton(int button, boolean usePostMessage) {
      sendMouseButtonDown(button, usePostMessage);
   }

   private static void sendExtendedMouseButtonMessage(boolean usePostMessage, boolean pressed, int button) {
      int message = pressed ? 523 : 524;
      long buttonMetadata = (long)button << 16;
      NativeBridge.smpm(usePostMessage, InputEventDispatcher.getInstance().getWindowHandle(), message, buttonMetadata, 0L);
   }

   public static void sendRightButtonUp() {
      sendRightButtonUp(false);
   }

   public static void sendXButton1Down() {
      sendXButton1Down(false);
   }

   public static void sendMouseButtonDown(int button, boolean usePostMessage) {
      dispatchMouseButtonMessage(button, true, usePostMessage);
   }

   public static void sendRightButtonDown(boolean usePostMessage) {
      if (usePostMessage) {
         NativeBridge.smdp(2, 516);
      } else {
         NativeBridge.smd(2, 516);
      }
   }

   public static void sendLeftButtonUp() {
      sendLeftButtonUp(false);
   }

   public static void pressAttackKey(boolean usePostMessage) {
      pressKeyBinding(Minecraft.gameSettings().F(), usePostMessage);
   }

   public static void sendUseKeyDown() {
      pressUseKey(false);
   }

   public static void releaseUseKey(boolean usePostMessage) {
      releaseKeyBinding(Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362(), usePostMessage);
   }

   static {
      long[] lArray = new long[]{
         5268082283977376261L,
         635020552620212748L,
         4531861087703793666L,
         -8592204563820314101L,
         4665297638462063112L,
         -5201470760010382847L,
         1670101142605922823L,
         3726329813741338628L,
         -2116712616800812542L,
         -2995678575870344700L,
         -5386562856667840496L,
         120866242339799045L,
         5034933093870338054L
      };
      WM_XBUTTONDOWN = (int)lArray[3];
      WM_LBUTTONUP = (int)lArray[8];
      VK_XBUTTON1 = (int)lArray[11];
      VK_XBUTTON2 = (int)lArray[12];
      VK_MBUTTON = (int)lArray[7];
      WM_RBUTTONUP = (int)lArray[0];
      WM_LBUTTONDOWN = (int)lArray[5];
      VK_RBUTTON = (int)lArray[2];
      WM_XBUTTONUP = (int)lArray[1];
      MK_MBUTTON = (int)lArray[10];
      WM_MBUTTONDOWN = (int)lArray[6];
      WM_RBUTTONDOWN = (int)lArray[9];
      WM_MBUTTONUP = (int)lArray[4];
      MK_RBUTTON = (int)lArray[2];
      XBUTTON2 = (int)lArray[2];
   }
}
