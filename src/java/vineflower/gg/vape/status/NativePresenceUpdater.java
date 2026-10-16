package gg.vape.status;

import gg.vape.Vape;
import gg.vape.module.none.TextGuiSettings;
import gg.vape.runtime.NativeBridge;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.TimerUtil;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.ServerData;

public class NativePresenceUpdater {
   private final TimerUtil updateTimer = new TimerUtil();
   private static GuiComponent[] controlFlowMarker;
   private String lastServerDescription;
   private String lastClientDescription;

   public void updatePresence(boolean enabled) {
      if (this.updateTimer.hasTimeElapsed(1000L)) {
         this.updateTimer.reset();
         if (!enabled) {
            NativeBridge.updc(null, null);
         } else {
            ServerData serverData = Minecraft.H();
            String serverDescription = "Not in a server";
            if (serverData.isNotNull()) {
               serverDescription = "Playing legit on " + serverData.getServerIp();
            }

            String clientDescription = Vape.INSTANCE.getModManager().getMod(TextGuiSettings.class).getEnabledModuleNames();
            if (clientDescription.length() >= 128) {
               clientDescription = clientDescription.substring(0, 128);
            }

            if (!serverDescription.equals(this.lastServerDescription) || !this.lastClientDescription.equals(clientDescription)) {
               NativeBridge.updc(serverDescription, clientDescription);
            }

            this.lastServerDescription = serverDescription;
            this.lastClientDescription = clientDescription;
         }
      }
   }

   public static void setControlFlowMarker(GuiComponent[] marker) {
      controlFlowMarker = marker;
   }

   public static GuiComponent[] getControlFlowMarker() {
      return controlFlowMarker;
   }

   static {
      setControlFlowMarker(new GuiComponent[5]);
   }
}
