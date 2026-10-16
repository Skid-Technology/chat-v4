package gg.vape.protocol.event;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusClient;

public class FriendRequestEvent extends OnlineEvent {
   private static int y;
   private final UserModel k;

   public static void i(int n) {
      y = n;
   }

   public static int R() {
      int n = c();
      return n == 0 ? 36 : 0;
   }

   public FriendRequestEvent(ZeusClient oZ, UserModel oj_12) {
      super(oZ);
      this.k = oj_12;
   }

   public static int c() {
      return y;
   }

   public UserModel f() {
      return this.k;
   }

   static {
      if (R() == 0) {
         i(18);
      }
   }
}
