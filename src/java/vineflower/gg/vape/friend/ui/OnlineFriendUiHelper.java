package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.OnlineFriend;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.notification.NotificationMessage;
import gg.vape.ui.notification.NotificationType;

public final class OnlineFriendUiHelper {
   private static final String MESSAGE_NOTIFICATION_PREFIX = "Message from §f";
   private static int obfuscationSeed;

   public static int getObfuscationSeed() {
      return obfuscationSeed;
   }

   public static void showNotification(NotificationType type, String message) {
      showNotification(new NotificationMessage(type, message));
   }

   public static void setObfuscationSeed(int seed) {
      obfuscationSeed = seed;
   }

   public static void refreshFriendLists() {
      ClientSettings.getFrame(OnlineFriendsFrame.class).getOnlineFriendEntriesPanel().requestRefresh();
      refreshMinecraftFriends();
   }

   public static OnlineFriendsFrame findOnlineFriendsFrame(GuiComponent component) {
      while (!component.getClass().equals(OnlineFriendsFrame.class)) {
         try {
            component = component.getParentFrameComponent();
         } catch (Exception var2) {
            break;
         }
      }

      return (OnlineFriendsFrame)component;
   }

   public static int getReservedTwentyOne() {
      int seed = getObfuscationSeed();
      return 21;
   }

   public static void addPartyChatMessage(PartyMemberRow messageRow) {
      OnlineFriendsFrame frame = ClientSettings.getFrame(OnlineFriendsFrame.class);
      PartyDetailsAndChatPanel detailsPanel = frame.getOnlineFriendsListPanel().getCurrentPartyPanel().getDetailsPanel();
      if (detailsPanel != null) {
         detailsPanel.getChatPanel().getMessageListPanel().addMessageRow(messageRow);
      }
   }

   public static void showNotification(NotificationMessage notification) {
      ClientSettings.getFrame(OnlineFriendsFrame.class).getNotificationOverlay().C(notification);
   }

   public static void refreshMinecraftFriends() {
      ClientSettings.getFrame(OnlineFriendsFrame.class).getMinecraftFriendEntriesPanel().requestRefresh();
   }

   public static void addFriendChatMessage(OnlineFriend conversationFriend, OnlineFriend sender, String message) {
      OnlineFriendListEntry listEntry = Vape.INSTANCE.getOnlineManager().getFriendCache().getListEntry(conversationFriend.getUser().getId());
      if (listEntry != null) {
         conversationFriend.setHasChatHistory(true);
         conversationFriend.setUnreadMessage(true);
         if (sender != null) {
            Vape.INSTANCE
               .getNotificationManager()
               .show(MESSAGE_NOTIFICATION_PREFIX + sender.getDisplayName(), message, gg.vape.notification.NotificationType.FRIENDS_NEW_CHAT, 4000L);
         }

         OnlineFriend messageSender = (OnlineFriend)(sender == null ? Vape.INSTANCE.getOnlineManager().getLocalFriend() : sender);
         ChatMessagePartyMemberRow messageRow = new ChatMessagePartyMemberRow(messageSender, new PartyMemberTextStatusComponent(message));
         listEntry.getFriendCard().getDetailsPanel().getChatPanel().getMessageListPanel().addMessageRow(messageRow);
         listEntry.getFriendCard().getDetailsPanel().getChatPanel().getMessageListPanel().l$src$V$1mibm4x();
         listEntry.getFriendCard().updateDisplayMode();
      }
   }

   static {
      setObfuscationSeed(0);
   }
}
