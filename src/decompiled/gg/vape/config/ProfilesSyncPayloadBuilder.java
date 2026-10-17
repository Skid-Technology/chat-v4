/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import gg.vape.config.Profile;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public class ProfilesSyncPayloadBuilder {
    private static int runtimeState;

    public static void setRuntimeState(int runtimeState) {
        ProfilesSyncPayloadBuilder.runtimeState = runtimeState;
    }

    public static int getDefaultRuntimeState() {
        int currentState = ProfilesSyncPayloadBuilder.getRuntimeState();
        return 0;
    }

    public static JsonObject build(@Nullable List<Profile> updatedProfiles, @Nullable List<UUID> deletedProfileIds) {
        JsonObject payload = new JsonObject();
        JsonArray updatedProfilesJson = new JsonArray();
        if (updatedProfiles != null) {
            for (Profile profile : updatedProfiles) {
                updatedProfilesJson.add(profile.toJson(true));
            }
        }
        JsonArray deletedProfilesJson = new JsonArray();
        if (deletedProfileIds != null) {
            for (UUID profileId : deletedProfileIds) {
                deletedProfilesJson.add(new JsonPrimitive(profileId.toString()));
            }
        }
        payload.add("updatedProfiles", updatedProfilesJson);
        payload.add("deletedProfiles", deletedProfilesJson);
        return payload;
    }

    public static int getRuntimeState() {
        return runtimeState;
    }

    static {
        ProfilesSyncPayloadBuilder.setRuntimeState(99);
    }
}

