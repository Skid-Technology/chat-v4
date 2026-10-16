package gg.vape.protocol.event;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusClient;

public class FriendRemovedEvent extends OnlineEventPayloadBase {
   public FriendRemovedEvent(ZeusClient zeusClient, UserModel userModel) {
      super(zeusClient, userModel);
   }
}
