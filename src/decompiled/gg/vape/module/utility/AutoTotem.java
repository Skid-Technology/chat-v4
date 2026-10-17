/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.utility;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventClickMouse;
import gg.vape.event.impl.EventPacketReceive;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.event.impl.EventRightClickMouse;
import gg.vape.event.impl.EventSendClickBlockToController;
import gg.vape.event.impl.EventThreadBoundPostTick;
import gg.vape.event.impl.EventThreadBoundPreTick;
import gg.vape.event.impl.EventWindowClick;
import gg.vape.input.KeyBindingHelper;
import gg.vape.inventory.InventoryClick;
import gg.vape.mapping.ItemMappingEntry;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.ModuleDisplayInfo;
import gg.vape.module.control.SharedModuleControlClaims;
import gg.vape.module.none.ClientSettings;
import gg.vape.module.render.Freecam;
import gg.vape.module.utility.inventory.InventoryActionModule;
import gg.vape.rotation.AdaptiveRotationController;
import gg.vape.rotation.RotationControlClaim;
import gg.vape.rotation.RotationManager;
import gg.vape.ui.click.frame.impl.hud.ActiveModuleStackFrame;
import gg.vape.utils.TimerUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.RandomValue;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.GuiScreen;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Packet;
import gg.vape.wrapper.impl.SPacketEntityStatus;
import gg.vape.wrapper.impl.SPacketEntityVelocity;
import gg.vape.wrapper.impl.WorldClient;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AutoTotem
extends Mod
implements InventoryActionModule {
    private final TimerUtil clickTimer = new TimerUtil();
    private final TimerUtil delayTimer = new TimerUtil();
    private final TimerUtil actionTimer = new TimerUtil();
    private final Queue<InventoryClick> clickQueue = new ConcurrentLinkedQueue<InventoryClick>();
    private final BooleanValue openInventory;
    private final BooleanValue silentOpen;
    private final RandomValue silentMoveDelay;
    private final BooleanValue closeInventory;
    private final BooleanValue inventoryOnly;
    private final BooleanValue combatOnly;
    private final RandomValue activationDelay;
    private final BooleanValue refill;
    private final BooleanValue randomSlot;
    private final RandomValue delay;
    private final BooleanValue extraRandomization;
    private final BooleanValue showTotemCount;
    private final Random random = new Random();
    private final RotationManager rotationManager = RotationManager.INSTANCE;
    private final RotationControlClaim rotationClaim = SharedModuleControlClaims.rotation;
    private long delayDuration = -1L;
    private long clickDelay = -1L;
    private final float[] healthHistory = new float[80];
    private final long[] timeHistory = new long[80];
    private float lastTotalHealth = -1.0f;
    private final boolean[] hotbarTotems = new boolean[9];
    private int lastEmptiedHotbarSlot = -1;
    private int pendingPopRefillSlot = -1;
    private int pendingRefillHotbarSlot = -1;
    private int historyIndex = 0;
    private boolean inventoryOpen;
    private boolean silentActive;
    private boolean suppressInput;
    private boolean clickingSlot;
    private boolean closePending;
    private boolean combatActive;
    private boolean hadOffhandTotem;
    private boolean totemLostGraceActive;
    private boolean totemLostInCombat;
    private long totemLostTime = 0L;
    private long totemLostGraceDuration = 0L;
    private long damageGraceUntil = 0L;
    private long cooldownUntil = 0L;
    private long popGraceUntil = 0L;
    private long popCooldownUntil = 0L;
    private long activationStartTime = 0L;
    private long dangerConfirmedTime = 0L;
    private long totemPopTime = 0L;
    private long lastEmptiedTime = 0L;
    private long lastDamage05Time = 0L;
    private long lastDamage2Time = 0L;
    private long lastDamage6Time = 0L;
    private long lastCrystalNearbyTime = 0L;
    private long lastHostileNearbyTime = 0L;
    private long lastHighVelocityTime = 0L;
    private long lastEnvironmentDangerTime = 0L;
    private long lastCombatVelocityTime = 0L;
    private Object lastScreen;
    private AdaptiveRotationController rotationController;
    private static Freecam freecam;

    public AutoTotem() {
        super("AutoTotem", -43691, Category.INVENTORY, "Automatically equips totems to your offhand");
        this.openInventory = BooleanValue.create(this, "Open inventory", true, "Opens your inventory to equip a totem");
        this.silentOpen = BooleanValue.create(this, "Silent open", false, "Silently opens your inventory to equip a totem");
        this.silentMoveDelay = RandomValue.createWithDescription(this, "Silent move delay", "#", "ms", 50.0, 100.0, 120.0, 200.0, 1.0, "Delay before preventing movement keys after silently opening the inventory");
        this.closeInventory = BooleanValue.create(this, "Close inventory", true, "Closes your inventory after equipping a totem");
        this.inventoryOnly = BooleanValue.create(this, "Inventory only", false, "Only equips a totem when in your inventory");
        this.combatOnly = BooleanValue.create(this, "Combat only", true, "Only equips after recent combat-like danger");
        this.activationDelay = RandomValue.createWithDescription(this, "Activation delay", "#", "ms", 0.0, 1000.0, 1000.0, 3000.0, 50.0, "How long combat must be detected before AutoTotem becomes active");
        this.refill = BooleanValue.create(this, "Refill", false, "Refills a main hand totem after it is used");
        this.randomSlot = BooleanValue.create(this, "Random slot", true, "Chooses a random totem slot from your inventory");
        this.delay = RandomValue.createWithDescription(this, "Delay", "#", "ms", 50.0, 100.0, 120.0, 200.0, 1.0, "How long to wait before equipping a totem");
        this.extraRandomization = BooleanValue.create(this, "Extra randomization", true, "Adds human-like timing variance while equipping totems");
        this.showTotemCount = BooleanValue.create(this, "Show totem count", false, "Renders your totem count on the center of your screen");
        this.openInventory.addDependentValues(this.silentOpen, this.silentMoveDelay, this.closeInventory);
        this.silentOpen.addDependentValues(this.silentMoveDelay);
        this.combatOnly.addDependentValues(this.activationDelay);
        this.silentOpen.getDisabledCondition().applyTo(this.closeInventory);
        this.addValue(this.openInventory, this.silentOpen, this.silentMoveDelay, this.closeInventory, this.inventoryOnly, this.combatOnly, this.activationDelay, this.refill, this.randomSlot, this.delay, this.extraRandomization, this.showTotemCount);
        this.rotationClaim.setPriority(this, 99);
    }

    @Override
    public void onEnable() {
        ClientSettings.getFrame(ActiveModuleStackFrame.class).addModule(this);
        this.resetDelayTimer();
        this.resetClickTimer();
        this.resetCombatTracking();
        this.resetRefillTracking();
    }

    @Override
    public void onDisable() {
        ClientSettings.getFrame(ActiveModuleStackFrame.class).removeModule(this);
        if (this.inventoryOpen && this.silentActive) {
            this.closeInventory();
        }
        if (this.suppressInput) {
            this.unblockMovementKeys(Minecraft.gameSettings());
            this.suppressInput = false;
        }
        this.closePending = false;
        this.inventoryOpen = false;
        this.lastScreen = null;
        this.delayDuration = -1L;
        this.clickDelay = -1L;
        this.resetCombatTracking();
        this.resetRefillTracking();
    }

    @Override
    public boolean isPerformingInventoryAction() {
        return this.isEnabled() && !this.clickQueue.isEmpty();
    }

    @Override
    public String getDetailedSuffix() {
        if (!this.combatOnly.getEffectiveValue().booleanValue()) {
            return super.getDetailedSuffix();
        }
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return "Inactive";
        }
        return this.isCombatActive() ? "\u00a76Active" : "Inactive";
    }

    private boolean isCombatActive() {
        long now = System.currentTimeMillis();
        if (now < this.cooldownUntil) {
            return false;
        }
        if (this.totemLostGraceActive) {
            return this.totemLostInCombat;
        }
        return this.combatActive;
    }

    @Override
    public ModuleDisplayInfo getModuleDisplayInfo() {
        if (!this.showTotemCount.getEffectiveValue().booleanValue()) {
            return null;
        }
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return null;
        }
        int count = this.countTotems(player);
        Color color = new Color(255, 20, 20);
        if (count >= 4) {
            color = new Color(2, 190, 58);
        } else if (count >= 2) {
            color = new Color(255, 249, 18);
        }
        return new ModuleDisplayInfo(String.valueOf(count), color);
    }

    private int countTotems(EntityPlayerSP player) {
        int count = 0;
        for (int i = 9; i <= 45; ++i) {
            ItemStack stack = player.getInventoryContainer().getSlot(i).getStack();
            if (stack.isNull() || !this.isTotem(stack)) continue;
            count += stack.getStackSize();
        }
        return count;
    }

    private boolean isTotem(ItemStack stack) {
        if (stack.isNull()) {
            return false;
        }
        return this.isTotem(Vape.INSTANCE.getItemStackResolver().resolve(stack));
    }

    private boolean isTotem(ItemMappingEntry entry) {
        return entry != null && entry.getResourceKey().toLowerCase().contains("totem_of_undying");
    }

    private long randomRange(long min, long max) {
        return min + (long)(this.random.nextDouble() * (double)(max - min));
    }

    private void queueClick(int windowId, int slotId, int mouseButton, int clickMode) {
        this.clickQueue.add(new InventoryClick(windowId, slotId, mouseButton, clickMode));
    }

    private double computeDelay() {
        if (!this.extraRandomization.getEffectiveValue().booleanValue()) {
            return this.delay.getRandomValue();
        }
        double min = this.delay.getMinimum();
        double max = this.delay.getMaximum();
        double spread = Math.max(1.0, max - min);
        double mid = min + spread * 0.5;
        double stdDev = Math.max(1.0, spread / 4.0);
        double val = mid + this.random.nextGaussian() * stdDev;
        val = Math.max(min, Math.min(max, val));
        if (this.random.nextDouble() < 0.18) {
            double extraMin = Math.max(120.0, spread * 1.25);
            double extraMax = Math.max(350.0, spread * 3.25);
            val += extraMin + (extraMax - extraMin) * this.random.nextDouble();
        }
        return val;
    }

    private void resetDelayTimer() {
        this.delayTimer.reset();
        this.delayDuration = Math.max(1L, (long)this.computeDelay());
    }

    private void resetClickTimer() {
        this.clickTimer.reset();
        this.clickDelay = Math.max(1L, (long)this.computeDelay());
    }

    private boolean isDelayElapsed() {
        if (this.delayDuration <= 0L) {
            this.delayDuration = Math.max(1L, (long)this.computeDelay());
        }
        return this.delayTimer.hasTimeElapsed(this.delayDuration);
    }

    private boolean isClickDelayElapsed() {
        if (this.clickDelay <= 0L) {
            this.clickDelay = Math.max(1L, (long)this.computeDelay());
        }
        return this.clickTimer.hasTimeElapsed(this.clickDelay);
    }

    private void resetRefillTracking() {
        for (int i = 0; i < this.hotbarTotems.length; ++i) {
            this.hotbarTotems[i] = false;
        }
        this.lastEmptiedHotbarSlot = -1;
        this.lastEmptiedTime = 0L;
        this.totemPopTime = 0L;
        this.pendingPopRefillSlot = -1;
        this.pendingRefillHotbarSlot = -1;
    }

    private void resetCombatTracking() {
        this.historyIndex = 0;
        this.lastTotalHealth = -1.0f;
        this.lastDamage05Time = 0L;
        this.lastDamage2Time = 0L;
        this.lastDamage6Time = 0L;
        this.lastCrystalNearbyTime = 0L;
        this.lastHostileNearbyTime = 0L;
        this.lastHighVelocityTime = 0L;
        this.lastEnvironmentDangerTime = 0L;
        this.lastCombatVelocityTime = 0L;
        this.activationStartTime = 0L;
        this.dangerConfirmedTime = 0L;
        this.combatActive = false;
        this.hadOffhandTotem = false;
        this.totemLostGraceActive = false;
        this.totemLostTime = 0L;
        this.totemLostGraceDuration = 0L;
        this.totemLostInCombat = false;
        this.damageGraceUntil = 0L;
        this.cooldownUntil = 0L;
        this.popGraceUntil = 0L;
        this.popCooldownUntil = 0L;
        for (int i = 0; i < 80; ++i) {
            this.healthHistory[i] = -1.0f;
            this.timeHistory[i] = 0L;
        }
    }

    private void resetHealthHistory(EntityPlayerSP player) {
        this.lastDamage05Time = 0L;
        this.lastDamage2Time = 0L;
        this.lastDamage6Time = 0L;
        this.historyIndex = 0;
        for (int i = 0; i < 80; ++i) {
            this.healthHistory[i] = -1.0f;
            this.timeHistory[i] = 0L;
        }
        this.lastTotalHealth = player.getHealth() + player.getAbsorption();
    }

    private void resetDangerTimestamps() {
        this.lastCrystalNearbyTime = 0L;
        this.lastHostileNearbyTime = 0L;
        this.lastHighVelocityTime = 0L;
        this.lastEnvironmentDangerTime = 0L;
        this.lastCombatVelocityTime = 0L;
    }

    private boolean isSilentOpenBlocked() {
        if (freecam == null) {
            freecam = Vape.INSTANCE.getModManager().getMod(Freecam.class);
        }
        return freecam != null && freecam.isEnabled() || this.rotationClaim.isBlockedFor(this) && !this.rotationClaim.acquire(this, true);
    }

    private void openInventory() {
        if (this.silentOpen.getEffectiveValue().booleanValue()) {
            if (this.isSilentOpenBlocked()) {
                return;
            }
            this.silentActive = true;
            this.suppressInput = true;
            this.actionTimer.reset();
            this.rotationController = new AdaptiveRotationController();
            this.rotationController.speed = 0.0f;
            this.rotationManager.setController(this.rotationController);
        } else {
            KeyBinding inventoryKey = Minecraft.gameSettings().getInventoryKey();
            if (ForgeVersion.MC_1_16_5.d()) {
                KeyBindingHelper.incrementPressTime(inventoryKey);
            } else {
                KeyBindingHelper.setPressedAndTick(inventoryKey, true);
                KeyBindingHelper.updateKeyBinding(inventoryKey, false, false);
            }
        }
        this.inventoryOpen = true;
        this.resetDelayTimer();
    }

    private void closeInventory() {
        GuiScreen currentScreen = Minecraft.currentScreen();
        EntityPlayerSP player = Minecraft.thePlayer();
        if (this.inventoryOpen) {
            if (this.silentActive) {
                this.silentActive = false;
                if (this.rotationController != null) {
                    this.rotationController.speed = 3.0f;
                    this.rotationController.setComplete(true);
                    this.rotationController.setRandomizeMovement(true);
                    this.rotationController.setLinearAcceleration(true);
                    this.rotationManager.releaseController(this.rotationController);
                    this.rotationController = null;
                }
                this.rotationClaim.release(this);
                player.closeScreen();
            } else if (this.closeInventory.getEffectiveValue().booleanValue() && currentScreen.isNotNull()) {
                player.closeScreen();
            }
        }
        this.inventoryOpen = false;
        this.closePending = false;
    }

    private void trackScreenChange() {
        Object currentObj = Minecraft.currentScreen().getObject();
        if (currentObj != this.lastScreen) {
            this.resetDelayTimer();
            if (this.isInventoryScreen(this.lastScreen) && !this.isInventoryScreen(currentObj) && !this.silentActive) {
                this.inventoryOpen = false;
                this.closePending = false;
                this.clickQueue.clear();
            }
        }
        this.lastScreen = currentObj;
    }

    private boolean isInventoryScreen(Object object) {
        if (object == null) {
            return false;
        }
        GuiScreen screen = new GuiScreen(object);
        return screen.isInstance(MappedClasses.YS) || screen.isInstance(MappedClasses.n);
    }

    private void blockMovementKeys(GameSettings gameSettings) {
        if (this.suppressInput) {
            KeyBindingHelper.setPressedAndTick(gameSettings.getForwardKey(), false);
            KeyBindingHelper.setPressedAndTick(gameSettings.getBackKey(), false);
            KeyBindingHelper.setPressedAndTick(gameSettings.getLeftKey(), false);
            KeyBindingHelper.setPressedAndTick(gameSettings.getRightKey(), false);
            KeyBindingHelper.setPressedAndTick(gameSettings.getJumpKey(), false);
            KeyBindingHelper.setPressedAndTick(gameSettings.getSneakKey(), false);
        }
    }

    private void unblockMovementKeys(GameSettings gameSettings) {
        if (this.suppressInput) {
            KeyBindingHelper.updateKeyBinding(gameSettings.getForwardKey(), gg.vape.config.ClientSettings.isPhysicalKeyDown(gameSettings.getForwardKey()), true);
            KeyBindingHelper.updateKeyBinding(gameSettings.getBackKey(), gg.vape.config.ClientSettings.isPhysicalKeyDown(gameSettings.getBackKey()), true);
            KeyBindingHelper.updateKeyBinding(gameSettings.getLeftKey(), gg.vape.config.ClientSettings.isPhysicalKeyDown(gameSettings.getLeftKey()), true);
            KeyBindingHelper.updateKeyBinding(gameSettings.getRightKey(), gg.vape.config.ClientSettings.isPhysicalKeyDown(gameSettings.getRightKey()), true);
            KeyBindingHelper.updateKeyBinding(gameSettings.getJumpKey(), gg.vape.config.ClientSettings.isPhysicalKeyDown(gameSettings.getJumpKey()), true);
            KeyBindingHelper.updateKeyBinding(gameSettings.getSneakKey(), gg.vape.config.ClientSettings.isPhysicalKeyDown(gameSettings.getSneakKey()), true);
        }
    }

    private int findTotemSlot(int excludedSlot) {
        ArrayList<Integer> candidateSlots;
        EntityPlayerSP player = Minecraft.thePlayer();
        ArrayList<Integer> inventorySlots = new ArrayList<Integer>();
        ArrayList<Integer> hotbarSlots = new ArrayList<Integer>();
        for (int i = 9; i < 45; ++i) {
            ItemStack stack;
            if (i == excludedSlot || (stack = player.getInventoryContainer().getSlot(i).getStack()).isNull() || !this.isTotem(stack)) continue;
            if (!this.randomSlot.getEffectiveValue().booleanValue()) {
                return i;
            }
            if (i < 36) {
                inventorySlots.add(i);
                continue;
            }
            hotbarSlots.add(i);
        }
        ArrayList<Integer> arrayList = candidateSlots = inventorySlots.isEmpty() ? hotbarSlots : inventorySlots;
        if (candidateSlots.isEmpty()) {
            return -1;
        }
        return (Integer)candidateSlots.get(this.random.nextInt(candidateSlots.size()));
    }

    private int findTotemForOffhand() {
        return this.findTotemSlot(-1);
    }

    private void recordHealthHistory(long now, float totalHealth) {
        this.healthHistory[this.historyIndex] = totalHealth;
        this.timeHistory[this.historyIndex] = now;
        this.historyIndex = (this.historyIndex + 1) % 80;
        this.lastTotalHealth = totalHealth;
    }

    private int getDangerScore(long now) {
        int score = 0;
        if (now - this.lastHostileNearbyTime <= 3000L) {
            score += 2;
        }
        if (now - this.lastCrystalNearbyTime <= 5000L) {
            score += 2;
        }
        if (now - this.lastHighVelocityTime <= 2500L) {
            ++score;
        }
        if (now - this.lastEnvironmentDangerTime <= 3000L) {
            score += 2;
        }
        if (now - this.lastCombatVelocityTime <= 6000L) {
            score += 4;
        }
        return score;
    }

    private boolean isDangerConditionMet(EntityPlayerSP player, long now) {
        boolean damage6;
        int score = this.getDangerScore(now);
        if (score >= 4) {
            return true;
        }
        boolean damage2 = this.lastDamage2Time > 0L && now - this.lastDamage2Time >= 3000L && now - this.lastDamage2Time <= 8000L;
        boolean bl = damage6 = this.lastDamage6Time > 0L && now - this.lastDamage6Time >= 3000L && now - this.lastDamage6Time <= 12000L;
        if ((damage2 || damage6) && score >= 2) {
            return true;
        }
        return player.getHealth() + player.getAbsorption() <= 10.0f && score >= 3;
    }

    private void handleCriticalHealthDrop(EntityPlayerSP player, long now) {
        this.cooldownUntil = now + 6000L;
        this.popGraceUntil = now + 15000L;
        this.damageGraceUntil = Math.max(this.damageGraceUntil, now + 3000L);
        this.popCooldownUntil = Math.max(this.popCooldownUntil, this.popGraceUntil);
        this.totemLostGraceActive = false;
        this.totemLostInCombat = false;
        this.updateCombatActive(now, false);
        this.resetDangerTimestamps();
        this.resetHealthHistory(player);
    }

    private void applyCombatCooldowns(EntityPlayerSP player, long now) {
        this.cooldownUntil = now + 6000L;
        this.popGraceUntil = now + 15000L;
        this.damageGraceUntil = Math.max(this.damageGraceUntil, now + 1500L);
        this.popCooldownUntil = Math.max(this.popCooldownUntil, this.popGraceUntil);
        this.updateCombatActive(now, false);
        this.resetDangerTimestamps();
        this.resetHealthHistory(player);
    }

    private void trackHealthChange(EntityPlayerSP player) {
        long now = System.currentTimeMillis();
        float totalHealth = player.getHealth() + player.getAbsorption();
        if (now < this.damageGraceUntil || now < this.popGraceUntil) {
            this.recordHealthHistory(now, totalHealth);
            return;
        }
        if (this.lastTotalHealth >= 0.0f) {
            float delta = this.lastTotalHealth - totalHealth;
            int score = this.getDangerScore(now);
            if (totalHealth <= 1.0f && delta >= 4.0f && score < 2) {
                this.handleCriticalHealthDrop(player, now);
                this.recordHealthHistory(now, totalHealth);
                return;
            }
            if (delta >= 0.5f) {
                this.lastDamage05Time = now;
            }
            if (delta >= 2.0f) {
                this.lastDamage2Time = now;
            }
            if (delta >= 6.0f) {
                this.lastDamage6Time = now;
            }
        }
        this.recordHealthHistory(now, totalHealth);
    }

    private long getActivationDelay() {
        return (long)this.activationDelay.getMaximum();
    }

    private void updateDangerConfirmedTime(long now) {
        if (this.activationStartTime == 0L) {
            return;
        }
        long maxRecent = Math.max(this.lastCombatVelocityTime, Math.max(this.lastDamage2Time, this.lastDamage6Time));
        maxRecent = Math.max(maxRecent, this.lastCrystalNearbyTime);
        maxRecent = Math.max(maxRecent, this.lastHighVelocityTime);
        if ((maxRecent = Math.max(maxRecent, this.lastEnvironmentDangerTime)) - this.activationStartTime >= 150L && maxRecent > this.dangerConfirmedTime) {
            this.dangerConfirmedTime = maxRecent;
        }
    }

    private void updateCombatActive(long now, boolean dangerMet) {
        if (now < this.popCooldownUntil || now < this.popGraceUntil) {
            this.activationStartTime = 0L;
            this.dangerConfirmedTime = 0L;
            this.combatActive = false;
            return;
        }
        if (!dangerMet) {
            this.activationStartTime = 0L;
            this.dangerConfirmedTime = 0L;
            this.combatActive = false;
            return;
        }
        if (this.activationStartTime == 0L) {
            this.activationStartTime = now;
            this.dangerConfirmedTime = 0L;
            this.combatActive = this.getActivationDelay() <= 0L;
            return;
        }
        this.updateDangerConfirmedTime(now);
        if (now - this.activationStartTime >= this.getActivationDelay()) {
            if (this.dangerConfirmedTime > this.activationStartTime) {
                this.combatActive = true;
            } else {
                this.activationStartTime = 0L;
                this.dangerConfirmedTime = 0L;
                this.combatActive = false;
            }
        }
    }

    private void checkDangerSources(EntityPlayerSP player) {
        WorldClient world;
        double velocity;
        long now = System.currentTimeMillis();
        if (player.isBurning() || player.isInLava() || !player.onGround() && player.getFallDistance() > 6.0f) {
            this.lastEnvironmentDangerTime = now;
        }
        if ((velocity = Math.abs(player.getMotionX()) + Math.abs(player.getMotionY()) + Math.abs(player.getMotionZ())) > 0.45) {
            this.lastHighVelocityTime = now;
        }
        if ((world = Minecraft.theWorld()).isNull()) {
            return;
        }
        ArrayList loadedEntities = new ArrayList(world.getEntityList());
        for (Object obj : loadedEntities) {
            Entity entity = new Entity(obj);
            if (entity.isNull() || entity.isDead() || entity.getEntityId() == player.getEntityId()) continue;
            float dist = player.getDistanceToEntity(entity);
            if (entity.isInstance(MappedClasses.Ze) && dist <= 10.0f) {
                this.lastCrystalNearbyTime = now;
                continue;
            }
            if (entity.isInstance(MappedClasses.Yl)) {
                if (!(dist <= 8.0f) || Vape.INSTANCE.getFriendManager().isFriend(new EntityPlayer(entity.getObject()))) continue;
                this.lastHostileNearbyTime = now;
                continue;
            }
            if (!entity.isInstance(MappedClasses.zm) || !entity.isInstance(MappedClasses.Y3) || !(dist <= 6.0f)) continue;
            this.lastHostileNearbyTime = now;
        }
    }

    private void updateCombatTracking(EntityPlayerSP player) {
        this.trackHealthChange(player);
        if (!this.combatOnly.getEffectiveValue().booleanValue()) {
            return;
        }
        long now = System.currentTimeMillis();
        if (now < this.popCooldownUntil) {
            this.updateCombatActive(now, false);
            return;
        }
        this.updateCombatActive(now, now >= this.cooldownUntil && this.isDangerConditionMet(player, now));
    }

    private boolean updateAndCheckCombat(EntityPlayerSP player, long now) {
        boolean dangerMet = this.isDangerConditionMet(player, now);
        this.updateCombatActive(now, dangerMet);
        return this.combatActive;
    }

    private void updateOffhandTracking(EntityPlayerSP player, boolean hasOffhandTotem) {
        if (hasOffhandTotem) {
            this.hadOffhandTotem = true;
            this.totemLostGraceActive = false;
            return;
        }
        if (this.hadOffhandTotem) {
            long now = System.currentTimeMillis();
            this.totemLostGraceActive = true;
            this.totemLostTime = now;
            this.totemLostInCombat = this.updateAndCheckCombat(player, now);
            this.totemLostGraceDuration = this.totemLostInCombat ? this.randomRange(150L, 350L) : this.randomRange(1800L, 2600L);
            this.damageGraceUntil = now + 1500L;
            if (!this.totemLostInCombat) {
                this.totemLostGraceActive = false;
                this.applyCombatCooldowns(player, now);
                this.hadOffhandTotem = false;
                return;
            }
        }
        this.hadOffhandTotem = false;
    }

    private boolean evaluateCombatAction(EntityPlayerSP player) {
        long now = System.currentTimeMillis();
        if (now < this.cooldownUntil) {
            return false;
        }
        if (!this.totemLostGraceActive) {
            return this.updateAndCheckCombat(player, now);
        }
        if (now - this.totemLostTime < this.totemLostGraceDuration) {
            return false;
        }
        this.totemLostGraceActive = false;
        if (this.totemLostInCombat) {
            return true;
        }
        this.applyCombatCooldowns(player, now);
        return false;
    }

    private boolean canPerformActionInCombat(EntityPlayerSP player) {
        return this.evaluateCombatAction(player);
    }

    private void trackHotbarTotems(EntityPlayerSP player) {
        ItemStack stack;
        if (!this.refill.getEffectiveValue().booleanValue()) {
            this.resetRefillTracking();
            return;
        }
        long now = System.currentTimeMillis();
        for (int i = 0; i < this.hotbarTotems.length; ++i) {
            int slot = 36 + i;
            boolean hasTotem = this.isTotem(player.getInventoryContainer().getSlot(slot).getStack());
            if (this.hotbarTotems[i] && !hasTotem) {
                this.lastEmptiedHotbarSlot = slot;
                this.lastEmptiedTime = now;
            }
            this.hotbarTotems[i] = hasTotem;
        }
        if (this.pendingPopRefillSlot == -1 && this.totemPopTime != 0L && this.lastEmptiedHotbarSlot != -1 && Math.abs(this.lastEmptiedTime - this.totemPopTime) <= 1500L) {
            this.pendingPopRefillSlot = this.lastEmptiedHotbarSlot;
        }
        if (this.pendingPopRefillSlot != -1 && !this.isTotem(stack = player.getInventoryContainer().getSlot(this.pendingPopRefillSlot).getStack())) {
            if (stack.isNull()) {
                this.pendingRefillHotbarSlot = this.pendingPopRefillSlot;
            }
            this.pendingPopRefillSlot = -1;
            this.totemPopTime = 0L;
        }
        if (this.totemPopTime != 0L && now - this.totemPopTime > 1500L) {
            this.totemPopTime = 0L;
            this.pendingPopRefillSlot = -1;
        }
    }

    private boolean handleRefill(EntityPlayerSP player, GuiScreen currentScreen, boolean isInvOpen, boolean isSilentInvOpen) {
        if (this.pendingRefillHotbarSlot == -1) {
            return false;
        }
        ItemStack slotStack = player.getInventoryContainer().getSlot(this.pendingRefillHotbarSlot).getStack();
        if (!slotStack.isNull()) {
            this.pendingRefillHotbarSlot = -1;
            return false;
        }
        if (this.combatOnly.getEffectiveValue().booleanValue() && !this.canPerformActionInCombat(player)) {
            return false;
        }
        int totemSlot = this.findTotemSlot(this.pendingRefillHotbarSlot);
        if (totemSlot == -1) {
            this.pendingRefillHotbarSlot = -1;
            return false;
        }
        if (!(!this.openInventory.getEffectiveValue().booleanValue() || currentScreen.isNotNull() && currentScreen.isInstance(MappedClasses.YS) || isSilentInvOpen)) {
            this.openInventory();
            return true;
        }
        if (isInvOpen && !this.inventoryOpen) {
            this.inventoryOpen = true;
        }
        int windowId = player.getInventoryContainer().getWindowId();
        int hotbarIndex = this.pendingRefillHotbarSlot - 36;
        this.queueClick(windowId, totemSlot, hotbarIndex, 2);
        this.pendingRefillHotbarSlot = -1;
        this.resetClickTimer();
        return true;
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onSendClickBlock(EventSendClickBlockToController event) {
        if (this.suppressInput) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onThreadBoundPreTick(EventThreadBoundPreTick event) {
        if (this.suppressInput) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onThreadBoundPostTick(EventThreadBoundPostTick event) {
        if (this.suppressInput) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onRightClickMouse(EventRightClickMouse event) {
        if (this.suppressInput) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onClickMouse(EventClickMouse event) {
        if (this.suppressInput) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onWindowClick(EventWindowClick event) {
        boolean inInv;
        GuiScreen currentScreen = event.getCurrentScreen();
        boolean bl = inInv = this.inventoryOpen && this.silentActive || currentScreen.isNotNull() && currentScreen.isInstance(MappedClasses.YS);
        if (inInv && !this.clickingSlot && this.inventoryOpen) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPacketReceive(EventPacketReceive event) {
        if (!this.combatOnly.getEffectiveValue().booleanValue() && !this.refill.getEffectiveValue().booleanValue()) {
            return;
        }
        Packet packet = event.getPacket();
        if (packet == null || packet.isNull()) {
            return;
        }
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return;
        }
        this.handleStatusPacket(packet, player);
        if (this.combatOnly.getEffectiveValue().booleanValue()) {
            this.handleVelocityPacket(packet, player);
        }
    }

    private void handleStatusPacket(Packet packet, EntityPlayerSP player) {
        if (!this.refill.getEffectiveValue().booleanValue() || !packet.isInstance(MappedClasses.lU)) {
            return;
        }
        SPacketEntityStatus statusPacket = new SPacketEntityStatus(packet.getObject());
        if (statusPacket.getEntityId() != player.getEntityId() || statusPacket.getLogicOpcode() != 35) {
            return;
        }
        long now = System.currentTimeMillis();
        int currentItem = player.getInventory().getCurrentItem();
        int hotbarSlot = 36 + currentItem;
        boolean hasTotem = this.isTotem(player.getInventoryContainer().getSlot(hotbarSlot).getStack());
        this.totemPopTime = now;
        if (hasTotem || this.hotbarTotems[currentItem]) {
            this.pendingPopRefillSlot = hotbarSlot;
        } else if (this.lastEmptiedHotbarSlot != -1 && now - this.lastEmptiedTime <= 1500L) {
            this.pendingPopRefillSlot = this.lastEmptiedHotbarSlot;
        }
    }

    private void handleVelocityPacket(Packet packet, EntityPlayerSP player) {
        if (!packet.isInstance(MappedClasses.YX)) {
            return;
        }
        SPacketEntityVelocity velocityPacket = new SPacketEntityVelocity(packet.getObject());
        if (velocityPacket.getEntityId() != player.getEntityId()) {
            return;
        }
        if (velocityPacket.getMotionX() == 0 && velocityPacket.getMotionY() == 0 && velocityPacket.getMotionZ() == 0) {
            return;
        }
        long now = System.currentTimeMillis();
        if (now < this.popGraceUntil || now < this.popCooldownUntil) {
            return;
        }
        this.lastCombatVelocityTime = now;
    }

    @EventHandler
    public void onTick(EventPrePlayerTick event) {
        boolean inInventoryScreen;
        boolean silentEquipping;
        if (this.suppressInput) {
            if (this.actionTimer.hasTimeElapsed((long)this.silentMoveDelay.getRandomValue())) {
                this.unblockMovementKeys(event.getGameSettings());
                this.suppressInput = false;
            } else {
                this.blockMovementKeys(event.getGameSettings());
            }
        }
        if (Vape.INSTANCE.getModManager().isOtherInventoryActionActive(AutoTotem.class) || Vape.INSTANCE.getClientSettings().isLobbyCheckActive()) {
            this.clickQueue.clear();
            return;
        }
        this.trackScreenChange();
        EntityPlayerSP player = event.getThePlayer();
        if (player.isNull() || player.isDead()) {
            if (this.inventoryOpen && this.silentActive) {
                this.closeInventory();
            }
            this.clickingSlot = false;
            this.resetCombatTracking();
            this.resetRefillTracking();
            return;
        }
        this.trackHotbarTotems(player);
        this.checkDangerSources(player);
        if (!player.getOpenContainer().isNull() && player.getOpenContainer().getWindowId() != 0) {
            this.updateCombatTracking(player);
            return;
        }
        GuiScreen currentScreen = Minecraft.currentScreen();
        boolean bl = silentEquipping = this.inventoryOpen && this.silentActive;
        if (silentEquipping) {
            this.actionTimer.reset();
        }
        boolean bl2 = inInventoryScreen = silentEquipping || currentScreen.isNotNull() && (currentScreen.isInstance(MappedClasses.YS) || currentScreen.isInstance(MappedClasses.n));
        if (this.openInventory.getEffectiveValue().booleanValue() && !inInventoryScreen) {
            this.clickQueue.clear();
        }
        if (this.inventoryOnly.getEffectiveValue().booleanValue() && !inInventoryScreen) {
            this.clickQueue.clear();
            this.updateCombatTracking(player);
            return;
        }
        if (!this.isDelayElapsed()) {
            this.updateCombatTracking(player);
            return;
        }
        if (!this.clickQueue.isEmpty()) {
            if (this.isClickDelayElapsed()) {
                InventoryClick inventoryClick = this.clickQueue.poll();
                if (inventoryClick != null) {
                    this.clickingSlot = true;
                    inventoryClick.execute();
                    this.clickingSlot = false;
                }
                this.actionTimer.reset();
                this.resetClickTimer();
            }
            this.updateCombatTracking(player);
            return;
        }
        if (this.closePending) {
            this.closeInventory();
            this.updateCombatTracking(player);
            return;
        }
        if (this.handleRefill(player, currentScreen, inInventoryScreen, silentEquipping)) {
            this.updateCombatTracking(player);
            return;
        }
        ItemStack offhandStack = player.getInventoryContainer().getSlot(45).getStack();
        boolean hasOffhandTotem = this.isTotem(offhandStack);
        this.updateOffhandTracking(player, hasOffhandTotem);
        if (hasOffhandTotem) {
            if (this.inventoryOpen && this.clickQueue.isEmpty()) {
                this.closePending = true;
                this.resetDelayTimer();
            }
            this.updateCombatTracking(player);
            return;
        }
        int totemSlot = this.findTotemForOffhand();
        if (totemSlot != -1) {
            if (this.combatOnly.getEffectiveValue().booleanValue() && !this.canPerformActionInCombat(player)) {
                this.clickQueue.clear();
                this.updateCombatTracking(player);
                return;
            }
            if (!(!this.openInventory.getEffectiveValue().booleanValue() || currentScreen.isNotNull() && currentScreen.isInstance(MappedClasses.YS) || silentEquipping)) {
                if (this.isDelayElapsed()) {
                    this.openInventory();
                }
                this.updateCombatTracking(player);
                return;
            }
            if (inInventoryScreen && !this.inventoryOpen) {
                this.inventoryOpen = true;
            }
            this.queueClick(player.getInventoryContainer().getWindowId(), totemSlot, 40, 2);
            this.resetClickTimer();
        }
        if (this.inventoryOpen && this.clickQueue.isEmpty()) {
            this.closePending = true;
            this.resetDelayTimer();
        }
        this.updateCombatTracking(player);
    }
}

