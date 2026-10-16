package gg.vape.ui.click.component.input;

import gg.vape.ui.click.frame.impl.ClientSettingsSearchFrameHeader;

public class ClientSettingsSearchInputComponent extends ModuleSearchInputComponent {
   final ClientSettingsSearchFrameHeader ey;

   @Override
   public float getLeftInset() {
      return 2.5F;
   }

   public ClientSettingsSearchInputComponent(
      ClientSettingsSearchFrameHeader clientSettingsSearchFrameHeader, ClientSettingsSearchFrameHeader clientSettingsSearchFrameHeader2
   ) {
      super(clientSettingsSearchFrameHeader2);
      this.ey = clientSettingsSearchFrameHeader;
   }
}
