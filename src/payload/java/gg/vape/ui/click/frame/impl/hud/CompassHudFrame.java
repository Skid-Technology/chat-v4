package gg.vape.ui.click.frame.impl.hud;

import gg.vape.module.render.hud.CompassHudModule;

public class CompassHudFrame extends HudModuleConfigFrameBase {
   private static final String FRAME_NAME = "CompassFrame";

   @Override
   public void renderHudContent() {
   }

   public CompassHudFrame() {
      super(CompassHudModule.class);
      this.h(new CompassStripComponent(this));
   }

   @Override
   public double L() {
      return 30.0;
   }

   @Override
   public double A() {
      return 308.0;
   }

   @Override
   public String getName() {
      return "CompassFrame";
   }
}
