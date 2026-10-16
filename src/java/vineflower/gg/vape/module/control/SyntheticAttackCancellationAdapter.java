package gg.vape.module.control;

import gg.vape.event.impl.SyntheticAttackRequestEvent;

public class SyntheticAttackCancellationAdapter implements AttackCancellationAdapter {
   private final SyntheticAttackRequestEvent attackRequestEvent;
   private boolean cancelled;

   @Override
   public void setCancelled(boolean cancelled) {
      this.cancelled = cancelled;
      if (this.attackRequestEvent != null) {
         this.attackRequestEvent.setCancelled(cancelled);
      }
   }

   @Override
   public boolean isCancelled() {
      return this.cancelled || this.attackRequestEvent != null && this.attackRequestEvent.isCanceled();
   }

   public SyntheticAttackCancellationAdapter(SyntheticAttackRequestEvent attackRequestEvent) {
      this.attackRequestEvent = attackRequestEvent;
   }

   public boolean isInternalAttack() {
      return this.attackRequestEvent == null;
   }
}
