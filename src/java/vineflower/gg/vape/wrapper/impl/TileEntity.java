package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class TileEntity extends Wrapper {
   private static boolean usesLegacyCoordinates;

   public TileEntity(Object wrappedObject) {
      super(wrappedObject);
   }

   public BlockPos getBlockPos() {
      return new BlockPos(vapeInstance.getMappingsMapperCompat().tileEntity.getBlockPos(this.I));
   }

   public int getX() {
      return usesLegacyCoordinates ? vapeInstance.getMappingsMapperCompat().tileEntity.getX(this.I) : this.getBlockPos().getX();
   }

   public static boolean setUsesLegacyCoordinates(boolean legacyCoordinates) {
      usesLegacyCoordinates = legacyCoordinates;
      return usesLegacyCoordinates;
   }

   public int getY() {
      return usesLegacyCoordinates ? vapeInstance.getMappingsMapperCompat().tileEntity.getY(this.I) : this.getBlockPos().getY();
   }

   public int getZ() {
      return usesLegacyCoordinates ? vapeInstance.getMappingsMapperCompat().tileEntity.getZ(this.I) : this.getBlockPos().getZ();
   }
}
