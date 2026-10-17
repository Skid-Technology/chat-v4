package gg.vape.notification;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.manager.client.OnlineConnectionManager;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MouseButton;
import gg.vape.utils.MathUtil;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.value.BooleanValue;
import gg.vape.wrapper.impl.Minecraft;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

public class NotificationManager implements EventListener {
   private final Map<NotificationGroup, BooleanValue> groupSettings;
   private final ArrayBlockingQueue<INotification> notifications = new ArrayBlockingQueue<>(20);
   private long lastRenderTime;
   private static int controlFlowState;
   private final Map<NotificationType, SoundClip> notificationSounds = new HashMap<>();

   private boolean isAllowedByFriendSettings(INotification notification) {
      FriendNotificationSettings friendNotificationSettings = OnlineConnectionManager.INSTANCE.getSettings().getFriendNotificationSettings();
      if (friendNotificationSettings == null) {
         return true;
      } else if (notification.getType() == NotificationType.FRIENDS_GENERAL
         && friendNotificationSettings.general != null
         && !friendNotificationSettings.general.getEffectiveValue()) {
         return false;
      } else if (notification.getType() == NotificationType.FRIENDS_NEW_REQUEST
         && friendNotificationSettings.friendRequests != null
         && !friendNotificationSettings.friendRequests.getEffectiveValue()) {
         return false;
      } else if (notification.getType() == NotificationType.FRIENDS_NEW_CHAT
         && friendNotificationSettings.chats != null
         && !friendNotificationSettings.chats.getEffectiveValue()) {
         return false;
      } else if (notification.getType() == NotificationType.FRIENDS_ONLINE
         && friendNotificationSettings.friendOnline != null
         && !friendNotificationSettings.friendOnline.getEffectiveValue()) {
         return false;
      } else if (notification.getType() == NotificationType.FRIENDS_PARTY_GENERAL
         && friendNotificationSettings.partyInviteAccepted != null
         && !friendNotificationSettings.partyInviteAccepted.getEffectiveValue()) {
         return false;
      } else {
         return notification.getType() == NotificationType.FRIENDS_PARTY_INVITE ? true : true;
      }
   }

   public void show(String title, String message, NotificationType type, long durationMillis, boolean force) {
      this.enqueue(new Notification(type, title, new TextNotificationContent(message), 0.0, 0.0, durationMillis), force);
   }

   public ArrayBlockingQueue<INotification> getNotifications() {
      return this.notifications;
   }

   private boolean isGroupEnabled(NotificationGroup group) {
      return !this.groupSettings.containsKey(group) ? true : this.groupSettings.get(group).getEffectiveValue();
   }

   public void renderNotifications() {
      int unusedControlFlowState = getControlFlowState();
      if (!Minecraft.theWorld().isNull() && !Minecraft.thePlayer().isNull()) {
         int previousAlphaTestFunction = OpenGlBackendHolder.backend.getIntegerState(3009);
         float previousAlphaTestReference = OpenGlBackendHolder.backend.getFloat(3010);
         OpenGlBackendHolder.backend.setAlphaFunction(516, 0.0F);
         OpenGlBackendHolder.backend.pushMatrix();
         double nextTargetY = -14.0;
         long now = System.currentTimeMillis();
         long elapsedMillis = now - this.lastRenderTime;
         this.lastRenderTime = now;
         ArrayList<INotification> expiredNotifications = new ArrayList<>();

         for (INotification notification : this.notifications) {
            nextTargetY -= notification.getHeight() + 2.0;
            notification.setTargetY(nextTargetY);
            int horizontalStep = (int)(Math.abs(notification.getTargetX() - notification.getCurrentX()) * 0.3);
            int verticalStep = (int)(Math.abs(notification.getTargetY() - notification.getCurrentY()) * 0.3);
            double currentX = interpolateToward(notification.getTargetX(), notification.getCurrentX(), elapsedMillis, horizontalStep);
            double currentY = interpolateToward(notification.getTargetY(), notification.getCurrentY(), elapsedMillis, verticalStep);
            notification.setCurrentX(currentX);
            notification.setCurrentY(currentY);
            notification.render();
            if (notification.shouldRemove()) {
               expiredNotifications.add(notification);
            }
         }

         for (INotification notificationx : expiredNotifications) {
            this.notifications.remove(notificationx);
         }

         OpenGlBackendHolder.backend.setAlphaFunction(previousAlphaTestFunction, previousAlphaTestReference);
         OpenGlBackendHolder.backend.popMatrix();
      }
   }

   public static int initializeControlFlowState() {
      int currentState = getControlFlowState();
      return currentState == 0 ? 112 : 0;
   }

   public void show(INotification notification) {
      this.enqueue(notification, false);
   }

   public void showInfo(String title, String message, long durationMillis) {
      this.show(title, message, NotificationType.INFO, durationMillis);
   }

   public static void setControlFlowState(int state) {
      controlFlowState = state;
   }

   public void show(String title, String message, NotificationType type, long durationMillis) {
      this.show(title, message, type, durationMillis, false);
   }

   @EventHandler
   public void onMouseClick(GuiMouseEvent event) {
      if (event.getAction().equals(MouseButton.LEFT_CLICK)) {
         for (INotification notification : this.notifications) {
            notification.handleClick(event.getX(), event.getY());
         }
      }
   }

   public static double interpolateToward(double target, double current, long elapsedMillis, double maxStep) {
      if (target == current) {
         return target;
      } else {
         double scaledStep = Math.max(maxStep * Math.max(1L, elapsedMillis) / 16.666666666666668, 0.1);
         return current + MathUtil.clamp(target - current, -scaledStep, scaledStep);
      }
   }

   public static int getControlFlowState() {
      return controlFlowState;
   }

   public void enqueue(INotification notification, boolean force) {
      if (this.isAllowedByFriendSettings(notification)) {
         if (Vape.INSTANCE.getPublicProfileSettings().notifications.getEffectiveValue() || force) {
            if (this.isGroupEnabled(notification.getType().getGroup())) {
               if (this.notificationSounds.containsKey(notification.getType())) {
                  Vape.INSTANCE.getNotificationSoundPlayer().queue(this.notificationSounds.get(notification.getType()));
               }

               double initialY = notification.getHeight() + 16.0;

               for (INotification queuedNotification : this.notifications) {
                  initialY += queuedNotification.getHeight() + 2.0;
               }

               notification.setCurrentY(-initialY);
               notification.setCurrentX(0.0);
               notification.setTargetX(-notification.getWidth());
               if (this.notifications.remainingCapacity() == 0) {
                  this.notifications.remove();
               }

               if (!this.notifications.contains(notification)) {
                  this.notifications.add(notification);
               }
            }
         }
      }
   }

   public NotificationManager() {
      this.groupSettings = new HashMap<>();
      this.notificationSounds.put(NotificationType.FRIENDS_NEW_CHAT, NotificationSounds.MESSAGE_RECEIVED);
      this.notificationSounds.put(NotificationType.FRIENDS_PARTY_INVITE, NotificationSounds.PARTY_INVITE);
      this.groupSettings.put(NotificationGroup.FRIENDS, Vape.INSTANCE.getPublicProfileSettings().friendNotifications);
   }

   static {
      if (initializeControlFlowState() == 0) {
         setControlFlowState(82);
      }
   }
}
