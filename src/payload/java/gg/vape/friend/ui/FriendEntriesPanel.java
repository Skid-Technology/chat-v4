package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.ExternalFriend;
import gg.vape.friend.FriendEntry;
import gg.vape.friend.OnlineStatus;
import gg.vape.manager.client.OnlineConnectionManager;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.frame.FrameScrollbarPlacement;

public class FriendEntriesPanel extends PanelComponent {
   private static final String LAYOUT_MODE = "wrap";
   private double contentHeight;
   private static boolean obfuscationFlag;
   private boolean refreshPending;

   @Override
   public double x() {
      return 0.0;
   }

   public FriendEntriesPanel() {
      super(99.0, 110.0);
      this.F(FrameScrollbarPlacement.INSIDE);
      this.setShowDisabledOverlay(false);
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M(LAYOUT_MODE);
   }

   @Override
   public void c() {
      super.c();
      if (this.refreshPending) {
         this.refreshList();
         this.refreshPending = false;
      }
   }

   @Override
   public void g(GuiMouseEvent guiMouseEvent) {
   }

   public void removeEntry(FriendListEntryRow friendListEntryRow) {
      this.removeChild(friendListEntryRow);
   }

   private void refreshList() {
      this.removeMarkedChildren();

      for (FriendEntry friendEntry : Vape.INSTANCE.getFriendManager().getFriends()) {
         if (!(friendEntry instanceof ExternalFriend)
            || ((ExternalFriend)friendEntry).getOnlineFriend().getStatus() != OnlineStatus.OFFLINE
               && !OnlineConnectionManager.INSTANCE.isManualDisconnectRequested()) {
            FriendListEntryRow entryRow = new FriendListEntryRow(friendEntry);
            this.h(entryRow);
         }
      }
   }

   @Override
   public void V() {
      double d = 0.0;

      for (GuiComponent guiComponent : this.f()) {
         if (guiComponent.V$src$Z$1xhop3l()) {
            d += guiComponent.L();
         }
      }

      this.contentHeight = d;
      this.t(112.0);
   }

   public static boolean isObfuscationFlagSet() {
      return obfuscationFlag;
   }

   @Override
   public void Y() {
   }

   @Override
   public void v() {
   }

   public void addEntry(FriendListEntryRow friendListEntryRow) {
      this.h(friendListEntryRow);
   }

   @Override
   public double C() {
      return this.contentHeight;
   }

   public void requestRefresh() {
      this.refreshPending = true;
   }

   public static void setObfuscationFlag(boolean flag) {
      obfuscationFlag = flag;
   }

   public static boolean getObfuscationConstant() {
      boolean flag = isObfuscationFlagSet();
      return false;
   }

   static {
      setObfuscationFlag(true);
   }
}
