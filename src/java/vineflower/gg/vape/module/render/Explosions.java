package gg.vape.module.render;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventRender3D;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.render.pearl.PearlEspEnderPearlEntityWrapper;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtil;
import gg.vape.utils.render.glu.GluSphere;
import gg.vape.value.BooleanValue;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RenderManager;
import org.lwjgl.opengl.GL11;

public class Explosions extends Mod {
   public final BooleanValue showBlastRing = BooleanValue.create(this, "Blast ring", true, "Shows the blast radius where blocks will be broken");

   @EventHandler
   public void onRender3D(EventRender3D event) {
      double cameraX = RenderManager.getInterpolatedRenderPosX();
      double cameraY = RenderManager.getInterpolatedRenderPosY();
      double cameraZ = RenderManager.getInterpolatedRenderPosZ();
      boolean blendEnabled = GL11.glIsEnabled(3042);
      double blockDamageRadius = 4.0;
      double entityDamageRadius = 8.0;

      for (Object e : Minecraft.theWorld().z()) {
         if (MappedClasses.qM.isAssignableFrom(e.getClass())) {
            PearlEspEnderPearlEntityWrapper pearl = new PearlEspEnderPearlEntityWrapper(e);
            RenderUtil.d();
            OpenGlBackendHolder.backend.enableCapability(3042);
            OpenGlBackendHolder.backend.disableCapability(3553);
            GL11.glBlendFunc(770, 771);
            float partialTicks = event.getTicks();
            double pearlX = pearl.M() + (pearl.z() - pearl.M()) * partialTicks;
            double pearlY = pearl.W() + (pearl.N() - pearl.W()) * partialTicks;
            double pearlZ = pearl.m$src$D$fwnne5() + (pearl.h() - pearl.m$src$D$fwnne5()) * partialTicks;
            double distanceToPlayer = Minecraft.thePlayer().i(pearlX, pearlY, pearlZ);
            OpenGlBackendHolder.backend.translate(pearlX - cameraX, pearlY - cameraY, pearlZ - cameraZ);
            float pulseProgress = (pearl.getAgeTicks() % 5 - partialTicks + 1.0F) / 5.0F;
            float pulseAlpha = pearl.getAgeTicks() / 5 % 2 == 0 ? 1.0F : pulseProgress * pulseProgress;
            int alpha = Math.max(Math.min((int)(255.0F * pulseAlpha), 255), 0);
            if (this.showBlastRing.getEffectiveValue()) {
               OpenGlBackendHolder.backend.setColor(255.0F, 255.0F, 0.0F, (float)((int)(alpha * 0.75F)));
               GluSphere gluSphere = new GluSphere();
               gluSphere.setDrawStyle(100011);
               gluSphere.draw((float)blockDamageRadius, 24, 24);
            }

            OpenGlBackendHolder.backend.setColor(255.0F, 0.0F, 0.0F, (float)((int)(alpha * 0.4F)));
            GluSphere gluSphere = new GluSphere();
            gluSphere.setDrawStyle(100012);
            gluSphere.setOrientation(distanceToPlayer < entityDamageRadius ? 100021 : 100020);
            gluSphere.draw((float)entityDamageRadius, 32, 32);
            OpenGlBackendHolder.backend.enableCapability(3553);
            OpenGlBackendHolder.backend.disableCapability(3042);
            GL11.glPopMatrix();
         }
      }

      if (blendEnabled) {
         OpenGlBackendHolder.backend.enableCapability(3042);
      }
   }

   public Explosions() {
      super("Explosions", 11534100, Category.RENDER, "Shows the explosion radius of TNT");
      this.addValue(this.showBlastRing);
   }
}
