package gg.vape.protocol.packet;

public enum GroupUninviteStatus {
   SUCCESS,
   FAILED;

   private static final GroupUninviteStatus[] L = new GroupUninviteStatus[]{SUCCESS, FAILED};

   static {
      String[] var10000 = new String[]{"SUCCESS", "FAILED"};
   }
}
