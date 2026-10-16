/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.account.MinecraftSessionWrapper;
import gg.vape.mapping.mappings.MMinecraft;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.EffectRenderer;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EntityRenderer;
import gg.vape.wrapper.impl.FontManager;
import gg.vape.wrapper.impl.FontRenderer;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Framebuffer;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.GuiScreen;
import gg.vape.wrapper.impl.GuiSpriteManager;
import gg.vape.wrapper.impl.KeyboardHandler;
import gg.vape.wrapper.impl.ModelManager;
import gg.vape.wrapper.impl.MouseHandler;
import gg.vape.wrapper.impl.MouseHelper;
import gg.vape.wrapper.impl.NetHandlerPlayClientImpl;
import gg.vape.wrapper.impl.NetworkManager;
import gg.vape.wrapper.impl.PlayerControllerMP;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.RenderGlobal;
import gg.vape.wrapper.impl.RenderItem;
import gg.vape.wrapper.impl.RenderManager;
import gg.vape.wrapper.impl.RenderTypeBuffer;
import gg.vape.wrapper.impl.ResourceManager;
import gg.vape.wrapper.impl.ScaledResolution;
import gg.vape.wrapper.impl.ServerData;
import gg.vape.wrapper.impl.TextureManager;
import gg.vape.wrapper.impl.TextureManagerBridge;
import gg.vape.wrapper.impl.TextureManagerHandle;
import gg.vape.wrapper.impl.Timer;
import gg.vape.wrapper.impl.TitledScreen;
import gg.vape.wrapper.impl.VoxelShape;
import gg.vape.wrapper.impl.WorldClient;
import java.util.Map;

public class Minecraft
extends Wrapper {
    private static Object var_java_lang_Object_L;
    private static boolean var_boolean_s;

    public static void J(boolean bl) {
        if (ForgeVersion.MC_1_16_5.d()) {
            Minecraft.p().D(bl);
            return;
        }
        MMinecraft.t(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i(), bl);
    }

    public static NetworkManager x$src$Lgg_vape_wrapper_impl_NetworkManager_$1sglv7v() {
        return new NetworkManager(MMinecraft.w(Vape.INSTANCE.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i()));
    }

    public static boolean V() {
        return MMinecraft.I(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i());
    }

    public static int w() {
        return MMinecraft.y(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i());
    }

    public static void b() {
        if (ForgeVersion.MC_1_16_5.d()) {
            Minecraft.p().Q();
            return;
        }
        MMinecraft.R(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i());
    }

    public static void r(int n) {
        MMinecraft.x(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i(), n);
    }

    public static Map P() {
        return Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.h$src$Ljava_util_Map_$8i5rvc(Minecraft.i());
    }

    public static RenderManager D() {
        return ForgeVersion.MC_1_7_10.L() ? RenderManager.getInstance() : new RenderManager(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.w(Minecraft.i()));
    }

    public static GuiSpriteManager T() {
        return new GuiSpriteManager(MMinecraft.p(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i()));
    }

    public static boolean l$src$Z$b9uwii() {
        boolean bl = Minecraft.g();
        return false;
    }

    public static int h() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return Minecraft.p().R();
        }
        return Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.R(Minecraft.i());
    }

    public static void O(boolean bl) {
        MMinecraft.u(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i(), bl);
    }

    public static TextureManagerHandle getMainRenderTarget() {
        if (!ForgeVersion.MC_1_21_10.d()) {
            return null;
        }
        return new TextureManagerHandle(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.V(Minecraft.i()));
    }

    public static boolean a() {
        return Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.f(Minecraft.i());
    }

    public static EffectRenderer z() {
        return new EffectRenderer(MMinecraft.v(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i()));
    }

    public static EntityLivingBase F() {
        return new EntityLivingBase(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.h(Minecraft.i()));
    }

    public static ModelManager x() {
        return new ModelManager(MMinecraft.n(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i()));
    }

    public static boolean F$src$Z$aoypys() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return Minecraft.p().x();
        }
        return MMinecraft.f(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i());
    }

    public static VoxelShape H$src$Lgg_vape_wrapper_impl_VoxelShape_$1dlcquv() {
        return new VoxelShape(MMinecraft.x(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i()));
    }

    public static FontRenderer getFontRenderer() {
        return new FontRenderer(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.J(Minecraft.i()));
    }

    public static ScaledResolution G() {
        return new ScaledResolution();
    }

    public static PlayerControllerMP playerController() {
        return new PlayerControllerMP(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.r(Minecraft.i()));
    }

    public static int Q() {
        return MMinecraft.S(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i());
    }

    public static Framebuffer getFrameBuffer() {
        return new Framebuffer(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.u(Minecraft.i()));
    }

    public static TextureManagerBridge M() {
        return new TextureManagerBridge(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.P(Minecraft.i()));
    }

    public static Entity Y() {
        return new Entity(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.F(Minecraft.i()));
    }

    public static void e(boolean bl) {
        var_boolean_s = bl;
    }

    public static FontManager q() {
        if (!ForgeVersion.MC_1_21_10.d()) {
            return null;
        }
        Object object = Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.j(Minecraft.i());
        return object != null ? new FontManager(object) : null;
    }

    public static int J() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return Minecraft.p().I();
        }
        return Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.Y(Minecraft.i());
    }

    public static boolean m$src$Z$baep3v() {
        return Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.X$src$Z$1ecebix(Minecraft.i());
    }

    public static Object u() {
        return Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.R$src$Ljava_lang_Object_$11ec019(Minecraft.i());
    }

    public static RenderItem v() {
        if (ForgeVersion.MC_1_7_10.L()) {
            return RenderItem.d();
        }
        return new RenderItem(MMinecraft.k(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i()));
    }

    public static ResourceManager getResourcePackRepository() {
        return new ResourceManager(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.X(Minecraft.i()));
    }

    public static Timer getTimer() {
        return new Timer(MMinecraft.W(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i()));
    }

    public static KeyboardHandler r() {
        return new KeyboardHandler(MMinecraft.o(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i()));
    }

    public static GameSettings gameSettings() {
        return new GameSettings(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.b(Minecraft.i()));
    }

    public static MouseHandler s() {
        return new MouseHandler(MMinecraft.X(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i()));
    }

    public static NetHandlerPlayClientImpl N() {
        return new NetHandlerPlayClientImpl(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.a(Minecraft.i()));
    }

    public static EntityPlayerSP thePlayer() {
        return new EntityPlayerSP(Vape.INSTANCE.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.T(Minecraft.i()));
    }

    public static int l() {
        return Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.L();
    }

    public static boolean g() {
        return var_boolean_s;
    }

    public static void B(GuiScreen guiScreen) {
        Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.r(Minecraft.i(), guiScreen.getObject());
    }

    public static void E(int n) {
        MMinecraft.v(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i(), n);
    }

    public static void W(Entity entity) {
        Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.a(Minecraft.i(), entity.getObject());
    }

    public static TitledScreen k() {
        return new TitledScreen(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.A(Minecraft.i()));
    }

    public static RenderTypeBuffer getFramerateLimitTracker() {
        return new RenderTypeBuffer(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.S(Minecraft.i()));
    }

    public Minecraft() {
        super(Vape.INSTANCE.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.J());
    }

    public static void w(MinecraftSessionWrapper minecraftSessionWrapper) {
        MMinecraft.p(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i(), minecraftSessionWrapper.getObject());
    }

    public static MinecraftSessionWrapper Q$src$Lgg_vape_account_MinecraftSessionWrapper_$1ftnn3u() {
        return new MinecraftSessionWrapper(MMinecraft.G(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i()));
    }

    public static Object i() {
        if (var_java_lang_Object_L == null) {
            var_java_lang_Object_L = Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.J();
        }
        return var_java_lang_Object_L;
    }

    public static void S() {
        MMinecraft.Y(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i());
    }

    public static TextureManager getTextureManager() {
        return new TextureManager(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.c(Minecraft.i()));
    }

    public static void R() {
        MMinecraft.b(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i());
    }

    public static void r(EntityLivingBase entityLivingBase) {
        Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.G(Minecraft.i(), entityLivingBase == null ? null : entityLivingBase.getObject());
    }

    public static void currentScreen(Object object) {
        Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.Y(Minecraft.i(), object);
    }

    public static RayTraceResult p$src$Lgg_vape_wrapper_impl_RayTraceResult_$5rw6n0() {
        return new RayTraceResult(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.q(Minecraft.i()));
    }

    public static RayTraceResult objectMouseOver() {
        return Minecraft.p$src$Lgg_vape_wrapper_impl_RayTraceResult_$5rw6n0();
    }

    public static MouseHelper p() {
        return new MouseHelper(MMinecraft.y$src$Ljava_lang_Object_$1igycos(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i()));
    }

    public static EntityRenderer m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf() {
        return new EntityRenderer(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.m(Minecraft.i()));
    }

    public static void v(Runnable runnable) {
        MMinecraft.m(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U).invokeVoid(Minecraft.i(), runnable);
    }

    public static ServerData H() {
        return new ServerData(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.l(Minecraft.i()));
    }

    public static WorldClient theWorld() {
        return new WorldClient(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.E(Minecraft.i()));
    }

    public static RenderGlobal O() {
        return new RenderGlobal(MMinecraft.N(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U, Minecraft.i()));
    }

    public static void F$src$V$aoypvc() {
        Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.V$src$V$9672l7(Minecraft.i());
        if (ForgeVersion.MC_1_16_5.d()) {
            Minecraft.s().u();
        }
    }

    public static void X(int n) {
        if (ForgeVersion.MC_1_16_5.d()) {
            Minecraft.p().a(n);
            return;
        }
        Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.B(Minecraft.i(), n);
    }

    public static void O(RayTraceResult rayTraceResult) {
        Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.P(Minecraft.i(), rayTraceResult.getObject());
    }

    public static void U(int n) {
        if (ForgeVersion.MC_1_16_5.d()) {
            Minecraft.p().H(n);
            return;
        }
        Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.R(Minecraft.i(), n);
    }

    public static GuiScreen currentScreen() {
        return new GuiScreen(Minecraft.vapeInstance.getMappings().var_gg_vape_mapping_mappings_MMinecraft_U.U(Minecraft.i()));
    }

    public static Timer a_jo_2_T() {
        return Minecraft.getTimer();
    }

    public static EntityPlayerSP a_xH_J() {
        return Minecraft.thePlayer();
    }

    public static GuiScreen a_pt_1_w() {
        return Minecraft.currentScreen();
    }

    public static GameSettings a_w3_0_S() {
        return Minecraft.gameSettings();
    }

    static {
        if (!Minecraft.g()) {
            Minecraft.e(true);
        }
    }
}

