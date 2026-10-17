package gg.vape.ui.click.frame.impl.online;

import gg.vape.manager.client.OnlineConnectionManager;
import gg.vape.ui.click.component.gui.UnderlinedTextLabel;
import gg.vape.ui.click.component.layout.PaddedComponent;
import java.awt.Color;

public class OnlineConnectionRetryPageComponent extends OnlineConnectionSettingsPageComponent {
   private UnderlinedTextLabel yr = new UnderlinedTextLabel("Cancel", 0.8F, J.A, new Color(255, 255, 255, 255));

   private static void cancelReconnect() {
      OnlineConnectionManager.INSTANCE.cancelConnectionAttempt();
   }

   @Override
   public void c() {
      super.c();
      long reconnectAt = OnlineConnectionManager.INSTANCE.getNextReconnectAt();
      if (reconnectAt != -1L) {
         int remainingSeconds = (int)((reconnectAt - System.currentTimeMillis()) / 1000L);
         this.getAlternateFontRenderer(0.8)
            .W(
               "Reconnecting in " + remainingSeconds + " second" + (remainingSeconds == 1 ? "" : "s") + "...",
               this.G$src$D$1b2f02a() + this.A() / 2.0,
               this.n() + 52.0,
               J.A
            );
      } else {
         this.getAlternateFontRenderer(1.0).W("Reconnecting...", this.G$src$D$1b2f02a() + this.A() / 2.0, this.n() + 52.0, J.A);
      }

      this.yr.setExplicitWidth(60.0);
   }

   public OnlineConnectionRetryPageComponent() {
      this.yr.addClickListener(OnlineConnectionRetryPageComponent::cancelReconnect);
      this.yr.o(50.0);
      this.yr.Y(10.0);
      this.h(new PaddedComponent(125.0, 0.0, 20.0, 0.0, this.yr));
   }

   @Override
   public void s() {
   }
}
