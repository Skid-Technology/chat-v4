package gg.vape.protocol.packet;

public enum ChatToFriendStatus {
   SUCCESS,
   NOT_FRIENDS,
   OFFLINE;

   private static final ChatToFriendStatus[] f = new ChatToFriendStatus[]{SUCCESS, NOT_FRIENDS, OFFLINE};

   static {
      String[] var10000 = new String[]{"SUCCESS", "OFFLINE", "NOT_FRIENDS"};
   }
}
