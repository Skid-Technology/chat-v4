package gg.vape.api;

import gg.vape.account.AccountInfoResponse;
import gg.vape.ui.click.component.GuiComponent;
import java.util.concurrent.CompletableFuture;

public class ApiServices {
   private final String baseUrl = resolveBaseUrl();
   private final UserDataApi userDataApi;
   private final PublicProfileApi publicProfileApi;
   private static ApiServices instance = new ApiServices();
   private static GuiComponent[] legacyComponentState;
   private final SettingsApi settingsApi;

   public PublicProfileApi getPublicProfileApi() {
      return this.publicProfileApi;
   }

   public CompletableFuture<ApiResponse<AccountInfoResponse>> getAccountInfo() {
      String accessToken = ApiAccessTokenProvider.getAccessToken();
      return CompletableFuture.supplyAsync(() -> this.requestAccountInfo(accessToken));
   }

   public static GuiComponent[] getLegacyComponentState() {
      return legacyComponentState;
   }

   private ApiResponse requestRegistration(String registrationCode) {
      String accessToken = ApiAccessTokenProvider.getAccessToken();

      try {
         StringBuilder url = new StringBuilder();
         this.getClass();
         return ApiHttpClient.get(
            url.append(this.baseUrl).append("/api/v1/").append(accessToken).append("/register/").append(registrationCode).toString(), ApiResponse.class
         );
      } catch (Exception var5) {
         throw new RuntimeException(var5);
      }
   }

   public static ApiServices getInstance() {
      return instance;
   }

   private ApiResponse requestAccountInfo(String accessToken) {
      try {
         StringBuilder url = new StringBuilder();
         this.getClass();
         return ApiHttpClient.getApiResponse(
            url.append(this.baseUrl).append("/api/v1/").append(accessToken).append("/authenticated").toString(), AccountInfoResponse::fromJson
         );
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public SettingsApi getSettingsApi() {
      return this.settingsApi;
   }

   public CompletableFuture<ApiResponse<Boolean>> registerOnlineAccount(String registrationCode) {
      return CompletableFuture.supplyAsync(() -> this.requestRegistration(registrationCode));
   }

   public ApiServices() {
      this.getClass();
      this.publicProfileApi = new PublicProfileApi(this.baseUrl);
      this.getClass();
      this.settingsApi = new SettingsApi(this.baseUrl);
      this.getClass();
      this.userDataApi = new UserDataApi(this.baseUrl);
   }

   private static String resolveBaseUrl() {
      String configured = System.getenv("VAPE_ONLINE_BASE_URL");
      return configured != null && !configured.trim().isEmpty() ? configured.replaceAll("/+$", "") : "https://online.vape.chat";
   }

   public static void setLegacyComponentState(GuiComponent[] state) {
      legacyComponentState = state;
   }

   public UserDataApi getUserDataApi() {
      return this.userDataApi;
   }

   static {
      setLegacyComponentState(new GuiComponent[4]);
   }
}
