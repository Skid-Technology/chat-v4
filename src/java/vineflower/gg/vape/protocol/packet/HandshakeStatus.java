package gg.vape.protocol.packet;

public enum HandshakeStatus {
   MATCHING,
   OUTDATED_CLIENT,
   OUTDATED_SERVER;

   private static final HandshakeStatus[] q = new HandshakeStatus[]{MATCHING, OUTDATED_CLIENT, OUTDATED_SERVER};

   static {
      String[] var10000 = new String[]{"OUTDATED_SERVER", "MATCHING", "OUTDATED_CLIENT"};
   }
}
