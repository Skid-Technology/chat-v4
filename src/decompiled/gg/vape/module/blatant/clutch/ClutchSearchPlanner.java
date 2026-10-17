/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.blatant.clutch;

import gg.vape.module.blatant.Clutch;
import gg.vape.module.utility.clutch.BlockPathSearchStrategy;
import gg.vape.module.utility.clutch.PlacementTarget;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.EnumFacing;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ClutchSearchPlanner {
    private static EnumFacing[] directions;
    private final BlockPathSearchStrategy<PlacementTarget> strategy;
    private int visitedNodeCount;
    private final Map<BlockData, Vector<PlacementTarget>> pathCache;
    private long elapsedNanos;

    public ClutchSearchPlanner(BlockPathSearchStrategy<PlacementTarget> strategy) {
        this.strategy = strategy;
        this.pathCache = new HashMap<BlockData, Vector<PlacementTarget>>();
        if (directions == null) {
            directions = EnumFacing.t();
        }
    }

    public Vector<PlacementTarget> findPath(BlockData start, BlockData destination) {
        this.pathCache.clear();
        this.visitedNodeCount = 0;
        long startNanos = System.nanoTime();
        Vector<PlacementTarget> result = this.findPathRecursive(start, destination, 0, new Vector<PlacementTarget>());
        this.elapsedNanos = System.nanoTime() - startNanos;
        return result;
    }

    public long getElapsedNanos() {
        return this.elapsedNanos;
    }

    public int getVisitedNodeCount() {
        return this.visitedNodeCount;
    }

    private Vector<PlacementTarget> findPathRecursive(BlockData current, BlockData destination, int depth, Vector<PlacementTarget> currentPath) {
        int yDirection;
        int xDirection;
        ++this.visitedNodeCount;
        if (this.pathCache.containsKey(current)) {
            return this.pathCache.get(current);
        }
        if (depth > this.strategy.getMaxDepth()) {
            return null;
        }
        int deltaX = destination.D() - current.D();
        int deltaY = destination.B() - current.B();
        int deltaZ = destination.G() - current.G();
        int n = deltaX > 0 ? 5 : (xDirection = deltaX < 0 ? 4 : -1);
        int n2 = deltaY > 0 ? 1 : (yDirection = deltaY < 0 ? 0 : -1);
        int zDirection = deltaZ > 0 ? 3 : (deltaZ < 0 ? 2 : -1);
        ArrayList<PlacementTarget> candidates = new ArrayList<PlacementTarget>();
        for (int directionIndex : new int[]{xDirection, yDirection, zDirection}) {
            EnumFacing direction;
            BlockData adjacent;
            if (directionIndex == -1 || !this.strategy.isValidBlock(adjacent = current.R(direction = directions[directionIndex]))) continue;
            PlacementTarget target = new PlacementTarget(current, direction);
            target.depth = depth;
            candidates.add(target);
        }
        candidates.sort(Comparator.comparingInt(candidate -> this.scoreCandidate(currentPath, depth, (PlacementTarget)candidate)));
        Vector<PlacementTarget> bestPath = null;
        int bestScore = Integer.MAX_VALUE;
        for (PlacementTarget candidate2 : candidates) {
            BlockData nextBlock = candidate2.supportBlock.R(candidate2.facing);
            Vector<PlacementTarget> candidatePath = new Vector<PlacementTarget>(currentPath);
            candidatePath.add(candidate2);
            if (nextBlock.equals(destination)) {
                bestPath = candidatePath;
                break;
            }
            Vector<PlacementTarget> recursivePath = this.findPathRecursive(nextBlock, destination, depth + 1, candidatePath);
            if (recursivePath != null && !recursivePath.isEmpty()) {
                candidatePath = new Vector<PlacementTarget>(recursivePath);
            }
            if (candidatePath.isEmpty()) break;
            if (depth == 0) {
                Clutch.DEBUG_PLACEMENT_PATHS.add(new Vector<PlacementTarget>(candidatePath));
            }
            int score = this.strategy.scorePath(candidatePath, depth);
            if (bestPath != null && score >= bestScore) continue;
            bestPath = candidatePath;
            bestScore = score;
        }
        this.pathCache.put(current, bestPath);
        return bestPath;
    }

    private int scoreCandidate(Vector<PlacementTarget> currentPath, int depth, PlacementTarget candidate) {
        Vector<PlacementTarget> candidatePath = new Vector<PlacementTarget>(currentPath);
        candidatePath.add(candidate);
        return this.strategy.scorePath(candidatePath, depth);
    }
}

