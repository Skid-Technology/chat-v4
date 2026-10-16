package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.EventListener;
import gg.vape.event.impl.EventMouseButton;
import gg.vape.event.impl.EventPreAttack;
import gg.vape.friend.OnlineFriendActivityState;
import gg.vape.friend.PartyState;
import gg.vape.input.MouseClickRateTracker;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.none.ClientSettings;
import gg.vape.value.BooleanValue;
import gg.vape.wrapper.impl.Minecraft;
import java.util.ArrayList;
import java.util.List;

public class OnlineActivityPanelOptions implements EventListener {
   public static final OnlineActivityPanelOptions INSTANCE = new OnlineActivityPanelOptions();
   private final BooleanValue cpsDisplay;
   private final BooleanValue renderBackground = BooleanValue.create(this, "Render Background", true);

   public BooleanValue getCpsDisplay() {
      return this.cpsDisplay;
   }

   @EventHandler
   public void onPreAttack(EventPreAttack event) {
      OnlineActivitySettingsFrame settingsFrame = ClientSettings.getFrame(OnlineActivitySettingsFrame.class);
      if (settingsFrame != null) {
         if (!Minecraft.currentScreen().isNotNull()) {
            if (event.getTarget().isInstance(MappedClasses.zc)) {
               Vape.INSTANCE.getOnlineManager().getLocalFriend().getActivityState().recordAttack(event.getTarget().S());
            }
         }
      }
   }

   public OnlineActivityPanelOptions() {
      this.cpsDisplay = BooleanValue.create(this, "CPS Display", true);
   }

   public BooleanValue getRenderBackground() {
      return this.renderBackground;
   }

   public List<OnlineFriendActivityState> getPartyActivities() {
      PartyState partyState = Vape.INSTANCE.getOnlineManager().getPartyManager().getCurrentParty();
      if (partyState == null) {
         return new ArrayList<>();
      } else {
         ArrayList<OnlineFriendActivityState> partyActivities = new ArrayList<>();

         for (OnlineFriendActivityState activityState : Vape.INSTANCE.getOnlineManager().getActivityManager().getActivityStates()) {
            if (activityState.hasData() && partyState.getMembers().contains(activityState.getFriend())) {
               partyActivities.add(activityState);
            }
         }

         return partyActivities;
      }
   }

   @EventHandler
   public void onMouseButton(EventMouseButton event) {
      OnlineActivitySettingsFrame settingsFrame = ClientSettings.getFrame(OnlineActivitySettingsFrame.class);
      if (settingsFrame != null) {
         if (event.getButtonState()) {
            Vape.INSTANCE.getOnlineManager().getLocalFriend().getActivityState().setAfkTicks(0);
            if (event.getButton() == 0 && Minecraft.currentScreen().isNull()) {
               MouseClickRateTracker.recordClick();
            }
         }
      }
   }
}
