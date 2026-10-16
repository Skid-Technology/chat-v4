package gg.vape.protocol.packet;

public enum ClientGroupLeaderPromoteStatus {
   SUCCESS,
   FAILED;

   private static final ClientGroupLeaderPromoteStatus[] F = new ClientGroupLeaderPromoteStatus[]{SUCCESS, FAILED};

   static {
      String[] var10000 = new String[]{"FAILED", "SUCCESS"};
   }
}
