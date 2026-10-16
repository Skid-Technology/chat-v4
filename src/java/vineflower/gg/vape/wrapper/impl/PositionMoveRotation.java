package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class PositionMoveRotation extends Wrapper {
   public Vec3 getDeltaMovement() {
      return new Vec3(vapeInstance.getMappingsMapperCompat().qT.getDeltaMovement(this.I));
   }

   public void setPitch(float pitch) {
      vapeInstance.getMappingsMapperCompat().qT.setPitch(this.I, pitch);
   }

   public void setYaw(float yaw) {
      vapeInstance.getMappingsMapperCompat().qT.setYaw(this.I, yaw);
   }

   public Vec3 getPosition() {
      return new Vec3(vapeInstance.getMappingsMapperCompat().qT.getPosition(this.I));
   }

   public float getPitch() {
      return vapeInstance.getMappingsMapperCompat().qT.getPitch(this.I);
   }

   public float getYaw() {
      return vapeInstance.getMappingsMapperCompat().qT.getYaw(this.I);
   }

   public PositionMoveRotation(Object handle) {
      super(handle);
   }
}
