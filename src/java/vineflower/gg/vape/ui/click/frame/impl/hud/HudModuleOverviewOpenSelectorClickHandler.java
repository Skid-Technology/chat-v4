package gg.vape.ui.click.frame.impl.hud;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;

class HudModuleOverviewOpenSelectorClickHandler implements GuiClickListener {
   @Override
   public void onPrimaryClick() {
      ClientSettings.getFrame(HudModuleListPanel.class).refreshModules();
      ClientSettings.showFrame(HudModuleSelectorFrame.class);
      HudModuleSelectorFrame.overviewVisible = false;
      ClientSettings.getFrame(HudModuleOverviewFrame.class).setVisible(false);
   }
}
