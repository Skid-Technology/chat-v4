package gg.vape.ui.click.frame.impl.online;

import gg.vape.manager.client.OnlineAccountState;
import gg.vape.manager.client.OnlineConnectionManager;
import gg.vape.ui.click.component.IconGlyphComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.WrappingTextLabelComponent;
import gg.vape.ui.click.component.gui.UnderlinedTextLabel;
import gg.vape.ui.click.component.layout.PaddedComponent;
import java.awt.Color;

public class OnlineAccountUnavailablePageComponent extends OnlineConnectionSettingsPageComponent {
   private final WrappingTextLabelComponent DX;
   private final UnderlinedTextLabel D2;

   public OnlineAccountUnavailablePageComponent() {
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
      IconGlyphComponent iconGlyphComponent = new IconGlyphComponent("warning_48", 12.0F, 12.0F);
      this.h(new PaddedComponent(20.0, 0.0, 45.0, 45.0, iconGlyphComponent));
      this.h(new SpacerComponent(0.0, 5.0));
      WrappingTextLabelComponent wrappingTextLabelComponent = new WrappingTextLabelComponent("Error establishing connection", 1.0);
      wrappingTextLabelComponent.setBold(true);
      wrappingTextLabelComponent.o(this.A() - 30.0);
      wrappingTextLabelComponent.Y(20.0);
      wrappingTextLabelComponent.setTextColor(J.q);
      this.h(new PaddedComponent(0.0, 0.0, 15.0, 15.0, wrappingTextLabelComponent));
      this.h(new SpacerComponent(0.0, 5.0));
      this.DX = new WrappingTextLabelComponent("Unknown", 0.8);
      this.DX.o(this.A() - 30.0);
      this.DX.Y(20.0);
      this.DX.setTextColor(J.A);
      this.h(new PaddedComponent(0.0, 0.0, 15.0, 15.0, this.DX));
      this.D2 = new UnderlinedTextLabel("Try again", 0.8F, J.A, new Color(255, 255, 255, 255));
      this.D2.addClickListener(OnlineAccountUnavailablePageComponent::lambda$new$0);
      this.D2.o(50.0);
      this.D2.Y(10.0);
      this.h(new PaddedComponent(40.0, 0.0, 26.0, 0.0, this.D2));
   }

   private static void lambda$new$0() {
      OnlineConnectionManager.INSTANCE.connect();
   }

   @Override
   public void s() {
      if (OnlineConnectionManager.INSTANCE.getAccountState().equals(OnlineAccountState.BANNED)) {
         this.DX.setText("You have been banned from Vape Online");
      } else if (OnlineConnectionManager.INSTANCE.getAccountState().equals(OnlineAccountState.REGISTRATION_OFFLINE)) {
         this.DX.setText("Registration is currently offline");
      } else {
         this.DX.setText("Unknown error");
      }
   }
}
