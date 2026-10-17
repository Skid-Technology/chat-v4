package gg.vape.manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.module.Macro;
import gg.vape.module.Mod;
import gg.vape.module.UtilityMod;
import gg.vape.module.none.ClientSettings;
import gg.vape.module.none.ConfigSettingsModule;
import gg.vape.value.Value;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValueManager {
   private static final List<Class<?>> NON_SERIALIZED_OWNER_TYPES = Arrays.asList(Mod.class, UtilityMod.class, Macro.class);
   private final List<Value<?, ?>> registeredValues = new ArrayList<>();
   private static final List<Class<?>> CONFIG_SETTINGS_OWNER_TYPES = Arrays.asList(ClientSettings.class);

   public List<Value<?, ?>> getValues() {
      return this.registeredValues;
   }

   public JsonArray toJson() {
      JsonArray result = new JsonArray();

      for (Value<?, ?> value : this.getValues()) {
         JsonObject serializedValue;
         if (!NON_SERIALIZED_OWNER_TYPES.contains(value.getOwner().getClass())
            && value.isSerializable()
            && !value.isDefault()
            && (serializedValue = value.toJson(false)).entrySet().size() > 1) {
            result.add(serializedValue);
         }
      }

      return result;
   }

   private void loadConfigSettings(JsonObject serializedValue) {
      for (Mod mod : Vape.INSTANCE.getModManager().getAllModules()) {
         if (mod instanceof ConfigSettingsModule) {
            ((ConfigSettingsModule)mod).loadMatchingValues(serializedValue);
         }
      }
   }

   public void registerValue(Value<?, ?> value) {
      this.registeredValues.add(value);
   }

   public void loadJson(JsonArray serializedValues) {
      if (serializedValues != null) {
         ArrayList<Value<?, ?>> loadedValues = new ArrayList<>();

         for (int index = 0; index < serializedValues.size(); index++) {
            JsonElement element = serializedValues.get(index);
            JsonObject serializedValue;
            if (element != null
               && element.isJsonObject()
               && !element.isJsonNull()
               && (serializedValue = element.getAsJsonObject()).get("id") != null
               && !serializedValue.get("id").isJsonNull()) {
               for (Value<?, ?> value : this.getValues()) {
                  if (value != null && !loadedValues.contains(value)) {
                     if (value.getOwner() != null) {
                        Class<?> ownerClass = value.getOwner().getClass();
                        Class<?> superOwnerClass = ownerClass.getSuperclass();
                        if (!CONFIG_SETTINGS_OWNER_TYPES.contains(superOwnerClass)
                           && !CONFIG_SETTINGS_OWNER_TYPES.contains(ownerClass)
                           && (NON_SERIALIZED_OWNER_TYPES.contains(superOwnerClass) || NON_SERIALIZED_OWNER_TYPES.contains(ownerClass))) {
                           continue;
                        }
                     }

                     try {
                        if (value.matchesJsonId(serializedValue)) {
                           loadedValues.add(value);
                           value.loadJson(serializedValue);
                        }
                     } catch (Throwable var10) {
                        Vape.logThrowable(var10);
                     }
                  }
               }
            }
         }
      }
   }
}
