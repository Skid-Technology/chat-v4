package gg.vape.friend.ui;

import gg.vape.ui.click.component.FlowLayoutComponent;
import gg.vape.ui.click.component.PanelComponent;

public class OnlineFriendsListPanel extends PanelComponent {
   private static final String WRAP_LAYOUT = "wrap";
   private final PartyInviteCountBadge inviteCountBadge;
   private final PartyInvitesPanel invitesPanel;
   private final FlowLayoutComponent partyHeader;
   private final CurrentPartyPanel currentPartyPanel;
   private final OnlineFriendEntriesPanel friendEntriesPanel = new OnlineFriendEntriesPanel();
   private static int[] obfuscationValues;

   public PartyInvitesPanel getInvitesPanel() {
      return this.invitesPanel;
   }

   @Override
   public void c() {
      super.c();
      double entriesHeight = this.L() - this.partyHeader.L();
      this.friendEntriesPanel.t(entriesHeight);
   }

   public OnlineFriendEntriesPanel getFriendEntriesPanel() {
      return this.friendEntriesPanel;
   }

   public static void setObfuscationValues(int[] values) {
      obfuscationValues = values;
   }

   public OnlineFriendsListPanel() {
      super(104.0, 130.0);
      this.partyHeader = new FlowLayoutComponent(103.0);
      this.currentPartyPanel = new CurrentPartyPanel();
      this.inviteCountBadge = new PartyInviteCountBadge();
      this.invitesPanel = new PartyInvitesPanel();
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(WRAP_LAYOUT);
      this.partyHeader.h(this.currentPartyPanel);
      this.partyHeader.h(this.inviteCountBadge);
      this.partyHeader.h(this.invitesPanel);
      this.friendEntriesPanel.t(126.0);
      this.addChildren(this.partyHeader, this.friendEntriesPanel);
   }

   public CurrentPartyPanel getCurrentPartyPanel() {
      return this.currentPartyPanel;
   }

   public static int[] getObfuscationValues() {
      return obfuscationValues;
   }

   static {
      setObfuscationValues(new int[2]);
   }
}
