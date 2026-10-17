package gg.vape.friend;

public class PartyInvite {
   private final OnlineFriend inviter;

   public PartyInvite(OnlineFriend inviter) {
      this.inviter = inviter;
   }

   public OnlineFriend getInviter() {
      return this.inviter;
   }
}
