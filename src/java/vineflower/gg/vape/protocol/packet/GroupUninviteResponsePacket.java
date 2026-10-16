package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;

public class GroupUninviteResponsePacket extends ZeusTrackedPacket<GroupUninvitePacket> {
   private GroupUninviteStatus status;

   @Override
   public void T(ZeusPacketBuffer gx_12) {
      gx_12.writeEnum(this.status);
   }

   public GroupUninviteStatus getStatus() {
      return this.status;
   }

   public GroupUninviteResponsePacket() {
   }

   @Override
   public void x(ZeusPacketBuffer gx_12) {
      this.status = gx_12.readEnum(GroupUninviteStatus.class);
   }

   public GroupUninviteResponsePacket(GroupUninvitePacket request, GroupUninviteStatus status) {
      super(request);
      this.status = status;
   }
}
