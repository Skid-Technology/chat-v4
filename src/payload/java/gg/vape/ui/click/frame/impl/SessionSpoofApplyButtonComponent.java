package gg.vape.ui.click.frame.impl;

import gg.vape.account.MinecraftSessionWrapper;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;

class SessionSpoofApplyButtonComponent extends TextButton {
   final SessionSpoofFrame X_;
   private static final String cb = "Logged in (";

   @Override
   public boolean w$src$Z$e457mb() {
      return SessionSpoofFrame.t(this.X_) == null ? false : super.w$src$Z$e457mb();
   }

   @Override
   public void u() {
      super.u();
      MinecraftSessionWrapper minecraftSessionWrapper;
      if (SessionSpoofFrame.t(this.X_) == null && (minecraftSessionWrapper = Minecraft.Q$src$Lgg_vape_account_MinecraftSessionWrapper_$1ftnn3u()).isNotNull()) {
         SessionSpoofFrame.i(this.X_).setLabelText("Logged in (" + minecraftSessionWrapper.getUsername() + ")");
      }
   }

   @Override
   public double A() {
      return 140.0;
   }

   SessionSpoofApplyButtonComponent(SessionSpoofFrame sessionSpoofFrame, String string, double d, Color color, Color color2) {
      super(string, d, color, color2);
      this.X_ = sessionSpoofFrame;
   }

   @Override
   public double L() {
      return 20.0;
   }
}
