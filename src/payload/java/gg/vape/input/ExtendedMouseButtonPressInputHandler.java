package gg.vape.input;

public class ExtendedMouseButtonPressInputHandler implements InputEventHandler {
   @Override
   public boolean handle(long buttonMetadata, long secondArgument) {
      return InputEventDispatcher.getInstance().getMouseState().setButtonState(KeyboardCodeUtil.decodeExtendedMouseButton(buttonMetadata), true);
   }
}
