/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonObject;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterConditionGroup;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterPreset;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public class SharedInventoryFilterPreset
extends InventoryFilterPreset {
    public SharedInventoryFilterPreset(@Nullable UUID id, String name) {
        super(id, name);
    }

    @Override
    public SharedInventoryFilterPreset copy() {
        SharedInventoryFilterPreset copy = new SharedInventoryFilterPreset(this.getId(), this.getName());
        for (InventoryFilterConditionGroup conditionGroup : this.getConditionGroups()) {
            copy.addConditionGroup(conditionGroup.copy());
        }
        return copy;
    }

    public SharedInventoryFilterPreset(JsonObject jsonObject) {
        super(jsonObject);
    }

    public SharedInventoryFilterPreset(InventoryFilterPreset preset) {
        super(preset.toJson());
        this.id = UUID.randomUUID();
    }

    @Override
    public InventoryFilterPreset copy() {
        return this.copy();
    }
}

