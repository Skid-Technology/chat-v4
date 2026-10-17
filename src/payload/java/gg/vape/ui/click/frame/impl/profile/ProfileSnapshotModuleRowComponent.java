package gg.vape.ui.click.frame.impl.profile;

import gg.vape.config.ProfileModuleSnapshot;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.ColorUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;

public class ProfileSnapshotModuleRowComponent extends InteractiveComponent {
   private boolean selected;
   private final ProfileModuleSnapshot snapshot;

   @Override
   public double x() {
      return 104.0;
   }

   @Override
   public void c() {
      super.c();
   }

   public void setSelected(boolean selected) {
      this.selected = selected;
   }

   public ProfileSnapshotModuleRowComponent(ProfileModuleSnapshot profileModuleSnapshot) {
      this.snapshot = profileModuleSnapshot;
   }

   public ProfileModuleSnapshot getSnapshot() {
      return this.snapshot;
   }

   @Override
   public void H() {
      if (this.selected) {
         GuiRenderPrimitives.B(this.G$src$D$1b2f02a(), this.n() + 1.0, this.A(), this.L() - 2.0, J.m, 2.0F);
         GuiRenderPrimitives.P(this.G$src$D$1b2f02a(), this.n() + 1.0, this.A(), this.L() - 2.0, J.l, 2.0F, 0.9F, 1.0F);
      }

      if (this.w$src$Z$e457mb()) {
         GuiRenderPrimitives.B(this.G$src$D$1b2f02a(), this.n() + 1.0, this.A(), this.L() - 2.0, new Color(255, 255, 255, 7), 2.0F);
      }

      float f = (float)(this.G$src$D$1b2f02a() + this.A() - 8.0);
      ImageRenderer.drawImage(this.selected ? J.A : J.Z, f, (float)this.n() + 7.0F, "expandarrow", 4.0F, 4.0F, false);
      double d2 = -1.0;
      double d3 = f - 10.0F;
      if (this.snapshot.hasBind()) {
         SmoothFontRenderer smoothFontRenderer = this.getAlternateFontRenderer(0.633);
         String string = this.snapshot.getBindDisplayText();
         double d = smoothFontRenderer.N(string);
         double d4 = Math.max(8.0, d) + 6.0;
         double d5 = d4 - d;
         d2 = this.G$src$D$1b2f02a() + this.A() - 26.0 - d / 2.0;
         GuiRenderPrimitives.B(d2, this.n() + 5.0, d4, 8.0, J.l, 2.0F);
         smoothFontRenderer.d(string, d2 + d5 / 2.0, this.n() + 7.0, J.Z);
      }

      if (this.snapshot.isEnabled()) {
         SmoothFontRenderer smoothFontRenderer = this.getAlternateFontRenderer(0.633);
         String string = "ON";
         double d = 14.0;
         double var18;
         d2 = d2 == -1.0 ? d3 - 8.0 : (var18 = d2 - (d + 2.0));
         GuiRenderPrimitives.B(d2, this.n() + 5.0, d, 8.0, J.z(), 2.0F);
         smoothFontRenderer.d(string, d2 + 3.0, this.n() + 7.0, ColorUtil.getAccentTextColor());
      }

      double d6 = this.G$src$D$1b2f02a() + 6.0;
      double d = this.A() - 8.0;
      if (d2 != -1.0) {
         d = d2 - d6 - 4.0;
      }

      TruncatedTextComponent truncatedTextComponent = new TruncatedTextComponent(this.snapshot.getName(), "...", d, 0.95, this.selected ? J.A : J.Z, false);
      truncatedTextComponent.renderAt(d6, this.n() + 5.0);
   }

   @Override
   public double C() {
      return 18.0;
   }
}
