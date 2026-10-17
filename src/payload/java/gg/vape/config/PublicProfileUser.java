package gg.vape.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class PublicProfileUser {
   private final String username;
   private final long userId;
   private static int[] runtimeState;

   public static int[] getRuntimeState() {
      return runtimeState;
   }

   public static void setRuntimeState(int[] runtimeState) {
      PublicProfileUser.runtimeState = runtimeState;
   }

   public String getUsername() {
      return this.username;
   }

   @Nullable
   @Contract("null -> null")
   public static PublicProfileUser fromJson(@Nullable JsonElement element) {
      if (element != null && !element.isJsonNull()) {
         JsonObject object = element.getAsJsonObject();
         PublicProfileUser user = new PublicProfileUser(object.get("userId").getAsLong(), ConfigJsonUtils.getString(object, "username"));
         return user.getUsername() != null ? user : null;
      } else {
         return null;
      }
   }

   PublicProfileUser(long userId, String username) {
      this.userId = userId;
      this.username = username;
   }

   @Override
   public String toString() {
      return "SimpleOnlineUser{userId=" + this.userId + ", username='" + this.username + '\'' + '}';
   }

   public long getUserId() {
      return this.userId;
   }

   static {
      setRuntimeState(null);
   }
}
