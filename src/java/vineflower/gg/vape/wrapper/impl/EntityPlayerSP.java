package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEntityPlayerSP;
import gg.vape.ui.click.component.GuiComponent;

public class EntityPlayerSP extends AbstractClientPlayer {
   private static GuiComponent[] q;

   public float a$src$F$1txy325() {
      return ForgeVersion.MC_1_7_10.B() ? new EntityClientPlayerMP(this.getObject()).o() : MEntityPlayerSP.q(vapeInstance.getMappings().CC, this.I);
   }

   @Override
   public float F() {
      return ForgeVersion.MC_1_12_2.d() ? vapeInstance.getMappings().CC.z(this.I) : super.F();
   }

   public double X$src$D$1tszxo6() {
      return ForgeVersion.MC_1_7_10.B() ? new EntityClientPlayerMP(this.getObject()).w() : MEntityPlayerSP.g(vapeInstance.getMappings().CC, this.I);
   }

   public float O$src$F$1to1sdn() {
      return MEntityPlayerSP.M(vapeInstance.getMappings().CC, this.I);
   }

   public void E(int n) {
      if (ForgeVersion.MC_1_7_10.B()) {
         new EntityClientPlayerMP(this.getObject()).setTicksSinceMovePacket(n);
      } else {
         MEntityPlayerSP.y(vapeInstance.getMappings().CC, this.I, n);
      }
   }

   public static GuiComponent[] M$src$ALgg_vape_ui_click_component_GuiComponent_$1jrrwr5() {
      return q;
   }

   @Override
   public void M(float f) {
      if (ForgeVersion.MC_1_12_2.d()) {
         vapeInstance.getMappings().CC.l(this.I, f);
      } else {
         super.M(f);
      }
   }

   public float g() {
      return ForgeVersion.MC_1_7_10.B()
         ? new EntityClientPlayerMP(this.getObject()).E$src$F$kvfqaa()
         : MEntityPlayerSP.z(vapeInstance.getMappings().CC, this.I);
   }

   public int z$src$I$1uboxyr() {
      return ForgeVersion.MC_1_20_6.d() ? 0 : vapeInstance.getMappings().CC.r(this.I);
   }

   public void A(float f) {
      if (ForgeVersion.MC_1_7_10.B()) {
         new EntityClientPlayerMP(this.getObject()).S(f);
      } else {
         MEntityPlayerSP.B(vapeInstance.getMappings().CC, this.I, f);
      }
   }

   public void Z(float f) {
      if (ForgeVersion.MC_1_7_10.B()) {
         new EntityClientPlayerMP(this.getObject()).w(f);
      } else {
         MEntityPlayerSP.Z(vapeInstance.getMappings().CC, this.I, f);
      }
   }

   public double o$src$D$1u5n7bh() {
      return ForgeVersion.MC_1_7_10.B()
         ? new EntityClientPlayerMP(this.getObject()).y$src$D$lo113c()
         : MEntityPlayerSP.a(vapeInstance.getMappings().CC, this.I);
   }

   public float k$src$F$1u3g0zr() {
      return MEntityPlayerSP.a$src$F$12v06yw(vapeInstance.getMappings().CC, this.I);
   }

   public void sendChatMessage(String string) {
      if (ForgeVersion.MC_1_7_10.B()) {
         new EntityClientPlayerMP(this.getObject()).sendChatMessage(string);
      } else {
         MEntityPlayerSP.X(vapeInstance.getMappings().CC, this.I, string);
      }
   }

   public EntityPlayerSP(Object object) {
      super(object);
   }

   public void I(double d) {
      if (ForgeVersion.MC_1_7_10.B()) {
         new EntityClientPlayerMP(this.getObject()).J(d);
      } else {
         MEntityPlayerSP.m(vapeInstance.getMappings().CC, this.I, d);
      }
   }

   public void z(double d) {
      if (ForgeVersion.MC_1_7_10.B()) {
         new EntityClientPlayerMP(this.getObject()).a(d);
      } else {
         MEntityPlayerSP.T(vapeInstance.getMappings().CC, this.I, d);
      }
   }

   public float t$src$F$1u8e6c0() {
      return MEntityPlayerSP.O(vapeInstance.getMappings().CC, this.I);
   }

   public void a(float f) {
      MEntityPlayerSP.d(vapeInstance.getMappings().CC, this.I, f);
   }

   public void E(float f) {
      MEntityPlayerSP.j(vapeInstance.getMappings().CC, this.I, f);
   }

   public void d(float f) {
      MEntityPlayerSP.f(vapeInstance.getMappings().CC, this.I, f);
   }

   public int L$src$I$1tmeeo5() {
      return vapeInstance.getMappings().CC.L(this.I);
   }

   public void Y(double d) {
      if (ForgeVersion.MC_1_7_10.B()) {
         new EntityClientPlayerMP(this.getObject()).Q(d);
      } else {
         MEntityPlayerSP.X(vapeInstance.getMappings().CC, this.I, d);
      }
   }

   public int y$src$I$1ub55de() {
      return ForgeVersion.MC_1_7_10.B()
         ? new EntityClientPlayerMP(this.getObject()).getTicksSinceMovePacket()
         : MEntityPlayerSP.z$src$I$9x5vb2(vapeInstance.getMappings().CC, this.I);
   }

   public NetHandlerPlayClientImpl sendQueue() {
      return ForgeVersion.MC_1_7_10.B()
         ? new EntityClientPlayerMP(this.I).sendQueue()
         : new NetHandlerPlayClientImpl(MEntityPlayerSP.I(vapeInstance.getMappings().CC, this.I));
   }

   public float n$src$F$1u53eru() {
      return MEntityPlayerSP.V(vapeInstance.getMappings().CC, this.I);
   }

   public float x$src$F$1ualcpg() {
      return MEntityPlayerSP.j(vapeInstance.getMappings().CC, this.I);
   }

   public void F(float f) {
      MEntityPlayerSP.A(vapeInstance.getMappings().CC, this.I, f);
   }

   public double Q$src$D$1tp5din() {
      return ForgeVersion.MC_1_7_10.B()
         ? new EntityClientPlayerMP(this.getObject()).O$src$D$l0xo66()
         : MEntityPlayerSP.T(vapeInstance.getMappings().CC, this.I);
   }

   public float q$src$F$1u6qsjx() {
      return MEntityPlayerSP.o(vapeInstance.getMappings().CC, this.I);
   }

   public static void P(GuiComponent[] guiComponentArray) {
      q = guiComponentArray;
   }

   public MovementInput movementInput() {
      return new MovementInput(vapeInstance.getMappings().CC.r$src$Ljava_lang_Object_$zogw4x(this.I));
   }

   public MovementInput a_jw_2_I() {
      return this.movementInput();
   }

   static {
      if (M$src$ALgg_vape_ui_click_component_GuiComponent_$1jrrwr5() == null) {
         P(new GuiComponent[1]);
      }
   }
}
