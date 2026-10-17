package gg.vape.ui.click.frame;

import gg.vape.ui.click.component.GuiClickListener;

class FrameHeaderCloseToggleClickHandler implements GuiClickListener {
   final Frame h;
   final CloseableFrameHeaderComponent x;

   FrameHeaderCloseToggleClickHandler(CloseableFrameHeaderComponent closeableFrameHeaderComponent, Frame frame) {
      this.x = closeableFrameHeaderComponent;
      this.h = frame;
   }

   @Override
   public void onPrimaryClick() {
      if (this.h instanceof CollapsibleFrame) {
         ((CollapsibleFrame)this.h).w();
         CloseableFrameHeaderComponent.c(this.x, ((CollapsibleFrame)this.h).q());
      }
   }
}
