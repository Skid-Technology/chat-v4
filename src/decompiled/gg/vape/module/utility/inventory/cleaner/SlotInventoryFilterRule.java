/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.module.utility.inventory.cleaner.AbstractInventoryFilterRule;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterPreset;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public class SlotInventoryFilterRule
extends AbstractInventoryFilterRule {
    private static int[] slotStateArray;
    private final int slot;

    public SlotInventoryFilterRule(JsonObject jsonObject) {
        super(jsonObject);
        this.slot = jsonObject.get("slot").getAsInt();
    }

    public static void setSlotStateArray(int[] states) {
        slotStateArray = states;
    }

    @Override
    @Nullable
    public InventoryFilterPreset resolvePreset() {
        UUID sharedPresetId = this.getSharedPresetId();
        if (sharedPresetId != null) {
            return Vape.INSTANCE.getInventoryFilterPresetRegistry().getSlotRulePresets().getById(sharedPresetId);
        }
        return this.getInlinePreset();
    }

    public int getContainerSlot() {
        return 36 + this.getSlot();
    }

    @Override
    public JsonObject toJson(boolean embedSharedPreset) {
        JsonObject jsonObject = super.toJson(embedSharedPreset);
        jsonObject.addProperty("slot", this.slot);
        return jsonObject;
    }

    public SlotInventoryFilterRule(int slot) {
        this.slot = slot;
    }

    public static int[] getSlotStateArray() {
        return slotStateArray;
    }

    public int getSlot() {
        return this.slot;
    }

    static {
        SlotInventoryFilterRule.setSlotStateArray(new int[5]);
    }
}

