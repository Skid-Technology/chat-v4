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
   private final List<Value<?, ?>> values = new ArrayList<>();

   public JsonArray toJson() {
      JsonArray result = new JsonArray();

      for (Value<?, ?> value : this.values) {
         if (!value.isDefault()) {
            result.add(value.toJson(false));
         }
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
      if (serializedSettings.size() != 0) {
         for (int index = 0; index < serializedSettings.size(); index++) {
            JsonElement element = serializedSettings.get(index);
            if (element.isJsonObject() && !element.isJsonNull()) {
               JsonObject setting = element.getAsJsonObject();

               for (Value<?, ?> value : this.values) {
                  if (value.matchesJsonId(setting)) {
                     value.loadJson(setting);
                  }
               }

               if (setting.get("enemies") != null && !setting.get("enemies").isJsonNull()) {
                  JsonArray nestedArray = setting.get("enemies").getAsJsonArray();
                  Vape.INSTANCE.getEnemyManager().loadJson(nestedArray);
               }

               if (setting.get("frames") != null && !setting.get("frames").isJsonNull()) {
                  JsonArray nestedArray = setting.get("frames").getAsJsonArray();
                  if (!Vape.INSTANCE.getPublicProfileSettings().framePositionsPerProfile.getEffectiveValue()) {
                     JsonArray wrappedFrameStates = new JsonArray();
                     wrappedFrameStates.add(nestedArray);
                     ClientSettings.INSTANCE.loadFrameStates(wrappedFrameStates);
                  }
               }

               if (setting.get("inventoryManager") != null && !setting.get("inventoryManager").isJsonNull()) {
                  JsonObject inventoryManager = setting.get("inventoryManager").getAsJsonObject();
                  Vape.INSTANCE.getInventoryFilterPresetRegistry().loadJson(inventoryManager);
               }
            }
         }
      }
   }

   public void registerValue(Value<?, ?> value) {
      this.values.add(value);
   }
}
