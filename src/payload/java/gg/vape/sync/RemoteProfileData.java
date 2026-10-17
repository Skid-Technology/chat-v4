package gg.vape.sync;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.api.ApiHttpClient;
import gg.vape.config.ProfileRemoteMetadata;
import java.util.Map;
import java.util.UUID;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class RemoteProfileData {
   @Nullable
   private final ProfileRemoteMetadata metadata;
   private final UUID ownerUuid;
   private final String vapeVersion;
   private final Map<String, Object> data;
   private final UUID profileId;
   private final String name;

   RemoteProfileData(UUID ownerUuid, UUID profileId, String name, String vapeVersion, Map<String, Object> data, @Nullable ProfileRemoteMetadata metadata) {
      this.ownerUuid = ownerUuid;
      this.profileId = profileId;
      this.name = name;
      this.vapeVersion = vapeVersion;
      this.data = data;
      this.metadata = metadata;
   }

   public Map<String, Object> getData() {
      return this.data;
   }

   public String getName() {
      return this.name;
   }

   public String getVapeVersion() {
      return this.vapeVersion;
   }

   @Nullable
   public ProfileRemoteMetadata getMetadata() {
      return this.metadata;
   }

   @Override
   public String toString() {
      return "PrivateProfile{uuid="
         + this.ownerUuid
         + "profileId="
         + this.profileId
         + ", name='"
         + this.name
         + '\''
         + ", data="
         + this.data
         + ", metadata="
         + this.metadata
         + '}';
   }

   public JsonObject toJson() {
      JsonObject json = new JsonObject();
      json.addProperty("uuid", this.ownerUuid != null ? this.ownerUuid.toString() : null);
      json.addProperty("profileId", this.profileId != null ? this.profileId.toString() : null);
      json.addProperty("name", this.name);
      json.addProperty("vapeVersion", this.vapeVersion);
      json.add("data", this.data != null ? ApiHttpClient.GSON.toJsonTree(this.data) : null);
      json.add("metadata", this.metadata != null ? this.metadata.toJson() : null);
      return json;
   }

   public UUID getProfileId() {
      return this.profileId;
   }

   @Nullable
   @Contract("!null -> !null; null -> null")
   public static RemoteProfileData fromJson(@Nullable JsonElement element) {
      if (element != null && !element.isJsonNull() && element.isJsonObject()) {
         JsonObject json = element.getAsJsonObject();
         UUID ownerUuid = null;
         JsonElement ownerUuidElement = json.get("uuid");
         if (ownerUuidElement != null && !ownerUuidElement.isJsonNull()) {
            try {
               ownerUuid = UUID.fromString(ownerUuidElement.getAsString());
            } catch (Exception var14) {
            }
         }

         UUID profileId = null;
         JsonElement profileIdElement = json.get("profileId");
         if (profileIdElement != null && !profileIdElement.isJsonNull()) {
            try {
               profileId = UUID.fromString(profileIdElement.getAsString());
            } catch (Exception var13) {
            }
         }

         if (profileId == null) {
            profileId = UUID.randomUUID();
         }

         JsonElement nameElement = json.get("name");
         String name = nameElement != null && !nameElement.isJsonNull() ? nameElement.getAsString() : "Default";
         JsonElement vapeVersionElement = json.get("vapeVersion");
         String vapeVersion = vapeVersionElement != null && !vapeVersionElement.isJsonNull() ? vapeVersionElement.getAsString() : "4.22";
         Map<String, Object> data = null;
         if (json.has("data") && !json.get("data").isJsonNull()) {
            try {
               data = ApiHttpClient.GSON.fromJson(json.get("data"), Map.class);
            } catch (Exception var12) {
            }
         }

         ProfileRemoteMetadata metadata = null;
         if (json.has("metadata") && !json.get("metadata").isJsonNull()) {
            metadata = ProfileRemoteMetadata.fromJson(json.get("metadata"));
         }

         return new RemoteProfileData(ownerUuid, profileId, name, vapeVersion, data, metadata);
      } else {
         return null;
      }
   }
}
