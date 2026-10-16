package gg.vape.account.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class YggdrasilAuthenticateResponse implements YggdrasilAuthResponse {
   @Expose
   @SerializedName("errorMessage")
   private String errorMessage;
   @Expose
   @SerializedName("selectedProfile")
   private YggdrasilProfile selectedProfile;
   @Expose
   @SerializedName("accessToken")
   private String accessToken;
   @Expose
   @SerializedName("clientToken")
   private String clientToken;
   private static int opaqueState;
   @Expose
   @SerializedName("availableProfiles")
   private List<YggdrasilProfile> availableProfiles;

   public YggdrasilProfile getSelectedProfile() {
      return this.selectedProfile;
   }

   public String getErrorMessage() {
      return this.errorMessage;
   }

   public String getAccessToken() {
      return this.accessToken;
   }

   public static int opaquePredicate() {
      int state = getOpaqueState();
      return state == 0 ? 14 : 0;
   }

   public static void setOpaqueState(int state) {
      opaqueState = state;
   }

   public List<YggdrasilProfile> getAvailableProfiles() {
      return this.availableProfiles;
   }

   public static int getOpaqueState() {
      return opaqueState;
   }

   public void clearErrorMessage() {
      this.errorMessage = null;
   }

   public String getClientToken() {
      return this.clientToken;
   }

   static {
      if (opaquePredicate() == 0) {
         setOpaqueState(1);
      }
   }
}
