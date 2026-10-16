package gg.vape.module.render.freecam;

import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventEntityRendererMouseUpdate;
import gg.vape.event.impl.EventKeyBindingState;
import gg.vape.event.impl.EventPostEntityRendererMouseUpdate;
import gg.vape.event.impl.EventPostRenderTick;
import gg.vape.event.impl.EventPostTick;
import gg.vape.event.impl.EventPreEntityRendererMouseUpdate;
import gg.vape.event.impl.EventPreLivingTravel;
import gg.vape.event.impl.EventPreLocalPlayerTick;
import gg.vape.event.impl.EventPreRenderTick;
import gg.vape.event.impl.EventPreTick;
import gg.vape.input.KeyboardInput;
import gg.vape.module.control.SharedModuleControlClaims;
import gg.vape.module.render.Freecam;
import gg.vape.utils.MathUtil;
import gg.vape.wrapper.impl.ActiveRenderInfo;
import gg.vape.wrapper.impl.EntityOtherPlayerMP;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.GameProfile;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.MovementInput;
import gg.vape.wrapper.impl.Vec3;
import java.util.UUID;

public class FreecamModernController extends FreecamController<Freecam> {
   private float cameraPitch;
   private float savedPrevRenderYaw;
   private float savedRenderYaw;
   private float bodyPitch;
   private double posX;
   private double lastMouseY;
   private float savedPitch;
   private float cameraYaw;
   private double posY;
   private double prevPosZ;
   private double prevPosY;
   private float savedYaw;
   private float savedPrevPitch;
   private double prevPosX;
   private float savedPrevRenderPitch;
   private float bodyYaw;
   private double lastMouseX;
   private boolean viewBobSaved;
   private boolean savedViewBobbing;
   private boolean active;
   private float savedRenderPitch;
   private double posZ;
   private boolean rotationSaved;
   private float savedPrevYaw;

   private void syncMouseHelper() {
      EntityPlayerSP player = Minecraft.thePlayer();
      if (!player.isNull()) {
         player.getFreecamPlayerBridge().resetBobbingState();
      }
   }

   private void handleMovement() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if (!Minecraft.currentScreen().isNotNull()) {
         double forward = ClientSettings.isPhysicalKeyDown(Minecraft.gameSettings().Y()) ? 1.0 : 0.0;
         if (ClientSettings.isPhysicalKeyDown(Minecraft.gameSettings().s())) {
            forward--;
         }

         double strafe = ClientSettings.isPhysicalKeyDown(Minecraft.gameSettings().x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg()) ? 1.0 : 0.0;
         if (ClientSettings.isPhysicalKeyDown(Minecraft.gameSettings().g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3())) {
            strafe--;
         }

         double vertical = ClientSettings.isPhysicalKeyDown(Minecraft.gameSettings().O()) ? 1.0 : 0.0;
         if (ClientSettings.isPhysicalKeyDown(Minecraft.gameSettings().d$src$Lgg_vape_wrapper_impl_KeyBinding_$adn2z0())) {
            vertical--;
         }

         double magnitude;
         if ((magnitude = Math.sqrt(forward * forward + strafe * strafe)) > 0.0) {
            forward /= magnitude;
            strafe /= magnitude;
            double speed = this.module.horizontalSpeed.getValue() / 5.0;
            if (ClientSettings.isPhysicalKeyDown(Minecraft.gameSettings().r())) {
               speed *= 2.0;
            }

            double yawRadians = Math.toRadians(this.cameraYaw + 90.0F);
            this.posX = this.posX + (forward * Math.cos(yawRadians) + strafe * Math.sin(yawRadians)) * speed;
            this.posZ = this.posZ + (forward * Math.sin(yawRadians) - strafe * Math.cos(yawRadians)) * speed;
         }

         if (vertical != 0.0) {
            double speed = this.module.verticalSpeed.getValue() / 5.0 * 0.42;
            if (ClientSettings.isPhysicalKeyDown(Minecraft.gameSettings().r())) {
               speed *= 2.0;
            }

            this.posY += vertical * speed;
         }
      }
   }

   public FreecamModernController(Freecam freecam) {
      super(freecam);
   }

   private void updateFakePlayer() {
      if (!this.module.spawnFakePlayer.getEffectiveValue()) {
         this.module.removeFakePlayer();
      } else {
         if (this.module.fakePlayer != null
            && (this.module.fakePlayer.M$src$Z$ff28xj() || this.module.fakeEntityId == 0 || Minecraft.theWorld().V(this.module.fakeEntityId).isNull())) {
            ClientSettings.releaseReservedEntityId(this.module.fakeEntityId);
            this.module.fakeEntityId = 0;
            this.module.fakePlayer = null;
         }

         if (this.module.fakePlayer == null) {
            this.spawnFakePlayer();
         }

         EntityPlayerSP player;
         if (!(player = Minecraft.thePlayer()).isNull() && this.module.fakePlayer != null) {
            this.module.fakePlayer.t(player.z(), player.N(), player.h(), player.J(), player.V());
            this.module.fakePlayer.n(player.f());
            this.module.fakePlayer.w(player.H());
            this.module.fakePlayer.A(player.R());
            this.module.fakePlayer.C(player.M());
            this.module.fakePlayer.L(player.W());
            this.module.fakePlayer.s(player.m$src$D$fwnne5());
            this.module.fakePlayer.H(player.J());
            this.module.fakePlayer.D(player.j());
            this.module.fakePlayer.z(player.s());
            this.module.fakePlayer.o(player.P$src$F$14ztfk8());
            this.module.fakePlayer.C(player.V());
            this.module.fakePlayer.l(player.D());
            this.module.fakePlayer.U(player.b$src$Z$fqlxe4());
            this.module.fakePlayer.E(player.t(), player.q(), player.T());
         }
      }
   }

   private void applyFreecamRotation(EntityPlayerSP player) {
      if (!this.rotationSaved) {
         this.savedRenderYaw = player.q$src$F$1u6qsjx();
         this.savedRenderPitch = player.t$src$F$1u8e6c0();
         this.savedPrevRenderYaw = player.x$src$F$1ualcpg();
         this.savedPrevRenderPitch = player.n$src$F$1u53eru();
         this.rotationSaved = true;
      }

      player.F(this.cameraYaw);
      player.d(this.cameraPitch);
      player.E(this.cameraYaw);
      player.a(this.cameraPitch);
   }

   private void suppressViewBobbing() {
      if (!this.isActiveAndEnabled()) {
         this.restoreViewBobbing();
      } else {
         this.syncMouseHelper();
         if (!this.viewBobSaved) {
            this.savedViewBobbing = Minecraft.gameSettings().k();
            this.viewBobSaved = true;
         }

         Minecraft.gameSettings().O(false);
      }
   }

   @EventHandler
   public void onTick(EventPreTick event) {
      if (Minecraft.theWorld().isNull() || Minecraft.thePlayer().isNull() || Minecraft.thePlayer().w$src$F$15l9epb() <= 0.0F) {
         this.module.setEnabled(false, false);
      }

      if (this.module.disableRequested) {
         this.restorePlayerView();
         this.module.removeFakePlayer();
         this.module.completeDisable();
      } else {
         this.module.rotationClaim.acquire(this.module);
         if (this.module.initializationPending || !this.active) {
            this.captureState();
         }

         this.handleMovement();
         this.updateFakePlayer();
         this.applyMovementInput();
      }
   }

   private double mouseSensitivityFactor() {
      double sensitivity = Minecraft.gameSettings().y() * 0.6 + 0.2;
      return sensitivity * sensitivity * sensitivity * 8.0;
   }

   @EventHandler
   public void onPreRenderTick(EventPreRenderTick event) {
      if (this.active) {
         this.suppressViewBobbing();
         this.restorePlayerRotation();
         if (Minecraft.currentScreen().isNotNull()) {
            this.lastMouseX = Minecraft.s().R();
            this.lastMouseY = Minecraft.s().b();
         } else {
            this.updateMouseLook();
         }
      }
   }

   @EventHandler
   public void onEntityRendererMouseUpdate(EventEntityRendererMouseUpdate event) {
      if (this.active) {
         this.updateRenderViewPosition(event.getPartialTicks());
      }
   }

   private void spawnFakePlayer() {
      EntityPlayerSP player = Minecraft.thePlayer();
      if (!player.isNull() && !Minecraft.theWorld().isNull()) {
         GameProfile profile = GameProfile.create(UUID.randomUUID(), player.getName());
         EntityOtherPlayerMP fakePlayer = EntityOtherPlayerMP.create(Minecraft.theWorld(), profile);
         fakePlayer.M(player, true);
         this.module.fakeEntityId = ClientSettings.reserveEntityId();
         fakePlayer.Q(this.module.fakeEntityId);
         fakePlayer.y(UUID.randomUUID());
         fakePlayer.t(player.z(), player.N(), player.h(), player.J(), player.V());
         fakePlayer.z(player.s());
         fakePlayer.o(player.P$src$F$14ztfk8());
         this.module.fakePlayer = fakePlayer;
         Minecraft.theWorld().D(this.module.fakeEntityId, fakePlayer);
      }
   }

   @Override
   public void onEnable() {
      this.active = false;
      this.module.initializationPending = true;
      this.captureState();
   }

   private void updateMouseLook() {
      double mouseX = Minecraft.s().R();
      double mouseY = Minecraft.s().b();
      double deltaX = mouseX - this.lastMouseX;
      double deltaY = mouseY - this.lastMouseY;
      this.lastMouseX = mouseX;
      this.lastMouseY = mouseY;
      double yawDelta = deltaX * this.mouseSensitivityFactor() * 0.15;
      double pitchDelta = deltaY * this.mouseSensitivityFactor() * 0.15;
      this.cameraYaw += (float)yawDelta;
      this.cameraPitch += (float)pitchDelta;
      this.cameraPitch = MathUtil.clamp(this.cameraPitch, -90.0F, 90.0F);
   }

   @EventHandler
   public void onPostEntityRendererMouseUpdate(EventPostEntityRendererMouseUpdate event) {
      if (this.active) {
         this.restorePlayerRotation();
      }
   }

   @EventHandler
   public void onPreLocalPlayerTick(EventPreLocalPlayerTick event) {
      if (this.active) {
         this.applyMovementInput();
      }
   }

   private void updateRenderViewPosition(float partialTicks) {
      ActiveRenderInfo activeRenderInfo = Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().l();
      if (!activeRenderInfo.isNull()) {
         double renderX = this.prevPosX + (this.posX - this.prevPosX) * partialTicks;
         double renderY = this.prevPosY + (this.posY - this.prevPosY) * partialTicks;
         double renderZ = this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks;
         activeRenderInfo.g(Vec3.create(renderX, renderY, renderZ));
      }
   }

   @EventHandler
   public void onKeyBindingState(EventKeyBindingState event) {
      if (this.active && event.isPressed() && event.getKeyBinding() != null) {
         if (this.isMovementKey(event.getKeyBinding())) {
            event.setCancelled(true);
         }
      }
   }

   @EventHandler
   public void onPreLivingTravel(EventPreLivingTravel event) {
      if (this.active) {
         this.applyMovementInput();
      }
   }

   @EventHandler
   public void onPreEntityRendererMouseUpdate(EventPreEntityRendererMouseUpdate event) {
      if (this.active) {
         this.applyCameraToPlayer();
      }
   }

   private void applyCameraToPlayer() {
      if (!SharedModuleControlClaims.renderPass.isRenderBlocked()) {
         EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
         if (!entityPlayerSP.isNull() && !Minecraft.theWorld().isNull()) {
            entityPlayerSP.H(this.cameraYaw);
            entityPlayerSP.D(this.cameraYaw);
            entityPlayerSP.C(this.cameraPitch);
            entityPlayerSP.l(this.cameraPitch);
            this.applyFreecamRotation(entityPlayerSP);
         }
      }
   }

   private void restorePlayerRotation() {
      EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
      if (!entityPlayerSP.isNull() && !Minecraft.theWorld().isNull()) {
         entityPlayerSP.H(this.bodyYaw);
         entityPlayerSP.D(this.bodyYaw);
         entityPlayerSP.z(this.bodyYaw);
         entityPlayerSP.o(this.bodyYaw);
         entityPlayerSP.C(this.bodyPitch);
         entityPlayerSP.l(this.bodyPitch);
         this.restoreRenderRotation(entityPlayerSP);
      }
   }

   private void captureState() {
      EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
      if (!entityPlayerSP.isNull() && !Minecraft.theWorld().isNull()) {
         this.savedYaw = entityPlayerSP.J();
         this.savedPitch = entityPlayerSP.V();
         this.savedPrevYaw = entityPlayerSP.j();
         this.savedPrevPitch = entityPlayerSP.D();
         this.bodyYaw = this.savedYaw;
         this.bodyPitch = this.savedPitch;
         this.cameraYaw = this.savedYaw;
         this.cameraPitch = this.savedPitch;
         this.posX = entityPlayerSP.z();
         this.posY = entityPlayerSP.N() + entityPlayerSP.X();
         this.posZ = entityPlayerSP.h();
         this.prevPosX = this.posX;
         this.prevPosY = this.posY;
         this.prevPosZ = this.posZ;
         this.lastMouseX = Minecraft.s().R();
         this.lastMouseY = Minecraft.s().b();
         this.active = true;
         this.module.initializationPending = false;
      }
   }

   private void restoreViewBobbing() {
      if (this.viewBobSaved) {
         Minecraft.gameSettings().O(this.savedViewBobbing);
         this.viewBobSaved = false;
      }
   }

   private void restorePlayerView() {
      EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
      if (!entityPlayerSP.isNull() && !Minecraft.theWorld().isNull()) {
         entityPlayerSP.H(this.savedYaw);
         entityPlayerSP.D(this.savedPrevYaw);
         entityPlayerSP.z(this.savedYaw);
         entityPlayerSP.o(this.savedPrevYaw);
         entityPlayerSP.C(this.savedPitch);
         entityPlayerSP.l(this.savedPrevPitch);
         this.restoreRenderRotation(entityPlayerSP);
      }
   }

   @Override
   public void onDisable() {
      this.restoreViewBobbing();
      this.restorePlayerRotation();
      this.module.removeFakePlayer();
      this.active = false;
      this.module.initializationPending = false;
      this.module.disableRequested = false;
   }

   private boolean isMovementKey(Object keyBindingHandle) {
      KeyBinding keyBinding = new KeyBinding(keyBindingHandle);
      return keyBinding.equals(Minecraft.gameSettings().Y())
         || keyBinding.equals(Minecraft.gameSettings().s())
         || keyBinding.equals(Minecraft.gameSettings().x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg())
         || keyBinding.equals(Minecraft.gameSettings().g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3())
         || keyBinding.equals(Minecraft.gameSettings().O())
         || keyBinding.equals(Minecraft.gameSettings().d$src$Lgg_vape_wrapper_impl_KeyBinding_$adn2z0())
         || keyBinding.equals(Minecraft.gameSettings().r());
   }

   private void applyMovementInput() {
      EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
      if (!entityPlayerSP.isNull() && !Minecraft.theWorld().isNull()) {
         float forward = 0.0F;
         float strafe = 0.0F;
         if (this.module.spawnFakePlayer.getEffectiveValue() && this.module.moveFakePlayer.getEffectiveValue() && Minecraft.currentScreen().isNull()) {
            if (KeyboardInput.isKeyDown(38)) {
               forward++;
            }

            if (KeyboardInput.isKeyDown(40)) {
               forward--;
            }

            if (KeyboardInput.isKeyDown(37)) {
               strafe++;
            }

            if (KeyboardInput.isKeyDown(39)) {
               strafe--;
            }
         }

         MovementInput movementInput = entityPlayerSP.movementInput();
         movementInput.B(forward);
         movementInput.M(strafe);
         movementInput.V(false);
         movementInput.setCancelled(false);
         movementInput.getFreecamInput().setSprinting(false);
         entityPlayerSP.M(forward);
         entityPlayerSP.k$src$V$5315b7(strafe);
         entityPlayerSP.R(false);
      }
   }

   @EventHandler
   public void onPostRenderTick(EventPostRenderTick event) {
      if (this.active) {
         this.restorePlayerRotation();
         this.restoreViewBobbing();
      }
   }

   private void restoreRenderRotation(EntityPlayerSP player) {
      if (this.rotationSaved) {
         player.F(this.savedRenderYaw);
         player.d(this.savedRenderPitch);
         player.E(this.savedPrevRenderYaw);
         player.a(this.savedPrevRenderPitch);
         this.rotationSaved = false;
      }
   }

   private boolean isActiveAndEnabled() {
      return this.active && this.module.spawnFakePlayer.getEffectiveValue() && this.module.moveFakePlayer.getEffectiveValue();
   }

   @EventHandler
   public void onPostTick(EventPostTick event) {
      if (this.active) {
         this.applyMovementInput();
      }
   }
}
