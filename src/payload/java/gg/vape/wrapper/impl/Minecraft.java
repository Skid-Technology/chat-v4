package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.account.MinecraftSessionWrapper;
import gg.vape.mapping.mappings.MMinecraft;
import gg.vape.wrapper.Wrapper;
import java.util.Map;

public class Minecraft extends Wrapper {
   private static Object L;
   private static boolean s;

   public static void J(boolean bl) {
      if (ForgeVersion.MC_1_16_5.d()) {
         p().D(bl);
      } else {
         MMinecraft.t(vapeInstance.getMappings().U, i(), bl);
      }
   }

   public static NetworkManager x$src$Lgg_vape_wrapper_impl_NetworkManager_$1sglv7v() {
      return new NetworkManager(MMinecraft.w(Vape.INSTANCE.getMappings().U, i()));
   }

   public static boolean V() {
      return MMinecraft.I(vapeInstance.getMappings().U, i());
   }

   public static int w() {
      return MMinecraft.y(vapeInstance.getMappings().U, i());
   }

   public static void b() {
      if (ForgeVersion.MC_1_16_5.d()) {
         p().Q();
      } else {
         MMinecraft.R(vapeInstance.getMappings().U, i());
      }
   }

   public static void r(int n) {
      MMinecraft.x(vapeInstance.getMappings().U, i(), n);
   }

   public static Map P() {
      return vapeInstance.getMappings().U.h$src$Ljava_util_Map_$8i5rvc(i());
   }

   public static RenderManager D() {
      return ForgeVersion.MC_1_7_10.L() ? RenderManager.getInstance() : new RenderManager(vapeInstance.getMappings().U.w(i()));
   }

   public static GuiSpriteManager T() {
      return new GuiSpriteManager(MMinecraft.p(vapeInstance.getMappings().U, i()));
   }

   public static boolean l$src$Z$b9uwii() {
      boolean bl = g();
      return false;
   }

   public static int h() {
      return ForgeVersion.MC_1_16_5.d() ? p().R() : vapeInstance.getMappings().U.R(i());
   }

   public static void O(boolean bl) {
      MMinecraft.u(vapeInstance.getMappings().U, i(), bl);
   }

   public static TextureManagerHandle getMainRenderTarget() {
      return !ForgeVersion.MC_1_21_10.d() ? null : new TextureManagerHandle(vapeInstance.getMappings().U.V(i()));
   }

   public static boolean a() {
      return vapeInstance.getMappings().U.f(i());
   }

   public static EffectRenderer z() {
      return new EffectRenderer(MMinecraft.v(vapeInstance.getMappings().U, i()));
   }

   public static EntityLivingBase F() {
      return new EntityLivingBase(vapeInstance.getMappings().U.h(i()));
   }

   public static ModelManager x() {
      return new ModelManager(MMinecraft.n(vapeInstance.getMappings().U, i()));
   }

   public static boolean F$src$Z$aoypys() {
      return ForgeVersion.MC_1_16_5.d() ? p().x() : MMinecraft.f(vapeInstance.getMappings().U, i());
   }

   public static VoxelShape H$src$Lgg_vape_wrapper_impl_VoxelShape_$1dlcquv() {
      return new VoxelShape(MMinecraft.x(vapeInstance.getMappings().U, i()));
   }

   public static FontRenderer getFontRenderer() {
      return new FontRenderer(vapeInstance.getMappings().U.J(i()));
   }

   public static ScaledResolution G() {
      return new ScaledResolution();
   }

   public static PlayerControllerMP playerController() {
      return new PlayerControllerMP(vapeInstance.getMappings().U.r(i()));
   }

   public static int Q() {
      return MMinecraft.S(vapeInstance.getMappings().U, i());
   }

   public static Framebuffer getFrameBuffer() {
      return new Framebuffer(vapeInstance.getMappings().U.u(i()));
   }

   public static TextureManagerBridge M() {
      return new TextureManagerBridge(vapeInstance.getMappings().U.P(i()));
   }

   public static Entity Y() {
      return new Entity(vapeInstance.getMappings().U.F(i()));
   }

   public static void e(boolean bl) {
      s = bl;
   }

   public static FontManager q() {
      if (!ForgeVersion.MC_1_21_10.d()) {
         return null;
      } else {
         Object object = vapeInstance.getMappings().U.j(i());
         return object != null ? new FontManager(object) : null;
      }
   }

   public static int J() {
      return ForgeVersion.MC_1_16_5.d() ? p().I() : vapeInstance.getMappings().U.Y(i());
   }

   public static boolean m$src$Z$baep3v() {
      return vapeInstance.getMappings().U.X$src$Z$1ecebix(i());
   }

   public static Object u() {
      return vapeInstance.getMappings().U.R$src$Ljava_lang_Object_$11ec019(i());
   }

   public static RenderItem v() {
      return ForgeVersion.MC_1_7_10.L() ? RenderItem.d() : new RenderItem(MMinecraft.k(vapeInstance.getMappings().U, i()));
   }

   public static ResourceManager getResourcePackRepository() {
      return new ResourceManager(vapeInstance.getMappings().U.X(i()));
   }

   public static Timer getTimer() {
      return new Timer(MMinecraft.W(vapeInstance.getMappings().U, i()));
   }

   public static KeyboardHandler r() {
      return new KeyboardHandler(MMinecraft.o(vapeInstance.getMappings().U, i()));
   }

   public static GameSettings gameSettings() {
      return new GameSettings(vapeInstance.getMappings().U.b(i()));
   }

   public static MouseHandler s() {
      return new MouseHandler(MMinecraft.X(vapeInstance.getMappings().U, i()));
   }

   public static NetHandlerPlayClientImpl N() {
      return new NetHandlerPlayClientImpl(vapeInstance.getMappings().U.a(i()));
   }

   public static EntityPlayerSP thePlayer() {
      return new EntityPlayerSP(Vape.INSTANCE.getMappings().U.T(i()));
   }

   public static int l() {
      return vapeInstance.getMappings().U.L();
   }

   public static boolean g() {
      return s;
   }

   public static void B(GuiScreen guiScreen) {
      vapeInstance.getMappings().U.r(i(), guiScreen.getObject());
   }

   public static void E(int n) {
      MMinecraft.v(vapeInstance.getMappings().U, i(), n);
   }

   public static void W(Entity entity) {
      vapeInstance.getMappings().U.a(i(), entity.getObject());
   }

   public static TitledScreen k() {
      return new TitledScreen(vapeInstance.getMappings().U.A(i()));
   }

   public static RenderTypeBuffer getFramerateLimitTracker() {
      return new RenderTypeBuffer(vapeInstance.getMappings().U.S(i()));
   }

   public Minecraft() {
      super(Vape.INSTANCE.getMappings().U.J());
   }

   public static void w(MinecraftSessionWrapper minecraftSessionWrapper) {
      MMinecraft.p(vapeInstance.getMappings().U, i(), minecraftSessionWrapper.getObject());
   }

   public static MinecraftSessionWrapper Q$src$Lgg_vape_account_MinecraftSessionWrapper_$1ftnn3u() {
      return new MinecraftSessionWrapper(MMinecraft.G(vapeInstance.getMappings().U, i()));
   }

   public static Object i() {
      if (L == null) {
         L = vapeInstance.getMappings().U.J();
      }

      return L;
   }

   public static void S() {
      MMinecraft.Y(vapeInstance.getMappings().U, i());
   }

   public static TextureManager getTextureManager() {
      return new TextureManager(vapeInstance.getMappings().U.c(i()));
   }

   public static void R() {
      MMinecraft.b(vapeInstance.getMappings().U, i());
   }

   public static void r(EntityLivingBase entityLivingBase) {
      vapeInstance.getMappings().U.G(i(), entityLivingBase == null ? null : entityLivingBase.getObject());
   }

   public static void currentScreen(Object object) {
      vapeInstance.getMappings().U.Y(i(), object);
   }

   public static RayTraceResult p$src$Lgg_vape_wrapper_impl_RayTraceResult_$5rw6n0() {
      return new RayTraceResult(vapeInstance.getMappings().U.q(i()));
   }

   public static RayTraceResult objectMouseOver() {
      return p$src$Lgg_vape_wrapper_impl_RayTraceResult_$5rw6n0();
   }

   public static MouseHelper p() {
      return new MouseHelper(MMinecraft.y$src$Ljava_lang_Object_$1igycos(vapeInstance.getMappings().U, i()));
   }

   public static EntityRenderer m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf() {
      return new EntityRenderer(vapeInstance.getMappings().U.m(i()));
   }

   public static void v(Runnable runnable) {
      MMinecraft.m(vapeInstance.getMappings().U).invokeVoid(i(), runnable);
   }

   public static ServerData H() {
      return new ServerData(vapeInstance.getMappings().U.l(i()));
   }

   public static WorldClient theWorld() {
      return new WorldClient(vapeInstance.getMappings().U.E(i()));
   }

   public static RenderGlobal O() {
      return new RenderGlobal(MMinecraft.N(vapeInstance.getMappings().U, i()));
   }

   public static void F$src$V$aoypvc() {
      vapeInstance.getMappings().U.V$src$V$9672l7(i());
      if (ForgeVersion.MC_1_16_5.d()) {
         s().u();
      }
   }

   public static void X(int n) {
      if (ForgeVersion.MC_1_16_5.d()) {
         p().a(n);
      } else {
         vapeInstance.getMappings().U.B(i(), n);
      }
   }

   public static void O(RayTraceResult rayTraceResult) {
      vapeInstance.getMappings().U.P(i(), rayTraceResult.getObject());
   }

   public static void U(int n) {
      if (ForgeVersion.MC_1_16_5.d()) {
         p().H(n);
      } else {
         vapeInstance.getMappings().U.R(i(), n);
      }
   }

   public static GuiScreen currentScreen() {
      return new GuiScreen(vapeInstance.getMappings().U.U(i()));
   }

   public static Timer a_jo_2_T() {
      return getTimer();
   }

   public static EntityPlayerSP a_xH_J() {
      return thePlayer();
   }

   public static GuiScreen a_pt_1_w() {
      return currentScreen();
   }

   public static GameSettings a_w3_0_S() {
      return gameSettings();
   }

   static {
      if (!g()) {
         e(true);
      }
   }
}
