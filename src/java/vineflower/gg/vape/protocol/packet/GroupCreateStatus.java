package gg.vape.protocol.packet;

public enum GroupCreateStatus {
   SUCCESS,
   ALREADY_IN_GROUP;

   private static final GroupCreateStatus[] L = new GroupCreateStatus[]{SUCCESS, ALREADY_IN_GROUP};

   static {
      String[] var10000 = new String[]{"SUCCESS", "ALREADY_IN_GROUP"};
   }
}
