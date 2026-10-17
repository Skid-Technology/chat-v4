package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MRenderHelper;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.wrapper.Wrapper;

public class RenderHelper extends Wrapper {
   public RenderHelper(Object object) {
      super(object);
   }

   public static void s() {
      if (!ForgeVersion.MC_1_17.d()) {
         MRenderHelper.L(vapeInstance.getMappingsMapperCompat().x);
      }
   }

   public static void l() {
      if (ForgeVersion.MC_1_17.d()) {
         MRenderHelper.M(vapeInstance.getMappingsMapperCompat().x);
      } else if (ForgeVersion.MC_1_16_5.d()) {
         OpenGlBackendHolder.backend.pushMatrix();
         OpenGlBackendHolder.backend.rotate(-30.0F, 0.0F, 1.0F, 0.0F);
         OpenGlBackendHolder.backend.rotate(165.0F, 1.0F, 0.0F, 0.0F);
         e();
         OpenGlBackendHolder.backend.popMatrix();
      } else {
         MRenderHelper.M(vapeInstance.getMappingsMapperCompat().x);
      }
   }

   public static void e() {
      if (!ForgeVersion.MC_1_17.d()) {
         MRenderHelper.Q(vapeInstance.getMappingsMapperCompat().x);
      }
   }
}
