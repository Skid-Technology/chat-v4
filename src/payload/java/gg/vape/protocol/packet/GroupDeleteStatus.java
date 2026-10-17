package gg.vape.protocol.packet;

public enum GroupDeleteStatus {
   SUCCESS,
   NO_PERMISSION,
   NO_GROUP;

   private static final GroupDeleteStatus[] N = new GroupDeleteStatus[]{SUCCESS, NO_PERMISSION, NO_GROUP};

   static {
      String[] var10000 = new String[]{"NO_GROUP", "SUCCESS", "NO_PERMISSION"};
   }
}
