package gg.vape.ui.click.component.value;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.FrameComponent;
import gg.vape.wrapper.impl.Minecraft;

public abstract class FloatingValueDropdownLayer<T extends AbstractListValueComponent> extends Frame {
   private double lastSourceX;
   private boolean visibleLastTick;
   private T sourceComponent;
   private double lastSourceY;

   @Override
   public boolean V$src$Z$1xhop3l() {
      return this.sourceComponent != null
         && this.sourceComponent.V$src$Z$1xhop3l()
         && this.sourceComponent.getParentFrameComponent() != null
         && this.sourceComponent.getParentFrameComponent().V$src$Z$1xhop3l()
         && this.sourceComponent.isExpanded();
   }

   @Override
   public void u() {
      super.u();
      boolean visible = this.V$src$Z$1xhop3l();
      if (visible && !this.visibleLastTick) {
         this.refreshContents();
      }

      this.visibleLastTick = visible;
   }

   @Override
   public void v() {
   }

   public void updatePosition() {
      if (this.sourceComponent != null && this.sourceComponent.getParentFrameComponent() != null) {
         double preferredX = this.sourceComponent.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa().G$src$D$1b2f02a()
            + this.sourceComponent.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa().A()
            + 1.0;
         if (preferredX != this.lastSourceX || this.sourceComponent.n() != this.lastSourceY) {
            double targetY = this.sourceComponent.n();
            FrameComponent frameComponent = this.sourceComponent.getParentFrameComponent();
            if (frameComponent.k$src$Z$if6xeb()) {
               targetY = Math.min(targetY, frameComponent.n() + frameComponent.d$src$D$ibccpu() - this.sourceComponent.L());
               targetY = Math.max(
                  targetY,
                  frameComponent.n()
                     + (
                        frameComponent.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc() != null
                           ? frameComponent.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().L()
                           : 0.0
                     )
               );
            }

            double screenCenterX = Minecraft.J() / 2;
            if (preferredX + this.A() / 2.0 > screenCenterX) {
               this.M(preferredX - this.sourceComponent.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa().A() - this.A() - 2.0, targetY);
            } else {
               this.M(preferredX, targetY);
            }

            this.lastSourceX = this.sourceComponent.G$src$D$1b2f02a();
            this.lastSourceY = this.sourceComponent.n();
         }
      }
   }

   @Override
   public void Y() {
      if (this.sourceComponent != null && this.sourceComponent.getParentFrameComponent() != null) {
         ClientSettings.INSTANCE.replaceFrame(this.sourceComponent.getParentFrameComponent().L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa(), this);
      }
   }

   public abstract void refreshContents();

   public T getSourceComponent() {
      return this.sourceComponent;
   }

   @Override
   public String getName() {
      return this.sourceComponent == null ? "sidecar_" + this.hashCode() : "sidecar_" + this.sourceComponent.hashCode();
   }

   public FloatingValueDropdownLayer(T sourceComponent) {
      this.setDisabledOverlayColor(J.i);
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(false);
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
      this.K(300.0);
      this.S(100.0);
      this.sourceComponent = sourceComponent;
      this.Y(false);
      this.L(false, false);
   }
}
