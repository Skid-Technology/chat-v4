package gg.vape.friend.ui;

import gg.vape.value.BooleanValue;

class OnlineFriendNotificationsValue extends BooleanValue {
   private final OnlineFriendActionPanel actionPanel;

   public Boolean getValue() {
      return OnlineFriendActionPanel.getFriend(this.actionPanel) == null ? false : OnlineFriendActionPanel.getFriend(this.actionPanel).isSyncWithFriends();
   }

   public void setValue(Boolean value) {
      super.setValue(value);
      OnlineFriendActionPanel.getFriend(this.actionPanel).setSyncWithFriends(value);
   }

   OnlineFriendNotificationsValue(OnlineFriendActionPanel actionPanel, Object owner, String name, boolean defaultValue) {
      super(owner, name, defaultValue);
      this.actionPanel = actionPanel;
   }
}
