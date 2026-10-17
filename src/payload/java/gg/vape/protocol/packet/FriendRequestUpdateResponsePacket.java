package gg.vape.protocol.packet;

import gg.vape.friend.FriendModel;
import gg.vape.protocol.ZeusPacketBuffer;

public class FriendRequestUpdateResponsePacket extends ZeusTrackedPacket<FriendRequestUpdatePacket> {
   private FriendModel updatedFriend;
   private FriendRequestUpdateStatus status;
   private static boolean l;
   private long g;

   public static boolean z() {
      boolean bl = v();
      return !bl;
   }

   public long getRequestId() {
      return this.g;
   }

   public FriendRequestUpdateResponsePacket() {
   }

   public FriendRequestUpdateStatus getStatus() {
      return this.status;
   }

   public FriendModel getUpdatedFriend() {
      return this.updatedFriend;
   }

   public static boolean v() {
      return l;
   }

   public FriendRequestUpdateResponsePacket(FriendRequestUpdatePacket friendRequestUpdatePacket, long requestId, FriendRequestUpdateStatus status) {
      super(friendRequestUpdatePacket);
      this.g = requestId;
      this.status = status;
   }

   public static void Y(boolean bl) {
      l = bl;
   }

   @Override
   public void T(ZeusPacketBuffer zeusPacketBuffer) {
      zeusPacketBuffer.writeLong(this.g);
      zeusPacketBuffer.writeEnum(this.status);
      if (this.status == FriendRequestUpdateStatus.ACCEPTED) {
         this.updatedFriend.writeTo(zeusPacketBuffer);
      }
   }

   public FriendRequestUpdateResponsePacket(FriendRequestUpdatePacket friendRequestUpdatePacket, long requestId, FriendModel updatedFriend) {
      this(friendRequestUpdatePacket, requestId, FriendRequestUpdateStatus.ACCEPTED);
      this.updatedFriend = updatedFriend;
   }

   @Override
   public void x(ZeusPacketBuffer zeusPacketBuffer) {
      this.g = zeusPacketBuffer.readLong();
      this.status = zeusPacketBuffer.readEnum(FriendRequestUpdateStatus.class);
      if (this.status == FriendRequestUpdateStatus.ACCEPTED) {
         this.updatedFriend = new FriendModel(zeusPacketBuffer);
      }
   }

   static {
      if (z()) {
         Y(true);
      }
   }
}
