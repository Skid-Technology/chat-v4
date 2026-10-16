package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.PartyInvite;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.frame.FrameScrollbarPlacement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PartyInvitesPanel extends PanelComponent {
   private static final String WRAP_LAYOUT = "wrap";
   private double contentHeight;
   private final Map<PartyInvite, PartyInviteRow> inviteRows = new LinkedHashMap<>();
   private static String obfuscationName;
   private boolean expanded;

   @Override
   public void v() {
   }

   public void addInviteRow(PartyInviteRow inviteRow) {
      synchronized (this.inviteRows) {
         this.inviteRows.put(inviteRow.getInvite(), inviteRow);
         this.h(inviteRow);
         this.updateRowVisibility();
      }
   }

   public void removeInvite(PartyInvite invite) {
      synchronized (this.inviteRows) {
         PartyInviteRow inviteRow = this.inviteRows.get(invite);
         if (inviteRow != null) {
            this.removeInviteRow(inviteRow);
         }
      }
   }

   @Override
   public void g(GuiMouseEvent guiMouseEvent) {
   }

   public void toggleExpanded() {
      this.expanded = !this.expanded;
      this.N(this.expanded);
      this.updateRowVisibility();
   }

   public void removeInviteRow(PartyInviteRow inviteRow) {
      synchronized (this.inviteRows) {
         this.inviteRows.remove(inviteRow.getInvite());
         this.removeChild(inviteRow);
         this.updateRowVisibility();
      }
   }

   @Override
   public double C() {
      return this.contentHeight;
   }

   @Override
   public double x() {
      return 100.0;
   }

   @Override
   public void V() {
   }

   public static void setObfuscationName(String name) {
      obfuscationName = name;
   }

   public PartyInvitesPanel() {
      super(100.0, 0.0);
      this.setShowDisabledOverlay(false);
      this.F(FrameScrollbarPlacement.OUTSIDE);
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(WRAP_LAYOUT);
   }

   public void updateRowVisibility() {
      List<GuiComponent> rows = this.f();

      for (int i = 0; i < rows.size(); i++) {
         if (i == 0) {
            rows.get(i).setVisible(true);
         } else {
            rows.get(i).setVisible(this.expanded);
         }
      }

      this.W(0.0);
   }

   @Override
   public void H() {
      int n = Vape.INSTANCE.getOnlineManager().getPartyManager().getInvites().size();
      this.contentHeight = n < 1 ? 1.0 : (n < 2 ? 17.0 : (this.expanded ? 48.0 : 17.0));
      this.t(this.contentHeight);
   }

   @Override
   public void Y() {
   }

   public static String getName() {
      return obfuscationName;
   }

   static {
      setObfuscationName("KA2HLb");
   }
}
