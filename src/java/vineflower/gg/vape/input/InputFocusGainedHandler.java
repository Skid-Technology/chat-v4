package gg.vape.input;

public class InputFocusGainedHandler implements InputEventHandler {
   @Override
   public boolean handle(long windowHandle, long focusState) {
      InputEventDispatcher.getInstance().getFocusState().markFocused();
      return false;
   }
}
