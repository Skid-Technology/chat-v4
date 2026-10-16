package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MSPacketAnimation extends Mapping {
   private MappingField animationTypeField;
   private MappingField entityIdField;

   public int getEntityId(Object packet) {
      return this.entityIdField.getInt(packet);
   }

   public MSPacketAnimation() {
      this(MSPacketEntityVelocity.getPacketMappingControlFlowState());
   }

   private MSPacketAnimation(int[] controlFlowState) {
      super(MappedClasses.ZQ);
      if (controlFlowState == null) {
         this.animationTypeField = this.J("type", true, int.class);
      } else {
         if (ForgeVersion.MC_1_7_10.L() && !Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
            this.entityIdField = this.J("field_148981_a", Wrapper.isNativeAvailable, int.class);
            this.animationTypeField = this.J("field_148980_b", Wrapper.isNativeAvailable, int.class);
         } else {
            this.entityIdField = this.J("entityId", true, int.class);
            this.animationTypeField = this.J("type", true, int.class);
         }
      }
   }

   public int getAnimationType(Object packet) {
      return this.animationTypeField.getInt(packet);
   }
}
