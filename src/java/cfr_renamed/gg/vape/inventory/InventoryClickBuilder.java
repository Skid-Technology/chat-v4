/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.inventory;

import gg.vape.inventory.InventoryClick;
import gg.vape.inventory.InventoryClickBuildStage;
import gg.vape.inventory.InventoryClickOptionsStage;
import gg.vape.inventory.InventoryClickSlotStage;
import gg.vape.inventory.InventoryClickWindowStage;

public class InventoryClickBuilder
implements InventoryClickWindowStage,
InventoryClickSlotStage,
InventoryClickOptionsStage,
InventoryClickBuildStage {
    private int clickMode;
    private int slotId;
    private int mouseButton;
    private int windowId;

    @Override
    public InventoryClickBuildStage swapWithHotbarSlot(int hotbarSlot) {
        this.clickMode = 2;
        this.mouseButton = hotbarSlot;
        return this;
    }

    @Override
    public InventoryClickBuildStage quickCraft(int dragEvent) {
        this.clickMode = 5;
        this.mouseButton = dragEvent;
        return this;
    }

    @Override
    public InventoryClickSlotStage window(int windowId) {
        this.windowId = windowId;
        return this;
    }

    @Override
    public InventoryClickBuildStage pickup() {
        this.clickMode = 0;
        return this;
    }

    @Override
    public InventoryClickBuildStage swapWithFirstHotbarSlot() {
        return this.swapWithHotbarSlot(0);
    }

    @Override
    public InventoryClickBuildStage throwItem(boolean entireStack) {
        this.clickMode = 4;
        this.mouseButton = entireStack ? 1 : 0;
        return this;
    }

    @Override
    public InventoryClickOptionsStage slot(int slotId) {
        this.slotId = slotId;
        return this;
    }

    @Override
    public InventoryClickBuildStage cloneStack() {
        this.clickMode = 3;
        return this;
    }

    @Override
    public InventoryClick build() {
        return new InventoryClick(this.windowId, this.slotId, this.mouseButton, this.clickMode);
    }

    @Override
    public InventoryClickBuildStage quickMove() {
        this.clickMode = 1;
        return this;
    }

    @Override
    public InventoryClickBuildStage pickupAll(boolean rightButton) {
        this.clickMode = 6;
        this.mouseButton = rightButton ? 1 : 0;
        return this;
    }
}

