package gg.vape.protocol.packet;

import gg.vape.friend.FriendModel;
import gg.vape.friend.FriendRequestModel;
import gg.vape.protocol.ZeusPacketBuffer;
import java.util.ArrayList;
import java.util.List;

public class FriendsListResponsePacket extends ZeusTrackedPacket<FriendsListPacket> {
   private final List<FriendModel> friends = new ArrayList<>();
   private final List<FriendRequestModel> incomingRequests = new ArrayList<>();
   private final List<FriendRequestModel> outgoingRequests = new ArrayList<>();

   @Override
   public void T(ZeusPacketBuffer gx_12) {
      gx_12.writeInt(this.friends.size());

      for (FriendModel object : this.friends) {
         object.writeTo(gx_12);
      }

      gx_12.writeInt(this.incomingRequests.size());

      for (FriendRequestModel friendRequestModel : this.incomingRequests) {
         friendRequestModel.writeTo(gx_12);
      }

      gx_12.writeInt(this.outgoingRequests.size());

      for (FriendRequestModel friendRequestModel : this.outgoingRequests) {
         friendRequestModel.writeTo(gx_12);
      }
   }

   public FriendsListResponsePacket(FriendsListPacket g4, List<FriendModel> list, List<FriendRequestModel> list2, List<FriendRequestModel> list3) {
      super(g4);
      this.friends.addAll(list);
      this.incomingRequests.addAll(list2);
      this.outgoingRequests.addAll(list3);
   }

   @Override
   public void x(ZeusPacketBuffer gx_12) {
      int n3 = gx_12.readInt();

      for (int n2 = 0; n2 < n3; n2++) {
         FriendModel lq_22 = new FriendModel(gx_12);
         this.friends.add(lq_22);
      }

      int var7 = gx_12.readInt();

      for (int n = 0; n < var7; n++) {
         this.incomingRequests.add(new FriendRequestModel(gx_12));
      }

      int var6 = gx_12.readInt();

      for (int i = 0; i < var6; i++) {
         this.outgoingRequests.add(new FriendRequestModel(gx_12));
      }
   }

   public FriendsListResponsePacket() {
   }

   public List<FriendModel> getFriends() {
      return this.friends;
   }

   public List<FriendRequestModel> getOutgoingRequests() {
      return this.outgoingRequests;
   }

   public List<FriendRequestModel> getIncomingRequests() {
      return this.incomingRequests;
   }
}
