package gg.vape.utils.render;

import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.ResourceLocation;
import java.awt.Color;

public interface EntityModelRenderBackend {
   void captureEntity(EntityLivingBase var1);

   void render(float var1, float var2, int var3, int var4, Color var5, float var6);

   void captureTexture(ResourceLocation var1);

   void dispose();
}
