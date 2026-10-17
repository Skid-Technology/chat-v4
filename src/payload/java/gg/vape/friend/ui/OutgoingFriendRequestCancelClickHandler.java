package gg.vape.friend.ui;

import gg.vape.ui.click.component.GuiClickListener;

public class OutgoingFriendRequestCancelClickHandler implements GuiClickListener {
   final OutgoingFriendRequestRow row;

   public OutgoingFriendRequestCancelClickHandler(OutgoingFriendRequestRow row) {
      this.row = row;
   }

   @Override
   public void onPrimaryClick() {
      OutgoingFriendRequestRow.cancel(this.row);
   }
}
