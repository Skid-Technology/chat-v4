package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MSPacketSoundEffect extends Mapping {
   private final MappingField zField;
   private final MappingField xField;
   private final MappingField yField;
   private MappingField legacyParticleNameField;
   private MappingField particleTypeField;

   public double getX(Object packet) {
      return ForgeVersion.MC_1_16_5.d() ? this.xField.getDouble(packet) : this.xField.getFloat(packet);
   }

   public String getLegacyParticleName(Object packet) {
      return (String)this.legacyParticleNameField.getObject(packet);
   }

   public double getY(Object packet) {
      return ForgeVersion.MC_1_16_5.d() ? this.yField.getDouble(packet) : this.yField.getFloat(packet);
   }

   public double getZ(Object packet) {
      return ForgeVersion.MC_1_16_5.d() ? this.zField.getDouble(packet) : this.zField.getFloat(packet);
   }

   public Object getParticleType(Object packet) {
      return this.particleTypeField.getObject(packet);
   }

   public MSPacketSoundEffect() {
      this(MSPacketEntityVelocity.getPacketMappingControlFlowState());
   }

   private MSPacketSoundEffect(int[] controlFlowState) {
      super(MappedClasses.Dk);
      if (controlFlowState != null) {
         if (ForgeVersion.MC_1_16_5.d()) {
            this.particleTypeField = this.J("particle", true, MappedClasses.lZ);
            this.xField = this.J("xCoord", true, double.class);
            this.yField = this.J("yCoord", true, double.class);
            this.zField = this.J("zCoord", true, double.class);
         } else if (ForgeVersion.MC_1_8_9.d()) {
            this.particleTypeField = this.J("particleType", true, MappedClasses.qi);
            this.xField = this.J("xCoord", true, float.class);
            this.yField = this.J("yCoord", true, float.class);
            this.zField = this.J("zCoord", true, float.class);
         } else {
            this.legacyParticleNameField = this.J("field_149236_a", Wrapper.isNativeAvailable, String.class);
            this.xField = this.J("field_149234_b", Wrapper.isNativeAvailable, float.class);
            this.yField = this.J("field_149235_c", Wrapper.isNativeAvailable, float.class);
            this.zField = this.J("field_149232_d", Wrapper.isNativeAvailable, float.class);
         }
      } else {
         if (ForgeVersion.MC_1_16_5.d()) {
            this.particleTypeField = this.J("particleType", true, MappedClasses.qi);
            this.J("xCoord", true, float.class);
            this.J("yCoord", true, float.class);
            this.J("zCoord", true, float.class);
         }

         this.legacyParticleNameField = this.J("field_149236_a", Wrapper.isNativeAvailable, String.class);
         this.xField = this.J("field_149234_b", Wrapper.isNativeAvailable, float.class);
         this.yField = this.J("field_149235_c", Wrapper.isNativeAvailable, float.class);
         this.zField = this.J("field_149232_d", Wrapper.isNativeAvailable, float.class);
      }
   }
}
