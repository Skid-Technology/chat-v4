package gg.vape.ui.click.frame.impl.hud;

import gg.vape.ui.click.component.GuiClickListener;

class HudModuleConfigFrameHeaderCloseClickHandler implements GuiClickListener {
   private final HudModuleConfigFrame configFrame;

   @Override
   public void onPrimaryClick() {
      if (!this.configFrame.isClosing()) {
         if (this.configFrame.V$src$Z$1xhop3l()) {
            this.configFrame.beginClosing();
            this.configFrame.U();
         }
      }
   }

   HudModuleConfigFrameHeaderCloseClickHandler(
      HudModuleConfigFrameHeaderComponent hudModuleConfigFrameHeaderComponent, HudModuleConfigFrame hudModuleConfigFrame
   ) {
      this.configFrame = hudModuleConfigFrame;
   }
}
