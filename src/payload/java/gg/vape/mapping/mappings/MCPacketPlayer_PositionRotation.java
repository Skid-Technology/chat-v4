package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MCPacketPlayer_PositionRotation extends Mapping {
   private MappingMethod positionRotationPacketConstructor = ForgeVersion.MC_1_21_11.d()
      ? this.Y("<init>", false, void.class, double.class, double.class, double.class, float.class, float.class, boolean.class, boolean.class)
      : (
         ForgeVersion.MC_1_7_10.Y()
            ? this.Y("<init>", false, void.class, double.class, double.class, double.class, float.class, float.class, boolean.class)
            : this.Y("<init>", false, void.class, double.class, double.class, double.class, double.class, float.class, float.class, boolean.class)
      );

   public MCPacketPlayer_PositionRotation() {
      this(MPacketIdFactory.getPacketMappingControlFlowState());
   }

   private MCPacketPlayer_PositionRotation(GuiComponent[] controlFlowState) {
      super(MappedClasses.FK);
      if (GuiComponent.getLegacyComponentState() == null) {
         MPacketIdFactory.setPacketMappingControlFlowState(new GuiComponent[2]);
      }
   }

   public Object createLegacyPositionRotationPacket(double x, double feetY, double stanceY, double z, float yaw, float pitch, boolean onGround) {
      return this.positionRotationPacketConstructor.newInstance(x, feetY, stanceY, z, yaw, pitch, onGround);
   }

   public Object createPositionRotationPacket(double x, double y, double z, float yaw, float pitch, boolean onGround) {
      return this.positionRotationPacketConstructor.newInstance(x, y, z, yaw, pitch, onGround);
   }

   public Object createPositionRotationPacket(double x, double y, double z, float yaw, float pitch, boolean onGround, boolean horizontalCollision) {
      return this.positionRotationPacketConstructor.newInstance(x, y, z, yaw, pitch, onGround, horizontalCollision);
   }
}
