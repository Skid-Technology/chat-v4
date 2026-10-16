package gg.vape.module.render.hud;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPreRenderTick;
import gg.vape.event.impl.EventPreTick;
import gg.vape.event.impl.EventWorldTime;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.WorldClient;

public class TimeChangerHudModule extends HudModule {
   private long overriddenWorldTime;
   private final NumberValue timeValue = NumberValue.create(this, "Time", "#", "hours", 0.0, 12.0, 24.0, 1.0);

   private void applyWorldTime(WorldClient worldClient, long time) {
      if (ForgeVersion.MC_1_16_5.d()) {
         worldClient.getWorldInfo().setGameTime(time);
         worldClient.getWorldInfo().setDayTime(time);
      } else {
         worldClient.i(time);
      }
   }

   @EventHandler
   public void onPreRenderTick(EventPreRenderTick event) {
      if (!ForgeVersion.MC_1_8_9.L()) {
         if (!event.getWorld().isNull()) {
            double hours = this.timeValue.getValue();
            if ((hours = hours - 6.0) < 0.0) {
               hours += 24.0;
            }

            this.applyWorldTime(event.getWorld(), Math.round(hours * 1000.0));
         }
      }
   }

   public TimeChangerHudModule() {
      super("Time Changer", HudModuleGroup.GAME, "time_changer");
      this.setSuffix("Sets the in-game world time");
      this.addValue(this.timeValue);
   }

   @EventHandler
   public void onWorldTime(EventWorldTime event) {
      event.setWorldTime(this.overriddenWorldTime);
   }

   @EventHandler
   public void onTick(EventPreTick event) {
      if (!ForgeVersion.MC_1_8_9.A()) {
         double hours = this.timeValue.getValue();
         if ((hours = hours - 6.0) < 0.0) {
            hours += 24.0;
         }

         this.overriddenWorldTime = Math.round(hours * 1000.0);
      }
   }
}
