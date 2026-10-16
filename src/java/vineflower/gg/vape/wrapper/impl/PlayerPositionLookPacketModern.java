package gg.vape.wrapper.impl;

import java.util.Set;

public class PlayerPositionLookPacketModern extends Packet {
   public PositionMoveRotation getChange() {
      return new PositionMoveRotation(vapeInstance.getMappings().Ry.getChange(this.I));
   }

   public Set getRelatives() {
      return (Set)vapeInstance.getMappings().Ry.getRelatives(this.I);
   }

   public void setPitch(float pitch) {
      if (ForgeVersion.MC_1_21_4.d()) {
         PositionMoveRotation change = this.getChange();
         change.setPitch(pitch);
      } else {
         vapeInstance.getMappings().Ry.setPitch(this.I, pitch);
      }
   }

   public float getPitch() {
      if (ForgeVersion.MC_1_21_4.d()) {
         PositionMoveRotation change = this.getChange();
         return change.getPitch();
      } else {
         return vapeInstance.getMappings().Ry.getPitch(this.I);
      }
   }

   public Set getRelativeFlags() {
      return ForgeVersion.MC_1_21_4.d() ? this.getRelatives() : (Set)vapeInstance.getMappings().Ry.getRelativeFlags(this.I);
   }

   public double getY() {
      if (ForgeVersion.MC_1_21_4.d()) {
         PositionMoveRotation change = this.getChange();
         return change.getPosition().getY();
      } else {
         return vapeInstance.getMappings().Ry.getY(this.I);
      }
   }

   public double getX() {
      if (ForgeVersion.MC_1_21_4.d()) {
         PositionMoveRotation change = this.getChange();
         return change.getPosition().getX();
      } else {
         return vapeInstance.getMappings().Ry.getX(this.I);
      }
   }

   public PlayerPositionLookPacketModern(Object handle) {
      super(handle);
   }

   public float getYaw() {
      if (ForgeVersion.MC_1_21_4.d()) {
         PositionMoveRotation change = this.getChange();
         return change.getYaw();
      } else {
         return vapeInstance.getMappings().Ry.getYaw(this.I);
      }
   }

   public void setYaw(float yaw) {
      if (ForgeVersion.MC_1_21_4.d()) {
         PositionMoveRotation change = this.getChange();
         change.setYaw(yaw);
      } else {
         vapeInstance.getMappings().Ry.setYaw(this.I, yaw);
      }
   }

   public int getTeleportId() {
      return vapeInstance.getMappings().Ry.getTeleportId(this.I);
   }

   public double getZ() {
      if (ForgeVersion.MC_1_21_4.d()) {
         PositionMoveRotation change = this.getChange();
         return change.getPosition().getZ();
      } else {
         return vapeInstance.getMappings().Ry.getZ(this.I);
      }
   }
}
