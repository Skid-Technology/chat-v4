package gg.vape.protocol.event;

import gg.vape.protocol.ZeusClient;

public class GroupLeftEvent extends OnlineEvent {
   private static String l;

   public static String java_lang_String_u() {
      return l;
   }

   public GroupLeftEvent(ZeusClient zeusClient) {
      super(zeusClient);
   }

   public static void t(String string) {
      l = string;
   }

   static {
      if (java_lang_String_u() != null) {
         t("mqBJS");
      }
   }
}
