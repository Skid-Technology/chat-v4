package gg.vape.ui.click.frame.impl;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;

class ClientSettingsSectionFrameCloseSearchClickHandler implements GuiClickListener {
   final ClientSettingsSectionFrame V;

   @Override
   public void onPrimaryClick() {
      ClientSettings.setFrameVisibility(ClientSettingsSearchFrame.class, false);
   }

   ClientSettingsSectionFrameCloseSearchClickHandler(ClientSettingsSectionFrame clientSettingsSectionFrame) {
      this.V = clientSettingsSectionFrame;
   }
}
