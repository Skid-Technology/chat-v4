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
import gg.vape.module.utility.inventory.cleaner.InventoryFilterAction;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterPreset;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public class ItemInventoryFilterRule
extends AbstractInventoryFilterRule {
    private InventoryFilterAction filterAction = InventoryFilterAction.REMOVE;

    public void setAction(InventoryFilterAction action) {
        this.filterAction = action;
    }

    public ItemInventoryFilterRule() {
    }

    public InventoryFilterAction getAction() {
        return this.filterAction;
    }

    @Override
    public JsonObject toJson(boolean embedSharedPreset) {
        JsonObject jsonObject = super.toJson(embedSharedPreset);
        jsonObject.addProperty("filterAction", this.filterAction.getName());
        return jsonObject;
    }

    @Override
    @Nullable
    public InventoryFilterPreset resolvePreset() {
        UUID sharedPresetId = this.getSharedPresetId();
        if (sharedPresetId != null) {
            return Vape.INSTANCE.getInventoryFilterPresetRegistry().getItemRulePresets().getById(sharedPresetId);
        }
        return this.getInlinePreset();
    }

    public ItemInventoryFilterRule(JsonObject jsonObject) {
        super(jsonObject);
        this.filterAction = InventoryFilterAction.fromName(jsonObject.get("filterAction").getAsString());
    }
}

