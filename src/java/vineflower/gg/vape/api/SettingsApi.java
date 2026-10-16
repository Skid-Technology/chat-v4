package gg.vape.api;

import gg.vape.config.RefreshableSettingsPayload;
import gg.vape.config.SettingsDataType;
import gg.vape.config.SettingsPayload;

public class SettingsApi {
   private final String baseUrl;

   public <T> T saveSettings(SettingsDataType settingsDataType, SettingsPayload settingsPayload) throws Exception {
      String accessToken = ApiAccessTokenProvider.getAccessToken();
      if (settingsPayload instanceof RefreshableSettingsPayload) {
         ((RefreshableSettingsPayload)settingsPayload).refreshFromCurrentSettings();
      }

      return ApiHttpClient.post(
         this.baseUrl + "/api/v1/" + accessToken + "/settings/save/" + settingsDataType.getScope().getRouteName(),
         (Class<T>)settingsDataType.getPayloadClass(),
         settingsPayload
      );
   }

   public SettingsApi(String baseUrl) {
      this.baseUrl = baseUrl;
   }

   private static Exception preserveException(Exception error) {
      return error;
   }

   public <T> ApiResponse<T> loadSettings(SettingsDataType settingsDataType) throws Exception {
      String accessToken = ApiAccessTokenProvider.getAccessToken();
      ApiResponse response = ApiHttpClient.get(
         this.baseUrl + "/api/v1/" + accessToken + "/settings/load/" + settingsDataType.getScope().getRouteName(), ApiResponse.class
      );
      if (response == null) {
         return null;
      } else {
         return !response.isSuccessful()
            ? ApiResponse.failure(response.getError())
            : ApiResponse.success(ApiHttpClient.GSON.fromJson(ApiHttpClient.GSON.toJson(response.getData()), (Class<T>)settingsDataType.getPayloadClass()));
      }
   }
}
