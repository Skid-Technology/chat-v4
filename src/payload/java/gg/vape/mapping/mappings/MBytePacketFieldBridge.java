package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.impl.ForgeVersion;

public class MBytePacketFieldBridge extends Mapping {
   private final MappingField headYawField;

   public MBytePacketFieldBridge() {
      this(MSPacketEntityVelocity.getPacketMappingControlFlowState());
   }

   private MBytePacketFieldBridge(int[] controlFlowState) {
      super(MappedClasses.D7);
      if (controlFlowState != null) {
         if (ForgeVersion.MC_1_16_5.d()) {
            this.headYawField = this.J("yHeadRot", true, byte.class);
         } else if (ForgeVersion.MC_1_7_10.L()) {
            this.headYawField = this.J("field_149383_b", true, byte.class);
         } else {
            this.headYawField = this.J("yaw", true, byte.class);
         }
      } else {
         if (ForgeVersion.MC_1_16_5.d()) {
            this.J("field_149383_b", true, byte.class);
         }

         this.headYawField = this.J("yaw", true, byte.class);
      }
   }

   public byte getHeadYaw(Object packet) {
      return (byte)this.headYawField.getInt(packet);
   }
}
