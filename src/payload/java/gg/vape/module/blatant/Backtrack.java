package gg.vape.module.blatant;

import gg.vape.event.EventHandler;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventPacketReceive;
import gg.vape.event.impl.EventPreAttack;
import gg.vape.event.impl.EventPreTick;
import gg.vape.event.impl.EventRender3D;
import gg.vape.event.impl.EventWorldChange;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.blatant.backtrack.BacktrackPacketQueueEntry;
import gg.vape.module.blatant.backtrack.BacktrackPacketReplayState;
import gg.vape.module.blatant.backtrack.BacktrackPacketState;
import gg.vape.utils.datas.PlayerLocationSnapshot;
import gg.vape.utils.network.PacketDispatchGuard;
import gg.vape.utils.render.RenderUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.ColorValue;
import gg.vape.value.RandomValue;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.NetHandlerPlayClientImpl;
import gg.vape.wrapper.impl.NetworkPlayerInfo;
import gg.vape.wrapper.impl.Packet;
import gg.vape.wrapper.impl.RenderManager;
import gg.vape.wrapper.impl.SEntityPacket;
import gg.vape.wrapper.impl.SPacketEntity;
import gg.vape.wrapper.impl.SPacketEntityTeleport;
import gg.vape.wrapper.impl.SPacketEntityVelocity;
import java.awt.Color;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.lwjgl.opengl.GL11;

public class Backtrack extends Mod {
   private double currentRenderY;
   private final Map<Integer, BacktrackPacketState> entityPacketStates;
   private double previousRenderZ;
   private double previousRenderY;
   private double targetRenderZ;
   private int activeTargetId = -1;
   private final BooleanValue renderServerPosition;
   public final RandomValue latency = RandomValue.create(this, "Latency", "#", "ms", 0.0, 50.0, 100.0, 500.0);
   private boolean delayingPackets;
   private double previousRenderX;
   private double targetRenderY;
   private int interpolationSteps;
   private final PacketDispatchGuard dispatchGuard;
   private final Map<Packet, Long> queuedPackets;
   private PlayerLocationSnapshot lastTargetSnapshot;
   private double targetRenderX;
   private double currentRenderZ;
   private final ColorValue renderColor;
   private int pendingTargetId = -1;
   private double currentRenderX;

   @Override
   public String getDetailedSuffix() {
      return this.latency.getDisplayValue();
   }

   @EventHandler
   public void onPreAttack(EventPreAttack event) {
      this.queueTarget(event.getTarget());
   }

   private boolean shouldFlushForPacket(Packet packet) {
      if (packet.isInstance(MappedClasses.zw)) {
         return true;
      } else if (packet.isInstance(MappedClasses.YX)) {
         EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
         if (entityPlayerSP.isNull()) {
            return true;
         } else {
            SPacketEntityVelocity sPacketEntityVelocity = new SPacketEntityVelocity(packet);
            return sPacketEntityVelocity.getEntityId() == entityPlayerSP.S();
         }
      } else {
         return false;
      }
   }

   public Backtrack() {
      super("BackTrack", -57312, Category.NETWORK);
      this.renderServerPosition = BooleanValue.create(this, "Render server pos", true);
      this.renderColor = ColorValue.createWithAlpha(this, "Color", new Color(5, 134, 105), 100);
      this.dispatchGuard = PacketDispatchGuard.b;
      this.queuedPackets = new LinkedHashMap<>();
      this.entityPacketStates = new HashMap<>();
      this.renderServerPosition.addDependentValues(this.renderColor);
      this.addValue(this.latency, this.renderServerPosition, this.renderColor);
   }

   private void resetState() {
      this.flushQueuedPackets();
      this.entityPacketStates.clear();
      this.activeTargetId = -1;
      this.pendingTargetId = -1;
      this.lastTargetSnapshot = null;
      this.resetRenderPosition(0.0, 0.0, 0.0);
   }

   @EventHandler
   public void onRender3D(EventRender3D eventRender3D) {
      if (this.renderServerPosition.getEffectiveValue()) {
         if (this.activeTargetId != -1) {
            if (!Minecraft.theWorld().isNull()) {
               Entity entity = Minecraft.theWorld().V(this.activeTargetId);
               if (entity != null && entity.isInstance(MappedClasses.zm)) {
                  EntityLivingBase target = new EntityLivingBase(entity);
                  double renderX = RenderManager.getInterpolatedRenderPosX();
                  double renderY = RenderManager.getInterpolatedRenderPosY();
                  double renderZ = RenderManager.getInterpolatedRenderPosZ();
                  float partialTicks = Minecraft.getTimer().renderPartialTicks();
                  double interpolatedX = this.previousRenderX + (this.currentRenderX - this.previousRenderX) * partialTicks;
                  double interpolatedY = this.previousRenderY + (this.currentRenderY - this.previousRenderY) * partialTicks;
                  double interpolatedZ = this.previousRenderZ + (this.currentRenderZ - this.previousRenderZ) * partialTicks;
                  eventRender3D.getEntityRenderer().B(1.0);
                  GL11.glBlendFunc(770, 771);
                  GlStateManager.enableBlend();
                  GlStateManager.enableAlpha();
                  GlStateManager.disableTexture2D();
                  GlStateManager.depthMask(false);
                  if (ForgeVersion.MC_1_21_4.v()) {
                     GL11.glLineWidth(1.5F);
                  }

                  double boxWidth = target.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu().getMaxX()
                     - target.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu().getMinX()
                     + target.b();
                  double halfWidth = boxWidth / 2.0;
                  RenderUtil.d();
                  RenderUtil.u(
                     interpolatedX - halfWidth,
                     interpolatedY + 0.01,
                     interpolatedZ - halfWidth,
                     boxWidth,
                     target.Y(),
                     boxWidth,
                     1.0,
                     this.renderColor.getMutableColor().darker().darker(),
                     this.renderColor.getMutableColor(),
                     renderX,
                     renderY,
                     renderZ
                  );
                  RenderUtil.Y();
                  GlStateManager.depthMask(true);
                  GlStateManager.enableTexture2D();
                  GlStateManager.disableBlend();
                  eventRender3D.getEntityRenderer().O(1.0);
               }
            }
         }
      }
   }

   private void promotePendingTarget() {
      if (this.pendingTargetId != -1) {
         this.activeTargetId = this.pendingTargetId;
         this.pendingTargetId = -1;
         this.lastTargetSnapshot = null;
         BacktrackPacketState backtrackPacketState = this.entityPacketStates.get(this.activeTargetId);
         if (backtrackPacketState != null) {
            PlayerLocationSnapshot playerLocationSnapshot = backtrackPacketState.toSnapshot();
            this.resetRenderPosition(playerLocationSnapshot.getX(), playerLocationSnapshot.getY(), playerLocationSnapshot.getZ());
         } else {
            Entity entity;
            if (Minecraft.theWorld().isNotNull() && (entity = Minecraft.theWorld().V(this.activeTargetId)) != null && entity.isNotNull()) {
               this.resetRenderPosition(entity.z(), entity.N(), entity.h());
            }
         }

         this.flushQueuedPackets();
      }
   }

   private void flushQueuedPackets() {
      if (!this.queuedPackets.isEmpty()) {
         EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
         NetHandlerPlayClientImpl netHandlerPlayClientImpl;
         if (entityPlayerSP.isNotNull() && (netHandlerPlayClientImpl = entityPlayerSP.sendQueue()).isNotNull()) {
            for (Packet packet : this.queuedPackets.keySet()) {
               this.dispatchGuard.l(packet, netHandlerPlayClientImpl);
            }
         }

         this.queuedPackets.clear();
      }
   }

   private void queueTarget(Entity entity) {
      if (entity != null) {
         int entityId = entity.S();
         if (entityId != this.activeTargetId && entityId != this.pendingTargetId) {
            this.pendingTargetId = entityId;
         }
      }
   }

   private boolean shouldDelaySnapshot(PlayerLocationSnapshot snapshot) {
      if (this.lastTargetSnapshot == null) {
         return false;
      } else {
         PlayerLocationSnapshot playerPosition = this.getPlayerSnapshot();
         if (playerPosition == null) {
            return false;
         } else {
            double previousDistance = PlayerLocationSnapshot.rayIntersectionDistance(playerPosition, this.lastTargetSnapshot);
            double newDistance = PlayerLocationSnapshot.rayIntersectionDistance(playerPosition, snapshot);
            return newDistance > previousDistance + 0.001;
         }
      }
   }

   private PlayerLocationSnapshot processTrackedPacket(Packet packet, EventPacketReceive event) {
      BacktrackPacketState packetState = null;
      int entityId = -1;
      if (packet.isInstance(MappedClasses.qz)) {
         SEntityPacket relativeMovePacket = new SEntityPacket(packet);
         Entity entity = relativeMovePacket.getEntity(event.getWorld());
         if (entity.isNotNull()) {
            entityId = entity.S();
            packetState = this.entityPacketStates.get(entityId);
            if (packetState != null) {
               packetState.applyRelativeMove(relativeMovePacket);
            } else {
               packetState = BacktrackPacketState.fromEntity(entity);
               packetState.applyRelativeMove(relativeMovePacket);
               this.entityPacketStates.put(entityId, packetState);
            }
         }
      } else if (packet.isInstance(MappedClasses.s)) {
         SPacketEntity entityPacket = new SPacketEntity(packet);
         entityId = entityPacket.getEntityId();
         packetState = this.entityPacketStates.get(entityId);
         if (packetState != null) {
            packetState.applyAbsolutePacket(entityPacket);
         } else {
            packetState = new BacktrackPacketState(entityPacket.getX(), entityPacket.getY(), entityPacket.getZ());
            this.entityPacketStates.put(entityId, packetState);
         }
      } else {
         if (packet.isInstance(MappedClasses.uW)) {
            SPacketEntityTeleport teleportPacket = new SPacketEntityTeleport(packet);
            this.entityPacketStates
               .put(teleportPacket.getEntityId(), new BacktrackPacketState(teleportPacket.getX(), teleportPacket.getY(), teleportPacket.getZ()));
            return null;
         }

         if (packet.isInstance(MappedClasses.Yv)) {
            BacktrackPacketReplayState removalPacket = new BacktrackPacketReplayState(packet);

            for (int removedEntityId : removalPacket.getDestroyedEntityIds()) {
               this.entityPacketStates.remove(removedEntityId);
               if (removedEntityId == this.activeTargetId) {
                  this.activeTargetId = -1;
                  this.lastTargetSnapshot = null;
                  this.flushQueuedPackets();
               }
            }

            return null;
         }

         if (packet.isInstance(MappedClasses.FE)) {
            this.scheduleReset();
            return null;
         }
      }

      if (ForgeVersion.MC_1_21_4.d() && packet.isInstance(MappedClasses.ly)) {
         NetworkPlayerInfo networkPlayerInfo = new NetworkPlayerInfo(packet);
         entityId = networkPlayerInfo.getEntityId();
         packetState = this.entityPacketStates.get(entityId);
         if (packetState != null) {
            packetState.applyNetworkPlayerInfo(networkPlayerInfo);
         }
      }

      if (entityId == this.activeTargetId && packetState != null) {
         PlayerLocationSnapshot snapshot = packetState.toSnapshot();
         this.setTargetRenderPosition(snapshot.getX(), snapshot.getY(), snapshot.getZ());
         return snapshot;
      } else {
         return null;
      }
   }

   private void queuePacket(Packet packet) {
      long latencyMillis = (long)this.latency.getRandomValue();
      long releaseAt = System.currentTimeMillis() + latencyMillis;
      Map<Packet, Long> queuedPackets = this.queuedPackets;
      if (!queuedPackets.isEmpty()) {
         long lastReleaseAt = this.queuedPackets.entrySet().stream().reduce((first, second) -> second).get().getValue();
         if (releaseAt < lastReleaseAt) {
            releaseAt = lastReleaseAt + 1L;
         }
      }

      queuedPackets.put(packet, releaseAt);
   }

   private void scheduleReset() {
      PacketDispatchGuard.B(this::resetState);
   }

   private void setTargetRenderPosition(double x, double y, double z) {
      this.targetRenderX = x;
      this.targetRenderY = y;
      this.targetRenderZ = z;
      this.interpolationSteps = 3;
   }

   @Override
   public void onDisable() {
      this.scheduleReset();
   }

   @EventHandler
   public void onWorldChange(EventWorldChange event) {
      this.scheduleReset();
   }

   @EventHandler(priority = EventPriority.LOW)
   public void onPacketReceive(EventPacketReceive event) {
      if (!this.queuedPackets.isEmpty()) {
         NetHandlerPlayClientImpl networkHandler = Minecraft.N();
         if (networkHandler.isNotNull()) {
            long now = System.currentTimeMillis();
            Iterator<Entry<Packet, Long>> iterator = this.queuedPackets.entrySet().iterator();

            while (iterator.hasNext()) {
               Entry<Packet, Long> queuedPacket = iterator.next();
               if (now < queuedPacket.getValue()) {
                  break;
               }

               this.dispatchGuard.l(queuedPacket.getKey(), networkHandler);
               iterator.remove();
            }
         } else {
            this.queuedPackets.clear();
         }
      }

      this.promotePendingTarget();
      if (!event.getWorld().isNull() && !event.getThePlayer().isNull()) {
         Packet receivedPacket = event.getPacket();
         if (!this.dispatchGuard.R(receivedPacket)) {
            List<Packet> packets;
            if (ForgeVersion.MC_1_21_4.d() && receivedPacket.isInstance(MappedClasses.ue)) {
               BacktrackPacketQueueEntry bundledPacket = new BacktrackPacketQueueEntry(receivedPacket);
               packets = bundledPacket.getPackets();
            } else {
               packets = Collections.singletonList(receivedPacket);
            }

            PlayerLocationSnapshot latestSnapshot = null;

            for (Packet packet : packets) {
               PlayerLocationSnapshot snapshot = this.processTrackedPacket(packet, event);
               if (snapshot != null) {
                  latestSnapshot = snapshot;
               }

               if (this.shouldFlushForPacket(packet)) {
                  this.flushQueuedPackets();
                  return;
               }
            }

            if (this.activeTargetId == -1) {
               if (!this.queuedPackets.isEmpty()) {
                  this.flushQueuedPackets();
               }
            } else {
               if (latestSnapshot != null) {
                  this.delayingPackets = this.shouldDelaySnapshot(latestSnapshot);
                  this.lastTargetSnapshot = latestSnapshot;
               }

               if (this.delayingPackets) {
                  event.setCancelled(true);
                  this.queuePacket(receivedPacket);
               } else if (!this.queuedPackets.isEmpty()) {
                  this.flushQueuedPackets();
               }
            }
         }
      } else {
         this.flushQueuedPackets();
         this.activeTargetId = -1;
      }
   }

   @EventHandler
   public void onTick(EventPreTick event) {
      this.previousRenderX = this.currentRenderX;
      this.previousRenderY = this.currentRenderY;
      this.previousRenderZ = this.currentRenderZ;
      if (this.activeTargetId != -1 && this.interpolationSteps > 0) {
         this.currentRenderX = this.currentRenderX + (this.targetRenderX - this.currentRenderX) / this.interpolationSteps;
         this.currentRenderY = this.currentRenderY + (this.targetRenderY - this.currentRenderY) / this.interpolationSteps;
         this.currentRenderZ = this.currentRenderZ + (this.targetRenderZ - this.currentRenderZ) / this.interpolationSteps;
         this.interpolationSteps--;
      }
   }

   private PlayerLocationSnapshot getPlayerSnapshot() {
      EntityPlayerSP player = Minecraft.thePlayer();
      return player.isNull() ? null : new PlayerLocationSnapshot(player.z(), player.N(), player.h());
   }

   private void resetRenderPosition(double x, double y, double z) {
      this.currentRenderX = this.previousRenderX = x;
      this.targetRenderX = x;
      this.currentRenderY = this.previousRenderY = y;
      this.targetRenderY = y;
      this.currentRenderZ = this.previousRenderZ = z;
      this.targetRenderZ = z;
      this.interpolationSteps = 0;
   }
}
