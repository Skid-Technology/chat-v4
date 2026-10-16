package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEntityClientPlayerMP;

public class EntityClientPlayerMP extends EntityPlayerSP {
   public void N(boolean bl) {
      MEntityClientPlayerMP.D(vapeInstance.getMappings().q_, this.I, bl);
   }

   public void setTicksSinceMovePacket(int n) {
      MEntityClientPlayerMP.Y(vapeInstance.getMappings().q_, this.I, n);
   }

   public void x(double d) {
      MEntityClientPlayerMP.v(vapeInstance.getMappings().q_, this.I, d);
   }

   public double y$src$D$lo113c() {
      return MEntityClientPlayerMP.H(vapeInstance.getMappings().q_, this.I);
   }

   public double I$src$D$kxmwm0() {
      return MEntityClientPlayerMP.x(vapeInstance.getMappings().q_, this.I);
   }

   public void w(float f) {
      MEntityClientPlayerMP.O(vapeInstance.getMappings().q_, this.I, f);
   }

   public void Q(double d) {
      MEntityClientPlayerMP.q(vapeInstance.getMappings().q_, this.I, d);
   }

   public void S(float f) {
      MEntityClientPlayerMP.D(vapeInstance.getMappings().q_, this.I, f);
   }

   @Override
   public void sendChatMessage(String string) {
      MEntityClientPlayerMP.e(vapeInstance.getMappings().q_, this.I, string);
   }

   public double O$src$D$l0xo66() {
      return MEntityClientPlayerMP.g(vapeInstance.getMappings().q_, this.I);
   }

   public float E$src$F$kvfqaa() {
      return MEntityClientPlayerMP.K(vapeInstance.getMappings().q_, this.I);
   }

   public void J(double d) {
      MEntityClientPlayerMP.z(vapeInstance.getMappings().q_, this.I, d);
   }

   public float o() {
      return MEntityClientPlayerMP.l(vapeInstance.getMappings().q_, this.I);
   }

   public boolean a() {
      return MEntityClientPlayerMP.T(vapeInstance.getMappings().q_, this.I);
   }

   public void A(boolean bl) {
      MEntityClientPlayerMP.F(vapeInstance.getMappings().q_, this.I, bl);
   }

   @Override
   public NetHandlerPlayClientImpl sendQueue() {
      return new NetHandlerPlayClientImpl(MEntityClientPlayerMP.W(vapeInstance.getMappings().q_, this.I));
   }

   public EntityClientPlayerMP(Object object) {
      super(object);
   }

   public void a(double d) {
      MEntityClientPlayerMP.n(vapeInstance.getMappings().q_, this.I, d);
   }

   public void v(boolean bl) {
      MEntityClientPlayerMP.S(vapeInstance.getMappings().q_, this.I, bl);
   }

   public double w() {
      return MEntityClientPlayerMP.p(vapeInstance.getMappings().q_, this.I);
   }

   public int getTicksSinceMovePacket() {
      return MEntityClientPlayerMP.K$src$I$jaup0x(vapeInstance.getMappings().q_, this.I);
   }

   public boolean R$src$Z$l2l2h7() {
      return MEntityClientPlayerMP.Q(vapeInstance.getMappings().q_, this.I);
   }
}
