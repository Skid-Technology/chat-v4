/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MBlockBed;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.BlockHorizontal;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.BlockProperty;
import gg.vape.wrapper.impl.BlockState;
import gg.vape.wrapper.impl.EnumFacing;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.World;

public class BlockBed
extends Block {
    public static BlockProperty part() {
        return new BlockProperty(MBlockBed.getPart(BlockBed.vapeInstance.getMappings().Dj));
    }

    public boolean isFoot(World world, int blockX, int blockY, int blockZ) {
        if (ForgeVersion.MC_1_16_5.d()) {
            BlockPos blockPos = BlockPos.create(blockX, blockY, blockZ);
            BlockState blockState = world.getBlockState(blockPos);
            Object part = blockState.I(BlockBed.part());
            if (part != null) {
                return part.toString().equals("foot");
            }
            return true;
        }
        if (ForgeVersion.MC_1_7_10.L()) {
            int metadata = world.A(blockX, blockY, blockZ);
            boolean isHead = (metadata & 8) != 0;
            return !isHead;
        }
        BlockPos blockPos = BlockPos.create(blockX, blockY, blockZ);
        BlockState blockState = world.getBlockState(blockPos);
        BlockState actualState = new BlockState(BlockBed.vapeInstance.getMappings().qg.y(this.I, blockState.getObject(), world.getObject(), blockPos.getObject()));
        Object part = actualState.I(BlockBed.part());
        if (part != null) {
            return part.toString().equals("foot");
        }
        return true;
    }

    public EnumFacing getBedDirection(World world, int blockX, int blockY, int blockZ) {
        if (ForgeVersion.MC_1_16_5.d()) {
            BlockPos blockPos = BlockPos.create(blockX, blockY, blockZ);
            Object direction = MBlockBed.getBedDirection(BlockBed.vapeInstance.getMappings().Dj, world.getObject(), blockPos.getObject());
            return new EnumFacing(direction);
        }
        BlockPos blockPos = BlockPos.create(blockX, blockY, blockZ);
        BlockState blockState = world.getBlockState(blockPos);
        BlockState actualState = new BlockState(BlockBed.vapeInstance.getMappings().qg.y(this.I, blockState.getObject(), world.getObject(), blockPos.getObject()));
        Object direction = actualState.I(BlockHorizontal.facing());
        return new EnumFacing(direction);
    }

    public BlockBed(Object wrappedObject) {
        super(wrappedObject);
    }
}

