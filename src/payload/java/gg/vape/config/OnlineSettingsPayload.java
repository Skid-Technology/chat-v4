package gg.vape.config;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import gg.vape.Vape;
import gg.vape.friend.OnlineFriend;
import gg.vape.manager.client.OnlineConnectionManager;
import java.util.LinkedHashMap;
import java.util.Map;

public class OnlineSettingsPayload implements RefreshableSettingsPayload {
   @SerializedName("inventorySwitchMode")
   private Integer inventorySwitchMode;
   @SerializedName("partyShowTarget")
   private Boolean partyShowTarget;
   @SerializedName("autoLogin")
   private Boolean autoLogin;
   @SerializedName("showSelf")
   private Boolean showSelf;
   @SerializedName("showUsername")
   private Boolean showUsername;
   @SerializedName("showInventoryKeybind")
   private JsonArray showInventoryKeybind;
   @SerializedName("friendStates")
   private Map<Long, Boolean> friendStates;
   @SerializedName("shareInventory")
   private Boolean shareInventory;
   public static final OnlineSettingsPayload DEFAULTS = new OnlineSettingsPayload();
   @SerializedName("showServer")
   private Boolean showServer;
   @SerializedName("pingKeybind")
   private JsonArray pingKeybind;

   public Boolean getShowUsername() {
      return this.showUsername;
   }

   public Boolean getShowSelf() {
      if (this.showSelf == null) {
         this.showSelf = DEFAULTS.showSelf;
      }

      return this.showSelf;
   }

   public JsonArray getShowInventoryKeybind() {
      return this.showInventoryKeybind;
   }

   public Boolean getAutoLogin() {
      return this.autoLogin;
   }

   public Boolean getPartyShowTarget() {
      return this.partyShowTarget;
   }

   public Boolean getShareInventory() {
      if (this.shareInventory == null) {
         this.shareInventory = DEFAULTS.shareInventory;
      }

      return this.shareInventory;
   }

   @Override
   public void initializeDefaults() {
      if (this.autoLogin == null) {
         this.autoLogin = OnlineConnectionManager.INSTANCE.getSettings().getAutoLogin().getDefaultValue();
      }

      if (this.friendStates == null) {
         this.friendStates = new LinkedHashMap<>();
      }

      if (this.showServer == null) {
         this.showServer = OnlineConnectionManager.INSTANCE.getSettings().getShareServer().getDefaultValue();
      }

      if (this.showUsername == null) {
         this.showUsername = OnlineConnectionManager.INSTANCE.getSettings().getShareUsername().getDefaultValue();
      }

      if (this.partyShowTarget == null) {
         this.partyShowTarget = OnlineConnectionManager.INSTANCE.getSettings().getTargetIndicators().getDefaultValue();
      }

      if (this.shareInventory == null) {
         this.shareInventory = OnlineConnectionManager.INSTANCE.getSettings().getShareInventory().getDefaultValue();
      }

      if (this.pingKeybind == null) {
         this.pingKeybind = OnlineConnectionManager.INSTANCE.getSettings().getPingBind().serializeBoundInputs();
      }

      if (this.showInventoryKeybind == null) {
         this.showInventoryKeybind = OnlineConnectionManager.INSTANCE.getSettings().getInventoryDisplayBind().serializeBoundInputs();
      }

      if (this.inventorySwitchMode == null) {
         this.inventorySwitchMode = OnlineConnectionManager.INSTANCE.getSettings().getInventorySwitchMode().getSelectedIndex();
      }

      if (this.showSelf == null) {
         this.showSelf = OnlineConnectionManager.INSTANCE.getSettings().getShowSelf().getDefaultValue();
      }
   }

   public Boolean getShowServer() {
      return this.showServer;
   }

   @Override
   public void refreshFromCurrentSettings() {
      this.autoLogin = OnlineConnectionManager.INSTANCE.getSettings().getAutoLogin().getEffectiveValue();
      this.friendStates.clear();

      for (OnlineFriend onlineFriend : Vape.INSTANCE.getOnlineFriendManager().getFriends()) {
         if (onlineFriend.isSyncWithFriends()) {
            this.friendStates.put(onlineFriend.getUser().getId(), true);
         }
      }

      this.showServer = OnlineConnectionManager.INSTANCE.getSettings().getShareServer().getEffectiveValue();
      this.showUsername = OnlineConnectionManager.INSTANCE.getSettings().getShareUsername().getEffectiveValue();
      this.partyShowTarget = OnlineConnectionManager.INSTANCE.getSettings().getTargetIndicators().getEffectiveValue();
      this.shareInventory = OnlineConnectionManager.INSTANCE.getSettings().getShareInventory().getEffectiveValue();
      this.pingKeybind = OnlineConnectionManager.INSTANCE.getSettings().getPingBind().serializeBoundInputs();
      this.inventorySwitchMode = OnlineConnectionManager.INSTANCE.getSettings().getInventorySwitchMode().getSelectedIndex();
      this.showSelf = OnlineConnectionManager.INSTANCE.getSettings().getShowSelf().getEffectiveValue();
      this.showInventoryKeybind = OnlineConnectionManager.INSTANCE.getSettings().getInventoryDisplayBind().serializeBoundInputs();
   }

   public JsonArray getPingKeybind() {
      return this.pingKeybind;
   }

   public Integer getInventorySwitchMode() {
      return this.inventorySwitchMode;
   }

   public Map<Long, Boolean> getFriendStates() {
      return this.friendStates;
   }

   static {
      DEFAULTS.initializeDefaults();
   }
}
