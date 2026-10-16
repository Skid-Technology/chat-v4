package gg.vape.ui.click.frame.impl;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;

public class ClientSettingsFrameOpenSearchClickHandler implements GuiClickListener {
   final ClientSettingsFrame f;

   @Override
   public void onPrimaryClick() {
      ClientSettingsSearchFrame clientSettingsSearchFrame = ClientSettings.getFrame(ClientSettingsSearchFrame.class);
      clientSettingsSearchFrame.M(this.f.double_G(), this.f.double_n());
      clientSettingsSearchFrame.t(true, false);
   }

   public ClientSettingsFrameOpenSearchClickHandler(ClientSettingsFrame clientSettingsFrame) {
      this.f = clientSettingsFrame;
   }
}
