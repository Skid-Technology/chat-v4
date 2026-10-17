package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;

public class GroupLeavePacket extends ZeusTrackedPacket<GroupLeaveResponsePacket> {
   private static int[] z;

   @Override
   public void x(ZeusPacketBuffer gx_12) {
   }

   public static void b(int[] nArray) {
      z = nArray;
   }

   @Override
   public void T(ZeusPacketBuffer gx_12) {
   }

   public static int[] l() {
      return z;
   }

   static {
      if (l() != null) {
         b(new int[4]);
      }
   }
}
