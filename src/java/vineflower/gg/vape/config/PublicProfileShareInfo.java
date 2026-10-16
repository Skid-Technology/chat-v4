package gg.vape.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.UUID;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class PublicProfileShareInfo {
   private final boolean uploadAnonymously;
   @Nullable
   private UUID derivedFrom;
   private final boolean shareCodeFriendsOnly;
   private final boolean listedPublicly;
   private String shareCode;
   private long unreadNotifications;

   public void setUnreadNotifications(long unreadNotifications) {
      this.unreadNotifications = unreadNotifications;
   }

   public String getUppercaseShareCode() {
      return this.shareCode.toUpperCase();
   }

   @Nullable
   public UUID getDerivedFrom() {
      return this.derivedFrom;
   }

   public boolean isShareCodeFriendsOnly() {
      return this.shareCodeFriendsOnly;
   }

   @Contract("!null -> !null; null -> null")
   public static PublicProfileShareInfo fromJson(@Nullable JsonElement element) {
      if (element != null && !element.isJsonNull()) {
         JsonObject object = element.getAsJsonObject();
         String derivedFrom = ConfigJsonUtils.getString(object, "derivedFrom");
         UUID derivedFromUuid = derivedFrom != null ? UUID.fromString(derivedFrom) : null;
         long unreadNotifications = object.has("unreadNotifications") && !object.get("unreadNotifications").isJsonNull()
            ? object.get("unreadNotifications").getAsLong()
            : 0L;
         String shareCode = object.has("shareCode") && !object.get("shareCode").isJsonNull() ? object.get("shareCode").getAsString() : "";
         boolean listedPublicly = object.has("listedPublicly") && !object.get("listedPublicly").isJsonNull()
            ? object.get("listedPublicly").getAsBoolean()
            : false;
         boolean shareCodeFriendsOnly = object.has("shareCodeFriendsOnly") && !object.get("shareCodeFriendsOnly").isJsonNull()
            ? object.get("shareCodeFriendsOnly").getAsBoolean()
            : false;
         boolean uploadAnonymously = object.has("uploadAnonymously") && !object.get("uploadAnonymously").isJsonNull()
            ? object.get("uploadAnonymously").getAsBoolean()
            : false;
         return new PublicProfileShareInfo(derivedFromUuid, unreadNotifications, shareCode, listedPublicly, shareCodeFriendsOnly, uploadAnonymously);
      } else {
         return null;
      }
   }

   public boolean isUploadAnonymously() {
      return this.uploadAnonymously;
   }

   public void setDerivedFrom(@Nullable UUID derivedFrom) {
      this.derivedFrom = derivedFrom;
   }

   public boolean isListedPublicly() {
      return this.listedPublicly;
   }

   PublicProfileShareInfo(
      @Nullable UUID derivedFrom, long unreadNotifications, String shareCode, boolean listedPublicly, boolean shareCodeFriendsOnly, boolean uploadAnonymously
   ) {
      this.derivedFrom = derivedFrom;
      this.unreadNotifications = unreadNotifications;
      this.shareCode = shareCode;
      this.listedPublicly = listedPublicly;
      this.shareCodeFriendsOnly = shareCodeFriendsOnly;
      this.uploadAnonymously = uploadAnonymously;
   }

   public long getUnreadNotifications() {
      return this.unreadNotifications;
   }

   public void setShareCode(String shareCode) {
      this.shareCode = shareCode;
   }
}
