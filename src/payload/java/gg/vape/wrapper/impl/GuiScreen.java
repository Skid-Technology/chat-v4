package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MGuiScreen;
import gg.vape.module.none.ClientSettings;

public class GuiScreen extends Screen {
   private static boolean r;

   public static void Q(boolean bl) {
      r = bl;
   }

   public int g() {
      return MGuiScreen.U(vapeInstance.getMappings().h1, this.I);
   }

   public static boolean p$src$Z$8062rc() {
      return r;
   }

   public int k() {
      return MGuiScreen.D(vapeInstance.getMappings().h1, this.I);
   }

   public static boolean Z() {
      boolean bl = p$src$Z$8062rc();
      return !bl;
   }

   @Override
   public boolean isNull() {
      return !ClientSettings.INSTANCE.inputEnabled ? false : super.isNull();
   }

   public GuiScreen(Object object) {
      super(object);
   }

   public ITextComponent F() {
      return new ITextComponent(vapeInstance.getMappings().h1.S(this.I));
   }

   static {
      if (Z()) {
         Q(true);
      }
   }
}
