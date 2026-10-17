package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MCPacketPlayer extends Mapping {
   private final MappingField zField;
   private final MappingField hasPositionField;
   private final MappingMethod packetConstructor = ForgeVersion.MC_1_21_11.d()
      ? this.Y(
         "<init>",
         false,
         void.class,
         double.class,
         double.class,
         double.class,
         float.class,
         float.class,
         boolean.class,
         boolean.class,
         boolean.class,
         boolean.class
      )
      : (
         ForgeVersion.MC_1_20_6.d() && !ForgeVersion.MC_1_21_4.d()
            ? this.Y(
               "<init>", false, void.class, double.class, double.class, double.class, float.class, float.class, boolean.class, boolean.class, boolean.class
            )
            : this.Y("<init>", false, void.class, boolean.class)
      );
   private final MappingField pitchField;
   private final MappingField xField;
   private final MappingField yField;
   private final MappingField yawField;

   public MCPacketPlayer() {
      this(MPacketIdFactory.getPacketMappingControlFlowState());
   }

   private MCPacketPlayer(GuiComponent[] controlFlowState) {
      super(MappedClasses.qD);
      if (ForgeVersion.MC_1_7_10.L()) {
         if (Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
            this.xField = this.J("x", true, double.class);
            this.yField = this.J("y", true, double.class);
            this.zField = this.J("z", true, double.class);
            this.yawField = this.J("yaw", true, float.class);
            this.pitchField = this.J("pitch", true, float.class);
         } else {
            this.xField = this.J("field_149479_a", Wrapper.isNativeAvailable, double.class);
            this.yField = this.J("field_149477_b", Wrapper.isNativeAvailable, double.class);
            this.zField = this.J("field_149475_d", Wrapper.isNativeAvailable, double.class);
            this.yawField = this.J("field_149476_e", Wrapper.isNativeAvailable, float.class);
            this.pitchField = this.J("field_149473_f", Wrapper.isNativeAvailable, float.class);
         }

         this.hasPositionField = this.J("field_149480_h", Wrapper.isNativeAvailable, boolean.class);
      } else {
         this.xField = this.J("x", true, double.class);
         this.yField = this.J("y", true, double.class);
         this.zField = this.J("z", true, double.class);
         if (ForgeVersion.MC_1_16_5.d()) {
            this.yawField = this.J("yRot", true, float.class);
            this.pitchField = this.J("xRot", true, float.class);
         } else {
            this.yawField = this.J("yaw", true, float.class);
            this.pitchField = this.J("pitch", true, float.class);
         }

         this.hasPositionField = this.J("moving", true, boolean.class);
      }
   }

   public Object createPacket(boolean onGround) {
      return this.packetConstructor.newInstance(onGround);
   }

   public Object createPacket(double x, double y, double z, float yaw, float pitch, boolean onGround, boolean hasPosition, boolean hasRotation) {
      return this.packetConstructor.newInstance(x, y, z, yaw, pitch, onGround, hasPosition, hasRotation);
   }

   public boolean hasPosition(Object packet) {
      return this.hasPositionField.getBoolean(packet);
   }

   public float getPitch(Object packet) {
      return this.pitchField.getFloat(packet);
   }

   public float getYaw(Object packet) {
      return this.yawField.getFloat(packet);
   }

   public double getY(Object packet) {
      return this.yField.getDouble(packet);
   }

   public Object createPacket(
      double x, double y, double z, float yaw, float pitch, boolean onGround, boolean horizontalCollision, boolean hasPosition, boolean hasRotation
   ) {
      return this.packetConstructor.newInstance(x, y, z, yaw, pitch, onGround, horizontalCollision, hasPosition, hasRotation);
   }

   public double getZ(Object packet) {
      return this.zField.getDouble(packet);
   }

   public double getX(Object packet) {
      return this.xField.getDouble(packet);
   }
}
