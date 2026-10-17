package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;

public class GroupLeaveResponsePacket extends ZeusTrackedPacket<GroupLeavePacket> {
   private GroupLeaveStatus status;

   public GroupLeaveStatus getStatus() {
      return this.status;
   }

   @Override
   public void T(ZeusPacketBuffer gx_12) {
      gx_12.writeEnum(this.status);
   }

   @Override
   public void x(ZeusPacketBuffer gx_12) {
      this.status = gx_12.readEnum(GroupLeaveStatus.class);
   }

   public GroupLeaveResponsePacket() {
   }

   public GroupLeaveResponsePacket(GroupLeavePacket request, GroupLeaveStatus status) {
      super(request);
      this.status = status;
   }
}
