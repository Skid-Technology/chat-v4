package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MCPacketPlayer_Rotation extends Mapping {
   private final MappingMethod rotationPacketConstructor = ForgeVersion.MC_1_21_11.d()
      ? this.Y("<init>", false, void.class, float.class, float.class, boolean.class, boolean.class)
      : this.Y("<init>", false, void.class, float.class, float.class, boolean.class);

   public MCPacketPlayer_Rotation() {
      this(MPacketIdFactory.getPacketMappingControlFlowState());
   }

   private MCPacketPlayer_Rotation(GuiComponent[] controlFlowState) {
      super(MappedClasses.qw);
   }

   public Object createRotationPacket(float yaw, float pitch, boolean onGround) {
      return this.rotationPacketConstructor.newInstance(yaw, pitch, onGround);
   }

   public Object createRotationPacket(float yaw, float pitch, boolean onGround, boolean horizontalCollision) {
      return this.rotationPacketConstructor.newInstance(yaw, pitch, onGround, horizontalCollision);
   }
}
