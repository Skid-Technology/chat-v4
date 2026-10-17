/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.Direction;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.Vec3;

public class BlockRayTraceResult
extends RayTraceResult {
    public BlockRayTraceResult(Object wrappedObject) {
        super(wrappedObject);
    }

    public static BlockRayTraceResult createMiss(Vec3 hitLocation, Direction direction, BlockPos blockPos) {
        return new BlockRayTraceResult(BlockRayTraceResult.vapeInstance.getMappings().blockRayTraceResult.createMiss(hitLocation.getObject(), direction.getObject(), blockPos.getObject()));
    }

    public boolean isInside() {
        return BlockRayTraceResult.vapeInstance.getMappings().blockRayTraceResult.isInside(this.I);
    }
}

