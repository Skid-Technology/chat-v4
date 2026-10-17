package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.wrapper.Wrapper;

public class KeyBinding extends Wrapper {
   public void setPressed(boolean bl) {
      if (bl) {
         this.I();
      } else {
         this.e();
      }
   }

   public static void H() {
      Vape.INSTANCE.getMappings().hJ.R();
   }

   public void Z() {
      boolean bl2 = ClientSettings.isPhysicalKeyDown(this);
      boolean bl = this.isKeyDown() != bl2;
      if (bl) {
         this.setPressed(bl2);
      }
   }

   public boolean u() {
      return vapeInstance.getMappings().hJ.a(this.I);
   }

   public void e() {
      if (ForgeVersion.MC_1_16_5.d()) {
         vapeInstance.getMappings().hJ.S(this.I);
      } else {
         this.onTick(0);
         setKeyBindState(this, false);
      }
   }

   public boolean isKeyDown() {
      return vapeInstance.getMappings().hJ.S$src$Z$wvm5qt(this.I);
   }

   public static void setKeyBindState(KeyBinding keyBinding, boolean bl) {
      if (ForgeVersion.MC_1_16_5.d()) {
         vapeInstance.getMappings().hJ.d(keyBinding.u$src$Lgg_vape_wrapper_impl_InputMappingsInput_$1c10gpv().getObject(), bl);
      } else {
         vapeInstance.getMappings().hJ.g(keyBinding.getKeyCode(), bl);
      }
   }

   public static void setLegacyKeyBindState(int keyCode, boolean pressed) {
      vapeInstance.getMappings().hJ.g(keyCode, pressed);
   }

   public boolean isPressed() {
      return vapeInstance.getMappings().hJ.M(this.I);
   }

   public void onTick(int n) {
      vapeInstance.getMappings().hJ.q(this.I, n);
   }

   public KeyBinding(Object object) {
      super(object);
   }

   public int getKeyCode() {
      return ForgeVersion.MC_1_16_5.d() ? this.u$src$Lgg_vape_wrapper_impl_InputMappingsInput_$1c10gpv().getKeyCode() : vapeInstance.getMappings().hJ.B(this.I);
   }

   private InputMappingsInput u$src$Lgg_vape_wrapper_impl_InputMappingsInput_$1c10gpv() {
      return new InputMappingsInput(vapeInstance.getMappings().hJ.l(this.I));
   }

   public static void onTick(KeyBinding keyBinding) {
      if (ForgeVersion.MC_1_16_5.d()) {
         vapeInstance.getMappings().hJ.x(keyBinding.u$src$Lgg_vape_wrapper_impl_InputMappingsInput_$1c10gpv().getObject());
      } else {
         vapeInstance.getMappings().hJ.s(keyBinding.getKeyCode());
      }
   }

   public static void onLegacyTick(int keyCode) {
      vapeInstance.getMappings().hJ.s(keyCode);
   }

   public void setCancelled(boolean bl) {
      vapeInstance.getMappings().hJ.o(this.I, bl);
   }

   public void I() {
      setKeyBindState(this, true);
      onTick(this);
   }

   public int V() {
      return vapeInstance.getMappings().hJ.n(this.I);
   }
}
