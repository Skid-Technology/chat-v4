package gg.vape.ui.click.frame;

import gg.vape.ui.click.component.GuiClickListener;

public class SettingsSubpageFrameCollapseClickHandler implements GuiClickListener {
   final SettingsSubpageFrame y;

   @Override
   public void onPrimaryClick() {
      this.y.p();
   }

   public SettingsSubpageFrameCollapseClickHandler(SettingsSubpageFrame settingsSubpageFrame) {
      this.y = settingsSubpageFrame;
   }
}
