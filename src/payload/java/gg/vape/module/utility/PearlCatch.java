package gg.vape.module.utility;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPreTick;
import gg.vape.module.UtilityMod;
import gg.vape.module.control.SharedModuleControlClaims;
import gg.vape.rotation.AdaptiveRotationController;
import gg.vape.rotation.FixedRotationController;
import gg.vape.rotation.RotationControlClaim;
import gg.vape.rotation.RotationManager;
import gg.vape.unmap.ModeOption;
import gg.vape.value.BooleanValue;
import gg.vape.value.ModeValue;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.InventoryPlayer;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.Vec3;

public class PearlCatch extends UtilityMod {
   private static final double PROJECTILE_SPEED = 1.5;
   private static final double PEARL_DRAG = 0.99;
   private static final double PEARL_GRAVITY = 0.03;
   private static final double MAX_INTERCEPT_DISTANCE_SQUARED = 0.36;
   private static final int MAX_FLIGHT_TICKS = 30;
   private static final int MAX_AIM_TICKS = 40;
   private final ModeOption upwardMode = new ModeOption("Upward");
   private final ModeOption currentAimMode = new ModeOption("Current aim");
   private final ModeValue aimMode = ModeValue.create(
      this,
      "Aim mode",
      "Upward - throws the pearl straight up\nCurrent aim - throws the wind charge where you were looking",
      this.upwardMode,
      this.upwardMode,
      this.currentAimMode
   );
   private final NumberValue aimSpeed = NumberValue.create(this, "Aim speed", "#.#", "", 1.0, 10.0, 10.0);
   private final BooleanValue silentAim = BooleanValue.create(this, "Silent aim", true, null);
   private final NumberValue chargeDelay = NumberValue.create(
      this, "Charge delay", "#", " ticks", 0.0, 0.0, 10.0, 1.0, "Ticks to wait after the pearl before throwing the wind charge"
   );
   private final RotationControlClaim rotationClaim = SharedModuleControlClaims.rotation;
   private PearlCatch.State state = PearlCatch.State.IDLE;
   private FixedRotationController rotationController;
   private PearlCatch.InterceptPlan plan;
   private int pearlSlot = -1;
   private int chargeSlot = -1;
   private int savedSlot = -1;
   private int stateTicks;
   private int ticksSincePearl;

   public PearlCatch() {
      super("PearlCatch", "Throws a pearl, then throws a wind charge to catch it");
      this.addValue(this.aimMode, this.aimSpeed, this.silentAim, this.chargeDelay);
      this.rotationClaim.setPriority(this, 7);
   }

   @Override
   public void onEnable() {
      EntityPlayerSP player = Minecraft.thePlayer();
      if (!player.isNull() && !Minecraft.theWorld().isNull() && !SharedModuleControlClaims.rightClickUse.isClaimed()) {
         InventoryPlayer inventory = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
         this.pearlSlot = this.findHotbarSlot(inventory, "minecraft:ender_pearl", "Ender Pearl");
         this.chargeSlot = this.findHotbarSlot(inventory, "minecraft:wind_charge", "Wind Charge", "Windcharge");
         if (this.pearlSlot != -1 && this.chargeSlot != -1) {
            this.savedSlot = inventory.v();
            int configuredDelay = this.chargeDelay.getValue().intValue();
            this.plan = this.currentAimMode.isSelected()
               ? this.findCurrentAimPlan(player, Math.max(2, configuredDelay))
               : this.findUpwardPlan(player, configuredDelay);
            if (this.plan == null) {
               this.abort();
            } else {
               this.state = PearlCatch.State.AIMING_PEARL;
               this.stateTicks = 0;
               this.ticksSincePearl = 0;
               this.setRotation(this.plan.pearlYaw, this.plan.pearlPitch);
            }
         } else {
            this.abort();
         }
      } else {
         this.abort();
      }
   }

   @Override
   public void onDisable() {
      SharedModuleControlClaims.rightClickUse.unblockUse();
      this.releaseRotation();
      EntityPlayerSP player = Minecraft.thePlayer();
      if (player.isNotNull() && this.savedSlot != -1) {
         player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(this.savedSlot);
      }

      this.state = PearlCatch.State.IDLE;
      this.rotationController = null;
      this.plan = null;
      this.pearlSlot = -1;
      this.chargeSlot = -1;
      this.savedSlot = -1;
      this.stateTicks = 0;
      this.ticksSincePearl = 0;
   }

   @EventHandler
   public void onTick(EventPreTick event) {
      EntityPlayerSP player = event.getThePlayer();
      if (!player.isNull() && !Minecraft.theWorld().isNull() && this.plan != null) {
         this.stateTicks++;
         if (this.state == PearlCatch.State.AIMING_CHARGE || this.state == PearlCatch.State.WAITING_FOR_CHARGE) {
            this.ticksSincePearl++;
         }

         if (this.stateTicks > 40 && this.state != PearlCatch.State.WAITING_FOR_CHARGE) {
            this.abort();
         } else {
            switch (this.state) {
               case AIMING_PEARL:
                  if (!this.hasRotationControl()) {
                     return;
                  }

                  if (this.isRotationReady()) {
                     this.state = PearlCatch.State.THROWING_PEARL;
                  }
                  break;
               case THROWING_PEARL:
                  this.throwFromSlot(this.pearlSlot);
                  this.state = PearlCatch.State.AIMING_CHARGE;
                  this.stateTicks = 0;
                  this.ticksSincePearl = 0;
                  this.setRotation(this.plan.chargeYaw, this.plan.chargePitch);
                  break;
               case AIMING_CHARGE:
                  if (!this.hasRotationControl()) {
                     return;
                  }

                  if (this.isRotationReady()) {
                     this.state = PearlCatch.State.WAITING_FOR_CHARGE;
                     this.stateTicks = 0;
                  } else if (this.plan.absoluteChargeTiming && this.ticksSincePearl >= this.plan.chargeTick) {
                     this.abort();
                  }
                  break;
               case WAITING_FOR_CHARGE:
                  boolean readyToThrow = this.plan.absoluteChargeTiming
                     ? this.ticksSincePearl >= this.plan.chargeTick
                     : this.stateTicks >= this.plan.chargeTick;
                  if (readyToThrow) {
                     this.throwFromSlot(this.chargeSlot);
                     this.state = PearlCatch.State.FINISHED;
                  }
                  break;
               case FINISHED:
                  this.setEnabled(false, true);
            }
         }
      } else {
         this.abort();
      }
   }

   private boolean hasRotationControl() {
      return this.rotationClaim.isOwnedBy(this) || this.rotationClaim.acquire(this, this.silentAim.getEffectiveValue());
   }

   private boolean isRotationReady() {
      if (this.rotationController != null && this.rotationClaim.isOwnedBy(this)) {
         if (RotationManager.INSTANCE.getActiveController() != this.rotationController) {
            RotationManager.INSTANCE.setController(this.rotationController);
         }

         return this.rotationController.isComplete();
      } else {
         return false;
      }
   }

   private void setRotation(float yaw, float pitch) {
      FixedRotationController controller = (FixedRotationController)(this.silentAim.getEffectiveValue()
         ? new AdaptiveRotationController(yaw, pitch)
         : new FixedRotationController(yaw, pitch));
      controller.setTargetRotation(yaw, pitch);
      controller.setSpeed(this.aimSpeed.getValue().floatValue());
      controller.setTolerance(0.35F);
      controller.setScaleAxesProportionally(true);
      controller.setLinearAcceleration(true);
      controller.setClampStepToRemaining(true);
      controller.setRetainAfterCompletion(true);
      this.rotationController = controller;
      if (this.rotationClaim.isOwnedBy(this)) {
         RotationManager.INSTANCE.setController(controller);
      }
   }

   private void releaseRotation() {
      if (this.rotationController != null && RotationManager.INSTANCE.getActiveController() == this.rotationController) {
         RotationManager.INSTANCE.releaseController(this.rotationController);
      }

      this.rotationClaim.release(this);
   }

   private void throwFromSlot(int slot) {
      EntityPlayerSP player = Minecraft.thePlayer();
      if (!player.isNull()) {
         player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(slot);
         KeyBinding useKey = Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362();
         SharedModuleControlClaims.rightClickUse.blockUse();

         try {
            KeyBinding.setKeyBindState(useKey, true);
            KeyBinding.onTick(useKey);
         } finally {
            KeyBinding.setKeyBindState(useKey, false);
            SharedModuleControlClaims.rightClickUse.clearClaimed();
         }
      }
   }

   private int findHotbarSlot(InventoryPlayer inventory, String itemId, String... displayNames) {
      Item expected = Item.L(itemId);

      for (int slot = 0; slot < 9; slot++) {
         ItemStack stack = inventory.c(slot);
         if (!stack.isNull() && !stack.getItem().isNull()) {
            Item item = stack.getItem();
            if (expected != null && expected.isNotNull() && item.equals(expected)) {
               return slot;
            }

            String text = item.toString();

            for (String displayName : displayNames) {
               if (text != null && text.contains(displayName)) {
                  return slot;
               }
            }
         }
      }

      return -1;
   }

   private void abort() {
      if (this.isEnabled()) {
         this.setEnabled(false, true);
      } else {
         this.onDisable();
      }
   }

   private PearlCatch.InterceptPlan findUpwardPlan(EntityPlayerSP player, int minimumDelay) {
      float referenceYaw = player.J();
      double horizontalMotionSquared = player.t() * player.t() + player.T() * player.T();
      float[] pearlYaws;
      if (horizontalMotionSquared < 0.003) {
         pearlYaws = new float[]{referenceYaw};
      } else {
         float movementYaw = (float)Math.toDegrees(Math.atan2(-player.t(), player.T()));
         pearlYaws = Math.abs(wrapAngle(movementYaw - referenceYaw)) < 20.0F ? new float[]{referenceYaw} : new float[]{referenceYaw, movementYaw};
      }

      PearlCatch.InterceptPlan best = null;
      double bestScore = Double.MAX_VALUE;

      for (float pearlPitch = -90.0F; pearlPitch <= -76.0F; pearlPitch += 0.5F) {
         for (float pearlBaseYaw : pearlYaws) {
            for (float pearlYawOffset = -20.0F; pearlYawOffset <= 20.0F; pearlYawOffset += 2.5F) {
               float pearlYaw = pearlBaseYaw + pearlYawOffset;

               for (float chargePitch = -90.0F; chargePitch <= -86.0F; chargePitch++) {
                  for (float chargeYawOffset = -20.0F; chargeYawOffset <= 20.0F; chargeYawOffset += 5.0F) {
                     float chargeYaw = referenceYaw + chargeYawOffset;
                     PearlCatch.InterceptPlan candidate = this.evaluatePlan(
                        player, pearlYaw, pearlPitch, chargeYaw, chargePitch, minimumDelay, minimumDelay, false
                     );
                     if (candidate != null) {
                        double score = Math.sqrt(candidate.missDistanceSquared)
                           + Math.abs(wrapAngle(chargeYaw - referenceYaw)) * 0.01
                           + Math.abs(wrapAngle(pearlYaw - referenceYaw)) * 0.003
                           + Math.abs(chargePitch + 90.0F) * 0.03;
                        if (score < bestScore) {
                           best = candidate;
                           bestScore = score;
                        }
                     }
                  }
               }
            }
         }
      }

      return best;
   }

   private PearlCatch.InterceptPlan findCurrentAimPlan(EntityPlayerSP player, int minimumDelay) {
      float chargeYaw = player.J();
      float chargePitch = player.V();
      PearlCatch.Vec chargeDir = direction(chargeYaw, chargePitch);
      double pX = player.t();
      double pY = player.b$src$Z$fqlxe4() ? 0.0 : player.q();
      double pZ = player.T();
      PearlCatch.Vec playerVel = new PearlCatch.Vec(pX, pY, pZ);
      PearlCatch.Vec chargeVel = new PearlCatch.Vec(chargeDir.x * 1.5 + pX, chargeDir.y * 1.5 + pY, chargeDir.z * 1.5 + pZ);
      PearlCatch.Vec pearlStartPos = new PearlCatch.Vec(player.z(), player.N() + player.Y() - 0.1, player.h());
      PearlCatch.Vec chargeStartPos = new PearlCatch.Vec(player.z(), player.N() + player.Y(), player.h());
      PearlCatch.InterceptPlan best = null;
      double bestScore = Double.MAX_VALUE;
      int minDelay = Math.max(3, minimumDelay);
      int maxDelay = minDelay + 12;

      for (int delay = minDelay; delay <= maxDelay; delay++) {
         PearlCatch.Vec chargePosAtDelay = new PearlCatch.Vec(
            chargeStartPos.x + playerVel.x * delay, chargeStartPos.y + playerVel.y * delay, chargeStartPos.z + playerVel.z * delay
         );
         int maxFlightTicks = this.getMaxFlightTicks(chargePosAtDelay, chargeVel, player);

         for (int flightTicks = 1; flightTicks <= maxFlightTicks; flightTicks++) {
            int pearlTicks = delay + flightTicks;
            PearlCatch.Vec chargeTarget = new PearlCatch.Vec(
               chargePosAtDelay.x + chargeVel.x * flightTicks, chargePosAtDelay.y + chargeVel.y * flightTicks, chargePosAtDelay.z + chargeVel.z * flightTicks
            );
            PearlCatch.Vec pBase = this.calcPearlBasePos(pearlStartPos, playerVel, pearlTicks - 1);
            PearlCatch.Vec pBaseNext = this.calcPearlBasePos(pearlStartPos, playerVel, pearlTicks);
            PearlCatch.Vec D_0 = new PearlCatch.Vec(chargeTarget.x - pBase.x, chargeTarget.y - pBase.y, chargeTarget.z - pBase.z);
            PearlCatch.Vec V_base = new PearlCatch.Vec(pBaseNext.x - pBase.x, pBaseNext.y - pBase.y, pBaseNext.z - pBase.z);
            double v_0 = this.calcGeometricSum(pearlTicks - 1);
            double v_diff = Math.pow(0.99, pearlTicks);
            double d10 = 1.5 * v_0;
            double d11 = 1.5 * v_diff;
            double a = this.dot(V_base, V_base) - d11 * d11;
            double b = -2.0 * this.dot(D_0, V_base) - 2.0 * d10 * d11;
            double c = this.dot(D_0, D_0) - d10 * d10;
            double[] alphas = new double[]{
               0.0, 0.5, 1.0, -c / b, (-b - Math.sqrt(b * b - 4.0 * a * c)) / (2.0 * a), (-b + Math.sqrt(b * b - 4.0 * a * c)) / (2.0 * a)
            };

            for (double alpha : alphas) {
               if (!Double.isNaN(alpha) && !(alpha < 0.0) && !(alpha > 1.0)) {
                  double reqDist = d10 + alpha * d11;
                  if (!(reqDist < 1.0E-9)) {
                     PearlCatch.Vec reqPearlDir = new PearlCatch.Vec(
                        (D_0.x - V_base.x * alpha) / reqDist, (D_0.y - V_base.y * alpha) / reqDist, (D_0.z - V_base.z * alpha) / reqDist
                     );
                     double len = Math.sqrt(this.dot(reqPearlDir, reqPearlDir));
                     if (!(len < 1.0E-9)) {
                        reqPearlDir = new PearlCatch.Vec(reqPearlDir.x / len, reqPearlDir.y / len, reqPearlDir.z / len);
                        float fYaw = (float)Math.toDegrees(Math.atan2(-reqPearlDir.x, reqPearlDir.z));
                        float fPitch = (float)(
                           -Math.toDegrees(Math.atan2(reqPearlDir.y, Math.sqrt(reqPearlDir.x * reqPearlDir.x + reqPearlDir.z * reqPearlDir.z)))
                        );
                        PearlCatch.InterceptPlan candidate = this.evaluatePlan(player, fYaw, fPitch, chargeYaw, chargePitch, delay, delay, true);
                        if (candidate != null) {
                           double score = Math.sqrt(candidate.missDistanceSquared)
                              + flightTicks * 0.005
                              + Math.abs(wrapAngle(fYaw - chargeYaw)) * 5.0E-4
                              + delay * 1.0E-4;
                           if (score < bestScore) {
                              best = candidate;
                              bestScore = score;
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return best;
   }

   private double calcGeometricSum(int ticks) {
      return ticks <= 0 ? 0.0 : 0.99 * (1.0 - Math.pow(0.99, ticks)) / 0.010000000000000009;
   }

   private PearlCatch.Vec calcPearlBasePos(PearlCatch.Vec startPos, PearlCatch.Vec playerVel, int ticks) {
      if (ticks <= 0) {
         return startPos;
      } else {
         double vMultiplier = this.calcGeometricSum(ticks);
         double gravityMultiplier = 98.99999999999991 * (ticks - vMultiplier);
         return new PearlCatch.Vec(
            startPos.x + playerVel.x * vMultiplier, startPos.y + playerVel.y * vMultiplier - 0.03 * gravityMultiplier, startPos.z + playerVel.z * vMultiplier
         );
      }
   }

   private double dot(PearlCatch.Vec a, PearlCatch.Vec b) {
      return a.x * b.x + a.y * b.y + a.z * b.z;
   }

   private int getMaxFlightTicks(PearlCatch.Vec chargeStartPos, PearlCatch.Vec chargeVelocity, EntityPlayerSP player) {
      PearlCatch.Vec chargeEndPos = new PearlCatch.Vec(
         chargeStartPos.x + chargeVelocity.x * 30.0, chargeStartPos.y + chargeVelocity.y * 30.0, chargeStartPos.z + chargeVelocity.z * 30.0
      );
      Vec3 startVec = Vec3.create(chargeStartPos.x, chargeStartPos.y, chargeStartPos.z);
      Vec3 endVec = Vec3.create(chargeEndPos.x, chargeEndPos.y, chargeEndPos.z);
      RayTraceResult result = Minecraft.theWorld().K(startVec, endVec, false, true, false, player);
      if (result != null && result.isNotNull() && result.isBlockHit()) {
         Vec3 hitVec = result.getHitVec();
         if (hitVec.isNotNull()) {
            double dx = hitVec.getX() - chargeStartPos.x;
            double dy = hitVec.getY() - chargeStartPos.y;
            double dz = hitVec.getZ() - chargeStartPos.z;
            double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
            double vel = Math.sqrt(this.dot(chargeVelocity, chargeVelocity));
            if (vel < 1.0E-9) {
               return 0;
            }

            int ticks = (int)Math.ceil(dist / vel - 1.0E-9) - 1;
            return Math.max(0, Math.min(30, ticks));
         }
      }

      return 30;
   }

   private PearlCatch.InterceptPlan evaluatePlan(
      EntityPlayerSP player,
      float pearlYaw,
      float pearlPitch,
      float chargeYaw,
      float chargePitch,
      int chargeTick,
      int minimumDelay,
      boolean absoluteChargeTiming
   ) {
      PearlCatch.Vec pearlDirection = direction(pearlYaw, pearlPitch);
      PearlCatch.Vec chargeDirection = direction(chargeYaw, chargePitch);
      double playerMotionX = player.t();
      double playerMotionY = player.b$src$Z$fqlxe4() ? 0.0 : player.q();
      double playerMotionZ = player.T();
      double pearlX = player.z();
      double pearlY = player.N() + player.Y() - 0.1;
      double pearlZ = player.h();
      double pearlMotionX = pearlDirection.x * 1.5 + playerMotionX;
      double pearlMotionY = pearlDirection.y * 1.5 + playerMotionY;
      double pearlMotionZ = pearlDirection.z * 1.5 + playerMotionZ;
      double chargeX = player.z() + playerMotionX * chargeTick;
      double chargeY = player.N() + player.Y() + playerMotionY * chargeTick;
      double chargeZ = player.h() + playerMotionZ * chargeTick;
      double chargeMotionX = chargeDirection.x * 1.5 + playerMotionX;
      double chargeMotionY = chargeDirection.y * 1.5 + playerMotionY;
      double chargeMotionZ = chargeDirection.z * 1.5 + playerMotionZ;
      double closest = Double.MAX_VALUE;

      for (int tick = 1; tick <= chargeTick + 30; tick++) {
         pearlMotionX *= 0.99;
         pearlMotionY = (pearlMotionY - 0.03) * 0.99;
         pearlMotionZ *= 0.99;
         pearlX += pearlMotionX;
         pearlY += pearlMotionY;
         pearlZ += pearlMotionZ;
         if (tick > chargeTick) {
            chargeX += chargeMotionX;
            chargeY += chargeMotionY;
            chargeZ += chargeMotionZ;
            double dx = pearlX - chargeX;
            double dy = pearlY - chargeY;
            double dz = pearlZ - chargeZ;
            double distance = dx * dx + dy * dy + dz * dz;
            if (distance < closest) {
               closest = distance;
            }
         }
      }

      return chargeTick >= minimumDelay && !(closest > 0.36)
         ? new PearlCatch.InterceptPlan(pearlYaw, pearlPitch, chargeYaw, chargePitch, chargeTick, closest, absoluteChargeTiming)
         : null;
   }

   private static PearlCatch.Vec direction(float yaw, float pitch) {
      double yawRadians = Math.toRadians(yaw);
      double pitchRadians = Math.toRadians(pitch);
      return new PearlCatch.Vec(-Math.sin(yawRadians) * Math.cos(pitchRadians), -Math.sin(pitchRadians), Math.cos(yawRadians) * Math.cos(pitchRadians));
   }

   private static float wrapAngle(float angle) {
      angle %= 360.0F;
      if (angle >= 180.0F) {
         angle -= 360.0F;
      }

      if (angle < -180.0F) {
         angle += 360.0F;
      }

      return angle;
   }

   private static final class InterceptPlan {
      private final float pearlYaw;
      private final float pearlPitch;
      private final float chargeYaw;
      private final float chargePitch;
      private final int chargeTick;
      private final double missDistanceSquared;
      private final boolean absoluteChargeTiming;

      private InterceptPlan(
         float pearlYaw, float pearlPitch, float chargeYaw, float chargePitch, int chargeTick, double missDistanceSquared, boolean absoluteChargeTiming
      ) {
         this.pearlYaw = pearlYaw;
         this.pearlPitch = pearlPitch;
         this.chargeYaw = chargeYaw;
         this.chargePitch = chargePitch;
         this.chargeTick = chargeTick;
         this.missDistanceSquared = missDistanceSquared;
         this.absoluteChargeTiming = absoluteChargeTiming;
      }
   }

   private static enum State {
      IDLE,
      AIMING_PEARL,
      THROWING_PEARL,
      AIMING_CHARGE,
      WAITING_FOR_CHARGE,
      FINISHED;
   }

   private static final class Vec {
      private final double x;
      private final double y;
      private final double z;

      private Vec(double x, double y, double z) {
         this.x = x;
         this.y = y;
         this.z = z;
      }
   }
}
