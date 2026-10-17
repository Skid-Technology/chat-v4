package gg.vape.module.combat.blockhit;

import gg.vape.combat.AttackPacketTimingTracker;
import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventLivingUpdate;
import gg.vape.event.impl.EventMouseButton;
import gg.vape.event.impl.EventPreTick;
import gg.vape.event.impl.EventTickBase;
import gg.vape.event.impl.SyntheticAttackRequestEvent;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Mod;
import gg.vape.rotation.RotationManager;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;

public class PredictBlockHitMode extends BlockHitMode {
   private boolean blocking = false;
   private long holdUntil;
   private boolean active;

   public PredictBlockHitMode(Mod parent, String name) {
      super(parent, name);
   }

   @Override
   public boolean shouldBlock() {
      return false;
   }

   @Override
   public boolean isBlocking() {
      return this.blocking;
   }

   public void setBlocking(boolean bl) {
      if (this.blocking != bl) {
         this.blocking = bl;
         this.holdUntil = 0L;
         this.active = false;
         Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362().setPressed(bl);
      }
   }

   private void checkAttackPrediction(EntityPlayerSP player) {
      if (!this.getParent().requiresMouseDown() || ClientSettings.isAttackButtonDown()) {
         RayTraceResult rayTrace = RotationManager.INSTANCE.getExtendedReachRayTrace();
         if (rayTrace.isNotNull() && rayTrace.getEntity().isNotNull() && rayTrace.getEntity().isInstance(MappedClasses.zm)) {
            EntityLivingBase target = new EntityLivingBase(rayTrace.getEntity().getObject());
            int hurtResistantTime = target.getHurtResistantTime();
            int cooldownThreshold = AttackPacketTimingTracker.INSTANCE.getExpectedHurtTimeTicks() + 1;
            if (!this.blocking
               && !player.isBlocking()
               && hurtResistantTime <= cooldownThreshold + 1
               && hurtResistantTime <= cooldownThreshold
               && this.getParent().findDefaultTarget() != null) {
               this.setBlocking(true);
               this.holdUntil = System.currentTimeMillis() + 50L * (cooldownThreshold + 1);
            }
         }
      }
   }

   @EventHandler
   public void onDamaged(EventLivingUpdate event) {
      if (this.getParent().isHoldingSword()) {
         this.holdUntil = System.currentTimeMillis();
         if (event.getEntity().getObject().equals(Minecraft.thePlayer().getObject())) {
            EventTickBase.PRE_TICK_EXECUTOR.execute(() -> this.setBlocking(false));
         }
      }
   }

   @EventHandler
   public void onMouseButton(EventMouseButton event) {
      int buttonBinding = -100 + event.getButton();
      if (event.getButtonState() && this.getParent().isHoldingSword()) {
         if (buttonBinding == Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362().getKeyCode()
            && this.getParent().requiresMouseDown()
            && ClientSettings.isUseItemButtonDown()) {
            event.setCancelled(true);
         } else {
            if (buttonBinding == Minecraft.gameSettings().F().getKeyCode()) {
               this.checkAttackPrediction(event.getThePlayer());
            }
         }
      }
   }

   @EventHandler
   public void onTick(EventPreTick event) {
      if (!Minecraft.thePlayer().isNull() && this.getParent().isHoldingSword()) {
         if (!Minecraft.currentScreen().isNotNull()) {
            EntityLivingBase target = this.getParent().findDefaultTarget();
            int playerHurtTime = event.getThePlayer().getHurtResistantTime();
            boolean hurtTimeExceeded = playerHurtTime > AttackPacketTimingTracker.INSTANCE.getExpectedHurtTimeTicks() + 1;
            boolean holdTimeExpired = this.holdUntil > 0L && System.currentTimeMillis() - this.holdUntil >= 0L;
            if (target == null || hurtTimeExceeded || holdTimeExpired) {
               this.setBlocking(false);
            } else if (!this.getParent().requiresMouseDown() || ClientSettings.isAttackButtonDown()) {
               int cooldownThreshold = AttackPacketTimingTracker.INSTANCE.getExpectedHurtTimeTicks() + 2;
               boolean targetVulnerable = target.getHurtResistantTime() <= cooldownThreshold + 2;
               if ((!this.blocking || !this.active || !targetVulnerable) && !this.blocking && playerHurtTime <= cooldownThreshold + 1 && playerHurtTime > 0) {
                  this.setBlocking(true);
                  this.active = true;
                  this.holdUntil = System.currentTimeMillis() + 50L * cooldownThreshold;
               }
            }
         }
      }
   }

   @EventHandler(priority = EventPriority.LOW)
   public void onSyntheticAttack(SyntheticAttackRequestEvent event) {
      if (!event.isCanceled() && this.getParent().isHoldingSword()) {
         EntityPlayerSP player = Minecraft.thePlayer();
         if (!player.isNull()) {
            this.checkAttackPrediction(player);
         }
      }
   }
}
