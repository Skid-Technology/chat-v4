package gg.vape.ui.click.frame.impl.hud;

import gg.vape.module.render.hud.HudModule;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MouseButton;
import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.ui.click.animation.ThemeColorAnimation;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.ColorUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;

public class HudModuleListEntry extends GuiComponent {
   private final float iconScale;
   private final HudModule module;
   private final float toggleDotSize;
   private final ColorAnimation inactiveColorAnimation;
   private final String moduleName;
   private final float toggleInset;
   private final float toggleWidth;
   private final ThemeColorAnimation activeColorAnimation;
   private Frame configFrame;
   private final IconButtonComponent settingsButton = new IconButtonComponent("settingdots", 0.8);
   private final Color toggleDotColor;
   private boolean hovered;
   private final String iconKey;
   private boolean enabled;
   private final float toggleHeight;
   private final DoubleAnimation togglePositionAnimation;

   @Override
   public void F() {
      this.hovered = true;
   }

   public boolean isAnimationShowingEnabled() {
      return this.togglePositionAnimation.I$src$Z$c48gtw();
   }

   @Override
   public void I() {
   }

   protected void syncEnabledState() {
      if (this.module.isEnabled() != this.enabled || this.enabled != this.isAnimationShowingEnabled() && !this.isToggleAnimating()) {
         this.toggleModule();
      }
   }

   public void toggleModule() {
      if (this.enabled == this.isAnimationShowingEnabled()) {
         this.enabled = !this.enabled;
         this.activeColorAnimation.J();
         this.togglePositionAnimation.J();
         this.applyConfigFrameState();
         if (this.module.isEnabled() != this.enabled) {
            this.module.setEnabled(this.enabled);
         }
      } else if (this.enabled) {
         this.activeColorAnimation.C();
         this.togglePositionAnimation.C();
      } else {
         this.activeColorAnimation.O();
         this.togglePositionAnimation.O();
      }
   }

   public boolean isToggleAnimating() {
      return !this.togglePositionAnimation.getInterpolatedValue().equals(this.togglePositionAnimation.getStartValue())
         && !this.togglePositionAnimation.getInterpolatedValue().equals(this.togglePositionAnimation.getEndValue());
   }

   public HudModuleListEntry(HudModule hudModule) {
      this(hudModule, 0.9F);
   }

   @Override
   public double x() {
      return 84.5;
   }

   public HudModule getModule() {
      return this.module;
   }

   private void renderModuleName() {
      SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.9);
      double textHeight = smoothFontRenderer.d(this.moduleName);
      double textY = this.n() + (this.L() - 15.0) - textHeight / 2.0;
      Color color = this.enabled ? Color.WHITE : J.Z;
      smoothFontRenderer.d(this.moduleName, this.G$src$D$1b2f02a() + 10.0, textY, color);
   }

   public void applyConfigFrameState() {
      if (this.configFrame != null) {
         this.configFrame.setVisible(this.enabled);
         this.configFrame.c(true);
         if (HudModuleConfigFrameBase.isHudEditorContext()) {
            this.configFrame.U();
         }

         double screenWidth = Minecraft.J();
         double screenHeight = Minecraft.h();
         if (this.configFrame.n() > screenHeight || this.configFrame.n() < 0.0) {
            this.configFrame.S(screenHeight / 2.0);
         }

         if (this.configFrame.G$src$D$1b2f02a() > screenWidth || this.configFrame.G$src$D$1b2f02a() < 0.0) {
            this.configFrame.K(screenWidth / 2.0);
         }

         if (this.configFrame instanceof HudModuleConfigFrameBase) {
            HudModuleConfigFrameBase hudModuleConfigFrameBase = (HudModuleConfigFrameBase)this.configFrame;
            HudModuleConfigFrameBase.closeAllHudSettings();
            hudModuleConfigFrameBase.getAnchoredSettingsFrame().setVisible(false);
         }
      }
   }

   private void renderToggle() {
      double toggleX = this.G$src$D$1b2f02a() + this.A() - this.toggleWidth * 2.0F - 17.5;
      double toggleY = this.n() + 10.0 - 3.0;
      Color color = this.activeColorAnimation.q() > 0.0 ? this.activeColorAnimation.getInterpolatedColor() : this.inactiveColorAnimation.getInterpolatedColor();
      if (this.hovered && this.activeColorAnimation.q() > 0.0) {
         color = ColorUtil.offsetRgb(color, 30.0);
      }

      ImageRenderer.drawImage(color, (float)toggleX, (float)toggleY, "togglebg", this.toggleWidth, this.toggleHeight, false);
      ImageRenderer.drawImage(
         this.toggleDotColor,
         (float)toggleX + this.toggleInset + (float)this.togglePositionAnimation.getInterpolatedValue().doubleValue(),
         (float)toggleY + this.toggleInset,
         "toggledot",
         this.toggleDotSize,
         this.toggleDotSize,
         false
      );
   }

   @Override
   public void u() {
      if (this.hovered && !this.w$src$Z$e457mb()) {
         this.hovered = false;
      }

      this.syncEnabledState();
   }

   public HudModuleListEntry setConfigFrame(Frame frame) {
      this.configFrame = frame;
      return this;
   }

   @Override
   public double C() {
      return 60.0;
   }

   public HudModuleListEntry(HudModule hudModule, float f) {
      this.toggleWidth = 6.0F;
      this.toggleHeight = 6.0F;
      this.toggleDotSize = 4.0F;
      this.toggleInset = 1.0F;
      this.inactiveColorAnimation = new ColorAnimation(0.15, J.K, J.W);
      this.activeColorAnimation = new ThemeColorAnimation(0.15, J.W);
      this.togglePositionAnimation = new DoubleAnimation(0.15, 0.0, this.toggleWidth - this.toggleInset);
      this.toggleDotColor = J.r;
      this.module = hudModule;
      this.moduleName = hudModule.getName();
      this.iconKey = hudModule.getKey();
      this.enabled = hudModule.isEnabled();
      this.iconScale = f;
      if (hudModule.getToolTip() != null) {
         this.w(hudModule.getToolTip());
      }

      this.settingsButton.addClickListener(new HudModuleListEntryToggleClickListener(this, hudModule));
      this.addChildren(this.settingsButton);
   }

   @Override
   public void H() {
      GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + 2.0, this.n(), this.A() - 3.0, this.L() - 3.0, !this.hovered && !this.enabled ? J.m : J.l);
      GuiRenderPrimitives.F(
         this.iconKey,
         this.G$src$D$1b2f02a() + 10.0 + 3.0,
         this.n() + 10.0,
         8.0F * this.iconScale,
         8.0F * this.iconScale,
         this.enabled ? J.z() : (this.hovered ? J.f : J.W)
      );
      this.settingsButton.K(this.G$src$D$1b2f02a() + this.A() - 15.0);
      this.settingsButton.S(this.n() + 10.0 - 4.5);
      this.settingsButton.Y(9.0);
      this.renderToggle();
      this.renderModuleName();
   }

   @Override
   public void g(GuiMouseEvent guiMouseEvent) {
      if (guiMouseEvent.getAction().equals(MouseButton.LEFT_CLICK)) {
         this.toggleModule();
      }
   }

   @Override
   public void c() {
      super.c();
   }

   public IconButtonComponent getSettingsButton() {
      return this.settingsButton;
   }
}
