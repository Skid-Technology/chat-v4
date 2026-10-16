package gg.vape.protocol.packet;

public enum GroupInviteUpdateStatus {
   ACCEPTED,
   SENT,
   DECLINED;

   private static final GroupInviteUpdateStatus[] k = new GroupInviteUpdateStatus[]{ACCEPTED, SENT, DECLINED};

   static {
      String[] var10000 = new String[]{"DECLINED", "ACCEPTED", "SENT"};
   }
}
