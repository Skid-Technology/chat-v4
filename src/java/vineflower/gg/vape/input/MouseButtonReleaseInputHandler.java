package gg.vape.input;

public class MouseButtonReleaseInputHandler implements InputEventHandler {
   int button;

   public MouseButtonReleaseInputHandler(int button) {
      this.button = button;
   }

   @Override
   public boolean handle(long firstArgument, long secondArgument) {
      return InputEventDispatcher.getInstance().getMouseState().setButtonState(this.button, false);
   }
}
