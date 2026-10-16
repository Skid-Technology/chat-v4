package gg.vape.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.api.ApiHttpClient;
import gg.vape.module.Mod;
import gg.vape.unmap.Bendable;
import gg.vape.utils.Base64Util;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Profile extends Bendable implements Comparable<Profile> {
   private String clientVersion;
   private static boolean runtimeState;
   private JsonObject data;
   private JsonObject enabledModuleStates;
   private JsonObject publishedData;
   private int useCount;
   private UUID localId = UUID.randomUUID();
   @Nullable
   private Integer sortOrder;
   @Nullable
   private UUID onlineId;
   public static final int SERIALIZATION_MARKER;
   private boolean saveQueued;
   private JsonObject legitEnabledModuleStates;
   private boolean draft;
   @Nullable
   private ProfileRemoteMetadata remoteMetadata;
   private long updatedAt;
   private boolean visible = true;
   private String name;
   private boolean dirty;
   @Nullable
   private PublicProfile publicProfile;
   private String originalUuid;
   @Deprecated
   private boolean publicProfileFlag;

   public boolean isDraft() {
      return this.draft;
   }

   public void captureCurrentState() {
      try {
         this.clientVersion = "4.22";
         this.updateTimestamp();
         this.data = this.serializeCurrentData(false);
         this.publishedData = this.serializeCurrentData(true);
         this.captureEnabledModuleStates();
         if (this.data != null && this.enabledModuleStates != null) {
            this.data.add("enabled", this.enabledModuleStates);
         }

         if (this.publishedData != null && this.enabledModuleStates != null) {
            this.publishedData.add("enabled", this.enabledModuleStates);
         }

         this.captureLegitEnabledModuleStates();
         if (this.data != null && this.legitEnabledModuleStates != null) {
            this.data.add("legit_enabled", this.legitEnabledModuleStates);
         }

         if (this.publishedData != null && this.legitEnabledModuleStates != null) {
            this.publishedData.add("legit_enabled", this.legitEnabledModuleStates);
         }
      } catch (Throwable var2) {
         Vape.logThrowable(var2);
      }
   }

   public void setDirty(boolean dirty) {
      this.dirty = dirty;
   }

   public JsonObject serializeCurrentData(boolean forPublication) {
      JsonObject serializedData = new JsonObject();

      try {
         if (Vape.INSTANCE.getModManager() != null) {
            serializedData.add("modules", Vape.INSTANCE.getModManager().toJson(forPublication));
         }

         if (Vape.INSTANCE.getModuleProfileMetadataCodec() != null) {
            serializedData.add("favorites", Vape.INSTANCE.getModuleProfileMetadataCodec().toJson());
         }

         if (Vape.INSTANCE.getValueManager() != null) {
            serializedData.add("values", Vape.INSTANCE.getValueManager().toJson());
         }

         if (Vape.INSTANCE.getMacrosManager() != null) {
            serializedData.add("macros", Vape.INSTANCE.getMacrosManager().toJson());
         }

         if (Vape.INSTANCE.getSearch() != null) {
            serializedData.add("search", Vape.INSTANCE.getSearch().toJson());
         }

         if (gg.vape.module.none.ClientSettings.INSTANCE != null) {
            serializedData.add("frames", gg.vape.module.none.ClientSettings.INSTANCE.serializeFrameStates());
         }
      } catch (Throwable var4) {
         Vape.logThrowable(var4);
      }

      return serializedData;
   }

   public JsonObject copyPublishedData() {
      JsonObject publishedDataCopy = ApiHttpClient.GSON.fromJson(this.publishedData, JsonObject.class);
      if (publishedDataCopy != null) {
         publishedDataCopy.remove("sortOrder");
      }

      return publishedDataCopy;
   }

   public Profile(String name, String clientVersion, boolean saveQueued) {
      this.enabledModuleStates = new JsonObject();
      this.legitEnabledModuleStates = new JsonObject();
      this.name = name;
      this.clientVersion = clientVersion;
      this.saveQueued = saveQueued;
      this.updateTimestamp();
   }

   public void setDraft(boolean draft) {
      this.draft = draft;
   }

   public void setOnlineId(@Nullable UUID onlineId) {
      this.onlineId = onlineId;
   }

   public static void setRuntimeState(boolean runtimeState) {
      Profile.runtimeState = runtimeState;
   }

   public String getClientVersion() {
      return this.clientVersion;
   }

   public JsonObject getPublishedData() {
      return this.publishedData;
   }

   public JsonObject toJson(boolean useRemoteFormat) {
      JsonObject object = new JsonObject();
      object.addProperty("uuid", this.localId.toString());
      if (this.onlineId != null) {
         object.addProperty("profileId", this.onlineId.toString());
      }

      if (useRemoteFormat && (this.clientVersion == null || this.clientVersion.isEmpty())) {
         this.clientVersion = "4.22";
      }

      object.addProperty("name", this.name.length() > 48 ? this.name.substring(0, 47) : this.name);
      object.addProperty(useRemoteFormat ? "vapeVersion" : "version", this.clientVersion);
      this.data.add("keybinds", this.serializeBoundInputs());
      this.data.addProperty("sortOrder", this.getCurrentSortIndex());
      object.add("data", this.data);
      object.addProperty("is_public", this.publicProfileFlag);
      object.addProperty("updated", this.updatedAt);
      if (this.originalUuid != null) {
         object.addProperty("original_uuid", this.originalUuid);
      }

      if (this.remoteMetadata != null) {
         object.add("metadata", this.remoteMetadata.toJson());
      }

      return object;
   }

   public List<Mod> getEnabledModules() {
      return Vape.INSTANCE.getModManager().getProfileModules(this.enabledModuleStates);
   }

   public void setUseCount(int useCount) {
      this.useCount = useCount;
   }

   @Nullable
   public UUID getOnlineId() {
      return this.onlineId;
   }

   @Override
   public boolean isActive() {
      return Vape.INSTANCE.getProfilesManager().getActiveProfile().equals(this);
   }

   private void updateTimestamp() {
      this.updatedAt = System.currentTimeMillis();
   }

   public void setPublishedData(JsonObject publishedData) {
      this.publishedData = publishedData;
   }

   public void setRemoteMetadata(ProfileRemoteMetadata remoteMetadata) {
      this.remoteMetadata = remoteMetadata;
   }

   @Override
   public String getDisplayText() {
      return String.format(
         " %s7[%sr%s%s7]%sr %s",
         ClientSettings.FORMAT_CODE,
         ClientSettings.FORMAT_CODE,
         this.getBindText(),
         ClientSettings.FORMAT_CODE,
         ClientSettings.FORMAT_CODE,
         this.getName()
      );
   }

   public void applyEnabledModuleStates() {
      Vape.INSTANCE.getModManager().applyProfileModuleStates(this);
   }

   public long getUpdatedAt() {
      return this.updatedAt;
   }

   public void setPublicProfile(PublicProfile publicProfile) {
      this.publicProfile = publicProfile;
   }

   public boolean isDirty() {
      return this.dirty;
   }

   public String getName() {
      return this.name;
   }

   public void loadData(boolean applyModuleStates) {
      if (this.data != null) {
         try {
            if (applyModuleStates && Vape.INSTANCE.getPublicProfileSettings().autoLoadModuleStates.getEffectiveValue()) {
               Vape.INSTANCE.getModManager().disableNonHudModules();
            }

            JsonArray valuesArray = ConfigJsonUtils.getJsonArray(this.data, "values");
            if (valuesArray != null) {
               Vape.INSTANCE.getValueManager().loadJson(valuesArray);
            }

            JsonArray modulesArray = ConfigJsonUtils.getJsonArray(this.data, "modules");
            if (modulesArray != null) {
               Vape.INSTANCE.getModManager().loadJson(modulesArray);
            }

            JsonObject favoritesObj = ConfigJsonUtils.getJsonObject(this.data, "favorites");
            if (favoritesObj != null) {
               Vape.INSTANCE.getModuleProfileMetadataCodec().loadJson(favoritesObj);
            }

            JsonArray macrosArray = ConfigJsonUtils.getJsonArray(this.data, "macros");
            if (macrosArray != null) {
               Vape.INSTANCE.getMacrosManager().loadJson(macrosArray);
            }

            JsonArray searchArray = ConfigJsonUtils.getJsonArray(this.data, "search");
            if (searchArray != null) {
               Vape.INSTANCE.getSearch().loadJson(searchArray);
            }

            if (applyModuleStates && Vape.INSTANCE.getPublicProfileSettings().autoLoadModuleStates.getEffectiveValue()) {
               this.applyEnabledModuleStates();
            }

            for (Mod mod : Vape.INSTANCE.getModManager().collectMods()) {
               if (mod.isEnabled()) {
                  try {
                     mod.syncSubModuleStates(true, true);
                  } catch (Throwable var10) {
                     Vape.logThrowable(var10);
                  }
               }
            }

            Vape.INSTANCE.getModManager().disableHiddenModules();
            gg.vape.module.none.ClientSettings.refreshModuleCategoryHeaders();
            gg.vape.module.none.ClientSettings.closeListDropdowns();
            JsonArray framesArray = ConfigJsonUtils.getJsonArray(this.data, "frames");
            if (framesArray != null && Vape.INSTANCE.getPublicProfileSettings().framePositionsPerProfile.getEffectiveValue()) {
               JsonArray frameGroups = new JsonArray();
               frameGroups.add(framesArray);
               gg.vape.module.none.ClientSettings.INSTANCE.loadFrameStates(frameGroups);
            }

            String origUuid = ConfigJsonUtils.getString(this.data, "original_uuid");
            if (origUuid != null) {
               this.originalUuid = origUuid;
            }
         } catch (Throwable var11) {
            Vape.logThrowable(var11);
         }
      }
   }

   public void updateData(JsonObject data) {
      this.updateTimestamp();
      this.parseData(data);
   }

   public String getFormattedUpdateDate() {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
      LocalDateTime updatedDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.updatedAt), ZoneId.systemDefault());
      return formatter.format(updatedDateTime);
   }

   public void setUpdatedAt(long updatedAt) {
      this.updatedAt = updatedAt;
   }

   @Nullable
   public Integer getSortOrder() {
      return this.sortOrder;
   }

   public void setName(String name) {
      this.name = name;
      this.dirty = true;
   }

   @Override
   public void onBindActivated() {
      Vape.INSTANCE.getProfilesManager().switchProfile(this);
   }

   public int compareSortOrder(@NotNull Profile profile) {
      return Integer.compare(this.getCurrentSortIndex(), profile.getCurrentSortIndex());
   }

   public int compareTo(Profile profile) {
      return this.compareSortOrder(profile);
   }

   public void setPublicProfileFlag(boolean publicProfileFlag) {
      this.publicProfileFlag = publicProfileFlag;
   }

   public boolean isVisible() {
      return this.visible;
   }

   public static boolean isRuntimeCheckEnabled() {
      boolean currentState = getRuntimeState();
      return true;
   }

   public void apply() {
      this.loadData(true);
   }

   @Deprecated
   public boolean isPublicProfileFlag() {
      return this.publicProfileFlag;
   }

   public static boolean getRuntimeState() {
      return runtimeState;
   }

   public int getCurrentSortIndex() {
      return Vape.INSTANCE.getProfilesManager().getProfiles().indexOf(this);
   }

   public JsonObject getEnabledModuleStates() {
      return this.enabledModuleStates;
   }

   public void applyLegitEnabledModuleStates() {
      Vape.INSTANCE.getModManager().applyHudModuleStates(this.legitEnabledModuleStates);
   }

   private void parseData(JsonObject data) {
      if (data != null) {
         try {
            this.data = data;
            JsonArray values = ConfigJsonUtils.getJsonArray(data, "values");
            if (values != null) {
               this.data.add("values", values);
            }

            JsonArray keybinds;
            if ((keybinds = ConfigJsonUtils.getJsonArray(data, "keybinds")) != null) {
               this.loadBoundInputs(keybinds, false);
            }

            JsonObject parsedEnabledStates;
            if ((parsedEnabledStates = ConfigJsonUtils.getJsonObject(data, "enabled")) != null) {
               this.enabledModuleStates = parsedEnabledStates;
            }

            JsonObject parsedLegitEnabledStates;
            if ((parsedLegitEnabledStates = ConfigJsonUtils.getJsonObject(data, "legit_enabled")) != null) {
               this.legitEnabledModuleStates = parsedLegitEnabledStates;
            }

            Integer parsedSortOrder;
            if ((parsedSortOrder = ConfigJsonUtils.getInteger(data, "sortOrder")) != null) {
               this.sortOrder = parsedSortOrder;
            }
         } catch (Throwable var7) {
            Vape.logThrowable(var7);
         }
      }
   }

   public void setSaveQueued(boolean saveQueued) {
      this.saveQueued = saveQueued;
   }

   public void setLocalId(UUID localId) {
      this.localId = localId;
   }

   private void captureLegitEnabledModuleStates() {
      this.legitEnabledModuleStates = Vape.INSTANCE.getModManager().getEnabledHudModuleStates();
   }

   public UUID getLocalId() {
      return this.localId;
   }

   @Nullable
   public PublicProfile getPublicProfile() {
      return this.publicProfile;
   }

   public void captureEnabledModuleStates() {
      this.enabledModuleStates = Vape.INSTANCE.getModManager().getEnabledNonHudModuleStates();
   }

   public Profile(String name, String clientVersion) {
      this(name, clientVersion, false);
   }

   @Nullable
   public ProfileRemoteMetadata getRemoteMetadata() {
      return this.remoteMetadata;
   }

   @Override
   public void setBoundInputs(List<Integer> inputCodes) {
      ArrayList<Integer> validInputCodes = new ArrayList<>();

      for (Integer inputCode : inputCodes) {
         if (inputCode >= 0 || inputCode + 100 > 1) {
            validInputCodes.add(inputCode);
         }
      }

      super.setBoundInputs(validInputCodes);
   }

   public int getUseCount() {
      return this.useCount;
   }

   public void setVisible(boolean visible) {
      this.visible = visible;
   }

   @Nullable
   public ProfileSnapshot createSnapshot(boolean usePublishedData) {
      JsonObject snapshotData = usePublishedData && this.publishedData != null ? this.getPublishedData() : this.getData();
      return snapshotData != null
            && snapshotData.get("modules") != null
            && !snapshotData.get("modules").isJsonNull()
            && snapshotData.get("modules").isJsonArray()
         ? new ProfileSnapshot(this, snapshotData.get("modules").getAsJsonArray())
         : null;
   }

   public boolean isSaveQueued() {
      return this.saveQueued;
   }

   public void setSortOrder(@NotNull Integer sortOrder) {
      this.sortOrder = sortOrder;
   }

   public JsonObject getData() {
      return this.data;
   }

   public Profile loadJson(JsonObject object) {
      if (object == null) {
         return this;
      } else {
         String uuid = ConfigJsonUtils.getString(object, "uuid");
         if (uuid != null) {
            this.localId = UUID.fromString(uuid);
         }

         String parsedName;
         if ((parsedName = ConfigJsonUtils.getString(object, "name")) != null) {
            this.name = parsedName;
            if (this.name.startsWith("b64:")) {
               this.name = Base64Util.decodeUtf8Base64(this.name.split(":")[1]);
            }
         } else {
            this.name = "Unknown";
         }

         String profileId;
         if ((profileId = ConfigJsonUtils.getString(object, "profileId")) != null) {
            this.onlineId = UUID.fromString(profileId);
         } else {
            this.dirty = true;
         }

         String version = ConfigJsonUtils.getString(object, "version");
         if (version != null) {
            this.clientVersion = version;
         }

         String vapeVersion;
         if ((vapeVersion = ConfigJsonUtils.getString(object, "vapeVersion")) != null) {
            this.clientVersion = vapeVersion;
         }

         Boolean legacyPublicFlag;
         if ((legacyPublicFlag = ConfigJsonUtils.getBoolean(object, "version")) != null) {
            this.publicProfileFlag = legacyPublicFlag;
         }

         JsonObject parsedData;
         if ((parsedData = ConfigJsonUtils.getJsonObject(object, "data")) != null) {
            this.parseData(parsedData);
         }

         Long parsedUpdatedAt;
         if ((parsedUpdatedAt = ConfigJsonUtils.getLong(object, "updated")) != null) {
            this.setUpdatedAt(parsedUpdatedAt);
         }

         String parsedOriginalUuid;
         if ((parsedOriginalUuid = ConfigJsonUtils.getString(object, "original_uuid")) != null) {
            this.originalUuid = parsedOriginalUuid;
         }

         this.remoteMetadata = ProfileRemoteMetadata.fromJson(object.get("metadata"));
         return this;
      }
   }

   static {
      setRuntimeState(false);
      long marker = 2432912498588909616L;
      SERIALIZATION_MARKER = (int)marker;
   }
}
