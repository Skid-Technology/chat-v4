/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.combat.blockhit;

import gg.vape.combat.AttackPacketTimingTracker;
import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventLivingUpdate;
import gg.vape.event.impl.EventMouseButton;
import gg.vape.event.impl.EventPreTick;
import gg.vape.event.impl.EventTickBase;
import gg.vape.module.Mod;
import gg.vape.module.combat.BlockHit;
import gg.vape.module.combat.blockhit.BlockHitMode;
import gg.vape.value.BooleanValue;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.Minecraft;

public class PredictBlockHitMode
extends BlockHitMode {
    private static final int DAMAGEABLE_HURT_RESISTANT_TIME = 10;
    private static final int DAMAGE_INTERVAL_CAPACITY = 8;
    private static final int PREDICTION_SAMPLE_COUNT = 3;
    private static final long MIN_DAMAGE_INTERVAL_MILLIS = 250L;
    private static final long MAX_DAMAGE_INTERVAL_MILLIS = 1500L;
    private static final long TICK_MILLIS = 50L;
    private final NumberValue maximumHurtTime = NumberValue.create(this, "Maximum hurt time", "#", "ms", 0.0, 50.0, 500.0, 10.0, "Milliseconds before you can take damage again to start blocking");
    private final BooleanValue includePing = BooleanValue.create(this, "Include ping", true, "Adds ping to maximum hurt time timing");
    private final NumberValue holdAfter = NumberValue.create(this, "Hold after", "#", "ticks", 0.0, 2.0, 10.0, 1.0, "Ticks to keep blocking after hurt time ends if no hit arrives");
    private final long[] damageIntervals = new long[8];
    private int damageIntervalCount;
    private int nextDamageIntervalIndex;
    private long lastDamageTime;
    private boolean damageObserved;
    private boolean blocking;
    private boolean holdTimerStarted;
    private long holdUntil;

    public PredictBlockHitMode(Mod parent, String name) {
        super(parent, name);
        this.addValue(this.maximumHurtTime, this.includePing, this.holdAfter);
    }

    @Override
    public boolean shouldBlock() {
        return false;
    }

    @Override
    public boolean isBlocking() {
        return this.blocking;
    }

    @EventHandler
    public void onTick(EventPreTick event) {
        if (!this.canPredict()) {
            this.resetPrediction();
            return;
        }
        int hurtResistantTime = event.getThePlayer().getHurtResistantTime();
        if (this.damageObserved && this.hasStableDamagePattern()) {
            this.updatePredictedBlocking(hurtResistantTime);
        } else {
            this.updateReactiveBlocking(hurtResistantTime);
        }
    }

    @EventHandler
    public void onDamaged(EventLivingUpdate event) {
        if (!event.getEntity().getObject().equals(Minecraft.thePlayer().getObject())) {
            return;
        }
        this.damageObserved = true;
        this.holdTimerStarted = false;
        this.holdUntil = 0L;
        this.recordDamageInterval();
        EventTickBase.PRE_TICK_EXECUTOR.execute(() -> this.setBlocking(false));
    }

    @EventHandler
    public void onMouseButton(EventMouseButton event) {
        if (!event.getButtonState() || !this.parent().isHoldingSword()) {
            return;
        }
        int buttonBinding = -100 + event.getButton();
        if (buttonBinding == Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362().getKeyCode() && this.parent().requiresMouseDown() && ClientSettings.isAttackButtonDown()) {
            event.setCancelled(true);
        }
    }

    private void updateReactiveBlocking(int hurtResistantTime) {
        if (hurtResistantTime > 10) {
            this.holdTimerStarted = false;
            this.holdUntil = 0L;
            this.setBlocking(hurtResistantTime <= 10 + this.getEarlyWindowTicks());
            return;
        }
        if (this.blocking) {
            this.releaseAfterConfiguredHold();
        }
    }

    private void updatePredictedBlocking(int hurtResistantTime) {
        long now = System.currentTimeMillis();
        long expectedDamageTime = this.lastDamageTime + this.getAverageDamageInterval();
        long earlyWindow = this.getEarlyWindowMillis();
        long lateWindow = (long)this.getHoldTicks() * 50L;
        boolean insidePredictionWindow = now >= expectedDamageTime - earlyWindow && now <= expectedDamageTime + lateWindow;
        boolean canTakeDamageSoon = hurtResistantTime <= 10 + this.getEarlyWindowTicks();
        this.setBlocking(insidePredictionWindow && canTakeDamageSoon);
    }

    private void releaseAfterConfiguredHold() {
        int holdTicks = this.getHoldTicks();
        if (holdTicks <= 0) {
            this.setBlocking(false);
            return;
        }
        long now = System.currentTimeMillis();
        if (!this.holdTimerStarted) {
            this.holdTimerStarted = true;
            this.holdUntil = now + (long)holdTicks * 50L;
        }
        if (now >= this.holdUntil) {
            this.setBlocking(false);
        }
    }

    private void recordDamageInterval() {
        long now = System.currentTimeMillis();
        if (this.lastDamageTime > 0L) {
            long interval = now - this.lastDamageTime;
            if (interval >= 250L && interval <= 1500L) {
                this.damageIntervals[this.nextDamageIntervalIndex] = interval;
                this.nextDamageIntervalIndex = (this.nextDamageIntervalIndex + 1) % 8;
                if (this.damageIntervalCount < 8) {
                    ++this.damageIntervalCount;
                }
            } else {
                this.damageIntervalCount = 0;
                this.nextDamageIntervalIndex = 0;
            }
        }
        this.lastDamageTime = now;
    }

    private long getAverageDamageInterval() {
        int sampleCount = Math.min(this.damageIntervalCount, 3);
        if (sampleCount <= 0) {
            return 0L;
        }
        long total = 0L;
        for (int offset = 0; offset < sampleCount; ++offset) {
            int index = this.nextDamageIntervalIndex - 1 - offset;
            if (index < 0) {
                index += 8;
            }
            total += this.damageIntervals[index];
        }
        return total / (long)sampleCount;
    }

    private long getEarlyWindowMillis() {
        long window = Math.round(this.maximumHurtTime.getValue());
        if (this.includePing.getEffectiveValue().booleanValue()) {
            window += AttackPacketTimingTracker.INSTANCE.getAverageHitDelay();
        }
        return window + 50L;
    }

    private int getEarlyWindowTicks() {
        return (int)Math.ceil((double)this.getEarlyWindowMillis() / 50.0);
    }

    private int getHoldTicks() {
        return this.holdAfter.getValue().intValue();
    }

    private boolean hasStableDamagePattern() {
        return this.damageIntervalCount >= 3 && this.lastDamageTime > 0L;
    }

    private boolean canPredict() {
        if (Minecraft.thePlayer().isNull() || !this.parent().isHoldingSword()) {
            return false;
        }
        if (this.parent().requiresMouseDown() && !ClientSettings.isUseItemButtonDown()) {
            return false;
        }
        return this.parent().findDefaultTarget() != null;
    }

    private void resetPrediction() {
        this.damageObserved = false;
        this.holdTimerStarted = false;
        this.holdUntil = 0L;
        this.lastDamageTime = 0L;
        this.damageIntervalCount = 0;
        this.nextDamageIntervalIndex = 0;
        this.setBlocking(false);
    }

    private void setBlocking(boolean blocking) {
        if (this.blocking == blocking) {
            return;
        }
        this.blocking = blocking;
        Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362().setPressed(blocking);
    }

    private BlockHit parent() {
        return (BlockHit)this.getParent();
    }
}

