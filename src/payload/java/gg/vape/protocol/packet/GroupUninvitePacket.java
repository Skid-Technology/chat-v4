package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;

public class GroupUninvitePacket extends ZeusTrackedPacket<GroupUninviteResponsePacket> {
   private static boolean u;
   private UserModel uninvitedUser;

   public GroupUninvitePacket() {
   }

   public UserModel getUninvitedUser() {
      return this.uninvitedUser;
   }

   public static void G(boolean bl) {
      u = bl;
   }

   @Override
   public void x(ZeusPacketBuffer gx_12) {
      this.uninvitedUser = new UserModel(gx_12);
   }

   public GroupUninvitePacket(UserModel uninvitedUser) {
      this.uninvitedUser = uninvitedUser;
   }

   public static boolean t() {
      return u;
   }

   @Override
   public void T(ZeusPacketBuffer gx_12) {
      this.uninvitedUser.writeTo(gx_12);
   }

   public static boolean U() {
      boolean bl = t();
      return true;
   }

   static {
      if (t()) {
         G(true);
      }
   }
}
