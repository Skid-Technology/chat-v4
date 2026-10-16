package gg.vape.protocol.packet;

public enum ClientFriendActivityUsersAction {
   ADD,
   CHANGED_WORLD;

   private static final ClientFriendActivityUsersAction[] j = new ClientFriendActivityUsersAction[]{ADD, CHANGED_WORLD};

   static {
      String[] var10000 = new String[]{"CHANGED_WORLD", "ADD"};
   }
}
