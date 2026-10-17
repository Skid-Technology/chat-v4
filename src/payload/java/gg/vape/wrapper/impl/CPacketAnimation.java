package gg.vape.wrapper.impl;

public class CPacketAnimation extends Packet {
   public static CPacketAnimation create() {
      return ForgeVersion.MC_1_12_2.d()
         ? new CPacketAnimation(vapeInstance.getMappingsMapperCompat().Q3.createAnimationPacket(EnumHand.mainHand().getObject()))
         : new CPacketAnimation(vapeInstance.getMappingsMapperCompat().Q3.createAnimationPacket());
   }

   public CPacketAnimation(Object handle) {
      super(handle);
   }
}
