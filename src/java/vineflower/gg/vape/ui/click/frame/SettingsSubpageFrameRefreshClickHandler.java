package gg.vape.ui.click.frame;

import gg.vape.ui.click.component.GuiClickListener;

public class SettingsSubpageFrameRefreshClickHandler implements GuiClickListener {
   final SettingsSubpageFrame C;

   public SettingsSubpageFrameRefreshClickHandler(SettingsSubpageFrame settingsSubpageFrame) {
      this.C = settingsSubpageFrame;
   }

   @Override
   public void onPrimaryClick() {
      this.C.w();
   }
}
