package gg.vape.ui.click.frame;

import gg.vape.Vape;
import gg.vape.module.none.ClientSettings;
import gg.vape.tutorial.page.HudTutorialPage;
import gg.vape.ui.click.component.GuiClickListener;

public class FrameHeaderMainLayerClickHandler implements GuiClickListener {
   @Override
   public void onPrimaryClick() {
      ClientSettings.INSTANCE.switchFrameStack(ClientSettings.mainStack);
      if (Vape.INSTANCE.getTutorialManager() != null && Vape.INSTANCE.getTutorialManager().getCurrentPage() instanceof HudTutorialPage) {
         Vape.INSTANCE.getTutorialManager().completeCurrentPage();
      }
   }
}
