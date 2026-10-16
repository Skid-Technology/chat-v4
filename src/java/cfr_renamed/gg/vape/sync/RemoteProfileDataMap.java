/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Contract
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.sync;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.sync.RemoteProfileData;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class RemoteProfileDataMap {
    private final Map<UUID, RemoteProfileData> profiles;
    private final static String TO_STRING_PREFIX = "PrivateProfilesResponse{profiles=";

    RemoteProfileDataMap(Map<UUID, RemoteProfileData> profiles) {
        this.profiles = profiles;
    }

    @Nullable
    @Contract(value="!null -> !null; null -> null")
    public static RemoteProfileDataMap fromJson(@Nullable JsonElement element) {
        if (element == null || element.isJsonNull() || !element.isJsonObject()) {
            return null;
        }
        JsonObject json = element.getAsJsonObject();
        LinkedHashMap<UUID, RemoteProfileData> profiles = new LinkedHashMap<UUID, RemoteProfileData>();
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            UUID keyUuid;
            RemoteProfileData profile = RemoteProfileData.fromJson(entry.getValue());
            if (profile == null) continue;
            try {
                keyUuid = UUID.fromString(entry.getKey());
            }
            catch (Exception e) {
                keyUuid = profile.getProfileId();
            }
            profiles.put(keyUuid, profile);
        }
        return new RemoteProfileDataMap(profiles);
    }

    public String toString() {
        return TO_STRING_PREFIX + this.profiles + '}';
    }

    public Map<UUID, RemoteProfileData> getProfiles() {
        return this.profiles;
    }
}

