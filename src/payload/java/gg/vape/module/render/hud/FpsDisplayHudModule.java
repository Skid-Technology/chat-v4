package gg.vape.module.render.hud;

import gg.vape.ui.click.frame.impl.hud.FpsDisplayHudFrame;

public class FpsDisplayHudModule extends HudModule {
   public FpsDisplayHudModule() {
      super("FPS", HudModuleGroup.HUD, "fps", FpsDisplayHudFrame.class);
      this.setSuffix("Shows your current frames per second");
   }
}
