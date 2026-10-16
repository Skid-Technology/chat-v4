package gg.vape.protocol.packet;

public enum PingTargetKind {
   POSITION,
   BLOCK,
   ENTITY;

   private static final PingTargetKind[] T = new PingTargetKind[]{POSITION, BLOCK, ENTITY};

   static {
      String[] var10000 = new String[]{"BLOCK", "POSITION", "ENTITY"};
   }
}
