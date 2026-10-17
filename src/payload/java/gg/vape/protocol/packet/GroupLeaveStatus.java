package gg.vape.protocol.packet;

public enum GroupLeaveStatus {
   SUCCESS,
   FAILED;

   private static final GroupLeaveStatus[] s = new GroupLeaveStatus[]{SUCCESS, FAILED};

   static {
      String[] var10000 = new String[]{"FAILED", "SUCCESS"};
   }
}
