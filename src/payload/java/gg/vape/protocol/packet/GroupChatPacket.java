package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;

public class GroupChatPacket extends ZeusTrackedPacket<GroupChatResponsePacket> {
   private String message;

   public GroupChatPacket(String message) {
      this.message = message;
   }

   public String getMessage() {
      return this.message;
   }

   public GroupChatPacket() {
   }

   @Override
   public void x(ZeusPacketBuffer gx_12) {
      this.message = gx_12.readString(255);
   }

   @Override
   public void T(ZeusPacketBuffer gx_12) {
      gx_12.writeString(this.message);
   }
}
