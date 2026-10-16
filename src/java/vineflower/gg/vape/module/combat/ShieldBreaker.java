package gg.vape.module.combat;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventKeyPress;
import gg.vape.event.impl.EventMouseButton;
import gg.vape.event.impl.EventPreTick;
import gg.vape.event.impl.SyntheticAttackRequestEvent;
import gg.vape.input.AttackKeyController;
import gg.vape.mapping.ItemMappingEntry;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.control.AttackCancellationAdapter;
import gg.vape.module.control.PhysicalAttackCancellationAdapter;
import gg.vape.module.control.SyntheticAttackCancellationAdapter;
import gg.vape.module.utility.AutoMace;
import gg.vape.rotation.RotationManager;
import gg.vape.unmap.ItemLimitData;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.LimitValue;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityOtherPlayerMP;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.InventoryPlayer;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.RayTraceResult_type;

public class ShieldBreaker extends Mod {
   private static final long MODULE_ID = 7954407336301342843L;
   private final NumberValue swapDelay;
   private final NumberValue swapBackDelay;
   private final BooleanValue doubleClick;
   private final BooleanValue limitToItems;
   private final LimitValue allowedItems;
   private boolean active;
   private boolean attackPending;
   private boolean releasePending;
   private boolean doubleClickPending;
   private int originalSlot = -1;
   private int axeSlot = -1;
   private int swapDelayTicks;
   private int swapBackDelayTicks;

   public ShieldBreaker() {
      super("ShieldBreaker", -16732037, Category.UTILITY, "Swaps to an axe when attacking a player with a raised shield");
      this.swapDelay = NumberValue.create(this, "Swap delay", "#", "ticks", 0.0, 0.0, 10.0, 1.0, "Delay between swapping to an axe and attacking");
      this.swapBackDelay = NumberValue.create(
         this, "Swap back delay", "#", "ticks", 1.0, 2.0, 10.0, 1.0, "Delay between attacking and swapping back to the original slot"
      );
      this.doubleClick = BooleanValue.create(this, "Double click", false, "Attacks again immediately after breaking the shield to knock the target back");
      this.limitToItems = BooleanValue.create(this, "Limit to items", false, "ShieldBreaker functions only while holding selected items");
      this.allowedItems = LimitValue.create(this, "shieldbreaker-alloweditems", "Allowed Items", LimitValue.ALLOW_LIST_COLOR, new ItemLimitData("swords"));
      this.limitToItems.addDependentValues(this.allowedItems);
      this.addValue(this.swapDelay, this.swapBackDelay, this.doubleClick, this.limitToItems, this.allowedItems);
   }

   @EventHandler(priority = EventPriority.HIGH)
   public void onMouseButton(EventMouseButton event) {
      if (this.isEnabled()) {
         if (event.isKeybinding(Minecraft.gameSettings().F()) && event.isDown() && !event.isCanceled()) {
            this.handleAttack(new PhysicalAttackCancellationAdapter(event));
         }
      }
   }

   @EventHandler(priority = EventPriority.HIGH)
   public void onKeyPress(EventKeyPress event) {
      if (this.isEnabled()) {
         if (event.isKeybinding(Minecraft.gameSettings().F()) && event.isDown() && !event.isCanceled()) {
            this.handleAttack(new PhysicalAttackCancellationAdapter(event));
         }
      }
   }

   @EventHandler(priority = EventPriority.HIGH)
   public void onSyntheticAttack(SyntheticAttackRequestEvent event) {
      if (this.isEnabled()) {
         if (!event.isCanceled() && event.getSource() != this && !(event.getSource() instanceof HitSwap) && !(event.getSource() instanceof AutoMace)) {
            this.handleAttack(new SyntheticAttackCancellationAdapter(event));
         }
      }
   }

   @EventHandler(priority = EventPriority.HIGH)
   public void onTick(EventPreTick event) {
      if (!this.isEnabled()) {
         if (this.releasePending) {
            AttackKeyController.releaseAttackKey();
            this.releasePending = false;
         }

         this.reset();
      } else {
         if (this.releasePending) {
            AttackKeyController.releaseAttackKey();
            this.releasePending = false;
         }

         EntityPlayerSP player = event.getThePlayer();
         if (player.isNull()) {
            this.reset();
         } else if (this.doubleClickPending) {
            this.doubleClickPending = false;
            AttackKeyController.releaseAttackKey();
            this.releasePending = AttackKeyController.requestSyntheticAttack(this);
            this.swapBackDelayTicks = 0;
         } else {
            if (this.attackPending) {
               if (!this.canAttack(player)) {
                  this.restoreSlot();
                  this.reset();
                  return;
               }

               if (this.swapDelayTicks++ >= this.swapDelay.getValue().intValue()) {
                  this.attack();
                  this.attackPending = false;
                  this.swapBackDelayTicks = 0;
               }
            } else if (this.active && this.swapBackDelayTicks++ >= this.swapBackDelay.getValue().intValue()) {
               this.restoreSlot();
               this.reset();
            }
         }
      }
   }

   private void handleAttack(AttackCancellationAdapter event) {
      if (!this.active && !this.attackPending) {
         if (!Minecraft.currentScreen().isNotNull()) {
            EntityPlayerSP player = Minecraft.thePlayer();
            if (!player.isNull() && this.canUseHeldItem(player) && !player.l$src$Z$1io4duf()) {
               EntityLivingBase target = this.getTarget();
               if (target != null && !target.isNull() && this.isShieldBlocking(target)) {
                  InventoryPlayer inventory = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
                  int currentSlot = inventory.v();
                  if (this.isAxe(inventory.c(currentSlot))) {
                     if (this.doubleClick.getEffectiveValue()) {
                        this.active = true;
                        this.doubleClickPending = true;
                        this.originalSlot = -1;
                        this.swapBackDelayTicks = 0;
                     }
                  } else {
                     int foundAxeSlot = this.findAxeSlot(inventory);
                     if (foundAxeSlot >= 0) {
                        event.setCancelled(true);
                        this.originalSlot = currentSlot;
                        this.axeSlot = foundAxeSlot;
                        inventory.g(this.axeSlot);
                        this.active = true;
                        this.attackPending = true;
                        this.swapDelayTicks = 0;
                        this.swapBackDelayTicks = 0;
                     }
                  }
               }
            }
         }
      }
   }

   private void attack() {
      AttackKeyController.releaseAttackKey();
      this.releasePending = AttackKeyController.requestSyntheticAttack(this);
      if (this.doubleClick.getEffectiveValue() && this.releasePending) {
         this.doubleClickPending = true;
      }
   }

   private boolean canAttack(EntityPlayerSP player) {
      if (!Minecraft.currentScreen().isNotNull() && !player.l$src$Z$1io4duf()) {
         InventoryPlayer inventory = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
         if (inventory.v() != this.axeSlot) {
            return false;
         } else {
            EntityLivingBase target = this.getTarget();
            return target != null && !target.isNull() && this.isShieldBlocking(target);
         }
      } else {
         return false;
      }
   }

   private void restoreSlot() {
      if (this.active && this.originalSlot >= 0) {
         EntityPlayerSP player = Minecraft.thePlayer();
         if (!player.isNull()) {
            InventoryPlayer inventory = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
            inventory.g(this.originalSlot);
         }
      }
   }

   private void reset() {
      this.active = false;
      this.attackPending = false;
      this.releasePending = false;
      this.doubleClickPending = false;
      this.originalSlot = -1;
      this.axeSlot = -1;
      this.swapDelayTicks = 0;
      this.swapBackDelayTicks = 0;
   }

   @Override
   public void onDisable() {
      if (this.releasePending) {
         AttackKeyController.releaseAttackKey();
         this.releasePending = false;
      }

      this.restoreSlot();
      this.reset();
   }

   private EntityLivingBase getTarget() {
      RayTraceResult rayTrace = RotationManager.INSTANCE.getExtendedReachRayTrace();
      if (rayTrace != null && !rayTrace.isNull() && rayTrace.getTypeOfHit().equals(RayTraceResult_type.entity())) {
         Entity entity = rayTrace.getEntity();
         if (entity == null || entity.isNull()) {
            return null;
         } else {
            return !entity.isInstance(MappedClasses.zm) ? null : new EntityLivingBase(entity.getObject());
         }
      } else {
         return null;
      }
   }

   public boolean isShieldBlocking(EntityLivingBase target) {
      if (target != null && !target.isNull()) {
         EntityOtherPlayerMP targetPlayer = new EntityOtherPlayerMP(target.getObject());
         return RotationUtil.isBlockingShield(targetPlayer);
      } else {
         return false;
      }
   }

   public static boolean isBlockedByShield(EntityPlayerSP player, Entity target) {
      if (player == null || player.isNull() || target == null || target.isNull()) {
         return false;
      } else if (!target.isInstance(MappedClasses.zm)) {
         return false;
      } else {
         EntityOtherPlayerMP targetPlayer = new EntityOtherPlayerMP(target.getObject());
         return RotationUtil.isBlockingShield(targetPlayer);
      }
   }

   public boolean canBreakShield(EntityPlayerSP player, Entity target) {
      if (!this.isEnabled() || player == null || player.isNull() || !this.canUseHeldItem(player)) {
         return false;
      } else if (!this.hasAxeInHotbar()) {
         return false;
      } else {
         return target != null && !target.isNull() && target.isInstance(MappedClasses.zm)
            ? this.isShieldBlocking(new EntityLivingBase(target.getObject()))
            : false;
      }
   }

   public boolean hasAxeInHotbar() {
      EntityPlayerSP player = Minecraft.thePlayer();
      if (player != null && !player.isNull()) {
         InventoryPlayer inventory = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();

         for (int slot = 0; slot < 9; slot++) {
            if (this.isAxe(inventory.c(slot))) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public boolean isSwapActive() {
      return this.active || this.attackPending;
   }

   private int findAxeSlot(InventoryPlayer inventory) {
      if (inventory != null && !inventory.isNull()) {
         int currentSlot = inventory.v();

         for (int slot = 0; slot < 9; slot++) {
            if (slot != currentSlot && this.isAxe(inventory.c(slot))) {
               return slot;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   private boolean isAxe(ItemStack stack) {
      if (stack == null || stack.isNull() || stack.getItem().isNull()) {
         return false;
      } else if (ItemStackScoreUtil.T(stack.getItem())) {
         return true;
      } else {
         ItemMappingEntry entry = Vape.INSTANCE.getItemStackResolver().resolve(stack);
         if (entry != null) {
            String key = entry.getResourceKey();
            if (key != null && key.contains("axe")) {
               return true;
            }

            String modern = entry.getModernId();
            if (modern != null && modern.contains("axe")) {
               return true;
            }
         }

         try {
            String className = stack.getItem().getObject().getClass().getName().toLowerCase();
            if (className.contains("axe")) {
               return true;
            }
         } catch (Throwable var6) {
         }

         String unlocalizedName = stack.getItem().A();
         if (unlocalizedName != null && unlocalizedName.toLowerCase().contains("axe")) {
            return true;
         } else {
            try {
               String str = stack.getItem().getObject().toString().toLowerCase();
               if (str.contains("axe")) {
                  return true;
               }
            } catch (Throwable var5) {
            }

            return false;
         }
      }
   }

   private boolean canUseHeldItem(EntityPlayerSP player) {
      return !this.limitToItems.getEffectiveValue() || this.allowedItems.isValid(player.getHeldItemHand(), false);
   }

   public boolean isActive() {
      return this.isEnabled() && (this.active || this.attackPending);
   }
}
