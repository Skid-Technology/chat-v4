package gg.vape.module.utility.inventory.cleaner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.value.Value;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public class InventoryCleanerProfileValue extends Value<InventoryCleanerProfile, InventoryCleanerProfileValue> {
   private final List<InventoryCleanerProfile> profiles = new ArrayList<>();

   @Nullable
   public InventoryCleanerProfile findByName(String name) {
      for (InventoryCleanerProfile profile : this.profiles) {
         if (profile.getName().equalsIgnoreCase(name)) {
            return profile;
         }
      }

      return null;
   }

   public void removeProfile(InventoryCleanerProfile profile) {
      this.profiles.remove(profile);
      this.notifyChanged();
   }

   public @UnmodifiableView List<InventoryCleanerProfile> getProfiles() {
      return this.profiles;
   }

   @Override
   public void reset() {
      super.reset();
      if (this.isResettable()) {
         this.profiles.clear();
      }
   }

   public static InventoryCleanerProfileValue create(Object owner, String id) {
      return new InventoryCleanerProfileValue(owner, id);
   }

   public InventoryCleanerProfileValue copyValueDefinition() {
      return new InventoryCleanerProfileValue(null, this.getId());
   }

   @Override
   public JsonObject toJson(boolean embedSharedPresets) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("id", this.getId());
      InventoryCleanerProfile selectedProfile = this.getValue();
      if (selectedProfile != null) {
         jsonObject.addProperty("selected", selectedProfile.getName());
      }

      if (!this.profiles.isEmpty()) {
         JsonArray jsonArray = new JsonArray();

         for (InventoryCleanerProfile profile : this.profiles) {
            jsonArray.add(profile.toJson(embedSharedPresets));
         }

         jsonObject.add("inventories", jsonArray);
      }

      return jsonObject;
   }

   @Override
   public String getDisplayValue() {
      InventoryCleanerProfile selectedProfile = this.getValue();
      return selectedProfile == null ? "" : selectedProfile.getName();
   }

   @Override
   public void parse(String value) {
   }

   @Override
   public boolean loadJson(JsonObject jsonObject) {
      if (!jsonObject.get("id").getAsString().equalsIgnoreCase(this.getId())) {
         return false;
      } else {
         this.profiles.clear();
         if (jsonObject.has("inventories")) {
            for (JsonElement jsonElement : jsonObject.getAsJsonArray("inventories")) {
               InventoryCleanerProfile profile = new InventoryCleanerProfile(jsonElement.getAsJsonObject());
               this.profiles.add(profile);
            }
         }

         if (jsonObject.has("selected")) {
            this.setValue(this.findByName(jsonObject.get("selected").getAsString()));
         }

         return true;
      }
   }

   public InventoryCleanerProfileValue(Object owner, String id) {
      super(owner, id, null);
   }

   public void addProfile(InventoryCleanerProfile profile) {
      this.profiles.add(profile);
      this.notifyChanged();
   }
}
