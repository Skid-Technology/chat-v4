package gg.vape.ui.click.component.value;

import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;

public abstract class AbstractListValueComponent extends InteractiveComponent {
   private boolean expanded;
   private boolean hovered;
   private ColorAnimation hoverAnimation = new ColorAnimation(0.15, J.l, J.W);

   public void setExpanded(boolean expanded) {
      if (expanded) {
         for (GuiComponent guiComponent : this.getParentFrameComponent().f()) {
            if (guiComponent instanceof AbstractListValueComponent) {
               ((AbstractListValueComponent)guiComponent).setExpanded(false);
            }
         }
      }

      this.expanded = expanded;
   }

   @Override
   public void u() {
      if (this.hovered && !this.w$src$Z$e457mb()) {
         this.hoverAnimation.J();
         this.hovered = false;
      }
   }

   @Override
   public double C() {
      return 25.0;
   }

   @Override
   public double x() {
      return 110.0;
   }

   @Override
   public void I() {
   }

   public ColorAnimation getHoverAnimation() {
      return this.hoverAnimation;
   }

   public boolean isExpanded() {
      return this.expanded;
   }

   public boolean isHovered() {
      return this.hovered;
   }

   @Override
   public void F() {
      if (!this.hovered) {
         this.hoverAnimation.J();
      }

      this.hovered = true;
   }
}
