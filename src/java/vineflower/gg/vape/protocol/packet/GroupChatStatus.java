package gg.vape.protocol.packet;

public enum GroupChatStatus {
   SUCCESS,
   UNKNOWN;

   private static final GroupChatStatus[] I = new GroupChatStatus[]{SUCCESS, UNKNOWN};

   static {
      String[] var10000 = new String[]{"SUCCESS", "UNKNOWN"};
   }
}
