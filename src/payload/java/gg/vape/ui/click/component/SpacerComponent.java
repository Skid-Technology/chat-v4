package gg.vape.ui.click.component;

import gg.vape.ui.click.GuiMouseEvent;

public class SpacerComponent extends GuiComponent {
   private double spacerHeight;
   private double spacerWidth;
   private static int legacyState;

   @Override
   public void g(GuiMouseEvent mouseEvent) {
   }

   public static int getLegacyCompatibilityValue() {
      int legacyState = getLegacyState();
      return legacyState == 0 ? 41 : 0;
   }

   @Override
   public double C() {
      return this.spacerHeight;
   }

   @Override
   public void o(double width) {
      super.o(width);
   }

   @Override
   public void I() {
   }

   public static int getLegacyState() {
      return legacyState;
   }

   public SpacerComponent(double width, double height) {
      this.spacerWidth = width;
      this.spacerHeight = height;
      this.o(width);
      this.Y(height);
   }

   @Override
   public void u() {
   }

   @Override
   public double x() {
      return this.spacerWidth;
   }

   @Override
   public void H() {
   }

   public static void setLegacyState(int legacyState) {
      SpacerComponent.legacyState = legacyState;
   }

   @Override
   public void Y(double height) {
      super.Y(height);
      this.setExplicitHeight(height);
      this.spacerHeight = height;
   }

   @Override
   public void F() {
   }

   public void renderSpacer() {
      this.H();
   }

   static {
      if (getLegacyCompatibilityValue() != 0) {
         setLegacyState(56);
      }
   }
}
