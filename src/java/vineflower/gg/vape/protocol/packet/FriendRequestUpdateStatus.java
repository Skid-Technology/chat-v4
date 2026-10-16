package gg.vape.protocol.packet;

public enum FriendRequestUpdateStatus {
   ACCEPTED,
   DECLINED,
   UNKNOWN;

   private static final FriendRequestUpdateStatus[] k = new FriendRequestUpdateStatus[]{ACCEPTED, DECLINED, UNKNOWN};

   static {
      String[] var10000 = new String[]{"DECLINED", "ACCEPTED", "UNKNOWN"};
   }
}
