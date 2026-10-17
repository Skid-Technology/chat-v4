package gg.vape.module.blatant.speed;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPreMove;
import gg.vape.module.Mod;
import gg.vape.module.SubModule;
import gg.vape.module.blatant.Speed;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PotionRegistry;

public class MineplexSpeed extends SubModule<Speed> {
   private final Speed parentSpeed = this.getParent();

   public MineplexSpeed(Mod mod, String string) {
      super(mod, string);
   }

   @EventHandler
   public void onMove(EventPreMove eventPreMove) {
      EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
      if (!entityPlayerSP.h$src$Z$ftwoya()) {
         if ((entityPlayerSP.F() != 0.0F || entityPlayerSP.N$src$F$14ypudi() != 0.0F) && entityPlayerSP.u$src$Z$g120nz()) {
            double jumpMotion = 0.42F;
            if (entityPlayerSP.i(PotionRegistry.Z)) {
               jumpMotion += (entityPlayerSP.b(PotionRegistry.Z).L() + 1) * 0.1F;
            }

            eventPreMove.setY(jumpMotion);
            entityPlayerSP.k(jumpMotion);
         }

         this.parentSpeed.strafe(eventPreMove, 0.4199999976158142, entityPlayerSP);
      }
   }
}
