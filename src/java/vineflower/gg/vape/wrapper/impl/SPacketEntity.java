package gg.vape.wrapper.impl;

public class SPacketEntity extends Packet {
   public int getZ() {
      if (ForgeVersion.MC_1_21_4.d()) {
         PositionMoveRotation change = this.getChange();
         return (int)change.getPosition().getZ();
      } else {
         return vapeInstance.getMappingsMapperCompat().qR.getZ(this.I);
      }
   }

   public SPacketEntity(Object handle) {
      super(handle);
   }

   public int getX() {
      if (ForgeVersion.MC_1_21_4.d()) {
         PositionMoveRotation change = this.getChange();
         return (int)change.getPosition().getX();
      } else {
         return vapeInstance.getMappingsMapperCompat().qR.getX(this.I);
      }
   }

   public byte getYaw() {
      if (ForgeVersion.MC_1_21_4.d()) {
         PositionMoveRotation change = this.getChange();
         return (byte)change.getYaw();
      } else {
         return vapeInstance.getMappingsMapperCompat().qR.getYaw(this.I);
      }
   }

   public int getY() {
      if (ForgeVersion.MC_1_21_4.d()) {
         PositionMoveRotation change = this.getChange();
         return (int)change.getPosition().getY();
      } else {
         return vapeInstance.getMappingsMapperCompat().qR.getY(this.I);
      }
   }

   public byte getPitch() {
      if (ForgeVersion.MC_1_21_4.d()) {
         PositionMoveRotation change = this.getChange();
         return (byte)change.getPitch();
      } else {
         return vapeInstance.getMappingsMapperCompat().qR.getPitch(this.I);
      }
   }

   public PositionMoveRotation getChange() {
      return new PositionMoveRotation(vapeInstance.getMappingsMapperCompat().qR.getChange(this.I));
   }

   public int getEntityId() {
      return vapeInstance.getMappingsMapperCompat().qR.getEntityId(this.I);
   }
}
