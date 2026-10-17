package gg.vape.input;

import gg.vape.Vape;
import gg.vape.module.none.ClientSettings;
import java.util.ArrayList;
import java.util.Collections;

class BindCaptureThread extends Thread {
   final BindCaptureTask captureTask;

   BindCaptureThread(BindCaptureTask bindCaptureTask, BindCaptureThreadConstructorMarker ignoredMarker) {
      this(bindCaptureTask);
   }

   private BindCaptureThread(BindCaptureTask bindCaptureTask) {
      this.captureTask = bindCaptureTask;
   }

   @Override
   public void run() {
      ClientSettings clientSettings = Vape.INSTANCE.getModManager().getMod(ClientSettings.class);
      KeyboardInputState keyboardState = InputEventDispatcher.getInstance().getKeyboardState();
      long lastKeyboardChangeTime = keyboardState.getLastChangeTime();
      long lastMouseChangeTime = MouseInput.getLastChangeTimeBridge();
      ArrayList<Integer> capturedInputs = new ArrayList<>();
      int firstKeyboardKey = -1;
      int firstMouseButton = -1;

      while (true) {
         if (Thread.interrupted()) {
            this.captureTask.finishCapture();
            return;
         }

         if (firstKeyboardKey != -1 && !keyboardState.isKeyDown(firstKeyboardKey)
            || firstMouseButton != -1 && !MouseInput.isButtonDown(firstMouseButton)
            || capturedInputs.size() >= 3) {
            break;
         }

         if (lastKeyboardChangeTime != keyboardState.getLastChangeTime() && keyboardState.isLastKeyDown()) {
            lastKeyboardChangeTime = keyboardState.getLastChangeTime();
            int inputCode = keyboardState.getLastKey();
            if (!capturedInputs.contains(inputCode)) {
               capturedInputs.add(inputCode);
               if (firstKeyboardKey == -1) {
                  firstKeyboardKey = inputCode;
               }

               if (!clientSettings.multiKeybinding.getEffectiveValue()) {
                  break;
               }
            }
         } else if (!keyboardState.isKeyDown(160)) {
            if (lastMouseChangeTime != MouseInput.getLastChangeTimeBridge() && MouseInput.isLastButtonDown()) {
               lastMouseChangeTime = MouseInput.getLastChangeTimeBridge();
               if (MouseInput.getLastButtonBridge() == 0) {
                  this.captureTask.getBendable().setBoundInputs(Collections.emptyList());
                  break;
               }

               int inputCode = -100 + MouseInput.getLastButtonBridge();
               if (firstMouseButton == -1) {
                  firstMouseButton = MouseInput.getLastButtonBridge();
               }

               if (!capturedInputs.contains(inputCode)) {
                  capturedInputs.add(inputCode);
                  if (!clientSettings.multiKeybinding.getEffectiveValue()) {
                     break;
                  }
               }
            } else {
               try {
                  Thread.sleep(10L);
               } catch (InterruptedException var12) {
                  var12.printStackTrace();
               }
            }
         }
      }

      this.captureTask.getBendable().setBoundInputs(capturedInputs);
      this.captureTask.finishCapture();
   }

   private static InterruptedException identityInterruptedException(InterruptedException interruptedException) {
      return interruptedException;
   }
}
