package gg.vape.input;

public class MouseWheelInputHandler implements InputEventHandler {
   @Override
   public boolean handle(long packedWheelMetadata, long secondArgument) {
      short scrollDelta = Win32InputConstants.extractHighWord(packedWheelMetadata);
      return InputEventDispatcher.getInstance().getMouseState().setScrollDelta(scrollDelta);
   }
}
