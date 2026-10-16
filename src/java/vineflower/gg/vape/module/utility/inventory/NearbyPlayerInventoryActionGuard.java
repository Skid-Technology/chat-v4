package gg.vape.module.utility.inventory;

import gg.vape.mapping.MappedClasses;
import gg.vape.utils.RotationUtil;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityOtherPlayerMP;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.World;

public class NearbyPlayerInventoryActionGuard extends InventoryActionGuard {
   private double nearestDistance = 99.0;
   private Entity nearestPlayer;

   @Override
   public void update(EntityLivingBase entity) {
      World world = entity.getWorld();
      if (!world.isNull() && (this.trackedWorld == null || !this.trackedWorld.isNotNull() || world.equals(this.trackedWorld))) {
         boolean nearbyThreat = RotationUtil.o(entity, 10.0, 60.0, true);

         for (Object handle : Minecraft.theWorld().X()) {
            if (MappedClasses.lG.isInstance(handle)) {
               EntityOtherPlayerMP otherPlayer = new EntityOtherPlayerMP(handle);
               EntityPlayerSP localPlayer = Minecraft.thePlayer();
               double distance = RotationUtil.y(localPlayer.z(), 0.0, localPlayer.h(), otherPlayer.z(), 0.0, otherPlayer.h());
               if (distance <= 7.0 && distance < this.nearestDistance) {
                  this.nearestDistance = distance;
                  this.nearestPlayer = otherPlayer;
               }
            }
         }

         if (this.nearestDistance == 99.0 && !nearbyThreat) {
            this.reset();
         } else {
            this.activateBlock();
         }

         if (this.blocked) {
            if (RotationUtil.D(entity, 10) == 0) {
               this.reset();
            } else if (RotationUtil.d(entity)) {
               this.stationaryTicks++;
               if (this.stationaryTicks >= 40) {
                  this.reset();
               } else {
                  this.stationaryTicks = 0;
               }
            }

            if (this.remainingCooldownTicks > 0) {
               this.remainingCooldownTicks--;
            } else {
               this.reset();
            }
         }

         RayTraceResult rayTraceResult = Minecraft.p$src$Lgg_vape_wrapper_impl_RayTraceResult_$5rw6n0();
         boolean targetingEntity = rayTraceResult.isNotNull() && rayTraceResult.getEntity().isNotNull() && entity.Y$src$Z$154rldp();
         if (targetingEntity) {
            this.activateBlock();
         }

         this.trackedWorld = world;
      } else {
         this.reset();
      }
   }

   public NearbyPlayerInventoryActionGuard(int cooldownTicks) {
      super(cooldownTicks);
   }

   @Override
   public void reset() {
      super.reset();
      this.nearestDistance = 99.0;
      this.nearestPlayer = null;
   }
}
