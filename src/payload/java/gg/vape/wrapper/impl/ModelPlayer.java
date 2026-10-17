package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MModelPlayer;
import gg.vape.wrapper.Wrapper;

public class ModelPlayer extends Wrapper {
   public boolean isFlying() {
      return MModelPlayer.v(vapeInstance.getMappings().Ri, this.I);
   }

   public boolean N() {
      return MModelPlayer.u(vapeInstance.getMappings().Ri, this.I);
   }

   public float m$src$F$1kykyr0() {
      return MModelPlayer.p(vapeInstance.getMappings().Ri, this.I);
   }

   public boolean isCreativeMode() {
      return MModelPlayer.k(vapeInstance.getMappings().Ri, this.I);
   }

   public float l() {
      return MModelPlayer.Y(vapeInstance.getMappings().Ri, this.I);
   }

   public boolean H() {
      return MModelPlayer.D(vapeInstance.getMappings().Ri, this.I);
   }

   public void c(boolean bl) {
      MModelPlayer.t(vapeInstance.getMappings().Ri, this.I, bl);
   }

   public ModelPlayer(Object object) {
      super(object);
   }

   public boolean c() {
      return MModelPlayer.a(vapeInstance.getMappings().Ri, this.I);
   }

   public static ModelPlayer Q() {
      return new ModelPlayer(MModelPlayer.C(vapeInstance.getMappings().Ri));
   }

   public void G(float f) {
      MModelPlayer.H(vapeInstance.getMappings().Ri, this.I, f);
   }

   public void n(boolean bl) {
      MModelPlayer.y(vapeInstance.getMappings().Ri, this.I, bl);
   }

   public void R(float f) {
      MModelPlayer.E(vapeInstance.getMappings().Ri, this.I, f);
   }
}
