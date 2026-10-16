package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;

public class FriendsListPacket extends ZeusTrackedPacket<FriendsListResponsePacket> {
   private static boolean K;

   public static boolean s() {
      return K;
   }

   @Override
   public void x(ZeusPacketBuffer zeusPacketBuffer) {
   }

   public static void m(boolean bl) {
      K = bl;
   }

   @Override
   public void T(ZeusPacketBuffer zeusPacketBuffer) {
   }

   public static boolean e() {
      boolean bl = s();
      return !bl;
   }

   static {
      if (e()) {
         m(true);
      }
   }
}
