package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.module.blatant.invwalk.InvWalkKeyLayout;
import gg.vape.rotation.LocalPlayerRotationUtil;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.TextureManagerHandle;
import gg.vape.wrapper.impl.TextureObjectHandle;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Objects;
import java.util.function.Supplier;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class RenderBatchManager {
   private int savedElementArrayBufferId;
   private RenderBatchBuilder lastWorldBuilder;
   private int targetFramebufferId = 999;
   private int savedArrayBufferId;
   private int savedFramebufferId;
   private int savedReadFramebufferId;
   private int previousTargetFramebufferId = 0;
   private static RenderBatchManager instance;
   private static final long FRAMEBUFFER_REFRESH_INTERVAL_MS = 1000L;
   public RenderBatchBuffer batchBuffer;
   private int savedTextureId;
   private int savedProgramId;
   private boolean batchResourcesBound;
   private int savedVertexArrayId;
   private final Deque<RenderBatchStateFlags> savedGlStates = new ArrayDeque<>();
   private long lastFramebufferRefreshTime = 0L;
   private boolean framebufferFailureLogged;
   private int ownedFramebufferId = -1;
   private int ownedColorTextureId = -1;
   private int ownedDepthTextureId = -1;
   private Object ownedColorTexture;
   private Object ownedDepthTexture;
   private RenderBatchBuilder lastGuiBuilder;
   public ArrayList<RenderBatch> worldBatches = new ArrayList<>();
   public RenderBatchShaderProgram shaderProgram;
   public ArrayList<RenderBatch> guiBatches = new ArrayList<>();

   private boolean shouldStartNewBatch(RenderBatchBuilder builder, RenderBatchBuilder previousBuilder, boolean worldSpace) {
      if (previousBuilder == null) {
         return true;
      } else if (builder.getCoordinateMode() != previousBuilder.getCoordinateMode()) {
         return true;
      } else {
         GlImageTexture texture = builder.getTexture();
         GlImageTexture previousTexture = previousBuilder.getTexture();
         if (texture != previousTexture) {
            if (texture == null || previousTexture == null) {
               return true;
            }

            if (texture.textureId != previousTexture.textureId) {
               return true;
            }
         }

         if (builder.getDrawSetupCallback() == null && previousBuilder.getDrawSetupCallback() == null) {
            PrimitiveTopology topology = builder.getTopology();
            PrimitiveTopology previousTopology = previousBuilder.getTopology();
            if (!Objects.equals(topology, previousTopology)) {
               return true;
            } else if (!builder.modelMatrix.contentEquals(previousBuilder.modelMatrix)) {
               return true;
            } else {
               GlCapabilityState capabilityState = builder.getCapabilityState();
               GlCapabilityState previousCapabilityState = previousBuilder.getCapabilityState();
               if (!Objects.equals(capabilityState, previousCapabilityState)) {
                  return true;
               } else {
                  GlScissorRect scissorRect = builder.getScissorRect();
                  GlScissorRect previousScissorRect = previousBuilder.getScissorRect();
                  return !Objects.equals(scissorRect, previousScissorRect) ? true : this.exceedsBatchCapacity(builder, worldSpace);
               }
            }
         } else {
            return true;
         }
      }
   }

   public int getLastBatchMergeCount(boolean worldSpace) {
      try {
         return worldSpace
            ? this.worldBatches.get(this.worldBatches.size() - 1).getMergedBuilderCount()
            : this.guiBatches.get(this.guiBatches.size() - 1).getMergedBuilderCount();
      } catch (Exception var3) {
         return 0;
      }
   }

   public int getTargetFramebufferId() {
      long currentTime = System.currentTimeMillis();
      if (this.targetFramebufferId == 999 || currentTime - this.lastFramebufferRefreshTime >= 1000L && this.previousTargetFramebufferId == 0) {
         this.lastFramebufferRefreshTime = currentTime;
         int previousFramebufferId = this.targetFramebufferId;
         this.refreshTargetFramebuffer();
         if (previousFramebufferId != 999 && previousFramebufferId != this.targetFramebufferId && this.targetFramebufferId != -1) {
         }
      }

      return this.targetFramebufferId;
   }

   public static RenderBatchManager getInstance() {
      if (!GuiRenderPrimitives.d()) {
         throw new IllegalStateException("Attempting to call RenderEngine on an older version of OpenGL");
      } else {
         if (instance == null) {
            instance = new RenderBatchManager();
         }

         return instance;
      }
   }

   public void setFramebufferOverride(int framebufferId) {
      this.previousTargetFramebufferId = this.getTargetFramebufferId();
      this.targetFramebufferId = framebufferId;
   }

   private boolean exceedsBatchCapacity(RenderBatchBuilder builder, boolean worldSpace) {
      if (builder.getVertexData() != null && builder.getIndices() != null) {
         RenderBatch renderBatch = worldSpace ? this.worldBatches.get(this.worldBatches.size() - 1) : this.guiBatches.get(this.guiBatches.size() - 1);
         return renderBatch.getBuilders().size() >= 4500;
      } else {
         return false;
      }
   }

   public void flushGuiBatches(float partialTicks, boolean resetProjection) {
      if (!this.guiBatches.isEmpty()) {
         if (resetProjection) {
            LocalPlayerRotationUtil.resetProjectionMatrix();
         }

         this.captureGlBindings();
         this.saveAndPrepareGlState();

         try {
            for (RenderBatch renderBatch : this.guiBatches) {
               this.ensureBatchResourcesBound();
               boolean scissorTestEnabled = GL11.glIsEnabled(3089);
               if (renderBatch.hasScissorRect()) {
                  if (!scissorTestEnabled) {
                     OpenGlBackendHolder.backend.enableCapability(3089);
                  }

                  GlScissorRect glScissorRect = renderBatch.getScissorRect();
                  GL11.glScissor(glScissorRect.x, glScissorRect.y, glScissorRect.width, glScissorRect.height);
               } else if (scissorTestEnabled) {
                  OpenGlBackendHolder.backend.disableCapability(3089);
               }

               if (renderBatch.getStandaloneRenderCallbacks().isEmpty()) {
                  if (renderBatch.getDrawSetupCallback() != null) {
                     renderBatch.getDrawSetupCallback().get();
                  }

                  this.batchBuffer.stageBatch(renderBatch);
                  if (this.batchResourcesBound) {
                     this.batchBuffer.draw();
                  }
               } else {
                  this.unbindBatchVertexArray();
                  this.restoreCapturedGlBindings();
                  OpenGlBackendHolder.backend.disableCapability(3089);

                  for (Supplier supplier : renderBatch.getStandaloneRenderCallbacks()) {
                     supplier.get();
                  }

                  this.captureGlBindings();
               }
            }
         } finally {
            this.guiBatches.clear();
            this.lastGuiBuilder = null;
            OpenGlBackendHolder.backend.disableCapability(3089);
            this.unbindBatchVertexArray();
            this.restoreSavedGlState();
            this.restoreCapturedGlBindings();
            BufferedGuiRenderPrimitives.matrixStack = new RenderMatrixStack();
            if (resetProjection) {
               LocalPlayerRotationUtil.updateProjectionMatrix(partialTicks);
            }
         }
      }
   }

   private void unbindBatchVertexArray() {
      if (this.batchResourcesBound) {
         GL30.glBindVertexArray(0);
         this.batchResourcesBound = false;
      }
   }

   public void flushGuiBatches(float partialTicks) {
      this.flushGuiBatches(partialTicks, true);
   }

   private static Throwable propagateThrowable(Throwable throwable) {
      return throwable;
   }

   public static String buildInitializationDiagnostics(String phase, Throwable throwable) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Failed to initialize modern renderer\n");
      stringBuilder.append("Phase: ").append(phase).append('\n');
      OpenGlDeviceInfo.appendDeviceInfo(stringBuilder);

      try {
         stringBuilder.append("Current Program: ").append(GL11.glGetInteger(35725)).append('\n');
         stringBuilder.append("Current VAO: ").append(GL11.glGetInteger(34229)).append('\n');
         stringBuilder.append("Array Buffer: ").append(GL11.glGetInteger(34964)).append('\n');
         stringBuilder.append("Element Buffer: ").append(GL11.glGetInteger(34965)).append('\n');
         stringBuilder.append("Framebuffer: ").append(GL11.glGetInteger(36006)).append('\n');
         stringBuilder.append("GL Error: ").append(GL11.glGetError()).append('\n');
      } catch (Throwable var4) {
         stringBuilder.append("GL Diagnostics Error: ").append(var4.getClass().getSimpleName()).append(": ").append(var4.getMessage()).append('\n');
      }

      if (throwable != null) {
         stringBuilder.append("Exception: ").append(throwable.getClass().getName());
         if (throwable.getMessage() != null) {
            stringBuilder.append(": ").append(throwable.getMessage());
         }

         stringBuilder.append('\n');
      }

      return stringBuilder.toString();
   }

   public void restoreRendererState() {
      GL30.glBindVertexArray(0);
      this.restoreSavedGlState();
      GL30.glBindVertexArray(this.savedVertexArrayId);
      GL20.glUseProgram(this.savedProgramId);
      GL11.glBindTexture(3553, this.savedTextureId);
      GL30.glBindFramebuffer(36009, this.savedFramebufferId);
      GL30.glBindFramebuffer(36008, this.savedReadFramebufferId);
      GL15.glBindBuffer(34962, this.savedArrayBufferId);
      GL15.glBindBuffer(34963, this.savedElementArrayBufferId);
      this.batchResourcesBound = false;
   }

   public void queueWorldBatch(RenderBatchBuilder builder) {
      if (this.shouldStartNewBatch(builder, this.lastWorldBuilder, true)) {
         this.worldBatches.add(new RenderBatch(builder));
      } else {
         RenderBatch renderBatch = this.worldBatches.get(this.worldBatches.size() - 1);
         renderBatch.incrementMergedBuilderCount();
         renderBatch.addBuilder(builder);
      }

      this.lastWorldBuilder = builder;
   }

   private void restoreSavedGlState() {
      if (!this.savedGlStates.isEmpty()) {
         RenderBatchStateFlags renderBatchStateFlags = this.savedGlStates.pop();
         if (renderBatchStateFlags.blendEnabled) {
            GlStateManager.enableBlend();
         } else {
            GlStateManager.disableBlend();
         }

         if (renderBatchStateFlags.cullFaceEnabled) {
            OpenGlBackendHolder.backend.enableCapability(2884);
         } else {
            OpenGlBackendHolder.backend.disableCapability(2884);
         }

         if (renderBatchStateFlags.depthTestEnabled) {
            GlStateManager.enableDepth();
         } else {
            GlStateManager.disableDepth();
         }

         GL11.glDepthMask(renderBatchStateFlags.depthWriteEnabled);
      }
   }

   public RenderBatchBuffer getBatchBuffer() {
      return this.batchBuffer;
   }

   public void queueGuiBatch(RenderBatchBuilder builder) {
      if (builder.getTexture() == null) {
         builder.setTexture(TextureAtlasRegistry.getInstance().get("vape_texture").getTexture());
      }

      if (this.shouldStartNewBatch(builder, this.lastGuiBuilder, false)) {
         this.guiBatches.add(new RenderBatch(builder));
      } else {
         RenderBatch renderBatch = this.guiBatches.get(this.guiBatches.size() - 1);
         renderBatch.incrementMergedBuilderCount();
         renderBatch.addBuilder(builder);
      }

      this.lastGuiBuilder = builder;
   }

   private void captureGlBindings() {
      this.savedVertexArrayId = GL11.glGetInteger(34229);
      this.savedProgramId = GL11.glGetInteger(35725);
      this.savedTextureId = GL11.glGetInteger(32873);
      this.savedFramebufferId = GL11.glGetInteger(36006);
      this.savedReadFramebufferId = GL11.glGetInteger(36010);
      this.savedArrayBufferId = GL11.glGetInteger(34964);
      this.savedElementArrayBufferId = GL11.glGetInteger(34965);
   }

   public static IllegalStateException initializationFailure(String phase, Throwable throwable) {
      return new IllegalStateException(buildInitializationDiagnostics(phase, throwable), throwable);
   }

   public void flushWorldBatches(float partialTicks) {
      if (!this.worldBatches.isEmpty()) {
         this.captureGlBindings();
         this.saveAndPrepareGlState();

         try {
            LocalPlayerRotationUtil.updateProjectionMatrix(partialTicks);

            for (RenderBatch renderBatch : this.worldBatches) {
               this.ensureBatchResourcesBound();
               renderBatch.getCapabilityState().apply();
               if (renderBatch.getStandaloneRenderCallbacks().isEmpty()) {
                  this.batchBuffer.stageBatch(renderBatch);
                  if (this.batchResourcesBound) {
                     this.batchBuffer.draw();
                  }
               } else {
                  this.unbindBatchVertexArray();
                  this.restoreCapturedGlBindings();
                  GlStateManager.enableBlend();

                  for (Supplier<Void> supplier : renderBatch.getStandaloneRenderCallbacks()) {
                     supplier.get();
                  }

                  this.captureGlBindings();
               }
            }
         } finally {
            this.lastWorldBuilder = null;
            this.worldBatches.clear();
            this.unbindBatchVertexArray();
            this.restoreSavedGlState();
            this.restoreCapturedGlBindings();
            BufferedGuiRenderPrimitives.matrixStack = new RenderMatrixStack();
            LocalPlayerRotationUtil.resetProjectionMatrix();
         }
      }
   }

   public void restoreFramebufferOverride() {
      this.targetFramebufferId = this.previousTargetFramebufferId;
      this.previousTargetFramebufferId = 0;
   }

   private void bindBatchResources() {
      int targetFramebufferId = this.getTargetFramebufferId();
      if (targetFramebufferId != -1) {
         GL30.glBindFramebuffer(36160, targetFramebufferId);
      }

      this.batchBuffer.bindResources();
      this.batchResourcesBound = true;
   }

   public static void shutdown() {
      RenderBatchState.cleanupInstance();
      InvWalkKeyLayout.clearShaders();
      if (instance != null) {
         instance.deleteOwnedFramebuffer();
      }

      instance = null;
   }

   public void refreshTargetFramebuffer() {
      if (ForgeVersion.MC_1_21_10.d()) {
         try {
            TextureManagerHandle mainRenderTarget = Minecraft.getMainRenderTarget();
            TextureObjectHandle colorTexture;
            if (mainRenderTarget != null
               && mainRenderTarget.isNotNull()
               && (colorTexture = mainRenderTarget.getColorTexture()) != null
               && colorTexture.isNotNull()) {
               TextureObjectHandle depthTexture = mainRenderTarget.getDepthTexture();
               int depthTextureId = depthTexture != null && depthTexture.isNotNull() ? depthTexture.getId() : 0;
               int resolvedFramebufferId;
               if (ForgeVersion.MC_26_2.d()) {
                  resolvedFramebufferId = this.resolveOwnedFramebuffer(colorTexture, depthTexture, colorTexture.getId(), depthTextureId);
               } else {
                  resolvedFramebufferId = colorTexture.resolveFramebufferId(depthTextureId);
               }

               if (resolvedFramebufferId <= 0) {
                  this.targetFramebufferId = -1;
                  if (ForgeVersion.MC_26_2.d()) {
                     this.deleteOwnedFramebuffer();
                  }

                  this.logFramebufferFailure("framebuffer resolution returned " + resolvedFramebufferId, null);
                  return;
               }

               this.targetFramebufferId = resolvedFramebufferId;
               return;
            }

            this.logFramebufferFailure("main render target or color texture is unavailable", null);
         } catch (LinkageError | RuntimeException var6) {
            this.logFramebufferFailure("framebuffer resolution threw an exception", var6);
         }
      }

      if (ForgeVersion.MC_26_2.d()) {
         this.deleteOwnedFramebuffer();
      }

      this.targetFramebufferId = -1;
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private int resolveOwnedFramebuffer(TextureObjectHandle colorTexture, TextureObjectHandle depthTexture, int colorTextureId, int depthTextureId) {
      if (colorTextureId > 0 && depthTexture != null && !depthTexture.isNull() && depthTextureId > 0) {
         Object colorTextureObject = colorTexture.getObject();
         Object depthTextureObject = depthTexture.getObject();
         if (this.ownedFramebufferId > 0
            && this.ownedColorTextureId == colorTextureId
            && this.ownedDepthTextureId == depthTextureId
            && this.ownedColorTexture == colorTextureObject
            && this.ownedDepthTexture == depthTextureObject) {
            return this.ownedFramebufferId;
         } else {
            int previousDrawFramebufferId = GL11.glGetInteger(36006);
            int previousReadFramebufferId = GL11.glGetInteger(36010);
            int previousOwnedFramebufferId = this.ownedFramebufferId;
            int framebufferId = GL30.glGenFramebuffers();
            boolean framebufferCreated = false;
            boolean var20 = false /* VF: Semaphore variable */;

            int status;
            label205: {
               int var23;
               label206: {
                  try {
                     var20 = true;
                     if (framebufferId <= 0) {
                        status = -1;
                        var20 = false;
                        break label205;
                     }

                     GL30.glBindFramebuffer(36160, framebufferId);
                     GL30.glFramebufferTexture2D(36160, 36064, 3553, colorTextureId, 0);
                     GL30.glFramebufferTexture2D(36160, 36096, 3553, depthTextureId, 0);
                     GL11.glDrawBuffer(36064);
                     GL11.glReadBuffer(36064);
                     status = GL30.glCheckFramebufferStatus(36160);
                     if (status != 36053) {
                        this.logFramebufferFailure("owned framebuffer is incomplete: 0x" + Integer.toHexString(status), null);
                        var23 = -1;
                        var20 = false;
                        break label206;
                     }

                     framebufferCreated = true;
                     this.ownedFramebufferId = framebufferId;
                     this.ownedColorTextureId = colorTextureId;
                     this.ownedDepthTextureId = depthTextureId;
                     this.ownedColorTexture = colorTextureObject;
                     this.ownedDepthTexture = depthTextureObject;
                     var23 = framebufferId;
                     var20 = false;
                  } finally {
                     if (var20) {
                        int restoredDrawFramebufferId = previousDrawFramebufferId == previousOwnedFramebufferId
                           ? (framebufferCreated ? framebufferId : 0)
                           : previousDrawFramebufferId;
                        int restoredReadFramebufferId = previousReadFramebufferId == previousOwnedFramebufferId
                           ? (framebufferCreated ? framebufferId : 0)
                           : previousReadFramebufferId;
                        GL30.glBindFramebuffer(36009, restoredDrawFramebufferId);
                        GL30.glBindFramebuffer(36008, restoredReadFramebufferId);
                        if (previousOwnedFramebufferId > 0) {
                           GL30.glDeleteFramebuffers(previousOwnedFramebufferId);
                        }

                        if (!framebufferCreated) {
                           if (framebufferId > 0) {
                              GL30.glDeleteFramebuffers(framebufferId);
                           }

                           this.clearOwnedFramebufferReference();
                        }
                     }
                  }

                  int restoredDrawFramebufferIdx = previousDrawFramebufferId == previousOwnedFramebufferId ? framebufferId : previousDrawFramebufferId;
                  int restoredReadFramebufferIdx = previousReadFramebufferId == previousOwnedFramebufferId ? framebufferId : previousReadFramebufferId;
                  GL30.glBindFramebuffer(36009, restoredDrawFramebufferIdx);
                  GL30.glBindFramebuffer(36008, restoredReadFramebufferIdx);
                  if (previousOwnedFramebufferId > 0) {
                     GL30.glDeleteFramebuffers(previousOwnedFramebufferId);
                  }

                  return var23;
               }

               int restoredDrawFramebufferIdx = previousDrawFramebufferId == previousOwnedFramebufferId ? 0 : previousDrawFramebufferId;
               int restoredReadFramebufferIdx = previousReadFramebufferId == previousOwnedFramebufferId ? 0 : previousReadFramebufferId;
               GL30.glBindFramebuffer(36009, restoredDrawFramebufferIdx);
               GL30.glBindFramebuffer(36008, restoredReadFramebufferIdx);
               if (previousOwnedFramebufferId > 0) {
                  GL30.glDeleteFramebuffers(previousOwnedFramebufferId);
               }

               if (framebufferId > 0) {
                  GL30.glDeleteFramebuffers(framebufferId);
               }

               this.clearOwnedFramebufferReference();
               return var23;
            }

            int restoredDrawFramebufferIdxx = previousDrawFramebufferId == previousOwnedFramebufferId ? 0 : previousDrawFramebufferId;
            int restoredReadFramebufferIdxx = previousReadFramebufferId == previousOwnedFramebufferId ? 0 : previousReadFramebufferId;
            GL30.glBindFramebuffer(36009, restoredDrawFramebufferIdxx);
            GL30.glBindFramebuffer(36008, restoredReadFramebufferIdxx);
            if (previousOwnedFramebufferId > 0) {
               GL30.glDeleteFramebuffers(previousOwnedFramebufferId);
            }

            if (framebufferId > 0) {
               GL30.glDeleteFramebuffers(framebufferId);
            }

            this.clearOwnedFramebufferReference();
            return status;
         }
      } else {
         return -1;
      }
   }

   private void deleteOwnedFramebuffer() {
      if (this.ownedFramebufferId > 0) {
         GL30.glDeleteFramebuffers(this.ownedFramebufferId);
      }

      this.clearOwnedFramebufferReference();
   }

   private void clearOwnedFramebufferReference() {
      this.ownedFramebufferId = -1;
      this.ownedColorTextureId = -1;
      this.ownedDepthTextureId = -1;
      this.ownedColorTexture = null;
      this.ownedDepthTexture = null;
   }

   private void logFramebufferFailure(String reason, Throwable error) {
      if (ForgeVersion.MC_26_2.d() && !this.framebufferFailureLogged) {
         this.framebufferFailureLogged = true;
         Vape.debugLog("RenderBatchManager 26.2: " + reason);
         if (error != null) {
            Vape.logThrowable(error);
         }
      }
   }

   public RenderBatchManager() {
      try {
         InvWalkKeyLayout.initializeShaders();
         this.shaderProgram = InvWalkKeyLayout.universalShader;
      } catch (Throwable var4) {
         throw initializationFailure("shader load", var4);
      }

      try {
         VertexAttributeType[] vertexAttributes = new VertexAttributeType[]{
            VertexAttributeType.Float,
            VertexAttributeType.Vec3,
            VertexAttributeType.Vec2,
            VertexAttributeType.Vec4,
            VertexAttributeType.Float,
            VertexAttributeType.Float,
            VertexAttributeType.Vec2,
            VertexAttributeType.Float,
            VertexAttributeType.Float,
            VertexAttributeType.Vec2,
            VertexAttributeType.Vec2,
            VertexAttributeType.Vec4,
            VertexAttributeType.Vec3,
            VertexAttributeType.Float,
            VertexAttributeType.Vec4
         };
         this.batchBuffer = new RenderBatchBuffer(this.shaderProgram, 5000, vertexAttributes);
      } catch (Throwable var3) {
         throw initializationFailure("mesh creation", var3);
      }

      try {
         TextureAtlasRegistry textureAtlasRegistry = TextureAtlasRegistry.getInstance();
         textureAtlasRegistry.setActiveAtlas(textureAtlasRegistry.getOrCreate("vape_texture"));
      } catch (Throwable var2) {
         throw initializationFailure("texture atlas creation", var2);
      }
   }

   private void saveAndPrepareGlState() {
      boolean cullFaceEnabled = GL11.glIsEnabled(2884);
      boolean blendEnabled = GL11.glIsEnabled(3042);
      boolean depthTestEnabled = GL11.glIsEnabled(2929);
      boolean depthWriteEnabled = GL11.glGetBoolean(2930);
      this.savedGlStates.push(new RenderBatchStateFlags(cullFaceEnabled, blendEnabled, depthTestEnabled, depthWriteEnabled));
      if (cullFaceEnabled) {
         OpenGlBackendHolder.backend.disableCapability(2884);
      }

      if (!blendEnabled) {
         OpenGlBackendHolder.backend.enableCapability(3042);
      }

      if (depthTestEnabled) {
         GlStateManager.disableDepth();
      }

      GlStateManager.Y(770, 771);
   }

   public void beginBatchRendering() {
      this.captureGlBindings();
      int targetFramebufferId = this.getTargetFramebufferId();
      if (targetFramebufferId != -1) {
         GL30.glBindFramebuffer(36160, targetFramebufferId);
      }

      this.saveAndPrepareGlState();
      this.batchBuffer.bindResources();
      this.batchResourcesBound = true;
   }

   private void restoreCapturedGlBindings() {
      GL30.glBindVertexArray(this.savedVertexArrayId);
      GL20.glUseProgram(this.savedProgramId);
      GL11.glBindTexture(3553, this.savedTextureId);
      GL30.glBindFramebuffer(36009, this.savedFramebufferId);
      GL30.glBindFramebuffer(36008, this.savedReadFramebufferId);
      GL15.glBindBuffer(34962, this.savedArrayBufferId);
      GL15.glBindBuffer(34963, this.savedElementArrayBufferId);
   }

   private void ensureBatchResourcesBound() {
      if (!this.batchResourcesBound) {
         this.bindBatchResources();
      }
   }
}
