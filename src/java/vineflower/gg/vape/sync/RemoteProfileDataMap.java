package gg.vape.sync;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class RemoteProfileDataMap {
   private final Map<UUID, RemoteProfileData> profiles;
   private static final String TO_STRING_PREFIX = "PrivateProfilesResponse{profiles=";

   RemoteProfileDataMap(Map<UUID, RemoteProfileData> profiles) {
      this.profiles = profiles;
   }

   @Nullable
   @Contract("!null -> !null; null -> null")
   public static RemoteProfileDataMap fromJson(@Nullable JsonElement element) {
      if (element != null && !element.isJsonNull() && element.isJsonObject()) {
         JsonObject json = element.getAsJsonObject();
         LinkedHashMap<UUID, RemoteProfileData> profiles = new LinkedHashMap<>();

         for (Entry<String, JsonElement> entry : json.entrySet()) {
            RemoteProfileData profile = RemoteProfileData.fromJson(entry.getValue());
            if (profile != null) {
               UUID keyUuid;
               try {
                  keyUuid = UUID.fromString(entry.getKey());
               } catch (Exception var8) {
                  keyUuid = profile.getProfileId();
               }

               profiles.put(keyUuid, profile);
            }
         }

         return new RemoteProfileDataMap(profiles);
      } else {
         return null;
      }
   }

   @Override
   public String toString() {
      return "PrivateProfilesResponse{profiles=" + this.profiles + '}';
   }

   public Map<UUID, RemoteProfileData> getProfiles() {
      return this.profiles;
   }
}
