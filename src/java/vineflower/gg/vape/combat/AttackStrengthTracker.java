package gg.vape.combat;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.impl.EventEntityJoinWorld;
import gg.vape.event.impl.EventLivingUpdate;
import gg.vape.event.impl.EventPostTick;
import gg.vape.event.impl.EventPreAttack;
import gg.vape.mapping.MappedClasses;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.wrapper.impl.AttributeModifier;
import gg.vape.wrapper.impl.EnchantmentHelper;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPotion;
import gg.vape.wrapper.impl.EnumCreatureAttribute;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ItemAttributeModifiers;
import gg.vape.wrapper.impl.ItemSplashPotion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PotionEffect;
import gg.vape.wrapper.impl.WorldClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class AttackStrengthTracker implements EventListener {
   private final HashMap<Integer, TrackedPlayerAttackState> playerStates = new HashMap<>();
   private Object trackedWorldHandle;
   public static final AttackStrengthTracker INSTANCE = new AttackStrengthTracker();
   private static int[] controlFlowMarker;
   private final List<EntityPotion> healingPotions = new ArrayList<>();

   static float calculateAttackDamage(ItemStack itemStack, EntityPlayer player, boolean targetWasBlocking, boolean criticalHit) {
      float baseDamage = 1.0F;
      if (itemStack.isNotNull()) {
         ItemAttributeModifiers modifiers = itemStack.o();
         if (modifiers.size() > 0) {
            int attackDamageModifierIndex = ForgeVersion.MC_1_12_2.L() ? 1 : 0;
            AttributeModifier attackDamageModifier = new AttributeModifier(modifiers.values().toArray()[attackDamageModifierIndex]);
            baseDamage += (float)attackDamageModifier.getAmount();
         }
      }

      float enchantmentDamage = EnchantmentHelper.C(itemStack, EnumCreatureAttribute.undefined());
      if (!(baseDamage > 0.0F) && !(enchantmentDamage > 0.0F)) {
         return 0.0F;
      } else {
         if (criticalHit && baseDamage > 0.0F) {
            baseDamage *= 1.5F;
         }

         return applyDamageReductions(player, targetWasBlocking, baseDamage + enchantmentDamage);
      }
   }

   @EventHandler
   public void onUpdate(EventLivingUpdate eventLivingUpdate) {
      if (eventLivingUpdate.getEntity().isInstance(MappedClasses.Yl) && Minecraft.thePlayer().getDistanceToEntity(eventLivingUpdate.getEntity()) < 6.0F) {
         TrackedPlayerAttackState state = this.playerStates.get(eventLivingUpdate.getEntity().S());
         if (state != null) {
            state.markLivingUpdate();
         }
      }
   }

   @EventHandler
   public void onEntityJoinWorld(EventEntityJoinWorld event) {
      if (event.getEntity().isInstance(MappedClasses.Yl)) {
         TrackedPlayerAttackState state = this.playerStates
            .getOrDefault(event.getEntity().S(), new TrackedPlayerAttackState(new EntityPlayer(event.getEntity())));
         if (state != null) {
            state.updatePlayer(new EntityPlayer(event.getEntity().getObject()));
            if (state.getInactivityTimer().hasTimeElapsed(10000L)) {
               state.resetPrediction();
            }
         }
      } else if (event.getEntity().isInstance(MappedClasses.Zf)) {
         if (!Minecraft.thePlayer().isNull()) {
            EntityPotion entityPotion = new EntityPotion(event.getEntity());
            if (!entityPotion.getPotion().isNull()) {
               boolean isHealingPotion = ItemStackScoreUtil.i(entityPotion.getPotion());
               if (isHealingPotion) {
                  this.healingPotions.add(new EntityPotion(event.getEntity().getObject()));
               }
            }
         }
      }
   }

   @EventHandler
   public void onPreAttack(EventPreAttack event) {
      if (event.getTarget().isInstance(MappedClasses.Yl)) {
         TrackedPlayerAttackState state = this.playerStates.get(event.getTarget().S());
         if (state != null) {
            EntityPlayer target = new EntityPlayer(event.getTarget().getObject());
            state.recordAttack(target.o$src$Z$1iprrmi(), Minecraft.thePlayer().B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt());
         }
      }
   }

   static float applyDamageReductions(EntityPlayer player, boolean blocking, float damage) {
      if (blocking && damage > 0.0F) {
         damage = (1.0F + damage) * 0.5F;
      }

      damage = RotationUtil.m(player, damage);
      return RotationUtil.j(player, damage);
   }

   private static double squaredDistance(Entity entity, double x, double y, double z) {
      double deltaX = entity.z() - x;
      double deltaY = entity.N() - y;
      double deltaZ = entity.h() - z;
      return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
   }

   private static float calculateInstantHealthAmount(int amplifier, double intensity) {
      return (int)(intensity * (4 << amplifier) + 0.5);
   }

   public boolean isHealthPredictionEnabled() {
      return Vape.INSTANCE.getClientSettings().healthPrediction.getEffectiveValue();
   }

   public boolean shouldEstimateFoodHealing() {
      return Vape.INSTANCE.getClientSettings().estimateFoodHealing.getEffectiveValue();
   }

   private static float calculateInstantHealthFromPotion(EntityPlayer player, ItemStack potionStack, double x, double y, double z, boolean directHit) {
      double distanceSquared = squaredDistance(player, x, y, z);
      int amplifier = 0;
      if (potionStack.getItem().isInstance(MappedClasses.Di)) {
         ItemSplashPotion splashPotion = new ItemSplashPotion(potionStack.getItem().getObject());
         if (splashPotion.getRawPotionEffects(potionStack) != null && ItemSplashPotion.isSplashPotion(potionStack)) {
            for (PotionEffect potionEffect : splashPotion.getPotionEffects(potionStack)) {
               if (potionEffect.C() == 6) {
                  amplifier = potionEffect.L();
               }
            }
         }
      }

      if (distanceSquared < 16.0) {
         double intensity = 1.0 - Math.sqrt(distanceSquared) / 4.0;
         if (directHit) {
            intensity = 1.0;
         }

         return calculateInstantHealthAmount(amplifier, intensity);
      } else {
         return 0.0F;
      }
   }

   @EventHandler
   public void onPostTick(EventPostTick event) {
      WorldClient world = Minecraft.theWorld();
      if (!world.isNull()) {
         if (this.trackedWorldHandle == null) {
            this.trackedWorldHandle = world.getObject();
         }

         if (!world.getObject().equals(this.trackedWorldHandle)) {
            this.healingPotions.clear();
            this.playerStates.clear();
            this.trackedWorldHandle = world.getObject();
         }

         for (Object entityHandle : world.z()) {
            if (MappedClasses.Yl.isAssignableFrom(entityHandle.getClass()) && !MappedClasses.z5.isAssignableFrom(entityHandle.getClass())) {
               EntityPlayer player = new EntityPlayer(entityHandle);
               if (this.playerStates.containsKey(player.S())) {
                  this.playerStates.get(player.S()).update();
               } else {
                  this.playerStates.put(player.S(), new TrackedPlayerAttackState(player));
               }
            }
         }

         ArrayList<EntityPotion> expiredPotions = new ArrayList<>();

         for (EntityPotion potion : this.healingPotions) {
            if (!world.z().contains(potion.getObject())) {
               double potionX = potion.z();
               double potionY = potion.N();
               double potionZ = potion.h();

               for (Entry<Integer, TrackedPlayerAttackState> entry : this.playerStates.entrySet()) {
                  Entity entity = world.V(entry.getKey());
                  if (entity.isNotNull() && entity.isInstance(MappedClasses.Yl)) {
                     boolean directHit = potion.N() > entity.N() + 0.5 && entity.getDistanceToEntity(potion) < 2.2F && potion.l() >= 5;
                     float healing = calculateInstantHealthFromPotion(
                        new EntityPlayer(entity.getObject()), potion.getPotion(), potionX, potionY, potionZ, directHit
                     );
                     entry.getValue().addEstimatedHealth(healing);
                  }
               }

               expiredPotions.add(potion);
            }
         }

         if (!expiredPotions.isEmpty()) {
            this.healingPotions.removeAll(expiredPotions);
         }
      }
   }

   public float getEstimatedHealth(EntityPlayer player) {
      if (!this.isHealthPredictionEnabled()) {
         return player.w$src$F$15l9epb();
      } else {
         TrackedPlayerAttackState state = this.playerStates.get(player.S());
         return state != null ? state.getEstimatedHealth() : player.w$src$F$15l9epb();
      }
   }

   public static void setControlFlowMarker(int[] marker) {
      controlFlowMarker = marker;
   }

   public static int[] getControlFlowMarker() {
      return controlFlowMarker;
   }

   public boolean shouldEstimateFallDamage() {
      return Vape.INSTANCE.getClientSettings().estimateFallDamage.getEffectiveValue();
   }

   static {
      setControlFlowMarker(new int[3]);
   }
}
