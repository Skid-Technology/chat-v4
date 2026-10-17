package gg.vape.protocol.packet;

public enum ClientGroupLeaderKickStatus {
   SUCCESS,
   FAILED;

   private static final ClientGroupLeaderKickStatus[] D = new ClientGroupLeaderKickStatus[]{SUCCESS, FAILED};

   static {
      String[] var10000 = new String[]{"SUCCESS", "FAILED"};
   }
}
