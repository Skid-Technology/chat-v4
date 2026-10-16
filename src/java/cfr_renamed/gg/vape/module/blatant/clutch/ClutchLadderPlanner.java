/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.blatant.clutch;

import gg.vape.config.ClientSettings;
import gg.vape.module.blatant.Clutch;
import gg.vape.module.blatant.autoladder.AutoLadderMovementController;
import gg.vape.module.blatant.blockin.BlockPathPlanner;
import gg.vape.module.blatant.blockin.BlockPlacementGraph;
import gg.vape.module.blatant.clutch.ClutchLadderPlan;
import gg.vape.module.blatant.clutch.ClutchSearchPlanner;
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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public final class ClutchLadderPlanner {
    private final static double LADDER_SIDE_ENTRY_DEPTH = 0.28;
    private final static double CATCH_CELL_INSET = 0.02;
    private final static double SUPPORT_CLEARANCE_MARGIN = 0.04;
    private final static double CATCH_CANDIDATE_RADIUS = 0.67;
    private final static double LADDER_TOP_CLEARANCE_MARGIN = 0.002;
    private final static int MAX_SIMULATION_TICKS = 20;
    private final static int MAX_EXTENSION_BLOCKS = 6;
    private final static int MAX_REACHABLE_SUPPORTS = 12;
    private final static EnumFacing[] HORIZONTAL_FACINGS = EnumFacing.c$src$ALgg_vape_wrapper_impl_EnumFacing_$1i3g4ft();
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
        this.physicalInput = new boolean[]{ClientSettings.isPhysicalKeyDown(settings.Y()), ClientSettings.isPhysicalKeyDown(settings.s()), ClientSettings.isPhysicalKeyDown(settings.x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg()), ClientSettings.isPhysicalKeyDown(settings.g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3())};
    }

    public String getFailureReason() {
        return this.failureReason;
    }

    public ClutchLadderPlan findPlan() {
        this.failureReason = null;
        List<TrajectoryPoint> trajectory = this.simulateTrajectory();
        if (trajectory.size() < 2) {
            this.failureReason = "Not enough time for a ladder rescue";
            return null;
        }
        List<CatchSample> catchSamples = this.findCatchSamples(trajectory);
        if (catchSamples.isEmpty()) {
            this.failureReason = "No ladder catch window in the fall trajectory";
            return null;
        }
        ArrayList<ClutchLadderPlan> plans = new ArrayList<ClutchLadderPlan>();
        for (CatchSample sample : catchSamples) {
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
        }
        plans.sort(Comparator.comparingDouble(ClutchLadderPlan::getScore));
        return (ClutchLadderPlan)plans.get(0);
    }

    private List<TrajectoryPoint> simulateTrajectory() {
        BlockPathPlanner simulation = new BlockPathPlanner(this.player, this.player, this.world, this.graph);
        simulation.applySnapshot(this.graph);
        this.applyPhysicalInput(simulation);
        EntityPlayer simulatedPlayer = simulation.getSimulatedPlayer();
        int worldBottom = ForgeVersion.MC_1_20_6.d() ? this.world.R() : 0;
        ArrayList<TrajectoryPoint> points = new ArrayList<TrajectoryPoint>();
        for (int tick = 0; tick <= 20; ++tick) {
            boolean onGround = simulatedPlayer.b$src$Z$fqlxe4();
            points.add(TrajectoryPoint.capture(tick, simulatedPlayer, onGround, new BlockPlacementGraph(simulation)));
            if (tick > 0 && onGround || simulatedPlayer.N() <= (double)worldBottom) break;
            simulation.simulateTick();
            if (tick != 0) continue;
            this.applyPhysicalInput(simulation);
        }
        return points;
    }

    private void applyPhysicalInput(BlockPathPlanner simulation) {
        simulation.setInput(this.physicalInput[0], this.physicalInput[1], this.physicalInput[2], this.physicalInput[3], false, false);
    }

    private List<CatchSample> findCatchSamples(List<TrajectoryPoint> trajectory) {
        LinkedHashMap<Long, CatchSample> samples = new LinkedHashMap<Long, CatchSample>();
        int minimumLadderY = this.minimumCatchLadderY(trajectory);
        TrajectoryPoint initial = trajectory.get(0);
        int initialLadderY = MathUtil.floor(initial.var_double_y + 1.0E-4);
        double initialEntryY = (double)initialLadderY + 1.0 - 0.28;
        if (!initial.onGround && initial.motionY < 0.0 && initialLadderY >= minimumLadderY && initial.var_double_y <= initialEntryY + 1.0E-4) {
            this.addCatchSample(samples, new CatchSample(initial, initial.tick, initialLadderY, false), false);
        }
        for (int index = 1; index < trajectory.size(); ++index) {
            double sideEntryY;
            int ladderY;
            TrajectoryPoint previous = trajectory.get(index - 1);
            TrajectoryPoint current = trajectory.get(index);
            if (current.var_double_y >= previous.var_double_y || previous.motionY >= 0.0 && current.motionY >= 0.0) continue;
            int highestCrossedLayer = MathUtil.floor(previous.var_double_y - 1.0 + 0.28 + 1.0E-4);
            int lowestCrossedLayer = Math.max(minimumLadderY, MathUtil.floor(current.var_double_y - 1.0 + 0.28 + 1.0E-4) + 1);
            for (ladderY = highestCrossedLayer; ladderY >= lowestCrossedLayer; --ladderY) {
                sideEntryY = (double)ladderY + 1.0 - 0.28;
                if (previous.var_double_y < sideEntryY || current.var_double_y >= sideEntryY) continue;
                TrajectoryPoint sweptPoint = TrajectoryPoint.interpolateAtY(previous, current, sideEntryY);
                this.addCatchSample(samples, new CatchSample(sweptPoint, previous.tick, ladderY, true), true);
            }
            ladderY = MathUtil.floor(current.var_double_y + 1.0E-4);
            sideEntryY = (double)ladderY + 1.0 - 0.28;
            if (current.onGround || !(current.motionY < 0.0) || ladderY < minimumLadderY || !(current.var_double_y >= (double)ladderY) || !(current.var_double_y <= sideEntryY + 1.0E-4)) continue;
            this.addCatchSample(samples, new CatchSample(current, current.tick, ladderY, false), false);
        }
        return new ArrayList<CatchSample>(samples.values());
    }

    private int minimumCatchLadderY(List<TrajectoryPoint> trajectory) {
        TrajectoryPoint last = trajectory.get(trajectory.size() - 1);
        return last.onGround ? MathUtil.floor(last.var_double_y + 1.0E-4) : 0;
    }

    private void addCatchSample(Map<Long, CatchSample> samples, CatchSample sample, boolean prefer) {
        long key = (long)sample.ladderY << 32 | (long)sample.point.tick & 0xFFFFFFFFL;
        CatchSample previous = samples.get(key);
        if (previous != null && !prefer) {
            return;
        }
        samples.put(key, sample);
    }

    private void enumerateCatchCells(CatchSample sample, CatchCellConsumer consumer) {
        int searchRadius = 1;
        int baseX = MathUtil.floor(sample.point.var_double_x);
        int baseZ = MathUtil.floor(sample.point.var_double_z);
        for (int xOffset = -searchRadius; xOffset <= searchRadius; ++xOffset) {
            for (int zOffset = -searchRadius; zOffset <= searchRadius; ++zOffset) {
                BlockData ladderBlock = new BlockData(baseX + xOffset, sample.ladderY, baseZ + zOffset);
                for (EnumFacing facing : HORIZONTAL_FACINGS) {
                    double catchX = (double)ladderBlock.D() + 0.5;
                    double catchZ = (double)ladderBlock.G() + 0.5;
                    double movementError = Math.hypot(catchX - sample.point.var_double_x, catchZ - sample.point.var_double_z);
                    if (movementError > 0.67) continue;
                    consumer.accept(ladderBlock, facing, catchX, catchZ, movementError);
                }
            }
        }
    }

    private ClutchLadderPlan buildPlan(CatchSample sample, BlockData ladderBlock, EnumFacing facing, double catchX, double catchZ, double movementError, List<TrajectoryPoint> trajectory) {
        if (!BlockUtil.u(this.blockAt(ladderBlock))) {
            return null;
        }
        BlockData supportBlock = ladderBlock.R(facing.getOpposite());
        Vector<PlacementTarget> targets = new Vector<PlacementTarget>();
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
            PlacementOpportunity opportunity = this.findPlacementOpportunity(target, trajectory, tickCursor, sample.latestPlacementTick, !target.ladderPlacement);
            if (opportunity == null) {
                return null;
            }
            tickCursor = opportunity.tick + 1;
            rotationTotal += opportunity.rotationDistance;
            ladderPlacementTick = opportunity.tick;
        }
        ControlledCatch controlledCatch = this.simulateControlledCatch(trajectory, ladderPlacementTick, ladderBlock, supportBlock);
        if (controlledCatch == null) {
            return null;
        }
        int slack = controlledCatch.catchTick - ladderPlacementTick;
        double score = movementError * 1000.0 + controlledCatch.remainingCenterError * 250.0 + rotationTotal * 2.0 + (double)controlledCatch.catchTick * 4.0 - (double)slack * 30.0 + (double)targets.size() * 5.0;
        return new ClutchLadderPlan(targets, ladderBlock, facing, catchX, catchZ, controlledCatch.catchTick, score);
    }

    private Vector<PlacementTarget> findExtensionPath(BlockData anchorBlock, List<TrajectoryPoint> trajectory, int latestPlacementTick) {
        int maxDepth = Math.min(Math.max(0, latestPlacementTick - 2), 6);
        if (maxDepth == 0) {
            return null;
        }
        List<BlockData> starts = this.findReachableSupports(trajectory);
        Vector<PlacementTarget> best = null;
        double bestScore = Double.MAX_VALUE;
        Iterator<BlockData> iterator = starts.iterator();
        while (iterator.hasNext()) {
            double score;
            ClutchSearchPlanner planner = new ClutchSearchPlanner(new LadderExtensionStrategy(maxDepth));
            BlockData start = iterator.next();
            Vector<PlacementTarget> path = planner.findPath(start, anchorBlock);
            if (path == null || path.isEmpty() || path.size() > maxDepth || !path.lastElement().getPlacedBlock().equals(anchorBlock) || !((score = (double)path.size() * 100.0 + (double)Math.abs(start.B() - anchorBlock.B()) * 3.0) < bestScore)) continue;
            bestScore = score;
            best = path;
        }
        return best;
    }

    private List<BlockData> findReachableSupports(List<TrajectoryPoint> trajectory) {
        TrajectoryPoint start = trajectory.get(0);
        int playerX = MathUtil.floor(start.var_double_x);
        int playerZ = MathUtil.floor(start.var_double_z);
        int playerY = MathUtil.floor(start.var_double_y);
        HashMap<Long, BlockData> cache = new HashMap<Long, BlockData>();
        ArrayList<BlockData> candidates = new ArrayList<BlockData>();
        for (int yOffset = -1; yOffset <= 3; ++yOffset) {
            for (int xOffset = -4; xOffset <= 4; ++xOffset) {
                for (int zOffset = -4; zOffset <= 4; ++zOffset) {
                    BlockData block2 = this.cachedBlock(cache, playerX + xOffset, playerY + yOffset, playerZ + zOffset);
                    if (!this.isStableSupport(block2) || this.clutch.getPlacedBlocks().Y(block2)) continue;
                    candidates.add(block2);
                }
            }
        }
        candidates.sort(Comparator.comparingDouble(block -> Math.hypot((double)block.D() + 0.5 - start.var_double_x, (double)block.G() + 0.5 - start.var_double_z)));
        return candidates.size() > 12 ? candidates.subList(0, 12) : candidates;
    }

    private BlockData cachedBlock(Map<Long, BlockData> cache, int x, int y, int z) {
        long key = (long)x << 32 ^ (long)y << 16 ^ (long)z;
        return cache.computeIfAbsent(key, ignored -> new BlockData(x, y, z));
    }

    private PlacementOpportunity findPlacementOpportunity(PlacementTarget target, List<TrajectoryPoint> trajectory, int earliestTick, int latestTick, boolean placingSolidBlock) {
        if (latestTick < earliestTick) {
            return null;
        }
        PlacementOpportunity best = null;
        for (TrajectoryPoint point : trajectory) {
            Vec3 hit;
            Vec3 eye;
            if (point.tick < earliestTick) continue;
            if (point.tick > latestTick) break;
            if (placingSolidBlock && point.intersectsUnitBlock(target.getPlacedBlock()) || placingSolidBlock && !ClutchPlacementPathUtils.isPlacementSpaceClear(this.world, this.player, target.getPlacedBlock()) || !ClutchPlacementPathUtils.isBlockFaceVisible(eye = point.eyePosition(), this.world, target.supportBlock, target.facing) || (hit = ClutchPlacementPathUtils.findBestPlacementHitPointWithinReach(this.player, this.world, eye, target, point.yaw, point.pitch, this.reach)) == null) continue;
            double rotationDistance = this.rotationDistance(eye, hit, point.yaw, point.pitch);
            PlacementOpportunity candidate = new PlacementOpportunity(point.tick, rotationDistance);
            if (best != null && candidate.tick >= best.tick && (candidate.tick != best.tick || !(candidate.rotationDistance < best.rotationDistance))) continue;
            best = candidate;
        }
        return best;
    }

    private double rotationDistance(Vec3 eye, Vec3 hit, float yaw, float pitch) {
        RotationAngles rotation = RotationVectorMath.d(eye, hit, yaw, pitch);
        return Math.abs(MathUtil.wrapAngleTo180(rotation.getYaw() - yaw)) + Math.abs(rotation.getPitch() - pitch);
    }

    private ControlledCatch simulateControlledCatch(List<TrajectoryPoint> trajectory, int controlStartTick, BlockData ladderBlock, BlockData supportBlock) {
        TrajectoryPoint start = this.pointAtTick(trajectory, controlStartTick);
        if (start == null || start.snapshot == null) {
            return null;
        }
        BlockPathPlanner simulation = new BlockPathPlanner(this.player, this.player, this.world, start.snapshot);
        simulation.applySnapshot(start.snapshot);
        EntityPlayer simulatedPlayer = simulation.getSimulatedPlayer();
        TrajectoryPoint previous = TrajectoryPoint.capture(controlStartTick, simulatedPlayer, simulatedPlayer.b$src$Z$fqlxe4(), new BlockPlacementGraph(simulation));
        if (this.isSafeCatchPosition(previous, ladderBlock, supportBlock) && this.verticallyOverlapsLadder(previous, ladderBlock)) {
            return new ControlledCatch(controlStartTick, this.centerError(previous, ladderBlock));
        }
        EnumFacing ladderFacing = this.facingFromSupport(ladderBlock, supportBlock);
        AxisAlignedBB ladderBounds = ClutchLadderPlanner.expectedLadderBounds(ladderBlock, ladderFacing);
        int lastTick = Math.min(20, controlStartTick + 20);
        for (int tick = controlStartTick + 1; tick <= lastTick; ++tick) {
            TrajectoryPoint topCrossing;
            BlockPlacementGraph snapshot = new BlockPlacementGraph(simulation);
            AutoLadderMovementController.CenterInput input = AutoLadderMovementController.chooseCentering(simulatedPlayer, this.player, this.world, snapshot, (double)ladderBlock.D() + 0.5, (double)ladderBlock.G() + 0.5);
            simulation.setInput(input.isForward(), input.isBackward(), input.isLeft(), input.isRight(), false, false);
            simulation.simulateTick(false);
            TrajectoryPoint current = TrajectoryPoint.capture(tick, simulatedPlayer, simulatedPlayer.b$src$Z$fqlxe4(), new BlockPlacementGraph(simulation));
            if (current.intersectsUnitBlock(supportBlock, 0.04) || previous.sweptIntersectsUnitBlock(current, supportBlock, 0.04)) {
                return null;
            }
            double ladderTop = (double)ladderBlock.B() + 1.0;
            if (previous.var_double_y >= ladderTop && current.var_double_y < ladderTop && (topCrossing = TrajectoryPoint.interpolateAtY(previous, current, ladderTop)).horizontallyIntersects(ladderBounds, 0.002)) {
                return null;
            }
            if (current.onGround) {
                return null;
            }
            if (this.isSafeCatchPosition(current, ladderBlock, supportBlock) && this.verticallyOverlapsLadder(current, ladderBlock)) {
                return new ControlledCatch(tick, this.centerError(current, ladderBlock));
            }
            if (current.var_double_y < (double)ladderBlock.B() - 0.05 || current.motionY >= 0.0) {
                return null;
            }
            previous = current;
        }
        return null;
    }

    private TrajectoryPoint pointAtTick(List<TrajectoryPoint> trajectory, int tick) {
        for (TrajectoryPoint point : trajectory) {
            if (point.tick != tick) continue;
            return point;
        }
        return null;
    }

    private boolean verticallyOverlapsLadder(TrajectoryPoint point, BlockData ladderBlock) {
        return point.maxY > (double)ladderBlock.B() && point.minY < (double)ladderBlock.B() + 1.0;
    }

    private double centerError(TrajectoryPoint point, BlockData ladderBlock) {
        return Math.hypot(point.var_double_x - ((double)ladderBlock.D() + 0.5), point.var_double_z - ((double)ladderBlock.G() + 0.5));
    }

    private boolean isSafeCatchPosition(TrajectoryPoint point, BlockData ladderBlock, BlockData supportBlock) {
        boolean centerInsideLadderCell = point.var_double_x >= (double)ladderBlock.D() + 0.02 && point.var_double_x <= (double)ladderBlock.D() + 1.0 - 0.02 && point.var_double_z >= (double)ladderBlock.G() + 0.02 && point.var_double_z <= (double)ladderBlock.G() + 1.0 - 0.02;
        return centerInsideLadderCell && !point.horizontallyIntersectsUnitBlock(supportBlock, 0.04);
    }

    private EnumFacing facingFromSupport(BlockData ladderBlock, BlockData supportBlock) {
        int directionX = ladderBlock.D() - supportBlock.D();
        int directionZ = ladderBlock.G() - supportBlock.G();
        for (EnumFacing facing : HORIZONTAL_FACINGS) {
            if (facing.getDirectionVector().getX() != directionX || facing.getDirectionVector().getZ() != directionZ) continue;
            return facing;
        }
        return HORIZONTAL_FACINGS[0];
    }

    private static double ladderThickness() {
        return ForgeVersion.MC_1_16_5.d() ? 0.1875 : 0.125;
    }

    private static AxisAlignedBB expectedLadderBounds(BlockData ladder, EnumFacing facing) {
        int directionX = facing.getDirectionVector().getX();
        int directionZ = facing.getDirectionVector().getZ();
        double thickness = ClutchLadderPlanner.ladderThickness();
        double minX = ladder.D();
        double maxX = (double)ladder.D() + 1.0;
        double minZ = ladder.G();
        double maxZ = (double)ladder.G() + 1.0;
        if (directionX > 0) {
            maxX = minX + thickness;
        } else if (directionX < 0) {
            minX = maxX - thickness;
        } else if (directionZ > 0) {
            maxZ = minZ + thickness;
        } else if (directionZ < 0) {
            minZ = maxZ - thickness;
        }
        return AxisAlignedBB.create(minX, ladder.B(), minZ, maxX, (double)ladder.B() + 1.0, maxZ);
    }

    private Block blockAt(BlockData block) {
        return this.world.getBlockByPos(block.D(), block.B(), block.G());
    }

    private boolean isStableSupport(BlockData blockData) {
        Block block = this.blockAt(blockData);
        return block.isNotNull() && BlockUtil.k(block) && !ClutchPlacementPathUtils.isBlacklistedPlacementBlock(block);
    }

    private static final class CatchSample {
        private final TrajectoryPoint point;
        private final int latestPlacementTick;
        private final int ladderY;
        private final boolean swept;

        private CatchSample(TrajectoryPoint point, int latestPlacementTick, int ladderY, boolean swept) {
            this.point = point;
            this.latestPlacementTick = latestPlacementTick;
            this.ladderY = ladderY;
            this.swept = swept;
        }
    }

    private static interface CatchCellConsumer {
        public void accept(BlockData var1, EnumFacing var2, double var3, double var5, double var7);
    }

    private static final class TrajectoryPoint {
        private final int tick;
        private final double var_double_x;
        private final double var_double_y;
        private final double var_double_z;
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

        private TrajectoryPoint(int tick, double x, double y, double z, double eyeY, double motionY, float yaw, float pitch, AxisAlignedBB bounds, boolean onGround, BlockPlacementGraph snapshot) {
            this.tick = tick;
            this.var_double_x = x;
            this.var_double_y = y;
            this.var_double_z = z;
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

        private static TrajectoryPoint capture(int tick, EntityPlayer player, boolean onGround, BlockPlacementGraph snapshot) {
            AxisAlignedBB bounds = player.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu();
            return new TrajectoryPoint(tick, player.z(), player.N(), player.h(), player.N() + (double)player.X(), player.q(), player.J(), player.V(), bounds, onGround, snapshot);
        }

        private static TrajectoryPoint interpolateAtY(TrajectoryPoint start, TrajectoryPoint end, double targetY) {
            double verticalDelta = end.var_double_y - start.var_double_y;
            double progress = Math.abs(verticalDelta) < 1.0E-9 ? 0.0 : (targetY - start.var_double_y) / verticalDelta;
            progress = Math.max(0.0, Math.min(1.0, progress));
            double x = TrajectoryPoint.lerp(start.var_double_x, end.var_double_x, progress);
            double z = TrajectoryPoint.lerp(start.var_double_z, end.var_double_z, progress);
            double eyeY = TrajectoryPoint.lerp(start.eyeY, end.eyeY, progress);
            double motionY = TrajectoryPoint.lerp(start.motionY, end.motionY, progress);
            float yaw = start.yaw + MathUtil.wrapAngleTo180(end.yaw - start.yaw) * (float)progress;
            float pitch = (float)TrajectoryPoint.lerp(start.pitch, end.pitch, progress);
            AxisAlignedBB bounds = AxisAlignedBB.create(TrajectoryPoint.lerp(start.minX, end.minX, progress), TrajectoryPoint.lerp(start.minY, end.minY, progress), TrajectoryPoint.lerp(start.minZ, end.minZ, progress), TrajectoryPoint.lerp(start.maxX, end.maxX, progress), TrajectoryPoint.lerp(start.maxY, end.maxY, progress), TrajectoryPoint.lerp(start.maxZ, end.maxZ, progress));
            return new TrajectoryPoint(end.tick, x, targetY, z, eyeY, motionY, yaw, pitch, bounds, false, end.snapshot);
        }

        private static double lerp(double start, double end, double progress) {
            return start + (end - start) * progress;
        }

        private Vec3 eyePosition() {
            return Vec3.create(this.var_double_x, this.eyeY, this.var_double_z);
        }

        private boolean intersectsUnitBlock(BlockData block) {
            return this.maxX > (double)block.D() && this.minX < (double)block.D() + 1.0 && this.maxY > (double)block.B() && this.minY < (double)block.B() + 1.0 && this.maxZ > (double)block.G() && this.minZ < (double)block.G() + 1.0;
        }

        private boolean intersectsUnitBlock(BlockData block, double horizontalMargin) {
            return this.maxX + horizontalMargin > (double)block.D() && this.minX - horizontalMargin < (double)block.D() + 1.0 && this.maxY > (double)block.B() && this.minY < (double)block.B() + 1.0 && this.maxZ + horizontalMargin > (double)block.G() && this.minZ - horizontalMargin < (double)block.G() + 1.0;
        }

        private boolean horizontallyIntersectsUnitBlock(BlockData block, double horizontalMargin) {
            return this.maxX + horizontalMargin > (double)block.D() && this.minX - horizontalMargin < (double)block.D() + 1.0 && this.maxZ + horizontalMargin > (double)block.G() && this.minZ - horizontalMargin < (double)block.G() + 1.0;
        }

        private boolean horizontallyIntersects(AxisAlignedBB bounds, double horizontalMargin) {
            return this.maxX + horizontalMargin > bounds.getMinX() && this.minX - horizontalMargin < bounds.getMaxX() && this.maxZ + horizontalMargin > bounds.getMinZ() && this.minZ - horizontalMargin < bounds.getMaxZ();
        }

        private boolean sweptIntersectsUnitBlock(TrajectoryPoint next, BlockData block, double horizontalMargin) {
            double exit;
            double startX = (this.minX + this.maxX) / 2.0;
            double startY = (this.minY + this.maxY) / 2.0;
            double startZ = (this.minZ + this.maxZ) / 2.0;
            double endX = (next.minX + next.maxX) / 2.0;
            double endY = (next.minY + next.maxY) / 2.0;
            double endZ = (next.minZ + next.maxZ) / 2.0;
            double halfWidthX = (this.maxX - this.minX) / 2.0 + horizontalMargin;
            double halfHeight = (this.maxY - this.minY) / 2.0;
            double halfWidthZ = (this.maxZ - this.minZ) / 2.0 + horizontalMargin;
            double xEntry = TrajectoryPoint.axisEntry(startX, endX, (double)block.D() - halfWidthX, (double)block.D() + 1.0 + halfWidthX);
            double yEntry = TrajectoryPoint.axisEntry(startY, endY, (double)block.B() - halfHeight, (double)block.B() + 1.0 + halfHeight);
            double zEntry = TrajectoryPoint.axisEntry(startZ, endZ, (double)block.G() - halfWidthZ, (double)block.G() + 1.0 + halfWidthZ);
            double xExit = TrajectoryPoint.axisExit(startX, endX, (double)block.D() - halfWidthX, (double)block.D() + 1.0 + halfWidthX);
            double yExit = TrajectoryPoint.axisExit(startY, endY, (double)block.B() - halfHeight, (double)block.B() + 1.0 + halfHeight);
            double zExit = TrajectoryPoint.axisExit(startZ, endZ, (double)block.G() - halfWidthZ, (double)block.G() + 1.0 + halfWidthZ);
            double entry = Math.max(0.0, Math.max(xEntry, Math.max(yEntry, zEntry)));
            return entry <= (exit = Math.min(1.0, Math.min(xExit, Math.min(yExit, zExit))));
        }

        private static double axisEntry(double start, double end, double minimum, double maximum) {
            double delta = end - start;
            if (Math.abs(delta) < 1.0E-9) {
                return start >= minimum && start <= maximum ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            }
            return Math.min((minimum - start) / delta, (maximum - start) / delta);
        }

        private static double axisExit(double start, double end, double minimum, double maximum) {
            double delta = end - start;
            if (Math.abs(delta) < 1.0E-9) {
                return start >= minimum && start <= maximum ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
            }
            return Math.max((minimum - start) / delta, (maximum - start) / delta);
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

    private static final class ControlledCatch {
        private final int catchTick;
        private final double remainingCenterError;

        private ControlledCatch(int catchTick, double remainingCenterError) {
            this.catchTick = catchTick;
            this.remainingCenterError = remainingCenterError;
        }
    }

    private final class LadderExtensionStrategy
    implements BlockPathSearchStrategy<PlacementTarget> {
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
            return ClutchPlacementPathUtils.isPlacementSpaceClear(ClutchLadderPlanner.this.world, ClutchLadderPlanner.this.player, blockData) && !ClutchLadderPlanner.this.clutch.getPlacedBlocks().Y(blockData);
        }
    }
}

