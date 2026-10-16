package gg.vape.utils.render;

import gg.vape.wrapper.impl.PotionEffect;

public interface PotionEffectIconRenderBackend {
   void render(float var1, float var2, int var3, int var4, float var5);

   default void renderQueued(float x, float y, int width, int height, float opacity, boolean worldSpace) {
      this.render(x, y, width, height, opacity);
   }

   void capture(PotionEffect var1);

   void dispose();
}
