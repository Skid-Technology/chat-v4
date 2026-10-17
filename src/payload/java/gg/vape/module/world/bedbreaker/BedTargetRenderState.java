package gg.vape.module.world.bedbreaker;

import func.skidline.RectData;
import gg.vape.module.render.entity.ProjectedEntityBounds;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.Vec3d;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ItemIconRenderer;
import gg.vape.utils.render.RenderUtil;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RenderWorldLastEvent;
import gg.vape.wrapper.impl.Vec3;
import java.awt.Color;

public class BedTargetRenderState {
   private static final float TOOL_ICON_SIZE_RATIO = 0.55F;
   private ProjectedEntityBounds projectedBounds;
   private final BedTargetRenderPosition targetPosition;
   private float visibilityProgress = 0.0F;
   private boolean insideReticle;
   private Vec3d obstructionPoint;
   private BedBreakPhase breakPhase = BedBreakPhase.UNRESOLVED;
   private BedCoverTarget coverTarget;
   private final TimerUtil animationTimer = new TimerUtil();

   private void setInsideReticle(boolean insideReticle) {
      this.insideReticle = insideReticle;
   }

   public void setObstructionPoint(Vec3d obstructionPoint) {
      this.obstructionPoint = obstructionPoint;
   }

   public BedTargetRenderPosition getTargetPosition() {
      return this.targetPosition;
   }

   public void renderIndicator(RectData rectData, boolean selected, float breakProgress, ItemStack toolIcon) {
      Vec3 playerView = Minecraft.F().O(1.0F);
      double targetDistance = playerView.distanceTo(
         new Vec3d(this.targetPosition.getBlockX() + 0.5, this.targetPosition.getBlockY() + 0.5, this.targetPosition.getBlockZ() + 0.5).toVec3()
      );
      float renderResolutionMultiplier = RenderWorldLastEvent.getRenderResolutionMultiplier();
      float screenHeight = Minecraft.h();
      ProjectedEntityBounds bounds = this.projectedBounds;
      double left = bounds.minX / renderResolutionMultiplier;
      double right = bounds.maxX / renderResolutionMultiplier;
      double top = screenHeight - bounds.maxY / renderResolutionMultiplier;
      double bottom = screenHeight - bounds.minY / renderResolutionMultiplier;
      double projectedWidth = right - left;
      double projectedHeight = bottom - top;
      float indicatorSize = (float)Math.min(projectedWidth, projectedHeight);
      boolean reticleContainsTarget = rectData.z(left + projectedWidth / 2.0, top + projectedHeight / 2.0, indicatorSize / 2.0F);
      boolean obstructionReachable = false;
      if (this.obstructionPoint != null) {
         double obstructionDistance = playerView.distanceTo(this.obstructionPoint.toVec3());
         if (obstructionDistance < 4.5) {
            obstructionReachable = true;
         }

         if (!obstructionReachable) {
            reticleContainsTarget = false;
         }
      }

      float distanceFade = 1.0F;
      if (targetDistance < 20.0) {
         distanceFade = targetDistance > 10.0 ? (float)((20.0 - targetDistance) / 10.0) : 1.0F;
      }

      this.setInsideReticle(reticleContainsTarget);
      float visibility = this.getVisibilityProgress();
      int backgroundAlpha = (int)((obstructionReachable ? 160 : 150) * distanceFade);
      int outlineAlpha = (int)((obstructionReachable ? 250 : 170) * distanceFade);
      indicatorSize += 20.0F * visibility;
      float indicatorX = (float)(left + projectedWidth / 2.0 - indicatorSize / 2.0F);
      float indicatorY = (float)(top + projectedHeight / 2.0 - indicatorSize / 2.0F);
      GuiRenderPrimitives.V(indicatorX, indicatorY, indicatorSize, 1.0, new Color(10, 10, 10, backgroundAlpha));
      int greenBoost = (int)(75.0F * visibility);
      if (obstructionReachable) {
         GuiRenderPrimitives.m(indicatorX, indicatorY, indicatorSize, indicatorSize * 0.12F, 1.0F, new Color(10, 100 + greenBoost, 10, 255));
      }

      GuiRenderPrimitives.m(indicatorX, indicatorY, indicatorSize, indicatorSize * 0.1F, 1.0F, new Color(10, 10, 10, outlineAlpha));
      if (selected) {
         float breakPercent = 100.0F * breakProgress;
         float breakDegrees = 360.0F * (breakPercent / 100.0F);
         if (breakPercent < 100.0F && breakPercent > 0.0F) {
            GuiRenderPrimitives.p(
               indicatorX, indicatorY, indicatorSize, indicatorSize * 0.12F, 1.0F, 270.0F, -breakDegrees, new Color(10, 100 + greenBoost, 10, 255)
            );
         }
      }

      if (toolIcon != null && toolIcon.isNotNull()) {
         int iconSize = Math.max(1, Math.round(indicatorSize * 0.55F));
         float iconX = indicatorX + (indicatorSize - iconSize) / 2.0F;
         float iconY = indicatorY + (indicatorSize - iconSize) / 2.0F;
         ItemIconRenderer.renderItemStack(toolIcon, iconX, iconY, iconSize, iconSize, distanceFade);
      }
   }

   public float getVisibilityProgress() {
      return this.visibilityProgress;
   }

   public BedTargetRenderState(BedTargetRenderPosition bedTargetRenderPosition) {
      this.targetPosition = bedTargetRenderPosition;
   }

   public BedBreakPhase getBreakPhase() {
      return this.breakPhase;
   }

   public BedCoverTarget getCoverTarget() {
      return this.coverTarget;
   }

   public void resolveCoverTarget(BedCoverTarget coverTarget) {
      this.coverTarget = coverTarget;
      this.breakPhase = coverTarget == null ? BedBreakPhase.BED : BedBreakPhase.COVER;
   }

   public void finishCover() {
      this.coverTarget = null;
      this.breakPhase = BedBreakPhase.BED;
   }

   public void resetBreakState() {
      this.coverTarget = null;
      this.breakPhase = BedBreakPhase.UNRESOLVED;
      this.obstructionPoint = null;
   }

   public void updateVisibilityAnimation() {
      if (this.animationTimer.hasTimeElapsed(10L)) {
         this.visibilityProgress = this.insideReticle ? (float)(this.visibilityProgress + 0.05) : (float)(this.visibilityProgress - 0.05);
         this.visibilityProgress = Math.min(1.0F, Math.max(0.0F, this.visibilityProgress));
         this.animationTimer.reset();
      }
   }

   public boolean isInsideReticle() {
      return this.insideReticle;
   }

   public void updateProjectedBounds() {
      AxisAlignedBB axisAlignedBB = AxisAlignedBB.create(0.2, 0.0, 0.2, 0.8, 0.8, 0.8);
      RenderUtil.d();
      this.projectedBounds = new ProjectedEntityBounds(
         this.targetPosition.getRelativeX(), this.targetPosition.getRelativeY() + 0.4, this.targetPosition.getRelativeZ(), axisAlignedBB, null, null, null
      );
      RenderUtil.Y();
   }
}
