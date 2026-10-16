package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;

class ClickGuiMacrosSettingsControlPanel extends PanelComponent {
   final ClickGuiMacrosSettingsPanel settingsPanel;

   ClickGuiMacrosSettingsControlPanel(ClickGuiMacrosSettingsPanel clickGuiMacrosSettingsPanel, double d, double d2) {
      super(d, d2);
      this.settingsPanel = clickGuiMacrosSettingsPanel;
   }

   @Override
   public void g(GuiMouseEvent guiMouseEvent) {
      if (this.settingsPanel.getViewMode() == ClickGuiMacrosSettingsViewMode.FULL_SETTINGS) {
         float f = (float)this.L();
         float f2 = (float)this.n() + f * 0.5632F;
         float f3 = (float)this.n() + f;
         if (guiMouseEvent.getY() >= f2
            && guiMouseEvent.getY() <= f3
            && guiMouseEvent.getX() >= this.G$src$D$1b2f02a()
            && guiMouseEvent.getX() <= this.G$src$D$1b2f02a() + this.A()) {
            this.settingsPanel.startPrimaryBindCapture();
         }
      }
   }

   @Override
   public void H() {
      ClickGuiMacrosSettingsViewMode clickGuiMacrosSettingsViewMode = this.settingsPanel.getViewMode();
      boolean bl = clickGuiMacrosSettingsViewMode == ClickGuiMacrosSettingsViewMode.KEYBIND_INPUT
         || clickGuiMacrosSettingsViewMode == ClickGuiMacrosSettingsViewMode.FULL_SETTINGS;
      if (bl) {
         boolean bl3 = clickGuiMacrosSettingsViewMode != ClickGuiMacrosSettingsViewMode.NAME_INPUT;
         float f = (float)this.G$src$D$1b2f02a();
         float f2 = (float)this.n();
         float f3 = (float)this.A();
         float f4 = (float)this.L();
         float f5 = f2 + 8.0F + 4.0F;
         float f6 = f2 + f4 * 0.5632F;
         float f7 = f4 - f4 * 0.5632F;
         GuiRenderPrimitives.B(f, f6, f3, f7, ClickGuiMacrosSettingsPanel.getBorderColor(), 3.0F);
         SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.8);
         smoothFontRenderer.d("Macro name", f, f2, J.A);
         Color color2 = bl3 ? ClickGuiMacrosSettingsPanel.getEnabledInputColor() : ClickGuiMacrosSettingsPanel.getDisabledInputColor();
         GuiRenderPrimitives.B(f, f5, f3, 18.0, color2, 3.0F);
         GuiRenderPrimitives.P(f, f5, f3, 18.0, ClickGuiMacrosSettingsPanel.getBorderColor(), 3.0F, 0.75F, 1.0F);
         float f8 = f2 + f4 * 0.5632F;
         float f9 = f8 + 8.0F + 4.0F;
         float f10 = 4.0F;
         float f11 = 6.0F;
         float f12 = f + f11;
         float f13 = f9 - f10 / 2.0F;
         ImageRenderer.drawImage(J.h, f12, f13, "newbind", f10, f10, false);
         Color color;
         String string;
         if (clickGuiMacrosSettingsViewMode == ClickGuiMacrosSettingsViewMode.KEYBIND_INPUT) {
            string = "Press key to bind";
            color = J.A;
         } else {
            string = this.settingsPanel.getMacro().getBindText();
            if (string == null || string.isEmpty()) {
               string = "No bind";
            }

            color = J.A;
         }

         SmoothFontRenderer smoothFontRenderer2 = this.getFontRenderer(0.8);
         float f14 = (float)smoothFontRenderer2.d("A");
         float f15 = f12 + f10 + 4.0F;
         float f16 = f9 - f14 / 2.0F;
         smoothFontRenderer2.d(string, f15, f16, color);
         if (clickGuiMacrosSettingsViewMode == ClickGuiMacrosSettingsViewMode.FULL_SETTINGS) {
            float f17 = 4.0F;
            float f18 = 4.0F;
            float f19 = f + f3 - f18 - f17;
            float f20 = f9 - f17 / 2.0F;
            ImageRenderer.drawImage(J.h, f19, f20, "newedit", f17, f17, false);
         }
      } else {
         boolean bl4 = clickGuiMacrosSettingsViewMode != ClickGuiMacrosSettingsViewMode.NAME_INPUT;
         float fx = (float)this.G$src$D$1b2f02a();
         float f21 = (float)this.n();
         float f22 = (float)this.A();
         float f23 = (float)this.L();
         float f24 = f21 + 8.0F + 4.0F;
         SmoothFontRenderer smoothFontRendererx = this.getFontRenderer(0.8);
         smoothFontRendererx.d("Macro name", fx, f21, J.A);
         Color colorx = bl4 ? ClickGuiMacrosSettingsPanel.getEnabledInputColor() : ClickGuiMacrosSettingsPanel.getDisabledInputColor();
         GuiRenderPrimitives.B(fx, f24, f22, 18.0, colorx, 3.0F);
         GuiRenderPrimitives.P(fx, f24, f22, 18.0, ClickGuiMacrosSettingsPanel.getBorderColor(), 3.0F, 0.75F, 1.0F);
      }
   }
}
