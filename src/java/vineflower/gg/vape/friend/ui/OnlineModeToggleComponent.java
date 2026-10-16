package gg.vape.friend.ui;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class OnlineModeToggleComponent extends PanelComponent {
   private final TextButton rightButton;
   private final DoubleAnimation selectionAnimation;
   private final boolean initialSelection;
   private static int obfuscationSeed;
   private boolean leftSelected;
   private final TextButton leftButton = new OnlineModeToggleLeftTextButton(this, "", 0.7, new Color(255, 255, 255, 102), new Color(255, 255, 255));

   public static void setObfuscationSeed(int seed) {
      obfuscationSeed = seed;
   }

   public OnlineModeToggleComponent(String string, String string2, boolean bl) {
      super(100.0, 12.0);
      this.rightButton = new OnlineModeToggleRightTextButton(this, "", 0.7, new Color(255, 255, 255, 102), new Color(255, 255, 255));
      this.selectionAnimation = new DoubleAnimation(0.1, 0.0, 49.0);
      this.leftButton.setLabelText(string);
      this.rightButton.setLabelText(string2);
      this.leftSelected = bl;
      this.initialSelection = bl;
      this.setShowDisabledOverlay(false);
      if (!bl) {
         this.selectionAnimation.Z();
      }

      this.leftButton.setExplicitWidth(this.A() / 2.0);
      this.leftButton.Y(12.0);
      this.rightButton.setExplicitWidth(this.A() / 2.0);
      this.rightButton.Y(12.0);
      this.leftButton.addClickListener(new OnlineModeToggleInactiveClickHandler(this));
      this.rightButton.addClickListener(new OnlineModeToggleClickHandler(this));
      this.addChildren(this.leftButton, this.rightButton);
   }

   @Override
   public double C() {
      return 12.0;
   }

   @Override
   public void u() {
   }

   @Override
   public void I() {
   }

   public static int getObfuscationSeed() {
      return obfuscationSeed;
   }

   @Override
   public void F() {
   }

   @Override
   public void V() {
   }

   private void toggleSelection() {
      this.setLeftSelected(!this.isLeftSelected());
   }

   @Override
   public void o(double d) {
      super.o(d);

      try {
         this.leftButton.setExplicitWidth(d / 2.0);
         this.rightButton.setExplicitWidth(d / 2.0);
      } catch (Exception var4) {
      }
   }

   public void setLeftSelected(Boolean selected) {
      boolean selectionChanged = this.leftSelected != selected;
      this.leftSelected = selected;
      if (selectionChanged) {
         boolean reverseAnimation = false;
         if (this.initialSelection) {
            if (selected) {
               if (this.selectionAnimation.getInterpolatedValue() == this.selectionAnimation.getEndValue()) {
                  reverseAnimation = true;
               }
            } else if (this.selectionAnimation.getInterpolatedValue() == 0.0) {
               reverseAnimation = true;
            }
         } else if (selected) {
            if (this.selectionAnimation.getInterpolatedValue() == 0.0) {
               reverseAnimation = true;
            }
         } else if (this.selectionAnimation.getInterpolatedValue() == this.selectionAnimation.getEndValue()) {
            reverseAnimation = true;
         }

         if (reverseAnimation) {
            this.selectionAnimation.J();
         }
      }
   }

   @Override
   public void Y() {
   }

   public Boolean isLeftSelected() {
      return this.leftSelected;
   }

   public static int getReservedMagicValue() {
      int n = getObfuscationSeed();
      return n == 0 ? 115 : 0;
   }

   public static void toggleSelection(OnlineModeToggleComponent toggle) {
      toggle.toggleSelection();
   }

   @Override
   public double x() {
      return 100.0;
   }

   @Override
   public void g(GuiMouseEvent guiMouseEvent) {
   }

   @Override
   public void v() {
   }

   public void setTooltips(String leftTooltip, String rightTooltip) {
      this.leftButton.w(leftTooltip);
      this.rightButton.w(rightTooltip);
   }

   @Override
   public void H() {
      GuiRenderPrimitives.d(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), new Color(54, 53, 54, 128));
      GuiRenderPrimitives.I(
         this.G$src$D$1b2f02a() + this.selectionAnimation.getInterpolatedValue(),
         this.n() + 0.5,
         this.A() / 2.0 + 1.0,
         this.L() - 1.0,
         J.y,
         true,
         1.0F,
         1.0F,
         8.0F,
         new Color(0, 0, 0, 70)
      );
      boolean bl = this.isLeftSelected();
      if (bl) {
         SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.6);
         double d = (this.leftButton.L() - smoothFontRenderer.d(this.leftButton.getText())) / 2.0;
         double d2 = this.leftButton.n() + d;
         double d3 = this.G$src$D$1b2f02a() + this.A() * 0.25;
         String string = this.leftButton.getText();
         smoothFontRenderer.W(string, d3, d2, J.A);
         double d4 = this.rightButton.n() + d;
         double d5 = this.G$src$D$1b2f02a() + this.A() * 0.75;
         String string2 = this.rightButton.getText();
         smoothFontRenderer.W(string2, d5, d4, this.rightButton.getBackgroundAnimation().getInterpolatedColor());
      } else {
         SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.6);
         double d = (this.leftButton.L() - smoothFontRenderer.d(this.leftButton.getText())) / 2.0;
         double d6 = this.leftButton.n() + d;
         double d7 = this.G$src$D$1b2f02a() + this.A() * 0.25;
         String string = this.leftButton.getText();
         smoothFontRenderer.W(string, d7, d6, this.leftButton.getBackgroundAnimation().getInterpolatedColor());
         double d8 = this.rightButton.n() + d;
         double d9 = this.G$src$D$1b2f02a() + this.A() * 0.75;
         String string3 = this.rightButton.getText();
         smoothFontRenderer.W(string3, d9, d8, J.A);
      }
   }

   static {
      if (getReservedMagicValue() != 0) {
         setObfuscationSeed(57);
      }
   }
}
