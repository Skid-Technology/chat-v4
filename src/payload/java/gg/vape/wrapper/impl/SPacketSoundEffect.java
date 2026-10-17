package gg.vape.wrapper.impl;

public class SPacketSoundEffect extends Packet {
   public SPacketSoundEffect(Object handle) {
      super(handle);
   }

   public String getParticleName() {
      return ForgeVersion.MC_1_8_9.d()
         ? new EnumParticleTypes(vapeInstance.getMappings().Cs.getParticleType(this.I)).K()
         : vapeInstance.getMappings().Cs.getLegacyParticleName(this.I);
   }

   public double getZ() {
      return vapeInstance.getMappings().Cs.getZ(this.I);
   }

   public double getX() {
      return vapeInstance.getMappings().Cs.getX(this.I);
   }

   public double getY() {
      return vapeInstance.getMappings().Cs.getY(this.I);
   }
}
