package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.DeltaTracker;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Timer;

public class EventRenderTickBase extends Event {
   private static final EventListeners EVENT_LISTENERS = new EventListeners();
   private static float lastPartialTicks = 1.0F;
   private final float partialTicks;
   private static String obfuscationState;

   public static EventListeners getEventListeners() {
      return EVENT_LISTENERS;
   }

   @Override
   public boolean fire() {
      return super.fire();
   }

   EventRenderTickBase(float partialTicks) {
      if (partialTicks != -1.0F) {
         this.partialTicks = partialTicks;
         lastPartialTicks = partialTicks;
      } else {
         Timer timer = Minecraft.getTimer();
         if (timer != null && timer.isNotNull()) {
            this.partialTicks = timer.renderPartialTicks();
            lastPartialTicks = this.partialTicks;
         } else {
            this.partialTicks = lastPartialTicks;
         }
      }
   }

   public float getTicks() {
      return this.partialTicks;
   }

   public static String getObfuscationState() {
      return obfuscationState;
   }

   EventRenderTickBase(DeltaTracker deltaTracker) {
      float pt = 1.0F;
      if (deltaTracker != null && deltaTracker.isNotNull()) {
         pt = deltaTracker.getGameTimeDeltaPartialTick(false);
      }

      this.partialTicks = pt;
      lastPartialTicks = pt;
   }

   public static void setObfuscationState(String state) {
      obfuscationState = state;
   }

   @Override
   public EventListeners getListeners() {
      return EVENT_LISTENERS;
   }

   static {
      setObfuscationState("qQGv3b");
   }
}
