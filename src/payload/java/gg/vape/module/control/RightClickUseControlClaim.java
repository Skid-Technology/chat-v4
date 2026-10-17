package gg.vape.module.control;

public class RightClickUseControlClaim extends ModuleControlClaim {
   public void blockUse() {
      this.setClaimed(true);
   }

   public void unblockUse() {
      this.setClaimed(false);
   }
}
