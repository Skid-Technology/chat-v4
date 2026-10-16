package gg.vape.friend.ui;

import gg.vape.ui.click.component.GuiClickListener;

public class OnlineFriendsFrameConditionalPopupCloseClickHandler implements GuiClickListener {
   private final OnlineFriendsFrame friendsFrame;

   @Override
   public void onPrimaryClick() {
      if (OnlineFriendsFrame.getFriendRequestsPopup(this.friendsFrame) != null) {
         OnlineFriendsFrame.toggleFriendRequestsPopup(this.friendsFrame);
      }
   }

   public OnlineFriendsFrameConditionalPopupCloseClickHandler(OnlineFriendsFrame onlineFriendsFrame) {
      this.friendsFrame = onlineFriendsFrame;
   }
}
