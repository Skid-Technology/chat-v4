package gg.vape.module.combat;

import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventKeyInputBase;
import gg.vape.event.impl.EventKeyPress;
import gg.vape.event.impl.EventMouseButton;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.event.impl.EventPreTick;
import gg.vape.event.impl.EventRender3D;
import gg.vape.input.AttackKeyController;
import gg.vape.input.InputEventDispatcher;
import gg.vape.input.KeyBindingHelper;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.combat.silentaura.SilentAuraAimJitter;
import gg.vape.module.combat.silentaura.SilentAuraClicker;
import gg.vape.module.combat.silentaura.SilentAuraEntityIdComparator;
import gg.vape.module.combat.silentaura.SilentAuraRotationController;
import gg.vape.module.control.SharedModuleControlClaims;
import gg.vape.module.render.Freecam;
import gg.vape.module.utility.AutoMace;
import gg.vape.rotation.AdaptiveRotationController;
import gg.vape.rotation.MouseRotationController;
import gg.vape.rotation.RotationAngles;
import gg.vape.rotation.RotationControlClaim;
import gg.vape.rotation.RotationManager;
import gg.vape.unmap.ItemLimitData;
import gg.vape.unmap.ModeOption;
import gg.vape.utils.EntityAngleComparator;
import gg.vape.utils.EntityArmorValueComparator;
import gg.vape.utils.EntityDistanceComparator;
import gg.vape.utils.EntityEquipmentValueComparator;
import gg.vape.utils.EntityHealthComparator;
import gg.vape.utils.MathUtil;
import gg.vape.utils.RayTraceUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.Vec3d;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.RenderUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.ColorValue;
import gg.vape.value.EntityTargetFilterValue;
import gg.vape.value.LimitValue;
import gg.vape.value.ModeValue;
import gg.vape.value.NumberValue;
import gg.vape.value.RandomClickDelayValue;
import gg.vape.value.RandomValue;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityOtherPlayerMP;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GuiScreen;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.RayTraceResult_type;
import gg.vape.wrapper.impl.TitledScreen;
import gg.vape.wrapper.impl.Vec3;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class SilentAura extends Mod {
   private final ModeOption distanceMode;
   private final SilentAuraAimJitter xJitter;
   private long nextBreakBlockTime = 0L;
   private final SilentAuraAimJitter pitchJitter;
   public final ModeOption armorMode;
   public final BooleanValue showTarget;
   private boolean perfectSwingAttackPending = false;
   private final RandomClickDelayValue attackRate;
   private float yawIntegralScale = 1.0F;
   public final ModeOption threatMode;
   public final NumberValue maxAngle;
   private AdaptiveRotationController rotationController;
   public final EntityTargetFilterValue targetFilter = EntityTargetFilterValue.createForModule(this);
   public final ModeOption closestMode;
   private final BooleanValue switchTargets;
   private final BooleanValue limitToItems;
   public final ModeOption yawMode;
   private final RotationControlClaim rotationClaim;
   private EntityLivingBase target = null;
   private static SilentAuraClicker clicker;
   private final RandomValue breakBlocksDelay;
   private boolean readyToAttack;
   public final ModeOption ringMode;
   private final BooleanValue requireMouseDown;
   private final LimitValue allowedItems;
   private float yawIntegral = 0.0F;
   private float pitchIntegral = 0.0F;
   public final ModeOption cpsMode;
   public final ModeOption triggerMode;
   public final ModeValue clickMode;
   private final BooleanValue airCrits;
   private final NumberValue earlyHitChance;
   private final RandomValue extraDelay;
   private final BooleanValue ignoreActivationClick;
   private final RandomValue mouseOverDelay;
   private final BooleanValue selectFirstHit;
   private final NumberValue targetMissChance;
   private final BooleanValue shieldCheck;
   public final ModeOption centerMode;
   private float pitchProportionalScale = 1.0F;
   private final ColorValue attackColor;
   private final NumberValue aimSpeed = NumberValue.createWithDescription(this, "Aim speed", "#.#", "", 1.0, 7.0, 10.0, "How fast aiming will be done silently");
   public final ModeOption healthMode;
   private boolean onTarget = false;
   private final BooleanValue breakBlocksWhitelist;
   public final BooleanValue disableOnDeath;
   private final SilentAuraAimJitter zJitter;
   private boolean breakingBlocks;
   private boolean toggledOff = false;
   private int earlyHitTicks = 0;
   private long mouseOverTime = 0L;
   private boolean hasBeenHit = false;
   private boolean hasClickedOnce = false;
   private boolean wasReadyToAttack = false;
   private boolean attackMissPending = false;
   private boolean mouseDown = false;
   private boolean attackCanceled = false;
   private final TimerUtil clickTimer = new TimerUtil();
   private int playerHurtTime = 0;
   private boolean lastOnTarget = false;
   private int mouseOverTicks = -1;
   private long mouseOverDelayTarget = 0L;
   private final TimerUtil mouseOverTimer = new TimerUtil();
   private int firstHitTargetId = -1;
   private boolean waitingForFirstHit = false;
   private boolean selectFirstHitWaiting = false;
   private final TimerUtil firstHitTimer = new TimerUtil();
   public final ModeOption boxMode;
   public final NumberValue extraSwingDistance = NumberValue.createWithDescription(
      this, "Extra swing distance", "#.#", "", 0.0, 1.0, 3.0, "Extra distance past attack range at which aura will begin to engage, before attacking"
   );
   private final BooleanValue breakBlocks;
   private static Freecam freecam;
   private final LimitValue blockBreakItems;
   private final ColorValue targetColor;
   private final Random random;
   private final TimerUtil breakBlocksTimer;
   public ModeValue renderType;
   private float yawProportionalScale = 1.0F;
   private float pitchIntegralScale = 1.0F;
   private static final long MODULE_ID = 267655872188715318L;
   public ModeValue targetArea;
   public ModeValue targetMode;
   private boolean deathHandled = false;

   public EntityLivingBase getTarget() {
      return this.target;
   }

   public RotationControlClaim getRotationClaim() {
      return this.rotationClaim;
   }

   public NumberValue getAimSpeed() {
      return this.aimSpeed;
   }

   public RandomClickDelayValue getAttackRate() {
      return this.attackRate;
   }

   public BooleanValue getLimitToItems() {
      return this.limitToItems;
   }

   public LimitValue getAllowedItems() {
      return this.allowedItems;
   }

   public BooleanValue getRequireMouseDown() {
      return this.requireMouseDown;
   }

   @EventHandler
   public void onRender3D(EventRender3D eventRender3D) {
      if (this.showTarget.getEffectiveValue() && this.target != null && Minecraft.currentScreen().isNull()) {
         float ringRadius = !this.target.isInstance(MappedClasses.Yl) && !this.target.isInstance(MappedClasses.lG) ? this.target.f$src$F$fst3ac() : 0.7F;
         if (this.ringMode.isSelected()) {
            GuiRenderPrimitives.R(
               this.target.c(),
               this.target.A(),
               this.target.Z(),
               50.0F,
               ringRadius,
               this.target.Y(),
               this.isLookingAtTarget() ? this.attackColor.getMutableColor() : this.targetColor.getMutableColor()
            );
         } else {
            RenderUtil.k(
               this.target,
               1.0,
               null,
               this.isLookingAtTarget() ? this.attackColor.getMutableColor() : this.targetColor.getMutableColor(),
               eventRender3D.getTicks()
            );
         }
      }
   }

   private boolean passesItemFilter(EntityLivingBase target) {
      if (this.limitToItems.getEffectiveValue()) {
         ItemStack itemStack = Minecraft.thePlayer().getHeldItemHand();
         return !this.allowedItems.isValid(itemStack, false) ? false : this.targetFilter.isValidTarget(target);
      } else {
         return this.targetFilter.isValidTarget(target);
      }
   }

   @Override
   public String getDetailedSuffix() {
      if (ForgeVersion.MC_1_12_2.d() && this.clickMode.getValue() == this.triggerMode) {
         float attackStrength = Minecraft.thePlayer().getCooledAttackStrength(0.0F);
         return attackStrength == 1.0F ? "§6Ready" : String.format("%.1f", attackStrength);
      } else {
         return this.attackRate.getDisplayValue() + "cps";
      }
   }

   public boolean isValidTarget(EntityLivingBase candidate) {
      if (candidate.isNull()) {
         return false;
      } else if (candidate.equals(Minecraft.thePlayer())) {
         return false;
      } else if (candidate.w$src$F$15l9epb() <= 0.0F || candidate.M$src$Z$ff28xj()) {
         return false;
      } else if (!this.isInRange(candidate)) {
         return false;
      } else if (RotationUtil.a(Minecraft.thePlayer(), candidate) > this.maxAngle.getValue().intValue() / 2) {
         return false;
      } else if (Vape.INSTANCE.getFriendManager().isFriend(candidate)) {
         return false;
      } else if (candidate.equals(Minecraft.thePlayer().S$src$Lgg_vape_wrapper_impl_Entity_$dgzs12())) {
         return false;
      } else {
         if (ForgeVersion.MC_1_12_2.d() && this.clickMode.getValue() == this.triggerMode && this.shieldCheck.getEffectiveValue()) {
            boolean enforceShieldCheck = true;
            ShieldBreaker shieldBreaker = Vape.INSTANCE.getModManager().getMod(ShieldBreaker.class);
            if (shieldBreaker != null && shieldBreaker.isEnabled() && shieldBreaker.hasAxeInHotbar()) {
               enforceShieldCheck = false;
            }

            HitSwap hitSwap = Vape.INSTANCE.getModManager().getMod(HitSwap.class);
            if (hitSwap != null && hitSwap.isEnabled() && hitSwap.hasAlternateAxe()) {
               enforceShieldCheck = false;
            }

            AutoMace autoMace = Vape.INSTANCE.getModManager().getMod(AutoMace.class);
            if (autoMace != null && autoMace.isEnabled() && autoMace.Z$src$Z$15nifn6()) {
               enforceShieldCheck = false;
            }

            if (enforceShieldCheck && candidate.isInstance(MappedClasses.lG) && RotationUtil.isBlockingShield(new EntityOtherPlayerMP(candidate.getObject()))) {
               return false;
            }
         }

         return this.passesItemFilter(candidate);
      }
   }

   private boolean isInRange(EntityLivingBase target) {
      double[] aimCoordinates = this.computeAimCoords(target);
      double distance = Minecraft.thePlayer().i(aimCoordinates[0], aimCoordinates[1], aimCoordinates[2]);
      return distance <= this.getAttackRange();
   }

   private void randomizeGainFactors() {
      this.pitchProportionalScale = 0.85F + this.random.nextFloat() * 0.3F;
      this.pitchIntegralScale = 0.85F + this.random.nextFloat() * 0.3F;
      this.yawProportionalScale = 0.8F + this.random.nextFloat() * 0.4F;
      this.yawIntegralScale = 0.85F + this.random.nextFloat() * 0.3F;
   }

   private boolean isTriggerMode() {
      return ForgeVersion.MC_1_12_2.d() && this.clickMode.getValue() == this.triggerMode;
   }

   private boolean isMaceAttackReady() {
      if (!RotationUtil.u(Minecraft.thePlayer())) {
         return false;
      } else {
         AutoMace autoMace = Vape.INSTANCE.getModManager().getMod(AutoMace.class);
         if (autoMace != null && autoMace.isEnabled() && autoMace.d()) {
            return true;
         } else {
            ItemStack heldItem = Minecraft.thePlayer().getHeldItemHand();
            return heldItem.isNotNull() && heldItem.getItem().isNotNull() && heldItem.getItem().isInstance(MappedClasses.zx);
         }
      }
   }

   private void resetEarlyHit() {
      this.earlyHitTicks = 0;
      this.attackMissPending = false;
   }

   private void randomizeEarlyHitTicks() {
      int earlyHitChanceValue = this.earlyHitChance.getValue().intValue();
      this.earlyHitTicks = earlyHitChanceValue > 0 && this.random.nextInt(100) < earlyHitChanceValue ? 2 + this.random.nextInt(2) : 0;
   }

   private boolean checkAttackReadyWithEarlyHit() {
      this.randomizeEarlyHitTicks();
      if (this.isMaceAttackReady()) {
         return true;
      } else {
         float attackStrength = Minecraft.thePlayer().getCooledAttackStrength((float)(-this.extraDelay.getRandomValue()) + this.earlyHitTicks);
         return attackStrength == 1.0F;
      }
   }

   private void resetFirstHit() {
      this.firstHitTargetId = -1;
      this.waitingForFirstHit = false;
      this.selectFirstHitWaiting = false;
      this.firstHitTimer.reset();
   }

   private boolean canHitTargetWithoutFirstHitBlock() {
      return !this.selectFirstHit.getValue() || this.firstHitTargetId == -1 || !this.waitingForFirstHit;
   }

   public boolean isAttackCooldownReady() {
      return this.isTriggerMode() ? this.checkAttackReadyWithEarlyHit() : this.attackRate.hasClickDelayElapsed();
   }

   private boolean handleBreakBlocks() {
      EntityPlayerSP player = Minecraft.thePlayer();
      if (player.isNull()) {
         return false;
      } else {
         if (this.breakBlocks.getEffectiveValue() && this.breakBlocksTimer.hasTimeElapsed(this.nextBreakBlockTime)) {
            if (this.breakBlocksWhitelist.getEffectiveValue() && !this.blockBreakItems.matches(player.getHeldItemHand())) {
               return false;
            }

            if (Minecraft.currentScreen().isInstance(MappedClasses.Ft)) {
               return false;
            }

            RayTraceResult rayTraceResult = RayTraceUtil.o();
            if (rayTraceResult.isNotNull() && rayTraceResult.isBlockHit() && ClientSettings.isAttackButtonDown()) {
               KeyBinding keyBinding = Minecraft.gameSettings().F();
               KeyBindingHelper.updateKeyBinding(keyBinding, true, false);
               return true;
            }

            this.nextBreakBlockTime = (long)this.breakBlocksDelay.getRandomValue();
            this.breakBlocksTimer.reset();
         }

         return false;
      }
   }

   public SilentAura() {
      super(
         "SilentAura",
         (int)MODULE_ID,
         Category.COMBAT,
         "Simulates feel of Killaura\nAttacks and aims safely using built in AutoClicker to click, and Silent Aim system to aim"
      );
      this.requireMouseDown = BooleanValue.create(this, "Require mouse down", false);
      this.disableOnDeath = BooleanValue.create(this, "Disable on death", false);
      this.showTarget = BooleanValue.create(this, "Show target", false);
      this.switchTargets = BooleanValue.create(this, "Switch", false, "Attacks other targets while current target is in hit cooldown");
      this.limitToItems = BooleanValue.create(this, "Limit to items", false, "Aura functions only while holding selected items");
      this.breakBlocks = BooleanValue.create(this, "Break blocks", false, "Prevents from aiming while attempting to break blocks");
      this.breakBlocksDelay = RandomValue.createWithDescription(
         this, "Break blocks delay", "#", "", 0.0, 0.0, 10.0, 2000.0, 1.0, "Delay in milliseconds before breaking blocks"
      );
      this.breakBlocksWhitelist = BooleanValue.create(this, "Break blocks whitelist", false);
      this.blockBreakItems = LimitValue.create(
         this,
         "SilentBlockBreakingItems",
         "Block breaking items",
         LimitValue.ALLOW_LIST_COLOR,
         Arrays.asList(new ItemLimitData("pickaxes"), new ItemLimitData("shovels"))
      );
      this.attackRate = RandomClickDelayValue.create(this, "Attacks per Second", "#.#", "", 1.0, 6.0, 13.0, 20.0);
      this.maxAngle = NumberValue.create(
         this, "Max angle", "#", "", 1.0, 120.0, 360.0, 5.0, "Angle at which targets will be acquired and aimed at\n(From your cursor)"
      );
      this.targetColor = ColorValue.createWithAlpha(this, "Target color", new Color(255, 200, 112), 50);
      this.attackColor = ColorValue.create(this, "Attack color", new Color(255, 0, 0, 100));
      this.distanceMode = new ModeOption("Distance");
      this.yawMode = new ModeOption("Yaw");
      this.armorMode = new ModeOption("Armor");
      this.threatMode = new ModeOption("Threat");
      this.healthMode = new ModeOption("Health");
      this.targetMode = ModeValue.create(
         this,
         "Target mode",
         "How Aura should prioritize targets\nArmor/Threat will default to Distance for non player targets",
         this.distanceMode,
         this.distanceMode,
         this.yawMode,
         this.armorMode,
         this.threatMode,
         this.healthMode
      );
      this.allowedItems = LimitValue.create(this, "silentaura-alloweditems", "Allowed Items", LimitValue.ALLOW_LIST_COLOR, Collections.emptyList());
      this.centerMode = new ModeOption("Center");
      this.closestMode = new ModeOption("Closest");
      this.targetArea = ModeValue.create(
         this,
         "Target area",
         "Where Aura will aim towards\nCenter: Center of entity\nClosest: Closest position on entity hitbox",
         this.centerMode,
         this.centerMode,
         this.closestMode
      );
      this.ringMode = new ModeOption("Ring");
      this.boxMode = new ModeOption("Box");
      this.renderType = ModeValue.create(this, "Render type", this.ringMode, this.ringMode, this.boxMode);
      this.cpsMode = new ModeOption("CPS");
      this.triggerMode = new ModeOption("Trigger");
      this.clickMode = ModeValue.create(
         this,
         "Click mode",
         "How SilentAura should time attacks\nCPS: Attacks at a configured clicks per second\nTrigger: Only attacks when there is no attack cooldown and hovering a target",
         this.cpsMode,
         this.cpsMode,
         this.triggerMode
      );
      this.extraDelay = RandomValue.createWithDescription(
         this,
         "Extra delay",
         "#",
         "ticks",
         -20.0,
         0.0,
         0.0,
         20.0,
         0.1,
         "Extra delay after attack cooldown(in ticks)\nNegative values will attack before cooldown is complete"
      );
      this.mouseOverDelay = RandomValue.createWithDescription(
         this, "Mouse over delay", "#", "ms", 0.0, 0.0, 0.0, 200.0, 10.0, "Delay after your crosshair reaches a target before attacking"
      );
      this.selectFirstHit = BooleanValue.create(this, "Select first hit", false, "Waits for the opponent to hit you first before attacking");
      this.ignoreActivationClick = BooleanValue.create(
         this, "Ignore activation click", false, "Ignores first manual click\n(unless already hovering a valid target with attack ready)"
      );
      this.airCrits = BooleanValue.create(this, "Air crits", false, "Won't attack in air unless you will crit");
      this.targetMissChance = NumberValue.create(
         this, "Target miss chance", "#", "%", 0.0, 0.0, 100.0, 1.0, "Chance to attack without hovering a valid target when attack is ready"
      );
      this.earlyHitChance = NumberValue.create(this, "Early hit chance", "#", "%", 0.0, 0.0, 100.0, 1.0, "Chance to attack earlier than attack is ready");
      this.shieldCheck = BooleanValue.create(
         this, "Shield check", false, "Won't attack players blocking with shield\nUsing ShieldBreaker, HitSwap, or AutoMace will override this behavior"
      );
      this.breakBlocksTimer = new TimerUtil();
      this.rotationClaim = SharedModuleControlClaims.rotation;
      this.random = new Random();
      this.pitchJitter = new SilentAuraAimJitter(-0.3, 0.25);
      this.xJitter = new SilentAuraAimJitter(-0.15, 0.15);
      this.zJitter = new SilentAuraAimJitter(-0.15, 0.15);
      this.clickMode.whenEqualTo(this.cpsMode).applyTo(this.attackRate);
      this.clickMode
         .whenEqualTo(this.triggerMode)
         .applyTo(
            this.extraDelay,
            this.mouseOverDelay,
            this.selectFirstHit,
            this.ignoreActivationClick,
            this.airCrits,
            this.shieldCheck,
            this.targetMissChance,
            this.earlyHitChance
         );
      this.requireMouseDown.whenEqualTo(true).applyTo(this.ignoreActivationClick);
      this.addValue(this.targetFilter);
      this.U(this.clickMode, ForgeVersion.MC_1_8_9.N());
      this.addValue(this.attackRate);
      this.X(
         ForgeVersion.MC_1_8_9.N(),
         this.extraDelay,
         this.mouseOverDelay,
         this.selectFirstHit,
         this.ignoreActivationClick,
         this.airCrits,
         this.shieldCheck,
         this.targetMissChance,
         this.earlyHitChance
      );
      this.addValue(this.aimSpeed, this.extraSwingDistance, this.maxAngle, this.targetMode, this.targetArea);
      this.showTarget.addDependentValues(this.targetColor, this.attackColor, this.renderType);
      this.breakBlocks.addDependentValues(this.breakBlocksDelay, this.breakBlocksWhitelist);
      this.breakBlocksWhitelist.addDependentValues(this.blockBreakItems);
      this.U(this.switchTargets, ForgeVersion.MC_1_8_9.H());
      this.addValue(
         this.disableOnDeath,
         this.breakBlocks,
         this.breakBlocksDelay,
         this.breakBlocksWhitelist,
         this.blockBreakItems,
         this.requireMouseDown,
         this.showTarget,
         this.targetColor,
         this.attackColor,
         this.renderType,
         this.limitToItems.addDependentValues(this.allowedItems),
         this.allowedItems
      );
      this.limitToItems.setCompactListValue(this.allowedItems);
      this.rotationClaim.setPriority(this, 5);
      this.attackRate.setMaximumFractionDigits(0);
   }

   public boolean canAttack() {
      if (!this.checkAttackReadyWithEarlyHit()) {
         return false;
      } else if (!this.canHitTargetWithoutFirstHitBlock()) {
         return false;
      } else if (this.shieldCheck.getEffectiveValue()
         && this.target != null
         && this.target.isInstance(MappedClasses.lG)
         && RotationUtil.isBlockingShield(new EntityOtherPlayerMP(this.target.getObject()))) {
         return false;
      } else {
         if (this.airCrits.getValue()) {
            EntityPlayerSP player = Minecraft.thePlayer();
            if (!player.boolean_d() && player.N() - player.W() >= 0.0) {
               return false;
            }
         }

         return true;
      }
   }

   @EventHandler
   public void onTick(EventPrePlayerTick eventPrePlayerTick) {
   }

   public boolean canClickAttack() {
      return this.isTriggerMode() ? false : this.canAttack();
   }

   private void updateRotationColors() {
      if (this.target != null && this.isControllingRotation()) {
         this.onTarget = this.isLookingAtTarget();
         this.rotationController.setSecondaryColor(this.attackColor.getMutableColor());
         this.rotationController.setPrimaryColor(this.targetColor.getMutableColor());
         this.rotationController.setUseSecondaryColor(this.onTarget);
      }
   }

   private void handleAttackInput(boolean isDown, EventKeyInputBase event) {
      if (this.requireMouseDown.getEffectiveValue() && isDown && !this.mouseDown) {
         this.mouseDown = true;
         this.clickTimer.reset();
         this.resetEarlyHit();
         if (this.ignoreActivationClick.getEffectiveValue()
            && Minecraft.currentScreen().isNull()
            && InputEventDispatcher.getInstance().getFocusState().isFocused()) {
            boolean shouldCancel = false;
            if (!this.checkAttackReadyWithEarlyHit()) {
               shouldCancel = true;
            }

            RayTraceResult rayTraceResult;
            if ((rayTraceResult = RotationManager.INSTANCE.getExtendedReachRayTrace()).isNotNull()
               && rayTraceResult.getTypeOfHit().equals(RayTraceResult_type.miss())) {
               shouldCancel = true;
            }

            if (shouldCancel) {
               this.attackCanceled = true;
               event.setCancelled(true);
            }
         }
      } else {
         if (isDown) {
            this.resetEarlyHit();
         }
      }
   }

   @EventHandler
   public void onMouseButton(EventMouseButton event) {
      if (!Minecraft.thePlayer().isNull() && this.isTriggerMode()) {
         if (event.isKeybinding(Minecraft.gameSettings().F())) {
            this.handleAttackInput(event.getButtonState(), event);
         }
      }
   }

   @EventHandler
   public void onKeyPress(EventKeyPress event) {
      if (!Minecraft.thePlayer().isNull() && this.isTriggerMode()) {
         if (event.isKeybinding(Minecraft.gameSettings().F())) {
            this.handleAttackInput(event.isDown(), event);
         }
      }
   }

   private void resetMouseOverDelay() {
      this.mouseOverTicks = -1;
      this.mouseOverDelayTarget = 0L;
      this.mouseOverTimer.reset();
   }

   private void updateFirstHitTimeout() {
      if (this.selectFirstHit.getValue() && this.firstHitTargetId != -1) {
         if (!this.selectFirstHitWaiting) {
            this.selectFirstHitWaiting = true;
            this.firstHitTimer.reset();
         } else {
            if (this.firstHitTimer.hasTimeElapsed(1000L)) {
               this.resetFirstHit();
            }
         }
      }
   }

   private boolean isTargetInAttackRange() {
      return this.target == null ? false : Minecraft.thePlayer().getDistanceToEntity(this.target) <= 3.0 + this.extraSwingDistance.getValue();
   }

   private boolean hasMouseOverDelayRemaining() {
      if (this.mouseOverDelay.getMaximumValue() <= 0.0) {
         return false;
      } else {
         if (this.mouseOverDelayTarget == 0L) {
            this.mouseOverDelayTarget = (long)this.mouseOverDelay.getRandomValue();
            this.mouseOverTimer.reset();
         }

         return !this.mouseOverTimer.hasTimeElapsed(this.mouseOverDelayTarget);
      }
   }

   private void handleTargetMiss() {
      if (this.lastOnTarget && !this.hasMouseOverDelayRemaining()) {
         if (this.isTargetInAttackRange() && this.canHitTargetWithoutFirstHitBlock() && this.checkAttackReadyWithEarlyHit()) {
            int missChance = this.targetMissChance.getValue().intValue();
            this.attackMissPending = missChance > 0 && this.random.nextInt(100) < missChance;
            if (this.attackMissPending) {
               AttackKeyController.releaseAttackKey();
               this.perfectSwingAttackPending = AttackKeyController.requestSyntheticAttack(this);
               this.attackMissPending = false;
            }
         }
      }
   }

   private void handleTriggerAttack() {
      if (this.clickTimer.hasTimeElapsed(50L)) {
         if (this.perfectSwingAttackPending) {
            AttackKeyController.releaseAttackKey();
            this.perfectSwingAttackPending = false;
         } else if (this.target != null && this.isControllingRotation()) {
            if (this.onTarget) {
               if (this.canAttack()) {
                  AttackKeyController.releaseAttackKey();
                  this.perfectSwingAttackPending = AttackKeyController.requestSyntheticAttack(this);
               }
            } else {
               this.resetMouseOverDelay();
               this.updateFirstHitTimeout();
               this.handleTargetMiss();
            }

            this.lastOnTarget = this.onTarget;
         } else {
            this.lastOnTarget = false;
         }
      }
   }

   @EventHandler(priority = EventPriority.LOWEST)
   public void onTick(EventPreTick eventPreTick) {
      int hurtTime = eventPreTick.getThePlayer().isNull() ? 0 : eventPreTick.getThePlayer().i();
      boolean playerWasHurt = this.playerHurtTime <= 0 && hurtTime > 0;
      this.playerHurtTime = hurtTime;
      if (this.attackCanceled) {
         Minecraft.gameSettings().F().onTick(0);
         this.attackCanceled = false;
      }

      this.breakingBlocks = this.handleBreakBlocks();
      if (this.perfectSwingAttackPending && !this.isTriggerMode()) {
         this.perfectSwingAttackPending = false;
         AttackKeyController.releaseAttackKey();
      }

      if (Minecraft.thePlayer().isNull()) {
         this.resetTargeting();
      } else if (!this.toggledOff && this.isEnabled() && !this.breakingBlocks) {
         if (clicker == null) {
            clicker = Vape.INSTANCE.getModManager().getMod(SilentAuraClicker.class);
         }

         if (!clicker.isEnabled()) {
            clicker.setEnabled(true);
         }

         this.updateRotationColors();
         if (this.disableOnDeath.getEffectiveValue()) {
            if (Minecraft.thePlayer().M$src$Z$ff28xj() || Minecraft.thePlayer().w$src$F$15l9epb() <= 0.0F) {
               this.toggle();
               return;
            }

            if (ForgeVersion.MC_1_16_5.d()) {
               GuiScreen guiScreen = Minecraft.currentScreen();
               if (guiScreen.isNotNull()) {
                  if (!this.deathHandled && guiScreen.isInstance(MappedClasses.D2)) {
                     this.deathHandled = true;
                     this.toggle();
                     return;
                  }

                  this.deathHandled = false;
               }
            } else {
               TitledScreen titledScreen = Minecraft.k();
               if (titledScreen.isNotNull()) {
                  String screenTitle = titledScreen.getDisplayedTitle();
                  if (!this.deathHandled && screenTitle != null && (screenTitle.toLowerCase().contains("died") || screenTitle.toLowerCase().contains("dead"))) {
                     this.deathHandled = true;
                     this.toggle();
                     return;
                  }

                  if (screenTitle == null || screenTitle.equals("")) {
                     this.deathHandled = false;
                  }
               }
            }
         }

         if (this.requireMouseDown.getEffectiveValue()
            && this.isTriggerMode()
            && this.mouseDown
            && !ClientSettings.isPhysicalKeyDown(Minecraft.gameSettings().F())) {
            this.mouseDown = false;
            this.attackCanceled = false;
            this.resetFirstHit();
         }

         if (this.isTriggerMode() && this.selectFirstHit.getValue() && this.waitingForFirstHit && this.firstHitTargetId != -1 && playerWasHurt) {
            this.waitingForFirstHit = false;
         }

         if (InputEventDispatcher.getInstance().getFocusState().isFocused()
            && (!this.requireMouseDown.getEffectiveValue() || ClientSettings.isAttackButtonDown())) {
            this.updateTarget();
            this.updateAim();
            if (this.readyToAttack && !this.wasReadyToAttack) {
               this.mouseOverTime = System.currentTimeMillis() + (long)this.mouseOverDelay.getRandomValue();
            }

            this.wasReadyToAttack = this.readyToAttack;
            if (this.isTriggerMode()) {
               this.handleTriggerAttack();
            }
         } else {
            this.resetTargeting();
         }
      } else {
         this.resetTargeting();
      }
   }

   private boolean isControllingRotation() {
      MouseRotationController mouseRotationController = RotationManager.INSTANCE.getActiveController();
      return mouseRotationController != null && mouseRotationController.equals(this.rotationController);
   }

   private void updateTarget() {
      ArrayList<EntityLivingBase> candidates = new ArrayList<>();
      if (!this.shouldSkip()) {
         for (Object entityObject : new ArrayList(Minecraft.theWorld().z())) {
            Entity entity = new Entity(entityObject);
            EntityLivingBase candidate;
            if ((!ClientSettings.IS_LEGACY_1_7 || !entity.isInstance(MappedClasses.FT))
               && entity.isInstance(MappedClasses.zm)
               && this.isValidTarget(candidate = new EntityLivingBase(entityObject))) {
               candidates.add(candidate);
            }
         }

         if (this.targetMode.getValue() == this.yawMode) {
            candidates.sort(new EntityAngleComparator());
         } else if (this.targetMode.getValue() == this.distanceMode) {
            candidates.sort(new EntityDistanceComparator());
         } else if (this.targetMode.getValue() == this.threatMode) {
            candidates.sort(new EntityArmorValueComparator());
         } else if (this.targetMode.getValue() == this.armorMode) {
            candidates.sort(new EntityEquipmentValueComparator());
         } else if (this.targetMode.getValue() == this.healthMode) {
            candidates.sort(new EntityHealthComparator());
         }

         if (this.switchTargets.getEffectiveValue()) {
            candidates.sort(new SilentAuraEntityIdComparator());
         }

         if (!candidates.isEmpty()) {
            EntityLivingBase selectedTarget = candidates.get(0);
            boolean targetChanged = this.target != null && !this.target.equals(selectedTarget);
            if (this.target == null || targetChanged) {
               AttackKeyController.releaseAttackKey();
               this.randomizeGainFactors();
            }

            if (this.target == null || !this.target.equals(selectedTarget)) {
               this.hasBeenHit = false;
            }

            this.target = selectedTarget;
            if (!this.rotationClaim.isOwnedBy(this)) {
               this.rotationClaim.acquire(this, true);
            }

            this.readyToAttack = false;
            if (this.rotationController != null) {
               if (ForgeVersion.MC_1_12_2.d() && this.clickMode.getValue() == this.triggerMode) {
                  RayTraceResult rayTrace = RotationManager.INSTANCE.getExtendedReachRayTrace();
                  if (rayTrace.isNotNull() && rayTrace.getEntity().isNotNull() && rayTrace.getEntity().equals(this.target)) {
                     this.readyToAttack = true;
                  }
               } else {
                  double angularDistance = RotationUtil.L(this.target);
                  if (angularDistance < 3.0 && this.isInRange(this.target)) {
                     this.readyToAttack = true;
                  }
               }
            }
         } else {
            this.resetTargeting();
         }
      }
   }

   private double[] computeAimCoords(EntityLivingBase target) {
      if (this.targetArea.getValue() == this.closestMode) {
         Vec3d closestPoint = RotationUtil.T(Minecraft.thePlayer(), target.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl(), 0.0, 0.0, 0.0);
         double motionX = target.z() - target.f();
         double motionZ = target.h() - target.R();
         double currentX = closestPoint.getX();
         double currentY = target.N();
         double currentZ = closestPoint.getZ();
         double previousX = currentX - motionX;
         double previousY = target.H();
         double previousZ = currentZ - motionZ;
         return new double[]{currentX, currentY, currentZ, previousX, previousY, previousZ};
      } else {
         return new double[]{target.z(), target.N(), target.h(), target.f(), target.H(), target.R()};
      }
   }

   private void updateAim() {
      if (Minecraft.theWorld().isNull()) {
         this.resetTargeting();
      } else {
         boolean hasValidTarget = this.target != null && this.isValidTarget(this.target);
         boolean ownsRotation = this.rotationClaim.isOwnedBy(this);
         if (hasValidTarget && ownsRotation) {
            this.resetJitter();
            EntityPlayerSP player = Minecraft.thePlayer();
            double[] aimCoordinates = this.computeAimCoords(this.target);
            double targetX = aimCoordinates[0];
            double targetY = aimCoordinates[1];
            double targetZ = aimCoordinates[2];
            double previousTargetX = aimCoordinates[3];
            double previousTargetZ = aimCoordinates[5];
            double distanceToTarget = player.i(targetX, targetY, targetZ);
            double targetMotionX = targetX - previousTargetX;
            double targetMotionZ = targetZ - previousTargetZ;
            double horizontalTargetMotion = Math.sqrt(targetMotionX * targetMotionX + targetMotionZ * targetMotionZ);
            double jitteredTargetX = targetX + this.xJitter.getCurrentOffset() * (1.0 + horizontalTargetMotion);
            double jitteredTargetZ = targetZ + this.zJitter.getCurrentOffset() * (1.0 + horizontalTargetMotion);
            double targetHeight = this.target.Y();
            double playerEyeY = player.N() + 1.62;
            double jitteredTargetY = playerEyeY < targetY
               ? targetY + this.pitchJitter.getCurrentOffset() * 0.5
               : Math.min(playerEyeY, targetY + targetHeight) - 0.275 + this.pitchJitter.getCurrentOffset();
            if (this.rotationController == null) {
               this.rotationController = new SilentAuraRotationController(this);
               this.rotationController.setRelativeMode(false);
               this.rotationController.setRetainAfterCompletion(true);
               this.rotationController.setClampStepToRemaining(true);
               this.rotationController.setTolerance(0.0F);
               this.rotationController.setScaleAxesProportionally(false);
               this.rotationController.setLinearAcceleration(false);
               RotationManager.INSTANCE.setController(this.rotationController);
            } else {
               this.rotationController.setRelativeMode(false);
               this.rotationController.setScaleAxesProportionally(false);
               this.rotationController.setLinearAcceleration(false);
               RotationAngles desiredRotation = this.rotationController.calculateRotation(Vec3.create(jitteredTargetX, jitteredTargetY, jitteredTargetZ));
               float targetPitch = (float)RotationUtil.h(player, targetX, jitteredTargetY, targetZ);
               float managedYaw = RotationManager.INSTANCE.getManagedYaw();
               float managedPitch = RotationManager.INSTANCE.getManagedPitch();
               float yawError = MathUtil.wrapAngleTo180(desiredRotation.getYaw() - managedYaw);
               float pitchError = MathUtil.wrapAngleTo180(targetPitch - managedPitch);
               float previousPitchStep = managedPitch - RotationManager.INSTANCE.getPreviousManagedPitch();
               float previousYawStep = managedYaw - RotationManager.INSTANCE.getPreviousManagedYaw();
               float integrationStep = 0.05F;
               boolean yawContinuingInSameDirection = Math.signum(yawError) == Math.signum(previousYawStep);
               double playerHorizontalSpeed = Math.sqrt(player.t() * player.t() + player.T() * player.T());
               float pitchProportionalGain = 0.45F * this.pitchProportionalScale;
               float pitchIntegralGain = 0.91F * this.pitchIntegralScale;
               float yawProportionalGain = (this.onTarget ? 0.05F : 0.1F) * this.yawProportionalScale;
               float yawIntegralGain = 0.33F * this.yawIntegralScale;
               double playerVerticalMotion = player.q();
               if (Math.abs(playerVerticalMotion) > 0.1) {
                  pitchError *= (float)(1.0 + Math.random() * 0.32);
               }

               if (yawContinuingInSameDirection && Math.abs(yawError) < 20.0F) {
                  yawProportionalGain *= 2.5F;
                  previousYawStep *= (float)(1.0 + Math.min(horizontalTargetMotion + playerHorizontalSpeed, 0.25));
               }

               if (distanceToTarget < 0.8) {
                  double distanceScale = distanceToTarget / 0.8;
                  pitchError *= (float)(distanceScale * distanceScale);
                  yawError *= (float)distanceScale;
               }

               float pitchControlError = pitchError - previousPitchStep + previousYawStep * integrationStep * (Math.random() >= 0.5 ? -1 : 1);
               float yawControlError = yawError - previousYawStep;
               this.pitchIntegral += pitchControlError * integrationStep;
               this.yawIntegral += yawControlError * integrationStep;
               float pitchAdjustment = pitchProportionalGain * pitchControlError + pitchIntegralGain * this.pitchIntegral;
               float yawAdjustment = yawProportionalGain * yawControlError + yawIntegralGain * this.yawIntegral;
               if (Math.abs(yawError) > 120.0F) {
                  this.yawIntegral = 0.0F;
                  yawAdjustment = 0.0F;
               }

               if (Minecraft.currentScreen().isNotNull()) {
                  this.yawIntegral = 0.0F;
                  this.pitchIntegral = 0.0F;
               }

               this.rotationController.setTargetRotation(managedYaw + yawError + yawAdjustment / 3.0F, managedPitch + pitchAdjustment);
            }

            if (RotationManager.INSTANCE.getActiveController() == null || !this.isControllingRotation() && RotationManager.INSTANCE.hasAdaptiveController()) {
               RotationManager.INSTANCE.setController(this.rotationController);
            }
         } else {
            this.resetTargeting();
         }
      }
   }

   @Override
   public void setEnabled(boolean enabledState, boolean bypassVisibilityCheck) {
      this.pitchIntegral = 0.0F;
      this.yawIntegral = 0.0F;
      if (!enabledState && this.isControllingRotation()) {
         this.toggledOff = !this.toggledOff;
      } else {
         this.toggledOff = false;
         super.setEnabled(enabledState, bypassVisibilityCheck);
      }
   }

   private boolean isLookingAtTarget() {
      RayTraceResult rayTraceResult;
      return this.target != null
         && this.isControllingRotation()
         && this.isInRange(this.target)
         && (rayTraceResult = RotationManager.INSTANCE.getExtendedReachRayTrace()) != null
         && rayTraceResult.isNotNull()
         && this.target.equals(rayTraceResult.getEntity());
   }

   public void resetTargeting() {
      this.pitchIntegral = 0.0F;
      this.yawIntegral = 0.0F;
      this.target = null;
      this.readyToAttack = false;
      if (this.rotationController != null && this.isControllingRotation()) {
         this.rotationController.setScaleAxesProportionally(true);
         this.rotationController.setLinearAcceleration(true);
         RotationManager.INSTANCE.releaseController(this.rotationController);
      }

      if (RotationManager.INSTANCE.getActiveController() == null
         || RotationManager.INSTANCE.getActiveController() != this.rotationController
         || this.rotationController != null && this.rotationController.isRelativeMode() && this.rotationController.isComplete()) {
         this.rotationController = null;
         this.rotationClaim.release(this);
         if (this.toggledOff) {
            this.toggledOff = false;
            super.setEnabled(false, true);
         }
      }
   }

   private double getAttackRange() {
      return 3.0 + this.extraSwingDistance.getValue();
   }

   private void resetJitter() {
      this.pitchJitter.update();
      this.xJitter.update();
      this.zJitter.update();
   }

   private boolean shouldSkip() {
      if (freecam == null) {
         freecam = Vape.INSTANCE.getModManager().getMod(Freecam.class);
      }

      return this.toggledOff
         || freecam != null && freecam.isEnabled()
         || this.breakingBlocks
         || this.rotationClaim.isBlockedFor(this) && !this.rotationClaim.acquire(this, true);
   }

   @Override
   public void onDisable() {
      if (this.rotationController != null) {
         this.rotationController = null;
      }

      if (this.perfectSwingAttackPending) {
         AttackKeyController.releaseAttackKey();
         this.perfectSwingAttackPending = false;
      }

      this.mouseDown = false;
      this.attackCanceled = false;
      this.lastOnTarget = false;
      this.resetEarlyHit();
      this.resetMouseOverDelay();
      this.resetFirstHit();
      this.rotationClaim.release(this);
   }
}
