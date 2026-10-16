package gg.vape.utils.render;

import gg.vape.wrapper.impl.ItemStack;

public interface ItemIconRenderBackend {
   void dispose();

   default void renderQueued(float x, float y, int width, int height, float opacity, boolean worldSpace) {
      this.render(x, y, width, height, opacity);
   }

   void capture(ItemStack var1, float var2);

   void render(float var1, float var2, int var3, int var4, float var5);
}
