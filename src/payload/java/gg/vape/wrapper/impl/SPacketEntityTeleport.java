package gg.vape.wrapper.impl;

public class SPacketEntityTeleport extends Packet {
   public SPacketEntityTeleport(Object handle) {
      super(handle);
   }

   public int getZ() {
      return vapeInstance.getMappings().qH.getZ(this.I);
   }

   public byte getYaw() {
      return vapeInstance.getMappings().qH.getYaw(this.I);
   }

   public byte getPitch() {
      return vapeInstance.getMappings().qH.getPitch(this.I);
   }

   public int getY() {
      return vapeInstance.getMappings().qH.getY(this.I);
   }

   public int getEntityId() {
      return vapeInstance.getMappings().qH.getEntityId(this.I);
   }

   public int getX() {
      return vapeInstance.getMappings().qH.getX(this.I);
   }
}
