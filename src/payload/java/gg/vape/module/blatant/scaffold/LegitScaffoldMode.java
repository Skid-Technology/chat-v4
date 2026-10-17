package gg.vape.module.blatant.scaffold;

import gg.vape.config.ClientSettings;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPostTick;
import gg.vape.event.impl.EventPreEntityUpdate;
import gg.vape.input.KeyBindingHelper;
import gg.vape.module.Mod;
import gg.vape.module.SubModule;
import gg.vape.module.blatant.Scaffold;
import gg.vape.rotation.RotationManager;
import gg.vape.utils.TimerUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.RandomValue;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.MovementInput;

public class LegitScaffoldMode extends SubModule<Scaffold> {
   private long sneakDelayMs;
   private boolean sneakKeyWasDown;
   private Scaffold scaffold;
   private final TimerUtil standDelayTimer;
   protected final BooleanValue requireSneak;
   private final RandomValue sneakDelay = RandomValue.createWithDescription(
      this, "Sneak delay", "#", "", 0.0, 100.0, 200.0, 500.0, 1.0, "Delay until standing after sneaking"
   );
   private final TimerUtil edgeSneakTimer;

   public boolean shouldScaffold(EntityPlayerSP player) {
      if (Minecraft.currentScreen().isNotNull()) {
         return false;
      } else if (this.requireSneak.getEffectiveValue()
         && !ClientSettings.isPhysicalKeyDown(Minecraft.gameSettings().d$src$Lgg_vape_wrapper_impl_KeyBinding_$adn2z0())) {
         return false;
      } else if (player.S$src$Z$151gttj()) {
         return false;
      } else {
         return this.scaffold.isPitchCheckEnabled() && player.V() < this.scaffold.getPitchThreshold() ? false : this.getParent().canActivate();
      }
   }

   public LegitScaffoldMode(Mod parent, String name) {
      super(parent, name);
      this.requireSneak = BooleanValue.create(this, "Require sneak", false, "Must be holding sneak to scaffold");
      this.scaffold = this.getParent();
      this.edgeSneakTimer = new TimerUtil();
      this.standDelayTimer = new TimerUtil();
      this.addValue(this.sneakDelay, this.requireSneak);
   }

   @Override
   public void onEnable() {
      this.sneakDelayMs = (long)this.sneakDelay.getRandomValue();
   }

   @EventHandler
   public void onPreEntityUpdate(EventPreEntityUpdate eventPreEntityUpdate) {
      if (eventPreEntityUpdate.getEntity().equals(Minecraft.thePlayer())) {
         String threadName = "Client thread";
         if (ForgeVersion.MC_1_16_5.d()) {
            threadName = "Render thread";
         }

         if (Thread.currentThread().getName().equals(threadName)) {
            EntityPlayerSP player = Minecraft.thePlayer();
            if (this.shouldScaffold(player)) {
               MovementInput movementInput = player.movementInput();
               GameSettings gameSettings = Minecraft.gameSettings();
               KeyBinding sneakKey = gameSettings.d$src$Lgg_vape_wrapper_impl_KeyBinding_$adn2z0();
               this.sneakKeyWasDown = ClientSettings.isPhysicalKeyDown(sneakKey);
               boolean shouldSneak = false;
               float forwardInput = movementInput.D();
               if (RotationManager.INSTANCE.hasAdaptiveController()) {
                  forwardInput = 0.0F;
                  if (ClientSettings.isPhysicalKeyDown(gameSettings.Y())) {
                     forwardInput++;
                  }

                  if (ClientSettings.isPhysicalKeyDown(gameSettings.s())) {
                     forwardInput--;
                  }
               }

               boolean atEdge = forwardInput <= 0.0F;
               if (forwardInput > 0.0F) {
                  atEdge = false;
               }

               if (atEdge && player.b$src$Z$fqlxe4()) {
                  AxisAlignedBB boundingBox;
                  if (ForgeVersion.MC_1_8_9.d()) {
                     boundingBox = player.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl();
                  } else {
                     AxisAlignedBB currentBox = player.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl();
                     boundingBox = currentBox.copy();
                  }

                  double motionX = player.t();
                  double offsetY = -1.0;
                  double motionZ = player.T();
                  AxisAlignedBB checkBox = boundingBox.expand(-0.2, 0.0, -0.2).k(motionX, offsetY, motionZ);
                  int collisionCount = Minecraft.theWorld().i(player, checkBox).size();
                  if (collisionCount == 0) {
                     shouldSneak = true;
                  }
               }

               boolean skipTimerReset = false;
               if (!shouldSneak && !this.edgeSneakTimer.hasTimeElapsed(this.sneakDelayMs) && this.sneakDelayMs > 30L) {
                  shouldSneak = true;
                  skipTimerReset = true;
               }

               if (player.b$src$Z$fqlxe4()) {
                  if (shouldSneak) {
                     if (!player.P()) {
                        this.sneakDelayMs = (long)this.sneakDelay.getRandomValue();
                     }

                     KeyBindingHelper.setPressedAndTick(sneakKey, true);
                     this.standDelayTimer.reset();
                     if (!skipTimerReset) {
                        this.edgeSneakTimer.reset();
                     }
                  } else if (this.requireSneak.getEffectiveValue()) {
                     if (!this.standDelayTimer.hasTimeElapsed(1000L) && forwardInput < 0.0F) {
                        KeyBindingHelper.setPressedAndTick(sneakKey, false);
                     }
                  } else if (!this.sneakKeyWasDown) {
                     KeyBindingHelper.setPressedAndTick(sneakKey, false);
                  }
               }
            }
         }
      }
   }

   @EventHandler
   public void onPostTick(EventPostTick eventPostTick) {
      EntityPlayerSP player = Minecraft.thePlayer();
      if (this.shouldScaffold(player)) {
         GameSettings gameSettings = Minecraft.gameSettings();
         KeyBinding sneakKey = gameSettings.d$src$Lgg_vape_wrapper_impl_KeyBinding_$adn2z0();
         KeyBindingHelper.setPressedAndTick(sneakKey, this.sneakKeyWasDown);
      }
   }
}
