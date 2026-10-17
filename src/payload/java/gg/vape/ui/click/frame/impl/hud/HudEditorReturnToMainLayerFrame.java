package gg.vape.ui.click.frame.impl.hud;

import gg.vape.Vape;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.frame.Frame;
import gg.vape.wrapper.impl.Minecraft;

public class HudEditorReturnToMainLayerFrame extends Frame {
   private boolean pressed;
   private final HudEditorReturnToMainLayerHeaderComponent header;

   public HudEditorReturnToMainLayerHeaderComponent getHeader() {
      return this.header;
   }

   @Override
   public void F() {
      this.setDisabledOverlayColor(J.i);
      if (ClientSettings.hudEditorStack != null) {
         ClientSettings.hudEditorStack.v(this);
      }

      this.centerAtTop();
      super.F();
   }

   @Override
   public String getName() {
      return "LegitToggleFrame";
   }

   public void centerAtTop() {
      this.M(Minecraft.J() / 4.0 / Vape.INSTANCE.getClientSettings().getGuiScaleFactor() - this.A() / 2.0, 7.0);
   }

   @Override
   public void v() {
      this.pressed = true;
   }

   @Override
   public void u() {
      if (this.pressed && !this.w$src$Z$e457mb()) {
         this.pressed = false;
      }
   }

   public HudEditorReturnToMainLayerFrame() {
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(false);
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
      this.I2 = false;
      this.setVisible(true);
      this.header = new HudEditorReturnToMainLayerHeaderComponent(this);
      this.Y(this.header);
   }

   @Override
   public boolean V$src$Z$1xhop3l() {
      return true;
   }

   @Override
   public void Y() {
   }
}
