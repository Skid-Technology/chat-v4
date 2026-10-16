package gg.vape.wrapper.impl;

public class SPacketBlockChange extends Packet {
   public SPacketBlockChange(Object handle) {
      super(handle);
   }

   public int getLegacyX() {
      return vapeInstance.getMappings().CN.getLegacyX(this.I);
   }

   public BlockState getBlockState() {
      return new BlockState(vapeInstance.getMappings().CN.getBlockState(this.I));
   }

   public BlockPos getBlockPosition() {
      return new BlockPos(vapeInstance.getMappings().CN.getBlockPosition(this.I));
   }

   public int getLegacyY() {
      return vapeInstance.getMappings().CN.getLegacyY(this.I);
   }

   public int getLegacyZ() {
      return vapeInstance.getMappings().CN.getLegacyZ(this.I);
   }
}
