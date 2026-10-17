package gg.vape.wrapper.impl;

public class SEntityPacket extends Packet {
   public int getDeltaY() {
      return vapeInstance.getMappingsMapperCompat().qC.getDeltaY(this.I);
   }

   public int getDeltaZ() {
      return vapeInstance.getMappingsMapperCompat().qC.getDeltaZ(this.I);
   }

   public Entity getEntity(World world) {
      return new Entity(vapeInstance.getMappingsMapperCompat().qC.getEntity(this.I, world.getObject()));
   }

   public SEntityPacket(Object handle) {
      super(handle);
   }

   public int getDeltaX() {
      return vapeInstance.getMappingsMapperCompat().qC.getDeltaX(this.I);
   }
}
