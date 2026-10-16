package gg.vape.input;

import gg.vape.event.impl.EventTickBase;
import gg.vape.event.impl.SyntheticAttackRequestEvent;
import gg.vape.module.Mod;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;

public class AttackKeyController {
   public static void releaseAttackKey() {
      if (shouldRunOnClientTick()) {
         EventTickBase.PRE_TICK_EXECUTOR.execute(AttackKeyController::releaseAttackKeyNow);
      } else {
         releaseAttackKeyNow();
      }
   }

   private static void releaseAttackKeyNow() {
      KeyBinding attackKey = Minecraft.a_w3_0_S().F();
      KeyBindingHelper.updateKeyBinding(attackKey, false, false);
   }

   public static boolean requestSyntheticAttack(Mod module) {
      if (shouldRunOnClientTick()) {
         EventTickBase.PRE_TICK_EXECUTOR.execute(() -> requestSyntheticAttackNow(module));
         return true;
      } else {
         return requestSyntheticAttackNow(module);
      }
   }

   private static boolean requestSyntheticAttackNow(Mod module) {
      SyntheticAttackRequestEvent event = new SyntheticAttackRequestEvent(module);
      if (event.fire()) {
         return false;
      } else {
         pressAttackKeyNow();
         return true;
      }
   }

   public static void pressAttackKey() {
      if (shouldRunOnClientTick()) {
         EventTickBase.PRE_TICK_EXECUTOR.execute(AttackKeyController::pressAttackKeyNow);
      } else {
         pressAttackKeyNow();
      }
   }

   private static void pressAttackKeyNow() {
      KeyBindingHelper.updateKeyBinding(Minecraft.a_w3_0_S().F(), true, true);
   }

   private static boolean shouldRunOnClientTick() {
      if (!ForgeVersion.MC_26_2.d()) {
         return false;
      } else {
         Thread ownerThread = EventTickBase.PRE_TICK_EXECUTOR.getOwnerThread();
         return ownerThread == null || !Thread.currentThread().equals(ownerThread);
      }
   }
}
