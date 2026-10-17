package gg.vape.input;

public class MouseButtonPressInputHandler implements InputEventHandler {
   int button;

   public MouseButtonPressInputHandler(int button) {
      this.button = button;
   }

   @Override
   public boolean handle(long firstArgument, long secondArgument) {
      return InputEventDispatcher.getInstance().getMouseState().setButtonState(this.button, true);
   }
}
