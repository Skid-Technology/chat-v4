package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MRenderManager;
import gg.vape.wrapper.Wrapper;

public class RenderManager extends Wrapper {
   private static double interpolatedRenderPosX;
   private static double interpolatedRenderPosY;
   private static double interpolatedRenderPosZ;

   public double getRenderPosZ() {
      return ForgeVersion.MC_1_16_5.d() ? this.getActiveRenderInfo().o().getZ() : MRenderManager.t(vapeInstance.getMappings().CA, this.I);
   }

   public static RenderManager getInstance() {
      return new RenderManager(MRenderManager.T(vapeInstance.getMappings().CA));
   }

   public Quaternion getCameraOrientation() {
      return ForgeVersion.MC_1_21_10.d() ? this.getActiveRenderInfo().G() : new Quaternion(MRenderManager.q(vapeInstance.getMappings().CA, this.I));
   }

   public static double getInterpolatedRenderPosX() {
      return interpolatedRenderPosX;
   }

   public double getRenderPosY() {
      return ForgeVersion.MC_1_16_5.d() ? this.getActiveRenderInfo().o().getY() : MRenderManager.N(vapeInstance.getMappings().CA, this.I);
   }

   public float getPlayerViewX() {
      return ForgeVersion.MC_1_16_5.d() ? this.getActiveRenderInfo().x() : MRenderManager.o(vapeInstance.getMappings().CA, this.I);
   }

   public double getRenderPosX() {
      return ForgeVersion.MC_1_16_5.d() ? this.getActiveRenderInfo().o().getX() : MRenderManager.M(vapeInstance.getMappings().CA, this.I);
   }

   public RenderManager(Object object) {
      super(object);
   }

   public float getPlayerViewY() {
      return ForgeVersion.MC_1_16_5.d() ? this.getActiveRenderInfo().Z() : MRenderManager.r(vapeInstance.getMappings().CA, this.I);
   }

   public ActiveRenderInfo getActiveRenderInfo() {
      return new ActiveRenderInfo(MRenderManager.e(vapeInstance.getMappings().CA, this.I));
   }

   public static double getInterpolatedRenderPosY() {
      return interpolatedRenderPosY;
   }

   public Render getEntityRenderObject(Entity entity) {
      return new Render(MRenderManager.O(vapeInstance.getMappings().CA, this.I, entity.getObject()));
   }

   public static void updateInterpolatedRenderPosition(float partialTicks) {
      EntityLivingBase entityLivingBase = Minecraft.F();
      double previousX = entityLivingBase.M();
      double previousY = entityLivingBase.W();
      double previousZ = entityLivingBase.m$src$D$fwnne5();
      interpolatedRenderPosX = previousX + (entityLivingBase.z() - previousX) * partialTicks;
      interpolatedRenderPosY = previousY + (entityLivingBase.N() - previousY) * partialTicks;
      if (ForgeVersion.MC_1_14_4.d()) {
         interpolatedRenderPosY = interpolatedRenderPosY + entityLivingBase.X();
         Vec3 cameraPos = Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().l().o();
         interpolatedRenderPosX = cameraPos.getX();
         interpolatedRenderPosY = cameraPos.getY();
         interpolatedRenderPosZ = cameraPos.getZ();
      } else {
         interpolatedRenderPosZ = previousZ + (entityLivingBase.h() - previousZ) * partialTicks;
      }
   }

   public static double getInterpolatedRenderPosZ() {
      return interpolatedRenderPosZ;
   }
}
