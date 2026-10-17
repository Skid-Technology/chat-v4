package gg.vape.manager.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.api.ApiHttpClient;
import gg.vape.api.ApiResponse;
import gg.vape.api.ApiServices;
import gg.vape.config.Profile;
import gg.vape.config.PublicProfile;
import gg.vape.config.PublicProfileReview;
import gg.vape.config.PublicProfileShareInfo;
import gg.vape.config.PublicProfileSummary;
import gg.vape.event.impl.PublicProfileCreatedEvent;
import gg.vape.event.impl.PublicProfileDeletedEvent;
import gg.vape.event.impl.PublicProfileTagsUpdatedEvent;
import gg.vape.notification.NotificationType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public class PublicProfileManager {
   private final Map<String, String> tagsByLowercase = new LinkedHashMap<>();
   public static final int reservedConstant;
   private final Map<Long, PublicProfile> profilesById = new LinkedHashMap<>();
   private final Map<Long, PublicProfileReview> cachedUserReviews = new LinkedHashMap<>();
   private boolean reviewsCacheLoaded = false;
   static final boolean assertionsDisabled = !PublicProfileManager.class.desiredAssertionStatus();

   public void cacheUserReview(long profileId, PublicProfileReview review) {
      if (review == null) {
         this.cachedUserReviews.remove(profileId);
      } else {
         this.cachedUserReviews.put(profileId, review);
      }

      this.saveReviewsCache();
   }

   public void removeCachedUserReview(long profileId) {
      this.cachedUserReviews.remove(profileId);
      this.saveReviewsCache();
   }

   @Nullable
   public PublicProfileReview getCachedUserReview(long profileId) {
      this.ensureReviewsCacheLoaded();
      return this.cachedUserReviews.get(profileId);
   }

   private void ensureReviewsCacheLoaded() {
      if (!this.reviewsCacheLoaded) {
         this.reviewsCacheLoaded = true;

         try {
            if (Vape.INSTANCE.getPublicProfileSettings() != null && Vape.INSTANCE.getPublicProfileSettings().userReviewsCache != null) {
               String raw = Vape.INSTANCE.getPublicProfileSettings().userReviewsCache.getValue();
               if (raw != null && !raw.trim().isEmpty() && !raw.trim().equals("{}")) {
                  JsonObject obj = ApiHttpClient.GSON.fromJson(raw, JsonObject.class);
                  if (obj != null) {
                     for (Entry<String, JsonElement> entry : obj.entrySet()) {
                        try {
                           long pid = Long.parseLong(entry.getKey());
                           PublicProfileReview review = PublicProfileReview.fromJson(entry.getValue());
                           if (review != null) {
                              this.cachedUserReviews.put(pid, review);
                           }
                        } catch (Exception var8) {
                        }
                     }
                  }
               }
            }
         } catch (Exception var9) {
         }
      }
   }

   private void saveReviewsCache() {
      try {
         if (Vape.INSTANCE.getPublicProfileSettings() != null && Vape.INSTANCE.getPublicProfileSettings().userReviewsCache != null) {
            JsonObject obj = new JsonObject();

            for (Entry<Long, PublicProfileReview> entry : this.cachedUserReviews.entrySet()) {
               JsonObject revJson = new JsonObject();
               revJson.addProperty("commentId", entry.getValue().getCommentId());
               revJson.addProperty("profileId", entry.getValue().getProfileId());
               revJson.addProperty("createdDate", ApiHttpClient.formatApiDate(entry.getValue().getCreatedDate()));
               revJson.addProperty("updatedDate", ApiHttpClient.formatApiDate(entry.getValue().getUpdatedDate()));
               revJson.addProperty("message", entry.getValue().getMessage());
               revJson.addProperty("liked", entry.getValue().isLiked());
               revJson.addProperty("version", entry.getValue().getVersion());
               revJson.addProperty("latest", entry.getValue().isLatest());
               if (entry.getValue().getCommenter() != null) {
                  JsonObject commenterJson = new JsonObject();
                  commenterJson.addProperty("userId", entry.getValue().getCommenter().getUserId());
                  commenterJson.addProperty("username", entry.getValue().getCommenter().getUsername());
                  revJson.add("commenter", commenterJson);
               }

               obj.add(String.valueOf(entry.getKey()), revJson);
            }

            Vape.INSTANCE.getPublicProfileSettings().userReviewsCache.setValue(obj.toString());
            Vape.INSTANCE.saveAndStop();
         }
      } catch (Exception var6) {
      }
   }

   private static ApiResponse handlePopularTagsFailure(Throwable throwable) {
      Vape.logThrowable(throwable);
      return null;
   }

   private void handlePopularTagsResponse(ApiResponse apiResponse, Throwable throwable) {
      if (throwable != null) {
         Vape.logThrowable(throwable);
      } else if (apiResponse.isSuccessful()) {
         if (!assertionsDisabled && apiResponse.getData() == null) {
            throw new AssertionError();
         } else {
            this.replaceTags((Collection<String>)apiResponse.getData());
         }
      }
   }

   public List<Profile> getDerivedProfiles() {
      ArrayList<Profile> derivedProfiles = new ArrayList<>();

      for (PublicProfile publicProfile : this.profilesById.values()) {
         PublicProfileShareInfo publicProfileShareInfo = publicProfile.getShareInfo();
         Profile profile;
         if (publicProfileShareInfo != null
            && publicProfileShareInfo.getDerivedFrom() != null
            && (profile = Vape.INSTANCE.getProfilesManager().getProfileByOnlineId(publicProfileShareInfo.getDerivedFrom())) != null) {
            derivedProfiles.add(profile);
         }
      }

      return derivedProfiles;
   }

   public void addProfileTags(PublicProfile publicProfile) {
      this.addTags(publicProfile.getTags());
   }

   public void replaceTags(Collection<String> tags) {
      this.tagsByLowercase.clear();
      this.addTags(tags);
   }

   public void addProfile(PublicProfile publicProfile) {
      this.profilesById.put(publicProfile.getProfileId(), publicProfile);
      if (!publicProfile.getTags().isEmpty()) {
         this.addTags(publicProfile.getTags());
      }

      new PublicProfileCreatedEvent(publicProfile).fire();
   }

   public PublicProfileManager() {
      ApiServices.getInstance()
         .getPublicProfileApi()
         .getMostPopularTags()
         .whenCompleteAsync(this::handlePopularTagsResponse)
         .exceptionally(PublicProfileManager::handlePopularTagsFailure);
   }

   public Collection<String> getTags() {
      return this.tagsByLowercase.values();
   }

   public static void showInfo(String message) {
      Vape.INSTANCE.getNotificationManager().show("Public Profiles", message, NotificationType.INFO, 5000L, true);
   }

   public void addTags(Collection<String> tags) {
      for (String tag : tags) {
         if (!this.tagsByLowercase.containsKey(tag.toLowerCase())) {
            this.tagsByLowercase.put(tag.toLowerCase(), tag);
         }
      }

      new PublicProfileTagsUpdatedEvent(tags).fire();
   }

   public void addSummaryTags(PublicProfileSummary summary) {
      this.addTags(summary.getTags());
   }

   public void replaceProfile(PublicProfile previousProfile, PublicProfile replacement) {
      PublicProfile existingProfile = this.profilesById.put(replacement.getProfileId(), replacement);
      if (existingProfile == null) {
         this.addProfile(replacement);
      } else {
         if (!replacement.getTags().isEmpty()) {
            this.addTags(replacement.getTags());
         }

         new PublicProfileDeletedEvent(previousProfile).fire();
         new PublicProfileCreatedEvent(replacement).fire();
      }
   }

   public void removeProfile(PublicProfile publicProfile) {
      this.profilesById.remove(publicProfile.getProfileId());
      new PublicProfileDeletedEvent(publicProfile).fire();
   }

   public static void showWarning(String message) {
      Vape.INSTANCE.getNotificationManager().show("Public Profiles", message, NotificationType.WARNING, 5000L, true);
   }

   public void replaceProfiles(Collection<PublicProfile> profiles) {
      this.profilesById.clear();

      for (PublicProfile publicProfile : profiles) {
         this.profilesById.put(publicProfile.getProfileId(), publicProfile);
      }
   }

   public @UnmodifiableView Map<Long, PublicProfile> getProfilesById() {
      return this.profilesById;
   }

   static {
      long reservedSeed = 2496869938925404163L;
      reservedConstant = (int)reservedSeed;
   }
}
