package gg.vape.wrapper.impl;

public class SPacketChunkData extends Packet {
   public SPacketChunkData(Object handle) {
      super(handle);
   }

   public int getChunkZ() {
      return vapeInstance.getMappingsMapperCompat().h8.getChunkZ(this.I);
   }

   public SPacketChunkDataExtracted getExtractedData() {
      return new SPacketChunkDataExtracted(vapeInstance.getMappingsMapperCompat().h8.getExtractedData(this.I));
   }

   public int getChunkX() {
      return vapeInstance.getMappingsMapperCompat().h8.getChunkX(this.I);
   }
}
