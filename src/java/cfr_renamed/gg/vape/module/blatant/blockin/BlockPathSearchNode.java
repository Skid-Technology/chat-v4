/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.blatant.blockin;

import gg.vape.utils.MathUtil;
import gg.vape.utils.datas.BlockCoordinate;
import gg.vape.wrapper.impl.BlockPos;

public class BlockPathSearchNode {
    public double pathCost;
    final public double var_double_z;
    public BlockPathSearchNode parent;
    final public double var_double_y;
    public double heuristicCost;
    final public double var_double_x;

    public BlockPathSearchNode(double x, double y, double z) {
        this.var_double_x = x;
        this.var_double_y = y;
        this.var_double_z = z;
    }

    public BlockCoordinate getBlockCoordinate() {
        return new BlockCoordinate(MathUtil.floor(this.var_double_x), MathUtil.floor(this.var_double_y), MathUtil.floor(this.var_double_z));
    }

    public double getTotalCost() {
        return this.pathCost + this.heuristicCost;
    }

    public String toString() {
        return "X: " + this.var_double_x + " Y: " + this.var_double_y + " Z: " + this.var_double_z;
    }

    public int hashCode() {
        return (int)BlockPos.f(MathUtil.floor(this.var_double_x), MathUtil.floor(this.var_double_y), MathUtil.floor(this.var_double_z));
    }

    public boolean equals(Object candidate) {
        if (!(candidate instanceof BlockPathSearchNode)) {
            return false;
        }
        BlockPathSearchNode other = (BlockPathSearchNode)candidate;
        return this.var_double_x == other.var_double_x && this.var_double_y == other.var_double_y && this.var_double_z == other.var_double_z;
    }
}

