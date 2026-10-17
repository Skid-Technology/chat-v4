/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.utility;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPreTick;
import gg.vape.module.UtilityMod;
import gg.vape.module.control.SharedModuleControlClaims;
import gg.vape.rotation.AdaptiveRotationController;
import gg.vape.rotation.FixedRotationController;
import gg.vape.rotation.RotationControlClaim;
import gg.vape.rotation.RotationManager;
import gg.vape.unmap.ModeOption;
import gg.vape.unmap.ModeSelection;
import gg.vape.value.BooleanValue;
import gg.vape.value.ModeValue;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.InventoryPlayer;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.Vec3;

public class PearlCatch
extends UtilityMod {
    private static final double PROJECTILE_SPEED = 1.5;
    private static final double PEARL_DRAG = 0.99;
    private static final double PEARL_GRAVITY = 0.03;
    private static final double MAX_INTERCEPT_DISTANCE_SQUARED = 0.36;
    private static final int MAX_FLIGHT_TICKS = 30;
    private static final int MAX_AIM_TICKS = 40;
    private final ModeOption upwardMode = new ModeOption("Upward");
    private final ModeOption currentAimMode = new ModeOption("Current aim");
    private final ModeValue aimMode = ModeValue.create((Object)this, "Aim mode", "Upward - throws the pearl straight up\nCurrent aim - throws the wind charge where you were looking", (ModeSelection)this.upwardMode, this.upwardMode, this.currentAimMode);
    private final NumberValue aimSpeed = NumberValue.create(this, "Aim speed", "#.#", "", 1.0, 10.0, 10.0);
    private final BooleanValue silentAim = BooleanValue.create(this, "Silent aim", true, null);
    private final NumberValue chargeDelay = NumberValue.create(this, "Charge delay", "#", " ticks", 0.0, 0.0, 10.0, 1.0, "Ticks to wait after the pearl before throwing the wind charge");
    private final RotationControlClaim rotationClaim = SharedModuleControlClaims.rotation;
    private State state = State.IDLE;
    private FixedRotationController rotationController;
    private InterceptPlan plan;
    private int pearlSlot = -1;
    private int chargeSlot = -1;
    private int savedSlot = -1;
    private int stateTicks;
    private int ticksSincePearl;

    public PearlCatch() {
        super("PearlCatch", "Throws a pearl, then throws a wind charge to catch it");
        this.addValue(this.aimMode, this.aimSpeed, this.silentAim, this.chargeDelay);
        this.rotationClaim.setPriority(this, 7);
    }

    @Override
    public void onEnable() {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull() || Minecraft.theWorld().isNull() || SharedModuleControlClaims.rightClickUse.isClaimed()) {
            this.abort();
            return;
        }
        InventoryPlayer inventory = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
        this.pearlSlot = this.findHotbarSlot(inventory, "minecraft:ender_pearl", "Ender Pearl");
        this.chargeSlot = this.findHotbarSlot(inventory, "minecraft:wind_charge", "Wind Charge", "Windcharge");
        if (this.pearlSlot == -1 || this.chargeSlot == -1) {
            this.abort();
            return;
        }
        this.savedSlot = inventory.v();
        int configuredDelay = this.chargeDelay.getValue().intValue();
        InterceptPlan interceptPlan = this.plan = this.currentAimMode.isSelected() ? this.findCurrentAimPlan(player, Math.max(2, configuredDelay)) : this.findUpwardPlan(player, configuredDelay);
        if (this.plan == null) {
            this.abort();
            return;
        }
        this.state = State.AIMING_PEARL;
        this.stateTicks = 0;
        this.ticksSincePearl = 0;
        this.setRotation(this.plan.pearlYaw, this.plan.pearlPitch);
    }

    @Override
    public void onDisable() {
        SharedModuleControlClaims.rightClickUse.unblockUse();
        this.releaseRotation();
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNotNull() && this.savedSlot != -1) {
            player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(this.savedSlot);
        }
        this.state = State.IDLE;
        this.rotationController = null;
        this.plan = null;
        this.pearlSlot = -1;
        this.chargeSlot = -1;
        this.savedSlot = -1;
        this.stateTicks = 0;
        this.ticksSincePearl = 0;
    }

    @EventHandler
    public void onTick(EventPreTick event) {
        EntityPlayerSP player = event.getThePlayer();
        if (player.isNull() || Minecraft.theWorld().isNull() || this.plan == null) {
            this.abort();
            return;
        }
        ++this.stateTicks;
        if (this.state == State.AIMING_CHARGE || this.state == State.WAITING_FOR_CHARGE) {
            ++this.ticksSincePearl;
        }
        if (this.stateTicks > 40 && this.state != State.WAITING_FOR_CHARGE) {
            this.abort();
            return;
        }
        switch (this.state) {
            case AIMING_PEARL: {
                if (!this.hasRotationControl()) {
                    return;
                }
                if (!this.isRotationReady()) break;
                this.state = State.THROWING_PEARL;
                break;
            }
            case THROWING_PEARL: {
                this.throwFromSlot(this.pearlSlot);
                this.state = State.AIMING_CHARGE;
                this.stateTicks = 0;
                this.ticksSincePearl = 0;
                this.setRotation(this.plan.chargeYaw, this.plan.chargePitch);
                break;
            }
            case AIMING_CHARGE: {
                if (!this.hasRotationControl()) {
                    return;
                }
                if (this.isRotationReady()) {
                    this.state = State.WAITING_FOR_CHARGE;
                    this.stateTicks = 0;
                    break;
                }
                if (!this.plan.absoluteChargeTiming || this.ticksSincePearl < this.plan.chargeTick) break;
                this.abort();
                break;
            }
            case WAITING_FOR_CHARGE: {
                boolean readyToThrow;
                boolean bl = this.plan.absoluteChargeTiming ? this.ticksSincePearl >= this.plan.chargeTick : (readyToThrow = this.stateTicks >= this.plan.chargeTick);
                if (!readyToThrow) break;
                this.throwFromSlot(this.chargeSlot);
                this.state = State.FINISHED;
                break;
            }
            case FINISHED: {
                this.setEnabled(false, true);
            }
        }
    }

    private boolean hasRotationControl() {
        return this.rotationClaim.isOwnedBy(this) || this.rotationClaim.acquire(this, this.silentAim.getEffectiveValue());
    }

    private boolean isRotationReady() {
        if (this.rotationController == null || !this.rotationClaim.isOwnedBy(this)) {
            return false;
        }
        if (RotationManager.INSTANCE.getActiveController() != this.rotationController) {
            RotationManager.INSTANCE.setController(this.rotationController);
        }
        return this.rotationController.isComplete();
    }

    private void setRotation(float yaw, float pitch) {
        FixedRotationController controller = this.silentAim.getEffectiveValue() != false ? new AdaptiveRotationController(yaw, pitch) : new FixedRotationController(yaw, pitch);
        controller.setTargetRotation(yaw, pitch);
        controller.setSpeed(this.aimSpeed.getValue().floatValue());
        controller.setTolerance(0.35f);
        controller.setScaleAxesProportionally(true);
        controller.setLinearAcceleration(true);
        controller.setClampStepToRemaining(true);
        controller.setRetainAfterCompletion(true);
        this.rotationController = controller;
        if (this.rotationClaim.isOwnedBy(this)) {
            RotationManager.INSTANCE.setController(controller);
        }
    }

    private void releaseRotation() {
        if (this.rotationController != null && RotationManager.INSTANCE.getActiveController() == this.rotationController) {
            RotationManager.INSTANCE.releaseController(this.rotationController);
        }
        this.rotationClaim.release(this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void throwFromSlot(int slot) {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return;
        }
        player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(slot);
        KeyBinding useKey = Minecraft.gameSettings().b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362();
        SharedModuleControlClaims.rightClickUse.blockUse();
        try {
            KeyBinding.setKeyBindState(useKey, true);
            KeyBinding.onTick(useKey);
        }
        finally {
            KeyBinding.setKeyBindState(useKey, false);
            SharedModuleControlClaims.rightClickUse.clearClaimed();
        }
    }

    private int findHotbarSlot(InventoryPlayer inventory, String itemId, String ... displayNames) {
        Item expected = Item.L(itemId);
        for (int slot = 0; slot < 9; ++slot) {
            ItemStack stack = inventory.c(slot);
            if (stack.isNull() || stack.getItem().isNull()) continue;
            Item item = stack.getItem();
            if (expected != null && expected.isNotNull() && item.equals(expected)) {
                return slot;
            }
            String text = item.toString();
            for (String displayName : displayNames) {
                if (text == null || !text.contains(displayName)) continue;
                return slot;
            }
        }
        return -1;
    }

    private void abort() {
        if (this.isEnabled()) {
            this.setEnabled(false, true);
        } else {
            this.onDisable();
        }
    }

    private InterceptPlan findUpwardPlan(EntityPlayerSP player, int minimumDelay) {
        float[] pearlYaws;
        float referenceYaw = player.J();
        double horizontalMotionSquared = player.t() * player.t() + player.T() * player.T();
        if (horizontalMotionSquared < 0.003) {
            pearlYaws = new float[]{referenceYaw};
        } else {
            float[] fArray;
            float movementYaw = (float)Math.toDegrees(Math.atan2(-player.t(), player.T()));
            if (Math.abs(PearlCatch.wrapAngle(movementYaw - referenceYaw)) < 20.0f) {
                float[] fArray2 = new float[1];
                fArray = fArray2;
                fArray2[0] = referenceYaw;
            } else {
                float[] fArray3 = new float[2];
                fArray3[0] = referenceYaw;
                fArray = fArray3;
                fArray3[1] = movementYaw;
            }
            pearlYaws = fArray;
        }
        InterceptPlan best = null;
        double bestScore = Double.MAX_VALUE;
        for (float pearlPitch = -90.0f; pearlPitch <= -76.0f; pearlPitch += 0.5f) {
            for (float pearlBaseYaw : pearlYaws) {
                for (float pearlYawOffset = -20.0f; pearlYawOffset <= 20.0f; pearlYawOffset += 2.5f) {
                    float pearlYaw = pearlBaseYaw + pearlYawOffset;
                    for (float chargePitch = -90.0f; chargePitch <= -86.0f; chargePitch += 1.0f) {
                        for (float chargeYawOffset = -20.0f; chargeYawOffset <= 20.0f; chargeYawOffset += 5.0f) {
                            double score;
                            float chargeYaw = referenceYaw + chargeYawOffset;
                            InterceptPlan candidate = this.evaluatePlan(player, pearlYaw, pearlPitch, chargeYaw, chargePitch, minimumDelay, minimumDelay, false);
                            if (candidate == null || !((score = Math.sqrt(candidate.missDistanceSquared) + (double)Math.abs(PearlCatch.wrapAngle(chargeYaw - referenceYaw)) * 0.01 + (double)Math.abs(PearlCatch.wrapAngle(pearlYaw - referenceYaw)) * 0.003 + (double)Math.abs(chargePitch + 90.0f) * 0.03) < bestScore)) continue;
                            best = candidate;
                            bestScore = score;
                        }
                    }
                }
            }
        }
        return best;
    }

    private InterceptPlan findCurrentAimPlan(EntityPlayerSP player, int minimumDelay) {
        float chargeYaw = player.J();
        float chargePitch = player.V();
        Vec chargeDir = PearlCatch.direction(chargeYaw, chargePitch);
        double pX = player.t();
        double pY = player.b$src$Z$fqlxe4() ? 0.0 : player.q();
        double pZ = player.T();
        Vec playerVel = new Vec(pX, pY, pZ);
        Vec chargeVel = new Vec(chargeDir.x * 1.5 + pX, chargeDir.y * 1.5 + pY, chargeDir.z * 1.5 + pZ);
        Vec pearlStartPos = new Vec(player.z(), player.N() + (double)player.Y() - 0.1, player.h());
        Vec chargeStartPos = new Vec(player.z(), player.N() + (double)player.Y(), player.h());
        InterceptPlan best = null;
        double bestScore = Double.MAX_VALUE;
        int minDelay = Math.max(3, minimumDelay);
        int maxDelay = minDelay + 12;
        for (int delay = minDelay; delay <= maxDelay; ++delay) {
            Vec chargePosAtDelay = new Vec(chargeStartPos.x + playerVel.x * (double)delay, chargeStartPos.y + playerVel.y * (double)delay, chargeStartPos.z + playerVel.z * (double)delay);
            int maxFlightTicks = this.getMaxFlightTicks(chargePosAtDelay, chargeVel, player);
            for (int flightTicks = 1; flightTicks <= maxFlightTicks; ++flightTicks) {
                double[] alphas;
                int pearlTicks = delay + flightTicks;
                Vec chargeTarget = new Vec(chargePosAtDelay.x + chargeVel.x * (double)flightTicks, chargePosAtDelay.y + chargeVel.y * (double)flightTicks, chargePosAtDelay.z + chargeVel.z * (double)flightTicks);
                Vec pBase = this.calcPearlBasePos(pearlStartPos, playerVel, pearlTicks - 1);
                Vec pBaseNext = this.calcPearlBasePos(pearlStartPos, playerVel, pearlTicks);
                Vec D_0 = new Vec(chargeTarget.x - pBase.x, chargeTarget.y - pBase.y, chargeTarget.z - pBase.z);
                Vec V_base = new Vec(pBaseNext.x - pBase.x, pBaseNext.y - pBase.y, pBaseNext.z - pBase.z);
                double v_0 = this.calcGeometricSum(pearlTicks - 1);
                double v_diff = Math.pow(0.99, pearlTicks);
                double d10 = 1.5 * v_0;
                double d11 = 1.5 * v_diff;
                double a = this.dot(V_base, V_base) - d11 * d11;
                double b = -2.0 * this.dot(D_0, V_base) - 2.0 * d10 * d11;
                double c = this.dot(D_0, D_0) - d10 * d10;
                for (double alpha : alphas = new double[]{0.0, 0.5, 1.0, -c / b, (-b - Math.sqrt(b * b - 4.0 * a * c)) / (2.0 * a), (-b + Math.sqrt(b * b - 4.0 * a * c)) / (2.0 * a)}) {
                    double score;
                    float fPitch;
                    float fYaw;
                    InterceptPlan candidate;
                    Vec reqPearlDir;
                    double len;
                    double reqDist;
                    if (Double.isNaN(alpha) || alpha < 0.0 || alpha > 1.0 || (reqDist = d10 + alpha * d11) < 1.0E-9 || (len = Math.sqrt(this.dot(reqPearlDir = new Vec((D_0.x - V_base.x * alpha) / reqDist, (D_0.y - V_base.y * alpha) / reqDist, (D_0.z - V_base.z * alpha) / reqDist), reqPearlDir))) < 1.0E-9 || (candidate = this.evaluatePlan(player, fYaw = (float)Math.toDegrees(Math.atan2(-(reqPearlDir = new Vec(reqPearlDir.x / len, reqPearlDir.y / len, reqPearlDir.z / len)).x, reqPearlDir.z)), fPitch = (float)(-Math.toDegrees(Math.atan2(reqPearlDir.y, Math.sqrt(reqPearlDir.x * reqPearlDir.x + reqPearlDir.z * reqPearlDir.z)))), chargeYaw, chargePitch, delay, delay, true)) == null || !((score = Math.sqrt(candidate.missDistanceSquared) + (double)flightTicks * 0.005 + (double)Math.abs(PearlCatch.wrapAngle(fYaw - chargeYaw)) * 5.0E-4 + (double)delay * 1.0E-4) < bestScore)) continue;
                    best = candidate;
                    bestScore = score;
                }
            }
        }
        return best;
    }

    private double calcGeometricSum(int ticks) {
        if (ticks <= 0) {
            return 0.0;
        }
        return 0.99 * (1.0 - Math.pow(0.99, ticks)) / 0.010000000000000009;
    }

    private Vec calcPearlBasePos(Vec startPos, Vec playerVel, int ticks) {
        if (ticks <= 0) {
            return startPos;
        }
        double vMultiplier = this.calcGeometricSum(ticks);
        double gravityMultiplier = 98.99999999999991 * ((double)ticks - vMultiplier);
        return new Vec(startPos.x + playerVel.x * vMultiplier, startPos.y + playerVel.y * vMultiplier - 0.03 * gravityMultiplier, startPos.z + playerVel.z * vMultiplier);
    }

    private double dot(Vec a, Vec b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    private int getMaxFlightTicks(Vec chargeStartPos, Vec chargeVelocity, EntityPlayerSP player) {
        Vec3 hitVec;
        Vec chargeEndPos = new Vec(chargeStartPos.x + chargeVelocity.x * 30.0, chargeStartPos.y + chargeVelocity.y * 30.0, chargeStartPos.z + chargeVelocity.z * 30.0);
        Vec3 startVec = Vec3.create(chargeStartPos.x, chargeStartPos.y, chargeStartPos.z);
        Vec3 endVec = Vec3.create(chargeEndPos.x, chargeEndPos.y, chargeEndPos.z);
        RayTraceResult result = Minecraft.theWorld().K(startVec, endVec, false, true, false, player);
        if (result != null && result.isNotNull() && result.isBlockHit() && (hitVec = result.getHitVec()).isNotNull()) {
            double dx = hitVec.getX() - chargeStartPos.x;
            double dy = hitVec.getY() - chargeStartPos.y;
            double dz = hitVec.getZ() - chargeStartPos.z;
            double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
            double vel = Math.sqrt(this.dot(chargeVelocity, chargeVelocity));
            if (vel < 1.0E-9) {
                return 0;
            }
            int ticks = (int)Math.ceil(dist / vel - 1.0E-9) - 1;
            return Math.max(0, Math.min(30, ticks));
        }
        return 30;
    }

    private InterceptPlan evaluatePlan(EntityPlayerSP player, float pearlYaw, float pearlPitch, float chargeYaw, float chargePitch, int chargeTick, int minimumDelay, boolean absoluteChargeTiming) {
        Vec pearlDirection = PearlCatch.direction(pearlYaw, pearlPitch);
        Vec chargeDirection = PearlCatch.direction(chargeYaw, chargePitch);
        double playerMotionX = player.t();
        double playerMotionY = player.b$src$Z$fqlxe4() ? 0.0 : player.q();
        double playerMotionZ = player.T();
        double pearlX = player.z();
        double pearlY = player.N() + (double)player.Y() - 0.1;
        double pearlZ = player.h();
        double pearlMotionX = pearlDirection.x * 1.5 + playerMotionX;
        double pearlMotionY = pearlDirection.y * 1.5 + playerMotionY;
        double pearlMotionZ = pearlDirection.z * 1.5 + playerMotionZ;
        double chargeX = player.z() + playerMotionX * (double)chargeTick;
        double chargeY = player.N() + (double)player.Y() + playerMotionY * (double)chargeTick;
        double chargeZ = player.h() + playerMotionZ * (double)chargeTick;
        double chargeMotionX = chargeDirection.x * 1.5 + playerMotionX;
        double chargeMotionY = chargeDirection.y * 1.5 + playerMotionY;
        double chargeMotionZ = chargeDirection.z * 1.5 + playerMotionZ;
        double closest = Double.MAX_VALUE;
        for (int tick = 1; tick <= chargeTick + 30; ++tick) {
            double dz;
            double dy;
            double dx;
            double distance;
            pearlMotionY = (pearlMotionY - 0.03) * 0.99;
            pearlX += (pearlMotionX *= 0.99);
            pearlY += pearlMotionY;
            pearlZ += (pearlMotionZ *= 0.99);
            if (tick <= chargeTick || !((distance = (dx = pearlX - (chargeX += chargeMotionX)) * dx + (dy = pearlY - (chargeY += chargeMotionY)) * dy + (dz = pearlZ - (chargeZ += chargeMotionZ)) * dz) < closest)) continue;
            closest = distance;
        }
        if (chargeTick < minimumDelay || closest > 0.36) {
            return null;
        }
        return new InterceptPlan(pearlYaw, pearlPitch, chargeYaw, chargePitch, chargeTick, closest, absoluteChargeTiming);
    }

    private static Vec direction(float yaw, float pitch) {
        double yawRadians = Math.toRadians(yaw);
        double pitchRadians = Math.toRadians(pitch);
        return new Vec(-Math.sin(yawRadians) * Math.cos(pitchRadians), -Math.sin(pitchRadians), Math.cos(yawRadians) * Math.cos(pitchRadians));
    }

    private static float wrapAngle(float angle) {
        if ((angle %= 360.0f) >= 180.0f) {
            angle -= 360.0f;
        }
        if (angle < -180.0f) {
            angle += 360.0f;
        }
        return angle;
    }

    private static enum State {
        IDLE,
        AIMING_PEARL,
        THROWING_PEARL,
        AIMING_CHARGE,
        WAITING_FOR_CHARGE,
        FINISHED;

    }

    private static final class InterceptPlan {
        private final float pearlYaw;
        private final float pearlPitch;
        private final float chargeYaw;
        private final float chargePitch;
        private final int chargeTick;
        private final double missDistanceSquared;
        private final boolean absoluteChargeTiming;

        private InterceptPlan(float pearlYaw, float pearlPitch, float chargeYaw, float chargePitch, int chargeTick, double missDistanceSquared, boolean absoluteChargeTiming) {
            this.pearlYaw = pearlYaw;
            this.pearlPitch = pearlPitch;
            this.chargeYaw = chargeYaw;
            this.chargePitch = chargePitch;
            this.chargeTick = chargeTick;
            this.missDistanceSquared = missDistanceSquared;
            this.absoluteChargeTiming = absoluteChargeTiming;
        }
    }

    private static final class Vec {
        private final double x;
        private final double y;
        private final double z;

        private Vec(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}

