package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.FriendRequest;
import gg.vape.friend.FriendRequestService;
import gg.vape.friend.IncomingFriendRequest;
import gg.vape.ui.click.component.PanelComponent;
import gg.vape.ui.click.component.SpacerComponent;
import gg.vape.ui.click.component.TextInputComponentBase;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.notification.NotificationMessage;
import gg.vape.ui.notification.NotificationType;

public class FriendRequestsPanel extends PanelComponent {
   private final FriendRequestListPanel requestListPanel;
   private final TextInputComponentBase usernameInput;
   private final UsernameEditorPanel usernameEditor = new UsernameEditorPanel();

   static void submitUsername(FriendRequestsPanel panel, String username) {
      panel.handleUsernameSubmission(username);
   }

   public FriendRequestListPanel getRequestListPanel() {
      return this.requestListPanel;
   }

   public FriendRequestsPanel() {
      super(105.0, 150.0);
      this.usernameInput = new FriendRequestUsernameInputComponent(this, "Add Vape friend...");
      this.setDisabledOverlayColor(J.i);
      this.l$src$Lgg_vape_ui_click_layout_ComponentLayout_$di1tij().M("wrap");
      this.usernameInput.setVisible(true);
      this.h(this.usernameEditor);
      this.h(new SpacerComponent(1.0, 2.0));
      this.h(this.usernameInput);
      this.requestListPanel = new FriendRequestListPanel();
      this.h(new PaddedComponent(3.0, this.requestListPanel));
   }

   private void handleUsernameSubmission(String username) {
      String submittedUsername = username;
      if (!username.isEmpty()) {
         for (FriendRequest friendRequest : Vape.INSTANCE.getOnlineManager().getFriendRequestManager().getIncomingRequests()) {
            if (friendRequest.getFriend().getDisplayName().equals(submittedUsername)) {
               OnlineFriendUiHelper.showNotification(new NotificationMessage(NotificationType.SUCCESS, "Added " + submittedUsername + " as a friend"));
               Vape.INSTANCE.getOnlineManager().getFriendRequestManager().acceptIncomingRequest((IncomingFriendRequest)friendRequest);
               return;
            }
         }

         FriendRequestService.sendFriendRequest(submittedUsername);
      }
   }

   @Override
   public void c() {
      super.c();
   }
}
