package gg.vape.ui.click.frame.impl;

import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;

public class ClientSettingsFrameSectionLabelComponent extends InteractiveComponent {
   private String K;
   private boolean Q;
   private Color I;
   private double v;
   private float b = 4.0F;
   private static final String cb = "expandarrow";

   @Override
   public double C() {
      return 20.0;
   }

   @Override
   public void I() {
   }

   public ClientSettingsFrameSectionLabelComponent(String string) {
      this(string, 0.9);
   }

   @Override
   public double x() {
      return 110.0;
   }

   @Override
   public void F() {
      this.Q = true;
   }

   @Override
   public void H() {
      float f = 6.88F;
      SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(this.v);
      double d = smoothFontRenderer.d(this.K);
      double d2 = this.n() + this.L() / 2.0 - d / 2.0;
      double d3 = this.n() + this.L() / 2.0 - f / 2.0F;
      double d4 = this.n() + this.L() / 2.0 - this.b / 2.0F;
      Color color = J.i;
      Color color2 = J.Z;
      if (this.Q) {
         color = J.m;
         color2 = J.A;
      }

      GuiRenderPrimitives.C(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), color);
      ImageRenderer.drawImage(this.I, (float)this.G$src$D$1b2f02a() + (float)this.A() - 5.0F - 5.0F, (float)d4, "expandarrow", this.b, this.b, false);
      smoothFontRenderer.d(this.K, this.G$src$D$1b2f02a() + 5.0, d2, color2);
   }

   public ClientSettingsFrameSectionLabelComponent(String string, double d) {
      this.I = J.W;
      this.K = string;
      this.v = d;
   }

   @Override
   public void u() {
      if (this.Q && !this.w$src$Z$e457mb()) {
         this.Q = false;
      }
   }
}
