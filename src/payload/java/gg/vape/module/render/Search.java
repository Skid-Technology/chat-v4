package gg.vape.module.render;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventRender3D;
import gg.vape.event.impl.EventRenderTracers3D;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.render.search.SearchBlockChunkScanner;
import gg.vape.module.render.search.SearchBlockRenderEntry;
import gg.vape.rotation.LocalPlayerRotationUtil;
import gg.vape.ui.unmap.SearchBlock;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.Vec3d;
import gg.vape.utils.datas.SearchResultData;
import gg.vape.utils.datas.SearchResultDataPool;
import gg.vape.utils.processors.SearchProcessor;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.BufferedRenderPrimitives;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderBatchState;
import gg.vape.utils.render.RenderUtil;
import gg.vape.utils.render.RenderUtils;
import gg.vape.value.BooleanValue;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.ActiveRenderInfo;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RenderManager;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.lwjgl.opengl.GL11;

public class Search extends Mod {
   private SearchProcessor primaryProcessor;
   private static final long MODULE_HASH = -323757894669395457L;
   private final TimerUtil scanTimer;
   private final NumberValue range;
   private final TimerUtil resultRefreshTimer;
   private final NumberValue dummyRange;
   private ArrayList<SearchBlockRenderEntry> pendingScanEntries;
   private final List<SearchBlock> searchBlocks = new ArrayList<>();
   private final BooleanValue onlyCaves;
   private final List<SearchResultData> searchResults;
   private final BooleanValue useTracers;
   private SearchProcessor secondaryProcessor;

   @Override
   public void onEnable() {
      if (ForgeVersion.MC_1_8_9.A()) {
         this.primaryProcessor = new SearchProcessor(this.searchResults, this.searchBlocks, this.range);
         this.secondaryProcessor = new SearchProcessor(this.searchResults, this.searchBlocks, this.dummyRange);
         this.primaryProcessor.X(this.onlyCaves.getEffectiveValue());
         this.secondaryProcessor.X(this.onlyCaves.getEffectiveValue());
         this.primaryProcessor.n();
         this.secondaryProcessor.n();
      }
   }

   private void updateSearchResults() {
      if (ForgeVersion.MC_1_8_9.L() && this.pendingScanEntries != null) {
         if (Minecraft.theWorld().isNull()) {
            return;
         }

         this.searchResults.clear();
         EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();

         for (SearchBlockRenderEntry object : this.pendingScanEntries) {
            SearchBlock searchBlock = this.findSearchBlock(object.getBlockId(), object.getMetadata());
            if (searchBlock != null) {
               int n = object.getWorldX();
               int n2 = object.getWorldY();
               int n3 = object.getWorldZ();
               int n4 = (int)RotationUtil.p(entityPlayerSP, n, n2, n3);
               SearchResultData searchResultData = SearchResultDataPool.acquire(
                  n, n2, n3, object.getBlockId(), searchBlock, searchBlock.I$src$Ljava_util_concurrent_atomic_AtomicBoolean_$10pq3bz(), n4
               );
               if (n4 < this.range.getValue() + 10.0) {
                  this.searchResults.add(searchResultData);
               } else {
                  SearchResultDataPool.release(searchResultData);
               }
            }

            SearchBlockChunkScanner.recycle(object);
         }

         for (SearchResultData searchResultData : this.searchResults) {
            SearchResultDataPool.release(searchResultData);
         }

         this.pendingScanEntries = null;
      }
   }

   public void addSearchBlock(SearchBlock searchBlock) {
      this.searchBlocks.add(searchBlock);
   }

   @Override
   public void onScheduledAction() {
      if (ForgeVersion.MC_1_8_9.L() && this.scanTimer.hasTimeElapsed(1000L)) {
         this.scanTimer.reset();
         if (Minecraft.theWorld().isNull() || Minecraft.thePlayer().isNull()) {
            return;
         }

         if (this.pendingScanEntries == null) {
            ArrayList<SearchBlock> arrayList = new ArrayList<>(this.searchBlocks);
            this.pendingScanEntries = SearchBlockChunkScanner.scanLoadedChunks(arrayList, this.range.getValue().intValue(), this.onlyCaves.getEffectiveValue());
         }
      }
   }

   @Override
   public void onDisable() {
      if (ForgeVersion.MC_1_8_9.A()) {
         this.primaryProcessor.w();
         this.secondaryProcessor.w();
      }

      this.searchResults.clear();
      RenderBatchState.cleanupInstance();
   }

   public SearchBlock findSearchBlock(int blockId, int metadata) {
      for (SearchBlock searchBlock : this.searchBlocks) {
         if (searchBlock.b(blockId, metadata)) {
            return searchBlock;
         }
      }

      return null;
   }

   public Search() {
      super("Search", -16737793, Category.RENDER, "Draws outline around selected blocks\nAdd blocks in Search frame");
      this.range = NumberValue.create(this, "Range", "#", "", 1.0, 50.0, 100.0, 1.0);
      this.dummyRange = NumberValue.create(this, "-", "-", "-", 5.0, 5.0, 5.0, 1.0);
      this.onlyCaves = BooleanValue.create(this, "Only caves", false, "Only looks for ores exposed to air");
      this.useTracers = BooleanValue.create(this, "Use tracers", false, "Add tracers to search blocks");
      this.scanTimer = new TimerUtil();
      this.resultRefreshTimer = new TimerUtil();
      this.searchResults = ForgeVersion.MC_1_8_9.L() ? new ArrayList<SearchResultData>() : new CopyOnWriteArrayList<SearchResultData>();
      this.addValue(this.range, this.onlyCaves, this.useTracers);
      this.v(50L, true);
      this.onlyCaves.addChangeListener(this::onOnlyCavesChanged);
   }

   private void onOnlyCavesChanged(BooleanValue booleanValue) {
      if (this.primaryProcessor != null) {
         this.primaryProcessor.X(this.onlyCaves.getEffectiveValue());
         this.secondaryProcessor.X(this.onlyCaves.getEffectiveValue());
      }
   }

   public void removeSearchBlock(SearchBlock searchBlock) {
      this.searchBlocks.remove(searchBlock);
      this.searchResults.clear();
   }

   @EventHandler
   public void onRender(EventRenderTracers3D eventRenderTracers3D) {
      if (this.useTracers.getEffectiveValue()) {
         EntityPlayerSP entityPlayerSP = eventRenderTracers3D.getThePlayer();
         RenderUtil.d();
         RenderUtils.g();
         OpenGlBackendHolder.backend.disableCapability(2929);
         eventRenderTracers3D.getEntityRenderer().B(0.0);
         double d = RenderManager.getInterpolatedRenderPosX();
         double d2 = RenderManager.getInterpolatedRenderPosY();
         double d3 = RenderManager.getInterpolatedRenderPosZ();
         double d4 = ForgeVersion.MC_1_7_10.Y() ? entityPlayerSP.X() : 0.0;

         for (SearchResultData searchResultData : this.searchResults) {
            if (searchResultData.q() && searchResultData.N != 0 && searchResultData.n()) {
               this.renderTracerLine(entityPlayerSP, searchResultData, searchResultData.O(), 2.0F, d, d2, d3, d4);
            }
         }

         OpenGlBackendHolder.backend.setColor(1.0, 1.0F, 1.0F);
         eventRenderTracers3D.getEntityRenderer().O(0.0);
         OpenGlBackendHolder.backend.enableCapability(2929);
         RenderUtils.f();
         OpenGlBackendHolder.backend.popMatrix();
      }
   }

   @EventHandler
   public void onRender3D(EventRender3D eventRender3D) {
      if (this.resultRefreshTimer.hasTimeElapsed(50L)) {
         this.updateSearchResults();
         this.resultRefreshTimer.reset();
      }

      Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().B(1.0);
      RenderUtil.d();
      boolean blendWasEnabled = OpenGlBackendHolder.backend.isCapabilityEnabled(3042);
      if (!blendWasEnabled) {
         OpenGlBackendHolder.backend.enableCapability(3042);
      }

      GL11.glBlendFunc(770, 771);
      OpenGlBackendHolder.backend.setLineWidth(1.5F);
      OpenGlBackendHolder.backend.disableCapability(3553);
      OpenGlBackendHolder.backend.enableCapability(2848);
      OpenGlBackendHolder.backend.disableCapability(2929);
      OpenGlBackendHolder.backend.setDepthMask(false);
      double renderX = RenderManager.getInterpolatedRenderPosX();
      double renderY = RenderManager.getInterpolatedRenderPosY();
      double renderZ = RenderManager.getInterpolatedRenderPosZ();
      double cameraYOffset = 0.0;
      if (ForgeVersion.MC_1_16_5.d()) {
         cameraYOffset = Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().l().o().getY() - renderY;
      }

      boolean useInstancedRenderer = GuiRenderPrimitives.d() && (!ForgeVersion.MC_1_21_11.L() || !Vape.INSTANCE.isFabricMinecraftPresent());
      if (useInstancedRenderer) {
         RenderBatchState renderBatchState = RenderBatchState.getInstance();
         renderBatchState.beginBatch();
         double adjustedRenderY = renderY + cameraYOffset;

         for (SearchResultData searchResultData : this.searchResults) {
            if (searchResultData.q() && searchResultData.N != 0) {
               Color color = searchResultData.O();
               renderBatchState.addInstance(
                  (float)(searchResultData.F - renderX),
                  (float)(searchResultData.O - adjustedRenderY),
                  (float)(searchResultData.D - renderZ),
                  color.getRed() / 255.0F,
                  color.getGreen() / 255.0F,
                  color.getBlue() / 255.0F,
                  color.getAlpha() / 255.0F
               );
            }
         }

         LocalPlayerRotationUtil.updateProjectionMatrix(eventRender3D.getTicks());
         renderBatchState.render(
            BufferedGuiRenderPrimitives.projectionMatrix, BufferedGuiRenderPrimitives.viewMatrix, BufferedGuiRenderPrimitives.matrixStack.peek()
         );
      } else {
         for (SearchResultData searchResultDatax : this.searchResults) {
            if (searchResultDatax.q() && searchResultDatax.N != 0) {
               RenderUtil.m(searchResultDatax, renderX, renderY + cameraYOffset, renderZ);
            }
         }
      }

      OpenGlBackendHolder.backend.setDepthMask(true);
      OpenGlBackendHolder.backend.enableCapability(2929);
      OpenGlBackendHolder.backend.enableCapability(3553);
      OpenGlBackendHolder.backend.disableCapability(2848);
      if (!blendWasEnabled) {
         OpenGlBackendHolder.backend.disableCapability(3042);
      }

      OpenGlBackendHolder.backend.popMatrix();
      Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().O(1.0);
   }

   private void renderTracerLine(
      EntityPlayerSP entityPlayerSP,
      SearchResultData searchResultData,
      Color color,
      float lineWidth,
      double renderX,
      double renderY,
      double renderZ,
      double startY
   ) {
      double targetX = searchResultData.U() + 0.5 - renderX;
      double targetY = searchResultData.D() + 0.5 - renderY;
      double targetZ = searchResultData.Y() + 0.5 - renderZ;
      boolean blendWasEnabled = false;
      boolean lightingWasEnabled = false;
      if (!GuiRenderPrimitives.d()) {
         blendWasEnabled = GL11.glIsEnabled(3042);
         lightingWasEnabled = GL11.glIsEnabled(2896);
         GL11.glBlendFunc(770, 771);
         if (!blendWasEnabled) {
            OpenGlBackendHolder.backend.enableCapability(3042);
         }

         if (lightingWasEnabled) {
            OpenGlBackendHolder.backend.disableCapability(2896);
         }

         GL11.glBlendFunc(770, 771);
         OpenGlBackendHolder.backend.enableCapability(2848);
         OpenGlBackendHolder.backend.disableCapability(3553);
      }

      double startX = 0.0;
      double startZ = 0.0;
      if (ForgeVersion.MC_1_12_2.d()) {
         Vec3d vec3d = new Vec3d(0.0, 0.0, 1.0);
         if (ForgeVersion.MC_1_16_5.d()) {
            vec3d.rotateAroundXAxis((float)(-Math.toRadians(Minecraft.D().getPlayerViewY())));
            vec3d.rotateAroundYAxis((float)(-Math.toRadians(Minecraft.D().getPlayerViewX())));
         } else {
            vec3d.rotateAroundXAxis((float)(-Math.toRadians(entityPlayerSP.V())));
            vec3d.rotateAroundYAxis((float)(-Math.toRadians(entityPlayerSP.J())));
         }

         startX = vec3d.getX();
         startY += ForgeVersion.MC_1_16_5.d() ? vec3d.getY() - entityPlayerSP.X() : vec3d.getY();
         startZ = vec3d.getZ();
         if (ForgeVersion.MC_1_16_5.d() && Minecraft.gameSettings().x() != 0) {
            ActiveRenderInfo activeRenderInfo = Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().l();
            targetX += RenderManager.getInterpolatedRenderPosX() - activeRenderInfo.o().getX();
            targetY += RenderManager.getInterpolatedRenderPosY() - activeRenderInfo.o().getY();
            targetZ += RenderManager.getInterpolatedRenderPosZ() - activeRenderInfo.o().getZ();
         }
      }

      if (GuiRenderPrimitives.d()) {
         BufferedRenderPrimitives.drawLine3D(startX, startY, startZ, targetX, targetY, targetZ, lineWidth, color);
      } else {
         GL11.glLineWidth(lineWidth);
         GL11.glBegin(1);
         RenderUtils.w(color);
         GL11.glVertex3d(startX, startY, startZ);
         GL11.glVertex3d(targetX, targetY, targetZ);
         GL11.glEnd();
      }

      if (!GuiRenderPrimitives.d()) {
         if (lightingWasEnabled) {
            OpenGlBackendHolder.backend.enableCapability(2896);
         }

         if (!blendWasEnabled) {
            OpenGlBackendHolder.backend.disableCapability(3042);
         }

         OpenGlBackendHolder.backend.enableCapability(3553);
         OpenGlBackendHolder.backend.disableCapability(2848);
      }
   }
}
