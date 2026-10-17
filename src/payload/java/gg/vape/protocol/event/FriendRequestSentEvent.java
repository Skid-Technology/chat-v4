package gg.vape.protocol.event;

import gg.vape.friend.FriendRequestModel;
import gg.vape.protocol.ZeusClient;

public class FriendRequestSentEvent extends UserOnlineEvent {
   public FriendRequestSentEvent(ZeusClient zeusClient, FriendRequestModel friendRequestModel) {
      super(zeusClient, friendRequestModel);
   }
}
