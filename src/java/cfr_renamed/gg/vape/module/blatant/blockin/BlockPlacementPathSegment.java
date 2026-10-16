/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.blatant.blockin;

import gg.vape.module.blatant.blockin.BlockInTargetRotationState;
import gg.vape.module.blatant.blockin.BlockPlacementPathSegmentState;
import gg.vape.utils.Vec3d;
import gg.vape.utils.datas.BlockCoordinate;
import gg.vape.wrapper.impl.Vec3;
import java.util.ArrayList;
import java.util.Vector;

public class BlockPlacementPathSegment {
    private final static String LEFT_SUFFIX = " (Left: ";
    final public BlockCoordinate startCoordinate;
    final public ArrayList<Vec3d> simulatedPositions;
    public BlockCoordinate targetCoordinate;
    public String failureReason;
    final public Vector<BlockInTargetRotationState> rotationStates = new Vector();
    public int simulationTickLimit;
    public Vec3 hitVector;
    public BlockPlacementPathSegmentState placementState;

    public BlockPlacementPathSegment(BlockCoordinate startCoordinate, BlockCoordinate targetCoordinate, ArrayList<Vec3d> simulatedPositions) {
        this.startCoordinate = startCoordinate;
        this.targetCoordinate = targetCoordinate;
        this.simulatedPositions = simulatedPositions;
    }

    public int getPendingPlacementCount() {
        return this.placementState != null ? this.placementState.pendingTargets.size() : 0;
    }

    public boolean hasFailed() {
        return this.failureReason != null;
    }

    public int getExpectedPlacementCount() {
        return this.placementState != null ? this.placementState.getExpectedCount() : 0;
    }

    public void fail(String reason) {
        if (this.failureReason == null) {
            this.failureReason = reason + LEFT_SUFFIX + this.getPendingPlacementCount() + ")";
            this.hitVector = null;
            this.placementState = null;
        }
    }
}

