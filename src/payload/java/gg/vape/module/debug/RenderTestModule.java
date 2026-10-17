package gg.vape.module.debug;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventRender3D;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.utils.render.ItemIconRenderer;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;

public class RenderTestModule extends Mod {
   private static final String MODULE_NAME = "Render Test Module";

   @EventHandler
   public void onRender3D(EventRender3D event) {
      ItemIconRenderer.renderItemStack(ItemStack.S(Item.T(1)), 10.0F, 1.0F, 16, 16);
      ItemIconRenderer.renderItemStack(ItemStack.S(Item.T(10)), 20.0F, 1.0F, 16, 16);
      ItemIconRenderer.renderItemStack(ItemStack.S(Item.T(11)), 30.0F, 1.0F, 16, 16);
   }

   public RenderTestModule() {
      super(MODULE_NAME, -1, Category.UTILITY);
   }

   @Override
   public void onEnable() {
   }
}
