package gg.vape.protocol.event;

import gg.vape.friend.OnlineFriend;
import gg.vape.protocol.ZeusClient;

public class PartyInviteRemovedEvent extends OnlineEvent {
   private final OnlineFriend H;
   private static boolean F;

   public static boolean R() {
      boolean bl = G();
      return false;
   }

   public PartyInviteRemovedEvent(ZeusClient zeusClient, OnlineFriend onlineFriend) {
      super(zeusClient);
      this.H = onlineFriend;
   }

   public static boolean G() {
      return F;
   }

   public OnlineFriend D() {
      return this.H;
   }

   public static void g(boolean bl) {
      F = bl;
   }

   static {
      if (!G()) {
         g(true);
      }
   }
}
