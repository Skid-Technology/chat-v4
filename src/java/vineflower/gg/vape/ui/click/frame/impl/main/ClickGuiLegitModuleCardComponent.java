package gg.vape.ui.click.frame.impl.main;

import gg.vape.module.render.hud.HudModule;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MouseButton;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.ui.click.animation.ThemeColorAnimation;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.ColorUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public class ClickGuiLegitModuleCardComponent extends GuiComponent {
   private static final float TOGGLE_DOT_INSET = 1.0F;
   private static final float TOGGLE_DOT_SIZE = 4.0F;
   private final String moduleName;
   private final DoubleAnimation togglePositionAnimation;
   private final IconButtonComponent settingsButton;
   @Nullable
   private Runnable settingsAction;
   private final HudModule module;
   private boolean selected;
   private final DoubleAnimation dimAnimation;
   private final Color toggleDotColor;
   private final ColorAnimation toggleColorAnimation = new ColorAnimation(0.15, J.K, J.W);
   private boolean hovered;
   private final ThemeColorAnimation enabledColorAnimation = new ThemeColorAnimation(0.15, J.W);
   private final float iconScale;
   private final String iconKey;
   private boolean dimmed;
   private boolean enabled;

   private Color applyDimmedAlpha(Color color) {
      if (color == null) {
         return null;
      } else if (this.dimmed && !this.selected) {
         double d = Math.min(1.0, Math.max(0.0, this.dimAnimation.getInterpolatedValue()));
         float f = (float)(1.0 - 0.8 * d);
         int n = Math.max(0, Math.round(color.getAlpha() * f));
         return new Color(color.getRed(), color.getGreen(), color.getBlue(), n);
      } else {
         return color;
      }
   }

   public void setSettingsAction(@Nullable Runnable runnable) {
      this.settingsAction = runnable;
   }

   public boolean isSelected() {
      return this.selected;
   }

   public ClickGuiLegitModuleCardComponent(HudModule hudModule) {
      this(hudModule, 0.9F);
   }

   public void setDimmed(boolean dimmed) {
      this.dimmed = dimmed;
   }

   @Override
   public void g(GuiMouseEvent guiMouseEvent) {
      if (guiMouseEvent.getAction().equals(MouseButton.LEFT_CLICK)) {
         this.toggleEnabled();
      } else if (guiMouseEvent.getAction().equals(MouseButton.RIGHT_CLICK) && this.settingsAction != null) {
         this.settingsAction.run();
      }
   }

   public boolean isToggleAnimating() {
      return !this.togglePositionAnimation.getInterpolatedValue().equals(this.togglePositionAnimation.getStartValue())
         && !this.togglePositionAnimation.getInterpolatedValue().equals(this.togglePositionAnimation.getEndValue());
   }

   public void toggleEnabled() {
      if (this.enabled == this.isToggleAnimationEnabled()) {
         this.enabled = !this.enabled;
         this.enabledColorAnimation.J();
         this.togglePositionAnimation.J();
      } else if (this.enabled) {
         this.enabledColorAnimation.C();
         this.togglePositionAnimation.C();
      } else {
         this.enabledColorAnimation.O();
         this.togglePositionAnimation.O();
      }

      if (this.module.isEnabled() != this.enabled) {
         this.module.setEnabled(this.enabled);
      }
   }

   @Override
   public void H() {
      this.dimAnimation.u(this.dimmed && !this.selected);
      GuiRenderPrimitives.d(
         this.G$src$D$1b2f02a() + 2.0, this.n(), this.A() - 3.0, this.L() - 3.0, this.applyDimmedAlpha(!this.hovered && !this.enabled ? J.m : J.l)
      );
      GuiRenderPrimitives.F(
         this.iconKey,
         this.G$src$D$1b2f02a() + 10.0 + 3.0,
         this.n() + 10.0,
         8.0F * this.iconScale,
         8.0F * this.iconScale,
         this.applyDimmedAlpha(this.enabled ? J.z() : (this.hovered ? J.f : J.W))
      );
      this.settingsButton.K(this.G$src$D$1b2f02a() + this.A() - 15.0);
      this.settingsButton.S(this.n() + 10.0 - 4.5);
      this.settingsButton.Y(9.0);
      this.renderToggle();
      this.renderLabel();
   }

   private void renderToggle() {
      double d = this.G$src$D$1b2f02a() + this.A();
      this.getClass();
      double d2 = d - 12.0 - 17.5;
      double d3 = this.n() + 10.0 - 3.0;
      Color color = this.enabledColorAnimation.q() > 0.0 ? this.enabledColorAnimation.getInterpolatedColor() : this.toggleColorAnimation.getInterpolatedColor();
      if (this.hovered && this.enabledColorAnimation.q() > 0.0) {
         color = ColorUtil.offsetRgb(color, 30.0);
      }

      Color color3 = this.applyDimmedAlpha(color);
      float f = (float)d2;
      float f2 = (float)d3;
      this.getClass();
      this.getClass();
      ImageRenderer.drawImage(color3, f, f2, "togglebg", 6.0F, 6.0F, false);
      ImageRenderer.drawImage(
         this.applyDimmedAlpha(this.toggleDotColor),
         (float)d2 + 1.0F + (float)this.togglePositionAnimation.getInterpolatedValue().doubleValue(),
         (float)d3 + 1.0F,
         "toggledot",
         4.0F,
         4.0F,
         false
      );
   }

   public ClickGuiLegitModuleCardComponent(HudModule hudModule, float f) {
      this.togglePositionAnimation = new DoubleAnimation(0.15, 0.0, 5.0);
      this.toggleDotColor = J.r;
      this.dimAnimation = new DoubleAnimation(0.15, 0.0, 1.0);
      this.module = hudModule;
      this.moduleName = hudModule.getName();
      this.iconKey = hudModule.getKey();
      this.enabled = hudModule.isEnabled();
      this.iconScale = f;
      if (hudModule.getToolTip() != null) {
         this.w(hudModule.getToolTip());
      }

      this.settingsButton = new IconButtonComponent("settingdots", 0.8);
      this.settingsButton.addClickListener(this::runSettingsAction);
      this.addChildren(this.settingsButton);
   }

   public HudModule getModule() {
      return this.module;
   }

   private void runSettingsAction() {
      if (this.settingsAction != null) {
         this.settingsAction.run();
      }
   }

   public boolean isDimmed() {
      return this.dimmed;
   }

   private void syncEnabledState() {
      if (this.module.isEnabled() != this.enabled || this.enabled != this.isToggleAnimationEnabled() && !this.isToggleAnimating()) {
         this.toggleEnabled();
      }
   }

   @Override
   public double x() {
      return 84.5;
   }

   @Override
   public void u() {
      if (this.hovered && !this.w$src$Z$e457mb()) {
         this.hovered = false;
      }

      this.syncEnabledState();
   }

   public boolean isToggleAnimationEnabled() {
      return this.togglePositionAnimation.I$src$Z$c48gtw();
   }

   public void setSelected(boolean selected) {
      this.selected = selected;
   }

   private void renderLabel() {
      SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.9);
      double d = smoothFontRenderer.d(this.moduleName);
      double d2 = this.G$src$D$1b2f02a() + 10.0;
      double d3 = this.n() + (this.L() - 15.0) - d / 2.0;
      Color color = this.enabled ? Color.WHITE : J.Z;
      smoothFontRenderer.d(this.moduleName, d2, d3, this.applyDimmedAlpha(color));
      if (!this.enabled && (this.module.t$src$Z$14g275z() || this.module.Q())) {
         SmoothFontRenderer smoothFontRenderer2 = this.getAlternateFontRenderer(0.65F);
         String string = this.module.Q() ? "INDEV" : "BETA";
         double d4 = smoothFontRenderer2.N(string) + 4.0;
         double d5 = smoothFontRenderer2.d(string) + 2.0;
         double d6 = d2 + smoothFontRenderer.N(this.moduleName) + 4.0;
         double d7 = d3 + (d - d5) / 2.0;
         Color color2 = this.applyDimmedAlpha(J.z());
         GuiRenderPrimitives.d(d6, d7, d4, d5, color2);
         smoothFontRenderer2.d(string, d6 + 2.0, d7 + 1.0, this.applyDimmedAlpha(ColorUtil.getContrastingGray(J.z(), 35, 255)));
      }
   }

   @Override
   public double C() {
      return 60.0;
   }

   @Override
   public void F() {
      this.hovered = true;
   }
}
