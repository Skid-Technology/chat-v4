/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.utility.clutch;

import gg.vape.utils.datas.BlockData;
import java.util.Vector;

public interface BlockPathSearchStrategy<T> {
    public int getMaxDepth();

    default public boolean canVisit(BlockData blockData) {
        return true;
    }

    public boolean isValidBlock(BlockData var1);

    default public int scorePath(Vector<T> path, int depth) {
        return this.scorePath(path);
    }

    default public int scorePath(Vector<T> path) {
        return path.size();
    }
}

