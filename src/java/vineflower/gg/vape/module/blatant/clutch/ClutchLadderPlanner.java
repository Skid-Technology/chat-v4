package gg.vape.module.blatant.clutch;

import gg.vape.config.ClientSettings;
import gg.vape.module.blatant.Clutch;
import gg.vape.module.blatant.autoladder.AutoLadderMovementController;
import gg.vape.module.blatant.blockin.BlockPathPlanner;
import gg.vape.module.blatant.blockin.BlockPlacementGraph;
import gg.vape.module.utility.clutch.BlockPathSearchStrategy;
import gg.vape.module.utility.clutch.ClutchPlacementPathUtils;
import gg.vape.module.utility.clutch.PlacementTarget;
import gg.vape.rotation.RotationAngles;
import gg.vape.utils.BlockUtil;
import gg.vape.utils.MathUtil;
import gg.vape.utils.RotationVectorMath;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EnumFacing;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Vec3;
import gg.vape.wrapper.impl.World;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public final class ClutchLadderPlanner {
   private static final double LADDER_SIDE_ENTRY_DEPTH = 0.28;
   private static final double CATCH_CELL_INSET = 0.02;
   private static final double SUPPORT_CLEARANCE_MARGIN = 0.04;
   private static final double CATCH_CANDIDATE_RADIUS = 0.67;
   private static final double LADDER_TOP_CLEARANCE_MARGIN = 0.002;
   private static final int MAX_SIMULATION_TICKS = 20;
   private static final int MAX_EXTENSION_BLOCKS = 6;
   private static final int MAX_REACHABLE_SUPPORTS = 12;
   private static final EnumFacing[] HORIZONTAL_FACINGS = EnumFacing.c$src$ALgg_vape_wrapper_impl_EnumFacing_$1i3g4ft();
   private final Clutch clutch;
   private final World world;
   private final EntityPlayerSP player;
   private final double reach;
   private final boolean[] physicalInput;
   private final BlockPlacementGraph graph;
   private String failureReason;

   public ClutchLadderPlanner(Clutch clutch, World world, EntityPlayerSP player, BlockPlacementGraph graph) {
      this.clutch = clutch;
      this.world = world;
      this.player = player;
      this.graph = graph;
      this.reach = Math.max(0.0, (double)Minecraft.playerController().N());
      GameSettings settings = Minecraft.gameSettings();
      this.physicalInput = new boolean[]{
         ClientSettings.isPhysicalKeyDown(settings.Y()),
         ClientSettings.isPhysicalKeyDown(settings.s()),
         ClientSettings.isPhysicalKeyDown(settings.x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg()),
         ClientSettings.isPhysicalKeyDown(settings.g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3())
      };
   }

   public String getFailureReason() {
      return this.failureReason;
   }

   public ClutchLadderPlan findPlan() {
      this.failureReason = null;
      List<ClutchLadderPlanner.TrajectoryPoint> trajectory = this.simulateTrajectory();
      if (trajectory.size() < 2) {
         this.failureReason = "Not enough time for a ladder rescue";
         return null;
      } else {
         List<ClutchLadderPlanner.CatchSample> catchSamples = this.findCatchSamples(trajectory);
         if (catchSamples.isEmpty()) {
            this.failureReason = "No ladder catch window in the fall trajectory";
            return null;
         } else {
            List<ClutchLadderPlan> plans = new ArrayList<>();

            for (ClutchLadderPlanner.CatchSample sample : catchSamples) {
               this.enumerateCatchCells(sample, (ladderBlock, facing, catchX, catchZ, movementError) -> {
                  ClutchLadderPlan plan = this.buildPlan(sample, ladderBlock, facing, catchX, catchZ, movementError, trajectory);
                  if (plan != null) {
                     plans.add(plan);
                  }
               });
            }

            if (plans.isEmpty()) {
               this.failureReason = "Could not place a ladder in time";
               return null;
            } else {
               plans.sort(Comparator.comparingDouble(ClutchLadderPlan::getScore));
               return plans.get(0);
            }
         }
      }
   }

   private List<ClutchLadderPlanner.TrajectoryPoint> simulateTrajectory() {
      BlockPathPlanner simulation = new BlockPathPlanner(this.player, this.player, this.world, this.graph);
      simulation.applySnapshot(this.graph);
      this.applyPhysicalInput(simulation);
      EntityPlayer simulatedPlayer = simulation.getSimulatedPlayer();
      int worldBottom = ForgeVersion.MC_1_20_6.d() ? this.world.R() : 0;
      List<ClutchLadderPlanner.TrajectoryPoint> points = new ArrayList<>();

      for (int tick = 0; tick <= 20; tick++) {
         boolean onGround = simulatedPlayer.b$src$Z$fqlxe4();
         points.add(ClutchLadderPlanner.TrajectoryPoint.capture(tick, simulatedPlayer, onGround, new BlockPlacementGraph(simulation)));
         if (tick > 0 && onGround || simulatedPlayer.N() <= worldBottom) {
            break;
         }

         simulation.simulateTick();
         if (tick == 0) {
            this.applyPhysicalInput(simulation);
         }
      }

      return points;
   }

   private void applyPhysicalInput(BlockPathPlanner simulation) {
      simulation.setInput(this.physicalInput[0], this.physicalInput[1], this.physicalInput[2], this.physicalInput[3], false, false);
   }

   private List<ClutchLadderPlanner.CatchSample> findCatchSamples(List<ClutchLadderPlanner.TrajectoryPoint> trajectory) {
      Map<Long, ClutchLadderPlanner.CatchSample> samples = new LinkedHashMap<>();
      int minimumLadderY = this.minimumCatchLadderY(trajectory);
      ClutchLadderPlanner.TrajectoryPoint initial = trajectory.get(0);
      int initialLadderY = MathUtil.floor(initial.y + 1.0E-4);
      double initialEntryY = initialLadderY + 1.0 - 0.28;
      if (!initial.onGround && initial.motionY < 0.0 && initialLadderY >= minimumLadderY && initial.y <= initialEntryY + 1.0E-4) {
         this.addCatchSample(samples, new ClutchLadderPlanner.CatchSample(initial, initial.tick, initialLadderY, false), false);
      }

      for (int index = 1; index < trajectory.size(); index++) {
         ClutchLadderPlanner.TrajectoryPoint previous = trajectory.get(index - 1);
         ClutchLadderPlanner.TrajectoryPoint current = trajectory.get(index);
         if (!(current.y >= previous.y) && (!(previous.motionY >= 0.0) || !(current.motionY >= 0.0))) {
            int highestCrossedLayer = MathUtil.floor(previous.y - 1.0 + 0.28 + 1.0E-4);
            int lowestCrossedLayer = Math.max(minimumLadderY, MathUtil.floor(current.y - 1.0 + 0.28 + 1.0E-4) + 1);

            for (int ladderY = highestCrossedLayer; ladderY >= lowestCrossedLayer; ladderY--) {
               double sideEntryY = ladderY + 1.0 - 0.28;
               if (!(previous.y < sideEntryY) && !(current.y >= sideEntryY)) {
                  ClutchLadderPlanner.TrajectoryPoint sweptPoint = ClutchLadderPlanner.TrajectoryPoint.interpolateAtY(previous, current, sideEntryY);
                  this.addCatchSample(samples, new ClutchLadderPlanner.CatchSample(sweptPoint, previous.tick, ladderY, true), true);
               }
            }

            int ladderYx = MathUtil.floor(current.y + 1.0E-4);
            double sideEntryY = ladderYx + 1.0 - 0.28;
            if (!current.onGround && current.motionY < 0.0 && ladderYx >= minimumLadderY && current.y >= ladderYx && current.y <= sideEntryY + 1.0E-4) {
               this.addCatchSample(samples, new ClutchLadderPlanner.CatchSample(current, current.tick, ladderYx, false), false);
            }
         }
      }

      return new ArrayList<>(samples.values());
   }

   private int minimumCatchLadderY(List<ClutchLadderPlanner.TrajectoryPoint> trajectory) {
      ClutchLadderPlanner.TrajectoryPoint last = trajectory.get(trajectory.size() - 1);
      return last.onGround ? MathUtil.floor(last.y + 1.0E-4) : 0;
   }

   private void addCatchSample(Map<Long, ClutchLadderPlanner.CatchSample> samples, ClutchLadderPlanner.CatchSample sample, boolean prefer) {
      long key = (long)sample.ladderY << 32 | sample.point.tick & 4294967295L;
      ClutchLadderPlanner.CatchSample previous = samples.get(key);
      if (previous == null || prefer) {
         samples.put(key, sample);
      }
   }

   private void enumerateCatchCells(ClutchLadderPlanner.CatchSample sample, ClutchLadderPlanner.CatchCellConsumer consumer) {
      int searchRadius = 1;
      int baseX = MathUtil.floor(sample.point.x);
      int baseZ = MathUtil.floor(sample.point.z);

      for (int xOffset = -searchRadius; xOffset <= searchRadius; xOffset++) {
         for (int zOffset = -searchRadius; zOffset <= searchRadius; zOffset++) {
            BlockData ladderBlock = new BlockData(baseX + xOffset, sample.ladderY, baseZ + zOffset);

            for (EnumFacing facing : HORIZONTAL_FACINGS) {
               double catchX = ladderBlock.D() + 0.5;
               double catchZ = ladderBlock.G() + 0.5;
               double movementError = Math.hypot(catchX - sample.point.x, catchZ - sample.point.z);
               if (!(movementError > 0.67)) {
                  consumer.accept(ladderBlock, facing, catchX, catchZ, movementError);
               }
            }
         }
      }
   }

   private ClutchLadderPlan buildPlan(
      ClutchLadderPlanner.CatchSample sample,
      BlockData ladderBlock,
      EnumFacing facing,
      double catchX,
      double catchZ,
      double movementError,
      List<ClutchLadderPlanner.TrajectoryPoint> trajectory
   ) {
      if (!BlockUtil.u(this.blockAt(ladderBlock))) {
         return null;
      } else {
         BlockData supportBlock = ladderBlock.R(facing.getOpposite());
         Vector<PlacementTarget> targets = new Vector<>();
         if (this.isStableSupport(supportBlock)) {
            PlacementTarget ladderTarget = new PlacementTarget(supportBlock, facing);
            ladderTarget.ladderPlacement = true;
            targets.add(ladderTarget);
         } else {
            if (!BlockUtil.u(this.blockAt(supportBlock))) {
               return null;
            }

            Vector<PlacementTarget> extension = this.findExtensionPath(supportBlock, trajectory, sample.latestPlacementTick);
            if (extension == null || extension.isEmpty()) {
               return null;
            }

            targets.addAll(extension);
            PlacementTarget ladderTarget = new PlacementTarget(supportBlock, facing);
            ladderTarget.ladderPlacement = true;
            targets.add(ladderTarget);
         }

         int tickCursor = 0;
         double rotationTotal = 0.0;
         int ladderPlacementTick = -1;

         for (PlacementTarget target : targets) {
            ClutchLadderPlanner.PlacementOpportunity opportunity = this.findPlacementOpportunity(
               target, trajectory, tickCursor, sample.latestPlacementTick, !target.ladderPlacement
            );
            if (opportunity == null) {
               return null;
            }

            tickCursor = opportunity.tick + 1;
            rotationTotal += opportunity.rotationDistance;
            ladderPlacementTick = opportunity.tick;
         }

         ClutchLadderPlanner.ControlledCatch controlledCatch = this.simulateControlledCatch(trajectory, ladderPlacementTick, ladderBlock, supportBlock);
         if (controlledCatch == null) {
            return null;
         } else {
            int slack = controlledCatch.catchTick - ladderPlacementTick;
            double score = movementError * 1000.0
               + controlledCatch.remainingCenterError * 250.0
               + rotationTotal * 2.0
               + controlledCatch.catchTick * 4.0
               - slack * 30.0
               + targets.size() * 5.0;
            return new ClutchLadderPlan(targets, ladderBlock, facing, catchX, catchZ, controlledCatch.catchTick, score);
         }
      }
   }

   private Vector<PlacementTarget> findExtensionPath(BlockData anchorBlock, List<ClutchLadderPlanner.TrajectoryPoint> trajectory, int latestPlacementTick) {
      int maxDepth = Math.min(Math.max(0, latestPlacementTick - 2), 6);
      if (maxDepth == 0) {
         return null;
      } else {
         List<BlockData> starts = this.findReachableSupports(trajectory);
         Vector<PlacementTarget> best = null;
         double bestScore = Double.MAX_VALUE;

         for (BlockData start : starts) {
            ClutchSearchPlanner planner = new ClutchSearchPlanner(new ClutchLadderPlanner.LadderExtensionStrategy(maxDepth));
            Vector<PlacementTarget> path = planner.findPath(start, anchorBlock);
            if (path != null && !path.isEmpty() && path.size() <= maxDepth && path.lastElement().getPlacedBlock().equals(anchorBlock)) {
               double score = path.size() * 100.0 + Math.abs(start.B() - anchorBlock.B()) * 3.0;
               if (score < bestScore) {
                  bestScore = score;
                  best = path;
               }
            }
         }

         return best;
      }
   }

   private List<BlockData> findReachableSupports(List<ClutchLadderPlanner.TrajectoryPoint> trajectory) {
      ClutchLadderPlanner.TrajectoryPoint start = trajectory.get(0);
      int playerX = MathUtil.floor(start.x);
      int playerZ = MathUtil.floor(start.z);
      int playerY = MathUtil.floor(start.y);
      Map<Long, BlockData> cache = new HashMap<>();
      List<BlockData> candidates = new ArrayList<>();

      for (int yOffset = -1; yOffset <= 3; yOffset++) {
         for (int xOffset = -4; xOffset <= 4; xOffset++) {
            for (int zOffset = -4; zOffset <= 4; zOffset++) {
               BlockData block = this.cachedBlock(cache, playerX + xOffset, playerY + yOffset, playerZ + zOffset);
               if (this.isStableSupport(block) && !this.clutch.getPlacedBlocks().Y(block)) {
                  candidates.add(block);
               }
            }
         }
      }

      candidates.sort(Comparator.comparingDouble(blockx -> Math.hypot(blockx.D() + 0.5 - start.x, blockx.G() + 0.5 - start.z)));
      return candidates.size() > 12 ? candidates.subList(0, 12) : candidates;
   }

   private BlockData cachedBlock(Map<Long, BlockData> cache, int x, int y, int z) {
      long key = (long)x << 32 ^ (long)y << 16 ^ z;
      return cache.computeIfAbsent(key, ignored -> new BlockData(x, y, z));
   }

   private ClutchLadderPlanner.PlacementOpportunity findPlacementOpportunity(
      PlacementTarget target, List<ClutchLadderPlanner.TrajectoryPoint> trajectory, int earliestTick, int latestTick, boolean placingSolidBlock
   ) {
      if (latestTick < earliestTick) {
         return null;
      } else {
         ClutchLadderPlanner.PlacementOpportunity best = null;

         for (ClutchLadderPlanner.TrajectoryPoint point : trajectory) {
            if (point.tick >= earliestTick) {
               if (point.tick > latestTick) {
                  break;
               }

               if ((!placingSolidBlock || !point.intersectsUnitBlock(target.getPlacedBlock()))
                  && (!placingSolidBlock || ClutchPlacementPathUtils.isPlacementSpaceClear(this.world, this.player, target.getPlacedBlock()))) {
                  Vec3 eye = point.eyePosition();
                  if (ClutchPlacementPathUtils.isBlockFaceVisible(eye, this.world, target.supportBlock, target.facing)) {
                     Vec3 hit = ClutchPlacementPathUtils.findBestPlacementHitPointWithinReach(
                        this.player, this.world, eye, target, point.yaw, point.pitch, this.reach
                     );
                     if (hit != null) {
                        double rotationDistance = this.rotationDistance(eye, hit, point.yaw, point.pitch);
                        ClutchLadderPlanner.PlacementOpportunity candidate = new ClutchLadderPlanner.PlacementOpportunity(point.tick, rotationDistance);
                        if (best == null || candidate.tick < best.tick || candidate.tick == best.tick && candidate.rotationDistance < best.rotationDistance) {
                           best = candidate;
                        }
                     }
                  }
               }
            }
         }

         return best;
      }
   }

   private double rotationDistance(Vec3 eye, Vec3 hit, float yaw, float pitch) {
      RotationAngles rotation = RotationVectorMath.d(eye, hit, yaw, pitch);
      return Math.abs(MathUtil.wrapAngleTo180(rotation.getYaw() - yaw)) + Math.abs(rotation.getPitch() - pitch);
   }

   private ClutchLadderPlanner.ControlledCatch simulateControlledCatch(
      List<ClutchLadderPlanner.TrajectoryPoint> trajectory, int controlStartTick, BlockData ladderBlock, BlockData supportBlock
   ) {
      ClutchLadderPlanner.TrajectoryPoint start = this.pointAtTick(trajectory, controlStartTick);
      if (start != null && start.snapshot != null) {
         BlockPathPlanner simulation = new BlockPathPlanner(this.player, this.player, this.world, start.snapshot);
         simulation.applySnapshot(start.snapshot);
         EntityPlayer simulatedPlayer = simulation.getSimulatedPlayer();
         ClutchLadderPlanner.TrajectoryPoint previous = ClutchLadderPlanner.TrajectoryPoint.capture(
            controlStartTick, simulatedPlayer, simulatedPlayer.b$src$Z$fqlxe4(), new BlockPlacementGraph(simulation)
         );
         if (this.isSafeCatchPosition(previous, ladderBlock, supportBlock) && this.verticallyOverlapsLadder(previous, ladderBlock)) {
            return new ClutchLadderPlanner.ControlledCatch(controlStartTick, this.centerError(previous, ladderBlock));
         } else {
            EnumFacing ladderFacing = this.facingFromSupport(ladderBlock, supportBlock);
            AxisAlignedBB ladderBounds = expectedLadderBounds(ladderBlock, ladderFacing);
            int lastTick = Math.min(20, controlStartTick + 20);
            int tick = controlStartTick + 1;

            while (tick <= lastTick) {
               BlockPlacementGraph snapshot = new BlockPlacementGraph(simulation);
               AutoLadderMovementController.CenterInput input = AutoLadderMovementController.chooseCentering(
                  simulatedPlayer, this.player, this.world, snapshot, ladderBlock.D() + 0.5, ladderBlock.G() + 0.5
               );
               simulation.setInput(input.isForward(), input.isBackward(), input.isLeft(), input.isRight(), false, false);
               simulation.simulateTick(false);
               ClutchLadderPlanner.TrajectoryPoint current = ClutchLadderPlanner.TrajectoryPoint.capture(
                  tick, simulatedPlayer, simulatedPlayer.b$src$Z$fqlxe4(), new BlockPlacementGraph(simulation)
               );
               if (!current.intersectsUnitBlock(supportBlock, 0.04) && !previous.sweptIntersectsUnitBlock(current, supportBlock, 0.04)) {
                  double ladderTop = ladderBlock.B() + 1.0;
                  if (previous.y >= ladderTop && current.y < ladderTop) {
                     ClutchLadderPlanner.TrajectoryPoint topCrossing = ClutchLadderPlanner.TrajectoryPoint.interpolateAtY(previous, current, ladderTop);
                     if (topCrossing.horizontallyIntersects(ladderBounds, 0.002)) {
                        return null;
                     }
                  }

                  if (current.onGround) {
                     return null;
                  }

                  if (this.isSafeCatchPosition(current, ladderBlock, supportBlock) && this.verticallyOverlapsLadder(current, ladderBlock)) {
                     return new ClutchLadderPlanner.ControlledCatch(tick, this.centerError(current, ladderBlock));
                  }

                  if (!(current.y < ladderBlock.B() - 0.05) && !(current.motionY >= 0.0)) {
                     previous = current;
                     tick++;
                     continue;
                  }

                  return null;
               }

               return null;
            }

            return null;
         }
      } else {
         return null;
      }
   }

   private ClutchLadderPlanner.TrajectoryPoint pointAtTick(List<ClutchLadderPlanner.TrajectoryPoint> trajectory, int tick) {
      for (ClutchLadderPlanner.TrajectoryPoint point : trajectory) {
         if (point.tick == tick) {
            return point;
         }
      }

      return null;
   }

   private boolean verticallyOverlapsLadder(ClutchLadderPlanner.TrajectoryPoint point, BlockData ladderBlock) {
      return point.maxY > ladderBlock.B() && point.minY < ladderBlock.B() + 1.0;
   }

   private double centerError(ClutchLadderPlanner.TrajectoryPoint point, BlockData ladderBlock) {
      return Math.hypot(point.x - (ladderBlock.D() + 0.5), point.z - (ladderBlock.G() + 0.5));
   }

   private boolean isSafeCatchPosition(ClutchLadderPlanner.TrajectoryPoint point, BlockData ladderBlock, BlockData supportBlock) {
      boolean centerInsideLadderCell = point.x >= ladderBlock.D() + 0.02
         && point.x <= ladderBlock.D() + 1.0 - 0.02
         && point.z >= ladderBlock.G() + 0.02
         && point.z <= ladderBlock.G() + 1.0 - 0.02;
      return centerInsideLadderCell && !point.horizontallyIntersectsUnitBlock(supportBlock, 0.04);
   }

   private EnumFacing facingFromSupport(BlockData ladderBlock, BlockData supportBlock) {
      int directionX = ladderBlock.D() - supportBlock.D();
      int directionZ = ladderBlock.G() - supportBlock.G();

      for (EnumFacing facing : HORIZONTAL_FACINGS) {
         if (facing.getDirectionVector().getX() == directionX && facing.getDirectionVector().getZ() == directionZ) {
            return facing;
         }
      }

      return HORIZONTAL_FACINGS[0];
   }

   private static double ladderThickness() {
      return ForgeVersion.MC_1_16_5.d() ? 0.1875 : 0.125;
   }

   private static AxisAlignedBB expectedLadderBounds(BlockData ladder, EnumFacing facing) {
      int directionX = facing.getDirectionVector().getX();
      int directionZ = facing.getDirectionVector().getZ();
      double thickness = ladderThickness();
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

   private Block blockAt(BlockData block) {
      return this.world.getBlockByPos(block.D(), block.B(), block.G());
   }

   private boolean isStableSupport(BlockData blockData) {
      Block block = this.blockAt(blockData);
      return block.isNotNull() && BlockUtil.k(block) && !ClutchPlacementPathUtils.isBlacklistedPlacementBlock(block);
   }

   private interface CatchCellConsumer {
      void accept(BlockData var1, EnumFacing var2, double var3, double var5, double var7);
   }

   private static final class CatchSample {
      private final ClutchLadderPlanner.TrajectoryPoint point;
      private final int latestPlacementTick;
      private final int ladderY;
      private final boolean swept;

      private CatchSample(ClutchLadderPlanner.TrajectoryPoint point, int latestPlacementTick, int ladderY, boolean swept) {
         this.point = point;
         this.latestPlacementTick = latestPlacementTick;
         this.ladderY = ladderY;
         this.swept = swept;
      }
   }

   private static final class ControlledCatch {
      private final int catchTick;
      private final double remainingCenterError;

      private ControlledCatch(int catchTick, double remainingCenterError) {
         this.catchTick = catchTick;
         this.remainingCenterError = remainingCenterError;
      }
   }

   private final class LadderExtensionStrategy implements BlockPathSearchStrategy<PlacementTarget> {
      private final int maxDepth;

      private LadderExtensionStrategy(int maxDepth) {
         this.maxDepth = maxDepth;
      }

      @Override
      public int getMaxDepth() {
         return this.maxDepth;
      }

      @Override
      public boolean isValidBlock(BlockData blockData) {
         return BlockUtil.u(ClutchLadderPlanner.this.blockAt(blockData));
      }

      @Override
      public boolean canVisit(BlockData blockData) {
         return ClutchPlacementPathUtils.isPlacementSpaceClear(ClutchLadderPlanner.this.world, ClutchLadderPlanner.this.player, blockData)
            && !ClutchLadderPlanner.this.clutch.getPlacedBlocks().Y(blockData);
      }
   }

   private static final class PlacementOpportunity {
      private final int tick;
      private final double rotationDistance;

      private PlacementOpportunity(int tick, double rotationDistance) {
         this.tick = tick;
         this.rotationDistance = rotationDistance;
      }
   }

   private static final class TrajectoryPoint {
      private final int tick;
      private final double x;
      private final double y;
      private final double z;
      private final double eyeY;
      private final double motionY;
      private final float yaw;
      private final float pitch;
      private final double minX;
      private final double minY;
      private final double minZ;
      private final double maxX;
      private final double maxY;
      private final double maxZ;
      private final boolean onGround;
      private final BlockPlacementGraph snapshot;

      private TrajectoryPoint(
         int tick,
         double x,
         double y,
         double z,
         double eyeY,
         double motionY,
         float yaw,
         float pitch,
         AxisAlignedBB bounds,
         boolean onGround,
         BlockPlacementGraph snapshot
      ) {
         this.tick = tick;
         this.x = x;
         this.y = y;
         this.z = z;
         this.eyeY = eyeY;
         this.motionY = motionY;
         this.yaw = yaw;
         this.pitch = pitch;
         this.minX = bounds.getMinX();
         this.minY = bounds.getMinY();
         this.minZ = bounds.getMinZ();
         this.maxX = bounds.getMaxX();
         this.maxY = bounds.getMaxY();
         this.maxZ = bounds.getMaxZ();
         this.onGround = onGround;
         this.snapshot = snapshot;
      }

      private static ClutchLadderPlanner.TrajectoryPoint capture(int tick, EntityPlayer player, boolean onGround, BlockPlacementGraph snapshot) {
         AxisAlignedBB bounds = player.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu();
         return new ClutchLadderPlanner.TrajectoryPoint(
            tick, player.z(), player.N(), player.h(), player.N() + player.X(), player.q(), player.J(), player.V(), bounds, onGround, snapshot
         );
      }

      private static ClutchLadderPlanner.TrajectoryPoint interpolateAtY(
         ClutchLadderPlanner.TrajectoryPoint start, ClutchLadderPlanner.TrajectoryPoint end, double targetY
      ) {
         double verticalDelta = end.y - start.y;
         double progress = Math.abs(verticalDelta) < 1.0E-9 ? 0.0 : (targetY - start.y) / verticalDelta;
         progress = Math.max(0.0, Math.min(1.0, progress));
         double x = lerp(start.x, end.x, progress);
         double z = lerp(start.z, end.z, progress);
         double eyeY = lerp(start.eyeY, end.eyeY, progress);
         double motionY = lerp(start.motionY, end.motionY, progress);
         float yaw = start.yaw + MathUtil.wrapAngleTo180(end.yaw - start.yaw) * (float)progress;
         float pitch = (float)lerp(start.pitch, end.pitch, progress);
         AxisAlignedBB bounds = AxisAlignedBB.create(
            lerp(start.minX, end.minX, progress),
            lerp(start.minY, end.minY, progress),
            lerp(start.minZ, end.minZ, progress),
            lerp(start.maxX, end.maxX, progress),
            lerp(start.maxY, end.maxY, progress),
            lerp(start.maxZ, end.maxZ, progress)
         );
         return new ClutchLadderPlanner.TrajectoryPoint(end.tick, x, targetY, z, eyeY, motionY, yaw, pitch, bounds, false, end.snapshot);
      }

      private static double lerp(double start, double end, double progress) {
         return start + (end - start) * progress;
      }

      private Vec3 eyePosition() {
         return Vec3.create(this.x, this.eyeY, this.z);
      }

      private boolean intersectsUnitBlock(BlockData block) {
         return this.maxX > block.D()
            && this.minX < block.D() + 1.0
            && this.maxY > block.B()
            && this.minY < block.B() + 1.0
            && this.maxZ > block.G()
            && this.minZ < block.G() + 1.0;
      }

      private boolean intersectsUnitBlock(BlockData block, double horizontalMargin) {
         return this.maxX + horizontalMargin > block.D()
            && this.minX - horizontalMargin < block.D() + 1.0
            && this.maxY > block.B()
            && this.minY < block.B() + 1.0
            && this.maxZ + horizontalMargin > block.G()
            && this.minZ - horizontalMargin < block.G() + 1.0;
      }

      private boolean horizontallyIntersectsUnitBlock(BlockData block, double horizontalMargin) {
         return this.maxX + horizontalMargin > block.D()
            && this.minX - horizontalMargin < block.D() + 1.0
            && this.maxZ + horizontalMargin > block.G()
            && this.minZ - horizontalMargin < block.G() + 1.0;
      }

      private boolean horizontallyIntersects(AxisAlignedBB bounds, double horizontalMargin) {
         return this.maxX + horizontalMargin > bounds.getMinX()
            && this.minX - horizontalMargin < bounds.getMaxX()
            && this.maxZ + horizontalMargin > bounds.getMinZ()
            && this.minZ - horizontalMargin < bounds.getMaxZ();
      }

      private boolean sweptIntersectsUnitBlock(ClutchLadderPlanner.TrajectoryPoint next, BlockData block, double horizontalMargin) {
         double startX = (this.minX + this.maxX) / 2.0;
         double startY = (this.minY + this.maxY) / 2.0;
         double startZ = (this.minZ + this.maxZ) / 2.0;
         double endX = (next.minX + next.maxX) / 2.0;
         double endY = (next.minY + next.maxY) / 2.0;
         double endZ = (next.minZ + next.maxZ) / 2.0;
         double halfWidthX = (this.maxX - this.minX) / 2.0 + horizontalMargin;
         double halfHeight = (this.maxY - this.minY) / 2.0;
         double halfWidthZ = (this.maxZ - this.minZ) / 2.0 + horizontalMargin;
         double xEntry = axisEntry(startX, endX, block.D() - halfWidthX, block.D() + 1.0 + halfWidthX);
         double yEntry = axisEntry(startY, endY, block.B() - halfHeight, block.B() + 1.0 + halfHeight);
         double zEntry = axisEntry(startZ, endZ, block.G() - halfWidthZ, block.G() + 1.0 + halfWidthZ);
         double xExit = axisExit(startX, endX, block.D() - halfWidthX, block.D() + 1.0 + halfWidthX);
         double yExit = axisExit(startY, endY, block.B() - halfHeight, block.B() + 1.0 + halfHeight);
         double zExit = axisExit(startZ, endZ, block.G() - halfWidthZ, block.G() + 1.0 + halfWidthZ);
         double entry = Math.max(0.0, Math.max(xEntry, Math.max(yEntry, zEntry)));
         double exit = Math.min(1.0, Math.min(xExit, Math.min(yExit, zExit)));
         return entry <= exit;
      }

      private static double axisEntry(double start, double end, double minimum, double maximum) {
         double delta = end - start;
         if (!(Math.abs(delta) < 1.0E-9)) {
            return Math.min((minimum - start) / delta, (maximum - start) / delta);
         } else {
            return start >= minimum && start <= maximum ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
         }
      }

      private static double axisExit(double start, double end, double minimum, double maximum) {
         double delta = end - start;
         if (!(Math.abs(delta) < 1.0E-9)) {
            return Math.max((minimum - start) / delta, (maximum - start) / delta);
         } else {
            return start >= minimum && start <= maximum ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
         }
      }
   }
}
