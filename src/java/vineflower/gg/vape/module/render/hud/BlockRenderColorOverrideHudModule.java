package gg.vape.module.render.hud;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventBlockRenderColorOverride;
import gg.vape.utils.MutableColor;
import gg.vape.value.ColorValue;
import java.awt.Color;

public class BlockRenderColorOverrideHudModule extends HudModule {
   private final ColorValue colorValue = ColorValue.create(this, "Color", new Color(255, 0, 0, 127));

   @EventHandler
   public void onBlockRenderColorOverride(EventBlockRenderColorOverride event) {
      MutableColor mutableColor = this.colorValue.getMutableColor();
      EventBlockRenderColorOverride.setColor(
         mutableColor.getRed() / 255.0F, mutableColor.getGreen() / 255.0F, mutableColor.getBlue() / 255.0F, mutableColor.getAlpha() / 255.0F
      );
   }

   public BlockRenderColorOverrideHudModule() {
      super("Hit Color", HudModuleGroup.GAME, "hit_color_mod");
      this.setSuffix("Changes the color of damaged entities");
      this.addValue(this.colorValue);
   }
}
