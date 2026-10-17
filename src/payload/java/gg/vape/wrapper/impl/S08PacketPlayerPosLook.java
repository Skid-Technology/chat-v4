package gg.vape.wrapper.impl;

public class S08PacketPlayerPosLook extends Packet {
   public void setPitch(float pitch) {
      vapeInstance.getMappingsMapperCompat().q9.setPitch(this.I, pitch);
   }

   public void setYaw(float yaw) {
      vapeInstance.getMappingsMapperCompat().q9.setYaw(this.I, yaw);
   }

   public S08PacketPlayerPosLook(Object handle) {
      super(handle);
   }

   public float getPitch() {
      return vapeInstance.getMappingsMapperCompat().q9.getPitch(this.I);
   }

   public float getYaw() {
      return vapeInstance.getMappingsMapperCompat().q9.getYaw(this.I);
   }
}
