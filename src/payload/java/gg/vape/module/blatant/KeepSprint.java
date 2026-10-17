package gg.vape.module.blatant;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPostAttack;
import gg.vape.event.impl.EventPreAttack;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.event.impl.EventSetSprinting;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.utils.MathUtil;
import gg.vape.utils.TimerUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;

public class KeepSprint extends Mod {
   private static final long MODULE_ID = -249991328817855756L;
   private double preMotionX;
   private double preMotionZ;
   private final TimerUtil timer;
   private final NumberValue retainFactor = NumberValue.create(this, "Retain factor", "#.##", "", 0.6, 0.95, 1.0);
   private Scaffold scaffold;
   private boolean resetPending;
   private boolean sprintCancelPending;
   private final BooleanValue resetSprint = new BooleanValue(this, "Reset sprint", false);

   public KeepSprint() {
      super("KeepSprint", 14828276, Category.OTHER, "Prevents you from losing sprint when attacking");
      this.timer = new TimerUtil();
      this.addValue(this.retainFactor, this.resetSprint);
   }

   @Override
   public boolean isBlatantMod() {
      return true;
   }

   @Override
   public String getDetailedSuffix() {
      return this.retainFactor.getDisplayValue();
   }

   @EventHandler
   public void onPostAttack(EventPostAttack event) {
      EntityPlayerSP player = Minecraft.thePlayer();
      if (this.scaffold == null) {
         this.scaffold = Vape.INSTANCE.getModManager().getMod(Scaffold.class);
      }

      if (this.scaffold == null || !this.scaffold.isActivelyScaffolding()) {
         if (!player.isNull() && !(event.getThePlayer().F() <= 0.0F)) {
            if (player.B$src$Z$f90iek() || this.isMovingForward(player)) {
               double d = 0.6;
               if (player.t() == this.preMotionX * d && player.T() == this.preMotionZ * d && !player.r()) {
                  double d3 = this.retainFactor.getValue();
                  double d4 = player.t() / d;
                  double d5 = player.T() / d;
                  double d8 = d4 * d3;
                  double d9 = d5 * d3;
                  player.r(d8);
                  player.i(d9);
                  if (this.resetSprint.getEffectiveValue()) {
                     if (this.timer.hasTimeElapsed(500L)) {
                        this.resetPending = true;
                        this.timer.reset();
                     }
                  } else {
                     player.R(true);
                  }
               }
            }
         }
      }
   }

   @EventHandler
   public void onSetSprinting(EventSetSprinting event) {
      if (this.sprintCancelPending && event.isNewStateSprinting()) {
         event.setCancelled(true);
         this.sprintCancelPending = false;
      }
   }

   @EventHandler
   public void onPreAttack(EventPreAttack event) {
      EntityPlayerSP player = Minecraft.thePlayer();
      if (this.scaffold == null) {
         this.scaffold = Vape.INSTANCE.getModManager().getMod(Scaffold.class);
      }

      if (this.scaffold == null || !this.scaffold.isActivelyScaffolding()) {
         if (!player.isNull()) {
            this.preMotionX = player.t();
            this.preMotionZ = player.T();
         }
      }
   }

   @EventHandler
   public void onPrePlayerTick(EventPrePlayerTick event) {
      if (this.resetPending) {
         event.getThePlayer().R(false);
         this.sprintCancelPending = true;
         this.resetPending = false;
      }
   }

   private boolean isMovingForward(EntityPlayerSP player) {
      float f2 = player.F();
      if (f2 > 0.0F) {
         float f6 = player.J();
         float f10 = -MathUtil.sin(f6 * (float) Math.PI / 180.0F);
         float f12 = MathUtil.cos(f6 * (float) Math.PI / 180.0F);
         double d3 = player.t();
         double d4 = d3 * f10 + player.T() * f12;
         return d4 > 0.0;
      } else {
         return false;
      }
   }
}
