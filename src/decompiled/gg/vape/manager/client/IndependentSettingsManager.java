/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.manager.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.module.none.ClientSettings;
import gg.vape.value.Value;
import java.util.ArrayList;
import java.util.List;

public class IndependentSettingsManager {
    private final List<Value<?, ?>> values = new ArrayList();

    public JsonArray toJson() {
        JsonArray result = new JsonArray();
        for (Value<?, ?> value : this.values) {
            if (value.isDefault()) continue;
            result.add(value.toJson(false));
        }
        JsonObject enemies = new JsonObject();
        enemies.add("enemies", Vape.INSTANCE.getEnemyManager().toJson());
        result.add(enemies);
        JsonObject frames = new JsonObject();
        frames.add("frames", ClientSettings.INSTANCE.serializeFrameStates());
        result.add(frames);
        JsonObject inventoryManager = new JsonObject();
        inventoryManager.add("inventoryManager", Vape.INSTANCE.getInventoryFilterPresetRegistry().toJson());
        result.add(inventoryManager);
        return result;
    }

    public List<Value<?, ?>> values() {
        return this.values;
    }

    public void loadIndependentSettings(JsonArray serializedSettings) {
        if (serializedSettings.size() == 0) {
            return;
        }
        for (int index = 0; index < serializedSettings.size(); ++index) {
            JsonArray nestedArray;
            JsonElement element = serializedSettings.get(index);
            if (!element.isJsonObject() || element.isJsonNull()) continue;
            JsonObject setting = element.getAsJsonObject();
            for (Value<?, ?> value : this.values) {
                if (!value.matchesJsonId(setting)) continue;
                value.loadJson(setting);
            }
            if (setting.get("enemies") != null && !setting.get("enemies").isJsonNull()) {
                nestedArray = setting.get("enemies").getAsJsonArray();
                Vape.INSTANCE.getEnemyManager().loadJson(nestedArray);
            }
            if (setting.get("frames") != null && !setting.get("frames").isJsonNull()) {
                nestedArray = setting.get("frames").getAsJsonArray();
                if (!Vape.INSTANCE.getPublicProfileSettings().framePositionsPerProfile.getEffectiveValue().booleanValue()) {
                    JsonArray wrappedFrameStates = new JsonArray();
                    wrappedFrameStates.add(nestedArray);
                    ClientSettings.INSTANCE.loadFrameStates(wrappedFrameStates);
                }
            }
            if (setting.get("inventoryManager") == null || setting.get("inventoryManager").isJsonNull()) continue;
            JsonObject inventoryManager = setting.get("inventoryManager").getAsJsonObject();
            Vape.INSTANCE.getInventoryFilterPresetRegistry().loadJson(inventoryManager);
        }
    }

    public void registerValue(Value<?, ?> value) {
        this.values.add(value);
    }
}

