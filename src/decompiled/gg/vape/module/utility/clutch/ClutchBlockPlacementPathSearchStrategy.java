/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.utility.clutch;

import gg.vape.module.utility.BlockIn;
import gg.vape.module.utility.clutch.BlockPathSearchStrategy;
import gg.vape.module.utility.clutch.BlockPlacementNode;
import gg.vape.module.utility.clutch.ClutchPlacementPathUtils;
import gg.vape.module.utility.clutch.PlacementTarget;
import gg.vape.utils.BlockUtil;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.World;
import java.util.HashSet;
import java.util.Vector;

public class ClutchBlockPlacementPathSearchStrategy
implements BlockPathSearchStrategy<PlacementTarget> {
    final World world;
    final BlockIn blockIn;
    final EntityPlayerSP player;
    final HashSet<BlockData> allowedBlocks;
    final HashSet<BlockData> excludedBlocks;
    final BlockPlacementNode node;

    @Override
    public int scorePath(Vector<PlacementTarget> path) {
        return path.size();
    }

    public ClutchBlockPlacementPathSearchStrategy(BlockIn blockIn, HashSet<BlockData> excludedBlocks, BlockPlacementNode node, World world, EntityPlayerSP player, HashSet<BlockData> allowedBlocks) {
        this.blockIn = blockIn;
        this.excludedBlocks = excludedBlocks;
        this.node = node;
        this.world = world;
        this.player = player;
        this.allowedBlocks = allowedBlocks;
    }

    @Override
    public boolean isValidBlock(BlockData blockData) {
        if (this.allowedBlocks.contains(blockData)) {
            return true;
        }
        Block block = this.world.getBlockByPos(blockData.D(), blockData.B(), blockData.G());
        boolean isPlaceable = BlockUtil.k(block) && !ClutchPlacementPathUtils.isBlacklistedPlacementBlock(block);
        return isPlaceable;
    }

    @Override
    public boolean canVisit(BlockData blockData) {
        if (this.excludedBlocks.contains(blockData)) {
            return false;
        }
        if (this.node.occupiedBlocks.contains(blockData)) {
            return false;
        }
        return ClutchPlacementPathUtils.isPlacementSpaceClear(this.world, this.player, blockData);
    }

    @Override
    public int getMaxDepth() {
        return 2;
    }
}

