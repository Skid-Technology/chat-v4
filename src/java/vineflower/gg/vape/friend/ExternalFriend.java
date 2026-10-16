package gg.vape.friend;

import com.google.gson.JsonObject;

public class ExternalFriend extends FriendEntry {
   private final OnlineFriend onlineFriend;

   @Override
   public JsonObject toJson() {
      return null;
   }

   @Override
   public String getName() {
      return this.onlineFriend.isVisible() ? this.onlineFriend.getMinecraftUsername() : "";
   }

   @Override
   public boolean isPersistent() {
      return false;
   }

   public OnlineFriend getOnlineFriend() {
      return this.onlineFriend;
   }

   @Override
   public Friend loadJson(JsonObject jsonObject) {
      return null;
   }

   public ExternalFriend(OnlineFriend onlineFriend) {
      this.onlineFriend = onlineFriend;
   }
}
