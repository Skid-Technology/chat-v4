package gg.vape.ui.click.component.input;

import gg.vape.ui.click.component.TextInputComponentBase;

public class CompactTextInputComponent extends TextInputComponentBase {
   private static String am;

   @Override
   public float getLeftInset() {
      return 0.0F;
   }

   public static void U(String string) {
      am = string;
   }

   @Override
   public double x() {
      return 0.0;
   }

   @Override
   public double C() {
      return 0.0;
   }

   public CompactTextInputComponent(String string) {
      super(string);
      this.o(100.0);
      this.Y(22.5);
   }

   @Override
   public void submit() {
   }

   @Override
   public void c() {
      super.c();
   }

   public static String getName() {
      return am;
   }

   static {
      if (getName() != null) {
         U("F62Qvc");
      }
   }
}
