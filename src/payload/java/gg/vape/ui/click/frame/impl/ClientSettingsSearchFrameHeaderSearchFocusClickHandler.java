package gg.vape.ui.click.frame.impl;

import gg.vape.ui.click.component.GuiClickListener;

class ClientSettingsSearchFrameHeaderSearchFocusClickHandler implements GuiClickListener {
   final ClientSettingsSearchFrameHeader q;

   @Override
   public void onPrimaryClick() {
      this.q.focusSearchInput();
   }

   ClientSettingsSearchFrameHeaderSearchFocusClickHandler(ClientSettingsSearchFrameHeader clientSettingsSearchFrameHeader) {
      this.q = clientSettingsSearchFrameHeader;
   }
}
