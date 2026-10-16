package gg.vape.module.control;

import gg.vape.Vape;
import gg.vape.module.Mod;
import gg.vape.module.blatant.AutoLadder;
import gg.vape.module.blatant.Clutch;
import gg.vape.module.utility.MLG;
import gg.vape.wrapper.impl.EntityPlayerSP;

public class FallRescuePriorityManager {
   public static final FallRescuePriorityManager INSTANCE = new FallRescuePriorityManager();
   private long lastStateKey = Long.MIN_VALUE;
   private Boolean lastClutchCovered;
   private Boolean lastAutoLadderCovered;

   private FallRescuePriorityManager() {
   }

   public boolean shouldStandDown(Mod self, EntityPlayerSP player) {
      if (player == null || player.isNull() || player.b$src$Z$fqlxe4()) {
         return false;
      } else if (self instanceof AutoLadder) {
         return this.isCoveredByCached(Clutch.class, player);
      } else {
         return !(self instanceof MLG) ? false : this.isCoveredByCached(Clutch.class, player) || this.isCoveredByCached(AutoLadder.class, player);
      }
   }

   private boolean isCoveredByCached(Class<? extends Mod> moduleClass, EntityPlayerSP player) {
      long stateKey = this.computeStateKey(player);
      if (stateKey != this.lastStateKey) {
         this.lastStateKey = stateKey;
         this.lastClutchCovered = null;
         this.lastAutoLadderCovered = null;
      }

      if (moduleClass == Clutch.class) {
         if (this.lastClutchCovered == null) {
            this.lastClutchCovered = this.isCoveredBy(Clutch.class, player);
         }

         return this.lastClutchCovered;
      } else if (moduleClass == AutoLadder.class) {
         if (this.lastAutoLadderCovered == null) {
            this.lastAutoLadderCovered = this.isCoveredBy(AutoLadder.class, player);
         }

         return this.lastAutoLadderCovered;
      } else {
         return false;
      }
   }

   private long computeStateKey(EntityPlayerSP player) {
      long hash = 1L;
      hash = hash * 31L + Double.doubleToLongBits(player.z());
      hash = hash * 31L + Double.doubleToLongBits(player.N());
      hash = hash * 31L + Double.doubleToLongBits(player.h());
      hash = hash * 31L + Double.doubleToLongBits(player.t());
      hash = hash * 31L + Double.doubleToLongBits(player.q());
      hash = hash * 31L + Double.doubleToLongBits(player.T());
      return hash * 31L + Float.floatToIntBits(player.getFallDistance());
   }

   private boolean isCoveredBy(Class<? extends Mod> moduleClass, EntityPlayerSP player) {
      Mod module = Vape.INSTANCE.getModManager().getMod(moduleClass);
      if (module == null || !module.isEnabled()) {
         return false;
      } else if (module instanceof Clutch) {
         Clutch clutch = (Clutch)module;
         return clutch.isRescueEngaged() || clutch.canHandleFall(player);
      } else if (!(module instanceof AutoLadder)) {
         return false;
      } else {
         AutoLadder autoLadder = (AutoLadder)module;
         return autoLadder.isRescueEngaged() || autoLadder.canHandleFall(player);
      }
   }
}
