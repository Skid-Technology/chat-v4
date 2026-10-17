package gg.vape.friend;

import gg.vape.friend.activity.ActivityHealthData;
import gg.vape.friend.activity.ActivityItemStack;
import gg.vape.friend.activity.ActivityItemStackPayload;
import gg.vape.friend.activity.ActivityPositionData;
import gg.vape.friend.activity.ActivitySnapshotPayload;
import gg.vape.friend.activity.ActivitySnapshotPayloadBuilder;
import gg.vape.friend.activity.ActivityTargetData;
import gg.vape.mapping.MappedClasses;
import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.utils.EnchantmentUtil;
import gg.vape.wrapper.impl.Enchantment;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PotionEffect;
import gg.vape.wrapper.impl.PotionEntry;
import gg.vape.wrapper.impl.PotionRegistry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.annotations.Nullable;

public class OnlineFriendActivityState {
   private float absorptionAmount;
   private final ActivityItemStack[] inventory;
   private double positionY;
   private int recentAttackCount;
   private boolean reservedFlag;
   HashMap<Integer, Integer> recentTargetAges;
   private int afkTicks;
   private int clicksPerSecond;
   private int buildingTicks;
   private int stationaryTicks;
   private final Map<PotionEntry, Integer> potionAmplifiers;
   private double positionX;
   @Nullable
   private UUID targetUuid;
   private boolean dataAvailable;
   private double positionZ;
   @Nullable
   private String targetName;
   private AtomicBoolean reservedAtomicFlag;
   private float health;
   private HashSet<OnlineFriendActivityType> activityTypes = new HashSet<>(Arrays.asList());
   private DoubleAnimation animation;
   private final ActivityItemStack[] armor;
   private int heldItemSlot;
   private int swingProgressTicks;
   private float maxHealth;
   private final OnlineFriend friend;
   private int hurtTime;

   public int getHurtTime() {
      return this.hurtTime;
   }

   public void setReservedFlag(boolean ignored) {
   }

   public int getSwingProgressTicks() {
      return this.swingProgressTicks;
   }

   @Nullable
   public static ActivityItemStack fromItemStack(ItemStack itemStack) {
      ActivityItemStackPayload activityItemStackPayload = createItemStackPayload(itemStack);
      return activityItemStackPayload == null ? null : ActivityItemStack.fromPayload(activityItemStackPayload);
   }

   public int getRecentAttackCount() {
      return this.recentAttackCount;
   }

   public float getHealth(EntityPlayer entityPlayer) {
      return this.health;
   }

   @Nullable
   public String getTargetName() {
      return this.targetName;
   }

   public int getSwingProgressTicks(EntityPlayer entityPlayer) {
      return entityPlayer == null ? this.swingProgressTicks : entityPlayer.i();
   }

   public void removeActivity(OnlineFriendActivityType onlineFriendActivityType) {
      this.activityTypes.remove(onlineFriendActivityType);
   }

   public void updateLocalActivity() {
      EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
      if (!entityPlayerSP.isNull()) {
         int afkThresholdTicks = 600;
         if (entityPlayerSP.V() != entityPlayerSP.a$src$F$1txy325() || entityPlayerSP.J() != entityPlayerSP.g()) {
            this.afkTicks = 0;
         }

         if (this.afkTicks > afkThresholdTicks) {
            this.addActivity(OnlineFriendActivityType.AFK);
         } else {
            this.removeActivity(OnlineFriendActivityType.AFK);
         }

         if (!this.recentTargetAges.isEmpty()) {
            this.setRecentAttackCount(this.recentTargetAges.size());
            this.addActivity(OnlineFriendActivityType.COMBAT);
            this.ageRecentTargets();
         } else {
            this.removeActivity(OnlineFriendActivityType.COMBAT);
         }

         int buildingThresholdTicks = 160;
         ActivityItemStack activityItemStack = this.getInventory()[this.getHeldItemSlot()];
         if (activityItemStack != null) {
            Item item = Item.L(String.valueOf(activityItemStack.getItemId()));
            if (item.isInstance(MappedClasses.Vw) && Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362().isKeyDown()) {
               this.buildingTicks = 0;
            }

            if (item.isInstance(MappedClasses.Vw) && this.buildingTicks < buildingThresholdTicks) {
               this.addActivity(OnlineFriendActivityType.BUILDING);
            } else {
               this.removeActivity(OnlineFriendActivityType.BUILDING);
            }
         } else {
            this.removeActivity(OnlineFriendActivityType.BUILDING);
         }

         if (entityPlayerSP.t() != 0.0 || entityPlayerSP.q() > 0.0 || entityPlayerSP.T() != 0.0) {
            this.stationaryTicks = 0;
         }

         if (this.stationaryTicks < 50) {
            this.addActivity(OnlineFriendActivityType.MOVING);
         } else {
            this.removeActivity(OnlineFriendActivityType.MOVING);
         }

         this.afkTicks++;
         this.buildingTicks++;
         this.stationaryTicks++;
      }
   }

   public boolean hasData() {
      return this.dataAvailable;
   }

   public ActivityItemStack[] getInventory() {
      return this.inventory;
   }

   public static ActivitySnapshotPayload createSnapshot(EntityPlayer entityPlayer) {
      ActivitySnapshotPayloadBuilder activitySnapshotPayloadBuilder = new ActivitySnapshotPayloadBuilder();
      activitySnapshotPayloadBuilder.withPosition(new ActivityPositionData(entityPlayer.z(), entityPlayer.N(), entityPlayer.h()));
      LinkedHashMap<Short, Integer> linkedHashMap = new LinkedHashMap<>();

      for (Object potionEffectHandle : entityPlayer.B$src$Ljava_util_Collection_$1uxz2f9()) {
         PotionEffect potionEffect = new PotionEffect(potionEffectHandle);
         PotionEntry potionEntry;
         if (!potionEffect.isNull() && (potionEntry = PotionRegistry.R(potionEffect)) != null) {
            linkedHashMap.put(potionEntry.getLegacyId(), potionEffect.k());
         }
      }

      activitySnapshotPayloadBuilder.withHealth(
         new ActivityHealthData(entityPlayer.w$src$F$15l9epb(), entityPlayer.I$src$F$14vyvep(), entityPlayer.p(), entityPlayer.c$src$I$15a9iwo(), linkedHashMap)
      );
      OnlineFriendActivityListener onlineFriendActivityListener = OnlineFriendActivityListener.INSTANCE;
      EntityPlayer target = onlineFriendActivityListener.getCombatTarget();
      if (target != null) {
         activitySnapshotPayloadBuilder.withTarget(new ActivityTargetData(target.X$src$Ljava_util_UUID_$1o5dyg6(), target.getName()));
      } else {
         activitySnapshotPayloadBuilder.withTarget(null);
      }

      return activitySnapshotPayloadBuilder.build();
   }

   public void setDataAvailable(boolean dataAvailable) {
      this.dataAvailable = dataAvailable;
   }

   public void setBuildingTicks(int buildingTicks) {
      this.buildingTicks = buildingTicks;
   }

   public void setHurtTime(int hurtTime) {
      this.hurtTime = hurtTime;
   }

   public void recordAttack(int entityId) {
      this.recentTargetAges.put(entityId, 0);
   }

   public void setSwingProgressTicks(int swingProgressTicks) {
      this.swingProgressTicks = swingProgressTicks;
   }

   public float getMaxHealth(EntityPlayer entityPlayer) {
      return entityPlayer == null ? this.maxHealth : entityPlayer.I$src$F$14vyvep();
   }

   public void addActivity(OnlineFriendActivityType onlineFriendActivityType) {
      this.activityTypes.add(onlineFriendActivityType);
   }

   public float getAbsorptionAmount(EntityPlayer entityPlayer) {
      return entityPlayer == null ? this.absorptionAmount : entityPlayer.p();
   }

   public void setAfkTicks(int afkTicks) {
      this.afkTicks = afkTicks;
   }

   public int getHeldItemSlot() {
      return this.heldItemSlot;
   }

   public void clearActivity() {
      this.activityTypes.clear();
      this.recentTargetAges.clear();
   }

   public double getPositionY(EntityPlayer entityPlayer) {
      return entityPlayer == null ? this.positionY : entityPlayer.A();
   }

   public int getHurtTime(EntityPlayer entityPlayer) {
      return entityPlayer == null ? this.hurtTime : entityPlayer.c$src$I$15a9iwo();
   }

   @Nullable
   public UUID getTargetUuid() {
      return this.targetUuid;
   }

   public OnlineFriendActivityState(OnlineFriend onlineFriend) {
      this.afkTicks = 0;
      this.buildingTicks = 1000000;
      this.stationaryTicks = 100000;
      this.recentAttackCount = 0;
      this.reservedAtomicFlag = new AtomicBoolean(false);
      this.recentTargetAges = new HashMap<>();
      this.health = 15.0F;
      this.maxHealth = 20.0F;
      this.absorptionAmount = 2.0F;
      this.swingProgressTicks = 0;
      this.animation = new DoubleAnimation(0.05, 0.0, 1.0);
      this.clicksPerSecond = 0;
      this.positionX = 0.0;
      this.positionY = 64.0;
      this.positionZ = 0.0;
      this.armor = new ActivityItemStack[4];
      this.inventory = new ActivityItemStack[36];
      this.potionAmplifiers = new LinkedHashMap<>();
      this.dataAvailable = false;
      this.friend = onlineFriend;
   }

   public void setClicksPerSecond(int clicksPerSecond) {
      this.clicksPerSecond = clicksPerSecond;
   }

   public void setHeldItemSlot(int heldItemSlot) {
      this.heldItemSlot = heldItemSlot;
   }

   @Nullable
   public static ActivityItemStackPayload createItemStackPayload(ItemStack itemStack) {
      if (!itemStack.isNull() && itemStack.getItem().P() != 0) {
         Map<Enchantment, Short> map = EnchantmentUtil.A(itemStack);
         HashMap<Short, Short> hashMap = new HashMap<>();

         for (Entry<Enchantment, Short> entry : map.entrySet()) {
            Short enchantmentId = EnchantmentUtil.c(entry.getKey());
            if (enchantmentId != null) {
               hashMap.put(enchantmentId, entry.getValue());
            }
         }

         return new ActivityItemStackPayload(itemStack.getItem().P(), itemStack.t(), itemStack.L(), hashMap);
      } else {
         return null;
      }
   }

   public void ageRecentTargets() {
      int retentionTicks = 150;
      HashMap<Integer, Integer> hashMap = new HashMap<>();
      hashMap.putAll(this.recentTargetAges);

      for (Integer entityId : hashMap.keySet()) {
         if (this.recentTargetAges.get(entityId) > retentionTicks) {
            this.recentTargetAges.remove(entityId);
         } else {
            this.recentTargetAges.put(entityId, this.recentTargetAges.get(entityId) + 1);
         }
      }
   }

   public ActivityItemStack[] getArmor() {
      return this.armor;
   }

   public boolean hasTarget() {
      return this.getTargetUuid() != null && this.getTargetName() != null;
   }

   public int getClicksPerSecond() {
      return this.clicksPerSecond;
   }

   public int getBuildingTicks() {
      return this.buildingTicks;
   }

   public OnlineFriendActivityType getPrimaryActivity() {
      if (this.activityTypes.contains(OnlineFriendActivityType.DEAD)) {
         return OnlineFriendActivityType.DEAD;
      } else if (this.activityTypes.contains(OnlineFriendActivityType.AFK)) {
         return OnlineFriendActivityType.AFK;
      } else if (this.activityTypes.contains(OnlineFriendActivityType.COMBAT)) {
         return OnlineFriendActivityType.COMBAT;
      } else if (this.activityTypes.contains(OnlineFriendActivityType.BUILDING)) {
         return OnlineFriendActivityType.BUILDING;
      } else {
         return this.activityTypes.contains(OnlineFriendActivityType.MOVING) ? OnlineFriendActivityType.MOVING : OnlineFriendActivityType.NONE;
      }
   }

   public DoubleAnimation getAnimation() {
      return this.animation;
   }

   public double getPositionX(EntityPlayer entityPlayer) {
      return entityPlayer == null ? this.positionX : entityPlayer.c();
   }

   public void applySnapshot(ActivitySnapshotPayload activitySnapshotPayload) {
      this.dataAvailable = true;
      ActivityPositionData activityPositionData = activitySnapshotPayload.getPosition();
      if (activityPositionData != null) {
         this.positionX = activityPositionData.getX();
         this.positionY = activityPositionData.getY();
         this.positionZ = activityPositionData.getZ();
      }

      ActivityHealthData activityHealthData;
      if ((activityHealthData = activitySnapshotPayload.getHealth()) != null) {
         this.health = activityHealthData.getHealth();
         this.maxHealth = activityHealthData.getMaxHealth();
         this.absorptionAmount = activityHealthData.getAbsorptionAmount();
         this.hurtTime = activityHealthData.getHurtTime();
         this.potionAmplifiers.clear();

         for (Entry<Short, Integer> entry : activityHealthData.getPotionAmplifiers().entrySet()) {
            PotionEntry potionEntry = PotionRegistry.A(entry.getKey());
            if (potionEntry != null) {
               this.potionAmplifiers.put(potionEntry, entry.getValue());
            }
         }
      }

      ActivityTargetData targetData = activitySnapshotPayload.getTarget();
      if (targetData != null) {
         this.targetUuid = targetData.getUuid();
         this.targetName = targetData.getName();
      } else {
         this.targetUuid = null;
         this.targetName = null;
      }
   }

   public void setRecentAttackCount(int recentAttackCount) {
      this.recentAttackCount = recentAttackCount;
   }

   public OnlineFriend getFriend() {
      return this.friend;
   }

   public boolean isReservedFlagSet() {
      return this.reservedFlag;
   }

   public double getPositionZ(EntityPlayer entityPlayer) {
      return entityPlayer == null ? this.positionZ : entityPlayer.Z();
   }
}
