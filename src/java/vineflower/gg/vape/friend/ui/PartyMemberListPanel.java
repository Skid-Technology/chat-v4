package gg.vape.friend.ui;

import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.WrappingTextLabelComponent;
import gg.vape.ui.click.layout.BottomUpFlowLayout;
import gg.vape.ui.click.layout.ComponentLayout;

public class PartyMemberListPanel extends PanelComponent {
   private static int obfuscationSeed;
   private final WrappingTextLabelComponent emptyStateLabel = new WrappingTextLabelComponent(
      "No messages yet\nMessages do not save and will clear when your game closes", 0.75, J.Z
   );

   public void addMessageRow(PartyMemberRow row) {
      if (row.isLocalUser()) {
         this.h(row, "alignright");
      } else {
         this.h(row);
      }
   }

   @Override
   public void c() {
      super.c();
      if (this.f().isEmpty()) {
         this.emptyStateLabel.setShowDisabledOverlay(false);
         this.emptyStateLabel.setFontScale(0.75);
         this.emptyStateLabel.K(this.G$src$D$1b2f02a() + 5.0);
         this.emptyStateLabel.S(this.n() + (this.L() - 40.0) / 2.0);
         this.emptyStateLabel.c();
      }
   }

   public static int getObfuscationSeed() {
      return obfuscationSeed;
   }

   public static int getReservedZero() {
      int reserved = getObfuscationSeed();
      return 0;
   }

   public static void setObfuscationSeed(int seed) {
      obfuscationSeed = seed;
   }

   public PartyMemberListPanel(double width, double height) {
      super(width, height);
      this.emptyStateLabel.setExplicitWidth(width - 10.0);
      this.t(this.L());
      this.N(true);
      this.setShowDisabledOverlay(false);
      this.N(new BottomUpFlowLayout(this));
      ComponentLayout componentLayout = this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij();
      componentLayout.t(false);
      componentLayout.M(false);
      componentLayout.U(false);
      componentLayout.I(false);
      componentLayout.u(false);
      this.w(true);
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
   }

   static {
      setObfuscationSeed(10);
   }
}
