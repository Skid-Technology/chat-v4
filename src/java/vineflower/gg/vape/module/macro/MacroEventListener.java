package gg.vape.module.macro;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.impl.EventKeyPress;
import gg.vape.event.impl.EventMouseButton;
import gg.vape.event.impl.EventPreTick;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Macro;
import gg.vape.wrapper.impl.Minecraft;

public class MacroEventListener implements EventListener {
   private MacroAction activeAction;

   private boolean startMacro(Macro macro) {
      MacroAction nextAction = macro.createAction();
      if (nextAction == null) {
         return false;
      } else {
         if (this.activeAction != null) {
            this.activeAction.cancel();
            nextAction.inheritState(this.activeAction);
         }

         this.activeAction = nextAction;
         return true;
      }
   }

   @EventHandler
   public void onTick(EventPreTick event) {
      if (this.activeAction != null) {
         this.activeAction.tick();
         if (this.activeAction.isFinished()) {
            this.activeAction = null;
         }
      }
   }

   @EventHandler
   public void onMouseButton(EventMouseButton event) {
      if (event.getButtonState()) {
         int binding = -100 + event.getButton();

         for (Macro macro : Vape.INSTANCE.getMacrosManager().getMacros(binding)) {
            if (macro.activateIfMatched(binding) && this.startMacro(macro)) {
               break;
            }
         }
      }
   }

   @EventHandler
   public void onKeyPress(EventKeyPress event) {
      if (!event.isDown()) {
         if (!event.getThePlayer().isNull()) {
            if (!Minecraft.a_pt_1_w().isInstance(MappedClasses.qo)) {
               int binding = event.getKey();

               for (Macro macro : Vape.INSTANCE.getMacrosManager().getMacros(binding)) {
                  if (macro.activateIfMatched(binding) && this.startMacro(macro)) {
                     break;
                  }
               }
            }
         }
      }
   }
}
