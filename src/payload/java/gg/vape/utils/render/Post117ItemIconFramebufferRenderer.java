package gg.vape.utils.render;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.BlockStateContainerBridge;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.MainWindow;
import gg.vape.wrapper.impl.Matrix4fHandle;
import gg.vape.wrapper.impl.MatrixStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RenderBufferBridge;
import gg.vape.wrapper.impl.RenderItem;
import gg.vape.wrapper.impl.RenderItemTextBridge;
import gg.vape.wrapper.impl.StringTextComponent;
import java.awt.Color;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.List;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class Post117ItemIconFramebufferRenderer implements ItemIconRenderBackend {
   GlFramebuffer framebuffer;

   @Override
   public void capture(ItemStack itemStack, float scale) {
      RenderBatchManager.getInstance().flushGuiBatches(0.0F);
      int iconWidth = 32;
      int iconHeight = 32;
      int previousFramebufferId = GL11.glGetInteger(36006);
      int previousTextureId = GL11.glGetInteger(32873);
      boolean scissorEnabled = GL11.glIsEnabled(3089);
      if (scissorEnabled) {
         OpenGlBackendHolder.backend.disableCapability(3089);
         ByteBuffer viewportBytes = ByteBuffer.allocateDirect(64);
         viewportBytes.order(ByteOrder.nativeOrder());
         IntBuffer viewport = viewportBytes.asIntBuffer();
         gg.vape.wrapper.impl.GL11.X(2978, viewport);
         this.framebuffer = new GlFramebuffer(iconWidth, iconHeight, true);
         this.framebuffer.bind(true);
         GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
         GL11.glClear(16384);
         GL11.glClear(256);
         GlStateManager.enableDepth();
         GlStateManager.enableBlend();
         MatrixStack renderMatrixStack = MatrixStack.A();
         renderMatrixStack.H();
         float guiScale = Minecraft.p().k(Minecraft.gameSettings().T(), false);
         float inverseGuiScale = 1.0F / guiScale;
         renderMatrixStack.S(inverseGuiScale, inverseGuiScale, inverseGuiScale);
         renderMatrixStack.S(Minecraft.J() / 32.0F, Minecraft.h() / 32.0F, 0.0F);
         OpenGlBackendHolder.backend.pushMatrix();
         if (ForgeVersion.MC_1_20_6.d()) {
            RenderItemTextBridge itemRenderState;
            if (ForgeVersion.MC_1_21_6.d()) {
               Object matrixHandle = Matrix4fHandle.b(16);
               itemRenderState = RenderItemTextBridge.l((Matrix4fHandle)matrixHandle);
            } else {
               itemRenderState = RenderItemTextBridge.t(renderMatrixStack);
            }

            if (ForgeVersion.MC_1_21_6.d()) {
               itemRenderState.S().reset();
            }

            ItemStackRenderUtils.renderItemOverlay(itemRenderState, itemStack, 0, -100);
            if (ForgeVersion.MC_1_21_10.d()) {
               List<String> fogEntries = itemRenderState.S().getFogMode().getItemStates();
               if (fogEntries == null || fogEntries.isEmpty()) {
                  OpenGlBackendHolder.backend.popMatrix();
                  return;
               }

               StringTextComponent renderTargetName = new StringTextComponent(fogEntries.get(fogEntries.size() - 1));
               RenderBufferBridge renderBuffer = Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().V();
               renderBuffer.j();
               Post117RenderPhaseCompat.applyRenderPhaseCompat();
               int renderTargetTextureId;
               Object renderTarget;
               if (ForgeVersion.MC_26_1.d()) {
                  Wrapper renderTargetHandle = renderBuffer.P();
                  renderTarget = renderTargetHandle.isNull()
                     ? null
                     : ((BlockStateContainerBridge)renderTargetHandle).getOrUpdate(renderTargetName.getItemStackRenderState());
                  renderTargetTextureId = renderTargetHandle.isNull() ? -1 : ((BlockStateContainerBridge)renderTargetHandle).getTextureId();
               } else {
                  renderTarget = renderBuffer.p().get(renderTargetName.getItemStackRenderState().getModelIdentity());
                  renderTargetTextureId = renderBuffer.F().isNull() ? -1 : renderBuffer.F().getId();
               }

               if (renderTarget == null || renderTargetTextureId == -1) {
                  OpenGlBackendHolder.backend.popMatrix();
                  return;
               }

               Wrapper renderTargetHandle = new MainWindow(renderTarget);
               float minU = ((MainWindow)renderTargetHandle).q();
               float minV = ((MainWindow)renderTargetHandle).r();
               float maxV;
               float maxU;
               if (ForgeVersion.MC_26_1.d()) {
                  maxU = ((MainWindow)renderTargetHandle).i();
                  maxV = ((MainWindow)renderTargetHandle).A();
               } else {
                  int guiScaleFactor = Minecraft.p().P();
                  int pixelOffset = 16 * guiScaleFactor;
                  int renderTargetSize = renderBuffer.L(pixelOffset);
                  maxU = minU + (float)pixelOffset / renderTargetSize;
                  maxV = minV + (float)(-pixelOffset) / renderTargetSize;
               }

               this.framebuffer.bind(true);
               GL11.glColorMask(true, true, true, true);
               GlScissorRect previousScissorRect = BufferedGuiRenderPrimitives.scissorRect;
               BufferedGuiRenderPrimitives.scissorRect = null;
               RenderBatchBuilder batchBuilder = new RenderBatchBuilder()
                  .setTexture(new GlImageTexture(renderTargetTextureId))
                  .addTexturedRect(0.0F, 0.0F, iconWidth, iconHeight, iconWidth, iconHeight, minU, minV, maxU, maxV, Color.WHITE);
               BufferedGuiRenderPrimitives.projectionMatrix = new RenderMatrix4f()
                  .setIdentity()
                  .setOrthographic(0.0F, iconWidth, iconHeight, 0.0F, -21000.0F, 21000.0F);
               RenderBatchManager batchManager = RenderBatchManager.getInstance();
               batchManager.queueGuiBatch(batchBuilder);
               batchManager.setFramebufferOverride(this.framebuffer.framebufferId);
               batchManager.flushGuiBatches(0.0F, false);
               batchManager.restoreFramebufferOverride();
               BufferedGuiRenderPrimitives.scissorRect = previousScissorRect;
               OpenGlBackendHolder.backend.popMatrix();
            }
         } else {
            RenderItem renderItem = Minecraft.v();
            renderItem.a(itemStack, 0, 0, renderMatrixStack);
         }

         this.framebuffer.bindColorTexture();
         this.framebuffer.unbind();
         GL11.glViewport(viewport.get(0), viewport.get(1), viewport.get(2), viewport.get(3));
         GL30.glBindFramebuffer(36160, previousFramebufferId);
         GlStateManager.bindTexture(previousTextureId);
         OpenGlBackendHolder.backend.enableCapability(3089);
      } else {
         ByteBuffer viewportBytes = ByteBuffer.allocateDirect(64);
         viewportBytes.order(ByteOrder.nativeOrder());
         IntBuffer viewport = viewportBytes.asIntBuffer();
         gg.vape.wrapper.impl.GL11.X(2978, viewport);
         this.framebuffer = new GlFramebuffer(iconWidth, iconHeight, true);
         this.framebuffer.bind(true);
         GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
         GL11.glClear(16384);
         GL11.glClear(256);
         GlStateManager.enableDepth();
         GlStateManager.enableBlend();
         MatrixStack renderMatrixStack = MatrixStack.A();
         renderMatrixStack.H();
         float guiScale = Minecraft.p().k(Minecraft.gameSettings().T(), false);
         float inverseGuiScale = 1.0F / guiScale;
         renderMatrixStack.S(inverseGuiScale, inverseGuiScale, inverseGuiScale);
         renderMatrixStack.S(Minecraft.J() / 32.0F, Minecraft.h() / 32.0F, 0.0F);
         OpenGlBackendHolder.backend.pushMatrix();
         if (ForgeVersion.MC_1_20_6.d()) {
            RenderItemTextBridge itemRenderStatex;
            if (ForgeVersion.MC_1_21_6.d()) {
               Object matrixHandle = Matrix4fHandle.b(16);
               itemRenderStatex = RenderItemTextBridge.l((Matrix4fHandle)matrixHandle);
            } else {
               itemRenderStatex = RenderItemTextBridge.t(renderMatrixStack);
            }

            if (ForgeVersion.MC_1_21_6.d()) {
               itemRenderStatex.S().reset();
            }

            ItemStackRenderUtils.renderItemOverlay(itemRenderStatex, itemStack, 0, -100);
            if (ForgeVersion.MC_1_21_10.d()) {
               List<String> fogEntriesx = itemRenderStatex.S().getFogMode().getItemStates();
               if (fogEntriesx == null || fogEntriesx.isEmpty()) {
                  OpenGlBackendHolder.backend.popMatrix();
                  return;
               }

               StringTextComponent renderTargetNamex = new StringTextComponent(fogEntriesx.get(fogEntriesx.size() - 1));
               RenderBufferBridge renderBufferx = Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().V();
               renderBufferx.j();
               Post117RenderPhaseCompat.applyRenderPhaseCompat();
               int renderTargetTextureIdx;
               Object renderTargetx;
               if (ForgeVersion.MC_26_1.d()) {
                  Wrapper renderTargetHandle = renderBufferx.P();
                  renderTargetx = renderTargetHandle.isNull()
                     ? null
                     : ((BlockStateContainerBridge)renderTargetHandle).getOrUpdate(renderTargetNamex.getItemStackRenderState());
                  renderTargetTextureIdx = renderTargetHandle.isNull() ? -1 : ((BlockStateContainerBridge)renderTargetHandle).getTextureId();
               } else {
                  renderTargetx = renderBufferx.p().get(renderTargetNamex.getItemStackRenderState().getModelIdentity());
                  renderTargetTextureIdx = renderBufferx.F().isNull() ? -1 : renderBufferx.F().getId();
               }

               if (renderTargetx == null || renderTargetTextureIdx == -1) {
                  OpenGlBackendHolder.backend.popMatrix();
                  return;
               }

               Wrapper renderTargetHandle = new MainWindow(renderTargetx);
               float minU = ((MainWindow)renderTargetHandle).q();
               float minV = ((MainWindow)renderTargetHandle).r();
               float maxV;
               float maxU;
               if (ForgeVersion.MC_26_1.d()) {
                  maxU = ((MainWindow)renderTargetHandle).i();
                  maxV = ((MainWindow)renderTargetHandle).A();
               } else {
                  int guiScaleFactor = Minecraft.p().P();
                  int pixelOffset = 16 * guiScaleFactor;
                  int renderTargetSize = renderBufferx.L(pixelOffset);
                  maxU = minU + (float)pixelOffset / renderTargetSize;
                  maxV = minV + (float)(-pixelOffset) / renderTargetSize;
               }

               this.framebuffer.bind(true);
               GL11.glColorMask(true, true, true, true);
               GlScissorRect previousScissorRect = BufferedGuiRenderPrimitives.scissorRect;
               BufferedGuiRenderPrimitives.scissorRect = null;
               RenderBatchBuilder batchBuilder = new RenderBatchBuilder()
                  .setTexture(new GlImageTexture(renderTargetTextureIdx))
                  .addTexturedRect(0.0F, 0.0F, iconWidth, iconHeight, iconWidth, iconHeight, minU, minV, maxU, maxV, Color.WHITE);
               BufferedGuiRenderPrimitives.projectionMatrix = new RenderMatrix4f()
                  .setIdentity()
                  .setOrthographic(0.0F, iconWidth, iconHeight, 0.0F, -21000.0F, 21000.0F);
               RenderBatchManager batchManager = RenderBatchManager.getInstance();
               batchManager.queueGuiBatch(batchBuilder);
               batchManager.setFramebufferOverride(this.framebuffer.framebufferId);
               batchManager.flushGuiBatches(0.0F, false);
               batchManager.restoreFramebufferOverride();
               BufferedGuiRenderPrimitives.scissorRect = previousScissorRect;
               OpenGlBackendHolder.backend.popMatrix();
            }
         } else {
            RenderItem renderItem = Minecraft.v();
            renderItem.a(itemStack, 0, 0, renderMatrixStack);
         }

         this.framebuffer.bindColorTexture();
         this.framebuffer.unbind();
         GL11.glViewport(viewport.get(0), viewport.get(1), viewport.get(2), viewport.get(3));
         GL30.glBindFramebuffer(36160, previousFramebufferId);
         GlStateManager.bindTexture(previousTextureId);
      }
   }

   @Override
   public void renderQueued(float x, float y, int width, int height, float opacity, boolean worldSpace) {
      RenderBatchBuilder batchBuilder = new RenderBatchBuilder(VertexCoordinateMode.DEFAULT, worldSpace)
         .setTexture(new GlImageTexture(this.framebuffer.colorTextureId))
         .addTexturedRect(x, y, width, height, 64.0F, 64.0F, 0.0F, 1.0F, 1.0F, 0.0F, new Color(1.0F, 1.0F, 1.0F, opacity));
      if (worldSpace) {
         RenderBatchManager.getInstance().queueWorldBatch(batchBuilder);
      } else {
         RenderBatchManager.getInstance().queueGuiBatch(batchBuilder);
      }
   }

   @Override
   public void render(float x, float y, int width, int height, float opacity) {
      this.renderQueued(x, y, width, height, opacity, false);
   }

   @Override
   public void dispose() {
      this.framebuffer.delete();
      this.framebuffer = null;
   }
}
