package gg.vape.ui.click.component.input;

import gg.vape.Vape;
import gg.vape.ui.click.component.GuiClickListener;

class BindableInputComponentCaptureToggleClickHandler implements GuiClickListener {
   final BindableInputComponent owner;

   BindableInputComponentCaptureToggleClickHandler(BindableInputComponent bindableInputComponent) {
      this.owner = bindableInputComponent;
   }

   @Override
   public void onPrimaryClick() {
      if (this.owner.getCaptureTask().isCapturing()) {
         this.owner.getBindLabel().setToolTips(null);
         this.owner.w(this.owner.buildTooltipText());
      } else if (this.owner.supportsActivationModeConfiguration() && this.owner.isShiftPressed()) {
         this.owner.getBendable().toggleActivationMode();
         Vape.INSTANCE.saveAndStop();
      } else {
         this.owner.getCaptureTask().run();
      }
   }
}
