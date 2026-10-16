package gg.vape.protocol.packet;

public enum PingAudience {
   FRIENDS,
   GROUP;

   private static final PingAudience[] K = new PingAudience[]{FRIENDS, GROUP};

   static {
      String[] var10000 = new String[]{"GROUP", "FRIENDS"};
   }
}
