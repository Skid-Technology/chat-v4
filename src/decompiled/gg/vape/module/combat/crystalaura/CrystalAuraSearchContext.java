/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.combat.crystalaura;

import gg.vape.mapping.MappedClasses;
import gg.vape.module.blatant.blockin.BlockPathPlanner;
import gg.vape.module.blatant.blockin.BlockPlacementGraph;
import gg.vape.wrapper.impl.BlockState;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.World;

public class CrystalAuraSearchContext {
    private final int obsidianSlot;
    private final BlockState obsidianState;
    private EntityLivingBase simulatedTargetDamage;
    private boolean damageSimulated;
    private EntityLivingBase simulatedTargetCollision;
    private boolean collisionSimulated;

    public CrystalAuraSearchContext(int obsidianSlot, BlockState obsidianState) {
        this.obsidianSlot = obsidianSlot;
        this.obsidianState = obsidianState;
    }

    public int getObsidianSlot() {
        return this.obsidianSlot;
    }

    public BlockState getObsidianState() {
        return this.obsidianState;
    }

    public EntityLivingBase getSimulatedTargetForDamage(EntityLivingBase target, EntityPlayerSP player, World world, boolean predictAttackVelocity, boolean attacked, EntityLivingBase currentTarget) {
        if (!this.damageSimulated) {
            this.simulatedTargetDamage = CrystalAuraSearchContext.simulateTarget(target, player, world, predictAttackVelocity, attacked, currentTarget, false);
            this.damageSimulated = true;
        }
        return this.simulatedTargetDamage;
    }

    public EntityLivingBase getSimulatedTargetForCollision(EntityLivingBase target, EntityPlayerSP player, World world, boolean predictAttackVelocity, boolean attacked, EntityLivingBase currentTarget) {
        if (!this.collisionSimulated) {
            this.simulatedTargetCollision = CrystalAuraSearchContext.simulateTarget(target, player, world, predictAttackVelocity, attacked, currentTarget, true);
            this.collisionSimulated = true;
        }
        return this.simulatedTargetCollision;
    }

    private static EntityLivingBase simulateTarget(EntityLivingBase target, EntityPlayerSP player, World world, boolean predictAttackVelocity, boolean attacked, EntityLivingBase currentTarget, boolean isCollision) {
        if (target != null && target.isNotNull() && target.isInstance(MappedClasses.Yl)) {
            EntityPlayer targetPlayer = new EntityPlayer(target);
            BlockPathPlanner pathPlanner = new BlockPathPlanner(targetPlayer, player, world, new BlockPlacementGraph(player));
            EntityPlayer simulatedTarget = pathPlanner.getSimulatedPlayer();
            pathPlanner.clearInput();
            pathPlanner.finishSimulation();
            double motionX = simulatedTarget.z() - simulatedTarget.M();
            double motionY = simulatedTarget.N() - simulatedTarget.W();
            double motionZ = simulatedTarget.h() - simulatedTarget.m$src$D$fwnne5();
            if (predictAttackVelocity && attacked && target.equals(currentTarget)) {
                double playerDeltaZ;
                double playerDeltaX;
                double horizontalDistance;
                float attackVerticalVelocity = 0.4f;
                double d = motionY = target.b$src$Z$fqlxe4() ? Math.min(0.4, motionY / 2.0 + (double)attackVerticalVelocity) : (double)attackVerticalVelocity;
                if (isCollision && (horizontalDistance = Math.sqrt((playerDeltaX = player.z() - target.z()) * playerDeltaX + (playerDeltaZ = player.h() - target.h()) * playerDeltaZ)) > 0.0) {
                    motionX = motionX / 2.0 - playerDeltaX / horizontalDistance * 0.3;
                    motionZ = motionZ / 2.0 - playerDeltaZ / horizontalDistance * 0.3;
                }
            }
            simulatedTarget.F(motionX, motionY, motionZ);
            for (int predictionTick = 0; predictionTick < 3; ++predictionTick) {
                pathPlanner.simulateTick();
            }
            return simulatedTarget;
        }
        return target;
    }
}

