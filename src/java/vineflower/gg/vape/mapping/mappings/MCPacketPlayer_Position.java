package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MCPacketPlayer_Position extends Mapping {
   private MappingMethod positionPacketConstructor = ForgeVersion.MC_1_21_11.d()
      ? this.Y("<init>", false, void.class, double.class, double.class, double.class, boolean.class, boolean.class)
      : (
         ForgeVersion.MC_1_7_10.Y()
            ? this.Y("<init>", false, void.class, double.class, double.class, double.class, boolean.class)
            : this.Y("<init>", false, void.class, double.class, double.class, double.class, double.class, boolean.class)
      );

   public MCPacketPlayer_Position() {
      this(MPacketIdFactory.getPacketMappingControlFlowState());
   }

   private MCPacketPlayer_Position(GuiComponent[] controlFlowState) {
      super(MappedClasses.ul);
   }

   public Object createPositionPacket(double x, double y, double z, boolean onGround, boolean horizontalCollision) {
      return this.positionPacketConstructor.newInstance(x, y, z, onGround, horizontalCollision);
   }

   public Object createPositionPacket(double x, double y, double z, boolean onGround) {
      return this.positionPacketConstructor.newInstance(x, y, z, onGround);
   }

   public Object createLegacyPositionPacket(double x, double feetY, double stanceY, double z, boolean onGround) {
      return this.positionPacketConstructor.newInstance(x, feetY, stanceY, z, onGround);
   }
}
