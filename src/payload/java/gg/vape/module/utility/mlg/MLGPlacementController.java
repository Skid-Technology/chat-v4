package gg.vape.module.utility.mlg;

import gg.vape.input.KeyBindingHelper;
import gg.vape.mapping.ItemMappingEntry;
import gg.vape.module.blatant.blockin.BlockPlacementUtility;
import gg.vape.module.blatant.blockin.HotbarSlotResolution;
import gg.vape.module.blatant.blockin.HotbarSlotResolutionWithValue;
import gg.vape.module.utility.MLG;
import gg.vape.rotation.AdaptiveRotationController;
import gg.vape.rotation.FixedRotationController;
import gg.vape.rotation.PointRotationController;
import gg.vape.rotation.RotationManager;
import gg.vape.rotation.WorldPointRotationTarget;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.datas.BlockCoordinate;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EnumFacing;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MLGPlacementController {
   private final MLG MLG;

   @Nullable
   private FixedRotationController prepareRotation(
      @Nullable BlockCoordinate targetCoordinate, @Nullable FixedRotationController currentController, ItemMappingEntry itemMappingEntry
   ) {
      FixedRotationController preparedController = currentController;
      if (targetCoordinate == null) {
         return currentController;
      } else {
         if (currentController != null && !(currentController instanceof AdaptiveRotationController) && !(currentController instanceof PointRotationController)
            )
          {
            BlockPlacementUtility.releaseRotationController(currentController, false, true);
            preparedController = this.createRotationController(targetCoordinate, itemMappingEntry);
         }

         if (preparedController instanceof PointRotationController || preparedController instanceof AdaptiveRotationController) {
            BlockPlacementUtility.updateRotationTarget((WorldPointRotationTarget)preparedController, targetCoordinate, itemMappingEntry);
         }

         if (this.MLG.acquireRotationControl() && preparedController != null && !preparedController.equals(RotationManager.INSTANCE.getActiveController())) {
            RotationManager.INSTANCE.setController(preparedController);
         }

         return preparedController;
      }
   }

   @NotNull
   public HotbarSlotResolution aimAtTarget(
      @NotNull ItemMappingEntry itemMappingEntry,
      @Nullable BlockCoordinate targetCoordinate,
      @Nullable FixedRotationController currentController,
      boolean pickupWater
   ) {
      FixedRotationController preparedController = this.prepareRotation(targetCoordinate, currentController, itemMappingEntry);
      if (preparedController != null) {
         if (!preparedController.equals(currentController)) {
            if (pickupWater) {
               this.MLG.pickupRotation = preparedController;
            } else {
               this.MLG.placementRotation = preparedController;
            }
         }

         if (BlockPlacementUtility.isLookingAtPlacementTarget(targetCoordinate, itemMappingEntry)) {
            return HotbarSlotResolution.success("AimJob completed by looking at valid block");
         } else if (preparedController.isComplete()) {
            if (BlockPlacementUtility.isLookingAtPlacementTarget(targetCoordinate, itemMappingEntry)) {
               return HotbarSlotResolution.success("AimJob was already completed");
            } else {
               BlockPlacementUtility.releaseRotationController(preparedController, false, true);
               preparedController = this.createRotationController(targetCoordinate, itemMappingEntry);
               if (pickupWater) {
                  this.MLG.pickupRotation = preparedController;
               } else {
                  this.MLG.placementRotation = preparedController;
               }

               RotationManager.INSTANCE.setController(preparedController);
               return HotbarSlotResolution.pending("AimJob drifted after completion, restarting");
            }
         } else if (!preparedController.equals(RotationManager.INSTANCE.getActiveController())) {
            if (this.MLG.acquireRotationControl()) {
               RotationManager.INSTANCE.setController(preparedController);
               return HotbarSlotResolution.pending("AimJob set as current job");
            } else {
               return HotbarSlotResolution.pending("AimJob is not current job");
            }
         } else {
            return HotbarSlotResolution.pending("Waiting for AimJob to complete");
         }
      } else {
         return BlockPlacementUtility.isLookingAtPlacementTarget(targetCoordinate, itemMappingEntry)
            ? HotbarSlotResolution.success("AimJob completed by looking at valid block")
            : HotbarSlotResolution.failure("AimJob is null").force();
      }
   }

   public MLGPlacementController(MLG MLG) {
      this.MLG = MLG;
   }

   private <T extends FixedRotationController> T configureController(T controller) {
      controller.setClampStepToRemaining(true);
      controller.setTolerance(0.1F);
      controller.setAngleBasedAcceleration(true);
      controller.setScaleAxesProportionally(true);
      controller.setRetainAfterCompletion(true);
      controller.setCubicAcceleration(true);
      controller.setLinearAcceleration(true);
      controller.setSpeed(this.MLG.aimSpeed.getValue().floatValue());
      controller.setRandomizeMovement(true);
      if (controller instanceof AdaptiveRotationController) {
         ((AdaptiveRotationController)controller).setRelativeMode(false);
      }

      return controller;
   }

   public HotbarSlotResolutionWithValue<BlockPos> placeItem(
      @NotNull ItemMappingEntry itemMappingEntry, @Nullable BlockCoordinate targetCoordinate, @Nullable TimerUtil timeoutTimer
   ) {
      HotbarSlotResolutionWithValue<BlockPos> result = new HotbarSlotResolutionWithValue<>();
      if (timeoutTimer != null && timeoutTimer.hasTimeElapsed(5000L)) {
         return result.markFailure("Timed out while trying to place item");
      } else {
         EntityPlayerSP localPlayer = Minecraft.thePlayer();
         if (localPlayer.isNull()) {
            return result.markPending("Player is unavailable");
         } else {
            HotbarSlotResolution closeResult = this.MLG.closeInventory();
            if (closeResult.isFailure()) {
               return result.markFailure("Failed to close GUI due to: " + closeResult.getMessage()).setForced(closeResult.canContinue());
            } else if (closeResult.isPending()) {
               return result.markPending("Waiting to close GUI");
            } else {
               RayTraceResult rayTraceResult = BlockPlacementUtility.getPlacementRayTrace(itemMappingEntry);
               if (rayTraceResult.isNull()) {
                  return result.markPending("Waiting for mouse over to not be null");
               } else if (!rayTraceResult.isBlockHit()) {
                  return result.markPending("Waiting for mouse over to be a block");
               } else if (!BlockPlacementUtility.isLookingAtPlacementTarget(targetCoordinate, itemMappingEntry)) {
                  return result.markPending("Waiting to look at valid block");
               } else {
                  BlockPos blockPos;
                  if (itemMappingEntry.equals(BlockPlacementUtility.getWaterBucketItem()) && targetCoordinate != null) {
                     BlockPos hitPos = rayTraceResult.getBlockPos();
                     EnumFacing hitSide = rayTraceResult.getSideHit();
                     if (hitPos == null || hitPos.isNull()) {
                        return result.markPending("Water target block is null");
                     }

                     if (hitSide == null || hitSide.isNull()) {
                        return result.markPending("Water target face is null");
                     }

                     if (!EnumFacing.F$src$Lgg_vape_wrapper_impl_EnumFacing_$glfxl5().equals(hitSide)) {
                        return result.markPending("Water bucket is not aimed at the top face");
                     }

                     if (hitPos.getX() != targetCoordinate.B() || hitPos.getY() != targetCoordinate.E() || hitPos.getZ() != targetCoordinate.A()) {
                        return result.markPending("Water bucket raytrace does not match landing target");
                     }

                     blockPos = targetCoordinate.E$src$Lgg_vape_wrapper_impl_BlockPos_$1bb1czr().W(1);
                  } else {
                     blockPos = BlockPlacementUtility.getPlacementBlockPos(itemMappingEntry);
                  }

                  if (blockPos != null && !blockPos.isNull()) {
                     KeyBinding keyBinding = Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362();
                     KeyBindingHelper.setPressedAndTick(keyBinding, true);
                     KeyBindingHelper.updateKeyBinding(keyBinding, false, false);
                     return result.markSuccess("Placed MLG Item").setValue(blockPos);
                  } else {
                     return result.markPending("Placement position is null");
                  }
               }
            }
         }
      }
   }

   @NotNull
   public FixedRotationController createRotationController(@Nullable BlockCoordinate targetCoordinate, @Nullable ItemMappingEntry itemMappingEntry) {
      FixedRotationController controller;
      if (targetCoordinate == null) {
         controller = (FixedRotationController)(this.MLG.silentAim.getEffectiveValue()
            ? new AdaptiveRotationController(-999.0F, 90.0F)
            : new FixedRotationController(-999.0F, 90.0F));
      } else {
         Vec3 aimPoint = BlockPlacementUtility.getAimPoint(targetCoordinate, itemMappingEntry);
         controller = (FixedRotationController)(this.MLG.silentAim.getEffectiveValue()
            ? new AdaptiveRotationController(aimPoint)
            : new PointRotationController(aimPoint));
      }

      return this.configureController(controller);
   }
}
