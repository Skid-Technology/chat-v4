package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.PresenceState;
import gg.vape.protocol.ZeusPacketBuffer;

public class ServerFriendPresenceStatePacket implements ZeusSerializablePacket {
   private UserModel user;
   private PresenceState presenceState;
   private static boolean f;

   public PresenceState getPresenceState() {
      return this.presenceState;
   }

   @Override
   public void o(ZeusPacketBuffer zeusPacketBuffer) {
      this.user.writeTo(zeusPacketBuffer);
      zeusPacketBuffer.writeEnum(this.presenceState);
   }

   public ServerFriendPresenceStatePacket(UserModel user, PresenceState presenceState) {
      this.user = user;
      this.presenceState = presenceState;
   }

   public static void r(boolean bl) {
      f = bl;
   }

   public UserModel getUser() {
      return this.user;
   }

   public static boolean d() {
      boolean bl = L();
      return true;
   }

   @Override
   public void S(ZeusPacketBuffer zeusPacketBuffer) {
      this.user = new UserModel(zeusPacketBuffer);
      this.presenceState = zeusPacketBuffer.readEnum(PresenceState.class);
   }

   public static boolean L() {
      return f;
   }

   public ServerFriendPresenceStatePacket() {
   }

   static {
      if (L()) {
         r(true);
      }
   }
}
