/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.render.freecam;

import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventPacketReceive;
import gg.vape.event.impl.EventPacketSend;
import gg.vape.event.impl.EventPreLivingTravel;
import gg.vape.event.impl.EventPreTick;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.render.Freecam;
import gg.vape.module.render.freecam.FreecamController;
import gg.vape.wrapper.impl.C03PacketPlayer;
import gg.vape.wrapper.impl.C06PacketPlayerPositionLook;
import gg.vape.wrapper.impl.CPacketPlayerPosition;
import gg.vape.wrapper.impl.CPacketPlayer_Rotation;
import gg.vape.wrapper.impl.EntityOtherPlayerMP;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Packet;
import gg.vape.wrapper.impl.PacketVelocityBridge;
import gg.vape.wrapper.impl.PlayerInteractEventAction;
import gg.vape.wrapper.impl.PlayerPositionLookPacketModern;
import gg.vape.wrapper.impl.SPacketEntityVelocity;

public class FreecamLegacyController
extends FreecamController<Freecam> {
    @EventHandler
    public void onTick(EventPreTick event) {
        if (Minecraft.theWorld().isNull() || Minecraft.thePlayer().isNull() || Minecraft.thePlayer().w$src$F$15l9epb() <= 0.0f) {
            ((Freecam)this.module).setEnabled(false, false);
        }
        if (((Freecam)this.module).disableRequested) {
            ((Freecam)this.module).completeDisable();
            return;
        }
        ((Freecam)this.module).rotationClaim.acquire(this.module);
        if (((Freecam)this.module).initializationPending) {
            ((Freecam)this.module).spawnLegacyFakePlayer();
            ((Freecam)this.module).initializationPending = false;
        }
        ((Freecam)this.module).updateLegacyFakePlayerMovement();
    }

    private CPacketPlayerPosition buildPositionPacket() {
        if (ForgeVersion.MC_1_7_10.L()) {
            return CPacketPlayerPosition.newInstance(((Freecam)this.module).fakePlayer.z(), ((Freecam)this.module).fakePlayer.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().getMinY(), ((Freecam)this.module).fakePlayer.N(), ((Freecam)this.module).fakePlayer.h(), ((Freecam)this.module).fakePlayer.b$src$Z$fqlxe4());
        }
        return CPacketPlayerPosition.newInstance(((Freecam)this.module).fakePlayer.z(), ((Freecam)this.module).fakePlayer.N(), ((Freecam)this.module).fakePlayer.h(), ((Freecam)this.module).fakePlayer.b$src$Z$fqlxe4());
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPacketSend(EventPacketSend event) {
        if (((Freecam)this.module).fakePlayer == null) {
            return;
        }
        if (((Freecam)this.module).bypassPacket != null && event.getPacketInstance().equals(((Freecam)this.module).bypassPacket)) {
            ((Freecam)this.module).bypassPacket = null;
            return;
        }
        if (Minecraft.theWorld().isNull()) {
            return;
        }
        if (((Freecam)this.module).shouldCancelPacket(event.getPacket())) {
            event.setCancelled(true);
            return;
        }
        if (!event.getPacket().isInstance(MappedClasses.qD)) {
            return;
        }
        event.setPacket(this.buildMovementPacket());
        Minecraft.thePlayer().E(((Freecam)this.module).positionUpdateTicks);
    }

    private C06PacketPlayerPositionLook buildPositionLookPacket() {
        if (ForgeVersion.MC_1_7_10.L()) {
            return C06PacketPlayerPositionLook.create(((Freecam)this.module).fakePlayer.z(), ((Freecam)this.module).fakePlayer.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().getMinY(), ((Freecam)this.module).fakePlayer.N(), ((Freecam)this.module).fakePlayer.h(), ((Freecam)this.module).fakePlayer.J(), ((Freecam)this.module).fakePlayer.V(), ((Freecam)this.module).fakePlayer.b$src$Z$fqlxe4());
        }
        return C06PacketPlayerPositionLook.create(((Freecam)this.module).fakePlayer.z(), ((Freecam)this.module).fakePlayer.N(), ((Freecam)this.module).fakePlayer.h(), ((Freecam)this.module).fakePlayer.J(), ((Freecam)this.module).fakePlayer.V(), ((Freecam)this.module).fakePlayer.b$src$Z$fqlxe4());
    }

    private void applyGhostPositionLookLater(PlayerPositionLookPacketModern packet) {
        this.applyGhostPositionLook(packet);
    }

    private C06PacketPlayerPositionLook buildLookOnlyPacket() {
        if (ForgeVersion.MC_1_7_10.L()) {
            return C06PacketPlayerPositionLook.create(((Freecam)this.module).fakePlayer.t(), -999.0, -999.0, ((Freecam)this.module).fakePlayer.T(), ((Freecam)this.module).fakePlayer.J(), ((Freecam)this.module).fakePlayer.V(), ((Freecam)this.module).fakePlayer.b$src$Z$fqlxe4());
        }
        return C06PacketPlayerPositionLook.create(((Freecam)this.module).fakePlayer.t(), -999.0, ((Freecam)this.module).fakePlayer.T(), ((Freecam)this.module).fakePlayer.J(), ((Freecam)this.module).fakePlayer.V(), ((Freecam)this.module).fakePlayer.b$src$Z$fqlxe4());
    }

    private void applyGhostPositionLook(PlayerPositionLookPacketModern packet) {
        if (packet == null || packet.isNull()) {
            return;
        }
        EntityOtherPlayerMP fakePlayer = ((Freecam)this.module).fakePlayer;
        double x = packet.getX();
        double y = packet.getY();
        double z = packet.getZ();
        float yaw = packet.getYaw();
        float pitch = packet.getPitch();
        if (ForgeVersion.MC_1_7_10.Y()) {
            if (packet.getRelativeFlags().contains(PlayerInteractEventAction.z().getObject())) {
                x += fakePlayer.z();
            } else {
                fakePlayer.r(0.0);
            }
            if (packet.getRelativeFlags().contains(PlayerInteractEventAction.t$src$Lgg_vape_wrapper_impl_PlayerInteractEventAction_$1n8jtc5().getObject())) {
                y += fakePlayer.N();
            } else {
                fakePlayer.k(0.0);
            }
            if (packet.getRelativeFlags().contains(PlayerInteractEventAction.a().getObject())) {
                z += fakePlayer.h();
            } else {
                fakePlayer.i(0.0);
            }
            if (packet.getRelativeFlags().contains(PlayerInteractEventAction.b().getObject())) {
                pitch += fakePlayer.V();
            }
            if (packet.getRelativeFlags().contains(PlayerInteractEventAction.m$src$Lgg_vape_wrapper_impl_PlayerInteractEventAction_$1581zn0().getObject())) {
                yaw += fakePlayer.J();
            }
            fakePlayer.t(x, y, z, yaw, pitch);
            fakePlayer.H(yaw);
            C06PacketPlayerPositionLook responsePacket = C06PacketPlayerPositionLook.create(fakePlayer.z(), fakePlayer.N(), fakePlayer.h(), fakePlayer.J(), fakePlayer.V(), false);
            ((Freecam)this.module).bypassPacket = responsePacket.getObject();
            ((Freecam)this.module).networkManager.G(responsePacket);
        }
    }

    @EventHandler
    public void onPacketReceive(EventPacketReceive event) {
        if (((Freecam)this.module).fakePlayer == null) {
            return;
        }
        Packet packet = event.getPacket();
        if (packet.isInstance(MappedClasses.zw)) {
            ((Freecam)this.module).networkManager = event.getNetworkManager();
            PlayerPositionLookPacketModern positionLookPacket = new PlayerPositionLookPacketModern(packet.getObject());
            event.setCancelled(true);
            if (ForgeVersion.MC_1_8_9.d()) {
                Minecraft.v(() -> this.applyGhostPositionLookLater(positionLookPacket));
            } else {
                this.applyGhostPositionLook(positionLookPacket);
            }
            return;
        }
        if (packet.isInstance(MappedClasses.YX)) {
            SPacketEntityVelocity velocityPacket = new SPacketEntityVelocity(packet.getObject());
            if (velocityPacket.getEntityId() == Minecraft.thePlayer().S()) {
                ((Freecam)this.module).fakePlayer.E((double)velocityPacket.getMotionX() / 8000.0, (double)velocityPacket.getMotionY() / 8000.0, (double)velocityPacket.getMotionZ() / 8000.0);
            }
        } else if (packet.isInstance(MappedClasses.qe)) {
            PacketVelocityBridge velocityPacket = new PacketVelocityBridge(packet.getObject());
            ((Freecam)this.module).fakePlayer.r(((Freecam)this.module).fakePlayer.t() + (double)velocityPacket.getMotionX());
            ((Freecam)this.module).fakePlayer.k(((Freecam)this.module).fakePlayer.q() + (double)velocityPacket.getMotionY());
            ((Freecam)this.module).fakePlayer.i(((Freecam)this.module).fakePlayer.T() + (double)velocityPacket.getMotionZ());
        }
    }

    @EventHandler
    public void onPreLivingTravel(EventPreLivingTravel event) {
        EntityPlayerSP player = Minecraft.thePlayer();
        double horizontalSpeed = ((Freecam)this.module).horizontalSpeed.getValue() / 5.0 * (ClientSettings.isPhysicalKeyDown(Minecraft.gameSettings().r()) ? 2.0 : 1.0);
        double verticalSpeed = ((Freecam)this.module).verticalSpeed.getValue() / 5.0 * (ClientSettings.isPhysicalKeyDown(Minecraft.gameSettings().r()) ? 2.0 : 1.0);
        player.z(true);
        float verticalDirection = player.movementInput().G() ? 1.0f : (player.movementInput().D$src$Z$v5d6e8() ? -1.0f : 0.0f);
        double verticalMotion = (double)(verticalDirection * 0.42f) * verticalSpeed;
        player.k(verticalMotion);
        Minecraft.thePlayer().movementInput().setCancelled(false);
        player.R(false);
        double strafe = player.movementInput().D();
        double forward = player.movementInput().T();
        float yaw = player.J();
        if (strafe == 0.0 && forward == 0.0) {
            player.r(0.0);
            player.i(0.0);
        } else {
            if (strafe != 0.0) {
                if (forward > 0.0) {
                    yaw += (float)(strafe > 0.0 ? -45 : 45);
                } else if (forward < 0.0) {
                    yaw += (float)(strafe > 0.0 ? 45 : -45);
                }
                forward = 0.0;
                strafe = strafe > 0.0 ? 1.0 : -1.0;
            }
            player.r(strafe * horizontalSpeed * Math.cos(Math.toRadians(yaw + 90.0f)) + forward * horizontalSpeed * Math.sin(Math.toRadians(yaw + 90.0f)));
            player.i(strafe * horizontalSpeed * Math.sin(Math.toRadians(yaw + 90.0f)) - forward * horizontalSpeed * Math.cos(Math.toRadians(yaw + 90.0f)));
        }
    }

    public FreecamLegacyController(Freecam freecam) {
        super(freecam);
    }

    private C03PacketPlayer buildMovementPacket() {
        C03PacketPlayer movementPacket;
        boolean rotationChanged;
        EntityOtherPlayerMP fakePlayer = ((Freecam)this.module).fakePlayer;
        double deltaX = fakePlayer.z() - ((Freecam)this.module).lastReportedX;
        double reportedY = ClientSettings.IS_LEGACY_1_7 ? fakePlayer.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu().getMinY() : fakePlayer.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().getMinY();
        double deltaY = reportedY - ((Freecam)this.module).lastReportedY;
        double deltaZ = fakePlayer.h() - ((Freecam)this.module).lastReportedZ;
        double deltaYaw = fakePlayer.J() - ((Freecam)this.module).lastReportedYaw;
        double deltaPitch = fakePlayer.V() - ((Freecam)this.module).lastReportedPitch;
        boolean positionChanged = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ > 9.0E-4 || ((Freecam)this.module).positionUpdateTicks >= 20;
        boolean bl = rotationChanged = deltaYaw != 0.0 || deltaPitch != 0.0;
        if (fakePlayer.S$src$Lgg_vape_wrapper_impl_Entity_$dgzs12().isNotNull()) {
            movementPacket = this.buildLookOnlyPacket();
            positionChanged = false;
        } else {
            movementPacket = rotationChanged ? (positionChanged ? this.buildPositionLookPacket() : CPacketPlayer_Rotation.create(fakePlayer.J(), fakePlayer.V(), fakePlayer.b$src$Z$fqlxe4())) : (positionChanged ? this.buildPositionPacket() : C03PacketPlayer.create(fakePlayer.b$src$Z$fqlxe4()));
        }
        ++((Freecam)this.module).positionUpdateTicks;
        if (positionChanged) {
            ((Freecam)this.module).lastReportedX = fakePlayer.z();
            ((Freecam)this.module).lastReportedY = ClientSettings.IS_LEGACY_1_7 ? fakePlayer.N() : fakePlayer.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().getMinY();
            ((Freecam)this.module).lastReportedZ = fakePlayer.h();
            ((Freecam)this.module).positionUpdateTicks = 0;
        }
        if (rotationChanged) {
            ((Freecam)this.module).lastReportedYaw = fakePlayer.J();
            ((Freecam)this.module).lastReportedPitch = fakePlayer.V();
        }
        return movementPacket;
    }
}

