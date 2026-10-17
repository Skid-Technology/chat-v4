/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.config.ConfigJsonUtils;
import gg.vape.config.PublicProfileUser;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class PublicProfileSummary {
    private final long version;
    private final String name;
    @Nullable
    private final PublicProfileUser owner;
    private final List<String> tags;
    private final long likes;
    private final long dislikes;
    private final long profileId;
    @Nullable
    private String shareCode;

    public List<String> getTags() {
        return this.tags;
    }

    public String toString() {
        return "SimplePublicProfile{profileId=" + this.profileId + ", owner=" + this.owner + ", name='" + this.name + '\'' + ", version=" + this.version + ", likes=" + this.likes + ", dislikes=" + this.dislikes + ", tags=" + this.tags + '}';
    }

    @Nullable
    public PublicProfileUser getOwner() {
        return this.owner;
    }

    @Nullable
    public String getUppercaseShareCode() {
        if (this.shareCode == null) {
            return null;
        }
        return this.shareCode.toUpperCase();
    }

    public static PublicProfileSummary fromJson(JsonObject object) {
        if (object == null || object.isJsonNull()) {
            return null;
        }
        ArrayList<String> tags = new ArrayList<String>();
        if (object.has("tags") && !object.get("tags").isJsonNull() && object.get("tags").isJsonArray()) {
            for (JsonElement tagElement : object.get("tags").getAsJsonArray()) {
                tags.add(tagElement.getAsString());
            }
        }
        long profileId = object.has("profileId") && !object.get("profileId").isJsonNull() ? object.get("profileId").getAsLong() : 0L;
        PublicProfileUser owner = object.has("owner") && !object.get("owner").isJsonNull() ? PublicProfileUser.fromJson(object.get("owner")) : null;
        String name = object.has("name") && !object.get("name").isJsonNull() ? object.get("name").getAsString() : "Profile";
        long version = object.has("version") && !object.get("version").isJsonNull() ? object.get("version").getAsLong() : 1L;
        long likes = object.has("likes") && !object.get("likes").isJsonNull() ? object.get("likes").getAsLong() : 0L;
        long dislikes = object.has("dislikes") && !object.get("dislikes").isJsonNull() ? object.get("dislikes").getAsLong() : 0L;
        String shareCode = ConfigJsonUtils.getString(object, "shareCode");
        return new PublicProfileSummary(profileId, owner, name, version, likes, dislikes, tags, shareCode);
    }

    public long getLikes() {
        return this.likes;
    }

    PublicProfileSummary(long profileId, @Nullable PublicProfileUser owner, String name, long version, long likes, long dislikes, List<String> tags, @Nullable String shareCode) {
        this.profileId = profileId;
        this.owner = owner;
        this.name = name;
        this.version = version;
        this.likes = likes;
        this.dislikes = dislikes;
        this.tags = tags;
        this.shareCode = shareCode;
    }

    public long getVersion() {
        return this.version;
    }

    public String getName() {
        return this.name;
    }

    public long getProfileId() {
        return this.profileId;
    }

    public long getDislikes() {
        return this.dislikes;
    }
}

