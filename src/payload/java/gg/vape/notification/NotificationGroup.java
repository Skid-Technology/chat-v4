package gg.vape.notification;

public enum NotificationGroup {
   NONE,
   FRIENDS;

   private static final NotificationGroup[] cachedValues = new NotificationGroup[]{NONE, FRIENDS};

   static {
      String[] var10000 = new String[]{"FRIENDS", "NONE"};
   }
}
