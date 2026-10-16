package gg.vape.protocol.event;

import gg.vape.friend.FriendRequestModel;
import gg.vape.protocol.ZeusClient;

public class FriendRequestReceivedEvent extends UserOnlineEvent {
   public FriendRequestReceivedEvent(ZeusClient zeusClient, FriendRequestModel friendRequestModel) {
      super(zeusClient, friendRequestModel);
   }
}
