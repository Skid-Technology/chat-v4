/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.inventory;

import gg.vape.inventory.InventoryClickBuildStage;
import gg.vape.inventory.InventoryClickSlotStage;

public interface InventoryClickOptionsStage
extends InventoryClickSlotStage {
    public InventoryClickBuildStage swapWithFirstHotbarSlot();

    public InventoryClickBuildStage throwItem(boolean var1);

    public InventoryClickBuildStage pickupAll(boolean var1);

    public InventoryClickBuildStage swapWithHotbarSlot(int var1);

    public InventoryClickBuildStage quickMove();

    public InventoryClickBuildStage pickup();

    public InventoryClickBuildStage quickCraft(int var1);

    public InventoryClickBuildStage cloneStack();
}

