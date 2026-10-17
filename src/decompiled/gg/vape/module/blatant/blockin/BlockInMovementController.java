/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.blatant.blockin;

import gg.vape.module.blatant.blockin.BlockPlacementGraph;

public interface BlockInMovementController {
    public void setInput(boolean var1, boolean var2, boolean var3, boolean var4, boolean var5, boolean var6);

    public void applySnapshot(BlockPlacementGraph var1);

    public void tick();

    public void initialize();
}

