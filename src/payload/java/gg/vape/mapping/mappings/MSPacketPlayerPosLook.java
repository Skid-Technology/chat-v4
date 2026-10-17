package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Set;

public class MSPacketPlayerPosLook extends Mapping {
   private MappingField yawField;
   private MappingField zField;
   private MappingMethod getRelativeFlagsMethod;
   private MappingField xField;
   private MappingField relativesField;
   private MappingField yField;
   private MappingField teleportIdField;
   private MappingField pitchField;
   private MappingField changeField;

   public int getTeleportId(Object packet) {
      return this.teleportIdField.getInt(packet);
   }

   public Object getChange(Object packet) {
      return this.changeField.getObject(packet);
   }

   public Object getRelatives(Object packet) {
      return this.relativesField.getObject(packet);
   }

   public MSPacketPlayerPosLook() {
      this(MSPacketEntityVelocity.getPacketMappingControlFlowState());
   }

   private MSPacketPlayerPosLook(int[] controlFlowState) {
      super(MappedClasses.zw);
      if (ForgeVersion.MC_1_21_4.d()) {
         this.changeField = this.J("change", true, MappedClasses.Dd);
         this.relativesField = this.J("relatives", true, Set.class);
         this.teleportIdField = this.J("id", true, int.class);
      } else {
         if (ForgeVersion.MC_1_7_10.Y()) {
            this.xField = this.J("x", true, double.class);
            this.yField = this.J("y", true, double.class);
            this.zField = this.J("z", true, double.class);
         } else {
            this.xField = this.J("field_148940_a", Wrapper.isNativeAvailable, double.class);
            this.yField = this.J("field_148938_b", Wrapper.isNativeAvailable, double.class);
            this.zField = this.J("field_148939_c", Wrapper.isNativeAvailable, double.class);
         }

         if (ForgeVersion.MC_1_7_10.Y()) {
            this.getRelativeFlagsMethod = this.Y("func_179834_f", Wrapper.isNativeAvailable, Set.class);
         }

         if (ForgeVersion.MC_1_8_9.Y()) {
            this.teleportIdField = this.J("teleportId", true, int.class);
         }

         if (Vape.INSTANCE.isVanillaMinecraftPresent() && ForgeVersion.MC_1_7_10.Y()) {
            this.yawField = this.J("yaw", true, float.class);
            this.pitchField = this.J("pitch", true, float.class);
         } else {
            this.yawField = this.J("field_148936_d", Wrapper.isNativeAvailable, float.class);
            this.pitchField = this.J("field_148937_e", Wrapper.isNativeAvailable, float.class);
         }
      }
   }

   public double getZ(Object packet) {
      return this.zField.getDouble(packet);
   }

   public float getYaw(Object packet) {
      return this.yawField.getFloat(packet);
   }

   public double getX(Object packet) {
      return this.xField.getDouble(packet);
   }

   public float getPitch(Object packet) {
      return this.pitchField.getFloat(packet);
   }

   public void setPitch(Object packet, float pitch) {
      this.pitchField.setFloat(packet, pitch);
   }

   public void setYaw(Object packet, float yaw) {
      this.yawField.setFloat(packet, yaw);
   }

   public double getY(Object packet) {
      return this.yField.getDouble(packet);
   }

   public Object getRelativeFlags(Object packet) {
      return this.getRelativeFlagsMethod.invokeObject(packet);
   }
}
