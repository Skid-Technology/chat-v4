package gg.vape.ui.click.frame.impl.hud;

import gg.vape.module.render.hud.HudModuleCategoryEntry;

public class HudModuleCategoryConfigFrame extends HudModuleConfigFrameBase {
   private static final String FRAME_NAME = "CooldownFrame";

   @Override
   public double L() {
      return 55.0;
   }

   @Override
   public void renderHudContent() {
   }

   public HudModuleCategoryConfigFrame() {
      super(HudModuleCategoryEntry.class);
   }

   @Override
   public String getName() {
      return "CooldownFrame";
   }

   @Override
   public double A() {
      return 55.0;
   }
}
