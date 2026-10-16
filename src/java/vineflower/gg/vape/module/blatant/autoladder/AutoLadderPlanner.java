package gg.vape.module.blatant.autoladder;

import gg.vape.config.ClientSettings;
import gg.vape.module.blatant.blockin.BlockPathPlanner;
import gg.vape.module.blatant.blockin.BlockPlacementGraph;
import gg.vape.module.utility.clutch.ClutchPlacementPathUtils;
import gg.vape.module.utility.clutch.PlacementTarget;
import gg.vape.rotation.RotationAngles;
import gg.vape.utils.BlockUtil;
import gg.vape.utils.MathUtil;
import gg.vape.utils.RotationVectorMath;
import gg.vape.utils.datas.BlockCoordinate;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.Blocks;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EnumFacing;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.Vec3;
import gg.vape.wrapper.impl.World;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.jetbrains.annotations.Nullable;

public final class AutoLadderPlanner {
   private static final int MAX_SIMULATION_TICKS = 30;
   private static final double LADDER_SIDE_ENTRY_DEPTH = 0.28;
   private static final double CATCH_CELL_INSET = 0.02;
   private static final double CATCH_CANDIDATE_RADIUS = 0.67;
   private static final double LANDING_CENTER_SCORE_WEIGHT = 600.0;
   private static final double FALL_ADJUSTMENT_SCORE = 20.0;
   private static final double PLAN_COUNT_BONUS = 25.0;
   private static final EnumFacing[] HORIZONTAL_FACINGS = EnumFacing.c$src$ALgg_vape_wrapper_impl_EnumFacing_$1i3g4ft();
   private static final EnumFacing[] ALL_FACINGS = EnumFacing.t();
   private final World world;
   private final EntityPlayerSP player;
   private final boolean ladderAvailable;
   private final boolean supportBlockAvailable;
   private final boolean allowSupportBlock;
   private final boolean controlMovement;
   private final Set<String> rejectedPlans;
   private final BlockCoordinate landingBlock;
   private final double reach;
   private final boolean[] physicalInput;
   private int catchGeometryCount;
   private int existingLadderCount;
   private int directSupportCount;
   private int directOpportunityCount;
   private int directLadderTopRejectedCount;
   private int fallbackSpaceCount;
   private int fallbackAnchorCount;
   private int fallbackBlockOpportunityCount;
   private int fallbackCollisionRejectedCount;
   private int fallbackLadderOpportunityCount;
   private int fallbackTimingRejectedCount;
   private int fallbackMovementRejectedCount;
   private int fallbackControlledCollisionRejectedCount;
   private int fallbackLadderTopRejectedCount;
   private AutoLadderFallAdjustment recommendedFallAdjustment = AutoLadderFallAdjustment.PHYSICAL;

   public AutoLadderPlanner(
      World world,
      EntityPlayerSP player,
      boolean ladderAvailable,
      boolean supportBlockAvailable,
      boolean allowSupportBlock,
      boolean controlMovement,
      Set<String> rejectedPlans,
      BlockCoordinate landingBlock
   ) {
      this.world = world;
      this.player = player;
      this.ladderAvailable = ladderAvailable;
      this.supportBlockAvailable = supportBlockAvailable;
      this.allowSupportBlock = allowSupportBlock;
      this.controlMovement = controlMovement;
      this.rejectedPlans = rejectedPlans;
      this.landingBlock = landingBlock;
      this.reach = Math.max(0.0, (double)Minecraft.playerController().N());
      GameSettings settings = Minecraft.gameSettings();
      this.physicalInput = new boolean[]{
         ClientSettings.isPhysicalKeyDown(settings.Y()),
         ClientSettings.isPhysicalKeyDown(settings.s()),
         ClientSettings.isPhysicalKeyDown(settings.x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg()),
         ClientSettings.isPhysicalKeyDown(settings.g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3())
      };
   }

   @Nullable
   public AutoLadderPlan findBestPlan() {
      List<AutoLadderPlanner.TrajectoryCandidate> trajectories = this.simulateTrajectories();
      if (trajectories.isEmpty()) {
         return null;
      } else {
         List<AutoLadderPlanner.CandidateEvaluation> evaluations = new ArrayList<>();

         for (AutoLadderPlanner.TrajectoryCandidate trajectory : trajectories) {
            AutoLadderPlanner.CounterSnapshot before = new AutoLadderPlanner.CounterSnapshot(this);
            AutoLadderPlanner.CandidateEvaluation evaluation = new AutoLadderPlanner.CandidateEvaluation(trajectory, new AutoLadderPlanner.OpportunityMemo());
            evaluation.directPlans = this.findDirectPlans(trajectory, evaluation.memo);
            evaluation.progressScore = before.progressSince(this);
            evaluations.add(evaluation);
         }

         AutoLadderPlanner.PlanSelection direct = this.selectPlanEvaluation(evaluations, true);
         if (direct != null) {
            this.recordRecommendation(direct.evaluation, direct.plan);
            return direct.plan;
         } else if (this.allowSupportBlock && this.supportBlockAvailable && this.ladderAvailable) {
            for (AutoLadderPlanner.CandidateEvaluation evaluation : evaluations) {
               AutoLadderPlanner.CounterSnapshot before = new AutoLadderPlanner.CounterSnapshot(this);
               evaluation.supportPlans = this.findSupportPlans(evaluation.trajectory, evaluation.memo);
               evaluation.progressScore += before.progressSince(this);
            }

            AutoLadderPlanner.PlanSelection support = this.selectPlanEvaluation(evaluations, false);
            AutoLadderPlan selected = support == null ? null : support.plan;
            this.recordRecommendation(support == null ? this.selectProgressEvaluation(evaluations) : support.evaluation, selected);
            return selected;
         } else {
            this.recordRecommendation(this.selectProgressEvaluation(evaluations), null);
            return null;
         }
      }
   }

   public AutoLadderFallAdjustment getRecommendedFallAdjustment() {
      return this.recommendedFallAdjustment;
   }

   private List<AutoLadderPlanner.TrajectoryCandidate> simulateTrajectories() {
      BlockPlacementGraph graph = new BlockPlacementGraph(this.player);
      List<AutoLadderPlanner.TrajectoryCandidate> trajectories = new ArrayList<>();

      for (AutoLadderFallAdjustment adjustment : AutoLadderFallAdjustment.values()) {
         if (this.controlMovement || !adjustment.overridesInput()) {
            List<AutoLadderPlanner.TrajectoryPoint> points = this.simulateTrajectory(graph, adjustment);
            if (points.size() >= 2) {
               AutoLadderPlanner.TrajectoryPoint finalPoint = points.get(points.size() - 1);
               double centerError = Math.hypot(finalPoint.x - (this.landingBlock.B() + 0.5), finalPoint.z - (this.landingBlock.A() + 0.5));
               trajectories.add(new AutoLadderPlanner.TrajectoryCandidate(points, this.findCatchSamples(points), adjustment, centerError));
            }
         }
      }

      return trajectories;
   }

   private List<AutoLadderPlanner.TrajectoryPoint> simulateTrajectory(BlockPlacementGraph graph, AutoLadderFallAdjustment adjustment) {
      BlockPathPlanner simulation = new BlockPathPlanner(this.player, this.player, this.world, graph);
      simulation.applySnapshot(graph);
      if (adjustment.overridesInput()) {
         simulation.setInput(adjustment.isForward(), adjustment.isBackward(), adjustment.isLeft(), adjustment.isRight(), false, false);
      } else {
         this.applyPhysicalInput(simulation);
      }

      EntityPlayer simulatedPlayer = simulation.getSimulatedPlayer();
      List<AutoLadderPlanner.TrajectoryPoint> points = new ArrayList<>();

      for (int tick = 0; tick <= 30; tick++) {
         boolean onGround = simulatedPlayer.b$src$Z$fqlxe4();
         points.add(AutoLadderPlanner.TrajectoryPoint.capture(tick, simulatedPlayer, onGround, new BlockPlacementGraph(simulation)));
         if (tick > 0 && onGround) {
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

   private List<AutoLadderPlan> findDirectPlans(AutoLadderPlanner.TrajectoryCandidate candidate, AutoLadderPlanner.OpportunityMemo memo) {
      Map<String, AutoLadderPlan> plans = new HashMap<>();
      List<AutoLadderPlanner.TrajectoryPoint> trajectory = candidate.points;

      for (AutoLadderPlanner.CatchSample catchSample : candidate.catchSamples) {
         AutoLadderPlanner.TrajectoryPoint point = catchSample.point;
         Map<EnumFacing, List<AutoLadderPlanner.CellCandidate>> facets = new LinkedHashMap<>();
         this.enumerateCatchCells(
            point,
            catchSample.ladderY,
            (ladderBlock, facing, catchX, catchZ, movementError) -> {
               BlockData supportBlock = ladderBlock.R(facing.getOpposite());
               PlacementTarget ladderTarget = new PlacementTarget(supportBlock, facing);
               Block ladderState = this.blockAt(ladderBlock);
               if (this.isLadder(ladderState)) {
                  this.existingLadderCount++;
                  double existingCatchX = ladderBlock.D() + 0.5;
                  double existingCatchZ = ladderBlock.G() + 0.5;
                  double existingMovementError = Math.hypot(existingCatchX - point.x, existingCatchZ - point.z);
                  AutoLadderPlan plan = new AutoLadderPlan(
                     AutoLadderPlan.Mode.EXISTING_LADDER,
                     null,
                     ladderTarget,
                     existingCatchX,
                     existingCatchZ,
                     point.tick,
                     -1,
                     -1,
                     existingMovementError * 1000.0 + point.tick * 4.0 + this.trajectoryCost(candidate),
                     candidate.adjustment
                  );
                  this.addPlan(plans, plan);
               } else if (this.ladderAvailable && BlockUtil.u(ladderState) && this.isStableSupport(supportBlock)) {
                  this.directSupportCount++;
                  facets.computeIfAbsent(facing, key -> new ArrayList<>())
                     .add(new AutoLadderPlanner.CellCandidate(ladderBlock, supportBlock, ladderTarget, catchX, catchZ, movementError));
               }
            }
         );

         for (Entry<EnumFacing, List<AutoLadderPlanner.CellCandidate>> entry : facets.entrySet()) {
            this.findDirectPlansForFacet(candidate, trajectory, catchSample, entry.getKey(), entry.getValue(), memo, plans);
         }
      }

      return new ArrayList<>(plans.values());
   }

   private void findDirectPlansForFacet(
      AutoLadderPlanner.TrajectoryCandidate candidate,
      List<AutoLadderPlanner.TrajectoryPoint> trajectory,
      AutoLadderPlanner.CatchSample catchSample,
      EnumFacing facing,
      List<AutoLadderPlanner.CellCandidate> cells,
      AutoLadderPlanner.OpportunityMemo memo,
      Map<String, AutoLadderPlan> plans
   ) {
      boolean anyViable = false;

      for (AutoLadderPlanner.CellCandidate cell : cells) {
         AutoLadderPlanner.PlacementOpportunity opportunity = this.findPlacementOpportunity(
            cell.ladderTarget, trajectory, 0, catchSample.latestPlacementTick, false, memo
         );
         if (opportunity != null) {
            cell.ladderOpportunity = opportunity;
            this.directOpportunityCount++;
            anyViable = true;
         }
      }

      if (anyViable) {
         cells.sort(Comparator.comparingDouble(cellx -> cellx.movementError));
         List<AutoLadderPlanner.CatchPathPoint> path = null;

         for (AutoLadderPlanner.CellCandidate cellx : cells) {
            if (cellx.ladderOpportunity != null) {
               int placementTick = cellx.ladderOpportunity.tick;
               path = this.simulateControlledCatchPath(trajectory, placementTick, cellx, facing, placementTick, placementTick);
               if (path != null) {
                  break;
               }
            }
         }

         if (path != null) {
            int supportExistsTick = path.get(0).tick;

            for (AutoLadderPlanner.CellCandidate cellxx : cells) {
               if (cellxx.ladderOpportunity != null) {
                  AutoLadderPlanner.ControlledCatch caught = this.evaluateCatchOnPath(path, cellxx, facing, supportExistsTick, cellxx.ladderOpportunity.tick);
                  if (caught == null) {
                     this.directLadderTopRejectedCount++;
                  } else {
                     int slack = caught.catchTick - cellxx.ladderOpportunity.tick;
                     double score = cellxx.movementError * 1000.0
                        + caught.remainingCenterError * 250.0
                        + cellxx.ladderOpportunity.rotationDistance * 2.0
                        + caught.catchTick * 4.0
                        - slack * 35.0
                        + this.trajectoryCost(candidate);
                     AutoLadderPlan plan = new AutoLadderPlan(
                        AutoLadderPlan.Mode.DIRECT,
                        null,
                        cellxx.ladderTarget,
                        cellxx.catchX,
                        cellxx.catchZ,
                        caught.catchTick,
                        -1,
                        cellxx.ladderOpportunity.tick,
                        score,
                        candidate.adjustment
                     );
                     this.addPlan(plans, plan);
                  }
               }
            }
         }
      }
   }

   private List<AutoLadderPlan> findSupportPlans(AutoLadderPlanner.TrajectoryCandidate candidate, AutoLadderPlanner.OpportunityMemo memo) {
      Map<String, AutoLadderPlan> plans = new HashMap<>();
      List<AutoLadderPlanner.TrajectoryPoint> trajectory = candidate.points;

      for (AutoLadderPlanner.CatchSample catchSample : candidate.catchSamples) {
         AutoLadderPlanner.TrajectoryPoint point = catchSample.point;
         Map<EnumFacing, List<AutoLadderPlanner.CellCandidate>> facets = new LinkedHashMap<>();
         this.enumerateCatchCells(
            point,
            catchSample.ladderY,
            (ladderBlock, ladderFacing, catchX, catchZ, movementError) -> {
               if (BlockUtil.u(this.blockAt(ladderBlock))) {
                  BlockData supportBlock = ladderBlock.R(ladderFacing.getOpposite());
                  if (BlockUtil.u(this.blockAt(supportBlock))) {
                     if (!this.isUnsafeLandingSupport(candidate, supportBlock)) {
                        if (ClutchPlacementPathUtils.isPlacementSpaceClear(this.world, this.player, supportBlock)) {
                           this.fallbackSpaceCount++;
                           PlacementTarget ladderTarget = new PlacementTarget(supportBlock, ladderFacing);
                           facets.computeIfAbsent(ladderFacing, key -> new ArrayList<>())
                              .add(new AutoLadderPlanner.CellCandidate(ladderBlock, supportBlock, ladderTarget, catchX, catchZ, movementError));
                        }
                     }
                  }
               }
            }
         );

         for (Entry<EnumFacing, List<AutoLadderPlanner.CellCandidate>> entry : facets.entrySet()) {
            this.findSupportPlansForFacet(candidate, trajectory, catchSample, entry.getKey(), entry.getValue(), memo, plans);
         }
      }

      return new ArrayList<>(plans.values());
   }

   private void findSupportPlansForFacet(
      AutoLadderPlanner.TrajectoryCandidate candidate,
      List<AutoLadderPlanner.TrajectoryPoint> trajectory,
      AutoLadderPlanner.CatchSample catchSample,
      EnumFacing ladderFacing,
      List<AutoLadderPlanner.CellCandidate> cells,
      AutoLadderPlanner.OpportunityMemo memo,
      Map<String, AutoLadderPlan> plans
   ) {
      boolean anyViable = false;

      for (AutoLadderPlanner.CellCandidate cell : cells) {
         for (EnumFacing blockFacing : ALL_FACINGS) {
            BlockData anchorBlock = cell.supportBlock.R(blockFacing.getOpposite());
            if (this.isStableSupport(anchorBlock)) {
               this.fallbackAnchorCount++;
               PlacementTarget blockTarget = new PlacementTarget(anchorBlock, blockFacing);
               AutoLadderPlanner.PlacementOpportunity blockOpportunity = this.findPlacementOpportunity(
                  blockTarget, trajectory, 0, catchSample.latestPlacementTick, true, memo
               );
               if (blockOpportunity != null) {
                  this.fallbackBlockOpportunityCount++;
                  int earliestLadderTick = blockOpportunity.tick + 1;
                  if (earliestLadderTick > catchSample.latestPlacementTick) {
                     this.fallbackTimingRejectedCount++;
                  } else {
                     AutoLadderPlanner.PlacementOpportunity ladderOpportunity = this.findFutureFaceOpportunity(
                        cell.ladderTarget, trajectory, earliestLadderTick, catchSample.latestPlacementTick, memo
                     );
                     if (ladderOpportunity != null) {
                        this.fallbackLadderOpportunityCount++;
                        if (this.intersectsBlockBeforeCatchControl(trajectory, cell.supportBlock, blockOpportunity.tick, ladderOpportunity.tick)) {
                           this.fallbackCollisionRejectedCount++;
                        } else {
                           cell.anchors.add(new AutoLadderPlanner.AnchorCandidate(blockTarget, blockOpportunity, ladderOpportunity));
                           anyViable = true;
                        }
                     }
                  }
               }
            }
         }
      }

      if (anyViable) {
         cells.sort(Comparator.comparingDouble(cellx -> cellx.movementError));
         List<AutoLadderPlanner.CatchPathPoint> path = null;

         for (AutoLadderPlanner.CellCandidate cell : cells) {
            if (!cell.anchors.isEmpty()) {
               int supportExistsTick = Integer.MAX_VALUE;
               int ladderExistsTick = Integer.MAX_VALUE;

               for (AutoLadderPlanner.AnchorCandidate anchor : cell.anchors) {
                  supportExistsTick = Math.min(supportExistsTick, anchor.blockOpportunity.tick);
                  ladderExistsTick = Math.min(ladderExistsTick, anchor.ladderOpportunity.tick);
               }

               path = this.simulateControlledCatchPath(trajectory, ladderExistsTick, cell, ladderFacing, supportExistsTick, ladderExistsTick);
               if (path != null) {
                  break;
               }
            }
         }

         if (path != null) {
            for (AutoLadderPlanner.CellCandidate cellx : cells) {
               if (!cellx.anchors.isEmpty()) {
                  for (AutoLadderPlanner.AnchorCandidate anchor : cellx.anchors) {
                     AutoLadderPlanner.ControlledCatch caught = this.evaluateCatchOnPath(
                        path, cellx, ladderFacing, anchor.blockOpportunity.tick, anchor.ladderOpportunity.tick
                     );
                     if (caught == null) {
                        this.fallbackMovementRejectedCount++;
                     } else {
                        int slack = caught.catchTick - anchor.ladderOpportunity.tick;
                        double score = cellx.movementError * 1000.0
                           + caught.remainingCenterError * 250.0
                           + (anchor.blockOpportunity.rotationDistance + anchor.ladderOpportunity.rotationDistance) * 2.0
                           + caught.catchTick * 5.0
                           - slack * 30.0
                           + this.trajectoryCost(candidate);
                        AutoLadderPlan plan = new AutoLadderPlan(
                           AutoLadderPlan.Mode.BUILD_SUPPORT,
                           anchor.blockTarget,
                           cellx.ladderTarget,
                           cellx.catchX,
                           cellx.catchZ,
                           caught.catchTick,
                           anchor.blockOpportunity.tick,
                           anchor.ladderOpportunity.tick,
                           score,
                           candidate.adjustment
                        );
                        this.addPlan(plans, plan);
                     }
                  }
               }
            }
         }
      }
   }

   private double trajectoryCost(AutoLadderPlanner.TrajectoryCandidate candidate) {
      return candidate.centerError * 600.0 + (candidate.adjustment.overridesInput() ? 20.0 : 0.0);
   }

   @Nullable
   private AutoLadderPlanner.PlanSelection selectPlanEvaluation(List<AutoLadderPlanner.CandidateEvaluation> evaluations, boolean direct) {
      AutoLadderPlanner.PlanSelection selected = null;
      double selectedScore = Double.POSITIVE_INFINITY;

      for (AutoLadderPlanner.CandidateEvaluation evaluation : evaluations) {
         List<AutoLadderPlan> plans = direct ? evaluation.directPlans : evaluation.supportPlans;
         if (plans != null && !plans.isEmpty()) {
            AutoLadderPlan bestPlan = direct ? evaluation.bestDirectPlan() : evaluation.bestSupportPlan();
            double score = bestPlan.getScore() - Math.min(8, plans.size()) * 25.0;
            if (selected == null
               || score < selectedScore
               || score == selectedScore && evaluation.trajectory.centerError < selected.evaluation.trajectory.centerError) {
               selected = new AutoLadderPlanner.PlanSelection(evaluation, bestPlan);
               selectedScore = score;
            }
         }
      }

      return selected;
   }

   @Nullable
   private AutoLadderPlanner.CandidateEvaluation selectProgressEvaluation(List<AutoLadderPlanner.CandidateEvaluation> evaluations) {
      AutoLadderPlanner.CandidateEvaluation selected = null;

      for (AutoLadderPlanner.CandidateEvaluation evaluation : evaluations) {
         if (selected == null
            || evaluation.progressScore > selected.progressScore
            || evaluation.progressScore == selected.progressScore && evaluation.trajectory.centerError < selected.trajectory.centerError
            || evaluation.progressScore == selected.progressScore
               && evaluation.trajectory.centerError == selected.trajectory.centerError
               && !evaluation.trajectory.adjustment.overridesInput()
               && selected.trajectory.adjustment.overridesInput()) {
            selected = evaluation;
         }
      }

      return selected;
   }

   private void recordRecommendation(@Nullable AutoLadderPlanner.CandidateEvaluation evaluation, @Nullable AutoLadderPlan plan) {
      if (plan != null) {
         this.recommendedFallAdjustment = plan.getFallAdjustment();
      } else if (evaluation == null) {
         this.recommendedFallAdjustment = AutoLadderFallAdjustment.PHYSICAL;
      } else {
         this.recommendedFallAdjustment = evaluation.trajectory.adjustment;
      }
   }

   @Nullable
   private List<AutoLadderPlanner.CatchPathPoint> simulateControlledCatchPath(
      List<AutoLadderPlanner.TrajectoryPoint> trajectory,
      int controlStartTick,
      AutoLadderPlanner.CellCandidate cell,
      EnumFacing facing,
      int supportExistsTick,
      int ladderExistsTick
   ) {
      AutoLadderPlanner.TrajectoryPoint start = this.pointAtTick(trajectory, controlStartTick);
      if (start != null && start.snapshot != null) {
         BlockPathPlanner simulation = new BlockPathPlanner(this.player, this.player, this.world, start.snapshot);
         simulation.applySnapshot(start.snapshot);
         EntityPlayer simulatedPlayer = simulation.getSimulatedPlayer();
         List<AutoLadderPlanner.CatchPathPoint> path = new ArrayList<>();
         AutoLadderPlanner.CatchPathPoint previous = AutoLadderPlanner.CatchPathPoint.capture(
            controlStartTick, simulatedPlayer, simulatedPlayer.b$src$Z$fqlxe4()
         );
         path.add(previous);
         if (controlStartTick >= ladderExistsTick
            && this.isSafeCatchPosition(previous, cell.ladderBlock, cell.supportBlock)
            && this.verticallyOverlapsLadder(previous, cell.ladderBlock)) {
            return path;
         } else {
            double catchCenterX = cell.ladderBlock.D() + 0.5;
            double catchCenterZ = cell.ladderBlock.G() + 0.5;
            double ladderTop = cell.ladderBlock.B() + 1.0;
            AxisAlignedBB ladderBounds = AutoLadderMovementController.getExpectedLadderBounds(cell.ladderBlock, facing);
            int lastTick = Math.min(30, controlStartTick + 30);
            int tick = controlStartTick + 1;

            while (tick <= lastTick) {
               AutoLadderMovementController.CenterInput input = AutoLadderMovementController.chooseCentering(
                  simulatedPlayer, this.player, this.world, start.snapshot, catchCenterX, catchCenterZ, cell.ladderBlock, facing
               );
               simulation.setInput(input.isForward(), input.isBackward(), input.isLeft(), input.isRight(), false, false);
               simulation.simulateTick(false);
               AutoLadderPlanner.CatchPathPoint current = AutoLadderPlanner.CatchPathPoint.capture(tick, simulatedPlayer, simulatedPlayer.b$src$Z$fqlxe4());
               path.add(current);
               if (previous.tick < supportExistsTick
                  || !current.intersectsUnitBlock(cell.supportBlock, this.supportClearanceMargin())
                     && !previous.sweptIntersectsUnitBlock(current, cell.supportBlock, this.supportClearanceMargin())) {
                  if (previous.tick >= ladderExistsTick
                     && previous.y >= ladderTop
                     && current.y < ladderTop
                     && previous.topCrossingIntersects(
                        current, ladderBounds, this.ladderTopClearanceMargin(), (ladderTop - previous.y) / (current.y - previous.y)
                     )) {
                     return null;
                  }

                  if (current.onGround) {
                     return null;
                  }

                  if (current.tick >= ladderExistsTick
                     && this.isSafeCatchPosition(current, cell.ladderBlock, cell.supportBlock)
                     && this.verticallyOverlapsLadder(current, cell.ladderBlock)) {
                     return path;
                  }

                  if (!(current.y < cell.ladderBlock.B() - 0.05) && !(current.motionY >= 0.0)) {
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

   @Nullable
   private AutoLadderPlanner.ControlledCatch evaluateCatchOnPath(
      List<AutoLadderPlanner.CatchPathPoint> path, AutoLadderPlanner.CellCandidate cell, EnumFacing facing, int supportExistsTick, int ladderExistsTick
   ) {
      double ladderTop = cell.ladderBlock.B() + 1.0;
      AxisAlignedBB ladderBounds = AutoLadderMovementController.getExpectedLadderBounds(cell.ladderBlock, facing);
      AutoLadderPlanner.CatchPathPoint previous = null;

      for (AutoLadderPlanner.CatchPathPoint current : path) {
         if (previous != null) {
            if (previous.tick >= supportExistsTick
               && (
                  current.intersectsUnitBlock(cell.supportBlock, this.supportClearanceMargin())
                     || previous.sweptIntersectsUnitBlock(current, cell.supportBlock, this.supportClearanceMargin())
               )) {
               return null;
            }

            if (previous.tick >= ladderExistsTick
               && previous.y >= ladderTop
               && current.y < ladderTop
               && previous.topCrossingIntersects(current, ladderBounds, this.ladderTopClearanceMargin(), (ladderTop - previous.y) / (current.y - previous.y))) {
               return null;
            }
         }

         if (current.tick >= ladderExistsTick
            && this.isSafeCatchPosition(current, cell.ladderBlock, cell.supportBlock)
            && this.verticallyOverlapsLadder(current, cell.ladderBlock)) {
            return new AutoLadderPlanner.ControlledCatch(current.tick, this.centerError(current, cell.ladderBlock));
         }

         previous = current;
      }

      return null;
   }

   @Nullable
   private AutoLadderPlanner.TrajectoryPoint pointAtTick(List<AutoLadderPlanner.TrajectoryPoint> trajectory, int tick) {
      for (AutoLadderPlanner.TrajectoryPoint point : trajectory) {
         if (point.tick == tick) {
            return point;
         }
      }

      return null;
   }

   private boolean verticallyOverlapsLadder(AutoLadderPlanner.CatchPathPoint point, BlockData ladderBlock) {
      return point.maxY > ladderBlock.B() && point.minY < ladderBlock.B() + 1.0;
   }

   private double centerError(AutoLadderPlanner.CatchPathPoint point, BlockData ladderBlock) {
      return Math.hypot(point.x - (ladderBlock.D() + 0.5), point.z - (ladderBlock.G() + 0.5));
   }

   private boolean isSafeCatchPosition(AutoLadderPlanner.CatchPathPoint point, BlockData ladderBlock, BlockData supportBlock) {
      boolean centerInsideLadderCell = point.x >= ladderBlock.D() + 0.02
         && point.x <= ladderBlock.D() + 1.0 - 0.02
         && point.z >= ladderBlock.G() + 0.02
         && point.z <= ladderBlock.G() + 1.0 - 0.02;
      return centerInsideLadderCell && !point.horizontallyIntersectsUnitBlock(supportBlock, this.supportClearanceMargin());
   }

   private double supportClearanceMargin() {
      return AutoLadderMovementController.getSupportClearanceMargin();
   }

   private double ladderTopClearanceMargin() {
      return AutoLadderMovementController.getLadderTopClearanceMargin();
   }

   private List<AutoLadderPlanner.CatchSample> findCatchSamples(List<AutoLadderPlanner.TrajectoryPoint> trajectory) {
      Map<Long, AutoLadderPlanner.CatchSample> samples = new LinkedHashMap<>();
      int minimumLadderY = this.landingBlock.E() + 1;
      AutoLadderPlanner.TrajectoryPoint initial = trajectory.get(0);
      int initialLadderY = MathUtil.floor(initial.y + 1.0E-4);
      double initialEntryY = initialLadderY + 1.0 - 0.28;
      if (!initial.onGround && initial.motionY < 0.0 && initialLadderY >= minimumLadderY && initial.y <= initialEntryY + 1.0E-4) {
         this.addCatchSample(samples, new AutoLadderPlanner.CatchSample(initial, initial.tick, initialLadderY), false);
      }

      for (int index = 1; index < trajectory.size(); index++) {
         AutoLadderPlanner.TrajectoryPoint previous = trajectory.get(index - 1);
         AutoLadderPlanner.TrajectoryPoint current = trajectory.get(index);
         if (!(current.y >= previous.y) && (!(previous.motionY >= 0.0) || !(current.motionY >= 0.0))) {
            int highestCrossedLayer = MathUtil.floor(previous.y - 1.0 + 0.28 + 1.0E-4);
            int lowestCrossedLayer = Math.max(minimumLadderY, MathUtil.floor(current.y - 1.0 + 0.28 + 1.0E-4) + 1);

            for (int ladderY = highestCrossedLayer; ladderY >= lowestCrossedLayer; ladderY--) {
               double sideEntryY = ladderY + 1.0 - 0.28;
               if (!(previous.y < sideEntryY) && !(current.y >= sideEntryY)) {
                  AutoLadderPlanner.TrajectoryPoint sweptPoint = AutoLadderPlanner.TrajectoryPoint.interpolateAtY(previous, current, sideEntryY);
                  this.addCatchSample(samples, new AutoLadderPlanner.CatchSample(sweptPoint, previous.tick, ladderY), true);
               }
            }

            int ladderYx = MathUtil.floor(current.y + 1.0E-4);
            double sideEntryY = ladderYx + 1.0 - 0.28;
            if (!current.onGround && current.motionY < 0.0 && ladderYx >= minimumLadderY && current.y >= ladderYx && current.y <= sideEntryY + 1.0E-4) {
               this.addCatchSample(samples, new AutoLadderPlanner.CatchSample(current, current.tick, ladderYx), false);
            }
         }
      }

      return new ArrayList<>(samples.values());
   }

   private void addCatchSample(Map<Long, AutoLadderPlanner.CatchSample> samples, AutoLadderPlanner.CatchSample sample, boolean prefer) {
      long key = (long)sample.ladderY << 32 | sample.point.tick & 4294967295L;
      AutoLadderPlanner.CatchSample previous = samples.get(key);
      if (previous == null || prefer) {
         samples.put(key, sample);
      }
   }

   private void enumerateCatchCells(AutoLadderPlanner.TrajectoryPoint point, int ladderY, AutoLadderPlanner.CatchCellConsumer consumer) {
      int searchRadius = this.controlMovement ? 1 : 0;
      int baseX = MathUtil.floor(point.x);
      int baseZ = MathUtil.floor(point.z);

      for (int xOffset = -searchRadius; xOffset <= searchRadius; xOffset++) {
         for (int zOffset = -searchRadius; zOffset <= searchRadius; zOffset++) {
            BlockData ladderBlock = new BlockData(baseX + xOffset, ladderY, baseZ + zOffset);
            this.catchGeometryCount++;

            for (EnumFacing facing : HORIZONTAL_FACINGS) {
               double catchX = ladderBlock.D() + 0.5;
               double catchZ = ladderBlock.G() + 0.5;
               double movementError = Math.hypot(catchX - point.x, catchZ - point.z);
               if (!(movementError > 0.67)) {
                  this.catchGeometryCount++;
                  consumer.accept(ladderBlock, facing, catchX, catchZ, movementError);
               }
            }
         }
      }
   }

   @Nullable
   private AutoLadderPlanner.PlacementOpportunity findPlacementOpportunity(
      PlacementTarget target,
      List<AutoLadderPlanner.TrajectoryPoint> trajectory,
      int earliestTick,
      int latestTick,
      boolean placingSolidBlock,
      AutoLadderPlanner.OpportunityMemo memo
   ) {
      if (latestTick < earliestTick) {
         return null;
      } else if (earliestTick != 0) {
         return this.scanPlacementOpportunity(target, trajectory, earliestTick, latestTick, placingSolidBlock);
      } else {
         Map<AutoLadderPlanner.OpportunityKey, AutoLadderPlanner.HitPointMemo> memoMap = placingSolidBlock ? memo.solidHitPoints : memo.nonSolidHitPoints;
         AutoLadderPlanner.OpportunityKey key = new AutoLadderPlanner.OpportunityKey(target.supportBlock, target.facing);
         AutoLadderPlanner.HitPointMemo hitPoint = memoMap.get(key);
         if (hitPoint == null) {
            hitPoint = this.computeFirstHitPointOpportunity(target, trajectory, placingSolidBlock);
            memoMap.put(key, hitPoint);
         }

         return hitPoint.firstValidTick >= 0 && hitPoint.firstValidTick <= latestTick
            ? new AutoLadderPlanner.PlacementOpportunity(hitPoint.firstValidTick, hitPoint.firstValidRotationDistance)
            : null;
      }
   }

   @Nullable
   private AutoLadderPlanner.PlacementOpportunity scanPlacementOpportunity(
      PlacementTarget target, List<AutoLadderPlanner.TrajectoryPoint> trajectory, int earliestTick, int latestTick, boolean placingSolidBlock
   ) {
      for (AutoLadderPlanner.TrajectoryPoint point : trajectory) {
         if (point.tick >= earliestTick) {
            if (point.tick > latestTick) {
               break;
            }

            if (!placingSolidBlock || !point.intersectsUnitBlock(target.getPlacedBlock())) {
               Vec3 eye = point.eyePosition();
               if (ClutchPlacementPathUtils.isBlockFaceVisible(eye, this.world, target.supportBlock, target.facing)
                  && this.isFaceWithinReach(eye, target.supportBlock, target.facing)) {
                  Vec3 hit = ClutchPlacementPathUtils.findBestPlacementHitPointWithinReach(
                     this.player, this.world, eye, target, point.yaw, point.pitch, this.reach
                  );
                  if (hit != null) {
                     return new AutoLadderPlanner.PlacementOpportunity(point.tick, this.rotationDistance(eye, hit, point.yaw, point.pitch));
                  }
               }
            }
         }
      }

      return null;
   }

   private AutoLadderPlanner.HitPointMemo computeFirstHitPointOpportunity(
      PlacementTarget target, List<AutoLadderPlanner.TrajectoryPoint> trajectory, boolean placingSolidBlock
   ) {
      AutoLadderPlanner.HitPointMemo result = new AutoLadderPlanner.HitPointMemo();

      for (AutoLadderPlanner.TrajectoryPoint point : trajectory) {
         if (!placingSolidBlock || !point.intersectsUnitBlock(target.getPlacedBlock())) {
            Vec3 eye = point.eyePosition();
            if (ClutchPlacementPathUtils.isBlockFaceVisible(eye, this.world, target.supportBlock, target.facing)
               && this.isFaceWithinReach(eye, target.supportBlock, target.facing)) {
               Vec3 hit = ClutchPlacementPathUtils.findBestPlacementHitPointWithinReach(
                  this.player, this.world, eye, target, point.yaw, point.pitch, this.reach
               );
               if (hit != null) {
                  result.firstValidTick = point.tick;
                  result.firstValidRotationDistance = this.rotationDistance(eye, hit, point.yaw, point.pitch);
                  break;
               }
            }
         }
      }

      return result;
   }

   private boolean isFaceWithinReach(Vec3 eye, BlockData block, EnumFacing facing) {
      if (facing == null) {
         return true;
      } else {
         double directionX = facing.getDirectionVector().getX();
         double directionY = facing.getDirectionVector().getY();
         double directionZ = facing.getDirectionVector().getZ();
         double eyeX = eye.getX();
         double eyeY = eye.getY();
         double eyeZ = eye.getZ();
         double blockMinX = block.D();
         double blockMinY = block.B();
         double blockMinZ = block.G();
         double dx;
         double dy;
         double dz;
         if (directionX != 0.0) {
            dx = eyeX - (directionX > 0.0 ? blockMinX + 1.0 : blockMinX);
            dy = Math.max(blockMinY - eyeY, Math.max(0.0, eyeY - (blockMinY + 1.0)));
            dz = Math.max(blockMinZ - eyeZ, Math.max(0.0, eyeZ - (blockMinZ + 1.0)));
         } else if (directionZ != 0.0) {
            dz = eyeZ - (directionZ > 0.0 ? blockMinZ + 1.0 : blockMinZ);
            dx = Math.max(blockMinX - eyeX, Math.max(0.0, eyeX - (blockMinX + 1.0)));
            dy = Math.max(blockMinY - eyeY, Math.max(0.0, eyeY - (blockMinY + 1.0)));
         } else {
            dy = eyeY - (directionY > 0.0 ? blockMinY + 1.0 : blockMinY);
            dx = Math.max(blockMinX - eyeX, Math.max(0.0, eyeX - (blockMinX + 1.0)));
            dz = Math.max(blockMinZ - eyeZ, Math.max(0.0, eyeZ - (blockMinZ + 1.0)));
         }

         double distanceLimit = this.reach + 1.0E-4;
         return dx * dx + dy * dy + dz * dz <= distanceLimit * distanceLimit;
      }
   }

   @Nullable
   private AutoLadderPlanner.PlacementOpportunity findFutureFaceOpportunity(
      PlacementTarget target, List<AutoLadderPlanner.TrajectoryPoint> trajectory, int earliestTick, int latestTick, AutoLadderPlanner.OpportunityMemo memo
   ) {
      if (latestTick < earliestTick) {
         return null;
      } else {
         AutoLadderPlanner.OpportunityKey key = new AutoLadderPlanner.OpportunityKey(target.supportBlock, target.facing);
         int[] validTicks = memo.faceCenterTicks.get(key);
         if (validTicks == null) {
            validTicks = this.computeFaceCenterValidTicks(target, trajectory);
            memo.faceCenterTicks.put(key, validTicks);
         }

         int index = lowerBound(validTicks, earliestTick);
         if (index < validTicks.length && validTicks[index] <= latestTick) {
            int tick = validTicks[index];
            AutoLadderPlanner.TrajectoryPoint point = this.pointAtTick(trajectory, tick);
            if (point == null) {
               return null;
            } else {
               Vec3 eye = point.eyePosition();
               Vec3 faceCenter = this.faceCenter(target.supportBlock, target.facing);
               return new AutoLadderPlanner.PlacementOpportunity(tick, this.rotationDistance(eye, faceCenter, point.yaw, point.pitch));
            }
         } else {
            return null;
         }
      }
   }

   private int[] computeFaceCenterValidTicks(PlacementTarget target, List<AutoLadderPlanner.TrajectoryPoint> trajectory) {
      Vec3 faceCenter = this.faceCenter(target.supportBlock, target.facing);
      List<Integer> valid = new ArrayList<>();

      for (AutoLadderPlanner.TrajectoryPoint point : trajectory) {
         Vec3 eye = point.eyePosition();
         if (ClutchPlacementPathUtils.isBlockFaceVisible(eye, this.world, target.supportBlock, target.facing)
            && !(eye.distanceTo(faceCenter) > this.reach)
            && this.isFutureFaceUnobstructed(eye, faceCenter)) {
            valid.add(point.tick);
         }
      }

      int[] result = new int[valid.size()];

      for (int index = 0; index < valid.size(); index++) {
         result[index] = valid.get(index);
      }

      return result;
   }

   private static int lowerBound(int[] array, int value) {
      int low = 0;
      int high = array.length;

      while (low < high) {
         int mid = low + high >>> 1;
         if (array[mid] < value) {
            low = mid + 1;
         } else {
            high = mid;
         }
      }

      return low;
   }

   private double rotationDistance(Vec3 eye, Vec3 hit, float yaw, float pitch) {
      RotationAngles rotation = RotationVectorMath.d(eye, hit, yaw, pitch);
      return Math.abs(MathUtil.wrapAngleTo180(rotation.getYaw() - yaw)) + Math.abs(rotation.getPitch() - pitch);
   }

   private Vec3 faceCenter(BlockData block, EnumFacing facing) {
      double x = block.D() + 0.5 + facing.getDirectionVector().getX() * 0.5;
      double y = block.B() + 0.5 + facing.getDirectionVector().getY() * 0.5;
      double z = block.G() + 0.5 + facing.getDirectionVector().getZ() * 0.5;
      return Vec3.create(x, y, z);
   }

   private boolean intersectsBlockBeforeCatchControl(List<AutoLadderPlanner.TrajectoryPoint> trajectory, BlockData block, int firstTick, int lastTick) {
      AutoLadderPlanner.TrajectoryPoint previous = null;

      for (AutoLadderPlanner.TrajectoryPoint point : trajectory) {
         if (point.tick >= firstTick - 1) {
            if (point.tick <= lastTick) {
               if (point.tick < firstTick
                  || !point.intersectsUnitBlock(block, this.supportClearanceMargin())
                     && (previous == null || !previous.sweptIntersectsUnitBlock(point, block, this.supportClearanceMargin()))) {
                  previous = point;
                  continue;
               }

               return true;
            }
            break;
         }
      }

      return false;
   }

   private boolean isUnsafeLandingSupport(AutoLadderPlanner.TrajectoryCandidate candidate, BlockData supportBlock) {
      AutoLadderPlanner.TrajectoryPoint landingPoint = candidate.points.get(candidate.points.size() - 1);
      return supportBlock.B() == MathUtil.floor(landingPoint.y + 1.0E-4)
         && landingPoint.horizontallyIntersectsUnitBlock(supportBlock, this.supportClearanceMargin());
   }

   private boolean isFutureFaceUnobstructed(Vec3 eye, Vec3 faceCenter) {
      RayTraceResult rayTrace = this.world.K(eye, faceCenter, false, false, ForgeVersion.MC_1_16_5.v(), this.player);
      return rayTrace.isNull() || !rayTrace.isBlockHit();
   }

   private void addPlan(Map<String, AutoLadderPlan> plans, AutoLadderPlan plan) {
      String key = plan.rejectionKey();
      if (!this.rejectedPlans.contains(key)) {
         AutoLadderPlan previous = plans.get(key);
         if (previous == null || plan.getScore() < previous.getScore()) {
            plans.put(key, plan);
         }
      }
   }

   private Block blockAt(BlockData block) {
      return this.world.getBlockByPos(block.D(), block.B(), block.G());
   }

   private boolean isLadder(Block block) {
      return block.isNotNull() && block.equals(Blocks.ladder());
   }

   private boolean isStableSupport(BlockData blockData) {
      Block block = this.blockAt(blockData);
      return block.isNotNull() && BlockUtil.k(block) && !ClutchPlacementPathUtils.isBlacklistedPlacementBlock(block);
   }

   private static final class AnchorCandidate {
      private final PlacementTarget blockTarget;
      private final AutoLadderPlanner.PlacementOpportunity blockOpportunity;
      private final AutoLadderPlanner.PlacementOpportunity ladderOpportunity;

      private AnchorCandidate(
         PlacementTarget blockTarget, AutoLadderPlanner.PlacementOpportunity blockOpportunity, AutoLadderPlanner.PlacementOpportunity ladderOpportunity
      ) {
         this.blockTarget = blockTarget;
         this.blockOpportunity = blockOpportunity;
         this.ladderOpportunity = ladderOpportunity;
      }
   }

   private static final class CandidateEvaluation {
      private final AutoLadderPlanner.TrajectoryCandidate trajectory;
      private final AutoLadderPlanner.OpportunityMemo memo;
      private List<AutoLadderPlan> directPlans = new ArrayList<>();
      private List<AutoLadderPlan> supportPlans = new ArrayList<>();
      private int progressScore;

      private CandidateEvaluation(AutoLadderPlanner.TrajectoryCandidate trajectory, AutoLadderPlanner.OpportunityMemo memo) {
         this.trajectory = trajectory;
         this.memo = memo;
      }

      private AutoLadderPlan bestDirectPlan() {
         return this.directPlans.stream().min(Comparator.comparingDouble(AutoLadderPlan::getScore)).orElse(null);
      }

      private AutoLadderPlan bestSupportPlan() {
         return this.supportPlans.stream().min(Comparator.comparingDouble(AutoLadderPlan::getScore)).orElse(null);
      }
   }

   private interface CatchCellConsumer {
      void accept(BlockData var1, EnumFacing var2, double var3, double var5, double var7);
   }

   private static final class CatchPathPoint {
      private final int tick;
      private final double x;
      private final double y;
      private final double z;
      private final double motionY;
      private final double minX;
      private final double minY;
      private final double minZ;
      private final double maxX;
      private final double maxY;
      private final double maxZ;
      private final boolean onGround;

      private CatchPathPoint(int tick, double x, double y, double z, double motionY, AxisAlignedBB bounds, boolean onGround) {
         this.tick = tick;
         this.x = x;
         this.y = y;
         this.z = z;
         this.motionY = motionY;
         this.minX = bounds.getMinX();
         this.minY = bounds.getMinY();
         this.minZ = bounds.getMinZ();
         this.maxX = bounds.getMaxX();
         this.maxY = bounds.getMaxY();
         this.maxZ = bounds.getMaxZ();
         this.onGround = onGround;
      }

      private static AutoLadderPlanner.CatchPathPoint capture(int tick, EntityPlayer player, boolean onGround) {
         return new AutoLadderPlanner.CatchPathPoint(
            tick, player.z(), player.N(), player.h(), player.q(), player.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu(), onGround
         );
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

      private boolean sweptIntersectsUnitBlock(AutoLadderPlanner.CatchPathPoint next, BlockData block, double horizontalMargin) {
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

      private boolean topCrossingIntersects(AutoLadderPlanner.CatchPathPoint end, AxisAlignedBB bounds, double margin, double progress) {
         double minX = lerp(this.minX, end.minX, progress) - margin;
         double maxX = lerp(this.maxX, end.maxX, progress) + margin;
         double minZ = lerp(this.minZ, end.minZ, progress) - margin;
         double maxZ = lerp(this.maxZ, end.maxZ, progress) + margin;
         return maxX > bounds.getMinX() && minX < bounds.getMaxX() && maxZ > bounds.getMinZ() && minZ < bounds.getMaxZ();
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

      private static double lerp(double start, double end, double progress) {
         return start + (end - start) * progress;
      }
   }

   private static final class CatchSample {
      private final AutoLadderPlanner.TrajectoryPoint point;
      private final int latestPlacementTick;
      private final int ladderY;

      private CatchSample(AutoLadderPlanner.TrajectoryPoint point, int latestPlacementTick, int ladderY) {
         this.point = point;
         this.latestPlacementTick = latestPlacementTick;
         this.ladderY = ladderY;
      }
   }

   private static final class CellCandidate {
      private final BlockData ladderBlock;
      private final BlockData supportBlock;
      private final PlacementTarget ladderTarget;
      private final double catchX;
      private final double catchZ;
      private final double movementError;
      private AutoLadderPlanner.PlacementOpportunity ladderOpportunity;
      private final List<AutoLadderPlanner.AnchorCandidate> anchors = new ArrayList<>();

      private CellCandidate(BlockData ladderBlock, BlockData supportBlock, PlacementTarget ladderTarget, double catchX, double catchZ, double movementError) {
         this.ladderBlock = ladderBlock;
         this.supportBlock = supportBlock;
         this.ladderTarget = ladderTarget;
         this.catchX = catchX;
         this.catchZ = catchZ;
         this.movementError = movementError;
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

   private static final class CounterSnapshot {
      private final int catchGeometry;
      private final int existingLadders;
      private final int directSupports;
      private final int directOpportunities;
      private final int directLadderTopRejects;
      private final int fallbackSpaces;
      private final int fallbackAnchors;
      private final int fallbackBlockOpportunities;
      private final int fallbackLadderOpportunities;
      private final int fallbackPreControlRejects;
      private final int fallbackTimingRejects;
      private final int fallbackMovementRejects;
      private final int fallbackControlledRejects;
      private final int fallbackLadderTopRejects;

      private CounterSnapshot(AutoLadderPlanner planner) {
         this.catchGeometry = planner.catchGeometryCount;
         this.existingLadders = planner.existingLadderCount;
         this.directSupports = planner.directSupportCount;
         this.directOpportunities = planner.directOpportunityCount;
         this.directLadderTopRejects = planner.directLadderTopRejectedCount;
         this.fallbackSpaces = planner.fallbackSpaceCount;
         this.fallbackAnchors = planner.fallbackAnchorCount;
         this.fallbackBlockOpportunities = planner.fallbackBlockOpportunityCount;
         this.fallbackLadderOpportunities = planner.fallbackLadderOpportunityCount;
         this.fallbackPreControlRejects = planner.fallbackCollisionRejectedCount;
         this.fallbackTimingRejects = planner.fallbackTimingRejectedCount;
         this.fallbackMovementRejects = planner.fallbackMovementRejectedCount;
         this.fallbackControlledRejects = planner.fallbackControlledCollisionRejectedCount;
         this.fallbackLadderTopRejects = planner.fallbackLadderTopRejectedCount;
      }

      private int progressSince(AutoLadderPlanner planner) {
         int catchGeometryDelta = planner.catchGeometryCount - this.catchGeometry;
         int existingDelta = planner.existingLadderCount - this.existingLadders;
         int directSupportDelta = planner.directSupportCount - this.directSupports;
         int directOpportunityDelta = planner.directOpportunityCount - this.directOpportunities;
         int directLadderTopRejectDelta = planner.directLadderTopRejectedCount - this.directLadderTopRejects;
         int fallbackSpaceDelta = planner.fallbackSpaceCount - this.fallbackSpaces;
         int fallbackAnchorDelta = planner.fallbackAnchorCount - this.fallbackAnchors;
         int fallbackBlockDelta = planner.fallbackBlockOpportunityCount - this.fallbackBlockOpportunities;
         int fallbackLadderDelta = planner.fallbackLadderOpportunityCount - this.fallbackLadderOpportunities;
         int rejectionDelta = planner.fallbackCollisionRejectedCount
            - this.fallbackPreControlRejects
            + directLadderTopRejectDelta
            + planner.fallbackTimingRejectedCount
            - this.fallbackTimingRejects
            + planner.fallbackMovementRejectedCount
            - this.fallbackMovementRejects
            + planner.fallbackControlledCollisionRejectedCount
            - this.fallbackControlledRejects
            + planner.fallbackLadderTopRejectedCount
            - this.fallbackLadderTopRejects;
         int viableLadderWindows = Math.max(0, fallbackLadderDelta - rejectionDelta);
         return viableLadderWindows * 1000
            + (existingDelta + directOpportunityDelta) * 800
            + fallbackLadderDelta * 120
            + fallbackBlockDelta * 30
            + fallbackAnchorDelta * 8
            + fallbackSpaceDelta * 4
            + directSupportDelta * 4
            + catchGeometryDelta
            - rejectionDelta * 5;
      }
   }

   private static final class HitPointMemo {
      private int firstValidTick = -1;
      private double firstValidRotationDistance;

      private HitPointMemo() {
      }
   }

   private static final class OpportunityKey {
      private final BlockData supportBlock;
      private final EnumFacing facing;

      private OpportunityKey(BlockData supportBlock, EnumFacing facing) {
         this.supportBlock = supportBlock;
         this.facing = facing;
      }

      @Override
      public boolean equals(Object object) {
         if (this == object) {
            return true;
         } else if (!(object instanceof AutoLadderPlanner.OpportunityKey)) {
            return false;
         } else {
            AutoLadderPlanner.OpportunityKey other = (AutoLadderPlanner.OpportunityKey)object;
            return this.supportBlock.equals(other.supportBlock) && this.facing.Y() == other.facing.Y();
         }
      }

      @Override
      public int hashCode() {
         return this.supportBlock.hashCode() * 31 + this.facing.Y();
      }
   }

   private static final class OpportunityMemo {
      private final Map<AutoLadderPlanner.OpportunityKey, AutoLadderPlanner.HitPointMemo> solidHitPoints = new HashMap<>();
      private final Map<AutoLadderPlanner.OpportunityKey, AutoLadderPlanner.HitPointMemo> nonSolidHitPoints = new HashMap<>();
      private final Map<AutoLadderPlanner.OpportunityKey, int[]> faceCenterTicks = new HashMap<>();

      private OpportunityMemo() {
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

   private static final class PlanSelection {
      private final AutoLadderPlanner.CandidateEvaluation evaluation;
      private final AutoLadderPlan plan;

      private PlanSelection(AutoLadderPlanner.CandidateEvaluation evaluation, AutoLadderPlan plan) {
         this.evaluation = evaluation;
         this.plan = plan;
      }
   }

   private static final class TrajectoryCandidate {
      private final List<AutoLadderPlanner.TrajectoryPoint> points;
      private final List<AutoLadderPlanner.CatchSample> catchSamples;
      private final AutoLadderFallAdjustment adjustment;
      private final double centerError;

      private TrajectoryCandidate(
         List<AutoLadderPlanner.TrajectoryPoint> points,
         List<AutoLadderPlanner.CatchSample> catchSamples,
         AutoLadderFallAdjustment adjustment,
         double centerError
      ) {
         this.points = points;
         this.catchSamples = catchSamples;
         this.adjustment = adjustment;
         this.centerError = centerError;
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

      private static AutoLadderPlanner.TrajectoryPoint capture(int tick, EntityPlayer player, boolean onGround, BlockPlacementGraph snapshot) {
         AxisAlignedBB bounds = player.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu();
         return new AutoLadderPlanner.TrajectoryPoint(
            tick, player.z(), player.N(), player.h(), player.N() + player.X(), player.q(), player.J(), player.V(), bounds, onGround, snapshot
         );
      }

      private static AutoLadderPlanner.TrajectoryPoint interpolateAtY(
         AutoLadderPlanner.TrajectoryPoint start, AutoLadderPlanner.TrajectoryPoint end, double targetY
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
         return new AutoLadderPlanner.TrajectoryPoint(end.tick, x, targetY, z, eyeY, motionY, yaw, pitch, bounds, false, end.snapshot);
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

      private boolean sweptIntersectsUnitBlock(AutoLadderPlanner.TrajectoryPoint next, BlockData block, double horizontalMargin) {
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
