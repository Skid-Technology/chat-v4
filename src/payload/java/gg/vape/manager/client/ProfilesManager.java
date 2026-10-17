package gg.vape.manager.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.api.ApiServices;
import gg.vape.config.BuiltinProfile;
import gg.vape.config.BuiltinProfileState;
import gg.vape.config.Minecraft121BuiltinProfile;
import gg.vape.config.Profile;
import gg.vape.config.ProfilesSyncPayloadBuilder;
import gg.vape.config.PublicProfile;
import gg.vape.event.EventBus;
import gg.vape.event.impl.ProfileChangeEvent;
import gg.vape.event.impl.ProfileListMutationAction;
import gg.vape.event.impl.ProfileListMutationEvent;
import gg.vape.module.Mod;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.value.AbstractListValueComponent;
import gg.vape.ui.click.component.value.FloatingValueDropdownLayer;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.impl.ClientSettingsFrame;
import gg.vape.ui.click.frame.impl.profile.ProfilesSettingsFrame;
import gg.vape.value.Value;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import org.jetbrains.annotations.Nullable;

public class ProfilesManager {
   private final List<Profile> profiles = new ArrayList<>();
   static final boolean assertionsDisabled = !ProfilesManager.class.desiredAssertionStatus();
   private Profile activeProfile;
   private final Set<UUID> deletedRemoteProfileIds = new LinkedHashSet<>();

   public void resetAllSettings() {
      try {
         if (Vape.INSTANCE.getModManager() != null && Vape.INSTANCE.getModManager().getAllModules() != null) {
            for (Mod object : Vape.INSTANCE.getModManager().getAllModules()) {
               if (object != null) {
                  if (object.getBind() != null && object.getBind().usesOwnKeybindStorage()) {
                     object.getBind().getBoundInputs().clear();
                     if (object.getDefaultKeybind() != 0) {
                        object.getBind().getBoundInputs().add(object.getDefaultKeybind());
                     }
                  }

                  object.setVisibility(object.isDefaultVisible());
                  if (object.getAllValues() != null) {
                     for (Value<?, ?> value : object.getAllValues()) {
                        if (value != null) {
                           value.reset();
                        }
                     }
                  }
               }
            }
         }

         ClientSettings.refreshModuleCategoryHeaders();
         if (Vape.INSTANCE.getValueManager() != null && Vape.INSTANCE.getValueManager().getValues() != null) {
            for (Value valuex : Vape.INSTANCE.getValueManager().getValues()) {
               if (valuex != null && valuex.getDefaultValue() != null) {
                  valuex.reset();
               }
            }
         }

         if (ClientSettings.getAllFrames() != null) {
            for (Frame frame : ClientSettings.getAllFrames()) {
               if (frame instanceof ClientSettingsFrame) {
                  ((ClientSettingsFrame)frame).d$src$V$16knweo();
               } else if (frame instanceof FloatingValueDropdownLayer) {
                  Object src = ((FloatingValueDropdownLayer)frame).getSourceComponent();
                  if (src instanceof AbstractListValueComponent) {
                     ((AbstractListValueComponent)src).setExpanded(false);
                  }
               }
            }
         }
      } catch (Throwable var5) {
         Vape.logThrowable(var5);
      }
   }

   public List<Profile> getProfiles() {
      return this.profiles;
   }

   public Profile getProfileByOnlineId(UUID onlineId) {
      for (Profile profile : this.profiles) {
         if (profile.getOnlineId() != null && profile.getOnlineId().toString().equalsIgnoreCase(onlineId.toString())) {
            return profile;
         }
      }

      return null;
   }

   public void removeProfile(Profile profile) {
      if (profile.getOnlineId() != null) {
         this.deletedRemoteProfileIds.add(profile.getOnlineId());

         try {
            ApiServices.getInstance()
               .getUserDataApi()
               .saveProfileData(ProfilesSyncPayloadBuilder.build(null, Collections.singletonList(profile.getOnlineId())));
         } catch (Throwable var3) {
         }
      }

      this.profiles.remove(profile);
      ClientSettings.getFrame(ProfilesSettingsFrame.class).removeProfile(profile);
      Vape.INSTANCE.saveAndStop();
      new ProfileListMutationEvent(profile, ProfileListMutationAction.REMOVE).fire();
   }

   public void addProfile(Profile profile, boolean prepend) {
      if (prepend) {
         this.profiles.add(0, profile);

         for (Profile profile2 : this.profiles) {
            profile2.setSortOrder(profile2.getCurrentSortIndex());
         }
      } else {
         this.profiles.add(profile);
      }

      ClientSettings.getFrame(ProfilesSettingsFrame.class).addProfile(profile);
      ProfilesSettingsFrame.refreshProfileList();
      Vape.INSTANCE.saveAndStop();
      new ProfileListMutationEvent(profile, ProfileListMutationAction.ADD).fire();
   }

   public JsonObject buildSingleProfileSyncPayload(Profile profile) {
      JsonObject jsonObject = new JsonObject();
      JsonArray jsonArray = new JsonArray();
      jsonArray.add(profile.toJson(true));
      jsonObject.add("updatedProfiles", jsonArray);
      jsonObject.add("deletedProfiles", new JsonArray());
      return jsonObject;
   }

   public void loadJson(JsonObject jsonObject) {
      if (jsonObject != null) {
         if (jsonObject.entrySet().isEmpty()) {
            Profile profile = this.initializeBuiltinProfiles();
            if (profile != null) {
               this.setActiveProfile(profile);
            }
         } else {
            for (Entry entry : jsonObject.entrySet()) {
               JsonObject jsonObject2 = ((JsonElement)entry.getValue()).getAsJsonObject();
               Profile profile = new Profile("", "", true).loadJson(jsonObject2);
               this.addProfile(profile);
            }

            try {
               this.profiles.sort(ProfilesManager::compareProfileSortOrder);
            } catch (Throwable var6) {
            }

            ProfilesSettingsFrame.refreshProfileList();
         }
      }
   }

   private static Throwable propagateThrowable(Throwable throwable) {
      return throwable;
   }

   private Profile initializeBuiltinProfiles() {
      ArrayList<BuiltinProfile> builtinProfiles = new ArrayList<>();
      builtinProfiles.add(new Minecraft121BuiltinProfile());
      builtinProfiles.add(new BuiltinProfileState());
      Profile selectedProfile = null;

      for (BuiltinProfile builtinProfile : builtinProfiles) {
         builtinProfile.applyPreset();
         this.addProfile(builtinProfile);
         if (selectedProfile == null && builtinProfile.isApplicable()) {
            selectedProfile = builtinProfile;
         }
      }

      if (selectedProfile == null && !this.profiles.isEmpty()) {
         selectedProfile = this.profiles.get(0);
      }

      return selectedProfile;
   }

   private static int compareProfileSortOrder(Profile first, Profile second) {
      if (first.getSortOrder() != null && second.getSortOrder() != null) {
         return first.getSortOrder().compareTo(second.getSortOrder());
      } else {
         return first.getSortOrder() != null ? 1 : -1;
      }
   }

   public Profile getProfileByName(String name) {
      for (Profile profile : this.profiles) {
         if (profile.getName().equalsIgnoreCase(name)) {
            return profile;
         }
      }

      return null;
   }

   public JsonObject toJson(boolean dirtyOnly) {
      if (dirtyOnly) {
         ArrayList<Profile> dirtyProfiles = new ArrayList<>();

         for (Profile profile : this.getProfiles()) {
            if (profile.isDirty()) {
               dirtyProfiles.add(profile);
            }
         }

         ArrayList<UUID> deletedIds = new ArrayList<>(this.deletedRemoteProfileIds);
         this.deletedRemoteProfileIds.clear();
         return ProfilesSyncPayloadBuilder.build(dirtyProfiles, deletedIds);
      } else {
         JsonObject result = new JsonObject();

         for (Profile profilex : this.getProfiles()) {
            result.add(profilex.getLocalId().toString(), profilex.toJson(dirtyOnly));
         }

         return result;
      }
   }

   public void clearProfiles() {
      this.profiles.clear();
   }

   public void addProfile(Profile profile) {
      this.addProfile(profile, false);
   }

   public void updatePublicProfileLinks() {
      Collection<PublicProfile> collection = Vape.INSTANCE.getPublicProfileManager().getProfilesById().values();

      for (Profile profile : this.profiles) {
         profile.setPublicProfile(null);

         for (PublicProfile publicProfile : collection) {
            UUID uUID;
            if (publicProfile.getShareInfo() != null
               && (uUID = publicProfile.getShareInfo().getDerivedFrom()) != null
               && profile.getOnlineId() != null
               && profile.getOnlineId().toString().equalsIgnoreCase(uUID.toString())) {
               profile.setPublicProfile(publicProfile);
            }
         }
      }
   }

   public void setActiveProfile(Profile profile) {
      if (profile != null) {
         if (this.activeProfile == null || !this.activeProfile.equals(profile)) {
            Profile previousProfile = this.activeProfile;

            try {
               this.resetAllSettings();
            } catch (Throwable var7) {
               Vape.logThrowable(var7);
            }

            this.activeProfile = profile;

            try {
               this.activeProfile.apply();
            } catch (Throwable var6) {
               Vape.logThrowable(var6);
            }

            try {
               Vape.INSTANCE.saveAndStop();
            } catch (Throwable var5) {
               Vape.logThrowable(var5);
            }

            try {
               EventBus.getInstance().post(new ProfileChangeEvent(previousProfile, profile));
            } catch (Throwable var4) {
               Vape.logThrowable(var4);
            }
         }
      }
   }

   public void switchProfile(Profile profile) {
      if (profile != null) {
         if (this.getActiveProfile() != null) {
            try {
               this.getActiveProfile().captureCurrentState();
            } catch (Throwable var3) {
               Vape.logThrowable(var3);
            }
         }

         this.setActiveProfile(profile);
      }
   }

   public void captureProfileState(Profile profile) {
      Profile currentProfile = this.getActiveProfile();
      if (profile.equals(currentProfile)) {
         profile.captureCurrentState();
      } else {
         this.setActiveProfile(profile);
         profile.captureCurrentState();
         this.setActiveProfile(currentProfile);
      }
   }

   public List<Profile> getRemoteProfiles() {
      ArrayList<Profile> remoteProfiles = new ArrayList<>();

      for (Profile profile : this.profiles) {
         if (profile.getRemoteMetadata() != null) {
            remoteProfiles.add(profile);
         }
      }

      return remoteProfiles;
   }

   public Profile getActiveProfile() {
      boolean activeProfileInvalid = this.activeProfile == null || !this.getProfiles().contains(this.activeProfile) && !this.activeProfile.isDraft();
      if (activeProfileInvalid) {
         if (!this.getProfiles().isEmpty()) {
            this.setActiveProfile(this.getProfiles().get(0));
         } else {
            Profile profile = this.initializeBuiltinProfiles();
            this.setActiveProfile(profile);
         }
      }

      return this.activeProfile;
   }

   @Nullable
   public Profile getProfileByPublicProfileId(long publicProfileId) {
      for (Profile profile : this.profiles) {
         if (profile.getRemoteMetadata() != null) {
            if (!assertionsDisabled && profile.getRemoteMetadata() == null) {
               throw new AssertionError();
            }

            if (profile.getRemoteMetadata().getPublicProfileId() == publicProfileId) {
               return profile;
            }
         }
      }

      return null;
   }

   public void sortProfiles() {
      this.profiles.sort(Profile::compareSortOrder);
   }

   public Profile getActiveProfileOrNull() {
      return this.activeProfile;
   }

   public Profile getProfileByLocalId(UUID localId) {
      for (Profile profile : this.profiles) {
         if (profile.getLocalId().toString().equalsIgnoreCase(localId.toString())) {
            return profile;
         }
      }

      return null;
   }
}
