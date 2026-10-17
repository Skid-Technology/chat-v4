package gg.vape.protocol.packet;

public enum GroupInviteStateStatus {
   SUCCESSFULLY_ACCEPTED,
   SUCCESSFULLY_DECLINED,
   GROUP_FULL,
   FAILED;

   private static final GroupInviteStateStatus[] O = new GroupInviteStateStatus[]{SUCCESSFULLY_ACCEPTED, SUCCESSFULLY_DECLINED, GROUP_FULL, FAILED};

   static {
      String[] var10000 = new String[]{"FAILED", "SUCCESSFULLY_ACCEPTED", "SUCCESSFULLY_DECLINED", "GROUP_FULL"};
   }
}
