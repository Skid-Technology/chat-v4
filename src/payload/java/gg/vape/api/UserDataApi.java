package gg.vape.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.sync.RemoteProfileDataMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class UserDataApi {
   private final String baseUrl;

   public CompletableFuture<ApiResponse<Boolean>> saveUserData(JsonObject userData) {
      String accessToken = ApiAccessTokenProvider.getAccessToken();
      return CompletableFuture.supplyAsync(() -> this.requestSaveUserData(accessToken, userData));
   }

   private ApiResponse requestSaveUserData(String accessToken, JsonObject userData) {
      try {
         return ApiHttpClient.postApiResponse(this.baseUrl + "/api/v1/" + accessToken + "/profile/private/save/user/", userData, JsonElement::getAsBoolean);
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   public CompletableFuture<ApiResponse<RemoteProfileDataMap>> saveProfileData(JsonObject profileData) {
      String accessToken = ApiAccessTokenProvider.getAccessToken();
      return CompletableFuture.supplyAsync(() -> this.requestSaveProfileData(accessToken, profileData));
   }

   private ApiResponse requestSaveProfileData(String accessToken, JsonObject profileData) {
      try {
         return ApiHttpClient.postApiResponse(
            this.baseUrl + "/api/v1/" + accessToken + "/profile/private/save/profile/", profileData, RemoteProfileDataMap::fromJson
         );
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   private ApiResponse requestUserData(String accessToken) {
      try {
         return ApiHttpClient.getApiResponse(this.baseUrl + "/api/v1/" + accessToken + "/profile/private/all", UserDataResponse::fromJson);
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   private ApiResponse requestReservedProfileId(String accessToken) {
      try {
         return ApiHttpClient.postApiResponse(this.baseUrl + "/api/v1/" + accessToken + "/profile/private/reserve/", null, UserDataApi::parseProfileId);
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public CompletableFuture<ApiResponse<UserDataResponse>> getUserData() {
      String accessToken = ApiAccessTokenProvider.getAccessToken();
      return CompletableFuture.supplyAsync(() -> this.requestUserData(accessToken));
   }

   private static UUID parseProfileId(JsonElement profileIdElement) {
      return UUID.fromString(profileIdElement.getAsString());
   }

   public CompletableFuture<ApiResponse<UUID>> reserveProfileId() {
      String accessToken = ApiAccessTokenProvider.getAccessToken();
      return CompletableFuture.supplyAsync(() -> this.requestReservedProfileId(accessToken));
   }

   public UserDataApi(String baseUrl) {
      this.baseUrl = baseUrl;
   }
}
