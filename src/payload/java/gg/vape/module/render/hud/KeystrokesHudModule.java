package gg.vape.module.render.hud;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventKeyPress;
import gg.vape.event.impl.EventMouseButton;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.frame.impl.hud.KeystrokesHudFrame;
import gg.vape.unmap.ModeOption;
import gg.vape.value.BooleanValue;
import gg.vape.value.ModeValue;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;

public class KeystrokesHudModule extends HudModule {
   public final ModeOption keyboardKeyStyle = new ModeOption("Keyboard");
   public final ModeOption mouseIconStyle;
   public final ModeValue mouseStyle;
   public final ModeValue keyStyle;
   public final BooleanValue showCpsOnly;
   public final ModeOption mouseButtonStyle;
   public final BooleanValue showSpacebar;
   public final ModeOption arrowKeyStyle = new ModeOption("Arrow");

   @EventHandler
   public void onKeyPress(EventKeyPress eventKeyPress) {
      KeystrokesHudFrame keystrokesHudFrame = ClientSettings.getFrame(KeystrokesHudFrame.class);
      if (keystrokesHudFrame != null) {
         keystrokesHudFrame.handleKeyEvent(eventKeyPress);
      }
   }

   public KeystrokesHudModule() {
      super("Keystrokes", HudModuleGroup.HUD, "keystrokes", KeystrokesHudFrame.class);
      this.keyStyle = ModeValue.create(this, "Key Style", this.keyboardKeyStyle, this.keyboardKeyStyle, this.arrowKeyStyle);
      this.mouseButtonStyle = new ModeOption("Button");
      this.mouseIconStyle = new ModeOption("Icon");
      this.mouseStyle = ModeValue.create(this, "Mouse Style", this.mouseButtonStyle, this.mouseButtonStyle, this.mouseIconStyle);
      this.showSpacebar = BooleanValue.create(this, "Show Spacebar", true);
      this.showCpsOnly = BooleanValue.create(this, "Show CPS Only", false);
      this.setSuffix("Shows when your movement keys or mouse buttons are pressed\nAs well as mouse clicks per second");
      this.addValue(this.keyStyle, this.mouseStyle, this.showSpacebar, this.showCpsOnly);
   }

   public void updateKeyState(KeyBinding keyBinding, boolean pressed) {
      KeystrokesHudFrame keystrokesHudFrame = ClientSettings.getFrame(KeystrokesHudFrame.class);
      if (keystrokesHudFrame != null) {
         keystrokesHudFrame.updateKeyState(keyBinding, pressed);
      }
   }

   @EventHandler
   public void onMouseButton(EventMouseButton eventMouseButton) {
      KeystrokesHudFrame keystrokesHudFrame = ClientSettings.getFrame(KeystrokesHudFrame.class);
      if (keystrokesHudFrame != null) {
         keystrokesHudFrame.handleMouseEvent(eventMouseButton);
         if (eventMouseButton.getButtonState()) {
            if (!Minecraft.currentScreen().isNotNull()) {
               keystrokesHudFrame.getCpsCounter().recordClick(eventMouseButton.getButton());
            }
         }
      }
   }
}
