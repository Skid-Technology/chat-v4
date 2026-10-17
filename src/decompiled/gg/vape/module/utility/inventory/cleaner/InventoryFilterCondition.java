/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonObject;
import gg.vape.module.utility.inventory.cleaner.InventoryFilterConditionType;
import gg.vape.wrapper.impl.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface InventoryFilterCondition<T extends InventoryFilterCondition<?>>
extends Cloneable {
    @Nullable
    default public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", this.getType().name());
        return jsonObject;
    }

    public boolean matches(ItemStack var1);

    public InventoryFilterConditionType getType();

    public T copy();

    public static InventoryFilterCondition<?> fromJson(JsonObject jsonObject) {
        InventoryFilterConditionType type = InventoryFilterConditionType.valueOf(jsonObject.get("type").getAsString());
        return type.getJsonFactory().apply(jsonObject);
    }
}

