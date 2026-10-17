package gg.vape.ui.click.frame.impl.hud;

import func.skidline.RectData;
import gg.vape.Vape;
import gg.vape.module.none.ClientSettings;
import gg.vape.module.render.hud.HudModule;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.ui.click.component.ColorDividerComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class HudModuleConfigFrame extends Frame {
   private int transitionState;
   private HudModule selectedModule;
   private String title = "LegitSettingFrame";
   private final DoubleAnimation backdropOpacity = new DoubleAnimation(0.15, 0.0, 100.0);
   private final DoubleAnimation slideProgress = new DoubleAnimation(0.15, 0.0, 1.0);

   public HudModule getSelectedModule() {
      return this.selectedModule == null ? null : Vape.INSTANCE.getModManager().getMod((Class<HudModule>)this.selectedModule.getClass());
   }

   @Override
   public void u() {
      if (this.transitionState == 1) {
         this.transitionState = 2;
         this.backdropOpacity.c();
         this.slideProgress.c();
      }

      if (this.transitionState == 3) {
         this.transitionState = 4;
         this.backdropOpacity.J();
         this.slideProgress.J();
         this.U();
      }

      if (this.transitionState == 4 && this.slideProgress.getInterpolatedValue() == this.slideProgress.getStartValue()) {
         this.transitionState = 0;
         this.selectedModule = null;
         this.setVisible(false);
      }
   }

   public void beginOpening() {
      this.transitionState = 1;
   }

   public void beginClosing() {
      this.transitionState = 3;
   }

   public void setSelectedModule(HudModule hudModule) {
      this.selectedModule = hudModule;
      this.title = hudModule.getName();
   }

   @Override
   public boolean d$src$Z$1lx9d06() {
      return false;
   }

   @Override
   public void v() {
   }

   @Override
   public void M() {
   }

   public void setTitle(String title) {
      this.title = title;
   }

   @Override
   public String getName() {
      return this.title;
   }

   @Override
   public void Y() {
      HudModuleSelectorFrame hudModuleSelectorFrame = ClientSettings.getFrame(HudModuleSelectorFrame.class);
      if (hudModuleSelectorFrame != null) {
         if (!hudModuleSelectorFrame.V$src$Z$1xhop3l()) {
            this.setVisible(false);
         } else {
            if (this.transitionState >= 2) {
               this.U();
            }

            if (this.slideProgress.getInterpolatedValue() != this.slideProgress.getEndValue()) {
               this.D(true);
               this.h(new RectData(hudModuleSelectorFrame.G$src$D$1b2f02a(), hudModuleSelectorFrame.n(), hudModuleSelectorFrame.A(), this.L()));
               this.K(hudModuleSelectorFrame.G$src$D$1b2f02a() + hudModuleSelectorFrame.A() - this.A() * this.slideProgress.getInterpolatedValue());
               this.S(hudModuleSelectorFrame.n());
               this.l$src$V$1mibm4x();
            }

            if (this.G$src$D$1b2f02a() != hudModuleSelectorFrame.G$src$D$1b2f02a() || this.n() != hudModuleSelectorFrame.n()) {
               this.M(
                  hudModuleSelectorFrame.G$src$D$1b2f02a() + hudModuleSelectorFrame.A() - this.A() * this.slideProgress.getInterpolatedValue(),
                  hudModuleSelectorFrame.n()
               );
            }

            this.K(hudModuleSelectorFrame.G$src$D$1b2f02a() + hudModuleSelectorFrame.A() - this.A() * this.slideProgress.getInterpolatedValue());
            GuiRenderPrimitives.e(
               hudModuleSelectorFrame.G$src$D$1b2f02a(),
               hudModuleSelectorFrame.n(),
               hudModuleSelectorFrame.A(),
               this.L(),
               new Color(0, 0, 0, this.backdropOpacity.getInterpolatedValue().intValue()),
               false,
               0.0F,
               0.0F
            );
         }
      }
   }

   @Override
   public double L() {
      HudModuleSelectorFrame hudModuleSelectorFrame = ClientSettings.getFrame(HudModuleSelectorFrame.class);
      return hudModuleSelectorFrame == null ? 0.0 : hudModuleSelectorFrame.L();
   }

   public HudModuleConfigFrame() {
      this.I2 = false;
      this.D(false);
      this.setDisabledOverlayColor(J.i);
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(false);
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
      HudModuleConfigFrameHeaderComponent hudModuleConfigFrameHeaderComponent = new HudModuleConfigFrameHeaderComponent(this);
      hudModuleConfigFrameHeaderComponent.getBackButton().addClickListener(new HudModuleConfigFrameCloseClickHandler(this));
      this.Y(hudModuleConfigFrameHeaderComponent);
      this.setVisible(false);
      this.L(false, false);
      this.h(new ColorDividerComponent(J.l));
   }

   @Override
   public void g(GuiMouseEvent guiMouseEvent) {
      if (!this.w$src$Z$e457mb()) {
         this.beginClosing();
         this.U();
      }
   }

   public boolean isClosing() {
      return this.transitionState == 3 || this.transitionState == 4;
   }
}
