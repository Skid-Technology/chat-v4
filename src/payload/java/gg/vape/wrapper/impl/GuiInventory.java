package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.RenderBatchBuilder;
import gg.vape.utils.render.RenderBatchManager;
import gg.vape.utils.render.RenderMatrix4f;
import gg.vape.utils.render.RenderVector4f;
import gg.vape.utils.render.VertexCoordinateMode;
import gg.vape.wrapper.Wrapper;
import java.util.function.Supplier;

public class GuiInventory extends Wrapper {
   private static Void renderQueuedEntity(RenderMatrix4f renderMatrix, int scale, float mouseX, float mouseY, EntityLivingBase entity) {
      int var10001 = (int)renderMatrix.elements[0];
      Vape.INSTANCE
         .getMappings()
         .guiInventory
         .drawEntityOnScreen(
            var10001, (int)renderMatrix.elements[5], (int)(scale * Vape.INSTANCE.getClientSettings().getGuiScaleFactor()), mouseX, mouseY, entity.getObject()
         );
      return null;
   }

   public static void drawEntityOnScreen(int screenX, int screenY, int scale, float mouseX, float mouseY, EntityLivingBase entity) {
      if (ForgeVersion.MC_1_20_6.d()) {
         Vape.notifyNativeStackTrace();
      }

      if (GuiRenderPrimitives.d()) {
         RenderMatrix4f renderMatrix = new RenderMatrix4f(new RenderVector4f((float)screenX, (float)screenY, 0.0F, 1.0F))
            .multiply(BufferedGuiRenderPrimitives.matrixStack.peek());
         Supplier<Void> renderCallback = () -> renderQueuedEntity(renderMatrix, scale, mouseX, mouseY, entity);
         RenderBatchBuilder renderBatch = new RenderBatchBuilder(VertexCoordinateMode.MINECRAFT, false).setStandaloneRenderCallback(renderCallback);
         RenderBatchManager.getInstance().queueGuiBatch(renderBatch);
      } else {
         Vape.INSTANCE.getMappings().guiInventory.drawEntityOnScreen(screenX, screenY, scale, mouseX, mouseY, entity.getObject());
      }
   }

   public GuiInventory(Object inventoryScreenHandle) {
      super(inventoryScreenHandle);
   }
}
