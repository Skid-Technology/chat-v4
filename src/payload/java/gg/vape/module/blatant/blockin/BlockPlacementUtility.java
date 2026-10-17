package gg.vape.module.blatant.blockin;

import gg.vape.Vape;
import gg.vape.mapping.ItemMappingEntry;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Mod;
import gg.vape.module.utility.inventory.ItemStackActionPredicate;
import gg.vape.module.utility.mlg.MLGImpactState;
import gg.vape.rotation.AdaptiveRotationController;
import gg.vape.rotation.FixedRotationController;
import gg.vape.rotation.RotationControlClaim;
import gg.vape.rotation.RotationManager;
import gg.vape.rotation.WorldPointRotationTarget;
import gg.vape.utils.BlockUtil;
import gg.vape.utils.MathUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.datas.BlockCoordinate;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.BlockState;
import gg.vape.wrapper.impl.BlockStatePredicate;
import gg.vape.wrapper.impl.DamageSource;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EnumFacing;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PotionEffect;
import gg.vape.wrapper.impl.PotionRegistry;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.Slot;
import gg.vape.wrapper.impl.Vec3;
import gg.vape.wrapper.impl.World;
import java.util.List;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BlockPlacementUtility {
   @Nullable
   private static ItemMappingEntry cobwebItem;
   @Nullable
   private static ItemMappingEntry emptyBucketItem;
   @Nullable
   private static ItemMappingEntry waterBucketItem;

   public static float getDistanceToGround(EntityPlayerSP player, boolean scanWorld, boolean stopAfterFirstLanding, BlockPlacementGraph placementGraph) {
      if (player.b$src$Z$fqlxe4()) {
         return 0.0F;
      } else if (player.q() == 0.0) {
         return 0.0F;
      } else {
         if (scanWorld) {
            World world = player.getWorld();
            int blockX = MathUtil.floor(player.z());
            int blockZ = MathUtil.floor(player.h());

            for (int blockY = MathUtil.floor(player.N()); blockY > world.R(); blockY--) {
               Block block = world.getBlockByPos(blockX, blockY, blockZ);
               if (!block.isNull() && !BlockUtil.p(block)) {
                  return (float)(player.N() - (blockY + 1));
               }
            }
         } else {
            BlockCoordinate landingBlock = predictLandingBlock(stopAfterFirstLanding, 120, player, placementGraph);
            if (landingBlock != null) {
               return (float)(player.N() - (landingBlock.E() + 1));
            }
         }

         return 100000.0F;
      }
   }

   public static RayTraceResult getPlacementRayTrace(@NotNull ItemMappingEntry itemMappingEntry) {
      if (ForgeVersion.MC_1_21_4.d()) {
         return RotationManager.INSTANCE.getNormalReachRayTrace();
      } else {
         return itemMappingEntry.equals(getCobwebItem())
            ? RotationManager.INSTANCE.getNormalReachRayTrace()
            : RotationManager.INSTANCE.rayTraceUsingManagedRotation(itemMappingEntry.equals(getEmptyBucketItem()));
      }
   }

   @Nullable
   public static ItemMappingEntry getSlotItem(@Nullable Slot slot) {
      return slot != null && !slot.isNull() && slot.getStack() != null && !slot.getStack().isNull()
         ? Vape.INSTANCE.getItemStackResolver().resolve(slot.getStack())
         : null;
   }

   public static boolean isLookingAtPlacementTarget(@Nullable BlockCoordinate blockCoordinate, @NotNull ItemMappingEntry itemMappingEntry) {
      RayTraceResult rayTraceResult = getPlacementRayTrace(itemMappingEntry);
      if (!rayTraceResult.isNull() && rayTraceResult.isBlockHit()) {
         BlockPos blockPos = rayTraceResult.getBlockPos();
         if (blockPos == null || blockPos.isNull()) {
            return false;
         } else if (itemMappingEntry.equals(getEmptyBucketItem())) {
            Block block = rayTraceResult.Z$src$Lgg_vape_wrapper_impl_Block_$6x2c9a();
            BlockStatePredicate blockStatePredicate = block.a();
            if (!block.isNotNull() || !BlockUtil.C(block) || !blockStatePredicate.isNotNull() || !blockStatePredicate.toString().contains("level=0")) {
               return false;
            } else {
               return blockCoordinate == null
                  ? true
                  : blockPos.getX() == blockCoordinate.B() && blockPos.getY() == blockCoordinate.E() && blockPos.getZ() == blockCoordinate.A();
            }
         } else if (!itemMappingEntry.equals(getWaterBucketItem())) {
            return !EnumFacing.F$src$Lgg_vape_wrapper_impl_EnumFacing_$glfxl5().equals(rayTraceResult.getSideHit())
               ? false
               : blockCoordinate == null || isNearTarget(blockPos, blockCoordinate, itemMappingEntry);
         } else if (blockCoordinate == null) {
            return false;
         } else {
            return !EnumFacing.F$src$Lgg_vape_wrapper_impl_EnumFacing_$glfxl5().equals(rayTraceResult.getSideHit())
               ? false
               : blockPos.getX() == blockCoordinate.B() && blockPos.getY() == blockCoordinate.E() && blockPos.getZ() == blockCoordinate.A();
         }
      } else {
         return false;
      }
   }

   public static boolean isSourceWaterAt(@Nullable BlockCoordinate blockCoordinate) {
      if (blockCoordinate == null) {
         return false;
      } else {
         EntityPlayerSP localPlayer = Minecraft.thePlayer();
         if (localPlayer.isNull()) {
            return false;
         } else {
            BlockPos blockPos = blockCoordinate.E$src$Lgg_vape_wrapper_impl_BlockPos_$1bb1czr();
            BlockState blockState = localPlayer.getWorld().getBlockState(blockPos);
            if (blockState != null && !blockState.isNull()) {
               Block block = blockState.getBlock();
               BlockStatePredicate blockStatePredicate = block.a();
               return block.isNotNull() && BlockUtil.C(block) && blockStatePredicate.isNotNull() && blockStatePredicate.toString().contains("level=0");
            } else {
               return false;
            }
         }
      }
   }

   private static boolean slotContainsItem(ItemMappingEntry itemMappingEntry, Slot slot) {
      return slot != null
         && slot.isNotNull()
         && slot.getStack().isNotNull()
         && itemMappingEntry.equals(Vape.INSTANCE.getItemStackResolver().resolve(slot.getStack()));
   }

   public static ItemMappingEntry getEmptyBucketItem() {
      if (emptyBucketItem == null) {
         emptyBucketItem = Vape.INSTANCE.getItemStackResolver().findByName("minecraft:bucket");
      }

      return emptyBucketItem;
   }

   @NotNull
   static Predicate<Slot> createItemPredicate(@NotNull ItemMappingEntry itemMappingEntry) {
      return slot -> slotContainsItem(itemMappingEntry, slot);
   }

   @Nullable
   static Slot findHotbarSlot(ItemMappingEntry itemMappingEntry) {
      return ItemStackActionPredicate.findSlot(createItemPredicate(itemMappingEntry), MLGImpactState.HOTBAR);
   }

   public static float calculateFallDamage(EntityPlayerSP entityPlayerSP, float fallDistance) {
      PotionEffect jumpBoost = entityPlayerSP.b(PotionRegistry.Z);
      float jumpBoostReduction = jumpBoost != null && jumpBoost.isNotNull() ? jumpBoost.L() + 1.0F : 0.0F;
      int damageDistance = MathUtil.ceil(fallDistance - 3.0F - jumpBoostReduction);
      if (damageDistance <= 0) {
         return 0.0F;
      } else {
         DamageSource damageSource = ForgeVersion.MC_1_21_4.d() ? entityPlayerSP.B().fall() : DamageSource.m$src$Lgg_vape_wrapper_impl_DamageSource_$z0ibym();
         return Math.max(0.0F, RotationUtil.y(entityPlayerSP, damageSource, damageDistance, false, false));
      }
   }

   @Nullable
   public static BlockCoordinate predictLandingBlock(
      boolean stopAfterFirstLanding, int maxTicks, EntityPlayerSP entityPlayerSP, BlockPlacementGraph blockPlacementGraph
   ) {
      int minimumWorldY = ForgeVersion.MC_1_20_6.d() ? entityPlayerSP.getWorld().R() : 0;
      BlockPathPlanner planner = new BlockPathPlanner(entityPlayerSP, entityPlayerSP, entityPlayerSP.getWorld(), blockPlacementGraph);
      planner.applySnapshot(blockPlacementGraph);
      planner.clearInput();
      int requiredGroundTicks = stopAfterFirstLanding ? 1 : 3;
      EntityPlayer simulatedPlayer = planner.getSimulatedPlayer();
      World world = simulatedPlayer.getWorld();
      int consecutiveGroundTicks = 0;
      BlockCoordinate landingCoordinate = null;
      boolean legacy1122 = ForgeVersion.MC_1_12_2.d();

      for (int tick = 0; tick <= maxTicks; tick++) {
         planner.simulateTick();
         boolean onGround = simulatedPlayer.b$src$Z$fqlxe4();
         int blockX = MathUtil.floor(simulatedPlayer.z());
         int blockY = MathUtil.floor(simulatedPlayer.N() - 0.015625);
         int blockZ = MathUtil.floor(simulatedPlayer.h());
         AxisAlignedBB collisionArea = simulatedPlayer.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().expand(0.0, -2.0, 0.0);
         List collisions = world.i(simulatedPlayer, collisionArea);
         if (!collisions.isEmpty()) {
            AxisAlignedBB highestCollision = null;

            for (Object collisionObject : collisions) {
               AxisAlignedBB collision = new AxisAlignedBB(collisionObject);
               BlockPos collisionPos = BlockPos.d(BlockData.P(collision));
               if (ForgeVersion.MC_1_16_5.v() && legacy1122) {
                  BlockState state = world.getBlockState(collisionPos);
                  BlockPos abovePos = collisionPos.X$src$Lgg_vape_wrapper_impl_BlockPos_$jlnp6b();
                  BlockState aboveState = world.getBlockState(abovePos);
                  boolean bothNonSolid = !state.getBlock().X(state) && !aboveState.getBlock().X(aboveState);
                  if (bothNonSolid) {
                     continue;
                  }
               }

               if (highestCollision == null || collision.getMaxY() > highestCollision.getMaxY()) {
                  highestCollision = collision;
               }
            }

            if (highestCollision != null) {
               return new BlockCoordinate(BlockData.P(highestCollision));
            }
         }

         Block blockBelow;
         boolean touchesSpecialLandingBlock = (blockBelow = entityPlayerSP.getWorld().getBlockByPos(blockX, blockY, blockZ)).isNotNull()
            && (blockBelow.isInstance(MappedClasses.q_) || blockBelow.isInstance(MappedClasses.b));
         if (!onGround && !touchesSpecialLandingBlock) {
            landingCoordinate = null;
            consecutiveGroundTicks = 0;
         } else {
            consecutiveGroundTicks++;
            if (landingCoordinate == null) {
               landingCoordinate = new BlockCoordinate(blockX, blockY, blockZ);
            }

            if (consecutiveGroundTicks >= requiredGroundTicks || simulatedPlayer.e$src$Z$15bd4i1()) {
               return landingCoordinate;
            }
         }

         if (simulatedPlayer.N() <= minimumWorldY) {
            return landingCoordinate;
         }
      }

      return null;
   }

   @Nullable
   public static BlockPos getPlacementBlockPos(@NotNull ItemMappingEntry itemMappingEntry) {
      RayTraceResult rayTraceResult = getPlacementRayTrace(itemMappingEntry);
      if (!rayTraceResult.isNull() && rayTraceResult.isBlockHit()) {
         BlockPos blockPos = rayTraceResult.getBlockPos();
         EnumFacing enumFacing = rayTraceResult.getSideHit();
         return !blockPos.isNull() && !enumFacing.isNull() ? blockPos.offset(enumFacing) : null;
      } else {
         return null;
      }
   }

   public static Vec3 getAimPoint(BlockCoordinate blockCoordinate, @Nullable ItemMappingEntry itemMappingEntry) {
      if (itemMappingEntry != null && itemMappingEntry.equals(getEmptyBucketItem())) {
         return blockCoordinate.P().addVector(0.5, 0.5, 0.5);
      } else if (itemMappingEntry != null && itemMappingEntry.equals(getWaterBucketItem())) {
         EntityPlayerSP localPlayer = Minecraft.thePlayer();
         double x = 0.5;
         double z = 0.5;
         if (localPlayer.isNotNull()) {
            x = Math.max(0.1, Math.min(0.9, localPlayer.z() - blockCoordinate.B()));
            z = Math.max(0.1, Math.min(0.9, localPlayer.h() - blockCoordinate.A()));
         }

         return blockCoordinate.P().addVector(x, 0.999, z);
      } else {
         return blockCoordinate.P().addVector(0.5, 0.5, 0.5);
      }
   }

   public static ItemMappingEntry getWaterBucketItem() {
      if (waterBucketItem == null) {
         waterBucketItem = Vape.INSTANCE.getItemStackResolver().findByName("minecraft:water_bucket");
      }

      return waterBucketItem;
   }

   public static void updateRotationTarget(
      WorldPointRotationTarget worldPointRotationTarget, BlockCoordinate blockCoordinate, ItemMappingEntry itemMappingEntry
   ) {
      Vec3 aimPoint = getAimPoint(blockCoordinate, itemMappingEntry);
      if (!aimPoint.equals(worldPointRotationTarget.getTarget())) {
         worldPointRotationTarget.setTarget(aimPoint);
      }
   }

   private static void releaseRotation(
      FixedRotationController controller,
      boolean releaseClaim,
      boolean finishAdaptiveRotation,
      @Nullable RotationControlClaim rotationControlClaim,
      @Nullable Mod mod
   ) {
      if (controller != null) {
         if (controller.equals(RotationManager.INSTANCE.getActiveController())) {
            RotationManager.INSTANCE.releaseController(controller);
         }

         controller.setRetainAfterCompletion(false);
         if (finishAdaptiveRotation && controller instanceof AdaptiveRotationController) {
            ((AdaptiveRotationController)controller).setRelativeMode(false);
            controller.setComplete(true);
         }

         if (releaseClaim && rotationControlClaim != null && mod != null) {
            rotationControlClaim.release(mod);
         }
      }
   }

   static ItemMappingEntry getCobwebItem() {
      if (cobwebItem == null) {
         cobwebItem = Vape.INSTANCE.getItemStackResolver().findByName("minecraft:cobweb");
      }

      return cobwebItem;
   }

   private BlockPlacementUtility() {
   }

   private static boolean isNearTarget(@Nullable BlockPos blockPos, @Nullable BlockCoordinate blockCoordinate, @Nullable ItemMappingEntry itemMappingEntry) {
      if (blockPos != null && blockCoordinate != null) {
         int deltaX = Math.abs(blockPos.getX() - blockCoordinate.B());
         int deltaY = Math.abs(blockPos.getY() - blockCoordinate.E());
         int deltaZ = Math.abs(blockPos.getZ() - blockCoordinate.A());
         return deltaX == 0 && deltaY == 0 && deltaZ == 0;
      } else {
         return false;
      }
   }

   public static void releaseRotationController(FixedRotationController controller, boolean releaseClaim, boolean finishAdaptiveRotation) {
      releaseRotation(controller, releaseClaim, finishAdaptiveRotation, null, null);
   }
}
