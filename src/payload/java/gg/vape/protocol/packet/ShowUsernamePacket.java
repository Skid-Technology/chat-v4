package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;

public class ShowUsernamePacket implements ZeusSerializablePacket {
   private boolean showUsername;

   @Override
   public void S(ZeusPacketBuffer zeusPacketBuffer) {
      this.showUsername = zeusPacketBuffer.readBoolean();
   }

   public ShowUsernamePacket(boolean showUsername) {
      this.showUsername = showUsername;
   }

   public ShowUsernamePacket() {
   }

   public boolean shouldShowUsername() {
      return this.showUsername;
   }

   @Override
   public void o(ZeusPacketBuffer zeusPacketBuffer) {
      zeusPacketBuffer.writeBoolean(this.showUsername);
   }
}
