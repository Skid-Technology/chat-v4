package gg.vape.module.combat.aimassist;

import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPreRenderTick;
import gg.vape.module.Mod;
import gg.vape.module.SubModule;
import gg.vape.module.combat.AimAssist;
import gg.vape.rotation.PlayerMouseRotationApplier;
import gg.vape.rotation.RotationManager;
import gg.vape.utils.MathUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.Vec3d;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;
import java.util.Random;
import org.jetbrains.annotations.Nullable;

public class AimAssistTargetingSubModule extends SubModule<AimAssist> {
   private float pitchVelocity;
   private float lastYawDiff;
   private double predictedZ;
   private float pendingPitch;
   private boolean aimPointInitialized;
   private float airFactor;
   private boolean predictionInitialized;
   private static final float SNAP_ENTER_THRESHOLD = 5.0F;
   private long lastFrameNanos;
   private double lastGroundEyeY;
   private float lastPlayerPitch;
   private float pitchBias;
   private float pendingYaw;
   private float overshoot;
   private float driftPos;
   private double leadY;
   private double leadX;
   private boolean yawSnapped;
   private static final float SNAP_MAX_ANGLE = 20.0F;
   private long driftNextNanos;
   private double leadZ;
   private float pitchFlickTicks;
   private static final float SNAP_EXIT_THRESHOLD = 7.0F;
   private float lastPitchSign;
   private float aimStrength;
   private double aimX;
   private float driftVelocity;
   private float yawAccel;
   private double predictedY;
   private float verticalVelocity;
   private float yawBias;
   private long noiseStartNanos;
   private float lastTargetPitch;
   private double lastEyeY;
   private float driftTarget;
   @Nullable
   private EntityLivingBase target;
   private float lastYawSign;
   private float driftNoise;
   private double predictedX;
   private float lastPlayerYaw;
   private float yawFlickTicks;
   private int targetSwitchTicks;
   private float pitchAccel;
   private boolean pitchSnapped;
   private float lastTargetYaw;
   private float yawVelocity;
   private final Random random = new Random();
   private boolean workerStarted;
   private double aimY;
   private float lastPitchDiff;
   private boolean initialized;
   private double aimZ;

   public static void runWorkerTick(AimAssistTargetingSubModule targetingModule) {
      targetingModule.runTargetLoop();
   }

   private double[] resolveSnapPoint(EntityPlayerSP player, EntityLivingBase target) {
      AimAssist aimAssist = this.getParent();
      double playerEyeY = player.A() + player.X();
      double targetMinY = target.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().getMinY();
      double targetMaxY = target.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().getMaxY();
      double targetHeight = targetMaxY - targetMinY;
      double targetCenterY = targetMinY + targetHeight * 0.65;
      double airborneOffset = Math.min(0.85, (double)this.airFactor);
      double eyeOffset = 0.1 + airborneOffset;
      double eyeAlignedY = Math.max(targetMinY + 0.01, Math.min(targetMaxY - 0.01, playerEyeY - eyeOffset));
      double centerBlend = Math.max(0.0, Math.min(1.0, airborneOffset / 0.55));
      double targetY = eyeAlignedY + (targetCenterY - eyeAlignedY) * centerBlend;
      targetY = Math.max(targetMinY + 0.01, Math.min(targetMaxY - 0.01, targetY));
      if (aimAssist.targetArea.getValue() == aimAssist.closestAreaMode) {
         Vec3d closestPoint = RotationUtil.T(player, target.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl(), 0.0, 0.0, 0.0);
         double closestY = Math.max(targetMinY + 0.01, Math.min(targetMaxY - 0.01, closestPoint.getY() - eyeOffset));
         closestY += (targetCenterY - closestY) * centerBlend;
         closestY = Math.max(targetMinY + 0.01, Math.min(targetMaxY - 0.01, closestY));
         return new double[]{closestPoint.getX(), closestY, closestPoint.getZ()};
      } else {
         return new double[]{target.c(), targetY, target.Z()};
      }
   }

   private void updateAim() {
      AimAssist aimAssist = this.getParent();
      boolean yawSnapEnabled = aimAssist.getHorizontalSpeed().getValue().floatValue() > 20.0F;
      boolean pitchSnapEnabled = aimAssist.getAimVertically().getEffectiveValue() && aimAssist.getVerticalSpeed().getValue().floatValue() > 20.0F;
      boolean snapYaw = yawSnapEnabled && this.yawSnapped;
      boolean snapPitch = pitchSnapEnabled && this.pitchSnapped;
      if (this.target != null && !this.target.isNull()) {
         EntityPlayerSP player = Minecraft.thePlayer();
         if (player.isNull()) {
            this.reset();
         } else {
            long nowNanos = System.nanoTime();
            float deltaTime;
            if (this.initialized && this.lastFrameNanos != 0L) {
               deltaTime = (float)(nowNanos - this.lastFrameNanos) / 1.0E9F;
               deltaTime = Math.max(0.008333334F, Math.min(0.12F, deltaTime));
            } else {
               deltaTime = 0.016666668F;
            }

            this.lastFrameNanos = nowNanos;
            double[] resolvedAimPoint = this.resolveAimTarget(player, this.target);
            double resolvedAimX = resolvedAimPoint[0];
            double resolvedAimY = resolvedAimPoint[1];
            double resolvedAimZ = resolvedAimPoint[2];
            double targetMotionX = this.target.t();
            double targetMotionY = this.target.q();
            double targetMotionZ = this.target.T();
            double relativeMotionX = targetMotionX - player.t();
            double relativeMotionY = targetMotionY - player.q();
            double relativeMotionZ = targetMotionZ - player.T();
            double targetLeadX = targetMotionX + relativeMotionX * 0.08;
            double targetLeadY = targetMotionY + relativeMotionY * 0.1;
            double targetLeadZ = targetMotionZ + relativeMotionZ * 0.08;
            if (!this.aimPointInitialized) {
               this.aimX = resolvedAimX;
               this.aimY = resolvedAimY;
               this.aimZ = resolvedAimZ;
               this.leadX = targetLeadX;
               this.leadY = targetLeadY;
               this.leadZ = targetLeadZ;
               this.aimPointInitialized = true;
            }

            double targetHorizontalSpeed = Math.sqrt(targetMotionX * targetMotionX + targetMotionZ * targetMotionZ);
            double relativeHorizontalSpeed = Math.sqrt(relativeMotionX * relativeMotionX + relativeMotionZ * relativeMotionZ);
            double targetDistance = player.getDistanceToEntity(this.target);
            double baseAimSmoothing = aimAssist.targetArea.getValue() == aimAssist.closestAreaMode ? 0.3 : 0.28;
            double aimSmoothing = Math.max(0.08, Math.min(0.75, baseAimSmoothing + Math.min(0.35, relativeHorizontalSpeed * 0.5)));
            double leadMotionScale = targetHorizontalSpeed + relativeHorizontalSpeed * 0.25;
            double leadSmoothing = Math.max(0.12, Math.min(0.68, 0.18 + Math.min(0.42, leadMotionScale * 0.72)));
            double frameScale = Math.max(0.45, Math.min(2.4, deltaTime * 60.0));
            double aimBlend = 1.0 - Math.pow(1.0 - aimSmoothing, frameScale);
            double leadBlend = 1.0 - Math.pow(1.0 - leadSmoothing, frameScale);
            this.aimX = this.aimX + (resolvedAimX - this.aimX) * aimBlend;
            this.aimY = this.aimY + (resolvedAimY - this.aimY) * aimBlend;
            this.aimZ = this.aimZ + (resolvedAimZ - this.aimZ) * aimBlend;
            this.leadX = this.leadX + (targetLeadX - this.leadX) * leadBlend;
            this.leadY = this.leadY + (targetLeadY - this.leadY) * leadBlend;
            this.leadZ = this.leadZ + (targetLeadZ - this.leadZ) * leadBlend;
            double aimLagX = resolvedAimX - this.aimX;
            double aimLagZ = resolvedAimZ - this.aimZ;
            double horizontalAimLag = Math.sqrt(aimLagX * aimLagX + aimLagZ * aimLagZ);
            double maximumAimLag = 0.3 + targetHorizontalSpeed * 1.3 + relativeHorizontalSpeed * 0.4;
            if (horizontalAimLag > maximumAimLag) {
               this.aimX = resolvedAimX;
               this.aimY = resolvedAimY;
               this.aimZ = resolvedAimZ;
               this.leadX = targetLeadX;
               this.leadY = targetLeadY;
               this.leadZ = targetLeadZ;
            }

            double leadAmount = 0.35 + targetDistance * 0.045 + targetHorizontalSpeed * 0.95 + Math.min(0.18, relativeHorizontalSpeed * 0.12);
            leadAmount = Math.max(0.1, Math.min(1.05, leadAmount));
            double distanceLeadScale = Math.max(0.0, Math.min(1.0, (targetDistance - 0.8) / 2.5));
            leadAmount *= 0.3 + 0.7 * distanceLeadScale;
            leadAmount = Math.max(0.05, leadAmount);
            double playerX = player.c();
            double playerZ = player.Z();
            double playerEyeY = player.A() + player.X();
            float measuredVerticalVelocity = 0.0F;
            if (this.initialized && this.lastEyeY != 0.0) {
               measuredVerticalVelocity = (float)((playerEyeY - this.lastEyeY) / deltaTime);
            }

            this.verticalVelocity = this.verticalVelocity + (measuredVerticalVelocity - this.verticalVelocity) * 0.65F;
            this.lastEyeY = playerEyeY;
            if (player.b$src$Z$fqlxe4()) {
               this.lastGroundEyeY = playerEyeY;
               this.airFactor *= 0.35F;
            } else {
               if (this.lastGroundEyeY == 0.0) {
                  this.lastGroundEyeY = playerEyeY;
               }

               double airborneHeight = Math.max(0.0, playerEyeY - this.lastGroundEyeY);
               float velocityAirFactor = (float)Math.min(0.7, Math.max(0.0, (double)this.verticalVelocity) * 0.34);
               float heightAirFactor = (float)Math.min(0.82, airborneHeight * 0.58);
               this.airFactor = Math.max(this.airFactor * 0.92F, Math.max(velocityAirFactor, heightAirFactor));
            }

            double adjustedAimX = this.aimX;
            double adjustedAimZ = this.aimZ;
            double verticalAimDelta = this.aimY - playerEyeY;
            double verticalOffsetScale = Math.max(0.0, Math.min(1.0, Math.abs(verticalAimDelta) / 0.7));
            double verticalLeadAmount = Math.min(leadAmount, 0.7 + targetDistance * 0.04);
            double verticalLeadScale = 0.28 + 0.52 * verticalOffsetScale;
            double verticalLead = this.leadY * verticalLeadAmount * verticalLeadScale;
            double verticalLeadLimit = 0.16 + targetDistance * 0.055;
            verticalLead = Math.max(-verticalLeadLimit, Math.min(verticalLeadLimit, verticalLead));
            double adjustedAimY = this.aimY + verticalLead;
            if (snapYaw) {
               adjustedAimX = resolvedAimX;
               adjustedAimZ = resolvedAimZ;
            }

            if (snapPitch) {
               adjustedAimY = resolvedAimY;
            }

            double aimDeltaX = adjustedAimX - playerX;
            double aimDeltaZ = adjustedAimZ - playerZ;
            double aimDeltaY = adjustedAimY - playerEyeY;
            double horizontalAimDistance = Math.sqrt(aimDeltaX * aimDeltaX + aimDeltaZ * aimDeltaZ);
            float targetYaw = (float)(Math.toDegrees(Math.atan2(aimDeltaZ, aimDeltaX)) - 90.0);
            float targetPitch = (float)(-Math.toDegrees(Math.atan2(aimDeltaY, Math.max(horizontalAimDistance, 1.0E-4))));
            if (this.initialized) {
               float yawTargetChange = Math.abs(MathUtil.wrapAngleTo180(targetYaw - this.lastTargetYaw));
               float pitchTargetChange = Math.abs(MathUtil.wrapAngleTo180(targetPitch - this.lastTargetPitch));
               float abruptChangeThreshold = aimAssist.targetArea.getValue() == aimAssist.closestAreaMode ? 20.0F : 12.0F;
               float maximumTargetChange = Math.max(yawTargetChange, pitchTargetChange);
               float abruptChangeFactor = Math.max(0.0F, Math.min(1.0F, (maximumTargetChange - abruptChangeThreshold) / 50.0F));
               this.overshoot = Math.max(this.overshoot, abruptChangeFactor);
               if (abruptChangeFactor > 0.3F) {
                  this.aimStrength *= 0.3F;
                  this.aimX = resolvedAimX;
                  this.aimY = resolvedAimY;
                  this.aimZ = resolvedAimZ;
               }

               float yawRateScale = 1.0F + Math.min(2.0F, yawTargetChange / 60.0F);
               float pitchRateScale = 1.0F + Math.min(2.0F, pitchTargetChange / 60.0F);
               float maximumYawRate = (120.0F + (float)(relativeHorizontalSpeed * 700.0)) * yawRateScale;
               float maximumPitchRate = (95.0F + (float)(Math.abs(this.leadY) * 550.0)) * pitchRateScale;
               float viewYawDifference = Math.abs(MathUtil.wrapAngleTo180(targetYaw - RotationManager.getViewYaw(player)));
               float wideTurnScale = smoothStep(10.0F, 35.0F, viewYawDifference);
               maximumYawRate *= 1.0F + wideTurnScale * 1.5F;
               maximumYawRate = Math.max(90.0F, Math.min(1080.0F, maximumYawRate));
               maximumPitchRate = Math.max(70.0F, Math.min(500.0F, maximumPitchRate));
               float yawStep = MathUtil.wrapAngleTo180(targetYaw - this.lastTargetYaw);
               float pitchStep = MathUtil.wrapAngleTo180(targetPitch - this.lastTargetPitch);
               if (!snapYaw) {
                  yawStep = Math.max(-maximumYawRate * deltaTime, Math.min(maximumYawRate * deltaTime, yawStep));
               }

               if (!snapPitch) {
                  pitchStep = Math.max(-maximumPitchRate * deltaTime, Math.min(maximumPitchRate * deltaTime, pitchStep));
               }

               targetYaw = this.lastTargetYaw + yawStep;
               targetPitch = this.lastTargetPitch + pitchStep;
            }

            float overshootDecay = (float)Math.pow(0.02, deltaTime);
            this.overshoot *= overshootDecay;
            if (this.overshoot < 0.01F) {
               this.overshoot = 0.0F;
            }

            float overshootMultiplier = 1.0F + 3.0F * this.overshoot;
            float playerYaw = RotationManager.getViewYaw(player);
            float playerPitch = RotationManager.getViewPitch(player);
            float yawError = MathUtil.wrapAngleTo180(targetYaw - playerYaw);
            float pitchError = MathUtil.wrapAngleTo180(targetPitch - playerPitch);
            if (!aimAssist.getAimVertically().getEffectiveValue()) {
               pitchError = 0.0F;
            }

            float absoluteYawError = Math.abs(yawError);
            float absolutePitchError = Math.abs(pitchError);
            float combinedAngleError = (float)Math.sqrt(absoluteYawError * absoluteYawError + absolutePitchError * absolutePitchError);
            this.yawSnapped = shouldSnap(yawSnapEnabled, this.yawSnapped, absoluteYawError);
            this.pitchSnapped = shouldSnap(pitchSnapEnabled, this.pitchSnapped, absolutePitchError);
            snapYaw = yawSnapEnabled && this.yawSnapped;
            snapPitch = pitchSnapEnabled && this.pitchSnapped;
            float desiredAimStrength = 1.0F - smoothStep(1.5F, 8.0F, combinedAngleError);
            if (this.initialized) {
               float yawClosingSpeed = -(absoluteYawError - Math.abs(this.lastYawDiff)) / deltaTime;
               float closingAdjustment = Math.max(-0.3F, Math.min(0.3F, yawClosingSpeed / 20.0F));
               desiredAimStrength += closingAdjustment;
               desiredAimStrength = Math.max(0.0F, Math.min(1.0F, desiredAimStrength));
            }

            float aimStrengthBlend = desiredAimStrength > this.aimStrength
               ? Math.max(0.01F, Math.min(0.25F, deltaTime * 3.0F))
               : Math.max(0.05F, Math.min(0.8F, deltaTime * 20.0F));
            this.aimStrength = this.aimStrength + (desiredAimStrength - this.aimStrength) * aimStrengthBlend;
            float currentAimStrength = this.aimStrength;
            float smoothedTargetYawRate = 0.0F;
            float smoothedTargetPitchRate = 0.0F;
            float playerYawRate = 0.0F;
            float playerPitchRate = 0.0F;
            if (this.initialized) {
               float targetYawRate = MathUtil.wrapAngleTo180(targetYaw - this.lastTargetYaw) / deltaTime;
               float targetPitchRate = MathUtil.wrapAngleTo180(targetPitch - this.lastTargetPitch) / deltaTime;
               playerYawRate = MathUtil.wrapAngleTo180(playerYaw - this.lastPlayerYaw) / deltaTime;
               playerPitchRate = MathUtil.wrapAngleTo180(playerPitch - this.lastPlayerPitch) / deltaTime;
               float accelerationBlend = Math.max(0.05F, Math.min(0.45F, deltaTime * 12.0F));
               this.yawAccel = this.yawAccel + (targetYawRate - this.yawAccel) * accelerationBlend;
               this.pitchAccel = this.pitchAccel + (targetPitchRate - this.pitchAccel) * accelerationBlend;
               smoothedTargetYawRate = this.yawAccel;
               smoothedTargetPitchRate = this.pitchAccel;
            }

            float horizontalSpeed = aimAssist.getHorizontalSpeed().getValue().floatValue() * 0.75F;
            float verticalSpeed = aimAssist.getVerticalSpeed().getValue().floatValue() * 0.75F;
            float horizontalSpeedFactor = Math.max(0.0F, Math.min(1.0F, (horizontalSpeed - 10.0F) / 90.0F));
            float verticalSpeedFactor = Math.max(0.0F, Math.min(1.0F, (verticalSpeed - 10.0F) / 90.0F));
            if (Math.signum(yawError) != Math.signum(this.lastYawDiff) && Math.abs(yawError) > 0.1F && Math.abs(this.lastYawDiff) > 0.1F) {
               this.yawBias *= 0.3F;
            }

            if (Math.signum(pitchError) != Math.signum(this.lastPitchDiff) && Math.abs(pitchError) > 0.1F && Math.abs(this.lastPitchDiff) > 0.1F) {
               this.pitchBias *= 0.3F;
            }

            float squaredAimStrength = currentAimStrength * currentAimStrength;
            this.yawBias += yawError * deltaTime * squaredAimStrength * (1.0F - horizontalSpeedFactor);
            this.pitchBias += pitchError * deltaTime * squaredAimStrength * (1.0F - verticalSpeedFactor);
            float biasRetention = 1.0F - (1.0F - currentAimStrength) * Math.max(0.0F, Math.min(0.5F, deltaTime * 5.0F));
            this.yawBias *= biasRetention;
            this.pitchBias *= biasRetention;
            float yawBiasLimit = 15.0F * (1.0F - horizontalSpeedFactor * 0.9F);
            float pitchBiasLimit = 10.0F * (1.0F - verticalSpeedFactor * 0.9F);
            this.yawBias = Math.max(-yawBiasLimit, Math.min(yawBiasLimit, this.yawBias));
            this.pitchBias = Math.max(-pitchBiasLimit, Math.min(pitchBiasLimit, this.pitchBias));
            float yawErrorVelocity = this.initialized ? (yawError - this.lastYawDiff) / deltaTime : 0.0F;
            float pitchErrorVelocity = this.initialized ? (pitchError - this.lastPitchDiff) / deltaTime : 0.0F;
            float velocityBlend = 0.15F;
            this.yawVelocity = this.yawVelocity * (1.0F - velocityBlend) + yawErrorVelocity * velocityBlend;
            this.pitchVelocity = this.pitchVelocity * (1.0F - velocityBlend) + pitchErrorVelocity * velocityBlend;
            float flickThreshold = aimAssist.targetArea.getValue() == aimAssist.closestAreaMode ? 1.5F : 0.5F;
            float yawSign = Math.signum(yawError);
            this.yawFlickTicks = yawSign != this.lastYawSign && Math.abs(yawError) > flickThreshold
               ? Math.min(this.yawFlickTicks + 1.0F, 8.0F)
               : Math.max(0.0F, this.yawFlickTicks - deltaTime * 3.0F);
            this.lastYawSign = yawSign;
            float pitchSign = Math.signum(pitchError);
            this.pitchFlickTicks = pitchSign != this.lastPitchSign && Math.abs(pitchError) > flickThreshold
               ? Math.min(this.pitchFlickTicks + 1.0F, 8.0F)
               : Math.max(0.0F, this.pitchFlickTicks - deltaTime * 3.0F);
            this.lastPitchSign = pitchSign;
            float yawFlickFactor = Math.max(0.0F, Math.min(1.0F, this.yawFlickTicks / 5.0F));
            float pitchFlickFactor = Math.max(0.0F, Math.min(1.0F, this.pitchFlickTicks / 5.0F));
            float limitedHorizontalSpeed = Math.min(horizontalSpeed, 10.0F);
            float limitedVerticalSpeed = Math.min(verticalSpeed, 10.0F);
            float horizontalGainInput = (limitedHorizontalSpeed - 1.0F) / 9.0F;
            float verticalGainInput = (limitedVerticalSpeed - 1.0F) / 9.0F;
            horizontalGainInput *= horizontalGainInput;
            verticalGainInput *= verticalGainInput;
            float horizontalGainScale = 0.15F + 0.85F * Math.max(0.0F, Math.min(1.0F, horizontalGainInput));
            float verticalGainScale = 0.15F + 0.85F * Math.max(0.0F, Math.min(1.0F, verticalGainInput));
            float yawErrorGain = lerp(currentAimStrength, 8.0F, 2.5F + limitedHorizontalSpeed * 0.15F) * horizontalGainScale;
            float pitchErrorGain = lerp(currentAimStrength, 7.0F, 2.2F + limitedVerticalSpeed * 0.13F) * verticalGainScale;
            float yawBiasGain = lerp(currentAimStrength, 0.15F, 0.8F + limitedHorizontalSpeed * 0.04F) * horizontalGainScale;
            float pitchBiasGain = lerp(currentAimStrength, 0.12F, 0.65F + limitedVerticalSpeed * 0.035F) * verticalGainScale;
            float yawVelocityGain = lerp(currentAimStrength, 0.08F, 0.25F) * horizontalGainScale;
            float pitchVelocityGain = lerp(currentAimStrength, 0.06F, 0.2F) * verticalGainScale;
            float targetYawRateGain = (0.85F + limitedHorizontalSpeed * 0.015F) * horizontalGainScale;
            float targetPitchRateGain = (0.82F + limitedVerticalSpeed * 0.013F) * verticalGainScale;
            float playerYawCompensation = lerp(currentAimStrength, 0.1F, 0.3F);
            float playerPitchCompensation = lerp(currentAimStrength, 0.08F, 0.25F);
            yawErrorGain *= 1.0F - 0.6F * yawFlickFactor;
            yawVelocityGain *= 1.0F + 2.0F * yawFlickFactor;
            pitchErrorGain *= 1.0F - 0.6F * pitchFlickFactor;
            pitchVelocityGain *= 1.0F + 2.0F * pitchFlickFactor;
            float yawRate = yawErrorGain * yawError
               + yawBiasGain * this.yawBias
               + yawVelocityGain * this.yawVelocity
               + targetYawRateGain * smoothedTargetYawRate
               - playerYawCompensation * playerYawRate;
            float pitchRate = 0.0F;
            if (aimAssist.getAimVertically().getEffectiveValue()) {
               pitchRate = pitchErrorGain * pitchError
                  + pitchBiasGain * this.pitchBias
                  + pitchVelocityGain * this.pitchVelocity
                  + targetPitchRateGain * smoothedTargetPitchRate
                  - playerPitchCompensation * playerPitchRate;
            } else {
               this.pitchBias = 0.0F;
               this.pitchVelocity = 0.0F;
            }

            if (!snapPitch) {
               pitchRate += this.computePitchDrift(playerPitch, deltaTime, nowNanos);
            }

            float strafeMultiplier = 1.0F;
            float strafeInput = player.movementInput().T();
            if (aimAssist.getStrafeIncrease().getEffectiveValue() && Math.abs(strafeInput) > 0.01F) {
               boolean targetToRight = yawError > 0.0F;
               boolean strafingAway = targetToRight && strafeInput < 0.0F || !targetToRight && strafeInput > 0.0F;
               if (strafingAway) {
                  strafeMultiplier = 1.15F;
               }
            }

            yawRate *= strafeMultiplier;
            float wideYawScale = smoothStep(8.0F, 40.0F, absoluteYawError);
            float wideTurnMultiplier = 1.0F + wideYawScale * 2.5F;
            float yawBaseLimit = (22.0F + limitedHorizontalSpeed * 15.0F) * horizontalGainScale * overshootMultiplier * wideTurnMultiplier;
            float pitchBaseLimit = (18.0F + limitedVerticalSpeed * 13.0F) * verticalGainScale * overshootMultiplier;
            float yawMotionLimit = (Math.abs(smoothedTargetYawRate) * 0.4F + 18.0F) * (0.35F + horizontalGainScale * 0.65F) * wideTurnMultiplier;
            float pitchMotionLimit = (Math.abs(smoothedTargetPitchRate) * 0.38F + 14.0F) * (0.35F + verticalGainScale * 0.65F);
            float maximumYawOutput = Math.min(400.0F * overshootMultiplier * wideTurnMultiplier, Math.max(yawBaseLimit, yawMotionLimit));
            float maximumPitchOutput = Math.min(300.0F * overshootMultiplier, Math.max(pitchBaseLimit, pitchMotionLimit));
            yawRate = Math.max(-maximumYawOutput, Math.min(maximumYawOutput, yawRate));
            pitchRate = Math.max(-maximumPitchOutput, Math.min(maximumPitchOutput, pitchRate));
            float distanceScale = smoothStep(0.5F, 3.0F, (float)targetDistance);
            float wideTurnBaseScale = lerp(wideYawScale, 0.15F, 0.65F);
            float yawDistanceMultiplier = lerp(currentAimStrength, wideTurnBaseScale + (1.0F - wideTurnBaseScale) * distanceScale, 0.4F + 0.6F * distanceScale);
            float pitchDistanceMultiplier = lerp(currentAimStrength, 0.2F + 0.8F * distanceScale, 0.45F + 0.55F * distanceScale);
            yawRate *= yawDistanceMultiplier;
            pitchRate *= pitchDistanceMultiplier;
            float noiseElapsedSeconds = (float)(nowNanos - this.noiseStartNanos) / 1.0E9F;
            float highFrequencyYawNoise = (float)(
               Math.sin(noiseElapsedSeconds * 62.83) * 0.4 + Math.sin(noiseElapsedSeconds * 47.12) * 0.25 + Math.sin(noiseElapsedSeconds * 78.54) * 0.15
            );
            float highFrequencyPitchNoise = (float)(
               Math.sin(noiseElapsedSeconds * 56.55 + 1.3) * 0.35
                  + Math.sin(noiseElapsedSeconds * 43.98 + 0.7) * 0.2
                  + Math.sin(noiseElapsedSeconds * 72.26 + 2.1) * 0.12
            );
            float mediumFrequencyYawNoise = (float)(Math.sin(noiseElapsedSeconds * 12.57) * 0.8 + Math.sin(noiseElapsedSeconds * 7.85) * 0.5);
            float mediumFrequencyPitchNoise = (float)(Math.sin(noiseElapsedSeconds * 10.47 + 0.9) * 0.6 + Math.sin(noiseElapsedSeconds * 5.65 + 1.8) * 0.4);
            float lowFrequencyYawNoise = (float)(Math.sin(noiseElapsedSeconds * 1.26) * 0.3);
            float lowFrequencyPitchNoise = (float)(Math.sin(noiseElapsedSeconds * 0.94 + 0.5) * 0.2);
            float noiseStrength = 0.15F + 0.85F * currentAimStrength;
            float speedNoiseScale = 0.5F + 0.5F * (1.0F - horizontalGainInput);
            float yawNoise = (highFrequencyYawNoise + mediumFrequencyYawNoise + lowFrequencyYawNoise) * noiseStrength * speedNoiseScale * 1.5F;
            float pitchNoise = (highFrequencyPitchNoise + mediumFrequencyPitchNoise + lowFrequencyPitchNoise) * noiseStrength * speedNoiseScale;
            if (!snapYaw) {
               yawRate += yawNoise;
            }

            if (aimAssist.getAimVertically().getEffectiveValue() && !snapPitch) {
               pitchRate += pitchNoise;
            }

            float yawFrameDelta = yawRate * deltaTime;
            float pitchFrameDelta = pitchRate * deltaTime;
            float sensitivity = Minecraft.gameSettings().y();
            float sensitivityBase = sensitivity * 0.6F + 0.2F;
            float sensitivityScale = sensitivityBase * sensitivityBase * sensitivityBase * 8.0F;
            float mouseAngleUnit = sensitivityScale * 0.15F;
            if (mouseAngleUnit > 1.0E-5F) {
               float yawVelocityUnits = yawFrameDelta / mouseAngleUnit;
               float pitchVelocityUnits = pitchFrameDelta / mouseAngleUnit;
               float pitchBlendFactor = aimAssist.getAimVertically().getEffectiveValue() ? verticalSpeedFactor : 0.0F;
               if (snapYaw) {
                  this.pendingYaw = 0.0F;
               }

               if (snapPitch) {
                  this.pendingPitch = 0.0F;
               }

               if (!snapYaw) {
                  if (horizontalSpeedFactor > 0.0F) {
                     float targetYawUnits = yawError / mouseAngleUnit;
                     float yawCorrection = targetYawUnits - this.pendingYaw;
                     float maximumYawCorrection = horizontalSpeed * 2.0F;
                     yawCorrection = Math.max(-maximumYawCorrection, Math.min(maximumYawCorrection, yawCorrection));
                     this.pendingYaw += yawVelocityUnits * (1.0F - horizontalSpeedFactor) + yawCorrection * horizontalSpeedFactor;
                  } else {
                     this.pendingYaw += yawVelocityUnits;
                  }
               }

               if (!snapPitch) {
                  if (pitchBlendFactor > 0.0F) {
                     float targetPitchUnits = aimAssist.getAimVertically().getEffectiveValue() ? pitchError / mouseAngleUnit : 0.0F;
                     float pitchCorrection = targetPitchUnits - this.pendingPitch;
                     float maximumPitchCorrection = verticalSpeed * 2.0F;
                     pitchCorrection = Math.max(-maximumPitchCorrection, Math.min(maximumPitchCorrection, pitchCorrection));
                     this.pendingPitch += pitchVelocityUnits * (1.0F - pitchBlendFactor) + pitchCorrection * pitchBlendFactor;
                  } else {
                     this.pendingPitch += pitchVelocityUnits;
                  }
               }
            }

            if (!this.initialized) {
               this.initialized = true;
            }

            this.lastYawDiff = yawError;
            this.lastPitchDiff = pitchError;
            this.lastTargetYaw = targetYaw;
            this.lastTargetPitch = targetPitch;
            this.lastPlayerYaw = playerYaw;
            this.lastPlayerPitch = playerPitch;
         }
      } else {
         this.reset();
      }
   }

   @Override
   public void onEnable() {
      this.reset();
      if (!this.workerStarted) {
         this.workerStarted = true;
         new AimAssistWorkerThread(this).start();
      }
   }

   private static float lerp(float factor, float start, float end) {
      return start + factor * (end - start);
   }

   private void runTargetLoop() {
      AimAssist aimAssist = this.getParent();
      if (!Minecraft.theWorld().isNull() && !Minecraft.thePlayer().isNull()) {
         if (!aimAssist.canAim()) {
            this.reset();
            this.target = null;
         } else {
            if (this.target != null && this.target.isNull()) {
               this.target = null;
            }

            if (aimAssist.getRequireMouseDown().getEffectiveValue() && !ClientSettings.isAttackButtonDown()) {
               this.target = null;
               this.reset();
            } else {
               if (this.target != null
                  && (RotationUtil.C(this.target) || Minecraft.thePlayer().getDistanceToEntity(this.target) > aimAssist.getDistance().getValue())) {
                  this.reset();
                  this.target = null;
               }

               if (aimAssist.getRequireMouseDown().getEffectiveValue() && ClientSettings.isAttackButtonDown() && this.target == null
                  || !aimAssist.getRequireMouseDown().getEffectiveValue()) {
                  EntityLivingBase candidateTarget = aimAssist.findBestTarget();
                  if (!aimAssist.getRequireMouseDown().getEffectiveValue()) {
                     this.targetSwitchTicks++;
                     if (this.targetSwitchTicks > 700 || this.target == null) {
                        if (this.target == null || !this.target.equals(candidateTarget)) {
                           this.reset();
                        }

                        this.target = candidateTarget;
                        this.targetSwitchTicks = 0;
                     }
                  } else {
                     if (this.target == null || !this.target.equals(candidateTarget)) {
                        this.reset();
                     }

                     this.target = candidateTarget;
                  }
               }

               if (Minecraft.theWorld().getObject() != null) {
                  if (this.target != null && Minecraft.currentScreen().getObject() == null && gg.vape.module.none.ClientSettings.INSTANCE.inputEnabled) {
                     this.updateAim();
                  } else {
                     this.target = null;
                     this.reset();
                  }
               }
            }
         }
      }
   }

   private void reset() {
      this.pendingYaw = 0.0F;
      this.pendingPitch = 0.0F;
      this.yawSnapped = false;
      this.pitchSnapped = false;
      this.yawBias = 0.0F;
      this.pitchBias = 0.0F;
      this.yawVelocity = 0.0F;
      this.pitchVelocity = 0.0F;
      this.lastYawDiff = 0.0F;
      this.lastPitchDiff = 0.0F;
      this.lastTargetYaw = 0.0F;
      this.lastTargetPitch = 0.0F;
      this.yawAccel = 0.0F;
      this.pitchAccel = 0.0F;
      this.lastPlayerYaw = 0.0F;
      this.lastPlayerPitch = 0.0F;
      this.initialized = false;
      this.lastFrameNanos = 0L;
      this.aimPointInitialized = false;
      this.aimX = 0.0;
      this.aimY = 0.0;
      this.aimZ = 0.0;
      this.leadX = 0.0;
      this.leadY = 0.0;
      this.leadZ = 0.0;
      this.aimStrength = 0.0F;
      this.yawFlickTicks = 0.0F;
      this.pitchFlickTicks = 0.0F;
      this.lastYawSign = 0.0F;
      this.lastPitchSign = 0.0F;
      this.overshoot = 0.0F;
      this.lastEyeY = 0.0;
      this.lastGroundEyeY = 0.0;
      this.verticalVelocity = 0.0F;
      this.airFactor = 0.0F;
      this.predictionInitialized = false;
      this.predictedX = 0.0;
      this.predictedY = 0.0;
      this.predictedZ = 0.0;
      this.noiseStartNanos = System.nanoTime();
      this.driftPos = 0.0F;
      this.driftVelocity = 0.0F;
      this.driftTarget = 0.0F;
      this.driftNoise = 0.0F;
      this.driftNextNanos = 0L;
   }

   @EventHandler
   public void onPreRenderTick(EventPreRenderTick eventPreRenderTick) {
      if (!eventPreRenderTick.getWorld().isNull() && this.target != null) {
         AimAssist aimAssist = this.getParent();
         boolean yawSnapEnabled = aimAssist.getHorizontalSpeed().getValue().floatValue() > 20.0F;
         boolean pitchSnapEnabled = aimAssist.getAimVertically().getEffectiveValue() && aimAssist.getVerticalSpeed().getValue().floatValue() > 20.0F;
         boolean snapYaw = yawSnapEnabled && this.yawSnapped;
         boolean snapPitch = pitchSnapEnabled && this.pitchSnapped;
         int yawSteps = snapYaw ? Math.round(this.pendingYaw) : (int)this.pendingYaw;
         int pitchSteps = snapPitch ? Math.round(this.pendingPitch) : (int)this.pendingPitch;
         float remainingYaw = snapYaw ? 0.0F : this.pendingYaw - yawSteps;
         float remainingPitch = snapPitch ? 0.0F : this.pendingPitch - pitchSteps;
         if (Math.abs(yawSteps) == 0) {
            yawSteps = 0;
         }

         if (Math.abs(pitchSteps) == 0) {
            pitchSteps = 0;
         }

         float sensitivity = Minecraft.gameSettings().y();
         float sensitivityBase = sensitivity * 0.6F + 0.2F;
         float sensitivityScale = sensitivityBase * sensitivityBase * sensitivityBase * 8.0F;
         float mouseDeltaX = yawSteps * sensitivityScale;
         float mouseDeltaY = -pitchSteps * sensitivityScale;
         if ((snapYaw || snapPitch) && Minecraft.thePlayer().isNotNull()) {
            EntityPlayerSP player = Minecraft.thePlayer();
            double[] snapPoint = this.resolveSnapPoint(player, this.target);
            double deltaX = snapPoint[0] - player.c();
            double deltaZ = snapPoint[2] - player.Z();
            double deltaY = snapPoint[1] - (player.A() + player.X());
            double horizontalDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
            float targetYaw = (float)(Math.toDegrees(Math.atan2(deltaZ, deltaX)) - 90.0);
            float targetPitch = (float)(-Math.toDegrees(Math.atan2(deltaY, Math.max(horizontalDistance, 1.0E-4))));
            if (snapYaw) {
               mouseDeltaX = MathUtil.wrapAngleTo180(targetYaw - RotationManager.getViewYaw(player)) / 0.15F;
            }

            if (snapPitch) {
               mouseDeltaY = -MathUtil.wrapAngleTo180(targetPitch - RotationManager.getViewPitch(player)) / 0.15F;
            }
         }

         PlayerMouseRotationApplier.applyTrackedMouseDelta(mouseDeltaX, mouseDeltaY);
         this.pendingYaw = remainingYaw;
         this.pendingPitch = remainingPitch;
      }
   }

   private double[] resolveAimTarget(EntityPlayerSP player, EntityLivingBase target) {
      AimAssist aimAssist = this.getParent();
      double playerEyeY = player.A() + player.X();
      double targetMinY = target.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().getMinY();
      double targetMaxY = target.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().getMaxY();
      double targetHeight = targetMaxY - targetMinY;
      double targetCenterY = targetMinY + targetHeight * 0.65;
      double airborneOffset = Math.min(0.85, (double)this.airFactor);
      double eyeOffset = 0.1 + airborneOffset;
      double eyeAlignedY = Math.max(targetMinY + 0.01, Math.min(targetMaxY - 0.01, playerEyeY - eyeOffset));
      double centerBlend = Math.max(0.0, Math.min(1.0, airborneOffset / 0.55));
      double targetY = eyeAlignedY + (targetCenterY - eyeAlignedY) * centerBlend;
      targetY = Math.max(targetMinY + 0.01, Math.min(targetMaxY - 0.01, targetY));
      if (aimAssist.targetArea.getValue() == aimAssist.closestAreaMode) {
         Vec3d closestPoint = RotationUtil.T(player, target.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl(), 0.0, 0.0, 0.0);
         double closestY = Math.max(targetMinY + 0.01, Math.min(targetMaxY - 0.01, closestPoint.getY() - eyeOffset));
         closestY += (targetCenterY - closestY) * centerBlend;
         closestY = Math.max(targetMinY + 0.01, Math.min(targetMaxY - 0.01, closestY));
         double resolvedX = closestPoint.getX();
         double resolvedY = closestY;
         double resolvedZ = closestPoint.getZ();
         if (this.predictionInitialized) {
            double smoothingFactor = 0.35;
            resolvedX = this.predictedX + (resolvedX - this.predictedX) * smoothingFactor;
            resolvedY = this.predictedY + (closestY - this.predictedY) * smoothingFactor;
            resolvedZ = this.predictedZ + (resolvedZ - this.predictedZ) * smoothingFactor;
         }

         this.predictedX = resolvedX;
         this.predictedY = resolvedY;
         this.predictedZ = resolvedZ;
         this.predictionInitialized = true;
         return new double[]{resolvedX, resolvedY, resolvedZ};
      } else {
         return new double[]{target.c(), targetY, target.Z()};
      }
   }

   private static float smoothStep(float edgeStart, float edgeEnd, float value) {
      float normalized = Math.max(0.0F, Math.min(1.0F, (value - edgeStart) / (edgeEnd - edgeStart)));
      return normalized * normalized * (3.0F - 2.0F * normalized);
   }

   @Nullable
   public EntityLivingBase getTarget() {
      return this.target;
   }

   public AimAssistTargetingSubModule(Mod parent, String name) {
      super(parent, name);
   }

   private float computePitchDrift(float playerPitch, float deltaTime, long nowNanos) {
      float strength = 0.65F + 0.35F * this.aimStrength;
      if (this.driftNextNanos == 0L || nowNanos >= this.driftNextNanos) {
         float randomAmplitude = lerp(strength, 0.05F, 0.15F);
         float pitchCorrection = playerPitch > 22.0F ? -0.18F : (playerPitch < -22.0F ? 0.18F : 0.0F);
         this.driftTarget = (this.random.nextFloat() * 2.0F - 1.0F) * randomAmplitude + pitchCorrection;
         this.driftTarget = Math.max(-0.5F, Math.min(0.5F, this.driftTarget));
         long intervalMillis = 300L + this.random.nextInt(420);
         this.driftNextNanos = nowNanos + intervalMillis * 1000000L;
      }

      float springStrength = lerp(strength, 2.0F, 5.0F);
      float damping = (float)Math.pow(0.04F, deltaTime);
      this.driftVelocity = this.driftVelocity + (this.driftTarget - this.driftPos) * springStrength * deltaTime;
      this.driftVelocity *= damping;
      this.driftPos = this.driftPos + this.driftVelocity * deltaTime;
      float positionLimit = lerp(strength, 0.45F, 0.8F);
      if (this.driftPos > positionLimit) {
         this.driftPos = positionLimit;
         this.driftVelocity = Math.min(0.0F, this.driftVelocity);
      } else if (this.driftPos < -positionLimit) {
         this.driftPos = -positionLimit;
         this.driftVelocity = Math.max(0.0F, this.driftVelocity);
      }

      this.driftNoise = this.driftNoise + (this.random.nextFloat() * 2.0F - 1.0F - this.driftNoise) * Math.max(0.02F, Math.min(0.18F, deltaTime * 5.0F));
      float elapsedSeconds = (float)(nowNanos - this.noiseStartNanos) / 1.0E9F;
      float noise = (float)(
         Math.sin(elapsedSeconds * 8.7 + 0.4) * 0.35F
            + Math.sin(elapsedSeconds * 13.1 + 2.2) * 0.22F
            + Math.sin(elapsedSeconds * 19.6 + 1.1) * 0.12F
            + this.driftNoise * 0.3F
      );
      float restoringForce = -this.driftPos * lerp(strength, 1.4F, 3.0F);
      float output = this.driftVelocity * 0.25F + restoringForce + noise * lerp(strength, 0.35F, 0.9F);
      float pitchLimitScale = 1.0F - 0.85F * smoothStep(72.0F, 88.0F, Math.abs(playerPitch));
      float outputLimit = lerp(strength, 0.25F, 0.55F) * pitchLimitScale;
      return Math.max(-outputLimit, Math.min(outputLimit, output));
   }

   private static boolean shouldSnap(boolean enabled, boolean currentlySnapped, float angleDifference) {
      if (!enabled) {
         return false;
      } else {
         return currentlySnapped ? angleDifference <= 7.0F : angleDifference <= 5.0F;
      }
   }

   @Override
   public void onDisable() {
      this.target = null;
      this.reset();
   }
}
