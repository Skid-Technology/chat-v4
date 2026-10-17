package gg.vape.wrapper.impl;

public class C03PacketPlayer extends Packet {
   public double getX() {
      return vapeInstance.getMappings().Dq.getX(this.I);
   }

   public float getPitch() {
      return vapeInstance.getMappings().Dq.getPitch(this.I);
   }

   public double getY() {
      return vapeInstance.getMappings().Dq.getY(this.I);
   }

   public float getYaw() {
      return vapeInstance.getMappings().Dq.getYaw(this.I);
   }

   public C03PacketPlayer(Object handle) {
      super(handle);
   }

   public static C03PacketPlayer create(boolean onGround) {
      if (ForgeVersion.MC_1_21_4.d()) {
         EntityPlayerSP player = Minecraft.thePlayer();
         return CPacketPlayer.create(onGround, player.r());
      } else if (ForgeVersion.MC_1_20_6.d()) {
         EntityPlayerSP player = Minecraft.thePlayer();
         return new C03PacketPlayer(
            vapeInstance.getMappings().Dq.createPacket(player.z(), player.N(), player.h(), player.J(), player.V(), onGround, false, false)
         );
      } else {
         return new C03PacketPlayer(vapeInstance.getMappings().Dq.createPacket(onGround));
      }
   }

   public double getZ() {
      return vapeInstance.getMappings().Dq.getZ(this.I);
   }

   public boolean hasPosition() {
      return vapeInstance.getMappings().Dq.hasPosition(this.I);
   }
}
