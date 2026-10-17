package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MSPacketEntity extends Mapping {
   private MappingField changeField;
   private MappingField entityIdField;
   private MappingField yawField;
   private MappingField zField;
   private MappingField pitchField;
   private MappingField xField;
   private MappingField yField;

   public Object getChange(Object packet) {
      return this.changeField.getObject(packet);
   }

   public int getX(Object packet) {
      return this.xField.getInt(packet);
   }

   public byte getYaw(Object packet) {
      return (byte)this.yawField.getInt(packet);
   }

   public int getY(Object packet) {
      return this.yField.getInt(packet);
   }

   public int getZ(Object packet) {
      return this.zField.getInt(packet);
   }

   public int getEntityId(Object packet) {
      return this.entityIdField.getInt(packet);
   }

   public MSPacketEntity() {
      this(MSPacketEntityVelocity.getPacketMappingControlFlowState());
   }

   private MSPacketEntity(int[] controlFlowState) {
      super(MappedClasses.s);
      if (ForgeVersion.MC_1_21_4.d()) {
         this.changeField = this.J("change", true, MappedClasses.Dd);
         this.entityIdField = this.J("id", true, int.class);
      } else if (ForgeVersion.MC_1_7_10.L()) {
         this.entityIdField = this.J("field_149458_a", Wrapper.isNativeAvailable, int.class);
         this.xField = this.J("field_149456_b", Wrapper.isNativeAvailable, int.class);
         this.yField = this.J("field_149457_c", Wrapper.isNativeAvailable, int.class);
         this.zField = this.J("field_149454_d", Wrapper.isNativeAvailable, int.class);
         this.yawField = this.J("field_149455_e", Wrapper.isNativeAvailable, byte.class);
         this.pitchField = this.J("field_149453_f", Wrapper.isNativeAvailable, byte.class);
      } else if (ForgeVersion.MC_1_16_5.d()) {
         this.entityIdField = this.J("id", true, int.class);
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
         this.xField = this.J("posX", true, int.class);
         this.yField = this.J("posY", true, int.class);
         this.zField = this.J("posZ", true, int.class);
         this.yawField = this.J("yaw", true, byte.class);
         this.pitchField = this.J("pitch", true, byte.class);
      }
   }

   public byte getPitch(Object packet) {
      return (byte)this.pitchField.getInt(packet);
   }
}
