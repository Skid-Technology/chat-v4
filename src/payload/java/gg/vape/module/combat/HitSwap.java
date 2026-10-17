package gg.vape.module.combat;

import gg.vape.event.EventHandler;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventKeyPress;
import gg.vape.event.impl.EventMouseButton;
import gg.vape.event.impl.EventPreTick;
import gg.vape.event.impl.SyntheticAttackRequestEvent;
import gg.vape.input.AttackKeyController;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.control.AttackCancellationAdapter;
import gg.vape.module.control.PhysicalAttackCancellationAdapter;
import gg.vape.module.control.SyntheticAttackCancellationAdapter;
import gg.vape.rotation.RotationManager;
import gg.vape.unmap.ItemLimitData;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.LimitValue;
import gg.vape.wrapper.impl.EnchantmentHelper;
import gg.vape.wrapper.impl.EntityOtherPlayerMP;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EnumHand;
import gg.vape.wrapper.impl.InventoryPlayer;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import java.util.Arrays;

public class HitSwap extends Mod {
   public final LimitValue allowedItems;
   private boolean swingReleasePending;
   private int originalSlot;
   public final BooleanValue breachMaces;
   private static final long MODULE_ID = -7666507152973844357L;
   private int tickCounter;
   public final BooleanValue limitToItems = BooleanValue.create(this, "Limit to items", false);
   public final BooleanValue smashOnly;
   private boolean stunSlamSecondPhase;
   private boolean swapActive;
   private boolean breachSwapPending;
   public final BooleanValue stunSlam;
   private boolean stunSlamActive;
   public final BooleanValue maces = BooleanValue.create(this, "Maces", true);
   public final BooleanValue densityMaces;
   public final BooleanValue axes;
   public final BooleanValue swords;
   private boolean swapAttackPending;

   private int findAxeSlot() {
      InventoryPlayer inventory = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
      int selectedSlot = inventory.v();

      for (int slot = 0; slot < 9; slot++) {
         ItemStack stack;
         if (slot != selectedSlot && !(stack = inventory.c(slot)).isNull() && !stack.getItem().isNull() && stack.getItem().isInstance(MappedClasses.YP)) {
            return slot;
         }
      }

      return -1;
   }

   private int findMaceSlot() {
      InventoryPlayer inventory = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();

      for (int slot = 0; slot < 9; slot++) {
         ItemStack stack = inventory.c(slot);
         if (this.isValidMace(stack, this.shouldFilterByEnchant())) {
            return slot;
         }
      }

      return -1;
   }

   public boolean hasAlternateAxe() {
      return this.axes.getEffectiveValue() && this.findAxeSlot() >= 0;
   }

   @Override
   public void onDisable() {
      this.swapAttackPending = false;
      this.stunSlamSecondPhase = false;
      if (this.swingReleasePending) {
         AttackKeyController.releaseAttackKey();
      }

      this.swingReleasePending = false;
      this.swapActive = false;
      this.originalSlot = -1;
      this.tickCounter = 0;
      this.breachSwapPending = false;
      this.stunSlamActive = false;
   }

   public HitSwap() {
      super("HitSwap", -16732037, Category.UTILITY, "Swaps into another weapon on attack, copying its attributes\nAKA BreachSwap, ZeroTick");
      this.smashOnly = BooleanValue.create(this, "Smash only", true, "Only swap to mace if will smash");
      this.breachMaces = BooleanValue.create(this, "Breach maces", false, "Will use Maces with Breach enchantment");
      this.densityMaces = BooleanValue.create(this, "Density maces", true, "Will use Maces with Density enchantment");
      this.stunSlam = BooleanValue.create(
         this,
         "Stun slam",
         false,
         "When holding an axe and attacking a shielded player:\nHits with axe first (breaks shield), then swaps to mace for a follow-up slam"
      );
      this.axes = BooleanValue.create(this, "Axes", true);
      this.swords = BooleanValue.create(this, "Swords", false);
      this.allowedItems = LimitValue.create(this, "bs-alloweditems", "Allowed Items", LimitValue.ALLOW_LIST_COLOR, Arrays.asList(new ItemLimitData("swords")));
      this.originalSlot = -1;
      this.swapAttackPending = false;
      this.swingReleasePending = false;
      this.breachSwapPending = false;
      this.stunSlamActive = false;
      this.stunSlamSecondPhase = false;
      this.addValue(
         this.maces, this.smashOnly, this.breachMaces, this.densityMaces, this.stunSlam, this.axes, this.swords, this.limitToItems, this.allowedItems
      );
      this.limitToItems.addDependentValues(this.allowedItems);
      this.maces.addDependentValues(this.smashOnly, this.breachMaces, this.densityMaces, this.stunSlam);
   }

   private boolean canHandleAttack() {
      return Minecraft.currentScreen().isNotNull()
         ? false
         : !this.limitToItems.getEffectiveValue() || this.allowedItems.isValid(Minecraft.thePlayer().getHeldItemHand(), false);
   }

   private boolean hasMaceInHotbar() {
      InventoryPlayer inventory = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();

      for (int slot = 0; slot < 9; slot++) {
         ItemStack stack = inventory.c(slot);
         if (this.isValidMace(stack, true)) {
            return true;
         }
      }

      return false;
   }

   private boolean isValidMace(ItemStack itemStack, boolean requireConfiguredEnchantment) {
      if (this.smashOnly.getEffectiveValue() && !RotationUtil.u(Minecraft.thePlayer())) {
         return false;
      } else if (itemStack.isNull()) {
         return false;
      } else if (itemStack.getItem().isNull()) {
         return false;
      } else {
         Item item = itemStack.getItem();
         if (item.isInstance(MappedClasses.zx)) {
            if (!requireConfiguredEnchantment) {
               return true;
            } else {
               if (this.densityMaces.getEffectiveValue()) {
                  int level = EnchantmentHelper.e("density", itemStack);
                  if (level > 0) {
                     return true;
                  }
               }

               if (this.breachMaces.getEffectiveValue()) {
                  int level = EnchantmentHelper.e("breach", itemStack);
                  if (level > 0) {
                     return true;
                  }
               }

               return !this.densityMaces.getEffectiveValue() && !this.breachMaces.getEffectiveValue();
            }
         } else {
            return false;
         }
      }
   }

   private boolean shouldFilterByEnchant() {
      return (this.densityMaces.getEffectiveValue() || this.breachMaces.getEffectiveValue()) && this.hasMaceInHotbar();
   }

   @EventHandler(priority = EventPriority.HIGH)
   public void onSyntheticAttack(SyntheticAttackRequestEvent event) {
      if (event.getSource() != this && !(event.getSource() instanceof ShieldBreaker)) {
         this.handleAttack(new SyntheticAttackCancellationAdapter(event));
      }
   }

   public boolean hasValidWeaponSwap() {
      int weaponSlot = this.findWeaponSlot();
      if (weaponSlot < 0) {
         return false;
      } else {
         InventoryPlayer inventory = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
         ItemStack stack = inventory.c(weaponSlot);
         return this.isValidMace(stack, this.shouldFilterByEnchant());
      }
   }

   @EventHandler(priority = EventPriority.HIGH)
   public void onTick(EventPreTick eventPreTick) {
      if (!eventPreTick.getThePlayer().isNull()) {
         if (this.swingReleasePending) {
            AttackKeyController.releaseAttackKey();
            this.swingReleasePending = false;
         }

         if (this.breachSwapPending && this.swapActive && this.tickCounter >= 1) {
            int maceSlot = this.findMaceSlot();
            if (maceSlot != -1) {
               eventPreTick.getThePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(maceSlot);
               AttackKeyController.releaseAttackKey();
               AttackKeyController.requestSyntheticAttack(this);
               this.swingReleasePending = true;
            }

            this.breachSwapPending = false;
         } else {
            if (this.swapAttackPending && this.swapActive) {
               AttackKeyController.releaseAttackKey();
               AttackKeyController.requestSyntheticAttack(this);
               this.swingReleasePending = true;
               this.swapAttackPending = false;
               if (this.stunSlamActive) {
                  this.stunSlamSecondPhase = true;
               }
            }

            if (this.stunSlamActive && this.swapActive && this.stunSlamSecondPhase && this.tickCounter >= 1) {
               int maceSlot = this.findMaceSlot();
               if (maceSlot != -1) {
                  eventPreTick.getThePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(maceSlot);
                  AttackKeyController.releaseAttackKey();
                  AttackKeyController.requestSyntheticAttack(this);
                  this.swingReleasePending = true;
               }

               this.stunSlamSecondPhase = false;
               this.stunSlamActive = false;
            } else {
               if (this.swapActive && this.tickCounter++ > (!this.stunSlamActive && !this.stunSlamSecondPhase ? 1 : 2)) {
                  if (this.originalSlot != -1) {
                     eventPreTick.getThePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(this.originalSlot);
                     this.originalSlot = -1;
                  }

                  this.swapActive = false;
                  this.tickCounter = 0;
                  this.stunSlamActive = false;
                  this.stunSlamSecondPhase = false;
               }
            }
         }
      }
   }

   private int findWeaponSlot() {
      InventoryPlayer inventory = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
      int selectedSlot = inventory.v();

      for (int slot = 0; slot < 9; slot++) {
         ItemStack stack;
         if (slot != selectedSlot && !(stack = inventory.c(slot)).isNull() && !stack.getItem().isNull()) {
            Item item = stack.getItem();
            EnumHand activeHand;
            EntityOtherPlayerMP targetPlayer;
            if (this.axes.getEffectiveValue()
               && item.isInstance(MappedClasses.YP)
               && (targetPlayer = this.getTargetPlayer()) != null
               && (activeHand = RotationUtil.q(targetPlayer)) != null) {
               ItemStack activeItem = targetPlayer.i(activeHand);
               float useDuration = targetPlayer.d(activeHand);
               int maximumUseDuration = activeItem.getItem().I(activeItem, targetPlayer);
               if (useDuration > 0.0F) {
                  float elapsed = maximumUseDuration - useDuration;
                  if (elapsed >= 5.0F) {
                     return slot;
                  }

                  return -2;
               }
            }

            if (this.maces.getEffectiveValue() && this.isValidMace(stack, this.shouldFilterByEnchant())) {
               return slot;
            }

            if (this.swords.getEffectiveValue() && ItemStackScoreUtil.h(item)) {
               return slot;
            }
         }
      }

      return -1;
   }

   private boolean isTargetSmashReady(EntityOtherPlayerMP targetPlayer) {
      EnumHand activeHand = RotationUtil.q(targetPlayer);
      if (activeHand == null) {
         return false;
      } else {
         ItemStack activeItem = targetPlayer.i(activeHand);
         float useDuration = targetPlayer.d(activeHand);
         if (useDuration <= 0.0F) {
            return false;
         } else {
            int maximumUseDuration = activeItem.getItem().I(activeItem, targetPlayer);
            float elapsed = maximumUseDuration - useDuration;
            return elapsed >= 5.0F;
         }
      }
   }

   private EntityOtherPlayerMP getTargetPlayer() {
      EntityPlayerSP player = Minecraft.thePlayer();
      return RotationUtil.m(player, 5.0, 90.0, true);
   }

   private void handleAttack(AttackCancellationAdapter cancellation) {
      if (!cancellation.isCancelled() && !this.swapActive) {
         if (this.canHandleAttack()) {
            EntityPlayerSP player = Minecraft.thePlayer();
            if (!player.isNull()) {
               if (!player.isDead()) {
                  RayTraceResult crosshairHit = RotationManager.INSTANCE.getExtendedReachRayTrace();
                  if (!crosshairHit.getEntity().isNull()) {
                     ItemStack heldItem;
                     EntityOtherPlayerMP targetPlayer;
                     if (this.stunSlam.getEffectiveValue()
                        && this.maces.getEffectiveValue()
                        && (heldItem = player.getHeldItemHand()).isNotNull()
                        && heldItem.getItem().isNotNull()
                        && (targetPlayer = this.getTargetPlayer()) != null
                        && this.isTargetSmashReady(targetPlayer)
                        && this.findMaceSlot() >= 0) {
                        if (heldItem.getItem().isInstance(MappedClasses.YP)) {
                           this.originalSlot = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v();
                           this.breachSwapPending = true;
                           this.swapActive = true;
                           this.tickCounter = 0;
                           return;
                        }

                        int axeSlot = this.findAxeSlot();
                        if (axeSlot >= 0) {
                           cancellation.setCancelled(true);
                           this.originalSlot = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v();
                           player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(axeSlot);
                           this.stunSlamActive = true;
                           this.swapActive = true;
                           this.tickCounter = 0;
                           this.swapAttackPending = true;
                           return;
                        }
                     }

                     int weaponSlot;
                     if ((weaponSlot = this.findWeaponSlot()) >= 0) {
                        cancellation.setCancelled(true);
                        this.originalSlot = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v();
                        player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(weaponSlot);
                        this.swapActive = true;
                        this.tickCounter = 0;
                        this.swapAttackPending = true;
                     }
                  }
               }
            }
         }
      }
   }

   @EventHandler(priority = EventPriority.HIGH)
   public void onMouseButton(EventMouseButton event) {
      if (event.isKeybinding(Minecraft.gameSettings().F()) && event.isDown()) {
         this.handleAttack(new PhysicalAttackCancellationAdapter(event));
      }
   }

   @EventHandler(priority = EventPriority.HIGH)
   public void onKeyPress(EventKeyPress event) {
      if (event.isKeybinding(Minecraft.gameSettings().F()) && event.isDown()) {
         this.handleAttack(new PhysicalAttackCancellationAdapter(event));
      }
   }
}
