package gg.vape.wrapper.impl;

import gg.vape.utils.MutableFloatTriple;
import gg.vape.wrapper.Wrapper;

public class CPacketPlayerBlockPlacement extends Wrapper {
   public float getFacingX() {
      return ForgeVersion.MC_1_16_5_ACTUAL.d() ? this.getFacingVector().getX() : vapeInstance.getMappings().D3.getFacingX(this.getObject());
   }

   public Object getHand() {
      if (ForgeVersion.MC_1_16_5_ACTUAL.d()) {
         return vapeInstance.getMappings().D3.getHand(this.I);
      } else {
         throw new UnsupportedOperationException("Unimplemented");
      }
   }

   public ItemStack getItemStack() {
      return ForgeVersion.MC_1_12_2.d() ? Minecraft.thePlayer().getHeldItemHand() : new ItemStack(vapeInstance.getMappings().D3.getItemStack(this.I));
   }

   public BlockRayTraceResult getBlockHit() {
      if (ForgeVersion.MC_1_16_5_ACTUAL.d()) {
         return new BlockRayTraceResult(vapeInstance.getMappings().D3.getBlockHit(this.I));
      } else {
         throw new UnsupportedOperationException("Unimplemented");
      }
   }

   public int getSequence() {
      return ForgeVersion.MC_1_21_11.d() ? vapeInstance.getMappings().D3.getSequence(this.I) : -1;
   }

   public float getFacingZ() {
      return ForgeVersion.MC_1_16_5_ACTUAL.d() ? this.getFacingVector().getZ() : vapeInstance.getMappings().D3.getFacingZ(this.getObject());
   }

   public MutableFloatTriple getFacingVector() {
      if (ForgeVersion.MC_1_16_5_ACTUAL.d()) {
         BlockRayTraceResult blockHit = this.getBlockHit();
         BlockPos blockPosition = blockHit.getBlockPos();
         Vec3 hitLocation = blockHit.getHitVec();
         float facingX = (float)(hitLocation.getX() - blockPosition.getX());
         float facingY = (float)(hitLocation.getY() - blockPosition.getY());
         float facingZ = (float)(hitLocation.getZ() - blockPosition.getZ());
         return new MutableFloatTriple(facingX, facingY, facingZ);
      } else {
         return new MutableFloatTriple(this.getFacingX(), this.getFacingY(), this.getFacingZ());
      }
   }

   public float getFacingY() {
      return ForgeVersion.MC_1_16_5_ACTUAL.d() ? this.getFacingVector().getY() : vapeInstance.getMappings().D3.getFacingY(this.getObject());
   }

   private static UnsupportedOperationException propagateException(UnsupportedOperationException exception) {
      return exception;
   }

   public CPacketPlayerBlockPlacement(Object handle) {
      super(handle);
   }

   public BlockPos getBlockPosition() {
      return ForgeVersion.MC_1_16_5_ACTUAL.d()
         ? this.getBlockHit().getBlockPos()
         : new BlockPos(vapeInstance.getMappings().D3.getBlockPosition(this.getObject()));
   }

   public int getPlacedBlockDirection() {
      return ForgeVersion.MC_1_16_5_ACTUAL.d() ? this.getBlockHit().Z() : vapeInstance.getMappings().D3.getPlacedBlockDirection(this.getObject());
   }
}
