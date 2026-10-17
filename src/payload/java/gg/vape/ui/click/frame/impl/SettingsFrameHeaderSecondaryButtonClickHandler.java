package gg.vape.ui.click.frame.impl;

import gg.vape.ui.click.component.GuiClickListener;

class SettingsFrameHeaderSecondaryButtonClickHandler implements GuiClickListener {
   final SettingsFrameHeaderComponent Q;

   SettingsFrameHeaderSecondaryButtonClickHandler(SettingsFrameHeaderComponent settingsFrameHeaderComponent) {
      this.Q = settingsFrameHeaderComponent;
   }

   @Override
   public void onPrimaryClick() {
      if (SettingsFrameHeaderComponent.O(this.Q) != null) {
         SettingsFrameHeaderComponent.O(this.Q).onPrimaryClick();
      }
   }
}
