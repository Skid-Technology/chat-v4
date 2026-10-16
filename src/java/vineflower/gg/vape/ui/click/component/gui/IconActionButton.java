package gg.vape.ui.click.component.gui;

import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;

public class IconActionButton extends ActionButtonBase {
   protected String iconResource;
   protected ColorAnimation iconColorAnimation = new ColorAnimation(0.15, J.W, J.f);
   protected float iconScale;

   public ColorAnimation getIconColorAnimation() {
      return this.iconColorAnimation;
   }

   public IconActionButton(String iconResource, double iconScale, double width, double height, Color backgroundColor, double borderThickness) {
      super(width, height, backgroundColor, borderThickness);
      this.iconResource = iconResource;
      this.iconScale = (float)iconScale;
   }

   public IconActionButton(String iconResource, double iconScale, double width, double height, Color backgroundColor) {
      super(width, height, backgroundColor);
      this.iconResource = iconResource;
      this.iconScale = (float)iconScale;
   }

   @Override
   public void setHovered(boolean hovered) {
      if (this.hovered != hovered) {
         this.iconColorAnimation.J();
      }

      super.setHovered(hovered);
   }

   public void setIconColorAnimation(ColorAnimation iconColorAnimation) {
      this.iconColorAnimation = iconColorAnimation;
   }

   @Override
   public void H() {
      super.H();
      ImageRenderer.drawRes(
         this.iconColorAnimation.getInterpolatedColor(),
         (float)(this.double_G() + this.double_A() / 2.0 - 32.0F * this.iconScale / 2.0F),
         (float)(this.double_n() + this.double_L() / 2.0 - 32.0F * this.iconScale / 2.0F),
         this.iconResource,
         this.iconScale
      );
   }

   public ColorAnimation getIconColorAnimationCompat() {
      return this.getIconColorAnimation();
   }
}
