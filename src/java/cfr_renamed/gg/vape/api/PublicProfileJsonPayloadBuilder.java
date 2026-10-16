/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public class PublicProfileJsonPayloadBuilder {
    public static JsonObject build(String name, String vapeVersion, String description, List<String> tags, boolean listed, boolean anonymous, boolean shareCodeFriendsOnly, @Nullable UUID derivedFrom, JsonObject profileData) {
        JsonArray tagsJson = new JsonArray();
        for (String tag : tags) {
            tagsJson.add(new JsonPrimitive(tag));
        }
        JsonObject payload = new JsonObject();
        payload.addProperty("name", name);
        payload.addProperty("vapeVersion", vapeVersion);
        payload.addProperty("description", description);
        payload.add("tags", tagsJson);
        payload.addProperty("listed", listed);
        payload.addProperty("anonymous", anonymous);
        payload.addProperty("shareCodeFriendsOnly", shareCodeFriendsOnly);
        payload.addProperty("derivedFrom", derivedFrom != null ? derivedFrom.toString() : null);
        payload.add("profileData", profileData);
        return payload;
    }
}

