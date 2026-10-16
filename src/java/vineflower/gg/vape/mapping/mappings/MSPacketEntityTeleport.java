package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MSPacketEntityTeleport extends Mapping {
   private MappingField entityIdField;
   private MappingField zField;
   private MappingField xField;
   private MappingField yField;
   private MappingField yawField;
   private MappingField pitchField;

   public int getY(Object packet) {
      return ForgeVersion.MC_1_21_4.d() ? (int)this.yField.getDouble(packet) : this.yField.getInt(packet);
   }

   public byte getYaw(Object packet) {
      return (byte)this.yawField.getInt(packet);
   }

   public int getZ(Object packet) {
      return ForgeVersion.MC_1_21_4.d() ? (int)this.zField.getDouble(packet) : this.zField.getInt(packet);
   }

   public byte getPitch(Object packet) {
      return (byte)this.pitchField.getInt(packet);
   }

   public int getEntityId(Object packet) {
      return this.entityIdField.getInt(packet);
   }

   public int getX(Object packet) {
      return ForgeVersion.MC_1_21_4.d() ? (int)this.xField.getDouble(packet) : this.xField.getInt(packet);
   }

   public MSPacketEntityTeleport() {
      this(MSPacketEntityVelocity.getPacketMappingControlFlowState());
   }

   private MSPacketEntityTeleport(int[] controlFlowState) {
      super(MappedClasses.uW);
      if (ForgeVersion.MC_1_7_10.L()) {
         this.entityIdField = this.J("field_148957_a", Wrapper.isNativeAvailable, int.class);
         this.xField = this.J("field_148956_c", Wrapper.isNativeAvailable, int.class);
         this.yField = this.J("field_148953_d", Wrapper.isNativeAvailable, int.class);
         this.zField = this.J("field_148954_e", Wrapper.isNativeAvailable, int.class);
         this.yawField = this.J("field_148951_f", Wrapper.isNativeAvailable, byte.class);
         this.pitchField = this.J("field_148952_g", Wrapper.isNativeAvailable, byte.class);
      } else if (ForgeVersion.MC_1_16_5.d()) {
         this.entityIdField = this.J("entityId", true, int.class);
         if (ForgeVersion.MC_1_17.d()) {
            this.xField = this.J("x", true, double.class);
            this.yField = this.J("y", true, double.class);
            this.zField = this.J("z", true, double.class);
         } else {
            this.xField = this.J("x", true, double.class);
            this.yField = this.J("y", true, double.class);
            this.zField = this.J("z", true, double.class);
         }

         this.yawField = this.J("yRot", true, byte.class);
         this.pitchField = this.J("xRot", true, byte.class);
      } else {
         this.entityIdField = this.J("entityId", true, int.class);
         this.xField = this.J("x", true, int.class);
         this.yField = this.J("y", true, int.class);
         this.zField = this.J("z", true, int.class);
         this.yawField = this.J("yaw", true, byte.class);
         this.pitchField = this.J("pitch", true, byte.class);
      }
   }
}
