package gg.vape.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class ProfileRemoteMetadata {
   private final long version;
   private final long publishedVersion;
   private final long publicProfileId;

   public JsonObject toJson() {
      JsonObject object = new JsonObject();
      object.addProperty("publicProfileId", this.publicProfileId);
      object.addProperty("version", this.version);
      object.addProperty("publishedVersion", this.publishedVersion);
      return object;
   }

   @Nullable
   @Contract("!null -> !null; null -> null")
   public static ProfileRemoteMetadata fromJson(@Nullable JsonElement element) {
      if (element != null && !element.isJsonNull() && element.isJsonObject()) {
         JsonObject object = element.getAsJsonObject();
         long publicProfileId = object.has("publicProfileId") && !object.get("publicProfileId").isJsonNull() ? object.get("publicProfileId").getAsLong() : 0L;
         long version = object.has("version") && !object.get("version").isJsonNull() ? object.get("version").getAsLong() : 0L;
         long publishedVersion = object.has("publishedVersion") && !object.get("publishedVersion").isJsonNull()
            ? object.get("publishedVersion").getAsLong()
            : 0L;
         return new ProfileRemoteMetadata(publicProfileId, version, publishedVersion);
      } else {
         return null;
      }
   }

   public long getPublicProfileId() {
      return this.publicProfileId;
   }

   public long getPublishedVersion() {
      return this.publishedVersion;
   }

   @Override
   public String toString() {
      return "PrivateProfileMetadata{publicProfileId="
         + this.publicProfileId
         + ", version="
         + this.version
         + ", publishedVersion="
         + this.publishedVersion
         + '}';
   }

   public long getVersion() {
      return this.version;
   }

   public ProfileRemoteMetadata(long publicProfileId, long version, long publishedVersion) {
      this.publicProfileId = publicProfileId;
      this.version = version;
      this.publishedVersion = publishedVersion;
   }

   public static ProfileRemoteMetadata forDownload(long publicProfileId) {
      return new ProfileRemoteMetadata(publicProfileId, 0L, 0L);
   }

   public boolean hasNewerPublishedVersion() {
      return this.version < this.publishedVersion;
   }
}
