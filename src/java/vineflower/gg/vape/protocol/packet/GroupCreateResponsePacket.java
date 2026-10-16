package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;

public class GroupCreateResponsePacket extends ZeusTrackedPacket<GroupCreatePacket> {
   private GroupCreateStatus status;
   private static String X;

   public GroupCreateStatus getStatus() {
      return this.status;
   }

   public GroupCreateResponsePacket(GroupCreatePacket groupCreatePacket, GroupCreateStatus status) {
      super(groupCreatePacket);
      this.status = status;
   }

   public static void m(String string) {
      X = string;
   }

   public static String Z() {
      return X;
   }

   @Override
   public void T(ZeusPacketBuffer zeusPacketBuffer) {
      zeusPacketBuffer.writeEnum(this.status);
   }

   @Override
   public void x(ZeusPacketBuffer zeusPacketBuffer) {
      this.status = zeusPacketBuffer.readEnum(GroupCreateStatus.class);
   }

   public GroupCreateResponsePacket() {
   }

   static {
      if (Z() != null) {
         m("koPAI");
      }
   }
}
