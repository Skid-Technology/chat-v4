package gg.vape.module.render.esp;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventRenderPlayerPost;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Mod;
import gg.vape.module.SubModule;
import gg.vape.module.render.ESP;
import gg.vape.module.render.entity.RenderEntityContext;
import gg.vape.module.render.entity.RenderEntityContextCache;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtil;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.ModelBiped;
import gg.vape.wrapper.impl.ModelBipedSkeletonBridge;
import gg.vape.wrapper.impl.ModelRenderer;
import gg.vape.wrapper.impl.Render;
import gg.vape.wrapper.impl.RenderManager;
import gg.vape.wrapper.impl.RenderPlayer;
import java.awt.Color;
import org.lwjgl.opengl.GL11;

public class ESPSkeleton extends SubModule<ESP> {
   private final ESP parent = this.getParent();

   @EventHandler
   public void onRenderPlayerPost(EventRenderPlayerPost event) {
      EntityPlayer entityPlayer = event.getEntityPlayer();
      float radToDeg = (float) (180.0 / Math.PI);
      GL11.glPushMatrix();
      OpenGlBackendHolder.backend.disableCapability(2929);
      event.getEntityRenderer().B(0.0);
      double camX = RenderManager.getInterpolatedRenderPosX();
      double camY = RenderManager.getInterpolatedRenderPosY();
      double camZ = RenderManager.getInterpolatedRenderPosZ();
      Color color = this.parent.resolveEntityColor(event.getThePlayer(), entityPlayer.getObject());
      if (color != null) {
         RenderEntityContext renderEntityContext = RenderEntityContextCache.getOrCreate(entityPlayer, event.getThePlayer());
         if (renderEntityContext.isAttackable()) {
            color = Color.BLUE;
         }

         float partialTicks = Minecraft.getTimer().renderPartialTicks();
         double prevX = entityPlayer.M();
         double prevY = entityPlayer.W();
         double prevZ = entityPlayer.m$src$D$fwnne5();
         double renderX = prevX + (entityPlayer.z() - prevX) * partialTicks - camX;
         double renderY = prevY + (entityPlayer.N() - prevY) * partialTicks - camY;
         double renderZ = prevZ + (entityPlayer.h() - prevZ) * partialTicks - camZ;
         boolean blendWasEnabled = GL11.glIsEnabled(3042);
         RenderUtil.d();
         GL11.glBlendFunc(770, 771);
         if (!blendWasEnabled) {
            OpenGlBackendHolder.backend.enableCapability(3042);
         }

         GL11.glBlendFunc(770, 771);
         RenderUtils.w(color);
         OpenGlBackendHolder.backend.disableCapability(2896);
         OpenGlBackendHolder.backend.enableCapability(2848);
         OpenGlBackendHolder.backend.disableCapability(3553);
         GL11.glTranslated(renderX, renderY, renderZ);
         Render render = this.parent.renderManager.getEntityRenderObject(entityPlayer);
         ModelBipedSkeletonBridge modelBipedSkeletonBridge;
         if (render.isInstance(MappedClasses.D0)
            && (modelBipedSkeletonBridge = new RenderPlayer(render.getObject()).getMainModel()).isNotNull()
            && modelBipedSkeletonBridge.isInstance(MappedClasses.zV)) {
            float distance = (float)renderEntityContext.getDistance();
            float lineWidth = Math.max(4.0F * ((100.0F - Math.min(distance, 100.0F)) / 100.0F), 0.1F);
            GL11.glLineWidth(lineWidth);
            ModelBiped modelBiped = new ModelBiped(modelBipedSkeletonBridge.getObject());
            boolean sneaking = renderEntityContext.isSneaking();
            float legOffset = sneaking ? 0.6F : 0.75F;
            float bodyRotation = entityPlayer.W$src$F$153nzpr();
            GL11.glRotatef(bodyRotation, 0.0F, -999.0F, 0.0F);
            double sneakOffset = sneaking ? -0.2 : 0.0;
            GL11.glTranslated(-0.15, legOffset, sneakOffset);
            ModelRenderer rightLeg = modelBiped.getBipedRightLeg();
            float angleX = rightLeg.getRotateAngleX() * radToDeg;
            float angleY = rightLeg.getRotateAngleY() * radToDeg;
            float angleZ = rightLeg.getRotateAngleZ() * radToDeg;
            GL11.glRotatef(angleX, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-angleY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-angleZ, 0.0F, 0.0F, 1.0F);
            GL11.glBegin(1);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, -legOffset, 0.0);
            GL11.glEnd();
            GL11.glRotatef(angleZ, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(angleY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-angleX, 1.0F, 0.0F, 0.0F);
            GL11.glTranslated(0.3, 0.0, 0.0);
            ModelRenderer leftLeg = modelBiped.getBipedLeftLeg();
            angleX = leftLeg.getRotateAngleX() * radToDeg;
            angleY = leftLeg.getRotateAngleY() * radToDeg;
            angleZ = leftLeg.getRotateAngleZ() * radToDeg;
            GL11.glRotatef(angleX, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-angleY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-angleZ, 0.0F, 0.0F, 1.0F);
            GL11.glBegin(1);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, -legOffset, 0.0);
            GL11.glEnd();
            GL11.glRotatef(angleZ, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(angleY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-angleX, 1.0F, 0.0F, 0.0F);
            GL11.glTranslated(-0.15, 0.0, 0.0);
            GL11.glBegin(1);
            GL11.glVertex3d(0.15, 0.0, 0.0);
            GL11.glVertex3d(-0.15, 0.0, 0.0);
            GL11.glEnd();
            if (sneaking) {
               GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
            }

            GL11.glBegin(1);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, 0.65, 0.0);
            GL11.glEnd();
            GL11.glTranslated(0.0, 0.65, 0.0);
            GL11.glBegin(1);
            GL11.glVertex3d(0.35, 0.0, 0.0);
            GL11.glVertex3d(-0.35, 0.0, 0.0);
            GL11.glEnd();
            GL11.glTranslated(-0.35, 0.0, 0.0);
            ModelRenderer rightArm = modelBiped.getBipedRightArm();
            angleX = rightArm.getRotateAngleX() * radToDeg;
            angleY = rightArm.getRotateAngleY() * radToDeg;
            angleZ = rightArm.getRotateAngleZ() * radToDeg;
            GL11.glRotatef(angleX, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-angleY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-angleZ, 0.0F, 0.0F, 1.0F);
            GL11.glBegin(1);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, -0.6, 0.0);
            GL11.glEnd();
            GL11.glRotatef(angleZ, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(angleY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-angleX, 1.0F, 0.0F, 0.0F);
            GL11.glTranslated(0.7, 0.0, 0.0);
            ModelRenderer leftArm = modelBiped.getBipedLeftArm();
            angleX = leftArm.getRotateAngleX() * radToDeg;
            angleY = leftArm.getRotateAngleY() * radToDeg;
            angleZ = leftArm.getRotateAngleZ() * radToDeg;
            GL11.glRotatef(angleX, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-angleY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-angleZ, 0.0F, 0.0F, 1.0F);
            GL11.glBegin(1);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, -0.6, 0.0);
            GL11.glEnd();
            GL11.glRotatef(angleZ, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(angleY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-angleX, 1.0F, 0.0F, 0.0F);
            GL11.glTranslated(-0.35, 0.0, 0.0);
            GL11.glRotatef(-bodyRotation, 0.0F, -999.0F, 0.0F);
            float headYaw = entityPlayer.J();
            float headPitch = entityPlayer.V();
            double headLength = 0.4;
            GL11.glRotated(headYaw, 0.0, -999.0, 0.0);
            GL11.glRotated(headPitch, 999.0, 0.0, 0.0);
            GL11.glBegin(1);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, headLength, 0.0);
            GL11.glEnd();
            GL11.glBegin(1);
            GL11.glVertex3d(0.0, headLength, 0.0);
            GL11.glVertex3d(0.0, headLength, 0.25);
            GL11.glEnd();
            GL11.glRotated(headPitch, 999.0, 0.0, 0.0);
            GL11.glRotated(-headYaw, 0.0, 999.0, 0.0);
         }

         if (!blendWasEnabled) {
            OpenGlBackendHolder.backend.disableCapability(3042);
         }

         OpenGlBackendHolder.backend.enableCapability(3553);
         OpenGlBackendHolder.backend.disableCapability(2848);
         OpenGlBackendHolder.backend.enableCapability(2896);
         RenderUtil.Y();
      }

      GL11.glColor3d(1.0, 1.0, 1.0);
      event.getEntityRenderer().O(0.0);
      OpenGlBackendHolder.backend.enableCapability(2929);
      GL11.glPopMatrix();
   }

   public ESPSkeleton(Mod parent, String name) {
      super(parent, name);
   }
}
