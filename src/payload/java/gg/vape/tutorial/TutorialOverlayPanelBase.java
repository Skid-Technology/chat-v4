package gg.vape.tutorial;

import gg.vape.Vape;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.FrameComponent;
import gg.vape.wrapper.impl.Minecraft;

public abstract class TutorialOverlayPanelBase extends FrameComponent {
   @Override
   public void v() {
   }

   @Override
   public void g(GuiMouseEvent guiMouseEvent) {
   }

   @Override
   public void V() {
   }

   @Override
   public double C() {
      return 20.0;
   }

   @Override
   public void F() {
      if (this.getParentFrameComponent() instanceof Frame) {
         double targetX = Minecraft.J() / 4 / Vape.INSTANCE.getClientSettings().getGuiScaleFactor() - this.x() / 2.0;
         double targetY = Minecraft.h() / 4 / Vape.INSTANCE.getClientSettings().getGuiScaleFactor() - this.C() / 2.0;
         this.getParentFrameComponent().K(targetX);
         this.getParentFrameComponent().S(targetY);
         this.K(targetX);
         this.S(targetY);
      }
   }

   @Override
   public void Y() {
      this.F();
   }

   @Override
   public double x() {
      return 20.0;
   }
}
