/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.combat;

import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.event.Event;
import gg.vape.event.EventHandler;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventKeyPress;
import gg.vape.event.impl.EventMouseButton;
import gg.vape.event.impl.EventPreTick;
import gg.vape.input.AttackKeyController;
import gg.vape.input.InputEventDispatcher;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.combat.HitSwap;
import gg.vape.module.combat.ShieldBreaker;
import gg.vape.module.utility.AutoMace;
import gg.vape.rotation.RotationManager;
import gg.vape.utils.AttackCooldownUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.TimerUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.EntityTargetFilterValue;
import gg.vape.value.LimitValue;
import gg.vape.value.NumberValue;
import gg.vape.value.RandomValue;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityOtherPlayerMP;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EnumHand;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.RayTraceResult_type;
import java.util.Collections;
import java.util.Random;

public class Triggerbot
extends Mod {
    public final EntityTargetFilterValue targetFilter = EntityTargetFilterValue.createForModule(this);
    public final RandomValue extraDelay = RandomValue.createWithDescription(this, "Extra delay", "#", "ticks", -20.0, 0.0, 0.0, 20.0, 0.1, "Extra delay after attack cooldown(in ticks)\nNegative values will attack before cooldown is complete");
    public final RandomValue mouseOverDelay = RandomValue.createWithDescription(this, "Mouse over delay", "#", "ms", 0.0, 0.0, 0.0, 200.0, 10.0, "Delay after your crosshair reaches a target before attacking");
    public final BooleanValue selectFirstHit = BooleanValue.create(this, "Select first hit", false, "Waits for the opponent to hit you first before attacking");
    public final BooleanValue requireMouseDown = BooleanValue.create(this, "Require mouse down", false);
    public final BooleanValue ignoreActivationClick = BooleanValue.create(this, "Ignore activation click", false, "Ignores first manual click\n(unless already hovering a valid target with attack ready)");
    public final BooleanValue airCrits = BooleanValue.create(this, "Air crits", false, "Won't attack in air unless you will crit");
    public final BooleanValue shieldCheck = BooleanValue.create(this, "Shield check", false, "Won't attack players blocking with shield\nUsing ShieldBreaker will override this behavior");
    public final NumberValue targetMissChance = NumberValue.create(this, "Target miss chance", "#", "%", 0.0, 0.0, 100.0, 1.0, "Chance to attack without hovering a valid target when attack is ready");
    public final NumberValue earlyHitChance = NumberValue.create(this, "Early hit chance", "#", "%", 0.0, 0.0, 100.0, 1.0, "Chance to attack earlier than attack is ready");
    public final BooleanValue limitToItems = BooleanValue.create(this, "Limit to items", false, "Trigger functions only while holding selected items");
    public final LimitValue allowedItems = LimitValue.create((Object)this, "trigger-alloweditems", "Allowed Items", LimitValue.ALLOW_LIST_COLOR, Collections.emptyList());
    private boolean releasePending = false;
    private final TimerUtil activationTimer = new TimerUtil();
    private final TimerUtil mouseOverTimer = new TimerUtil();
    private final TimerUtil selectTimer = new TimerUtil();
    private int mouseOverTargetId = -1;
    private long mouseOverDelayMs = 0L;
    private int selectTargetId = -1;
    private int lastHurtTime = 0;
    private boolean waitingForFirstHit = false;
    private boolean graceActive = false;
    private final Random random = new Random();
    private boolean missRollResult = false;
    private boolean earlyHitCalculated = false;
    private boolean earlyHitActive = false;
    private int earlyHitTicks = 0;
    private double earlyHitPercent = 0.0;
    private float earlyHitThreshold = 1.0f;
    private boolean mouseDown = false;
    private boolean cancelActivationClick = false;

    public Triggerbot() {
        super("Triggerbot", -62703, Category.COMBAT, "");
        this.requireMouseDown.addDependentValues(this.ignoreActivationClick);
        this.limitToItems.addDependentValues(this.allowedItems);
        this.addValue(this.targetFilter, this.extraDelay, this.mouseOverDelay, this.requireMouseDown, this.ignoreActivationClick, this.airCrits, this.shieldCheck, this.selectFirstHit, this.targetMissChance, this.earlyHitChance, this.limitToItems, this.allowedItems);
    }

    private void calculateEarlyHit(float cooldownOffset) {
        if (this.earlyHitCalculated) {
            return;
        }
        this.earlyHitCalculated = true;
        int chance = (int)this.earlyHitChance.getValue().doubleValue();
        if (ForgeVersion.MC_1_12_2.v() || chance <= 0 || this.random.nextInt(100) >= chance) {
            this.earlyHitActive = false;
            this.earlyHitTicks = 0;
            this.earlyHitPercent = 0.0;
            this.earlyHitThreshold = 1.0f;
            return;
        }
        this.earlyHitActive = true;
        this.earlyHitPercent = 15.0 + this.random.nextDouble() * 25.0;
        this.earlyHitThreshold = (float)(1.0 - this.earlyHitPercent / 100.0);
        int ticksToFull = this.getTicksUntilCooldown(cooldownOffset, 1.0f);
        int ticksToEarly = this.getTicksUntilCooldown(cooldownOffset, this.earlyHitThreshold);
        this.earlyHitTicks = Math.max(0, ticksToFull - ticksToEarly);
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onTick(EventPreTick event) {
        if (event.getThePlayer().isNull()) {
            return;
        }
        if (this.cancelActivationClick) {
            Minecraft.gameSettings().F().onTick(0);
            this.cancelActivationClick = false;
        }
        int hurtTime = event.getThePlayer().c$src$I$15a9iwo();
        boolean wasJustHurt = this.lastHurtTime <= 0 && hurtTime > 0;
        this.lastHurtTime = hurtTime;
        if (this.requireMouseDown.getEffectiveValue().booleanValue() && this.mouseDown && !ClientSettings.isKeyBindingDown(Minecraft.gameSettings().F())) {
            this.mouseDown = false;
            this.cancelActivationClick = false;
            this.resetSelectState();
        }
        if (this.selectFirstHit.getEffectiveValue().booleanValue() && this.waitingForFirstHit && this.selectTargetId != -1 && wasJustHurt) {
            this.waitingForFirstHit = false;
        }
        if (!this.activationTimer.hasTimeElapsed(50L)) {
            return;
        }
        if (this.releasePending) {
            AutoMace autoMace = Vape.INSTANCE.getModManager().getMod(AutoMace.class);
            if (autoMace == null || !autoMace.isEnabled() || !autoMace.isWeaponSwapActive()) {
                AttackKeyController.releaseAttackKey();
            }
            this.releasePending = false;
            return;
        }
        if (!this.canAttack(true)) {
            return;
        }
        float cooldownOffset = (float)(-this.extraDelay.getRandomValue());
        RayTraceResult rayTrace = RotationManager.INSTANCE.getExtendedReachRayTrace();
        if (rayTrace.isNotNull() && rayTrace.getTypeOfHit().equals(RayTraceResult_type.entity())) {
            Entity target = rayTrace.getEntity();
            if (this.isValidTarget(target)) {
                if (!this.updateSelectFirstHit(target, wasJustHurt)) {
                    return;
                }
                if (!this.hasMouseOverDelayElapsed(target)) {
                    return;
                }
                if (this.isAttackReady(cooldownOffset)) {
                    AttackKeyController.releaseAttackKey();
                    this.releasePending = AttackKeyController.requestSyntheticAttack(this);
                    if (this.releasePending) {
                        this.resetEarlyHit();
                    }
                }
            } else {
                this.resetMouseOverState();
                this.updateSelectGrace();
            }
        } else {
            this.resetMouseOverState();
            this.updateSelectGrace();
            if (this.missRollResult && this.canMissAttack() && this.isAttackReady(cooldownOffset)) {
                AttackKeyController.releaseAttackKey();
                this.releasePending = AttackKeyController.requestSyntheticAttack(this);
                this.missRollResult = false;
                if (this.releasePending) {
                    this.resetEarlyHit();
                }
            }
        }
    }

    private boolean canUseHeldItem() {
        if (!this.limitToItems.getEffectiveValue().booleanValue()) {
            return true;
        }
        return this.allowedItems.isValid(Minecraft.thePlayer().getHeldItemHand(), false);
    }

    private boolean canAttack(boolean requireActivation) {
        if (Minecraft.currentScreen().isNotNull() || !InputEventDispatcher.getInstance().getFocusState().isFocused()) {
            return false;
        }
        if (requireActivation && (!InputEventDispatcher.getInstance().getFocusState().isFocused() || this.requireMouseDown.getEffectiveValue().booleanValue() && !this.mouseDown)) {
            return false;
        }
        if (!this.canUseHeldItem()) {
            return false;
        }
        return this.isAirCritSatisfied();
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onMouseButton(EventMouseButton event) {
        if (Minecraft.thePlayer().isNull()) {
            return;
        }
        if (!event.isKeybinding(Minecraft.gameSettings().F())) {
            return;
        }
        this.handleInput(event.getButtonState(), event);
    }

    private void handleInput(boolean pressed, Event event) {
        if (this.requireMouseDown.getEffectiveValue().booleanValue() && pressed && !this.mouseDown) {
            this.mouseDown = true;
            this.activationTimer.reset();
            this.missRollResult = false;
            this.resetEarlyHit();
            if (this.ignoreActivationClick.getEffectiveValue().booleanValue() && this.shouldIgnoreClick()) {
                this.cancelActivationClick = true;
                event.setCancelled(true);
            }
            return;
        }
        if (pressed) {
            this.activationTimer.reset();
            RayTraceResult rayTrace = RotationManager.INSTANCE.getExtendedReachRayTrace();
            boolean hasTarget = false;
            if (rayTrace.isNotNull() && rayTrace.getTypeOfHit().equals(RayTraceResult_type.entity())) {
                Entity target = rayTrace.getEntity();
                hasTarget = target.isNotNull() && this.isValidTarget(target);
            }
            int chance = (int)this.targetMissChance.getValue().doubleValue();
            this.missRollResult = hasTarget && chance > 0 && this.random.nextInt(100) < chance;
        }
    }

    private void resetSelectState() {
        this.selectTargetId = -1;
        this.waitingForFirstHit = false;
        this.graceActive = false;
        this.selectTimer.reset();
    }

    private boolean hasMouseOverDelayElapsed(Entity target) {
        int targetId = target.S();
        if (this.mouseOverTargetId != targetId) {
            this.mouseOverTargetId = targetId;
            this.mouseOverDelayMs = (long)this.mouseOverDelay.getRandomValue();
            this.mouseOverTimer.reset();
        }
        return this.mouseOverTimer.hasTimeElapsed(this.mouseOverDelayMs);
    }

    private void resetEarlyHit() {
        this.earlyHitCalculated = false;
        this.earlyHitActive = false;
        this.earlyHitTicks = 0;
        this.earlyHitPercent = 0.0;
        this.earlyHitThreshold = 1.0f;
    }

    private boolean shouldIgnoreClick() {
        return this.canAttackBasic() && !this.isEarlyAttackReady();
    }

    private int getTicksUntilCooldown(float offset, float targetStrength) {
        for (int ticks = 0; ticks <= 60; ++ticks) {
            if (!(Minecraft.thePlayer().getCooledAttackStrength(offset + (float)ticks) >= targetStrength)) continue;
            return ticks;
        }
        return 0;
    }

    private void resetMouseOverState() {
        this.mouseOverTargetId = -1;
        this.mouseOverDelayMs = 0L;
        this.mouseOverTimer.reset();
    }

    private boolean isMaceFalling() {
        if (!RotationUtil.u(Minecraft.thePlayer())) {
            return false;
        }
        AutoMace autoMace = Vape.INSTANCE.getModManager().getMod(AutoMace.class);
        if (autoMace != null && autoMace.isEnabled() && autoMace.hasReadyMace()) {
            return true;
        }
        ItemStack heldItem = Minecraft.thePlayer().i(EnumHand.mainHand());
        return heldItem.isNotNull() && heldItem.getItem().isNotNull() && MappedClasses.zx != null && heldItem.getItem().isInstance(MappedClasses.zx) && RotationUtil.u(Minecraft.thePlayer());
    }

    private boolean canMissAttack() {
        return this.selectFirstHit.getEffectiveValue() == false || this.selectTargetId == -1 || !this.waitingForFirstHit;
    }

    private void updateSelectGrace() {
        if (!this.selectFirstHit.getEffectiveValue().booleanValue() || this.selectTargetId == -1) {
            return;
        }
        if (!this.graceActive) {
            this.graceActive = true;
            this.selectTimer.reset();
            return;
        }
        if (this.selectTimer.hasTimeElapsed(1000L)) {
            this.resetSelectState();
        }
    }

    private boolean canAttackBasic() {
        return Minecraft.currentScreen().isNull() && InputEventDispatcher.getInstance().getFocusState().isFocused() && this.canUseHeldItem();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.mouseDown = false;
        this.cancelActivationClick = false;
        this.missRollResult = false;
        this.resetEarlyHit();
        this.resetMouseOverState();
        this.resetSelectState();
        if (this.releasePending) {
            AttackKeyController.releaseAttackKey();
            this.releasePending = false;
        }
    }

    private boolean isHoveringValidTarget() {
        RayTraceResult rayTrace = RotationManager.INSTANCE.getExtendedReachRayTrace();
        if (rayTrace.isNull() || !rayTrace.getTypeOfHit().equals(RayTraceResult_type.entity())) {
            return false;
        }
        Entity target = rayTrace.getEntity();
        return target.isNotNull() && this.isValidTarget(target);
    }

    private boolean isAttackReady(float cooldownOffset) {
        this.calculateEarlyHit(cooldownOffset);
        if (this.isMaceFalling()) {
            return true;
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            float strength = Minecraft.thePlayer().getCooledAttackStrength(cooldownOffset);
            return this.earlyHitActive ? strength >= this.earlyHitThreshold : AttackCooldownUtil.isAttackReady(cooldownOffset);
        }
        return AttackCooldownUtil.isAttackReady(cooldownOffset);
    }

    private boolean isAirCritSatisfied() {
        if (!this.airCrits.getEffectiveValue().booleanValue()) {
            return true;
        }
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.b$src$Z$fqlxe4()) {
            return true;
        }
        double deltaY = player.N() - player.W();
        return deltaY < 0.0;
    }

    private boolean isEarlyAttackReady() {
        return this.isAirCritSatisfied() && this.isAttackReady(-this.extraDelay.getMinimumInt()) && this.isHoveringValidTarget();
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onKeyPress(EventKeyPress event) {
        if (Minecraft.thePlayer().isNull()) {
            return;
        }
        if (!event.isKeybinding(Minecraft.gameSettings().F())) {
            return;
        }
        this.handleInput(event.isDown(), event);
    }

    private boolean isValidTarget(Entity entity) {
        if (this.shieldCheck.getEffectiveValue().booleanValue()) {
            EntityOtherPlayerMP targetPlayer;
            AutoMace autoMace;
            HitSwap hitSwap;
            boolean checkShield = true;
            ShieldBreaker shieldBreaker = Vape.INSTANCE.getModManager().getMod(ShieldBreaker.class);
            if (shieldBreaker != null && shieldBreaker.isEnabled() && shieldBreaker.hasAxeInHotbar()) {
                checkShield = false;
            }
            if ((hitSwap = Vape.INSTANCE.getModManager().getMod(HitSwap.class)) != null && hitSwap.isEnabled() && hitSwap.hasAlternateAxe()) {
                checkShield = false;
            }
            if ((autoMace = Vape.INSTANCE.getModManager().getMod(AutoMace.class)) != null && autoMace.isEnabled() && autoMace.canHandleShieldAttack()) {
                checkShield = false;
            }
            if (checkShield && (entity.isInstance(MappedClasses.lG) || entity.isInstance(MappedClasses.Yl)) && RotationUtil.isBlockingShield(targetPlayer = new EntityOtherPlayerMP(entity.getObject()))) {
                return false;
            }
        }
        return this.targetFilter.isValidTarget(entity);
    }

    private boolean updateSelectFirstHit(Entity target, boolean wasJustHurt) {
        if (!this.selectFirstHit.getEffectiveValue().booleanValue()) {
            this.resetSelectState();
            return true;
        }
        int targetId = target.S();
        if (this.selectTargetId != targetId) {
            this.selectTargetId = targetId;
            this.waitingForFirstHit = true;
        }
        this.graceActive = false;
        this.selectTimer.reset();
        if (this.waitingForFirstHit && wasJustHurt) {
            this.waitingForFirstHit = false;
        }
        return !this.waitingForFirstHit;
    }
}

