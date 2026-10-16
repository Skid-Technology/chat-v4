package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import org.jetbrains.annotations.Nullable;

public class FriendServerAddressPacket implements ZeusSerializablePacket {
   private long userId;
   private String serverAddress;

   public FriendServerAddressPacket(UserModel userModel, String serverAddress) {
      this.userId = userModel.getId();
      this.serverAddress = serverAddress;
   }

   public long getUserId() {
      return this.userId;
   }

   public FriendServerAddressPacket() {
   }

   @Override
   public void o(ZeusPacketBuffer zeusPacketBuffer) {
      zeusPacketBuffer.writeLong(this.userId);
      boolean hasServerAddress = this.serverAddress != null;
      zeusPacketBuffer.writeBoolean(hasServerAddress);
      if (this.serverAddress != null) {
         zeusPacketBuffer.writeString(this.serverAddress);
      }
   }

   @Nullable
   public String getServerAddress() {
      return this.serverAddress;
   }

   @Override
   public void S(ZeusPacketBuffer zeusPacketBuffer) {
      this.userId = zeusPacketBuffer.readLong();
      if (zeusPacketBuffer.readBoolean()) {
         this.serverAddress = zeusPacketBuffer.readString(128);
      }
   }
}
