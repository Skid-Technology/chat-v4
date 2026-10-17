package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;

public class ZeusClientReadyPacket implements ZeusSerializablePacket {
   private static boolean Q;

   public static boolean X() {
      boolean bl = q();
      return true;
   }

   @Override
   public void S(ZeusPacketBuffer zeusPacketBuffer) {
   }

   public static void y(boolean bl) {
      Q = bl;
   }

   @Override
   public void o(ZeusPacketBuffer zeusPacketBuffer) {
   }

   public static boolean q() {
      return Q;
   }

   static {
      if (q()) {
         y(true);
      }
   }
}
