package gg.vape.wrapper.impl;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.mappings.MEntityPlayer;
import gg.vape.module.render.freecam.FreecamPlayerBridge;

public class EntityPlayer extends EntityLivingBase {
   private static boolean H;

   public void j(double d) {
      if (ForgeVersion.MC_1_21_10.d()) {
         FreecamPlayerBridge freecamPlayerBridge = this.getFreecamPlayerBridge();
         if (freecamPlayerBridge != null) {
            freecamPlayerBridge.setPreviousCloakY(d);
         }
      } else {
         MEntityPlayer.r(vapeInstance.getMappings().hd, this.I, d);
      }
   }

   public void V$src$V$1ic0wp1() {
      MEntityPlayer.i(vapeInstance.getMappings().hd, this.I);
   }

   public static boolean z$src$Z$1ivti5h() {
      boolean bl = T$src$Z$1iaxblr();
      return false;
   }

   public boolean X$src$Z$1id4hz7() {
      return MEntityPlayer.j(vapeInstance.getMappings().hd, this.I);
   }

   public void G(double d) {
      if (ForgeVersion.MC_1_21_10.d()) {
         FreecamPlayerBridge freecamPlayerBridge = this.getFreecamPlayerBridge();
         if (freecamPlayerBridge != null) {
            freecamPlayerBridge.setPreviousCloakX(d);
         }
      } else {
         MEntityPlayer.U(vapeInstance.getMappings().hd, this.I, d);
      }
   }

   public void Z$src$V$1ie832h() {
      MEntityPlayer.a(vapeInstance.getMappings().hd, this.I);
   }

   public boolean l$src$Z$1io4duf() {
      return MEntityPlayer.U(vapeInstance.getMappings().hd, this.I);
   }

   public float C$src$F$1i1kt1e() {
      return MEntityPlayer.y(vapeInstance.getMappings().hd, this.I);
   }

   public FoodStats Y$src$Lgg_vape_wrapper_impl_FoodStats_$fakh1z() {
      return new FoodStats(MEntityPlayer.s$src$Ljava_lang_Object_$11essic(vapeInstance.getMappings().hd, this.I));
   }

   public double F$src$D$1i386rr() {
      if (ForgeVersion.MC_1_21_10.d()) {
         FreecamPlayerBridge freecamPlayerBridge = this.getFreecamPlayerBridge();
         return freecamPlayerBridge == null ? -1.0 : freecamPlayerBridge.getPreviousCloakY();
      } else {
         return MEntityPlayer.F(vapeInstance.getMappings().hd, this.I);
      }
   }

   public ItemStack getHeldItemHand() {
      return new ItemStack(MEntityPlayer.M$src$Ljava_lang_Object_$159ckze(vapeInstance.getMappings().hd, this.I));
   }

   public void w$src$V$1iu649y() {
      if (!isNativeAvailable) {
         MEntityPlayer.A(vapeInstance.getMappings().hd, this.getObject());
      }
   }

   public void S(double d) {
      if (ForgeVersion.MC_1_21_10.d()) {
         FreecamPlayerBridge freecamPlayerBridge = this.getFreecamPlayerBridge();
         if (freecamPlayerBridge != null) {
            freecamPlayerBridge.setCloakX(d);
         }
      } else {
         MEntityPlayer.O(vapeInstance.getMappings().hd, this.I, d);
      }
   }

   public int F$src$I$1i386w2() {
      return MEntityPlayer.W(vapeInstance.getMappings().hd, this.I);
   }

   public double L$src$D$1i6iybx() {
      if (ForgeVersion.MC_1_21_10.d()) {
         FreecamPlayerBridge freecamPlayerBridge = this.getFreecamPlayerBridge();
         return freecamPlayerBridge == null ? -1.0 : freecamPlayerBridge.getCloakY();
      } else {
         return MEntityPlayer.q(vapeInstance.getMappings().hd, this.I);
      }
   }

   public Container p$src$Lgg_vape_wrapper_impl_Container_$1a6go00() {
      return new Container(MEntityPlayer.u(vapeInstance.getMappings().hd, this.I));
   }

   public void d$src$V$1ijq103() {
      MEntityPlayer.p$src$V$19rwqod(vapeInstance.getMappings().hd, this.I);
   }

   private FreecamPlayerBridge getFreecamPlayerBridge() {
      if (ForgeVersion.MC_1_21_10.d() && this.isInstance(MappedClasses.zt)) {
         AbstractClientPlayer abstractClientPlayer = new AbstractClientPlayer(this.I);
         return abstractClientPlayer.getFreecamPlayerBridge();
      } else {
         return null;
      }
   }

   public Vec3 m(Vec3 vec3, MoverType moverType) {
      return new Vec3(MEntityPlayer.v(vapeInstance.getMappings().hd, this.I, vec3.getObject(), moverType.getObject()));
   }

   public Team J$src$Lgg_vape_wrapper_impl_Team_$1jrmnx4() {
      return new Team(MEntityPlayer.v$src$Ljava_lang_Object_$92h3ox(vapeInstance.getMappings().hd, this.I));
   }

   public void i$src$V$1imgzyw() {
      MEntityPlayer.y$src$V$epmkwm(vapeInstance.getMappings().hd, this.I);
   }

   public InventoryPlayer V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6() {
      return new InventoryPlayer(vapeInstance.getMappings().hd.K(this.I));
   }

   public void c(double d) {
      if (ForgeVersion.MC_1_21_10.d()) {
         FreecamPlayerBridge freecamPlayerBridge = this.getFreecamPlayerBridge();
         if (freecamPlayerBridge != null) {
            freecamPlayerBridge.setCloakZ(d);
         }
      } else {
         MEntityPlayer.J(vapeInstance.getMappings().hd, this.I, d);
      }
   }

   public void P(double d) {
      if (ForgeVersion.MC_1_21_10.d()) {
         FreecamPlayerBridge freecamPlayerBridge = this.getFreecamPlayerBridge();
         if (freecamPlayerBridge != null) {
            freecamPlayerBridge.setCloakY(d);
         }
      } else {
         MEntityPlayer.B(vapeInstance.getMappings().hd, this.I, d);
      }
   }

   public void C(Entity entity) {
      MEntityPlayer.o(vapeInstance.getMappings().hd, this.I, entity.getObject());
   }

   public void p(double d) {
      if (ForgeVersion.MC_1_21_10.d()) {
         FreecamPlayerBridge freecamPlayerBridge = this.getFreecamPlayerBridge();
         if (freecamPlayerBridge != null) {
            freecamPlayerBridge.setPreviousCloakZ(d);
         }
      } else {
         MEntityPlayer.g(vapeInstance.getMappings().hd, this.I, d);
      }
   }

   public boolean i$src$Z$1imh02c() {
      return MEntityPlayer.O(vapeInstance.getMappings().hd, this.I);
   }

   public float getCooledAttackStrength(float f) {
      return MEntityPlayer.d(vapeInstance.getMappings().hd, this.I, f);
   }

   public double s$src$D$1iryxh0() {
      if (ForgeVersion.MC_1_21_10.d()) {
         FreecamPlayerBridge freecamPlayerBridge = this.getFreecamPlayerBridge();
         return freecamPlayerBridge == null ? -1.0 : freecamPlayerBridge.getCloakY();
      } else {
         return MEntityPlayer.S(vapeInstance.getMappings().hd, this.I);
      }
   }

   public boolean N$src$Z$1i7mk1l() {
      return MEntityPlayer.x(vapeInstance.getMappings().hd, this.I);
   }

   public ModelPlayer C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86() {
      return new ModelPlayer(MEntityPlayer.p(vapeInstance.getMappings().hd, this.I));
   }

   public void e(Entity entity) {
      MEntityPlayer.c(vapeInstance.getMappings().hd, this.I, entity.getObject());
   }

   public boolean s$src$Z$1iryxzy() {
      if (ForgeVersion.MC_1_21_11.d()) {
         if (this.isInstance(MappedClasses.z5)) {
            EntityPlayerSP entityPlayerSP = new EntityPlayerSP(this.I);
            NetHandlerPlayClientImpl netHandlerPlayClientImpl = entityPlayerSP.sendQueue();
            return netHandlerPlayClientImpl.M();
         } else {
            return false;
         }
      } else {
         return MEntityPlayer.v$src$Z$p2dyxb(vapeInstance.getMappings().hd, this.I);
      }
   }

   public double C$src$D$1i1kszo() {
      if (ForgeVersion.MC_1_21_10.d()) {
         FreecamPlayerBridge freecamPlayerBridge = this.getFreecamPlayerBridge();
         return freecamPlayerBridge == null ? -1.0 : freecamPlayerBridge.getCloakX();
      } else {
         return MEntityPlayer.v(vapeInstance.getMappings().hd, this.I);
      }
   }

   public EntityPlayer(Object object) {
      super(object);
   }

   public void c(Entity entity) {
      MEntityPlayer.e(vapeInstance.getMappings().hd, this.I, entity.getObject());
   }

   public boolean o$src$Z$1iprrmi() {
      return MEntityPlayer.P(vapeInstance.getMappings().hd, this.I);
   }

   public boolean isBlocking() {
      return this.o$src$Z$1iprrmi();
   }

   public double a$src$D$1ii2msi() {
      if (ForgeVersion.MC_1_21_10.d()) {
         FreecamPlayerBridge freecamPlayerBridge = this.getFreecamPlayerBridge();
         return freecamPlayerBridge == null ? -1.0 : freecamPlayerBridge.getCloakZ();
      } else {
         return MEntityPlayer.l(vapeInstance.getMappings().hd, this.I);
      }
   }

   public static void f(boolean bl) {
      H = bl;
   }

   public void N(int n) {
      MEntityPlayer.p(vapeInstance.getMappings().hd, this.I, n);
   }

   public Container F$src$Lgg_vape_wrapper_impl_Container_$152y6lm() {
      return new Container(MEntityPlayer.G$src$Ljava_lang_Object_$qwvwn4(vapeInstance.getMappings().hd, this.I));
   }

   public Container getInventoryContainer() {
      return this.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm();
   }

   public Container getOpenContainer() {
      return this.p$src$Lgg_vape_wrapper_impl_Container_$1a6go00();
   }

   public InventoryPlayer getInventory() {
      return this.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
   }

   public void closeScreen() {
      vapeInstance.getMappings().hd.closeScreen(this.I);
   }

   public static boolean T$src$Z$1iaxblr() {
      return H;
   }

   public boolean y$src$Z$1iv9pk4() {
      return MEntityPlayer.G$src$Z$1982x40(vapeInstance.getMappings().hd, this.I);
   }

   public GameProfile c$src$Lgg_vape_wrapper_impl_GameProfile_$ir8937() {
      return new GameProfile(MEntityPlayer.S$src$Ljava_lang_Object_$1jlt9bo(vapeInstance.getMappings().hd, this.I));
   }

   public int j$src$I$1in0s92() {
      return MEntityPlayer.M(vapeInstance.getMappings().hd, this.I);
   }

   public double G$src$D$1i3rzd4() {
      if (ForgeVersion.MC_1_21_10.d()) {
         FreecamPlayerBridge freecamPlayerBridge = this.getFreecamPlayerBridge();
         return freecamPlayerBridge == null ? -1.0 : freecamPlayerBridge.getPreviousCloakZ();
      } else {
         return MEntityPlayer.s(vapeInstance.getMappings().hd, this.I);
      }
   }

   public EntityPlayerMacroBridge K$src$Lgg_vape_wrapper_impl_EntityPlayerMacroBridge_$1agjn9() {
      return new EntityPlayerMacroBridge(MEntityPlayer.k(vapeInstance.getMappings().hd, this.I));
   }

   public float i$src$F$1imgzl4() {
      return MEntityPlayer.G(vapeInstance.getMappings().hd, this.I);
   }

   public boolean w$src$Z$1iu64de() {
      return MEntityPlayer.L(vapeInstance.getMappings().hd, this.I);
   }

   public ModelPlayer a_xf_0_C() {
      return this.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86();
   }

   public boolean boolean_o() {
      return this.o$src$Z$1iprrmi();
   }

   static {
      if (!T$src$Z$1iaxblr()) {
         f(true);
      }
   }
}
