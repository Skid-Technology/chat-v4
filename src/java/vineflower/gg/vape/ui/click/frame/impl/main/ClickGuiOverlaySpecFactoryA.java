package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.frame.impl.online.OnlineAccountConnectedPageComponent;

public final class ClickGuiOverlaySpecFactoryA {
   private static void B(PanelComponent panelComponent) {
      OnlineAccountConnectedPageComponent onlineAccountConnectedPageComponent = new OnlineAccountConnectedPageComponent();

      for (GuiComponent guiComponent : onlineAccountConnectedPageComponent.f()) {
         panelComponent.h(guiComponent);
      }
   }

   private ClickGuiOverlaySpecFactoryA() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static ClickGuiOverlaySpec o() {
      return ClickGuiOverlaySpec.builder()
         .title("Vape Online Login")
         .sidecarIcon("login")
         .initializeContent(ClickGuiOverlaySpecFactoryA::B)
         .transitionMode(ClickGuiOverlayTransitionMode.REPLACE)
         .build();
   }
}
