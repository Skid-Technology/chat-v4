package gg.vape.module.blatant.autoladder;

import gg.vape.module.blatant.blockin.BlockPlacementGraph;
import gg.vape.movement.MovementInputHelper;
import gg.vape.utils.MathUtil;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EnumFacing;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.World;

public final class AutoLadderMovementController {
   private static final double LEGACY_LADDER_THICKNESS = 0.125;
   private static final double MODERN_LADDER_THICKNESS = 0.1875;
   private static final double CONTACT_PRESS_OVERLAP = 0.005;
   private static final double AIR_DECAY_PER_TICK = 0.91;
   private static final double GROUND_DECAY_PER_TICK = 0.546;
   private static final double CENTERING_DEADZONE = 0.03;
   private static final double AXIS_PRESS_THRESHOLD = 0.5;

   private AutoLadderMovementController() {
   }

   public static AutoLadderMovementController.CenterInput chooseCentering(EntityPlayerSP player, World world, double centerX, double centerZ) {
      return chooseCentering(player, player, world, new BlockPlacementGraph(player), centerX, centerZ);
   }

   public static AutoLadderMovementController.CenterInput chooseCentering(
      EntityPlayerSP player, World world, double centerX, double centerZ, BlockData ladderBlock, EnumFacing facing
   ) {
      return chooseCentering(player, player, world, new BlockPlacementGraph(player), centerX, centerZ, ladderBlock, facing);
   }

   public static AutoLadderMovementController.CenterInput chooseCentering(
      EntityPlayer sourcePlayer, EntityPlayerSP localPlayer, World world, BlockPlacementGraph graph, double centerX, double centerZ
   ) {
      return chooseCentering(sourcePlayer, localPlayer, world, graph, centerX, centerZ, null, null);
   }

   public static AutoLadderMovementController.CenterInput chooseCentering(
      EntityPlayer sourcePlayer,
      EntityPlayerSP localPlayer,
      World world,
      BlockPlacementGraph graph,
      double centerX,
      double centerZ,
      BlockData ladderBlock,
      EnumFacing facing
   ) {
      double[] target = resolveCenteringTarget(sourcePlayer, centerX, centerZ, ladderBlock, facing);
      return chooseCenteringAnalytic(sourcePlayer, target[0], target[1], ladderBlock, facing);
   }

   private static double[] resolveCenteringTarget(EntityPlayer player, double centerX, double centerZ, BlockData ladderBlock, EnumFacing facing) {
      if (requiresLadderContact() && ladderBlock != null && facing != null) {
         AxisAlignedBB bounds = player.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu();
         double halfWidth = (bounds.getMaxX() - bounds.getMinX()) / 2.0;
         double halfDepth = (bounds.getMaxZ() - bounds.getMinZ()) / 2.0;
         double thickness = getLadderThickness();
         double targetX = centerX;
         double targetZ = centerZ;
         int directionX = facing.getDirectionVector().getX();
         int directionZ = facing.getDirectionVector().getZ();
         if (directionX > 0) {
            targetX = ladderBlock.D() + halfWidth + thickness - 0.005;
         } else if (directionX < 0) {
            targetX = ladderBlock.D() + 1.0 - halfWidth - thickness + 0.005;
         } else if (directionZ > 0) {
            targetZ = ladderBlock.G() + halfDepth + thickness - 0.005;
         } else if (directionZ < 0) {
            targetZ = ladderBlock.G() + 1.0 - halfDepth - thickness + 0.005;
         }

         return new double[]{targetX, targetZ};
      } else {
         return new double[]{centerX, centerZ};
      }
   }

   static boolean requiresLadderContact() {
      return ForgeVersion.MC_1_8_9.d() || ForgeVersion.MC_1_12_2.d();
   }

   static double getSupportClearanceMargin() {
      return requiresLadderContact() ? -0.005 : 0.04;
   }

   static double getLadderTopClearanceMargin() {
      return requiresLadderContact() ? -0.005 : 0.002;
   }

   private static AutoLadderMovementController.CenterInput chooseCenteringAnalytic(
      EntityPlayer player, double centerX, double centerZ, BlockData ladderBlock, EnumFacing facing
   ) {
      double residualX = centerX - player.z();
      double residualZ = centerZ - player.h();
      double decay = player.b$src$Z$fqlxe4() ? 0.546 : 0.91;
      double driftFactor = decay * (1.0 + decay);
      residualX -= player.t() * driftFactor;
      residualZ -= player.T() * driftFactor;
      double residualMagnitude = Math.sqrt(residualX * residualX + residualZ * residualZ);
      boolean maintainPress = residualMagnitude < 0.03 && maintainsLadderPress(player, ladderBlock, facing, centerX, centerZ);
      if (residualMagnitude < 0.03 && !maintainPress) {
         return new AutoLadderMovementController.CenterInput(false, false, false, false);
      } else {
         double unitX;
         double unitZ;
         if (residualMagnitude < 1.0E-9) {
            if (ladderBlock == null || facing == null) {
               return new AutoLadderMovementController.CenterInput(false, false, false, false);
            }

            AxisAlignedBB ladderBounds = getExpectedLadderBounds(ladderBlock, facing);
            unitX = (ladderBounds.getMinX() + ladderBounds.getMaxX()) / 2.0 - player.z();
            unitZ = (ladderBounds.getMinZ() + ladderBounds.getMaxZ()) / 2.0 - player.h();
            double magnitude = Math.sqrt(unitX * unitX + unitZ * unitZ);
            if (magnitude < 1.0E-9) {
               return new AutoLadderMovementController.CenterInput(false, false, false, false);
            }

            unitX /= magnitude;
            unitZ /= magnitude;
         } else {
            unitX = residualX / residualMagnitude;
            unitZ = residualZ / residualMagnitude;
         }

         float yawRadians = player.J() * (float) (Math.PI / 180.0);
         float sinYaw = MathUtil.sin(yawRadians);
         float cosYaw = MathUtil.cos(yawRadians);
         double forwardProjection = unitX * -sinYaw + unitZ * cosYaw;
         double rightProjection = unitX * -cosYaw + unitZ * -sinYaw;
         return new AutoLadderMovementController.CenterInput(forwardProjection > 0.5, forwardProjection < -0.5, rightProjection < -0.5, rightProjection > 0.5);
      }
   }

   private static boolean maintainsLadderPress(EntityPlayer player, BlockData ladderBlock, EnumFacing facing, double centerX, double centerZ) {
      if (ladderBlock != null && facing != null) {
         AxisAlignedBB ladderBounds = getExpectedLadderBounds(ladderBlock, facing);
         AxisAlignedBB playerBounds = player.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu();
         if (playerBounds.getMaxX() > ladderBounds.getMinX()
            && playerBounds.getMinX() < ladderBounds.getMaxX()
            && playerBounds.getMaxZ() > ladderBounds.getMinZ()
            && playerBounds.getMinZ() < ladderBounds.getMaxZ()) {
            return true;
         } else {
            double directionX = facing.getDirectionVector().getX();
            double directionZ = facing.getDirectionVector().getZ();
            double thickness = getLadderThickness();
            double contactReach = 0.03 + (playerBounds.getMaxX() - playerBounds.getMinX()) / 2.0;
            double contactDepthReach = 0.03 + (playerBounds.getMaxZ() - playerBounds.getMinZ()) / 2.0;
            if (directionX > 0.0) {
               return centerX < ladderBlock.D() + thickness + contactReach;
            } else if (directionX < 0.0) {
               return centerX > ladderBlock.D() + 1.0 - thickness - contactReach;
            } else if (directionZ > 0.0) {
               return centerZ < ladderBlock.G() + thickness + contactDepthReach;
            } else {
               return directionZ < 0.0 ? centerZ > ladderBlock.G() + 1.0 - thickness - contactDepthReach : false;
            }
         }
      } else {
         return false;
      }
   }

   static double getLadderThickness() {
      return ForgeVersion.MC_1_16_5.d() ? 0.1875 : 0.125;
   }

   static AxisAlignedBB getExpectedLadderBounds(BlockData ladder, EnumFacing facing) {
      int directionX = facing.getDirectionVector().getX();
      int directionZ = facing.getDirectionVector().getZ();
      double thickness = getLadderThickness();
      double minX = ladder.D();
      double maxX = ladder.D() + 1.0;
      double minZ = ladder.G();
      double maxZ = ladder.G() + 1.0;
      if (directionX > 0) {
         maxX = minX + thickness;
      } else if (directionX < 0) {
         minX = maxX - thickness;
      } else if (directionZ > 0) {
         maxZ = minZ + thickness;
      } else if (directionZ < 0) {
         minZ = maxZ - thickness;
      }

      return AxisAlignedBB.create(minX, ladder.B(), minZ, maxX, ladder.B() + 1.0, maxZ);
   }

   public static void apply(AutoLadderMovementController.CenterInput input) {
      applyDirectional(input.forward, input.backward, input.left, input.right);
   }

   public static void apply(AutoLadderFallAdjustment adjustment) {
      if (!adjustment.overridesInput()) {
         MovementInputHelper.restorePhysicalInput(false);
      } else {
         applyDirectional(adjustment.isForward(), adjustment.isBackward(), adjustment.isLeft(), adjustment.isRight());
      }
   }

   private static void applyDirectional(boolean forward, boolean backward, boolean left, boolean right) {
      MovementInputHelper.synchronizeDirectionalInput(forward, backward, left, right);
      MovementInputHelper.setJumpPressed(false);
      GameSettings settings = Minecraft.gameSettings();
      MovementInputHelper.synchronizeKeyState(settings.d$src$Lgg_vape_wrapper_impl_KeyBinding_$adn2z0(), false);
   }

   public static final class CenterInput {
      private final boolean forward;
      private final boolean backward;
      private final boolean left;
      private final boolean right;

      private CenterInput(boolean forward, boolean backward, boolean left, boolean right) {
         this.forward = forward;
         this.backward = backward;
         this.left = left;
         this.right = right;
      }

      public boolean isForward() {
         return this.forward;
      }

      public boolean isBackward() {
         return this.backward;
      }

      public boolean isLeft() {
         return this.left;
      }

      public boolean isRight() {
         return this.right;
      }
   }
}
