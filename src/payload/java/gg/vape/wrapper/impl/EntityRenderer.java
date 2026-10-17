package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEntityRenderer;
import gg.vape.module.control.SharedModuleControlClaims;
import gg.vape.utils.MathUtil;
import gg.vape.wrapper.Wrapper;

public class EntityRenderer extends Wrapper {
   public ActiveRenderInfo l() {
      return new ActiveRenderInfo(MEntityRenderer.Q(vapeInstance.getMappings().RY, this.I));
   }

   public void Y(float f) {
      MEntityRenderer.i(vapeInstance.getMappings().RY, this.I, f);
   }

   public RenderBufferBridge V() {
      return new RenderBufferBridge(MEntityRenderer.E(vapeInstance.getMappings().RY, this.I));
   }

   public void setPointedEntity(Entity entity) {
      if (ForgeVersion.MC_1_16_5.d()) {
         MEntityRenderer.T(vapeInstance.getMappings().RY, Minecraft.i(), entity.getObject());
      } else {
         MEntityRenderer.T(vapeInstance.getMappings().RY, this.I, entity.getObject());
      }
   }

   public float b() {
      return MEntityRenderer.A(vapeInstance.getMappings().RY, this.I);
   }

   public void i(float f) {
      MEntityRenderer.u(vapeInstance.getMappings().RY, this.I, f);
   }

   public void B(MatrixStack matrixStack, float f) {
      MEntityRenderer.M(vapeInstance.getMappings().RY, this.I, matrixStack.getObject(), f);
   }

   public void J(ShaderGroup shaderGroup) {
      MEntityRenderer.D(vapeInstance.getMappings().RY, this.I, shaderGroup.getObject());
   }

   public Entity getPointedEntity() {
      return ForgeVersion.MC_1_16_5.d()
         ? new Entity(MEntityRenderer.Z(vapeInstance.getMappings().RY, Minecraft.i()))
         : new Entity(MEntityRenderer.Z(vapeInstance.getMappings().RY, this.I));
   }

   public ShaderGroupState K() {
      return new ShaderGroupState(MEntityRenderer.P(vapeInstance.getMappings().RY, this.I));
   }

   public void q(float f) {
      MEntityRenderer.N(vapeInstance.getMappings().RY, this.I, f);
   }

   public void m(float f, long l) {
      MEntityRenderer.X(vapeInstance.getMappings().RY, this.I, f, l);
   }

   public void B(double d) {
      if (ForgeVersion.MC_1_16_5.d()) {
         this.l$src$Lgg_vape_wrapper_impl_LightTexture_$to78f8().V();
      } else {
         MEntityRenderer.f(vapeInstance.getMappings().RY, this.I, d);
      }
   }

   public FogRenderer getFogRenderer() {
      return new FogRenderer(MEntityRenderer.getFogRenderer(vapeInstance.getMappings().RY, this.I));
   }

   public GlStateManager$FogState y() {
      return new GlStateManager$FogState(MEntityRenderer.b(vapeInstance.getMappings().RY, this.I));
   }

   public void updateShaderGroupSize(int n, int n2) {
      MEntityRenderer.i(vapeInstance.getMappings().RY, this.I, n, n2);
   }

   public ShaderGroup L() {
      return new ShaderGroup(MEntityRenderer.t(vapeInstance.getMappings().RY, this.I));
   }

   public void s(float f, int n) {
      SharedModuleControlClaims.renderPass.blockRender();
      if (ForgeVersion.MC_1_16_5.d()) {
         GameSettings gameSettings = Minecraft.gameSettings();
         this.i(gameSettings.v() * 16.0F);
         MatrixStack matrixStack = MatrixStack.A();
         Matrix4f matrix4f = this.l(this.l(), f, true);
         matrixStack.F().getMatrix().a(matrix4f);
         this.B(matrixStack, f);
         if (gameSettings.k()) {
            this.Z(matrixStack, f);
         }

         EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
         float f2 = MathUtil.round(f, entityPlayerSP.k$src$F$1u3g0zr(), entityPlayerSP.O$src$F$1to1sdn()) * gameSettings.f() * gameSettings.f();
         if (f2 > 0.0F) {
            int n2 = entityPlayerSP.i(StatusEffect.V()) ? 7 : 20;
            float f3 = 5.0F / (f2 * f2 + 5.0F) - f2 * 0.04F;
            f3 *= f3;
            Vector3f vector3f = Vector3f.create(0.0F, MathUtil.sqrt(2.0F) / 2.0F, MathUtil.sqrt(2.0F) / 2.0F);
            matrixStack.i(vector3f.rotationDegrees((this.Q() + f) * n2));
            matrixStack.S(1.0F / f3, 1.0F, 1.0F);
            float f4 = -(this.Q() + f) * n2;
            matrixStack.i(vector3f.rotationDegrees(f4));
         }

         Matrix4f matrix4f2 = matrixStack.F().getMatrix();
         this.setShaderGroup(matrix4f2);
         SharedModuleControlClaims.renderPass.clearClaimed();
      } else {
         MEntityRenderer.D(vapeInstance.getMappings().RY, this.I, f, n);
         SharedModuleControlClaims.renderPass.clearClaimed();
      }
   }

   public LightTexture l$src$Lgg_vape_wrapper_impl_LightTexture_$to78f8() {
      return new LightTexture(MEntityRenderer.O(vapeInstance.getMappings().RY, this.I));
   }

   public Matrix4f l(ActiveRenderInfo activeRenderInfo, float f, boolean bl) {
      Object object = MEntityRenderer.z(vapeInstance.getMappings().RY, this.I, activeRenderInfo.getObject(), f, bl);
      if (object == null && ForgeVersion.MC_26_1.d()) {
         object = MEntityRenderer.o(vapeInstance.getMappings().RY, this.I);
      }

      return new Matrix4f(object);
   }

   public void setUseShader(boolean bl) {
      if (ForgeVersion.MC_1_7_10.Y()) {
         MEntityRenderer.C(vapeInstance.getMappings().RY, this.I, bl);
      }
   }

   public void setShaderGroup(Matrix4f matrix4f) {
      if (ForgeVersion.MC_1_21_0.d()) {
         RenderSystem.L(matrix4f, Items.perspective());
      } else {
         MEntityRenderer.o(vapeInstance.getMappings().RY, this.I, matrix4f.getObject());
      }
   }

   public int Q() {
      return MEntityRenderer.m(vapeInstance.getMappings().RY, this.I);
   }

   public void Z(MatrixStack matrixStack, float f) {
      MEntityRenderer.z(vapeInstance.getMappings().RY, this.I, matrixStack.getObject(), f);
   }

   public void V(float f) {
      MEntityRenderer.x(vapeInstance.getMappings().RY, this.I, f);
   }

   public void D(float f, long l) {
      if (ForgeVersion.MC_1_21_0.d()) {
         if (ForgeVersion.MC_1_21_11.d()) {
            MEntityRenderer.a(vapeInstance.getMappings().RY, this.I, Minecraft.getTimer().getObject());
         }

         MEntityRenderer.q(vapeInstance.getMappings().RY, this.I, Minecraft.getTimer().getObject());
      } else if (ForgeVersion.MC_1_16_5.d()) {
         MEntityRenderer.h(vapeInstance.getMappings().RY, this.I, f, l, MatrixStack.A().getObject());
      } else {
         MEntityRenderer.F(vapeInstance.getMappings().RY, this.I, f, l);
      }
   }

   public boolean isUseShader() {
      return ForgeVersion.MC_1_7_10.Y() ? MEntityRenderer.c$src$Z$4ble0w(vapeInstance.getMappings().RY, this.I) : false;
   }

   public void r(float f) {
      MEntityRenderer.j(vapeInstance.getMappings().RY, this.I, f);
   }

   public static Object[] getShaderResourceLocations() {
      return MEntityRenderer.k(vapeInstance.getMappings().RY);
   }

   public void O(double d) {
      if (ForgeVersion.MC_1_16_5.d()) {
         this.l$src$Lgg_vape_wrapper_impl_LightTexture_$to78f8().X();
      } else {
         MEntityRenderer.V(vapeInstance.getMappings().RY, this.I, d);
      }
   }

   public static Object s$src$Ljava_lang_Object_$1ecvhy8() {
      return MEntityRenderer.G(vapeInstance.getMappings().RY);
   }

   public EntityRenderer(Object object) {
      super(object);
   }

   public float s() {
      return MEntityRenderer.c(vapeInstance.getMappings().RY, this.I);
   }
}
