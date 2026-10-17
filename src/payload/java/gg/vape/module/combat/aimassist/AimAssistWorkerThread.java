package gg.vape.module.combat.aimassist;

import gg.vape.Vape;

public class AimAssistWorkerThread extends Thread {
   private final AimAssistTargetingSubModule targetingSubModule;

   @Override
   public void run() {
      while (!Vape.INSTANCE.isEnabled()) {
         try {
            Thread.sleep(1L);
            if (this.targetingSubModule.isEnabled() && this.targetingSubModule.isSelectedSubModule()) {
               AimAssistTargetingSubModule.runWorkerTick(this.targetingSubModule);
            }
         } catch (Exception var2) {
         }
      }
   }

   public AimAssistWorkerThread(AimAssistTargetingSubModule targetingSubModule) {
      this.targetingSubModule = targetingSubModule;
   }
}
