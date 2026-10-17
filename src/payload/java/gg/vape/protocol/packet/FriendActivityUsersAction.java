package gg.vape.protocol.packet;

public enum FriendActivityUsersAction {
   ADD,
   CHANGED_WORLD;

   private static final FriendActivityUsersAction[] o = new FriendActivityUsersAction[]{ADD, CHANGED_WORLD};

   static {
      String[] var10000 = new String[]{"CHANGED_WORLD", "ADD"};
   }
}
