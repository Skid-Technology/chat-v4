/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.api.ApiHttpClient;
import gg.vape.config.ClientSettings;
import gg.vape.config.ConfigJsonUtils;
import gg.vape.config.ProfileRemoteMetadata;
import gg.vape.config.ProfileSnapshot;
import gg.vape.config.PublicProfile;
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

public class Profile
extends Bendable
implements Comparable<Profile> {
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
    final public static int SERIALIZATION_MARKER;
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
        }
        catch (Throwable t) {
            Vape.logThrowable(t);
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
        }
        catch (Throwable t) {
            Vape.logThrowable(t);
        }
        return serializedData;
    }

    public JsonObject copyPublishedData() {
        JsonObject publishedDataCopy = ApiHttpClient.GSON.fromJson((JsonElement)this.publishedData, JsonObject.class);
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
        return String.format(" %s7[%sr%s%s7]%sr %s", ClientSettings.FORMAT_CODE, ClientSettings.FORMAT_CODE, this.getBindText(), ClientSettings.FORMAT_CODE, ClientSettings.FORMAT_CODE, this.getName());
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
        if (this.data == null) {
            return;
        }
        try {
            String origUuid;
            JsonArray searchArray;
            JsonArray macrosArray;
            JsonObject favoritesObj;
            JsonArray modulesArray;
            JsonArray valuesArray;
            if (applyModuleStates && Vape.INSTANCE.getPublicProfileSettings().autoLoadModuleStates.getEffectiveValue().booleanValue()) {
                Vape.INSTANCE.getModManager().disableNonHudModules();
            }
            if ((valuesArray = ConfigJsonUtils.getJsonArray(this.data, "values")) != null) {
                Vape.INSTANCE.getValueManager().loadJson(valuesArray);
            }
            if ((modulesArray = ConfigJsonUtils.getJsonArray(this.data, "modules")) != null) {
                Vape.INSTANCE.getModManager().loadJson(modulesArray);
            }
            if ((favoritesObj = ConfigJsonUtils.getJsonObject(this.data, "favorites")) != null) {
                Vape.INSTANCE.getModuleProfileMetadataCodec().loadJson(favoritesObj);
            }
            if ((macrosArray = ConfigJsonUtils.getJsonArray(this.data, "macros")) != null) {
                Vape.INSTANCE.getMacrosManager().loadJson(macrosArray);
            }
            if ((searchArray = ConfigJsonUtils.getJsonArray(this.data, "search")) != null) {
                Vape.INSTANCE.getSearch().loadJson(searchArray);
            }
            if (applyModuleStates && Vape.INSTANCE.getPublicProfileSettings().autoLoadModuleStates.getEffectiveValue().booleanValue()) {
                this.applyEnabledModuleStates();
            }
            for (Mod mod : Vape.INSTANCE.getModManager().collectMods()) {
                if (!mod.isEnabled()) continue;
                try {
                    mod.syncSubModuleStates(true, true);
                }
                catch (Throwable t) {
                    Vape.logThrowable(t);
                }
            }
            Vape.INSTANCE.getModManager().disableHiddenModules();
            gg.vape.module.none.ClientSettings.refreshModuleCategoryHeaders();
            gg.vape.module.none.ClientSettings.closeListDropdowns();
            JsonArray framesArray = ConfigJsonUtils.getJsonArray(this.data, "frames");
            if (framesArray != null && Vape.INSTANCE.getPublicProfileSettings().framePositionsPerProfile.getEffectiveValue().booleanValue()) {
                JsonArray frameGroups = new JsonArray();
                frameGroups.add(framesArray);
                gg.vape.module.none.ClientSettings.INSTANCE.loadFrameStates(frameGroups);
            }
            if ((origUuid = ConfigJsonUtils.getString(this.data, "original_uuid")) != null) {
                this.originalUuid = origUuid;
            }
        }
        catch (Throwable t) {
            Vape.logThrowable(t);
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

    @Override
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
        boolean currentState = Profile.getRuntimeState();
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
        if (data == null) {
            return;
        }
        try {
            Integer parsedSortOrder;
            JsonObject parsedLegitEnabledStates;
            JsonObject parsedEnabledStates;
            JsonArray keybinds;
            this.data = data;
            JsonArray values = ConfigJsonUtils.getJsonArray(data, "values");
            if (values != null) {
                this.data.add("values", values);
            }
            if ((keybinds = ConfigJsonUtils.getJsonArray(data, "keybinds")) != null) {
                this.loadBoundInputs(keybinds, false);
            }
            if ((parsedEnabledStates = ConfigJsonUtils.getJsonObject(data, "enabled")) != null) {
                this.enabledModuleStates = parsedEnabledStates;
            }
            if ((parsedLegitEnabledStates = ConfigJsonUtils.getJsonObject(data, "legit_enabled")) != null) {
                this.legitEnabledModuleStates = parsedLegitEnabledStates;
            }
            if ((parsedSortOrder = ConfigJsonUtils.getInteger(data, "sortOrder")) != null) {
                this.sortOrder = parsedSortOrder;
            }
        }
        catch (Throwable t) {
            Vape.logThrowable(t);
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
        ArrayList<Integer> validInputCodes = new ArrayList<Integer>();
        for (Integer inputCode : inputCodes) {
            int mouseButton;
            if (inputCode < 0 && (mouseButton = inputCode + 100) <= 1) continue;
            validInputCodes.add(inputCode);
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
        JsonObject snapshotData;
        JsonObject jsonObject = snapshotData = usePublishedData && this.publishedData != null ? this.getPublishedData() : this.getData();
        if (snapshotData != null && snapshotData.get("modules") != null && !snapshotData.get("modules").isJsonNull() && snapshotData.get("modules").isJsonArray()) {
            return new ProfileSnapshot(this, snapshotData.get("modules").getAsJsonArray());
        }
        return null;
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
        String parsedOriginalUuid;
        Long parsedUpdatedAt;
        JsonObject parsedData;
        Boolean legacyPublicFlag;
        String vapeVersion;
        String profileId;
        String parsedName;
        if (object == null) {
            return this;
        }
        String uuid = ConfigJsonUtils.getString(object, "uuid");
        if (uuid != null) {
            this.localId = UUID.fromString(uuid);
        }
        if ((parsedName = ConfigJsonUtils.getString(object, "name")) != null) {
            this.name = parsedName;
            if (this.name.startsWith("b64:")) {
                this.name = Base64Util.decodeUtf8Base64(this.name.split(":")[1]);
            }
        } else {
            this.name = "Unknown";
        }
        if ((profileId = ConfigJsonUtils.getString(object, "profileId")) != null) {
            this.onlineId = UUID.fromString(profileId);
        } else {
            this.dirty = true;
        }
        String version = ConfigJsonUtils.getString(object, "version");
        if (version != null) {
            this.clientVersion = version;
        }
        if ((vapeVersion = ConfigJsonUtils.getString(object, "vapeVersion")) != null) {
            this.clientVersion = vapeVersion;
        }
        if ((legacyPublicFlag = ConfigJsonUtils.getBoolean(object, "version")) != null) {
            this.publicProfileFlag = legacyPublicFlag;
        }
        if ((parsedData = ConfigJsonUtils.getJsonObject(object, "data")) != null) {
            this.parseData(parsedData);
        }
        if ((parsedUpdatedAt = ConfigJsonUtils.getLong(object, "updated")) != null) {
            this.setUpdatedAt(parsedUpdatedAt);
        }
        if ((parsedOriginalUuid = ConfigJsonUtils.getString(object, "original_uuid")) != null) {
            this.originalUuid = parsedOriginalUuid;
        }
        this.remoteMetadata = ProfileRemoteMetadata.fromJson(object.get("metadata"));
        return this;
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((Profile)object);
    }

    static {
        Profile.setRuntimeState(false);
        long marker = 2432912498588909616L;
        SERIALIZATION_MARKER = (int)marker;
    }
}

