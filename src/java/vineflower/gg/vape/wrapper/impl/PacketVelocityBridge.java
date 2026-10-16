package gg.vape.wrapper.impl;

import java.util.Optional;

public class PacketVelocityBridge extends Packet {
   private void setPlayerKnockbackX(float motionX) {
      Optional<Object> playerKnockback = this.getPlayerKnockback();
      if (playerKnockback.isPresent()) {
         Vec3 knockback = new Vec3(playerKnockback.get());
         knockback.N(motionX);
      }
   }

   public void setMotionY(float motionY) {
      if (ForgeVersion.MC_1_21_0.d()) {
         this.setPlayerKnockbackY(motionY);
      } else {
         vapeInstance.getMappingsMapperCompat().qO.setMotionY(this.I, motionY);
      }
   }

   public float getMotionZ() {
      return ForgeVersion.MC_1_21_0.d() ? this.getPlayerKnockbackZ() : vapeInstance.getMappingsMapperCompat().qO.getMotionZ(this.I);
   }

   private float getPlayerKnockbackZ() {
      Optional<Object> playerKnockback = this.getPlayerKnockback();
      if (playerKnockback.isPresent()) {
         Vec3 knockback = new Vec3(playerKnockback.get());
         return (float)knockback.getZ();
      } else {
         return 0.0F;
      }
   }

   public void setMotionX(float motionX) {
      if (ForgeVersion.MC_1_21_0.d()) {
         this.setPlayerKnockbackX(motionX);
      } else {
         vapeInstance.getMappingsMapperCompat().qO.setMotionX(this.I, motionX);
      }
   }

   public float getMotionY() {
      return ForgeVersion.MC_1_21_0.d() ? this.getPlayerKnockbackY() : vapeInstance.getMappingsMapperCompat().qO.getMotionY(this.I);
   }

   public PacketVelocityBridge(Object handle) {
      super(handle);
   }

   private void setPlayerKnockbackZ(float motionZ) {
      Optional<Object> playerKnockback = this.getPlayerKnockback();
      if (playerKnockback.isPresent()) {
         Vec3 knockback = new Vec3(playerKnockback.get());
         knockback.Z(motionZ);
      }
   }

   public void setMotionZ(float motionZ) {
      if (ForgeVersion.MC_1_21_0.d()) {
         this.setPlayerKnockbackZ(motionZ);
      } else {
         vapeInstance.getMappingsMapperCompat().qO.setMotionZ(this.I, motionZ);
      }
   }

   private float getPlayerKnockbackX() {
      Optional<Object> playerKnockback = this.getPlayerKnockback();
      if (playerKnockback.isPresent()) {
         Vec3 knockback = new Vec3(playerKnockback.get());
         return (float)knockback.getX();
      } else {
         return 0.0F;
      }
   }

   private void setPlayerKnockbackY(float motionY) {
      Optional<Object> playerKnockback = this.getPlayerKnockback();
      if (playerKnockback.isPresent()) {
         Vec3 knockback = new Vec3(playerKnockback.get());
         knockback.m(motionY);
      }
   }

   private float getPlayerKnockbackY() {
      Optional<Object> playerKnockback = this.getPlayerKnockback();
      if (playerKnockback.isPresent()) {
         Vec3 knockback = new Vec3(playerKnockback.get());
         return (float)knockback.getY();
      } else {
         return 0.0F;
      }
   }

   public float getMotionX() {
      return ForgeVersion.MC_1_21_0.d() ? this.getPlayerKnockbackX() : vapeInstance.getMappingsMapperCompat().qO.getMotionX(this.I);
   }

   public Optional<Object> getPlayerKnockback() {
      return vapeInstance.getMappingsMapperCompat().qO.getPlayerKnockback(this.I);
   }
}
