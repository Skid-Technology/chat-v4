package gg.vape.module.combat;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPostAttack;
import gg.vape.event.impl.EventPostRenderTick;
import gg.vape.event.impl.EventPostTick;
import gg.vape.event.impl.EventPreRenderTick;
import gg.vape.event.impl.EventPreTick;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.combat.autoclicker.AutoClickerEntityPositionState;
import gg.vape.unmap.ModeOption;
import gg.vape.utils.RayTraceUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.ModeValue;
import gg.vape.value.NumberValue;
import gg.vape.value.RandomValue;
import gg.vape.wrapper.impl.EffectRenderer;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityFX;
import gg.vape.wrapper.impl.EntityOtherPlayerMP;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Packet;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.WorldClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class Reach extends Mod {
   private int hitCooldown = 0;
   private final Random random;
   private final BooleanValue misplace;
   private final BooleanValue disableInWater;
   private final NumberValue chance;
   private final Map<Integer, AutoClickerEntityPositionState> entityStates;
   private final RandomValue range = RandomValue.createWithDescription(
      this, "Range", "#.##", "", 3.0, 3.0, 3.1, 4.0, 0.01, "The range in which your reach will be increased to."
   );
   private final BooleanValue verticalCheck;
   private final ModeValue chanceMode;
   private final BooleanValue disadvantage;
   private boolean reachActive = false;
   private final BooleanValue onlyWhileSprinting;
   private final ModeOption normalMode;
   private Entity lastTarget;
   private final ModeOption advancedMode;

   @EventHandler
   public void onPostAttack(EventPostAttack eventPostAttack) {
      if (!Packet.h() || this.advancedMode.isSelected()) {
         this.reachActive = false;
      }
   }

   @EventHandler
   public void onPreRenderTick(EventPreRenderTick eventPreRenderTick) {
      if (this.misplace.getEffectiveValue()) {
         this.applyMisplace(true);
      }
   }

   private boolean shouldExtendReach() {
      if (Packet.A()) {
         return this.advancedMode.isSelected();
      } else {
         return this.advancedMode.isSelected() ? this.reachActive : this.chance.getValue() > this.random.nextInt(100);
      }
   }

   private void misplaceParticles() {
      float directionOffset = this.disadvantage.getEffectiveValue() ? -90.0F : 90.0F;
      double misplaceDistance = this.range.getMinimumValue() - 3.0;
      EffectRenderer effectRenderer = Minecraft.z();
      if (!effectRenderer.isNull()) {
         try {
            List<EntityFX>[][] particleLayers = effectRenderer.getFxLayers();

            for (List<EntityFX>[] particleLayer : particleLayers) {
               List[] var10 = particleLayer;
               int var11 = particleLayer.length;

               for (int var12 = 0; var12 < var11; var12++) {
                  for (EntityFX particle : var10[var12]) {
                     EntityPlayerSP player = Minecraft.thePlayer();
                     if (!particle.isNull()
                        && (particle.isInstance(MappedClasses.Fy) || particle.isInstance(MappedClasses.Vc))
                        && !player.isNull()
                        && !(player.getDistanceToEntity(particle) >= this.range.getMaximumValue() + 2.0)
                        && !(particle.N() >= player.N() + 2.5)
                        && !RotationUtil.S(player, particle)) {
                        float angle = this.computeAngle(player.z(), player.h(), particle.z(), particle.h());
                        double offsetX = Math.cos(Math.toRadians(angle + directionOffset)) * misplaceDistance;
                        double offsetZ = Math.sin(Math.toRadians(angle + directionOffset)) * misplaceDistance;
                        particle.H(particle.z() - offsetX);
                        particle.l(particle.h() - offsetZ);
                        particle.C(particle.M() - offsetX);
                        particle.s(particle.m$src$D$fwnne5() - offsetZ);
                     }
                  }
               }
            }
         } catch (Exception var22) {
         }
      }
   }

   public BooleanValue getMisplaceSetting() {
      return this.misplace;
   }

   @EventHandler
   public void onPreTick(EventPreTick eventPreTick) {
      if (this.advancedMode.isSelected()) {
         this.updateTarget();
      }
   }

   private float computeAngle(double sourceX, double sourceZ, double targetX, double targetZ) {
      double deltaX = targetX - sourceX;
      double deltaZ = targetZ - sourceZ;
      float angle = (float)Math.toDegrees(-Math.atan(deltaX / deltaZ));
      if (deltaZ < 0.0 && deltaX < 0.0) {
         angle = (float)(90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
      } else if (deltaZ < 0.0 && deltaX > 0.0) {
         angle = (float)(-90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
      }

      return angle;
   }

   @EventHandler
   public void onPostRenderTick(EventPostRenderTick eventPostRenderTick) {
      if (this.misplace.getEffectiveValue()) {
         this.restorePositions(true);
      }
   }

   private void applyMisplace(boolean includeHistoricalPositions) {
      WorldClient world = Minecraft.theWorld();
      if (!world.isNull()) {
         for (Object underlyingEntity : world.z()) {
            Entity entity = new Entity(underlyingEntity);
            if (entity.isInstance(MappedClasses.lG) && !entity.isInstance(MappedClasses.z5)) {
               EntityOtherPlayerMP remotePlayer = new EntityOtherPlayerMP(underlyingEntity);
               int entityId = entity.S();
               AutoClickerEntityPositionState state = this.entityStates.computeIfAbsent(entityId, ignored -> {
                  AutoClickerEntityPositionState newState = new AutoClickerEntityPositionState();
                  newState.entityId = entityId;
                  return newState;
               });
               state.actualPosX = entity.z();
               state.actualPosZ = entity.h();
               state.actualLastTickPosX = entity.M();
               state.actualLastTickPosZ = entity.m$src$D$fwnne5();
               state.actualPrevPosX = remotePlayer.C$src$D$1i1kszo();
               state.actualPrevPosZ = remotePlayer.L$src$D$1i6iybx();
               state.actualServerPosX = remotePlayer.a$src$D$1ii2msi();
               state.actualServerPosZ = remotePlayer.G$src$D$1i3rzd4();
               if (includeHistoricalPositions) {
                  remotePlayer.H(state.misplacedPosX);
                  remotePlayer.l(state.misplacedPosZ);
                  remotePlayer.C(state.misplacedLastTickPosX);
                  remotePlayer.s(state.misplacedLastTickPosZ);
                  remotePlayer.S(state.misplacedPrevPosX);
                  remotePlayer.c(state.misplacedPrevPosZ);
                  remotePlayer.B(entity.z(), entity.N(), entity.h());
               } else {
                  remotePlayer.H(state.misplacedPosX);
                  remotePlayer.l(state.misplacedPosZ);
                  remotePlayer.B(entity.z(), entity.N(), entity.h());
                  remotePlayer.H(state.actualPosX);
                  remotePlayer.l(state.actualPosZ);
               }
            }
         }
      }
   }

   private boolean isReachAllowed() {
      if (!Packet.h()) {
         return this.isEnabled();
      } else if (this.isEnabled() && !this.misplace.getEffectiveValue()) {
         EntityPlayerSP player = Minecraft.thePlayer();
         return !this.disableInWater.getEffectiveValue() || !player.h$src$Z$ftwoya() && !player.Q$src$Z$fh9faz()
            ? !this.onlyWhileSprinting.getEffectiveValue() || player.B$src$Z$f90iek()
            : false;
      } else {
         return false;
      }
   }

   @EventHandler
   public void onPostTick(EventPostTick eventPostTick) {
      if (this.misplace.getEffectiveValue()) {
         this.updateMisplacedPositions();
         this.restorePositions(false);
         if (ForgeVersion.MC_1_8_9.B()) {
            this.misplaceParticles();
         }
      }
   }

   private void updateMisplacedPositions() {
      WorldClient world = Minecraft.theWorld();
      EntityPlayerSP player = Minecraft.thePlayer();
      if (!world.isNull()) {
         float directionOffset = this.disadvantage.getEffectiveValue() ? -90.0F : 90.0F;

         for (Object underlyingEntity : world.z()) {
            Entity entity = new Entity(underlyingEntity);
            if (entity.isInstance(MappedClasses.lG) && !entity.isInstance(MappedClasses.z5)) {
               EntityOtherPlayerMP remotePlayer = new EntityOtherPlayerMP(underlyingEntity);
               double misplaceDistance = this.range.getMinimumValue() - 3.0;
               double distanceToPlayer = Math.hypot(player.z() - entity.z(), player.h() - entity.h());
               float angle = this.computeAngle(player.z(), player.h(), entity.z(), entity.h());
               double remainingDistance = distanceToPlayer - misplaceDistance;
               if (remainingDistance < 0.5 && (misplaceDistance += remainingDistance - 0.5) < 0.0) {
                  misplaceDistance = 0.0;
               }

               double offsetX = Math.cos(Math.toRadians(angle + directionOffset)) * misplaceDistance;
               double offsetZ = Math.sin(Math.toRadians(angle + directionOffset)) * misplaceDistance;
               int entityId = entity.S();
               AutoClickerEntityPositionState state = this.entityStates.get(entityId);
               boolean hadPreviousState = state != null;
               if (!hadPreviousState) {
                  state = new AutoClickerEntityPositionState();
                  state.entityId = entityId;
               } else {
                  state = this.entityStates.get(entityId);
               }

               this.entityStates.put(entityId, state);
               state.actualPosX = entity.z();
               state.actualPosZ = entity.h();
               state.actualLastTickPosX = entity.M();
               state.actualLastTickPosZ = entity.m$src$D$fwnne5();
               state.actualPrevPosX = remotePlayer.C$src$D$1i1kszo();
               state.actualPrevPosZ = remotePlayer.L$src$D$1i6iybx();
               state.actualServerPosX = remotePlayer.a$src$D$1ii2msi();
               state.actualServerPosZ = remotePlayer.G$src$D$1i3rzd4();
               state.misplacedPosX = state.actualPosX - offsetX;
               state.misplacedPosZ = state.actualPosZ - offsetZ;
               state.misplacedLastTickPosX = state.actualLastTickPosX - offsetX;
               state.misplacedLastTickPosZ = state.actualLastTickPosZ - offsetZ;
               state.misplacedPrevPosX = state.actualPrevPosX - offsetX;
               state.misplacedPrevPosZ = state.actualPrevPosZ - offsetZ;
               if (hadPreviousState) {
                  state.misplacedLastTickPosX = state.previousMisplacedLastTickPosX;
                  state.misplacedLastTickPosZ = state.previousMisplacedLastTickPosZ;
               }
            }
         }
      }
   }

   private void updateTarget() {
      Entity tracedEntity = null;
      RayTraceResult rayTrace = RayTraceUtil.F(this.range.getMaximumValue(), 0.0F, true);
      if (rayTrace != null && rayTrace.isNotNull() && rayTrace.getEntity().isNotNull()) {
         tracedEntity = rayTrace.getEntity();
      }

      if (Packet.h()) {
         if (this.hitCooldown > 0) {
            this.hitCooldown--;
         }

         if (tracedEntity != null && !tracedEntity.isNull() && (this.lastTarget == null || tracedEntity.equals(this.lastTarget))) {
            if (this.isReachAllowed() && this.hitCooldown == 0) {
               this.reachActive = this.chance.getValue() > this.random.nextInt(100);
               if (this.reachActive) {
                  this.hitCooldown = 10;
               }
            }

            this.lastTarget = tracedEntity;
         } else {
            this.lastTarget = null;
         }
      } else {
         this.hitCooldown--;
         this.lastTarget = tracedEntity;
      }
   }

   @Override
   public String getDetailedSuffix() {
      return this.range.getDisplayValue();
   }

   @Override
   public void onDisable() {
      this.entityStates.clear();
   }

   public double getReachDistance() {
      return this.isReachAllowed() && this.shouldExtendReach() ? this.range.getRandomValue() : 3.0;
   }

   @EventHandler
   public void onPreTickMisplace(EventPreTick eventPreTick) {
      if (this.misplace.getEffectiveValue()) {
         this.updateMisplacedPositions();
         this.applyMisplace(false);
      }
   }

   public Reach() {
      super("Reach", -16711936, Category.COMBAT, "Extends attack reach");
      this.chance = NumberValue.createWithDescription(this, "Chance", "#", "%", 0.0, 50.0, 100.0, "The chance of reach taking affect when hitting an opponent");
      this.advancedMode = new ModeOption("Advanced");
      this.normalMode = new ModeOption("Normal");
      this.chanceMode = ModeValue.create(this, "Chance mode", this.advancedMode, this.advancedMode, this.normalMode);
      this.misplace = BooleanValue.create(
         this, "Misplace", false, "Pulls players towards you rather than giving you extra reach distance. Uses the minimum slider value."
      );
      this.disadvantage = BooleanValue.create(this, "Disadvantage", false, "Moves misplaced players in opposite direction. Useful for framing other players");
      this.verticalCheck = BooleanValue.create(
         this, "Vertical check", false, "Prevents hitting players which are y0.2 above or below you\nfor more legitimate use"
      );
      this.onlyWhileSprinting = BooleanValue.create(this, "Only while sprinting", false, "Only give extra reach while sprinting");
      this.disableInWater = BooleanValue.create(this, "Disable in water", false, "Won't give any extra reach while standing in water");
      this.entityStates = new HashMap<>();
      this.random = new Random();
      this.misplace.addDependentValues(this.disadvantage);
      this.range.setAbsoluteClampLimit(100.0);
      this.addValue(
         this.range, this.chance, this.chanceMode, this.misplace, this.disadvantage, this.verticalCheck, this.onlyWhileSprinting, this.disableInWater
      );
   }

   private void restorePositions(boolean restoreHistoricalPositions) {
      WorldClient world = Minecraft.theWorld();
      if (!world.isNull()) {
         for (Entry<Integer, AutoClickerEntityPositionState> entry : this.entityStates.entrySet()) {
            AutoClickerEntityPositionState state = entry.getValue();
            Object underlyingEntity = world.V(state.entityId);
            Entity entity = new Entity(underlyingEntity);
            if (entity.isNotNull() && entity.isInstance(MappedClasses.lG)) {
               EntityOtherPlayerMP remotePlayer = new EntityOtherPlayerMP(underlyingEntity);
               if (restoreHistoricalPositions) {
                  state.previousMisplacedLastTickPosX = state.misplacedPosX;
                  state.previousMisplacedLastTickPosZ = state.misplacedPosZ;
                  remotePlayer.H(state.actualPosX);
                  remotePlayer.l(state.actualPosZ);
                  remotePlayer.C(state.actualLastTickPosX);
                  remotePlayer.s(state.actualLastTickPosZ);
                  remotePlayer.S(state.actualPrevPosX);
                  remotePlayer.c(state.actualPrevPosZ);
               }

               remotePlayer.B(remotePlayer.z(), remotePlayer.N(), remotePlayer.h());
            }
         }
      }
   }
}
