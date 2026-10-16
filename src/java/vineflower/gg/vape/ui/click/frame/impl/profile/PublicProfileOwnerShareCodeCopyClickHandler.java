package gg.vape.ui.click.frame.impl.profile;

import gg.vape.manager.client.PublicProfileManager;
import gg.vape.runtime.NativeBridge;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import java.awt.Point;

class PublicProfileOwnerShareCodeCopyClickHandler implements GuiMouseListener {
   private static final String SUCCESS_MESSAGE = "Copied share code to clipboard!";
   private final PublicProfileOwnerDetailsPanel ownerDetailsPanel;

   PublicProfileOwnerShareCodeCopyClickHandler(PublicProfileOwnerDetailsPanel publicProfileOwnerDetailsPanel) {
      this.ownerDetailsPanel = publicProfileOwnerDetailsPanel;
   }

   @Override
   public void g(Point point, MouseClickButton mouseClickButton) {
      NativeBridge.cpy(this.ownerDetailsPanel.getShareCode());
      PublicProfileManager.showInfo("Copied share code to clipboard!");
   }
}
